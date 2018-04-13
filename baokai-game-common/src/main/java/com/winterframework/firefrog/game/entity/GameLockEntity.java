package com.winterframework.firefrog.game.entity;

import java.util.Date;

/**
 * 封锁变价实体类
 * @date 2014-5-20 下午5:44:14 
 */
public class GameLockEntity {
	/**彩種ID*/
	private Long gameId;
	/**封鎖值；對應3D、P3、六合彩(特碼)*/
	private Long upVal;
	/**封鎖值2；對應p5、六合彩(正特碼一肖)*/
	private Long upVal2;
	/**封鎖值3；對應六合彩(其他玩法)*/
	private Long upVal3;
	/**紅球封鎖值；對應雙色球*/
	private Long redSlipVal;
	/**藍球封鎖值；對應雙色球*/
	private Long blueSlipVal;
	
	//此处为变价99110的数据----p3
	/**變價最小值；對應p3(From gameLockAppraise)*/
	private Long minVal;
	/**變更結構體；對應p3(From gameLockAppraise)*/
	private String changeStruc;
	/**起始時間；對應p3(From gameLockParam)*/
	private Date startTime;
	/**結束時間；對應p3(From gameLockParam)*/
	private Date endTime;
	/**是哪個模板；a,b,c；對應p3(From gameLockAppraise)*/
	private String template;
	
	//此次为变价99109的数据----p5
	/**變價最小值；對應p5(From gameLockAppraise)*/
	private Long minVal2;
	/**變更結構體；對應p5(From gameLockAppraise)*/
	private String changeStruc2;
	/**起始時間；對應p5(From gameLockParam)*/
	private Date startTime2;
	/**結束時間；對應p5(From gameLockParam)*/
	private Date endTime2;
	/**是哪個模板；a,b,c；對應p5(From gameLockAppraise)*/
	private String template2;

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getGameId() {
		return gameId;
	}

	/**
	 * 設定彩種ID。
	 * @param gameId
	 */
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	/**
	 * 取得封鎖值；對應3D、P3、六合彩(特碼)。
	 * @return
	 */
	public Long getUpVal() {
		return upVal;
	}

	/**
	 * 設定封鎖值；對應3D、P3、六合彩(特碼)。
	 * @param upVal
	 */
	public void setUpVal(Long upVal) {
		this.upVal = upVal;
	}

	/**
	 * 取得變價最小值；對應p3(From gameLockAppraise)。
	 * @return
	 */
	public Long getMinVal() {
		return minVal;
	}

	/**
	 * 設定變價最小值；對應p3(From gameLockAppraise)。
	 * @param minVal
	 */
	public void setMinVal(Long minVal) {
		this.minVal = minVal;
	}

	/**
	 * 取得變更結構體；對應p3(From gameLockAppraise)。
	 * @return
	 */
	public String getChangeStruc() {
		return changeStruc;
	}

	/**
	 * 設定變更結構體；對應p3(From gameLockAppraise)。
	 * @param changeStruc
	 */
	public void setChangeStruc(String changeStruc) {
		this.changeStruc = changeStruc;
	}

	/**
	 * 取得起始時間；對應p3(From gameLockParam)。
	 * @return
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 設定起始時間；對應p3(From gameLockParam)。
	 * @param startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 取得結束時間；對應p3(From gameLockParam)。
	 * @return
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 設定結束時間；對應p3(From gameLockParam)。
	 * @param endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 取得是哪個模板；a,b,c；對應p3(From gameLockAppraise)。
	 * @return
	 */
	public String getTemplate() {
		return template;
	}

	/**
	 * 設定是哪個模板；a,b,c；對應p3(From gameLockAppraise)。
	 * @param template
	 */
	public void setTemplate(String template) {
		this.template = template;
	}

	/**
	 * 取得封鎖值2；對應p5、六合彩(正特碼一肖)。
	 * @return
	 */
	public Long getUpVal2() {
		return upVal2;
	}

	/**
	 * 設定封鎖值2；對應p5、六合彩(正特碼一肖)。
	 * @param upVal2
	 */
	public void setUpVal2(Long upVal2) {
		this.upVal2 = upVal2;
	}
	
	/**
	 * 取得封鎖值3；對應六合彩(其他玩法)。
	 * @return 
	 */
	public Long getUpVal3() {
		return upVal3;
	}

	/**
	 * 設定封鎖值3；對應六合彩(其他玩法)。
	 * @param upVal3 
	 */
	public void setUpVal3(Long upVal3) {
		this.upVal3 = upVal3;
	}

	/**
	 * 取得紅球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getRedSlipVal() {
		return redSlipVal;
	}

	/**
	 * 設定紅球封鎖值；對應雙色球。
	 * @param redSlipVal
	 */
	public void setRedSlipVal(Long redSlipVal) {
		this.redSlipVal = redSlipVal;
	}

	/**
	 * 取得藍球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getBlueSlipVal() {
		return blueSlipVal;
	}

	/**
	 * 設定藍球封鎖值；對應雙色球。
	 * @param blueSlipVal
	 */
	public void setBlueSlipVal(Long blueSlipVal) {
		this.blueSlipVal = blueSlipVal;
	}

	/**
	 * 取得變價最小值；對應p5(From gameLockAppraise)。
	 * @return
	 */
	public Long getMinVal2() {
		return minVal2;
	}

	/**
	 * 設定變價最小值；對應p5(From gameLockAppraise)。
	 * @param minVal2
	 */
	public void setMinVal2(Long minVal2) {
		this.minVal2 = minVal2;
	}

	/**
	 * 取得變更結構體；對應p5(From gameLockAppraise)。
	 * @return
	 */
	public String getChangeStruc2() {
		return changeStruc2;
	}

	/**
	 * 設定變更結構體；對應p5(From gameLockAppraise)。
	 * @param changeStruc2
	 */
	public void setChangeStruc2(String changeStruc2) {
		this.changeStruc2 = changeStruc2;
	}

	/**
	 * 取得起始時間；對應p5。
	 * @return
	 */
	public Date getStartTime2() {
		return startTime2;
	}

	/**
	 * 設定起始時間；對應p5。
	 * @param startTime2
	 */
	public void setStartTime2(Date startTime2) {
		this.startTime2 = startTime2;
	}

	/**
	 * 取得結束時間；對應p5(From gameLockParam)。
	 * @return
	 */
	public Date getEndTime2() {
		return endTime2;
	}

	/**
	 * 設定結束時間；對應p5(From gameLockParam)。
	 * @param endTime2
	 */
	public void setEndTime2(Date endTime2) {
		this.endTime2 = endTime2;
	}

	/**
	 * 取得是哪個模板；a,b,c；對應p5(From gameLockAppraise)。
	 * @return
	 */
	public String getTemplate2() {
		return template2;
	}

	/**
	 * 設定是哪個模板；a,b,c；對應p5(From gameLockAppraise)。
	 * @param template2
	 */
	public void setTemplate2(String template2) {
		this.template2 = template2;
	}
}
