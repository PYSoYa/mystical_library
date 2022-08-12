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


    @Transactional
    Page<EpisodeEntity> findByBookEntityOrderByIdAsc(Pageable pageable, BookEntity bookEntity);

    List<EpisodeEntity> findByBookEntity_Id(Long bookId);
    @Transactional
    @Query(value = "select * from episode where book_id = :bookId order by created_date_time asc", nativeQuery = true)
    List<EpisodeEntity> findByBookId(Long bookId);

    @Transactional
    @Modifying
    @Query(value = "update EpisodeEntity e set e.hits = e.hits + 1 where e.id = :id")
    void episodeHits(Long id);

    @Transactional
    @Modifying
    @Query(value = "select * from episode where book_id = :bookId order by episode_id desc", nativeQuery = true)
    List<EpisodeEntity> findByBookEntityId(@Param("bookId") Long bookId);


    @Transactional
    @Query(value = "select distinct(book_id) from episode order by episode_id asc", nativeQuery = true)
    List<Long> findByIdAll();
}
