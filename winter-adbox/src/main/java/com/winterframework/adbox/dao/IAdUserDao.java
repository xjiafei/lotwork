package com.winterframework.adbox.dao;

import java.util.List;

import com.winterframework.adbox.dao.base.IBaseDao;
import com.winterframework.adbox.entity.AdUser;

public interface IAdUserDao extends IBaseDao<AdUser>{
	public List<AdUser> getUserList(String userName) throws Exception;
}
