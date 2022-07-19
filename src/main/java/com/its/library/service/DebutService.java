package com.its.library.service;

import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.DebutCategoryEntity;
import com.its.library.entity.DebutEpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.DebutCategoryRepository;
import com.its.library.repository.DebutRepository;
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

    //데뷔글 저장 파일 처리
    public void save(DebutEpisodeDTO debutEpisodeDTO, Long id) throws IOException {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
            MemberEntity memberEntity= optionalMemberEntity.get();
            DebutCategoryEntity categoryEntity= debutCategoryRepository.findById(debutEpisodeDTO.getCategoryId()).get();
            MultipartFile debutImg = debutEpisodeDTO.getDebutImg();
            String debutImgName = debutImg.getOriginalFilename();
            debutImgName = System.currentTimeMillis() + "_" + debutImgName;
            String savePath = "C:\\springboot_img\\" + debutImgName;
            if(!debutImg.isEmpty()){
                debutImg.transferTo(new File(savePath));
            }
            debutEpisodeDTO.setDebutImgName(debutImgName);
            DebutEpisodeEntity debutEpisodeEntity = DebutEpisodeEntity.toSave(categoryEntity,debutEpisodeDTO,memberEntity);
            debutRepository.save(debutEpisodeEntity);
        }

    }

    public DebutEpisodeDTO detail(Long id) {
       Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity= debutRepository.findById(id);
       if (optionalDebutEpisodeEntity.isPresent()){
           DebutEpisodeEntity debutEpisodeEntity= optionalDebutEpisodeEntity.get();
          DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity);
           return debutEpisodeDTO;
       }
       return null;
    }

    public DebutEpisodeDTO updateForm(Long id) {
       Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(id);
       if (optionalDebutEpisodeEntity.isPresent()){
           DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
          DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity);
          return debutEpisodeDTO;
       }
       return null;
    }

    public void update(DebutEpisodeDTO debutEpisodeDTO) {
       Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(debutEpisodeDTO.getMemberId());
       Optional<DebutCategoryEntity> optionalDebutCategoryEntity =debutCategoryRepository.findById(debutEpisodeDTO.getCategoryId());
       if (optionalMemberEntity.isPresent()){
           MemberEntity memberEntity = optionalMemberEntity.get();
           if(optionalDebutCategoryEntity.isPresent()){
               DebutCategoryEntity debutCategoryEntity = optionalDebutCategoryEntity.get();
               DebutEpisodeEntity debutEpisodeEntity = DebutEpisodeEntity.toUpdate(debutCategoryEntity,debutEpisodeDTO,memberEntity);
               debutRepository.save(debutEpisodeEntity);
           }

       }

    }
}
