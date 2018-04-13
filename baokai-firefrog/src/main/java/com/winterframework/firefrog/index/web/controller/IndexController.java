/**   
* @Title: IndexController.java 
* @Package com.winterframework.firefrog.index.web.controller 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-23 上午10:07:16 
* @version V1.0   
*/
package com.winterframework.firefrog.index.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Optional;
import com.winterframework.firefrog.active.dao.IActivityConfigDao;
import com.winterframework.firefrog.active.dao.IActivityLogDao;
import com.winterframework.firefrog.active.dao.vo.ActivityConfig;
import com.winterframework.firefrog.advert.entity.AdNotice;
import com.winterframework.firefrog.advert.entity.AdSpace;
import com.winterframework.firefrog.advert.service.IAdNoticeService;
import com.winterframework.firefrog.advert.service.IAdSpaceService;
import com.winterframework.firefrog.advert.web.dto.AdDTOConverter;
import com.winterframework.firefrog.advert.web.dto.AdNoticeStruc;
import com.winterframework.firefrog.advert.web.dto.AdSpaceStruc;
import com.winterframework.firefrog.advert.web.dto.DTOConverter;
import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.PageConverterUtils;
import com.winterframework.firefrog.common.util.RandomVal;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.service.IBankCardService;
import com.winterframework.firefrog.game.service.IGameBettypeStatusService;
import com.winterframework.firefrog.help.entity.Help;
import com.winterframework.firefrog.help.service.IHelpService;
import com.winterframework.firefrog.help.web.dto.HelpQueryRequest;
import com.winterframework.firefrog.help.web.dto.HelpStruc;
import com.winterframework.firefrog.index.web.dto.IndexDTO;
import com.winterframework.firefrog.index.web.dto.IndexStruc;
import com.winterframework.firefrog.index.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.index.web.dto.RedBowActivity;
import com.winterframework.firefrog.index.web.dto.RedBowRequest;
import com.winterframework.firefrog.index.web.dto.Super2000Activity;
import com.winterframework.firefrog.index.web.dto.Super2000Request;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.firefrog.user.web.controller.game.IndexLottery;
import com.winterframework.modules.ip.IPSeeker;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;

