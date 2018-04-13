package com.winterframework.firefrog.game.lock.base;

import java.util.ArrayList;
import java.util.List;

public class PointPkg {

	private String point;
	private Integer time;
	private List<String> sku=new ArrayList<String>();
	public PointPkg(String point,String sku){
		this.point=point;
		this.sku.add(sku);
		this.time=1;
	}
	
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public List<String> getSku() {
		return sku;
	}
	public void setSku(List<String> sku) {
		this.sku = sku;
	}

	@Override
	public String toString() {
		return "PointPkg [point=" + point + ", time=" + time + ", sku=" + sku + "]";
	}
	

}
