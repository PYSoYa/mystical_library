package com.its.library.dto;

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


}
