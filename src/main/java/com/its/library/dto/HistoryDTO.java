package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {

    private Long id;
    private Long memberId;
    private Long episodeId;
    private LocalDateTime lastTime;
    private int hidden;

}
