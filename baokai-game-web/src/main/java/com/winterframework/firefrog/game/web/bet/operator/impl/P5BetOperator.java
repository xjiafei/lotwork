package com.winterframework.firefrog.game.web.bet.operator.impl;

import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;

/**
 * @author Lex
 * @ClassName: P5BetOperator
 * @Description: P3/P5操作类
 * @date 2014/4/28 10:15
 */
public class P5BetOperator extends AbstractBetOperator {
	@Override
	protected String formatLastBalls(GameIssueQueryResponse gameIssue) {
		// 为了在第一期时正常显示页面，初始化上期开奖号码
		String formatedLastBalls = "1,2,3,4,5";

		//将开奖号码用逗号分隔
		if (gameIssue.getNumberRecord() != null) {//测试阶段 初始化数据时这里的获取可能为null
			char[] repeatChars = gameIssue.getNumberRecord().toCharArray();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < repeatChars.length; i++) {
				sb.append(repeatChars[i]);
				if (i != repeatChars.length - 1) {
					sb.append(",");
				}
			}
			formatedLastBalls = sb.toString();//获取上期开奖号码
		}

		return formatedLastBalls;
	}
}
