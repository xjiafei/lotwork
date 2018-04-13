/**   
* @Title: VOConverter.java 
* @Package com.winterframework.firefrog.fund.dao.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-1 上午11:10:37 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.util.AccountTool;
import com.winterframework.firefrog.fund.dao.entity.UserCardBind;
import com.winterframework.firefrog.fund.dao.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundChargeMCOrder;
import com.winterframework.firefrog.fund.entity.FundChargeOrder;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.entity.FundSoloRemarkManual;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.entity.FundWithdrawMCOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.entity.UserFundChangeLog;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.schedule.Status;
import com.winterframework.firefrog.schedule.dto.UserBankStruc;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard.CardType;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.RiskType;

/** 
* @ClassName: VOConverter 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-1 上午11:10:37 
*  
*/
public class VOConverter {


	public static FundTransfer FundTransferOrder2FundTransfer(FundTransferOrder fundTransferOrder) {
		FundTransfer fundTransfer = new FundTransfer();
		fundTransfer.setUserId(fundTransferOrder.getApplyUser().getId());
		fundTransfer.setTransferAmt(fundTransferOrder.getTransferAmt());
		fundTransfer.setGmtCreated(fundTransferOrder.getApplyTime());
		fundTransfer.setRcvAccount(fundTransferOrder.getReceiveUser().getUserProfile().getAccount());
		fundTransfer.setRcvUserId(fundTransferOrder.getReceiveUser().getId());
		fundTransfer.setIsUpward(fundTransferOrder.getIsUpward());
		fundTransfer.setUserChain(fundTransferOrder.getUserChain());
		fundTransfer.setUserAccount(fundTransferOrder.getApplyUser().getAccount());
		fundTransfer.setSn(fundTransferOrder.getSn());
		return fundTransfer;
	}

	/** 
	* @Title: fund2UserFund 
	* @Description:用户资金信息vo转换为entity
	* @param fund
	* @return
	*/
	public static UserFund fund2UserFund(Fund fund) {
		UserFund userFund = new UserFund();
		userFund.setBal(fund.getBal());
		userFund.setDisableAmt(fund.getDisableAmt());
		userFund.setFrozenAmt(fund.getFrozenAmt());
		AclUser user = new AclUser();
		user.setId(fund.getUserId());
		userFund.setUser(user);
		userFund.setId(fund.getId());
		return userFund;
	}

	public static FundCharge transFundChargeOrder2FundCharge(FundChargeOrder order) {
		FundCharge fundCharge = new FundCharge();
		BankCard payCard = order.getPayCard();
		if (payCard != null) {
			fundCharge.setPayBankId(payCard.getBank() != null ? payCard.getBank().getId() : null);
			fundCharge.setCardNumber(payCard.getBankCardNo());
			fundCharge.setAccount(payCard.getAccountHolder());
		}
		if (order.getPayBank() != null) {
			fundCharge.setPayBankId(order.getPayBank().getId());

		}
		fundCharge.setId(order.getId());
		fundCharge.setApplyTime(order.getApplyTime());
		fundCharge.setChargeMemo(order.getMemo());
		fundCharge.setChargeTime(order.getChargeTime());
		fundCharge.setSn(order.getSn());
		fundCharge.setStatus(order.getStatus() != null ? order.getStatus().getValue() : null);
		fundCharge.setUserId(order.getApplyUser() != null ? order.getApplyUser().getId() : null);
		fundCharge.setUserAct(order.getApplyUser().getAccount());
		fundCharge.setPreChargeAmt(order.getPreChargeAmt());
		fundCharge.setDepositMode(order.getDepositMode());
		fundCharge.setBreakUrl(order.getBreakUrl());
		fundCharge.setPlatfom(order.getPlatfom());
		fundCharge.setVer(order.getVer());
		return fundCharge;
	}

