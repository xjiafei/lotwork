package com.winterframework.firefrog.beginmession.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.beginmession.dao.BeginDataCountVODao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;
import com.winterframework.firefrog.beginmession.service.BeginDataCountService;

@Transactional
@Service
public class BeginDataCountServiceImpl implements BeginDataCountService {

	@Autowired
	private BeginDataCountVODao beginDataCountVODao;
	
	@Override
	public BeginDataCountVO getDataCount(Date startTime, Date endTime) {
		
		BeginDataCountVO vo = new BeginDataCountVO();
		
		String zero = new Integer(0).toString();
		
		//篩選期間註冊帳號數
		Long activityMissionCount = beginDataCountVODao.getMissionCount(startTime, endTime);
		if(activityMissionCount==null ||activityMissionCount==0){
			vo.setActivityMissionCount(zero);
		}else{
			vo.setActivityMissionCount(activityMissionCount.toString());
		}

		//篩選期間領取綁卡獎勵
		Long bindcardCount = beginDataCountVODao.getBindCardAward(startTime, endTime);
		if(bindcardCount!=null){
			vo.setBindcardCount(bindcardCount.toString());
		}else{
			vo.setBindcardCount(zero);
		}
		
		//完成充值帳戶
		Long chargeCount= beginDataCountVODao.getCharegCount(startTime, endTime);
		if(chargeCount!=null){
			vo.setChargeCount(chargeCount.toString());
		}else{
			vo.setChargeCount(zero);
		}
		
		//完成提現帳戶
		Long withDrawCount= beginDataCountVODao.getWithdrawCount(startTime, endTime);
		if(withDrawCount!=null){
			vo.setWithDrawCount(withDrawCount.toString());
		}else{
			vo.setWithDrawCount(zero);
		}
		
	

		if(activityMissionCount==0 || activityMissionCount==null ){
			vo.setChargeAmtPercen(zero);
			vo.setChargePercent(zero);
			vo.setWithdrawPercent(zero);
			vo.setBindcardPercent(zero);
			vo.setOrderAmtPercen(zero);
		}else{
			
			//綁卡比例
			if(bindcardCount==null){
				vo.setBindcardPercent(zero);
			}else{
				Double bindcardPercent = (new Double(bindcardCount)/new Double(activityMissionCount))*100;
				vo.setBindcardPercent(new BigDecimal(bindcardPercent).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			if(chargeCount==null){
				vo.setChargePercent(zero);
			}else{
				//首充比例
				Double chargePercent = (new Double(chargeCount)/new Double(activityMissionCount))*100;				
				vo.setChargePercent(new BigDecimal(chargePercent).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			
			if(withDrawCount==null){
				vo.setWithdrawPercent(zero);
			}else{
				//提現比例
				Double withdrawPercent = (new Double(withDrawCount)/new Double(activityMissionCount))*100;				
				vo.setWithdrawPercent(new BigDecimal(withdrawPercent).setScale(2, BigDecimal.ROUND_HALF_UP).toString());				
			}

			//同期首存金額
			Double totalChargeAmt = beginDataCountVODao.getTotalCharegAmt(startTime, endTime);
			if(totalChargeAmt==null){
				vo.setChargeAmtPercen(zero);
			}else{
				Double chargeAmtPercen= (totalChargeAmt/new Double(chargeCount));			
				vo.setChargeAmtPercen(new BigDecimal(chargeAmtPercen).setScale(2, BigDecimal.ROUND_HALF_UP).toString());				
			}
			
			//同期投注金額
			Double orderAmt =beginDataCountVODao.getTotalOrderAmt(startTime, endTime);
			if(orderAmt==null){
				vo.setOrderAmtPercen(zero);
			}else{
				//投注均額
				Double orderAmtPercen= (orderAmt/new Double(activityMissionCount));		
				vo.setOrderAmtPercen(new BigDecimal(orderAmtPercen).setScale(2, BigDecimal.ROUND_HALF_UP).toString());				
			}
		}
		
		//同期抽獎人數
		Long lotteryPersion= beginDataCountVODao.getAwardLotteryCount(startTime, endTime);
		if(lotteryPersion==null){
			vo.setLotteryPersion(zero);
		}else{
			vo.setLotteryPersion(lotteryPersion.toString());
		}
		
		//同期抽獎金額
		Long totalLotteryAmt= beginDataCountVODao.getAwardTotalAmt(startTime, endTime);
		if(totalLotteryAmt==null){
			vo.setTotalLotteryAmt(zero);
		}else{
			vo.setTotalLotteryAmt(totalLotteryAmt.toString());
		}
			
		//同期開獎次數
		Long totalLotteryTime= beginDataCountVODao.getAwardLotteryTime(startTime, endTime);
		if(totalLotteryTime==null){
			vo.setTotalLotteryTime(zero);
		}else{
			vo.setTotalLotteryTime(totalLotteryTime.toString());
		}
		
		return vo;
	}

}
