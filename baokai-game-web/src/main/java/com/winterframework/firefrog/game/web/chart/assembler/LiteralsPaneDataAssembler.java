/**   
* @Title: LiteralsPaneDataAssembler.java 
* @Package com.winterframework.firefrog.game.web.chart.assembler 
* @Description: winter-game-web.LiteralsPaneDataAssembler.java 
* @author Denny  
* @date 2014-5-8 下午5:41:56 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.chart.assembler;

import java.util.Map;

import com.winterframework.firefrog.game.web.chart.IChartBodyRowGroupPaneDataAssembler;
import com.winterframework.firefrog.game.web.chart.data.ChartBodyRowGroupPaneData;

/**
 * @Title 字面值数据组装器
 * 
 * 包括：单双、中位数、奇偶、大小几种类型
 * 
 * 直接显示字面值
 * 
 * @author Denny
 *
 */
public class LiteralsPaneDataAssembler implements IChartBodyRowGroupPaneDataAssembler {

	@Override
	public void assemble(ChartBodyRowGroupPaneData bodyItemPane, String ball, Map<String, String> styles,
			String numberRecordFormat) {

		bodyItemPane.setPane(String.valueOf(ball));
		bodyItemPane.setStyleClass(styles.get("normalStyle"));
	}

}
