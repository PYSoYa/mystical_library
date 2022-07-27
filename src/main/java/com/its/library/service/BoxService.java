package com.its.library.service;

import com.its.library.dto.BoxDTO;
import com.its.library.entity.BookEntity;
import com.its.library.entity.BoxEntity;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.BookRepository;
import com.its.library.repository.BoxRepository;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;
    private final BookRepository bookRepository;

    public String pointCheck(Long memberId, Long episodeId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            if (memberEntity.getMemberPoint() < episodeEntity.getPrice()) {
                return "ok";
            } else {
                return "no";
            }
        } else {
            return null;
        }
    }

    public String save(BoxDTO boxDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boxDTO.getMemberId());
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(boxDTO.getBookId());
        BoxEntity boxEntity = new BoxEntity();
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent() && optionalBookEntity.isPresent()){
            memberEntity = optionalMemberEntity.get();
           boxRepository.save(boxEntity.saveEntity(boxDTO, memberEntity));
           return "ok";
        } else {
            return "no";
        }

    }
}
