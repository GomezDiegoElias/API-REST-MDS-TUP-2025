package app_tup.mds.api_spa.customer.domain;

import app_tup.mds.api_spa.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private User user;
    private String phone;
    private LocalDate birthdate;
}
