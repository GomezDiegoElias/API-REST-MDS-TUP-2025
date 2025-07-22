package app_tup.mds.api_spa.util.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Metadatos de paginación")
public class PaginationResponse {
    @Schema(
            description = "Total de ítems disponibles",
            example = "100"
    )
    private Integer totalItems;

    @Schema(
            description = "Página actual",
            example = "1"
    )
    private Integer currentPage;

    @Schema(
            description = "Ítems por página",
            example = "10"
    )
    private Integer perPage;

    @Schema(
            description = "Total de páginas disponibles",
            example = "10"
    )
    private Integer totalPages;
}