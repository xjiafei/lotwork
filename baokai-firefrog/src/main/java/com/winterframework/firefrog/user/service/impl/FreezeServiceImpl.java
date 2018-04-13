package com.winterframework.firefrog.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.IFreezeDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.impl.FreezeDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserFreezeLog;
import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.FreezeLog.FreezenRange;
import com.winterframework.firefrog.user.entity.FreezeLog.FrozenAction;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserFreezeInfo.FreezeMethodType;
import com.winterframework.firefrog.user.service.IFreezeService;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * 
 *    
 * 类功能说明: 冻结解冻用户操作服务类  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David
 *
 */
@Service("freezeServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class FreezeServiceImpl extends BaseManager<FreezeDaoImpl, UserFreezeLog> implements IFreezeService {

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "freezeDaoImpl")
	private IFreezeDao freezeDao;

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void freezeUser(FreezeDTO freeze) throws Exception {

		User _user = customerDao.queryUserById(freeze.getUserId());
		//如果已经是冻结状态，则返回；
		if ((freeze.getFreezeMethod()!=null && freeze.getFreezeMethod()!=5) && _user.isFreeze()) {
			return;
		}

		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setId(freeze.getUserId());

		list.add(user);

		//2.保存用户冻结日志信息
		FreezeLog freezeLog = new FreezeLog();
		freezeLog.setAction(FrozenAction.Freeze); //0 为冻结
		freezeLog.setFrozenUser(user);
		freezeLog.setMethod(freeze.getFreezeMethod());
		freezeLog.setRange(FreezenRange.SingleUser);
		freezeLog.setReason(freeze.getMemo());
		freezeLog.setTime(freeze.getFreezeDate());
		User frozenUser = new User();
		freezeLog.setFreezeAccount(freeze.getFreezeAccount());
		frozenUser.setId(freeze.getFreezeId());

		freezeLog.setActor(frozenUser);

		freezeDao.saveFreezeLog(freezeLog);

		user.setFreezeLog(freezeLog);
		customerDao.updateUserFreeze(list, freeze);

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void freezeUserAndSubUser(FreezeDTO freeze) throws Exception {

		//1.获取需要更新的用户Id
		List<User> list = customerDao.getUserAndSubUserList(freeze.getUserId());

		List<FreezeLog> freezeLogs = new ArrayList<FreezeLog>();

		List<User> _list = new ArrayList<User>();

		for (User _user : list) {

			/*if (_user.isFreeze()) { //如果已经是冻结状态，则跳过
				continue;
			}*/

			_user.setFreeze(true);

			FreezeLog freezeLog = new FreezeLog();
			freezeLog.setAction(FrozenAction.Freeze); //0 为冻结
			freezeLog.setFrozenUser(_user);
			freezeLog.setMethod(freeze.getFreezeMethod());
			freezeLog.setRange(FreezenRange.UserTree);
			freezeLog.setReason(freeze.getMemo());
			freezeLog.setTime(freeze.getFreezeDate());
			User frozenUser = new User();
			frozenUser.setId(freeze.getFreezeId());
			freezeLog.setActor(frozenUser);
			freezeLogs.add(freezeLog);
			_list.add(_user);
			freezeLog.setFreezeAccount(freeze.getFreezeAccount());
			_user.setFreezeLog(freezeLog);
		}
		//3.保存用户冻结日志信息
		freezeDao.saveFreezeLogBatch(freezeLogs);

		//2.更新用户冻结信息
		customerDao.updateUserFreeze(_list, freeze);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void unFreezeApproveUser(UnFreezeDTO unFreeze) throws Exception {
		// 1。先更新用户信息
		List<User> list = new ArrayList<User>();
		User user = customerDao.queryUserById(unFreeze.getUserId());
		list.add(user);

		//2.保存用户冻结日志信息
		FreezeLog freezeLog = new FreezeLog();
		//freezeLog.setAction(FrozenAction.UnFreeze); //1 为冻结
		//freezeLog.setFrozenUser(user);
		freezeLog.setUnfreezeMethod(0);
		freezeLog.setUnfreezeRange(FreezenRange.SingleUser);
		freezeLog.setUnfreezeMemo(unFreeze.getMemo());
		freezeLog.setUnfreezeDate(new Date());
		User frozenUser = new User();
		frozenUser.setId(unFreeze.getFreezeId());
		freezeLog.setUnfreezeActor(frozenUser);
		freezeLog.setAction(FrozenAction.UnFreeze);
		freezeLog.setDbId(user.getFreezeId());
		freezeLog.setUnfreezeAccount(unFreeze.getFreezeAccount());
		//freezeDao.deleteByFreezeAccount(unFreeze.getFreezeAccount());
		freezeDao.saveUnFreezeLog(freezeLog);

		user.setFreezeLog(freezeLog);
		customerDao.updateUserUnFreeze(list, unFreeze);

	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void unFreezeUser(UnFreezeDTO unFreeze) throws Exception {

		// 1。先更新用户信息
		List<User> list = new ArrayList<User>();
		User user = customerDao.queryUserById(unFreeze.getUserId());
		if (user.getUserFreeze().getFreezeMethodType() == FreezeMethodType.APPREAL) {
			//申述产生的冻结，也不可解冻
			return;
		}
		if (!user.isFreeze()) {
			return;
		}
		list.add(user);

		//2.保存用户冻结日志信息
		FreezeLog freezeLog = new FreezeLog();
		//freezeLog.setAction(FrozenAction.UnFreeze); //1 为冻结
		//freezeLog.setFrozenUser(user);
		freezeLog.setUnfreezeMethod(0);
		freezeLog.setUnfreezeRange(FreezenRange.SingleUser);
		freezeLog.setUnfreezeMemo(unFreeze.getMemo());
		freezeLog.setUnfreezeDate(new Date());
		User frozenUser = new User();
		frozenUser.setId(unFreeze.getFreezeId());
		freezeLog.setUnfreezeActor(frozenUser);
		freezeLog.setAction(FrozenAction.UnFreeze);
		freezeLog.setDbId(user.getFreezeId());
		freezeLog.setUnfreezeAccount(unFreeze.getFreezeAccount());
		//freezeDao.deleteByFreezeAccount(unFreeze.getFreezeAccount());
		freezeDao.saveUnFreezeLog(freezeLog);

		user.setFreezeLog(freezeLog);
		customerDao.updateUserUnFreeze(list, unFreeze);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void unFreezeUserAndSubUser(UnFreezeDTO unFreeze) throws Exception {

		//1.获取需要更新的用户Id
		List<User> list = customerDao.getUserAndSubUserList(unFreeze.getUserId());

		List<User> _list = new ArrayList<User>();

		for (User _user : list) {

			if (!_user.isFreeze()) {
				continue;
			}
			if (_user.getUserFreeze().getFreezeMethodType() == FreezeMethodType.APPREAL) {
				//申述产生的冻结，也不可解冻
				continue;
			}

			_user.setFreeze(false);
			FreezeLog freezeLog = new FreezeLog();
			freezeLog.setAction(FrozenAction.UnFreeze); //1 为解冻
			freezeLog.setUnfreezeRange(FreezenRange.UserTree);
			freezeLog.setUnfreezeMemo(unFreeze.getMemo());
			freezeLog.setUnfreezeDate(new Date());
			User frozenUser = new User();
			frozenUser.setId(unFreeze.getFreezeId());
			freezeLog.setUnfreezeActor(frozenUser);
			freezeLog.setDbId(_user.getFreezeId());
			freezeLog.setUnfreezeAccount(unFreeze.getFreezeAccount());
			_user.setFreezeLog(freezeLog);

			_list.add(_user);
			//freezeDao.deleteByFreezeAccount(unFreeze.getFreezeAccount());
			freezeDao.saveUnFreezeLog(freezeLog);
		}
		//3.保存用户冻结日志信息

		//2.更新用户冻结信息
		customerDao.updateUserUnFreeze(_list, unFreeze);
	}

	@Override
	public void setEntityDao(FreezeDaoImpl entityDao) {
		this.entityDao = entityDao;
	}

	@Override
	public Page<User> searchFreezeUser(PageRequest<QueryFreezeUserDTO> pageReqeust) throws Exception {

		return customerDao.searchFreezeUser(pageReqeust);
	}

	@Override
	public Page<FreezeLog> searchUnFreezeUserLog(PageRequest<QueryUnFreezeUserLogDTO> pageReqeust) throws Exception {
		return customerDao.searchUnFreezeUserLog(pageReqeust);
	}

}
