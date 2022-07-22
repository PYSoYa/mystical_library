package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CategoryEntity;
import com.its.library.entity.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByCategoryEntityAndGenreEntity(Pageable pageable, CategoryEntity categoryEntity, GenreEntity genreEntity);

    @Query(value = "select avg(s.star) from EpisodeEntity s where s.bookEntity.id = :bookId")
    double starAvg(@Param("bookId") Long bookId);


    @Query(value = "select b from BookEntity b where b.memberName like %:q%")
    List<BookEntity> findByMemberName(@Param("q") String q);

    @Query(value = "select b from BookEntity b where b.bookTitle like %:q%")
    List<BookEntity> findByBookTitle(@Param("q") String q);
}
