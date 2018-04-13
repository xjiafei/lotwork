package com.winterframework.firefrog.help.web.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.winterframework.modules.validate.FFLength;

public class HelpStruc implements Serializable{

	private static final long serialVersionUID = -4893950552323954025L;

	private Long id;

	
	@FFLength(min = 1, max = 150)
	private String title;

	
	@Min(0)
	@Max(1)
	private Integer isRec;
	
	
	@FFLength(min = 0, max = 300)
	private String preface;

	private String content;

	@NotNull
	@Min(-1)
	private Long cateId;

	@NotNull
	@Min(-1)
	private Long cateId2;

	@NotNull
	@Min(0)
	private Long no;

	private Integer type;

	@FFLength(min = 0, max = 100)
	private String lotteryLogo;
	
	private String lotteryLink;

	@FFLength(min = 0, max = 40)
	private String lotteryAdvert;
	
	@Min(0)
	private Long browsenum;
	
	@Min(0)
	private Long solvednum;
	
	@Min(0)
	private Long unsolvednum;
	
	private List<LotteryContentStruc> lotteryContentStruc;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getIsRec() {
		return isRec;
	}

	public void setIsRec(Integer isRec) {
		this.isRec = isRec;
	}

	public String getPreface() {
		return preface;
	}

	public void setPreface(String preface) {
		this.preface = preface;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getCateId() {
		return cateId;
	}

	public void setCateId(Long cateId) {
		this.cateId = cateId;
	}

	public Long getCateId2() {
		return cateId2;
	}

	public void setCateId2(Long cateId2) {
		this.cateId2 = cateId2;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLotteryLogo() {
		return lotteryLogo;
	}

	public void setLotteryLogo(String lotteryLogo) {
		this.lotteryLogo = lotteryLogo;
	}

	public String getLotteryLink() {
		return lotteryLink;
	}

	public void setLotteryLink(String lotteryLink) {
		this.lotteryLink = lotteryLink;
	}

	public String getLotteryAdvert() {
		return lotteryAdvert;
	}

	public void setLotteryAdvert(String lotteryAdvert) {
		this.lotteryAdvert = lotteryAdvert;
	}

	public List<LotteryContentStruc> getLotteryContentStruc() {
		return lotteryContentStruc;
	}

	public void setLotteryContentStruc(List<LotteryContentStruc> lotteryContentStruc) {
		this.lotteryContentStruc = lotteryContentStruc;
	}

	public Long getBrowsenum() {
		return browsenum;
	}

	public void setBrowsenum(Long browsenum) {
		this.browsenum = browsenum;
	}

	public Long getSolvednum() {
		return solvednum;
	}

	public void setSolvednum(Long solvednum) {
		this.solvednum = solvednum;
	}

	public Long getUnsolvednum() {
		return unsolvednum;
	}

	public void setUnsolvednum(Long unsolvednum) {
		this.unsolvednum = unsolvednum;
	}

}
