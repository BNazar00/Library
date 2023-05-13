package com.bn.library.repository;

import com.bn.library.model.BookRegister;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRegisterRepository extends JpaRepository<BookRegister, Integer> {
    List<BookRegister> findAllByUserId(int userId);
}
