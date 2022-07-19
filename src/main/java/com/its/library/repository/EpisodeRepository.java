package com.its.library.repository;

import com.its.library.entity.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<EpisodeEntity, Long> {

}
