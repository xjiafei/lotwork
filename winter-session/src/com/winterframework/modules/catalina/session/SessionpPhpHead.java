package com.winterframework.modules.catalina.session;

import java.util.HashMap;
import java.util.Map;


public class SessionpPhpHead implements java.io.Serializable{
	private Map<String,Object> datas=new HashMap<String,Object>();

	public Map<String, Object> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, Object> datas) {
		this.datas = datas;
	}
    
	
    
}
