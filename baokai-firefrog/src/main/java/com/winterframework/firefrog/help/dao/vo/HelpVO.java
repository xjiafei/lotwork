 /**
 * Copyright (c) 2005-2012 winterframework.com
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
 package com.winterframework.firefrog.help.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */


public class HelpVO extends BaseEntity {
	
	private static final long serialVersionUID = 2415337147729242034L;
	//alias
	public static final String TABLE_ALIAS = "Help";
	public static final String ALIAS_TITLE = "帮助标题";
	public static final String ALIAS_IS_REC = "是否推荐";
	public static final String ALIAS_PREFACE = "简介";
	public static final String ALIAS_CONTENT = "内容";
	public static final String ALIAS_NO = "序号";
	public static final String ALIAS_TYPE = "帮助类型(1)彩种知识、0)普通帮助)";
	public static final String ALIAS_LOTTERY_LOGO = "彩种知识Logo地址";
	public static final String ALIAS_LOTTERY_LINK = "彩种知识链接地址";
	public static final String ALIAS_LOTTERY_ADVERT = "彩种广告词";
	public static final String ALIAS_SOLVEDNUM = "已解决的数量";
	public static final String ALIAS_UNSOLVEDNUM = "未解决的数量";
	public static final String ALIAS_BROWSENUM = "浏览人数";
	public static final String ALIAS_CATE_ID = "一级分类";
	public static final String ALIAS_CATE_ID2 = "二级分类";
	public static final String ALIAS_REC_NO = "推荐序号";
	public static final String ALIAS_INFO = "info";
	
	//date formats
	public static final String FORMAT_GMT_CREATE = DATE_TIME_FORMAT;
	public static final String FORMAT_MODIFYTIME = DATE_TIME_FORMAT;
	
	//columns START
	private String title;
	private Long isRec;
	private String preface;
	private String content;
	private Long no;
	private Long type;
	private String lotteryLogo;
	private String lotteryLink;
	private String lotteryAdvert;
	private Long solvednum;
	private Long unsolvednum;
	private Long browsenum;
	private Long cateId;
	private Long cateId2;
	private Long recNo;
	
	/** 
	* 推荐时间 
	*/ 
	private Date recommandTime;
	//columns END

	public HelpVO(){
	}

	public HelpVO(
		Long id
	){
		this.id = id;
	}

	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setIsRec(Long value) {
		this.isRec = value;
	}
	
	public Long getIsRec() {
		return this.isRec;
	}
	public void setPreface(String value) {
		this.preface = value;
	}
	
	public String getPreface() {
		return this.preface;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public void setNo(Long value) {
		this.no = value;
	}
	
	public Long getNo() {
		return this.no;
	}
	public void setType(Long value) {
		this.type = value;
	}
	
	public Long getType() {
		return this.type;
	}
	public void setLotteryLogo(String value) {
		this.lotteryLogo = value;
	}
	
	public String getLotteryLogo() {
		return this.lotteryLogo;
	}
	public void setLotteryLink(String value) {
		this.lotteryLink = value;
	}
	
	public String getLotteryLink() {
		return this.lotteryLink;
	}
	public void setLotteryAdvert(String value) {
		this.lotteryAdvert = value;
	}
	
	public String getLotteryAdvert() {
		return this.lotteryAdvert;
	}
	public void setSolvednum(Long value) {
		this.solvednum = value;
	}
	
	public Long getSolvednum() {
		return this.solvednum;
	}
	public void setUnsolvednum(Long value) {
		this.unsolvednum = value;
	}
	
	public Long getUnsolvednum() {
		return this.unsolvednum;
	}
	public void setBrowsenum(Long value) {
		this.browsenum = value;
	}
	
	public Long getBrowsenum() {
		return this.browsenum;
	}
	public void setCateId(Long value) {
		this.cateId = value;
	}
	
	public Long getCateId() {
		return this.cateId;
	}
	public void setCateId2(Long value) {
		this.cateId2 = value;
	}
	
	public Long getCateId2() {
		return this.cateId2;
	}
	public void setRecNo(Long value) {
		this.recNo = value;
	}
	
	public Long getRecNo() {
		return this.recNo;
	}
    @Override
	public String toString() {
		return new ToStringBuilder(this)
		.append("Id",getId())		
		.append("Title",getTitle())		
		.append("IsRec",getIsRec())		
		.append("Preface",getPreface())		
		.append("Content",getContent())		
		.append("No",getNo())		
		.append("Type",getType())		
		.append("LotteryLogo",getLotteryLogo())		
		.append("LotteryLink",getLotteryLink())		
		.append("LotteryAdvert",getLotteryAdvert())		
		.append("Solvednum",getSolvednum())		
		.append("Unsolvednum",getUnsolvednum())		
		.append("Browsenum",getBrowsenum())		
		.append("CateId",getCateId())		
		.append("CateId2",getCateId2())		
		.append("RecNo",getRecNo())		
			.toString();
	}
    @Override
	public int hashCode() {
		return new HashCodeBuilder()
		.append(getId())
		.append(getTitle())
		.append(getIsRec())
		.append(getPreface())
		.append(getContent())
		.append(getNo())
		.append(getType())
		.append(getLotteryLogo())
		.append(getLotteryLink())
		.append(getLotteryAdvert())
		.append(getSolvednum())
		.append(getUnsolvednum())
		.append(getBrowsenum())
		.append(getCateId())
		.append(getCateId2())
		.append(getRecNo())
		.append(getRecommandTime())
			.toHashCode();
	}
    @Override
	public boolean equals(Object obj) {
		if(obj instanceof HelpVO == false) return false;
		if(this == obj) return true;
		HelpVO other = (HelpVO)obj;
		return new EqualsBuilder()
		.append(getId(),other.getId())

		.append(getTitle(),other.getTitle())

		.append(getIsRec(),other.getIsRec())

		.append(getPreface(),other.getPreface())

		.append(getContent(),other.getContent())

		.append(getNo(),other.getNo())

		.append(getType(),other.getType())

		.append(getLotteryLogo(),other.getLotteryLogo())

		.append(getLotteryLink(),other.getLotteryLink())

		.append(getLotteryAdvert(),other.getLotteryAdvert())

		.append(getSolvednum(),other.getSolvednum())

		.append(getUnsolvednum(),other.getUnsolvednum())

		.append(getBrowsenum(),other.getBrowsenum())

		.append(getCateId(),other.getCateId())

		.append(getCateId2(),other.getCateId2())

		.append(getRecNo(),other.getRecNo())
		.append(getRecommandTime(),other.getRecommandTime())

			.isEquals();
	}

	public Date getRecommandTime() {
		return recommandTime;
	}

	public void setRecommandTime(Date recommandTime) {
		this.recommandTime = recommandTime;
	}
}

