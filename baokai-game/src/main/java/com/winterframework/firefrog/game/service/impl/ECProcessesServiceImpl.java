/**
 * 
 */
package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameECLogDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameECLog;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueLog;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.exception.GameIssueNotExistErrorException;
import com.winterframework.firefrog.game.service.IECProcessesService;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.ec.utils.ECNoticeBeforeSaleTime;
import com.winterframework.firefrog.game.service.ec.utils.ECNoticeUpdate;
import com.winterframework.firefrog.game.service.ec.utils.ECUtils;
import com.winterframework.firefrog.game.service.ec.utils.GetCodeJson;
import com.winterframework.firefrog.game.service.ec.utils.NumberJson;
import com.winterframework.firefrog.game.web.dto.ECIssueCheckReponse;
import com.winterframework.firefrog.game.web.dto.ECIssueCheckReponseIssueStruc;
import com.winterframework.firefrog.game.web.dto.GetIssueDrawResult;
import com.winterframework.modules.security.DigestUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * @author charleswang
 * 
 */
@Service("eCProcessesServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class ECProcessesServiceImpl implements IECProcessesService {

	private Logger log = LoggerFactory.getLogger(ECProcessesServiceImpl.class);

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueServiceImpl;

	@Resource(name = "EClotteryMap")
	private Map<String, String> ECMap;// ec彩种名称 转换为游戏彩种id

	@Resource(name = "EClotteryNameMap")
	private Map<String, String> ECNameMap;// game彩种id转换为ec彩种代码

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig("url.ec.check")
	private String ecCheckUrl;

	@PropertyConfig("url.ec.correctCheck")
	private String correctCheck;

	@PropertyConfig("url.ec.sendMessageCheck")
	private String sendMessageCheck;

	@PropertyConfig("url.ec.getIssueDrawResult")
	private String getIssueDrawResult;

	@PropertyConfig(value = "url.ec.connect")
	private String serverPath;

	@PropertyConfig(value = "url.local.connect")
	private String gameServerPath;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;

	@Resource(name = "gameECLogDaoImpl")
	private IGameECLogDao gameECLogDao;

	@PropertyConfig("customerId")
	private String customerId;

	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;

	/*
	 * 正常获取开奖通知接口
	 * 
	 * @see com.winterframework.firefrog.game.service.IECProcessesService#
	 * receivedLotteryAwardNumber
	 * (com.winterframework.firefrog.game.web.dto.ECReceivesNumbersRequest)
	 */
	@Override
	public String receivedLotteryAwardNumber(Map<String, String> request, String reqPath) throws Exception {
		String disposeMemo = "EC开奖通知";
		request.put("type", String.valueOf(ECUtils.EC_NOTICES_NORMAL_TYPE));
		String status = new String();
		long id = 0L;
		try {
			// 记录EC日志入库
			id = gameECLogDao.addGameECLog(ECUtils.getMapToEntity(request));
			log.info(disposeMemo + "入库,id为" + id);
			String lotteryStr = ECMap.get(request.get("lottery"));
			Long lotteryId = Long.valueOf(lotteryStr);
			String issueCodeStr = request.get("issue");
			// 转换奖期格式 开奖中心格式to4.0格式
			String gameWebIsuue = ECUtils.getIssueFromEC(lotteryId, issueCodeStr);
			// 回传请求参数
			String params = ECUtils.echoECData(request, String.valueOf(id));
			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);
			StringBuffer sb = new StringBuffer(params);

			String number = request.get("number").replaceAll("%2C", ",").replaceAll("%2B", "+");
			Date earliestTime = null;
			try {
				earliestTime = DateUtils.parse(request.get("earliestTime"), "yyyyMMddHHmmss");
			} catch (Exception e) {
				earliestTime = new Date(Long.valueOf(request.get("earliestTime")) * 1000);
			}

			Date verifiedTime = null;
			try {
				verifiedTime  = DateUtils.parse(request.get("verifiedTime"), "yyyyMMddHHmmss");
			} catch (Exception e) {
				verifiedTime = new Date(Long.valueOf(request.get("verifiedTime")) * 1000);
			}
			// EC验证字符串
			sb.append("&safestr=").append(qs);
			request.put("safestr", qs);

			GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryId, gameWebIsuue);
			boolean isCancelDraw = !StringUtils.isBlank(number) && number.startsWith("X");
			boolean isOpenTooEarly = earliestTime.before(issue.getSaleEndTime());
			
			//判斷是否需要寫入獎號
			Boolean isWirteDraw = true;
			
			if(lotteryId==99602 || lotteryId==99603){
				//骰寶只記錄EC送過來的號碼,不做開獎動作,開獎由排成自主取號
				isWirteDraw =false;
			}
			
			
			if(isWirteDraw){
				if(isCancelDraw){
					//取消開獎
					GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
					warnIssueLog.setLotteryid(lotteryId);
					warnIssueLog.setIssueCode(issue.getIssueCode());
					warnIssueLog.setDisposeInfo(disposeMemo + "取消开奖");
					warnIssueLog.setCreateTime(new Date());
					warnIssueLog.setDisposeMemo(disposeMemo + "取消开奖");
					warnIssueLog.setDisposeUser("ECNF");
					warnIssueLog.setEvent(GameWarnEvent.SYSTEM_C_AWARD.getCode());
					warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_C_AWARD.getMessage());
					gameDrawServiceImpl.receivedSystemCancelAward(lotteryId, issue.getIssueCode(), number, warnIssueLog);
				}else if (isOpenTooEarly) {
					//提早開獎-暫停獎期
					// 开奖时间 早于 我们的销售截止时间
					log.info(issue.getLottery().getLotteryId() + ","
							+ issue.getWebIssueCode() + "开奖时间 早于  我们的销售截止时间");
					GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
					warnIssueLog.setLotteryid(lotteryId);
					warnIssueLog.setIssueCode(issue.getIssueCode());
					warnIssueLog.setDisposeMemo(disposeMemo);
					warnIssueLog.setCreateTime(new Date());
					warnIssueLog.setDisposeUser("4.0");
					warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
					warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_1.getCode());// 官方提前开奖x秒（开奖已暂缓）												
					warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_B_AWARD_1.getMessage());
					gameDrawServiceImpl.receivedAwardNumberBeforeSaleTime(lotteryId, issue.getIssueCode(), number, warnIssueLog, verifiedTime);
				}else{
					gameDrawServiceImpl.saveDrawECResultEvent(lotteryId, gameWebIsuue,
							ECUtils.echoSaveData(request));
				}
			}
			
		} catch (GameIssueNotExistErrorException e) {
			log.error(disposeMemo + "奖期不存在...", e);
			status = "N1024";
		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
			status = "N0";
		}
		
		request.put("status", status);
		//		不判断验证结果直接返回成功状态
		request.put("id", String.valueOf(id));

		// 更新状态

		gameECLogDao.upGameECLog(request);

		//		return status;
		return "N4";
	}

	/*
	 * 重新开奖
	 * 
	 * @see com.winterframework.firefrog.game.service.IECProcessesService#
	 * receivedChangeLotteryAwardNumber
	 * (com.winterframework.firefrog.game.web.dto.ECReceivesNumbersRequest)
	 */
	@Override
	public String receivedChangeLotteryAwardNumber(Map<String, String> request, String reqPath) throws Exception {
		String disposeMemo = "EC重新输入开奖号码通知";
		request.put("type", String.valueOf(ECUtils.EC_NOTICES_UPDATE_TYPE));

		String status = new String();
		long id = 0L;

		try {

			// 记录EC日志入库

			id = gameECLogDao.addGameECLog(ECUtils.getMapToEntity(request));
			request.put("logId", id + "");
			log.info(disposeMemo + "入库,id为" + id);

			String lotteryStr = ECMap.get(request.get("lottery"));
			String issueCodeStr = request.get("issue");
			String numberRecord = request.get("number");
			numberRecord = numberRecord.replaceAll("%2C", ",");
			//correctTime
			Long lotteryid = Long.valueOf(lotteryStr);

			// 转换奖期格式 开奖中心格式to4.0格式
			String gameWebIsuue = ECUtils.getIssueFromEC(lotteryid, issueCodeStr);

			// 回传请求参数
			String params = ECNoticeUpdate.echoECData(request);

			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);

			StringBuffer sb = new StringBuffer(params);

			// EC验证字符串
			sb.append("&safestr=").append(qs);

			request.put("safestr", qs);

		/*	String res = httpClient.invokeHttpWithoutResultTypeForEC(serverPath + correctCheck, sb.toString(),
					gameServerPath + reqPath);

			status = res;

			log.info(disposeMemo + "验证返回状态," + res);*/

			/**
			 * 模拟开奖中心的收取号码流程 if("N16383".equals(res)) if("N16383".equals(res) ||
			 * IssueCodeUtil.LOTTERYID_CQSSC == lotteryid)
			 */
			//			if ("N16383".equals(res)) {

			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, gameWebIsuue);

			if (issue != null) {
				GameDrawResult drawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid, issue.getIssueCode());
				if(drawResult == null){
					warnIssueLog.setDisposeInfo("EC修改开奖号码：null>>" + numberRecord);
				}else{
					warnIssueLog.setDisposeInfo("EC修改开奖号码："+drawResult.getNumberRecord()+ ">>"+ numberRecord);
				}
				
				//warnIssueLog.setStatus(1L);// 处理中
				warnIssueLog.setIssueCode(issue.getIssueCode());
				warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
				warnIssueLog.setLotteryid(issue.getLottery().getLotteryId());
				warnIssueLog.setCreateTime(new Date());
				warnIssueLog.setDisposeMemo(disposeMemo);
				warnIssueLog.setEvent(GameWarnEvent.SYSTEM_R_AWARD.getCode());
				warnIssueLog.setDisposeUser("ECNF");
				// 1、添加开奖结果记录
				gameDrawServiceImpl.modifyDrawResult(lotteryid, issue.getIssueCode(), numberRecord, warnIssueLog,null);
				status = "N4";
			} else {// 奖期不存在
				status = "N0";
			}
			//			}// endif http response

		} catch (GameIssueNotExistErrorException e) {
			status = "N1024";
		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
			status = "N0";
		}

		request.put("status", status);
		request.put("id", String.valueOf(id));

		// 更新状态
		gameECLogDao.upGameECLog(request);

		//		return status;
		return "N4";
	}

	/*
	 * 提前开奖
	 * 
	 * @see com.winterframework.firefrog.game.service.IECProcessesService#
	 * receivedAwardNumberBeforeSaleTime
	 * (com.winterframework.firefrog.game.web.dto.ECReceivesNumbersRequest)
	 */
	@Override
	public String receivedAwardNumberBeforeSaleTime(Map<String, String> request, String reqPath) throws Exception {
		String disposeMemo = "接收到EC错误通知：";
		request.put("type", String.valueOf(ECUtils.EC_NOTICES_ENDSALES_TYPE));

		String status = new String();
		long id = 0L;

		try {

			// 记录EC日志入库

			id = gameECLogDao.addGameECLog(ECUtils.getMapToEntity(request));

			log.info(disposeMemo + "入库,id为" + id);

			String lotteryStr = ECMap.get(request.get("lottery"));
			String issueCodeStr = request.get("issue");
			//String numberRecord = request.get("number");
			//numberRecord = numberRecord.replaceAll("%2C", ",");

			Long lotteryid = Long.valueOf(lotteryStr);

			// 转换奖期格式 开奖中心格式to4.0格式
			String gameWebIsuue = ECUtils.getIssueFromEC(lotteryid, issueCodeStr);

			// 回传请求参数
			String params = ECNoticeBeforeSaleTime.echoECData(request);

			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);

			StringBuffer sb = new StringBuffer(params);

			// EC验证字符串
			sb.append("&safestr=").append(qs);

			request.put("safestr", qs);

			//			String res = httpClient.invokeHttpWithoutResultTypeForEC(serverPath + sendMessageCheck, sb.toString(),
			//					gameServerPath + reqPath);
			//
			//			status = res;
			//
			//			log.info(disposeMemo + "验证返回状态," + res);

			/**
			 * 模拟开奖中心的收取号码流程 if("N16383".equals(res)) if("N16383".equals(res) ||
			 * IssueCodeUtil.LOTTERYID_CQSSC == lotteryid)
			 */
			//			if ("N16383".equals(res)) {

			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();

			GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, gameWebIsuue);

			if (issue != null) {
				warnIssueLog.setLotteryid(lotteryid);
				warnIssueLog.setIssueCode(issue.getIssueCode());
				warnIssueLog.setWebIssueCode(issue.getWebIssueCode());
				try {
					String errCode = request.get("errCode");
					String errMessage = request.get("errMessage");
					if ("102001".equals(errCode)) {//提前开奖(开奖时间早于销售截止时间)
						String numberRecord = request.get("number");
						numberRecord = numberRecord.replaceAll("%2C", ",");
						warnIssueLog.setDisposeInfo(disposeMemo + "提前开奖(开奖时间早于销售截止时间)，号码：" + numberRecord);
						warnIssueLog.setCreateTime(new Date());
						warnIssueLog.setDisposeMemo(disposeMemo);
						warnIssueLog.setDisposeUser("ECNF");
						warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_1.getCode());
						gameDrawServiceImpl.receivedAwardNumberBeforeSaleTime(lotteryid, issue.getIssueCode(),
								numberRecord, warnIssueLog, null);
					} else if ("104001".equals(errCode)) {//提前开奖(开奖时间早于官方理论开奖时间)
						String numberRecord = request.get("number");
						numberRecord = numberRecord.replaceAll("%2C", ",");
						warnIssueLog.setDisposeInfo(disposeMemo + "提前开奖(开奖时间早于官方理论开奖时间)，号码：" + numberRecord);
						warnIssueLog.setCreateTime(new Date());
						warnIssueLog.setDisposeMemo(disposeMemo);
						warnIssueLog.setDisposeUser("ECNF");
						warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_2.getCode());
						gameDrawServiceImpl.receivedAwardNumberBeforeSaleTime(lotteryid, issue.getIssueCode(),
								numberRecord, warnIssueLog, null);
					} else if ("105001".equals(errCode)) {//提前开奖(开奖时间早于销售截止时间)
						warnIssueLog.setDisposeInfo(disposeMemo + "取消开奖");
						warnIssueLog.setCreateTime(new Date());
						warnIssueLog.setDisposeMemo(disposeMemo + "取消开奖");
						warnIssueLog.setDisposeUser("ECNF");
						warnIssueLog.setEvent(GameWarnEvent.SYSTEM_C_AWARD.getCode());
						gameDrawServiceImpl.receivedSystemCancelAward(lotteryid, issue.getIssueCode(), null,
								warnIssueLog);
					} else {
						log.error("----------------该EC错误未监控" + errCode + errMessage);
					}
				} catch (Exception e) {
					log.error(disposeMemo + "解析结果异常" + sb, e);
				}

				status = "N4";
			} else {// 奖期不存在
				status = "N0";
			}
			//			}// endif http response

		} catch (GameIssueNotExistErrorException e) {
			status = "N1024";
		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
			status = "N0";
		}

		request.put("status", status);
		request.put("id", String.valueOf(id));

		// 更新状态
		gameECLogDao.upGameECLog(request);

		//		return status;
		return "N4";
	}

	@Override
	public String issueCheckEC(Map<String, String> request, String reqPath) {
		Response<ECIssueCheckReponse> response = new Response<ECIssueCheckReponse>();
		List<ECIssueCheckReponseIssueStruc> issues = new ArrayList<ECIssueCheckReponseIssueStruc>();
		String disposeMemo = "EC校验奖期";
		ECIssueCheckReponse result = new ECIssueCheckReponse();
		boolean isSuccess = true;
		result.setMessage(" ");
		try {
			// 获取ec请求中的参数信息
			String lotteryStr = ECMap.get(request.get("lottery"));
			String issueStartTimeStr = request.get("startTime");
			String issueEndTimeStr = request.get("endTime");
			//			Date issueStartTime = DateUtils.parse(issueStartTimeStr,
			//					"yyyyMMddHHmmss");
			//			Date issueEndTime = DateUtils.parse(issueEndTimeStr,
			//					"yyyyMMddHHmmss");

			// 验证请求
			String str = new String();
			
			StringBuffer sb = new StringBuffer(str);
			sb.append("customer=" + request.get("customer")).append("&lottery=" + request.get("lottery"))
					.append("&startTime=" + request.get("startTime")).append("&endTime=" + request.get("endTime"));
			String params = sb.toString();
			String qs = DigestUtils.getMD5ofStr(params).toLowerCase();
			if (!qs.equals(request.get("safestr"))) {
				isSuccess = false;
				result.setMessage("N16");// 请求非法
			}

			if (!customerId.equals(request.get("customer"))) {
				isSuccess = false;
				result.setMessage("N32");// 商户key错误
			}

			Long lotteryId = Long.valueOf(lotteryStr);
			List<GameIssueEntity> gies = gameIssueDao.getGameIssues(lotteryId, issueStartTimeStr, issueEndTimeStr);

			if (!gies.isEmpty()) {
				isSuccess = true;
				result.setMessage("");// 商户key错误
				for (GameIssueEntity gie : gies) {
					ECIssueCheckReponseIssueStruc struc = new ECIssueCheckReponseIssueStruc();
					struc.setDrawTime(DateUtils.format(gie.getOpenDrawTime(), "yyyyMMddHHmmss"));
					struc.setSaleCloseTime(DateUtils.format(gie.getSaleEndTime(), "yyyyMMddHHmmss"));
					
					/*struc.setSaleStartTime(DateUtils.format(
							gie.getSaleStartTime(), "yyyyMMddHHmmss"));*/
					struc.setIssue(ECUtils.tansferGameIssueForECIssue(lotteryId, gie.getWebIssueCode()));
					struc.setLottery(ECNameMap.get(String.valueOf(lotteryId)));
					issues.add(struc);
				}
			} else {
				isSuccess = false;
				result.setMessage("N1024");// 奖期不存在
			}
		} catch (Exception e) {
			isSuccess = false;
			log.info(disposeMemo + "验证返回状态," + e);
		}
		result.setIssues(issues);
		result.setIsSuccess(isSuccess);
		

		//		JsonMapper jmapper = JsonMapper.nonEmptyMapper();
		//		
		//		log.error("ec json:" + jmapper.toJson(result));
		//		
		//		response.setResult(result);

		return JsonMapper.nonEmptyMapper().toJson(result);
	}

	@Override
	public String queryCheckEC(Map<String, String> request, String string) {
		String str = new String();
		StringBuffer sb = new StringBuffer(str);
		sb.append("customer=" + request.get("customer")).append("&logId=" + request.get("logId"))
				.append("&lottery=" + request.get("lottery")).append("&issues=" + request.get("issues"))
				.append("&time=" + request.get("time")).append("&recordId=" + request.get("recordId"));
		String params = sb.toString();
		String qs = DigestUtils.getMD5ofStr(params);
		if (!qs.equals(request.get("safestr"))) {
			return "N16";// 请求非法
		}

		GameECLog gec = gameECLogDao.getById(Long.valueOf(request.get("logId")));

		if (gec == null) {
			return "N16384";
		} else {

		}
		return null;
	}

	@Override
	public String getIssueDrawResult(Request<GetIssueDrawResult> request, String reqPath) {
		String disposeMemo = "商户主动从ec获取开奖号码";

		GetIssueDrawResult gidr = request.getBody().getParam();
		long id = 0L;
		String res = "";

		try {
			GameIssueEntity issue = gameIssueDao.queryGameIssue(gidr.getLotteryId(), gidr.getWebIssueCode());
			gameIssueDao.updateTry(1L, issue.getId());
			// 记录EC查询日志

			GameECLog gameECLog = new GameECLog();

			gameECLog.setCustomer(customerId);
			gameECLog.setLottery(ECNameMap.get(gidr.getLotteryId() + ""));

			// 多个奖期号处理
			String[] issueCodes = gidr.getWebIssueCode().split(",");
			StringBuilder temp = new StringBuilder();
			for (int i = 0; i < issueCodes.length; i++) {
				temp.append(ECUtils.tansferGameIssueForECIssue(gidr.getLotteryId(), issueCodes[i]));
				if (i != issueCodes.length - 1) {
					temp.append(",");
				}
			}
			gameECLog.setIssue(temp.toString());
			gameECLog.setType(ECUtils.EC_TRY_LOG);
			gameECLog.setRequestTime(DateUtils.format(gidr.getRequestTime(), "yyyyMMddHHmmss"));

			id = gameECLogDao.addGameECLog(gameECLog);

			log.info(disposeMemo + "添加日志记录,id为" + id);

			// 拼接ec查询参数
			String str = new String();
			StringBuffer sb = new StringBuffer(str);
			sb.append("customer=" + customerId).append("&logId=" + id).append("&lottery=" + gameECLog.getLottery())
					.append("&issues=" + gameECLog.getIssue()).append("&time=" + gameECLog.getRequestTime());

			// 回传请求参数
			String params = sb.toString();

			log.error("ec try log params = "+params);
			
			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);

			StringBuffer sb1 = new StringBuffer(params);

			// EC验证字符串
			sb1.append("&safestr=").append(qs);

			res = httpClient.invokeHttpWithoutResultTypeForEC(serverPath + getIssueDrawResult, sb1.toString(), reqPath);//已写死

			log.error("主动获取收到" + res);

			GetCodeJson getCodeJson = new GetCodeJson();
			NumberJson number = null;
			Map<String, String> resparams = new HashMap<String, String>();
			try {
				getCodeJson = JsonMapper.nonEmptyMapper().fromJson(res, GetCodeJson.class);
				if ("true".equals(getCodeJson.getIsSuccess())) {
					number = getCodeJson.getNumbers().get(0);
					
					if(StringUtils.isBlank(number.getNumber())){
						gameECLog.setMessage(getCodeJson.getMessage());
					}else{
						resparams.put("lottery", number.getLottery());
						resparams.put("issue", number.getIssue());
						resparams.put("number", number.getNumber());
						resparams.put("deteminedTime", number.getDeteminedTime());
						resparams.put("earliestTime", number.getEarliestTime());
						resparams.put("errCode", number.getErrCode());
						resparams.put("errMessage", number.getErrMessage());
						resparams.put("drawTime", number.getDrawTime());
						resparams.put("saleCloseTime", number.getSaleCloseTime());
					}
					
				}else{
					gameECLog.setMessage(getCodeJson.getMessage());
					//gameECLogDao.update(gameECLog);
				}

			} catch (Exception e) {
				log.error("主动请求解析结果异常" + res, e);
				gameECLog.setMessage("主动请求解析结果异常" );
				//gameECLogDao.update(gameECLog);
			}

			
			
			//Map<String, String> resparams = ECUtils.getMapFormRequestParams(res);	
			if (number != null) {
				String result = getIssueDrawResult(resparams, gameServerPath + reqPath);
				if(result.startsWith("NULL")){
					log.error("主动请求解析结果异常 获取number失败" + result);
					gameECLog.setMessage(getCodeJson.getMessage());
					gameECLog.setStatus("get number failed "+result);
					gameECLogDao.update(gameECLog);
				}
			} else {
				log.error("主动请求解析结果异常 获取number失败" + res);
				gameECLog.setMessage(getCodeJson.getMessage());
				gameECLog.setStatus("get number failed");
				gameECLogDao.update(gameECLog);
				
			}
			
			// 将获取到的结果保存到记录表中
			//gameECLog.setMessage(result);
			//gameECLogDao.update(gameECLog);

		} catch (Exception e) {
			log.error(disposeMemo + "失败...", e);
		}

		return res;
	}

	public String getIssueDrawResult(Map<String, String> request, String reqPath) throws Exception {
		String disposeMemo = "主动获取EC开奖号码";
		request.put("type", String.valueOf(ECUtils.EC_NOTICES_NORMAL_TYPE));
		String status = "auto get";
		long id = 0L;
		try {
			// 记录EC日志入库
			//id = gameECLogDao.addGameECLog(ECUtils.getMapToEntity(request));
			log.info(disposeMemo + "入库,id为" + id);
			
			if(request.get("lottery")!=null){
				log.error("lottery= "+request.get("lottery"));				
			}

			if(request.get("issue")!=null){
				log.error("issue= "+request.get("issue"));				
			}
			
			if(request.get("number")!=null){
				log.error("number= "+request.get("number"));				
			}
			
			if(request.get("earliestTime")!=null){
				log.error("earliestTime= "+request.get("earliestTime"));				
			}
			
			if(request.get("determinedTime")!=null){
				log.error("determinedTime= "+request.get("determinedTime"));				
			}
			
			if(request.get("lottery")==null){			
				return "NULL_lottery";
			}else if (request.get("issue")==null){				
				return "NULL_issue";
			}else if (request.get("number")==null){			
				return "NULL_number";
			}else if (request.get("earliestTime")==null){				
				return "NULL_earliestTime";
			}else if (request.get("determinedTime")==null){			
				return "NULL_determinedTime";
			}
			
			String lotteryStr = ECMap.get(request.get("lottery"));
			String issueCodeStr = request.get("issue");
			String numberRecord = request.get("number").replaceAll("%2C", ",");
			Date earliestTime = null;
			try {
				earliestTime = DateUtils.parse(request.get("earliestTime"), "yyyyMMddHHmmss");
			} catch (Exception e) {
				earliestTime = new Date(Long.valueOf(request.get("earliestTime")) * 1000);
			}
			Date verifiedTime = null;
			try {
				verifiedTime = DateUtils.parse(request.get("determinedTime"), "yyyyMMddHHmmss");
			} catch (Exception e) {
				try {
					verifiedTime = new Date(Long.valueOf(request.get("verifiedTime")) * 1000);
				} catch (Exception e2) {
					log.error("verifiedTime 获取错误");
				}
			}

			Long lotteryid = Long.valueOf(lotteryStr);
			// 转换奖期格式 开奖中心格式to4.0格式
			String gameWebIsuue = ECUtils.getIssueFromEC(lotteryid, issueCodeStr);
			// 回传请参数
			String params = ECUtils.echoECData(request, String.valueOf(id));
			// MD5验证串
			String qs = DigestUtils.getMD5ofStr(params);
			StringBuffer sb = new StringBuffer(params);

			// EC验证字符串
			sb.append("&safestr=").append(qs);
			request.put("safestr", qs);
			//String res = httpClient.invokeHttpWithoutResultTypeForEC(serverPath + ecCheckUrl, sb.toString(),
			//		gameServerPath + reqPath);
			//status = res;
			//log.info(disposeMemo + "验证返回状态," + res);

			//if(!"N16383".equals(res)){
			//	log.error("进程非真实");
			//}
			/**
			 * 模拟开奖中心的收取号码流程 if("N16383".equals(res)) if("N16383".equals(res) ||
			 * IssueCodeUtil.LOTTERYID_CQSSC == lotteryid)
			 */

			// 不判断验证结果直接返回成功状态 20140710 by charles
			GameWarnIssueLog warnIssueLog = new GameWarnIssueLog();
			GameIssueEntity issue = gameIssueDao.queryGameIssue(lotteryid, gameWebIsuue);
			if (!StringUtils.isBlank(numberRecord) && numberRecord.startsWith("X")) {
				warnIssueLog.setDisposeInfo(disposeMemo + "取消开奖");
				warnIssueLog.setCreateTime(new Date());
				warnIssueLog.setDisposeMemo(disposeMemo + "取消开奖");
				warnIssueLog.setDisposeUser("ECNF");
				gameDrawServiceImpl.receivedSystemCancelAward(lotteryid, issue.getIssueCode(), null, warnIssueLog);
			} else if (issue != null) {
				warnIssueLog.setDisposeInfo("接受到EC开奖号码：" + numberRecord);
				warnIssueLog.setCreateTime(new Date());
				warnIssueLog.setDisposeMemo(disposeMemo);
				warnIssueLog.setDisposeUser("ECNF");
				warnIssueLog.setIssueCode(issue.getIssueCode());
				warnIssueLog.setLotteryid(lotteryid);
				warnIssueLog.setWebIssueCode(gameWebIsuue);

				GameDrawResult gameDrawResult = gameDrawServiceImpl.getDrawResuleByLotteryIdAndIssueCode(lotteryid,
						issue.getIssueCode());
				if (gameDrawResult != null) {
					log.info(lotteryStr + "," + issue.getWebIssueCode() + "已经存在开奖号码 ");
					// 有号码  且 非手动输入 
					if (!numberRecord.equals(gameDrawResult.getNumberRecord()) && gameDrawResult.getType().longValue() == 0L) {
						warnIssueLog.setEvent(GameWarnEvent.SYSTEM_R_AWARD.getCode());// 开奖号码错误（已处理）		
						warnIssueLog.setDisposeInfo("EC修改开奖号码:"+gameDrawResult.getNumberRecord()+ ">>"+ numberRecord);
						gameDrawServiceImpl.modifyDrawResult(lotteryid, issue.getIssueCode(), numberRecord,
								warnIssueLog, 0L ,verifiedTime);
					}

				} else {
					// 1、添加开奖结果记录
					if (earliestTime.before(issue.getSaleEndTime())) {
						//开奖时间 早于  我们的销售截止时间
						log.info(lotteryStr + "," + issue.getWebIssueCode() + "开奖时间 早于  我们的销售截止时间");
						warnIssueLog.setDisposeUser("4.0");
						warnIssueLog.setEvent(GameWarnEvent.SYSTEM_B_AWARD_1.getCode());// 官方提前开奖x秒（开奖已暂缓）												
						warnIssueLog.setDisposeInfo(GameWarnEvent.SYSTEM_B_AWARD_1.getMessage());
						gameDrawServiceImpl.receivedAwardNumberBeforeSaleTime(lotteryid, issue.getIssueCode(),
								numberRecord, warnIssueLog, verifiedTime);

					} else {
						//开奖时间 晚于  我们的销售截止时间
						gameDrawServiceImpl.inputNumberDrawResult(lotteryid, issue.getIssueCode(), numberRecord, null,
								verifiedTime);
					}
				}
			}

		} catch (GameIssueNotExistErrorException e) {
			status = "N1024";
		} catch (Exception e) {
			log.error(disposeMemo + "输入开奖号码失败...", e);
			status = "N0";
		}

		request.put("status", status);
		//		不判断验证结果直接返回成功状态
		request.put("id", String.valueOf(id));

		// 更新状态

		//gameECLogDao.upGameECLog(request);

		//		return status;
		return "N4";
	}
	@Override
	public void receivedBallResult(Long lotteryId, String webIssueCode, String ball, Date receiceTime) throws Exception {
		StringBuffer sb = new StringBuffer();
		String number = ball;
		sb.append("{");
		sb.append("\"number\":\"").append(number).append("\"").append(",");
		sb.append("\"verifiedTime\":\"").append(DateUtils.format(receiceTime, "yyyyMMddHHmmss")).append("\"");
		sb.append("}");
		gameDrawServiceImpl.saveDrawZKResultEvent(lotteryId, webIssueCode,
				sb.toString());
		
	}
}
