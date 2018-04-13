package com.winterframework.firefrog.phone.web.chart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public class ChartTransQiansanHandler extends AbstractChartTransHandler{ 
		private Logger log = LoggerFactory.getLogger(ChartTransQiansanHandler.class);
		
		@Override
		public  ChartStruc handle(Long lotteryId, String gameMethod, GameTrendChartStruc chartStruc) {
			ChartStruc cs=new ChartStruc();
			List<ChartSubStruc> data=new ArrayList<ChartSubStruc>();
			List<Object> statistics=new ArrayList<Object>();
			for(int i=0;i<chartStruc.getData().size();i++){
				List<Object> d=(List<Object>)chartStruc.getData().get(i);
				ChartSubStruc css=new ChartSubStruc();
				
				css.setIssue((String)d.get(0));
				css.setCode((String)d.get(1));
				css.setByteNumber(d.subList(2, 6));
				data.add(css);
			}
			for(int k=0;k<4;k++){
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
		public static void main(String[] s){
			StringBuffer sb=new StringBuffer();
			sb.append("{").append("\"isSuccess\" : 1,").append("\"zoneComment\" : null,").append("\"lotteryCode\" : null,")
			.append("\"data\" : [ [ \"20171024-070\", \"37080\", [ [ 7, 0, 1, 1 ], [ 10, 1, 1, 1 ], [ 13, 2, 1, 1 ], [ 0, 3, 3, 0 ], [ 12, 4, 1, 1 ], [ 21, 5, 1, 1 ], [ 2, 6, 1, 1 ], [ 9, 7, 1, 1 ], [ 3, 8, 1, 1 ], [ 6, 9, 1, 1 ] ], [ [ 4, 0, 1, 1 ], [ 57, 1, 1, 1 ], [ 8, 2, 1, 1 ], [ 27, 3, 1, 1 ], [ 1, 4, 1, 1 ], [ 18, 5, 1, 1 ], [ 2, 6, 1, 1 ], [ 0, 7, 3, 0 ], [ 3, 8, 1, 1 ], [ 19, 9, 1, 1 ] ], [ [ 0, 0, 3, 0 ], [ 3, 1, 1, 1 ], [ 13, 2, 1, 1 ], [ 6, 3, 1, 1 ], [ 7, 4, 1, 1 ], [ 1, 5, 1, 1 ], [ 9, 6, 1, 1 ], [ 2, 7, 1, 1 ], [ 4, 8, 1, 1 ], [ 5, 9, 1, 1 ] ], [ [ 0, 0, 1 ], [ 3, 1, 0 ], [ 8, 2, 0 ], [ 0, 3, 1 ], [ 1, 4, 0 ], [ 1, 5, 0 ], [ 2, 6, 0 ], [ 0, 7, 1 ], [ 3, 8, 0 ], [ 5, 9, 0 ] ], [ 0, 1, 0 ], [ 1, 1, 0 ], [ 1, 1, 0 ], [ 0, 1, 0 ], [ 2 ], [ 0 ], [ 13 ], \"7\", \"10\", 0 ] ],")
			.append("\"statistics\" : [ [ 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0 ], [ 7, 10, 13, 1, 12, 21, 2, 9, 3, 6, 4, 57, 8, 27, 1, 18, 2, 1, 3, 19, 1, 3, 13, 6, 7, 1, 9, 2, 4, 5, 1, 3, 8, 1, 1, 1, 2, 1, 3, 5, 2, 1, 13 ], [ 7, 10, 13, 0, 12, 21, 2, 9, 3, 6, 4, 57, 8, 27, 1, 18, 2, 0, 3, 19, 0, 3, 13, 6, 7, 1, 9, 2, 4, 5, 0, 3, 8, 0, 1, 1, 2, 0, 3, 5, 2, 0, 13 ], [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 ] ]")
			.append("}");
			
		 
			
			//Response<QueryChartResponse>
			GameTrendChartStruc c=JsonUtil.fromJson(sb.toString(), GameTrendChartStruc.class);
			
			ChartTransQiansanHandler h=new ChartTransQiansanHandler();
			ChartStruc cc= h.handle(null, null, c);
			
			System.out.println(JsonUtil.toJson(cc));


		}
		
}
