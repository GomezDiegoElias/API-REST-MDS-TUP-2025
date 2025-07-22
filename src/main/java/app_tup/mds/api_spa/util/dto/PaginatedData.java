package app_tup.mds.api_spa.util.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Estructura de datos paginados")
public class PaginatedData<T> {

    @Schema(
            description = "Lista de items de la pagina actual"
    )
    private List<T> items;

    @Schema(
            description = "Metadatos de paginacion"
    )
    private PaginationResponse pagination;

}
