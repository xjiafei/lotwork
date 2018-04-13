package com.winterframework.firefrog.user.entity;

import com.winterframework.firefrog.user.vo.ImGroupMessageVo;
import com.winterframework.firefrog.user.vo.ImGroupUserVo;
import com.winterframework.firefrog.user.vo.ImGroupVo;

public class ImEntitiyVoConvertor {
	
	public static ImGroupVo convertImGroupToVo(ImGroup entity) {

		ImGroupVo vo = new ImGroupVo();
		vo.setId(entity.getId());
		vo.setGroupKey(entity.getGroupKey());
		vo.setCreateDate(entity.getCreateDate());
		return vo;
	}

	public static ImGroup convertImGroupToEntity(ImGroupVo vo) {

		ImGroup entity = new ImGroup();
		entity.setId(vo.getId());
		entity.setGroupKey(vo.getGroupKey());
		entity.setCreateDate(vo.getCreateDate());
		return entity;
	}

	public static ImGroupMessageVo convertImGroupMessageToVo(ImGroupMessage entity) {

		ImGroupMessageVo vo = new ImGroupMessageVo();
		vo.setId(entity.getId());
		vo.setGroupId(entity.getGroupId());
		vo.setUserId(entity.getUserId());
		vo.setAccount(entity.getAccount());
		vo.setContent(entity.getContent());
		vo.setStompContent(entity.getStompContent());
		vo.setCreateDate(entity.getCreateDate());
		return vo;
	}

	public static ImGroupMessage convertImGroupMessageToEntity(ImGroupMessageVo vo) {

		ImGroupMessage entity = new ImGroupMessage();
		entity.setId(vo.getId());
		entity.setGroupId(vo.getGroupId());
		entity.setUserId(vo.getUserId());
		entity.setAccount(vo.getAccount());
		entity.setContent(vo.getContent());
		entity.setStompContent(vo.getStompContent());
		entity.setCreateDate(vo.getCreateDate());
		return entity;
	}

	public static ImGroupUserVo convertImGroupUserToVo(ImGroupUser entity) {

		ImGroupUserVo vo = new ImGroupUserVo();
		vo.setId(entity.getId());
		vo.setGroupId(entity.getGroupId());
		vo.setUserId(entity.getUserId());
		vo.setUnreadCount(entity.getUnreadCount());
		vo.setHistoryStartTime(entity.getHistoryStartTime());
		vo.setCreateDate(entity.getCreateDate());
		vo.setLastUpdateDate(entity.getLastUpdateDate());
		vo.setIsActive(entity.getIsActive());
		return vo;
	}

	public static ImGroupUser convertImGroupUserToEntity(ImGroupUserVo vo) {

		ImGroupUser entity = new ImGroupUser();
		entity.setId(vo.getId());
		entity.setGroupId(vo.getGroupId());
		entity.setUserId(vo.getUserId());
		entity.setUnreadCount(vo.getUnreadCount());
		entity.setHistoryStartTime(vo.getHistoryStartTime());
		entity.setCreateDate(vo.getCreateDate());
		entity.setLastUpdateDate(vo.getLastUpdateDate());
		entity.setIsActive(vo.getIsActive());
		return entity;
	}

}
