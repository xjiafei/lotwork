package com.winterframework.firefrog.phone.web.cotroller.dto;

public class Keys {
	/**彩种名称*/
	private String cnname;
	/**彩种id*/
	private Long lotteryid;
	/**所有玩法反点、任选型玩法返点(六合彩代表特碼-直選返點)*/
	private Float userpoint;
	/**一码不定位返点、趣味型玩法返点*/
	private Float indefinitePoint;
	private Integer selected;
	
	/**六合彩特碼-特肖返点*/
	private Float lhcYear;
	/**六合彩特碼-兩面、色波返点*/
	private Float lhcColor;
	/**骰寶猜一個號返點*/
	private Float sbThreeoneRet;
	/**六合彩正碼-平码返点*/
	private Float lhcFlatcode;
	/**六合彩特碼-半波返点*/
	private Float lhcHalfwave;
	/**六合彩正特碼-一肖返点*/
	private Float lhcOneyear;
	/**六合彩正特碼-不中返点*/
	private Float lhcNotin;
	/**六合彩正特碼-連肖(中)二、三連肖返点*/
	private Float lhcContinuein23;
	/**六合彩正特碼-連肖(中)四連肖返点*/
	private Float lhcContinuein4;
	/**六合彩正特碼-連肖(中)五連肖返点*/
	private Float lhcContinuein5;
	/**六合彩正特碼-連肖(不中)二、三連肖返点*/
	private Float lhcContinuenotin23;
	/**六合彩正特碼-連肖(不中)四連肖返点*/
	private Float lhcContinuenotin4;
	/**六合彩正特碼-連肖(不中)五連肖返点*/
	private Float lhcContinuenotin5;
	/**六合彩連碼返点*/
	private Float lhcContinuecode;
	
