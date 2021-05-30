package ru.balabolka.chat.services.defaultImpl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.repositories.UserRepository;
import ru.balabolka.chat.services.UserService;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean delete(Long id) {
        boolean rsl = false;
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            rsl = true;
        }
        return rsl;
    }
}
