package com.its.library.dto;

import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.HistoryEntity;
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
    private Long bookId;
    private LocalDateTime createdTime;
    private int hidden;

    public static HistoryDTO findDTO(HistoryEntity historyEntity) {
        HistoryDTO historyDTO = new HistoryDTO();
        historyDTO.setMemberId(historyEntity.getMemberEntity().getId());
        historyDTO.setEpisodeId(historyEntity.getEpisodeEntity().getId());
        historyDTO.setBookId(historyEntity.getBooKId());
        historyDTO.setCreatedTime(historyEntity.getCreatedDateTime());
        historyDTO.setHidden(historyEntity.getHidden());
        return historyDTO;
    }


}
