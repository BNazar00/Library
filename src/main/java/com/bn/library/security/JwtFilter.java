package com.bn.library.security;

import com.bn.library.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private static final String ABSENT_TOKEN = "Token is empty [%s]";
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final TokenRepository tokenRepository;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService, TokenRepository tokenRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            boolean isTokenValid = tokenRepository.findByValue(jwt).isPresent();
            if (isTokenValid && jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);

        //try {
        //    String accessToken = authHeader.substring(7);
        //    String userEmail = jwtService.extractUsername(jwt);
        //        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        //
        //    if (StringUtils.hasText(accessToken) && jwtService.isTokenValid(accessToken, userDetails)) {
        //        UsernamePasswordAuthenticationToken authentication =
        //                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        //        SecurityContextHolder.getContext().setAuthentication(authentication);
        //        log.debug("User {} successfully authenticate with token {}", userDetails.getUsername(), accessToken);
        //    } else {
        //        log.debug(String.format(ABSENT_TOKEN, request.getRequestURI()));
        //    }
        //} catch (Exception ex) {
        //    log.error("Could not authenticate user", ex);
        //}
        //filterChain.doFilter(request, response);
    }
}
