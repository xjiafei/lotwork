/**   
* @Title: WithdrawRiskIdentifierServiceImpl.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-12 下午1:39:26 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.common.util.IPConverter;
import com.winterframework.firefrog.config.web.controller.dto.WithDrawCheck;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.RiskType;
import com.winterframework.firefrog.fund.service.IFundSuspiciousCardService;
import com.winterframework.firefrog.fund.service.IWithdrawRiskIdentifierService;
import com.winterframework.firefrog.fund.web.controller.RedisKey;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.global.service.GlobalIpService;
import com.winterframework.firefrog.global.service.IGlobalGrayListService;
import com.winterframework.firefrog.user.dao.impl.UserLoginLogDaoImpl;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.modules.spring.exetend.PropertyConfig;

/** 
* @ClassName: WithdrawRiskIdentifierServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-12 下午1:39:26 
*  
*/
@Service("withdrawRiskIdentifierService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class WithdrawRiskIdentifierServiceImpl implements IWithdrawRiskIdentifierService {

	@Resource(name = "globalIpServiceImpl")
	private GlobalIpService globalIpService;

	@Resource(name = "fundSuspiciousCardServiceImpl")
	private IFundSuspiciousCardService fundSuspiciousCardService;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "rediskey_withdraw_times")
	private String keyWithdrawTimes;
	@Resource(name="userLoginLogDaoImpl")
	public UserLoginLogDaoImpl userLoginLogDaoImpl;
	
	@Resource(name = "globalGrayListServiceImpl")
	private IGlobalGrayListService globalGrayListService;

	/**
	* Title: queryRiskType
	* Description:
	* @param withdraw
	* @param count
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IWithdrawRiskIdentifierService#queryRiskType(com.winterframework.firefrog.fund.entity.FundWithdrawOrder, java.lang.Long) 
	*/
	private boolean isIpLimit(Long userId,Long ip){
		List<LoginLog> logs=userLoginLogDaoImpl.queryUserLoginLog(userId);
		if(logs!=null && logs.size()>1){
			return logs.get(1).getLoginIP().equals(ip);
		}
		return false;
	}
	@Override
	public RiskType queryRiskType(FundWithdrawOrder withdraw) throws Exception {
		
		UserBankStruc bankSruc = (UserBankStruc) DataConverterUtil.convertJson2Object(withdraw.getCardStr(),
				UserBankStruc.class);
		if (bankSruc != null && fundSuspiciousCardService.isFundSuspiciousCard(bankSruc.getBankNumber())) {
			return RiskType.BLACKLIST;
		}

		if (fundSuspiciousCardService.isFundSuspiciousCardAccount(withdraw.getApplyUser().getId())) {
			return RiskType.BLACKLIST;
		}
	/*	if (isIpLimit(withdraw.getApplyUser().getId(),(withdraw.getApplyIpAddr()))) {
			return RiskType.IPLIMIT;
		}*/

		String withDrawCheckStr = configService.getConfigValueByKey("fund", "withdrawCheck");
		WithDrawCheck withDrawCheck = (WithDrawCheck) DataConverterUtil.convertJson2Object(withDrawCheckStr,
				WithDrawCheck.class);
		Long grayRiskType = globalGrayListService.isGlobalGrayList(withdraw.getApplyUser().getId());
		
		if(grayRiskType!=0){
			switch (grayRiskType.intValue()) {
			case 6:
				return RiskType.GRAYLIST;
			case 7:
				return RiskType.GRAYTRANSFER;
			case 8:
				return RiskType.GRAYTRANSFERMANY;
			}
		}
		if (withdraw.getWithdrawAmt().longValue() >= withDrawCheck.getAmt()) {
			return RiskType.FUNDLIMIT;
		}
		String withdrawCountRedisKey = RedisKey.createDateKey(module, keyWithdrawTimes, withdraw.getApplyUser()
				.getId());
		String availWithdrawCount = redisSerive.get(withdrawCountRedisKey);
		Long count = 1l;
		if (StringUtils.isNotBlank(availWithdrawCount)) {
			String[] _value = availWithdrawCount.split("\\|");
			count = Long.parseLong(_value[0]) + 1;
		}
		if (count >= withDrawCheck.getTime()) {
			return RiskType.TIMESLIMIT;
		}
		
		if (withDrawCheck.getPart().intValue() == 1) {
			//如果是全部
			return RiskType.ALLCHECK;
		} 
		return RiskType.NONE;
	}

}
