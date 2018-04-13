package com.winterframework.firefrog.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.user.dao.IUserCustomerRecycleDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserCustomerRecycle;
import com.winterframework.firefrog.user.dao.vo.UserCustomerVo;
import com.winterframework.firefrog.user.entity.UserCustomerRequestVo;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("userCustomerRecycleDaoImpl")
public class UserCustomerRecycleDaoImpl extends BaseIbatis3Dao<UserCustomerRecycle> implements IUserCustomerRecycleDao{

	@Override
	public void cleanSafeCenter(UserCustomer userCustomer) {
		this.sqlSessionTemplate.update("cleanSafeCenter", userCustomer);		
	}

	@Override
	public void cleanPersonalInfo(UserCustomer userCustomer) {
		this.sqlSessionTemplate.update("cleanPersonalInfo", userCustomer);		
	}

	@Override
	public Long getCountByAccountPasswd(UserCustomer userCustomer) {
		return this.sqlSessionTemplate.selectOne("getCountByAccountPasswd", userCustomer);
	}	
}
