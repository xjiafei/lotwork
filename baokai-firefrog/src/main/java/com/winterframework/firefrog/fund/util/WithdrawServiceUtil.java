/**    
 *    
 * Copyright (c) 2013 by Richard.    
 *
 *      
 * @version 1.0    
 */
package com.winterframework.firefrog.fund.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDto;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.service.impl.FundWithdrawServiceImpl;
import com.winterframework.firefrog.fund.web.controller.vo.SeperateJsonClass;
import com.winterframework.firefrog.fund.web.dto.WithdrawApplyRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;

/**
 * 
 * 类功能说明: 对于Service层返回来的实体类按接口文件规定的格式进行转换。
 * 
 * <p>
 * Copyright: Copyright(c) 2013
 * </p>
 * 
 * @Version 1.0
 * 
 * 
 */
public class WithdrawServiceUtil {
	private static Logger log = LoggerFactory.getLogger(WithdrawServiceUtil.class);
	public static List<FundWithdrawOrder> seperateDrawList(User user, WithdrawApplyRequest applyRequest, 
			String sn,String seperaeCfg, WithdralDto wd ){
		List<FundWithdrawOrder> orders = new ArrayList<FundWithdrawOrder>();
		Long seperateCount = 1l;
		
		SeperateJsonClass sepJ=DataConverterUtil.convertJson2Object(seperaeCfg, SeperateJsonClass.class);
		Long withdrawAmt = applyRequest.getPreWithdrawAmt();
		if(withdrawAmt >= sepJ.getSeperateAmt()){
			seperateCount = withdrawAmt/sepJ.getSingleCut();
			if(withdrawAmt%sepJ.getSingleCut() != 0){
				seperateCount++;
			}
		}
		//set param
		String isSeperate = seperateCount > 1?"Y":"N";
		Long restAmt = withdrawAmt;
		for(int i=0;i<seperateCount;i++){
			log.info(" begin restAmt : " + restAmt);
			if(restAmt <= 0){
				break;
			}
			String realSn = sn;
			if("Y".equals(isSeperate)){
				realSn = realSn + (i+1);
			}
			FundWithdrawOrder order = new FundWithdrawOrder(FundModel.FD.CWXX.ITEMS.CWXX);
			User applyUser = new User();
			applyUser.setId(applyRequest.getUserId());
			UserProfile up = new UserProfile();
			up.setAccount(user.getAccount());
			applyUser.setUserProfile(up);
			order.setApplyUser(applyUser);
			Long writeAmt = sepJ.getSingleCut().longValue();
			
			restAmt = restAmt - writeAmt;
			if(i == (seperateCount-1)){
				writeAmt = withdrawAmt - (sepJ.getSingleCut().longValue()*(seperateCount-1));
			}else{
				if(restAmt < wd.getLowLimit()){
					writeAmt += restAmt;
					restAmt = 0l;
				}
			}
			
			
			order.setWithdrawAmt(writeAmt);
			order.setApplyTime(applyRequest.getApplyTime());
			FundBank bank = new FundBank();
			bank.setId(applyRequest.getBankId());
			order.setBank(bank);
			order.setApplyIpAddr(applyRequest.getIpAddr());
			order.setUserBankStruc(applyRequest.getUserBankStruc());
			order.setStauts(WithdrawStauts.APPLY);
			order.setSn(realSn);
			order.setRootSn(sn);
			order.setIsSeperate(isSeperate);
			orders.add(order);
		}
		return orders;
	}
}
