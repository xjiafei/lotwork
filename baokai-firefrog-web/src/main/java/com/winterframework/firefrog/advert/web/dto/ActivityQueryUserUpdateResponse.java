package com.winterframework.firefrog.advert.web.dto;

import java.io.Serializable;



/** 
* @ClassName ActivityUserUpdateResponse 
* @Description 用户升级系统 
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityQueryUserUpdateResponse implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 573925279415801319L;
	private Long isUpdate = 0L;
	private Long isRecharge = 0L;
	private Long isBet= 0L;
	public Long getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(Long isUpdate) {
		this.isUpdate = isUpdate;
	}
	public Long getIsRecharge() {
		return isRecharge;
	}
	public void setIsRecharge(Long isRecharge) {
		this.isRecharge = isRecharge;
	}
	public Long getIsBet() {
		return isBet;
	}
	public void setIsBet(Long isBet) {
		this.isBet = isBet;
	}


}
