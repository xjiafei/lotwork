package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.service.IDataMakerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Lex
 * @ClassName: SSQDataMakerServiceImpl
 * @Description: 双色球数据构造
 * @date 2014/9/12 13:52
 */
@Service("ssqDataMaker")
@Transactional(rollbackFor = Exception.class)
public class SSQDataMakerServiceImpl implements IDataMakerService {
    @Override
    public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteyrId, Integer isGeneral) {
        //遗漏数据
        String str[] = list.get(0).split(",");
        //组合红球1区
        List<List<Integer>> redZone1 = new ArrayList<List<Integer>>();
        for (int i = 1; i <= 11; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExitRed(i, currentIndex, numberRecords));
            redZone1.add(balls);
        }
        single.add(redZone1);
        //2区
        List<List<Integer>> redZone2 = new ArrayList<List<Integer>>();
        for (int i = 12; i <= 22; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExitRed(i, currentIndex, numberRecords));
            redZone2.add(balls);
        }
        single.add(redZone2);
        //3区
        List<List<Integer>> redZone3 = new ArrayList<List<Integer>>();
        for (int i = 23; i <= 33; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExitRed(i, currentIndex, numberRecords));
            redZone3.add(balls);
        }
        single.add(redZone3);

        //篮球1区
        List<List<Integer>> blueZone1 = new ArrayList<List<Integer>>();
        for (int i = 1; i <= 8; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[32 + i]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExitBlue(i, currentIndex, numberRecords));
            blueZone1.add(balls);
        }
        single.add(blueZone1);

        //篮球2区
        List<List<Integer>> blueZone2 = new ArrayList<List<Integer>>();
        for (int i = 9; i <= 16; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[32 + i]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExitBlue(i, currentIndex, numberRecords));
            blueZone2.add(balls);
        }
        single.add(blueZone2);
        return single;
    }

    /**
     * 判断是否显示球，1不显示，0显示
     *
     * @param ball
     * @param arry
     * @return
     */
    private Integer show(int ball, String[] arry) {
        for (int i = 0; i < arry.length; i++) {
            if (Integer.parseInt(arry[i]) == ball) return 0;
        }
        return 1;
    }

    /**
     * 计算遗漏
     *
     * @param ball          当前的号码球
     * @param currentIndex  当前开始的行数
     * @param numberRecords 总的中奖号码列表
     * @return
     */
    private Integer isOmitBarExitRed(int ball, int currentIndex, List<List<Integer>> numberRecords) {
        //遍历从当前期往后的中奖号码列表
        for (; currentIndex < numberRecords.size(); currentIndex++) {
            //遍历中奖号码球，如果等于当前列的号码则为没遗漏
            for (int i = 0; i < 6; i++) {
                if (numberRecords.get(currentIndex).get(i) == ball) return 0;
            }
        }
        return 1;
    }
    private Integer isOmitBarExitBlue(int ball, int currentIndex, List<List<Integer>> numberRecords) {
        //遍历从当前期往后的中奖号码列表
        for (; currentIndex < numberRecords.size(); currentIndex++) {
            //遍历中奖号码球，如果等于当前列的号码则为没遗漏
            if (numberRecords.get(currentIndex).get(6) == ball) return 0;
        }
        return 1;
    }
}
