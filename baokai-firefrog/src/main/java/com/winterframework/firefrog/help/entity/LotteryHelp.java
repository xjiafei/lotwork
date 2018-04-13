package com.winterframework.firefrog.help.entity;

import java.util.ArrayList;
import java.util.List;

public class LotteryHelp extends Help {
	
	private String lotteryLogo;
	private String lotteryLink;
	private String lotteryAdvert;
	private List<HelpKnowledgeLink> linkList = new ArrayList<HelpKnowledgeLink>();
	
	
	public String getLotteryLogo() {
		return lotteryLogo;
	}
	public void setLotteryLogo(String lotteryLogo) {
		this.lotteryLogo = lotteryLogo;
	}
	public String getLotteryLink() {
		return lotteryLink;
	}
	public void setLotteryLink(String lotteryLink) {
		this.lotteryLink = lotteryLink;
	}
	public String getLotteryAdvert() {
		return lotteryAdvert;
	}
	public void setLotteryAdvert(String lotteryAdvert) {
		this.lotteryAdvert = lotteryAdvert;
	}
	public List<HelpKnowledgeLink> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<HelpKnowledgeLink> linkList) {
		this.linkList = linkList;
	}

}
