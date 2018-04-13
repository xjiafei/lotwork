package com.winterframework.firefrog.user.dao;

import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserCustomerVo;
import com.winterframework.firefrog.user.entity.UserCustomerRequestVo;

public interface IUserCustomerRecycleDao {	
	
	/**
	 * 清里安全中心信息
	 */
	public void cleanSafeCenter(UserCustomer userCustomer);
	
	/**
	 * 清理个人资料
	 */
	public void cleanPersonalInfo(UserCustomer userCustomer);
	
	/**
	 * 检查帐号与密码
	 * @param userCustomer
	 * @return
	 */
	
	public Long getCountByAccountPasswd(UserCustomer userCustomer);	

}
