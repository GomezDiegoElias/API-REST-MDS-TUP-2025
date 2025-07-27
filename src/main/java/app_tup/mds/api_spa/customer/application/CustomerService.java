package app_tup.mds.api_spa.customer.application;

import app_tup.mds.api_spa.customer.domain.Customer;
import app_tup.mds.api_spa.customer.domain.ICustomerRepository;
import app_tup.mds.api_spa.customer.domain.ICustomerService;
import app_tup.mds.api_spa.user.domain.Role;
import app_tup.mds.api_spa.user.domain.Status;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.util.dto.PaginatedData;
import app_tup.mds.api_spa.util.dto.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    public PaginatedData<Customer> findAll(int pageIndex, int pageSize) {
        List<Object[]> rows = customerRepository.findCustomersPaginatedRaw(pageIndex, pageSize);

        List<Customer> customers = rows.stream()
                .map(row -> Customer.builder()
                        .user(mapUserFromRow(row))
                        .phone((String) row[5])  // phone en posición 5
                        .birthdate(row[6] != null ? ((java.sql.Date) row[6]).toLocalDate() : null) // birthdate en 6
                        .build())
                .toList();

        int totalItems = rows.isEmpty() ? 0 : ((Number) rows.get(0)[8]).intValue();

        PaginationResponse pagination = PaginationResponse.builder()
                .totalItems(totalItems)
                .currentPage(pageIndex)
                .perPage(pageSize)
                .totalPages((int) Math.ceil((double) totalItems / pageSize))
                .build();

        return PaginatedData.<Customer>builder()
                .items(customers)
                .pagination(pagination)
                .build();
    }

    @Override
    public Customer save(Customer customer) {
        return null;
    }

    private User mapUserFromRow(Object[] row) {
        return User.builder()
                .id((String) row[0])
                .dni(((Number) row[1]).longValue())
                .email((String) row[2])
                .firstname((String) row[3])
                .lastname((String) row[4])
                .password(null)
                .salt(null)
                .role(Role.CUSTOMER)
                .status(Status.ACTIVE)
                .build();
    }

}
