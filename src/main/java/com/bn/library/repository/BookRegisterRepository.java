package com.bn.library.repository;

import com.bn.library.model.BookRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRegisterRepository extends JpaRepository<BookRegister, Integer> {
}
