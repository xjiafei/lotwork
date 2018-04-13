package com.winterframework.adbox.dto;

public class DeviceInfoReq {

	private Integer onffline;
	private Integer battery;
	private Integer lockScreen;
	private Long deviceId;
	public Integer getOnffline() {
		return onffline;
	}
	public void setOnffline(Integer onffline) {
		this.onffline = onffline;
	}
	public Integer getBattery() {
		return battery;
	}
	public void setBattery(Integer battery) {
		this.battery = battery;
	}
	public Integer getLockScreen() {
		return lockScreen;
	}
	public void setLockScreen(Integer lockScreen) {
		this.lockScreen = lockScreen;
	}
	public Long getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(Long deviceId) {
		this.deviceId = deviceId;
	}
}
