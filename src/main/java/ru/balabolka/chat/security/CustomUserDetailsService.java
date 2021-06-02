package ru.balabolka.chat.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.services.UserService;

@Service("customUserDetailsService")
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        return CustomUser.fromUserToCustomUser(user);
    }
}