	public static FundCharge transFundChargeOrder2FundChargeUpdate(FundChargeOrder order) {
		FundCharge fundCharge = new FundCharge();
		FundChargeMCOrder mcOrder = order.getMcOrder();
		BankCard payCard = order.getPayCard();
		if (payCard != null) {
			fundCharge.setPayBankId(payCard.getBank() != null ? payCard.getBank().getId() : null);
			fundCharge.setCardNumber(payCard.getBankCardNo());
			fundCharge.setAccount(payCard.getAccountHolder());
		}
		fundCharge.setId(order.getId());
		if (order.getRevBank() != null) {
			fundCharge.setBankId(order.getRevBank().getId());
		}
		fundCharge.setChargeMemo(order.getMemo());
		fundCharge.setRealChargeAmt(mcOrder.getAmount());
		fundCharge.setMcBankFee(mcOrder.getBankFee());

		fundCharge.setStatus(order.getStatus() != null ? order.getStatus().getValue() : null);
		fundCharge.setMcArea(mcOrder.getArea());
		fundCharge.setMcChannel(mcOrder.getChannel());
		fundCharge.setMcErrorMsg(mcOrder.getErrorMsg());
		fundCharge.setMcExpireTime(mcOrder.getExpireTime());
		fundCharge.setMcFee(mcOrder.getFee());
		fundCharge.setMcNoticeTime(mcOrder.getNoticeTime());
		fundCharge.setMcSn(mcOrder.getSn());
		fundCharge.setSn(order.getSn());
		fundCharge.setMcUuid(mcOrder.getUuid());
		fundCharge.setRealBankId(order.getRealBankId());
		fundCharge.setChargeTime(order.getChargeTime());
		if (order.getRevCard() != null) {
			fundCharge.setRcvAccName(order.getRevCard().getAccountHolder());
			fundCharge.setRcvEmail(order.getRevEmail());
			fundCharge.setRcvCardNumber(order.getRevCard().getBankCardNo());
			fundCharge.setRcvBankName(order.getRevCard().getSubBranch());
			if(order.getRevCard().getBank()!=null){
				fundCharge.setBankId(order.getRevCard().getBank().getId());
			}
		}
		return fundCharge;
	}

	public static FundChargeOrder transFundCharge2FundChargeOrder(FundCharge fundCharge) {
		FundChargeOrder order = new FundChargeOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
		FundChargeMCOrder mcOrder = order.getMcOrder();
		order.setApplyTime(fundCharge.getApplyTime());
		AclUser applyUser = new AclUser();
		applyUser.setId(fundCharge.getUserId());
		applyUser.setAccount(fundCharge.getAccount());
		order.setApplyUser(applyUser);
		order.setChargeTime(fundCharge.getChargeTime());
		order.setId(fundCharge.getId());
		order.setMemo(fundCharge.getChargeMemo());
			BankCard payCard = new BankCard();
			payCard.setBankCardNo(fundCharge.getCardNumber());
			payCard.setAccountHolder(fundCharge.getAccount());
				FundBank payBank = new FundBank();
				payBank.setId(fundCharge.getPayBankId());
				payCard.setBank(payBank);
			order.setPayCard(payCard);
			order.setPayBank(payBank);
			order.setPreChargeAmt(fundCharge.getPreChargeAmt());
			order.setRealChargeAmt(fundCharge.getRealChargeAmt());
			order.setPayCard(payCard);
			BankCard revCard = new BankCard();
				FundBank revBank = new FundBank();
				revBank.setId(fundCharge.getBankId());
			revBank.setName(fundCharge.getRcvBankName());
			revCard.setBank(revBank);
			revCard.setRcvEmail(fundCharge.getRcvEmail());
			revCard.setBankCardNo(fundCharge.getRcvCardNumber());
			revCard.setAccountHolder(fundCharge.getRcvAccName());
		order.setRevCard(revCard);
		order.setRevEmail(fundCharge.getRcvEmail());
		order.setSn(fundCharge.getSn());
		order.setStatus(FundChargeOrder.Status.getStatusByValue(fundCharge.getStatus()));
			mcOrder.setArea(fundCharge.getMcArea());
			mcOrder.setBankFee(fundCharge.getMcBankFee());
			mcOrder.setFee(fundCharge.getMcFee());
			mcOrder.setAmount(fundCharge.getRealChargeAmt());
	
			if (fundCharge.getMcBankFee() != null) {
				mcOrder.setBankFee(fundCharge.getMcBankFee());
			}
			if (fundCharge.getMcFee() != null) {
				mcOrder.setFee((fundCharge.getMcFee()));
			}
	
			mcOrder.setChannel(fundCharge.getMcChannel());
			mcOrder.setErrorMsg(fundCharge.getMcErrorMsg());
			mcOrder.setExpireTime(fundCharge.getMcExpireTime());
	
			mcOrder.setNoticeTime(fundCharge.getMcNoticeTime());
			mcOrder.setSn(fundCharge.getMcSn());
			mcOrder.setTempSn(fundCharge.getTempSn());
			mcOrder.setUuid(fundCharge.getMcUuid());
		order.setDepositMode(fundCharge.getDepositMode());
		order.setTopVip(fundCharge.getTopVip());
		order.setUserAct(fundCharge.getUserAct());
		order.setRealBankId(fundCharge.getRealBankId());
		order.setPlatfom(fundCharge.getPlatfom());
		order.setVer(fundCharge.getVer());
		return order;
	}

