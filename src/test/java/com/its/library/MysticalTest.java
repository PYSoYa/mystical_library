package com.its.library;

import com.its.library.dto.MemberDTO;
import com.its.library.entity.MemberEntity;
import com.its.library.repository.MemberRepository;
import com.its.library.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MysticalTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    // 일반 회원 기능 테스트
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입시 아이디 중복체크 테스트")
    public void MemberLoginIdDuplicateTest() {
        // 현재 관리자 아이디가 admin 으로 이미 사용중
        MemberDTO memberDTO = new MemberDTO();
        String loginId = "admin";
        memberDTO.setLoginId(loginId);
        MemberDTO findDTO = memberService.findByLoginId(loginId);
        assertThat(findDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원가입시 이메일 중복체크 테스트")
    public void MemberEmailDuplicateTest() {
        // 관리자의 이메일은 admin@mystical.com 사용중
        MemberDTO memberDTO = new MemberDTO();
        String memberEmail = "admin@mystical.com";
        MemberDTO findDTO = memberService.findByMemberEmail(memberEmail);
        assertThat(findDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 로그인 테스트")
    public void MemberLoginTest() {
        // 아이디 1, 비밀번호 1 인 회원이 있음
        MemberDTO memberDTO = new MemberDTO();
        String loginId = "1";
        String memberPassword = "1";
        memberDTO.setLoginId(loginId);
        memberDTO.setMemberPassword(memberPassword);
        MemberDTO findDTO = memberService.login(memberDTO);
        assertThat(findDTO).isNotNull();
    }

    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원정보 상세조회 테스트")
    public void MemberDetailTest() {
        // 회원번호 1번의 상세정보 조회
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(1L);
        MemberDTO memberDTO = new MemberDTO();
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            memberDTO = MemberDTO.toDTO(memberEntity);
        }
        assertThat(memberDTO).isNotNull();
    }
    
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("회원 탈퇴(삭제) 테스트")
    public void MemberDeleteTest() {
        // 1번 회원을 삭제
        memberService.memberDelete(1L);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(1L);
        assertThat(optionalMemberEntity).isEmpty();
    }
    
    @Test
    @Transactional
    @Rollback(value = true)
    @DisplayName("전체 회원 수 테스트")
    public void MemberFindAllTest() {
        // 현재 회원 수 3명
        List<MemberDTO> memberDTOList = memberService.findAll();
        assertThat(memberDTOList.size()).isEqualTo(3);
    }
    
    // 관리자 기능 테스트
//    @Test
//    @Transactional
//    @Rollback(value = true)
//    @DisplayName("")

}
