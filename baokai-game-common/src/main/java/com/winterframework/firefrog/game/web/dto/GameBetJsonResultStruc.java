package com.winterframework.firefrog.game.web.dto;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: HandlingChargeJsonResult 
* @Description: 投注模块json数据返回结构，根据已提供的js数据处理结构构造 
* @author david 
* @date 2013-9-27 下午2:14:48 
*  
*/
public class GameBetJsonResultStruc {
    /**操作成功表示1 成功，0失败*/
	private Integer isSuccess;
	/**操作提示*/
	private String msg;
	/**操作类型*/
	private String type;
	/**返回用戶餘額*/
	private double balance;
	/**返回数据结构*/
	private GameBetJsonDateStruc data;
		
	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Integer getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(Integer isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public GameBetJsonDateStruc getData() {
		return data;
	}

	public void setData(GameBetJsonDateStruc data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
