/**   
* @Title: SNUtil.java 
* @Package com.winterframework.firefrog.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-3 下午4:35:16 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.util;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.MultMd5;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.utils.DateConvertUtils;

/** 
* @ClassName: SNUtil 
* @Description: 创建资金业务的流水号和资金变动的流水号. 
* @author Page
* @date 2013-7-3 下午4:35:16 
*  
*/
@Service("SNUtil")
public class SNUtil implements ISNGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SNUtil.class);
	
	private final static String FLAG = "";

	@Resource(name = "RedisClient")
	private RedisClient redis;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "function_business_sn")
	private String businessSn;
	
	@PropertyConfig(value = "fund_withdraw_appeal")
	private String fwappealSn;

	@PropertyConfig(value = "function_fund_sn")
	private String fundSn;

	private String getRedisKey(String functionKey) {
		return this.module + "_" + functionKey;
	}

	private String createSn(String module, String identifier, String redisKey, long userId) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(module);
		sb.append(FLAG);
		sb.append(identifier);
		sb.append(FLAG);
		sb.append(MultMd5.to62Digit(userId));
		String now = DateConvertUtils.format(new Date(), "yyyyMMddHHmmssSSS");
		sb.append(FLAG);
		sb.append(MultMd5.to62Digit(Long.parseLong(now)));
		sb.append(FLAG);
		long tcode = 1;
		if (!redis.exists(redisKey)) {
			redis.set(redisKey, String.valueOf(System.currentTimeMillis()) + "+1");
		} else {
			String[] value = StringUtils.split(redis.get(redisKey),"+");
			long redisDate = Long.valueOf(value[0]);
			long code = Long.valueOf(value[1]);
			Calendar redisCal = Calendar.getInstance();
			redisCal.setTimeInMillis(redisDate);
			if (DateUtils.truncatedEquals(Calendar.getInstance(), redisCal,Calendar.HOUR_OF_DAY)) {
				tcode = code + 1;
				redis.set(redisKey, redisDate + "+" + String.valueOf(tcode));
			} else {
				redis.set(redisKey, String.valueOf(System.currentTimeMillis()) + "+1");
			}
		}
	
		tcode += Thread.currentThread().getId();
	
	//	tcode += getPID();
	
		tcode += Runtime.getRuntime().freeMemory();
	
		//sb.append(MultMd5.to62Digit(tcode));
		return sb.toString().toUpperCase()+MultMd5.to62Digit(tcode) ;
	}
		
	
	private String createAppealSn(String module, String identifier, String redisKey, long userId) {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(identifier);
		String now = DateConvertUtils.format(new Date(), "yyyyMMdd");
		sb.append(now);
		sb.append(MultMd5.getRandomThree());		
		int liveTime = 24*60*60;
		String key = sb.toString();
		if(redis.exists(redisKey)){
			key = createAppealSn(module, identifier, redisKey, userId);
		}else{
			redis.set(sb.toString(),String.valueOf(userId),liveTime);
		}
		return key.toUpperCase();
	}	
	

	/**
	* Title: createSN
	* Description:
	* @param item
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#createBusinessSn(com.winterframework.firefrog.fund.enums.EnumItem, long) 
	*/
	@Override
	public String createBusinessSn(EnumItem item, long userId) {
		return this.createSn(item.getModel().getCode(), item.getSummaryCode(), getRedisKey(this.businessSn), userId);
	}
	@Override
	public String createBusinessSnForNull(EnumItem item, long userId) {
		return this.createSn(item.getModel().getCode(), item.getSummaryCode()+item.getTradeStatus(), getRedisKey(this.businessSn), userId);
	}
	

	public String createAPlSn(EnumItem item, long userId) {
		return this.createAppealSn(item.getModel().getCode(), item.getSummaryCode(), getRedisKey(this.fwappealSn), userId);
	}

	/**
	* Title: parseSNDesc
	* Description:
	* @param sn
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#parseBusinessSnDesc(java.lang.String) 
	*/
	@Override
	public String parseBusinessSnDesc(String sn) {
		//String typeStr = sn.substring(0, 2);
		//String descStr = sn.substring(2, 6);
		if (sn.startsWith("TCL"))
			return "TCL";
		return sn.substring(0, 6);

	}
	
	public long getPID() {
		 String processName =
		 java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		 return Long.parseLong(processName.split("@")[0]);

		}

	/**
	* Title: createFundSn
	* Description:
	* @param item
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#createFundSn(com.winterframework.firefrog.fund.enums.EnumItem, long) 
	*/
	@Override
	public String createFundSn(EnumItem item, long userId) {
		if (item.getTradeCode() != null) {
			return this.createSn(item.getModel().getCode(), item.getTradeCode(), getRedisKey(this.fundSn), userId);
		} else
			return null;
	}
}
