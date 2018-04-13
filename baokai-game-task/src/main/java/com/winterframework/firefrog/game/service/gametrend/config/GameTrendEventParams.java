package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import com.winterframework.modules.web.util.JsonMapper;

/** 
* @ClassName GameTrendEventParams 
* @Description 走势图事件参数 
* @author  hugh
* @date 2014年9月5日 上午11:35:27 
*  
*/
public class GameTrendEventParams {
	@NotNull
	private Long lotteryId;
	@NotNull
	private Long issueCode;
	private int type = 0; //类型  0：增量 1：全量
	@NotNull
	private String numberRecord; //开奖号码	
	
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getIssueCode() {
		return issueCode;
	}
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNumberRecord() {
		return numberRecord;
	}
	public void setNumberRecord(String numberRecord) {
		this.numberRecord = numberRecord;
	}
	
	public enum TrendEventTypeEnum{
		ADD(0),ALL(1);
		int code;
		TrendEventTypeEnum(int code){
			this.code = code;
		}
		public int getCode(){
			return this.code;		
		}
	}

	public GameTrendEventParams fromJson(String json) throws Exception{
		GameTrendEventParams params ;
		try {
			params = JsonMapper.nonEmptyMapper().fromJson(json, GameTrendEventParams.class);		
		
		} catch (Exception e) {
			String message = "走势图解析参数出错" + json;
			throw new Exception(message);
		}

       Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
       Set<ConstraintViolation<GameTrendEventParams>> constraintViolations = validator.validate( params );
       if(constraintViolations.size() > 0){
    	   String message = "走势图解析参数不完整 " + constraintViolations.iterator().next().getMessage() + " json = " + json;
		   throw new Exception(message); 
       }
       
       return params;
	}
}
