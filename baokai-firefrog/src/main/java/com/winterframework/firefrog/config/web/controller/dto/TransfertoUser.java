package com.winterframework.firefrog.config.web.controller.dto;

public class TransfertoUser {
	
	private Long up_time;
	private Long up_userlimit;
	private Long up_viplimit;
	private Long down_time;
	private Long down_viplimit;
	private Long down_userlimit;
	private Integer[] down_lvls;
	
	public Integer[] getDown_lvls() {
		return down_lvls;
	}
	public void setDown_lvls(Integer[] down_lvls) {
		this.down_lvls = down_lvls;
	}
	public Long getUp_time() {
		return up_time;
	}
	public void setUp_time(Long up_time) {
		this.up_time = up_time;
	}
	public Long getUp_userlimit() {
		return up_userlimit;
	}
	public void setUp_userlimit(Long up_userlimit) {
		this.up_userlimit = up_userlimit;
	}
	public Long getUp_viplimit() {
		return up_viplimit;
	}
	public void setUp_viplimit(Long up_viplimit) {
		this.up_viplimit = up_viplimit;
	}
	public Long getDown_time() {
		return down_time;
	}
	public void setDown_time(Long down_time) {
		this.down_time = down_time;
	}
	public Long getDown_viplimit() {
		return down_viplimit;
	}
	public void setDown_viplimit(Long down_viplimit) {
		this.down_viplimit = down_viplimit;
	}
	public Long getDown_userlimit() {
		return down_userlimit;
	}
	public void setDown_userlimit(Long down_userlimit) {
		this.down_userlimit = down_userlimit;
	}
	
}
