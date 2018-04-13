package com.winterframework.firefrog.fund.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.common.convert.BeanConverter.Alias;

public class FundBankUpdateRequest implements Serializable {

	private static final long serialVersionUID = 4654650446843896907L;
	
	private Long id;
	
	private String name;
	
	private String logo;
	
	private String code;
	
	private String url;
	
	@Alias(field = "mcId")
	private Long mownecumId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getMownecumId() {
		return mownecumId;
	}

	public void setMownecumId(Long mownecumId) {
		this.mownecumId = mownecumId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
