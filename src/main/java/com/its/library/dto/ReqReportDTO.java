package com.its.library.dto;

import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqReportEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqReportDTO {

    private Long id;
    private Long memberId;
    private Long commentId;
    private Long debutCommentId;


    public static ReqReportDTO toDebutReportDTO(ReqReportEntity reqReportEntity1) {
        ReqReportDTO reqReportDTO = new ReqReportDTO();
        reqReportDTO.setId(reqReportEntity1.getId());
        reqReportDTO.setDebutCommentId(reqReportEntity1.getDebutCommentEntity().getId());
        reqReportDTO.setMemberId(reqReportEntity1.getMemberEntity().getId());
        return reqReportDTO;
    }

    public static  ReqReportDTO toReportDTO(ReqReportEntity reqReportEntity1) {
        ReqReportDTO reqReportDTO = new ReqReportDTO();
        reqReportDTO.setId(reqReportEntity1.getId());
        reqReportDTO.setDebutCommentId(reqReportEntity1.getCommentEntity().getId());
        reqReportDTO.setMemberId(reqReportEntity1.getMemberEntity().getId());
        return reqReportDTO;
    }
}
