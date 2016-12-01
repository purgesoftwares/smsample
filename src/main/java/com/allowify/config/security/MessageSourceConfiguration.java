package com.allowify.config.security;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;



/**
 * 
 * @author bharatxtreem
 *
 */

@Configuration 
@ComponentScan("com.zippi.*") 
@EnableWebMvc
public class MessageSourceConfiguration extends WebMvcConfigurerAdapter {
		
	
   /* @Autowired
    private RequestProcessingTimeInterceptor requestInterceptor;*/
	
	@Bean  
    public InternalResourceViewResolver viewResolver() {  
		
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();  
        resolver.setPrefix("/WEB-INF/jsp/");  
        resolver.setSuffix(".jsp");
        return resolver;  
    }
	
    @Bean
    public MessageSource messageSource() {
    	
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:language/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
    
    @Bean
    public LocaleResolver localeResolver(){
    	CookieLocaleResolver resolver=new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en","US"));
		resolver.setCookieMaxAge(60000);
		return resolver;
    }
    
  /*  @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	
		LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
		interceptor.setParamName("locale");
		registry.addInterceptor(interceptor);
		
		registry.addInterceptor(requestInterceptor);
		
    }*/
    
}
