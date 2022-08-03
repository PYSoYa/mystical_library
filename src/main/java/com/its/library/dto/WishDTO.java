package com.its.library.dto;

import com.its.library.entity.WishEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishDTO {

    private Long id;
    private Long memberId;
    private Long bookId;
    private String memberName;

    public static WishDTO findBookDTO(WishEntity wishEntity) {
        WishDTO wishDTO = new WishDTO();
        wishDTO.setBookId(wishEntity.getBookEntity().getId());
        wishDTO.setMemberName(wishEntity.getMemberName());
        return wishDTO;
    }
    public static WishDTO findMemberDTO(WishEntity wishEntity) {
        WishDTO wishDTO = new WishDTO();
        wishDTO.setMemberId(wishEntity.getMemberEntity().getId());
        wishDTO.setMemberName(wishEntity.getMemberName());
        return wishDTO;
    }
}
