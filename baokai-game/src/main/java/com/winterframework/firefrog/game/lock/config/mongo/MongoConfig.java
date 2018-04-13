package com.winterframework.firefrog.game.lock.config.mongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {
	public @Bean
	MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "lockdb");
	}

	public @Bean
	MongoTemplate mongoTemplate() throws Exception {
		//MappingMongoConverter  convert=new MappingMongoConverter(mongoDbFactory(), new MongoMappingContext());
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());

		return mongoTemplate;

	}

}