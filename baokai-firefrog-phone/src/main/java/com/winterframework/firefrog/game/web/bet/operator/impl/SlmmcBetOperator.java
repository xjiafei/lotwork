package com.winterframework.firefrog.game.web.bet.operator.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.winterframework.firefrog.game.web.dto.DynamicConfig;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;
import com.winterframework.firefrog.game.web.dto.GetAwardResultDTO;
import com.winterframework.firefrog.game.web.dto.HistoryBallsResultDTO;
import com.winterframework.firefrog.game.web.dto.NumberFrequencyCell;
import com.winterframework.firefrog.game.web.dto.OrdersStrucDTO;
import com.winterframework.firefrog.game.web.dto.PlansStrucForUI;

public class SlmmcBetOperator extends AbstractBetOperator {

	@Override
	public String show(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLotteryConfig(Model model) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DynamicConfig getDynamicConfig() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlansStrucForUI> getUserPlans() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameBetJsonResultStruc getCancelOrderCharge(String amount)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameBetJsonResultStruc save(String data, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistoryBallsResultDTO getTrendChart(String type) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GetAwardResultDTO getBetAward(String type, String extent,
			Integer line, Integer lenth) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<NumberFrequencyCell>> frequency(String gameMode,
			String extent, String frequencyType, Integer line, Integer lenth)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object indexInit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object queryGameUserAwardGroupByLotteryId() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object saveProxyBetGameAwardGroup(Long awardGroupId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getUserBal() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OrdersStrucDTO> getUserOrders() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
