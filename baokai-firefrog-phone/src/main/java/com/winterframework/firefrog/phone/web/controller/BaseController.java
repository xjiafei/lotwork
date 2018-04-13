package com.winterframework.firefrog.phone.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.redis.RedisClient2;
import com.winterframework.firefrog.phone.utils.Encrypter;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserDetailResponse;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserStruc;
import com.winterframework.firefrog.phone.web.cotroller.dto.UserToken;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

@Controller("baseController")
public class BaseController {
	
	@Resource(name = "RedisClient")
	protected RedisClient redisClient;
	@Resource(name = "RedisClient2")
	protected RedisClient2 redisClient2;
	@Resource(name = "httpJsonClientImpl")
	protected IHttpJsonClient httpClient;
	
	@PropertyConfig(value = "url.connect")
	protected String firefrogUrl;
	@PropertyConfig(value = "url.game")
	protected String gameUrl;
	@PropertyConfig(value = "url.support")
	protected String supportUrl;
	@PropertyConfig(value = "url.active")
	protected String activeUrl;
	@PropertyConfig(value="encrypter.Key")
	protected String encrypterKey;
	@PropertyConfig(value="encrypter.value")
	protected String encrypterValue;
	
	protected static final Map<String,String> Bank_Name_Map= new HashMap<String,String>();
	protected static final Map<String,String> QUESTION = new HashMap<String,String>();
	protected static final Map<Long,String> LOTTERY_CODE_MAP=new HashMap<Long, String>();
	protected static final List<String> RECHARGE_COUNTDOWN = new ArrayList<String>();
	static {
		Bank_Name_Map.put("2", "招商银行");
		Bank_Name_Map.put("3", "建设银行");
		Bank_Name_Map.put("4", "农业银行");
		Bank_Name_Map.put("5", "中国银行");
		Bank_Name_Map.put("6", "交通银行");
		Bank_Name_Map.put("7", "民生银行");
		Bank_Name_Map.put("8", "中信银行");
		Bank_Name_Map.put("9", "浦发银行");
		Bank_Name_Map.put("10", "邮政储蓄银行");
		Bank_Name_Map.put("11", "光大银行");
		Bank_Name_Map.put("12", "平安银行");
		Bank_Name_Map.put("13", "广发银行");
		Bank_Name_Map.put("14", "华夏银行");
		Bank_Name_Map.put("1", "中国工商银行");
		Bank_Name_Map.put("15", "福建兴业银行");
		Bank_Name_Map.put("51", "中国银联银行");
		Bank_Name_Map.put("30", "支付宝");
		Bank_Name_Map.put("40", "微信支付");
		Bank_Name_Map.put("35", "QQ钱包");
		
		QUESTION.put("1", "我宠物的名字？");
		QUESTION.put("2", "我最亲密的朋友是？");
		QUESTION.put("3", "我最喜欢的演员？");
		QUESTION.put("4", "我最喜欢的球星？");
		QUESTION.put("5", "我最喜欢的球队？");
		QUESTION.put("6", "我最喜欢吃的菜？");
		QUESTION.put("7", "我最崇拜的人？");
		
		LOTTERY_CODE_MAP.put(99101L, "cqssc");  
		LOTTERY_CODE_MAP.put(99102L,"jxssc");
		LOTTERY_CODE_MAP.put(99103L,"xjssc");
		LOTTERY_CODE_MAP.put(99104L,"tjssc");
		LOTTERY_CODE_MAP.put(99105L,"hljssc");
		LOTTERY_CODE_MAP.put(99106L,"llssc");
		LOTTERY_CODE_MAP.put(99107L,"shssl");
		LOTTERY_CODE_MAP.put(99108L,"fc3d");
		LOTTERY_CODE_MAP.put(99109L,"p5");
		LOTTERY_CODE_MAP.put(99201L,"bjkl8");
		LOTTERY_CODE_MAP.put(99301L,"sd115");
		LOTTERY_CODE_MAP.put(99302L,"jx115");
		LOTTERY_CODE_MAP.put(99303L,"gd115");
		LOTTERY_CODE_MAP.put(99304L,"cq115");
		LOTTERY_CODE_MAP.put(99305L,"ll115");
		LOTTERY_CODE_MAP.put(99307L,"js115");
		LOTTERY_CODE_MAP.put(99401L,"ssq");
		LOTTERY_CODE_MAP.put(99111L,"jlffc");
		LOTTERY_CODE_MAP.put(99112L,"slmmc");
		LOTTERY_CODE_MAP.put(99501L,"jsk3");
		LOTTERY_CODE_MAP.put(99502L,"ahk3");
		LOTTERY_CODE_MAP.put(99601L,"jsdice");
		LOTTERY_CODE_MAP.put(99602L,"jldice1");
		LOTTERY_CODE_MAP.put(99701L,"lhc");
		LOTTERY_CODE_MAP.put(99114L,"txffc");
		
		RECHARGE_COUNTDOWN.add("chargeCoute");
		RECHARGE_COUNTDOWN.add("chargeCouteCaifu");		
		RECHARGE_COUNTDOWN.add("chargeCouteThired");		
		RECHARGE_COUNTDOWN.add("chargeCouteUnionpay");	
		RECHARGE_COUNTDOWN.add("thirdChargeLimit");	
	}
	
	
	
	
	
