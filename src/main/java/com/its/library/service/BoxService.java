package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.BoxDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.entity.*;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoxService {
    private final BoxRepository boxRepository;
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;
    private final BookRepository bookRepository;
    private final HistoryRepository historyRepository;

    public String pointCheck(BoxDTO boxDTO, HistoryDTO historyDTO, Long episodeId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boxDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        MemberEntity memberEntity = new MemberEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
             Optional<HistoryEntity> optionalHistoryEntity = historyRepository.findByBooKIdAndMemberEntity(boxDTO.getBookId(), memberEntity);
             if (optionalHistoryEntity.isPresent()) {
                 historyEntity = optionalHistoryEntity.get();
                 if (historyEntity.getHidden() == 1){
                     historyEntity.setHidden(0);
                     historyRepository.save(historyEntity);
                     return "보여줘";
                 }
             }
            if (episodeEntity.getPrice() == 0) {
                historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                return "무료";
            }
            if (memberEntity.getMemberPoint() < episodeEntity.getPrice()) {
                return "ok";
            } else {
                historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
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

    public List<BookDTO> list(Long memberId) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        MemberEntity memberEntity = new MemberEntity();
        List<BoxEntity> boxEntityList = new ArrayList<>();
        List<BookDTO> bookDTOList = new ArrayList<>();
        if (optionalMemberEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            boxEntityList = boxRepository.findByMemberEntity(memberEntity);
            for (int i = 0; i < boxEntityList.size(); i++){
                Optional<BookEntity> optionalBookEntity = bookRepository.findById(boxEntityList.get(i).getBookId());
                if (optionalBookEntity.isPresent()) {
                    bookDTOList.add(BookDTO.findDTO(optionalBookEntity.get()));
                }
            }
            return bookDTOList;
        } else {
            return null;
        }
    }
}
