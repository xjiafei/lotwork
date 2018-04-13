package com.winterframework.firefrog.game.web.bet.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.web.bet.convertor.IBetNameConvertor;
import com.winterframework.firefrog.game.web.dto.BetDetailStrucDTO;
import com.winterframework.firefrog.game.web.dto.BlockadeResponseDTO;
import com.winterframework.firefrog.game.web.dto.GameBetJsonDateStruc;
import com.winterframework.firefrog.game.web.dto.GameBetJsonResultStruc;
import com.winterframework.firefrog.game.web.dto.GameBlockadeInfo;
import com.winterframework.firefrog.game.web.dto.GameOrderRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GameSlipResponseDTO;
import com.winterframework.firefrog.game.web.dto.LockPointRequestDTO;
import com.winterframework.firefrog.game.web.dto.PointsRequestDTO;
import com.winterframework.firefrog.game.web.util.GameAwardNameUtil;

/** 
* @ClassName: LockFacadeUtils 
* @Description: 封锁变价的封装类,封装界面渲染需要的结构体 
* @author 你的名字 
* @date 2014-6-17 下午12:28:39 
*  
*/
public class LockFacadeUtils {

	/** 
	* @Title: facadeGameOrderStruc 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param struc
	* @param gameOrder
	* @param gameDTO
	* @param account
	* @param convert
	 * @param lotteryId 
	*/
	public static void facadeGameOrderStruc(GameBetJsonResultStruc struc, GameOrderResponseDTO gameOrder,
			GameOrderRequestDTO gameDTO, String account, IBetNameConvertor convert, Long lotteryId) {
		GameBetJsonDateStruc jsonDate = struc.getData();
		facadeBlockadeStruc(jsonDate, gameOrder, gameDTO, account , lotteryId);
		GameOrderRequestDTO orderRequeset = facadeGameOrderStruc(gameOrder, gameDTO.getIsTrace(), gameDTO.getGameType());
		jsonDate.setOrderData(orderRequeset);
		List<BetDetailStrucDTO> balls = new ArrayList<BetDetailStrucDTO>();
		orderRequeset.setBalls(balls);
		facadeBetDetailStru(gameOrder, balls, convert, lotteryId);
		facadeGamePlanParam(orderRequeset, gameOrder, jsonDate);
	}

	public static void facadeGamePlanStruc(GameBetJsonResultStruc struc, GameOrderResponseDTO gameOrder,
			GameOrderRequestDTO gameDTO, String account, IBetNameConvertor convert, Long lotteryId) {

		GameBetJsonDateStruc jsonDate = struc.getData();
		facadeBlockadeStruc(jsonDate, gameOrder, gameDTO, account , lotteryId);
		GameOrderRequestDTO orderRequeset = facadeGameOrderStruc(gameOrder, gameDTO.getIsTrace(), gameDTO.getGameType());
		jsonDate.setOrderData(orderRequeset);
		List<BetDetailStrucDTO> balls = new ArrayList<BetDetailStrucDTO>();
		orderRequeset.setBalls(balls);

		for (GameSlipResponseDTO dto : gameOrder.getSlipResonseDTOList()) {

			GameOrderResponseDTO gameOrderTemp = new GameOrderResponseDTO();
			List<GameSlipResponseDTO> list = new ArrayList<GameSlipResponseDTO>();
			list.add(dto);
			gameOrderTemp.setSlipResonseDTOList(list);
			facadeBetDetailStru(gameOrderTemp, balls, convert, lotteryId);
		}

		facadeGamePlanParam(orderRequeset, gameOrder, jsonDate);
	}

	private static void facadeBlockadeStruc(GameBetJsonDateStruc jsonDate, GameOrderResponseDTO gameOrder,
			GameOrderRequestDTO gameDTO, String account ,Long lotteryId) {
		GameBlockadeInfo blockadeInfo = new GameBlockadeInfo();
		Long adjust = 0L;

		//		blockadeInfo.setAmountAdjust(gameOrder.getTotalAmount()/10000L);
		for (GameSlipResponseDTO dto : gameOrder.getSlipResonseDTOList()) {
			//若六合彩有一個slip為0,則先完全封鎖
			if(lotteryId==99701 && dto.getTotalAmount()==0){
				adjust = 0l;
				break;
			}
			//最终订单金额
			adjust += dto.getTotalAmount();
		}
		blockadeInfo.setAmountAdjust((gameOrder.getTotalAmount() - adjust) / 10000);
		gameOrder.setTotalAmount(adjust);
		blockadeInfo.setType(3);
		blockadeInfo.setUsername(account);
		jsonDate.getTplData().setCurrentGameNumber(gameOrder.getWebIssueCode().toString());
		jsonDate.setBlockadeInfo(blockadeInfo);
	}

