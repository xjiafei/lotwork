package com.winterframework.firefrog.fund.dao;

import java.util.Date;
import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundChangeLog;
import com.winterframework.firefrog.fund.entity.UserFundChangeLog;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IFundChangeLogDao 
* @Description: 资金变动记录接口 
* @author david
* @date 2013-7-1 下午3:06:48 
*  
*/
public interface IFundChangeLogDao extends BaseDao<FundChangeLog> {

	/** 
	* @Title: saveFundChangeLog 
	* @Description: 保存资金变动记录 
	* @param userFundChangeLog
	*/
	public void saveFundChangeLog(UserFundChangeLog userFundChangeLog);

	/** 
	* @Title: queryFundChangeLog 
	* @Description: 按类型 时间  查询资金交易情况
	* @param reason
	* @param beginTime
	* @param endTime
	* @return
	*/
	List<FundChangeLog> queryFundChangeLog(Long userId, String reason ,Date beginTime, Date endTime,String account);
}
