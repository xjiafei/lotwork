/**   
* @Title: BankCardDao.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author Denny  
* @date 2013-7-1 下午4:26:16 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.vo.UserBank;
import com.winterframework.firefrog.fund.dao.vo.UserCardBindHistoryRecordVO;
import com.winterframework.firefrog.fund.dao.vo.UserCardBindVO;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.UserCardBind;
import com.winterframework.firefrog.fund.entity.UserCardBindHistory;
import com.winterframework.firefrog.fund.web.dto.BankCardBindHistoryRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardBindRecordQueryDTO;
import com.winterframework.firefrog.fund.web.dto.BankCardQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: BankCardDao 
* @Description: 银行卡管理Dao 
* @author Denny 
* @date 2013-7-1 下午4:26:16 
*  
*/
@Repository("bankCardDaoImpl")
public class BankCardDaoImpl extends BaseIbatis3Dao<UserBank> implements IBankCardDao {

	@Override
	public UserBank queryBankCardByUserId(long userId) {
		return sqlSessionTemplate.selectOne("selectUserBankByUserId", userId);
	}
	
	@Override
	public List<UserBank> queryBankCardsByUserId(long userId) {
		return sqlSessionTemplate.selectList("selectUserBankByUserId", userId);
	}

	@Override
	public void addBankCard(BankCard bc) {
		UserBank ub = VOConverter.bankCard2UserBank(bc);
		this.insert(ub);
	}
	
	@Override
	public void updateBankCard(BankCard bc) {
		UserBank ub = VOConverter.bankCard2UserBank(bc);
		ub.setGmtModified(new Date());
		this.update(ub);
	}

	@Override
	public void removeBankCard(long bindId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", bindId);
		sqlSessionTemplate.delete("deleteBankCard", map);
	}

	@Override
	public void addBankCardHistoryRecord(UserCardBindHistory userCardBindHistoryRecord) {
		UserCardBindHistoryRecordVO vo = VOConverter
				.UserCardBindHistoryRecord2UserCardBindHistoryRecordVO(userCardBindHistoryRecord);
		sqlSessionTemplate.insert("insertBindHistoryRecord", vo);
	}
	
	@Override
	public List<String> getBankCardNotSuspicious(long userId){
		List<String> boundBankCards = new ArrayList<String>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sortColumns", "ID");
		boundBankCards = sqlSessionTemplate.selectList("getBankCardNotSuspicious", map);
		return boundBankCards;
	}
	
	@Override
	public List<BankCard> getBoundBankCard(long userId,String cardNumber) {
		List<BankCard> boundBankCards = new ArrayList<BankCard>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sortColumns", "ID");
		map.put("bankNumber", cardNumber);

		List<UserBank> userBanks = sqlSessionTemplate.selectList("getBoundBankCardByUserId", map);
		if (userBanks.size() > 0) {
			for (UserBank ub : userBanks) {
				BankCard bc = VOConverter.userBank2BankCard(ub);
				boundBankCards.add(bc);
			}
		}

		return boundBankCards;
	}
	
	
	@Override
	public List<BankCard> getBoundBankCardByRequest(BankCardQueryRequest request) {
		List<BankCard> boundBankCards = new ArrayList<BankCard>();
		
		long userId = request.getUserId();
		long bindCardType = request.getBindCardType();
		String bankNumber = request.getBankNumber();
		String bankAccount = request.getBankAccount();
		String nickName = request.getNickName();
		String nickNameMust = request.getNickNameMust();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("sortColumns", "ID");
		map.put("bindCardType", bindCardType);
		map.put("bankNumber", bankNumber);
		map.put("bankAccount", bankAccount);
		map.put("nickName", nickName);
		map.put("nickNameMust", nickNameMust);

		List<UserBank> userBanks = sqlSessionTemplate.selectList("getBoundBankCardByUserId", map);
		if (userBanks.size() > 0) {
			for (UserBank ub : userBanks) {
				BankCard bc = VOConverter.userBank2BankCard(ub);
				boundBankCards.add(bc);
			}
		}

		return boundBankCards;
	}
	


	@Override
	public Page<UserCardBind> searchUserCardBindRecords(PageRequest<BankCardBindRecordQueryDTO> pageRequest)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		BankCardBindRecordQueryDTO queryDTO = pageRequest.getSearchDo();

		if (null != queryDTO.getUserAccount()) {
			map.put("account", queryDTO.getUserAccount());
		}

		if (null != queryDTO.getUserLvl()) {
			map.put("userLvl", queryDTO.getUserLvl());
		}

