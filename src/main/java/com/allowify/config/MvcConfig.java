package com.allowify.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.allowify.oAuth.OAuth2AuthenticationAccessTokenRepository;
import com.allowify.oAuth.OAuth2RefreshTokenRepository;
import com.allowify.repository.UserRepositoryCustom;

@Configuration
@EnableMongoRepositories(basePackageClasses = {
        UserRepositoryCustom.class,
        OAuth2RefreshTokenRepository.class,
        OAuth2AuthenticationAccessTokenRepository.class
})
public class MvcConfig extends WebMvcConfigurerAdapter {
    
	/** @Override
	 public void configureDefaultServletHandling(
	 DefaultServletHandlerConfigurer configurer) {
	 configurer.enable();
	 }
	**/
	
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        
        registry.addViewController("/login").setViewName("login");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/resources/**")
                    .addResourceLocations("/resources/").addResourceLocations("/resources/templates/img/logo.png");
    	
    }

}
