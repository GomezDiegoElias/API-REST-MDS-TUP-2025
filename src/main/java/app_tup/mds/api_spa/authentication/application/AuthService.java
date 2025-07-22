package app_tup.mds.api_spa.authentication.application;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import app_tup.mds.api_spa.configuration.application.JwtService;
import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.Status;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.IUserRepository;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import app_tup.mds.api_spa.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository IUserRepository;
    //private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;

    public AuthResponse register(SingUpRequest request) {

        log.info("Attempting to register user with email: {}", request.getEmail());

        // Valida el email unico
        if (IUserRepository.findByEmail(request.getEmail()).isPresent()) throw new NotFoundException("error");

        // Genera el salt
        String salt = PasswordUtils.generateRandomSalt();
        log.debug("Generated salt: {}", salt);

        String hashedPassword = PasswordUtils.hashPasswordWhitSalt(request.getPassword(), salt);
        log.debug("Generated hash: {}", hashedPassword);

        User user = User.builder()
                .dni(request.getDni())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(hashedPassword)
                .salt(salt)
                .build();

        // Guardado del usuario
        User savedUser = IUserRepository.save(user);
        UserEntity userEntity = UserMapper.userToUserEntity(savedUser);

        // Agregar claima
        /*Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userEntity.getId());
        extraClaims.put("role", userEntity.getRole());
        extraClaims.put("app", "api-mds-hexagonal");*/

        // Claims por defecto
        Map<String, Object> extraClaims = jwtService.buildDefaultClaims(userEntity);

        String accessToken = jwtService.generateAccessToken(extraClaims, userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return AuthResponse.builder()
                .message("Register successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {

        log.info("Authentication attempt for email: {}", request.getEmail());

        User user = IUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                   log.warn("Usuario no encontrado con email: {}", request.getEmail());
                   return new NotFoundException("Credenciales invalidas");
                });

        if (PasswordUtils.verifyPassword(request.getPassword(), user.getPassword(), user.getSalt())) {
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

        UserEntity userEntity = UserMapper.userToUserEntity(user);

        Map<String, Object> extraClaims = jwtService.buildDefaultClaims(userEntity);

        String accessToken = jwtService.generateAccessToken(extraClaims, userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return AuthResponse.builder()
                .message("Login successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse refreshToken(String refreshToken) {

        try {

            String userEmail = jwtService.extractUsername(refreshToken);

            if (userEmail == null) throw new IllegalArgumentException("Invalid refresh token");

            User userExisting = IUserRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            UserEntity user = UserMapper.userToUserEntity(userExisting);

            if (!jwtService.isTokenValid(refreshToken, user)) throw new IllegalArgumentException("Refresh token is invalid or expired");

            Map<String, Object> extraClaims = jwtService.buildDefaultClaims(user);

            String accessToken = jwtService.generateAccessToken(extraClaims, user);
            // Opcional si queremos generar un nuevo refresh token (rotacion de tokens)
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return AuthResponse.builder()
                    .message("Refresh token successfully")
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Refresh token failed: " + e.getMessage());
        }

    }

}
