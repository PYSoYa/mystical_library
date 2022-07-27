package com.its.library.dto;

import com.its.library.entity.BoxEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxDTO {

    private Long id;
    private Long memberId;
    private Long bookId;


    public static BoxDTO findDTO(BoxEntity boxEntity) {
        BoxDTO boxDTO = new BoxDTO();
        boxDTO.setMemberId(boxEntity.getMemberEntity().getId());
        boxDTO.setBookId(boxEntity.getBookId());
        return boxDTO;
    }
}
