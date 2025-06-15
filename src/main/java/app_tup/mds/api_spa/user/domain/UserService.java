package app_tup.mds.api_spa.user.domain;

import java.util.List;

public interface UserService {

    User update(User user);
    List<User> findAll();
    User findById(Long id);
    void delete(Long id);

}
