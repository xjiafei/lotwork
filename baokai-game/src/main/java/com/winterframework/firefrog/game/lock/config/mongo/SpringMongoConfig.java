package com.winterframework.firefrog.game.lock.config.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {
	@Value("${lock.mongo.ip}")
	private String ip;
	@Override
	public String getDatabaseName() {
		return "lockdb";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new MongoClient(ip);
	}
}