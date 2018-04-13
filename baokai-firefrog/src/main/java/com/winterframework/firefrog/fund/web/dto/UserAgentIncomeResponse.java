package com.winterframework.firefrog.fund.web.dto;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;

/** 
* @ClassName UserAgentIncomeResponse 
* @Description 总代盈亏 
* @author  hugh
* @date 2014年9月25日 上午11:31:51 
*  
*/
public class UserAgentIncomeResponse {
	private List<UserAgentIncomeVO> vos;
	public List<UserAgentIncomeVO> getVos() {
		return vos;
	}

	public void setVos(List<UserAgentIncomeVO> vos) {
		this.vos = vos;
	}


}

