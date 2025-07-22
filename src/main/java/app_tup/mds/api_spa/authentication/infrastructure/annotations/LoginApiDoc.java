package app_tup.mds.api_spa.authentication.infrastructure.annotations;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
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
        summary = "Login",
        description = "User login",
        tags = {"Authentication"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Authentication request with email and password",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = AuthRequest.class
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "You have successfully logged in",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = AuthResponse.class
                                )
                        )
                )
        }
)
public @interface LoginApiDoc { }
