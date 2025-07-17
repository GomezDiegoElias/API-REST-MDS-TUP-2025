package app_tup.mds.api_spa.user.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String id;
    private long dni;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String salt;
    private Role role;
}
