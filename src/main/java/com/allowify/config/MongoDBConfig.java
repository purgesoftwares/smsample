package com.allowify.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableAutoConfiguration
public class MongoDBConfig {

	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
	    return new SimpleMongoDbFactory(new MongoClient("127.0.0.1", 27017), "test_app");
	  }
	
	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
 
		MongoTemplate mongoTemplate = 
				new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
 
	}
	
	 public @Bean Mongo mongo() throws Exception {
	      return new Mongo("127.0.0.1", 27017);
	  }
	 
	/*
	   * Use the standard Mongo driver API to create a com.mongodb.Mongo instance.
	   */
	/*   @SuppressWarnings("deprecation")
	public @Bean Mongo mongo() throws UnknownHostException {
	       return new Mongo("127.0.0.1", 27017);
	   }*/
	   
	    /*
	     * Factory bean that creates the com.mongodb.Mongo instance
	     */
	     /*public @Bean MongoFactoryBean mongoFactoryBean() throws Exception {
	    	 
	    	 MongoOperations mongoOperation = (MongoOperations) mongoTemplate();
	    	 
	          MongoFactoryBean mongo = new MongoFactoryBean();
	          mongo.setHost("localhost");
	          return mongo;
	     }*/
	     
	     /*
		     * Factory bean that creates the com.mongodb.Mongo instance
		     */
		    /* public @Bean MongoOperations mongoOperation() throws Exception {
		    	 
		    	 MongoOperations mongoOperation = (MongoOperations) mongoTemplate();
		    	 
		    	 return mongoOperation;
		     }*/
}