/**   
* @Title: IFundSuspiciousCardDao.java 
* @Package com.winterframework.firefrog.fund.dao 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 下午5:41:24 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundSuspiciousCardVO;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/** 
* @ClassName: IFundSuspiciousCardDao 
* @Description: 可疑银行卡操作接口
* @author Alan
* @date 2013-7-24 下午5:41:24 
*  
*/
public interface IFundSuspiciousCardDao extends BaseDao<FundSuspiciousCardVO> {

	/**
	 * 
	* @Title: searchFundSuspiciousCardRecords 
	* @Description: 查询可疑银行卡分页列表 
	* @return
	 */
	public Page<FundSuspiciousCard> searchFundSuspiciousCardRecords(PageRequest<?> pageRequest);

	/**
	 * 
	* @Title: addFundSuspiciousCard 
	* @Description: 添加可疑银行卡
	* @param fundSuspiciousCard
	 */
	public void addFundSuspiciousCard(FundSuspiciousCard fundSuspiciousCard);

	/**
	 * 
	* @Title: deleteFundSuspiciousCard 
	* @Description: 删除可疑银行卡
	* @param id
	 */
	public void deleteFundSuspiciousCard(Long id);
	
	/**
	 * 
	* @Title: queryFundSuspiciousCards 
	* @Description: 查询所有可疑银行卡
	* @return
	 */
	public List<FundSuspiciousCard> queryFundSuspiciousCards() throws Exception;
	public boolean isSuspiciousCardUser(Long userId) throws Exception;
	
	/**
	 * 
	* @Title: queryFundSuspiciousCards 
	* @Description: 查询所有可疑银行卡
	* @return
	 */
	public List<String> queryFundSuspiciousAccounts() throws Exception;
	
}
