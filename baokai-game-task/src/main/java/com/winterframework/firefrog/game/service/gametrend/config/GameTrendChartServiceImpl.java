package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameControlEventDao;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameJbylTrendDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.dao.vo.LotteryIdAndIssueCode;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameTrendBetService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendBetStrategyService;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartService;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendEventParams.TrendEventTypeEnum;
import com.winterframework.firefrog.game.service.impl.RedisService;
import com.winterframework.firefrog.user.entity.IndexLottery;
import com.winterframework.modules.web.util.JsonMapper;

/**
* @ClassName: GameTrendChartServiceImpl
* @Description: 彩种走势图遗漏数据统计入口
* @author floy
* @date 2014-4-1 下午5:00:13
*
*/
@Service("gameTrendChartServiceImpl")
@Transactional
public class GameTrendChartServiceImpl implements IGameTrendChartService {

	private static final Logger log = LoggerFactory.getLogger(GameTrendChartServiceImpl.class);

	@Resource(name = "gameJbylTrendDaoImpl")
	private IGameJbylTrendDao gameJbylTrendDao;

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	//彩种对应的统计规则配置
	@Resource(name = "gameTrendChartRuleListMap")
	private Map<String, GameTrendChartRuleList> gameTrendChartRuleListMap;

	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gameTrendBetServiceImpl")
	private IGameTrendBetService gameTrendBetService; 
	@Resource(name = "gameControlEventDaoImpl")
	private IGameControlEventDao gameControlEventDao;
	
	@Resource(name = "gameTrendBetStrategyFactory")
	private HashMap<Integer,IGameTrendBetStrategyService> gameTrendBetStrategyFactory;
	@Resource(name = "gameTrendBetMap")
	private Map<String, Map<TrendType,List<LotteryPlayMethodKeyGenerator>>> gameTrendBetMap;
	@Resource(name = "RedisService")
	private RedisService redisService;
	
	private final String SEPERATOR = ",";
	private final String SEPERATOR_HORI_LOW = "_";
	
