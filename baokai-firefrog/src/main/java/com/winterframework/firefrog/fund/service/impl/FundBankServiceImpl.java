/**   
* @Title: FundBankServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午10:57:07 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundBankWithSub;
import com.winterframework.firefrog.fund.service.IFundBankService;
import com.winterframework.firefrog.fund.web.dto.DTOConverter;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;

/** 
* @ClassName: FundBankServiceImpl 
* @Description: 银行管理 ServiceImpl
* @author Alan
* @date 2013-7-1 上午10:57:07 
*  
*/
@Service("fundBankServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FundBankServiceImpl implements IFundBankService {

	@Resource(name = "fundBankDaoImpl")
	private IFundBankDao fundBankDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDaoImpl;
	
	private static Logger logger = LoggerFactory.getLogger(FundBankServiceImpl.class);

	/**
	* Title: queryAllBank
	* Description:
	* @return 
	* @see com.winterframework.firefrog.fund.service.IFundBankService#queryAllBank() 
	*/
	@Override
	public List<FundBank> queryAllBank() throws Exception {
		return fundBankDao.queryAllBank();
	}

	@Override
	public void updateBank(FundBank bank) throws Exception {
		fundBankDao.updateBank(bank);
	}

	@Override
	public void saveBank(FundBank bank) throws Exception {
		fundBankDao.saveBank(bank);
	}

	@Override
	public Boolean checkChargeOpen(Long bankId, Long userId, Long mode) throws Exception {
		List<FundBank> banks = fundBankDao.queryByCode(bankId);
		UserCustomer user = userCustomerDaoImpl.getById(userId);
		Long vipLvl = user.getVipLvl();
		Boolean isOpen = false;
		//一般用戶
		switch (mode.intValue()) {
		case 0:// WEB
			if (vipLvl == 0) {
				if(banks.get(0).getCode().equals("30")){
					// 判斷個人版或企業版的普通用戶是否可充值
					if (banks.get(0).getNormalOpen() == 1 || (banks.size()==2 && banks.get(1).getNormalOpen() == 1)) {
						isOpen = true;
					}
				} else{
					if (banks.get(0).getNormalOpen() == 1){
						isOpen = true;
					}
				}
				
			} else {
				if(banks.get(0).getCode().equals("30")){
					// 判斷個人版或企業版的vip用戶是否可充值
					if (banks.get(0).getVipOpen() == 1 || banks.get(1).getVipOpen() == 1) {
						isOpen = true;
					}
				} else{
					if (banks.get(0).getVipOpen() == 1){
						isOpen = true;
					}
				}
				
			}
			break;
		case 1:// Mobile
			if (vipLvl == 0) {
				isOpen = banks.get(0).getMoveNormalOpen() == 1 ? true : false;
			} else {
				isOpen = banks.get(0).getMoveVipOpen() == 1 ? true : false;
			}
			break;
		default:
			logger.debug("checkChargeOpen has some problem,mode="+mode.intValue()+
					",bankId="+bankId.intValue()+",userId="+userId.intValue());
		}

		return isOpen;
	}
}
