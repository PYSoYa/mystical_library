package com.its.library.repository;

import com.its.library.entity.HistoryEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {
    List<HistoryEntity> findByMemberEntity(MemberEntity memberEntity);
}
