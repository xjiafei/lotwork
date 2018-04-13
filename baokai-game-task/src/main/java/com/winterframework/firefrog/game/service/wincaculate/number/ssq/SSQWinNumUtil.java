package com.winterframework.firefrog.game.service.wincaculate.number.ssq;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SSQWinNumUtil {


	private Integer win1 = 0;
	private Integer win2 = 0;
	private Integer win3 = 0;
	private Integer win4 = 0;
	private Integer win5 = 0;
	private Integer win6 = 0;
	
	private Map<String, Integer> resultMap = new HashMap<String, Integer>();
	{
		resultMap.put("1", win1);
		resultMap.put("2", win2);
		resultMap.put("3", win3);
		resultMap.put("4", win4);
		resultMap.put("5", win5);
		resultMap.put("6", win6);
	}
	
	private void addWin1(Integer win1) {
		this.win1 += win1;
	}
	
	private void addWin2(Integer win2) {
		this.win2 += win2;
	}
	
	private void addWin3(Integer win3) {
		this.win3 += win3;
	}

	private void addWin4(Integer win4) {
		this.win4 += win4;
	}

	private void addWin5(Integer win5) {
		this.win5 += win5;
	}
	
	private void addWin6(Integer win6) {
		this.win6 += win6;
	}
	
	
	/** 
	* @Title: generatorSSQWinNum 
	* @Description: 生成SSQWinNum属性值，  多次赋值执行可累加结果
	* @param retBetDetails
	* @param blueBetDetail
	* @param retResultCodes
	* @param blueResultCode
	*/
	public void generatorSSQWinNum(Set<String> retBetDetails, String blueBetDetail, Set<String> retResultCodes,
			String blueResultCode){
		retBetDetails.addAll(retResultCodes);	
		int retKit = 12-retBetDetails.size();//红球中几个
		int bluekit = blueBetDetail.equals(blueResultCode)?1:0;//蓝球中几个
		
		boolean win1Condition = retKit==6 && bluekit==1;
		boolean win2Condition = retKit==6 && bluekit==0;
		boolean win3Condition = retKit==5 && bluekit==1;
		boolean win4Condition = (retKit==5 && bluekit==0)||(retKit==4 && bluekit==1);
		boolean win5Condition = (retKit==4 && bluekit==0)||(retKit==3 && bluekit==1);
		boolean win6Condition = (retKit==2 && bluekit==1)||(retKit==1 && bluekit==1)||(retKit==0 && bluekit==1);
		
		if(win1Condition){
			addWin1(1);
		}else if(win2Condition){
			addWin2(1);
		}else if(win3Condition){
			addWin3(1);
		}else if(win4Condition){
			addWin4(1);
		}else if(win5Condition){
			addWin5(1);
		}else if(win6Condition){
			addWin6(1);
		}
				
	}

	/** 
	* @Title: getResultMap 
	* @Description: 获取包装ResultMap对象
	* @return
	*/
	public Map<String, Integer> getResultMap() {
		resultMap.put("1", win1);
		resultMap.put("2", win2);
		resultMap.put("3", win3);
		resultMap.put("4", win4);
		resultMap.put("5", win5);
		resultMap.put("6", win6);
		return resultMap;
	};
}
