package app_tup.mds.api_spa.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Role role;
}
