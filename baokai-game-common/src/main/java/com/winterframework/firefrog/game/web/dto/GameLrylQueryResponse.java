package com.winterframework.firefrog.game.web.dto;

import java.util.List;

import com.winterframework.firefrog.game.entity.GameLryl;

/** 
* @ClassName: GameLrylQueryResponse 
* @Description: 冷热遗漏查询返回数据
* @author Denny 
* @date 2013-7-22 下午5:51:42 
*  
*/
public class GameLrylQueryResponse {

	/** 冷热遗漏查询结果 */
	private List<GameLryl> gameLrylNumbers;

	public List<GameLryl> getGameLrylNumbers() {
		return gameLrylNumbers;
	}

	public void setGameLrylNumbers(List<GameLryl> gameLrylNumbers) {
		this.gameLrylNumbers = gameLrylNumbers;
	}
}
