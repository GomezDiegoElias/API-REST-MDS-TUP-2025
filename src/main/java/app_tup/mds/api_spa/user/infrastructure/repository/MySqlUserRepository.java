package app_tup.mds.api_spa.user.infrastructure.repository;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.UserRepository;
import app_tup.mds.api_spa.user.infrastructure.entity.UserEntity;
import app_tup.mds.api_spa.user.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MySqlUserRepository implements UserRepository {

    private final SpringUserRepository springUserRepository;
    private final UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return springUserRepository.findAll().stream().map(userMapper::userEntityToUser).toList();
    }

    @Override
    public Optional<User> findByDni(long dni) {
        return springUserRepository.findByDni(dni).map(userMapper::userEntityToUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springUserRepository.findByEmail(email).map(userMapper::userEntityToUser);
    }

    @Override
    public User save(User userEntity) {
        UserEntity entity = userMapper.userToUserEntity(userEntity);
        UserEntity saved = springUserRepository.save(entity);
        return userMapper.userEntityToUser(saved);
    }

    @Override
    public void deleteByDni(long dni) {
        UserEntity user = springUserRepository.findByDni(dni).orElseThrow(() -> new NotFoundException("User not found"));
        springUserRepository.delete(user);
    }

    /*@Override
    public Optional<User> findById(String id) {
        return springUserRepository.findById(id).map(userMapper::userEntityToUser);
    }*/

    /*@Override
    public Boolean existsByEmail(String email) {
        return springUserRepository.existsByEmail(email);
    }*/

}
