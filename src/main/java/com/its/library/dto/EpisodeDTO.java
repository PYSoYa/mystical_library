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
    private String updateContents;
    private MultipartFile episodeImg;
    private String episodeImgName;
    private int payment;
    private int episodeHits;
    private int writerRole;
    private double star;
    private LocalDateTime createdTime;

    public EpisodeDTO(Long id, Long bookId, String episodeTitle, String episodeContents, String episodeImgName, int payment, int episodeHits, int writerRole, double star, LocalDateTime createdTime) {
        this.id = id;
        this.bookId = bookId;
        this.episodeTitle = episodeTitle;
        this.episodeContents = episodeContents;
        this.episodeImgName = episodeImgName;
        this.payment = payment;
        this.episodeHits = episodeHits;
        this.writerRole = writerRole;
        this.star = star;
        this.createdTime = createdTime;
    }

    public static EpisodeDTO findDTO(EpisodeEntity episodeEntity) {
        EpisodeDTO episodeDTO = new EpisodeDTO();
        episodeDTO.setId(episodeEntity.getId());
        episodeDTO.setBookId(episodeEntity.getBookEntity().getId());
        episodeDTO.setEpisodeTitle(episodeEntity.getEpisodeTitle());
        episodeDTO.setEpisodeContents(episodeEntity.getEpisodeContents());
        episodeDTO.setEpisodeImgName(episodeEntity.getEpisodeImgName());
        episodeDTO.setPayment(episodeEntity.getPayment());
        episodeDTO.setEpisodeHits(episodeEntity.getHits());
        episodeDTO.setWriterRole(episodeEntity.getWriterRole());
        episodeDTO.setCreatedTime(episodeEntity.getCreatedDateTime());
        return episodeDTO;
    }

    public static EpisodeDTO agreeList(EpisodeEntity episodeEntity) {
        EpisodeDTO episodeDTO = new EpisodeDTO();
        episodeDTO.setId(episodeEntity.getId());
        episodeDTO.setBookId(episodeEntity.getBookEntity().getId());
        episodeDTO.setEpisodeTitle(episodeEntity.getEpisodeTitle());
        episodeDTO.setEpisodeContents(episodeEntity.getEpisodeContents());
        episodeDTO.setEpisodeImgName(episodeEntity.getEpisodeImgName());
        episodeDTO.setPayment(episodeEntity.getPayment());
        episodeDTO.setEpisodeHits(episodeEntity.getHits());
        episodeDTO.setWriterRole(episodeEntity.getWriterRole());
        episodeDTO.setCreatedTime(episodeEntity.getCreatedDateTime());
        return episodeDTO;
    }
}
