package com.its.library.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter@Setter
@Table(name = "req_writer")
public class ReqWriterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reqWriterId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    public static ReqWriterEntity save(MemberEntity memberEntity) {
        ReqWriterEntity reqWriterEntity = new ReqWriterEntity();
        reqWriterEntity.setMemberEntity(memberEntity);
        return reqWriterEntity;
    }
    //작가승인 - 멤버 manyToOne ㅇ

}
