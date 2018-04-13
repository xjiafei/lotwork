/**   
* @Title: BankCardQueryResponse.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description:  查询绑定银行卡响应参数DTO 
* @author Denny  
* @date 2013-7-2 上午9:26:33 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.GameGoldDetailVO;


public class GameGoldDetailResponse {


	private List<GameGoldDetailVO> details;

	public List<GameGoldDetailVO> getDetails() {
		return details;
	}

	public void setDetails(List<GameGoldDetailVO> details) {
		this.details = details;
	}


}
