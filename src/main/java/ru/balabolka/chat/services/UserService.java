package ru.balabolka.chat.services;

import ru.balabolka.chat.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    boolean delete(Long id);
}
