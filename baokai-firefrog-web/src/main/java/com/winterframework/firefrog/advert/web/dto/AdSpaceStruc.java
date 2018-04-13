package com.winterframework.firefrog.advert.web.dto;

/** 
* @ClassName: AdSpaceStruc 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-5 下午3:48:47 
*  
*/
public class AdSpaceStruc {
	private Long id;
	private Long width;
	private Long height;
	private Long paramId;
	private String name;
	private Long urlTarget;
	private String dftImg;
	private Long isUsed;
	private String dftImgs;
	private Long isDftUsed;
	private Integer allProcess;
	private Integer inProcess;
	private Integer noProcess;
	private Long pageId;
	private String paramName;
	public String getPageIdStr() {
		String pageIdStr="";
		if (pageId == null) {
			return "";
		}
		switch (Integer.valueOf(pageId.toString())) {
		case 1:
			pageIdStr = "平台首页";
			break;
		case 2:
			pageIdStr = "平台顶部";
			break;
		case 3:
			pageIdStr = "平台底部";
			break;
		case 4:
			pageIdStr = "注册页";
			break;
		case 5:
			pageIdStr = "登录页";
			break;
		case 6:
			pageIdStr = "重庆时时彩";
			break;
		case 7:
			pageIdStr = "江西时时彩 ";
			break;
		case 8:
			pageIdStr = "新疆时时彩";
			break;
		case 9:
			pageIdStr = "天津时时彩";
			break;
		case 10:
			pageIdStr = "黑龙江时时彩";
			break;
		case 11:
			pageIdStr = "上海时时彩";
			break;
		case 12:
			pageIdStr = "乐利时时彩  ";
			break;
		case 13:
			pageIdStr = "山东11选5 ";
			break;
		case 14:
			pageIdStr = "江西11选5";
			break;
		case 15:
			pageIdStr = "广东11选5";
			break;
		case 16:
			pageIdStr = "重庆11选5 ";
			break;
		case 17:
			pageIdStr = "乐利11选5  ";
			break;
		case 18:
			pageIdStr = "北京快乐8   ";
			break;
		case 19:
			pageIdStr = "3D";
			break;
		case 20:
			pageIdStr = "排列5";
			break;
		case 21:
			pageIdStr = "双球";
			break;
		case 23:
			pageIdStr = "PT";
			break;
		case 24:
			pageIdStr = "安徽快三";
			break;
		case 25:
			pageIdStr = "六合彩";
			break;
		case 26:
			pageIdStr = "VIP页";
			break;
		default:
			break;
		}
		return pageIdStr;
	}

	public AdSpaceStruc() {
	}

	public AdSpaceStruc(Long adSpaceId) {
		this.id=adSpaceId;
	}

	public void setWidth(Long value) {
		this.width = value;
	}

	public Long getWidth() {
		return this.width;
	}

	public void setHeight(Long value) {
		this.height = value;
	}

	public Long getHeight() {
		return this.height;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getName() {
		return this.name;
	}

	public void setUrlTarget(Long value) {
		this.urlTarget = value;
	}

	public Long getUrlTarget() {
		return this.urlTarget;
	}

	public void setDftImg(String value) {
		this.dftImg = value;
	}

	public String getDftImg() {
		return this.dftImg;
	}

	public void setIsUsed(Long value) {
		this.isUsed = value;
	}

	public Long getIsUsed() {
		return this.isUsed;
	}

	public void setDftImgs(String value) {
		this.dftImgs = value;
	}

	public String getDftImgs() {
		return this.dftImgs;
	}

	public void setIsDftUsed(Long value) {
		this.isDftUsed = value;
	}

	public Long getIsDftUsed() {
		return this.isDftUsed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAllProcess() {
		return allProcess;
	}

	public void setAllProcess(Integer allProcess) {
		this.allProcess = allProcess;
	}

	public Integer getInProcess() {
		return inProcess;
	}

	public void setInProcess(Integer inProcess) {
		this.inProcess = inProcess;
	}

	public Integer getNoProcess() {
		return noProcess;
	}

	public void setNoProcess(Integer noProcess) {
		this.noProcess = noProcess;
	}

	public Long getParamId() {
		return paramId;
	}

	public void setParamId(Long paramId) {
		this.paramId = paramId;
	}

	public Long getPageId() {
		return pageId;
	}

	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
