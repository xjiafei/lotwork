package com.winterframework.firefrog.game.entity;

import java.util.ArrayList;
import java.util.List;

public class PackDetailPoints {

	private Long pckDetailId;
	private List<Points> points=new ArrayList<Points>();
	public Long getPckDetailId() {
		return pckDetailId;
	}
	public void setPckDetailId(Long pckDetailId) {
		this.pckDetailId = pckDetailId;
	}
	public List<Points> getPoints() {
		return points;
	}
	public void setPoints(List<Points> points) {
		this.points = points;
	}
	

}
