package app_tup.mds.api_spa.user.infrastructure.repository;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.IUserRepository;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MySqlIUserRepository implements IUserRepository {

    private final SpringUserRepository repository;

    @Override
    public List<Object[]> findUsersPaginatedRaw(int pageIndex, int pageSize) {
        return repository.findUsersPaginatedRaw(pageIndex, pageSize);
    }

    @Override
    public Optional<User> findByDni(long dni) {
        return repository.findByDni(dni).map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(UserMapper::toDomain);
    }

    @Override
    public User save(User userEntity) {
        UserEntity entity = UserMapper.toEntity(userEntity);
        UserEntity saved = repository.save(entity);
        return UserMapper.toDomain(saved);
    }

    @Override
    public void deleteByDni(long dni) {
        UserEntity user = repository.findByDni(dni).orElseThrow(() -> new NotFoundException("User does not exist with DNI: " + dni));
        repository.delete(user);
    }

    /*@Override
    public Optional<User> findById(String id) {
        return repository.findById(id).map(userMapper::toDomain);
    }*/

    /*@Override
    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }*/

}
