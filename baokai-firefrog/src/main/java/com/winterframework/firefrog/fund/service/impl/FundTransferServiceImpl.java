package com.winterframework.firefrog.fund.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.config.web.controller.dto.TransfertoUser;
import com.winterframework.firefrog.fund.dao.IFundTransferDao;
import com.winterframework.firefrog.fund.dao.impl.FundTransferDaoImpl;
import com.winterframework.firefrog.fund.dao.vo.FundTransfer;
import com.winterframework.firefrog.fund.dao.vo.FundTransferView;
import com.winterframework.firefrog.fund.entity.FundTransferOrder;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.enums.FundModel;
import com.winterframework.firefrog.fund.enums.MethodEnum;
import com.winterframework.firefrog.fund.enums.FundModel.TF;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.service.IFundAtomicOperationService;
import com.winterframework.firefrog.fund.service.IFundTransferService;
import com.winterframework.firefrog.fund.util.ISNGenerator;
import com.winterframework.firefrog.fund.web.controller.RedisKey;
import com.winterframework.firefrog.fund.web.controller.vo.CountPage;
import com.winterframework.firefrog.fund.web.controller.vo.FundGameVo;
import com.winterframework.firefrog.fund.web.dto.FundTransferCountResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferDetailResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferInitResponse;
import com.winterframework.firefrog.fund.web.dto.FundTransferRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.FundTransferRequest;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.subsys.dao.ISubSystemDao;
import com.winterframework.firefrog.subsys.vo.SubSystem;
import com.winterframework.firefrog.user.entity.BaseUser;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.orm.dal.ibatis3.BaseManager;

