package com.allowify.config;


import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.allowify.CustomUserDetailsService;
import com.allowify.filters.AbstractAuthenticationProcessingFilterImpl;
import com.allowify.handlers.AuthenticationFailureHandlerImpl;
import com.allowify.handlers.AuthenticationSuccessHandlerImpl;
import com.allowify.service.ActivityService;

@Configuration
@EnableWebMvcSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    private static final int FOURTEEN_DAYS_IN_SECONDS = 1209600; 
	
	//private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("userDetailsService")
	UserDetailsService userDetailsService;
	
	@Autowired
	DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth, ApplicationContext context)
			throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(
				passwordEncoder());
		auth.authenticationEventPublisher(new DefaultAuthenticationEventPublisher(context));
	}

	
    @SuppressWarnings("restriction")
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	
    	http
        	.csrf().disable();
    	http
		.headers()
		.frameOptions().disable();
	
	    http.addFilterBefore(authenticationFilter(),
	        UsernamePasswordAuthenticationFilter.class);
        http
            .authorizeRequests()
                //.antMatchers("/", "/home").permitAll()
            .antMatchers("/secure/**").permitAll()
                .anyRequest().authenticated();
        http
        	.httpBasic()
        		.and()
        		.x509();
        http
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
        http
        	.securityContext();
    }
    
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private ShaPasswordEncoder passwordEncoder;
	
	
	@Override
    protected void configure(AuthenticationManagerBuilder registry) 
    		throws Exception {
		
		registry.userDetailsService(customUserDetailsService)
		.passwordEncoder(passwordEncoder);
    }
	
	


	  @Override
	  public void configure(WebSecurity web) throws Exception {
	    web
	      .ignoring()
	         .antMatchers("**/css/**")
	         .antMatchers("**/img/**")
	         .antMatchers("**/js/**")
	         .antMatchers("**/fonts/**")
	         .antMatchers("**.png")
	         .antMatchers("**.css")
	         .antMatchers("/home")
	         .antMatchers("/users/**/processTransaction/**")
	         .antMatchers("/users/getUserLocation/**")
	         .antMatchers("/users/getUsers")
	         .antMatchers("/users/create")
	         .antMatchers("/users/getlogo")
	         .antMatchers("/users/addCard")
	         .antMatchers("/users/me")
	         .antMatchers("/users/addUserByGPS")
	         .antMatchers("/users/getUserCards/**")
	         .antMatchers("/user")
	         .antMatchers("/**");
	  }
	  
	  	@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	  
		@Bean
		public DaoAuthenticationProvider getProvider(CustomUserDetailsService customUserDetailsService){
			DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
			daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
			daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
			
			return daoAuthenticationProvider;
		}
		
		
	  @Bean
	  public ShaPasswordEncoder passwordEncoder(){
		  return new ShaPasswordEncoder();
	  }
	  
	  @Bean
	  public ConcurrentTaskExecutor concurrentTaskExecutor() {
		  return new ConcurrentTaskExecutor();
	  }
	  
	  	@Bean
		public AbstractAuthenticationProcessingFilterImpl authenticationFilter() throws Exception {
			AbstractAuthenticationProcessingFilterImpl authFilter = new AbstractAuthenticationProcessingFilterImpl();
			authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login","POST"));
			authFilter.setAuthenticationManager(authenticationManagerBean());
		    authFilter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandlerImpl());
		    authFilter.setAuthenticationFailureHandler(new AuthenticationFailureHandlerImpl());
		    return authFilter;
		}
	 
}
	  
	    
