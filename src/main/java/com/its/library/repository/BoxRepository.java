package com.its.library.repository;

import com.its.library.entity.BoxEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BoxRepository extends JpaRepository<BoxEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "select * from box where member_id = :memberId group by book_id ", nativeQuery = true)
    List<BoxEntity> findByMemberEntity_Id(@Param("memberId") Long memberId);

    List<BoxEntity> findByMemberEntityAndEpisodeId(MemberEntity memberEntity, Long episodeId);
}