	public Integer getSelected() {
		return selected;
	}
	public void setSelected(Integer selected) {
		this.selected = selected;
	}
	/**
	 * 取得一码不定位返点、趣味型玩法返点。
	 * @return
	 */
	public Float getIndefinitePoint() {
		return indefinitePoint;
	}
	/**
	 * 設定一码不定位返点、趣味型玩法返点。
	 * @param indefinitePoint
	 */
	public void setIndefinitePoint(Float indefinitePoint) {
		this.indefinitePoint = indefinitePoint;
	}
	/**
	 * 取得彩种名称。
	 * @return
	 */
	public String getCnname() {
		return cnname;
	}
	/**
	 * 設定彩种名称。
	 * @param cnname
	 */
	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	/**
	 * 取得彩种id。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}
	/**
	 * 設定彩种id。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	/**
	 * 取得所有玩法反点、任选型玩法返点。
	 * @return
	 */
	public Float getUserpoint() {
		return userpoint;
	}
	/**
	 * 設定所有玩法反点、任选型玩法返点。
	 * @param userpoint
	 */
	public void setUserpoint(Float userpoint) {
		this.userpoint = userpoint;
	}
	/**
	 * 取得六合彩特碼-特肖返点。
	 * @return 
	 */
	public Float getLhcYear() {
		return lhcYear;
	}
	/**
	 * 設定六合彩特碼-特肖返点。
	 * @param lhcYear 
	 */
	public void setLhcYear(Float lhcYear) {
		this.lhcYear = lhcYear;
	}
	/**
	 * 取得六合彩特碼-兩面、色波返点。
	 * @return 
	 */
	public Float getLhcColor() {
		return lhcColor;
	}
	/**
	 * 設定六合彩特碼-兩面、色波返点。
	 * @param lhcColor 
	 */
	public void setLhcColor(Float lhcColor) {
		this.lhcColor = lhcColor;
	}
	/**
	 * 取得骰寶猜一個號返點。
	 * @return 
	 */
	public Float getSbThreeoneRet() {
		return sbThreeoneRet;
	}
	/**
	 * 設定骰寶猜一個號返點。
	 * @param sbThreeoneRet 
	 */
	public void setSbThreeoneRet(Float sbThreeoneRet) {
		this.sbThreeoneRet = sbThreeoneRet;
	}
	/**
	 * 取得六合彩正碼-平码返点。
	 * @return 
	 */
	public Float getLhcFlatcode() {
		return lhcFlatcode;
	}
	/**
	 * 設定六合彩正碼-平码返点。
	 * @param lhcFlatcode 
	 */
	public void setLhcFlatcode(Float lhcFlatcode) {
		this.lhcFlatcode = lhcFlatcode;
	}
	/**
	 * 取得六合彩特碼-半波返点。
	 * @return 
	 */
	public Float getLhcHalfwave() {
		return lhcHalfwave;
	}
	/**
	 * 設定六合彩特碼-半波返点。
	 * @param lhcHalfwave 
	 */
	public void setLhcHalfwave(Float lhcHalfwave) {
		this.lhcHalfwave = lhcHalfwave;
	}
	/**
	 * 取得六合彩正特碼-一肖返点。
	 * @return 
	 */
	public Float getLhcOneyear() {
		return lhcOneyear;
	}
	/**
	 * 設定六合彩正特碼-一肖返点。
	 * @param lhcOneyear 
	 */
	public void setLhcOneyear(Float lhcOneyear) {
		this.lhcOneyear = lhcOneyear;
	}
	/**
	 * 取得六合彩正特碼-不中返点。
	 * @return 
	 */
	public Float getLhcNotin() {
		return lhcNotin;
	}
	/**
	 * 設定六合彩正特碼-不中返点。
	 * @param lhcNotin 
	 */
	public void setLhcNotin(Float lhcNotin) {
		this.lhcNotin = lhcNotin;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)二、三連肖返点。
	 * @return 
	 */
	public Float getLhcContinuein23() {
		return lhcContinuein23;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)二、三連肖返点。
	 * @param lhcContinuein23 
	 */
	public void setLhcContinuein23(Float lhcContinuein23) {
		this.lhcContinuein23 = lhcContinuein23;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)四連肖返点。
	 * @return 
	 */
	public Float getLhcContinuein4() {
		return lhcContinuein4;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)四連肖返点。
	 * @param lhcContinuein4 
	 */
	public void setLhcContinuein4(Float lhcContinuein4) {
		this.lhcContinuein4 = lhcContinuein4;
	}
	/**
	 * 取得六合彩正特碼-連肖(中)五連肖返点。
	 * @return 
	 */
	public Float getLhcContinuein5() {
		return lhcContinuein5;
	}
	/**
	 * 設定六合彩正特碼-連肖(中)五連肖返点。
	 * @param lhcContinuein5 
	 */
	public void setLhcContinuein5(Float lhcContinuein5) {
		this.lhcContinuein5 = lhcContinuein5;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @return 
	 */
	public Float getLhcContinuenotin23() {
		return lhcContinuenotin23;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)二、三連肖返点。
	 * @param lhcContinuenotin23 
	 */
	public void setLhcContinuenotin23(Float lhcContinuenotin23) {
		this.lhcContinuenotin23 = lhcContinuenotin23;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)四連肖返点。
	 * @return 
	 */
	public Float getLhcContinuenotin4() {
		return lhcContinuenotin4;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)四連肖返点。
	 * @param lhcContinuenotin4 
	 */
	public void setLhcContinuenotin4(Float lhcContinuenotin4) {
		this.lhcContinuenotin4 = lhcContinuenotin4;
	}
	/**
	 * 取得六合彩正特碼-連肖(不中)五連肖返点。
	 * @return 
	 */
	public Float getLhcContinuenotin5() {
		return lhcContinuenotin5;
	}
	/**
	 * 設定六合彩正特碼-連肖(不中)五連肖返点。
	 * @param lhcContinuenotin5 
	 */
	public void setLhcContinuenotin5(Float lhcContinuenotin5) {
		this.lhcContinuenotin5 = lhcContinuenotin5;
	}
	/**
	 * 取得六合彩連碼返点。
	 * @return 
	 */
	public Float getLhcContinuecode() {
		return lhcContinuecode;
	}
	/**
	 * 設定六合彩連碼返点。
	 * @param lhcContinuecode 
	 */
	public void setLhcContinuecode(Float lhcContinuecode) {
		this.lhcContinuecode = lhcContinuecode;
	}
}
