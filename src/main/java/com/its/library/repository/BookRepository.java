package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
