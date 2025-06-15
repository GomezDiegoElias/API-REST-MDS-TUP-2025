package app_tup.mds.api_spa.user.domain;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User userEntity);
    Optional<User> findById(Long id);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    void deleteById(Long id);

}
