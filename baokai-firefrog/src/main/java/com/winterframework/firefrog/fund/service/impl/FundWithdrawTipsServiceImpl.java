package com.winterframework.firefrog.fund.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.fund.dao.IFundWithdrawTipsDao;
import com.winterframework.firefrog.fund.dao.vo.FundWithdrawTips;
import com.winterframework.firefrog.fund.enums.FundLogEnum;
import com.winterframework.firefrog.fund.enums.FundLogEnum.TipsGroupB;
import com.winterframework.firefrog.fund.service.IFundWithdrawTipsService;
import com.winterframework.firefrog.fund.web.dto.FundWithdrawTipsResponse;

@Service("fundWithdrawTipsServiceImpl")
@Transactional
public class FundWithdrawTipsServiceImpl implements IFundWithdrawTipsService {

	private static final Logger log = LoggerFactory.getLogger(FundWithdrawTipsServiceImpl.class); 
	
	@Resource(name="fundWithdrawTipsDaoImpl")
	private IFundWithdrawTipsDao tipsDao;

	@Override
	public List<FundWithdrawTips> searchTipsByCondition(FundWithdrawTips tips) {
		return tipsDao.getTips(tips);
	}

	@Override
	public void deleteTipsAndAddTipsList(List<FundWithdrawTips> tipsList,FundWithdrawTips deleteCondition) throws Exception {
		log.debug(" into FundWithdrawTipsServiceImpl addTips");
		
		log.debug(deleteCondition.toString());
		
		
		if(!Optional.of(deleteCondition).isPresent()){
			throw new Exception("delete Condition is null");
		}
		tipsDao.deleteByCondition(deleteCondition);
		
		
		tipsDao.insert(tipsList);
		
		log.debug(" tips insert success");
	}
	
	@Override
	public void insertTip(FundWithdrawTips tip) throws Exception {
		tipsDao.insert(tip);
	} 
	
	@Override
	public Integer getGroupBCount(FundWithdrawTips tips) {
		return tipsDao.getGroupBCount(tips);
	}
	
	@Override
	public FundWithdrawTipsResponse processDrawTipsData(
			FundWithdrawTipsResponse response,
			List<FundWithdrawTips> uncheckDrawTips,
			List<FundWithdrawTips> reviewDrawTips) {
		
		//通過
		response.setReviewDrawPassTips(getFilterTips(reviewDrawTips , TipsGroupB.PASS));
		//未通過
		response.setReviewDrawUnpassTips(getFilterTips(reviewDrawTips , TipsGroupB.UNPASS));
		//待複審
		response.setReviewDrawRecheckTips(getFilterTips(reviewDrawTips , TipsGroupB.UNDER_CHEKC));

		response.setUncheckDrawPassTips(getFilterTips(uncheckDrawTips , TipsGroupB.PASS));
		
		response.setUncheckDrawUnpassTips(getFilterTips(uncheckDrawTips , TipsGroupB.UNPASS));
		
		response.setUncheckDrawRecheckTips(getFilterTips(uncheckDrawTips , TipsGroupB.UNDER_CHEKC));
		return response;
	}
	
	
	private	List<FundWithdrawTips> getFilterTips(List<FundWithdrawTips> list,final TipsGroupB group){
		//by pass
		List<FundWithdrawTips>  filterTips = Lists.newCopyOnWriteArrayList(Collections2.filter(list, new Predicate<FundWithdrawTips>() {
			@Override
			public boolean apply(FundWithdrawTips tips) {
				if(group.getCode().equals(tips.getTipsGroupb())){
					return true;					
				}
				return false;
			}
		}));
		
		return filterTips;
	}
	

}
