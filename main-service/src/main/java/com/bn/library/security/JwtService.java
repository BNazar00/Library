package com.bn.library.security;

import com.bn.clients.constant.RoleData;
import com.bn.library.exception.JsonWriteException;
import com.bn.library.model.Role;
import com.bn.library.model.User;
import com.bn.library.repository.TokenRepository;
import com.bn.library.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtService {
    private final String accessTokenSecret;
    private final Long accessTokenExpirationTime;
    private final UserService userService;
    private final TokenRepository tokenRepository;
    private final ObjectMapper objectMapper;

    public JwtService(@Value("${application.security.jwt.access-token-secret}") String accessTokenSecret,
                      @Value("${application.security.jwt.access-token-expiration-time-in-minutes}")
                      Integer accessTokenExpirationTimeInMinutes, UserService userService,
                      TokenRepository tokenRepository, ObjectMapper objectMapper) {
        this.accessTokenSecret = accessTokenSecret;
        this.accessTokenExpirationTime = accessTokenExpirationTimeInMinutes * 60 * 1000L;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.objectMapper = objectMapper;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        UserPrincipal userPrincipal = new UserPrincipal(
                user.getEmail(),
                user.getRoles()
                        .stream().map(role -> RoleData.valueOf(role.getName())).collect(Collectors.toSet()));
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        try {
            return generateToken(
                    Map.of("roles", objectMapper.writeValueAsString(roleNames)),
                    userPrincipal);
        } catch (JsonProcessingException e) {
            log.error("Error converting user roles to json {}", e.getMessage());
            throw new JsonWriteException(e.getMessage());
        }
    }

    public String generateToken(Map<String, String> extraClaims, UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        if (extractExpiration(token).before(new Date())) {
            tokenRepository.deleteByValue(token);
            return true;
        } else {
            return false;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
