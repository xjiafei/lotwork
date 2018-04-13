package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.IGameNumberConfigDao;
import com.winterframework.firefrog.game.dao.ISlipItemAssistDao;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem.AwardMode;
import com.winterframework.firefrog.game.entity.BetMethodDescription;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.ItemAssist;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.entity.SlipItemAssist;
import com.winterframework.firefrog.game.enums.GameLockPlayType;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PontStatus;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.bet.SpecialLotteryAssistFactory;
import com.winterframework.firefrog.game.service.order.utlis.GameAssistProcess;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;
import com.winterframework.modules.web.util.JsonMapper;

/**
 * 六合彩封鎖Service。<br>
 * 目前無變價。
 * @author Ami.Tsai
 */
@Service("lhcLockService")
public class LHCLockService extends LotteryLockService{
	
	private static final Logger log = LoggerFactory.getLogger(LHCLockService.class);
	
	@Resource(name="gameNumberConfigDaoImpl")
	private IGameNumberConfigDao gameNumberConfigDao;
	
	@Resource(name = "specialLotteryAssistFactory")
	private SpecialLotteryAssistFactory specialLotteryAssistFactory;
	
	@Resource(name = "gameAssistProcess")
	private GameAssistProcess gameAssistProcess;
	@Resource(name = "gameUserAwardGroupServiceImpl")
	private IGameUserAwardGroupService gameUserAwardGroupService;
	@Resource(name="slipItemAssistDaoImpl")
	private ISlipItemAssistDao slipItemAssistDao;
	
