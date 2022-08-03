package com.its.library.config.oauth.provider;

import java.util.Map;

public class KaKaoUserInfo implements Oauth2UserInfo{
    private Map<String, Object> attributes;

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
        return "kakao_";
    }

}
