package com.its.library.repository;

import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.HistoryEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

//    List<HistoryEntity> findByEpisodeEntity(EpisodeEntity episodeEntity);

//    Optional<HistoryEntity> findByBookId(Long bookId);

    List<HistoryEntity> findByBooKId(Long bookId);

    @Query(value = "select distinct(h.booKId) from HistoryEntity h where h.memberEntity.id = :memberId")
    List<Long> findByMemberId(@Param("memberId") Long memberId);

    @Query(value = "select h.episodeEntity.id from HistoryEntity h where h.booKId = :bookId")
    List<Long> findByEpisodeId(@Param("bookId") Long bookId);
}