	@Override
	public boolean haveNoLockAndChange(GameOrder gameOrder, Long userId) throws Exception {

		Long _lotteryId = gameOrder.getLottery().getLotteryId();
		Long _issueCode = gameOrder.getGameIssue().getIssueCode();
		log.debug("进行封锁变价，lotteryId=" + _lotteryId + ",userId=" + userId + ",issueCode=" + _issueCode);

		if (!checkLotteryId(_lotteryId)) {
			return false;
		}

		// 获取封锁变价配置信息
		GameLockEntity _glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(_lotteryId);
		if (null == _glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		/*获取缓存中的LockIssue信息，为空根据GameLockEntity配置信息初始化*/
		//六合彩(特碼玩法)
		LockIssue _lockIssue = null;
		//六合彩(正特碼_一肖玩法)
		LockIssue _lockIssueYixiao = null;
		//六合彩(其他玩法)
		LockIssue _lockIssueOther = null;
		
		_lockIssue = getLockIssue(_glEntity, _lotteryId, _issueCode, GameLockPlayType.TEMA.toString());
		_lockIssueYixiao = getLockIssue(_glEntity, _lotteryId, _issueCode, GameLockPlayType.YIXIAO.toString());
		_lockIssueOther = getLockIssue(_glEntity, _lotteryId, _issueCode, GameLockPlayType.OTHER.toString());

		// 初始化 lockIssueMap  
		Map<String, Object> _lockIssueMap = new HashMap<String, Object>();
		boolean _needLockOrCHANGE = false;

		List<LockIssue> _lockIssueList = new ArrayList<LockIssue>();
		_lockIssueMap.put(LOCK_ISSUE_LIST_KEY, _lockIssueList);
		/*
		 * 检查订单是否封锁和变价
		 * 不管什么情况，只要达到封锁状态，则不能投注
		 * 进行封锁、变价 
		 */
		_needLockOrCHANGE = checkGameOrderIsLock(gameOrder, userId,
				_glEntity,  _lockIssueMap, _lockIssue, _lockIssueYixiao, _lockIssueOther);
		log.info("needLockOrCHANGE:" + _needLockOrCHANGE);
		
		//不需要第二次check 并且可以
		if (!_needLockOrCHANGE ) {
			//如果不需要check，并不需要change
			saveLockIssue2Redis(_lockIssueMap);
			return false;
		}
		return _needLockOrCHANGE;
	}
	
	/**
	 * 檢核訂單是否需要封鎖。
	 * @param gameOrder 訂單
	 * @param userId 用戶ID
	 * @param glEntity 封锁变价配置信息
	 * @param lockIssueMap 記錄要回存 Redis 的 lockIssue，若注單內容已達封鎖條件就無須回存。
	 * @param lockIssue 特碼玩法
	 * @param lockIssueYixiao 正特碼_一肖玩法
	 * @param lockIssueOther 其他玩法
	 * @return true:要封鎖、false:不要封鎖
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private boolean checkGameOrderIsLock(
			GameOrder gameOrder, Long userId, GameLockEntity glEntity, Map<String, Object> lockIssueMap,
			LockIssue lockIssue, LockIssue lockIssueYixiao, LockIssue lockIssueOther) throws Exception {
		Long _lotteryId = gameOrder.getLottery().getLotteryId();
		boolean _isOrderLock = false;
		
		//计算投注金額
		List<GameSlip> _slipList = gameOrder.getSlipList();
		for (GameSlip _slip : _slipList) {
			if (!_slip.getIssueCode().getIssueCode().equals(gameOrder.getGameIssue().getIssueCode())) {
				continue;
			}
			_slip.setLockPoints(LockPoint.getEmptyLockPoint(_slip.getSingleWin()));
			
			//組成封鎖物件
			BetMethodDescription _description = new BetMethodDescription(_lotteryId, 
					_slip.getGameBetType().getGameGroupCode(), _slip.getGameBetType().getGameSetCode(), 
					_slip.getGameBetType().getBetMethodCode());
			List<ItemAssist> _assistList = this.specialLotteryAssistFactory.getSlipItemAssistList(_description, userId);
			if (CollectionUtils.isEmpty(_assistList)) {
				_slip.setMutlAward(0);
			} else {
				List<SlipItemAssist> _slipItemAssists = new ArrayList<SlipItemAssist>();
				for(ItemAssist _itemAssist : _assistList) {
					_itemAssist.setEvaluatAward(gameAssistProcess.getBonus(_itemAssist.getEvaluatAward(), new Long(_slip.getMoneyMode().getValue()), _slip.getMultiple().longValue()));
					if(AwardMode.HIGH.getValue() == _slip.getAwardMode()) {
						_itemAssist.setEvaluatAward(_itemAssist.getEvaluatAward() + _slip.getRetAward());
					}
					_itemAssist.setEvaluatAwardDown(gameAssistProcess.getBonus(_itemAssist.getEvaluatAwardDown(), new Long(_slip.getMoneyMode().getValue()), _slip.getMultiple().longValue()));
					SlipItemAssist _slipItemAssist = new SlipItemAssist(_itemAssist);
					_slipItemAssist.setSlipId(_slip.getId());
					_slipItemAssists.add(_slipItemAssist);
				}
				_slip.setSlipItemAssist(_slipItemAssists);
				_slip.setMutlAward(1);
			}
			SlipAssist _sa = null;
			if(GameLockPlayType.isTEMA(_slip.getGameBetType().getBetTypeCode())) {
				_sa = new SlipAssist(_slip, lockIssue, _lotteryId);
			} else if(GameLockPlayType.isYIXIAO(_slip.getGameBetType().getBetTypeCode())) {
				_sa = new SlipAssist(_slip, lockIssueYixiao, _lotteryId);
			} else {
				_sa = new SlipAssist(_slip, lockIssueOther, _lotteryId);
			}
			dealWithSlipLhc(gameOrder.getLottery().getLotteryId(), userId, _sa);
			
			List<LockIssue> _list = (List<LockIssue>) lockIssueMap.get(LOCK_ISSUE_LIST_KEY);
			if (_sa.getIsLock()) {
				_isOrderLock = true;
				dealWithSlipPointToMinAmount(_slip, _sa);
			} else {
				_list.add(_sa.getLockIssue());
				lockIssueMap.put(LOCK_ISSUE_LIST_KEY, _list);
				lockIssueMap.put(IS_LOCK_KEY, new Boolean(true));
			}
		}
		// 未到达封锁值才保存。
		if (_isOrderLock) {
			lockIssueMap.put(IS_LOCK_KEY, new Boolean(true));
		} else {
			lockIssueMap.put(IS_LOCK_KEY, new Boolean(false));
		}
		
		return _isOrderLock;
	}

	/**
	 * 
	 * @param lotteryId 彩種ID
	 * @param gameOrder 訂單
	 * @param userId 用戶ID
	 * @param slipAssist 注單明細輔助(依據玩法會有三種區分:特碼、正特碼_一肖、其他)
	 * @throws Exception
	 */
	private void dealWithSlipLhc(Long lotteryId, Long userId, SlipAssist slipAssist) throws Exception {
		GameSlip _slip = slipAssist.getGameSlip();
		LockIssue _lockIssue = slipAssist.getLockIssue();
		String _slipBetTypeCode = _slip.getGameBetType().getBetTypeCode();
		
		//封鎖上限
		BigDecimal _limitVal = null;
		if(GameLockPlayType.isTEMA(_slipBetTypeCode)) {
			_limitVal = new BigDecimal(_lockIssue.getUpValue());
		} else if(GameLockPlayType.isYIXIAO(_slipBetTypeCode)) {
			_limitVal = new BigDecimal(_lockIssue.getUpValue2());
		} else {
			_limitVal = new BigDecimal(_lockIssue.getUpValue3());
		}
		
		//影響點(key:號球/生肖;value:投注次數)
		Map<String, Integer> _influencePointMap = super.getInfluncePoint(_slipBetTypeCode, lotteryId, _slip.getBetDetail());
		if(MapUtils.isNotEmpty(_influencePointMap)) {
			Iterator<Map.Entry<String, Integer>> _influencePointEntrys = _influencePointMap.entrySet().iterator();
			Entry<String, Integer> _entry;
			Long _lockCheckValueTmp = 0l;
			
			BigDecimal _base = new BigDecimal(10000);
			_limitVal = _limitVal.divide(_base);
			// 賠率
			BigDecimal _odds = getOdds(_slip).divide(_base);
			// 注單投注金額
			BigDecimal _totalAmount = new BigDecimal(_slip.getTotalAmount()).divide(_base);
			// 預收入金額 = 注單投注金額
			BigDecimal _income = _totalAmount;
			// 總代返點
			BigDecimal _agentRetValue = new BigDecimal(gameReturnPointDao.getRetunPoint(_slip, null)).divide(_base);
			// 玩法返點  = 注單投注金額 * 總代返點
			BigDecimal _retValue = _totalAmount.multiply(_agentRetValue);
			// 預支出金額 = 注單投注金額 * 賠率  + 玩法返點 
			BigDecimal _outlay = _totalAmount.multiply(_odds).add(_retValue);
			
			while (_influencePointEntrys.hasNext()) {
				_entry = _influencePointEntrys.next();
				Map<String, PontStatus> _pointsMap = _lockIssue.getPoints();
				PontStatus _pontStatus = _pointsMap.get(_entry.getKey());
				 
				// 取不到，标识没有该投注的盈虧值
				if (null == _pontStatus) {
					_pontStatus = new PontStatus();
					_pontStatus.setTotal(0L);
				    _pointsMap.put(_entry.getKey(), _pontStatus);
				}
				
				// 兩面的49號球賠率改為 1，並重算預支出金額
				if(BetTypeCodeMapping.lhc_54_37_83.name().contains(_slip.getGameBetType().getBetTypeCode()) && "49".equals(_entry.getKey())) {
					_odds = new BigDecimal(1);
					_outlay =  _totalAmount.multiply(_odds).add(_retValue);
				}
				
				// 目前盈虧值 = 目前總投注金額 - 目前總返點值 + 目前支出金額
				BigDecimal _currentProfirLossValue = new BigDecimal(_lockIssue.getBetTotal()).subtract(new BigDecimal(_lockIssue.getRetPoint())).add(new BigDecimal(_pontStatus.getTotal())).divide(_base);
				// 盈虧值 = 預收入金額 - 預支出金額 + 目前盈虧值
				BigDecimal _profirLossValue = _income.subtract(_outlay).add(_currentProfirLossValue);
				// 封鎖檢核值
				BigDecimal _lockCheckValue = _limitVal.add(_profirLossValue);
				
				Points _points = new Points();
		        _points.setMult(_slip.getMultiple().longValue());
		        //本投注之前盈虧值,最佳化使用
		        _points.setSingle(_lockIssue.getBetTotal() - _lockIssue.getRetPoint() + _pontStatus.getTotal());
		        _points.setCurrPhase(0);
		        _points.setPoint(_entry.getKey());
		        _points.setSingleBet(_lockCheckValue.multiply(_base).longValue());
		        BigDecimal _newAmountVal = _limitVal.add(new BigDecimal(_points.getSingle()).divide(_base)).divide(new BigDecimal(1).subtract(_agentRetValue).subtract(_odds), 0, RoundingMode.UP).abs();
		        _points.setAfterMoney(_newAmountVal.multiply(_base).longValue());
		        _points.setBeforeMoney(_totalAmount.multiply(_base).longValue());
		        _slip.getLockPoints().getPoints().add(_points);
				
				if(_lockCheckValue.longValue() < 0) {
					slipAssist.setIsLock(true);
					_lockCheckValueTmp = (_lockCheckValue.longValue() > _limitVal.negate().longValue() ? _lockCheckValue.multiply(_base) : _limitVal.negate().multiply(_base)).longValue();
					_slip.getLockPoints().getLocks().put(_entry.getKey(), _odds.multiply(_base).longValue());
					//紀錄封鎖號碼
		            _lockIssue.getLockNumbers().add(_entry.getKey());
		            continue;
				} else {
					//若該注單號碼存在本次以封鎖號碼,需同步處理
		            if(_lockIssue.getLockNumbers().contains(_entry.getKey())){
		                slipAssist.setIsLock(true);
		                _slip.getLockPoints().getLocks().put(_entry.getKey(), _odds.multiply(_base).longValue());
		                continue;
		            }
		            //若沒超過封鎖,則將盈虧值(減去玩法返點)保存
		            _pontStatus.setTotal(_outlay.subtract(_retValue).negate().add(new BigDecimal(_pontStatus.getTotal()).divide(_base)).multiply(_base).longValue());
		            slipAssist.getLockIssue().getPoints().put(_entry.getKey(), _pontStatus);
				}
			}
			
			// 沒封鎖要加總本次投注金額及玩法返點存回 redis 內。
			if(!slipAssist.getIsLock()) {
				slipAssist.getLockIssue().setBetTotal(BigDecimal.valueOf(_lockIssue.getBetTotal()).divide(_base).add(_totalAmount).multiply(_base).longValue());
				slipAssist.getLockIssue().setRetPoint(BigDecimal.valueOf(_lockIssue.getRetPoint()).divide(_base).add(_retValue).multiply(_base).longValue());
			} else {
				slipAssist.setLockCheckValue(_lockCheckValueTmp);
			}
		} else {
			throw new NullPointerException("六合彩(" + _slipBetTypeCode + ")未设定投注明细转换号球或生肖的对应档，无法计算封锁。");
		}
	}
	
	/**
	 * 依據玩法取得賠率。<br>
	 * 有輔助玩法資料則取主肖賠率，否則抓取 slip.singleWin。
	 * @param slip 注單明細
	 * @return
	 */
	private BigDecimal getOdds(GameSlip slip) {
		Long _singleWin = null;
		// 判斷是否有輔助玩法，singleWin == 0表示有輔助玩法
		if(null == slip.getSingleWin() || slip.getSingleWin() == 0) {
			// 一般玩法的最高獎金
			Long _maxEvaluatAward = 0L;
			// 主肖玩法的最高獎金
			Long _maxOneYearEvaluatAward = 0L;
			for(SlipItemAssist _sia : slip.getSlipItemAssist()) {
				if(_maxEvaluatAward < _sia.getEvaluatAward()) {
					_maxEvaluatAward = _sia.getEvaluatAward();
				}
				if(_sia.isLhcOnYear() && (_maxEvaluatAward < _sia.getEvaluatAward())) {
					_maxOneYearEvaluatAward = _sia.getEvaluatAward();
				}
			}
			
			_singleWin = _maxOneYearEvaluatAward == 0 ? _maxEvaluatAward : _maxOneYearEvaluatAward;
		} else {
			_singleWin = slip.getSingleWin();
		}
		
		return new BigDecimal(gameAssistProcess.getOdds(_singleWin.longValue(), slip.getMoneyMode(), slip.getMultiple().longValue()));
	}

	/**
	 * 超過封鎖值後須將投注金額調到最小建議值。
	 * @param slip
	 * @param slipAssist
	 * @throws Exception 
	 */
	private void dealWithSlipPointToMinAmount(GameSlip slip, SlipAssist slipAssist) throws Exception {
		LockPoint _lps = slip.getLockPoints();
		LockIssue _lockIssue = slipAssist.getLockIssue();

		//進行盈虧值整理,將盈虧值最大的擺第一順位
		Collections.sort(_lps.getPoints(), new Comparator<Points>() {
			@Override
			public int compare(Points o1, Points o2) {
				if(o1.getSingle() > o2.getSingle()){
					return 1;					
				} else if(o1.getSingle() < o2.getSingle()) {
					return -1;					
				} else {
					return 0;
				}
			}
		});
		Points _ps = _lps.getPoints().get(0);
		Long _preLockValue= _lockIssue.getPreLockValueMap().get(_ps.getPoint());
		if(_preLockValue == null){
			_preLockValue = 0L;
		}
		
		BigDecimal _base = new BigDecimal(10000);
		
		//封鎖上限
		BigDecimal _limitVal = null;
		String _slipBetTypeCode = slip.getGameBetType().getBetTypeCode();
		if(GameLockPlayType.isTEMA(_slipBetTypeCode)) {
			_limitVal = new BigDecimal(_lockIssue.getUpValue());
		} else if(GameLockPlayType.isYIXIAO(_slipBetTypeCode)) {
			_limitVal = new BigDecimal(_lockIssue.getUpValue2());
		} else {
			_limitVal = new BigDecimal(_lockIssue.getUpValue3());
		}
		_limitVal = _limitVal.divide(_base);
		BigDecimal _single = new BigDecimal(_ps.getSingle()).divide(_base);
		BigDecimal _odds = getOdds(slip).divide(_base);
		// 總代返點
		BigDecimal _retValue = new BigDecimal(gameReturnPointDao.getRetunPoint(slip, null)).divide(_base);
		
		/**
		 * 計算封鎖最佳化,(封鎖+投注前盈虧值+本次投注該號碼目前累積盈虧)/(1-返點-賠率) = 可投注金額
		 * 金額無條件捨去，避免超出封鎖上限
		 */
		BigDecimal _newAmountVal = _limitVal.add(_single).add(new BigDecimal(_preLockValue).divide(_base)).divide(new BigDecimal(1).subtract(_retValue).subtract(_odds).negate(), 0, RoundingMode.DOWN);
		if(_newAmountVal.longValue() < 0) {
			_newAmountVal = new BigDecimal(0);
		}
		
		BigDecimal _newTotalAmount = _newAmountVal.divide(new BigDecimal(slip.getTotalBet()), 0, RoundingMode.DOWN).multiply(new BigDecimal(slip.getTotalBet())).multiply(_base);
		// 多注單時，可能別的注單造成本單連鎖封鎖，則需跳過尚可投注金額超過注單總金額者不異動
		if(_newTotalAmount.longValue() >= slip.getTotalAmount()) {
			_ps.setAfterMoney(slip.getTotalAmount());
			return;
		}
		slip.setTotalAmount(_newTotalAmount.longValue());
		_ps.setSingle(_newAmountVal.longValue());
		_ps.setAfterMoney(_newTotalAmount.longValue());
		
		_preLockValue += slipAssist.getLockCheckValue();
		_lockIssue.getPreLockValueMap().put(_ps.getPoint(), _preLockValue);
	}
	
	/**
	 * 用户撤销或系统撤销调用。
	 */
	@Override
	public void undoLock(Long lotteryId, Long issueCode, Long userId, Long orderId) throws Exception {

		log.debug("进行封锁变价的撤销操作： lotteryId=" + lotteryId + ", userId = " + userId + ", issueCode = " + issueCode);

		// 检查当前彩种是否支持封锁变价
		if (!checkLotteryId(lotteryId)) {
			log.debug("彩种【" + lotteryId + "】,暂未支持封锁变价信息，无效回滚。");
			return;
		}

		//GameOrder
		GameOrder _gameOrder = gameOrderDao.getOrderById(orderId);
		List<GameSlip> _slipList = gameOrderService.querySlipsByOrderId(orderId);

		GameLockEntity _glEntity = gameLockServiceImpl.queryCurrUseGameLockEntity(lotteryId);
		if (null == _glEntity) {
			log.error("获取封锁变价参数错误。");
			throw new RuntimeException("获取封锁变价参数错误。");
		}
		
		LockIssue _lockIssue = null;
		LockIssue _lockIssueTema = getLockIssue(_glEntity, lotteryId, issueCode, GameLockPlayType.TEMA.toString());
		LockIssue _lockIssueYixiao = getLockIssue(_glEntity, lotteryId, issueCode, GameLockPlayType.YIXIAO.toString());
		LockIssue _lockIssueOther = getLockIssue(_glEntity, lotteryId, issueCode, GameLockPlayType.OTHER.toString());
		
		for(GameSlip _slip :_slipList){
			_slip.setGameOrder(_gameOrder);
			if(_slip.getMutlAward() != null && _slip.getMutlAward() == 1) {
				_slip.setSlipItemAssist(slipItemAssistDao.getSlipAssistItemList(_slip.getId()));
			}
			
			String _slipBetTypeCode = _slip.getGameBetType().getBetTypeCode();
			if(GameLockPlayType.isTEMA(_slipBetTypeCode)) {
				_lockIssue = _lockIssueTema;
			} else if(GameLockPlayType.isYIXIAO(_slipBetTypeCode)) {
				_lockIssue = _lockIssueYixiao;
			} else {
				_lockIssue = _lockIssueOther;
			}
			
			if((_lockIssue.getBetTotal() - _slip.getTotalAmount()) < 0) {
				throw new Exception("撤销六合彩方案时，恢复封锁纪录投注总金额不得小于0。投注方式:" + _slipBetTypeCode + ", 目前投注金额:" + _lockIssue.getBetTotal() + ", 待撤销金额:" + _slip.getTotalAmount());
			}
			
			BigDecimal _base = new BigDecimal(10000);
			// 賠率
			BigDecimal _odds = getOdds(_slip).divide(_base);
			// 注單投注金額
			BigDecimal _totalAmount = new BigDecimal(_slip.getTotalAmount()).divide(_base);
			// 玩法返點  = 注單投注金額 * 總代返點
			BigDecimal _retValue = _totalAmount.multiply(new BigDecimal(gameReturnPointDao.getRetunPoint(_slip, null)).divide(_base));
			// 預支出金額 = 注單投注金額 * 賠率  + 玩法返點 
			BigDecimal _outlay = _totalAmount.multiply(_odds).add(_retValue);
			
			//扣减總收入
			_lockIssue.setBetTotal(_lockIssue.getBetTotal() - _slip.getTotalAmount());
			//扣減玩法返點
			_lockIssue.setRetPoint(BigDecimal.valueOf(_lockIssue.getRetPoint()).divide(_base).subtract(_retValue).multiply(_base).longValue());
			
			Map<String, Integer> _map = super.getInfluncePoint(_slip.getGameBetType().getBetTypeCode(), lotteryId, _slip.getBetDetail());
			if (_map != null && !_map.isEmpty()) {
				Iterator<Map.Entry<String, Integer>> _influencePoint = _map.entrySet().iterator();
				Entry<String, Integer> _entry;
				//每個號碼進行盈虧值回復
				while (_influencePoint.hasNext()) {
					_entry = _influencePoint.next();
					//取得封鎖各號碼盈虧值
					Map<String, PontStatus> _points = _lockIssue.getPoints();
					// 变价信息。
					PontStatus _pontStatus = _points.get(_entry.getKey());
					
					// 兩面的49號球賠率改為 1，並重算預支出金額
					if(BetTypeCodeMapping.lhc_54_37_83.name().contains(_slip.getGameBetType().getBetTypeCode()) && "49".equals(_entry.getKey())) {
						_odds = new BigDecimal(1);
						_outlay =  _totalAmount.multiply(_odds).add(_retValue);
					}
					
					// 盈虧值 = 預支出金額 - 玩法返點
					BigDecimal _profirLossValue = _outlay.subtract(_retValue);
					_pontStatus.setTotal(new BigDecimal(_pontStatus.getTotal()).divide(_base).subtract(_profirLossValue.negate()).multiply(_base).longValue());
				}
			}

			log.debug("before save:" + _lockIssue);
			redisClient.set(_lockIssue.getId(), JsonMapper.nonDefaultMapper().toJson(_lockIssue));
		}
	}
}