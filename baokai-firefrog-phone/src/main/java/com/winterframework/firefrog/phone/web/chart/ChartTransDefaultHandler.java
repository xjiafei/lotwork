package com.winterframework.firefrog.phone.web.chart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public class ChartTransDefaultHandler extends AbstractChartTransHandler{ 
		private Logger log = LoggerFactory.getLogger(ChartTransDefaultHandler.class);
		
		@Override
		public ChartStruc handle(Long lotteryId, String gameMethod, GameTrendChartStruc chartStruc) {
			ChartStruc cs=new ChartStruc();
			List<ChartSubStruc> data=new ArrayList<ChartSubStruc>();
			List<Object> statistics=new ArrayList<Object>();
			for(int i=0;i<chartStruc.getData().size();i++){
				@SuppressWarnings("unchecked")
				List<Object> d=(List<Object>)chartStruc.getData().get(i);
				ChartSubStruc css=new ChartSubStruc();
				css.setIssue((String)d.get(0));
				css.setCode((String)d.get(1));
				css.setByteNumber(d.subList(2, d.size()));
				data.add(css);
			}
			int num=gameMethod.equals("Wuxing")?6:5;
			for(int k=0;k<num;k++){
				List<Object> statisticsSub=new ArrayList<Object>();
				for(int i=0;i<chartStruc.getStatistics().size();i++){
					List<Object> d=(List<Object>)chartStruc.getStatistics().get(i);
					statisticsSub.add(d.subList(k*10, (k+1)*10));
				}
				statistics.add(statisticsSub);
			}
			cs.setData(data);
			cs.setStatistics(statistics);
			return cs;
		}
		
		
}
