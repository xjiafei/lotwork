package com.winterframework.firefrog.game.web.chart.formatter;

import java.util.List;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;
import com.winterframework.firefrog.game.web.chart.ChartTemplate;

/**
 * @Title 循环格式化类
 * 
 * 判断筛选的数据是否为List，如果为List则循环数据，并调用父类format方法
 * 
 * @author bob
 *
 */
public class DefaultChartLoopFormatter extends DefaultChartFormatter {
	
	@Override
	public String format(ChartEntitySupport chart, ChartTemplate template) {
		
		StringBuffer body = new StringBuffer();
		
		if (chart.getSelectedData() != null) {
			try {
				
				Object fieldObj = chart.getSelectedData();
				
				if (fieldObj == null)
					throw new NullPointerException("No loop data");
				
				if (!(fieldObj instanceof List<?>))
					return super.format(chart, template);
				
				List<?> list = (List<?>)fieldObj;
				
				for (Object data : list) {
					// 设置数据
					chart.setSelectedData(data);
					
					body.append(super.format(chart, template));
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return body.toString();
	}

}
