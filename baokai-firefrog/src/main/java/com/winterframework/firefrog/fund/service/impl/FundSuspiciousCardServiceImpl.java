/**   
* @Title: FundSuspiciousCardServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-25 上午10:20:50 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundSuspiciousCardDao;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.firefrog.fund.service.IFundSuspiciousCardService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: FundSuspiciousCardServiceImpl 
* @Description: 可疑银行卡操作Service impl
* @author Alan
* @date 2013-7-25 上午10:20:50 
*  
*/
@Service("fundSuspiciousCardServiceImpl")
public class FundSuspiciousCardServiceImpl implements IFundSuspiciousCardService {

	@Resource(name = "fundSuspiciousCardDaoImpl")
	private IFundSuspiciousCardDao fundSuspiciousCardDao;

	/**
	* Title: queryFundSuspiciousCardByPage
	* Description:
	* @param pageRequest
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundSuspiciousCardService#queryFundSuspiciousCardByPage(com.winterframework.modules.page.PageRequest) 
	*/
	@Override
	public Page<FundSuspiciousCard> queryFundSuspiciousCardByPage(PageRequest<?> pageRequest) {
		return fundSuspiciousCardDao.searchFundSuspiciousCardRecords(pageRequest);
	}

	/**
	* Title: addFundSuspiciousCard
	* Description:
	* @param fundSuspiciousCard 
	* @see com.winterframework.firefrog.fund.service.IFundSuspiciousCardService#addFundSuspiciousCard(com.winterframework.firefrog.fund.entity.FundSuspiciousCard) 
	*/
	@Override
	public void addFundSuspiciousCard(FundSuspiciousCard fundSuspiciousCard) {
		fundSuspiciousCardDao.addFundSuspiciousCard(fundSuspiciousCard);
	}

	/**
	* Title: deleteFundSuspiciousCard
	* Description:
	* @param id 
	* @see com.winterframework.firefrog.fund.service.IFundSuspiciousCardService#deleteFundSuspiciousCard(java.lang.Long) 
	*/
	@Override
	public void deleteFundSuspiciousCard(Long id) {
		fundSuspiciousCardDao.deleteFundSuspiciousCard(id);
	}

	/**
	* Title: isFundSuspiciousCard
	* Description:
	* @param cardNumber
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundSuspiciousCardService#isFundSuspiciousCard(java.lang.String) 
	*/
	@Override
	public Boolean isFundSuspiciousCard(String cardNumber) throws Exception {
		List<FundSuspiciousCard> list = fundSuspiciousCardDao.queryFundSuspiciousCards();
		for (FundSuspiciousCard card : list) {
			if (cardNumber.equals(card.getCardNumber().replace(" ", ""))) {
				return true;
			}
		}
		return false;
	}

	
	@Override
	public Boolean isFundSuspiciousCardAccount(Long userId) throws Exception {
		return fundSuspiciousCardDao.isSuspiciousCardUser(userId);
	}
}
