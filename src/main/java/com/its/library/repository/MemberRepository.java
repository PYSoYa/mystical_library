package com.its.library.repository;


import com.its.library.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByLoginId(String loginId);

    Optional<MemberEntity> findByMemberName(String memberName);
}
