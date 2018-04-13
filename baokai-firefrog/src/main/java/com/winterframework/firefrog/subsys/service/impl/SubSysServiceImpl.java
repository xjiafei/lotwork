package com.winterframework.firefrog.subsys.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.dao.IBankCardDao;
import com.winterframework.firefrog.fund.dao.IFundWithdrawDao;
import com.winterframework.firefrog.subsys.dao.ISubSystemDao;
import com.winterframework.firefrog.subsys.service.ISubSysService;
import com.winterframework.firefrog.subsys.vo.SubSystem;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityBankCard;
import com.winterframework.firefrog.subsys.web.dto.SubSysActivityWithdraw;
import com.winterframework.firefrog.subsys.web.dto.SubSysUserStrucResponse;
import com.winterframework.firefrog.user.dao.IGameAwardUserGroupDao;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupDTO;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupVo;


@Transactional(rollbackFor = Exception.class)
@Service("subSysServiceImpl")
public class SubSysServiceImpl implements ISubSysService {

	private static Logger logger = LoggerFactory.getLogger(SubSysServiceImpl.class);
    
    @Resource(name = "SubSystemDaoImpl")
	private ISubSystemDao subSystemDao;
    
    @Resource(name = "gameAwardUserGroupDaoImpl")
	private IGameAwardUserGroupDao gameAwardUserGroupDao;

    @Resource(name = "bankCardDaoImpl")
	private IBankCardDao bankCardDao;
    
    @Resource(name = "fundWithdrawDaoImpl")
	private IFundWithdrawDao fundWithdrawDao;
    
    @Override
	public Boolean validateToken(String token) throws Exception {
		if (token  != null){
			SubSystem subRequest = new SubSystem();
			subRequest.setToken(token);
			SubSystem subRespone = subSystemDao.getName(subRequest);
			
			if (subRespone == null)
				return false;
		}
		return true;
	}

	@Override
	public List<GameAwardUserGroupVo> queryUserAward(Long userId ,Long lotteryId)throws Exception{
		GameAwardUserGroupDTO dto = new GameAwardUserGroupDTO();
		dto.setLotteryId(lotteryId);
		dto.setUserId(userId);
		List<GameAwardUserGroupVo> result = gameAwardUserGroupDao.queryGameAwardGruopByUserId(dto);
		return result;
	}
	
	@Override
	public SubSysUserStrucResponse getNewUserBankList(Integer isNewUser, SubSysUserStrucResponse result)throws Exception{
		
		if(isNewUser != null && isNewUser == 1){
			Long userId = result.getId();
			
			//List<BankCard> bankLists = bankCardDao.getBoundBankCard(userId, null);
			List<String> bankList = bankCardDao.getBankCardNotSuspicious(userId);		
			
			List<SubSysActivityWithdraw> withdrawList = fundWithdrawDao.queryWithdrawFHXList(userId);
			logger.info("withdrawList size : " + withdrawList.size());
			List<SubSysActivityBankCard> bList = new ArrayList<SubSysActivityBankCard>();
					
			SubSysActivityBankCard bModel;
			for(String cardnumber : bankList){
				bModel = new SubSysActivityBankCard();
				bModel.setBankNumber(cardnumber);
				bList.add(bModel);
			}
			result.setBankList(bList);
			result.setWithdrawList(withdrawList);
		}else{
			result.setBankList(new ArrayList<SubSysActivityBankCard>());
			result.setWithdrawList(new ArrayList<SubSysActivityWithdraw>());
		}
		
		return result;
	}
}
