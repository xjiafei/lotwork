package com.winterframework.firefrog.fund.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.fund.dao.IUserAgentIncomeDao;
import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.dao.vo.UserGameBettypeIncome;
import com.winterframework.firefrog.fund.service.IUserAgentIncomeService;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeRequest;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeDetailDto;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeGroupDto;
import com.winterframework.firefrog.fund.web.dto.UserGameBettypeIncomeSetDto;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.entity.User;

/**
 * @ClassName UserAgentIncomeServiceImpl
 * @Description 总代盈亏报表
 * @author hugh
 * @date 2014年9月25日 上午11:24:33
 * 
 */
@Service("userAgentIncomeServiceImpl")
public class UserAgentIncomeServiceImpl implements IUserAgentIncomeService {

	private static final Logger log = LoggerFactory
			.getLogger(UserAgentIncomeServiceImpl.class);
	@Resource(name = "userAgentIncomeDaoImpl")
	private IUserAgentIncomeDao userAgentIncomeDaoImpl;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao userCustomerDaoImpl;
	

	/**
	 * @Title: getUserAgentIncomes
	 * @Description: 盈亏查询（包含下级）
	 * @param req
	 * @return
	 */
	@Override
	public List<UserAgentIncomeVO> getUserAgentIncomes(
			UserAgentIncomeRequest req) {
		return userAgentIncomeDaoImpl.getUserAgentIncomes(req);
	}
	
	/**
	 * @Title: selectAllLowers
	 * @Description: 查询下级總數
	 * @param req
	 * @return
	 */
	@Override
	public Long selectAllLowers(UserAgentIncomeRequest req){
		return userAgentIncomeDaoImpl.selectAllLowers(req);
	}
	
	/**
	 * @Title: getUserAndAgentIncomes
	 * @Description: 查詢下級（自己 和 下级）先查自己 在查自己的下級
	 * @param req
	 * @return
	 */
	@Override
	public List<UserAgentIncomeVO> getUserAndAgentIncomes(UserAgentIncomeRequest req) {
		
		Integer startNo = req.getStartNo();
		
		List<UserAgentIncomeVO> list = new ArrayList<UserAgentIncomeVO>();
		
		//自己的部分
		if(startNo.intValue() == 1){
			log.info("=getAgentReportListByOwn Start");
			UserAgentIncomeVO model = userAgentIncomeDaoImpl.getAgentReportListByOwn(req);
			
			if(model == null){
				UserCustomer user = userCustomerDaoImpl.getById(req.getId());
				model = new UserAgentIncomeVO();
				model.setId(user.getId());
				model.setAccount(user.getAccount());
				model.setIsFreeze(Long.parseLong(user.getIsFreeze().toString()));
				model.setUserLvl(Long.parseLong(user.getUserLvl().toString()));
				model.setUserChain(user.getUserChain());
			}
			
			log.info("=getAgentReportListByOwn End");
			list.add(model);
		}
		
		log.info("=getAgentReportList Start");
		List<UserAgentIncomeVO> dataList = userAgentIncomeDaoImpl.getAgentReportList(req);
		log.info("=getAgentReportList End");
		
		//下級的部分
		for(UserAgentIncomeVO voList : dataList){
			list.add(voList);
		}
		
		return list;
	}
	
//	/**
//	 * @Title: getUserAndAgentIncomes
//	 * @Description: 查詢下級（自己 和 下级）先查自己 在查自己的下級
//	 * @param req
//	 * @return
//	 */
//	@Override
//	public List<UserAgentIncomeVO> getUserAndAgentIncomes(
//			UserAgentIncomeRequest req, Integer startNo, Integer endNo) {
//		List<UserAgentIncomeVO> resData = new ArrayList<UserAgentIncomeVO>();
//		log.debug("req.getId() : " + req.getId());
//		UserCustomer usr = userCustomerDaoImpl.getById(req.getId());
//		
//		req.setAccount(usr.getAccount());
//		log.debug("----------------------------------------------------------usr.getAccount() : " + usr.getAccount());
//		if(startNo.intValue() == 1){
//			UserAgentIncomeVO self = userAgentIncomeDaoImpl.queryUserIncomes(req);
//				
//			if (self == null) {
//				self = new UserAgentIncomeVO();
//				UserCustomer user = userCustomerDaoImpl.getById(req.getId());
//				self.setAccount(user.getAccount());
//				self.setBet(0L);
//				self.setId(req.getId());
//				self.setIsDeleted(user.getIsDeleted());
//				if (user.getIsFreeze() != null) {
//					self.setIsFreeze(Long.valueOf(user.getIsFreeze() + ""));
//				}
//	
//				self.setResult(0L);
//				self.setRet(0L);
//				self.setTrueBet(0L);
//				self.setUserChain(user.getUserChain());
//				if (user.getUserLvl() != null) {
//					self.setUserLvl(Long.valueOf(user.getUserLvl() + ""));
//				}
//				self.setWin(0L);
//				self.setActivityGifts(0L);
//			}
//			
//			resData.add(self);
//		}
//		
//		List<String> lowers = userAgentIncomeDaoImpl.selectSubLowers(req,startNo,endNo);
//		for(String account:lowers){
//			log.debug("account:"+account);
//		}
//		for(String account:lowers){
//			req.setAccount(account);
//			
//			List<UserAgentIncomeVO> info = userAgentIncomeDaoImpl.getAgentReport(req);
//			if(info.size() > 0){
//				log.info("info size > 0 & account=" + account);
//				resData.add(info.get(0));
//			}
//		}
//		
//		for(UserAgentIncomeVO test : resData){
//			log.info("=test: Account: ActivityGifts:" + test.getAccount() + ":::" +test.getActivityGifts());
//		}
//		
//		return resData;
//	}

