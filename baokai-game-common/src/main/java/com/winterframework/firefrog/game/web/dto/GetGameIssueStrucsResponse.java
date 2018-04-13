package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class GetGameIssueStrucsResponse implements Serializable {

	private static final long serialVersionUID = -4975373561714269943L;

	private List<IssueStruc> issueStrucs;

	public List<IssueStruc> getIssueStrucs() {
		return issueStrucs;
	}

	public void setIssueStrucs(List<IssueStruc> issueStrucs) {
		this.issueStrucs = issueStrucs;
	}

}
