package app_tup.mds.api_spa.user.infrastructure.repository;

import app_tup.mds.api_spa.user.domain.Status;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringUserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByDni(long dni);

    @Query(value = "CALL getUserPagination(:pageIndex, :pageSize)", nativeQuery = true)
    List<Object[]> findUsersPaginatedRaw(
            @Param("pageIndex") int pageIndex,
            @Param("pageSize") int pageSize
    );

    // Methods to change state

    @Modifying
    @Query("UPDATE UserEntity u SET u.status = :status WHERE u.dni = :dni")
    void changeStatus(@Param("dni") long dni, @Param("status")Status status);

    // Filtered queries
    // all actives -> ACTIVE
    // all deleted -> DELETED
    // all visible -> ACTIVE, INACTIVE

}
