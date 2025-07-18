package app_tup.mds.api_spa.user.infrastructure.entity;

import app_tup.mds.api_spa.user.domain.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_users")
public class UserEntity implements UserDetails {

    @Id
    @Column(length = 50, updatable = false, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private long dni;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name")
    private String lastname;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, length = 250)
    private String hash;

    @Column(nullable = false, length = 250)
    private String salt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", nullable = false)
    private Role role = Role.USER;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            String timestamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String uuidPart = UUID.randomUUID().toString().substring(0, 8);
            this.id = "usr_" + timestamp + "_" + uuidPart;
        }
    }

}
