package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameTrendBet extends BaseEntity {
  
	private static final long serialVersionUID = 4596114633752785136L;
	//alias
	public static final String TABLE_ALIAS = "走势投注页数据表";
	public static final String ALIAS_LOTTERYID = "彩种";
	public static final String ALIAS_GROUP_CODE = "玩法群";
	public static final String ALIAS_SET_CODE = "玩法组";
	public static final String ALIAS_METHOD_CODE = "玩法";
	public static final String ALIAS_TYPE = "类型"; 
	public static final String ALIAS_VALUE = "遗漏值";
	public static final String ALIAS_WEB_VALUE = "遗漏显示值";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间"; 

	//date formats

	//columns START
	private Long lotteryId;
	private Integer groupCode;
	private Integer setCode; 
	private Integer methodCode;
	private Integer type;
	private String value;
	private String webValue; 	
	private Date createTime;
	private Date updateTime;
	//columns END
 

	public enum Type { 
		//类型(1:当前遗漏，2:历史最大遗漏，3:冷热30期，4:冷热60期，5:冷热100期)
		CURRENT(1), MAX(2), HOTCOLD_30(3), HOTCOLD_60(4), HOTCOLD_100(5);
		private int _value = 0; 
		Type(int value) {
			this._value = value;
		} 
		public int getValue() {
			return _value;
		}
	}
	public Long getLotteryId() {
		return lotteryId;
	}
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	} 
	
	public Integer getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}
	public Integer getSetCode() {
		return setCode;
	}
	public void setSetCode(Integer setCode) {
		this.setCode = setCode;
	}
	public Integer getMethodCode() {
		return methodCode;
	}
	public void setMethodCode(Integer methodCode) {
		this.methodCode = methodCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getWebValue() {
		return webValue;
	}
	public void setWebValue(String webValue) {
		this.webValue = webValue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	} 
	 
}
