package com.its.library.repository;

import com.its.library.entity.CommentEntity;
import com.its.library.entity.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByEpisodeEntity(EpisodeEntity episodeEntity);
}
