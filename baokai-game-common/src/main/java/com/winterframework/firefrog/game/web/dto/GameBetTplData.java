package com.winterframework.firefrog.game.web.dto;

import java.util.List;

/**
* @ClassName: GameBetTplData 
* @Description: 返回的提示数据结构 
* @author david 
* @date 2013-9-27 下午3:03:47 
*  
*/
public class GameBetTplData {
	private String msg;
	private List<GameBetBalls> balls;
	private GameBetBitDate bitDate;
	//彩种推荐
    private String  lotteryType;

    private String currentGameNumber;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<GameBetBalls> getBalls() {
		return balls;
	}

	public void setBalls(List<GameBetBalls> balls) {
		this.balls = balls;
	}

	public GameBetBitDate getBitDate() {
		return bitDate;
	}

	public void setBitDate(GameBetBitDate bitDate) {
		this.bitDate = bitDate;
	}

    public String getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(String lotteryType) {
        this.lotteryType = lotteryType;
    }

	public void setCurrentGameNumber(String string) {
		this.currentGameNumber=string;
	}
    public String getCurrentGameNumber() {
		return currentGameNumber;
	}
}
