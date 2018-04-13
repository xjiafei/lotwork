package com.winterframework.firefrog.user.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.web.controller.game.GameResp;

public interface ILoginService {

	public User login(String userName, String password, Long ip , String userAgent) throws Exception;
	public GameResp getGameGroup(Long userid) throws ClientProtocolException, SecurityException, IOException, NoSuchMethodException;

}
