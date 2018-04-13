package com.winterframework.firefrog.game.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.game.service.IGameUserCenterReportService;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexRequest;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

@Service("gameUserCenterReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameUserCenterReportServiceImpl implements IGameUserCenterReportService {
	
	private Logger log = LoggerFactory.getLogger(GameUserCenterReportServiceImpl.class);

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Override
	public Page<UserCenterReportInfo> queryUserCenterReportByUserId(PageRequest<SubUserReportRequest> pr) {
		return userCustomerDao.queryUserCenterReportInfo(pr);
	}

	@Override
	public Page<UserCenterReportInfo> getUserReportByBetTypeCodeList(PageRequest<UserCenterReportComplexRequest> pr,Long userId) {
		return userCustomerDao.getUserReportByBetTypeCodeList(pr,userId);
	}
	
	@Override
	public Page<UserCenterReportInfo> queryUserCenterReportByComplexCondition(PageRequest<UserCenterReportComplexRequest> pr,Long userId) {
		return userCustomerDao.queryUserCenterReportByComplexCondition(pr,userId);
	}

	@Override
	public UserCenterReportInfo getCurrentUserReportByUserId(Long userId) {
		return userCustomerDao.getCurrentUserReportByUserId(userId); 
	}

}
