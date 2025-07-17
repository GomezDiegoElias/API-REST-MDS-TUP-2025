package app_tup.mds.api_spa.user.infrastructure.mapper;

import app_tup.mds.api_spa.authentication.infrastructure.dto.RegisterRequest;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User userEntityToUser(UserEntity userEntity);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "role", target = "role")
    UserEntity userToUserEntity(User user);
    UserResponse userToUserResponse(User user);
    User userResponseToUser(UserResponse userResponse);

    @Mapping(source = "firstname", target = "firstname")
    @Mapping(source = "lastname", target = "lastname")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User userRequestToUser(UserRequest userRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    User registerRequestToUser(RegisterRequest registerRequest);

}
