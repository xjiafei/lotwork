package com.winterframework.firefrog.game.web.bet.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "code" })
public class LotteryBetMethod {

	private Integer code;

	private String title;

	private String name;

	private String parent;

	private String mode;

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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getCode()).append(getTitle()).append(getName()).append(getParent())
				.append(getMode()).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof LotteryBetMethod == false)
			return false;
		if (this == obj)
			return true;
		LotteryBetMethod other = (LotteryBetMethod) obj;
		return new EqualsBuilder().append(getCode(), other.getCode()).append(getName(), other.getName())
				.append(getTitle(), other.getTitle()).append(getParent(), other.getParent())
				.append(getMode(), other.getMode()).isEquals();
	}

}
