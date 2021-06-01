package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public List<User> getAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.findById(id);
        return new ResponseEntity<>(
                user.orElse(new User()),
                user.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:write')")
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> update(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return new ResponseEntity<>(userService.delete(id) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
