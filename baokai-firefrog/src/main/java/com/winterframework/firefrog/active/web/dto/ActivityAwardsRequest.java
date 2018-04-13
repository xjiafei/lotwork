package com.winterframework.firefrog.active.web.dto;

import java.io.Serializable;
import java.util.List;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityAwardsRequest implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1017310155712873878L;
	
	
	private Long actId;
	private String actDate;
	private List<String[]> awardList;
	

	public Long getActId() {
		return actId;
	}
	public void setActId(Long actId) {
		this.actId = actId;
	}
	public String getActDate() {
		return actDate;
	}
	public void setActDate(String actDate) {
		this.actDate = actDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<String[]> getAwardList() {
		return awardList;
	}
	public void setAwardList(List<String[]> awardList) {
		this.awardList = awardList;
	}
	


	
	

}
