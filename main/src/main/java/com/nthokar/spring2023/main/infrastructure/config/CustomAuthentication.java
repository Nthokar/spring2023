package com.nthokar.spring2023.main.infrastructure.config;

import com.nthokar.spring2023.main.application.entities.UserEntity;
import lombok.Getter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CustomAuthentication implements Authentication {

    private String token;
    private boolean isAuthenticated;
    private final List<GrantedAuthority> authorities;
    @Getter
    private final String id;

    public CustomAuthentication(String claimsString) {
        setAuthenticated(true);
        var claims = new HashMap<String, String>();
        claimsString = claimsString.replace("\"", "");
        var keyValuePairs = claimsString.split(",");
        for (var keyValue:keyValuePairs) {
            var keyValueArray = keyValue.split(":");
            claims.put(keyValueArray[0], keyValueArray[1]);
        }
        authorities = new ArrayList<>();
        var scopes = claims.get("scope");
        authorities.add(new SimpleGrantedAuthority(scopes));
        id = claims.get("user_id");
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getCredentials() {
        return token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public UserEntity getPrincipal() {
        return new UserEntity(id, null, null);
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return null;
    }
}
