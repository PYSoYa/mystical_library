package com.its.library.config.oauth.provider;

import com.its.library.config.auth.PrincipalDetails;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;

public class KaKaoUserInfo implements Oauth2UserInfo{
    private Map<String, Object> attributes;

    @Autowired
    private PrincipalDetails oAuth2User;

    public KaKaoUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return (String) attributes.get("nickname");
    }

}
