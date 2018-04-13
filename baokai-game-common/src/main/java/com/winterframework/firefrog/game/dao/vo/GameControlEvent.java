package com.winterframework.firefrog.game.dao.vo;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 任务执行计划表
 * @author cms group
 */
public class GameControlEvent extends BaseEntity {
	
	private static final long serialVersionUID = -6916398503840778265L;
	
	/**彩种ID*/
	private Long lotteryid;
	/**开始奖期，8位日期加4位sequence*/
	private Long startIssueCode;
	/**结束奖期*/
	private Long endIssueCode;
	/**开始销售时间*/
	private Date saleStartTime;
	/**结束销售时间*/
	private Date saleEndTime;
	/**事件完成状态；0:待执行、1:执行中、2:执行完成、3:执行失败、4:已忽略*/
	private Long status;
	/**创建时间*/
	private Date createTime;
	/**更新时间*/
	private Date updateTime;
	/**事件类型；1:奖期销售结束后补、2:开奖、3:撤销开奖时间提前的违规订单、4:撤销本期方案、5:撤销后续追号、6:重新开奖、9:继续派奖、10:补开奖、12:手工输入开奖号码、13:资金请求调用、14:追号任务、15:EC開獎任務、17:资金调用、18:生成走势图（依赖获取开奖号码）、20:生成报表（依赖开奖）*/
	private Long enentType;
	/**异常奖期ID*/
	private Long warnIssueId;
	private Long warnIssueLogId;
	/**消息字段*/
	private String message;
	/**事件业务参数 */
	private String params;
	/**重做次数*/
	private Integer redoNumber;
	/**下次重做时间*/
	private Date nextDoTime;
	/**重新开奖时间*/
	private Date reDrawTime;
	/**执行时间*/
	private Date execTime;

	/**
	 * <pre>
	 * 事件类型
	 * ISSUE_END:1(奖期销售结束后补)
	 * DRAW:2(开奖)
	 * VIOLATION_ORDER_CANCEL:3(撤销开奖时间提前的违规订单)
	 * ISSUE_CANCEL:4(撤销本期方案)
	 * FOLLOW_PLAN_CANCEL:5(撤销后续追号)
	 * REDRAW:6(重新开奖)
	 * DISTRIBUTE_CONTINUE:9(继续派奖)
	 * DRAW_MAKEUP:10(补开奖)
	 * NUMBER_INPUT:12(手工输入开奖号码)
	 * FUND_QUEUE:13(资金请求调用)
	 * PLAN:14(追号任务)
	 * EC_DRAW:15(EC開獎任務)
	 * ORDER_FUND:17(资金调用)
	 * TREND:18(生成走势图（依赖获取开奖号码）)
	 * WIN_REPORT:20(生成报表（依赖开奖）)
	 * </pre>
	 * @author Pogi.Lin
	 */
	public enum EventType{
		/**1(奖期销售结束后补)*/
		ISSUE_END(1), 
		/**2(开奖)*/
		DRAW(2),
		/**3(撤销开奖时间提前的违规订单)*/
		VIOLATION_ORDER_CANCEL(3),		
		/**4(撤销本期方案)*/
		ISSUE_CANCEL(4),
		/**5(撤销后续追号)*/
		FOLLOW_PLAN_CANCEL(5),
		/**6(重新开奖)*/
		REDRAW(6),
		/**9(继续派奖)*/
		DISTRIBUTE_CONTINUE(9),
		/**10(补开奖)*/
		DRAW_MAKEUP(10),
		/**12(手工输入开奖号码)*/
		NUMBER_INPUT(12),
		/**13(资金请求调用)*/
		FUND_QUEUE(13),
		/**14(追号任务)*/
		PLAN(14),
		/**15(EC開獎任務)*/
		EC_DRAW(15),
		/**17(资金调用)*/
		ORDER_FUND(17),
		/**18(生成走势图（依赖获取开奖号码）)*/
		TREND(18),
		/**20(生成报表（依赖开奖）)*/
		WIN_REPORT(20); 
		
