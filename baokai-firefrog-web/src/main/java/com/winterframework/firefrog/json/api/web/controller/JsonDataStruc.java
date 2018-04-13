package com.winterframework.firefrog.json.api.web.controller;

public class JsonDataStruc<T> {
	private String name;
	private T[] tplData;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public T[] getTplData() {
		return tplData;
	}
	public void setTplData(T[] tplData) {
		this.tplData = tplData;
	}
	

}
