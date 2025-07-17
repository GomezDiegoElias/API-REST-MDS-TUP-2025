package app_tup.mds.api_spa.user.infrastructure.repository;

import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringUserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByDni(long dni);
    //Optional<UserEntity> findById(String id);
}
