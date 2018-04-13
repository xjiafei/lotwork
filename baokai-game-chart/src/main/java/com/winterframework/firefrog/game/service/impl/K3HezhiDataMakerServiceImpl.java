package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.service.IDataMakerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/** 
* @ClassName: WeiShuDataMakerServiceImpl 
* @Description:  位数分布的数据转换
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("k3HezhiDataMaker")
@Transactional(rollbackFor = Exception.class)
public class K3HezhiDataMakerServiceImpl implements IDataMakerService {
	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		
		String[] numberRange = {"3","18"};
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = Integer.parseInt(numberRange[0]); i <= Integer.parseInt(numberRange[1]); i++) {
			numbers.add(i);
		}
		
		List<List<Integer>> eachBitJson = new ArrayList<List<Integer>>();;
		List<Integer> eachBit = null;
		for (int j = 0; j < list.get(0).split(",").length; j++) {
			eachBit = new ArrayList<Integer>();
			eachBit.add(Integer.valueOf(list.get(0).split(",")[j]));
			eachBit.add(isOmitBarExsit(numbers.get(j), j, numberRecords, currentIndex));
			eachBitJson.add(eachBit);
		}
		single.add(eachBitJson);
		return single;
	}

	/** 
	* @Title: isOmitBarExsit
	* @Description: 是否有遗漏条：0，没有；1，有
	* @return Integer    返回类型
	* @throws 
	*/
	private Integer isOmitBarExsit(int ballNumber, int bitNumber, List<List<Integer>> numberRecords, int currentIndex) {
		
		// 当前位数上的所有开奖号码
		List<Integer> currentBitNumberRecordList = new ArrayList<Integer>();
		for (List<Integer> list : numberRecords) {
			currentBitNumberRecordList.add(list.get(0)+list.get(1)+list.get(2));
		}
		
		boolean flag = true;
		for (int i = currentBitNumberRecordList.size()-1; i > currentIndex; i-- ) {
			if (currentBitNumberRecordList.get(i) == ballNumber) {
				flag = false;
				break;
			}
		}
		
		if (flag) {
			return 1;
		}
		
		return 0;
	}
}
