package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}
