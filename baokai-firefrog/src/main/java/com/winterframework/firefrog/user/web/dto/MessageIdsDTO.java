package com.winterframework.firefrog.user.web.dto;

/**  
 * @Description: 站内信删除和标记接口的请求数据DTO
 * @author Denny 
 * @date 2013-4-24 下午9:46:42 
 *  
 */
public class MessageIdsDTO {

	private MessageMarkAndDeleteRequestDTO[] msgIds;

	public MessageMarkAndDeleteRequestDTO[] getMsgIds() {
		return msgIds;
	}

	public void setMsgIds(MessageMarkAndDeleteRequestDTO[] msgIds) {
		this.msgIds = msgIds;
	}

}
