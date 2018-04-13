package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.game.dao.IGameJbylTrendDao;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendBetStrategyService;
 
public abstract class AbstractGameTrendBetHotColdStrategyService implements IGameTrendBetStrategyService {
	@Resource(name = "gameJbylTrendDaoImpl")
    private IGameJbylTrendDao gameJbylTrendDao;
	
	@Override
	public String getBetOmit(GameTrendJbyl trendJbyl, String lastValue, String curValue) throws Exception{
		/**
		 * 1.取最近(30-1)期计算冷热（遗漏值为0则加1）
		 * 2.加上当前期的遗漏值
		 * ----由于当期期的数据在前面已经添加至数据库，故此处直接取30期
		 */
		Long lotteryId=trendJbyl.getLotteryid();
		Integer groupCode=trendJbyl.getGameGroupCode();
		Integer setCode=trendJbyl.getGameSetCode();
		Integer methodCode=trendJbyl.getBetMethodCode();
		Integer trendType=Integer.valueOf(trendJbyl.getTrendType());
		Integer topNum=this.getNum();
		
		List<String> valueList=new ArrayList<String>(); 
		int[] intNewValueArr=null;
		//取最近30期计算冷热（遗漏值为0则加1）
		List<GameTrendJbyl> trendJbylList=this.gameJbylTrendDao.getLatestByLotteryIdAndBetTypeAndType(lotteryId, groupCode, setCode, methodCode, trendType, topNum);
		if(trendJbylList!=null && trendJbylList.size()>0){ 
			for(GameTrendJbyl jbyl:trendJbylList){
				valueList.add(jbyl.getValue());
			}  
		}
		/*//加上当前期的遗漏值
		valueList.add(curValue);*/
		//计算冷热值
		if(valueList!=null && valueList.size()>0){ 
			int[] intValueArr=null;
			for(String value:valueList){ 
				if(intValueArr==null){ 
					intValueArr=this.parseStringArray(value.split(SEPERATOR));
					intNewValueArr=new int[intValueArr.length]; 
				}else{ 
					intValueArr=this.parseStringArray(value.split(SEPERATOR));
				}
				if(intValueArr!=null){ 
					for(int i=0;i<intValueArr.length;i++){
						if(intValueArr[i]==0){
							intNewValueArr[i]+=1;
						}
					}
				}
			}
		}   
		return this.toSeperatorString(intNewValueArr); 
	}
	
	/**
	 * @return 最近的期数
	 */
	protected abstract Integer getNum();
	/**
	 * @param arrays	["1","2","3","4","5"]
	 * @return	[1,2,3,4,5]
	 */
	private int[] parseStringArray(String[] arrays){
		if(arrays==null) return null;
		int[] intArrays=new int[arrays.length];
		for(int i=0;i<arrays.length;i++){
			intArrays[i]=Integer.valueOf(arrays[i]);
		}
		
		return intArrays;
	}
	/** 
	 * @param arrays	[1,2,3,4,5]
	 * @return "1,2,3,4,5"
	 */
	private String toSeperatorString(int[] arrays){
		if(arrays==null) return ""; 
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<arrays.length;i++){
			sb.append(arrays[i]);
			if((i+1)==arrays.length) break;
			sb.append(SEPERATOR);
		}
		return sb.toString();
	} 
}
