/**   
* @Title: IUserMessageReplyDao.java 
* @Package com.winterframework.firefrog.user.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-5-6 下午5:25:36 
* @version V1.0   
*/
package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;

/** 
* @ClassName: IUserMessageReplyDao 
* @Description: 站内信消息回复相关的DAO层接口 
* @author Denny 
* @date 2013-5-6 下午5:25:36 
*  
*/

public interface IUserMessageReplyDao {

	/**
	 * 查询某一主题的所有回复消息
	 * 
	 * @param
	 * @return
	 */
	public List<MessageReply> getMessageReplyList(Long topicId, Long fromId,MessageTopic title);

	/**
	 * 新增一条回复消息
	 * 
	 * @param messageReply
	 * 
	 * @return
	 */
	public long insertMessageReply(MessageReply messageReply);

	/**
	 * 根据parentId取出回复消息的id(此方法是UT需要)
	 * 
	 * @param parentId
	 * 
	 * @returnR
	 */
	public long getIdByParentId(long parentId);

	/**
	 * 删除一条回复消息(此方法是UT需要)
	 * 
	 * @param id
	 * 
	 * @returnR
	 */
	public void deleteMessageReply(long id);
}
