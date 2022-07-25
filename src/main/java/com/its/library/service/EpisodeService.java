package com.its.library.service;

import com.its.library.dto.EpisodeDTO;
import com.its.library.entity.EpisodeEntity;
import com.its.library.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EpisodeService {
    private final EpisodeRepository episodeRepository;


    public List<EpisodeDTO> findAll() {
        List<EpisodeEntity> episodeEntityList = episodeRepository.findAll();
        List<EpisodeDTO> episodeDTOList = new ArrayList<>();
        for (EpisodeEntity episodeEntity: episodeEntityList) {
            EpisodeEntity episodeEntity1 = episodeEntity;
           EpisodeDTO episodeDTO =  EpisodeDTO.findDTO(episodeEntity1);
           episodeDTOList.add(episodeDTO);
        }
        return episodeDTOList;
    }
}
