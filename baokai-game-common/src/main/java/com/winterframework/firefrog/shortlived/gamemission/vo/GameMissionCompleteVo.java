package com.winterframework.firefrog.shortlived.gamemission.vo;

import java.io.Serializable;
import java.util.Date;

public class GameMissionCompleteVo implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;

	private Long missionId;

	private Long userId;

	private String param;

	private Date createDate;

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

}
