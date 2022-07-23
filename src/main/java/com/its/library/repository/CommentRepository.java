package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.CommentEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByEpisodeEntity(EpisodeEntity episodeEntity);

    List<CommentEntity> findByBookId(Long id);
}