	/** 
	* @Title: fundTransfer2FundTransferOrder 
	* @Description: 转账记录vo转化为转账记录entity
	* @param fundTransfer
	* @return
	*/
	public static FundTransferOrder fundTransfer2FundTransferOrder(FundTransfer fundTransfer) {
		FundTransferOrder fundTransferOrder = new FundTransferOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
		fundTransferOrder.setApplyTime(fundTransfer.getGmtCreated());
		User applyUser = new User();
		applyUser.setId(fundTransfer.getUserId());
		UserProfile up = new UserProfile();
		up.setAccount(fundTransfer.getUserAccount());
		applyUser.setUserProfile(up);
		fundTransferOrder.setApplyUser(applyUser);
		fundTransferOrder.setId(fundTransfer.getId());
		fundTransferOrder.setIsUpward(fundTransfer.getIsUpward());
		User receiveUser = new User();
		receiveUser.setId(fundTransfer.getRcvUserId());
		UserProfile userProfile = new UserProfile();
		userProfile.setAccount(fundTransfer.getRcvAccount());
		receiveUser.setUserProfile(userProfile);
		fundTransferOrder.setReceiveUser(receiveUser);
		fundTransferOrder.setSn(fundTransfer.getSn());
		fundTransferOrder.setTransferAmt(fundTransfer.getTransferAmt());
		fundTransferOrder.setUserChain(fundTransfer.getUserChain());
		return fundTransferOrder;
	}

	/** 
	* @Title: userFundChangeLog2FundChangeLog 
	* @Description:  资金变动记录entity转换为vo 
	* @param userFundChangeLog
	* @return
	*/
	public static FundChangeLog userFundChangeLog2FundChangeLog(UserFundChangeLog userFundChangeLog) {
		FundChangeLog fundChangeLog = new FundChangeLog();
		fundChangeLog.setBeforBal(userFundChangeLog.getBeforBal());
		fundChangeLog.setBeforeDamt(userFundChangeLog.getBeforeDamt());
		fundChangeLog.setCtBal(userFundChangeLog.getCtBal());
		fundChangeLog.setCtDamt(userFundChangeLog.getCtDamt());
		fundChangeLog.setFundId(userFundChangeLog.getFund().getId());
		fundChangeLog.setOperator(userFundChangeLog.getOperator());
		fundChangeLog.setReason(userFundChangeLog.getReason());
		fundChangeLog.setSn(userFundChangeLog.getSn());
		fundChangeLog.setFundSn(userFundChangeLog.getFundSn());
		fundChangeLog.setExCode(userFundChangeLog.getExCode());
		fundChangeLog.setUserId(userFundChangeLog.getUserId());
		fundChangeLog.setNote(userFundChangeLog.getNote());
		fundChangeLog.setPlanCode(userFundChangeLog.getPlanCode());
		fundChangeLog.setIsAclUser(userFundChangeLog.getIsAclUser());
		fundChangeLog.setIsVisiblebyFrontUser(userFundChangeLog.getIsVisiblebyFrontUser());
		fundChangeLog.setBeforeAvailBal(userFundChangeLog.getBeforeAvailBal());
		fundChangeLog.setCtAvailBal(userFundChangeLog.getCtAvailBal());
		return fundChangeLog;
	} 

