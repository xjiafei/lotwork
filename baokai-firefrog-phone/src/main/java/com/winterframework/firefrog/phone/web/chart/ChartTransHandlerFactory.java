package com.winterframework.firefrog.phone.web.chart;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: BetValidatorFactory 
* @Description: 按一定规则来匹配选择哪一个验证器。 按递减方式来逐个匹配，先是4个key factor都匹配，其次是能匹配3个，类推，最后匹配1个
* @author page
* @date 2013-8-6 下午3:27:40 
*  
*/
public class ChartTransHandlerFactory {
	
	private static Logger log = LoggerFactory.getLogger(ChartTransHandlerFactory.class);
	private static Map<String,String> transMap=new HashMap<String,String>();
	static{
		//ssc 115 3d/p5
		transMap.put("Wuxing", "115x5");
		transMap.put("Qiansan", "qiansan");
		transMap.put("Zhongsan", "qiansan");
		transMap.put("Housan", "qiansan");
		transMap.put("Qianer", "qianer");
		transMap.put("Houer", "qianer"); 
		transMap.put("p5charth2", "qianer");
		transMap.put("p3chart", "qiansan");
		transMap.put("p3chartq2", "qianer");
		transMap.put("p3charth2", "qianer");
		transMap.put("chart", "k3");
		transMap.put("ssq_redball", "redball");
		transMap.put("ssq_blueball", "blueball");
		transMap.put("DEFAULT", "default");
	}
	public static AbstractChartTransHandler getHandler(Long lotteryId,String gameMethod){

		String transKey=transMap.get(gameMethod);
		if(transKey.equals("115x5")){
			return new ChartTrans115x5Handler();
		}else if(transKey.equals("qiansan")){
			return new ChartTransQiansanHandler();
		}else if(transKey.equals("qianer")){
			return new ChartTransQianerHandler();
		}else if(transKey.equals("k3")){
			return new ChartTransK3Handler();
		}else if(transKey.equals("redball")){
			return new ChartTransRedHandler();
		}else if(transKey.equals("blueball")){
			return new ChartTransBlueHandler();
		}else if(transKey.equals("default")){
			return new ChartTransDefaultHandler();
		}
		return null;
	}
	
	


}
