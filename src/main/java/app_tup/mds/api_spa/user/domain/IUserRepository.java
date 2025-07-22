package app_tup.mds.api_spa.user.domain;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {

    List<Object[]> findUsersPaginatedRaw(int pageIndex, int pageSize);
    Optional<User> findByDni(long dni);
    Optional<User> findByEmail(String email);
    User save(User user);
    void deleteByDni(long dni);

    // Optional<User> findById(String id);
    // User update(User user);
    // Boolean existsByEmail(String email);

}
