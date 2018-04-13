/**   
* @Title: BaseGeneralRule.java 
* @Package com.winterframework.firefrog.game.service.gametrend.generalrule 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 上午11:24:59 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;

import com.winterframework.firefrog.game.service.utils.NumberTrendsUtils;

/** 
* @ClassName: BaseGeneralRule 
* @Description: 遗漏数据统计规则基类
* @author floy
* @date 2014-4-2 上午11:24:59 
*  
*/
public class BaseGeneralRule {

	/**
	 * 玩法群
	 */
	protected Integer groupCode;

	/**
	 * 玩法组
	 */
	protected Integer setCode;

	/**
	 * 玩法
	 */
	protected Integer methodCode;

	/**遗漏数据初始值*/
	protected String initValue;

	//统计数据类型
	protected String type;

	protected String SEPERATOR = ",";

	//号球开始值
	protected Integer startValue;

	protected static Map<String, Integer[]> GAME_GROUP_NUMBER_BITS_MAP = new HashMap<String, Integer[]>();

	static {
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99101-333", new Integer[] { 1, 3 });
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99102-333", new Integer[] { 1, 3 });
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99103-333", new Integer[] { 1, 3 });
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99104-333", new Integer[] { 1, 3 });	//中三
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99105-333", new Integer[] { 1, 3 });	//中三
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99106-333", new Integer[] { 1, 3 });	//中三
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99111-333", new Integer[] { 1, 3 });	//中三
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-10", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-11", new Integer[] { 1, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-13", new Integer[] { 2, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-14", new Integer[] { 3, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-16", new Integer[] { 0, 4 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99112-333", new Integer[] { 1, 3 });	//中三
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99107-13", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99107-14", new Integer[] { 1, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99107-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99107-16", new Integer[] { 0, 2 });
		
		GAME_GROUP_NUMBER_BITS_MAP.put("99108-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99108-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99108-14", new Integer[] { 1, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99108-16", new Integer[] { 0, 2 });

		GAME_GROUP_NUMBER_BITS_MAP.put("99109-12", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99109-15", new Integer[] { 0, 1 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99109-14", new Integer[] { 1, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99109-30", new Integer[] { 3, 4 });

		GAME_GROUP_NUMBER_BITS_MAP.put("99301-16", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99302-16", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99303-16", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99304-16", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99305-16", new Integer[] { 0, 2 });
		GAME_GROUP_NUMBER_BITS_MAP.put("99306-16", new Integer[] { 0, 2 });

	}

	protected static Map<String, Integer[]> GAME_GROUP_SET_NUMBER_BITS_MAP = new HashMap<String, Integer[]>();

	static {
		GAME_GROUP_SET_NUMBER_BITS_MAP.put("22-12", new Integer[] { 0, 2 });
		GAME_GROUP_SET_NUMBER_BITS_MAP.put("23-10", new Integer[] { 0, 1 });
		GAME_GROUP_SET_NUMBER_BITS_MAP.put("23-11", new Integer[] { 0, 1 });
		GAME_GROUP_SET_NUMBER_BITS_MAP.put("24-10", new Integer[] { 0, 2 });
		GAME_GROUP_SET_NUMBER_BITS_MAP.put("24-11", new Integer[] { 0, 2 });
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	public Integer getGroupCode() {
		return groupCode;
	}

	public void setSetCode(Integer setCode) {
		this.setCode = setCode;
	}

	public void setMethodCode(Integer methodCode) {
		this.methodCode = methodCode;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue;
	}

	public Integer getStartValue() {
		return startValue;
	}

	public void setStartValue(Integer startValue) {
		this.startValue = startValue;
	}

	public String getType() {
		return type;
	}

	//取上一期该类型的统计数据
	public GameTrendJbyl getLastGameTrendJbyl(List<GameTrendJbyl> list) throws Exception {
		GameTrendJbyl result = null;
		if (list != null && list.size()>0) {
			if(this.type!=null && this.groupCode!=null){ 
				int matchType=0;	//2：匹配2项，1：匹配1项 （3项直接跳出）
				for (GameTrendJbyl trendJbyl : list) {
					if(this.type.equals(trendJbyl.getTrendType())){
						if(setCode!=null && methodCode!=null
								&& groupCode.equals(trendJbyl.getGameGroupCode())
								&& setCode.equals(trendJbyl.getGameSetCode())
								&& methodCode.equals(trendJbyl.getBetMethodCode())){ 
							result=trendJbyl; 
							break;
						}else if(setCode!=null
								&& groupCode.equals(trendJbyl.getGameGroupCode())
								&& setCode.equals(trendJbyl.getGameSetCode())){ 
							if(matchType<2){
								result=trendJbyl;
								matchType=2;
							}
						}else if(groupCode.equals(trendJbyl.getGameGroupCode())){
							if(matchType<1){
								result=trendJbyl;
								matchType=1;
							}
						}
					} 
				}
			}
		}
		//数据为空，则使用初始值
		if (result == null) {
			result = new GameTrendJbyl();
			result.setValue(initValue);
		}
		return result;
	}

	public GameTrendJbyl getGameTrendJbyl(String value, String viewValue, GameDrawResult gdr) throws Exception {
		GameTrendJbyl gameJbyTrend = new GameTrendJbyl();
		gameJbyTrend.setBetMethodCode(this.methodCode);
		gameJbyTrend.setCreateTime(new Date());
		gameJbyTrend.setGameGroupCode(this.groupCode);
		gameJbyTrend.setGameSetCode(this.setCode);
		gameJbyTrend.setIssueCode(gdr.getIssueCode());
		gameJbyTrend.setLotteryid(gdr.getLotteryid());
		gameJbyTrend.setTrendType(this.type);
		gameJbyTrend.setValue(value);
		gameJbyTrend.setWebValue(viewValue);
		gameJbyTrend.setWebIssueCode(gdr.getWebIssueCode());
		return gameJbyTrend;
	}

	public List<Integer> getNumberRecordList(GameDrawResult gdr) {
		List<Integer> numberRecordList = NumberTrendsUtils.explode(gdr.getNumberRecord());
		Integer[] arrBits = GAME_GROUP_SET_NUMBER_BITS_MAP.get(groupCode + "-" + setCode);
		if (arrBits == null) {
			arrBits = GAME_GROUP_NUMBER_BITS_MAP.get(gdr.getLotteryid() + "-" + groupCode);
		}
		if (arrBits == null) {
			arrBits = new Integer[] { 0, numberRecordList.size() - 1 };
		}
		return numberRecordList.subList(arrBits[0], arrBits[1] + 1);
	}

	/** 
	* @Title: makeNewOmission 
	* @Description: 根据上期遗漏值及当期非遗漏位生成当前期的遗漏值
	*/
	public String makeNewOmission(String last, int v) {

		StringBuilder current = new StringBuilder();

		String[] arr = last.split(SEPERATOR);

		for (int i = 0; i < arr.length; i++) {
			Integer n = Integer.parseInt(arr[i]);
			if (v == i) {
				current.append("0,");
			} else {
				n++;
				current.append(n).append(",");
			}
		}

		return current.substring(0, current.length() - 1);
	}
}
