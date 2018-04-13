package com.winterframework.firefrog.channel.dao.vo;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

public class ChannelVO extends BaseEntity{
	
	//alias
	public static final String TABLE_ALIAS = "channel";
	public static final String ALIAS_ID = "編號";
	public static final String ALIAS_NAME = "渠道參數";
	public static final String ALIAS_URL = "使用連結";
	public static final String ALIAS_VAILD = "生效次數";
	public static final String ALIAS_END_TIME = "到期時間";
	public static final String ALIAS_FREQUENCY = "預警頻率";
	public static final String ALIAS_FREQUENCY_TIME = "預警頻率時間";
	public static final String ALIAS_FREEZE = "永久凍結";
	public static final String ALIAS_FREEZE_TIME = "凍結時間";
	public static final String ALIAS_RESET = "每日重置";
		
	//columns START
	private Long id;
	private String name;
	private String url;
	private Long vaild;
	private Date end_time;
	private Long frequency;
	private Long frequency_time;
	private Long freeze;
	private Long freeze_time;
	private Long ip;
	private Long ip_time;
	private Long reset;
	private String uuid;
	
	
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
	public Long getIp() {
		return ip;
	}
	public void setIp(Long ip) {
		this.ip = ip;
	}
	public Long getIp_time() {
		return ip_time;
	}
	public void setIp_time(Long ip_time) {
		this.ip_time = ip_time;
	}
	public Long getReset() {
		return reset;
	}
	public void setReset(Long reset) {
		this.reset = reset;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("Id", getId()).append("Name", getName()).append("Url", getUrl())
				.append("Vaild", getVaild()).append("End_time", getEnd_time()).append("Frequency", getFrequency())
				.append("Frequency_time", getFrequency_time()).append("Freeze", getFreeze()).append("Freeze_time", getFreeze_time())
				.append("Reset", getReset()).append("UUID",getUuid()).append("IP",getIp()).append("IP_TIME",getIp_time()).toString();
	}
	
	
}
