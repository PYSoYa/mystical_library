package com.its.library.dto;

import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqWriterEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqWriterDTO {

    private Long id;
    private Long memberId;

    public static ReqWriterDTO findDTO(ReqWriterEntity reqWriterEntity1, MemberEntity memberEntity) {
        ReqWriterDTO reqWriterDTO = new ReqWriterDTO();
        reqWriterDTO.setId(reqWriterEntity1.getId());
        reqWriterDTO.setMemberId(reqWriterEntity1.getMemberEntity().getId());
        return reqWriterDTO;
    }
}
