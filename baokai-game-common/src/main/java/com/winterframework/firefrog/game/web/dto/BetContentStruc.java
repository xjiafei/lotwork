/**   
* @Title: BetContentStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-21 下午5:37:46 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: BetContentStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-21 下午5:37:46 
*  
*/
public class BetContentStruc {

	private String number;
	private Integer times;
	private Long subamout;
	//LHC-賠率
	private String singleWin;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getTimes() {
		return times;
	}
	public void setTimes(Integer times) {
		this.times = times;
	}
	public Long getSubamout() {
		return subamout;
	}
	public void setSubamout(Long subamout) {
		this.subamout = subamout;
	}
	public String getSingleWin() {
		return singleWin;
	}
	public void setSingleWin(String singleWin) {
		this.singleWin = singleWin;
	}
}
