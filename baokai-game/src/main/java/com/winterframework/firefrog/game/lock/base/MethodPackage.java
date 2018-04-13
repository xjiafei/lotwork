package com.winterframework.firefrog.game.lock.base;

import java.util.HashMap;
import java.util.Map;

public class MethodPackage {
	
	public MethodPackage(String methodId, Map<String,Integer> betContent) {
		super();
		this.methodId = methodId;
		this.betContent = betContent;
	}
	public MethodPackage(String methodId) {
		super();
		this.methodId = methodId;
	}
	private String methodId;
	private Map<String,Integer> betContent=new HashMap<String,Integer>();
	public String getMethodId() {
		return methodId;
	}
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	public Map<String, Integer> getBetContent() {
		return betContent;
	}
	public void setBetContent(Map<String, Integer> betContent) {
		this.betContent = betContent;
	}
	
		

}
