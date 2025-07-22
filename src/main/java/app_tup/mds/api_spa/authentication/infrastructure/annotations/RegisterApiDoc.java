package app_tup.mds.api_spa.authentication.infrastructure.annotations;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Operation(
        summary = "Sing up user",
        description = "Register a user and return the authentication token",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Registration application with ID, first name, last name, email, password and role",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = SingUpRequest.class
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "User successfully registered",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = AuthResponse.class
                                )
                        )
                )
        }
)
public @interface RegisterApiDoc { }
