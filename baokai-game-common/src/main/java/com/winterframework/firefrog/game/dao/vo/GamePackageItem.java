package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

public class GamePackageItem extends BaseEntity {

	private static final long serialVersionUID = 5995296443607015807L;
	//alias
	public static final String TABLE_ALIAS = "方案明细表";
	public static final String ALIAS_PACKAGEID = "方案ID";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_MONEY_MODE = "元角模式";
	public static final String ALIAS_TOTBETS = "注数";
	public static final String ALIAS_TOTAMOUNT = "单注金额";
	public static final String ALIAS_MULTIPLE = "倍数";
	public static final String ALIAS_BET_DETAIL = "投注内容";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_ITEM_AMOUNT = "单行金额";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";

	public static final String ALIAS_fileMode = "是否是文件 1为文件类型";
	public static final String ALIAS_MULT_AWARD = "是否存多奖金 1是";
	public static final String ALIAS_EVALUATE_AWARD = "预计奖金";
	public static final String ALIAS_RET_POINT_CHAIN = "返点链百分比 33,22,11";

	public static final String ALIAS_DIAMOND_AMOUNT = "鑽石加獎金額";
	//date formats

	//columns START
	private Long packageid;
	private String betTypeCode;
	private Integer moneyMode;
	private Long totbets;
	private Long totamount;
	private Integer multiple;
	private String betDetail;
	private Date createTime;
	/**文件模式；0:非文件、1:文件*/
	private Integer fileMode;
	//新增字段
	private Integer mutiAward;
	private Long evaluateAward;
	private String retPointChain;
	private Long retPoint;
	/**奖金模式；1:普通、2:高獎金*/
	private Integer awardMode;
	/**返点奖金；高獎金模式時記錄*/
	private Long retAward;
	
	private Long diamondAmount; //鑽石加獎金額
	//columns END

	/**
	 * 獎金模式<br>
	 * 1:普通、2:高獎金
	 * @author Pogi.Lin
	 */
	public enum AwardMode{
		/**1:普通奖金*/
		NORMAL(1),
		/**2:高奖金*/
		HIGH(2);
		private int _value=1;
		AwardMode(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
		/**
		 * 依據 value 取得 Enum 物件。
		 * @param value 獎金模式；1:普通、2:高獎金
		 * @return 對應不到時回傳 IllegalArgumentException
		 */
		public AwardMode getObject(int value) {
			if(NORMAL.getValue() == value) {
				return NORMAL;
			} else if(HIGH.getValue() == value) {
				return HIGH;
			} else {
				throw new IllegalArgumentException("奖金模式无法判断此来源:" + value);
			}
		}
	}
	public GamePackageItem() {
	}

	public GamePackageItem(Long id) {
		this.id = id;
	}

	public void setPackageid(Long value) {
		this.packageid = value;
	}

	public Long getPackageid() {
		return this.packageid;
	}

	public Long getRetPoint() {
		return retPoint==null?Long.valueOf("0"):retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Integer getMoneyMode() {
		return moneyMode;
	}

	public void setMoneyMode(Integer moneyMode) {
		this.moneyMode = moneyMode;
	}

	public void setTotbets(Long value) {
		this.totbets = value;
	}

	public Long getTotbets() {
		return this.totbets;
	}

	public void setTotamount(Long value) {
		this.totamount = value;
	}

	public Long getTotamount() {
		return this.totamount;
	}

	public void setMultiple(Integer value) {
		this.multiple = value;
	}

	public Integer getMultiple() {
		return this.multiple;
	}

	public void setBetDetail(String value) {
		this.betDetail = value;
	}

	public String getBetDetail() {
		return this.betDetail;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Packageid", getPackageid())
				.append("betTypeCode", getBetTypeCode()).append("MoneyMode", getMoneyMode())
				.append("Totbets", getTotbets()).append("Totamount", getTotamount()).append("Multiple", getMultiple())
				.append("BetDetail", getBetDetail()).append("CreateTime", getCreateTime())
				.append("MultAward", getMutiAward()).append("MultAward", getMutiAward())
				.append("RetPointChain", getRetPointChain()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getPackageid()).append(getBetTypeCode())
				.append(getMoneyMode()).append(getTotbets()).append(getTotamount()).append(getMultiple())
				.append(getBetDetail()).append(getCreateTime()).append(getMutiAward()).append(getMultiple())
				.append(getRetPointChain()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePackageItem == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GamePackageItem other = (GamePackageItem) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getPackageid(), other.getPackageid())

		.append(getBetTypeCode(), other.getBetTypeCode())

		.append(getMoneyMode(), other.getMoneyMode())

		.append(getTotbets(), other.getTotbets())

		.append(getTotamount(), other.getTotamount())

		.append(getMultiple(), other.getMultiple())

		.append(getBetDetail(), other.getBetDetail())

		.append(getCreateTime(), other.getCreateTime())

		.append(getMutiAward(), other.getMutiAward()).append(getMultiple(), other.getMultiple())
				.append(getRetPointChain(), other.getRetPointChain())

				.isEquals();
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

	public Integer getMutiAward() {
		return mutiAward;
	}

	public void setMutiAward(Integer multAward) {
		this.mutiAward = multAward;
	}

	public Long getEvaluateAward() {
		if(null==evaluateAward){
			evaluateAward=0L;
		}
		return evaluateAward;
	}

	public void setEvaluateAward(Long evaluateAward) {
		this.evaluateAward = evaluateAward;
	}

	public String getRetPointChain() {
		return retPointChain;
	}

	public void setRetPointChain(String retPointChain) {
		this.retPointChain = retPointChain;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
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
		this.retAward = retAward;
	}

	public Long getDiamondAmount() {
		return diamondAmount;
	}

	public void setDiamondAmount(Long diamondAmount) {
		this.diamondAmount = diamondAmount;
	}
	
}