		private int _value=1;
		EventType(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	/**
	 * <pre>
	 * 异步类型
	 * TREND:10000(走势图)
	 * WIN_REPORT:10001(奖期盈亏表)
	 * SALE_END:10002(奖期销售结束后补任务)
	 * FUND:10003(资金队列（按订单）)
	 * PLAN:10004(追号任务) 
	 * </pre>
	 * @author Pogi.Lin
	 */
	public enum AsyncType {
		/**10000(走势图)*/
		TREND(10000L),
		/**10001(奖期盈亏表)*/
		WIN_REPORT(10001L),
		/**10002(奖期销售结束后补任务)*/
		SALE_END(10002L),
		/**10003(资金队列（按订单）)*/
		FUND(10003L),
		/**10004(追号任务)*/
		PLAN(10004L); 
		private Long _value;
		AsyncType(Long value){
			this._value=value;
		}
		public Long getValue(){
			return this._value;
		}
	}
	
	/**
	 * <pre>
	 * 事件状态
	 * INIT:0(未执行)
	 * DOING:1(执行中)
	 * SUCCESS:2(执行成功)
	 * FAIL:3(执行失败)
	 * </pre>
	 * @author Pogi.Lin
	 */
	public enum Status{
		/**0(未执行)*/
		INIT(0),
		/**1(执行中)*/
		DOING(1),
		/**2(执行成功)*/
		SUCCESS(2),
		/**3(执行失败)*/
		FAIL(3); 
		private int _value;
		Status(int value){
			this._value=value;
		}
		public int getValue(){
			return this._value;
		}
	}
	
	public GameControlEvent(){
	}

	public GameControlEvent(Long id){
		this.id = id;
	}

	/**
	 * 设定彩种ID。
	 * @param value
	 */
	public void setLotteryid(Long value) {
		this.lotteryid = value;
	}
	/**
	 * 取得彩种ID。
	 * @return
	 */
	public Long getLotteryid() {
		return this.lotteryid;
	}
	/**
	 * 设定开始奖期，8位日期加4位sequence。
	 * @param value
	 */
	public void setStartIssueCode(Long value) {
		this.startIssueCode = value;
	}
	/**
	 * 取得开始奖期，8位日期加4位sequence。
	 * @return
	 */
	public Long getStartIssueCode() {
		return this.startIssueCode;
	}
	/**
	 * 设定结束奖期。
	 * @param value
	 */
	public void setEndIssueCode(Long value) {
		this.endIssueCode = value;
	}
	/**
	 * 取得结束奖期。
	 * @return
	 */
	public Long getEndIssueCode() {
		return this.endIssueCode;
	}
	/**
	 * 取得开始销售时间。
	 * @return
	 */
	public Date getSaleStartTime() {
		return saleStartTime;
	}
	/**
	 * 设定开始销售时间。
	 * @param saleStartTime
	 */
	public void setSaleStartTime(Date saleStartTime) {
		this.saleStartTime = saleStartTime;
	}
	/**
	 * 取得结束销售时间。
	 * @return
	 */
	public Date getSaleEndTime() {
		return saleEndTime;
	}
	/**
	 * 设定结束销售时间。
	 * @param saleEndTime
	 */
	public void setSaleEndTime(Date saleEndTime) {
		this.saleEndTime = saleEndTime;
	}
	/**
	 * 取得创建时间。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设定创建时间。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 取得更新时间。
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设定更新时间。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 设定事件完成状态。
	 * @param value 0:待执行、1:执行中、2:执行完成、3:执行失败、4:已忽略
	 */
	public void setStatus(Long value) {
		this.status = value;
	}
	/**
	 * 取得事件完成状态。
	 * @return 0:待执行、1:执行中、2:执行完成、3:执行失败、4:已忽略
	 */
	public Long getStatus() {
		return this.status;
	}
	/**
	 * 设定事件类型。
	 * @param value 1:奖期销售结束后补、2:开奖、3:撤销开奖时间提前的违规订单、4:撤销本期方案、5:撤销后续追号、6:重新开奖、9:继续派奖、10:补开奖、12:手工输入开奖号码、13:资金请求调用、14:追号任务、15:EC開獎任務、17:资金调用、18:生成走势图（依赖获取开奖号码）、20:生成报表（依赖开奖）
	 */
	public void setEnentType(Long value) {
		this.enentType = value;
	}
	/**
	 * 取得事件类型。
	 * @return 1:奖期销售结束后补、2:开奖、3:撤销开奖时间提前的违规订单、4:撤销本期方案、5:撤销后续追号、6:重新开奖、9:继续派奖、10:补开奖、12:手工输入开奖号码、13:资金请求调用、14:追号任务、15:EC開獎任務、17:资金调用、18:生成走势图（依赖获取开奖号码）、20:生成报表（依赖开奖）
	 */
	public Long getEnentType() {
		return this.enentType;
	}
	/**
	 * 取得异常奖期ID。
	 * @return
	 */
	public Long getWarnIssueId() {
		return warnIssueId;
	}
	/**
	 * 设定异常奖期ID。
	 * @param warnIssueId
	 */
	public void setWarnIssueId(Long warnIssueId) {
		this.warnIssueId = warnIssueId;
	}
	/**
	 * 取得重新开奖时间。
	 * @return
	 */
	public Date getReDrawTime() {
		return reDrawTime;
	}
	/**
	 * 设定重新开奖时间。
	 * @param reDrawTime
	 */
	public void setReDrawTime(Date reDrawTime) {
		this.reDrawTime = reDrawTime;
	}
	/**
	 * 取得消息字段。
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设定消息字段。
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Long getWarnIssueLogId() {
		return warnIssueLogId;
	}

	public void setWarnIssueLogId(Long warnIssueLogId) {
		this.warnIssueLogId = warnIssueLogId;
	}
	/**
	 * 取得事件业务参数。
	 * @return
	 */
	public String getParams() {
		return params;
	}
	/**
	 * 设定事件业务参数。
	 * @param params
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * 取得重做次数。
	 * @return
	 */
	public Integer getRedoNumber() {
		return redoNumber;
	}
	/**
	 * 设定重做次数。
	 * @param redoNumber
	 */
	public void setRedoNumber(Integer redoNumber) {
		this.redoNumber = redoNumber;
	}
	/**
	 * 取得下次重做时间。
	 * @return
	 */
	public Date getNextDoTime() {
		return nextDoTime;
	}
	/**
	 * 设定下次重做时间。
	 * @param nextDoTime
	 */
	public void setNextDoTime(Date nextDoTime) {
		this.nextDoTime = nextDoTime;
	}
	/**
	 * 取得执行时间。
	 * @return
	 */
	public Date getExecTime() {
		return execTime;
	}
	/**
	 * 设定执行时间。
	 * @param execTime
	 */
	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}
	 
}

