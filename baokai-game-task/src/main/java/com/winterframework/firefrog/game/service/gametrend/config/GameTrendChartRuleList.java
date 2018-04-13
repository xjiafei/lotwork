package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameJbylTrendDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGamePackageService;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;

/** 
* @ClassName: GameTrendChartRuleList 
* @Description: 走势图数据生成规则列表 
* @author Floy 
* @date 2014-4-1 下午4:12:00 
*  
*/
public class GameTrendChartRuleList extends ArrayList<IGameTrendChartGenerate> {

	private static final long serialVersionUID = -223244343434333341L;
	
	private static final Logger log = LoggerFactory.getLogger(GameTrendChartRuleList.class);

	@Resource(name = "gameJbylTrendDaoImpl")
	private IGameJbylTrendDao gameJbylTrendDao;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;
	@Resource(name = "gamePackageServiceImpl")
	private IGamePackageService gamePackageService;
	

	public GameTrendChartRuleList() {
		super();
	}

	public GameTrendChartRuleList(List<IGameTrendChartGenerate> list) {
		super(list);
	}

	public List<GameTrendJbyl> excuteGenerate(GameDrawResult gdr, List<GameTrendJbyl> trendList,Long userId) throws Exception {
		Long time1=DateUtils.currentDate().getTime();
		log.debug("excuteGenerate");
		//秒秒彩设置userId 
		List<GameTrendJbyl> generateResult = new ArrayList<GameTrendJbyl>();
		if (gdr != null && !this.isEmpty()) { 
			log.debug("excute insert");
			for (int i = 0; i < this.size(); i++) {
				//generateResult.add(this.get(i).doGenerateChart(gdr, trendList));
				
				GameTrendJbyl jbyl = this.get(i).doGenerateChart(gdr, trendList);
				log.debug("insert trendJbyl data begin===>trendType="+jbyl.getTrendType());
				jbyl.setNumberRecord(gdr.getNumberRecord());
				jbyl.setUserId(userId); 
				//秒秒彩webIssueCode存方案编码
				/*if(gdr.getLotteryid()==99112L){
					GameOrder order=gameOrderService.getByLotteryIdAndIssueCode(new GameContext(), gdr.getLotteryid(), gdr.getIssueCode());
					if(order!=null){ 
						GamePackage gamePackage=this.gamePackageService.getById(order.getParentid());
						if(gamePackage!=null){
							jbyl.setWebIssueCode(gamePackage.getPackageCode());
						}else{
							log.error("秒秒彩设置方案编号时获取方案为空。");
						} 
					}else{
						log.error("秒秒彩设置方案编号时获取订单为空。");
					} 
				} */
				generateResult.add(jbyl);				
				log.debug("insert trendJbyl data end===>");
			}
			GameTrendJbyl jbylResult = new GameTrendJbyl();
			jbylResult.setLotteryid(gdr.getLotteryid());
			jbylResult.setIssueCode(gdr.getIssueCode());
			jbylResult.setWebIssueCode(gdr.getWebIssueCode());
			jbylResult.setValue(gdr.getNumberRecord());
			jbylResult.setWebValue(gdr.getNumberRecord());
			jbylResult.setTrendType(TrendTypeEnum.NumberRecord.getIndex());
			jbylResult.setCreateTime(new Date());
			jbylResult.setGameGroupCode(1);
			jbylResult.setUserId(userId);

			generateResult.add(jbylResult); 
		} 
		Long time2=DateUtils.currentDate().getTime();
    	log.info("走势图任务生成本期数据--生成本期数据--统计遗漏数据集合耗时，彩种="+gdr.getLotteryid()+",奖期="+gdr.getIssueCode()+",cost time="+(time2-time1));
		gameJbylTrendDao.insert(generateResult); 
		Long time3=DateUtils.currentDate().getTime();
    	log.info("走势图任务生成本期数据--生成本期数据--insert遗漏数据集合耗时，彩种="+gdr.getLotteryid()+",奖期="+gdr.getIssueCode()+",cost time="+(time3-time2));
		return generateResult;
	} 
}
