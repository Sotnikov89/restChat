package ru.balabolka.chat.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtProvider {

    @Value("$(jwt.secret)")
    private String secretKey;
    @Value("#{new Long('${jwt.validDays}')}")
    private long validDays;
    @Value("$(jwt.header)")
    private String authHeader;

    private final UserDetailsService userDetailsService;

    public JwtProvider(@Qualifier("customUserDetailsService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public String generateToken(String login, String role) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("ROLE", role);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + validDays))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        boolean rsl = true;
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (JwtException | IllegalStateException e) {
            rsl = false;
            e.printStackTrace();
        }
        return rsl;
    }

    public String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(authHeader);
        if (hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }
}
