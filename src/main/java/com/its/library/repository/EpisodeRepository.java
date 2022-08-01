package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {

    Page<EpisodeEntity> findByBookEntity(Pageable pageable, BookEntity bookEntity);

    List<EpisodeEntity> findByBookEntity(BookEntity bookEntity);

    @Transactional
    @Modifying
    @Query(value = "update EpisodeEntity e set e.hits = e.hits + 1 where e.id = :id")
    void episodeHits(Long id);

    @Transactional
    @Modifying
    @Query(value = "select e from EpisodeEntity e where e.bookEntity.id = :bookId order by e.createdDateTime desc")
    List<EpisodeEntity> findByBookEntityId(@Param("bookId") Long bookId);

    List<EpisodeEntity> findByBookEntityOrderByCreatedDateTimeDesc(BookEntity bookEntity);
}
