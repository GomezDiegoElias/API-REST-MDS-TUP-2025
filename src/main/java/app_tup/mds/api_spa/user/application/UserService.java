package app_tup.mds.api_spa.user.application;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.IUserService;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.IUserRepository;
import app_tup.mds.api_spa.util.PasswordUtils;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;

    @Override
    public PaginatedData<User> findAll(int pageIndex, int pageSize) {
        List<Object[]> rows = userRepository.findUsersPaginatedRaw(pageIndex, pageSize);

        List<User> users = rows.stream().map(row -> User.builder()
                .id((String) row[0])
                .dni(((Number) row[1]).longValue())
                .email((String) row[2])
                .firstname((String) row[3])
                .lastname((String) row[4])
                .role(Role.valueOf((String) row[5]))
                .build()
        ).toList();

        int totalItems = rows.isEmpty() ? 0 : ((Number) rows.get(0)[7]).intValue();

        PaginationResponse pagination = PaginationResponse.builder()
                .totalItems(totalItems)
                .currentPage(pageIndex)
                .perPage(pageSize)
                .totalPages((int) Math.ceil((double) totalItems / pageSize))
                .build();

        return PaginatedData.<User>builder()
                .items(users)
                .pagination(pagination)
                .build();
    }

    @Override
    public User findByDni(long dni) throws NotFoundException {
        return userRepository.findByDni(dni).orElseThrow(() -> new NotFoundException("User does not exist with DNI: " + dni));
    }

    @Override
    public User save(User user) {

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot by empty");
        }

        String salt = PasswordUtils.generateRandomSalt();
        String hashPassword = PasswordUtils.hashPasswordWhitSalt(user.getPassword(), salt);

        user.setPassword(hashPassword);
        user.setSalt(salt);

        return userRepository.save(user);

    }

    @Override
    public void delete(long dni) {
        userRepository.deleteByDni(dni);
    }

    /*@Override
    public User update(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        BeanUtils.copyProperties(updateUser, user, "role");
        return userRepository.save(user);
    }*/

    /*@Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }*/

}
