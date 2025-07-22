package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface IUserController {

    ResponseEntity<StandardResponse<PaginatedData<UserResponse>>> findAll(int pageIndex, int pageSize);
    ResponseEntity<StandardResponse<UserResponse>> findByDni(long dni) throws NotFoundException;
    ResponseEntity<StandardResponse<UserResponse>> save(UserRequest userRequest);
    ResponseEntity<StandardResponse<?>> delete(long dni) throws NotFoundException;

    // ResponseEntity<UserResponse> findById(String id) throws NotFoundException;
    // ResponseEntity<UserResponse> update(UserResponse userResponse);

}
