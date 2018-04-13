/**   
* @Title: FundChangeServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl.change 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-16 下午1:41:58 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl.change;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.dao.IFundChangeLogDao;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.entity.UserFundChangeLog;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.user.entity.BaseUser;

/** 
* @ClassName: FundChangeServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-16 下午1:41:58 
*  
*/
@Service
public class FundChangeLog2Service {

	private static final Logger logger = LoggerFactory.getLogger(FundChangeLog2Service.class);

	@Resource(name = "fundChangeLogDaoImpl")
	private IFundChangeLogDao fundChangeLogDao;

	@Resource(name = "SNUtilFund")
	private ISNGenerator sNUtil; 



	

	/** 
	* @Title: logFundChange 
	* @Description:资金变动记录日志保存
	* @param beforeFund
	* @param afterFund
	* @param fundOrder
	*/
	public String logFundChange(UserFund beforeFund, UserFund afterFund, String sn, BaseUser user, EnumItem item,
			Long isVisiblebyFrontUser, String exCode, String planCode, String note) throws Exception {
		UserFundChangeLog userFundChangeLog = new UserFundChangeLog();
		userFundChangeLog.setBeforBal(Long.valueOf(beforeFund.getBal()));
		userFundChangeLog.setPlanCode(planCode);
		userFundChangeLog.setNote(note);
		userFundChangeLog.setBeforeDamt(Long.valueOf(beforeFund.getFrozenAmt()));
		userFundChangeLog.setBeforeAvailBal(beforeFund.getDisableAmt());
		userFundChangeLog.setCtAvailBal(afterFund.getDisableAmt());
		userFundChangeLog.setCtBal(Long.valueOf(afterFund.getBal()));
		userFundChangeLog.setCtDamt(Long.valueOf(afterFund.getFrozenAmt()));
		UserFund userFund = new UserFund();
		userFund.setId(beforeFund.getId());
		userFundChangeLog.setFund(userFund);
		userFundChangeLog.setExCode(exCode);
		userFundChangeLog.setOperator(user.getId());//系统更改申请人id为0
		userFundChangeLog.setIsAclUser(user.isFrontUser() == true ? 0l : 1l);
		userFundChangeLog.setIsVisiblebyFrontUser(isVisiblebyFrontUser);

		userFundChangeLog.setUserId(beforeFund.getUser().getId());
		userFundChangeLog.setReason(item.getModel().getCode() + "," + item.getSummaryCode() + "," + item.getTradeCode()
				+ "," + item.getTradeStatus());
		userFundChangeLog.setFundSn(sNUtil.createFundSn(item, beforeFund.getUser().getId()));
		if (StringUtils.isEmpty(sn)) {
			sn = sNUtil.createBusinessSnForNull(item, beforeFund.getUser().getId());
		}
		userFundChangeLog.setSn(sn);
		fundChangeLogDao.saveFundChangeLog(userFundChangeLog);
		return sn;

	}
}

