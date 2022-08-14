package com.its.library.repository;

import com.its.library.entity.CommentEntity;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReqReportRepository extends JpaRepository<ReqReportEntity,Long> {


    List<ReqReportEntity> findAllByDebutCommentEntityIsNotNull();

    List<ReqReportEntity> findAllByCommentEntityIsNotNull();

    @Transactional
    void deleteByCommentEntity(CommentEntity commentEntity);

    @Transactional
    void deleteByDebutCommentEntity(DebutCommentEntity debutCommentEntity);

    @Transactional
    Optional<ReqReportEntity> findByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);

    @Transactional
    Optional<ReqReportEntity> findByMemberEntityAndDebutCommentEntity(MemberEntity memberEntity, DebutCommentEntity debutCommentEntity);
}
