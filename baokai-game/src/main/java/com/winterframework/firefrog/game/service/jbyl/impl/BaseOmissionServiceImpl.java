package com.winterframework.firefrog.game.service.jbyl.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.entity.TrendType;
import com.winterframework.firefrog.game.service.IGameDrawService;
import com.winterframework.firefrog.game.service.IGameTrendService;
import com.winterframework.firefrog.game.service.jbyl.IBaseOmissionService;
import com.winterframework.firefrog.game.web.dto.BaseChartStruc;
import com.winterframework.firefrog.game.web.dto.BaseTrendChartStruc;

/** 
* @ClassName: BaseOmissionServiceImpl 
* @Description:  
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("baseOmissionServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BaseOmissionServiceImpl implements IBaseOmissionService {

	@Resource(name = "gameTrendAssistServiceImpl")
	private IGameTrendService gameTrendAssistServiceImpl;
	
	@Resource(name = "gameTrendJbylServiceImpl")
	private IGameTrendService gameTrendJbylServiceImpl;

	@Resource(name = "gameDrawServiceImpl")
	private IGameDrawService gameDrawServiceImpl;
	
	/** 不同彩种的选号球范围 */
	private Map<Long, String> ballRengeMap;

	public void setBallRengeMap(Map<Long, String> ballRengeMap) {
		this.ballRengeMap = ballRengeMap;
	}

	@Override
	public List<BaseChartStruc> queryBaseChartStruc(Long lotteryId, Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode, int num) throws Exception {
		
		List<BaseChartStruc> baseChartStrucList = new ArrayList<BaseChartStruc>();
		// 1.取开奖结果列表，并将web期号及开奖号码填充到走势图数据结构体中
		List<GameDrawResult> gameDrawResultList = gameDrawServiceImpl.getAllByLotteryIdAndCountIssue(lotteryId, num);
		for (GameDrawResult gdr : gameDrawResultList) {
			BaseChartStruc baseChartStruc = new BaseChartStruc();
			baseChartStruc.setWebIssueCode(gdr.getWebIssueCode());
			baseChartStruc.setNumberRecord(gdr.getNumberRecord());
			baseChartStrucList.add(baseChartStruc);
		}
		
		// 2.取走势图数据
		Map<TrendType, List<String>> omissionValueMap = gameTrendAssistServiceImpl.queryOmissionValue(lotteryId, gameGroupCode, gameSetCode, betMethodCode, num);
		
		Map<TrendType, List<String>> chartStrucMap;
		
		for (TrendType trendType : omissionValueMap.keySet()) {
			List<String> viewValues = omissionValueMap.get(trendType);
			for (int i = 0; i < viewValues.size(); i++) {
				if (baseChartStrucList.get(i).getChartStruc() == null) {
					chartStrucMap = new HashMap<TrendType, List<String>>();
					baseChartStrucList.get(i).setChartStruc(chartStrucMap);
				}
				
				List<String> singleStrucList = makeSingleStrucList(viewValues.get(i), lotteryId, trendType);
				baseChartStrucList.get(i).getChartStruc().put(trendType, singleStrucList);
			}
		}
		
		return baseChartStrucList;
	}

	/** 
	* @Title: makeSingleStrucList 
	* @Description: 将VIEW_STRUC的值拆分为List：位数遗漏按照位数个数来分，其它类型不拆分 
	* @param   设定文件 
	* @return List<String>    返回类型 
	* @throws 
	*/
	private List<String> makeSingleStrucList(String totalStruc, Long lotteryId, TrendType trendType) {
		
		List<String> list = new ArrayList<String>();;
		
		String[] strucArr = totalStruc.split(",");
		
		String[] ballRenge = ballRengeMap.get(lotteryId).split(",");
		
		int ballStartIndex = Integer.parseInt(ballRenge[0]);
		int ballEndIndex = Integer.parseInt(ballRenge[1]);
		int ballCount = ballEndIndex - ballStartIndex + 1;
		
		StringBuffer sb = null;
		if (trendType == TrendType.WEISHU) {
			for (int i = 0; i < strucArr.length; i++) {
				if (i % ballCount == 0) {
					if (sb != null) {
						list.add(sb.subSequence(0, sb.length()-1).toString());
					} 
					sb = new StringBuffer();
				}
				sb.append(strucArr[i]);
				sb.append(",");
			}
			list.add(sb.subSequence(0, sb.length()-1).toString());
		} else {
			list.add(totalStruc);
		}
		return list;
	}

	@Override
	public BaseTrendChartStruc queryTrendCharts(List<GameDrawResult> gameDrawResultList, Long lotteryId, Integer gameGroupCode, int isGeneral,
			int num) {
		
		BaseTrendChartStruc baseTrendChartStruc = new BaseTrendChartStruc();
		
		List<BaseChartStruc> baseChartStrucList = new ArrayList<BaseChartStruc>();
		for (GameDrawResult gdr : gameDrawResultList) {
			BaseChartStruc baseChartStruc = new BaseChartStruc();
			baseChartStruc.setWebIssueCode(gdr.getWebIssueCode());
			baseChartStruc.setNumberRecord(gdr.getNumberRecord());
			baseChartStrucList.add(baseChartStruc);
		}
		
		// 取走势图数据
		Map<TrendType, List<String>> omissionValueMap = gameTrendJbylServiceImpl.queryOmissionValue(lotteryId, gameGroupCode, null, null, num);
		
		Map<TrendType, List<String>> chartStrucMap;
		
		for (TrendType trendType : omissionValueMap.keySet()) {
			List<String> viewValues = omissionValueMap.get(trendType);
			for (int i = 0; i < viewValues.size(); i++) {
				if (baseChartStrucList.get(i).getChartStruc() == null) {
					chartStrucMap = new HashMap<TrendType, List<String>>();
					baseChartStrucList.get(i).setChartStruc(chartStrucMap);
				}
				
				List<String> singleStrucList = makeSingleStrucList(viewValues.get(i), lotteryId, trendType);
				baseChartStrucList.get(i).getChartStruc().put(trendType, singleStrucList);
			}
		}		
		
		baseTrendChartStruc.setBaseChartStrucs(baseChartStrucList);
		
		return baseTrendChartStruc;
	}

}
