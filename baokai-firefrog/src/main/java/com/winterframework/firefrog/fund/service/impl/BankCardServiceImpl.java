/**   
* @Title: BankCardService.java 
* @Package com.winterframework.firefrog.fund.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-6-28 下午6:01:45 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.beginmession.service.BeginMissionService;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.IFundBankDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.exception.FundBankcardBindLimitException;
import com.winterframework.firefrog.fund.exception.FundBankcardLockedException;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.fund.service.IUserBankLockedService;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardBindRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: BankCardService 
* @Description: 银行卡管理Service 
* @author Denny 
* @date 2013-6-28 下午6:01:45 
*  
*/
@Service("bankCardServiceImpl")
public class BankCardServiceImpl implements IBankCardService {

	@Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
	@Resource(name = "userBankLockedServiceImpl")
	private IUserBankLockedService userBankLockedService;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;
	
	@Resource(name = "fundBankDaoImpl")
	private IFundBankDao fundBankDao;
	
	private final Long NORMAL_CARD = 0L;
	
	private final Long ALIPAY_CARD = 1L;
	
	private static Logger log = LoggerFactory.getLogger(BankCardServiceImpl.class);

	@Autowired
	private BeginMissionService beginMissionService;
	
	@Override
	public void applyBind(BankCard bankCard) {
		long userId = bankCard.getBindingUser().getId();
		if(bankCard.getBindcardType()!= null && bankCard.getBindcardType()==ALIPAY_CARD){
			if (userBankLockedService.isFirstLock(userId,bankCard.getBindcardType())) {
				userBankLockedService.addLocked(userId,bankCard.getBindcardType());
			}
				bankCardDao.addBankCard(bankCard);
				addCardBindHistoryRecord(1L, userId, bankCard,bankCard.getBindcardType(),bankCard.getNickName());
		}else{
			if (userBankLockedService.isFirstLock(userId,bankCard.getBindcardType())) {
	
				bankCardDao.addBankCard(bankCard);
	
				addCardBindHistoryRecord(1L, userId, bankCard,bankCard.getBindcardType(),bankCard.getNickName());
				
				//新手活動第一次綁卡,給予綁卡獎勵,
				beginMissionService.bindCardAward(userId);
				
				//notice_银行卡绑定成功
				userBankLockedService.addLocked(userId,bankCard.getBindcardType());
			} else if (this.isLessThanConfiguredBindQuantity(userId,bankCard.getBindcardType())) {
				if (userBankLockedService.isTimeOut(userId,bankCard.getBindcardType()) && userBankLockedService.isHaveCard(userId, bankCard.getBindcardType()) ) {
					throw new FundBankcardLockedException();
				} else {
	
					bankCardDao.addBankCard(bankCard);
	
					addCardBindHistoryRecord(1L, userId, bankCard,bankCard.getBindcardType(),bankCard.getNickName());
					//notice_银行卡绑定成功
					beginMissionService.checkBindCardMissionAndCancel(userId);
				}
			} else {
				throw new FundBankcardBindLimitException();
			}
		}
	}
	

	@Override
	public void updateBind(BankCard bankCard) {
		long userId = bankCard.getBindingUser().getId();
		if(bankCard.getBindcardType()!=null && bankCard.getBindcardType()==ALIPAY_CARD){
			bankCardDao.updateBankCard(bankCard);
			bankCard.setId(bankCard.getBank().getId());
			addCardBindHistoryRecord(3L, userId, bankCard,bankCard.getBindcardType(),bankCard.getNickName());
		}
	}
	public boolean  checkBind(String bankCard,Long userId,String bankAccount){
		UserBank bank=new UserBank();
		bank.setBankNumber(bankCard);
		bank.setUserId(userId);
		bank.setBankAccount(bankAccount);
		return bankCardDao.getCountByEntity(bank)>0;
	};
	
	@Override
	public void unbind(long bindId,long userId, long bankId, long mcBankId, long bindcardType,String nickName) {
		//銀行卡才需要判斷
		if(NORMAL_CARD.equals(bindcardType)){
			unbindNormalCard(bindId, userId, nickName);
		}else if(ALIPAY_CARD.equals(bindcardType)){
			unbindAlipayCard(bindId, userId, nickName);
		}
	}
	
	private void unbindNormalCard(long bindId,long userId,String nickName){
		if (userBankLockedService.isTimeOut(userId,NORMAL_CARD)) {
			throw new FundBankcardLockedException();
		}
		//1、添加历史绑卡记录
		this.addCardBindHistoryRecord(2L, userId, bindId,NORMAL_CARD,nickName);

		//2、删除绑卡
		bankCardDao.removeBankCard(bindId);
		
		//3.检查新手任务绑卡异动时间，若不符则取消任务
		beginMissionService.checkBindCardMissionAndCancel(userId);	
		
	}
	
