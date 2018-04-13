package com.winterframework.firefrog.game.fund.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.fund.bean.GameRiskFund;
import com.winterframework.firefrog.game.fund.dao.IGameRiskFundDao;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameRiskFundDaoImpl")
public class GameRiskFundDaoImpl extends BaseIbatis3Dao<GameRiskFund> implements IGameRiskFundDao {

	@Override
	public void saveRiskFund(TORiskDTO dto, String[] users, String[] amouts, int Status) throws Exception {

		log.trace("进入保存风控资金风险表方法。");
		
		if(null == dto){
			log.error("saveGameRiskFund() 参数 dto 为NULL。");
			throw new RuntimeException("ToRiskDTO不能为NULL");
		}

		for (int i = 0; i < users.length; i++) {

			//save GameRiskFund
			GameRiskFund fund = new GameRiskFund();
			fund.setCancelStatus(0L); //未撤销
			fund.setCreateTime(new Date());
			fund.setFundType(new Long(dto.getType()));
			fund.setIssueCode(dto.getIssueCode());
			fund.setLotteryid(dto.getLotteryid());
			if(StringUtils.isNotBlank(dto.getOrderCodeList())){
				fund.setOrderCode(dto.getOrderCodeList());
			}
			if(StringUtils.isNotBlank(dto.getPlanCodeList())){
				fund.setPlanCode(dto.getPlanCodeList());
			}
			fund.setStatus(new Long(Status)); 
			fund.setUserid(Long.valueOf(users[i]));
			fund.setAmount(Long.valueOf(amouts[i]));
			
			log.trace("save Game_Risk_FUND user="+ users[i]);
			log.trace("save Game_Risk_FUND amount="+ amouts[i]);
			insert(fund);
		}
		
	}

	@Override
	public void saveRiskFund(Long userid, Long amout, int status,String orderCode, Long lotteryId,Long isuueCode, int type)throws Exception{
		
		GameRiskFund fund = new GameRiskFund();
		fund.setCancelStatus(0L); //未撤销
		fund.setCreateTime(new Date());
		fund.setFundType(new Long(type));
		fund.setIssueCode(isuueCode);
		fund.setLotteryid(lotteryId);
		fund.setOrderCode(orderCode);
		fund.setStatus(new Long(status)); 
		fund.setUserid(userid);
		fund.setAmount(amout);

		insert(fund);
	}

	@Override
	public List<GameRiskFund> getGameRiskFundByOrderCode(String orderCode) {
		
		GameRiskFund fund = new GameRiskFund();
		fund.setOrderCode(orderCode);
		fund.setFundType(new Long(GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE));
		
		List<GameRiskFund> list = getAllByEntity(fund);
		return list;
	}
}