	/**
	 * @Title: getUserBetIncomes
	 * @Description: 用户玩法盈亏
	 * @param req
	 * @return
	 */
	public List<UserGameBettypeIncomeGroupDto> getUserBetIncomes(
			UserAgentIncomeRequest req) {
		List<UserGameBettypeIncomeGroupDto> groups = new ArrayList<UserGameBettypeIncomeGroupDto>();
		List<UserGameBettypeIncome> bets = userAgentIncomeDaoImpl
				.getUserBetIncomes(req);

		TreeMap<Integer, TreeMap<Integer, List<UserGameBettypeIncome>>> gMap = new TreeMap<Integer, TreeMap<Integer, List<UserGameBettypeIncome>>>();
		for (UserGameBettypeIncome userGameBettypeIncome : bets) {
			TreeMap<Integer, List<UserGameBettypeIncome>> sMap = gMap
					.get(userGameBettypeIncome.getGameGroupCode());

			if (sMap == null) {
				sMap = new TreeMap<Integer, List<UserGameBettypeIncome>>();
			}

			List<UserGameBettypeIncome> methods = sMap
					.get(userGameBettypeIncome.getGameSetCode());
			if (methods == null) {
				methods = new ArrayList<UserGameBettypeIncome>();
			}
			methods.add(userGameBettypeIncome);
			sMap.put(userGameBettypeIncome.getGameSetCode(), methods);
			gMap.put(userGameBettypeIncome.getGameGroupCode(), sMap);
		}

		for (Integer groupCode : gMap.keySet()) {

			UserGameBettypeIncomeGroupDto groupDto = new UserGameBettypeIncomeGroupDto();
			List<UserGameBettypeIncomeSetDto> setDtos = new ArrayList<UserGameBettypeIncomeSetDto>();

			TreeMap<Integer, List<UserGameBettypeIncome>> sets = gMap
					.get(groupCode);
			for (Integer setCode : sets.keySet()) {

				UserGameBettypeIncomeSetDto setDto = new UserGameBettypeIncomeSetDto();
				List<UserGameBettypeIncomeDetailDto> details = new ArrayList<UserGameBettypeIncomeDetailDto>();

				List<UserGameBettypeIncome> methods = sets.get(setCode);
				for (UserGameBettypeIncome userGameBettypeIncome : methods) {
					groupDto.setGroupCodeName(userGameBettypeIncome
							.getGroupCodeName());
					setDto.setSetCodeName(userGameBettypeIncome
							.getSetCodeName());

					UserGameBettypeIncomeDetailDto detail = new UserGameBettypeIncomeDetailDto();
					detail.setMethodCodeName(userGameBettypeIncome
							.getMethodCodeName());
					detail.setBet(userGameBettypeIncome.getBet());
					detail.setResult(userGameBettypeIncome.getResult());
					detail.setRet(userGameBettypeIncome.getRet());
					detail.setTrueBet(userGameBettypeIncome.getTrueBet());
					detail.setWin(userGameBettypeIncome.getWin());
					details.add(detail);

				}

				setDto.setDetails(details);
				setDtos.add(setDto);
			}
			groupDto.setSets(setDtos);
			groups.add(groupDto);
		}

		return groups;
	}

