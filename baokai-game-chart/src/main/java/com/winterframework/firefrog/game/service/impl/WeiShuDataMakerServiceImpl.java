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

@Service("weiShuDataMaker")
@Transactional(rollbackFor = Exception.class)
public class WeiShuDataMakerServiceImpl implements IDataMakerService {
	
	@Resource(name = "ballRengeMap")
	private Map<String, String> ballRengeMap;

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		
		String[] numberRange = ballRengeMap.get(String.valueOf(lotteryId)).split(",");
		List<Integer> numbers = new ArrayList<Integer>();
		for (int i = Integer.parseInt(numberRange[0]); i <= Integer.parseInt(numberRange[1]); i++) {
			numbers.add(i);
		}
		
		List<List<Integer>> eachBitJson = null;
		List<Integer> eachBit = null;
		for (int j = 0; j < list.size(); j++) {
			eachBit = new ArrayList<Integer>();
			String[] arr = list.get(j).split(",");
			for (String a : arr) {
				eachBit.add(Integer.parseInt(a));
			}
			
			eachBitJson = new ArrayList<List<Integer>>();
			List<Integer> eachBitJsonCell = null;
			for (int i = 0; i < eachBit.size(); i++) {
				eachBitJsonCell = new ArrayList<Integer>();
				eachBitJsonCell.add(eachBit.get(i));
				eachBitJsonCell.add(numbers.get(i));
				eachBitJsonCell.add(isColdOrHot(numbers.get(i), j, numberRecords));
                if (eachBit.get(i) == 0) {
                    eachBitJsonCell.add(0);
                } else {
                    eachBitJsonCell.add(isOmitBarExsit(numbers.get(i), j, numberRecords, currentIndex));
                }
				eachBitJson.add(eachBitJsonCell);
			}
			
			single.add(eachBitJson);
		}
		
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
			currentBitNumberRecordList.add(list.get(bitNumber));
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

	/** 
	* @Title: isColdOrHot 
	* @Description: 取号温值：1，冷号；2，温号；3，热号 (选取规则：出现次数最多的3个为热号，其次4个是温号，剩下的是冷号)
	* @param   设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	private Integer isColdOrHot(int ballNumber, int bitNumber, List<List<Integer>> numberRecords) {

		List<Integer> hotNumbers = new ArrayList<Integer>();
		List<Integer> warmNumbers = new ArrayList<Integer>();
		
		// 当前位数上的所有开奖号码
		List<Integer> currentBitNumberRecordList = new ArrayList<Integer>();
		for (List<Integer> list : numberRecords) {
			currentBitNumberRecordList.add(list.get(bitNumber));
		}
		
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (Integer n : currentBitNumberRecordList) {
			if (map.containsKey(n)) {
				map.put(n, map.get(n)+1);
			} else {
				map.put(n, 1);
			}
		}
		
		List<Integer> frequencyNumbers = new ArrayList<Integer>();
		for (Integer n : map.keySet()) {
			frequencyNumbers.add(map.get(n));
		}
		Integer[] orderedFrequencyNumbers = frequencyNumbers.toArray(new Integer[]{});
		Arrays.sort(orderedFrequencyNumbers);
		
		List<Integer> orderedNumbers = new ArrayList<Integer>();
		for (Integer n : orderedFrequencyNumbers) {
			for (Integer m : map.keySet()) {
				if (map.get(m) == n) {
					orderedNumbers.add(m);
				}
			}
		}
		
		for (int i = orderedNumbers.size()-1; i >= 0; i--) {
			if (hotNumbers.size() < 3) {
				hotNumbers.add(orderedNumbers.get(i));
			} else if (warmNumbers.size() < 4) {
				warmNumbers.add(orderedNumbers.get(i));
			} else {
				break;
			}
		}
		
		if (hotNumbers.contains(ballNumber)) {
			return 3;
		} else if (warmNumbers.contains(ballNumber)) {
			return 2;
		} 
		
		return 1;
	}

	
}
