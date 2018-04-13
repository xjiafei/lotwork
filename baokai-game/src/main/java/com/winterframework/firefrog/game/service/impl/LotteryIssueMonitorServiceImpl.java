package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameWarnIssueDao;
import com.winterframework.firefrog.game.dao.IGameWarnOrderDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameExceptionAuditOrder;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameWarnEvent;
import com.winterframework.firefrog.game.dao.vo.GameWarnIssueList;
import com.winterframework.firefrog.game.dao.vo.GameWarnOrder;
import com.winterframework.firefrog.game.dao.vo.RiskOrders;
import com.winterframework.firefrog.game.dao.vo.SpiteOrders;
import com.winterframework.firefrog.game.entity.GameIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity;
import com.winterframework.firefrog.game.entity.GameWarnIssueEntity.GameWarnStatus;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameWarnOrderEntity;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.LotteryIssueMonitorLogs;
import com.winterframework.firefrog.game.exception.GameCancalOutTimeErrorException;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.service.ILotteryIssueMonitorService;
import com.winterframework.firefrog.game.web.controller.LotteryIssueMonitorController;
import com.winterframework.firefrog.game.web.dto.GameExceptionAuditRequestDTO;
import com.winterframework.firefrog.game.web.dto.GameWarnDetailQueryDto;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorIssueDetail;
import com.winterframework.firefrog.game.web.dto.LotteryMonitorListRequestDTO;
import com.winterframework.firefrog.game.web.dto.QueryLotteryIssueWarnDTO;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: LotteryMonitorServiceImpl 
* @Description: 彩种奖期监控
* @author Richard
* @date 2013-10-12 上午10:54:55 
*
 */
