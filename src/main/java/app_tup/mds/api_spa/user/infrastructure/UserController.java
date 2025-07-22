package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.IUserService;
import app_tup.mds.api_spa.user.infrastructure.annotation.DeleteApiDoc;
import app_tup.mds.api_spa.user.infrastructure.annotation.FindAllApiDoc;
import app_tup.mds.api_spa.user.infrastructure.annotation.FindByDniApiDoc;
import app_tup.mds.api_spa.user.infrastructure.annotation.SaveApiDoc;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User endpoints")
//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final IUserService userService;
    //private final UserMapperStruct userMapper;
    //private final UserMapper userMapper;

    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @FindAllApiDoc
    @GetMapping
    @Override
    public ResponseEntity<StandardResponse<PaginatedData<UserResponse>>> findAll(
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        PaginatedData<User> paginatedUsers = userService.findAll(pageIndex, pageSize);

        List<UserResponse> userResponses = paginatedUsers.getItems().stream()
                .map(UserMapper::userToUserResponse)
                .toList();

        PaginatedData<UserResponse> responseData = PaginatedData.<UserResponse>builder()
                .items(userResponses)
                .pagination(paginatedUsers.getPagination())
                .build();

        StandardResponse<PaginatedData<UserResponse>> response = StandardResponse.<PaginatedData<UserResponse>>builder()
                .success(true)
                .message("Users successfully obtained")
                .data(responseData)
                .error(null)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);
    }

    @FindByDniApiDoc
    @GetMapping("/{dni}")
    @Override
    public ResponseEntity<StandardResponse<UserResponse>> findByDni(@PathVariable long dni) throws NotFoundException {

        User user = userService.findByDni(dni);
        UserResponse userDTO = UserMapper.userToUserResponse(user);

        StandardResponse<UserResponse> response = StandardResponse.<UserResponse>builder()
                .success(true)
                .message("User successfully obtained")
                .data(userDTO)
                .error(null)
                .status(HttpStatus.OK.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @SaveApiDoc
    @PostMapping
    @Override
    public ResponseEntity<StandardResponse<UserResponse>> save(@RequestBody UserRequest userRequest) {

        User user = UserMapper.userRequestToUser(userRequest);
        User saved = userService.save(user);
        UserResponse userResponse = UserMapper.userToUserResponse(saved);

        StandardResponse<UserResponse> response = StandardResponse.<UserResponse>builder()
                .success(true)
                .message("New user created successfully")
                .data(userResponse)
                .error(null)
                .status(HttpStatus.CREATED.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @DeleteApiDoc
    @DeleteMapping("/{dni}")
    @Override
    public ResponseEntity<StandardResponse<?>> delete(@PathVariable long dni) throws NotFoundException {

        userService.delete(dni);

        StandardResponse<?> response = StandardResponse.builder()
                .success(true)
                .message("Deleted user successfully")
                .data(null)
                .error(null)
                .status(HttpStatus.OK.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /*@GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponse> findById(@PathVariable String id) throws NotFoundException {
        User user = userService.findById(id);
        UserResponse userDTO = userMapper.userToUserResponse(user);
        return ResponseEntity.ok(userDTO);
    }*/

    /*@PutMapping
    @Override
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UserResponse userResponse) {
        User user = userMapper.userResponseToUser(userResponse);
        User updated = userService.update(user);
        UserResponse updatedResponse = userMapper.userToUserResponse(updated);
        return ResponseEntity.ok(updatedResponse);
    }*/

}
