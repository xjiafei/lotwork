package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameTransactionFundDao;
import com.winterframework.firefrog.game.dao.vo.FundGameVos;
import com.winterframework.firefrog.game.dao.vo.GameTransactionFund;
import com.winterframework.firefrog.game.service.ITransactionRecordService;
import com.winterframework.firefrog.game.web.dto.FundTransactionStruc;
import com.winterframework.firefrog.game.web.dto.GameReportRequest;
import com.winterframework.firefrog.game.web.dto.GameReportStruc;
import com.winterframework.firefrog.game.web.dto.GameRiskTransactionReportStruc;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GameTransactionRecordServiceImpl 
* @Description: 游戏与资金交易查询Serivce
* @author Richard hugh
* @date 2014-2-17 上午10:33:29 
*
 */
@Service("gameTransactionRecordServiceImpl")
public class GameTransactionRecordServiceImpl implements ITransactionRecordService {

	private static final Logger log = LoggerFactory.getLogger(GameTransactionRecordServiceImpl.class);

	@Resource(name = "gameTransactionFundDaoImpl")
	private IGameTransactionFundDao transactionDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<FundTransactionStruc> queryTransactionRecord(Long userId, List<String> orderCodeList,
			List<String> planCodeList) throws Exception {
		log.info("开始进入查询游戏与资金交易处理方法。 userid=" + userId);

		List<GameTransactionFund> transactionList = new ArrayList<GameTransactionFund>();

		List<String> conditionList = new ArrayList<String>();

		if (null != orderCodeList && !orderCodeList.isEmpty()) {
			log.info("queryTransactionRecord orderCodeList.size=" + orderCodeList.size());
			conditionList.addAll(orderCodeList);
		}

		if (null != planCodeList && !planCodeList.isEmpty()) {
			log.info("queryTransactionRecord planCodeList.size=" + planCodeList.size());
			conditionList.addAll(planCodeList);
		}

		transactionList = transactionDao.queryTransactionByCode(userId, conditionList);

		List<FundTransactionStruc> strucsList = new ArrayList<FundTransactionStruc>();

		for (Iterator<GameTransactionFund> iterator = transactionList.iterator(); iterator.hasNext();) {

			GameTransactionFund fund = (GameTransactionFund) iterator.next();
			FundTransactionStruc struc = new FundTransactionStruc();

			struc.setTransactionId(fund.getTid());
			struc.setTransactionTime(fund.getCreateTime());

			if (StringUtils.isNotBlank(fund.getFundType())) {
				String[] temp = fund.getFundType().split("|");
				struc.setTransactionInfp(temp[2]);
			}

			if (fund.getSymbol().equals("+")) {
				//收入 
				struc.setTransactionPositive(fund.getAmount());
			} else {
				//支出
				struc.setTransactionNegative(fund.getAmount());
			}

			//			struc.setAccountBalance(); TODO 待确认

			strucsList.add(struc);
		}

		return strucsList;
	}

	@Override
	public Page<GameReportStruc> gameReport(PageRequest<GameReportRequest> pageRequest) throws Exception {

		log.info("游戏报表查询服务开始。。。");
		return transactionDao.queryAllTransaction(pageRequest);
	}

	@Override
	public Page<GameRiskTransactionReportStruc> queryTransactionReport(PageRequest<GameReportRequest> pageRequest)
			throws Exception {

		log.info("游戏报表查询服务开始。。。");
		return transactionDao.queryTransactionReport(pageRequest);
	}

	public List<GameTransactionFund> parseGameTransactionFundList(TORiskDTO dto, List<FundGameVos> list)
			throws Exception {
		List<GameTransactionFund> trans = new ArrayList<GameTransactionFund>();
		for (FundGameVos vo : list) {
			trans.add(parseGameTransactionFund(dto, vo));
		}
		return trans;
	}

	public GameTransactionFund parseGameTransactionFund(TORiskDTO dto, FundGameVos vos) throws Exception {

		String[] userAndAmont = StringUtils.split(vos.getVals(), "|");

		GameTransactionFund tran = new GameTransactionFund();
		tran.setAmount(Long.parseLong(userAndAmont[1]));
		tran.setOperatorid(Long.parseLong(userAndAmont[0]));//"资金变更用户id";
		tran.setOrderCode(dto.getOrderCodeList());
		tran.setPlanCode(dto.getPlanCodeList());
		tran.setCheckType(dto.getType().longValue());

		tran.setCreateTime(new Date());
		tran.setCreator(1L);//系统默认1
		tran.setFundType(vos.getReason());

		tran.setIssueCode(dto.getIssueCode());
		tran.setLotteryId(dto.getLotteryid());
		//tran.setWebIssueCode();
		if (GameFundTypesUtils.addReason.contains(vos.getReason())) {
			tran.setSymbol("+");
		} else {
			tran.setSymbol("-");
		}
		tran.setTid("");
		tran.setUpdateTime(new Date());
		tran.setUserid(1L); //"执行用户ID 系统默认1";			

		return tran;
	}


}
