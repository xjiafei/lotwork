package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 訂單明細表。
 * @author cms group
 * @version 1.0
 * @since 1.0
 */
public class GameSlip extends BaseEntity {

	private static final long serialVersionUID = 7744177100299713594L;

	/**訂單ID*/
	private Long orderid;
	/**用戶ID*/
	private Long userid;
	/**獎期編碼*/
	private Long issueCode;
	/**彩種ID*/
	private Long lotteryid;
	/**投注方式編碼*/
	private String betTypeCode;
	/**元角模式；1:元、2:角*/
	private Long moneyMode;
	/**总注数*/
	private Long totbets;
	/**總金額*/
	private Long totamount;
	/**倍數*/
	private Long multiple;
	/**投注內容*/
	private String betDetail;
	/**注單總獎金*/
	private Long evaluateWin = 0L;
	/**注單明細狀態；1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常*/
	private Integer status;
	/**創建時間*/
	private Date createTime;
	/**是否存在多獎金；0:不存在、1存在(需要GAME_SLIP_ASSIST輔助)*/
	private Long mutiAward;
	/**单注中奖金额；多獎金時本欄位為0*/
	private Long singleWin = 0L;
	/**单注最小中奖金额*/
	private Long singleWinDown = 0L;
	/**中奖注数*/
	private Long winNumber = 0L;
	private Long winLevel = 0L;
	/**文件模式；0:非文件、1:文件*/
	private Integer fileMode;
	
	/**开奖中间字段  中奖情况一等奖几注 (包含倍数 比如  100倍 1注 该值为 100) 不存于数据库*/
	private Integer awardOne = 0;
	/**开奖中间字段  中奖情况二等奖几注  不存于数据库*/
	private Integer awardTwo = 0;	
	/**开奖中间字段  中奖情况一等奖总共几注  不存于数据库*/
	private Integer awardOneCount = 0;
	/**开奖中间字段  中奖情况二等奖总共几注  不存于数据库*/
	private Integer awardTwoCount = 0;
	/**奖金模式；1:普通、2:高獎金*/
	private Integer awardMode;
	/**返点*/
	private Long retPoint;
	/**返点奖金；高獎金模式時記錄*/
	private Long retAward;
	/**方案明细ID*/
	private Long packageItemId;
	/**鑽石加獎金額*/
	private Long diamondAmount = 0l;
	/**鑽石中獎金額*/
	private Long diamondWin = 0l;
	
	/**
	 * 訂單明細狀態；1:等待开奖 2:中奖 3:未中奖 4:撤销 5：异常。
	 * @author Pogi.Lin
	 */
	public enum Status{
		/**1:等待开奖*/
		WAIT(1),
		/**2:中奖*/
		WIN(2),
		/**3:未中奖*/
		UN_WIN(3),
		/**4:撤销*/
		CANCEL(4),
		/**5：异常*/
		EXCEP(5);
		private int _value=1;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	public GameSlip() {
	}