	@Override
	public void generateTrendData(GameControlEvent event) throws Exception {
		/**
		 * 1.删除本期后面期数的走势遗漏数据
		 * 2.生成本期的走势遗漏数据
		 * 3.通知下一期生成走势遗漏数据
		 */
		
		GameTrendEventParams params = new GameTrendEventParams().fromJson(event.getParams());
        if (TrendEventTypeEnum.ADD.getCode() == params.getType()) {	  
        	Long lotteryId=params.getLotteryId();
        	Long issueCode=params.getIssueCode();
        	Long trendLotteryId=event.getLotteryid();
        	Long userId=null;
        	if(99601L==lotteryId) return;	//骰宝不生成数据 借用快三
        	Long time1=DateUtils.currentDate().getTime();
        	log.info("走势图任务执行开始，彩种="+lotteryId+",奖期="+issueCode+",time="+time1);
        	if((lotteryId!=null && lotteryId==99112L)||(lotteryId!=null && lotteryId==99306L)){
        		List<GameOrder> orderList=this.gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);
    			if(orderList==null || orderList.size()==0){
    				throw new Exception("Game order not exists.(issueCode:"+issueCode+")");
    			}
    			//存放秒秒彩userId 
    			userId=orderList.get(0).getUserid(); 
        	} 
        	//1.删除本期后面期数的走势遗漏数据
        	if(lotteryId != 99111L && lotteryId != 99114L && lotteryId != 99112L&&lotteryId != 99306L){
        		deleteFollowTrendData(lotteryId, issueCode,userId);
        	}
        	/*if(lotteryId == 99111L){
        		int num = getCheckChartMMC(lotteryId, issueCode);
        		if(num != 1){
        			throw new Exception("Game order not exists.(issueCode:"+issueCode+")");
        		}
        	}*/
        	Long time2=DateUtils.currentDate().getTime();
        	log.info("走势图任务删除后面期数数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time2-time1));
        	//2.生成本期的走势遗漏数据
        	generateCurrentIssueTrendData(lotteryId, issueCode,userId);
        	Long time3=DateUtils.currentDate().getTime();
        	log.info("走势图任务生成本期数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time3-time2));
        	//每一个开奖操作都会调用生成走势图任务//3.通知下一期生成走势遗漏数据 (秒秒彩除外)
        	if(lotteryId!=null && lotteryId!=99112L && lotteryId!=99111L && lotteryId!=99114L){	//分分彩不回溯处理    		
	        	noticeNextIssueTrendData(lotteryId, issueCode,trendLotteryId);
	        	Long time4=DateUtils.currentDate().getTime();
	        	log.info("走势图任务通知下一期耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time4-time3));
        	}
        }
				
		
	}
	/**
	 * 删除本期后面期数的走势遗漏数据
	 * @param lotteryId
	 * @param issueCode
	 */
	private void deleteFollowTrendData(Long lotteryId, Long issueCode,Long userId) {
		gameJbylTrendDao.deleteFollowGameTrendChart(lotteryId, issueCode,userId);
	}
	
	/**
	 * MMC Chart
	 * @param lotteryId
	 * @param issueCode
	 */
	private int getCheckChartMMC(Long lotteryId, Long issueCode) {
		return gameJbylTrendDao.getCheckChartMMC(lotteryId, issueCode);
	}
	/**
	 * 生成本期的走势遗漏数据
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	public void generateCurrentIssueTrendData(Long lotteryId, Long issueCode,Long userId) throws Exception { 
		Long time1=DateUtils.currentDate().getTime();
		GameDrawResult drawResult=null; 
		if(lotteryId!=null && lotteryId!=99112L && lotteryId!=99306L){
			drawResult= gameDrawResultDao.getDrawResuleByLotteryIdAndIssueCode(lotteryId, issueCode);  				  
		}else{
			GameIssue issue=this.gameIssueService.getGameIssueByLotteryAndIssue(lotteryId, issueCode);
			if(issue==null || issue.getNumberRecord()==null){
				throw new Exception("Game issue not exists.(issueCode:"+issueCode+")");
			}
			drawResult=new GameDrawResult(); 
			String numberRecord=issue.getNumberRecord();
			drawResult.setLotteryid(lotteryId);
			drawResult.setIssueCode(issueCode);
			drawResult.setWebIssueCode(issue.getWebIssueCode());
			drawResult.setNumberRecord(numberRecord);
			drawResult.setCreateTime(issue.getCreateTime());
			drawResult.setUpdateTime(issue.getUpdateTime());
			drawResult.setOpenDrawTime(issue.getOpenDrawTime());
			drawResult.setType(0L);      
		}
		Long time2=DateUtils.currentDate().getTime();
    	log.debug("走势图任务生成本期数据--获取开奖号码耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time2-time1));
		//获取上一期的走势数据
		List<GameTrendJbyl> preTrendList = null ;
		List<GameTrendJbyl> newTrendList=null;
		log.debug("走势图任务生成本期数据--获取开奖号码耗时，彩种="+lotteryId+",奖期="+issueCode+",使用者ID="+userId);
		GameTrendJbyl preTrend = gameJbylTrendDao.getPreGameTrendChart(lotteryId, issueCode,userId);	//考虑秒秒彩
		if(preTrend != null){
			log.debug("走势图任务生成本期数据--获取开奖号码耗时，彩种="+lotteryId+",奖期="+issueCode+",上期彩種="+preTrend.getLotteryid()+"上期獎期="+preTrend.getIssueCode());
			LotteryIdAndIssueCode preLotteryIssue = new LotteryIdAndIssueCode(preTrend.getLotteryid(),preTrend.getIssueCode());
			preTrendList = gameJbylTrendDao.getGameTrendChart(preLotteryIssue);		//秒秒彩奖期号唯一，故不用关联userId
		}	
		/*if(null!=preTrendList){
			for(GameTrendJbyl kk:preTrendList){
				log.error("PreTrend.lotteryId="+preTrend.getLotteryid()+" PreTrend.issueCode="+preTrend.getIssueCode()
						+" :trendType="+kk.getTrendType()+" value="+kk.getValue()+" userId="+kk.getUserId());
			}
		}*/
		Long time3=DateUtils.currentDate().getTime();
    	log.debug("走势图任务生成本期数据--获取上一期走势数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time3-time2));
		newTrendList=gameTrendChartRuleListMap.get(lotteryId+"").excuteGenerate(drawResult, preTrendList,userId); 
		/*if(null!=newTrendList){
			for(GameTrendJbyl kk:preTrendList){
				log.error("NewTrend.lotteryId="+preTrend.getLotteryid()+" NewTrend.issueCode="+preTrend.getIssueCode()
						+" :trendType="+kk.getTrendType()+" value="+kk.getValue()+" userId="+kk.getUserId());
			}
		}*/
		Long time4=DateUtils.currentDate().getTime();
    	log.debug("走势图任务生成本期数据--生成当期走势数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time4-time3));
		if(lotteryId!=null && lotteryId!=99112L && lotteryId!=99306L){
	    	//生成投注页遗漏信息
			generateTrendBet(newTrendList);
			Long time5=DateUtils.currentDate().getTime();
	    	log.debug("走势图任务生成本期数据--生成投注页数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time5-time4));
	    	//写首页遗漏信息
	    	refreshHomeOmitInfo();
	    	Long time6=DateUtils.currentDate().getTime();
	    	log.debug("走势图任务生成本期数据--写首页遗漏数据耗时，彩种="+lotteryId+",奖期="+issueCode+",cost time="+(time6-time5));
		}
	}

	/**
	 * 通知下一期(已经开奖)生成走势遗漏数据 
	 * @param lotteryId
	 * @param issueCode
	 * @throws Exception
	 */
	private void noticeNextIssueTrendData(Long lotteryId, Long issueCode,Long trendLotteryId)
			throws Exception {
		GameIssue nextIssue=this.gameIssueService.getNextByLotteryIdAndIssueCode(lotteryId, issueCode);
		if(nextIssue!=null && nextIssue.getNumberRecord()!=null && nextIssue.getNumberRecord().length()>0){
			GameControlEvent event =null;
			if(trendLotteryId==10000L){
				event = this.gameControlEventDao.getSameEventUnexecByLotteryIdAndIssueCodeTrend(trendLotteryId, nextIssue.getIssueCode(), lotteryId);
			}else{
				event = this.gameControlEventDao.getSameEventUnexecByLotteryIdAndIssueCode(lotteryId, nextIssue.getIssueCode());
			}
			if(event==null){
				this.addGameTrendChartRegenerateEvent(nextIssue);
			}
		}
	}
	/**
	 * 添加走势图调度事件
	 * @param gameIssue
	 * @throws Exception
	 */
	private void addGameTrendChartRegenerateEvent(GameIssue gameIssue) throws Exception{ 		
		GameControlEvent event = new GameControlEvent();				
		event.setCreateTime(new Date()); 
		event.setEnentType(18L);
		event.setLotteryid(10000L);
		event.setMessage("生成走势图。");

		GameTrendEventParams params = new GameTrendEventParams();
		params.setLotteryId(gameIssue.getLotteryid());
		params.setIssueCode(gameIssue.getIssueCode());
		params.setType(TrendEventTypeEnum.ADD.getCode());
		params.setNumberRecord(gameIssue.getNumberRecord());
		event.setParams(JsonMapper.nonEmptyMapper().toJson(params));

		event.setSaleEndTime(gameIssue.getSaleEndTime());
		event.setSaleStartTime(gameIssue.getSaleStartTime());
		event.setStartIssueCode(gameIssue.getIssueCode());
		event.setEndIssueCode(gameIssue.getLotteryid());
		event.setStatus(0L);
		gameControlEventDao.insert(event);
	}
	
	/**
	* Title: generateTrendChartData
	* Description:彩种走势图遗漏数据统计入口方法
	* @throws Exception
	*/
	@Override
	@Deprecated
	public void generateTrendChartData() throws Exception {
		log.debug("生成统计数据开始....");
		if (gameTrendChartRuleListMap != null) {
			log.debug("gameTrendChartRuleListMap is not null");
			try {
				//获取所有的彩种的最新的统计奖期
				List<LotteryIdAndIssueCode> lastChartLotteryIdAndIssueCode = gameJbylTrendDao
						.getLastLotteryIdAndIssueCode();
				for (Entry<String, GameTrendChartRuleList> entry : gameTrendChartRuleListMap.entrySet()) {
					Long lotteryId = Long.valueOf(entry.getKey());
					//获取当前彩种的最新统计奖期
					LotteryIdAndIssueCode lastChartIssueCodeForLottery = this.getLastChartByLotteryId(lotteryId,
							lastChartLotteryIdAndIssueCode);
					//获取当前彩种的最新统计数据
					List<GameTrendJbyl> trendList = lastChartIssueCodeForLottery != null ? gameJbylTrendDao
							.getGameTrendChart(lastChartIssueCodeForLottery) : null;
					//获取最新统计奖期的下一期，即为需要生成数据的奖期
					GameDrawResult needGenerategdr = gameDrawResultDao.getNextDrawResuleByLotteryIdAndIssueCode(
							lotteryId,
							lastChartIssueCodeForLottery != null ? lastChartIssueCodeForLottery.getIssueCode() : null);
					//数据统计
					entry.getValue().excuteGenerate(needGenerategdr, trendList,null);

				}
			} catch (Exception e) {
				log.error("生成统计数据错误", e);
				throw e;
			}
		}
		log.debug("生成统计数据结束....");

	}

	private LotteryIdAndIssueCode getLastChartByLotteryId(Long lotteryId,
			List<LotteryIdAndIssueCode> lastChartLotteryIdAndIssueCode) {
		for (LotteryIdAndIssueCode lotteryIdAndIssueCode : lastChartLotteryIdAndIssueCode) {
			if (lotteryIdAndIssueCode.getLotteryid().longValue() == lotteryId.longValue()) {
				return lotteryIdAndIssueCode;
			}
		}
		return null;
	}

	@Deprecated
	@Override	
	public void regenerateTrendChartData(GameControlEvent event) throws Exception{
		

		GameTrendEventParams params = new GameTrendEventParams().fromJson(event.getParams());
        if (TrendEventTypeEnum.ADD.getCode() == params.getType()) {	
        	
        	/*try {
        		gameControlEventService.updateTrendTaskStatusNotCare(event);
			} catch (Exception e) {
				log.error("走势图忽略任务出错！",e);
			}*/
        	
        	
        	regenerateAddTrendChartData( params);         
        }
	}

	public void regenerateAddTrendChartData(GameTrendEventParams params) throws Exception{ 
		//删除  该彩种该期以后的所有数据
		gameJbylTrendDao.deleteFollowGameTrendChart(params.getLotteryId(), params.getIssueCode(),null);
		
		//获取上一期的走势数据
		List<GameTrendJbyl> pretrendList = null ;
		GameTrendJbyl preTrend = gameJbylTrendDao.getPreGameTrendChart(params.getLotteryId(), params.getIssueCode(),null);
		if(preTrend != null){
			LotteryIdAndIssueCode preLotteryIssue = new LotteryIdAndIssueCode(preTrend.getLotteryid(),preTrend.getIssueCode());
			pretrendList = gameJbylTrendDao.getGameTrendChart(preLotteryIssue);
		}	
		
		regenerateAddTrendChartData(pretrendList,params);				
	}
	
	public void regenerateAddTrendChartData(List<GameTrendJbyl> trendList , GameTrendEventParams params)  throws Exception{
		Long lotteryId=params.getLotteryId();
		Long issueCode=params.getIssueCode();
		if(lotteryId!=null && lotteryId!=99112L && lotteryId!=99306L){
			//获取从该彩种这一期开始、100以内的开奖记录		
			List<GameDrawResult> needGenerategdrs = gameDrawResultDao.getFollowDrawResuleByLotteryIdAndIssueCode(lotteryId, issueCode);
			
			int size = needGenerategdrs.size();
			int length = size < 100 ? size : 99;
	
			GameDrawResult drawResut=null;
			for (int i = 0; i < length; i++) {
				//数据统计
				drawResut=needGenerategdrs.get(i);
				trendList = gameTrendChartRuleListMap.get(params.getLotteryId()+"").excuteGenerate(drawResut, trendList,null); 
				
				//generateTrendBet(trendList);
			}
			
			//大于等于100时，实施递归
			if(size >= 100){
				params.setIssueCode(needGenerategdrs.get(99).getIssueCode());		
				regenerateAddTrendChartData(trendList,params);
			}
		}else{
			GameIssue issue=this.gameIssueService.getGameIssueByLotteryAndIssue(lotteryId, issueCode);
			if(issue==null || issue.getNumberRecord()==null){
				throw new Exception("Game issue not exists.(issueCode:"+issueCode+")");
			}
			List<GameOrder> orderList=this.gameOrderService.getGameOrderByIssueAndLottery(lotteryId, issueCode);
			if(orderList==null || orderList.size()==0){
				throw new Exception("Game order not exists.(issueCode:"+issueCode+")");
			}
			//存放秒秒彩userId
			GameOrder order=orderList.get(0);
			Long userId=order.getUserid(); 
			GameDrawResult drawResult=new GameDrawResult(); 
			String numberRecord=issue.getNumberRecord();
			drawResult.setLotteryid(lotteryId);
			drawResult.setIssueCode(issueCode);
			drawResult.setWebIssueCode(issue.getWebIssueCode());
			drawResult.setNumberRecord(numberRecord);
			drawResult.setCreateTime(issue.getCreateTime());
			drawResult.setUpdateTime(issue.getUpdateTime());
			drawResult.setOpenDrawTime(issue.getOpenDrawTime());
			drawResult.setType(0L);     
			gameTrendChartRuleListMap.get(params.getLotteryId()+"").excuteGenerate(drawResult, trendList,userId); 
		}
		
	}
	private void setLastTrendBetMap(GameContext ctx,Long lotteryId,Map<String,GameTrendBet> lastTrendBetMap) throws Exception{	
		log.debug("走势图任务生成本期数据--生成投注页数据耗时-ing setLastTrendBetMap，彩种="+lotteryId+"---1");
		List<GameTrendBet> trendBetList=gameTrendBetService.getByLotteryId(ctx, lotteryId);
		log.debug("走势图任务生成本期数据--生成投注页数据耗时-ing setLastTrendBetMap，彩种="+lotteryId+"---2");
		if(null!=trendBetList){
			log.debug("走势图任务生成本期数据--生成投注页数据耗时-ing setLastTrendBetMap，彩种="+lotteryId+"---"+trendBetList.size());
			for(GameTrendBet trendBet:trendBetList){
				log.debug("走势图任务生成本期数据--生成投注页数据耗时-ing setLastTrendBetMap，彩种="+lotteryId+"---3");
				String key=trendBet.getType()+SEPERATOR_HORI_LOW+trendBet.getGroupCode()+SEPERATOR_HORI_LOW+trendBet.getSetCode()+SEPERATOR_HORI_LOW+trendBet.getMethodCode();
				lastTrendBetMap.put(key, trendBet);
				log.debug("走势图任务生成本期数据--生成投注页数据耗时-ing setLastTrendBetMap，彩种="+lotteryId+"---4");
			}
		}
	}
	/**
	 * 生成走势图投注页信息
	 * @param trendList
	 * @throws Exception
	 */
	private void generateTrendBet(List<GameTrendJbyl> trendList) throws Exception {  
		if(trendList!=null && trendList.size()>0){
			GameContext ctx=new GameContext();
			Long lotteryId=trendList.get(0).getLotteryid(); 
			Long time1=DateUtils.currentDate().getTime();
			Map<TrendType,List<LotteryPlayMethodKeyGenerator>> settingMap=gameTrendBetMap.get(String.valueOf(lotteryId));
			if(settingMap!=null && settingMap.size()>0){
				log.debug("走势图任务生成本期数据--生成投注页数据耗时-before setLastTrendBetMap，彩种="+lotteryId);
				Map<String,GameTrendBet> lastTrendBetMap=new HashMap<String,GameTrendBet>();
				setLastTrendBetMap(ctx,lotteryId,lastTrendBetMap);
				Long time2=DateUtils.currentDate().getTime();
		    	log.debug("走势图任务生成本期数据--生成投注页数据耗时-setLastTrendBetMap，彩种="+lotteryId+",cost time="+(time2-time1));
				for(Map.Entry<TrendType,List<LotteryPlayMethodKeyGenerator>> entry:settingMap.entrySet()){
					List<LotteryPlayMethodKeyGenerator> generatorlist=entry.getValue();
					if(generatorlist!=null && generatorlist.size()>0){
						Integer groupCode=null;
						Integer setCode=null;//==null?0:trendJbyl.getGameSetCode();
						Integer methodCode=null;
						for(LotteryPlayMethodKeyGenerator generator:generatorlist){
							groupCode=generator.getGroupCode();
							setCode=generator.getSetCode();
							methodCode=generator.getMethodCode();
							if(groupCode!=null && setCode!=null && methodCode!=null){
								GameTrendJbyl trendJbylMatch=null;
								int matchType=0;	//2：匹配2项，1：匹配1项 （3项直接跳出）
								for(GameTrendJbyl trendJbyl:trendList){
									if(trendJbyl.getTrendType().equals(String.valueOf(entry.getKey().getValue()))){
										if(groupCode.equals(trendJbyl.getGameGroupCode())
												&& setCode.equals(trendJbyl.getGameSetCode())
												&& methodCode.equals(trendJbyl.getBetMethodCode())){ 
											trendJbylMatch=trendJbyl; 
											break;
										}else if(groupCode.equals(trendJbyl.getGameGroupCode())
												&& setCode.equals(trendJbyl.getGameSetCode())){ 
											if(matchType<2){
												trendJbylMatch=trendJbyl;
												matchType=2;
											}
										}else if(groupCode.equals(trendJbyl.getGameGroupCode())){
											if(matchType<1){
												trendJbylMatch=trendJbyl;
												matchType=1;
											}
										}
									}
								}
								Long time3=DateUtils.currentDate().getTime();
								saveTrendBet(ctx, lotteryId, trendJbylMatch, groupCode, setCode,
										methodCode,lastTrendBetMap);
								Long time4=DateUtils.currentDate().getTime();
						    	log.debug("走势图任务生成本期数据--生成投注页数据耗时-saveTrendBet，彩种="+lotteryId+",cost time="+(time4-time3));
							}
						}
					} 
				}
				lastTrendBetMap=null;
			}
		}
	}
	/**
	 * 生成走势图投注页信息
	 * @param trendList
	 * @throws Exception
	 */
	private void generateTrendBet_bk(List<GameTrendJbyl> trendList) throws Exception {      

		if(trendList!=null && trendList.size()>0){
			GameContext ctx=new GameContext();
			Long lotteryId=trendList.get(0).getLotteryid(); 
			Map<TrendType,List<LotteryPlayMethodKeyGenerator>> settingMap=gameTrendBetMap.get(String.valueOf(lotteryId));
			if(settingMap!=null && settingMap.size()>0){
				for(GameTrendJbyl trendJbyl:trendList){
					TrendType trendTypeReal=null;
					for(TrendType trendType:TrendType.values()){
						if(trendJbyl.getTrendType().equals(String.valueOf(trendType.getValue()))){ 
							trendTypeReal=trendType;
							break;
						}
					}
					if(trendTypeReal!=null){
						Integer groupCode=null;
						Integer setCode=null;//==null?0:trendJbyl.getGameSetCode();
						Integer methodCode=null;//==null?0:trendJbyl.getBetMethodCode();
						//根据配置文件的走势图类型对应的玩法列表 ,否则按位数（只到玩法群）
						List<LotteryPlayMethodKeyGenerator> generatorlist=settingMap.get(trendTypeReal);
						if(generatorlist!=null && generatorlist.size()>0){
							for(LotteryPlayMethodKeyGenerator generator:generatorlist ){ 
								groupCode=generator.getGroupCode();
								setCode=generator.getSetCode();
								methodCode=generator.getMethodCode();
								/*//到 玩法群
								boolean flag=false;
								if(groupCode!=null && setCode!=null && methodCode!=null){
									if(groupCode.equals(trendJbyl.getGameGroupCode())
											&& setCode.equals(trendJbyl.getGameSetCode())
											&& setCode.equals(trendJbyl.getGameSetCode())){ 
										flag=true;
									}else if(groupCode.equals(trendJbyl.getGameGroupCode())
											&& setCode.equals(trendJbyl.getGameSetCode())){ 
										flag=true;
									}else if
								}*/
								if(groupCode!=null && groupCode.equals(trendJbyl.getGameGroupCode())){ 
									saveTrendBet(ctx, lotteryId, trendJbyl, groupCode, setCode,
											methodCode,null);
								}
							}   
						}
						/*else if(trendJbyl.getTrendType().equals(String.valueOf(TrendType.WEISHU.getValue()))){
						groupCode=trendJbyl.getGameGroupCode();
						setCode=trendJbyl.getGameSetCode();//==null?0:trendJbyl.getGameSetCode();
						methodCode=trendJbyl.getBetMethodCode();//==null?0:trendJbyl.getBetMethodCode();
						saveTrendBet(ctx, lotteryId, trendJbyl, groupCode, setCode,
								methodCode);
					} */
					} 
				}
			}
		} 
	}
	private void saveTrendBet(GameContext ctx, Long lotteryId,
			GameTrendJbyl trendJbyl, Integer groupCode, Integer setCode,
			Integer methodCode,Map<String,GameTrendBet> lastTrendBetMap) throws Exception {
		if(trendJbyl!=null){ 
			GameTrendBet lastTrendBet=null; 
			String curValue=trendJbyl.getValue();
			String curWebValue=trendJbyl.getWebValue();
			String newValue=curValue==null?"":curValue;
			String newWebValue=curWebValue==null?"":curWebValue;
			//gameTrendBetService.save(ctx, trendBet); 
			List<GameTrendBet> saveList=new ArrayList<GameTrendBet>();
			GameTrendBet trendBet=null;
			String key="";
			for(GameTrendBet.Type betType:GameTrendBet.Type.values()){
				Long time1=DateUtils.currentDate().getTime();
				trendBet=new GameTrendBet();
				trendBet.setLotteryId(lotteryId);   
				trendBet.setGroupCode(groupCode);
				trendBet.setSetCode(setCode);
				trendBet.setMethodCode(methodCode);  
				trendBet.setType(betType.getValue());
				log.debug("走势图任务生成本期数据--生成投注页数据耗时-trendBet init");
				if(null!=lastTrendBetMap){
					key=betType.getValue()+SEPERATOR_HORI_LOW+groupCode+SEPERATOR_HORI_LOW+setCode+SEPERATOR_HORI_LOW+methodCode;
					log.debug("走势图任务生成本期数据--生成投注页数据耗时-trendBet key:"+key);
					lastTrendBet=lastTrendBetMap.get(key);
					log.debug("走势图任务生成本期数据--生成投注页数据耗时-trendBet last:"+lastTrendBet);
					//lastTrendBet= this.gameTrendBetService.getByLotteryIdAndBetTypeAndType(ctx, lotteryId, groupCode,setCode, methodCode,betType.getValue());
					if(lastTrendBet!=null){   
						log.debug("走势图任务生成本期数据--生成投注页数据耗时-trendBet not null");
						trendBet.setId(lastTrendBet.getId());
						trendBet.setCreateTime(lastTrendBet.getCreateTime());
						IGameTrendBetStrategyService gameTrendBetStrategyService= gameTrendBetStrategyFactory.get(String.valueOf(betType.getValue()));
						newValue=gameTrendBetStrategyService.getBetOmit(trendJbyl,lastTrendBet.getValue(),curValue);
						//newWebValue=calculateOmitWebValue(curWebValue,newValue); 
					}
				}
				log.debug("走势图任务生成本期数据--生成投注页数据耗时-trendBet id:"+trendBet.getId());
				trendBet.setValue(newValue);
				trendBet.setWebValue(newWebValue);
				saveList.add(trendBet);
				Long time2=DateUtils.currentDate().getTime();
		    	log.debug("走势图任务生成本期数据--生成投注页数据耗时-gameTrendBetService.for，彩种="+lotteryId+",cost time="+(time2-time1));
			}
			Long time3=DateUtils.currentDate().getTime();
			gameTrendBetService.save(ctx, saveList);
			Long time4=DateUtils.currentDate().getTime();
	    	log.debug("走势图任务生成本期数据--生成投注页数据耗时-gameTrendBetService.save，彩种="+lotteryId+",cost time="+(time4-time3));
		}
	}
	
	private void refreshHomeOmitInfo() throws Exception{
		log.debug("refreshHomeOmitInfo...........");
		GameContext ctx=new GameContext();
		Long lotteryId3D=99108L;
		Long lotteryIdP5=99109L;
		GameTrendBet trendBet=gameTrendBetService.getByLotteryIdAndBetTypeAndType(ctx, lotteryId3D, 12, 10, 10, 2); 
		if(trendBet!=null){			
			IndexLottery indexLottery =redisService.getHome(lotteryId3D); 
			indexLottery.setOmityTrend(getHomeMaxOmit(3,trendBet.getValue()));
			redisService.setHome(lotteryId3D,indexLottery); 	
		}
		
		trendBet=gameTrendBetService.getByLotteryIdAndBetTypeAndType(ctx, lotteryIdP5, 31, 14, 10, 2); 
		if(trendBet!=null){
			IndexLottery indexLottery =redisService.getHome(lotteryIdP5); 
			indexLottery.setOmityTrend(getHomeMaxOmit(5,trendBet.getValue()));
			redisService.setHome(lotteryIdP5,indexLottery); 		
		}
	}
	private String getHomeMaxOmit(int bits,String value){
		if(bits>0 && !StringUtils.isEmpty(value)){
			String[] valueArr = value.split(",");
			int regionSize = valueArr.length / bits;
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<bits;i++){
				int temp=0;
				int digit=0;
				for(int j=i*regionSize;j<(i+1)*regionSize;j++){ 
					int v=StringUtils.isEmpty(valueArr[j])?0:Integer.valueOf(valueArr[j]);
					if(v>temp){
						temp=v;
						digit=j%regionSize;
					}
				}
				sb.append(digit+","+temp+";");
			} 
			return sb.toString();
		}
		return "";
	}
	 
	/*private String[] getMaxOmit(GameTrendBet lastTrendBet,List<GameTrendJbyl> trendList) throws Exception{
		*//**
		 * 取位数遗漏记录（除北京快乐8取trend_type=2其余取trend_type=1）
		 *//*
		try{
			if(trendList==null || trendList.size()==0){
				String msg="parameter is empty when get max omit value.";
				log.error(msg);
				throw new Exception(msg);
			}
			String[] valueArr=new String[2];
			GameTrendJbyl localTrend=null;
			for(GameTrendJbyl trend:trendList){
				if(trend.getTrendType().equals("1")){
					localTrend=trend;
					break;
				}
				if(trend.getLotteryid()==99201L && trend.getTrendType().equals("2")){
					localTrend=trend;
					break;
				}
			}
			String value=localTrend.getValue(); 
			String webValue=localTrend.getValue(); 
			if(StringUtils.isEmpty(value) || StringUtils.isEmpty(webValue)){
				String msg="failed when calculate omit value.";
				log.error(msg);
				throw new Exception(msg);
			}
			if(lastTrendBet!=null){ 
				String newValue=calculateMaxOmit(lastTrendBet.getValue(),value); 
				String newWebValue=calculateWebMaxOmit(newValue,webValue);
				//如果此次获取历史最大遗漏为空（最大遗漏值没有改变）则保持原值
				valueArr[0]=newValue.equals("")?lastTrendBet.getValue():newValue;
				valueArr[1]=newWebValue.equals("")?lastTrendBet.getWebValue():newWebValue;
			}else{
				valueArr[0]=value;
				valueArr[1]=webValue;
			} 
			return valueArr; 
		}catch(Exception e){
			log.error("error occurs when calculate max omit value.",e);
			throw e;
		} 
	}*/
	
	/**
	 * 计算历史最大遗漏值:历史最大遗漏值=当前遗漏>历史最大遗漏值?当前遗漏：历史最大遗漏值
	 * @param value
	 * @param curValue
	 * @return
	 * @throws Exception
	 */
	private String calculateOmitValue(String lastValue,String curValue) throws Exception{
		if(StringUtils.isEmpty(lastValue) || StringUtils.isEmpty(curValue)){
			String msg="parameter is empty when calculate max omit value.";
			log.error(msg);
			throw new Exception(msg);
		} 
		String[] singleValueArr=lastValue.split(SEPERATOR);
		String[] singleNewValueArr=curValue.split(SEPERATOR);
		if(singleValueArr==null || singleNewValueArr==null || singleValueArr.length!=singleNewValueArr.length){
			String msg="omit values mismatch.";
			log.error(msg);
			throw new Exception(msg);
		}
		int simgleValue=-1;
		int simgleNewValue=-1;
		boolean flag=false;
		StringBuffer newValue=new StringBuffer();
		for(int i=0;i<singleValueArr.length;i++){
			simgleValue=Integer.parseInt(singleValueArr[i]);
			simgleNewValue=Integer.parseInt(singleNewValueArr[i]);
			if(simgleNewValue>simgleValue){
				flag=true;
				singleValueArr[i]=String.valueOf(simgleNewValue);
			}
			newValue.append(singleValueArr[i]).append(",");
		}
		if(flag){
			return "";
		}
		return newValue.substring(0,newValue.length()-1).toString(); 
	}
	/**
	 * 计算历史最大遗漏WEB显示值:根据历史最大遗漏值和当前WEB显示值，构造显示值结构（替换每一位的值，同时如果某一位遗漏值不为0则标识1
	 * 3|0|8|0|0,	3|0|1|0|0,
	 * @param value
	 * @param newValue
	 * @return
	 * @throws Exception
	 */
	private String calculateOmitWebValue(String curWebalue,String newValue) throws Exception{ 
		if(StringUtils.isEmpty(newValue) || StringUtils.isEmpty(curWebalue)){
			String msg="parameter is empty when calculate omit value.";
			log.error(msg);
			throw new Exception(msg);
		}
		
		newValue.split(SEPERATOR);
		
		
		return "";
	}
			
}
