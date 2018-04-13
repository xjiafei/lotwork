package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 獎金組。
 * @author Pogi.Lin
 */
public class GameAwardGroupEntity implements Serializable {

	private static final long serialVersionUID = 8615612796461122261L;
	
	private Long id;
	/**彩種資訊*/
	private Lottery lottery;
	/**奖金組名稱*/
	private String awardName; 
	/**直选及其他返点(六合彩代表特碼-直選返點)*/
	private Long directRet;
	/**三星一码不定位返点*/
	private Long threeoneRet;
	/**超级对子返点*/
	private Long superRet;	//超级对子
	/**状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布*/
	private Status status;
	/**奖金组类型;1:系统奖金组 2:用户奖金组*/
	private SysAwardGroup sysAwardGroup;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	
	private Long awardGroupid;
	
	private List<GameAwardEntity> gameAwards;
	/**六合彩特碼-特肖返点*/
	private Long lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private Long lhcColor;
	/**骰寶猜一個號返點*/
	private Long sbThreeoneRet;
	/**六合彩正碼-平码返点*/
	private Long lhcFlatcode;
	/**六合彩特碼-半波返点*/
	private Long lhcHalfwave;
	/**六合彩正特碼-一肖返点*/
	private Long lhcOneyear;
	/**六合彩正特碼-不中返点*/
	private Long lhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖返点*/
	private Long lhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖返点*/
	private Long lhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖返点*/
	private Long lhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖返点*/
	private Long lhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖返点*/
	private Long lhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖返点*/
	private Long lhcContinuenotin5;
	/**六合彩連碼返点*/
	private Long lhcContinuecode;

	/**
	 * 獎金組明細狀態。<br>
	 * 1:進行中、2:已刪除、3:待審核、4:待發佈、5:未審核、6:未發佈
	 * @author Pogi.Lin
	 */
	public enum Status{
		/**1:進行中*/
		CURRENT(1), 
		/**2:已刪除*/
		DELETE(2),
		/**3:待審核*/
		WATING_AUDIT(3),
		/**4:待發佈*/
		WATING_PUBLISH(4), 
		/**5:未審核*/
		NotAudit(5), 
		/**6:未發佈*/
		NotPublish(6);
		
		private int value = 0;
		Status(int action){
			
			this.value = action;
		}
		
		public int getValue(){
			return value;
		}
	}
	/**
	 * 奖金组类型;1:系统奖金组 2:用户奖金组。
	 * @param SysAwardGroup
	 */
	public enum SysAwardGroup{
		/**1:系统奖金组*/
		SYSTEM(1),
		/**2:用户奖金组*/
		USER(2);
		private int value = 0;
		
		SysAwardGroup(int action){
			 value = action;
		}
		
		public int getValue(){
			return value;
		}
	}

