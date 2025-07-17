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

import java.util.HashMap;
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

    public AuthenticationResponse register(RegisterRequest request) {

        log.info("Attempting to register user with email: {}", request.getEmail());
        // Verifica si existe un usuario con ese email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new NotFoundException("error");

        User user = userMapper.registerRequestToUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        UserEntity userSaved = userMapper.userToUserEntity(userRepository.save(user));

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userSaved.getId());
        extraClaims.put("role", userSaved.getRole());
        extraClaims.put("app", "api-mds-hexagonal");

        String accessToken = jwtService.generateAccessToken(
                extraClaims,
                userSaved
        );
        String refreshToken = jwtService.generateRefreshToken(userSaved);

        return AuthenticationResponse.builder()
                .message("Register successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        log.info("Authentication attempt for email: {}", request.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new NotFoundException("Invalid credentials"); // Credenciales incorrectas
        }

        User userExisting = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity user = userMapper.userToUserEntity(userExisting);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("dni", user.getDni());
        extraClaims.put("userId", user.getId());
        extraClaims.put("role", user.getRole());
        extraClaims.put("app", "api-mds-hexagonal");

        String accessToken = jwtService.generateAccessToken(extraClaims, user);
        String refreshToken = jwtService.generateRefreshToken(user);

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

}
