package com.winterframework.firefrog.fund.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDto;
import com.winterframework.firefrog.config.web.controller.dto.WithdralDtoUser;
import com.winterframework.firefrog.fund.dao.IFundMowPayDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.fund.dao.vo.FundMowPay;
import com.winterframework.firefrog.fund.dao.vo.FundWithdraw;
import com.winterframework.firefrog.fund.entity.FundOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder;
import com.winterframework.firefrog.fund.entity.FundWithdrawOrder.WithdrawStauts;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo;
import com.winterframework.firefrog.fund.entity.MowCallbackInfo.Status;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.service.IFundWithdrawService;
import com.winterframework.firefrog.fund.service.impl.mow.MownecumWithdrawResponseData;
import com.winterframework.firefrog.fund.web.controller.ConfigUtils;
import com.winterframework.firefrog.fund.web.controller.RedisKey;
import com.winterframework.firefrog.fund.web.dto.UserBankStruc;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;

@Service("FundMowWithdrawService")
public class FundMowWithdrawServiceImpl extends FundMowBaseService {

	@Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;

	@Resource(name = "fundWithdrawService")
	private IFundWithdrawService fundService;
	@Resource(name = "fundMowPayDao")
	private IFundMowPayDao fundMowPayDao;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@PropertyConfig(value = "platform.name")
	private String platformName;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	public FundMowWithdrawServiceImpl(){}
	@Override
	public MownecumWithdrawResponseData apply(FundOrder fundOrder) throws Exception {

		FundWithdrawOrder order = (FundWithdrawOrder) fundOrder;

		UserBankStruc card = (UserBankStruc) DataConverterUtil.convertJson2Object(order.getCardStr(),
				new UserBankStruc().getClass());

		FundMowPay mowPay = new FundMowPay();
		mowPay.setFfBankId(card.getBankId());
		mowPay.setFfCompanyId(companyId);
		mowPay.setFfAmount(order.getWithdrawAmt());
		mowPay.setFfCardNum(String.valueOf(card.getBankNumber()));
		mowPay.setFfCardName(card.getBankAccount());
		mowPay.setFfIssueBankName(card.getBranchName());
		mowPay.setApplyTime(new Date());
		mowPay.setExSn(order.getSn());
		mowPay.setExId(order.getId());
		fundMowPayDao.saveMowPay(mowPay);
		return null;

	}
	//修改redid的体现次数
		private void changeWithdrawTime(FundWithdrawOrder withdraw,WithdralDto wd,boolean ifAdd){
			String withdrawCountRedisKey = RedisKey.createDateKey(module, keyWithdrawTimes, withdraw.getApplyUser()
					.getId());
			String availWithdrawCount = redisSerive.get(withdrawCountRedisKey);

			if (StringUtils.isNotBlank(availWithdrawCount) && availWithdrawCount.length() > 8) {

				String[] _value = availWithdrawCount.split("\\|");

				StringBuilder str = new StringBuilder();
				Long count = Long.parseLong(_value[0]) + (ifAdd?1:-1);
				logger.debug("when callback bf:"+_value[0]+" after:"+count);
			
				redisSerive.set(withdrawCountRedisKey, str.append(count).append("|").append(_value[1]).toString());
			} else {
				redisSerive.set(withdrawCountRedisKey, 1 + "|" + (ifAdd?1:0));
			}
		}
	@Override
	protected void callbackFaild(MowCallbackInfo info) throws Exception {
		info.setStatus(Status.failed);
		
		FundWithdraw fundWithdraw = fundWithdrawDao.queryFundWithdrawByMowNum(info.getCompanyOrderNum(),
				info.getMowOrderNum());
		if (fundWithdraw == null) {
			//脏数据
			return;
		}
		int lockCount = fundWithdrawDao.updateTargetStatusToLock(fundWithdraw.getId(),WithdrawStauts.APPROVED);
		if(lockCount==0){
			logger.info("mowcum 短時間重复回调:" + info.getCompanyOrderNum());
			return;
		}else{
			fundWithdrawDao.updateFundWithdrawMow(fundWithdraw.getId(),Status.failed.getValue(), info.getAmount(),
					info.getDetail(),info.getOperatingTime());
			FundWithdrawOrder entity = fundWithdrawDao.queryById(fundWithdraw.getId());
			WithdralDtoUser withDraw = configUtils.getWITHDRAW();
			WithdralDto wd = withDraw.getUser();
			changeWithdrawTime(entity,wd,false);
	
			FundWithdrawOrder order = new FundWithdrawOrder(FundModel.FD.CWXX.ITEMS.FAILED);
			User applyUser = new User();
			applyUser.setId(fundWithdraw.getUserId());
			order.setStauts(WithdrawStauts.FAIL);
			order.setSn(fundWithdraw.getSn());
			order.setApplyUser(applyUser);
			order.setWithdrawAmt(fundWithdraw.getWithdrawAmt());
			fundService.callChange(order,null);
		}
	}

