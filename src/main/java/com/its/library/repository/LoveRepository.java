package com.its.library.repository;

import com.its.library.entity.LoveEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoveRepository extends JpaRepository<LoveEntity,Long> {
    Optional<LoveEntity> findByDebutIdAndMemberEntity(Long debutId, MemberEntity memberEntity);
    int countByDebutId(Long debutId);
    void deleteByDebutIdAndMemberEntity(Long debutId, MemberEntity memberEntity);

}
