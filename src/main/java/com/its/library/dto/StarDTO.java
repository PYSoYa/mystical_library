package com.its.library.dto;

import com.its.library.entity.StarEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarDTO {

    private Long id;
    private Long memberId;
    private Long episodeId;
    private double star;

    public static StarDTO findDTO(StarEntity starEntity) {
        StarDTO starDTO = new StarDTO();
        starDTO.setId(starEntity.getId());
        starDTO.setMemberId(starEntity.getMemberEntity().getId());
        starDTO.setEpisodeId(starEntity.getEpisodeEntity().getId());
        starDTO.setStar(starEntity.getStar());
        return starDTO;
    }
}
