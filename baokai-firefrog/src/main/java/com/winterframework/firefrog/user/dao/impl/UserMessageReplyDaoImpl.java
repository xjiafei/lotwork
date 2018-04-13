/**   
* @Title: UserMessageReplyDaoImpl.java 
* @Package com.winterframework.firefrog.user.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-5-6 下午5:27:57 
* @version V1.0   
*/
package com.winterframework.firefrog.user.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserMessageReplyDao;
import com.winterframework.firefrog.user.dao.vo.UserMessageReply;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.MessageReply;
import com.winterframework.firefrog.user.entity.MessageTopic;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: UserMessageReplyDaoImpl 
* @Description: 站内信消息回复相关的DAO层实现类
* @author Denny 
* @date 2013-5-6 下午5:27:57 
*  
*/
@Repository
public class UserMessageReplyDaoImpl extends BaseIbatis3Dao<UserMessageReply> implements IUserMessageReplyDao {

	@Override
	public List<MessageReply> getMessageReplyList(Long topicId, Long fromId,MessageTopic mt) {
		// 时间正序
		List<MessageReply> replys = new ArrayList<MessageReply>();
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("rootId", topicId);
		map.put("fromId", fromId);
		List<UserMessageReply> UserMessageReplyList = sqlSessionTemplate.selectList("getByRootId", map);

		if (UserMessageReplyList.size() > 0) {
			for (UserMessageReply umr : UserMessageReplyList) {
				MessageReply mr = VOConverter.userMessageReply2MessageReply(umr);
				mr.setRoot(mt);
				replys.add(mr);
			}
		}
		return replys;

	}

	@Override
	public long insertMessageReply(MessageReply messageReply) {
		UserMessageReply userMessageReply = VOConverter.messageReply2UserMessageReply(messageReply);
		this.insert(userMessageReply);
		return userMessageReply.getId();
	}

	@Override
	public long getIdByParentId(long parentId) {
		Long id = sqlSessionTemplate.selectOne("getIdByParentId", Long.valueOf(parentId));
		return id;
	}

	@Override
	public void deleteMessageReply(long id) {
		this.delete(id);
	}

}
