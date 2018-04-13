package com.winterframework.firefrog.phone.web.chart;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.common.util.JsonUtil;
import com.winterframework.firefrog.game.web.dto.GameTrendChartStruc;

public class ChartTransK3Handler extends AbstractChartTransHandler{ 
		private Logger log = LoggerFactory.getLogger(ChartTransK3Handler.class);
		
		@Override
		public ChartStruc handle(Long lotteryId, String gameMethod, GameTrendChartStruc chartStruc) {
			ChartStruc cs=new ChartStruc();
			List<ChartSubStruc> data=new ArrayList<ChartSubStruc>();
			for(int i=0;i<chartStruc.getData().size();i++){
				List<Object> d=(List<Object>)chartStruc.getData().get(i);
				ChartSubStruc css=new ChartSubStruc();
				css.setIssue((String)d.get(0));
				css.setCode((String)d.get(1));
				css.setByteNumber(makeDataFenbuAndHezhi((List<Object>)d.get(2),(List<Object>)d.get(3)));
				data.add(css);
			}
			cs.setData(data);
			cs.setStatistics(makeDataStatistics(chartStruc.getData()));
			return cs;
		}
		private List<Object> makeDataFenbuAndHezhi(List<Object> fenbu,List<Object> hezhi){
			List<Object> data=new ArrayList<Object>();
			List<Object> fenbuData=new ArrayList<Object>();
			List<Object> fenbuSubData=null;
			List<Object> hezhiData=new ArrayList<Object>();
			List<Object> hezhiSubData=null;
			for(int i=0;i<fenbu.size();i++){
				fenbuSubData=new ArrayList<Object>();
				fenbuSubData.add(fenbu.get(i));
				fenbuSubData.add(i);
				fenbuSubData.add(0);	//号温
				fenbuSubData.add(0);	//遗漏条
				fenbuData.add(fenbuSubData);
			}
			for(int i=0;i<hezhi.size();i++){
				hezhiSubData=new ArrayList<Object>();
				hezhiSubData.addAll((List<Object>)hezhi.get(i));
				hezhiSubData.add(0);	//号温
				hezhiSubData.add(0);	//遗漏条
				hezhiData.add(hezhiSubData);
			}
			data.add(fenbuData);
			data.add(hezhiData);
			return data;
		}
		private List<Object> makeDataStatistics(List<Object> pdata){
			List<Object> data=new ArrayList<Object>();
			int[] countFenbu=new int[6];
			int[] countHezhi=new int[16];
			int[] avgFenbu=new int[6];
			int[] avgHezhi=new int[16];
			int[] maxFenbu=new int[6];
			int[] maxHezhi=new int[16];
			int[] maxContiFenbu=new int[6];
			int[] maxContiHezhi=new int[16];
			int[] contiFenbu=new int[6];
			int[] contiHezhi=new int[16];
			for(int i=0;i<pdata.size();i++){
				List<Object> d=(List<Object>)pdata.get(i);
				List<Integer> fenbuData=(List<Integer>)d.get(2);
				for(int j=0;j<fenbuData.size();j++){
					if(fenbuData.get(j)==0){
						countFenbu[j]++;
						contiFenbu[j]++;
					}else{
						maxContiFenbu[j]=contiFenbu[j];
						contiFenbu[j]=0;
					}
					avgFenbu[j]+=fenbuData.get(j);
					if(maxFenbu[j]<fenbuData.get(j)){
						maxFenbu[j]=fenbuData.get(j);
					}
				}
				List<Object> hezhiData=(List<Object>)d.get(3);
				int k=0;	//fenbuData.size();
				for(int j=0;j<hezhiData.size();j++){
					List<Integer> hezhiSubData=(List<Integer>)hezhiData.get(j);
					if(hezhiSubData.get(0)==0){
						countHezhi[k+j]++;
						contiHezhi[k+j]++;
					}else{
						maxContiHezhi[k+j]=contiHezhi[k+j];
						contiHezhi[k+j]=0;
					}
					avgHezhi[k+j]+=hezhiSubData.get(0);
					if(maxHezhi[k+j]<hezhiSubData.get(0)){
						maxHezhi[k+j]=hezhiSubData.get(0);
					}
				}
			}
			for(int j=0;j<6;j++){
				if(maxContiFenbu[j]<contiFenbu[j]){
					maxContiFenbu[j]=contiFenbu[j];
				}
			}
			for(int j=0;j<6;j++){
				avgFenbu[j]=avgFenbu[j]/pdata.size();
			}
			for(int j=6;j<16;j++){
				if(maxContiHezhi[j]<contiHezhi[j]){
					maxContiHezhi[j]=contiHezhi[j];
				}
			}
			for(int j=6;j<16;j++){
				avgHezhi[j]=avgHezhi[j]/pdata.size();
			}
			List<Object> fenbu=new ArrayList<Object>();
			List<Object> hezhi=new ArrayList<Object>();
			fenbu.add(countFenbu);
			fenbu.add(avgFenbu);
			fenbu.add(maxFenbu);
			fenbu.add(maxContiFenbu);
			
			hezhi.add(countHezhi);
			hezhi.add(avgHezhi);
			hezhi.add(maxHezhi);
			hezhi.add(maxContiHezhi);
			data.add(fenbu);
			data.add(hezhi);
			return data;
		}
		
		public static void main(String[] s){
			StringBuffer sb=new StringBuffer();
			sb.append("{").append("\"isSuccess\" : 1,").append("\"zoneComment\" : null,").append("\"lotteryCode\" : null,")
			.append("\"data\" : [ [ \"20171025-033\", \"535\", [ 2, 1, 0, 6, 0, 3 ], [ [ 92, 1 ], [ 16, 1 ], [ 2, 1 ], [ 9, 1 ], [ 5, 1 ], [ 42, 1 ], [ 1, 1 ], [ 3, 1 ], [ 10, 1 ], [ 11, 1 ], [ 0, 1 ], [ 8, 1 ], [ 6, 1 ], [ 50, 1 ], [ 171, 1 ], [ 47, 1 ] ], [ 1, 3, 0, 8 ], [ 15, 3, 29, 0, 0, 0 ] ] ],")
			.append("\"statistics\" : [ [ ], [ ], [ ], [ ] ]")
			.append("}");
			
		 
			
			//Response<QueryChartResponse>
			GameTrendChartStruc c=JsonUtil.fromJson(sb.toString(), GameTrendChartStruc.class);
			
			ChartStruc cc= new ChartTransK3Handler().handle(null, null, c);
			
			System.out.println(JsonUtil.toJson(cc));


		}
		
		

}
