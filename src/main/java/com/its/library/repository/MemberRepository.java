package com.its.library.repository;


import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
}
