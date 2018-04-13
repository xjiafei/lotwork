package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: GamePlanOperationsRequest 
* @Description: 追号记录运营后台request
* @author Alan
* @date 2013-10-12 上午10:27:26 
*  
*/
public class GamePlanOperationsRequest {

		//彩种id(0全部彩种)
		private Long lotteryid;
		//投注时间选择方式
		private Long selectTimeMode;
		//开始提交时间
		private Long startCreateTime;
		//结束提交时间
		private Long endCreateTime;
		//用户名或方案编号(精确查询)
		private String paramCode;
		//状态(-1全部;0未开始;1进行中;2已结束)
		private Integer status;
		//奖金开始范围
		private Long startWinsCount;
		//奖金结束范围
		private Long endWinsCount;
		//是否包含下级(0不包含;1包含)
		private Integer containSub;
		//追中后续处理(停止方式 0:不停止 1:按累计盈利停止 2:中奖即停)
		private Integer stopMode;
		//盈利参数
		private Long stopParams;
		//排序规则
		private Integer sortType = 1;
		
		private Long issueCode;
		
		private String device;
		
		public String getDevice() {
			return device;
		}
		
		public void setDevice(String device) {
			this.device = device;
		}
		
		public Long getIssueCode() {
			return issueCode;
		}
		public void setIssueCode(Long issueCode) {
			this.issueCode = issueCode;
		}
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
		public String getParamCode() {
			return paramCode;
		}
		public void setParamCode(String paramCode) {
			this.paramCode = paramCode;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Long getStartWinsCount() {
			return startWinsCount;
		}
		public void setStartWinsCount(Long startWinsCount) {
			this.startWinsCount = startWinsCount;
		}
		public Long getEndWinsCount() {
			return endWinsCount;
		}
		public void setEndWinsCount(Long endWinsCount) {
			this.endWinsCount = endWinsCount;
		}
		public Integer getContainSub() {
			return containSub;
		}
		public void setContainSub(Integer containSub) {
			this.containSub = containSub;
		}
		public Integer getStopMode() {
			return stopMode;
		}
		public void setStopMode(Integer stopMode) {
			this.stopMode = stopMode;
		}
		public Long getStopParams() {
			return stopParams;
		}
		public void setStopParams(Long stopParams) {
			this.stopParams = stopParams;
		}
		public Integer getSortType() {
			return sortType;
		}
		public void setSortType(Integer sortType) {
			this.sortType = sortType;
		}

}
