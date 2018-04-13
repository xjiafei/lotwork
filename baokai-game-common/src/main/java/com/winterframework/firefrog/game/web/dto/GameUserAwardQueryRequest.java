package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

/**
 * 
* @ClassName: GameAwardQueryRequest 
* @Description: 奖金组奖金查询
* @author Richard
* @date 2013-8-16 上午10:13:52 
*
 */
public class GameUserAwardQueryRequest implements Serializable {

	private static final long serialVersionUID = -4914178551928266359L;

	@NotNull
	private Long lotteryId;
	private Long awardGroupId;
	/**用戶類型；0：總代；1：除總代以外之用戶*/
	private Long type;
	/**返點型態；1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選、7:直選、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點*/
	private Long awardType;
	private Long sysAwardGroupId;
	private Long userId;
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}
	public Long getAwardGroupId() {
		return awardGroupId;
	}
	public void setAwardGroupId(Long awardGroupId) {
		this.awardGroupId = awardGroupId;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	/**
	 * 取得返點型態。
	 * @return 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選、7:直選、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 */
	public Long getAwardType() {
		return awardType;
	}
	/**
	 * 設定返點型態。
	 * @param awardType 1:直選返點、2:不定位返點、3:所有返點、4:任選型返點、5:趣味型返點、6:混選、7:直選、8:超級2000返點、9:特肖返點、10:色波兩面返點、11:平碼返點、12:半波返點、13:一肖返點、14:不中返點、15:連肖(中)二三連肖返點、16:連肖(中)四連肖返點、17:連肖(中)五連肖返點、18:連肖(不中)二三連肖返點、19:連肖(不中)四連肖返點、20:連肖(不中)五連肖返點、21:連碼返點
	 */
	public void setAwardType(Long awardType) {
		this.awardType = awardType;
	}
	public Long getSysAwardGroupId() {
		return sysAwardGroupId;
	}
	public void setSysAwardGroupId(Long sysAwardGroupId) {
		this.sysAwardGroupId = sysAwardGroupId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
