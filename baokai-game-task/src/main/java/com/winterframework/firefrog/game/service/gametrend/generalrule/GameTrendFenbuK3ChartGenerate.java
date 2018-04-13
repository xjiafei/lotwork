/**   
* @Title: GameTrendFenbuChartGenerate.java 
* @Package GameTrendWeishuChartGenerate 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:35:12 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.service.gametrend.IGameTrendChartGenerate;
import com.winterframework.firefrog.game.service.wincaculate.util.CaculateUtil;
 
/**
 *GameTrendFenbuK3ChartGenerate
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月16日
 */
public class GameTrendFenbuK3ChartGenerate extends GameTrendFenbuChartGenerate{
 
	protected String calcCurValue(GameDrawResult gdr, String lastValue) {
		StringBuilder omitValue = new StringBuilder();
		String[] lastValueArr = lastValue.split(SEPERATOR);
		if(groupCode==35){
			sthtx(gdr, lastValueArr, omitValue);
		}else if(groupCode==36){
			sthdx(gdr, lastValueArr, omitValue);
		}else if(groupCode==38){
			slhtx(gdr, lastValueArr, omitValue);
		}else if(groupCode==39){
			ethfx(gdr, lastValueArr, omitValue);
		}else if(groupCode==40){
			ethdx(gdr, lastValueArr, omitValue);
		} 
		return omitValue.substring(0, omitValue.length() - 1).toString();
	}
	
	private String sthtx(GameDrawResult gdr,String[] lastValue,StringBuilder omitValue){
		List<Integer> numberRecordList=this.getNumberRecordList(gdr);
		if(GameTrendK3Util.isThreeSame(numberRecordList)){
			omitValue.append("0,");
		}else{
			if(lastValue!=null && lastValue.length>0){				
				Integer n = Integer.parseInt(lastValue[0]); 
				omitValue.append(++n+",");
			}
		}
		return omitValue.toString();
	}
	private String sthdx(GameDrawResult gdr,String[] lastValue,StringBuilder omitValue){
		List<Integer> numberRecordList=this.getNumberRecordList(gdr);
		boolean flag=GameTrendK3Util.isThreeSame(numberRecordList);
		int sumValue=CaculateUtil.getHezhi(gdr.getNumberRecord());
		for (int i = 0; i < lastValue.length; i++) {
			Integer n= Integer.parseInt(lastValue[i]);
			boolean isOmit=true;
			if(flag && (i+1)*3==sumValue){
				isOmit=false;
			}
			n=isOmit?n+1:0;
			omitValue.append(n).append(","); 
		} 
		return omitValue.toString();
	}

	private String slhtx(GameDrawResult gdr,String[] lastValue,StringBuilder omitValue){
		List<Integer> numberRecordList=this.getNumberRecordList(gdr);
		if(GameTrendK3Util.isThreeConsecutive(numberRecordList)){
			omitValue.append("0,");
		}else{
			if(lastValue!=null && lastValue.length>0){				
				Integer n = Integer.parseInt(lastValue[0]); 
				omitValue.append(++n+",");
			}
		}
		return omitValue.toString();
	}
	
	private String ethfx(GameDrawResult gdr,String[] lastValue,StringBuilder omitValue){
		List<Integer> numberRecordList=this.getNumberRecordList(gdr);
		boolean flag=GameTrendK3Util.isTwoSameMulty(numberRecordList); 
		int number=GameTrendK3Util.getTwoSameNumber(numberRecordList);
		for (int i = 0; i < lastValue.length; i++) {
			Integer n= Integer.parseInt(lastValue[i]);
			boolean isOmit=true;
			if(flag && (i+1)==number){
				isOmit=false;
			}
			n=isOmit?n+1:0;
			omitValue.append(n).append(","); 
		} 
		return omitValue.toString();
	}
	private String ethdx(GameDrawResult gdr,String[] lastValue,StringBuilder omitValue){
		List<Integer> numberRecordList=this.getNumberRecordList(gdr);
		boolean flag=GameTrendK3Util.isTwoSameMulty(numberRecordList); 
		int number=GameTrendK3Util.getTwoSameNumber(numberRecordList);
		int otherNumber=GameTrendK3Util.getTwoSameOtherNumber(numberRecordList);
		final int size=6;
		for (int i = 0; i < lastValue.length; i++) {
			Integer n= Integer.parseInt(lastValue[i]);
			boolean isOmit=true;
			if(i<size && flag && (i+1)==number){
				isOmit=false;
			}else if((i-size+1)==otherNumber){
				isOmit=false;
			}
			n=isOmit?n+1:0;
			omitValue.append(n).append(","); 
		} 
		return omitValue.toString();
	}	
}
