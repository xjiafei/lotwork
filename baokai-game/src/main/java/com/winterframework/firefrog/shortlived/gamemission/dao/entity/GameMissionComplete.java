package com.winterframework.firefrog.shortlived.gamemission.dao.entity;

import com.winterframework.orm.dal.ibatis3.BaseEntity;
import java.util.Date;

public class GameMissionComplete extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long missionId;

	private Long userId;

	private String param;

	private Date createDate;

	private Long targetId;

	private Long targetMissionId;

	private Long targetUserId;

	private String targetParam;

	private Date targetCreateDateStart;

	private Date targetCreateDateEnd;
	
	private String targetMissionCode;
	
	private Long targetItemSeq;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setMissionId(Long missionId){
		 this.missionId= missionId;
	}

	public Long getMissionId(){
		return this.missionId;
	}

	public void setUserId(Long userId){
		 this.userId= userId;
	}

	public Long getUserId(){
		return this.userId;
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

	public void setTargetId(Long targetId){
		 this.targetId= targetId;
	}

	public Long getTargetId(){
		return this.targetId;
	}

	public void setTargetMissionId(Long targetMissionId){
		 this.targetMissionId= targetMissionId;
	}

	public Long getTargetMissionId(){
		return this.targetMissionId;
	}

	public void setTargetUserId(Long targetUserId){
		 this.targetUserId= targetUserId;
	}

	public Long getTargetUserId(){
		return this.targetUserId;
	}

	public void setTargetParam(String targetParam){
		 this.targetParam= targetParam;
	}

	public String getTargetParam(){
		return this.targetParam;
	}

	public void setTargetCreateDateStart(Date targetCreateDateStart){
		 this.targetCreateDateStart= targetCreateDateStart;
	}

	public Date getTargetCreateDateStart(){
		return this.targetCreateDateStart;
	}

	public void setTargetCreateDateEnd(Date targetCreateDateEnd){
		 this.targetCreateDateEnd= targetCreateDateEnd;
	}

	public Date getTargetCreateDateEnd(){
		return this.targetCreateDateEnd;
	}

	public String getTargetMissionCode() {
		return targetMissionCode;
	}

	public void setTargetMissionCode(String targetMissionCode) {
		this.targetMissionCode = targetMissionCode;
	}

	public Long getTargetItemSeq() {
		return targetItemSeq;
	}

	public void setTargetItemSeq(Long targetItemSeq) {
		this.targetItemSeq = targetItemSeq;
	}

}
