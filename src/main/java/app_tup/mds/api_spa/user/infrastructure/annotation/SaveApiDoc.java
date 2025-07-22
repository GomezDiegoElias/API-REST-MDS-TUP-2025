package app_tup.mds.api_spa.user.infrastructure.annotation;

import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
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
        summary = "Save user",
        description = "Save a new user",
        tags = {"User"},
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Request to create a new user with dni, first name, last name, email, password, and role",
                required = true,
                content = @Content(
                        mediaType = "application/json",
                        schema = @Schema(
                                implementation = UserRequest.class
                        )
                )
        ),
        responses = {
                @ApiResponse(
                        responseCode = "201",
                        description = "New user created successfully",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = UserResponse.class
                                )
                        )
                )
        }
)
public @interface SaveApiDoc { }
