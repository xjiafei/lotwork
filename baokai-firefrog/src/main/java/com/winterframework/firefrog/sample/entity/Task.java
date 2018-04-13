package com.winterframework.firefrog.sample.entity;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

@XmlRootElement
public class Task extends BaseEntity {
	@NotNull
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
