package app_tup.mds.api_spa.authentication.application;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import app_tup.mds.api_spa.configuration.application.JwtService;
import app_tup.mds.api_spa.customer.domain.Customer;
import app_tup.mds.api_spa.customer.domain.ICustomerRepository;
import app_tup.mds.api_spa.customer.infrastructure.entity.CustomerEntity;
import app_tup.mds.api_spa.customer.infrastructure.mapper.CustomerMapper;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final IUserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    //private final AuthenticationManager authenticationManager;
    private final ICustomerRepository customerRepository;

    @Transactional
    public AuthResponse register(SingUpRequest request) {

        log.info("Attempting to register user with email: {}", request.getEmail());

        // Valida el email unico
        if (userRepository.findByEmail(request.getEmail()).isPresent()) throw new NotFoundException("error");

        // Genera el salt y hash
        String salt = PasswordUtils.generateRandomSalt();
        String hashedPassword = PasswordUtils.hashPasswordWhitSalt(request.getPassword(), salt);

        User user = User.builder()
                .dni(request.getDni())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(hashedPassword)
                .salt(salt)
                .role(Role.CUSTOMER)
                .status(Status.ACTIVE)
                .build();

        Customer customer = Customer.builder()
                .user(user)
                .phone(request.getPhone())
                .birthdate(request.getBirthdate())
                .build();

        Customer customerDomain = customerRepository.save(customer);
        CustomerEntity savedEntity = CustomerMapper.toEntity(customerDomain);

        // Agregar claima
        /*Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userEntity.getId());
        extraClaims.put("role", userEntity.getRole());
        extraClaims.put("app", "api-mds-hexagonal");*/

        // Claims por defecto
        Map<String, Object> extraClaims = jwtService.buildDefaultClaims(savedEntity);

        String accessToken = jwtService.generateAccessToken(extraClaims, savedEntity);
        String refreshToken = jwtService.generateRefreshToken(savedEntity);

        return AuthResponse.builder()
                .message("Register successfully")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {

        log.info("Authentication attempt for email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                   log.warn("User not found with email: {}", request.getEmail());
                   return new NotFoundException("Invalid credentials");
                });

        if (!PasswordUtils.verifyPassword(request.getPassword(), user.getPassword(), user.getSalt())) {
            log.warn("Incorrect password for user: {}", request.getEmail());
            throw new NotFoundException("Invalid credentials");
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

        UserEntity userEntity = UserMapper.toEntity(user);

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

            User userExisting = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new NotFoundException("User not found"));

            UserEntity user = UserMapper.toEntity(userExisting);

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
