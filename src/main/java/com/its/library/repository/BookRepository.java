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

    List<BookEntity> findByGenreEntity(GenreEntity genreEntity);

    List<BookEntity> findByGenreEntityOrderByStarDesc(GenreEntity genreEntity);

    List<BookEntity> findByEpisodeEntityList(EpisodeEntity episodeEntity);
    List<BookEntity> findByCategoryEntity(CategoryEntity categoryEntity);

//    @Transactional
//    @Modifying
//    @Query(value = "update BookEntity b set b.hits = b.hits + 1 where ")
//    void bookHits(EpisodeEntity episodeEntity);
}
