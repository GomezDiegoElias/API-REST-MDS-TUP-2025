package app_tup.mds.api_spa.user.infrastructure.mapper;

import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "password", source = "hash")
    User userEntityToUser(UserEntity userEntity);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "role", target = "role")
    @Mapping(source = "id", target = "id")
    @Mapping(target = "hash", source = "password")
    @Mapping(target = "salt", source = "salt")
    UserEntity userToUserEntity(User user);

    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "id", target = "id")
    UserResponse userToUserResponse(User user);

    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "id", target = "id")
    User userResponseToUser(UserResponse userResponse);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User userRequestToUser(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    //@Mapping(target = "role", constant = "USER")
    @Mapping(target = "role", ignore = true)
    @Mapping(source = "dni", target = "dni")
    User registerRequestToUser(SingUpRequest singUpRequest);

}
