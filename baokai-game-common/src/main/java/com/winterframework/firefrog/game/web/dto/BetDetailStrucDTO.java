package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
 * 投注方案DTO<br>
 * 例:<br>
 * balls:[{ball:'1,2,3,4',type:'wuxing.zhixuan.fushi',moneyunit:0.1,multiple:1,id:2,fileMode:1},{ball:'选球数据',type:'玩法类型',moneyunit:元角模式,multiple:倍数,id:ID编号,fileMode,文件模式}],
 * @author Richard
 * @date 2013-7-22 上午9:47:18 
 */
public class BetDetailStrucDTO implements Serializable {

	private static final long serialVersionUID = -8848174089315020130L;
	/**倍数*/
	private Integer multiple;
	/**投注內容=betDetail*/
	private String ball;
	/**投注方式代碼=gameGroupCode+gameSetCode+betMethodCode*/
	private String type;
	/**元角模式=moneyMode；1:元、2:角*/
	private Double moneyunit;
	private Integer id;
	private Integer num;
	private Long issueCode;
	private Long curr;
	private Long amount;
	/**奖金模式；1:普通、2:高獎金*/
	private Integer awardMode;
	private String content;		//號碼
	private String lotterys;	//號碼
	private Double odds;
	/**单注金额*/
	private Long onePrice;

	/**有封鎖变价的 号码列表*/
	private LockPointRequestDTO lockPoint;
	/**秒秒彩 加獎模式*/
	private Boolean hasdiamondSubmit = false;
	
	/**
	 * 取得倍数。
	 * @return
	 */
	public Integer getMultiple() {
		return multiple;
	}

	/**
	 * 設定倍数。
	 * @return
	 */
	public Long getCurr() {
		return curr;
	}

	public void setCurr(Long curr) {
		this.curr = curr;
	}

	public Long getIssueCode() {
		return issueCode;
	}

	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	/**
	 * 取得投注內容=betDetail。
	 * @return
	 */
	public String getBall() {
		return ball;
	}

	/**
	 * 設定投注內容=betDetail。
	 * @param ball
	 */
	public void setBall(String ball) {
		this.ball = ball;
	}

	/**
	 * 取得投注方式代碼=gameGroupCode+gameSetCode+betMethodCode。
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 設定投注方式代碼=gameGroupCode+gameSetCode+betMethodCode。
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 取得元角模式=moneyMode。
	 * @return 1:元、2:角
	 */
	public Double getMoneyunit() {
		return moneyunit;
	}

	/**
	 * 設定元角模式=moneyMode。
	 * @param moneyunit 1:元、2:角
	 */
	public void setMoneyunit(Double moneyunit) {
		this.moneyunit = moneyunit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 取得有封鎖变价的 号码列表。
	 * @return
	 */
	public LockPointRequestDTO getLockPoint() {
		return lockPoint;
	}
	/**
	 * 設定有封鎖变价的 号码列表。
	 * @param lockPoint
	 */
	public void setLockPoint(LockPointRequestDTO lockPoint) {
		this.lockPoint = lockPoint;
	}
	/**
	 * 取得单注金额。
	 * @return
	 */
	public Long getOnePrice() {
		return onePrice;
	}
	/**
	 * 設定单注金额。
	 * @param onePrice
	 */
	public void setOnePrice(Long onePrice) {
		this.onePrice = onePrice;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
	/**
	 * 取得奖金模式。
	 * @return 1:普通、2:高獎金
	 */
	public Integer getAwardMode() {
		return awardMode;
	}
	/**
	 * 設定奖金模式。
	 * @param awardMode 1:普通、2:高獎金
	 */
	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}
	/**
	 * 取得秒秒彩 加獎模式。
	 * @return
	 */
	public Boolean getHasdiamondSubmit() {
		return hasdiamondSubmit;
	}
	/**
	 * 設定秒秒彩 加獎模式。
	 * @param hasdiamondSubmit
	 */
	public void setHasdiamondSubmit(Boolean hasdiamondSubmit) {
		this.hasdiamondSubmit = hasdiamondSubmit;
	}

    	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getLotterys() {
		return lotterys;
	}

	public void setLotterys(String lotterys) {
		this.lotterys = lotterys;
	}

	public Double getOdds() {
		return odds;
	}

	public void setOdds(Double odds) {
		this.odds = odds;
	}
	
}
