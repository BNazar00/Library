package com.bn.library.service.impl;

import com.bn.library.model.Token;
import com.bn.library.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
public class LogoutServiceImpl implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Autowired
    public LogoutServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7);
        Token storedToken = tokenRepository.findByValue(jwt)
                .orElse(null);
        if (storedToken != null) {
            tokenRepository.delete(storedToken);
            SecurityContextHolder.clearContext();
        }
    }
}
