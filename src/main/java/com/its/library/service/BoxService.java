package com.its.library.service;

import com.its.library.dto.BookDTO;
import com.its.library.dto.BoxDTO;
import com.its.library.dto.HistoryDTO;
import com.its.library.entity.*;
import com.its.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
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

    public String pointCheck(BoxDTO boxDTO, HistoryDTO historyDTO, Long episodeId, String memberName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boxDTO.getMemberId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(historyDTO.getBookId());
        MemberEntity memberEntity = new MemberEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        BookEntity bookEntity = new BookEntity();
        if (optionalBookEntity.isPresent()) {
            bookEntity = optionalBookEntity.get();
        }
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            if (episodeEntity.getPrice() == 0) {
                Optional<HistoryEntity> optionalHistoryEntity = historyRepository.findByMemberEntityAndEpisodeEntity(memberEntity, episodeEntity);
                if (optionalHistoryEntity.isPresent()) {
                    historyEntity = optionalHistoryEntity.get();
                    historyDTO.setId(historyEntity.getId());
                    historyRepository.save(HistoryEntity.updateEntity(historyDTO, memberEntity, episodeEntity));
                    return "무료저장";
                }
                historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                return "무료저장";
            } else if (memberEntity.getMemberPoint() >= episodeEntity.getPrice()) {
                Optional<HistoryEntity> optionalHistoryEntity = historyRepository.findByMemberEntityAndEpisodeEntity(memberEntity, episodeEntity);
                if (optionalHistoryEntity.isPresent()) {
                    historyEntity = optionalHistoryEntity.get();
                    historyDTO.setId(historyEntity.getId());
                    historyRepository.save(HistoryEntity.updateEntity(historyDTO, memberEntity, episodeEntity));
                    return "결제이력있음";
                }
                historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                return "유료저장";
            } else {
                return "잔고부족";
            }

        }

        return null;
    }

    public String save(BoxDTO boxDTO, String memberName) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boxDTO.getMemberId());
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(boxDTO.getBookId());
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(boxDTO.getEpisodeId());
        BoxEntity boxEntity = new BoxEntity();
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        BookEntity bookEntity = new BookEntity();
        if (optionalMemberEntity.isPresent() && optionalBookEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            bookEntity = optionalBookEntity.get();
            if (bookEntity.getMemberName().equals(memberName)) {
                boxRepository.save(boxEntity.saveEntity(boxDTO, memberEntity));
                return "no";
            }
            List<BoxEntity> boxEntityList = boxRepository.findByMemberEntityAndEpisodeId(memberEntity, episodeEntity.getId());

            if (boxEntityList.size() != 0) {
                boxDTO.setId(boxEntityList.get(0).getId());
                boxRepository.save(boxEntity.saveEntity(boxDTO, memberEntity));
                return "no";
            } else {
                boxRepository.save(boxEntity.saveEntity(boxDTO, memberEntity));
                System.out.println("여기로오면안돼");
                return "ok";
            }
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
            for (int i = 0; i < boxEntityList.size(); i++) {
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
