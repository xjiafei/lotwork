package com.winterframework.firefrog.phone.web.cotroller.dto;

import java.io.Serializable;

/**
 * @ClassName: IndexStruc
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 你的名字
 * @date 2013-12-23 上午10:20:44
 * 
 */
public class JanRedBowActivity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2528918094149433836L;

	private Long userId;

	private Long device;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getDevice() {
		return device;
	}

	public void setDevice(Long device) {
		this.device = device;
	}

}
