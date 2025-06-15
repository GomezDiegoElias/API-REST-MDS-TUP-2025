package app_tup.mds.api_spa.user.infrastructure.mapper;

import app_tup.mds.api_spa.authentication.infrastructure.RegisterRequest;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User userEntityToUser(UserEntity userEntity);
    UserEntity userToUserEntity(User user);
    UserResponse userToUserResponse(User user);
    User userResponseToUser(UserResponse userResponse);
    User registerRequestToUser(RegisterRequest registerRequest);

}
