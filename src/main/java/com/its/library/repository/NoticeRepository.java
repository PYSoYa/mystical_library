package com.its.library.repository;

import com.its.library.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeEntity,Long> {
    List<NoticeEntity> findAllByMemberEntity_IdAndNoticeReadIsFalse(Long memberId);


    Optional<NoticeEntity> findByIdAndNoticeReadIsFalse(Long memberId);
}
