package app_tup.mds.api_spa.authentication.application;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthenticationRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthenticationResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.RegisterRequest;
import app_tup.mds.api_spa.configuration.application.JwtService;
import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.UserRepository;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    private final int saltLength = 16; // longitud para el salt
    private final int hashIterations = 10000; // numero de iteracion

    public AuthenticationResponse register(RegisterRequest request) {

        log.info("Attempting to register user with email: {}", request.getEmail());

        // Valida el email unico
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new NotFoundException("error");

        // Genera el salt
        String salt = generateRandomSalt(saltLength);
        log.debug("Generated salt: {}", salt);

        String hashedPassword = hashPasswordWithSalt(request.getPassword(), salt);
        log.debug("Generated hash: {}", hashedPassword);

        User user = User.builder()
                .dni(request.getDni())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(hashedPassword)
                .salt(salt)
                .role(Role.USER)
                .build();

        // Guardado del usuario
        User savedUser = userRepository.save(user);
        UserEntity userEntity = userMapper.userToUserEntity(savedUser);

        // Generar tokens
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userEntity.getId());
        extraClaims.put("role", userEntity.getRole());
        extraClaims.put("app", "api-mds-hexagonal");

        String accessToken = jwtService.generateAccessToken(extraClaims, userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return AuthenticationResponse.builder()
                .message("Register successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        log.info("Authentication attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                   log.warn("Usuario no encontrado con email: {}", request.getEmail());
                   return new NotFoundException("Credenciales invalidas");
                });

        if (!verifyPassword(request.getPassword(), user.getPassword(), user.getSalt())) {
            log.warn("Contraseña incorrecta para usuario: {}", request.getEmail());
            throw new NotFoundException("Credenciales inválidas");
        }

        // Primero verifica con AuthenticationManager
        /*try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.error("Error en autenticación para {}: {}", request.getEmail(), e.getMessage());
            throw new NotFoundException("Invalid credentials"); // Credenciales incorrectas
        }*/

        UserEntity userEntity = userMapper.userToUserEntity(user);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("dni", userEntity.getDni());
        extraClaims.put("userId", userEntity.getId());
        extraClaims.put("role", userEntity.getRole());
        extraClaims.put("app", "api-mds-hexagonal");

        String accessToken = jwtService.generateAccessToken(extraClaims, userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return AuthenticationResponse.builder()
                .message("Login successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse refreshToken(String refreshToken) {

        try {

            String userEmail = jwtService.extractUsername(refreshToken);

            if (userEmail == null) throw new IllegalArgumentException("Invalid refresh token");

            User userExisting = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            UserEntity user = userMapper.userToUserEntity(userExisting);

            if (!jwtService.isTokenValid(refreshToken, user)) throw new IllegalArgumentException("Refresh token is invalid or expired");

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("dni", user.getDni());
            extraClaims.put("userId", user.getId());
            extraClaims.put("role", user.getRole());
            extraClaims.put("app", "api-mds-hexagonal");

            String accessToken = jwtService.generateAccessToken(extraClaims, user);
            // Opcional si queremos generar un nuevo refresh token (rotacion de tokens)
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return AuthenticationResponse.builder()
                    .message("Refresh token successfully")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Refresh token failed: " + e.getMessage());
        }

    }

    // Genera el salt aleatorio
    private String generateRandomSalt(int length) {

        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, validChars.length())
                .mapToObj(validChars::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();

    }

    // Hashea la constraseña con salt
    private String hashPasswordWithSalt(String password, String salt) {

        try {

            byte[] saltBytes = salt.getBytes(StandardCharsets.UTF_8);
            PBEKeySpec spec = new PBEKeySpec(
                    password.toCharArray(),
                    saltBytes,
                    hashIterations,
                    256
            );
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new RuntimeException("Error al hashear la contraseña", e);
        }

    }

    // Verificar contraseña
    private boolean verifyPassword(String inputPassword, String storedHash, String salt) {
        String hashedInput = hashPasswordWithSalt(inputPassword, salt);
        return hashedInput.equals(storedHash);
    }

}
