package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameIssueDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameWinsReportDao;
import com.winterframework.firefrog.game.dao.vo.GameIssue;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameWinsReport;
import com.winterframework.firefrog.game.service.IGameWinsReportService;

@Service("gameWinsReportServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameWinsReportServiceImpl implements IGameWinsReportService {
	
	private static final Logger log = LoggerFactory.getLogger(GameWinsReportServiceImpl.class);
	
	@Resource(name = "gameIssueDaoImpl")
	private IGameIssueDao gameIssueDao;
	
	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;
	
	@Resource(name = "gameWinsReportDaoImpl")
	private IGameWinsReportDao gameWinsReportDao;
	
	@Override
	public int save(GameWinsReport report) throws Exception {
		if(report==null) return 0;
		if(report.getId()==null){
			report.setCreateTime(DateUtils.currentDate());
			return gameWinsReportDao.insert(report);
		}else{
			report.setUpdateTime(DateUtils.currentDate()); 
			return gameWinsReportDao.update(report);
		}
	}
	
	private GameWinsReport getGameWinsReportByLotteryId(Long lotteryid, Long gameSetCode, Long gameGroupCode, Long betMethodeCode, Long issueCode){
		
		  GameWinsReport report = new GameWinsReport();
		  report.setLotteryid(lotteryid);
		  report.setGameSetCode(gameSetCode);
		  report.setGameGroupCode(gameGroupCode);
		  report.setBetMethodCode(betMethodeCode);
		  report.setIssueCode(issueCode);
		  
		  List<GameWinsReport> list = gameWinsReportDao.getAllByEntity(report);
		  
		  if(null != list && !list.isEmpty()){
			  return list.get(0);
		  }
		  
		  return null;
	} 
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly=false)
	public void createGameWinReport(Long lotteryId, Long issueCode) throws Exception {
		/**
		 * 有注单则取注单数据，无注单则生成默认0数据（可能存在后续追过来的注单，此时删除原先的默认0数据）
		 * 撤单手续费取订单表中：手续费（订单状态：撤销）
		 * 此方法关键处可批量调用DAO
		 */
		log.debug("1:生成 Game_WIN_REPORT 数据，lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);
		GameIssue issue = gameIssueDao.getGameIssueByLotteryIssue(lotteryId, issueCode);
		log.debug("2:生成 Game_WIN_REPORT 数据，lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);
		GameSeries gameSeries = gameSeriesDao.getByLotteyId(lotteryId);
		log.debug("3:生成 Game_WIN_REPORT 数据，lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);
		//当期彩种所有玩法汇总，
		List<String> betTypeCodeList = gameWinsReportDao.getBetTypeCodeListByLotteryIdAndIssueCode(lotteryId, issueCode);
		log.debug("4：生成 Game_WIN_REPORT 数据，lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);  
		GameWinsReport report = getGameWinsReportByLotteryId(lotteryId,0L,0L,0L, issueCode);
		if(null!=report){			
			//删除
			gameWinsReportDao.delete(report.getId());
		}
		//追号撤单手续费
		Long planCancelFee=gameWinsReportDao.getPlanCancelFeeByLotteryIdAndIssueCode(lotteryId, issueCode); 
		boolean hasPlanCancelFee=planCancelFee!=null && planCancelFee.intValue()>0;
		if(hasPlanCancelFee){  
			report = new GameWinsReport(); 
			report.setLotteryid(lotteryId);
			report.setIssueCode(issueCode);
			report.setGameSetCode(0L);
			report.setGameGroupCode(0L);
			report.setBetMethodCode(0L);
			report.setTotalSales(0L);
			report.setTotalCancel(planCancelFee);
			report.setTotalPoints(0L);
			report.setTotalWins(0L);    
			report.setTotalProfit(planCancelFee);
			
			report.setWebIssueCode(issue.getWebIssueCode()); 
			report.setLotterySeriesCode(gameSeries.getLotterySeriesCode().longValue());
			report.setLotteryTypeCode(gameSeries.getLotteryTypeCode().longValue());
			report.setReportDate(issue.getOpenDrawTime());
			this.save(report);
		} 
		if(betTypeCodeList != null && betTypeCodeList.size()>0 ){
			//有投注 
			for(String betTypeCode : betTypeCodeList){ 
				log.debug("5：生成 Game_WIN_REPORT 数据，lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);
				
				String[] btc  = betTypeCode.split("_"); 
				Long gameGroupCode = Long.parseLong(btc[0]);
				Long gameSetCode = Long.parseLong(btc[1]);
				Long betMethodCode = Long.parseLong(btc[2]);
						
				report = getGameWinsReportByLotteryId(lotteryId,gameSetCode,gameGroupCode,betMethodCode, issueCode);
				if(null == report){ 
					report = new GameWinsReport();
					report.setIssueCode(issueCode);
					report.setLotteryid(lotteryId);
					report.setGameSetCode(gameSetCode);
					report.setGameGroupCode(gameGroupCode);
					report.setBetMethodCode(betMethodCode);
				} 
				report.setWebIssueCode(issue.getWebIssueCode()); 
				report.setLotterySeriesCode(gameSeries.getLotterySeriesCode().longValue());
				report.setLotteryTypeCode(gameSeries.getLotteryTypeCode().longValue());
				report.setReportDate(issue.getOpenDrawTime());
				//玩法销售总金额
				Long totalSaleAmount = gameWinsReportDao.getTotalSaleAmountByIssueCodeAndBetTypeCode(lotteryId,issueCode,betTypeCode);
				if(totalSaleAmount == null){
					totalSaleAmount = 0L;
				}
				report.setTotalSales(totalSaleAmount);
				//add 整个奖期的销售金额
				Long totalIssueSaleAmount = gameWinsReportDao.getTotalIssueSaleAmountByIssueCode(lotteryId,issueCode);
				
				List<String> retPointList =  gameWinsReportDao.getRetPointByIssueCodeAndBetTypeCode(lotteryId, issueCode, betTypeCode);
				//返点总金额
				Long retPontAmount = 0L;
				for(String retPoint : retPointList){ 
					String[] rp = retPoint.split(",");
					//计算该玩法所有的返点值。
					for(int i=0; i<rp.length;i++){
						retPontAmount += Long.parseLong(rp[i]);
					}
					
					//修改bug0005051: 预发布环境，后台JLFFC 单期盈亏表 返点总额 统计错误。用户投注【中三玩法】 注释
					//retPontAmount += Long.parseLong(rp[0]) + Long.parseLong(rp[1]);
				}
				
				Long realRetPontAmount = 0L;
				if(totalIssueSaleAmount !=null && totalIssueSaleAmount > 0){
					
					realRetPontAmount = new BigDecimal(totalSaleAmount).divide(new BigDecimal(totalIssueSaleAmount), 6, RoundingMode.HALF_UP).multiply(new BigDecimal(retPontAmount), MathContext.DECIMAL32).longValue();
				}
				
				//修改bug0005051: 预发布环境，后台JLFFC 单期盈亏表 返点总额 统计错误。用户投注【中三玩法】 注释。
//				report.setTotalPoints(retPontAmount == null ? 0L : retPontAmount);
				report.setTotalPoints(realRetPontAmount);
				
				//返奖总金额
				Long totalWinAmount = gameWinsReportDao.getTotalWinAmountByIssueCodeAndByTypeCode(lotteryId,issueCode,betTypeCode);
				if(null == totalWinAmount){
					totalWinAmount = 0L;
				}
				report.setTotalWins(totalWinAmount);
				//撤单手续费 
				Long cancelAmount = gameWinsReportDao.getCancelAmountByIssueCodeAndBetTypeCode(lotteryId, issueCode, betTypeCode);
				cancelAmount=cancelAmount == null ? 0L : cancelAmount;
				report.setTotalCancel(cancelAmount);  
				//盈亏值
				// 修复 0005099: 【中三二期】JXSSC/TJSSC 后台游戏中心->单期盈亏表总返点、盈亏值计算错误 改 retPontAmount 为realRetPontAmount
				report.setTotalProfit(totalSaleAmount+cancelAmount-realRetPontAmount -totalWinAmount);
				this.save(report);
				log.debug("生成 GAME_WINS_REPORT数据成功！lotteryId = "+ lotteryId + ", issueCode= "+ issueCode);
			}
		}else if(hasPlanCancelFee){
			return;
		}else{ 
			log.debug(" 生成GAEM_WIN_REPORT 数据，无BET_TYPE_CODE，lotteryId=" + lotteryId + ",issueCode= "+ issueCode); 
			report = new GameWinsReport(); 
			report.setLotteryid(lotteryId);
			report.setIssueCode(issueCode);
			report.setGameSetCode(0L);
			report.setGameGroupCode(0L);
			report.setBetMethodCode(0L);
			report.setTotalSales(0L);
			report.setTotalPoints(0L);
			report.setTotalProfit(0L);
			report.setTotalCancel(0L);
			report.setTotalWins(0L);  
			report.setWebIssueCode(issue.getWebIssueCode());
			report.setLotterySeriesCode(gameSeries.getLotterySeriesCode().longValue());
			report.setLotteryTypeCode(gameSeries.getLotteryTypeCode().longValue());
			report.setReportDate(issue.getOpenDrawTime());
			
			this.save(report);
			
			log.debug("LotteryId = " + lotteryId + ", IssueCode = " + issueCode + ", 无投注信息，无生成报表"); 
		} 
	}  
	 
	@Override
	@Transactional
	public List<Long> getGameIssueCodeListNoWinsReport(Long lotteryId)
			throws Exception { 
		return gameWinsReportDao.getGameIssueCodeListNoWinsReport(lotteryId);
	}
}
