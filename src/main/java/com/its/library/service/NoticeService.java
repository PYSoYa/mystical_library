package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.EpisodeDTO;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.NoticeEntity;
import com.its.library.entity.WishEntity;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;
    private final WishRepository wishRepository;
    private final NoticeRepository noticeRepository;

    @Transactional
    public void save(EpisodeDTO episodeDTO, Long id) {
       Optional<MemberEntity> optionalMemberEntity =memberRepository.findById(id);
       Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeDTO.getId());
       if (optionalEpisodeEntity.isPresent()& optionalMemberEntity.isPresent()){
           MemberEntity memberEntity = optionalMemberEntity.get();
           EpisodeEntity episodeEntity = optionalEpisodeEntity.get();
          Optional<WishEntity> optionalWishEntity = wishRepository.findByMemberEntity_IdAndBookEntity_Id(memberEntity,episodeEntity.getBookEntity());
          if (optionalWishEntity.isPresent()){
              WishEntity wishEntity = optionalWishEntity.get();
             NoticeEntity noticeEntity = NoticeEntity.save(memberEntity,episodeEntity,wishEntity);
             noticeRepository.save(noticeEntity);

          }

       }




    }
}
