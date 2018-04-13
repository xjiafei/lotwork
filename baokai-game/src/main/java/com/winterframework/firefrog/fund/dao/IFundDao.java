package com.winterframework.firefrog.fund.dao;

import java.util.Map;

import com.winterframework.firefrog.fund.dao.vo.Fund;
import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IFundDao 
* @Description: 用户资金表 
* @author david
* @date 2013-6-28 下午4:10:25 
*  
*/
public interface IFundDao extends BaseDao<Fund> {

	public UserFund getUserFund(Long userId);
	public Long getTeamFund(Long userId);
	public void addWhite(String account);
	public int updateFund(Map<String, Object> map);

	public int updateFundById(Long id, Long frozenAmt);
}