@Service("lotteryIssueMonitorServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class LotteryIssueMonitorServiceImpl implements ILotteryIssueMonitorService {
	private Logger log = LoggerFactory.getLogger(LotteryIssueMonitorServiceImpl.class);
	@Resource(name = "gameWarnIssueDaoImpl")
	private IGameWarnIssueDao gameWarnIssueDao;

	@Resource(name = "gameWarnOrderDaoImpl")
	private IGameWarnOrderDao gameWarnOrderDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao issueDao;

	@Resource(name = "gameOrderDaoImpl")
	private IGameOrderDao orderDao;

	@Resource(name = "gameReturnPointDaoImpl")
	private IGameReturnPointDao returnPointDao;

	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService fundRiskService;

	@Resource(name = "gameSeriesConfigServiceImpl")
	private IGameSeriesConfigService gameSeriesConfigService;

	@Override
	public List<GameWarnIssueEntity> queryGameWarnIssue() throws Exception {

		List<GameWarnIssueEntity> list = gameWarnIssueDao.queryGameWarnIssueNotices();
		List<GameWarnIssueEntity> rlist = new ArrayList<GameWarnIssueEntity>();
		List<GameSeries> series = gameSeriesDao.getAll();
		for (GameSeries gameSeries : series) {
			Long lotteryId = gameSeries.getLotteryid();
			GameIssue issue = issueDao.queryCurrentGameIssue(lotteryId);
			GameWarnIssueEntity newWarn = new GameWarnIssueEntity();
			if (issue != null) {
				newWarn.setIssueCode(issue.getIssueCode());
				newWarn.setWebIssueCode(issue.getWebIssueCode());
			}
			Lottery lottery = new Lottery();
			lottery.setLotteryId(lotteryId);
			lottery.setLotteryName(gameSeries.getLotteryName());
			newWarn.setLottery(lottery);
			newWarn.setStatus(GameWarnStatus.pending);
			for (GameWarnIssueEntity warn : list) {
				if (warn.getLottery().getLotteryId().longValue() == lotteryId.longValue()) {
					newWarn.setStatus(GameWarnStatus.processed);
				}
			}
			rlist.add(newWarn);
		}

		return rlist;
	}

	@Override
	public Page<GameWarnIssueList> queryGameWarnList(PageRequest<LotteryMonitorListRequestDTO> pageRequest)
			throws Exception {

		//0-全部；1-仅查看尚未结束的奖期;2-仅查看存在异常的奖期;3仅查看尚未结束的奖期并且仅查看存在异常的奖期
		Integer queryType = pageRequest.getSearchDo().getIssueType();
		LotteryMonitorListRequestDTO dto = pageRequest.getSearchDo();
		try {
			if(dto!=null && dto.getLotteryId() != null){
				gameWarnIssueDao.updateNoticeStatus(dto.getLotteryId());
			}			
		} catch (Exception e) {
			log.error("更新warn_issue通知阅读状态失败" + dto.getLotteryId() , e);
		}
		
		if (queryType == 0) {
			return gameWarnIssueDao.queryGameWarnIssueList(pageRequest);
		} else if (queryType == 1) {
			return gameWarnIssueDao.queryGameWarnIssueOnSale(pageRequest);
		} else if (queryType == 2) {
			return gameWarnIssueDao.queryGameWarnIssue(pageRequest);
		} else if (queryType == 3) {
			return gameWarnIssueDao.queryGameWarnOnlyCurrntIssue(pageRequest);
		}

		return null;
	}

	@Override
	public LotteryMonitorIssueDetail queryGameWarnDetail(PageRequest<GameWarnDetailQueryDto> pageRequest)
			throws Exception {

		
		
		GameWarnDetailQueryDto dto = pageRequest.getSearchDo();
		
		
		
		
		List<GameWarnIssueEntity> entitys = gameWarnIssueDao.queryGameWarnIssueByLotteryIdAndIssueCode(
				dto.getLotteryId(), dto.getIssueCode());

		//获取奖期的信息，以防止无异常警告奖期报错。
		GameIssueEntity issueEntity = issueDao.queryGameIssue(dto.getLotteryId(), dto.getIssueCode());
		String disposeMemo = "";
		String suggestMemo = "无需操作";
		String warnPara = "";
		String warnTypeStr = "无异常";
		Long status = 0L;
		if (!entitys.isEmpty()) {
			Collections.sort(entitys, new Comparator<GameWarnIssueEntity>() {
				@Override
				public int compare(GameWarnIssueEntity o1, GameWarnIssueEntity o2) {
					return o2.getId().compareTo(o1.getId());
				}
			});

			warnPara += entitys.get(0).getWarnParas() + "  ";
			warnTypeStr = GameWarnEvent.getRoutNameByRoutCode(entitys.get(0).getStatusRout());
			suggestMemo = GameWarnEvent.getLastSuggestByRoutCode(entitys.get(0).getStatusRout());
			if(warnTypeStr != null && warnTypeStr.indexOf("x") > 0 && issueEntity.getRecivceDrawTime() !=null){
				String r = (issueEntity.getOpenDrawTime().getTime() - issueEntity.getRecivceDrawTime().getTime())/1000 + "";
				warnTypeStr = warnTypeStr.replace("x", r);
			}
			status = (long) entitys.get(0).getStatus().getValue();
		} else {
			warnTypeStr = "无异常";
		}
		GameSeries series = gameSeriesDao.getByLotteyId(dto.getLotteryId());

		Page<RiskOrders> page = gameWarnOrderDao.queryGameWarnOrderByLotteryIdAndIssueCode(pageRequest);

		List<RiskOrders> riskList = page.getResult();
		if (null != riskList && riskList.size() > 0) {

			for (RiskOrders order : riskList) {

				order.setOrderDetails(gameWarnOrderDao.queryGameWarnOrderByLotteryIdIssueCodeUserId(dto.getLotteryId(),
						dto.getIssueCode(), order.getUserid()));
			}
		}
		List<SpiteOrders> spiteList = gameWarnOrderDao.querySpiteOrderByLotteryIdAndIssueCode(dto.getLotteryId(),
				dto.getIssueCode());

		LotteryMonitorIssueDetail detail = new LotteryMonitorIssueDetail();

		detail.setIssueCode(dto.getIssueCode());
		detail.setLotteryId(dto.getLotteryId());
		detail.setLotteryName(series.getLotteryName());

		detail.setOtherTypeStr(disposeMemo);
		detail.setWebIssueCode(issueEntity.getWebIssueCode());
		//		detail.setStatus(issueEntity.getStatus());
		//		detail.setRiskOrderList(riskList);
		detail.setPage(page);
		detail.setSpiteOrderList(spiteList);
		detail.setSuggestTypeStr(suggestMemo);

		detail.setWarnParas(warnPara);
		detail.setWarnTypeStr(warnTypeStr);
		detail.setStatus(status);

		//GameDrawResult draw = drawResultDao.getDrawResuleByLotteryIdAndIssueCode(dto.getLotteryId(), dto.getIssueCode());
		Date drawTime = issueEntity.getFactionDrawTime();
		boolean darwCondition = true;
		Date now = new Date();
		if (drawTime != null) {
			detail.setNumberRecord(issueEntity.getNumberRecord());
			//GameSeriesConfigEntity gse = gameSeriesConfigService.getSeriesConfigByLotteryId(dto.getLotteryId());

			Long backoutTime = issueEntity.getIssuewarnExceptionTime() == null ? 0 : issueEntity
					.getIssuewarnExceptionTime();
			if (drawTime != null && now.getTime() > drawTime.getTime() + backoutTime * 60 * 1000) {
				darwCondition = false;
			}
		}
		//销售截止时间 在 当前时间之前 （销售截止后）， 且不在开奖后但超过cancelTime 		
		if (now.after(issueEntity.getSaleEndTime()) && darwCondition) {
			detail.setIsCanCanel(0L);
		} else {
			detail.setIsCanCanel(1L);
		}
		if (now.after(issueEntity.getSaleEndTime())) {
			detail.setIsAfterSaleEndTime(1L);
		} else {
			detail.setIsAfterSaleEndTime(0L);
		}
		return detail;
	}

	@Override
	public List<GameWarnOrderEntity> queryGameWarnOrderDetail(Long lotteryId, Long issueCode) throws Exception {
		return gameWarnOrderDao.queryGameWarnOrderDetail(lotteryId, issueCode);
	}

	@Override
	public Page<LotteryIssueMonitorLogs> queryLotteryIssueWarnLog(PageRequest<QueryLotteryIssueWarnDTO> pageRequest)
			throws Exception {
		return gameWarnIssueDao.queryLotteryIssueWarnLog(pageRequest);
	}

	@Override
	public void auditLotteryIssueMonitor(Long lotteryid, Long issueCode) throws Exception {
		//审核全部通过 1为审核通过
		gameWarnOrderDao.updateGameWarnOrderByLotteryIdAndIssueCode(lotteryid, issueCode, 1);

		List<GameWarnOrderEntity> list = gameWarnOrderDao.queryGameWarnOrderDetail(lotteryid, issueCode);
		for (GameWarnOrderEntity entity : list) {
			//调用风控中心。
			callFundService(entity.getOrderCode());
		}
	}

	@Override
	public void auditLotteryOrderMonitor(String orderCode, Integer status) throws Exception {
		//1 已审核 2 未通过审核
		gameWarnOrderDao.updateGameWarnOrderByOrderCode(orderCode, status);

		//调用风控中心
		callFundService(orderCode);
	}

	//调用风控中心
	private void callFundService(String orderCode) throws Exception {

		GameWarnOrder order = gameWarnOrderDao.getGameWarnOrderByOrderCode(orderCode);
		if (null != order) {

			//拼装数据
			List<TORiskDTO> list = new ArrayList<TORiskDTO>();

			Long issueCode = order.getIssueCode();
			Long lotteryId = order.getLotteryid();

			if (order.getStatus() == 1) { //审核通过。
				//3002,派发奖金   
				//中奖。
				TORiskDTO r1 = new TORiskDTO();

				r1.setAmount(String.valueOf(order.getCountWin()));
				r1.setIssueCode(issueCode);
				r1.setLotteryid(lotteryId);
				r1.setOrderCodeList(order.getOrderCode());
				r1.setType(GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE);
				r1.setUserid(String.valueOf(order.getUserid()));
				list.add(r1);
			}

			//5004,派奖后投注资金解冻  

			TORiskDTO r2 = new TORiskDTO();
			r2.setAmount(String.valueOf(order.getTotamount()));
			r2.setIssueCode(issueCode);
			r2.setLotteryid(lotteryId);
			r2.setOrderCodeList(order.getOrderCode());
			r2.setType(order.getParentType() == 2 ? GameFundTypesUtils.GAME_PLAN_UNFREEZER_DETEAIL_TYPE
					: GameFundTypesUtils.GAME_BET_UNFREEZER_DETEAIL_TYPE);
			r2.setUserid(String.valueOf(order.getUserid()));

			list.add(r2);

			//5008,派奖后返点解冻   
			GameRetPoint point = returnPointDao.getRetPointByOrderId(order.getOrderId());
			if (null != point) {
				TORiskDTO ret = new TORiskDTO();
				ret.setAmount(point.getRetPointChain());
				ret.setIssueCode(issueCode);
				ret.setLotteryid(lotteryId);
				ret.setOrderCodeList(order.getOrderCode());
				ret.setType(GameFundTypesUtils.GAME_RET_UNFREEZER_DETEAIL_TYPE);
				ret.setUserid(point.getRetUserChain());

				list.add(ret);
			}

			fundRiskService.distributeAward(list);

		}
	}

	@Override
	public Page<GameExceptionAuditOrder> queryGameWarnAuditList(PageRequest<GameExceptionAuditRequestDTO> pageRequest) {
		Integer status = pageRequest.getSearchDo().getStatus();
		switch (status) {//0全部，1 待审核，2 已完成
		case 0:
			return issueDao.queryGameWarnIssueAuditList(pageRequest);
		case 1:
			return issueDao.queryGameWarnIssueToAuditList(pageRequest);
		case 2:
			return issueDao.queryGameWarnIssueAuditedList(pageRequest);
		default:
			return issueDao.queryGameWarnIssueAuditList(pageRequest);
		}
	}

	@Override
	public void auditLotteryOrdersMonitor(String orderCodes) throws Exception {
		String[] strs = orderCodes.split(",");
		for (String orderCode : strs) {
			auditLotteryOrderMonitor(orderCode, 1);
		}
	}

}
