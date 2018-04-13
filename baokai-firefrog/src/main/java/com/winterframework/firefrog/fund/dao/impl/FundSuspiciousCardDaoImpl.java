/**   
* @Title: FundSuspiciousCardDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-24 下午5:51:59 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.fund.dao.IFundSuspiciousCardDao;
import com.winterframework.firefrog.fund.dao.vo.FundSuspiciousCardVO;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.FundSuspiciousCard;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundSuspiciousCardDaoImpl 
* @Description: 可疑银行卡DAO
* @author Alan
* @date 2013-7-24 下午5:51:59 
*  
*/
@Repository("fundSuspiciousCardDaoImpl")
public class FundSuspiciousCardDaoImpl extends BaseIbatis3Dao<FundSuspiciousCardVO> implements IFundSuspiciousCardDao {

	@Override
	public Page<FundSuspiciousCard> searchFundSuspiciousCardRecords(PageRequest<?> pageRequest) {
		Long totalCount = this.getCount();

		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<FundSuspiciousCard>(0);
		}

		Page<FundSuspiciousCard> page = new Page<FundSuspiciousCard>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = null;
		try {
			filters = new PageBeanUtilsBean().describe(pageRequest.getSearchDo());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", "GMT_CREATED desc");

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		List<FundSuspiciousCardVO> list = sqlSessionTemplate.selectList("getFundSusppiciousCardRecordsByPage", filters,
				rowBounds);

		List<FundSuspiciousCard> cardList = new ArrayList<FundSuspiciousCard>();

		for (FundSuspiciousCardVO vo : list) {

			FundSuspiciousCard card = VOConverter.FundSuspiciousCardVO2FundSuspiciousCard(vo);

			cardList.add(card);

		}

		page.setResult(cardList);

		return page;
	}

	@Override
	public List<FundSuspiciousCard> queryFundSuspiciousCards() throws Exception {
		List<FundSuspiciousCardVO> list = this.getAll();
		List<FundSuspiciousCard> cardList = new ArrayList<FundSuspiciousCard>();
		for (FundSuspiciousCardVO vo : list) {
			FundSuspiciousCard card = VOConverter.FundSuspiciousCardVO2FundSuspiciousCard(vo);
			cardList.add(card);
		}
		return cardList;
	}

	public boolean isSuspiciousCardUser() throws Exception {
		return false;
	}

	@Override
	public void addFundSuspiciousCard(FundSuspiciousCard fundSuspiciousCard) {
		FundSuspiciousCardVO vo = VOConverter.FundSuspiciousCard2FundSuspiciousCardVO(fundSuspiciousCard);
		this.insert(vo);
	}

	@Override
	public void deleteFundSuspiciousCard(Long id) {
		this.delete(id);
	}

	@Override
	public List<String> queryFundSuspiciousAccounts() throws Exception {
		List<UserBank> accounts = this.sqlSessionTemplate.selectList("getFundSuspiciousAccounts");
		List<String> list = new ArrayList<String>();
		for (UserBank userBank : accounts) {
			list.add(userBank.getBankAccount());
		}
		return list;
	}

	public boolean isSuspiciousCardUser(Long userId) throws Exception {
		List<UserBank> accounts = this.sqlSessionTemplate.selectList("getFundSuspiciousAccounts");
		for (UserBank userBank : accounts) {
			if (userId.equals(userBank.getUserId())) {
				return true;
			}
		}
		return false;
	}

}
