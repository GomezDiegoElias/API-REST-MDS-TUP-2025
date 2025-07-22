package app_tup.mds.api_spa.user.domain;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.util.dto.PaginatedData;

import java.util.List;

public interface IUserService {

    PaginatedData<User> findAll(int pageIndex, int pageSize);
    User findByDni(long dni) throws NotFoundException;
    User save(User user);
    void delete(long dni);

    //User findByEmail(String email);
    //User update(User user);
    //User findById(String id);
    //Boolean existsByEmail(String email);

}
