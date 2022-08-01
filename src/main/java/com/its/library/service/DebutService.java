package com.its.library.service;

import com.its.library.common.PagingConst;
import com.its.library.dto.DebutEpisodeDTO;
import com.its.library.dto.LoveDTO;
import com.its.library.dto.MemberDTO;
import com.its.library.entity.*;
import com.its.library.repository.DebutCategoryRepository;
import com.its.library.repository.DebutRepository;
import com.its.library.repository.LoveRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    //데뷔글 상세조회
    @Transactional
    public DebutEpisodeDTO detail(Long id) {
        Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(id);
        if (optionalDebutEpisodeEntity.isPresent()) {
            DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
            debutRepository.hitsAdd(debutEpisodeEntity.getId());
            DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity);

            return debutEpisodeDTO;
        } else {
            return null;
        }


    }

    //데뷔글 업데이트 화면 처리
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

    // 데뷔글 업데이트 처리
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

    //데뷔글 삭제 처리
    public void delete(Long id) {
        debutRepository.deleteById(id);
    }


    //데뷔글 좋아요 등록(좋아요 중복체크 좋아요 종합)
    public int loveSave(Long debutId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            LoveDTO loveDTO = new LoveDTO();
            loveDTO.setDebutId(debutId);
            loveDTO.setMemberId(memberId);
            LoveEntity loveEntity = LoveEntity.toSave(loveDTO, memberEntity);
            Optional<LoveEntity> optionalLoveEntity = loveRepository.findByDebutIdAndMemberEntity(debutId, memberEntity);
            if (optionalLoveEntity.isPresent()) {
                return 0;
            } else {
                loveRepository.save(loveEntity);
                int loveNum = loveRepository.countByDebutId(debutId);
                Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(debutId);
                if (optionalDebutEpisodeEntity.isPresent()) {
                    DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
                    Optional<DebutCategoryEntity> optionalDebutCategoryEntity = debutCategoryRepository.findById(debutEpisodeEntity.getDebutCategoryEntity().getId());
                    if (optionalDebutCategoryEntity.isPresent()) {
                        DebutCategoryEntity debutCategoryEntity = optionalDebutCategoryEntity.get();
                        debutEpisodeEntity.setMemberEntity(memberEntity);
                        debutEpisodeEntity.setDebutCategoryEntity(debutCategoryEntity);
                        debutEpisodeEntity.setLove(loveNum);
                        debutRepository.save(debutEpisodeEntity);

                    }
                }
                return loveNum;
            }


        } else {
            return 0;
        }
    }

    //좋아요 삭제 기능
    @Transactional
    public int loveDelete(Long debutId, Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            LoveDTO loveDTO = new LoveDTO();
            loveDTO.setDebutId(debutId);
            loveDTO.setMemberId(memberId);
            loveRepository.deleteByDebutIdAndMemberEntity(debutId, memberEntity);
            int loveNum = loveRepository.countByDebutId(debutId);
            Optional<DebutEpisodeEntity> optionalDebutEpisodeEntity = debutRepository.findById(debutId);
            if (optionalDebutEpisodeEntity.isPresent()) {
                DebutEpisodeEntity debutEpisodeEntity = optionalDebutEpisodeEntity.get();
                Optional<DebutCategoryEntity> optionalDebutCategoryEntity = debutCategoryRepository.findById(debutEpisodeEntity.getDebutCategoryEntity().getId());
                if (optionalDebutCategoryEntity.isPresent()) {
                    DebutCategoryEntity debutCategoryEntity = optionalDebutCategoryEntity.get();
                    debutEpisodeEntity.setMemberEntity(memberEntity);
                    debutEpisodeEntity.setDebutCategoryEntity(debutCategoryEntity);
                    debutEpisodeEntity.setLove(loveNum);
                    debutRepository.save(debutEpisodeEntity);

                }
            }
            return loveNum;

        } else {
            return 0;
        }
    }

//    @Transactional
//    public Page<DebutEpisodeDTO> poemList(Long categoryId, Pageable pageable) {
//        Optional<DebutCategoryEntity> optionalCategoryEntity = debutCategoryRepository.findById(categoryId);
//        DebutCategoryEntity debutCategoryEntity = new DebutCategoryEntity();
//        if (optionalCategoryEntity.isPresent()) {
//            debutCategoryEntity = optionalCategoryEntity.get();
//        }
//
//        int page = pageable.getPageNumber();
//        page = (page == 1) ? 0 : (page - 1);
//
//        Page<DebutEpisodeEntity> debutEpisodeEntityPage = debutRepository.findByDebutCategoryEntity(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")), debutCategoryEntity);
//        Page<DebutEpisodeDTO> debutEpisodeDTOPage = debutEpisodeEntityPage.map(
//                debutEpisode -> new DebutEpisodeDTO(
//                        debutEpisode.getId(),
//                        debutEpisode.getDebutCategoryEntity().getId(),
//                        debutEpisode.getDebutTitle(),
//                        debutEpisode.getMemberEntity().getMemberName(),
//                        debutEpisode.getFeat(),
//                        debutEpisode.getIntroduce(),
//                        debutEpisode.getDebutHits(),
//                        debutEpisode.getDebutImgName()
//                ));
//        return debutEpisodeDTOPage;
//    }

    @Transactional
    public List<DebutEpisodeDTO> categoryList(Long categoryId) {
        Optional<DebutCategoryEntity> optionalDebutCategoryEntity = debutCategoryRepository.findById(categoryId);
        if (optionalDebutCategoryEntity.isPresent()) {
            DebutCategoryEntity debutCategoryEntity = optionalDebutCategoryEntity.get();
            List<DebutEpisodeEntity> debutEpisodeEntityList = debutRepository.findByDebutCategoryEntity(debutCategoryEntity);
            List<DebutEpisodeDTO> debutEpisodeDTOS = new ArrayList<>();
            for (DebutEpisodeEntity debutEpisodeEntity:debutEpisodeEntityList){
                DebutEpisodeEntity debutEpisodeEntity1 =debutEpisodeEntity;
               DebutEpisodeDTO debutEpisodeDTO = DebutEpisodeDTO.toDTO(debutEpisodeEntity1);
               debutEpisodeDTOS.add(debutEpisodeDTO);
            }
            return debutEpisodeDTOS;
        }
        return null;
    }

}
