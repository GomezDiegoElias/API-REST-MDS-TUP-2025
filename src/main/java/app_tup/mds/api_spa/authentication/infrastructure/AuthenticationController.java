package app_tup.mds.api_spa.authentication.infrastructure;

import app_tup.mds.api_spa.authentication.application.AuthenticationService;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthenticationRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthenticationResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.RegisterRequest;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Controlador para la Autenticación")
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserMapper userMapper;

    @Operation(
            summary = "Inicio de Sesión del Usuario",
            description = "Autentica un usuario y devuelve el token y refresh token",
            tags = {"Autenticación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de autenticación con email y contraseña",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = AuthenticationRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Has iniciado sesión correctamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AuthenticationResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @Operation(
            summary = "Registro de Usuario",
            description = "Registrar un usuario y devolver el token de autenticación",
            tags = {"Autenticación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Solicitud de registro con dni, nombre, apellido, email, password, rol",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = RegisterRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario registrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = AuthenticationResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

}
