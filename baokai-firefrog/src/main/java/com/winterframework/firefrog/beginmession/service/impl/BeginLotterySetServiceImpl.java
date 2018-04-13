package com.winterframework.firefrog.beginmession.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.winterframework.firefrog.beginmession.dao.BeginLotterySetDao;
import com.winterframework.firefrog.beginmession.dao.vo.BeginLotterySet;
import com.winterframework.firefrog.beginmession.enums.BeginMissionEnum;
import com.winterframework.firefrog.beginmession.service.BeginLotterySetService;
import com.winterframework.firefrog.beginmession.utils.BeginMissionUtils;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetRequest;
import com.winterframework.firefrog.beginmession.web.dto.BeginLotterySetResponse;
import com.winterframework.firefrog.common.util.DateUtils;

@Service
@Transactional
public class BeginLotterySetServiceImpl implements BeginLotterySetService{

	
	@Autowired
	private BeginLotterySetDao beginLotterySetDao;
	
	
	private final int lotterySize = 3;
	
	@Override
	public void insert(BeginLotterySetRequest dto) throws Exception {
		
		List<BeginLotterySet> beginLotterySets = dto.getBeginLotterySets();
		final Long maxVersion = beginLotterySetDao.getMaxVersion();
		final String userAccount = dto.getUserName();
		
		List<BeginLotterySet> lotterySets = new ArrayList<BeginLotterySet>();
		
		for(BeginLotterySet set:beginLotterySets){
			set.setVersion(maxVersion+1);
			set.setCreateUser(userAccount);
			set.setModifyUser(userAccount);
			set.setCreateTime(DateUtils.currentDate());
			set.setModifyTime(DateUtils.currentDate());
			lotterySets.add(BeginMissionUtils.convertInsertAmtField(set));
		}
		
		Optional<List<BeginLotterySet>> lotterySetOptional= beginLotterySetDao.findMaxVersion();
		beginLotterySetDao.insert(lotterySets);
		BeginMissionUtils.logBeginMissionPageManyRow(lotterySetOptional, lotterySets, BeginMissionEnum.LogType.LOTTERY_SET, userAccount, BeginLotterySet.class);
	}

	@Override
	public BeginLotterySetResponse selectMaxVersion()
			throws Exception {
		BeginLotterySetResponse reponse = new BeginLotterySetResponse();
		Optional<List<BeginLotterySet>> lotterySetOptional = beginLotterySetDao.findMaxVersion();
		reponse.setBeginLotterySets(BeginMissionUtils.createPageList(lotterySetOptional,lotterySize,BeginLotterySet.class));
		return reponse;
	}


}
