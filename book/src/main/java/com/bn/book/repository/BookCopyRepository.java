package com.bn.book.repository;

import com.bn.book.model.BookCopy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
    Optional<BookCopy> findFirstByInStorageOrderById(boolean isInStorage);
}
