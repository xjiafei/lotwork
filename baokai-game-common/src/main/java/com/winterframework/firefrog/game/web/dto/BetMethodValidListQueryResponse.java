package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;


/** 
* @ClassName: BetMethodValidListQueryResponse 
* @Description: 有效彩种玩法显示,查询出彩种有效的玩法，便于投注页面显示
* @author david 
* @date 2014-3-21 下午1:22:07 
*  
*/
public class BetMethodValidListQueryResponse implements Serializable {

	private static final long serialVersionUID = -4709516554522930287L;

	private String[] validMethods;

	public String[] getValidMethods() {
		return validMethods;
	}

	public void setValidMethods(String[] validMethods) {
		this.validMethods = validMethods;
	}
	
}
