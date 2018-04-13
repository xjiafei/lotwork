package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.service.IDataMakerService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lex
 * @ClassName: SSQBLUEDataMakerServiceImpl
 * @Description:
 * @date 2014/9/12 19:50
 */
public class SSQBLUEDataMakerServiceImpl implements IDataMakerService {
    @Override
    public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteyrId, Integer isGeneral) {
        List<Object> result = new ArrayList<Object>();
        result.add(single.get(0));
        String record = single.get(1).toString();
        result.add(record.substring(record.lastIndexOf(",")+1));
        //遗漏数据
        String str[] = list.get(0).split(",");
        List<List<Integer>> blueZone = new ArrayList<List<Integer>>();
        for (int i = 1; i <= 16; i++) {
            List<Integer> balls = new ArrayList<Integer>();
            //显示的中奖号码
            balls.add(Integer.parseInt(str[i - 1]));
            balls.add(i);
            //由于双色球没有色温这个数字随便给
            balls.add(2);
            //遗漏
            balls.add(isOmitBarExit(i, currentIndex, numberRecords));
            blueZone.add(balls);
        }
        result.add(blueZone);
        //单双
        List<Integer> oddEven = new ArrayList<Integer>();
        oddEven.add(Integer.parseInt(str[16]));
        oddEven.add(Integer.parseInt(str[17]));
        result.add(oddEven);

        //质和
        List<Integer> primeComposite = new ArrayList<Integer>();
        primeComposite.add(Integer.parseInt(str[18]));
        primeComposite.add(Integer.parseInt(str[19]));
        result.add(primeComposite);

        //大小
        List<Integer> size = new ArrayList<Integer>();
        size.add(Integer.parseInt(str[20]));
        size.add(Integer.parseInt(str[21]));
        result.add(size);

        //012路
        List<Integer> road = new ArrayList<Integer>();
        road.add(Integer.parseInt(str[22]));
        road.add(Integer.parseInt(str[23]));
        road.add(Integer.parseInt(str[24]));
        result.add(road);
        //蓝球数字特征
        List<Integer> blueFeature = new ArrayList<Integer>();
        blueFeature.add(Integer.parseInt(str[25]));
        blueFeature.add(Integer.parseInt(str[26]));
        blueFeature.add(Integer.parseInt(str[27]));
        blueFeature.add(Integer.parseInt(str[28]));
        blueFeature.add(Integer.parseInt(str[29]));
        blueFeature.add(Integer.parseInt(str[30]));
        result.add(blueFeature);
        //蓝球数字特征
        List<Object> blueDistribution = new ArrayList<Object>();
        List<Integer> ball = new ArrayList<Integer>();
        ball.add(Integer.parseInt(str[31]));
        ball.add(Integer.parseInt(str[32]));
        ball.add(Integer.parseInt(str[33]));
        ball.add(Integer.parseInt(str[34]));
        blueDistribution.add(ball);
        blueDistribution.add(result.get(1));
        result.add(blueDistribution);

        return result;
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
        for (; currentIndex < numberRecords.size(); currentIndex++) {
            //遍历中奖号码球(蓝球），如果等于当前列的号码则为没遗漏
        	List<Integer> numberRecordList=numberRecords.get(currentIndex);
        	if(ball==numberRecordList.get(numberRecordList.size()-1)){	//只计算最后一个篮球
        		return 0;
        	}
        }
        return 1;
    }
}