	public List<GameBettypeStatus> getGameBetTypes(Long lotteryId) {
		return userAgentIncomeDaoImpl.getGameBetTypes(lotteryId);
	}
	
	@Override
	public Integer getUserAndAgentIncomesCount(UserAgentIncomeRequest req) {
		
		Integer startNo = req.getStartNo();
		
		Long count = userAgentIncomeDaoImpl.getAgentReportCount(req);
		
		int size = startNo.intValue() == 1 ? count.intValue()+1 : count.intValue();
		
		return size;
	}
	
	// 後台報表條件查詢/2015-08-12 modify統一此方法查詢單一玩家包含其所有下級的報表資料
	@Override
	public List<UserAgentIncomeVO> getUserAndAgentReport(UserAgentIncomeRequest req) {
		List<UserAgentIncomeVO> datas = new ArrayList<UserAgentIncomeVO>();
		log.info("query user data!");
		datas = userAgentIncomeDaoImpl.getAgentReport(req);
		return datas;
	}

	//
	public Long getGeneralAgentCounts(UserAgentIncomeRequest req){
		return userAgentIncomeDaoImpl.getGeneralAgentCounts(req);
	}
	
	// 後台報表默認查詢
	public Long getCurrentUserReportCount(UserAgentIncomeRequest req) {
		
		if(req.getAccount() == null && req.getUserLvl() == null){
			req.setUserLvl(0L);
		}
		
		return userAgentIncomeDaoImpl.getAgentReportCount(req);
	}
	
	// 後台報表默認查詢
	public List<UserAgentIncomeVO> getCurrentUserReport(UserAgentIncomeRequest req) {
		
		if(req.getAccount() == null && req.getUserLvl() == null){
			req.setUserLvl(0L);
		}
		
		return userAgentIncomeDaoImpl.getAgentReportList(req);
	}
	
	// 後台報表默認查詢
//	public List<UserAgentIncomeVO> getCurrentUserReport(UserAgentIncomeRequest req,Integer startNo, Integer endNo) {
//		
//		// 先查詢當天是有紀錄的總代
//		List<String> accounts = userAgentIncomeDaoImpl.getGeneralAgents(req,startNo,endNo);
//		log.info("=getGeneralAgents : Size : " + accounts.size());
//
//		List<UserAgentIncomeVO> datas = new ArrayList<UserAgentIncomeVO>();
//
//		for (String account : accounts) {
//			
//			UserAgentIncomeRequest rq = new UserAgentIncomeRequest();
//			rq.setStartDate(req.getStartDate());
//			rq.setId(req.getId());
//			rq.setEndDate(req.getEndDate());
//			//只會查詢總代 所以userLvl給0
//			rq.setUserLvl(0L);
//			rq.setAccount(account);
//			rq.setIsFreeze(null);
//			rq.setMoneyMode(null);
//			rq.setLotteryId(req.getLotteryId());
//			rq.setBetTypeCode(null);
//			rq.setParentId(null);
//			rq.setId(null);
//			rq.setPage(null);
//			List<UserAgentIncomeVO> infos = userAgentIncomeDaoImpl.getAgentReport(rq);
//			if (infos.size() > 0){
//				datas.add(infos.get(0));
//			}
//		}
//		
//		return datas;
//	}

}
