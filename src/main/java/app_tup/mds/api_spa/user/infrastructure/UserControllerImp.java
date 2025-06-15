package app_tup.mds.api_spa.user.infrastructure;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.UserService;
import app_tup.mds.api_spa.user.infrastructure.dto.UserResponse;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserControllerImp implements UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) throws NotFoundException {

        User user = userService.findById(id);

        UserResponse userDTO = userMapper.userToUserResponse(user);

        return ResponseEntity.ok(userDTO);

    }

    @GetMapping
    @Override
    public ResponseEntity<List<UserResponse>> findAll() {

        List<UserResponse> userDTOS = userService.findAll().stream().map(userMapper::userToUserResponse).toList();

        return ResponseEntity.ok(userDTOS);

    }

    @PutMapping
    @Override
    public ResponseEntity<UserResponse> update(@RequestBody UserResponse userResponse) {

        User user = userMapper.userResponseToUser(userResponse);

        User updated = userService.update(user);

        UserResponse updatedResponse = userMapper.userToUserResponse(updated);

        return ResponseEntity.ok(updatedResponse);

    }

    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        userService.delete(id);
        return ResponseEntity.noContent().build();

    }

}
