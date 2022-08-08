package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CommentEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


    List<CommentEntity> findByEpisodeEntity(EpisodeEntity episodeEntity);

    @Transactional
    @Query(value = "select * from comment where episode_id = :episodeId order by comment_id desc", nativeQuery = true)
    List<CommentEntity> findByEpisodeId(@Param("episodeId") Long episodeId);

    @Transactional
    @Query(value = "select * from comment where book_id = :bookId order by comment_id desc", nativeQuery = true)
    List<CommentEntity> findByBookId(@Param("bookId") Long bookId);
}
