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
        List<HistoryEntity> historyEntityList = new ArrayList<>();
        List<Long> list = new ArrayList<>();
        MemberEntity memberEntity = new MemberEntity();
        HistoryEntity historyEntity = new HistoryEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();

            if (episodeEntity.getPrice() == 0) {
                list = historyRepository.findByMemberId(memberEntity.getId());
                if (list.size() == 0) {
                    historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                    return "무료";
                } else if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!episodeId.equals(list.get(i))) { // 히스토리에 있는 회차와 들고온 회차가 같을때
                            historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                            return "무료";
                        } else {
                            Optional<HistoryEntity> optionalHistoryEntity = historyRepository.findByBooKIdAndMemberEntity(boxDTO.getBookId(), memberEntity);
                            if (optionalHistoryEntity.isPresent()) {
                                historyEntity = optionalHistoryEntity.get();
                                if (historyEntity.getHidden() == 1) {
                                    historyEntity.setHidden(0);
                                    historyRepository.save(historyEntity);
                                    return "무료";
                                }
                            }
                        }
                    }
                }
            } else if (memberEntity.getMemberPoint() < episodeEntity.getPrice()) { // 사용자 돈이 부족할때
                return "잔고부족";
            } else if (memberEntity.getMemberPoint() > episodeEntity.getPrice()) { // 사용자 돈이 부족하지 않을때
                list = historyRepository.findByMemberId(memberEntity.getId());
                if (list.size() == 0) {
                    historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                    return "결제";
                } else if (list.size() != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        if (!episodeId.equals(list.get(i))) {
                            historyRepository.save(HistoryEntity.saveEntity(historyDTO, memberEntity, episodeEntity));
                            return "결제";
                        }
                    }
                }

            } else {
                return null;
            }
        } else {
            return null;
        }
        return null;
    }

    public String save(BoxDTO boxDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(boxDTO.getMemberId());
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(boxDTO.getBookId());
        BoxEntity boxEntity = new BoxEntity();
        MemberEntity memberEntity = new MemberEntity();
        if (optionalMemberEntity.isPresent() && optionalBookEntity.isPresent()) {
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
