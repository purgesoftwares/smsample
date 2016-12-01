package com.zippi.test;

import java.net.URL;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.allowify.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Application.class})
@WebAppConfiguration
@IntegrationTest({"server.port=0"})
public class ZippiTestSuite {
	
	protected String baseContext = "http://localhost:";
	
	@Value("${local.server.port}")
	protected int port ;
	
	protected RestTemplate restTemplate;
	
	protected URL userControllerURL;
	
	protected URL userRepositoryURL;
	
	protected URL codeURL;
	
	protected URL bookmarkURL;
	
	protected URL commentURL;
	
	protected URL likeURL;
	
	protected URL checkInURL;
	
	protected URL reviewsURL;
	
	protected URL friendURL;
	
	
	
	@Before
	public void setUp() throws Exception {
		String baseURL = baseContext + port;
		
		this.userControllerURL = new URL(baseURL + "/users");
		
		this.userRepositoryURL = new URL(baseURL + "/user");
		
		this.codeURL  = new URL(baseURL+ "/code");
		
		this.bookmarkURL = new URL(baseURL+ "/bookmark");
		
		this.commentURL = new URL(baseURL+ "/comment");
		
		this.likeURL = new URL(baseURL+ "/like");
		
		this.checkInURL = new URL(baseURL+ "/check-in");
		
		this.reviewsURL = new URL(baseURL+ "/reviews");
		
		this.friendURL = new URL(baseURL+ "/friend"); 
		
		restTemplate = new TestRestTemplate();
	
	}

}
