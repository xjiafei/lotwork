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

public class GameHelpAndBonus extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5945856403535195431L;
	//alias
	public static final String TABLE_ALIAS = "投注方式帮忙说明";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GAME_GROUP_CODE = "玩法群";
	public static final String ALIAS_GAME_SET_CODE = "玩法组";
	public static final String ALIAS_BET_METHOD_CODE = "投注方式";
	public static final String ALIAS_GAME_PROMPT_DES = "玩法提示描述";
	public static final String ALIAS_GAME_EXAMPLES_DES = "投注示例";
	public static final String ALIAS_CRETEAT_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_ACTUAL_BONUS = "奖金";

	//date formats

	//columns START
	private Long lotteryid;
	private Integer gameGroupCode;
	private Integer gameSetCode;
	private Integer betMethodCode;
	private String gamePromptDes;
	private String gameExamplesDes;
	private Date creteatTime;
	private Date updateTime;
	private Long actualBonus;
	private Long theoryBonus;	//玩法理论奖金
	private Long retPoint;	//玩法返点
	private Long actualBonusDown;
	private Boolean moreBouns;//玩法多奖金标识，前台投注多奖金无法使用盈利追号以及盈利金额追号

	private String betTypeCode;
	private String methodCodeName; // 玩法名稱
	private String setCodeName;	   // 玩法組名稱
	private String groupCodeName;  // 玩法群名稱
	//private String METHOD_CODE_NAME

	//columns END

	public GameHelpAndBonus() {
	}

	public GameHelpAndBonus(Long id) {
		this.id = id;
	}

	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}

	public Long getLotteryid() {
		return this.lotteryid;
	}

	public void setGamePromptDes(String value) {
		this.gamePromptDes = value;
	}

	public String getGamePromptDes() {
		return this.gamePromptDes;
	}

	public void setGameExamplesDes(String value) {
		this.gameExamplesDes = value;
	}

	public String getGameExamplesDes() {
		return this.gameExamplesDes;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Lotteryid", getLotteryid())
				.append("GameGroupCode", getGameGroupCode()).append("GameSetCode", getGameSetCode())
				.append("BetMethodCode", getBetMethodCode()).append("GamePromptDes", getGamePromptDes())
				.append("GameExamplesDes", getGameExamplesDes()).append("CreteatTime", getCreteatTime())
				.append("UpdateTime", getUpdateTime()).append("ActualBonus", getActualBonus()).toString();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).append(getLotteryid()).append(getGameGroupCode())
				.append(getGameSetCode()).append(getBetMethodCode()).append(getGamePromptDes())
				.append(getGameExamplesDes()).append(getCreteatTime()).append(getUpdateTime()).append(getActualBonus())
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameHelpAndBonus == false) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		GameHelpAndBonus other = (GameHelpAndBonus) obj;
		return new EqualsBuilder().append(getId(), other.getId())

		.append(getLotteryid(), other.getLotteryid())

		.append(getGameGroupCode(), other.getGameGroupCode())

		.append(getGameSetCode(), other.getGameSetCode())

		.append(getBetMethodCode(), other.getBetMethodCode())

		.append(getGamePromptDes(), other.getGamePromptDes())

		.append(getGameExamplesDes(), other.getGameExamplesDes())

		.append(getCreteatTime(), other.getCreteatTime())

		.append(getUpdateTime(), other.getUpdateTime())

		.append(getActualBonus(), other.getActualBonus())

		.isEquals();
	}

	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	public Integer getGameSetCode() {
		return gameSetCode;
	}

	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	public Date getCreteatTime() {
		return creteatTime;
	}

	public void setCreteatTime(Date creteatTime) {
		this.creteatTime = creteatTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getActualBonus() {
		return actualBonus;
	}

	public void setActualBonus(Long actualBonus) {
		this.actualBonus = actualBonus;
	}
	
	public Long getTheoryBonus() {
		return theoryBonus;
	}

	public void setTheoryBonus(Long theoryBonus) {
		this.theoryBonus = theoryBonus;
	}
	
	public Long getRetPoint() {
		return retPoint;
	}

	public void setRetPoint(Long retPoint) {
		this.retPoint = retPoint;
	}

	public Boolean getMoreBouns() {
		return moreBouns;
	}

	public void setMoreBouns(Boolean moreBouns) {
		this.moreBouns = moreBouns;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public String getTypeCode() {
		if (betTypeCode != null) {
			String[] codes = betTypeCode.split("_");
			return codes[codes.length - 1];
		}
		return null;
	}

	public Long getActualBonusDown() {
		return actualBonusDown;
	}

	public void setActualBonusDown(Long actualBonusDown) {
		this.actualBonusDown = actualBonusDown;
	}

	public String getMethodCodeName() {
		return methodCodeName;
	}

	public void setMethodCodeName(String methodCodeName) {
		this.methodCodeName = methodCodeName;
	}

	public String getSetCodeName() {
		return setCodeName;
	}

	public void setSetCodeName(String setCodeName) {
		this.setCodeName = setCodeName;
	}

	public String getGroupCodeName() {
		return groupCodeName;
	}

	public void setGroupCodeName(String groupCodeName) {
		this.groupCodeName = groupCodeName;
	}
	
}
