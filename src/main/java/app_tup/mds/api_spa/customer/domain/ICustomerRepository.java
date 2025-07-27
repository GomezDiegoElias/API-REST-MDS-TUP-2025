package app_tup.mds.api_spa.customer.domain;

import java.util.List;

public interface ICustomerRepository {

    List<Object[]> findCustomersPaginatedRaw(int pageIndex, int pageSize);
    Customer save(Customer customer);
    // find all customers
    // find customer by dni

}
