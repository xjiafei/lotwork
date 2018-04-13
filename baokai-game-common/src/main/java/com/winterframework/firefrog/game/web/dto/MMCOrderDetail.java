package com.winterframework.firefrog.game.web.dto;

public class MMCOrderDetail {

	private String code;
	private Integer isWin;
	private String methodName;
	private String modes;
	private String multiple;
	private Integer num;
	private String projectid;
	private String totalPrice;
	private Long winMoney;
	private Long winNum;
	private String writeTime;
	private Integer awardMode;	//奖金模式
	private Long groupAward;	//奖金组奖金
	private Long groupAwardDown;	//奖金组小奖金
	private Long retPoint;		//返点
	private Long retAward;	//返点奖金
	private Long diamondAmount;//鑽石加獎金額
	private Boolean issuportdiamond;//是否加獎
	private Long diamondWin;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIsWin() {
		return isWin;
	}

	public void setIsWin(Integer isWin) {
		this.isWin = isWin;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getModes() {
		return modes;
	}

	public void setModes(String modes) {
		this.modes = modes;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getProjectid() {
		return projectid;
	}

	public void setProjectid(String projectid) {
		this.projectid = projectid;
	}

	public String getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Long getWinMoney() {
		return winMoney;
	}

	public void setWinMoney(Long winMoney) {
		this.winMoney = winMoney;
	}

	public Long getWinNum() {
		return winNum;
	}

	public void setWinNum(Long winNum) {
		this.winNum = winNum;
	}

	public String getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(String writeTime) {
		this.writeTime = writeTime;
	}

	public Integer getAwardMode() {
		return awardMode;
	}

	public void setAwardMode(Integer awardMode) {
		this.awardMode = awardMode;
	}

	public Long getGroupAward() {
		return groupAward;
	}

	public void setGroupAward(Long groupAward) {
		this.groupAward = groupAward;
	}

	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Long getRetAward() {
		return retAward;
	}

	public void setRetAward(Long retAward) {
		this.retAward = retAward;
	}

	public Long getGroupAwardDown() {
		return groupAwardDown;
	}

	public void setGroupAwardDown(Long groupAwardDown) {
		this.groupAwardDown = groupAwardDown;
	}

	public Long getDiamondAmount() {
		return diamondAmount;
	}

	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}

	public Boolean getIssuportdiamond() {
		return issuportdiamond;
	}

	public void setIssuportdiamond(Boolean issuportdiamond) {
		this.issuportdiamond = issuportdiamond;
	}

	public Long getDiamondWin() {
		return diamondWin;
	}

	public void setDiamondWin(Long diamondWin) {
		this.diamondWin = diamondWin;
	}
	

}
