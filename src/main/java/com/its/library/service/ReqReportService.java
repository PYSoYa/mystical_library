package com.its.library.service;

import com.its.library.dto.ReqReportDTO;
import com.its.library.entity.DebutCommentEntity;
import com.its.library.entity.ReqReportEntity;
import com.its.library.repository.DebutCommentRepository;
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

    public void debutCommentDelete(Long id) {
       Optional<ReqReportEntity> reqReportEntity = reqReportRepository.findById(id);
        if(reqReportEntity.isPresent()){
            ReqReportEntity reqReportEntity1 =reqReportEntity.get();
           Optional<DebutCommentEntity> optionalDebutCommentEntity = debutCommentRepository.findById(reqReportEntity1.getDebutCommentEntity().getId());
           if(optionalDebutCommentEntity.isPresent()){
               DebutCommentEntity debutComment =optionalDebutCommentEntity.get();
              DebutCommentEntity debutCommentEntity = DebutCommentEntity.toUpdate(debutComment);
              DebutCommentEntity debutComment1 = debutCommentRepository.save(debutCommentEntity);
              reqReportRepository.deleteById(id);

           }
        }

    }
}
