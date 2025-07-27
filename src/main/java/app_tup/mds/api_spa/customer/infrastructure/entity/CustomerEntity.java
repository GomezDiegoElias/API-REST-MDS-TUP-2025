package app_tup.mds.api_spa.customer.infrastructure.entity;

import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "tbl_customer")
public class CustomerEntity extends UserEntity {

    @Column(nullable = false)
    private String phone;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthdate;

}
