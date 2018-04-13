package com.winterframework.firefrog.game.service.api.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.api.IGameTrendService;
import com.winterframework.firefrog.game.service.gametrend.config.GameTrendChartRuleList;
import com.winterframework.firefrog.game.service.gametrend.config.TrendTypeEnum;

 
/**
 * 走势图对外服务实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月17日
 */
@Service("gameTrendServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameTrendServiceImpl implements IGameTrendService {

	private static final Logger log = LoggerFactory.getLogger(GameTrendServiceImpl.class);

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	@Resource(name = "sscGameTrendChartRuleList")
	private GameTrendChartRuleList sscGameTrendChartRuleList;
	@Resource(name = "gameIssueServiceImpl")
	private IGameIssueService gameIssueService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	
	@Override
	public List<GameTrendJbyl> getTrendJbyl(Long lotteryId,Long userId,Integer gameGroupCode,Integer trendType,Date startTime,Date endTime,Integer top)
			throws Exception { 
		//时间条件未给值则默认该用户7天之内的数据
		startTime=startTime==null?DateUtils.addDays(DateUtils.currentDate(),-7):startTime;
		endTime=endTime==null?DateUtils.currentDate():endTime;
		List<GameOrder> orderList=this.gameOrderService.getByLotteryUserIdTime(lotteryId,userId,startTime,endTime);
		
		if(orderList!=null && orderList.size()>0){
			List<GameTrendJbyl> generateResult = new ArrayList<GameTrendJbyl>();
			GameDrawResult drawResult=new GameDrawResult();
			int num=0;
			GameIssue issue=null;
			String numberRecord=null;
			for(GameOrder order:orderList){ 
				if(order.getStatus().intValue()==GameOrder.Status.WAIT.getValue()) continue;
				issue=this.gameIssueService.getGameIssueByLotteryAndIssue(lotteryId, order.getIssueCode());
				if(issue==null || issue.getNumberRecord()==null) continue;
				numberRecord=issue.getNumberRecord();
				drawResult.setLotteryid(lotteryId);
				drawResult.setIssueCode(issue.getIssueCode());
				drawResult.setWebIssueCode(issue.getWebIssueCode());
				drawResult.setNumberRecord(numberRecord);
				drawResult.setCreateTime(issue.getCreateTime());
				drawResult.setUpdateTime(issue.getUpdateTime());
				drawResult.setOpenDrawTime(issue.getOpenDrawTime());
				drawResult.setType(0L); 
				if(sscGameTrendChartRuleList!=null && sscGameTrendChartRuleList.size()>0){
					List<GameTrendJbyl> lastTrendJbylList=null;	//MMC不管历史遗漏
					for(int i=0;i<sscGameTrendChartRuleList.size();i++){
						GameTrendJbyl trendJbyl=this.sscGameTrendChartRuleList.get(i).doGenerateChart(drawResult, lastTrendJbylList);
						trendJbyl.setNumberRecord(numberRecord);
						generateResult.add(trendJbyl);
					}  
				} 
				GameTrendJbyl jbylResult = new GameTrendJbyl();
				jbylResult.setLotteryid(drawResult.getLotteryid());
				jbylResult.setIssueCode(drawResult.getIssueCode());
				jbylResult.setWebIssueCode(drawResult.getWebIssueCode());
				jbylResult.setValue(drawResult.getNumberRecord());
				jbylResult.setWebValue(drawResult.getNumberRecord());
				jbylResult.setTrendType(TrendTypeEnum.NumberRecord.getIndex());
				jbylResult.setCreateTime(new Date());
				jbylResult.setGameGroupCode(1);
				generateResult.add(jbylResult);
				//取期数则超过top退出
				if(top!=null && top.intValue()>0){
					num++;
					if(num>=top) break;
				}
			}
			
			return generateResult;
		}
		return null;
		
	}
}
