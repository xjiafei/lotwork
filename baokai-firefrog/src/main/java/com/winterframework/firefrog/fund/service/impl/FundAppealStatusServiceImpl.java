package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundAppealStatusDao;
import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundAppealStatusVO;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.FundAppealStatus;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.service.IFundAppealStatusService;
import com.winterframework.firefrog.fund.web.dto.FundAppealStatusRequest;
import com.winterframework.firefrog.fund.web.dto.FundAppealStatusResponse;
import com.winterframework.firefrog.fund.web.dto.FundRechargeStrucResponse;
import com.winterframework.modules.web.jsonresult.Pager;

@Service("fundAppealStatusServiceImpl")
public class FundAppealStatusServiceImpl implements IFundAppealStatusService {
	private static Logger log = LoggerFactory.getLogger(FundAppealStatusServiceImpl.class);
	@Resource(name = "fundAppealStatusDaoImpl")
	private IFundAppealStatusDao fundAppealStatusDao;
	
	@Resource(name = "fundChargeDaoImpl")
	private IFundChargeDao fundChargeDao;
	
	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;


	@Override
	public Long queryFundAppealCount(FundAppealStatusRequest request)
			throws Exception {
		FundAppealStatus vo = new FundAppealStatus();
		vo.setUserId(request.getUserId());
		vo.setAppealSn(request.getAppealSn());
		vo.setFundSn(request.getFundSn());
		vo.setStatus(request.getStatus());
		vo.setStartDate(request.getStartDate());
		vo.setEndDate(request.getEndDate());
		return fundAppealStatusDao.queryFundAppealCount(vo);
	}

	@Override
	public List<FundAppealStatusResponse> queryFundAppealList(
			FundAppealStatusRequest request, Pager pager) throws Exception {
		List<FundAppealStatusResponse> results = new ArrayList<FundAppealStatusResponse>();
		FundAppealStatus vo = new FundAppealStatus();
		vo.setUserId(request.getUserId());
		vo.setAppealSn(request.getAppealSn());
		vo.setFundSn(request.getFundSn());
		vo.setStatus(request.getStatus());
		vo.setStartDate(request.getStartDate());
		vo.setEndDate(request.getEndDate());
		vo.setStartNo(pager.getStartNo());
		vo.setEndNo(pager.getEndNo());

		List<FundAppealStatusVO> appeals = fundAppealStatusDao
				.queryFundAppealList(vo);

		for (FundAppealStatusVO appeal : appeals) {
			FundAppealStatusResponse result = new FundAppealStatusResponse();
			result.setAccount(appeal.getAccount());
			result.setAppealCreator(appeal.getAppealCreator());
			result.setAppealSn(appeal.getAppealSn());
			result.setAppealTime(appeal.getAppealTime());
			result.setArgueTime(appeal.getArgueTime());
			result.setFundAmt(appeal.getFundAmt());
			result.setFundCard(appeal.getFundCard());
			result.setFundCardUser(appeal.getFundCardUser());
			result.setFundSn(appeal.getFundSn());
			result.setFundTime(appeal.getFundTime());
			result.setMemo(appeal.getMemo());
			result.setStatus(appeal.getStatus());
			result.setType(appeal.getType());
			result.setUserId(appeal.getUserId());
			result.setTenpayAccount(appeal.getTenpayAccount());
			result.setTenpayName(appeal.getTenpayName());
			result.setDepositeMode(appeal.getDepositeMode());
			result.setBankName(appeal.getBankName());
			result.setChargeMemo(appeal.getChargeMemo());
			result.setIsSeperate(appeal.getIsSeperate());
			if("Y".equals(appeal.getIsSeperate())){
				log.info("appeal.getWithdrawSn()      ="+appeal.getFundSn());
				List<FundWithdrawOrder> subDraws = fundWithdrawDao.queryByRootSn(appeal.getFundSn());
				result.setSubDraws(subDraws);
			}
			results.add(result);
		}
		return results;
	}
	
	public FundRechargeStrucResponse queryFundAppeal(String appealSn) throws Exception{
		log.debug("--------------------queryFundAppeal service");
		
		FundChargeAppealVO vo = fundAppealStatusDao.queryFundAppeal(appealSn);
		if(null == vo){
			return null;
		}
		log.debug("--------------------before queryByOrderNum");
		FundRechargeStrucResponse result = new FundRechargeStrucResponse();
		log.debug("------------------vo AppealSn: " + vo.getAppealSn());
		log.debug("------------------vo.: " + vo.getAppealAcct());
		log.debug("------------------vo.: " + vo.getAppealMemo());
		log.debug("------------------vo.: " + vo.getBankCardNumber());
		log.debug("------------------vo ChargeSn : " + vo.getChargeSn());
		log.debug("------------------vo.: " + vo.getChargeUserName());
		log.debug("------------------vo.: " + vo.getGmtModifiedString());
		if(null == vo.getChargeSn()){
			result.setFundChargeAppealVO(vo);
			result.setFundChargeOrder(null);
		}else{
			FundChargeOrder fc = fundChargeDao.queryByOrderNum(vo.getChargeSn());
			log.debug("--------------------after queryByOrderNum");
			result.setFundChargeAppealVO(vo);
			result.setFundChargeOrder(fc);
		}
		return result;
	}

}
