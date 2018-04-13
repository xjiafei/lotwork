package com.winterframework.firefrog.game.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.service.ICommonGamePlanService;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameIssueEndAfterService;
import com.winterframework.firefrog.game.service.IGameIssueEndPlanService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.utils.ParamsParserUtil;
import com.winterframework.firefrog.game.web.dto.ActiveUserMigrate;
import com.winterframework.modules.spring.exetend.PropertyConfig;

 
@Service("gameIssueEndAfterServiceImpl") 
@Transactional(rollbackFor = Exception.class)
public class GameIssueEndAfterServiceImpl implements IGameIssueEndAfterService {
	private static final Logger log = LoggerFactory.getLogger(GameIssueEndAfterServiceImpl.class);
	
	@Resource(name = "saleEndGamePlanServiceImpl")
	private IGameIssueEndPlanService saleEndGamePlanService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient; 
	@PropertyConfig(value = "url.business.connect")
	private String businessConnect;
	@Resource(name = "exportFileServiceImpl")
	private IExportFileService exportFileService;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService; 
	@Resource(name = "gamePlanService")
	private ICommonGamePlanService gamePlanService; 
	
	@PropertyConfig(value = "activityEndTime")
	private String activityEndTime;
	@PropertyConfig(value = "url.baseFundUrl")
	private String activityUrl;//投注到活动前缀	
	@PropertyConfig(value = "url.activity.migrate")
	private String migrateUrl;//投注到活动接口url
	@Override
	public void doBusiness(GameControlEvent event) throws Exception {
		/** 
		 * 1.导出当期注单信息
		 * 2.导出活动
		 */
		// 解析event.params 得到需要的业务信息 
		Map map = ParamsParserUtil.parse(event.getParams());
		if (map == null || map.size() == 0) {
			throw new Exception("调度事件的参数不正确");
		}		
		Long lotteryId = map.get("lotteryId") == null ? null : Long.valueOf(((String) map.get("lotteryId")));
		Long issueCode = map.get("issueCode") == null ? null : Long.valueOf(((String) map.get("issueCode"))); 
		if(lotteryId==null || issueCode==null){
			throw new Exception("调度事件的参数不正确");
		} 
		GameIssue issue=this.gameIssueService.getByLotteryIdAndIssueCode(lotteryId, issueCode);
		if(issue==null){
			throw new Exception("奖期不存在:lotteryId="+lotteryId+" issueCode="+issueCode);
		}
		//20140312 add 用于生成本期追号计划，如果用户在奖期1生成奖期2的追号计划。
		log.debug("doBusiness,奖期" + issueCode + ",lotteryId=" + lotteryId + ";生成本期的追号订单信息。");
 
		//3.导出当期注单信息
		//生成本奖期订单数据文件 		
		GameIssueEntity issueEntity=VOConverter.gameIssue2GameIssueEntity(issue); 
		exportFileService.exportGameSlip(issueEntity, "1");		
		//4.导出活动
		//用户投注记录通知活动记录，在活动期间发送到活动中去
		try {
			log.info("---活动订单导出开始----");
			if(new Date().before(DateUtils.getEndTimeOfDate(DateUtils.parse(activityEndTime)))){
				//获取当期的非撤销投注,此记录已过滤一次
				List<GameOrder> gameOders=gameOrderService.getOrderListByGameIssue(issueCode);
				//构建request
				if(!gameOders.isEmpty()&&gameOders.size()>0){
					ActiveUserMigrate[] aums=new ActiveUserMigrate[gameOders.size()];
					for(int i=0;i<gameOders.size();i++){
						ActiveUserMigrate aum=new ActiveUserMigrate();
						aum.setId(gameOders.get(i).getUserid());
						aum.setBetTime(gameOders.get(i).getOrderTime());
//						aum.setFundTime(gameOders.get(i).getOrderTime());
//						aum.setUpdateTime(gameOders.get(i).getOrderTime());
						aums[i]=aum;
					}		
					
					this.activeRequest(aums);
				}
			}
		} catch (Exception e1) {
			log.error("---活动订单导出异常----");
		}  
	} 
	private void activeRequest(final ActiveUserMigrate[] aums){ 
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					httpClient.invokeHttp(activityUrl+migrateUrl, aums);
				} catch (Exception e) {
					log.error("---活动订单导出异常----", e);
				}
			}
		}).start();		
	}
////////////////////////业务服务----end////////////////////////// 
}
