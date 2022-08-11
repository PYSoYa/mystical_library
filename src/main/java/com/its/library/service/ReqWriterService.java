package com.its.library.service;

import com.its.library.dto.MemberDTO;
import com.its.library.dto.ReqReportDTO;
import com.its.library.dto.ReqWriterDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.ReqWriterEntity;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.ReqReportRepository;
import com.its.library.repository.ReqWriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReqWriterService {
    private final ReqWriterRepository reqWriterRepository;
    private final MemberRepository memberRepository;

    public List<MemberDTO> findAll() {
        List<ReqWriterEntity> reqWriterEntityList = reqWriterRepository.findAll();
        List<MemberDTO> reqWriterDTOList = new ArrayList<>();
        for (ReqWriterEntity reqWriterEntity : reqWriterEntityList) {
            Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(reqWriterEntity.getMemberEntity().getId());
            if (optionalMemberEntity.isPresent()) {
                MemberEntity memberEntity = optionalMemberEntity.get();
                reqWriterDTOList.add(MemberDTO.toDTO(memberEntity));
            }

        }
        return reqWriterDTOList;
    }

    public String save(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            ReqWriterEntity reqWriterEntity = ReqWriterEntity.save(memberEntity);
            ReqWriterEntity result = reqWriterRepository.save(reqWriterEntity);
            if (result != null) {
                return "ok";
            } else {
                return "no";
            }
        }
        return null;
    }

    // 작가로 권한 변경
    @Transactional
    public void roleChange(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            memberEntity.setRole("ROLE_WRITER");
            memberEntity.setRoleChangeTime(LocalDateTime.now());
            memberRepository.save(memberEntity);
            reqWriterListDelete(id);
        }
    }

    // 작가 승인 처리 후 테이블에서 삭제
    @Transactional
    public void reqWriterListDelete(Long memberId) {
        reqWriterRepository.deleteByMemberEntity_Id(memberId);
    }
    
    // 작가 거절 처리 후 테이블에서 삭제
    @Transactional
    public void reqWriterDelete(Long memberId) {
        reqWriterListDelete(memberId);
    }
}
