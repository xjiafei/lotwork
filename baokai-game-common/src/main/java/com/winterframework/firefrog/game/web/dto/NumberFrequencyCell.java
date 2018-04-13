package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: NumberFrequencyCell 
* @Description: 
* @author Denny 
* @date 2013-10-14 下午7:59:02 
*  
*/
public class NumberFrequencyCell implements Serializable {

	private static final long serialVersionUID = -6434542299538051646L;

	private Integer currentNum;
	private Integer pinlv;

	public Integer getCurrentNum() {
		return currentNum;
	}

	public void setCurrentNum(Integer currentNum) {
		this.currentNum = currentNum;
	}

	public Integer getPinlv() {
		return pinlv;
	}

	public void setPinlv(Integer pinlv) {
		this.pinlv = pinlv;
	}

}
