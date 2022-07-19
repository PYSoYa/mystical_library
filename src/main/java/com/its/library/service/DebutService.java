package com.its.library.service;

import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.dto.LoveDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.DebutCategoryEntity;
import com.its.library.entity.DebutEpisodeEntity;
import com.its.library.entity.LoveEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.DebutCategoryRepository;
import com.its.library.repository.DebutRepository;
import com.its.library.repository.LoveRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DebutService {
    private final DebutRepository debutRepository;
    private final MemberRepository memberRepository;
    private final DebutCategoryRepository debutCategoryRepository;
    private final LoveRepository loveRepository;

    //데뷔글 저장 파일 처리
    public void save(DebutEpisodeDTO debutEpisodeDTO, Long id) throws IOException {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            DebutCategoryEntity categoryEntity = debutCategoryRepository.findById(debutEpisodeDTO.getCategoryId()).get();
            MultipartFile debutImg = debutEpisodeDTO.getDebutImg();
            String debutImgName = debutImg.getOriginalFilename();
            debutImgName = System.currentTimeMillis() + "_" + debutImgName;
            String savePath = "C:\\springboot_img\\" + debutImgName;
            if (!debutImg.isEmpty()) {
                debutImg.transferTo(new File(savePath));
            }
            debutEpisodeDTO.setDebutImgName(debutImgName);
            DebutEpisodeEntity debutEpisodeEntity = DebutEpisodeEntity.toSave(categoryEntity, debutEpisodeDTO, memberEntity);
            debutRepository.save(debutEpisodeEntity);
        }

    }

    public DebutEpisodeDTO detail(Long id) {
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(id);
        if (optionalDebutEpisodeEntity.isPresent()) {
            DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
            DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity);
            return debutEpisodeDTO;
        } else {
            return null;
        }


    }

    public DebutEpisodeDTO updateForm(Long id) {
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(id);
        if (optionalDebutEpisodeEntity.isPresent()) {
            DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
            DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity);
            return debutEpisodeDTO;
        } else {
            return null;
        }

    }

    public void update(DebutEpisodeDTO debutEpisodeDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(debutEpisodeDTO.getMemberId());
        Optional<DebutCategoryEntity> optionalDebutCategoryEntity = debutCategoryRepository.findById(debutEpisodeDTO.getCategoryId());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (optionalDebutCategoryEntity.isPresent()) {
                DebutCategoryEntity debutCategoryEntity = optionalDebutCategoryEntity.get();
                DebutEpisodeEntity debutEpisodeEntity = DebutEpisodeEntity.toUpdate(debutCategoryEntity, debutEpisodeDTO, memberEntity);
                debutRepository.save(debutEpisodeEntity);
            }

        }

    }

    public void delete(Long id) {
        debutRepository.deleteById(id);
    }

    public LoveDTO love(Long debutId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            Optional<LoveEntity> optionalLoveEntity = loveRepository.findByDebutIdAndMemberEntity(debutId, memberEntity);
            if (optionalLoveEntity.isPresent()) {
                LoveEntity loveEntity = optionalLoveEntity.get();
                LoveDTO loveDTO = LoveDTO.toSave(loveEntity, memberEntity);
                return loveDTO;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int loveSave(Long debutId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            LoveDTO loveDTO = new LoveDTO();
            loveDTO.setDebutId(debutId);
            loveDTO.setMemberId(memberId);
            LoveEntity loveEntity = LoveEntity.toSave(loveDTO, memberEntity);
            loveRepository.save(loveEntity);
            int loveNum = loveRepository.countByDebutId(debutId);
            return loveNum;
        } else {
            return 0;
        }
    }

    public int loveDelete(Long debutId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            LoveDTO loveDTO = new LoveDTO();
            loveDTO.setDebutId(debutId);
            loveDTO.setMemberId(memberId);
            loveRepository.deleteByDebutIdAndMemberEntity(debutId, memberEntity);
            int loveNum = loveRepository.countByDebutId(debutId);
            return loveNum;
        } else {return 0;
        }
    }
}
