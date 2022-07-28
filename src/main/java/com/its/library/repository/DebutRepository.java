package com.its.library.repository;

import com.its.library.entity.CategoryEntity;
import com.its.library.entity.DebutCategoryEntity;
import com.its.library.entity.DebutEpisodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DebutRepository extends JpaRepository<DebutEpisodeEntity,Long> {

    Page<DebutEpisodeEntity> findByDebutCategoryEntity(Pageable pageable, DebutCategoryEntity debutCategoryEntity);


    List<DebutEpisodeEntity> findByDebutCategoryEntity(DebutCategoryEntity debutCategoryEntity);
}

