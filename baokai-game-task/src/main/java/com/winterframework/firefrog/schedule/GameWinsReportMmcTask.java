package com.winterframework.firefrog.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.service.IGameWinsReportService;

@Service("gameWinsReportMmcService")
public class GameWinsReportMmcTask { 
		private Logger log = LoggerFactory.getLogger(GameWinsReportMmcTask.class);

		@Resource(name = "gameWinsReportServiceImpl")
		private IGameWinsReportService gameWinsReportService;

		public void execute() throws Exception {
			log.debug("---begin GameWinsReportMmcTask----");
			Long lotteryId=99112L;	//顺利秒秒彩
			List<Long> issueCodeList =gameWinsReportService.getGameIssueCodeListNoWinsReport(lotteryId);
			if(issueCodeList!=null && issueCodeList.size()>0){
				for(Long issueCode:issueCodeList){
					gameWinsReportService.createGameWinReport(lotteryId, issueCode);
				}
			}
			lotteryId=99306l;
			issueCodeList =gameWinsReportService.getGameIssueCodeListNoWinsReport(lotteryId);
			if(issueCodeList!=null && issueCodeList.size()>0){
				for(Long issueCode:issueCodeList){
					gameWinsReportService.createGameWinReport(lotteryId, issueCode);
				}
			}
			lotteryId=99113l;
			issueCodeList =gameWinsReportService.getGameIssueCodeListNoWinsReport(lotteryId);
			if(issueCodeList!=null && issueCodeList.size()>0){
				for(Long issueCode:issueCodeList){
					gameWinsReportService.createGameWinReport(lotteryId, issueCode);
				}
			}
			log.debug("---end GameWinsReportMmcTask----");
		} 
}
