package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GamePlanParm 
* @Description: 当为追号时 记录选中的期数和相应的倍数，当不为追号时记录投注期号 
* @author david 
* @date 2013-10-8 下午8:24:32 
*/
public class GamePlanParm {
	/**倍数*/
	private Integer multiple;
	/**web奖期*/
	private String number;
	/**奖期*/
	private Long issueCode;

	/**
	 * 取得倍数。
	 * @return
	 */
	public Integer getMultiple() {
		return multiple;
	}
	/**
	 * 設定倍数。
	 * @param multiple
	 */
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	/**
	 * 取得web奖期。
	 * @return
	 */
	public String getNumber() {
		return number;
	}
	/**
	 * 設定web奖期。
	 * @param number
	 */
	public void setNumber(String number) {
		this.number = number;
	}
	/**
	 * 取得奖期。
	 * @return
	 */
	public Long getIssueCode() {
		return issueCode;
	}
	/**
	 * 設定奖期。
	 * @param issueCode
	 */
	public void setIssueCode(Long issueCode) {
		this.issueCode = issueCode;
	}
}
