package com.its.library.entity;

import com.its.library.dto.ReqReportDTO;
import com.its.library.repository.DebutCommentRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.ReqReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReqReportService {
    private final ReqReportRepository reqReportRepository;
    private final MemberRepository memberRepository;
    private final DebutCommentRepository debutCommentRepository;
    //데뷔글 신고 내역
    public List<ReqReportDTO> debutReportList() {
        List<ReqReportEntity> reqReportEntityList = reqReportRepository.findAll();
        List<ReqReportDTO> debutReport = new ArrayList<>();
        for (ReqReportEntity reqReportEntity : reqReportEntityList) {
            ReqReportEntity reqReportEntity1 = reqReportEntity;
            ReqReportDTO debutReportResult = ReqReportDTO.toDebutReportDTO(reqReportEntity1);
            if(debutReportResult.getDebutCommentId()!=null){
                debutReport.add(debutReportResult);
            }else {
                return null;
            }

            }
                return debutReport;
}
    //작가글 신고내역
    public List<ReqReportDTO> reportList() {
        List<ReqReportEntity> reqReportEntityList = reqReportRepository.findAll();;
        List<ReqReportDTO> report = new ArrayList<>();

        for (ReqReportEntity reqReportEntity : reqReportEntityList) {
            ReqReportEntity reqReportEntity1 = reqReportEntity;
            ReqReportDTO debutReportResult = ReqReportDTO.toDebutReportDTO(reqReportEntity1);

            if(debutReportResult.getCommentId()!=null){
                ReqReportDTO reportResult = ReqReportDTO.toReportDTO(reqReportEntity1);
                report.add(reportResult);
                return report;

            }

        }
        return null;
    }
}
