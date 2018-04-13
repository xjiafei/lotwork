package com.winterframework.firefrog.channel.entity;

import java.util.Date;

public class Channel {
	
	private Long id;
	private String name;
	private String url;
	private Long vaild;
	private Date end_time;
	private Long frequency;
	private Long frequency_time;
	private Long freeze;
	private Long freeze_time;
	private Long reset;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getVaild() {
		return vaild;
	}
	public void setVaild(Long vaild) {
		this.vaild = vaild;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	public Long getFrequency() {
		return frequency;
	}
	public void setFrequency(Long frequency) {
		this.frequency = frequency;
	}
	public Long getFrequency_time() {
		return frequency_time;
	}
	public void setFrequency_time(Long frequency_time) {
		this.frequency_time = frequency_time;
	}
	public Long getFreeze() {
		return freeze;
	}
	public void setFreeze(Long freeze) {
		this.freeze = freeze;
	}
	public Long getFreeze_time() {
		return freeze_time;
	}
	public void setFreeze_time(Long freeze_time) {
		this.freeze_time = freeze_time;
	}
	public Long getReset() {
		return reset;
	}
	public void setReset(Long reset) {
		this.reset = reset;
	}
}
