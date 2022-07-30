package com.its.library.dto;

import com.its.library.entity.BookEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDTO {

    private Long id;
    private Long memberId;
    private Long episodeId;
    private int plusPoint;
    private int minusPoint;
    private int totalPoint;
    private LocalDateTime pointTime;
    private String bookTitle;
    private String episodeTitle;


    public static PointDTO findDTO(PointEntity pointEntity1, EpisodeEntity episodeEntity, BookEntity bookEntity) {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setId(pointEntity1.getId());
        pointDTO.setMemberId(pointDTO.getMemberId());
        pointDTO.setEpisodeId(episodeEntity.getId());
        pointDTO.setPlusPoint(pointEntity1.getPlusPoint());
        pointDTO.setMinusPoint(pointEntity1.getMinusPoint());
        pointDTO.setTotalPoint(pointEntity1.getTotalPoint());
        pointDTO.setPointTime(pointEntity1.getCreatedDateTime());
        pointDTO.setEpisodeTitle(episodeEntity.getEpisodeTitle());
        pointDTO.setBookTitle(bookEntity.getBookTitle());

        return pointDTO;

    }

    public static PointDTO memberDTO(MemberEntity memberEntity) {
        PointDTO pointDTO = new PointDTO();
        pointDTO.setMemberId(memberEntity.getId());
        pointDTO.setPlusPoint(memberEntity.getMemberPoint());
        pointDTO.setTotalPoint(memberEntity.getMemberPoint());
        return pointDTO;
    }


}
