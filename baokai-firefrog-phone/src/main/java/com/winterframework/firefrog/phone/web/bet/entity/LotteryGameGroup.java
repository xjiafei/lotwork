package com.winterframework.firefrog.phone.web.bet.entity;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "code" })
public class LotteryGameGroup {

	private Integer code;

	private String title;

	private String name;

	private List<LotteryGameSet> childs;
	
	private String gameType;
	
	private String gameTypeCn;
	
	private String gameTips;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LotteryGameSet> getChilds() {
		return childs;
	}

	public void setChilds(List<LotteryGameSet> childs) {
		this.childs = childs;
	}
	
	

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public String getGameTypeCn() {
		return gameTypeCn;
	}

	public void setGameTypeCn(String gameTypeCn) {
		this.gameTypeCn = gameTypeCn;
	}

	public String getGameTips() {
		return gameTips;
	}

	public void setGameTips(String gameTips) {
		this.gameTips = gameTips;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCode()).append(getTitle()).append(getName()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LotteryGameGroup == false)
			return false;
		if (this == obj)
			return true;
		LotteryGameGroup other = (LotteryGameGroup) obj;
		return new EqualsBuilder().append(getCode(), other.getCode()).append(getName(), other.getName())
				.append(getTitle(), other.getTitle()).isEquals();
	}

}
