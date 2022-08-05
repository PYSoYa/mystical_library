package com.its.library.repository;

import com.its.library.entity.BoxEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {
    List<BoxEntity> findByMemberEntity(MemberEntity memberEntity);

    List<BoxEntity> findByMemberEntityAndEpisodeId(MemberEntity memberEntity, Long episodeId);
}
