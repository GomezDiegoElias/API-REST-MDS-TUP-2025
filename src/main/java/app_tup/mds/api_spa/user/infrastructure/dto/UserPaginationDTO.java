package app_tup.mds.api_spa.user.infrastructure.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPaginationDTO {
    private String id;
    private long dni;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private int fila;
    private int totalFilas;
}
