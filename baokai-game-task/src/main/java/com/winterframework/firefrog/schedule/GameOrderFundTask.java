package com.winterframework.firefrog.schedule;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.service.IExportFileService;
import com.winterframework.firefrog.game.service.IGameOrderService;

@Service("gameOrderFundTask")
public class GameOrderFundTask { 
		private Logger log = LoggerFactory.getLogger(GameOrderFundTask.class);


		@Resource(name = "gameOrderServiceImpl")
		protected IGameOrderService gameOrderService;
		 
		private Long lotteryId;
		public void execute() throws Exception {
			 
			 
		}


		public Long getLotteryId() {
			return lotteryId;
		}


		public void setLotteryId(Long lotteryId) {
			this.lotteryId = lotteryId;
		} 
		
}
