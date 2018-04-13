package com.winterframework.firefrog.common.wincaculate;

import java.util.Map;

public class WinResultMultBean implements IWinResultBean {

	private Map<String, Integer> multWin;

	public WinResultMultBean(Map<String, Integer> multWin) {
		this.setMultWin(multWin);
	}

	public Map<String, Integer> getMultWin() {
		return multWin;
	}

	public void setMultWin(Map<String, Integer> multWin) {
		this.multWin = multWin;
	}

	@Override
	public boolean getIsWin() {
		if (this.multWin != null && this.multWin.size() > 0) {
			for (Integer temp : multWin.values()) {
				if (temp != null && temp.intValue() > 0) {
					return true;
				}
			}
			return false;
		}
		return false;
	}
}
