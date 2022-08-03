package com.its.library.config.auth;

import com.its.library.dto.MemberDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByLoginId(loginId);
        if (optionalMemberEntity.isPresent()) {
            MemberDTO memberDTO = MemberDTO.toDTO(optionalMemberEntity.get());
            return new PrincipalDetails(memberDTO);
        }
        return null;
    }
}
