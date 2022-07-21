package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CategoryEntity;
import com.its.library.entity.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByCategoryEntityAndGenreEntity(Pageable pageable, CategoryEntity categoryEntity, GenreEntity genreEntity);

    Page<BookEntity> findByGenreEntity(PageRequest id, GenreEntity genreEntity);
}
