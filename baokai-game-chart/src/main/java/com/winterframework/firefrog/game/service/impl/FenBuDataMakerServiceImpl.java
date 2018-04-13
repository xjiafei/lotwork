package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.service.IDataMakerService;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: WeiShuDataMakerServiceImpl 
* @Description:  位数分布的数据转换
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("fenBuDataMaker")
@Transactional(rollbackFor = Exception.class)
public class FenBuDataMakerServiceImpl implements IDataMakerService {
	
	@Resource(name = "ballRengeMap")
	private Map<String, String> ballRengeMap;
	
	@PropertyConfig(value = "lottery.BJKL8")
	private Long BJKL8;
	
	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		List<Object> result = new ArrayList<Object>();
		
		String[] trends = list.get(0).split(",");
		String[] numberRange = ballRengeMap.get(String.valueOf(lotteryId)).split(",");
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = Integer.parseInt(numberRange[0]); i <= Integer.parseInt(numberRange[1]); i++) {
			numbers.add(i);
		}
		
		List<Integer> resultList = numberRecords.get(currentIndex);
		for (int i = 0; i < trends.length; i++) {
			List<Integer> bitResult = new ArrayList<Integer>();
			bitResult.add(Integer.valueOf(trends[i]));
			bitResult.add(numbers.get(i));
			bitResult.add(getBit3Value(resultList, numbers.get(i)));
			if ((isGeneral != null && isGeneral == 0) || lotteryId == BJKL8) {
				bitResult.add(isOmitBarExsit(numbers.get(i), numberRecords, currentIndex));
			}
			result.add(bitResult);
		}
		single.add(result);
		return single;
	}

	private Integer isOmitBarExsit(Integer ballNumber, List<List<Integer>> numberRecords, int currentIndex) {
		boolean flag = true;
		for (int i = numberRecords.size()-1; i > currentIndex; i-- ) {
			if (numberRecords.get(i).contains(ballNumber)) {
				flag = false;
				break;
			}
		}
		
		if (flag) {
			return 1;
		}
		
		return 0;
	}

	private Integer getBit3Value(List<Integer> resultList, Integer number) {
		if (resultList.contains(number)) {
			int exist = 0;
			for (Integer result : resultList) {
				if (result.intValue() == number) {
					exist++;
				}
			}
			return exist > 1 ? 2 : 1;
		} else {
			return 0;
		}
	}

}
