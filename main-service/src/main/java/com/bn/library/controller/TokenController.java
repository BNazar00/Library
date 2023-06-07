package com.bn.library.controller;

import com.bn.clients.constant.RoleData;
import com.bn.clients.user.dto.JwtParseResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jwt")
@Slf4j
public class TokenController {
    private final ObjectMapper objectMapper;

    public TokenController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostMapping("/parse")
    public JwtParseResponseDto parseJwtForApiGateway(@RequestBody String token) {
        return JwtParseResponseDto.builder()
                .username("testUsername")
                .roleData(List.of(RoleData.ADMIN))
                .build();
    }

    @GetMapping("/parse")
    public ResponseEntity<Void> parseJwtForNginx(@RequestHeader("Authorization") String token) {
        log.info("Parsing token request");
        System.out.println(token);
        HttpHeaders responseHeaders = new HttpHeaders();
        try {
            responseHeaders.set("username", "testUsername");
            responseHeaders.set("roles", objectMapper.writeValueAsString(List.of(RoleData.ADMIN)));
        } catch (JsonProcessingException e) {
            log.error("Error parsing user roles");
            return ResponseEntity.status(FORBIDDEN).build();
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .build();
    }
}
