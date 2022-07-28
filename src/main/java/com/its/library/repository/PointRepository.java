package com.its.library.repository;

import com.its.library.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointRepository extends JpaRepository<PointEntity,Long> {

    List<PointEntity> findByMemberEntity_Id(Long id);


    List<PointEntity> findByMemberEntity_IdOrderByIdDesc(Long memberId);
}
