package com.its.library.repository;

import com.its.library.dto.DebutCommentDTO;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.DebutEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DebutCommentRepository extends JpaRepository<DebutCommentEntity,Long> {
    List<DebutCommentEntity> findByDebutEpisodeEntity(DebutEpisodeEntity debutEpisodeEntity);

    @Modifying
    @Query(value = "update debut_comment  set contents =: contents  where id=:id ",nativeQuery = true)
    DebutCommentEntity update(Long id, String contents);
}
