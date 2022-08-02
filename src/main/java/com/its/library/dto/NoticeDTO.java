package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long id;
    private Long loveId;
    private Long memberId;
    private Long episodeId;
    private String episodeTitle;
    private LocalDateTime createdTime;
    private  boolean read;


}
