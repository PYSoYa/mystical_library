package com.its.library.config.auth;

import com.its.library.dto.MemberDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {
    private MemberDTO memberDTO;

    private Map<String, Object> attributes;

    public PrincipalDetails(MemberDTO memberDTO) {
        this.memberDTO = memberDTO;
    }

    public PrincipalDetails(MemberDTO memberDTO, Map<String, Object> attributes) {
        this.memberDTO = memberDTO;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDTO.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return memberDTO.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return memberDTO.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
