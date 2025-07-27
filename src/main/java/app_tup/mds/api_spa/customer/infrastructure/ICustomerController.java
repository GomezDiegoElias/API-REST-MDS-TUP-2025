package app_tup.mds.api_spa.customer.infrastructure;

import app_tup.mds.api_spa.customer.infrastructure.dto.CustomerResponse;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.StandardResponse;
import org.springframework.http.ResponseEntity;

public interface ICustomerController {
    ResponseEntity<StandardResponse<PaginatedData<CustomerResponse>>> findAll(int pageIndex, int pageSize);
}
