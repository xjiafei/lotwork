package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.fund.dao.IUserAgentIncomeDao;
import com.winterframework.firefrog.fund.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.fund.dao.vo.UserAgentIncomeVO;
import com.winterframework.firefrog.fund.dao.vo.UserGameBettypeIncome;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.web.dto.UserAgentIncomeRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName UserAgentIncomeDaoImpl 
* @Description 总代盈亏报表 
* @author  hugh
* @date 2014年9月25日 上午11:23:18 
*  
*/
@Repository("userAgentIncomeDaoImpl")
public class UserAgentIncomeDaoImpl  extends BaseIbatis3Dao<UserAgentIncomeVO> implements IUserAgentIncomeDao{
	private static final Logger log = LoggerFactory.getLogger(UserAgentIncomeDaoImpl.class);
	
	/** 
	* @Title: getUserAgentIncomes 
	* @Description: 盈亏查询（包含下级）
	* @param req
	* @return
	*/
	@Override
	public List<UserAgentIncomeVO> getUserAgentIncomes(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		log.info("getUserAgentIncomes filters:" + filters.toString());
		List<UserAgentIncomeVO> list = sqlSessionTemplate.selectList(getQueryPath("getUserAgentIncomes"),filters);
		log.info("list:"+list.size());
		return list;
	}


	/** 
	* @Title: getUserIncomes 
	* @Description: 盈亏查询（只有自己）
	* @param req
	* @return
	*/
	public List<UserAgentIncomeVO> getUserIncomes(UserAgentIncomeRequest req){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("id", req.getId());
		log.info("getUserIncomes filters:"+filters.toString());
		return sqlSessionTemplate.selectList(getQueryPath("getUserIncomes"),filters);
	}
	
	/** 
	* @Title: getUserBetIncomes 
	* @Description: 用户玩法盈亏
	* @param req
	* @return
	*/
	public List<UserGameBettypeIncome> getUserBetIncomes(UserAgentIncomeRequest req){
		Map<String, Object> filters = new HashMap<String, Object>();
//		filters.put("account", req.getAccount());
//		filters.put("isFreeze", req.getIsFreeze());
//		filters.put("betTypeCode", req.getBetTypeCode());
//		filters.put("moneyMode", req.getMoneyMode());
//		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("id", req.getId());//
		return sqlSessionTemplate.selectList(getQueryPath("getUserBetIncomes"),filters);
	}
	
	/** 
	* @Title: getGameBetTypes 
	* @Description: 根据彩种获得彩种玩法列表
	* @param lotteryId
	* @return
	*/
	public List<GameBettypeStatus> getGameBetTypes(Long lotteryId){
		return sqlSessionTemplate.selectList(getQueryPath("getGameBetTypes"),lotteryId);
	}
	
	@Override
	public List<UserAgentIncomeVO> getUserAndAgentReport(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		
		filters.put("account", req.getAccount());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		log.info("getUserAndAgentReport filters :" + filters.toString());
		return sqlSessionTemplate.selectList("getUserAndAgentReport",filters);
	}
	
	public UserAgentIncomeVO getAgentReportListByOwn(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startNo", req.getStartNo());
		filters.put("endNo", req.getEndNo());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		
		log.info("getAgentReportListByOwn filters :" + filters.toString());
		return sqlSessionTemplate.selectOne("getAgentReportListByOwn",filters);
	}	
	
	public Long getAgentReportCount(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startNo", req.getStartNo());
		filters.put("endNo", req.getEndNo());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		
		log.info("getAgentReportCount filters :" + filters.toString());
		return sqlSessionTemplate.selectOne("getAgentReportCount",filters);
	}	
	
	public List<UserAgentIncomeVO> getAgentReportList(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startNo", req.getStartNo());
		filters.put("endNo", req.getEndNo());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		
		log.info("getAgentReportList filters :" + filters.toString());
		return sqlSessionTemplate.selectList("getAgentReportList",filters);
	}
	
	public List<UserAgentIncomeVO> getAgentReport(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		log.info("getAgentReport filters :" + filters.toString());
		return sqlSessionTemplate.selectList("getUserReport",filters);
	}	
	
	public List<UserAgentIncomeVO> getAgentIncome(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("id", req.getId());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		log.info("getUserAndAgentReport filters :" + filters.toString());
		return sqlSessionTemplate.selectList("getAgentIncome",filters);
	}
	
	public List<UserAgentIncomeVO> getCurrentUserReport(UserAgentIncomeRequest req) {
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		return sqlSessionTemplate.selectList("queryCurrentUserReport",filters);
	}
	
	
	public Long getGeneralAgentCounts(UserAgentIncomeRequest req){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("lotteryId",req.getLotteryId());
		return sqlSessionTemplate.selectOne("getGeneralAgentCounts",filters);
		
	}
	//查詢當天有紀錄的總代
	public List<String> getGeneralAgents(UserAgentIncomeRequest req,Integer startNo,Integer endNo){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("isFreeze", req.getIsFreeze());
		filters.put("betTypeCode", req.getBetTypeCode());
		filters.put("moneyMode", req.getMoneyMode());
		filters.put("userLvl", req.getUserLvl());
		filters.put("lotteryId", req.getLotteryId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		filters.put("startNo", startNo);
		filters.put("endNo", endNo);
		
				
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		
		filters.put("parentId", req.getParentId());
		
		return sqlSessionTemplate.selectList("getGeneralAgents",filters);
				
	}
	
	/** 
	* @Title: queryUserIncomes 
	* @Description: 查询下級（只有查自己(代理)）
	* @param userid
	* @return
	*/
	
	public UserAgentIncomeVO queryUserIncomes(UserAgentIncomeRequest req){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("account", req.getAccount());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("parentId", req.getParentId());
		
		filters.put("id", req.getId());
		filters.put("lotteryId", req.getLotteryId());
		log.info("queryUserIncomes filters:"+filters.toString());
		List<UserAgentIncomeVO> datas = new ArrayList<UserAgentIncomeVO>();
		datas = sqlSessionTemplate.selectList(getQueryPath("queryUserIncomes"),filters);
		if(datas.size() > 0){
			return datas.get(0);
		}
		return null;
	}
	
	public Long selectAllLowers(UserAgentIncomeRequest req){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("id", req.getId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		
		return sqlSessionTemplate.selectOne("getLowers",filters);
	}
	
	public List<String> selectSubLowers(UserAgentIncomeRequest req ,long startNo, long endNo){
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("parentId", req.getParentId());
		filters.put("startDate", DateUtils.parse(req.getStartDate()));
		if(req.getEndDate() != null){
			req.setEndDate(req.getEndDate() + " 23:59:59");
		}
		filters.put("endDate", DateUtils.parse(req.getEndDate(),DateUtils.DATETIME_FORMAT_PATTERN));
		filters.put("startNo", startNo);
		filters.put("endNo", endNo);
		
		return sqlSessionTemplate.selectList("getSubLowers",filters);
	}
	
	
	
}