	public GameSlip(Long id) {
		this.id = id;
	}
	/**
	 * 設定訂單ID。
	 * @param value
	 */
	public void setOrderid(Long value) {
		this.orderid = value;
	}
	/**
	 * 取得訂單ID。
	 * @return
	 */
	public Long getOrderid() {
		return this.orderid;
	}
	/**
	 * 設定用戶ID。
	 * @param value
	 */
	public void setUserid(Long value) {
		this.userid = value;
	}
	/**
	 * 取得用戶ID。
	 * @return
	 */
	public Long getUserid() {
		return this.userid;
	}
	/**
	 * 設定獎期編碼。
	 * @param value
	 */
	public void setIssueCode(Long value) {
		this.issueCode = value;
	}
	/**
	 * 取得獎期編碼。
	 * @return
	 */
	public Long getIssueCode() {
		return this.issueCode;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Orderid", getOrderid())
				.append("Userid", getUserid()).append("IssueCode", getIssueCode()).append("Lottoryid", getLotteryid())
				.append("BetMethodCode", getBetTypeCode()).append("MoneyMode", getMoneyMode())
				.append("Totbets", getTotbets()).append("Totamount", getTotamount()).append("Multiple", getMultiple())
				.append("BetDetail", getBetDetail()).append("EvaluateWin", getEvaluateWin())
				.append("Status", getStatus()).append("createTime", getCreateTime())
				.append("MutiAward", getMutiAward()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getOrderid()).append(getUserid()).append(getIssueCode())
				.append(getLotteryid()).append(getBetTypeCode()).append(getMoneyMode()).append(getTotbets())
				.append(getTotamount()).append(getMultiple()).append(getBetDetail()).append(getEvaluateWin())
				.append(getStatus()).append(getCreateTime()).append(getMutiAward()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameSlip == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameSlip other = (GameSlip) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getOrderid(), other.getOrderid())

		.append(getUserid(), other.getUserid())

		.append(getIssueCode(), other.getIssueCode())

		.append(getLotteryid(), other.getLotteryid())

		.append(getBetTypeCode(), other.getBetTypeCode())

		.append(getMoneyMode(), other.getMoneyMode())

		.append(getTotbets(), other.getTotbets())

		.append(getTotamount(), other.getTotamount())

		.append(getMultiple(), other.getMultiple())

		.append(getBetDetail(), other.getBetDetail())

		.append(getEvaluateWin(), other.getEvaluateWin())

		.append(getStatus(), other.getStatus())

		.append(getCreateTime(), other.getCreateTime())

		.append(getMutiAward(), other.getMutiAward())

		.isEquals();
	}
	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryid() {
		return lotteryid;
	}
	/**
	 * 設定彩種ID。
	 * @param lotteryid
	 */
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	/**
	 * 取得元角模式。
	 * @return 1:元、2:角
	 */
	public Long getMoneyMode() {
		return moneyMode;
	}
	/**
	 * 設定元角模式。
	 * @param moneyMode 1:元、2:角
	 */
	public void setMoneyMode(Long moneyMode) {
		this.moneyMode = moneyMode;
	}
	/**
	 * 取得总注数。
	 * @return
	 */
	public Long getTotbets() {
		return totbets;
	}
	/**
	 * 設定总注数。
	 * @param totbets
	 */
	public void setTotbets(Long totbets) {
		this.totbets = totbets;
	}
	/**
	 * 取得總金額。
	 * @return
	 */
	public Long getTotamount() {
		return totamount;
	}
	/**
	 * 設定總金額。
	 * @param totamount
	 */
	public void setTotamount(Long totamount) {
		this.totamount = totamount;
	}
	/**
	 * 取得倍數。
	 * @return
	 */
	public Long getMultiple() {
		return multiple;
	}
	/**
	 * 設定倍數。
	 * @param multiple
	 */
	public void setMultiple(Long multiple) {
		this.multiple = multiple;
	}
	/**
	 * 取得投注內容。
	 * @return
	 */
	public String getBetDetail() {
		return betDetail;
	}
	/**
	 * 設定投注內容。
	 * @param betDetail
	 */
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	/**
	 * 取得注單總獎金。
	 * @return
	 */
	public Long getEvaluateWin() {
		if(null==evaluateWin){
			evaluateWin=0L;
		}
		return evaluateWin;
	}
	/**
	 * 設定注單總獎金。
	 * @param evaluateWin
	 */
	public void setEvaluateWin(Long evaluateWin) { 
		this.evaluateWin = evaluateWin;
	}
	/**
	 * 取得注單明細狀態。
	 * @return 1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常(此狀態目前尚不清楚其定義依據為何)
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 設定注單明細狀態。
	 * @param status 1:等待開獎、2:中獎、3:未中獎、4:撤銷、5:異常(此狀態目前尚不清楚其定義依據為何)
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 取得是否存在多獎金
	 * @return 0:不存在、1存在(需要GAME_SLIP_ASSIST輔助)
	 */
	public Long getMutiAward() {
		return mutiAward;
	}

