package com.its.library.dto;

import com.its.library.entity.LoveEntity;
import com.its.library.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoveDTO {

    private Long id;
    private Long memberId;
    private Long debutId;
    private int love;

    public static LoveDTO toSave(LoveEntity loveEntity, MemberEntity memberEntity) {
        LoveDTO loveDTO = new LoveDTO();
        loveDTO.setId(loveEntity.getId());
        loveDTO.setMemberId(memberEntity.getId());
        loveDTO.setDebutId(loveEntity.getDebutId());
        loveDTO.setLove(loveEntity.getLove());
        return loveDTO;
    }
}
