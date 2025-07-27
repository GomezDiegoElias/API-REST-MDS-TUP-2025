package app_tup.mds.api_spa.authentication.infrastructure.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingUpRequest {

    @NotNull(message = "The dni is mandatory")
    @Positive(message = "DNI must be a positive number")
    private long dni;

    @NotNull(message = "The firstname is mandatory")
    @NotBlank(message = "The firstname is mandatory")
    private String firstname;

    @NotNull(message = "The lastname is mandatory")
    @NotBlank(message = "The lastname is mandatory")
    private String lastname;

    @NotNull(message = "The email is mandatory")
    @NotBlank(message = "The email is mandatory")
    @Email(message = "The given email does not match the pattern")
    //@UniqueElements()
    private String email;

    @NotNull(message = "The password is mandatory")
    @NotBlank(message = "The password is mandatory")
    @Length(min = 4, message = "The password should be at least of 5 characters of length")
    private String password;

    private String phone;

    private LocalDate birthdate;

}
