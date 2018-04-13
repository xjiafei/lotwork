/**   
* @Title: FundManualDepositDaoImpl.java 
* @Package com.winterframework.firefrog.fund.dao.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-29 下午3:36:21 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PageBeanUtilsBean;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.acl.entity.AclUser;
import com.winterframework.firefrog.common.convert.BeanConverter;
import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.fund.dao.IFundManualDepositDao;
import com.winterframework.firefrog.fund.dao.vo.FundBank;
import com.winterframework.firefrog.fund.dao.vo.FundManualDepositVO;
import com.winterframework.firefrog.fund.dao.vo.VOConverter;
import com.winterframework.firefrog.fund.entity.BankCard;
import com.winterframework.firefrog.fund.entity.FundManualOrder;
import com.winterframework.firefrog.fund.web.dto.DepositQueryRequest;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.web.util.JsonMapper;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: FundManualDepositDaoImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-7-29 下午3:36:21 
*  
*/
@Repository("fundManualDepositDao")
public class FundManualDepositDaoImpl extends BaseIbatis3Dao<FundManualDepositVO>  implements IFundManualDepositDao {

	/**
	* Title: saveManualDeposit
	* Description:
	* @param deposit
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundManualDepositDao#saveManualDeposit(com.winterframework.firefrog.fund.dao.vo.FundManualDepositVO) 
	*/
	
	public void saveManualDeposit(FundManualOrder deposit) throws Exception {
		FundManualDepositVO entity = new FundManualDepositVO();
		BeanConverter.convert(entity, deposit);
		entity.setStatus(deposit.getStatus());
		entity.setTypeId(deposit.getType().getStatis());
		entity.setApplyAccount(deposit.getApplyUser().getAccount());
		BankCard card = deposit.getUserBank();
		if (card != null) {
			card.setBindingUser(new User());
			entity.setUserBankStruc(DataConverterUtil.convertObject2Json(card));
		}
		entity.setRcvId(deposit.getRcvId());
		this.insert(entity);
	}

	/**
	* Title: updateManualDeposit
	* Description:
	* @param deposit
	* @throws Exception 
	* @see com.winterframework.firefrog.fund.dao.IFundManualDepositDao#updateManualDeposit(com.winterframework.firefrog.fund.dao.vo.FundManualDepositVO) 
	*/
	
	public void updateManualDeposit(FundManualOrder deposit) throws Exception {
		FundManualDepositVO entity = new FundManualDepositVO();
		BeanConverter.convert(entity, deposit);
		entity.setStatus((deposit.getStatus()));
		entity.setTypeId(deposit.getType().getStatis());
		BankCard card = deposit.getUserBank();
		if (card != null) {
			card.setBindingUser(new User());
			entity.setUserBankStruc(DataConverterUtil.convertObject2Json(card));
		} else {
			entity.setUserBankStruc("");
		}
		this.update(entity);
	}

	public int updateManualDepositCheckStatus(FundManualOrder deposit) throws Exception {
		FundManualDepositVO entity = new FundManualDepositVO();
		BeanConverter.convert(entity, deposit);
		entity.setStatus((deposit.getStatus()));
		entity.setTypeId(deposit.getType().getStatis());
		BankCard card = deposit.getUserBank();
		if (card != null) {
			card.setBindingUser(new User());
			entity.setUserBankStruc(DataConverterUtil.convertObject2Json(card));
		} else {
			entity.setUserBankStruc("");
		}
		return sqlSessionTemplate.update("updateManualDepositCheckStatus", entity);
	}
	
	public FundManualOrder queryManualDepositBySn(String sn) throws Exception {

		FundManualDepositVO vo = sqlSessionTemplate.selectOne(this.getQueryPath("getBySn"), sn);
		FundManualOrder deposit = new FundManualOrder(FundManualOrder.Type.RGDK.creatStatus(
				vo.getTypeId()).getItem());
		BeanConverter.convert(deposit, vo);
		deposit.setStatus((vo.getStatus()));
		deposit.setType(VOConverter.manualLong2Type(vo.getTypeId()));
		return deposit;
	}

	
	public FundManualOrder queryManualDepositById(Long id) throws Exception {
		FundManualDepositVO vo = this.getById(id);
		FundManualOrder deposit = new FundManualOrder(FundManualOrder.Type.RGDK.creatStatus(
				vo.getTypeId()).getItem());
		BeanConverter.convert(deposit, vo);
		deposit.setStatus((vo.getStatus()));
		User acl = new User();
		acl.setAccount(vo.getApplyAccount());
		deposit.setApplyUser(acl);
		deposit.setType(VOConverter.manualLong2Type(vo.getTypeId()));
		if (vo.getUserBankStruc() != null) {
			deposit.setUserBank(JsonMapper.nonAlwaysMapper().fromJson(vo.getUserBankStruc(), BankCard.class));
		} else {
			BankCard bankCard = new BankCard();
			bankCard.setBank(new FundBank());
			deposit.setUserBank(bankCard);
		}
		return deposit;
	}

	
	public Page<FundManualOrder> queryFundManualDeposit(PageRequest<DepositQueryRequest> pageReqeust)
			throws Exception {
		Map<String, Object> param = null;
		try {
			param = new PageBeanUtilsBean().describe(pageReqeust.getSearchDo());
			param.putAll(pageReqeust.getFilters());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.putAll(param);

		int totalCount = ((Long) sqlSessionTemplate.selectOne("getDepositCountByPage", map)).intValue();

		Page<FundManualOrder> page = new Page<FundManualOrder>(pageReqeust.getPageNumber(),
				pageReqeust.getPageSize(), totalCount);

		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageReqeust.getSortColumns());
		filters.putAll(map);

		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<FundManualDepositVO> list = sqlSessionTemplate.selectList("getDepositByPage", filters, rowBounds);

		List<FundManualOrder> orderList = new ArrayList<FundManualOrder>();
		for (FundManualDepositVO vo : list) {
			FundManualOrder deposit = new FundManualOrder(vo.getTypeId());
			BeanConverter.convert(deposit, vo);
			User user = new User();
			UserProfile uf = new UserProfile();
			uf.setAccount(vo.getApplyAccount());
			user.setUserProfile(uf);
			deposit.setApplyUser(user);
			deposit.setId(vo.getId());
			deposit.setVipLvl(vo.getVipLvl());
			deposit.setApprover(vo.getApprover());
			deposit.setApproveTime(vo.getApproveTime());
			deposit.setStatus(vo.getStatus());
			deposit.setType(VOConverter.manualLong2Type(vo.getTypeId()));
			if (vo.getUserBankStruc() != null) {
				deposit.setUserBank((BankCard) DataConverterUtil.convertJson2Object(vo.getUserBankStruc(),
						new BankCard().getClass()));
			} else {
				deposit.setUserBank(new BankCard());
			}
			orderList.add(deposit);
		}
		page.setResult(orderList);
		return page;
	}

	
	public int updateRemark(Long id, String remark) throws Exception {
		FundManualDepositVO entity = new FundManualDepositVO();
		entity.setId(id);
		entity.setMemo(remark);
    	sqlSessionTemplate.update("updateDemo", entity);
		return 0;
	}

}
