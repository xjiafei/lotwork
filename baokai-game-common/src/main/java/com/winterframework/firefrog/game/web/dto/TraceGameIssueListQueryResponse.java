package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;
import java.util.List;

public class TraceGameIssueListQueryResponse implements Serializable {

	private static final long serialVersionUID = 109920214872655867L;

	private List<PreviewIssueStruc> IssueStrucs;

	public List<PreviewIssueStruc> getIssueStrucs() {
		return IssueStrucs;
	}

	public void setIssueStrucs(List<PreviewIssueStruc> issueStrucs) {
		IssueStrucs = issueStrucs;
	}

}
