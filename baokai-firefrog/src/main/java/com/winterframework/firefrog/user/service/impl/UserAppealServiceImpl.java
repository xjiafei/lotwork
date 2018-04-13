package com.winterframework.firefrog.user.service.impl;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.exception.ServiceException;
import com.winterframework.firefrog.user.dao.IUserAppealDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.impl.UserAppealDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserAppeal;
import com.winterframework.firefrog.user.entity.Appeal;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAppealQueryDTO;
import com.winterframework.firefrog.user.entity.Appeal.AppealType;
import com.winterframework.firefrog.user.service.IAppealService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * 
 *    
 * 类功能说明:  申诉服务接口 
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 *  
 *
 */
@Service("userAppealServiceImpl")
public class UserAppealServiceImpl extends BaseManager<UserAppealDaoImpl, UserAppeal> implements IAppealService {

	@Resource(name = "userCustomerDaoImpl")
	public IUserCustomerDao userCustomerDao;

	@Resource(name = "userAppealDaoImpl")
	public IUserAppealDao userAppealDao;

	@Override
	public void saveUserAppeal(Appeal appeal) throws Exception {
		User user = userCustomerDao.getUserByUserName(appeal.getAccount());
		if (user == null) {
			throw new ServiceException("申诉用户名不存在");
		}
		userAppealDao.saveUserAppeal(appeal);
	}

	@Override
	public void setEntityDao(UserAppealDaoImpl entityDao) {
		this.entityDao = entityDao;

	}

	@Override
	public Page<Appeal> searchUserAppeal(PageRequest<UserAppealQueryDTO> pageReqeust) throws Exception {

		return userAppealDao.searchUserAppeal(pageReqeust);
	}

	@Override
	public Appeal getUserAppealDetail(int userAppleId) throws Exception {
		return userAppealDao.getUserAppealDetail(userAppleId);
	}

	@Override
	public void userAppealAudit(Appeal appeal) throws Exception {

		userAppealDao.userAppealAudit(appeal);
		if (1 == appeal.getPassed()) {
			String aciybt = userAppealDao.getUserAppealDetail((int) appeal.getId()).getAccount();
			//修改安全信息生效时间，n个工自然日之后的24点
			User userCustomer = userCustomerDao.queryUserByName(aciybt);
		/*	if (AppealType.QA.equals(appeal.getType())) {
				userCustomer.getUserProfile().setPassword(null);
				userCustomer.getUserProfile().setWithdrawPwd(null);
				userCustomer.getUserProfile().setQa(null);
			} else {
				userCustomer.getUserProfile().setEmail(null);
			}*/
			userCustomer.getUserProfile()
					.setWithdrawPwdActiveDate(getEndTimeOfDate(appeal.getActivedDays().intValue()));
			userCustomer.getUserProfile().setQaActiveDate(getEndTimeOfDate(appeal.getActivedDays().intValue()));
			userCustomerDao.updateUser(userCustomer);
		}
	}

	private Date getEndTimeOfDate(int addDays) {
		if (addDays == 0) {
			return new Date();
		}
		Date date = DateUtils.addDays(new Date(), addDays);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

}
