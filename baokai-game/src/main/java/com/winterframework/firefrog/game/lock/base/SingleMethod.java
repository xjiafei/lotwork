package com.winterframework.firefrog.game.lock.base;

import java.util.HashMap;
import java.util.Map;

public class SingleMethod extends Method{

	public boolean isqOrh() {
		return qOrh;
	}
	public void setqOrh(boolean qOrh) {
		this.qOrh = qOrh;
	}
	protected boolean qOrh;
	public Map<String, Integer> influncePoint() {	
		String[] ab=betContent.split(" ");
		Map<String,Integer> list=new HashMap<String,Integer>();
		for(String a:ab){
			LockTools.addPkg(list,a);			
		}
		 return list;
	}
	public String getBetContent() {
		return betContent;
	}
	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}
	
	
}
