package com.winterframework.firefrog.game.dao.entity;

import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class GameMmcTask extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	public enum Type{
		UNKNOW_TYPE(-99L),EXPORT_ORDER(1L);
		private Long value;
		Type(Long value){
			this.value = value;
		}
		public Long value(){
			return value;
		}
		public static Type mapping(Long value){
			Type[] types= Type.values();
			Type result = UNKNOW_TYPE;
			for(Type type:types){
				if(type.value().equals(value)){
					result = type;
					break;
				}
			}
			return result;
		}
	}
	
	public enum Status{
		WAIT(-1L),UNDO(0L),DOING(1L),FINISH(2L),FAIL(3L);
		private Long value;
		Status(Long value){
			this.value = value;
		}
		public Long value(){
			return value;
		}
	}
	
	private Long id;

	private Long lotteryid;

	private Long type;

	private Long issue;
	
	private Date issueStartTime;
	
	private Date issueEndTime;

	private Long status;

	private String param;

	private Date createDate;

	private Date updateDate;
	
	private Long targetId;
	
	private Long targetLotteryId;
	
	private Long targetType;
	
	private Long targetIssue;
	
	private Date targetStartTimeStart;
	
	private Date targetStartTimeEnd;
	
	private Date targetEndTimeStart;
	
	private Date targetEndTimeEnd;
	
	private Long targetStatus;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setLotteryid(Long lotteryid){
		 this.lotteryid= lotteryid;
	}

	public Long getLotteryid(){
		return this.lotteryid;
	}

	public void setType(Long type){
		 this.type= type;
	}

	public void setType(Type type){
		 this.type= type.value();
	}

	public Long getType(){
		return this.type;
	}

	public void setIssue(Long issue){
		 this.issue= issue;
	}

	public Long getIssue(){
		return this.issue;
	}
	
	public Date getIssueStartTime() {
		return issueStartTime;
	}

	public void setIssueStartTime(Date issueStartTime) {
		this.issueStartTime = issueStartTime;
	}

	public Date getIssueEndTime() {
		return issueEndTime;
	}

	public void setIssueEndTime(Date issueEndTime) {
		this.issueEndTime = issueEndTime;
	}

	public void setStatus(Long status){
		 this.status= status;
	}

	public void setStatus(Status status){
		 this.status= status.value();
	}

	public Long getStatus(){
		return this.status;
	}

	public void setParam(String param){
		 this.param= param;
	}

	public String getParam(){
		return this.param;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setUpdateDate(Date updateDate){
		 this.updateDate= updateDate;
	}

	public Date getUpdateDate(){
		return this.updateDate;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Long getTargetLotteryId() {
		return targetLotteryId;
	}

	public void setTargetLotteryId(Long targetLotteryId) {
		this.targetLotteryId = targetLotteryId;
	}

	public Long getTargetType() {
		return targetType;
	}

	public void setTargetType(Long targetType) {
		this.targetType = targetType;
	}

	public void setTargetType(Type targetType) {
		this.targetType = targetType.value();
	}

	public Long getTargetIssue() {
		return targetIssue;
	}

	public void setTargetIssue(Long targetIssue) {
		this.targetIssue = targetIssue;
	}

	public Date getTargetStartTimeStart() {
		return targetStartTimeStart;
	}

	public void setTargetStartTimeStart(Date targetStartTimeStart) {
		this.targetStartTimeStart = targetStartTimeStart;
	}

	public Date getTargetStartTimeEnd() {
		return targetStartTimeEnd;
	}

	public void setTargetStartTimeEnd(Date targetStartTimeEnd) {
		this.targetStartTimeEnd = targetStartTimeEnd;
	}

	public Date getTargetEndTimeStart() {
		return targetEndTimeStart;
	}

	public void setTargetEndTimeStart(Date targetEndTimeStart) {
		this.targetEndTimeStart = targetEndTimeStart;
	}

	public Date getTargetEndTimeEnd() {
		return targetEndTimeEnd;
	}

	public void setTargetEndTimeEnd(Date targetEndTimeEnd) {
		this.targetEndTimeEnd = targetEndTimeEnd;
	}

	public Long getTargetStatus() {
		return targetStatus;
	}

	public void setTargetStatus(Long targetStatus) {
		this.targetStatus = targetStatus;
	}
	
	public void setTargetStatus(Status targetStatus) {
		this.targetStatus = targetStatus.value();
	}
	
}
