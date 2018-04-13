package com.winterframework.firefrog.game.dao.vo;

import java.util.List;

/** 
* @ClassName GameBonusJsonBean 
* @Description 每期中奖玩法奖金结构 
* @author  hugh
* @date 2014年8月1日 上午11:17:28 
*  
*/
public class GameBonusJsonBean {
	private GameBonusPoolJson gameBonusPool; //奖池
	private List<GameBonusAwardJsonBean> awards;//可能玩法 中奖金额
	
	public GameBonusPoolJson getGameBonusPoolJson() {
		return gameBonusPool;
	}
	public void setGameBonusPoolJson(GameBonusPoolJson gameBonusPool) {
		this.gameBonusPool = gameBonusPool;
	}
	public List<GameBonusAwardJsonBean> getAwards() {
		return awards;
	}
	public void setAwards(List<GameBonusAwardJsonBean> awards) {
		this.awards = awards;
	}

}
