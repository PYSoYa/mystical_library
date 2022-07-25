package com.its.library.entity;

import com.its.library.dto.WishlistDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter@Getter
@Table(name = "wishlist")
public class WishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlistId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId")
    private BookEntity bookEntity;

    @Column(name = "memberName",length = 20)
    private String memberName;

    public static WishEntity saveWriterEntity(WishlistDTO wishlistDTO, MemberEntity memberEntity) {
        WishEntity wishEntity = new WishEntity();
        wishEntity.setMemberEntity(memberEntity);
        wishEntity.setMemberName(wishlistDTO.getMemberName());
        return wishEntity;
    }

    public static WishEntity saveBookEntity(WishlistDTO wishlistDTO, BookEntity bookEntity) {
        WishEntity wishEntity = new WishEntity();
        wishEntity.setBookEntity(bookEntity);
        wishEntity.setMemberName(wishlistDTO.getMemberName());
        return wishEntity;
    }


    //관심 - 맴버 manyToOne ㅇ
    //관심 - 책 manyToOne ㅇ

}
