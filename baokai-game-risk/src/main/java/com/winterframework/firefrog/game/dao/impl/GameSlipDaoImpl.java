package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.common.util.FileUtil;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameSlip;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameSlipDaoImpl")
public class GameSlipDaoImpl extends BaseIbatis3Dao<GameSlip> implements IGameSlipDao {

	@Resource(name = "fileUtil")
	private FileUtil fileUtil;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao gameOrderDao;



	@Override
	public List<GameSlip> querySlipByLotteryIdAndIssueCode(Long lotteryId, Long issueCode) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("issueCode", issueCode);

		return this.sqlSessionTemplate.selectList("querySlipByLotteryIdAndIssueCode", map);
	}

	@Override
	public List<GameSlip> querySlipByOrder(Long orderId) throws Exception {
		return this.sqlSessionTemplate.selectList("getSlipsByOrderId", orderId);
	}

	@Override
	public void updateSlipByOrderID(Long orderID, Integer status) throws Exception {
		Map<String, Object> slipMap = new HashMap<String, Object>();

		slipMap.put("status", status);
		slipMap.put("orderId", orderID);

		this.sqlSessionTemplate.update("updateGameSlipByOrderId", slipMap);
	}

	@Override
	public String queryOrderCodeBySlipID(Long id) throws Exception {
		return this.sqlSessionTemplate.selectOne("queryOrderCodeBySlipID", id);
	}

	@Override
	public void revocationGameSlipsByOrderId(Long orderId, int status) {
		Map<String, Object> slipMap = new HashMap<String, Object>();

		slipMap.put("status", status);
		slipMap.put("orderId", orderId);
		slipMap.put("cancelModes", 2);
		slipMap.put("cancelTime", new Date());

		this.sqlSessionTemplate.update("revocationGameSlipsByOrderId", slipMap);
	}

	@Override
	public void undoGameSlip(Long lotteryId, Long issueCode, Date saleTime, int aimStatus) {
		Map<String, Object> orderMap = new HashMap<String, Object>();
		orderMap.put("lotteryId", lotteryId);
		orderMap.put("issueCode", issueCode);
		if (saleTime != null) {
			orderMap.put("saleTime", saleTime);
		}
		orderMap.put("status", aimStatus);
		this.sqlSessionTemplate.update(getQueryPath("undoGameSlip"), orderMap);
	}
	@Override
	public int changeStatus(Long orderId, Integer fromStatus, Integer toStatus) { 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("fromStatus", fromStatus);
		map.put("toStatus", toStatus);  
		return this.sqlSessionTemplate.update(getQueryPath("changeStatus"), map); 
	}
}
