/**      
 *    
 * Copyright (c) 2013 by Denny.    
 *
 *      
 * @version 1.0    
 */
package com.winterframework.firefrog.user.web.dto;

import java.io.Serializable;

/**  
 * 
 * @Description: 站内信标记已读与删除操作时请求参数的DTO  
 * 
 * @author Denny
 * @date 2013-4-28 下午5:36:44 
 * @version 1.0
 *  
 */
public class MessageMarkAndDeleteRequestDTO implements Serializable {

	private static final long serialVersionUID = 198752542338507537L;

	private long id;
	private int flag;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
