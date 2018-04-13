package com.winterframework.adbox.service;
import java.util.List;

import com.winterframework.adbox.dao.base.IBaseService;
import com.winterframework.adbox.entity.AdUser;
public interface IAdUserService extends IBaseService<AdUser> {
	public AdUser getUserByUserPass(String userName,String password) throws Exception;
	
	public List<AdUser> getUserList(String userName) throws Exception;
	
	public List<AdUser> getUserList(Integer status,String userName,Long parentId) throws Exception;
}
