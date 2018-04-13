/**   
* @Title: DTOCoverter.java 
* @Package com.winterframework.firefrog.fund.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 下午1:56:08 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundAdjustOrder;
import com.winterframework.firefrog.fund.entity.FundChargeException;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard.CardType;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.entity.WithdrawAppeal;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.service.impl.ddb.DDBPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.din.DINPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.hb.HBPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.huayin.HUAYINPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.ChargeConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.ECPSSConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.THConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.mow.WORTHConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.sp.SPPayConfirmRequest;
import com.winterframework.firefrog.fund.service.impl.wft.WFTPayConfirmRequest;
import com.winterframework.firefrog.fund.util.MowNumTool;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.firefrog.fund.web.controller.charge.Status;
import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.subsys.web.dto.SubSysFundChangeLogRequestDTO;
import com.winterframework.firefrog.subsys.web.dto.SubSysTransferRequestDTO;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.web.jsonresult.Request;

/** 
* @ClassName: DTOCoverter 
* @Description: DTOCoverter 
* @author 你的名字 
* @date 2013-7-1 下午1:56:08 
*  
*/
public class DTOConverter {
	
	private static Long getLongVal(Boolean ori) {
		if (ori == null) {
			return null;
		}
		return (ori != null && ori.equals(true)) ? 1L : 0L;
	}

	private static Boolean getBoolVal(Long ori) {
		if (ori == null) {
			return null;
		}
		return (ori != null && Long.valueOf(1).equals(ori)) ? true : false;
	}

