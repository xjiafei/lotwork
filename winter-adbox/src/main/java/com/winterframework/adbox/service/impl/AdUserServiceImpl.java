package com.winterframework.adbox.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.adbox.dao.IAdUserDao;
import com.winterframework.adbox.dao.base.BaseServiceImpl;
import com.winterframework.adbox.entity.AdUser;
import com.winterframework.adbox.service.IAdUserService;


@Service("adUserServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class AdUserServiceImpl  extends BaseServiceImpl<IAdUserDao,AdUser> implements IAdUserService {
	@Resource(name="adUserDaoImpl")
	private IAdUserDao dao;
	@Override
	protected IAdUserDao getEntityDao() {
		return dao;
	}
	@Override
	public AdUser getUserByUserPass(String userName, String password)
			throws Exception {
		AdUser user = new AdUser();
		user.setPassword(password);
		user.setName(userName);
		return this.selectOneObjByAttribute(null, user);
	}
	
	
	@Override
	public List<AdUser> getUserList(String userName) throws Exception {
		return dao.getUserList(userName);
	}
	@Override
	public List<AdUser> getUserList(Integer status,String userName,Long parentId) throws Exception {
		AdUser user = new AdUser();
		user.setType(status);
		user.setName(userName);
		user.setParentId(parentId);
		return this.selectListObjByAttribute(null, user);
	}
}
