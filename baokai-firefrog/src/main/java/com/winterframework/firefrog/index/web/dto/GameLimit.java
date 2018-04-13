/**   
* @Title: GameMethodLimit.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-10-9 下午2:57:50 
* @version V1.0   
*/
package com.winterframework.firefrog.index.web.dto;

/** 
* @ClassName: GameMethodLimit 
* @Description: 游戏玩法限制
* @author Alan
* @date 2013-10-9 下午2:57:50 
*  
*/
public class GameLimit {

	//最大可选倍数(-1表示无限制)
	private Long maxmultiple;
	//用户奖金组
	private Long usergroupmoney;
	public Long getMaxmultiple() {
		return maxmultiple;
	}
	public void setMaxmultiple(Long maxmultiple) {
		this.maxmultiple = maxmultiple;
	}
	public Long getUsergroupmoney() {
		return usergroupmoney;
	}
	public void setUsergroupmoney(Long usergroupmoney) {
		this.usergroupmoney = usergroupmoney;
	}
	
	

}
