package com.winterframework.firefrog.fund.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundChangeLogDao;
import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.dao.vo.ResultPager;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.UserFundChangeLog;
import com.winterframework.firefrog.fund.web.controller.charge.FundLogReq;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundChangeLogDaoImpl 
* @Description: 保存资金变动记录 
* @author david
* @date 2013-7-2 下午5:17:49 
*  
*/
@Repository("fundChangeLogDaoImpl")
public class FundChangeLogDaoImpl extends BaseIbatis3Dao<FundChangeLog> implements IFundChangeLogDao {

	@Override
	public void saveFundChangeLog(UserFundChangeLog userFundChangeLog) {
		FundChangeLog fundChangeLog = VOConverter.userFundChangeLog2FundChangeLog(userFundChangeLog);
		insert(fundChangeLog);
	}

	
	@Override
	public List<FundChangeLog> queryFundChangeLog(Long userId, String reason, Date beginTime, Date endTime,String account) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromDate", beginTime);
		map.put("toDate", endTime);
		map.put("reason", reason);	
		map.put("userId", userId);
		map.put("account", account);
		return sqlSessionTemplate.selectList(this.getQueryPath("getByPage"), map);
	}
	
	@Override
	public FundChangeLog queryTotalamaount(PageRequest<FundLogReq> pageRequest) {
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
			param.putAll(pageRequest.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		FundChangeLog totalCount = this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.fund.dao.vo.FundChangeLog.getTotal", param);
		return totalCount;
		
	}
	
	@Override
	public List<FundChangeLog> queryFundChangeLogBySn(Long userId, Date beginTime, Date endTime,String SN) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fromDate", beginTime);
		map.put("toDate", endTime);
//		map.put("fromDate", "2015-10-01 00:00:00");
//		map.put("toDate", "2015-10-20 00:00:00");
		map.put("userId", userId);
		map.put("sn", SN);
		return sqlSessionTemplate.selectList(this.getQueryPath("getBylogSn"), map);
	}
	
	
	public Page<FundChangeLog> getAllByPageUnion(PageRequest<FundLogReq> page)
	{
		return this.pageQuery(page, null, "Union");
	}
}
