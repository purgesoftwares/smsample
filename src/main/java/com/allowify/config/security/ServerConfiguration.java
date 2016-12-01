/** Copyright © 2015 by 1986 Tech, LLC. All Rights Reserved. **/

package com.allowify.config.security;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@EnableWebMvc
@ComponentScan
public class ServerConfiguration extends WebMvcAutoConfiguration{
			
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("**/*.css", "**/*.js", "**/*.map", "*.html")
            	.addResourceLocations("classpath:META-INF/resources/").setCachePeriod(0);
    }
    	
}