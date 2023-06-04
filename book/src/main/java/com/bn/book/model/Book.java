package com.bn.book.model;

import com.bn.clients.util.marker.Convertible;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "books")
public class Book implements Convertible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "author_id")
    private Integer authorId;

    @Column
    private String title;

    @Column(name = "photo_url")
    @ToString.Exclude
    private String photoUrl;

    @Column(name = "publisher_id")
    private Integer publisherId;

    @Column(name = "publication_year")
    private int publicationYear;

    @Column(name = "page_count")
    private int pageCount;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BookCopy> copies;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;
}
