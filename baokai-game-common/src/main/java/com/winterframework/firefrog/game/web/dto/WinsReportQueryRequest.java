package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/** 
* @ClassName: WinsReportQueryRequest 
* @Description: 盈亏报表查询请求参数DTO 
* @author Denny 
* @date 2013-10-16 上午9:49:19 
*  
*/
public class WinsReportQueryRequest implements Serializable {

	private static final long serialVersionUID = -1824844006289311244L;

	private Long lotteryid;
	private Long selectTimeMode; 
	private Long startCreateTime;
	private Long endCreateTime;
	/** 0 默认排序,1 销售总额升序,2 销售总额降序,3 返奖总额升序,4 返奖总额降序,5 盈亏值升序,6 盈亏值降序 */
	private Integer sortType;

	public Long getLotteryid() {
		return lotteryid;
	}

	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
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

	public Integer getSortType() {
		return sortType;
	}

	public void setSortType(Integer sortType) {
		this.sortType = sortType;
	}

}
