package com.its.library.repository;

import com.its.library.entity.BookEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishRepository extends JpaRepository<WishEntity, Long> {
    Optional<WishEntity> findByMemberEntityAndMemberName(MemberEntity memberEntity, String memberName);

    Optional<WishEntity> findByBookEntityAndMemberName(BookEntity bookEntity, String memberName);

    List<WishEntity> findByMemberName(String memberName);

    List<WishEntity> findByMemberEntity(MemberEntity memberEntity);


    Optional<WishEntity> findByMemberNameAndBookEntity_Id(String memberName, Long id1);
    List<WishEntity> findByBookEntity_Id(Long id);

    int countByMemberEntity_Id(Long memberId);
}
