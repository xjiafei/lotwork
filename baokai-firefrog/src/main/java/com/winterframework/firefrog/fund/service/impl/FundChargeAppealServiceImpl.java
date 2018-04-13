package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.IFundChargeAppealDao;
import com.winterframework.firefrog.fund.dao.IFundChargeDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundChargeAppealVO;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeAppeal;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.service.IFundChargeAppealService;
import com.winterframework.firefrog.fund.util.SNUtil;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.dto.ChargeQueryRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealRequest;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealResponse;
import com.winterframework.firefrog.fund.web.dto.FundChargeAppealResult;
import com.winterframework.firefrog.fund.web.dto.FundRechargeAppealRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Pager;

@Service("fundChargeAppealServiceImpl")
public class FundChargeAppealServiceImpl implements IFundChargeAppealService {
	private static Logger log = LoggerFactory
			.getLogger(FundChargeAppealServiceImpl.class);
	
	@Resource(name = "SNUtil")
	private SNUtil snUtil;

	@Resource(name = "fundChargeAppealDaoImpl")
	private IFundChargeAppealDao fundChargeAppealDao;

	@Resource(name = "fundChargeDaoImpl")
	private IFundChargeDao fundChargeDao;

	@Resource(name = "fundBankDaoImpl")
	private IFundBankDao fundBankDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Override
	public FundChargeAppealResult queryCanAppealRechargeList(Long userId,
			Pager pager) throws Exception {
		PageRequest<ChargeQueryRequest> pageReqeust = PageRequest
				.getRestPageRequest(pager.getStartNo(), pager.getEndNo());
		ChargeQueryRequest searchDo = new ChargeQueryRequest();
		searchDo.setUserId(userId);
		searchDo.setStatus(new Long[] { 1L, 3L, 4L });
		Calendar cl = Calendar.getInstance();
		cl.setTime(new Date());
		cl.add(cl.DATE, -15); //查包含今天的前15日資料
		searchDo.setFromDate(cl.getTime());
		searchDo.setNotAppealStatus(new Long[] { 2L});//排除申訴審核通過
		pageReqeust.setSortColumns("APPLY_TIME DESC");
		pageReqeust.setSearchDo(searchDo);
		CountPage<FundChargeOrder> charges = fundChargeDao
				.queryFundCharge(pageReqeust);

		List<FundChargeOrder> list = charges.getResult();
		List<FundChargeAppealResponse> reps = new ArrayList<FundChargeAppealResponse>();
		for (FundChargeOrder chargeOrder : list) {
			FundChargeAppealResponse rep = new FundChargeAppealResponse();
			rep.setAccount(chargeOrder.getUserAct());
			BankCard card = chargeOrder.getRevCard();
			rep.setBankCardNumber(card.getBankCardNo());
			FundBank bank = fundBankDao.getById(chargeOrder.getPayBank()
					.getId());
			rep.setBankId(bank.getId().intValue());
			rep.setBankName(bank.getName());
			rep.setChargeAmt(chargeOrder.getPreChargeAmt());
			rep.setChargeMemo(chargeOrder.getMemo());
			rep.setChargeSn(chargeOrder.getSn());
			rep.setChargeStatus(Integer.parseInt(String.valueOf(chargeOrder
					.getStatus().getValue())));
			rep.setChargeTime(chargeOrder.getApplyTime());
			rep.setDepositeMode(chargeOrder.getDepositMode().intValue());
			rep.setUserId(userId);
			rep.setHasBeenAppeal(hasBeenAppeal(chargeOrder.getSn()));
			rep.setCanRechargeAppeal(bank.getCanRechargeAppeal());
			rep.setOtherCanRechargeAppeal(bank.getOtherCanRechargeAppeal());
			reps.add(rep);
		}
		FundChargeAppealResult result = new FundChargeAppealResult();
		result.setPager(pager);
		result.setResponses(reps);
		result.setTotalCount(charges.getTotalCount());
		return result;
	}

	private boolean hasBeenAppeal(String chargeSn) throws Exception {
		boolean hasBeenAppeal = false;
		FundChargeAppeal appealReq = new FundChargeAppeal();
		appealReq.setChargeSn(chargeSn);
		appealReq.setAppealStatus(FundChargeAppeal.Status.DOING.value());
		List<FundChargeAppealVO> appeals = fundChargeAppealDao
				.queryAppealList(appealReq);
		if (appeals != null && appeals.size() > 0) {
			hasBeenAppeal = true;
		}
		return hasBeenAppeal;

	}

	@Override
	public void addRechargeAppeal(FundChargeAppealRequest param)
			throws Exception {
		FundChargeAppeal request = new FundChargeAppeal();
		request.setAppealAcct(param.getAppealAcct());
		request.setAppealMemo(param.getAppealMemo());
		request.setAppealSn(snUtil.createAPlSn(FundModel.AP.ITEMS.SCZ, param.getUserId()));
		request.setAppealStatus(param.getAppealStatus());
		request.setReviewStartTime(param.getReviewStartTime());
		request.setArgueAcct(param.getArgueAcct());
		request.setArgueTime(param.getArgueTime());
		request.setBankCardNumber(param.getBankCardNumber());
		request.setBankId(param.getBankId());
		FundBank bank = fundBankDao.getById(param.getBankId().longValue());
		request.setBankName(bank.getName());
		request.setChargeAmt(param.getChargeAmt());
		request.setChargeSn(param.getChargeSn());
		request.setChargeTime(param.getChargeTime());
		request.setChargeMemo(param.getChargeMemo());
		request.setTenpayAccount(param.getTenpayAccount());
		request.setTenpayName(param.getTenpayName());
		request.setChargeUserName(param.getChargeUserName());
		request.setDepositeMode(param.getDepositeMode());
		request.setElectronicNumber(param.getElectronicNumber());
		request.setUploadImages(param.getUploadImages());
		request.setUserId(param.getUserId());
		request.setArgueTime(new Date());
		request.setArgueAcct(param.getUserAccount());
		request.setAppealStatus(FundChargeAppeal.Status.DOING.value());
		request.setTransactionNum(param.getTransactionNum());
		fundChargeAppealDao.insertAppeal(request);
	}

	@Override
	public List<FundChargeAppealVO> queryAppealRechargesByCondition(
			FundRechargeAppealRequest request, Pager pager) throws Exception {
		FundChargeAppeal appealReq = new FundChargeAppeal();
		appealReq.setAppealSn(request.getAppealSn());
		appealReq.setUserAccount(request.getAccount());

		if (null != request.getUserlvl())
			appealReq.setUserLvl(Integer.valueOf(request.getUserlvl()));

		appealReq.setAmtStart(request.getRefundAmtFrom());
		appealReq.setAmtEnd(request.getRefundAmtTo());
		appealReq.setArgueStartDate(request.getAppealTimeFrom());
		appealReq.setArgueEndDate(request.getAppealTimeTo());

		if (null != request.getIsvip())
			appealReq.setVipLvl(Integer.valueOf(request.getIsvip().toString()));

		if (null != request.getStatus())
			appealReq.setAppealStatus(Integer.valueOf(request.getStatus()));

		List<FundChargeAppealVO> list = fundChargeAppealDao
				.queryAppealList(appealReq);
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}
	
	public Long updateAppeal(FundChargeAppeal request) throws Exception { 
		return fundChargeAppealDao.updateAppeal(request);
	}
	
	public Long queryAppealCountsByStatus(int status) throws Exception {
		FundChargeAppeal vo = new FundChargeAppeal();
		vo.setAppealStatus(status);
		return fundChargeAppealDao.queryAppealCount(vo);
	}
 
}