	/* MOW 回傳成功
	 * 
	 * @see com.winterframework.firefrog.fund.service.impl.FundMowBaseService#callbackSuccessful(com.winterframework.firefrog.fund.entity.MowCallbackInfo)
	 * REVISION　HISTORY
	 * --------------------------------------------------------
	 * 2016.05.16 David Wu Changed : #8069 FH4.0后台 - 提现订单查询不到 　因ＭＰ連線失敗觸發　roll back
	 */
	@Override
	protected void callbackSuccessful(MowCallbackInfo info) throws Exception {
		FundWithdraw fundWithdraw = fundWithdrawDao.queryFundWithdrawByMowNum(info.getCompanyOrderNum(),
				(info.getMowOrderNum()));
		if (fundWithdraw == null) {
			//脏数据
			return;
		}
		if (WithdrawStauts.APPLY.getValue().equals(fundWithdraw.getStatus())) {
			logger.info("mowcum 重复回调:" + info.getCompanyOrderNum());
			return;
		}
		int lockCount = fundWithdrawDao.updateTargetStatusToLock(fundWithdraw.getId(),WithdrawStauts.APPROVED);
		if(lockCount==0){
			logger.info("mowcum 短時間重复回调:" + info.getCompanyOrderNum());
			return;
		}else{
			FundWithdrawOrder order = new FundWithdrawOrder(FundModel.FD.CWXX.ITEMS.SUCCESS);
			User applyUser = new User();
			applyUser.setId(fundWithdraw.getUserId());
			order.setStauts(WithdrawStauts.SUCCESS);
			order.setSn(fundWithdraw.getSn());
			order.setApplyUser(applyUser);
			order.setWithdrawAmt(info.getAmount());
	
			Map<String, String> map = new HashMap<String, String>();
			map.put("platform", platformName);
			Double amount = (Double.valueOf(order.getWithdrawAmt()) / 10000);
			map.put("drawMoney", String.valueOf(amount));
			//2016.05.16 #8069 FH4.0后台 - 提现订单查询不到 Start
			//msgToMQ.addMessageToMq(applyUser, NoticeTaskEnum.WithdrawSuccessful, map);
			try{
				msgToMQ.addMessageToMq(applyUser, NoticeTaskEnum.WithdrawSuccessful, map);
			}catch(Exception e){
				logger.error("MQ Connect Error :" ,e );
			}			
			//2016.05.16 #8069 FH4.0后台 - 提现订单查询不到 End
	
			fundService.callChange(order,null);
			
			fundWithdrawDao.updateFundWithdrawMow(fundWithdraw.getId(), Status.sucessful.getValue(), info.getAmount(),
					info.getDetail(),info.getOperatingTime());
		}
		
	}

	@Override
	protected void callbackIncomplete(MowCallbackInfo info) throws Exception {
		info.setStatus(Status.incomplete);
		FundWithdraw fundWithdraw = fundWithdrawDao.queryFundWithdrawByMowNum(info.getCompanyOrderNum(),
				(info.getMowOrderNum()));
		if (fundWithdraw == null) {
			//脏数据
			return;
		}
		
		int lockCount = fundWithdrawDao.updateTargetStatusToLock(fundWithdraw.getId(),WithdrawStauts.APPROVED);
		if(lockCount==0){
			logger.info("mowcum 短時間重复回调:" + info.getCompanyOrderNum());
			return;
		}else{
			FundWithdrawOrder order = new FundWithdrawOrder(FundModel.FD.CWXX.ITEMS.PART_REDUCE);
			User applyUser = new User();
			applyUser.setId(fundWithdraw.getUserId());
			order.setStauts(WithdrawStauts.PART);
			order.setApplyUser(applyUser);
			if (info.getAmount() != null) {
				order.setWithdrawAmt(fundWithdraw.getWithdrawAmt());
				order.setRealWithdrawAmt(info.getAmount());
			}
			fundService.callChange(order,null);
			
			fundWithdrawDao.updateFundWithdrawMow(fundWithdraw.getId(), Status.incomplete.getValue(), info.getAmount(),
					info.getDetail(),info.getOperatingTime());
		}

	}
	@Resource(name = "RedisClient")
	private RedisClient redisSerive;
	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "rediskey_withdraw_times")
	private String keyWithdrawTimes;
	@Autowired
	private ConfigUtils configUtils;
}
