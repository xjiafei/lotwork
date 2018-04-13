package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameWinsReportDao;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.dao.vo.WinsSumReport;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.entity.WinsReport;
import com.winterframework.firefrog.game.service.IGameWinsReportService;
import com.winterframework.firefrog.game.web.dto.WinsReportQueryRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: GameWinsReportServiceImpl 
* @Description: 运营盈亏报表Srervice实现类 
* @author Denny 
* @date 2013-10-16 下午3:41:08 
*  
*/
@Service("gameWinsReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameWinsReportServiceImpl implements IGameWinsReportService {

	@Resource(name = "gameWinsReportDaoImpl")
	private IGameWinsReportDao gameWinsReportDao;
	
	@Override
	public Page<WinsReport> queryWinsReport(PageRequest<WinsReportQueryRequest> pr) {
		return gameWinsReportDao.getWinsReport(pr);
	}

	@Override
	public WinsSumReport queryWinsSumReport(Long lotteryid, Long startTime, Long endTime) {
		return gameWinsReportDao.getWinsSumReport(lotteryid, startTime, endTime);
	}

	@Override
	public List<WinsReport> queryWinsDetailReport(Long lotteryid, Long issueCode, String sortColumns) {
		List<GameWinsReport> gameWinsReportList = gameWinsReportDao.getWinsDetailReport(lotteryid, issueCode, sortColumns);
		List<WinsReport> list = new ArrayList<WinsReport>();
		for (GameWinsReport gwr : gameWinsReportList) {
			WinsReport wr = VOConvert.gameWinsReport2WinsReport(gwr);
			list.add(wr);
		}
		return list;
	}

	@Override
	public List<WinsReport> queryWinsReportForExport(Long lotteryid, Long startTime, Long endTime, String sortColumns) {
		List<GameWinsReport> gameWinsReportList = gameWinsReportDao.getWinsReportForExport(lotteryid, startTime, endTime,sortColumns);
		List<WinsReport> list = new ArrayList<WinsReport>();
		for (GameWinsReport gwr : gameWinsReportList) {
			WinsReport wr = VOConvert.gameWinsReport2WinsReport(gwr);
			list.add(wr);
		}
		return list;
	}

	@Override
	public WinsSumReport queryWinsDetailSumReport(long lotteryid, long issueCode) {
		return gameWinsReportDao.getWinsDetailSumReport(lotteryid, issueCode);
	}

}
