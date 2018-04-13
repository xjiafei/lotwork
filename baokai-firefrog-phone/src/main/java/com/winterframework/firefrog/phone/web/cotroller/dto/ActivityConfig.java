package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/** 
* @ClassName ActivityAwardConfig 
* @Description 活动奖品配置表
* @author  hugh
* @date 2014年11月24日 下午4:17:12 
*  
*/
public class ActivityConfig extends BaseEntity{


//	 	"ID" NUMBER(20,0) NOT NULL ENABLE, 
//		"BEGIN_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE, 
//		"END_TIME" TIMESTAMP (3) DEFAULT sysdate NOT NULL ENABLE, 
//		"MAX_PRIZE" FLOAT(126) DEFAULT 0 NOT NULL ENABLE, 
//		"MIN_PRIZE" FLOAT(126) DEFAULT 0 NOT NULL ENABLE, 
//		"MEMO" VARCHAR2(256 BYTE), 
//		PRIMARY KEY ("ID")
	 
	
	
	private static final long serialVersionUID = -2145043309571048091L;
	
	private Long id;
	private Date beginTime;
	private Date endTime;
	private Float maxPrize;
	private Float minPrize;
	private String memo;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Float getMaxPrize() {
		return maxPrize;
	}
	public void setMaxPrize(Float maxPrize) {
		this.maxPrize = maxPrize;
	}
	public Float getMinPrize() {
		return minPrize;
	}
	public void setMinPrize(Float minPrize) {
		this.minPrize = minPrize;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
	
}
