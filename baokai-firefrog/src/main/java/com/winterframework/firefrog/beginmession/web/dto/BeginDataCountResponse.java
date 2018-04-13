package com.winterframework.firefrog.beginmession.web.dto;

import java.io.Serializable;

import com.winterframework.firefrog.beginmession.dao.vo.BeginDataCountVO;

public class BeginDataCountResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2359071915243714492L;
	private BeginDataCountVO vo;

	public BeginDataCountVO getVo() {
		return vo;
	}

	public void setVo(BeginDataCountVO vo) {
		this.vo = vo;
	}
}
