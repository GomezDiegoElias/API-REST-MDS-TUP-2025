package app_tup.mds.api_spa.customer.infrastructure;

import app_tup.mds.api_spa.customer.domain.Customer;
import app_tup.mds.api_spa.customer.domain.ICustomerService;
import app_tup.mds.api_spa.customer.infrastructure.annotation.FindAllApiDoc;
import app_tup.mds.api_spa.customer.infrastructure.dto.CustomerResponse;
import app_tup.mds.api_spa.customer.infrastructure.mapper.CustomerMapper;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.StandardResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Customer", description = "Customer endpoints")
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController implements ICustomerController {

    private final ICustomerService customerService;

    @FindAllApiDoc
    @GetMapping
    @Override
    public ResponseEntity<StandardResponse<PaginatedData<CustomerResponse>>> findAll(
            @RequestParam(defaultValue = "1") int pageIndex,
            @RequestParam(defaultValue = "5") int pageSize
    ) {

        PaginatedData<Customer> paginatedCustomers = customerService.findAll(pageIndex, pageSize);

        List<CustomerResponse> customerResponses = paginatedCustomers.getItems().stream()
                .map(CustomerMapper::domainToCustomerResponse)
                .toList();

        PaginatedData<CustomerResponse> responseData = PaginatedData.<CustomerResponse>builder()
                .items(customerResponses)
                .pagination(paginatedCustomers.getPagination())
                .build();

        StandardResponse<PaginatedData<CustomerResponse>> response = StandardResponse.<PaginatedData<CustomerResponse>>builder()
                .success(true)
                .message("Customers successfully obtained")
                .data(responseData)
                .error(null)
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(response);

    }

}
