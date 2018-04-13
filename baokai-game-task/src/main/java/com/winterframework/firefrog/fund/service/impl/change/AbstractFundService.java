package com.winterframework.firefrog.fund.service.impl.change;

import javax.annotation.Resource;

import com.winterframework.firefrog.fund.dao.IFundDao;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundService;
import com.winterframework.modules.spring.exetend.PropertyConfig;


public abstract class AbstractFundService<T extends FundOrder> implements IFundService<T> {

	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;
	
	@Resource(name = "fundDaoImpl")
	protected IFundDao fundDao;
	 
	@PropertyConfig(value = "mownum_url")
	protected String mowUrl;
	
		

	public IFundAtomicOperationService getFundChangeService() {
		return fundChangeService;
	}

	public void setFundChangeService(IFundAtomicOperationService fundChangeService) {
		this.fundChangeService = fundChangeService;
	}

	@Override
	public UserFund getUserFund(Long userId) {
		return fundChangeService.getUserFund(userId);
	} 
	
	public Long getTeamFund(Long userId){
		return fundDao.getTeamFund(userId);
	};
	/** 
	* @Title: updateFund 
	* @Description: 更改用户资金信息 
	* @param fundOrder
	* @throws FundChangedExcetion
	*/

}