package com.winterframework.firefrog.shortlived.sheepactivity.dto;

/** 
* @ClassName ActivitySheepHongBaoDetail 
* @Description 红包详情 
* @author  hugh
* @date 2015年2月4日 上午10:54:26 
*  
*/
public class ActivitySheepHongBaoDetail {
	
	private String date;
	private Long award3;
	private Long award4;
	private Long id; //for query
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getAward3() {
		return award3;
	}
	public void setAward3(Long award3) {
		this.award3 = award3;
	}
	public Long getAward4() {
		return award4;
	}
	public void setAward4(Long award4) {
		this.award4 = award4;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