/** 
* @ClassName: IndexController 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-12-23 上午10:07:16 
*  
*/
@Controller("indexController")
@RequestMapping("/index")
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	private static final long SUPER2000_ACTID = 5;
//	private static final long redBowNovOne_ACTID = 185;
//	private static final long redBowNovTwo_ACTID = 186;
//	private static final long redBowNovThree_ACTID = 187;
	@Resource(name = "helpServiceImpl")
	private IHelpService helpService;

	@Resource(name = "HttpJsonClientImpl")
	private IHttpJsonClient httpJsonClient;

	@Resource(name = "adNoticeServiceImpl")
	private IAdNoticeService adNoticeService;

	@Resource(name = "adSpaceServiceImpl")
	private IAdSpaceService adSpaceServiceImpl;

	@Resource(name = "bankCardServiceImpl")
	private IBankCardService bankCardService;

	@PropertyConfig(value = "game_get_indexInit")
	private String indexInitUrl;

	@PropertyConfig(value = "game_get_userOrders")
	private String userOrdersUrl;
	@Resource(name = "userProfileServiceImpl")
	private IUserProfileService userServcie;

	@PropertyConfig(value = "super2000.bet.lotteryId")
	private String super2000_lotteryId;
	
	@Resource(name = "configServiceImpl")
	private IConfigService configService;
	
	@Resource(name = "activityLogDaoImpl")
	private IActivityLogDao activityLogDaoImpl;
	
	@Resource(name = "activityConfigDaoImpl")
	private IActivityConfigDao activityConfigDaoImpl;
	
	@Resource(name = "gameBettypeStatusServiceImpl")
	private IGameBettypeStatusService gameBettypeStatusService;
	
	private List<String> whiteLists;
	private List<String> redBowNovOneWhiteLists;
	private List<String> redBowNovTwoWhiteLists;
	private List<String> redBowNovThreeWhiteLists;
	private ActivityConfig super2000ActCfg;
	private List<String> betTypeCodes;
	private List<Integer> lotsList;
	
	@PostConstruct
	public void init() throws Exception{
		//取活動白名單用戶
		whiteLists = activityLogDaoImpl.getActWhiteList(5l);
		super2000ActCfg = activityConfigDaoImpl.getActCfgById(SUPER2000_ACTID);
		//11月活動
//		redBowNovOneActCfg = activityConfigDaoImpl.getActCfgById(redBowNovOne_ACTID);
//		redBowNovOneWhiteLists = activityLogDaoImpl.getActWhiteList(redBowNovOne_ACTID);
//		redBowNovTwoActCfg = activityConfigDaoImpl.getActCfgById(redBowNovTwo_ACTID);
//		redBowNovTwoWhiteLists = activityLogDaoImpl.getActWhiteList(redBowNovTwo_ACTID);
//		redBowNovThreeActCfg = activityConfigDaoImpl.getActCfgById(redBowNovThree_ACTID);
//		redBowNovThreeWhiteLists = activityLogDaoImpl.getActWhiteList(redBowNovThree_ACTID);
		
		
		betTypeCodes  = gameBettypeStatusService.getSuper2000BettypecodeByLotId();
		logger.info("super2000_lotteryId : " + super2000_lotteryId);
		String[] super2000Lots = super2000_lotteryId.split(",");
		logger.info("super2000Lots : " + super2000Lots.length);
		lotsList = new ArrayList<Integer>();
		for(String lot:super2000Lots){
			lotsList.add(Integer.valueOf(lot));
		}
		for(Integer lot:lotsList){
			logger.info("lot : " + lot);
		}
		
		
	}
	/**
	 * 
	* @Title: queryIndexInfo 
	* @Description: 根据首页帮助和公告
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/queryIndexInfo")
	public @ResponseBody
	Response<IndexStruc> queryIndexInfo(@RequestBody Request<IndexDTO> request) throws Exception {
		Response<IndexStruc> response = new Response<IndexStruc>(request);
		try {
			Long userId = request.getHead().getUserId();
			List<AdNotice> adNotices = adNoticeService.getAdNoticeForUser(userId, null);
			List<AdNoticeStruc> notices = new ArrayList<AdNoticeStruc>();
			int notice0[] = new int [5];
			for (AdNotice adNotice : adNotices) {
				if (notices.size() >= 20) {
					break;
				}
			
				switch (Math.round(adNotice.getNoticelevel()))
				{
				case 0:
					notice0[0]++;
					break;
				case 1:
					notice0[1]++;
					break;
				case 2:
					notice0[2]++;
					break;
				case 3:
					notice0[3]++;
					break;
				}
				if (notice0[Math.round(adNotice.getNoticelevel())] >5)
					continue;
				notices.add(DTOConverter.transAdNotice2Dto(adNotice));
			}
			IndexStruc result = new IndexStruc();
			result.setLastNotice(notices);
			try {
				User user = userServcie.queryUserById(userServcie.queryUserById(userId).getParent().getId());
				if(user!=null && user.getLastLoginLog()!=null && user.getLastLoginLog().getLoginIP()!=null)
				result.setLoginArea(IPSeeker.getInstance().getArea(longToIp(user.getLastLoginLog().getLoginIP())));
				if (user != null && user.getUserProfile() != null)
					result.setQqs(user.getUserProfile().getQq());

			} catch (Exception e) {
				e.printStackTrace();
			}
			//result.setIsBindCard(isBindCardForUser(userId));
			//result.setLastGame(getPreGames(userId,AccountTool.getRealAccount(accoung)));
			//result.setLastBetList(getUserOrders(userId));
			//if(request.getBody().getParam()==null ||request.getBody().getParam().getAds()==null){
			//request.getBody().getParam().setAds(new String[]{"index_pos_left_1","index_pos_left_2"});
			result.setAdSpaces(getAdSpaces(new String[] { "index_pos_left_1", "index_pos_left_2" }));
			//}else{
			//	result.setAdSpaces(getAdSpaces(request.getBody().getParam().getAds()));
			//}
			response.setResult(result);
		} catch (Exception e) {
			logger.error("queryIndexInfo error.", e);
			throw e;
		}
		return response;
	}
	/**
	 * 
	* @Title: super2000Activity 
	* @Description: super2000活動查詢
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/super2000Activity")
	public @ResponseBody
	Response<Super2000Activity> super2000Activity(@RequestBody Request<Super2000Request> request) throws Exception {
		Response<Super2000Activity> response = new Response<Super2000Activity>(request);
		logger.info("getUserId : " +request.getBody().getParam().getUserId());
		User user = userServcie.queryUserById(request.getBody().getParam().getUserId());
		
		//SUPER2000狀態檢查
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		logger.info("begin time : " + super2000ActCfg.getBeginTime());
		logger.info("end time : " + super2000ActCfg.getEndTime());
		int prize = 0;
		int status = 0;
		Super2000Activity result = new Super2000Activity();
		//有在活動時間內 才作
		if(now.after(super2000ActCfg.getBeginTime()) && now.before(super2000ActCfg.getEndTime())){
			status = do2000ActCheck(user.getAccount(), user.getId());
			if(status >= 0){
				switch(status){
				case 1:
					prize = super2000ActCfg.getMinPrize().intValue();
					break;
				case 2:
					prize = RandomVal.randInt(20, 12);
					break;
				default:
					logger.info(" -------------------------------status : " + status);
					result.setStatus(status);
					response.setResult(result);
					return response;
					
				}
			}
		}
		
		logger.info("status : " + status + ",prize : " + prize);
		result.setPrize(prize);
		result.setStatus(status);
		response.setResult(result);
		return response;
	}
	public String longToIp(long ip) {
		StringBuilder result = new StringBuilder(15);

		for (int i = 0; i < 4; i++) {

			result.insert(0, Long.toString(ip & 0xff));

			if (i < 3) {
				result.insert(0, '.');
			}

			ip = ip >> 8;
		}
		return result.toString();
	}

	private List<AdSpaceStruc> getAdSpaces(String[] adsArray) throws Exception {
		if (adsArray == null) {
			return new ArrayList<AdSpaceStruc>();
		}
		List<String> list = Arrays.asList(adsArray);
		List<AdSpace> adSpaces = adSpaceServiceImpl.getAdSpacesByNames(list);
		List<AdSpaceStruc> result = new ArrayList<AdSpaceStruc>();
		for (AdSpace adSpace : adSpaces) {
			result.add(AdDTOConverter.transAdSpaceToStruc(adSpace));
		}
		return result;
	}

	private List<HelpStruc> getHelp(Long type) throws Exception {
		PageRequest<HelpQueryRequest> pageRequest = PageConverterUtils.getRestPageRequest(1, 5);
		HelpQueryRequest searchDo = new HelpQueryRequest();
		searchDo.setType(type);
		searchDo.setIsRec(1l);
		pageRequest.setSearchDo(searchDo);
		pageRequest.setSortColumns("GMT_CREATED desc");
		Page<Help> page = helpService.queryHelp(pageRequest);
		List<Help> helpList = page.getResult();
		List<HelpStruc> helpStrucs = new ArrayList<HelpStruc>();
		for (Help help : helpList) {
			helpStrucs.add(com.winterframework.firefrog.help.web.dto.DTOConverter.transHelp2HelpStruc(help));
		}
		return helpStrucs;
	}

	private Integer isBindCardForUser(Long userId) throws Exception {
		List<BankCard> list = bankCardService.queryBoundBankCard(userId, null);
		return list.isEmpty() == true ? 0 : 1;
	}

	private List<OrdersStrucDTO> getUserOrders(Long userId) throws Exception {
		Map paramMap1 = new HashMap();
		paramMap1.put("userId", userId);
		try {
			List<OrdersStrucDTO> orderStrucs = (List<OrdersStrucDTO>) httpJsonClient.invokeHttp(userOrdersUrl,
					paramMap1, new TypeReference<List<OrdersStrucDTO>>() {
					});
			return orderStrucs;
		} catch (Exception e) {
			logger.error("getUserOrders error", e);
			throw e;
		}
	}

	public List<IndexLottery> getPreGames(Long userId, String account) throws Exception {
		try {
			List<IndexLottery> gameConfigAndAwardDate = (List<IndexLottery>) httpJsonClient.invokeHttp(indexInitUrl,
					null, new TypeReference<List<IndexLottery>>() {
					});
			return gameConfigAndAwardDate;
		} catch (Exception e) {
			logger.error("getPreGames error", e);
			throw e;
		}
	}
	
	//SUPER2000活動CHECK是在名單中/0:不在名單中或已領過兩次獎　1:領第一次獎　2:領第二次獎
	private int do2000ActCheck(String acoount,Long userId){
		
		//處理SUPER2000活動相關
		boolean isExist = false;
		
		logger.info("between activity time~~~!!!");
		if(Optional.fromNullable(whiteLists).isPresent()){
			//有名單，則開始做檢查是否在名單中
			isExist = whiteLists.contains(acoount)?true:false;
		}else{
			logger.error("無活動名單資料~~!!");
			return 0;
		}
		
		//取得玩家活動領取次數
		long actCount = activityLogDaoImpl.queryActivityLogCount(userId, SUPER2000_ACTID);
		logger.info("isExist" + isExist + ",actCount : " + actCount);		
		if(isExist && actCount == 0){
			return 1;
		} else if(isExist && actCount == 1){
//			ActivityLog actLog = activityLogDaoImpl.getActivtyLogByUserId(userId, SUPER2000_ACTID);
//			logger.info("actLog.getCreateTime : " + actLog.getCreateTime());		
			logger.info("betTypeCodes : " + betTypeCodes.size());		
			long totalBets = gameBettypeStatusService.getSuper2000TotalBets(userId, betTypeCodes,super2000ActCfg,lotsList);
			logger.info("totalBets : " + totalBets);		
			if(totalBets >= 8*10000)
				return 2;
		} 
		return 0;
	}
	
	public enum ActStatus {
		/**第一次開紅包*/
		FIRST_OPEN(1, "第一次開紅包"),
		
		/**第二次開紅包*/
		SECOND_OPEN(2, "第二次開紅包"),
		
		/**超過開紅包次數的錯誤狀態*/
		OUTPACE_OPEN(-2, "超過開紅包次數的錯誤狀態"),
		
		/**投注金額尚未到達*/
		NON_AMOUNT_ACHIEVE(3, "投注金額尚未到達"),
		
		/**非活動時間*/
		NOT_IN_ACT(-1, "非活動時間"),
		
		/**非白名單內的人*/
		NOT_IN_WHITE_LIST(0, "非白名單內的人");
		
		private int code;
		
		private String content;
		
		ActStatus(int code, String content){
			this.code = code;
			this.content = content;
		}
		public int getCode() {
			return code;
		}
		public String getContent() {
			return content;
		}
	}
	
	@RequestMapping(value = "/redBowConfigActivity")
	public @ResponseBody
	Response<RedBowActivity> redBowConfigActivity(@RequestBody Request<RedBowRequest> request) throws Exception {
		Response<RedBowActivity> response = new Response<RedBowActivity>(request);
		logger.info("getUserId : " +request.getBody().getParam().getUserId());
		User user = userServcie.queryUserById(request.getBody().getParam().getUserId());
		RedBowActivity result = new RedBowActivity();
		//紅包狀態檢查
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		//取得目前的活動設定檔
		ActivityConfig activityConfig = activityConfigDaoImpl.getActivityConfigByTime(now);
		if(null != activityConfig && null != activityConfig.getId()){
			result.setUserLv(user.getVipLvl()==0?0:1);
			result.setWeekType(activityConfig.getType()); //判斷第幾周
			result.setActivityId(activityConfig.getId());
			response.setResult(result);
			return response;
		} else {
			result.setUserLv(user.getVipLvl()==0?0:1);
			result.setWeekType("0"); //判斷第幾周
			result.setActivityId(0l);
			response.setResult(result);
			return response;
		}
	}
	
	@RequestMapping(value = "/checkActivityId")
	public @ResponseBody
	Response<Boolean> checkActivityId(@RequestBody Request<RedBowActivity> request) throws Exception {
		Response<Boolean> response = new Response<Boolean>(request);
		Long activityId = request.getBody().getParam().getActivityId();
		//紅包狀態檢查
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		//取得目前的活動設定檔
		ActivityConfig activityConfig = activityConfigDaoImpl.getActivityConfigByTime(now);
		boolean isCheck = activityConfig.getId().equals(activityId);
		response.setResult(isCheck);
		return response;
	}
	
	
	/**
	 * 
	* @Title: redBowActivity 
	* @Description: 11月紅包活動查詢
	* @param request
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/redBowActivity")
	public @ResponseBody
	Response<RedBowActivity> redBowActivity(@RequestBody Request<RedBowRequest> request) throws Exception {
		Response<RedBowActivity> response = new Response<RedBowActivity>(request);
		logger.info("getUserId : " +request.getBody().getParam().getUserId());
		User user = userServcie.queryUserById(request.getBody().getParam().getUserId());
		RedBowActivity result = new RedBowActivity();
		//紅包狀態檢查
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		//取得目前的活動設定檔
		ActivityConfig activityConfig = activityConfigDaoImpl.getActivityConfigByTime(now);
		
		if(null != activityConfig && null != activityConfig.getId()){
			//活動開始時間
			Date startTime = activityConfig.getBeginTime();
			Date endTime = activityConfig.getEndTime();
			
			logger.info("11 月活動第{}周  begin time : {}", activityConfig.getType(), startTime);
			logger.info("11 月活動第{}周  end time : {}", activityConfig.getType(), endTime);
			
			int prize = 0;
			
			ActStatus status = doRedBowActCheck(user.getAccount(), user.getId(), activityConfig, user.getVipLvl() == 0 ? 0 : 1);
			
			switch(status){
				case FIRST_OPEN :
					if(user.getVipLvl() == 0){
						prize = 6;//非vip
					}else{
						prize = 18;//vip
					}
					
					break;
				case SECOND_OPEN :
					if(user.getVipLvl() ==0){
						prize = RandomVal.randInt(18, 10);//非vip
					}else{
						prize = RandomVal.randInt(38, 20);//vip
					}
					
					break;
				default:
					logger.info(" -------------------------------status : " + status.toString());
//					result.setPrize(prize);
//					result.setStatus(status);
//					response.setResult(result);
//					return response;
					break;
			}
			
			logger.info("account : {}, status : {}({}), prize : {}", user.getAccount(), status.getContent(), status.getCode(), prize);
			result.setPrize(prize);
			result.setUserLv(user.getVipLvl() == 0 ? 0 : 1);
			result.setStatus(status.getCode());
			result.setWeekType(activityConfig.getType()); //判斷第幾周
			result.setActivityId(activityConfig.getId());
			response.setResult(result);
			return response;
			
		} else {
			//非活動時間內
			logger.info("account : {}, status : {}({})", user.getAccount(), ActStatus.NOT_IN_ACT.getContent(), ActStatus.NOT_IN_ACT.getCode());
			result.setStatus(ActStatus.NOT_IN_ACT.getCode());
			result.setWeekType("0");
			result.setPrize(0);
			response.setResult(result);
			return response;
		}
		
		
	}
	
	
	//紅包活動CHECK是在名單中/0:不在名單中或已領過兩次獎　1:領第一次獎　2:領第二次獎
	private ActStatus doRedBowActCheck(String acoount, Long userId, ActivityConfig activityConfig, int userLv){
		
		//對應的彩種
		String[] lotteryStr = activityConfig.getRule().split(",");
		
		//是否有白名單資料 count = 1 有, <> 1 沒有
		Integer count = activityLogDaoImpl.getActWhiteListByIdAndAccount(activityConfig.getId(), acoount);
		
		if(count.equals(1)){
			//取得玩家活動領取次數
			Long actCount = activityLogDaoImpl.queryActivityLogCount(userId, activityConfig.getId());
			logger.info("acoount : {}, 紅包領取次數為 actCount : {}", acoount, actCount);
			if(actCount.equals(0l)){
				//第一次紅包開獎
				return ActStatus.FIRST_OPEN;
			} else if(actCount.equals(1l)){
				List<Integer> lotteryList= new ArrayList<Integer>();
				for(String lot:lotteryStr){
					lotteryList.add(Integer.valueOf(lot));
				}
				long totalBets = gameBettypeStatusService.getTotalBets(userId, activityConfig, lotteryList);
				logger.info("totalBets : " + totalBets);
				if(userLv ==0){
					//普通會員
					if(totalBets >= 6*10000){
						//第二次紅包開獎
						return ActStatus.SECOND_OPEN;
					} else {
						//投注金額未到達
						return ActStatus.NON_AMOUNT_ACHIEVE;
					}
				}else{
					//VIP會員
					if(totalBets >= 18*10000){
						//第二次紅包開獎
						return ActStatus.SECOND_OPEN;
					} else {
						//投注金額未到達
						return ActStatus.NON_AMOUNT_ACHIEVE;
					}
				}
			} else {
				//超過開紅包次數錯誤
				return ActStatus.OUTPACE_OPEN;
			}
		} else {
			return ActStatus.NOT_IN_WHITE_LIST;
		}
	}

}