	/**
	 * 
	* @Title: fundWithdrawTOFundWithdrawOrder 
	* @Description: FundWithdraw converter entity
	* @param fundWithdraw
	* @return
	 */
	public static FundWithdrawOrder fundWithdrawTOFundWithdrawOrder(FundWithdraw fundWithdraw) {

		FundWithdrawOrder order = new FundWithdrawOrder(FundModel.FD.AUTO.ITEMS.SUCCESS);
		order.setId(fundWithdraw.getId());

		User applyUser = new User();
		applyUser.setId(fundWithdraw.getUserId());
		UserProfile up = new UserProfile();
		up.setAccount(AccountTool.getRealAccount(fundWithdraw.getApplyAccount()));
		applyUser.setUserProfile(up);
		order.setApplyUser(applyUser);
		order.setIsVip(fundWithdraw.getIsVip());
		order.setRiskType(RiskType.getEnum(fundWithdraw.getRiskType()));
		order.setAppr2BeginTime(fundWithdraw.getAppr2BeginTime());
		order.setCurrDate(fundWithdraw.getCurrDate());
		order.setAttach(fundWithdraw.getAttach());
		order.setWithdrawAmt(fundWithdraw.getWithdrawAmt());
		order.setTopAcc(fundWithdraw.getTopAcc());
		order.setApChannel(fundWithdraw.getApChannel());
		order.setApProject(fundWithdraw.getApProject());

		//		BankCard card = null;
		//		if (fundWithdraw.getUserBankStruc() != null) {
		//			card = (BankCard) DataConverterUtil.convertJson2Object(fundWithdraw.getUserBankStruc(),
		//					new BankCard().getClass());
		//		}
		//		order.setCard(card);
		order.setCardStr(fundWithdraw.getUserBankStruc());

		if (fundWithdraw.getApprAccount() != null) {
			User approver = new User();
			UserProfile profile = new UserProfile();
			profile.setAccount(fundWithdraw.getApprAccount());
			approver.setUserProfile(profile);
			order.setApprover(approver);
		}
		//		if (StringUtils.isNotBlank(fundWithdraw.getApproverAct())) {
		//			UserProfile profile = new UserProfile();
		//			profile.setAccount(fundWithdraw.getApproverAct());
		//			applyUser.setUserProfile(profile);
		//		}

		order.setCurrApprer(fundWithdraw.getCurrApprer());
		order.setApproverTime(fundWithdraw.getApprTime());

		order.setSn(fundWithdraw.getSn());

		order.setApplyTime(fundWithdraw.getApplyTime());

		order.setWithdrawAmt((fundWithdraw.getWithdrawAmt()));
		order.setRealWithdrawAmt(fundWithdraw.getRealWithdrawAmt());

		FundWithdrawMCOrder mcOrder = new FundWithdrawMCOrder();
		if (null != fundWithdraw.getMcRemitTime()) {
			mcOrder.setMcRemitTime(fundWithdraw.getMcRemitTime());
		}


		mcOrder.setMcSN(fundWithdraw.getMcSn());
		mcOrder.setMcNoticeTime(fundWithdraw.getMcNoticeTime());
		mcOrder.setMcFee(fundWithdraw.getMcFee());
		mcOrder.setMcMemo(fundWithdraw.getMcMemo());
		order.setMc(mcOrder);
		order.setStauts(getWithdrawStautsValue(fundWithdraw.getStatus()));
		order.setApprBeginStatus(fundWithdraw.getApprBeginStatus());
		order.setApprBeginTime(fundWithdraw.getApprBeginTime());

		if (fundWithdraw.getIpAddr() != null && fundWithdraw.getIpAddr() > 0) {
			order.setApplyIpAddr(fundWithdraw.getIpAddr());
		}

		if (null != fundWithdraw.getApproveMemo()) {
			order.setAuditMemo(fundWithdraw.getApproveMemo());
		}

		if (null != fundWithdraw.getApplyExpireTime()) {
			order.setApplyExpireTime(fundWithdraw.getApplyExpireTime());
		}

		if (StringUtils.isNotBlank(fundWithdraw.getMemo())) {
			order.setMemo(fundWithdraw.getMemo());
		}
		UserBankStruc struc = JsonMapper.nonDefaultMapper().fromJson(order.getCardStr(), UserBankStruc.class);
		order.setUserBankStruc(struc);
		order.setAppr2Acct(fundWithdraw.getAppr2Acct());
		order.setAppr2Time(fundWithdraw.getAppr2Time());

		return order;
	}

