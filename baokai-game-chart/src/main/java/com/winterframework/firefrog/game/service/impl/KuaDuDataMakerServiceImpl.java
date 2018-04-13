package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: KuaDuDataMakerServiceImpl 
* @Description:  跨度的数据转换
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("kuaDuDataMaker")
@Transactional(rollbackFor = Exception.class)
public class KuaDuDataMakerServiceImpl implements IDataMakerService {

	@Resource(name = "kuaduRengeMap")
	private Map<String, String> kuaduRengeMap;
	
	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		List<Object> result = new ArrayList<Object>();
		String[] omissions = list.get(0).split(",");
		String[] kuaduRange = kuaduRengeMap.get(String.valueOf(lotteryId)).split(",");
		List<Integer> kuaduValues = new ArrayList<Integer>();
		for (int i = Integer.parseInt(kuaduRange[0]); i <= Integer.parseInt(kuaduRange[1]); i++) {
			kuaduValues.add(i);
		}
		
		for (int i = 0; i < omissions.length; i++) {
			List<Integer> bitResult = new ArrayList<Integer>();
			bitResult.add(Integer.valueOf(omissions[i]));
			bitResult.add(kuaduValues.get(i));
			if (isGeneral != null && isGeneral == 0) {
				bitResult.add(isOmitBarExsit(Integer.valueOf(omissions[i]), numberRecords, currentIndex));
			}
			
			result.add(bitResult);
		}
		single.add(result);
		return single;
	
	}

	private Integer isOmitBarExsit(Integer kuaduValue, List<List<Integer>> numberRecords, int currentIndex) {
		
		boolean flag = true;
		for (int i = numberRecords.size()-1; i > currentIndex; i-- ) {
			int eachKuaduValue = calcKuaduValue(numberRecords.get(i));
			if (eachKuaduValue == kuaduValue) {
				flag = false;
				break;
			}
		}
		
		if (flag) {
			return 1;
		}
		
		return 0;
	}

	private int calcKuaduValue(List<Integer> list) {
		
		int max = -1;
		int min = 100;
		for (int i : list) {
			if (i > max) {
				max = i;
			}
			if (i < min) {
				min = i;
			}
		}
		
		return max - min;
	}
}
