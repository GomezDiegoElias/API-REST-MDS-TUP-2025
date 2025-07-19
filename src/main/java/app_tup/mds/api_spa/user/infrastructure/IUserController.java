package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserController {

    ResponseEntity<List<UserResponse>> findAll();
    ResponseEntity<UserResponse> findByDni(long dni) throws NotFoundException;
    ResponseEntity<UserResponse> save(UserRequest userRequest);
    ResponseEntity<Void> delete(long dni) throws NotFoundException;
    //ResponseEntity<UserResponse> findById(String id) throws NotFoundException;
    //ResponseEntity<UserResponse> update(UserResponse userResponse);
}