	public static WithdrawStauts getWithdrawStautsValue(Long status) {
		return WithdrawStauts.creatStatus(status);
	}

	/** 
	* @Title: bankCard2UserBank 
	* @Description:银行卡实体转换为用户绑定银行卡VO
	* @param bc
	* @return
	*/
	public static UserBank bankCard2UserBank(BankCard bc) {
		UserBank ub = new UserBank();
		ub.setUserId(bc.getBindingUser().getId());
		ub.setBankId(bc.getBank().getId());
		ub.setBankNumber(bc.getBankCardNo());
		ub.setProvince(bc.getProvince());
		ub.setCity(bc.getCity());
		ub.setBranchName(bc.getSubBranch());
		ub.setBankAccount(bc.getAccountHolder());
		ub.setMcBankId(bc.getMownecumId());
		//		ub.setGmtCreated(new Date());
		//		ub.setGmtModified(new Date());
		return ub;
	}

	/** 
	* @Title: userBank2BankCard 
	* @Description:用户绑定银行卡VO转换为银行卡实体
	* @param bc
	* @return
	*/
	public static BankCard userBank2BankCard(UserBank ub) {
		BankCard bc = new BankCard();
		bc.setBankCardNo(ub.getBankNumber());
		bc.setBank(getBank(ub.getBankId()));
		bc.setAccountHolder(ub.getBankAccount());
		bc.setBindingUser(getUser(ub.getUserId()));
		bc.setProvince(ub.getProvince());
		bc.setCity(ub.getCity());
		bc.setSubBranch(ub.getBranchName());
		bc.setMownecumId(ub.getMcBankId());
		bc.setGmtCreated(ub.getGmtCreated());
		bc.setId(ub.getId());
		bc.setAccount(ub.getAccount());
		bc.setTopAcc(ub.getTopAcc());
		bc.setIsFreeze(ub.getIsFreeze());
		bc.setVipLvl(ub.getVipLvl());
		bc.setFreezeMethod(ub.getFreezeMethod());
		bc.setIsBlackList(ub.getIsBlackList());
		return bc;
	}

	private static FundBank getBank(Long bankId) {
		FundBank bank = new FundBank();
		bank.setId(bankId);
		return bank;
	}

	private static User getUser(long userId) {
		User user = new User();
		user.setId(userId);
		return user;
	}

