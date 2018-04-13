package com.winterframework.firefrog.game.web.bet.operator.impl;

import com.winterframework.firefrog.game.web.dto.GameIssueQueryResponse;

/**
 * @author Lex
 * @ClassName: Fc3dBetOperator
 * @Description: 3D操作类
 * @date 2014/4/8 17:16
 */
public class Fc3dBetOperator extends AbstractBetOperator {
    @Override
    protected String formatLastBalls(GameIssueQueryResponse gameIssue) {
        String record = gameIssue.getNumberRecord();
        if (record != null) {
            StringBuffer sb = new StringBuffer();
            char[] chars = record.toCharArray();
            for (int i = 0, size = chars.length; i < size; i++) {
                sb.append(chars[i]);
                if (i < size - 1) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return "1,2,3";
    }
}
