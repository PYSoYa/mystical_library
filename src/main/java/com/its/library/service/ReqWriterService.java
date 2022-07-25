package com.its.library.service;

import com.its.library.dto.ReqReportDTO;
import com.its.library.dto.ReqWriterDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqWriterEntity;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.ReqReportRepository;
import com.its.library.repository.ReqWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReqWriterService {
    private final ReqWriterRepository reqWriterRepository;
    private final MemberRepository memberRepository;

    public List<ReqWriterDTO> findAll() {
       List<ReqWriterEntity> reqWriterEntityList = reqWriterRepository.findAll();
       List<ReqWriterDTO> reqWriterDTOList = new ArrayList<>();
        for (ReqWriterEntity reqWriterEntity:reqWriterEntityList) {
            ReqWriterEntity reqWriterEntity1 = reqWriterEntity;
           Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(reqWriterEntity1.getMemberEntity().getId());
           if(optionalMemberEntity.isPresent()){
               MemberEntity memberEntity= optionalMemberEntity.get();
              ReqWriterDTO reqWriterDTO= ReqWriterDTO.findDTO(reqWriterEntity1,memberEntity);
               reqWriterDTOList.add(reqWriterDTO);
               return reqWriterDTOList;
           }else {
               return null;
           }

        }
        return null;
    }
}