	public static UserCardBind UserCardBindVO2UserCardBind(UserCardBindVO vo) {
		UserCardBind userCardBind = new UserCardBind();

		if (vo != null) {
			User bindUser = new User();
			bindUser.setId(vo.getUserId());
			UserProfile userProfile = new UserProfile();
			userProfile.setAccount(vo.getAccount());
			userProfile.setUserLvl(vo.getUserLvl());
			bindUser.setUserProfile(userProfile);

			userCardBind.setBindUser(bindUser);

			if (vo.getLockId() != null) {
				userCardBind.setLockId(vo.getLockId());
			}

			if (vo.getOperator() != null) {
				userCardBind.setOperator(vo.getOperator());
			}

			if (vo.getOverTime() != null) {
				long overTime = vo.getOverTime().getTime();
				long currentTime = new Date().getTime();
				userCardBind.setStatus(currentTime < overTime ? 1L : 2L);
			}

			if (vo.getGmtCreated() != null) {
				userCardBind.setGmtCreated(vo.getGmtCreated());
			}

		}

		return userCardBind;
	}

	public static Long exceptionStatus2Long(Status status) {
		if (status == null) {
			return null;
		}
		return status.getStatis();
	}

	public static Status exceptionLong2Status(Long status) {
		return Status.addCoin.creatStatus(status);
	}

	public static FundManualOrder.Status manualLong2Status(Long status) {
		return FundManualOrder.Status.APPLY.creatStatus(status);
	}

	public static Long manualStatus2Long(FundManualOrder.Status status) {
		return status.getStatis();
	}

	public static FundManualOrder.Type manualLong2Type(Long type) {
		return FundManualOrder.Type.PTJL.creatStatus(type);
	}

	public static UserCardBindHistory UserCardBindHistoryRecordVO2UserCardBindHistoryRecord(
			UserCardBindHistoryRecordVO vo) {
		UserCardBindHistory historyRecord = new UserCardBindHistory();

		historyRecord.setUserId(vo.getUserId());
		historyRecord.setAction(vo.getAction());
		historyRecord.setActionTime(vo.getActionTime());

		BankCard bankCard = new BankCard();
		bankCard.setId(vo.getBankId());
	    FundBank bank=new FundBank();
	    bank.setId(vo.getBankId());
	    bank.setName(vo.getName());
	    bankCard.setBank(bank);
		bankCard.setAccountHolder(vo.getBankAccount());
		bankCard.setProvince(vo.getProvince());
		
		bankCard.setCity(vo.getCity());
		bankCard.setSubBranch(vo.getBranchName());
		bankCard.setMownecumId(vo.getMcBankId());
		bankCard.setBankCardNo(vo.getBankNumber());
		
		bankCard.setAccount(vo.getAccount());
		bankCard.setTopAcc(vo.getTopAcc());
		bankCard.setIsFreeze(vo.getIsFreeze());
		bankCard.setFreezeMethod(vo.getFreezeMethod());
		bankCard.setIsBlackList(vo.getIsBlackList());
		historyRecord.setAccount(vo.getAccount());
		historyRecord.setBankCard(bankCard);
		historyRecord.setTopAcc(vo.getTopAcc());
		historyRecord.setIsFreeze(vo.getIsFreeze());
		historyRecord.setFreezeMethod(vo.getFreezeMethod());
		return historyRecord;
	}

	public static UserCardBindHistoryRecordVO UserCardBindHistoryRecord2UserCardBindHistoryRecordVO(
			UserCardBindHistory historyRecord) {
		UserCardBindHistoryRecordVO vo = new UserCardBindHistoryRecordVO();

		vo.setUserId(historyRecord.getUserId());
		vo.setAction(historyRecord.getAction());
		vo.setActionTime(historyRecord.getActionTime());

		vo.setBankId(historyRecord.getBankCard().getId());
		vo.setBankAccount(historyRecord.getBankCard().getAccountHolder());
		vo.setProvince(historyRecord.getBankCard().getProvince());
		vo.setCity(historyRecord.getBankCard().getCity());
		vo.setBranchName(historyRecord.getBankCard().getSubBranch());
		vo.setMcBankId(historyRecord.getBankCard().getMownecumId());
		vo.setBankNumber(historyRecord.getBankCard().getBankCardNo());

		return vo;
	}

