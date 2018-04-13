package com.winterframework.firefrog.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.dao.IFundChangeLogDao;
import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.dao.vo.FundChargeWithdrawVo;
import com.winterframework.firefrog.user.dao.IUserAgentCountDao;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserAgentCountVo;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.UserAgentCount;
import com.winterframework.firefrog.user.service.IUserAgentCountService;
import com.winterframework.firefrog.user.web.dto.UserAgentCountStruc;

@Service("userAgentCountServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class UserAgentCountServiceImpl implements IUserAgentCountService {

	@Resource(name = "userAgentCountDaoImpl")
	private IUserAgentCountDao userAgentCountDao;

	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDao;

	@Resource(name = "fundChangeLogDaoImpl")
	private IFundChangeLogDao fundChangeLogDao;

	@Override
	public List<UserAgentCount> queryUserAgentCount(UserAgentCountStruc userAgentCount) throws Exception {
		return userAgentCountDao.queryUserAgentCount(userAgentCount);
	}
	
	public List<FundChargeWithdrawVo> queryTopChargeWithdrawRpt(UserAgentCountStruc userAgentCount) throws Exception {
		return userAgentCountDao.queryTopChargeWithdrawRpt(userAgentCount);
	}
	@Override
	public Map queryTeamUser(Long userId) throws Exception {
		return userAgentCountDao.queryUserCount(userId);
	}


	@Override
	public void userAgentCountOneHourBefore() throws Exception {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
		Date beforeDate = df.parse(df.format(calendar.getTime()));
		Date toDate = df.parse(df.format(new Date()));
		SimpleDateFormat dfDay = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dfTime = new SimpleDateFormat("HH:00:00");
		Date day = dfDay.parse(dfDay.format(toDate));
		Long time = Long.parseLong(dfTime.format(toDate).substring(0, 2));

		//先查总代用户
		UserCustomer entity = new UserCustomer();
		entity.setUserLvl(0);
		List<UserCustomer> userCustomers = userCustomerDao.getAllByEntity(entity);

		for (UserCustomer userCustomer : userCustomers) {
			UserAgentCountVo userAgentCount = new UserAgentCountVo();
			userAgentCount.setDay(day);
			userAgentCount.setTime(time);
			userAgentCount.setUserId(userCustomer.getId());
			//递归查询设置用户信息
			userAgentCountOneHourBeforeFatherAndSon(userAgentCount, beforeDate, toDate);
		}
	}

	private void userAgentCountOneHourBeforeFatherAndSon(UserAgentCountVo userAgentCount, Date beforeDate, Date toDate) {

		Long userId = userAgentCount.getUserId();
		userAgentCount.setDay(userAgentCount.getDay());

		//统计投注金额  没有减去撤销的投注
		List<FundChangeLog> changes = fundChangeLogDao.queryFundChangeLog(userId, "GM,DVCB,null,1", beforeDate, toDate,null);
		if (changes != null) {
			for (FundChangeLog fundChangeLog : changes) {
				userAgentCount.setBet(userAgentCount.getBet() + fundChangeLog.getBeforBal() - fundChangeLog.getCtBal());
			}
		}

		List<FundChangeLog> changes0 = fundChangeLogDao
				.queryFundChangeLog(userId, "GM,DVCN,null,1", beforeDate, toDate,null);
		if (changes0 != null) {
			for (FundChangeLog fundChangeLog : changes0) {
				userAgentCount.setBet(userAgentCount.getBet() + fundChangeLog.getBeforBal() - fundChangeLog.getCtBal());
			}
		}

		//统计充值  TODO 后期代码优化
		List<FundChangeLog> changes1 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,MDAX,null,5", beforeDate, toDate,null);
		if (changes1 != null) {
			for (FundChangeLog fundChangeLog : changes1) {
				userAgentCount.setCharge(userAgentCount.getCharge() + fundChangeLog.getCtBal()
						- fundChangeLog.getBeforBal());
			}
		}

		List<FundChangeLog> changes2 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,ADML,null,8", beforeDate, toDate,null);
		if (changes2 != null) {
			for (FundChangeLog fundChangeLog : changes2) {
				userAgentCount.setCharge(userAgentCount.getCharge() + fundChangeLog.getCtBal()
						- fundChangeLog.getBeforBal());
			}
		}
		List<FundChangeLog> changes22 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,ADAL,null,3", beforeDate, toDate,null);
		if (changes2 != null) {
			for (FundChangeLog fundChangeLog : changes22) {
				userAgentCount.setCharge(userAgentCount.getCharge() + fundChangeLog.getCtBal()
						- fundChangeLog.getBeforBal());
			}
		}
		//统计提现 TODO 后期代码优化
		List<FundChangeLog> changes3 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,CWTS,null,5", beforeDate, toDate,null);
		if (changes3 != null) {
			for (FundChangeLog fundChangeLog : changes3) {
				userAgentCount.setWithDraw(userAgentCount.getWithDraw() + fundChangeLog.getBeforBal()
						- fundChangeLog.getCtBal());
			}
		}
		List<FundChangeLog> changes4 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,CWTS,null,6", beforeDate, toDate,null);
		if (changes4 != null) {
			for (FundChangeLog fundChangeLog : changes4) {
				userAgentCount.setWithDraw(userAgentCount.getWithDraw() + fundChangeLog.getBeforBal()
						- fundChangeLog.getCtBal());
			}
		}
		List<FundChangeLog> changes5 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,CWCS,null,5", beforeDate, toDate,null);
		if (changes5 != null) {
			for (FundChangeLog fundChangeLog : changes5) {
				userAgentCount.setWithDraw(userAgentCount.getWithDraw() + fundChangeLog.getBeforBal()
						- fundChangeLog.getCtBal());
			}
		}
		List<FundChangeLog> changes6 = fundChangeLogDao
				.queryFundChangeLog(userId, "FD,CWCS,null,6", beforeDate, toDate,null);
		if (changes6 != null) {
			for (FundChangeLog fundChangeLog : changes6) {
				userAgentCount.setWithDraw(userAgentCount.getWithDraw() + fundChangeLog.getBeforBal()
						- fundChangeLog.getCtBal());
			}
		}

		//统计返点  没有排除撤销的返点  TODO 后期代码优化
		List<FundChangeLog> changes7 = fundChangeLogDao
				.queryFundChangeLog(userId, "GM,RSXX,null,1", beforeDate, toDate,null);
		if (changes7 != null) {
			for (FundChangeLog fundChangeLog : changes7) {
				userAgentCount.setReward(userAgentCount.getReward() + fundChangeLog.getCtBal()
						- fundChangeLog.getBeforBal());
			}
		}
		List<FundChangeLog> changes8 = fundChangeLogDao
				.queryFundChangeLog(userId, "GM,RHAX,null,2", beforeDate, toDate,null);
		if (changes8 != null) {
			for (FundChangeLog fundChangeLog : changes8) {
				userAgentCount.setReward(userAgentCount.getReward() + fundChangeLog.getCtBal()
						- fundChangeLog.getBeforBal());
			}
		}
		//释放不必要的资源
		changes = null;
		changes0 = null;
		changes1 = null;
		changes2 = null;
		changes22 = null;
		changes3 = null;
		changes4 = null;
		changes5 = null;
		changes6 = null;
		changes7 = null;
		changes8 = null;

		UserCustomer search = new UserCustomer();
		search.setParentId(userId);
		List<UserCustomer> childUserCustomers = userCustomerDao.getAllByEntity(search);
		if (childUserCustomers != null) {
			for (UserCustomer userCustomer2 : childUserCustomers) {
				UserAgentCountVo userAgentCount2 = new UserAgentCountVo();
				userAgentCount2.setUserId(userCustomer2.getId());
				userAgentCount2.setDay(userAgentCount.getDay());
				userAgentCount2.setTime(userAgentCount.getTime());

				Date userRegisterDate = userCustomer2.getRegisterDate();
				if (userRegisterDate.after(beforeDate) && userRegisterDate.before(toDate)) {
					userAgentCount.setNewUser(userAgentCount.getNewUser() + 1L);
				}
				//递归查询子用户信息
				userAgentCountOneHourBeforeFatherAndSon(userAgentCount2, beforeDate, toDate);
			}
		}

		userAgentCountDao.insert(userAgentCount);
	}

}