	private void unbindAlipayCard(long bindId,long userId,String nickName){
		//1、添加历史绑卡记录
		this.addCardBindHistoryRecord(2L, userId, bindId,ALIPAY_CARD,nickName);
		//2、删除绑卡
		bankCardDao.removeBankCard(bindId);
	}

	private void addCardBindHistoryRecord(long action, long userId, long bindId ,long bindcardType,String nickName) {
		UserBank userBank = bankCardDao.getById(bindId);
		UserCardBindHistory userCardBindHistoryRecord = new UserCardBindHistory();
		BankCard bankCard = new BankCard();
		bankCard.setId(userBank.getBankId());
		bankCard.setAccountHolder(userBank.getBankAccount());
		bankCard.setProvince(userBank.getProvince());
		bankCard.setCity(userBank.getCity());
		bankCard.setSubBranch(userBank.getBranchName());
		bankCard.setMownecumId(userBank.getMcBankId());
		bankCard.setBankCardNo(userBank.getBankNumber());
		userCardBindHistoryRecord.setAction(action);
		userCardBindHistoryRecord.setActionTime(new Date());
		userCardBindHistoryRecord.setUserId(userId);
		userCardBindHistoryRecord.setBankCard(bankCard);
		userCardBindHistoryRecord.setBindcardType(bindcardType);
		userCardBindHistoryRecord.setNickName(nickName);
		bankCardDao.addBankCardHistoryRecord(userCardBindHistoryRecord);
	}
	
	private void addCardBindHistoryRecord(long action, long userId, BankCard bankCard, long bindcardType,String nickName) {
		UserCardBindHistory userCardBindHistoryRecord = new UserCardBindHistory();
		userCardBindHistoryRecord.setAction(action);
		userCardBindHistoryRecord.setActionTime(new Date());
		userCardBindHistoryRecord.setUserId(userId);
		userCardBindHistoryRecord.setBankCard(bankCard);
		userCardBindHistoryRecord.setBindcardType(bindcardType);
		userCardBindHistoryRecord.setNickName(nickName);
		bankCardDao.addBankCardHistoryRecord(userCardBindHistoryRecord);
	}


	@Override
	public List<BankCard> queryBoundBankCard(long userId,String cardNumber) {
		return bankCardDao.getBoundBankCard(userId,cardNumber);
	}
	
	@Override
	public List<BankCard> queryBoundBankCardByRequest(BankCardQueryRequest request) {
		return bankCardDao.getBoundBankCardByRequest(request);
	}
	

	@Override
	public boolean isFirstBind(long userId) {
		return (this.queryBoundBankCard(userId,null).size() == 0);
	}

	@Override
	public boolean isLessThanConfiguredBindQuantity(long userId,long bindcardType) {
		String v=configService.getConfigValueByKey("fund", "chargeCoute");
		int configuredBindQuantity = Integer.parseInt(StringUtils.isEmpty(v)?"10":v);
		BankCardQueryRequest br = new BankCardQueryRequest();
		br.setUserId(userId);
		br.setBindCardType(bindcardType);
		List<BankCard> boundBankCards = this.queryBoundBankCardByRequest(br);
		int boundCardNum = boundBankCards.size();
		if(configuredBindQuantity==-1){
			return true;
		}
		return boundCardNum < configuredBindQuantity;
	}

	@Override
	public Page<UserCardBind> searchUserCardBindRecords(PageRequest<BankCardBindRecordQueryDTO> pageRequest)
			throws Exception {
		return bankCardDao.searchUserCardBindRecords(pageRequest);
	}

	@Override
	public Page<UserCardBindHistory> searchUserCardBindHistoryRecords(
			PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest) throws Exception {
		return bankCardDao.searchUserCardBindHistoryRecords(pageRequest);
	}


	@Override
	public boolean checkAlipayStatus(BankCard bankCard) throws Exception {
		//是否可以綁卡
		boolean result = false;
		try {
			List<FundBank> banks = fundBankDao.queryByCode(30L);
			for(FundBank bank : banks){
				if(bank.getVersion() == 0 && bank.getDeposit() == 1 ){ //支付寶個人版才能綁卡
					result=true;
				}
			}
		} catch (Exception e) {
			log.error("確認支付寶狀態有異常：", e);
		}
		return result;
	}

}
