package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GameSpiteOrderQueryRequest 
* @Description: 恶意记录request
* @author Alan
* @date 2013-10-23 下午3:09:44 
*  
*/
public class GameSpiteOrderQueryRequest {

	/**彩种ID*/
	private Long lotteryid;
	/**用户/方案*/
	private String paramCode;
	/**是否包含下级*/
	private Integer containSub;
	/**异常原因（状态）*/
	private Integer status;
	/**投注时间选择方式*/
	private Long selectTimeMode;
	/**开始投注时间*/
	private Long startCreateTime;
	/**结束投注时间*/
	private Long endCreateTime;
	
	public Long getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getParamCode() {
		return paramCode;
	}
	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}
	public Integer getContainSub() {
		return containSub;
	}
	public void setContainSub(Integer containSub) {
		this.containSub = containSub;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getSelectTimeMode() {
		return selectTimeMode;
	}
	public void setSelectTimeMode(Long selectTimeMode) {
		this.selectTimeMode = selectTimeMode;
	}
	public Long getStartCreateTime() {
		return startCreateTime;
	}
	public void setStartCreateTime(Long startCreateTime) {
		this.startCreateTime = startCreateTime;
	}
	public Long getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(Long endCreateTime) {
		this.endCreateTime = endCreateTime;
	}

}
