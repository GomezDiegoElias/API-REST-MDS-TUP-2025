package app_tup.mds.api_spa.customer.infrastructure.mapper;

import app_tup.mds.api_spa.customer.domain.Customer;
import app_tup.mds.api_spa.customer.infrastructure.entity.CustomerEntity;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;

public final class CustomerMapper {

    public static Customer toDomain(CustomerEntity entity) {
        User user = UserMapper.toDomain(entity);
        return Customer.builder()
                .user(user)
                .phone(entity.getPhone())
                .birthdate(entity.getBirthdate())
                .build();
    }

    public static CustomerEntity toEntity(Customer domain) {
        UserEntity userEntity = UserMapper.toEntity(domain.getUser());
        return CustomerEntity.builder()
                .id(userEntity.getId())
                .dni(userEntity.getDni())
                .firstname(userEntity.getFirstname())
                .lastname(userEntity.getLastname())
                .email(userEntity.getEmail())
                .hash(userEntity.getHash())
                .salt(userEntity.getSalt())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .phone(domain.getPhone())
                .birthdate(domain.getBirthdate())
                .build();
    }

}
