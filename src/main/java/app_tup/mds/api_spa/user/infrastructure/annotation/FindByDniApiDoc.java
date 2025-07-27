package app_tup.mds.api_spa.user.infrastructure.annotation;

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
        summary = "Find user by dni",
        description = "returns the user by his dni",
        tags = {"User"},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "User successfully obtained",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = UserResponse.class
                                )
                        )
                )
        }
)
public @interface FindByDniApiDoc { }
