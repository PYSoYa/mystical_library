package com.its.library.repository;

import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.StarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StarRepository extends JpaRepository<StarEntity, Long> {

    @Query(value = "select avg(s.star) from StarEntity s where s.episodeEntity.id = :episodeId")
    double starAvg(@Param("episodeId") Long episodeId);

}
