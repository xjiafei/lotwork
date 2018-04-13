package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 六合彩趣味玩法賠率
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月15日
 */
public class LhcGameZodiac implements Serializable {
	private static final long serialVersionUID = 1L;
	/**生肖*/
	private String zodiacNameCn;
	private String zodiacName;
	/**生肖號球(逗號分隔)*/
	private String number;
	private String specialFlag;
	/**
	 * 取得生肖。
	 * @return
	 */
	public String getZodiacNameCn() {
		return zodiacNameCn;
	}
	/**
	 * 設定生肖。
	 * @param zodiacNameCn
	 */
	public void setZodiacNameCn(String zodiacNameCn) {
		this.zodiacNameCn = zodiacNameCn;
	}
	public String getZodiacName() {
		return zodiacName;
	}
	public void setZodiacName(String zodiacName) {
		this.zodiacName = zodiacName;
	}
	/**
	 * 取得生肖號球(逗號分隔)。
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 設定生肖號球(逗號分隔)。
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSpecialFlag() {
		return specialFlag;
	}
	public void setSpecialFlag(String specialFlag) {
		this.specialFlag = specialFlag;
	}
}
