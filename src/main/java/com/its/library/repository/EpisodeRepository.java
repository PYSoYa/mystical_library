package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {

    Page<EpisodeEntity> findByBookEntity(Pageable pageable, BookEntity bookEntity);

    List<EpisodeEntity> findByBookEntity(BookEntity bookEntity);
}
