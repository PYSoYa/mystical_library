package com.its.library.repository;

import com.its.library.entity.DebutEpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebutRepository extends JpaRepository<DebutEpisodeEntity,Long> {

}