	public static FundSuspiciousCard FundSuspiciousCardVO2FundSuspiciousCard(FundSuspiciousCardVO vo) {
		FundSuspiciousCard card = new FundSuspiciousCard();

		if (vo != null) {
			card.setId(vo.getId());

			if (null != vo.getCardNumber()) {
				card.setCardNumber(vo.getCardNumber());
			}

			if (null != vo.getMemo()) {
				card.setMemo(vo.getMemo());
			}

			if (null != vo.getType()) {
				card.setType(CardType.getCardTypeByValue(vo.getType()));
			}

			if (null != vo.getCreatorAccount()) {
				card.setCreatorAccount(vo.getCreatorAccount());
			}

			if (null != vo.getGmtCreated()) {
				card.setCreateTime(vo.getGmtCreated());
			}
			card.setTopAcc(vo.getTopAcc());
			card.setAccount(AccountTool.getRealAccount(vo.getAccount()));
			card.setBankAcc(vo.getBankAcc());
		}

		return card;
	}

	public static FundSuspiciousCardVO FundSuspiciousCard2FundSuspiciousCardVO(FundSuspiciousCard card) {
		FundSuspiciousCardVO vo = new FundSuspiciousCardVO();

		if (card != null) {
			if (null != card.getId()) {
				vo.setId(card.getId());
			}

			if (null != card.getCardNumber()) {
				vo.setCardNumber(card.getCardNumber());
			}

			if (null != card.getMemo()) {
				vo.setMemo(card.getMemo());
			}

			if (null != card.getType()) {
				vo.setType(card.getType().getValue());
			}

			if (null != card.getCreatorAccount()) {
				vo.setCreatorAccount(card.getCreatorAccount());
			}

			if (null != card.getCreateTime()) {
				vo.setGmtCreated(card.getCreateTime());
			}
		}

		return vo;
	}

	public static FundSoloRemarkManualVO transSoloManualEntityToVO(FundSoloRemarkManual manual) {
		FundSoloRemarkManualVO vo = new FundSoloRemarkManualVO();
		vo.setIsused(manual.getIsused());
		vo.setRemark(manual.getRemark());
		vo.setId(manual.getId());
		return vo;
	}

	public static FundSoloRemarkManual transSoloManualVOToEntity(FundSoloRemarkManualVO vo) {
		FundSoloRemarkManual entity = new FundSoloRemarkManual();
		entity.setId(vo.getId());
		entity.setIsused(vo.getIsused());
		entity.setRemark(vo.getRemark());
		return entity;
	}

	public static FundUserRemarkVO transUserRemarkEntityToVO(FundUserRemark manual) {
		FundUserRemarkVO vo = new FundUserRemarkVO();
		vo.setId(manual.getId());
		vo.setUserId(manual.getUserId());
		vo.setRemark(manual.getRemark());
		vo.setGmtCreated(manual.getGmtCreated());
		vo.setGmtModified(manual.getGmtModified());
		vo.setGmtAutounlocked(manual.getGmtAutounlocked());
		vo.setGmtCansetremark(manual.getGmtCansetremark());
		return vo;
	}

	public static FundUserRemark transUserRemarkExtVOToEntity(FundUserRemarkExtVO extVO) {
		FundUserRemark temp = transUserRemarkVOToEntity(extVO);
		temp.setAccount(extVO.getAccount());
		temp.setUserChain(extVO.getUserChain());
		temp.setVipLvl(extVO.getVipLvl());
		temp.setIsFreeze(extVO.getIsFreeze());
		return temp;
	}

	public static FundUserRemark transUserRemarkVOToEntity(FundUserRemarkVO extVO) {
		FundUserRemark remark = new FundUserRemark();
		remark.setId(extVO.getId());
		remark.setRemark(extVO.getRemark());
		remark.setUserId(extVO.getUserId());
		remark.setGmtCreated(extVO.getGmtCreated());
		remark.setGmtModified(extVO.getGmtModified());
		remark.setGmtAutounlocked(extVO.getGmtAutounlocked());
		remark.setGmtCansetremark(extVO.getGmtCansetremark());
		return remark;
	}


}
