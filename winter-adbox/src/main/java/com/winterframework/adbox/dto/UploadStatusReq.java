package com.winterframework.adbox.dto;

public class UploadStatusReq {

	private Long deviceId;
	private Long version;
	private Integer status;
	private Long[] resoreceIds;
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long[] getResoreceIds() {
		return resoreceIds;
	}
	public void setResoreceIds(Long[] resoreceIds) {
		this.resoreceIds = resoreceIds;
	}
}
