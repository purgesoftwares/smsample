package com.allowify.oAuth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

@Document
public class OAuth2AuthenticationAccessToken {

	@Id
	private String id;
	
	private String tokenId;
    private OAuth2AccessToken oAuth2AccessToken;
    private String authenticationId;
    private String userName;
    private String clientId;
    private OAuth2Authentication authentication;
    private OAuth2Request clientAuthentication;
    

	public OAuth2Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(OAuth2Authentication authentication) {
		this.authentication = authentication;
	}

	public OAuth2Request getClientAuthentication() {
		return clientAuthentication;
	}

	public void setClientAuthentication(OAuth2Request clientAuthentication) {
		this.clientAuthentication = clientAuthentication;
	}

	private String refreshToken;

	
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public void setoAuth2AccessToken(OAuth2AccessToken oAuth2AccessToken) {
		this.oAuth2AccessToken = oAuth2AccessToken;
	}

	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	public String getTokenId() {
        return tokenId;
    }

    public OAuth2AccessToken getoAuth2AccessToken() {
        return oAuth2AccessToken;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public OAuth2AuthenticationAccessToken() {
    }

    public OAuth2AuthenticationAccessToken(final OAuth2AccessToken oAuth2AccessToken, final OAuth2Authentication authentication, final String authenticationId) {
        this.tokenId = oAuth2AccessToken.getValue();
        this.oAuth2AccessToken = oAuth2AccessToken;
        this.authenticationId = authenticationId;
        this.userName = authentication.getName();
        this.clientId = authentication.getOAuth2Request().getClientId();
        this.authentication = authentication;
        this.refreshToken = oAuth2AccessToken.getValue();
        this.clientAuthentication = authentication.getOAuth2Request();
    }
}