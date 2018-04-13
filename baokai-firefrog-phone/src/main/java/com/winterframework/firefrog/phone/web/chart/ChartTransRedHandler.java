package com.winterframework.firefrog.phone.web.chart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public class ChartTransRedHandler extends AbstractChartTransHandler{ 
		private Logger log = LoggerFactory.getLogger(ChartTransRedHandler.class);
		
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
				css.setByteNumber(d.subList(2, 5));
				data.add(css);
			}
			
			for(int k=0;k<3;k++){
				List<Object> statisticsSub=new ArrayList<Object>();
				for(int i=0;i<chartStruc.getStatistics().size();i++){
					List<Object> d=(List<Object>)chartStruc.getStatistics().get(i);
					statisticsSub.add(d.subList(k*11, (k+1)*11));
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
			.append("\"data\":[[\"2017094\",\"08,11,13,19,28,31,06\",[[4,1,2,0],[12,2,2,0],[2,3,2,0],[12,4,2,0],[3,5,2,0],[2,6,2,0],[1,7,2,0],[0,8,2,0],[1,9,2,0],[12,10,2,0],[0,11,2,0]],[[9,12,2,0],[0,13,2,0],[2,14,2,0],[1,15,2,0],[4,16,2,0],[12,17,2,0],[7,18,2,0],[0,19,2,0],[3,20,2,0],[7,21,2,0],[1,22,2,0]],[[10,23,2,0],[5,24,2,0],[4,25,2,0],[6,26,2,1],[1,27,2,0],[0,28,2,0],[7,29,2,0],[4,30,2,0],[0,31,2,0],[12,32,2,0],[12,33,2,0]],\"2:2:2\",\"3:3\",\"4:2\",110,\"0\"]],")
			.append("\"statistics\":[[18,17,15,4,2,2,2,5,2,18,3,4,16,4,4,2,2,4,5,4,2,2,4,1,2,0,2,3,3,4,5,2,17],[1,1,2,7,15,15,15,6,15,1,10,7,1,7,7,15,15,7,6,7,15,15,7,30,15,9,15,10,10,7,6,15,1],[9,13,7,20,19,20,16,15,17,12,20,16,5,15,16,15,17,15,19,16,15,19,15,19,18,20,18,20,20,17,18,17,15],[2,1,0,1,1,1,1,1,1,1,1,2,1,1,0,1,0,1,0,2,1,1,2,0,1,0,1,1,2,2,1,1,1]]")
			.append("}");
			
		 
			
			//Response<QueryChartResponse>
			GameTrendChartStruc c=JsonUtil.fromJson(sb.toString(), GameTrendChartStruc.class);
			
			ChartStruc cc= new ChartTransRedHandler().handle(null, null, c);
			
			System.out.println(JsonUtil.toJson(cc));


		}
		
		
		
		
}
