package com.winterframework.firefrog.fund.dao.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.advert.web.dto.BaseEntity;

public class BypassTable extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8270396254368209305L;
	
	private static  Map<Long,List<BypassConfigVO>> bypassMap;
	
	private static Map<Long,List<BypassConfigVO>> nextBypassMap;

	public BypassTable() {
		bypassMap=new HashMap<Long, List<BypassConfigVO>>();
		nextBypassMap=new HashMap<Long, List<BypassConfigVO>>();
	}
	public Map<Long, List<BypassConfigVO>> getBypassMap() {
		return bypassMap;
	}

	public void setBypassMap(Map<Long, List<BypassConfigVO>> bypassMap) {
		this.bypassMap = bypassMap;
	}

	public Map<Long, List<BypassConfigVO>> getNextBypassMap() {
		return nextBypassMap;
	}
	public void setNextBypassMap(Map<Long, List<BypassConfigVO>> nextBypassMap) {
		this.nextBypassMap = nextBypassMap;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	

}
