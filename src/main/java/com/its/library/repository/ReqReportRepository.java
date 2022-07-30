package com.its.library.repository;

import com.its.library.entity.ReqReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReqReportRepository extends JpaRepository<ReqReportEntity,Long> {


    List<ReqReportEntity> findAllByDebutCommentEntityIsNotNull();

    List<ReqReportEntity> findAllByCommentEntityIsNotNull();
}
