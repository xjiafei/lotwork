package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 冻结日志信息列表
 * 
 * @author david
 * 
 */
public class QueryFreezeUserResponse implements Serializable {

	private static final long serialVersionUID = 4435089716310294683L;

	private List<UserStrucResponse> agents;

	public List<UserStrucResponse> getAgents() {
		return agents;
	}

	public void setAgents(List<UserStrucResponse> agents) {
		this.agents = agents;
	}

}
