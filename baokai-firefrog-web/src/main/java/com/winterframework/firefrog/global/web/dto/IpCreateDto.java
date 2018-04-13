package com.winterframework.firefrog.global.web.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class IpCreateDto {

	@NotNull
	@Pattern(regexp = "((([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))|^\\*$)")
	private String ip1;

	@NotNull
	@Pattern(regexp = "((([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))|^\\*$)")
	private String ip2;

	@NotNull
	@Pattern(regexp = "((([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))|^\\*$)")
	private String ip3;

	@NotNull
	@Pattern(regexp = "((([0-9])|([1-9][0-9])|(1[0-9][0-9])|(2[0-4][0-9])|(25[0-5]))|^\\*$)")
	private String ip4;

	@NotNull
	@Min(1)
	private Long period;

	@NotNull
	@Min(0)
	@Max(1)
	private Long type;

	public String getIp1() {
		return ip1;
	}

	public void setIp1(String ip1) {
		this.ip1 = ip1;
	}

	public String getIp2() {
		return ip2;
	}

	public void setIp2(String ip2) {
		this.ip2 = ip2;
	}

	public String getIp3() {
		return ip3;
	}

	public void setIp3(String ip3) {
		this.ip3 = ip3;
	}

	public String getIp4() {
		return ip4;
	}

	public void setIp4(String ip4) {
		this.ip4 = ip4;
	}

	public Long getPeriod() {
		return period;
	}

	public void setPeriod(Long period) {
		this.period = period;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

}
