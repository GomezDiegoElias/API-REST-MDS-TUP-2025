package app_tup.mds.api_spa.customer.infrastructure.repository;

import app_tup.mds.api_spa.customer.infrastructure.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    // custom methods
}
