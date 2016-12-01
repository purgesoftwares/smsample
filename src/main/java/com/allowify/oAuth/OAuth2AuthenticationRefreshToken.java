package com.allowify.oAuth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

@Document
public class OAuth2AuthenticationRefreshToken{

	@Id
	private String id;
	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public void setoAuth2RefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
		this.oAuth2RefreshToken = oAuth2RefreshToken;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = authentication;
	}

	private String tokenId;
	
    private OAuth2RefreshToken oAuth2RefreshToken;
    private OAuth2Authentication authentication;

    public OAuth2AuthenticationRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication authentication) {
        this.oAuth2RefreshToken = oAuth2RefreshToken;
        this.authentication = authentication;
        this.tokenId = oAuth2RefreshToken.getValue();
    }

    public String getTokenId() {
        return tokenId;
    }

    public OAuth2RefreshToken getoAuth2RefreshToken() {
        return oAuth2RefreshToken;
    }

    public OAuth2Authentication getAuthentication() {
        return authentication;
    }
}