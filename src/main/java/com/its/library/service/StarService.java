package com.its.library.service;

import com.its.library.dto.StarDTO;
import com.its.library.entity.EpisodeEntity;
import com.its.library.entity.MemberEntity;
import com.its.library.entity.StarEntity;
import com.its.library.repository.EpisodeRepository;
import com.its.library.repository.MemberRepository;
import com.its.library.repository.StarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StarService {
    private final StarRepository starRepository;
    private final MemberRepository memberRepository;
    private final EpisodeRepository episodeRepository;

    public StarDTO starList(Long memberId, Long episodeId) {
        List<StarEntity> starEntityList = new ArrayList<>();
        StarEntity starEntity = new StarEntity();
        MemberEntity memberEntity = new MemberEntity();
        EpisodeEntity episodeEntity = new EpisodeEntity();
        StarDTO starDTO = new StarDTO();
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(memberId);
        Optional<EpisodeEntity> optionalEpisodeEntity = episodeRepository.findById(episodeId);
        if (optionalMemberEntity.isPresent() && optionalEpisodeEntity.isPresent()) {
            memberEntity = optionalMemberEntity.get();
            episodeEntity = optionalEpisodeEntity.get();
            Optional<StarEntity> optionalStarEntity = starRepository.findByMemberEntityAndEpisodeEntity(memberEntity, episodeEntity);
            if (optionalStarEntity.isPresent()) {
                starEntity = optionalStarEntity.get();
                starDTO = StarDTO.findDTO(starEntity);
            }
        }
        return starDTO;
    }
}
