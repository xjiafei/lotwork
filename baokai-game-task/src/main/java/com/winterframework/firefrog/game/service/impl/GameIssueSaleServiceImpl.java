package com.winterframework.firefrog.game.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.entity.GameOrder.OrderStatus;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameSlipStatus;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameIssueSaleService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

@Service("gameIssueSaleServiceImpl")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameIssueSaleServiceImpl implements IGameIssueSaleService {
	private static final Logger log = LoggerFactory.getLogger(GameIssueSaleServiceImpl.class);

	@Resource(name = "gameSlipDaoImpl")
	private IGameSlipDao gameSlipDao;

	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao gameReturnPointDao;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService gameSeriesConfigService;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;

	@Override
	public void undo(Long lotteryId, Long issueCode) throws Exception {
		undo(lotteryId, issueCode, null);
	}

	@Override
	public void undo(Long lotteryId, Long issueCode, Date saleTime) throws Exception {

		try {
			//撤销返点金额，冻结金额以及撤单手续费
			undoRiskAmount(lotteryId, issueCode, saleTime);

			//撤销订单，注单
			int orderStatus = OrderStatus.CANCEL.getValue();
			gameOrderService.undoRedoGameOrders(lotteryId, issueCode, saleTime, orderStatus);
			int aimStatus = GameSlipStatus.CANCEL.getValue();
			gameSlipDao.undoGameSlip(lotteryId, issueCode, saleTime, aimStatus);

			//撤销返点
			gameReturnPointDao.updateGameRetunPointByLotteryInfo(lotteryId, issueCode, saleTime);

		} catch (Exception e) {
			log.error("撤销奖期错误：issueCode=" + issueCode + "lotteryId=" + lotteryId + "saleTime=" + saleTime, e);
			throw e;
		}
	}

	/** 
	* @Title: undoRiskAmount 
	* @Description: 撤销返点金额，冻结金额以及撤单手续费
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	* @throws Exception
	*/
	private void undoRiskAmount(Long lotteryId, Long issueCode, Date saleTime) throws Exception {

		List<TORiskDTO> resultList = undoFreezerOrderAmount(lotteryId, issueCode, saleTime);
		resultList.addAll(undoRetPointAmount(lotteryId, issueCode, saleTime));
		resultList.addAll(undoCancelOrderCharge(lotteryId, issueCode, saleTime));

		//调用风控资金撤销接口
		fundRiskService.cancelFee(resultList);
	}

	/** 
	* @Title: undofreezerOrderAmount 
	* @Description: 根据彩种id和奖期获取需要解冻的金额(如果是普通方案则金额为order的totalamount，如果为追号方案，则为对应的packageAmount)
	*/
	private List<TORiskDTO> undoFreezerOrderAmount(Long lotteryId, Long issueCode, Date saleTime) throws Exception {
		List<Integer> aimStatus = new ArrayList<Integer>();
		aimStatus.add(OrderStatus.WAITING.getValue());
		aimStatus.add(OrderStatus.UN_PRIZE.getValue());
		aimStatus.add(OrderStatus.PRIZE.getValue());
		aimStatus.add(OrderStatus.ARCHIVING.getValue());
		aimStatus.add(OrderStatus.ERROR.getValue());
		List<GameOrder> gameOrderList = gameOrderService.getGameOrderByLotteryInfo(lotteryId, issueCode, aimStatus,
				saleTime);
		List<TORiskDTO> riskDTOList = new ArrayList<TORiskDTO>();
		for (GameOrder gameOrder : gameOrderList) {

			TORiskDTO unfreezerOrderDTO = new TORiskDTO();
			unfreezerOrderDTO.setAmount(gameOrder.getTotamount() + "");
			unfreezerOrderDTO.setIssueCode(gameOrder.getIssueCode());
			unfreezerOrderDTO.setLotteryid(gameOrder.getLotteryid());
			unfreezerOrderDTO.setOrderCodeList(gameOrder.getOrderCode());
			unfreezerOrderDTO.setType(GameFundTypesUtils.GAME_USER_CANCEL_BET_UNFREEZER_DETEAIL_TYPE);
			unfreezerOrderDTO.setUserid(gameOrder.getUserid() + "");
			riskDTOList.add(unfreezerOrderDTO);
		}
		return riskDTOList;
	}

	/** 
	* @Title: undofreezerOrderAmount 
	* @Description: 根据彩种id和奖期撤销返点金额
	*/
	private List<TORiskDTO> undoRetPointAmount(Long lotteryId, Long issueCode, Date saleTime) throws Exception {
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		List<GameRetPoint> retPointList = gameReturnPointDao.getRetPointByLotteryInfo(lotteryId, issueCode, saleTime);
		for (GameRetPoint retPoint : retPointList) {
			TORiskDTO retPointDTO = new TORiskDTO();
			retPointDTO.setAmount(retPoint.getRetPointChain());
			retPointDTO.setIssueCode(retPoint.getIssueCode());
			retPointDTO.setLotteryid(retPoint.getLotteryId());
			retPointDTO.setOrderCodeList(retPoint.getOrderCode());
			retPointDTO.setType(GameFundTypesUtils.GAME_CANCEL_RET_DETEAIL_TYPE);
			retPointDTO.setUserid(retPoint.getRetUserChain());
			toRiskDTOList.add(retPointDTO);
		}
		return toRiskDTOList;
	}

	/** 
	* @Title: undoCancelOrderCharge 
	* @Description:根据彩种id和奖期获取撤单手续费
	* @param lotteryId
	* @param issueCode
	* @param saleTime
	 * @throws Exception 
	*/
	private List<TORiskDTO> undoCancelOrderCharge(Long lotteryId, Long issueCode, Date saleTime) throws Exception {
		GameSeriesConfigEntity gse = gameSeriesConfigService.queryGameSeriesConfigByLotteryId(lotteryId);
		List<Integer> aimStatus = new ArrayList<Integer>();
		aimStatus.add(OrderStatus.WAITING.getValue());
		aimStatus.add(OrderStatus.UN_PRIZE.getValue());
		aimStatus.add(OrderStatus.PRIZE.getValue());
		aimStatus.add(OrderStatus.ARCHIVING.getValue());
		aimStatus.add(OrderStatus.ERROR.getValue());
		List<GameOrder> gameOrderList = gameOrderService.getGameOrderByLotteryInfo(lotteryId, issueCode, aimStatus,
				saleTime);
		List<TORiskDTO> toRiskDTOList = new ArrayList<TORiskDTO>();
		for (GameOrder gameOrder : gameOrderList) {
			Double amount = getCancelOrderCharge(gse, Double.valueOf(gameOrder.getTotamount()));
			if (amount > 0) {
				//撤单手续费DTO生成
				TORiskDTO cancelFeeDTO = new TORiskDTO();
				cancelFeeDTO.setAmount(amount.longValue() + "");
				cancelFeeDTO.setIssueCode(gameOrder.getIssueCode());
				cancelFeeDTO.setLotteryid(gameOrder.getLotteryid());
				cancelFeeDTO.setOrderCodeList(gameOrder.getOrderCode());
				cancelFeeDTO.setType(GameFundTypesUtils.GAME_CANCEL_FEE_DETEAIL_TYPE);
				cancelFeeDTO.setUserid(gameOrder.getUserid() + "");
				toRiskDTOList.add(cancelFeeDTO);
			}
		}
		return toRiskDTOList;
	}

	public Double getCancelOrderCharge(GameSeriesConfigEntity gse, Double totalBetAmount) throws Exception {
		if (gse == null) {
			return 0.00D;
		} else {
			if (totalBetAmount.longValue() <= gse.getBackoutStartFee().longValue()) {
				return 0.00D;
			} else {
				DecimalFormat df = new DecimalFormat(".00");
				//后台撤单时获取撤单手续费，因为金额和撤单率 都*10000 因此需要除以一个10000
				return Double.valueOf(df.format(totalBetAmount * gse.getBackoutRatio() / 10000.00));
			}
		}
	}
}
