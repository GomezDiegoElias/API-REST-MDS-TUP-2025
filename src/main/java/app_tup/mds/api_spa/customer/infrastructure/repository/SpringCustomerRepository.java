package app_tup.mds.api_spa.customer.infrastructure.repository;

import app_tup.mds.api_spa.customer.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringCustomerRepository extends JpaRepository<CustomerEntity, Long> {

    // custom methods

    @Query(value = "CALL getCustomerPagination(:pageIndex, :pageSize)", nativeQuery = true)
    List<Object[]> findCustomersPaginatedRaw(
            @Param("pageIndex") int pageIndex,
            @Param("pageSize") int pageSize
    );

}
