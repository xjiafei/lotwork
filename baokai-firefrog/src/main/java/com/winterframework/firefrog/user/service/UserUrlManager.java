/**
* Copyright (c) 2005-2012 winterframework.com
* Licensed under the Apache License, Version 2.0 (the "License");
*/
package com.winterframework.firefrog.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.util.HttpJsonClientExt;
import com.winterframework.firefrog.user.dao.UserUrlDao;
import com.winterframework.firefrog.user.entity.UserUrl;
import com.winterframework.firefrog.user.web.controller.game.GameGroupReq;
import com.winterframework.firefrog.user.web.controller.game.GameGroups;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.Response;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * @author cms group
 * @version 1.0
 * @since 1.0
 */

@Component
@Transactional(rollbackFor = Exception.class)
public class UserUrlManager extends BaseManager<UserUrlDao, UserUrl> {
	@PropertyConfig(value = "game_get_group")
	private String game_get_group;

	@Override
	@Autowired
	public void setEntityDao(UserUrlDao userUrlDao) {
		this.entityDao = userUrlDao;
	}
	public Response<GameGroups> getGameGroup(Request<GameGroupReq> request) throws Exception{
		Response<GameGroups> response = null;
		try {
			response = HttpJsonClientExt.postJsonObject(game_get_group, request,
					new TypeReference<Response<GameGroups>>() {
					});
		} catch (Exception e) {
			throw e;
		}
		return response;
	}
	
	

}
