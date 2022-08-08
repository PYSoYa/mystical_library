package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.HistoryEntity;
import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long> {

//    List<HistoryEntity> findByEpisodeEntity(EpisodeEntity episodeEntity);

//    Optional<HistoryEntity> findByBookId(Long bookId);
    Optional<HistoryEntity> findByBooKIdAndMemberEntity(Long bookId, MemberEntity memberEntity);

    List<HistoryEntity> findByBooKId(Long bookId);

    @Query(value = "select distinct(h.booKId) from HistoryEntity h where h.memberEntity.id = :memberId")
    List<Long> findByMemberId(@Param("memberId") Long memberId);

    List<HistoryEntity> findByMemberEntityIsNull();

    @Query(value = "select h.episodeEntity.id from HistoryEntity h where h.booKId = :bookId")
    List<Long> findByEpisodeId(@Param("bookId") Long bookId);

    Optional<HistoryEntity> findByMemberEntityAndEpisodeEntity(MemberEntity memberEntity, EpisodeEntity episodeEntity);

    @Transactional
    @Query(value = "select * from history where member_id = :memberId and booKId = :bookId order by created_date_time asc", nativeQuery = true)
    List<HistoryEntity> findByMemberEntityAndBooKId(Long memberId, Long bookId);

    @Transactional
    @Query(value = "select * from history where member_id = :memberId and booKId = :bookId order by created_date_time desc", nativeQuery = true)
    List<HistoryEntity> findByAgain(Long memberId, Long bookId);
}