	/**
	 * 設定是否存在多獎金。
	 * @param mutiAward 0:不存在、1存在(需要GAME_SLIP_ASSIST輔助)
	 */
	public void setMutiAward(Long mutiAward) {
		this.mutiAward = mutiAward;
	}
	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 設定創建時間。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 取得投注方式編碼。
	 * @return
	 */
	public String getBetTypeCode() {
		return betTypeCode;
	}
	/**
	 * 設定投注方式編碼。
	 * @param betTypeCode
	 */
	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}
	/**
	 * 取得文件模式。
	 * @return 0:非文件、1:文件
	 */
	public Integer getFileMode() {
		return fileMode;
	}
	/**
	 * 設定文件模式。
	 * @param fileMode 0:非文件、1:文件
	 */
	public void setFileMode(Integer fileMode) {
		this.fileMode = fileMode;
	}
	/**
	 * 取得玩法群編碼。
	 * @return
	 */
	public Integer getGameGroupCode() {
		return Integer.valueOf(betTypeCode.split("_")[0]);
	}
	/**
	 * 取得玩法組編碼。
	 * @return
	 */
	public Integer getGameSetCode() {
		return Integer.valueOf(betTypeCode.split("_")[1]);
	}
	/**
	 * 取得投注方法編碼。
	 * @return
	 */
	public Integer getBetMethodCode() {
		if (betTypeCode.split("_")[2] == null || betTypeCode.split("_")[2].equals("null")) {
			return null;
		}
		return Integer.valueOf(betTypeCode.split("_")[2]);
	}
	/**
	 * 取得开奖中间字段  中奖情况一等奖几注 (包含倍数 比如  100倍 1注 该值为 100) 不存于数据库。
	 * @return
	 */
	public Integer getAwardOne() {
		return awardOne;
	}
	/**
	 * 設定开奖中间字段  中奖情况一等奖几注 (包含倍数 比如  100倍 1注 该值为 100) 不存于数据库。
	 * @param awardOne
	 */
	public void setAwardOne(Integer awardOne) {
		this.awardOne = awardOne;
	}
	/**
	 * 取得开奖中间字段  中奖情况二等奖几注  不存于数据库。
	 * @return
	 */
	public Integer getAwardTwo() {
		return awardTwo;
	}
	/**
	 * 設定开奖中间字段  中奖情况二等奖几注  不存于数据库。
	 * @param awardTwo
	 */
	public void setAwardTwo(Integer awardTwo) {
		this.awardTwo = awardTwo;
	}
	/**
	 * 取得开奖中间字段  中奖情况一等奖总共几注  不存于数据库。
	 * @return
	 */
	public Integer getAwardOneCount() {
		return awardOneCount;
	}
	/**
	 * 設定开奖中间字段  中奖情况一等奖总共几注  不存于数据库。
	 * @param awardOneCount
	 */
	public void setAwardOneCount(Integer awardOneCount) {
		this.awardOneCount = awardOneCount;
	}
	/**
	 * 取得开奖中间字段  中奖情况二等奖总共几注  不存于数据库。
	 * @return
	 */
	public Integer getAwardTwoCount() {
		return awardTwoCount;
	}
	/**
	 * 設定开奖中间字段  中奖情况二等奖总共几注  不存于数据库。
	 * @param awardTwoCount
	 */
	public void setAwardTwoCount(Integer awardTwoCount) {
		this.awardTwoCount = awardTwoCount;
	}
	/**
	 * 取得单注中奖金额；多獎金時本欄位為0。
	 * @return
	 */
	public Long getSingleWin() {
		return singleWin;
	}
	/**
	 * 設定单注中奖金额；多獎金時本欄位為0。
	 * @param singleWin
	 */
	public void setSingleWin(Long singleWin) {
		this.singleWin = singleWin;
	}
	/**
	 * 取得中奖注数。
	 * @return
	 */
	public Long getWinNumber() {
		return winNumber;
	}
	/**
	 * 設定中奖注数。
	 * @param winNumber
	 */
	public void setWinNumber(Long winNumber) {
		this.winNumber = winNumber;
	}

	public Long getWinLevel() {
		return winLevel;
	}

	public void setWinLevel(Long winLevel) {
		this.winLevel = winLevel;
	}
	/**
	 * 取得单注最小中奖金额。
	 * @return
	 */
	public Long getSingleWinDown() {
		return singleWinDown;
	}
	/**
	 * 設定单注最小中奖金额。
	 * @param singleWinDown
	 */
	public void setSingleWinDown(Long singleWinDown) {
		this.singleWinDown = singleWinDown;
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
	 * 取得返点。
	 * @return
	 */
	public Long getRetPoint() {
		if(null==retPoint){
			retPoint=0L;
		}
		return retPoint;
	}
	/**
	 * 設定返点。
	 * @param retPoint
	 */
	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}
	/**
	 * 取得返点奖金；高獎金模式時記錄。
	 * @return
	 */
	public Long getRetAward() {
		if(null==retAward){
			retAward=0L;
		}
		return retAward;
	}
	/**
	 * 設定返点奖金；高獎金模式時記錄。
	 * @param retAward
	 */
	public void setRetAward(Long retAward) {
		this.retAward =retAward;
	}
	/**
	 * 取得方案明细ID。
	 * @return
	 */
	public Long getPackageItemId() {
		return packageItemId;
	}
	/**
	 * 設定方案明细ID。
	 * @param packageItemId
	 */
	public void setPackageItemId(Long packageItemId) {
		this.packageItemId = packageItemId;
	}
	/**
	 * 取得鑽石加獎金額。
	 * @return
	 */
	public Long getDiamondAmount() {
		return diamondAmount;
	}
	/**
	 * 設定鑽石加獎金額。
	 * @param diamondAmount
	 */
	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}
	/**
	 * 取得鑽石中獎金額。
	 * @return
	 */
	public Long getDiamondWin() {
		return diamondWin;
	}
	/**
	 * 設定鑽石中獎金額。
	 * @param diamondWin
	 */
	public void setDiamondWin(Long diamondWin) {
		this.diamondWin = diamondWin;
	}
}