	private static GameOrderRequestDTO facadeGameOrderStruc(GameOrderResponseDTO gameOrder, int isTrace,
			String lotteryType) {
		GameOrderRequestDTO requestDTO = new GameOrderRequestDTO();
		requestDTO.setAmount(String.valueOf(new BigDecimal(gameOrder.getTotalAmount()).divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
		requestDTO.setGameType(lotteryType);
		requestDTO.setIsFirstSubmit(1);
		requestDTO.setAwardGroupId(gameOrder.getAwardGroupId());
		//如果是追号设置，设置停止参数已经停止值
		if (isTrace == 1) {
			requestDTO.setIsTrace(1);
			if (gameOrder.getStopMode().intValue() == 1) {//中奖即停
				requestDTO.setTraceWinStop(2);
			} else if (gameOrder.getStopMode().intValue() == 2) {//盈利停止
				requestDTO.setTraceWinStop(1);
			} else {//默认为中奖不停止
				requestDTO.setTraceWinStop(0);
			}
			requestDTO.setTraceStopValue(gameOrder.getStopParms().intValue() / 10000);
		} else {
			requestDTO.setIsTrace(0);
			requestDTO.setTraceStopValue(-1);
			requestDTO.setTraceWinStop(0);
		}
		return requestDTO;
	}

	/** 
	* @Title: facadeBetDetailStru 
	* @Description: 封装投注注单的结构体 
	*/
	private static void facadeBetDetailStru(GameOrderResponseDTO gameOrder, List<BetDetailStrucDTO> balls,
			IBetNameConvertor convert, Long lotteryId) {
		List<GameSlipResponseDTO> slipList = gameOrder.getSlipResonseDTOList();
		//投注注单
		int idIndex = 1;
		for (GameSlipResponseDTO gameSlip : slipList) {
			BetDetailStrucDTO dto = new BetDetailStrucDTO();
			dto.setBall(gameSlip.getBetDetail());
				
			if(lotteryId == 99701){
				dto.setAmount(gameSlip.getTotalAmount()/gameSlip.getTotalBet()/10000);
				dto.setOdds(new Double(gameSlip.getLockPoints().getBeishu())/10000);
			}
			dto.setId(idIndex);
			dto.setMoneyunit(gameSlip.getMoneyMode().equals(MoneyMode.FEN)?0.01:(gameSlip.getMoneyMode().equals(MoneyMode.JIAO)?0.1:1));
			dto.setNum(gameSlip.getTotalBet().intValue());
			GameBetType betType = gameSlip.getGameBetType();
			dto.setType(GameAwardNameUtil.getFullName(lotteryId, betType.getGameGroupCode(), betType.getGameSetCode(),
					betType.getBetMethodCode()));
			dto.setCurr(gameSlip.getCurr() == null ? 1 : gameSlip.getCurr());
			dto.setIssueCode(gameSlip.getIssueCode());
			dto.setMultiple(gameSlip.getMultiple());
			if (null != gameSlip.getLockPoints()) {
				facadeLockPointStruc(dto, gameSlip);
			}
			dto.setOnePrice(gameSlip.getMoneyMode().equals(MoneyMode.FEN)?20L:(gameSlip.getMoneyMode().equals(MoneyMode.YUAN) ? 2000l : 200));

			balls.add(dto);
			//重新设置订单金额，按照每个朱丹的金额累加起来
			gameOrder.setTotalAmount(gameOrder.getTotalAmount()==null?0L:gameOrder.getTotalAmount() + dto.getMultiple() * dto.getOnePrice()
					* dto.getNum().longValue());
			idIndex++;
		}
	}

	/** 
	* @Title: facadeLockPointStruc 
	* @Description: 封装封锁变价的结构体 
	*/
	private static void facadeLockPointStruc(BetDetailStrucDTO dto, GameSlipResponseDTO slip) {
		//封锁变价结构
		LockPointRequestDTO lockPoint = new LockPointRequestDTO();

		LockPoint lockPointSource = slip.getLockPoints();
		List<PointsRequestDTO> pointsList = new ArrayList<PointsRequestDTO>();
		List<BlockadeResponseDTO> blockadeList = new ArrayList<BlockadeResponseDTO>();
		if (lockPointSource != null) {
			//如果有变价
			if (lockPointSource.getIsChange() != null && lockPointSource.getIsChange()) {
				//变价的结构体
				for (Points tempPoints : lockPointSource.getPoints()) {
					PointsRequestDTO pointDTO = new PointsRequestDTO(tempPoints);
					pointsList.add(pointDTO);
				}
			}
			//有封锁
			if (lockPointSource.getLocks() != null && lockPointSource.getIsLocks()) {
				for (Entry<String, Long> lockMapEntry : lockPointSource.getLocks().entrySet()) {
					BlockadeResponseDTO blockade = new BlockadeResponseDTO();
					blockade.setBeishu(slip.getMultiple().longValue());
					blockade.setRealBeishu(lockMapEntry.getValue());
					blockade.setBlockadeDetail(lockMapEntry.getKey());
					for (Points tempPoints : lockPointSource.getPoints()) {
						blockade.setAfterAmount((tempPoints.getAfterMoney() == null ? 0L : tempPoints.getAfterMoney()) / 10000L);
						blockade.setBeforeAmount((tempPoints.getBeforeMoney() == null ? 0L : tempPoints.getBeforeMoney()) / 10000L);
					}
					
					blockadeList.add(blockade);
				}
			}
		}
		if (null == slip.getLockPoints()) {
			lockPoint.setBeishu(slip.getMultiple().longValue());
		} else {
			lockPoint.setBeishu(slip.getLockPoints().getBeishu());
		}
		lockPoint.setBeforeBlockadeList(blockadeList);
		lockPoint.setPointsList(pointsList);
		dto.setLockPoint(lockPoint);
	}

	/** 
	* @Title: facadeGamePlanParam 
	* @Description: 封装追号参数信息(追号单)
	*/
	private static void facadeGamePlanParam(GameOrderRequestDTO orderRequeset, GameOrderResponseDTO gameOrder,
			GameBetJsonDateStruc jsonDate) {
		orderRequeset.setOrders(gameOrder.getGamePlanParm());
	}
}