		if (null != queryDTO.getOperator()) {
			map.put("operator", queryDTO.getOperator());
		}

		if (null != queryDTO.getBankCardNumber()) {
			map.put("cardNumber", queryDTO.getBankCardNumber());
		}
		
		if (null != queryDTO.getBindcardType()) {
			map.put("bindcardType", queryDTO.getBindcardType());
		}

		return getUserCardBindRecords(map, pageRequest);
	}

	private Page<UserCardBind> getUserCardBindRecords(Map<String, Object> map,
			PageRequest<BankCardBindRecordQueryDTO> pageRequest) throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountFromUserBank", map);

		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<UserCardBind>(0);
		}

		Page<UserCardBind> page = new Page<UserCardBind>(pageRequest.getPageNumber(), pageRequest.getPageSize(),
				totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", BankCardBindRecordQueryDTO.SORT_COLUMNS);
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		List<UserCardBindVO> list = sqlSessionTemplate.selectList("queryUserBankBindList", filters, rowBounds);

		List<UserCardBind> userCardBindList = new ArrayList<UserCardBind>();

		for (UserCardBindVO userCardBindVO : list) {

			try {

				UserCardBind userCardBind = VOConverter.UserCardBindVO2UserCardBind(userCardBindVO);
				
				BankCardQueryRequest br = new BankCardQueryRequest();
				br.setUserId(userCardBindVO.getUserId());
				br.setBankNumber(map.get("cardNumber")==null?null:(String)map.get("cardNumber"));
				
				if(map.get("bindcardType")!=null){
					br.setBindCardType(Long.valueOf(map.get("bindcardType").toString()));					
				}
				
				List<BankCard> bankCards = getBoundBankCardByRequest(br);
						
						//getBoundBankCard(userCardBindVO.getUserId(),map.get("cardNumber")==null?null:(String)map.get("cardNumber"));

				userCardBind.setBankCards(bankCards);

				userCardBindList.add(userCardBind);

			} catch (Exception e) {
				log.error("com.winterframework.firefrog.fund.dao.impl>>>BankCardDaoImpl error......", e);
				throw e;
			}

		}

		page.setResult(userCardBindList);

		return page;
	}

	@Override
	public Page<UserCardBindHistory> searchUserCardBindHistoryRecords(
			PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		BankCardBindHistoryRecordQueryDTO queryDTO = pageRequest.getSearchDo();

		map.put("userId", queryDTO.getUserId());
		map.put("bank_number", queryDTO.getBankCard());
		map.put("bindcardType", queryDTO.getBindcardType());
		return getUserCardBindHistoryRecords(map, pageRequest);
	}

	private Page<UserCardBindHistory> getUserCardBindHistoryRecords(Map<String, Object> map,
			PageRequest<BankCardBindHistoryRecordQueryDTO> pageRequest) throws Exception {
		Number totalCount = (Number) sqlSessionTemplate.selectOne("getCountFromBindRecord", map);

		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<UserCardBindHistory>(0);
		}

		Page<UserCardBindHistory> page = new Page<UserCardBindHistory>(pageRequest.getPageNumber(),
				pageRequest.getPageSize(), totalCount.intValue());

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());

		List<UserCardBindHistoryRecordVO> list = sqlSessionTemplate.selectList("queryBindHistoryRecordList", filters,
				rowBounds);

		List<UserCardBindHistory> userCardBindHistoryRecordList = new ArrayList<UserCardBindHistory>();

		for (UserCardBindHistoryRecordVO vo : list) {

			try {

				UserCardBindHistory userCardBindHistoryRecord = VOConverter
						.UserCardBindHistoryRecordVO2UserCardBindHistoryRecord(vo);

				userCardBindHistoryRecordList.add(userCardBindHistoryRecord);

			} catch (Exception e) {
				log.error("com.winterframework.firefrog.fund.dao.impl>>>BankCardDaoImpl error......", e);
				throw e;
			}

		}

		page.setResult(userCardBindHistoryRecordList);

		return page;
	}

	@Override
	public List<UserBank> getFormUserBankByUserId(Long userId) {
		return this.sqlSessionTemplate.selectList(this.getQueryPath("getFormUserBankByUserId"), userId);
	}

	@Override
	public Long getCountFromUserBankByBankNum(String bankNum) {
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getCountFromUserBankByBankNum"), bankNum);
	}

	@Override
	public Long getCountFromUserBankHistoryByDate(Long userId, Date lockTime) {
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("lockTime", lockTime);		
		return this.sqlSessionTemplate.selectOne(this.getQueryPath("getCountFromUserBankHistoryByDate"), map);
	}

}
