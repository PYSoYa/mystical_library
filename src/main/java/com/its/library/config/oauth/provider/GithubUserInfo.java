package com.its.library.config.oauth.provider;

import java.util.Map;

public class GithubUserInfo implements Oauth2UserInfo{
    private Map<String, Object> attributes;

    public GithubUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProvider() {
        return "github";
    }

    @Override
    public String getEmail() {
        return attributes.get("login") + "@github.com";
    }

    @Override
    public String getName() {
        return  (String) attributes.get("login");
    }

}
