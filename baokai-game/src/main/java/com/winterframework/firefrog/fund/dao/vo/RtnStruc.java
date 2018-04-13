package com.winterframework.firefrog.fund.dao.vo;

import java.math.BigDecimal;

import com.winterframework.modules.web.util.JsonMapper;

public class RtnStruc {
	
	private Long big;
	private Long type;
	private BigDecimal value;
	private Long sm;

	public Long getBig() {
		return big;
	}

	public void setBig(Long big) {
		this.big = big;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Long getSm() {
		return sm;
	}

	public void setSm(Long sm) {
		this.sm = sm;
	}
	@Override
	public String toString(){
		return JsonMapper.nonEmptyMapper().toString();
	}
}
