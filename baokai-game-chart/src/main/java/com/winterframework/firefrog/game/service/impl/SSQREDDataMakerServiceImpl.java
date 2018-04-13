package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.service.IDataMakerService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lex
 * @ClassName: SSQREDDataMakerServiceImpl
 * @Description:
 * @date 2014/9/12 19:49
 */
public class SSQREDDataMakerServiceImpl implements IDataMakerService {
    @Override
    public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteyrId, Integer isGeneral) {
        //遗漏数据
        String str[] = list.get(0).split(",");
        //组合红球1区
        List<List<Integer>> redZone1 = new ArrayList<List<Integer>>();
        //大小比：小于等于16为小，大于16为大
        int small = 0, big = 0;
        //奇偶比
        int ji = 0, ou = 0;
        //和值
        int hezhi = 0;
        //区比一区：二区：三区
        int zone1 = 0;
        for (int i = 1; i <= 11; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            if (Integer.parseInt(str[i - 1]) == 0) {
                zone1++;
                small++;
                //奇偶
                if (i % 2 == 0) {
                    ou++;
                } else {
                    ji++;
                }
                //和值相加
                hezhi += i;
            }
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExit(i, currentIndex, numberRecords));
            redZone1.add(balls);
        }
        single.add(redZone1);
        //2区
        List<List<Integer>> redZone2 = new ArrayList<List<Integer>>();
        int zone2 = 0;
        for (int i = 12; i <= 22; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            if (Integer.parseInt(str[i - 1]) == 0) {
                zone2++;
                if (i < 17) {
                    small++;
                } else {
                    big++;
                }
                //奇偶
                if (i % 2 == 0) {
                    ou++;
                } else {
                    ji++;
                }
                //和值相加
                hezhi += i;
            }
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExit(i, currentIndex, numberRecords));
            redZone2.add(balls);
        }
        single.add(redZone2);
        //3区
        List<List<Integer>> redZone3 = new ArrayList<List<Integer>>();
        int zone3 = 0;
        for (int i = 23; i <= 33; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            if (Integer.parseInt(str[i - 1]) == 0) {
                zone3++;
                big++;
                //奇偶
                if (i % 2 == 0) {
                    ou++;
                } else {
                    ji++;
                }
                //和值相加
                hezhi += i;
            }
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExit(i, currentIndex, numberRecords));
            redZone3.add(balls);
        }
        single.add(redZone3);
        single.add(zone1 + ":" + zone2 + ":" + zone3);
        single.add(big + ":" + small);
        single.add(ji + ":" + ou);
        single.add(hezhi);
        String he = String.valueOf(hezhi);
        single.add(he.substring(he.length() - 1));
        return single;
    }

    /**
     * 计算遗漏
     *
     * @param ball          当前的号码球
     * @param currentIndex  当前开始的行数
     * @param numberRecords 总的中奖号码列表
     * @return
     */
    private Integer isOmitBarExit(int ball, int currentIndex, List<List<Integer>> numberRecords) {
        //遍历从当前期往后的中奖号码列表
        for (; currentIndex <( numberRecords.size()); currentIndex++) {	//不计算最后一个篮球
            //遍历中奖号码球，如果等于当前列的号码则为没遗漏  
        	List<Integer> numberRecordList=numberRecords.get(currentIndex); 
        	for (int i=0;i<numberRecordList.size()-1;i++) {	//不计算最后一个篮球
                if (ball==numberRecordList.get(i)) return 0;
            }
        }
        return 1;
    }
}
