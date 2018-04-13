package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 开奖号码组装器
 * 
 * 开奖号码部分高亮显示，格式如：**###, *####
 * 其中，“*”表示非高亮号码，“#”表示高亮号码
 * 
 * @author bob
 *
 */
public class NumberRecordPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles, String numberRecordFormat) {
		
		String [] balls = ball.split("#");
		
		
		if (numberRecordFormat == null || numberRecordFormat.length() != balls.length) {
			StringBuilder ballsValue = new StringBuilder();
			for (String s : balls) {
				ballsValue.append(s);
			}
			bodyItemPane.setPane(styles.get("numberRecordHighlightStart") + ballsValue.toString() + styles.get("numberRecordHighlightEnd"));
		} else {
			StringBuffer formatted = new StringBuffer();
			boolean foundHighlight = false;
			for (int i = 0; i < numberRecordFormat.length(); i++) {
				if (!foundHighlight && numberRecordFormat.charAt(i) == '#') {
					formatted.append(styles.get("numberRecordHighlightStart"));
					foundHighlight = true;
				} 
				if (foundHighlight && numberRecordFormat.charAt(i) == '*') {
					formatted.append(styles.get("numberRecordHighlightEnd"));
					foundHighlight = false;
				}
				formatted.append(balls[i]);
			}
			if (foundHighlight) {
				formatted.append(styles.get("numberRecordHighlightEnd"));
			}
			bodyItemPane.setPane(formatted.toString());	
		}
		

		bodyItemPane.setStyleClass("");
	}

}
