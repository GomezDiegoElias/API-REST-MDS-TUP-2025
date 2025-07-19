package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.IUserService;
import app_tup.mds.api_spa.user.infrastructure.dto.UserRequest;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "User", description = "User endpoints")
//@PreAuthorize("hasRole('ADMIN')")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController implements IUserController {

    private final IUserService userService;
    private final UserMapper userMapper;

    @Operation(
            summary = "Find all users",
            description = "returns all registered users",
            tags = {"User"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "All users have been successfully obtained",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    @Override
    public ResponseEntity<List<UserResponse>> findAll() {
        List<UserResponse> userDTOS = userService.findAll().stream().map(userMapper::userToUserResponse).toList();
        return ResponseEntity.ok(userDTOS);
    }

    @Operation(
            summary = "Find user by dni",
            description = "returns the user by his dni",
            tags = {"User"},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User successfully obtained",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    @GetMapping("/{dni}")
    @Override
    public ResponseEntity<UserResponse> findByDni(@PathVariable long dni) throws NotFoundException {
        User user = userService.findByDni(dni);
        UserResponse userDTO = userMapper.userToUserResponse(user);
        return ResponseEntity.ok(userDTO);
    }

    @Operation(
            summary = "Save user",
            description = "Save a new user",
            tags = {"User"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request to create a new user with dni, first name, last name, email, password, and role",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = UserRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "New user created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = UserResponse.class
                                    )
                            )
                    )
            }
    )
    @PostMapping
    @Override
    public ResponseEntity<UserResponse> save(@RequestBody UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        User saved = userService.save(user);
        UserResponse userResponse = userMapper.userToUserResponse(saved);
        return ResponseEntity.ok(userResponse);
    }

    @Operation(
            summary = "Delete user by dni",
            description = "Delete a user searched by their dni",
            tags = {"User"}
    )
    @DeleteMapping("/{dni}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable long dni) throws NotFoundException {
        userService.delete(dni);
        return ResponseEntity.noContent().build(); // 204 No Content
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
