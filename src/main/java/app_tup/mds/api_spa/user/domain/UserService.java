package app_tup.mds.api_spa.user.domain;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByDni(long dni);
    User save(User user);
    void delete(long dni);

    //User findByEmail(String email);
    //User update(User user);
    //User findById(String id);
    //Boolean existsByEmail(String email);
}
