package app_tup.mds.api_spa.customer.infrastructure.repository;

import app_tup.mds.api_spa.customer.domain.Customer;
import app_tup.mds.api_spa.customer.domain.ICustomerRepository;
import app_tup.mds.api_spa.customer.infrastructure.entity.CustomerEntity;
import app_tup.mds.api_spa.customer.infrastructure.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MySqlCustomerRepository implements ICustomerRepository {

    private final SpringCustomerRepository repository;

    @Override
    public List<Object[]> findCustomersPaginatedRaw(int pageIndex, int pageSize) {
        return repository.findCustomersPaginatedRaw(pageIndex, pageSize);
    }

    @Override
    public Customer save(Customer customer) {
        CustomerEntity entity = CustomerMapper.toEntity(customer);
        CustomerEntity saved = repository.save(entity);
        return CustomerMapper.toDomain(saved);
    }

}
