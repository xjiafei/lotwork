package com.winterframework.firefrog.user.entity;

import java.util.List;

public class UserAgent extends User {

	private static final long serialVersionUID = 7567735206227777448L;

	private Integer teamUserCount;

	private Integer teamAgentCount;

	private Integer agentLimit;

	private List<User> children;

	public Integer getTeamUserCount() {
		return teamUserCount;
	}

	public void setTeamUserCount(Integer teamUserCount) {
		this.teamUserCount = teamUserCount;
	}

	public Integer getTeamAgentCount() {
		return teamAgentCount;
	}

	public void setTeamAgentCount(Integer teamAgentCount) {
		this.teamAgentCount = teamAgentCount;
	}

	public Integer getAgentLimit() {
		return agentLimit;
	}

	public void setAgentLimit(Integer agentLimit) {
		this.agentLimit = agentLimit;
	}

	public List<User> getChildren() {
		return children;
	}

	public void setChildren(List<User> children) {
		this.children = children;
	}

}
