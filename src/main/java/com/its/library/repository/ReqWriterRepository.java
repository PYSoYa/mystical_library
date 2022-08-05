package com.its.library.repository;

import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqWriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReqWriterRepository extends JpaRepository<ReqWriterEntity,Long> {
    void deleteByMemberEntity_Id(Long memberId);
}
