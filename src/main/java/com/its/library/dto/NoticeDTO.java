package com.its.library.dto;

import com.its.library.entity.BaseEntity;
import com.its.library.entity.NoticeEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long id;
    private Long wishId;
    private Long memberId;
    private Long episodeId;
    private String episodeTitle;
    private LocalDateTime createdTime;
    private  boolean read;


    public static NoticeDTO save(NoticeEntity noticeEntity1) {
        NoticeDTO noticeDTO =new NoticeDTO();
        noticeDTO.setId(noticeEntity1.getId());
        noticeDTO.setEpisodeId(noticeEntity1.getEpisodeEntity().getId());
        noticeDTO.setEpisodeTitle(noticeEntity1.getEpisodeTitle());
        noticeDTO.setWishId(noticeEntity1.getWishEntity().getId());
        noticeDTO.setMemberId(noticeEntity1.getMemberEntity().getId());
        noticeDTO.setRead(noticeEntity1.isNoticeRead());
        noticeDTO.setCreatedTime(noticeEntity1.getCreatedDateTime());
        return noticeDTO;
    }
}
