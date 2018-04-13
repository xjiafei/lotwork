package com.winterframework.firefrog.user.web.dto;

public class PtBonusItem {
	private String lvl;
	private Long activeSubUserCount;
	private Long rmbMin;
	private Long rmbMax;
	private Long ret;

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}

	public Long getActiveSubUserCount() {
		return activeSubUserCount;
	}

	public void setActiveSubUserCount(Long activeSubUserCount) {
		this.activeSubUserCount = activeSubUserCount;
	}

	public Long getRmbMin() {
		return rmbMin;
	}

	public void setRmbMin(Long rmbMin) {
		this.rmbMin = rmbMin;
	}

	public Long getRmbMax() {
		return rmbMax;
	}

	public void setRmbMax(Long rmbMax) {
		this.rmbMax = rmbMax;
	}

	public Long getRet() {
		return ret;
	}

	public void setRet(Long ret) {
		this.ret = ret;
	}

}
