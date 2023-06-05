package com.bn.library.controller;

import com.bn.clients.constant.RoleData;
import com.bn.clients.user.dto.JwtParseResponseDto;
import com.bn.library.security.JwtService;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jwt")
public class TokenController {
    private final JwtService jwtService;

    public TokenController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/parse")
    public JwtParseResponseDto parse(@RequestBody String token) {
        return JwtParseResponseDto.builder()
                .username("testUsername")
                .roleData(List.of(RoleData.ADMIN))
                .build();
    }
}