	private static final String REDIS_KEY ="USER_TOKEN_";
	private static final int REDIS_LIMIT_EXPIRE_TIME = 30*24*60*60;

	protected void setRedis(UserToken ut){
		String redKey = REDIS_KEY+ ut.getUserName();
		redisClient.set(redKey, JsonMapper.nonDefaultMapper().toJson(ut),REDIS_LIMIT_EXPIRE_TIME);
	}
	
	protected UserToken getUserToken(String userName){
		
		String redisKey = REDIS_KEY+userName;
		String utStr =  redisClient.get(redisKey);
		UserToken ut = JsonMapper.nonDefaultMapper().fromJson(utStr, UserToken.class);
		return ut;
	}
	
	protected String getToken(String userName){
		UserToken ut = getUserToken(userName);
		return ut.getToken();
	}
	
	protected Long[] allSeries(){
		Long[] all = {99101L,99102L,99103L,99104L,99105L,99106L,99107L,99108L,99109L,99110L,99111L,99114L,99112L,99201L,99301L,99302L,99303L,99304L,99305L,99307L,99401L,99501L,99502L,99601L,99602L,99701L};
		return all;
	}
	
	protected String getUserNameByToken(String token) throws Exception{
//		MD5Utils md = new MD5Utils();
		Encrypter en = Encrypter.getInstance(encrypterKey,encrypterValue);
		String tt = en.decrypt(token);
		String[] str = tt.split("\\|");
		
		
		//更新token
		UserToken ut = getUserToken(str[0]);
		//token失效
		if(null == ut){
			return null;
		}
		
		if(new Date().compareTo(ut.getTimeOut()) > 0){
			return null;
		}
		
		ut.setLoginDate(new Date());
		java.util.Calendar cal=java.util.Calendar.getInstance();  
		cal.setTime(new Date());   
		cal.add(java.util.Calendar.DAY_OF_MONTH,1);  
		ut.setTimeOut(cal.getTime());
		token = new String(en.encrypt(ut.getUserName() +"|"+ ut.getLoginDate()));
		setRedis(ut);
		return str[0];
	}
	
	protected Response<UserDetailResponse> getUserInfos(String account, String path) throws Exception{
		UserStruc userRequestData = new UserStruc();
		userRequestData.setAccount(account);
		//userRequestData.setAccount("suhern5");
		Response<UserDetailResponse> userDataResponse = null;
		try{
			userDataResponse = httpClient.invokeHttp(firefrogUrl+ path, userRequestData, new TypeReference<Response<UserDetailResponse>>() {
			});
		}catch(Exception e){
			throw new Exception("getUserInfos error:" + e);
		}
		return userDataResponse;
	}
}