	public static FundChargeMCOrder tranChargeConfirmReq2MCOrder(ChargeConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();

		mcOrder.setFee(MowNumTool.fromMow(chargeConfirmRequest.getFee()));
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getMownecum_order_num());
		mcOrder.setChannel(chargeConfirmRequest.getChannel());
		mcOrder.setArea(chargeConfirmRequest.getArea());
		mcOrder.setBankFee(MowNumTool.fromMow(chargeConfirmRequest.getFee()));
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getAmount()));
		mcOrder.setMcBankFee(MowNumTool.fromMow(chargeConfirmRequest.getTransaction_charge()));
		return mcOrder;
	}
	
	public static FundChargeMCOrder tranTHChargeConfirmReq2MCOrder(THConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getTrade_no());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getOrder_amount()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	
	public static FundChargeMCOrder tranECPSSConfirmReq2MCOrder(ECPSSConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getBillNo());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getAmount()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	
	public static FundChargeMCOrder tranHBConfirmReq2MCOrder(HBPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		Double parseAmt = Double.parseDouble(chargeConfirmRequest.getAmount())/100;
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrderId());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(parseAmt.toString()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeMCOrder tranSPConfirmReq2MCOrder(SPPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		Double parseAmt = Double.parseDouble(chargeConfirmRequest.getTXNAMT())/100;
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getORDERNO());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(parseAmt.toString()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeMCOrder tranDDBConfirmReq2MCOrder(DDBPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrder_no());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getOrder_amount()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeMCOrder tranWFTConfirmReq2MCOrder(WFTPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrdernumber());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getPaymoney()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeMCOrder tranDINConfirmReq2MCOrder(DINPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrder_no());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getOrder_amount()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeMCOrder tranHUAYINConfirmReq2MCOrder(HUAYINPayConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrder_no());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getOrder_amount()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	
	public static FundChargeMCOrder tranWORTHConfirmReq2MCOrder(WORTHConfirmRequest chargeConfirmRequest) {
		FundChargeMCOrder mcOrder = new FundChargeMCOrder();
		mcOrder.setFee(0L);
		mcOrder.setNoticeTime(new Date());
		mcOrder.setSn(chargeConfirmRequest.getOrder_no());
		mcOrder.setChannel("");
		mcOrder.setArea("");
		mcOrder.setBankFee(0L);
		mcOrder.setAmount(MowNumTool.fromMow(chargeConfirmRequest.getTotal_fee()));
		mcOrder.setMcBankFee(0L);
		return mcOrder;
	}
	public static FundChargeOrder tranChargeApplyReq2Order(ChargeApplyRequest chargeApplyRequest) {
		FundChargeOrder order = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.AUTO);
		order.setDepositMode(chargeApplyRequest.getDepositMode());
		order.setApplyTime(chargeApplyRequest.getApplyTime());
		order.setMemo(chargeApplyRequest.getMemo());
		FundBank payBank = new FundBank();
		payBank.setId(chargeApplyRequest.getBankId());
		order.setPayBank(payBank);
		order.setPreChargeAmt(chargeApplyRequest.getPreChargeAmt());
		order.setPlatfom(chargeApplyRequest.getPlatfom());
		order.setVer(chargeApplyRequest.getVer());
		BankCard payCard = new BankCard();
		payCard.setBankCardNo(chargeApplyRequest.getBankNumber());
		payCard.setAccountHolder(chargeApplyRequest.getBankAccount());
		order.setPayCard(payCard);
		order.setBankNumber(chargeApplyRequest.getBankNumber());
		order.setBankAccount(chargeApplyRequest.getBankAccount());
		order.setNickName(chargeApplyRequest.getNickName());
		order.setChargeVersion(chargeApplyRequest.getChargeVersion());
		
		return order;
	}

	/** 
	* @Title: fundTransferRequestDTO2UserFund 
	* @Description: 转账实体转换方法 
	* @param request
	* @return
	*/
	public static FundTransferOrder fundTransferRequestDTO2UserFund(Request<FundTransferRequestDTO> request) {
	
		FundTransferOrder fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.SOSX);
		FundTransferRequestDTO fundTransferRequestDTO = request.getBody().getParam();
		fundTransferOrder.setApplyTime(DataConverterUtil.convertLong2Date(fundTransferRequestDTO.getApplyTime()));
		User applyUser = new User();
		applyUser.setId(fundTransferRequestDTO.getUserId());
		UserProfile up = new UserProfile();
		up.setUserChain(request.getHead().getUserAccount());
		up.setAccount(AccountTool.getRealAccount(up.getUserChain()));
		applyUser.setUserProfile(up);
		fundTransferOrder.setApplyUser(applyUser);
		fundTransferOrder.setIsUpward(fundTransferRequestDTO.getIsUpward());
		User receiveUser = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(fundTransferRequestDTO.getRcvAccount());
		receiveUser.setId(fundTransferRequestDTO.getRcvUserId());
		receiveUser.setUserProfile(userProfile);
		fundTransferOrder.setReceiveUser(receiveUser);
		fundTransferOrder.setUserChain(applyUser.getUserProfile().getUserChain());
		fundTransferOrder.setTransferAmt(fundTransferRequestDTO.getTransferAmt());

		return fundTransferOrder;
	}

	
	/** 
	* @Title: fundTransferRequestDTO2UserFund 
	* @Description: 转账实体转换方法 
	* @param request
	* @return
	*/
	public static FundTransferOrder fundTransferRequestDTOSubSystem(Request<SubSysTransferRequestDTO> request, int direct) {
	
		FundTransferOrder fundTransferOrder = null;
		switch (direct)
		{
		case 0:
		case 2:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TFTX);
			break;
		case 6:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TFTG);
			break;
		case 1:
		case 3:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TXTM);
			break;
		case 7:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TGTM);
			break;
		case 8:
			fundTransferOrder = new FundTransferOrder(FundModel.GM.RSXX.ITEMS.RDWX);
			break;
		case 9:
			fundTransferOrder = new FundTransferOrder(FundModel.GM.RSXX.ITEMS.RDXX);
			break;
		}
		
		SubSysTransferRequestDTO fundTransferRequestDTO = request.getBody().getParam();
		fundTransferOrder.setApplyTime(DataConverterUtil.convertLong2Date(fundTransferRequestDTO.getApplyTime()));
		User applyUser = new User();
		applyUser.setId(fundTransferRequestDTO.getUserId());
		UserProfile up = new UserProfile();
		up.setUserChain(request.getHead().getUserAccount());
		up.setAccount(AccountTool.getRealAccount(up.getUserChain()));
		applyUser.setUserProfile(up);
		fundTransferOrder.setApplyUser(applyUser);
		fundTransferOrder.setIsUpward(2L);
		User receiveUser = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(fundTransferRequestDTO.getRcvAccount());
		receiveUser.setId(fundTransferRequestDTO.getUserId());
		receiveUser.setUserProfile(userProfile);
		fundTransferOrder.setReceiveUser(receiveUser);
		fundTransferOrder.setUserChain("");
		fundTransferOrder.setTransferAmt(fundTransferRequestDTO.getTransferAmt());
		fundTransferOrder.setAmountBal(fundTransferRequestDTO.getAmountBal());
		fundTransferOrder.setNote(fundTransferRequestDTO.getRetDate());
		
		return fundTransferOrder;
	}

	/**
	 * @Title: fundTransferRequestDTO2UserFund
	 * @Description: 转账实体转换方法 --活動禮金
	 * @param request
	 * @return
	 */
	public static FundTransferOrder activityGiftRequestDTOSubSystem(
			String account,Long amount,String note, int direct,String sn) {

		FundTransferOrder fundTransferOrder = null;
		switch (direct) {
		case 0:
		case 2:
			fundTransferOrder = new FundTransferOrder(
					FundModel.TF.TAIX.ITEMS.TFTX);
			break;
		case 6:
			fundTransferOrder = new FundTransferOrder(
					FundModel.TF.TAIX.ITEMS.TFTG);
			break;
		case 1:
		case 3:
			fundTransferOrder = new FundTransferOrder(
					FundModel.TF.TAIX.ITEMS.TXTM);
			break;
		case 7:
			fundTransferOrder = new FundTransferOrder(
					FundModel.TF.TAIX.ITEMS.TGTM);
			break;
		case 8:
			fundTransferOrder = new FundTransferOrder(
					FundModel.GM.RSXX.ITEMS.RDWX);
			break;
		case 9:
			fundTransferOrder = new FundTransferOrder(
					FundModel.GM.RSXX.ITEMS.RDXX);
			break;
		case 10:
			fundTransferOrder = new FundTransferOrder(
					FundModel.PM.PGFX.ITEMS.PGFX);
		}

		//SubSysActivityGiftRequestDTO fundTransferRequestDTO = request.getBody().getParam();
		fundTransferOrder.setApplyTime(new Date());
		fundTransferOrder.setSn(sn);
		User applyUser = new User();
//		UserProfile up = new UserProfile();
//		up.setUserChain(request.getHead().getUserAccount());
//		up.setAccount(AccountTool.getRealAccount(up.getUserChain()));
//		applyUser.setUserProfile(up);
		fundTransferOrder.setApplyUser(applyUser);
		fundTransferOrder.setIsUpward(2L);
		User receiveUser = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(account);
//		receiveUser.setId(userId);
		receiveUser.setUserProfile(userProfile);
		fundTransferOrder.setReceiveUser(receiveUser);
		fundTransferOrder.setUserChain("");
		fundTransferOrder.setTransferAmt(amount);
//		fundTransferOrder.setAmountBal(fundTransferRequestDTO.getAmountBal());
		fundTransferOrder.setNote(note);
		return fundTransferOrder;
	}

	public static FundLogReq fundChangeRequestDTOSubSystem(
			Request<SubSysFundChangeLogRequestDTO> request) {

		FundLogReq fundLogReq = new FundLogReq();
		fundLogReq.setUserId(request.getBody().getParam().getUserId());
		fundLogReq.setSn(request.getBody().getParam().getSn());
		fundLogReq.setFromDate(request.getBody().getParam().getFromDate());
		fundLogReq.setToDate(request.getBody().getParam().getToDate());
		
		return fundLogReq;
	}
	
	
	/** 
	* @Title: fundTransferRequestDTO2UserFund 
	* @Description: 转账实体转换方法 
	* @param request
	* @return
	*/
	public static FundTransferOrder fundTransferRequestDTO2SubSystem(Request<FundTransferSubRequestDTO> request, int direct) {
	
		FundTransferOrder fundTransferOrder = null;
		switch (direct)
		{
		case 0:
		case 2:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TFTP);
			break;
		case 1:
		case 3:
			fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TFTM);
			break;
		case 4:
			fundTransferOrder = new FundTransferOrder(FundModel.GM.RSXX.ITEMS.RDAX);
			break;
	//	case 5:
	//		fundTransferOrder = new FundTransferOrder(FundModel.TF.TAIX.ITEMS.TFBU);
		//	break;
		
		
		}
		
		FundTransferSubRequestDTO fundTransferRequestDTO = request.getBody().getParam();
		fundTransferOrder.setApplyTime(DataConverterUtil.convertLong2Date(fundTransferRequestDTO.getApplyTime()));
		fundTransferOrder.setRetTime (DataConverterUtil.convertLong2Date(fundTransferRequestDTO.getRetTime()));
		User applyUser = new User();
		applyUser.setId(fundTransferRequestDTO.getUserId());
		UserProfile up = new UserProfile();
		up.setUserChain(request.getHead().getUserAccount());
		up.setAccount(AccountTool.getRealAccount(up.getUserChain()));
		applyUser.setUserProfile(up);
		fundTransferOrder.setApplyUser(applyUser);
		fundTransferOrder.setIsUpward(2L);
		User receiveUser = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(fundTransferRequestDTO.getRcvAccount());
		receiveUser.setId(fundTransferRequestDTO.getUserId());
		receiveUser.setUserProfile(userProfile);
		fundTransferOrder.setReceiveUser(receiveUser);
		fundTransferOrder.setUserChain("");
		fundTransferOrder.setTransferAmt(fundTransferRequestDTO.getTransferAmt());
		
		return fundTransferOrder;
	}
	
	public static Long getWithdrawStatusValue(WithdrawStauts stauts) {

		return stauts._action;
	}

	/** 
	* @Title: convertFundTransferRecordQueryDTO 
	* @Description: 组合转账记录查询DTO的各种参数设置
	* @param request
	* @return
	*/
	public static FundTransferRecordQueryDTO convertFundTransferRecordQueryDTO(
			Request<QueryFundTransferOrderDTO> request) {
		FundTransferRecordQueryDTO queryDTO = new FundTransferRecordQueryDTO();
		QueryFundTransferOrderDTO cRequestDTO = request.getBody().getParam();

		if (cRequestDTO != null) {
			if (StringUtils.isNotBlank(cRequestDTO.getSn())) {
				queryDTO.setSn(cRequestDTO.getSn());
			}

			if (null != cRequestDTO.getFromDate()) {
				queryDTO.setFromDate(cRequestDTO.getFromDate());
			}
			if (null != cRequestDTO.getToDate()) {
				queryDTO.setToDate(cRequestDTO.getToDate());
			}
			queryDTO.setExactUser(cRequestDTO.getExactUser());
			queryDTO.setEndNo(request.getBody().getPager().getEndNo());
			queryDTO.setStartNo(request.getBody().getPager().getStartNo());
			queryDTO.setDirection(cRequestDTO.getDirection());
		}

		return queryDTO;
	}

	/** 
	* @Title: fundTransferOrder2FundTransferStrc 
	* @Description: 将用户转账记录转换为用户转账记录返回数据结构 
	* @param fundTransferOrder
	* @return
	*/
	public static FundTransferStrc fundTransferOrder2FundTransferStrc(FundTransferOrder fundTransferOrder) {
		FundTransferStrc fundTransferStrc = new FundTransferStrc();
		fundTransferStrc.setIsUpward(fundTransferOrder.getIsUpward());
		fundTransferStrc.setRcvAccount(fundTransferOrder.getReceiveUser().getUserProfile().getAccount());
		fundTransferStrc.setSn(fundTransferOrder.getSn());
		fundTransferStrc.setTransferAmt(fundTransferOrder.getTransferAmt());
		fundTransferStrc.setTransferTime(DataConverterUtil.convertDate2Long(fundTransferOrder.getApplyTime()));
		fundTransferStrc.setUserAccount(fundTransferOrder.getApplyUser().getAccount());
		if (fundTransferOrder.getApplyUser().isFrontUser()) {
			//fundTransferStrc.setUserChain(fundTransferOrder.getApplyUser().getUserProfile().getUserChain());
		}
		return fundTransferStrc;
	}

	/** 
	* @Title: bankCard2UserBankStruc 
	* @Description: 银行卡实体转换响应参数结构体 
	* @param bc
	* @return
	*/
	public static UserBankStruc bankCard2UserBankStruc(BankCard bc) {
		UserBankStruc ubs = new UserBankStruc();
		ubs.setBankId(bc.getBank() != null ? bc.getBank().getId() : null);
		ubs.setBankAccount(bc.getAccountHolder());
		ubs.setBankNumber(bc.getBankCardNo()!=null?(bc.getBankCardNo().replaceAll(" ", "")):null);
		ubs.setBranchName(bc.getSubBranch());
		ubs.setMcBankId(bc.getMownecumId());
		ubs.setBindDate(bc.getGmtModified());
		ubs.setId(bc.getId());
		ubs.setNickName(bc.getNickName());
		ubs.setBindcardType(bc.getBindcardType());
		return ubs;
	}

	public static BankCard bankCardApplyBindRequest2BankCard(BankCardApplyBindRequest bcabr) {
		BankCard bc = new BankCard();
		bc.setAccountHolder(bcabr.getBankAccount());
		bc.setBank(getBank(bcabr.getBankId()));
		bc.setBankCardNo(bcabr.getBankNumber());
		bc.setBindingUser(getUser(bcabr.getUserId()));
		bc.setCity(bcabr.getCity());
		bc.setProvince(bcabr.getProvince());
		bc.setMownecumId(bcabr.getMcBankId());
		bc.setSubBranch(bcabr.getBranchName());
		bc.setBindcardType(bcabr.getBindcardType());
		bc.setNickName(bcabr.getNickName());
		bc.setId(bcabr.getId());
		
		return bc;
	}

	public static FundChargeException transChargeExceptionRequest2FundChargeException(ChargeExceptionRequest req) {
		FundChargeException exception = new FundChargeException();

		exception.setId(req.getExeptionId());
		//接受卡银行
		try {
			if (StringUtils.isNotEmpty(req.getExact_payment_bank()))
				//实际支付银行id
				//exception.setBankName(req.getExact_payment_bank());
				exception.setBankId(Long.valueOf(req.getExact_payment_bank()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		exception.setBankName(req.getArea());
		exception.setSn(req.getException_order_num());
		exception.setMcSn(req.getException_order_num());
		exception.setCardAcct(req.getPay_card_name());
		exception.setCardNumber(req.getPay_card_num());
		exception.setRcvBank(Long.valueOf(req.getReceiving_bank()));
		exception.setRcvAccName(req.getReceiving_account_name());
		exception.setRcvEmail(req.getEmail());
		//exception.setBankName(req.getExact_payment_bank());
		exception.setBankName(req.getArea());
		exception.setBankAddr(req.getArea());

		//exception.setBankName(req.getArea());
		exception.setMcChannel(req.getChannel());
		exception.setMemo(req.getNote());
		exception.setMcExactTime(DataConverterUtil.convertLong3Date(req.getExact_time()));
		exception.setRealChargeAmt(MowNumTool.fromMow(req.getAmount()));
		exception.setMcBankFee(MowNumTool.fromMow(req.getFee()));
		exception.setMcNoticeTime(new Date());
		exception.setStatus(Status.untreated.getStatis());
		exception.setMcFee(MowNumTool.fromMow(req.getTransaction_charge()));
		exception.setOperatingTime(DataConverterUtil.convertLong3Date(req.getOperating_time()));
		exception.setBaseInfo(req.getBase_info());
		return exception;
	}

	private static FundBank getBank(long bankId) {
		FundBank bank = new FundBank();
		bank.setId(bankId);
		return bank;
	}

	private static User getUser(long userId) {
		User user = new User();
		user.setId(userId);
		return user;
	}

	public static FundAdjustRecordQueryDTO convertAccountBalanceRecordQueryDTO(Request<FundAdjustRequest> request) {
		FundAdjustRecordQueryDTO queryDTO = new FundAdjustRecordQueryDTO();
		FundAdjustRequest queryRequest = request.getBody().getParam();

		if (queryRequest != null) {
			if (null != queryRequest.getStatus()) {
				queryDTO.setStatus(queryRequest.getStatus());
			}

			queryDTO.setStartNo(request.getBody().getPager().getStartNo());
			queryDTO.setEndNo(request.getBody().getPager().getEndNo());
		}

		return queryDTO;
	}

	public static FundAdjustResponse fundAdjust2AdjustStruc(FundAdjustOrder fundAdjust) {
		FundAdjustResponse fundAdjustStruc = new FundAdjustResponse();

		fundAdjustStruc.setSn(fundAdjust.getSn());
		fundAdjustStruc.setUserAccount(fundAdjust.getUser().getUserProfile().getAccount());
		fundAdjustStruc.setTypeId(fundAdjust.getType().getValue());
		fundAdjustStruc.setApplyTime(DataConverterUtil.convertDate2Long(fundAdjust.getApplyTime()));
		fundAdjustStruc.setWithdrawAmt(fundAdjust.getAmt());
		fundAdjustStruc.setMemo(fundAdjust.getApplyMemo());
		//fundAdjustStruc.setApplyAccount(fundAdjust.getApplyUser().getUserProfile().getAccount());
		if (null != fundAdjust.getApprUser()) {
			fundAdjustStruc.setApprAccount(fundAdjust.getApprUser().getUserProfile().getAccount());
		}
		if (null != fundAdjust.getApprTime()) {
			fundAdjustStruc.setApprTime(DataConverterUtil.convertDate2Long(fundAdjust.getApprTime()));
		}
		fundAdjustStruc.setStatus(fundAdjust.getApprStatus().getValue());

		return fundAdjustStruc;

	}

	public static FundAdjustOrder accountBalanceApplyRequest2FundAdjust(Request<FundAdjustApplyRequest> request) {
		FundAdjustOrder fundAdjust = new FundAdjustOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
		FundAdjustApplyRequest accountBalanceApplyRequest = request.getBody().getParam();

		User user = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(accountBalanceApplyRequest.getRcvAct());
		user.setUserProfile(userProfile);
		fundAdjust.setUser(user);

		fundAdjust.setAmt(accountBalanceApplyRequest.getDepositAmt());
		fundAdjust.setType(FundAdjustOrder.Type.getTypeByValue(accountBalanceApplyRequest.getTypeId()));
		fundAdjust.setApplyMemo(accountBalanceApplyRequest.getMemo());
		User applyUser = new User();
		applyUser.setId(request.getHead().getUserId());
		fundAdjust.setApplyUser(applyUser);
		return fundAdjust;
	}

	public static FundAdjustOrder accountBalanceApplyRequest2FundAdjust(
			FundAdjustApplyRequest accountBalanceApplyRequest, BaseUser applyUser) {
		FundAdjustOrder fundAdjust = new FundAdjustOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
		User user = new User();
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(accountBalanceApplyRequest.getRcvAct());
		user.setUserProfile(userProfile);
		fundAdjust.setUser(user);

		fundAdjust.setAmt(accountBalanceApplyRequest.getDepositAmt());
		fundAdjust.setType(FundAdjustOrder.Type.getTypeByValue(accountBalanceApplyRequest.getTypeId()));
		fundAdjust.setApplyMemo(accountBalanceApplyRequest.getMemo());

		fundAdjust.setApplyUser(applyUser);
		return fundAdjust;
	}

	public static BankCardBindRecordQueryDTO convertBankCardBindRecordQueryDTO(
			Request<BankCardQueryBindRecordRequest> request) {
		BankCardBindRecordQueryDTO bankCardBindRecordQueryDTO = new BankCardBindRecordQueryDTO();
		BankCardQueryBindRecordRequest queryRequest = request.getBody().getParam();
		if (queryRequest != null) {
			if (null != queryRequest.getUserLvl()) {
				bankCardBindRecordQueryDTO.setUserLvl(queryRequest.getUserLvl());
			}

			if (null != queryRequest.getUserAccount()) {
				bankCardBindRecordQueryDTO.setUserAccount(queryRequest.getUserAccount());
			}

			if (null != queryRequest.getBankCard()) {
				bankCardBindRecordQueryDTO.setBankCardNumber(queryRequest.getBankCard());
			}

			if (null != queryRequest.getOperator()) {
				bankCardBindRecordQueryDTO.setOperator(queryRequest.getOperator());
			}

			bankCardBindRecordQueryDTO.setStartNo(request.getBody().getPager().getStartNo());
			bankCardBindRecordQueryDTO.setEndNo(request.getBody().getPager().getEndNo());
			bankCardBindRecordQueryDTO.setBindcardType(queryRequest.getBindcardType());
		}

		return bankCardBindRecordQueryDTO;
	}

	public static List<BankCardQueryBindRecordResponse> UserCardBind2BankCardQueryBindRecordResponse(
			 boolean isList,UserCardBind... userCardBinds) {
		List<BankCardQueryBindRecordResponse> list = new ArrayList<BankCardQueryBindRecordResponse>();
		if (isList) {			
			for(UserCardBind userCardBind:userCardBinds)
			if (userCardBind.getBankCards() != null && userCardBind.getBankCards().size() > 0) {
				for (int i = 0; i < userCardBind.getBankCards().size(); i++) {
					BankCardQueryBindRecordResponse response = new BankCardQueryBindRecordResponse();
					response.setLockedId(userCardBind.getLockId());
					response.setAccount(userCardBind.getBindUser().getUserProfile().getAccount());
					if(userCardBind.getBindUser().getUserProfile().getUserLvl()!=null)
					response.setUserLvl(userCardBind.getBindUser().getUserProfile().getUserLvl().longValue());
					List<UserBankStruc> userBanks = new ArrayList<UserBankStruc>();
					BankCard bankCard = userCardBind.getBankCards().get(i);
					UserBankStruc userBankStruc = new UserBankStruc();
					userBankStruc.setBankId(bankCard.getBank().getId());
					userBankStruc.setBankAccount(bankCard.getAccountHolder());
					if (bankCard.getBankCardNo() != null)
						userBankStruc.setBankNumber((bankCard.getBankCardNo().replaceAll(" ", "")));
					userBankStruc.setBranchName(bankCard.getSubBranch());
					userBankStruc.setMcBankId(bankCard.getMownecumId());
					userBankStruc.setBindDate(bankCard.getGmtCreated());
					userBankStruc.setProvince(bankCard.getProvince());
					userBankStruc.setCity(bankCard.getCity());
					userBankStruc.setIsBlackList(bankCard.getIsBlackList());
					userBankStruc.setBindcardType(bankCard.getBindcardType());
					userBankStruc.setNickName(bankCard.getNickName());
					userBanks.add(userBankStruc);
					response.setTopAcc(bankCard.getTopAcc());
					response.setUserBanks(userBanks);
					response.setVipLvl(bankCard.getVipLvl());
					response.setBindCount(Long.valueOf(userBanks.size()));
					response.setOperator(userCardBind.getOperator());
					response.setStatus(userCardBind.getStatus());
					list.add(response);
				}
			}
		
		} else {
			UserCardBind userCardBind=userCardBinds[0];
			BankCardQueryBindRecordResponse response = new BankCardQueryBindRecordResponse();
			response.setLockedId(userCardBind.getLockId());
			response.setAccount(userCardBind.getBindUser().getUserProfile().getAccount());
			response.setUserLvl(userCardBind.getBindUser().getUserProfile().getUserLvl().longValue());
			List<UserBankStruc> userBanks = new ArrayList<UserBankStruc>();
			String topAcc=null;
			if (userCardBind.getBankCards() != null && userCardBind.getBankCards().size() > 0) {
				for (int i = 0; i < userCardBind.getBankCards().size(); i++) {
					BankCard bankCard = userCardBind.getBankCards().get(i);
					UserBankStruc userBankStruc = new UserBankStruc();
					userBankStruc.setBankId(bankCard.getBank().getId());
					userBankStruc.setBankAccount(bankCard.getAccountHolder());
					if (bankCard.getBankCardNo() != null)
						userBankStruc.setBankNumber((bankCard.getBankCardNo().replaceAll(" ", "")));
					userBankStruc.setBranchName(bankCard.getSubBranch());
					userBankStruc.setMcBankId(bankCard.getMownecumId());
					userBankStruc.setBindDate(bankCard.getGmtCreated());
					userBankStruc.setProvince(bankCard.getProvince());
					userBankStruc.setCity(bankCard.getCity());
					userBankStruc.setBindcardType(bankCard.getBindcardType());
					userBankStruc.setNickName(bankCard.getNickName());
					userBankStruc.setIsBlackList(bankCard.getIsBlackList());
					userBanks.add(userBankStruc);
					topAcc=bankCard.getTopAcc();
					response.setVipLvl(bankCard.getVipLvl());
				}
			}
			response.setTopAcc(topAcc);
			response.setUserBanks(userBanks);
			
			response.setBindCount(Long.valueOf(userBanks.size()));
			response.setOperator(userCardBind.getOperator());
			response.setStatus(userCardBind.getStatus());
			list.add(response);
		}

		return list;
	}

	public static BankCardBindHistoryRecordQueryDTO convertBankCardBindHistoryRecordQueryDTO(
			Request<BankCardBindHistoryRecordRequest> request) {
		BankCardBindHistoryRecordQueryDTO dto = new BankCardBindHistoryRecordQueryDTO();

		BankCardBindHistoryRecordRequest queryRequest = request.getBody().getParam();

		if (queryRequest != null) {
			if (null != queryRequest.getUserId()) {
				dto.setUserId(queryRequest.getUserId());
			}
			if (queryRequest.getBankCard() != null)
				dto.setBankCard(queryRequest.getBankCard());
			dto.setStartNo(request.getBody().getPager().getStartNo());
			dto.setEndNo(request.getBody().getPager().getEndNo());
			dto.setBindcardType(queryRequest.getBindcardType());
		}

		return dto;
	}

	public static BankCardHistoryStruc UserCardBindHistoryRecord2BankCardHistoryStruc(
			UserCardBindHistory userCardBindHistoryRecord) {
		BankCardHistoryStruc struc = new BankCardHistoryStruc();

		struc.setUserId(userCardBindHistoryRecord.getUserId());
		struc.setAction(userCardBindHistoryRecord.getAction());
		struc.setActionTime(userCardBindHistoryRecord.getActionTime());
		struc.setBankId(userCardBindHistoryRecord.getBankCard().getId());
		struc.setBankAccount(userCardBindHistoryRecord.getBankCard().getAccountHolder());
		struc.setProvince(userCardBindHistoryRecord.getBankCard().getProvince());
		struc.setCity(userCardBindHistoryRecord.getBankCard().getCity());
		struc.setBranchName(userCardBindHistoryRecord.getBankCard().getSubBranch());
		struc.setMcBankId(userCardBindHistoryRecord.getBankCard().getMownecumId());
		struc.setBankNumber(userCardBindHistoryRecord.getBankCard().getBankCardNo());
		struc.setTopAcc(userCardBindHistoryRecord.getTopAcc());
		struc.setAccount(userCardBindHistoryRecord.getAccount());
		struc.setIsFreeze(userCardBindHistoryRecord.getIsFreeze());
		struc.setFreezeMethod(userCardBindHistoryRecord.getFreezeMethod());
		struc.setIsBlackList(userCardBindHistoryRecord.getBankCard().getIsBlackList());
		struc.setNickName(userCardBindHistoryRecord.getNickName());
		struc.setBindcardType(userCardBindHistoryRecord.getBindcardType());
		return struc;
	}

	public static FundSuspiciousCardResponse FundSuspiciousCard2FundSuspiciousCardResponse(FundSuspiciousCard card) {
		FundSuspiciousCardResponse response = new FundSuspiciousCardResponse();

		if (card != null) {
			if (null != card.getId()) {
				response.setId(card.getId());
			}

			if (null != card.getCardNumber()) {
				response.setCardNumber(card.getCardNumber());
			}

			if (null != card.getMemo()) {
				response.setMemo(card.getMemo());
			}

			if (null != card.getCreatorAccount()) {
				response.setCreatorAccount(card.getCreatorAccount());
			}

			if (null != card.getCreateTime()) {
				response.setGmtCreated(card.getCreateTime());
			}
			response.setTopAcc(card.getTopAcc());
			response.setAccount(card.getAccount());
			response.setBankAcc(card.getBankAcc());
		}

		return response;
	}

	public static FundSuspiciousCard FundSuspiciousCardRequest2FundSuspiciousCard(FundSuspiciousCardRequest request) {
		FundSuspiciousCard card = new FundSuspiciousCard();

		if (request != null) {
			if (null != request.getId()) {
				card.setId(request.getId());
			}

			if (null != request.getCreatorAccount()) {
				card.setCreatorAccount(request.getCreatorAccount());
			}

			if (null != request.getCardNumber()) {
				card.setCardNumber(request.getCardNumber());
			}

			if (null != request.getType()) {
				card.setType(CardType.getCardTypeByValue(request.getType()));
			}

			if (null != request.getMemo()) {
				card.setMemo(request.getMemo());
			}
		}

		return card;
	}

	public static FundUserRemarkRecyleStruc transforRemarkRecyle2Struc(FundUserRemarkRecyle entity) {
		FundUserRemarkRecyleStruc struc = new FundUserRemarkRecyleStruc();
		struc.setCreateTime(entity.getGmtCreated());
		struc.setId(entity.getId());
		struc.setRemark(entity.getRemark());
		struc.setUserName(entity.getAccount());
		return struc;
	}

	public static FundUserRemarkStruc transforRemark2Struc(FundUserRemark entity) {
		FundUserRemarkStruc struc = new FundUserRemarkStruc();
		struc.setAccount(entity.getAccount());
		struc.setGmtCansetremark(entity.getGmtCansetremark());
		struc.setGmtCreated(entity.getGmtCreated());
		struc.setGmtModified(entity.getGmtModified());
		struc.setId(entity.getId());
		struc.setRemark(entity.getRemark());
		struc.setUserId(entity.getUserId());
		return struc;
	}

	public static FundUserChargeStruc transforFundChargeOrder2Struc(FundChargeOrder order) {
		if (order != null) {
			FundUserChargeStruc struc = new FundUserChargeStruc();
			struc.setId(order.getSn());
			struc.setChargeAmt(order.getPreChargeAmt());
			struc.setPayBankName(order.getPayBank().getName());
			struc.setRcvAccountName(order.getRevCard().getAccountHolder());
			struc.setBankCardNo(order.getRevCard().getBankCardNo());
			struc.setRcvBankName(order.getRevCard().getBank().getName());
			struc.setRcvMail(order.getRevEmail());
			struc.setRemark(order.getMemo());
			if (order.getRevBank() != null)
				struc.setUserBankName(order.getRevBank().getName());
			struc.setBankUrl(order.getPayBank().getUrl());
			struc.setBreakUrl(order.getBreakUrl());
			struc.setNickName(order.getNickName());
			struc.setBankNumber(order.getPayCard()!=null?order.getPayCard().getBankCardNo():"");
			struc.setBankAccount(order.getApplyUser().getAccount());
			return struc;
		} else {
			return null;
		}
	}
	
	public static UserCenterReportStruc UserCenterReportInfo2UserCenterReportStruc(UserCenterReportInfo info) {
		UserCenterReportStruc ut = new UserCenterReportStruc();
		ut.setAccount(info.getUser().getUserProfile().getAccount());
		ut.setActualTotalSubuserSaleroom(info.getActualTotalSubuserSaleroom());
		ut.setTotalProfit(info.getTotalProfit());
		ut.setTotalSubuserPoint(info.getTotalSubuserPoint());
		ut.setTotalSubuserSaleroom(info.getTotalSubuserSaleroom());
		ut.setTotalSubuserWins(info.getTotalSubuserWins());
		ut.setUserId(info.getUser().getId());
		ut.setUserLvl(info.getUser().getUserLevel());
		return ut;
	}
	
	public static WithdrawAppealStruc transWithdrawAppeal2Struc(WithdrawAppeal withdrawAppeal) {
		WithdrawAppealStruc withdrawAppealStruc = new WithdrawAppealStruc();
		withdrawAppealStruc.setSn(withdrawAppeal.getSn());
		withdrawAppealStruc.setUserId(withdrawAppeal.getUserId());
		withdrawAppealStruc.setWithdrawAmt(withdrawAppeal.getWithdrawAmt());
		withdrawAppealStruc.setApplyTime(withdrawAppeal.getApplyTime());
		withdrawAppealStruc.setUserBankStruc(withdrawAppeal.getUserBankStruc());
		withdrawAppealStruc.setBankId(withdrawAppeal.getBankId());
		withdrawAppealStruc.setCardNumber(withdrawAppeal.getCardNumber());
		withdrawAppealStruc.setAppealMemo(withdrawAppeal.getAppealMemo());	
		withdrawAppealStruc.setAppealTips(withdrawAppeal.getAppealTips());			
		withdrawAppealStruc.setAppealStatus(withdrawAppeal.getAppealStatus());
		withdrawAppealStruc.setAppealTipsResult(withdrawAppeal.getAppealTipsResult());
		withdrawAppealStruc.setIsSeperate(withdrawAppeal.getIsSeperate());
		return withdrawAppealStruc;
	}
	
	public static WithdrawAppealStruc transAppeal2Struc(WithdrawAppeal withdrawAppeal) {
		WithdrawAppealStruc withdrawAppealStruc = new WithdrawAppealStruc();
		
		withdrawAppealStruc.setId(withdrawAppeal.getId());
		withdrawAppealStruc.setUserId(withdrawAppeal.getUserId());
		withdrawAppealStruc.setAppealSn(withdrawAppeal.getAppealSn());
		withdrawAppealStruc.setWithdrawAmt(withdrawAppeal.getWithdrawAmt());
		withdrawAppealStruc.setWithdrawTime(withdrawAppeal.getWithdrawTime());
		withdrawAppealStruc.setVipLvl(withdrawAppeal.getVipLvl());
		withdrawAppealStruc.setUserName(withdrawAppeal.getUserName());
		withdrawAppealStruc.setAppealStatus(withdrawAppeal.getAppealStatus());
		withdrawAppealStruc.setAppealAcct(withdrawAppeal.getAppealAcct());
		withdrawAppealStruc.setAppealTime(withdrawAppeal.getAppealTime());
		withdrawAppealStruc.setBankId(withdrawAppeal.getBankId());
		withdrawAppealStruc.setBankName(withdrawAppeal.getBankName());
		withdrawAppealStruc.setCardNumber(withdrawAppeal.getCardNumber());
		withdrawAppealStruc.setAppealMemo(withdrawAppeal.getAppealMemo());	
		withdrawAppealStruc.setArgueTime(withdrawAppeal.getArgueTime());
		withdrawAppealStruc.setArgueAcct(withdrawAppeal.getArgueAcct());
		withdrawAppealStruc.setWithdrawSn(withdrawAppeal.getWithdrawSn());
		withdrawAppealStruc.setIsAppeal(withdrawAppeal.getIsAppeal());
		withdrawAppealStruc.setAppealTips(withdrawAppeal.getAppealTips());
		withdrawAppealStruc.setAppealTipsResult(withdrawAppeal.getAppealTipsResult());
		withdrawAppealStruc.setIsCheck(withdrawAppeal.getIsCheck());
		withdrawAppealStruc.setNowCheckUser(withdrawAppeal.getNowCheckUser());
		withdrawAppealStruc.setUploadImages(withdrawAppeal.getUploadImages());

		return withdrawAppealStruc;
	}


}
