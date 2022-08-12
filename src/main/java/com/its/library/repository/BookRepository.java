package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CategoryEntity;
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
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findByCategoryEntityAndGenreEntity(Pageable pageable, CategoryEntity categoryEntity, GenreEntity genreEntity);

    @Query(value = "select avg(s.star) from EpisodeEntity s where s.bookEntity.id = :bookId")
    double starAvg(@Param("bookId") Long bookId);

    @Query(value = "select sum(h.hits) from EpisodeEntity h where h.bookEntity.id = :bookId")
    int hitsSum(@Param("bookId") Long bookId);

    @Query(value = "select b from BookEntity b where b.memberName like %:q%")
    List<BookEntity> findByMemberNameContaining(@Param("q") String q);

    @Query(value = "select b from BookEntity b where b.bookTitle like %:q%")
    List<BookEntity> findByBookTitleContaining(@Param("q") String q);

    @Transactional
    @Query(value = "select * from book where genre_id = :genreId order by episode_update_time desc", nativeQuery = true)
    List<BookEntity> findByGenreEntity(Long genreId);

    List<BookEntity> findByGenreEntityOrderByStarDesc(GenreEntity genreEntity);

    List<BookEntity> findByGenreEntityOrderByHitsDesc(GenreEntity genreEntity);

    List<BookEntity> findByWriterRole(int num);

    @Transactional
    @Query(value = "select * from book where writer_role = 1 order by hits desc", nativeQuery = true)
    List<BookEntity> findAllHits();

    @Transactional
    @Query(value = "select * from book where status = '완결' order by episode_update_time desc", nativeQuery = true)
    List<BookEntity> findAllNew();

    List<BookEntity> findAllByMemberEntity_IdAndStatus(Long memberId, String status);

    List<BookEntity> findAllByMemberEntity_IdAndWriterRole(Long memberId, int number);
}
