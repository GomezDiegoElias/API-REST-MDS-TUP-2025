package app_tup.mds.api_spa.user.infrastructure.mapper;

import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.Status;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;

public final class UserMapper {

    public static User userEntityToUser(UserEntity e) {
        return User.builder()
                .id(e.getId())
                .dni(e.getDni())
                .firstname(e.getFirstname())
                .lastname(e.getLastname())
                .email(e.getEmail())
                .password(e.getHash())
                .salt(e.getSalt())
                .role(e.getRole() != null ? e.getRole() : Role.CUSTOMER)
                .status(e.getStatus() != null ? e.getStatus() : Status.ACTIVE)
                .build();
    }

    public static UserEntity userToUserEntity(User u) {
        return UserEntity.builder()
                .id(u.getId())
                .dni(u.getDni())
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .hash(u.getPassword())
                .salt(u.getSalt())
                .role(u.getRole() != null ? u.getRole() : Role.CUSTOMER)
                .status(u.getStatus() != null ? u.getStatus() : Status.ACTIVE)
                .build();
    }

    public static UserResponse userToUserResponse(User u) {
        return UserResponse.builder()
                .id(u.getId())
                .dni(u.getDni())
                .firstname(u.getFirstname())
                .lastname(u.getLastname())
                .email(u.getEmail())
                .role(u.getRole())
                .status(u.getStatus())
                .build();
    }

    public static User userResponseToUser(UserResponse r) {
        return User.builder()
                .id(r.getId())
                .dni(r.getDni())
                .firstname(r.getFirstname())
                .lastname(r.getLastname())
                .email(r.getEmail())
                .role(r.getRole())
                .status(r.getStatus())
                .build();
    }

    public static User userRequestToUser(UserRequest r) {
        return User.builder()
                .dni(r.getDni())
                .firstname(r.getFirstname())
                .lastname(r.getLastname())
                .email(r.getEmail())
                .password(r.getPassword())
                .role(r.getRole())
                .build();
    }

    public static User registerRequestToUser(SingUpRequest s) {
        return User.builder()
                .dni(s.getDni())
                .firstname(s.getFirstname())
                .lastname(s.getLastname())
                .email(s.getEmail())
                .password(s.getPassword())
                .build();
    }

}
