package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LockPoint {

	private Long beishu;
	private String betTotal;
	//实际可头倍数
	private Long realBeishu;
	private String betDetail;
	//是否有封锁
	private Boolean isLocks;
	//是否有变价
	private Boolean isChange;
	//<收限制号码，实际可投倍数>
	private Map<String,Long> locks=new HashMap<String,Long>();
	//有变价的号码
	private List<Points> points=new ArrayList<Points>();
	public Long getBeishu() {
		return beishu;
	}
	public void setBeishu(Long beishu) {
		this.beishu = beishu;
	}
	public Boolean getIsLocks() {
		if(locks==null){
			return true;
		}
		return locks.size()>0;
	}
	public Boolean getIsChange() {
		return this.points.size()>0;
	}
	
	public Map<String, Long> getLocks() {
		return locks;
	}
	public void setLocks(Map<String, Long> locks) {
		this.locks = locks;
	}
	public List<Points> getPoints() {
		return points;
	}
	public void setPoints(List<Points> points) {
		this.points = points;
	}
	public Long getRealBeishu() {
		return realBeishu;
	}
	public void setRealBeishu(Long realBeishu) {
		this.realBeishu = realBeishu;
	}
	
	public String getBetTotal() {
		return betTotal;
	}
	public void setBetTotal(String betTotal) {
		this.betTotal = betTotal;
	}
	public String getBetDetail() {
		return betDetail;
	}
	public void setBetDetail(String betDetail) {
		this.betDetail = betDetail;
	}
	public static LockPoint getEmptyLockPoint(Long multy){
		LockPoint lp=new LockPoint();
		lp.setBeishu(multy);
		lp.setLocks(new HashMap<String, Long>());
		lp.setRealBeishu(0L);
		return lp;
	}
}
