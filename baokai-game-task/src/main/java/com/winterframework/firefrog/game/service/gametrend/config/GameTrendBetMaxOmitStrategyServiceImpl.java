package com.winterframework.firefrog.game.service.gametrend.config;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendBetStrategyService;
@Service("gameTrendBetMaxOmitStrategyServiceImpl")
public class GameTrendBetMaxOmitStrategyServiceImpl implements IGameTrendBetStrategyService { 
	private static final Logger log = LoggerFactory.getLogger(GameTrendBetMaxOmitStrategyServiceImpl.class);
	@Override
	public String getBetOmit(GameTrendJbyl trendJbyl,String lastValue, String curValue) throws Exception {
		//计算历史最大遗漏值:历史最大遗漏值=当前遗漏>历史最大遗漏值?当前遗漏：历史最大遗漏值  
		if(StringUtils.isEmpty(lastValue) || StringUtils.isEmpty(curValue)){
			String msg="parameter is empty when calculate max omit value.";
			log.error(msg);
			throw new Exception(msg);
		}  
		String[] singleValueArr=lastValue.split(SEPERATOR);
		String[] singleNewValueArr=curValue.split(SEPERATOR);
		if(singleValueArr==null || singleNewValueArr==null || singleValueArr.length==0 || singleNewValueArr.length ==0 || singleValueArr.length!=singleNewValueArr.length){
			String msg="omit values mismatch.lastValue:"+Arrays.toString(singleValueArr)+" newValue:"+Arrays.toString(singleNewValueArr);
			log.error(msg);
			throw new Exception(msg);
		}
		int simgleValue=-1;
		int simgleNewValue=-1; 
		StringBuffer newValue=new StringBuffer();
		if(singleValueArr.length>0){
			for(int i=0;i<singleValueArr.length;i++){
				simgleValue=Integer.parseInt(singleValueArr[i]);
				simgleNewValue=Integer.parseInt(singleNewValueArr[i]);
				if(simgleNewValue>simgleValue){ 
					singleValueArr[i]=String.valueOf(simgleNewValue);
				}
				newValue.append(singleValueArr[i]).append(",");
			} 
			return newValue.substring(0,newValue.length()-1).toString(); 
		}
		return curValue;
	} 
}
