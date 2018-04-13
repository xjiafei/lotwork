package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**
 * 
 *    
 * 类功能说明:   查询下级客户请求DTO
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard 
 *
 */
public class SubCustomerQueryRequestDTO implements Serializable {

	/**
	 * serialVersionUID：
	 */
	private static final long serialVersionUID = -4479484716267317501L;

	private long userId;

	public SubCustomerQueryRequestDTO() {

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

}
