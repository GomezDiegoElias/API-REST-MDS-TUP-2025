package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserController {

    ResponseEntity<UserResponse> save(UserRequest userRequest);
    ResponseEntity<UserResponse> findById(String id);
    ResponseEntity<List<UserResponse>> findAll();
    ResponseEntity<UserResponse> update(UserResponse userResponse);
    ResponseEntity<Void> delete(Long id);

}
