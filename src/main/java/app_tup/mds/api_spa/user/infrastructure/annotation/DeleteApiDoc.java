package app_tup.mds.api_spa.user.infrastructure.annotation;

import app_tup.mds.api_spa.util.dto.StandardResponse;
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
        summary = "Delete user by dni",
        description = "Delete a user searched by their dni",
        tags = {"User"},
        responses = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Deleted user successfully",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(
                                        implementation = StandardResponse.class
                                )
                        )
                )
        }
)
public @interface DeleteApiDoc { }
