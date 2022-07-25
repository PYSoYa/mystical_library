package com.its.library.dto;

import com.its.library.entity.WishEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishlistDTO {

    private Long id;
    private Long memberId;
    private Long bookId;
    private String memberName;

    public static WishlistDTO findBookDTO(WishEntity wishEntity) {
        WishlistDTO wishlistDTO = new WishlistDTO();
        wishlistDTO.setBookId(wishEntity.getBookEntity().getId());
        wishlistDTO.setMemberName(wishEntity.getMemberName());
        return wishlistDTO;
    }
}
