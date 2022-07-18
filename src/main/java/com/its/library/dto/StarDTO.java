package com.its.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StarDTO {

    private Long id;
    private Long memberId;
    private Long episodeId;
    private double star;

}
