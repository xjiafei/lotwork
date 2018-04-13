package com.winterframework.firefrog.phone.web.chart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public abstract class AbstractChartTransHandler { 
		private Logger log = LoggerFactory.getLogger(AbstractChartTransHandler.class);
		public abstract ChartStruc handle(Long lotteryId,String gameMethod,GameTrendChartStruc chartStruc);
}
