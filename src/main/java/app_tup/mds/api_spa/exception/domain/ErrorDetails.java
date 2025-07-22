package app_tup.mds.api_spa.exception.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Detalles estructurados de un error")
public class ErrorDetails {

    @Schema(
            description = "Mensaje tecnico (para desarrolladores)",
            example = "User not found in database",
            nullable = true
    )
    private String message;

    @Schema(
            description = "Descripcion legible para el cliente",
            example = "El usuario no existe"
    )
    private String details;

    @Schema(
            description = "Url donde ocurrio el error",
            example = "/api/user/123"
    )
    private String path;

}
