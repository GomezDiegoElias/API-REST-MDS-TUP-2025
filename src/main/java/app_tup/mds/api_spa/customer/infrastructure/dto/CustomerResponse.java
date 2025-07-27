package app_tup.mds.api_spa.customer.infrastructure.dto;

import app_tup.mds.api_spa.user.domain.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CustomerResponse {
    private String id;
    private long dni;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private LocalDate birthdate;
    @Enumerated(EnumType.STRING)
    private Status status;
}
