package com.its.library.dto;

import com.its.library.entity.EpisodeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDTO {

    private Long id;
    private Long bookId;
    private String episodeTitle;
    private String episodeContents;
    private MultipartFile episodeImg;
    private String episodeImgName;
    private int payment;
    private int episodeHits;
    private int hidden;
    private LocalDateTime createdTime;


    public static EpisodeDTO findDTO(EpisodeEntity episodeEntity) {
        EpisodeDTO episodeDTO = new EpisodeDTO();
        episodeDTO.setBookId(episodeEntity.getBookEntity().getId());
        episodeDTO.setEpisodeTitle(episodeEntity.getEpisodeTitle());
        episodeDTO.setEpisodeContents(episodeEntity.getEpisodeContents());
        episodeDTO.setEpisodeImgName(episodeEntity.getEpisodeImgName());
        episodeDTO.setPayment(episodeEntity.getPayment());
        episodeDTO.setEpisodeHits(episodeEntity.getEpisodeHits());
        episodeDTO.setHidden(episodeEntity.getHidden());
        return episodeDTO;
    }
}
