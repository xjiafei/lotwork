package com.winterframework.firefrog.acl.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.winterframework.firefrog.acl.dao.IAclGroupDao;
import com.winterframework.firefrog.acl.entity.AclGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.acl.dao.AclUserDao;
import com.winterframework.firefrog.acl.entity.Acl;
import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.acl.service.AclUserService;
import com.winterframework.firefrog.acl.service.IAclService;
import com.winterframework.firefrog.acl.web.dto.AclUserStruc;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.exception.LoginServiceException;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("aclUserServiceImpl")
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class AclUserServiceImpl implements AclUserService {

	@Resource(name = "aclUserDaoImpl")
	private AclUserDao aclUserDao;

	@Resource(name = "RedisClient")
	private RedisClient redisSerive;

	@Resource(name = "aclServiceImpl")
	private IAclService aclService;

    @Resource(name = "aclGroupDaoImpl")
    private IAclGroupDao aclGroupDao;
	@Override
	public void saveUser(AclUser user) throws Exception {
		//保存的时候根据卡号找到密码
		if(user.getBindCard()!=null)
		user.setBindPasswd(aclUserDao.getUserByBindCard(user.getBindCard()));
		aclUserDao.saveUser(user);
	}

	@Override
	public void deleteUser(Long id) throws Exception {
		aclUserDao.deleteUser(id);
	}

	@Override
	public void modifyUser(AclUser user) throws Exception {
		//修改的时候根据卡号找到密码
		if(user.getBindCard()!=null)
		user.setBindPasswd(aclUserDao.getUserByBindCard(user.getBindCard()));
		aclUserDao.updateUser(user);
	}

	@Override
	public Page<AclUser> queryList(PageRequest<AclUserStruc> pageRequest) throws Exception {
		return aclUserDao.selectList(pageRequest);
	}

	/**
	* Title: getByUserId
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.acl.service.AclUserService#getByUserId(java.lang.Long) 
	*/
	@Override
	public AclUser getByUserId(Long id) throws Exception {
		return aclUserDao.getByUserId(id);
	}

	public AclUser getUserByBindPwd(String bindPwd) throws Exception {
		return aclUserDao.getUserByBindPwd(bindPwd);
	}
	
	

	@Override
	public List<AclUser> queryUserByBindCard(String bindCard) throws Exception {
		return aclUserDao.queryUserByBindCard(bindCard);
	}

	@Override
	public void modifyUserPwd(Long id, String oldPwd, String newPwd) throws Exception {

		AclUser user = this.getByUserId(id);
		if (user.getPasswd().equals(oldPwd)) {
			user.setPasswd(newPwd);
			this.aclUserDao.updateUser(user);
		} else {
			throw new Exception();
		}
	}

	@Override
	public AclUser adminLogin(String userName, String password) throws Exception {
       
		AclUser admin = aclUserDao.getUserByName(userName);
		if (admin == null) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_USER_NOT_EXIST, "用户不存在");
		}
		if (!admin.getPasswd().equals(password)) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_PWD_ERROR, "用户密码错误");
		}
		if (admin.getUserStatus() != AclUser.Status.normal) {
			throw new LoginServiceException(UserCustomer.LOGIN_FAIL_REASON_USER_FREEZE, "用户被锁定或已删除，不能登录");
		}
        AclGroup group = aclGroupDao.queryAclGroupByUser(admin.getId());
        if (group.getInUse().intValue() == 0) {
            throw new LoginServiceException(UserCustomer.GROUP_LIMIT, "登录失败，权限组被禁用");
        }
		Long adminId = admin.getId();
		List<Acl> aclList = aclService.queryAclByUser(adminId);
		List<String> strList = new ArrayList<String>();
		for (Acl acl : aclList) {
			strList.add(acl.getName());
		}
		admin.setAcls(strList);

		return admin;
	}

	@Override
	public List<AclUser> getUserByAccount(String account) {
		List<AclUser> aclList = aclUserDao.getUserByAccount(account);
		return aclList;
	}
}
