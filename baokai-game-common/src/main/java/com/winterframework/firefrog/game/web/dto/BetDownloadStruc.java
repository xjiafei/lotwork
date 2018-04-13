/**   
* @Title: BetDownloadStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author floy   
* @date 2015-7-21 下午5:35:18 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/** 
* @ClassName: BetDownloadStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author floy 
* @date 2015-7-21 下午5:35:18 
*  
*/
public class BetDownloadStruc {

	private String gameType;
	private List<BetContentStruc> betContent;
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public List<BetContentStruc> getBetContent() {
		return betContent;
	}
	public void setBetContent(List<BetContentStruc> betContent) {
		this.betContent = betContent;
	}
}