@Service("fundTransferServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FundTransferServiceImpl extends BaseManager<FundTransferDaoImpl, FundTransfer> implements
		IFundTransferService {

	@Resource(name = "userProfileServiceImpl")
	public IUserProfileService userProfileService;

	@Resource(name = "fundTransferDaoImpl")
	public IFundTransferDao fundTransferDao;
	@Resource(name = "fundChangeServiceImpl")
	protected IFundAtomicOperationService fundChangeService;

	@Resource(name = "SNUtil")
	private ISNGenerator snUtil;

	@Resource(name = "configServiceImpl")
	private IConfigService configService;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "rediskey_transfer_amt")
	private String keyTransferAmt;
	@PropertyConfig(value = "rediskey_transfer_time")
	private String keyTrasferTime;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@PropertyConfig(value = "platform.name")
	private String platformName;

	@Resource(name = "SubSystemDaoImpl")
	private ISubSystemDao subSystemDao;
	
	
	/**
	* Title: saveFundTransferOrder
	* Description: 更改双方的资金bal信息，保存资金变动记录
	* @param fundTransferOrder 
	 * @throws Exception 
	* @see com.winterframework.firefrog.fund.service.IFundTransferService#transferFund(com.winterframework.firefrog.fund.entity.FundTransferOrder) 
	*/
	@Override
	public void transferFund(FundTransferOrder fundTransferOrder) throws Exception {
		long userId = fundTransferOrder.getApplyUser().getId();
		fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.SOSX, userId));

		fundTransferDao.saveUserFundTransfer(fundTransferOrder);
		updateFund(fundTransferOrder);

		
	//	SendMsg (fundTransferOrder.getTransferAmt()==null?0L:fundTransferOrder.getTransferAmt(),fundTransferOrder.getApplyUser());
		
		String transCountRedisKey = RedisKey.createDateKey(module, keyTransferAmt, userId);

		String amtStr = redisSerive.get(transCountRedisKey);
		if (amtStr != null) {
			redisSerive.set(transCountRedisKey,
					String.valueOf(Long.parseLong(amtStr) + fundTransferOrder.getTransferAmt()));
		} else {
			redisSerive.set(transCountRedisKey, String.valueOf(fundTransferOrder.getTransferAmt()));
		}
		Long isUpper = fundTransferOrder.getIsUpward();
		String transferCountRedisKey = RedisKey.createDateKey(module, keyTrasferTime + isUpper.longValue(), userId);
		amtStr = redisSerive.get(transferCountRedisKey);
		if (amtStr == null) {
			redisSerive.set(transferCountRedisKey, String.valueOf("1"));
		} else {
			redisSerive.set(transferCountRedisKey, String.valueOf(Long.parseLong(amtStr) + 1));
		}
	}
	
	public void SendMsg ( Long Amount,  BaseUser baseUser) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", platformName);
		BigDecimal transferAmt=new BigDecimal(Amount); 
		String amount =transferAmt.divide(new BigDecimal(10000L),2,BigDecimal.ROUND_HALF_UP).toString();
		map.put("transferMoney", amount);
		msgToMQ.addMessageToMq(baseUser, NoticeTaskEnum.TransferSuccessful, map);
	}
	
	public void updateFund(FundTransferOrder fundOrder) throws FundChangedException {
		String userChin = fundOrder.getUserChain();
		String account = "/"+fundOrder.getReceiveUser().getAccount()+"/";
		
		if (StringUtils.contains(userChin, account)) {
			//如果是像上级转账
			log.error("--IF向上轉帳");
			fundChangeService.action(new FundGameVo(FundModel.TF.TAIX.ITEMS.WPXX, fundOrder.getApplyUser().getId(),
					fundOrder.getTransferAmt(), fundOrder.getSn(), true,"转入上级"));
			fundChangeService.action(new FundGameVo(FundModel.TF.TAIX.ITEMS.RRXX, fundOrder.getReceiveUser().getId(),
					fundOrder.getTransferAmt(), fundOrder.getSn(), true,"下级 "+fundOrder.getApplyUser().getAccount()+"支付"));

		}else{
			log.error("--E向下轉帳");
			fundChangeService.action(new FundGameVo(FundModel.TF.TAIX.ITEMS.SOSX, fundOrder.getApplyUser().getId(),
					fundOrder.getTransferAmt(), fundOrder.getSn(), true,"转入下级"+fundOrder.getReceiveUser().getAccount()));
			fundChangeService.action(
					new FundGameVo(FundModel.TF.TAIX.ITEMS.BIRX, fundOrder.getReceiveUser().getId(),fundOrder.getTransferAmt(), fundOrder.getSn(), true,"上级 支付"));

		}
	}
	
	
	@Override
	@Transactional(timeout=20,rollbackFor = Exception.class)
	public long transferSubsystemFund (FundTransferOrder fundTransferOrder, int direct, String token) throws Exception {
		long userId = fundTransferOrder.getApplyUser().getId();
		
		if (fundTransferOrder.getSn() == null){
			switch (direct)
			{
				case 0:
				case 2:	
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTP, userId));
					break;
				case 1:
				case 3:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTM, userId));
					break;
				case 4:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.GM.RSXX.ITEMS.RDAX, userId));
					break;
			//	case 5:
				//	fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFBU, userId));
			//		break;
			
			}
		}
	
		updateSubsystemFund (fundTransferOrder, direct, token);
		
		UserFund fund = fundChangeService.getUserFund(userId);
		Long balance = fund.getBal();
		return balance;
	}
	
	public void updateSubsystemFund (FundTransferOrder fundOrder, int direct , String token) throws Exception {
		String SubName = "";
		if (token  != null){
			SubSystem subRequest = new SubSystem();
			subRequest.setToken(token);
			SubSystem subRespone = subSystemDao.getName(subRequest);
			
			if (subRespone == null)
				throw new Exception();
			SubName = subRespone.getName();
		}
		switch (direct){
			case 0:{  // 轉出 到 子系統
				//如果是像上级转账
				String Note = "转至 子 " + SubName + "系統" + fundOrder.getReceiveUser().getAccount() ;
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTP, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														Note );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 1:{ // 子系統轉回
				String Note = SubName + "子系統转回 " + fundOrder.getReceiveUser().getAccount() + SubName ;
				FundGameVo trannsferVo = new FundGameVo(FundModel.TF.TAIX.ITEMS.TFTM, 
															fundOrder.getApplyUser().getId(),
															fundOrder.getTransferAmt(),
															fundOrder.getSn(),
															true,
															Note);
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 2:{ // 轉帳錯誤 扣除 轉入金
				//如果是像上级转账
				String Node = SubName + "转帐出错 扣除 转入金" + fundOrder.getReceiveUser().getAccount() + SubName;
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTP, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														Node);
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 3:{ // 轉帳錯誤 返還  轉出金
				//如果是像上级转账
				String Node = SubName + "转帐出错 补回 转出金" + fundOrder.getReceiveUser().getAccount() + SubName;
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTM, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														Node );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}
			break;
			case 4:{ // PT 反點
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				FundGameVo trannsferVo = new  FundGameVo(FundModel.GM.RSXX.ITEMS.RDAX, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														"PT返点 "+ "\n" + sdf.format(fundOrder.getRetTime()) );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}
			break;
			/*case 5:{ // PT 分紅
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFBU, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														"PT 轉帳 分紅" + fundOrder.getReceiveUser().getAccount() );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}
			break;*/
		}
	}
	
	
	@Override
	@Transactional(timeout=20,rollbackFor = Exception.class)
	public long transferSubsystemFundMW (FundTransferOrder fundTransferOrder, int direct, String token) throws Exception {
		long userId = fundTransferOrder.getApplyUser().getId();
		
		if (fundTransferOrder.getSn() == null){
			switch (direct)
			{
				case 0:
				case 2:	
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTX, userId));
					break;
				case 1:
				case 3:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TXTM, userId));
					break;
				case 4:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.GM.RSXX.ITEMS.RDAX, userId));
					break;
			//	case 5:
				//	fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFBU, userId));
			//		break;
				case 10:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.PM.PGFX.ITEMS.PGFX, userId));
					break;
			
			}
		}
	
		updateSubsystemTransferFund (fundTransferOrder, direct, token);
		
		UserFund fund = fundChangeService.getUserFund(userId);
		Long balance = fund.getBal();
		return balance;
	}
	
	
	
	public void updateSubsystemTransferFund (FundTransferOrder fundOrder, int direct , String token) throws Exception {
		String SubName = "";
		if (token  != null){
			SubSystem subRequest = new SubSystem();
			subRequest.setToken(token);
			SubSystem subRespone = subSystemDao.getName(subRequest);
			
			if (subRespone == null)
				throw new Exception();
			SubName = subRespone.getName();
		}
		switch (direct){
			case 0:{  // 轉出 到 子系統
				//如果是像上级转账
				String Note = "记录转入FHX" ;
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTX, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														Note );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 1:{ // 子系統轉回
				String Note = "转出FHX";
				FundGameVo trannsferVo = new FundGameVo(FundModel.TF.TAIX.ITEMS.TXTM, 
															fundOrder.getApplyUser().getId(),
															fundOrder.getTransferAmt(),
															fundOrder.getSn(),
															true,
															Note);
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
//			case 2:{ // 轉帳錯誤 扣除 轉入金
//				//如果是像上级转账
//				String Node = SubName + "转帐出错 扣除 转入金" + fundOrder.getReceiveUser().getAccount() + SubName;
//				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTP, 
//														fundOrder.getApplyUser().getId(),
//														fundOrder.getTransferAmt(), 
//														fundOrder.getSn(),
//														true,
//														Node);
//				trannsferVo.setSn(fundOrder.getSn());
//				fundChangeService.action(trannsferVo);
//			}break;
//			case 3:{ // 轉帳錯誤 返還  轉出金
//				//如果是像上级转账
//				String Node = SubName + "转帐出错 补回 转出金" + fundOrder.getReceiveUser().getAccount() + SubName;
//				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTM, 
//														fundOrder.getApplyUser().getId(),
//														fundOrder.getTransferAmt(), 
//														fundOrder.getSn(),
//														true,
//														Node );
//				trannsferVo.setSn(fundOrder.getSn());
//				fundChangeService.action(trannsferVo);
//			}
//			break;
//			case 4:{ // PT 反點
//				
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				FundGameVo trannsferVo = new  FundGameVo(FundModel.GM.RSXX.ITEMS.RDAX, 
//														fundOrder.getApplyUser().getId(),
//														fundOrder.getTransferAmt(), 
//														fundOrder.getSn(),
//														true,
//														"PT返点 "+ "\n" + sdf.format(fundOrder.getRetTime()) );
//				trannsferVo.setSn(fundOrder.getSn());
//				fundChangeService.action(trannsferVo);
//			}
//			break;
			/*case 5:{ // PT 分紅
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFBU, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														"PT 轉帳 分紅" + fundOrder.getReceiveUser().getAccount() );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}
			break;*/
			case 6:{  // 轉出 到 子系統
				//如果是像上级转账
				String Note = "记录转入万国" ;
				FundGameVo trannsferVo = new  FundGameVo(FundModel.TF.TAIX.ITEMS.TFTG, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														Note );
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 7:{ // 子系統轉回
				String Note = "转出万国";
				FundGameVo trannsferVo = new FundGameVo(FundModel.TF.TAIX.ITEMS.TGTM, 
															fundOrder.getApplyUser().getId(),
															fundOrder.getTransferAmt(),
															fundOrder.getSn(),
															true,
															Note);
				trannsferVo.setAmountBal(fundOrder.getAmountBal());
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 8:{ // WG 反點
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				FundGameVo trannsferVo = new  FundGameVo(FundModel.GM.RSXX.ITEMS.RDWX, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														"万国返点 ");
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 9:{ // FHX 反點
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				FundGameVo trannsferVo = new  FundGameVo(FundModel.GM.RSXX.ITEMS.RDXX, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														"FHX返点 "+fundOrder.getNote());
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
			case 10:{ // FHX 活動禮金
				
				FundGameVo trannsferVo = new  FundGameVo(FundModel.PM.PGFX.ITEMS.PGFX, 
														fundOrder.getApplyUser().getId(),
														fundOrder.getTransferAmt(), 
														fundOrder.getSn(),
														true,
														fundOrder.getNote());
				trannsferVo.setAmountBal(0l);
				trannsferVo.setSn(fundOrder.getSn());
				fundChangeService.action(trannsferVo);
			}break;
		}
	}
	
	
	@Override
	@Transactional(timeout=20,rollbackFor = Exception.class)
	public String transferSubsystemGiftMoneyMW (FundTransferOrder fundTransferOrder, int direct, String token) throws Exception {
		long userId = fundTransferOrder.getApplyUser().getId();
		
		if (fundTransferOrder.getSn() == null){
			switch (direct)
			{
				case 0:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TFTX, userId));
					break;
				case 1:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.TF.TAIX.ITEMS.TXTM, userId));
					break;
				case 10:
					fundTransferOrder.setSn(snUtil.createBusinessSn(FundModel.PM.PGFX.ITEMS.PGFX,userId));
					break;			
			}
		}
	
		updateSubsystemTransferFund (fundTransferOrder, direct, token);
		
		return fundTransferOrder.getSn();
	}

	@Override
	public void setEntityDao(FundTransferDaoImpl entityDao) {
		this.entityDao = entityDao;

	}

	@Override
	public CountPage<FundTransferOrder> searchFundTransferOrder(PageRequest<FundTransferRecordQueryDTO> pageReqeust)
			throws Exception {
		return fundTransferDao.searchFundTransferOrder(pageReqeust);
	}

	@Override
	public FundTransferInitResponse init(long userId) throws Exception {
		FundTransferInitResponse initInfo = new FundTransferInitResponse();
		String transfer = configService.getConfigValueByKey("fund", "transfer");
		TransfertoUser tu = JsonMapper.nonDefaultMapper().fromJson(transfer, TransfertoUser.class);
		initInfo.setTransfer(transfer);
		initInfo.setTransferStruc(tu);
		UserFund fund = fundChangeService.getUserFund(userId);
		initInfo.setBal(fund.getBal());
		initInfo.setUnavailBal(fund.getDisableAmt() > fund.getBal()? fund.getBal():fund.getDisableAmt());
	
		String transferCountRedisKey = RedisKey.createDateKey(module, keyTrasferTime+1, userId);
		String transferCountRedisKey2 = RedisKey.createDateKey(module, keyTrasferTime+0, userId);
		String amtStr2 = redisSerive.get(transferCountRedisKey);
		long up = amtStr2 == null ? 0 : Long.parseLong(amtStr2);
		String amtStr3 = redisSerive.get(transferCountRedisKey2);
		long down = amtStr3 == null ? 0 : Long.parseLong(amtStr3);

		////上线
		initInfo.setAvaliTransferTimeUp(up);
		initInfo.setAvaliTransferTimeDown(down);

		return initInfo;
	}

	public Page<FundTransferDetailResponse> userFundTransfer(Request<FundTransferRequest> request) throws Exception {
		List<FundTransferDetailResponse> details = new ArrayList<FundTransferDetailResponse>();
		List<FundTransferView> views = fundTransferDao.queryViewFundTrasfer(request.getBody().getParam());

		FundTransferCountResponse count = new FundTransferCountResponse();
		for (FundTransferView fundTransferView : views) {
			String account = fundTransferView.getAccount();
			count.add(fundTransferView);
			count.setStartDate(request.getBody().getParam().getStartDate());
			count.setEndDate(request.getBody().getParam().getEndDate());
			count.setVipLevel(fundTransferView.getVipLevel());
			FundTransferDetailResponse detail = new FundTransferDetailResponse(fundTransferView);
			detail.setStartDate(request.getBody().getParam().getStartDate());
			detail.setEndDate(request.getBody().getParam().getEndDate());
			details.add(detail);
		}
		Page<FundTransferDetailResponse> page = new Page<FundTransferDetailResponse>(views.size());
		page.setPageScope(request.getBody().getPager().getStartNo(), request.getBody().getPager().getEndNo());
		page.setOtherCount(new HashMap());
		page.getOtherCount().put("count", count);
		page.setResult(details);
		return page;
	}

}
