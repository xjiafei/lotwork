package com.winterframework.sup.order.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SupportQuestionTypeVo extends BaseSupVo{
	
	private static final long serialVersionUID = -2960381142376415476L;

	private Long id;

	private Long pid;

	private String typename;

	private String template;

	private Long showRegist;

	private Long creator;

	private Date createDate;

	private Date lastUpdateDate;

	private Long isActive;

	private Long orderby;

	private Long platformId;
	
	private List<SupportQuestionTypeVo> childs;
	
	private SupportQuestionTypeVo parent;

	public void setId(Long id){
		 this.id= id;
	}

	public Long getId(){
		return this.id;
	}

	public void setPid(Long pid){
		 this.pid= pid;
	}

	public Long getPid(){
		return this.pid;
	}

	public void setTypename(String typename){
		 this.typename= typename;
	}

	public String getTypename(){
		return this.typename;
	}

	public void setTemplate(String template){
		 this.template= template;
	}

	public String getTemplate(){
		return this.template;
	}

	public void setShowRegist(Long showRegist){
		 this.showRegist= showRegist;
	}

	public Long getShowRegist(){
		return this.showRegist;
	}

	public void setCreator(Long creator){
		 this.creator= creator;
	}

	public Long getCreator(){
		return this.creator;
	}

	public void setCreateDate(Date createDate){
		 this.createDate= createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate){
		 this.lastUpdateDate= lastUpdateDate;
	}

	public Date getLastUpdateDate(){
		return this.lastUpdateDate;
	}

	public void setIsActive(Long isActive){
		 this.isActive= isActive;
	}

	public Long getIsActive(){
		return this.isActive;
	}

	public void setOrderby(Long orderby){
		 this.orderby= orderby;
	}

	public Long getOrderby(){
		return this.orderby;
	}

	public void setPlatformId(Long platformId){
		 this.platformId= platformId;
	}

	public Long getPlatformId(){
		return this.platformId;
	}

	public List<SupportQuestionTypeVo> getChilds() {
		return childs;
	}

	public void setChilds(List<SupportQuestionTypeVo> childs) {
		this.childs = childs;
	}

	public SupportQuestionTypeVo getParent() {
		return parent;
	}

	public void setParent(SupportQuestionTypeVo parent) {
		this.parent = parent;
	}

}
