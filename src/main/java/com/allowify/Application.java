package com.allowify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.zippi.listeners.code.CodeListener;




@EnableAutoConfiguration
@Configuration
@EnableMongoRepositories
@Import(RepositoryRestMvcConfiguration.class)
@ComponentScan
public class Application {

    public static void main(String[] args) throws Throwable {
    	
    	SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPidListener("/home/allowify/app.pid"));
        springApplication.addListeners(new CodeListener());
        springApplication.run(args);

    }

}
