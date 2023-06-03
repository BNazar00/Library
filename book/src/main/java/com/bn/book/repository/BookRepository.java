package com.bn.book.repository;

import com.bn.book.model.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query(value = """
            SELECT b.*, COUNT(c.book_copy_id) AS total_borrowed
            FROM books b
            LEFT JOIN book_copies bc ON b.id = bc.book_id
            LEFT JOIN checkouts c ON bc.id = c.book_copy_id
            GROUP BY b.id
            ORDER BY total_borrowed DESC
            """, nativeQuery = true)
    List<Book> findAllBookPreviewsOrderByCheckoutCount();

    @Query(value = """
            SELECT b.*, COUNT(c.book_copy_id) AS total_borrowed
            FROM books b
            JOIN book_copies bc ON b.id = bc.book_id
            JOIN checkouts c ON bc.id = c.book_copy_id
            GROUP BY b.id
            ORDER BY total_borrowed DESC
            LIMIT 10
            """, nativeQuery = true)
    List<Book> findTop10Bestsellers();

    List<Book> findTop10ByOrderByIdDesc();
}
