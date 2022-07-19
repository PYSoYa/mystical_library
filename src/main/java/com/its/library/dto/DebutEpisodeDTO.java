package com.its.library.dto;

import com.its.library.entity.BaseEntity;
import com.its.library.entity.DebutEpisodeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebutEpisodeDTO{

    private Long id;
    private Long categoryId;
    private Long memberId;
    private String memberName;
    private String feat;
    private String debutTitle;
    private String introduce;
    private String debutContents;
    private int debutHits;
    private MultipartFile debutImg;
    private String debutImgName;
    private LocalDateTime createdTime;

    public static DebutEpisodeDTO toDTO(DebutEpisodeEntity debutEpisodeEntity) {
        DebutEpisodeDTO debutEpisodeDTO = new DebutEpisodeDTO();
        debutEpisodeDTO.setId(debutEpisodeEntity.getId());
        debutEpisodeDTO.setDebutTitle(debutEpisodeEntity.getDebutTitle());
        debutEpisodeDTO.setCreatedTime(debutEpisodeEntity.getCreatedDateTime());
        debutEpisodeDTO.setDebutImgName(debutEpisodeEntity.getDebutImgName());
        debutEpisodeDTO.setDebutContents(debutEpisodeEntity.getDebutContents());
        return debutEpisodeDTO;
    }
}
