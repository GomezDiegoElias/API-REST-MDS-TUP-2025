package app_tup.mds.api_spa.user.application;

import app_tup.mds.api_spa.exception.domain.NotFoundException;
import app_tup.mds.api_spa.user.domain.User;
import app_tup.mds.api_spa.user.domain.UserRepository;
import app_tup.mds.api_spa.user.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByDni(long dni) {
        return userRepository.findByDni(dni).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(long dni) {
        userRepository.deleteByDni(dni);
    }

    /*@Override
    public User update(User updateUser) {
        User user = userRepository.findById(updateUser.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        BeanUtils.copyProperties(updateUser, user, "role");
        return userRepository.save(user);
    }*/

    /*@Override
    public User findById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }*/

}
