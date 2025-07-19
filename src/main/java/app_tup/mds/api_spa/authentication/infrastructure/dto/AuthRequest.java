package app_tup.mds.api_spa.authentication.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotNull(message = "The email is mandatory")
    @Email(message = "The given email does not match the pattern")
    private String email;

    @NotNull(message = "The password is mandatory")
    @Length(min = 3, message = "The password should be at least of 5 characters of length")
    private String password;

}
