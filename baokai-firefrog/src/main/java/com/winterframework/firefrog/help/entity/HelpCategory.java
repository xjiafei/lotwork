package com.winterframework.firefrog.help.entity;

import java.util.List;

public class HelpCategory {

	private Long id;

	private String cname;

	private Long clevel;

	private Long cno;

	private HelpCategory parent;

	private List<HelpCategory> subCategorys;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public Long getClevel() {
		return clevel;
	}

	public void setClevel(Long clevel) {
		this.clevel = clevel;
	}

	public Long getCno() {
		return cno;
	}

	public void setCno(Long cno) {
		this.cno = cno;
	}
	
	public HelpCategory getParent() {
		return parent;
	}

	public void setParent(HelpCategory parent) {
		this.parent = parent;
	}

	public List<HelpCategory> getSubCategorys() {
		return subCategorys;
	}

	public void setSubCategorys(List<HelpCategory> subCategorys) {
		this.subCategorys = subCategorys;
	}
}
