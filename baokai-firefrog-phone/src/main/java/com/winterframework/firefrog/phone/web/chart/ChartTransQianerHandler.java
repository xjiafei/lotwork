package com.winterframework.firefrog.phone.web.chart;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public class ChartTransQianerHandler extends AbstractChartTransHandler{ 
		private Logger log = LoggerFactory.getLogger(ChartTransQianerHandler.class);
		
		@Override
		public  ChartStruc handle(Long lotteryId, String gameMethod, GameTrendChartStruc chartStruc) {
			ChartStruc cs=new ChartStruc();
			List<ChartSubStruc> data=new ArrayList<ChartSubStruc>();
			List<Object> statistics=new ArrayList<Object>();
			for(int i=0;i<chartStruc.getData().size();i++){
				List<Object> d=(List<Object>)chartStruc.getData().get(i);
				
				List<Object> list=new ArrayList<Object>();
				list.addAll(d.subList(2, 4));
				list.addAll(d.subList(5, 6));
				
				ChartSubStruc css=new ChartSubStruc();
				css.setIssue((String)d.get(0));
				css.setCode((String)d.get(1));
				css.setByteNumber(list);
				data.add(css);
			}
			
			for(int k=0;k<3;k++){
				List<Object> statisticsSub=new ArrayList<Object>();
				for(int i=0;i<chartStruc.getStatistics().size();i++){
					@SuppressWarnings("unchecked")
					List<Object> d=(List<Object>)chartStruc.getStatistics().get(i);
					List<Object> list=new ArrayList<Object>();
					list.addAll(d.subList(0, 20));
					list.addAll(d.subList(21, 31));
					statisticsSub.add(list.subList(k*10, (k+1)*10));
				}
				statistics.add(statisticsSub);
			}
			cs.setData(data);
			cs.setStatistics(statistics);
			return cs;
		}
		
		
}
