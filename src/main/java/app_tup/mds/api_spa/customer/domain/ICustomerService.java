package app_tup.mds.api_spa.customer.domain;

import app_tup.mds.api_spa.util.dto.PaginatedData;

public interface ICustomerService {

    // find all customer
    // find customer dni
    // update customer

    PaginatedData<Customer> findAll(int pageIndex, int pageSize);
    Customer save(Customer customer);

}
