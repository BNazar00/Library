package com.bn.library.repository;

import com.bn.library.model.BookCopy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Integer> {
    Optional<BookCopy> findFirstByInStorageOrderById(boolean isInStorage);
}
