package app_tup.mds.api_spa.util.dto;

import app_tup.mds.api_spa.exception.domain.ErrorDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Estructura estandar de respuesta de la API")
public class StandardResponse<T> {

    @Schema(
            description = "Indica si la solicitud fue exitosa",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean success;

    @Schema(
            description = "Mensaje descriptivo para el cliente",
            example = "Operacion completada con exito"
    )
    private String message;

    @Schema(
            description = "Datos de la respuesta (puede ser un objeto, lista o paginacion)",
            nullable = true
    )
    private T data;

    @Schema(
            description = "Detalles del error (si aplica)",
            nullable = true
    )
    private ErrorDetails error;

    @Schema(
            description = "Codigo HTTP de la respuesta",
            example = "200",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Integer status;

}
