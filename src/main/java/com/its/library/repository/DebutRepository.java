package com.its.library.repository;

import com.its.library.entity.CategoryEntity;
import com.its.library.entity.DebutCategoryEntity;
import com.its.library.entity.DebutEpisodeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DebutRepository extends JpaRepository<DebutEpisodeEntity,Long> {

//    Page<DebutEpisodeEntity> findByDebutCategoryEntity(Pageable pageable, DebutCategoryEntity debutCategoryEntity);


    List<DebutEpisodeEntity> findByDebutCategoryEntity(DebutCategoryEntity debutCategoryEntity);


    @Modifying
    @Query("update DebutEpisodeEntity d set d.debutHits=d.debutHits+1 where d.id = :id   ")
    void hitsAdd(Long id);




    @Modifying
    @Query(value = "select * from debut_episode d where category_id =:id order by id asc",nativeQuery = true)
    List<DebutEpisodeEntity> oderByNew(Long id);

    @Modifying
    @Query(value = "select * from debut_episode d  where category_id =:id order by debut_hits desc",nativeQuery = true)
    List<DebutEpisodeEntity> orderByHits(Long id);

    @Modifying
    @Query(value = "select * from debut_episode  where category_id =:id order by love desc",nativeQuery = true)
    List<DebutEpisodeEntity> orderByLove(Long id);

    List<DebutEpisodeEntity> findByMemberEntity_Id(Long id);
}

