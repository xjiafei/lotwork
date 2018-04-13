/**   
* @Title: GameTrendChartRuleList.java 
* @Package com.winterframework.firefrog.game.service.gametrend.config 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-1 下午4:12:00 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.gametrend.generalrule.BaseGeneralRule;

/** 
* @ClassName: GameTrendChartRuleList 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-4-1 下午4:12:00 
*  
*/
public class GameTrendChartRuleList extends ArrayList<IGameTrendChartGenerate> {

	private static final long serialVersionUID = -223244343434333341L;

	@Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameJbylTrendDao;

	public GameTrendChartRuleList() {
		super();
	}

	public GameTrendChartRuleList(List<IGameTrendChartGenerate> list) {
		super(list);
	}

	public void excuteGenerate(GameDrawResult gdr, List<GameTrendJbyl> trendList) throws Exception {
		List<GameTrendJbyl> generateResult = new ArrayList<GameTrendJbyl>();
		if (gdr != null && !this.isEmpty()) {
			for (int i = 0; i < this.size(); i++) {
				generateResult.add(this.get(i).doGenerateChart(gdr, trendList));
			}
		}
		gameJbylTrendDao.insert(generateResult);
	}
	
	public List<GameTrendJbyl> excuteMMCGenerate(GameDrawResult gdr, List<GameTrendJbyl> trendList, List<TrendType> trendTypes, Long groupCode) throws Exception {
		List<GameTrendJbyl> generateResult = new ArrayList<GameTrendJbyl>();
		if (gdr != null && !this.isEmpty()) {
			for (int i = 0; i < this.size(); i++) {
				BaseGeneralRule baseGeneralRule = (BaseGeneralRule)this.get(i);
				if(groupCode.equals(baseGeneralRule.getGroupCode().longValue())){
					GameTrendJbyl gameTrendJbyl = this.get(i).doGenerateChart(gdr, trendList);
					boolean isMatchType = false;
					for(TrendType trendType : trendTypes){
						if(Integer.valueOf(trendType.getValue()).equals(Integer.valueOf(gameTrendJbyl.getTrendType()))){
							isMatchType = true;
						}
					}
					if(isMatchType){
						gameTrendJbyl.setNumberRecord(gdr.getNumberRecord());
						generateResult.add(gameTrendJbyl);
					}
				}
			}
		}
		return generateResult;
	}
}
