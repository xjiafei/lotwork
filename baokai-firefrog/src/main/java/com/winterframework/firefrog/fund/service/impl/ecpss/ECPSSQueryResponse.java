package com.winterframework.firefrog.fund.service.impl.ecpss;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class ECPSSQueryResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1126498505871416089L;

	@XmlElement(name = "merCode")
	private String merCode;
	
	@XmlElement(name = "beginTime")
	private String beginTime;
	
	@XmlElement(name = "endTime")
	private String endTime;
	
	@XmlElement(name = "pageIndex")
	private String pageIndex;
	
	@XmlElement(name = "resultCount")
	private String resultCount;
	
	@XmlElement(name = "pageSize")
	private String pageSize;
	
	@XmlElement(name = "resultCode")
	private String resultCode;
	
	@XmlElement(name = "list")
	private List<ECPSSQueryResponseDetail> list;

	public String getMerCode() {
		return merCode;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public String getResultCount() {
		return resultCount;
	}

	public String getPageSize() {
		return pageSize;
	}

	public String getResultCode() {
		return resultCode;
	}

	public List<ECPSSQueryResponseDetail> getList() {
		return list;
	}
	
}
