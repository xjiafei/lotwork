/**   
* @Title: GameTrendFenbuChartGenerate.java 
* @Package GameTrendWeishuChartGenerate 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-4-2 下午3:35:12 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.gametrend.generalrule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
/**
 * GameTrendK3Util
 * @ClassName
 * @Description
 * @author ibm
 * 2015年4月16日
 */
public class GameTrendK3Util{ 
	
	/**
	 * 开奖号码是否三同号
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isThreeSame(List<Integer> numberRecordList){
		int tmpNumber=numberRecordList.get(0);
		for(int i=1;i<numberRecordList.size();i++){
			int number=numberRecordList.get(i);
			if(tmpNumber!=number){
				return false;
			}
		}
		return true;
	}
	/**
	 * 开奖号码是否三不同号
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isThreeDiff(List<Integer> numberRecordList){
		Set<Integer> numberSet=null;
		for(Integer number:numberRecordList){
			if(numberSet==null){
				numberSet=new HashSet<Integer>();
				numberSet.add(number);
			}else{
				if(!numberSet.contains(number)){
					numberSet.add(number);
				}else{
					return false;
				}
			}
		}
		return true;
	}
	/**
	 * 开奖号码是否三同号
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isThreeConsecutive(List<Integer> numberRecordList){
		int tmpNumber=numberRecordList.get(0);
		for(int i=1;i<numberRecordList.size();i++){
			int number=numberRecordList.get(i);
			if(tmpNumber+1!=number){
				return false;
			}
			tmpNumber=number;
		}
		return true;
	}
	/**
	 * 开奖号码是否二同号复选
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isTwoSameMulty(List<Integer> numberRecordList){
		Set<Integer> numberSet=new HashSet<Integer>();
		numberSet.add(numberRecordList.get(0));
		for(int i=1;i<numberRecordList.size();i++){
			if(numberSet.contains(numberRecordList.get(i))){
				return true;
			}
			numberSet.add(numberRecordList.get(i));
		}
		return false;
	}
	/**
	 * 开奖号码是否二同号单选
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isTwoSameSingle(List<Integer> numberRecordList){
		return isTwoSameMulty(numberRecordList);
	}
	/**
	 * 开奖号码是否二不同号
	 * @param numberRecordList
	 * @return
	 */
	public static boolean isTwoDiff(List<Integer> numberRecordList){
		int tmpNumber=numberRecordList.get(0);
		for(int i=1;i<numberRecordList.size();i++){
			int number=numberRecordList.get(i);
			if(tmpNumber!=number){
				return true;
			}
		}
		return false;
	} 
	/**
	 * 开奖号码得到二同号的数字
	 * @param numberRecordList
	 * @return
	 */
	public static int getTwoSameNumber(List<Integer> numberRecordList){
		Set<Integer> numberSet=new HashSet<Integer>();
		numberSet.add(numberRecordList.get(0));
		for(int i=1;i<numberRecordList.size();i++){
			Integer number=numberRecordList.get(i);
			if(numberSet.contains(number)){
				return number;
			}else{
				numberSet.add(number);
			}
		}
		return 0;
	}
	/**
	 * 开奖号码得到二同号的其它数字
	 * @param numberRecordList
	 * @return
	 */
	public static int getTwoSameOtherNumber(List<Integer> numberRecordList){
		Set<Integer> numberSet=new HashSet<Integer>();
		numberSet.add(numberRecordList.get(0));
		for(int i=1;i<numberRecordList.size();i++){
			Integer number=numberRecordList.get(i);
			if(numberSet.contains(number)){
				numberSet.remove(number);
			}else{
				numberSet.add(number);
			}
		}
		return (Integer)numberSet.toArray()[0];
	}
	
	public static void main(String[] args){
		List<Integer> l=new ArrayList<Integer>();
		l.add(5);
		l.add(5);
		l.add(5);
		System.out.println(isTwoDiff(l));
	}
	
}