	public GameAwardGroupEntity() {
		
	}
	/**
	 * 取得彩系資訊。
	 * @param lottery
	 */
	public Lottery getLottery() {
		return lottery;
	}
	/**
	 * 設定彩系資訊。
	 * @param lottery
	 */
	public void setLottery(Lottery lottery) {
		this.lottery = lottery;
	}
	/**
	 * 取得奖金組名稱。
	 * @param awardName
	 */
	public String getAwardName() {
		return awardName;
	}
	/**
	 * 設定奖金組名稱。
	 * @param awardName
	 */
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	/**
	 * 取得直选及其他返点(六合彩代表特碼-直選返點)。
	 * @param directRet
	 */
	public Long getDirectRet() {
		return directRet;
	}
	/**
	 * 設定直选及其他返点(六合彩代表特碼-直選返點)。
	 * @param directRet
	 */
	public void setDirectRet(Long directRet) {
		this.directRet = directRet;
	}
	/**
	 * 取得三星一码不定位返点。
	 * @param threeoneRet
	 */
	public Long getThreeoneRet() {
		return threeoneRet;
	}
	/**
	 * 設定三星一码不定位返点。
	 * @param threeoneRet
	 */
	public void setThreeoneRet(Long threeoneRet) {
		this.threeoneRet = threeoneRet;
	}
	/**
	 * 取得状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布。
	 * @param status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * 設定状态;1:待审核 2:待发布 3:进行中 4:进行中,待审核 5:进行中，待发布。
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * 取得创建时间。
	 * @param createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 設定创建时间。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 取得更新时间。
	 * @param updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 設定更新时间。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<GameAwardEntity> getGameAwards() {
		return gameAwards;
	}

	public void setGameAwards(List<GameAwardEntity> gameAwards) {
		this.gameAwards = gameAwards;
	}
	/**
	 * 取得奖金组类型。
	 * @return 1:系统奖金组 2:用户奖金组
	 */
	public SysAwardGroup getSysAwardGroup() {
		return sysAwardGroup;
	}
	/**
	 * 設定奖金组类型。
	 * @param sysAwardGroup 1:系统奖金组 2:用户奖金组
	 */
	public void setSysAwardGroup(SysAwardGroup sysAwardGroup) {
		this.sysAwardGroup = sysAwardGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 取得奖金组ID。
	 * @param awardGroupid
	 */
	public Long getAwardGroupid() {
		return awardGroupid;
	}
	/**
	 * 設定奖金组ID。
	 * @param awardGroupid
	 */
	public void setAwardGroupid(Long awardGroupid) {
		this.awardGroupid = awardGroupid;
	}
	/**
	 * 取得超级对子返点。
	 * @param superRet
	 */
	public Long getSuperRet() {
		return superRet;
	}
	/**
	 * 設定超级对子返点。
	 * @param superRet
	 */
	public void setSuperRet(Long superRet) {
		this.superRet = superRet;
	}
	/**
	 * 取得六合彩特碼-特肖返点。
	 * @param lhcYear
	 */
	public Long getLhcYear() {
		return lhcYear;
	}
	/**
	 * 設定六合彩特碼-特肖返点。
	 * @param lhcYear
	 */
	public void setLhcYear(Long lhcYear) {
		this.lhcYear = lhcYear;
	}
	/**
	 * 取得六合彩特碼-兩面、色波返点。
	 * @param lhcColor
	 */
	public Long getLhcColor() {
		return lhcColor;
	}
	/**
	 * 設定六合彩特碼-兩面、色波返点。
	 * @param lhcColor
	 */
	public void setLhcColor(Long lhcColor) {
		this.lhcColor = lhcColor;
	}
	/**
	 * 取得骰寶猜一個號返點。
	 * @return
	 */
	public Long getSbThreeoneRet() {
		return sbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號返點。
	 * @param sbThreeoneRet
	 */
	public void setSbThreeoneRet(Long sbThreeoneRet) {
		this.sbThreeoneRet = sbThreeoneRet;
	}
	/**
	 * 取得六合彩正碼-平码返点。
	 * @param lhcFlatcode
	 */
	public Long getLhcFlatcode() {
		return lhcFlatcode;
	}
	/**
	 * 設定六合彩正碼-平码返点。
	 * @param lhcFlatcode
	 */
	public void setLhcFlatcode(Long lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}
	/**
	 * 取得六合彩特碼-半波返点。
	 * @param lhcHalfwave
	 */
	public Long getLhcHalfwave() {
		return lhcHalfwave;
	}
	/**
	 * 設定六合彩特碼-半波返点。
	 * @param lhcHalfwave
	 */
	public void setLhcHalfwave(Long lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}
	/**
	 * 取得六合彩正特碼-一肖返点。
	 * @param lhcOneyear
	 */
	public Long getLhcOneyear() {
		return lhcOneyear;
	}
	/**
	 * 設定六合彩正特碼-一肖返点。
	 * @param lhcOneyear
	 */
	public void setLhcOneyear(Long lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}
	/**
	 * 取得六合彩正特碼-不中返点。
	 * @param lhcNotin
	 */
	public Long getLhcNotin() {
		return lhcNotin;
	}
	/**
	 * 設定六合彩正特碼-不中返点。
	 * @param lhcNotin
	 */
	public void setLhcNotin(Long lhcNotin) {
		this.lhcNotin = lhcNotin;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖返点。
	 * @param lhcContinuein23
	 */
	public Long getLhcContinuein23() {
		return lhcContinuein23;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖返点。
	 * @param lhcContinuein23
	 */
	public void setLhcContinuein23(Long lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)四連肖返点。
	 * @param lhcContinuein4
	 */
	public Long getLhcContinuein4() {
		return lhcContinuein4;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)四連肖返点。
	 * @param lhcContinuein4
	 */
	public void setLhcContinuein4(Long lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)五連肖返点。
	 * @param lhcContinuein5
	 */
	public Long getLhcContinuein5() {
		return lhcContinuein5;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)五連肖返点。
	 * @param lhcContinuein5
	 */
	public void setLhcContinuein5(Long lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @param lhcContinuenotin23
	 */
	public Long getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @param lhcContinuenotin23
	 */
	public void setLhcContinuenotin23(Long lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖返点。
	 * @param lhcContinuenotin4
	 */
	public Long getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖返点。
	 * @param lhcContinuenotin4
	 */
	public void setLhcContinuenotin4(Long lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖返点。
	 * @param lhcContinuenotin5
	 */
	public Long getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖返点。
	 * @param lhcContinuenotin5
	 */
	public void setLhcContinuenotin5(Long lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}
	/**
	 * 取得六合彩連碼返点。
	 * @param lhcContinuecode
	 */
	public Long getLhcContinuecode() {
		return lhcContinuecode;
	}
	/**
	 * 設定六合彩連碼返点。
	 * @param lhcContinuecode
	 */
	public void setLhcContinuecode(Long lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
	}

}
