package com.winterframework.firefrog.common.wincaculate;

public class WinResultSingleBean implements IWinResultBean {

	private Integer singleWin;

	public WinResultSingleBean(Integer singleWin) {
		this.setSingleWin(singleWin);
	}

	public Integer getSingleWin() {
		return singleWin;
	}

	public void setSingleWin(Integer singleWin) {
		this.singleWin = singleWin;
	}

	@Override
	public boolean getIsWin() {
		if (this.singleWin != null && this.singleWin.intValue() > 0) {
			return true;
		}
		return false;
	}
}
