package com.bn.library.repository;

import com.bn.library.model.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByValue(String token);

    boolean deleteByValue(String value);
}
