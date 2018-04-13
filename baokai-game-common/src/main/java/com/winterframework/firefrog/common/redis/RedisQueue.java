package com.winterframework.firefrog.common.redis;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("redisQueue")
public class RedisQueue {
	private static final Logger log = LoggerFactory.getLogger(RedisQueue.class);
	
	@PropertyConfig(value = "redis1.ip")
	private String redisIp;
	@PropertyConfig(value = "redis.port")
	private Integer redisPort;
	@PropertyConfig(value = "redis.pool.maxActive")
	private Integer maxActive;
	@PropertyConfig(value = "redis.pool.maxIdle")
	private Integer maxIdle;
	@PropertyConfig(value = "redis.pool.maxWait")
	private Integer maxWait;
	@PropertyConfig(value = "redis.pool.testOnBorrow")
	private Boolean testOnBorrow;
	@PropertyConfig(value = "redis.pool.testOnReturn")
	private Boolean testOnReturn;

	private JedisPool jedisPool;

	public RedisQueue() {
	}

	@PostConstruct
	public void initialPool() {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(maxIdle);
		config.setMinIdle(10);
		config.setTestOnBorrow(testOnBorrow);
		config.setTestOnReturn(testOnReturn);
		config.setMaxWait(maxWait);
		config.setMaxActive(maxActive);
		config.setTestWhileIdle(true);
		//config.setTimeBetweenEvictionRunsMillis(30000);
		jedisPool = new JedisPool(config, redisIp);
    	//尝试获取一下数据，检验是否http超时
		this.ping();

	}
	/**
	 * 入队列
	 * @param queueName
	 * @param data
	 */
	public void add(String queueName,String data){
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				instance.rpush(queueName, data);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
			throw e;
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	} 
	/**
	 * 查看队列长度 (小于5000)
	 * @param queueName
	 * @return
	 */
	public int len(String queueName){
		Long value = 0L;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				value = instance.llen(queueName);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value.intValue();
	}
	public List<String> get(String... queueNames) {
		List<String> value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(getAllQueueName(queueNames)){
				value = instance.blpop(1,queueNames);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	public String get(String queueName) {
		String value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			long time1=System.currentTimeMillis();
			instance = jedisPool.getResource();
			long time2=System.currentTimeMillis();
			log.debug("redis get resource time:"+(time2-time1));
			synchronized(queueName){
				long time3=System.currentTimeMillis();
				log.debug("redis get queue time:"+(time3-time2));
				value = instance.lpop(queueName);
				long time4=System.currentTimeMillis();
				log.debug("redis oper time:"+(time4-time3));
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}
	public List<String> get(String queueName,int fromIndex,int toIndex) {
		List<String> value = null;
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				value = instance.lrange(queueName, fromIndex, toIndex);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return value;
	}

	/**
	 * 通过key删除
	 * @param key
	 */
	public void del(String queueName,String value) {

		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			synchronized(queueName){
				instance.lrem(queueName, 1, value);
			}
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
	}
	/**
	 * 检查是否连接成功
	 * @return
	 */
	public String ping() {
		Jedis instance = null;
		boolean borrowOrOprSuccess = true;
		try {
			instance = jedisPool.getResource();
			return instance.ping();
		} catch (JedisConnectionException e) {
			borrowOrOprSuccess = false;
			if (instance != null) {
				jedisPool.returnBrokenResource(instance);
			}
		} finally {
			if (borrowOrOprSuccess) {
				jedisPool.returnResource(instance);
			}
		}
		return null;
	}
	
	private String getAllQueueName(String...queueNames){
		if(null!=queueNames){
			StringBuilder sb=new StringBuilder();
			for(String queueName:queueNames){
				sb.append(queueName);
			}
			return sb.toString();
		}
		return "";
	}
}
