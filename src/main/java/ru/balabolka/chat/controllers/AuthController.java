package ru.balabolka.chat.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.balabolka.chat.domain.User;
import ru.balabolka.chat.security.JwtProvider;
import ru.balabolka.chat.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userService.findUserByEmail(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
        String token = jwtProvider.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @Data
    private static class AuthRequest{
        private String email;
        private String password;
    }
    @Data
    @AllArgsConstructor
    private static class AuthResponse{
        private String token;
    }
}
