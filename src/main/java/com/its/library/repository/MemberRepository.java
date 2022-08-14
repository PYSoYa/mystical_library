package com.its.library.repository;


import com.its.library.entity.BookEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByLoginId(String loginId);

    Optional<MemberEntity> findByMemberName(String memberName);

    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    @Override
    @Query (value = "select * from member where login_id not like 'admin'", nativeQuery = true)
    List<MemberEntity> findAll();

    Optional<MemberEntity> findByLoginIdAndMemberEmail(String loginId, String memberEmail);

    @Transactional
    @Query (value = "select * from member where role='ROLE_WRITER' order by role_change_time desc limit 0, 5", nativeQuery = true)
    List<MemberEntity> findWriter1();

    @Transactional
    @Query (value = "select * from member where role='ROLE_WRITER' order by role_change_time desc limit 5, 10", nativeQuery = true)
    List<MemberEntity> findWriter2();

    @Query(value = "select m from MemberEntity m where m.role = 'ROLE_WRITER' and m.memberName like %:q%")
    List<MemberEntity> findByMemberNameContaining(@Param("q") String q);
}
