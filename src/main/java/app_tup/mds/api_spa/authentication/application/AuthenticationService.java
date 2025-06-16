package app_tup.mds.api_spa.authentication.application;

import app_tup.mds.api_spa.authentication.infrastructure.AuthenticationRequest;
import app_tup.mds.api_spa.authentication.infrastructure.AuthenticationResponse;
import app_tup.mds.api_spa.configuration.application.JwtService;
import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.UserRepository;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    public AuthenticationResponse register(User user) {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) throw new NotFoundException("error");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        User saved = userRepository.save(user);
        UserEntity userEntity = userMapper.userToUserEntity(saved);

        String jwtToken = jwtService.generateToken(userEntity);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        /*authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );*/

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new NotFoundException("Invalid credentials");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundException("User not found"));

        UserEntity userEntity = userMapper.userToUserEntity(user);

        String jwtToken = jwtService.generateToken(userEntity);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

}
