package com.winterframework.firefrog.game.web.chart.formatter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.winterframework.firefrog.game.web.chart.ChartEntitySupport;
import com.winterframework.firefrog.game.web.chart.ChartTemplate;
import com.winterframework.firefrog.game.web.chart.IChartFormatter;

/**
 * @Title 默认格式化类
 * 
 * 在format方法中，载入模板和数据，执行格式化。
 * 
 * 以下几种关键词将会替换为对应的数据：
 * 1. @{xxx}
 * 	找到页面所有此样式的关键词，取得关键词名称xxx，通过反射找chart对象中是否有该属性
 * 	1.1 如果有：
 * 		1.1.1 如果该属性是ChartSupport的实现类，或者List<ChartSupport>实现类，则循环递归调用该属性的format方法
 * 		1.1.2 如果该属性不是ChartSupport的实现类，则使用该属性的toString方法获得字符串来替换模板中的关键词
 * 	1.2 如果没有，将该关键词替换为空
 * 
 * 2. @{data:xxx}
 * 	找到页面所有此样式的关键词，取得关键词名称xxx，通过反射找chart.selectedData中是否有该属性
 * 	2.1 如果有，则替换为对应的属性toString方法值
 * 	2.2 如果没有，则替换为空
 * 
 * 3. &lt;boolean:xxx&gt;...&lt;/boolean&gt;和&lt;boolean:!xxx&gt;...&lt;/boolean&gt;
 * 	找到页面所有此样式的关键词，取得关键词名称xxx，通过反射找chart.selectedData中是否有该boolean属性
 * 	根据boolean值判断是否显示其中的内容
 * 	
 * 
 * @author bob
 *
 */
public class DefaultChartFormatter implements IChartFormatter {

	private static Pattern pattern = Pattern.compile("@\\{(.*?)\\}");
	private static Pattern patternBoolean = Pattern.compile("<boolean:(.*?)>(.*?)</boolean>", Pattern.DOTALL);

	public String format(ChartEntitySupport chart, ChartTemplate template) throws Exception {

		String body = template != null ? template.getBody() : "";

		// 利用正则表达式找到模板中所有#{xxx}中的关键词
		List<String> keys = new ArrayList<String>();
		List<String> dataKeys = new ArrayList<String>();
		List<String> booleanKeys = new ArrayList<String>();
		
		// 找到模板中的@{xxx}和@{data:xxx}
		Matcher matcher = pattern.matcher(body);
		while (matcher.find()) {
			String key = matcher.group(1);
			if (key.startsWith("data:")) {
				dataKeys.add(key.substring(5));
			} else {
				keys.add(key);
			}
		}
		// 找到模板中的<boolean:xxx></boolean>
		matcher = patternBoolean.matcher(body);
		while (matcher.find()) {
			String booleanKey = matcher.group(1);
			if (booleanKey.startsWith("!")) {
				booleanKey = booleanKey.substring(1);
			}
			booleanKeys.add(booleanKey);
		}
		
		// 判断和替换<boolean:first></boolean>和<boolean:last></boolean>
		if (booleanKeys.size() > 0 && chart.getSelectedData() != null) {
			
			for (String booleanKey : booleanKeys) {
				
				Field field = chart.getSelectedData().getClass().getDeclaredField(booleanKey);
				
				//如果属性为不可访问，则设置为可访问
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				
				Object fieldObj = field.get(chart.getSelectedData());
				
				if (fieldObj != null && fieldObj instanceof Boolean) {
					Boolean bool = (Boolean)fieldObj;
					
					StringBuffer proceedBody = new StringBuffer();
					
					Matcher m = patternBoolean.matcher(body);
					int lastStart = 0;
					while (m.find()) {
						proceedBody.append(body.substring(lastStart, m.start()));
						String boolKey = m.group(1);
						String content = m.group(2);
						proceedBody.append(bool && boolKey.startsWith("!") ? "" : content);
						lastStart = m.end();
					}
					proceedBody.append(body.substring(lastStart));
					body = proceedBody.toString();
				}				
			}
		}
		
		// 替换@{data:xxx}
		if (dataKeys.size() > 0 && chart.getSelectedData() != null) {
			
			for (String dataKey : dataKeys) {
				
				Object data = chart.getSelectedData();
				
				Field field = data.getClass().getDeclaredField(dataKey);
				
				//如果属性为不可访问，则设置为可访问
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				
				Object fieldObj = field.get(data);
				
				if (fieldObj != null) {
					body = StringUtils.replace(body, "@{data:" + dataKey + "}", fieldObj.toString());
				}
			}
		}

		// 替换@{xxx}
		if (keys.size() > 0) {
			// 判断关键词对应于chart中的类型
			for (String key : keys) {
				
				Field field = chart.getClass().getDeclaredField(key);
				
				//如果属性为不可访问，则设置为可访问
				if (!field.isAccessible()) {
					field.setAccessible(true);
				}
				
				//获得属性对象
				Object fieldObj = field.get(chart);
				
				if (fieldObj != null) {
					
					String replacement = "";
					
					if (fieldObj instanceof ChartEntitySupport) {

						//如果属性是IChart类型，则调用format方法
						replacement = ((ChartEntitySupport)fieldObj).format(chart.getSelectedData());
						
					} else if (fieldObj instanceof List<?> && ((List<?>)fieldObj).size() > 0 && ((List<?>)fieldObj).get(0) instanceof ChartEntitySupport) {

						//如果属性是List<IChart>类型，则循环所有的列表对象，调用format方法
						StringBuffer block = new StringBuffer();
						
						@SuppressWarnings("unchecked")
						List<ChartEntitySupport> list = (List<ChartEntitySupport>)fieldObj;
						
						for (ChartEntitySupport childChart : list) {
							block.append(childChart.format(chart.getSelectedData()));
						}
						
						replacement = block.toString();
					} else {
						//如果是其他对象，则调用对象的toString方法
						replacement = fieldObj.toString();
					}
					
					// 替换掉模板中的关键字
					body = StringUtils.replace(body, "@{" + key + "}", replacement);
						
				}
			}
		}
		
		return body;
	}

}
