package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: ZuXuanDataMakerServiceImpl 
* @Description:  组选类型的数据转换
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("zuXuanDataMaker")
@Transactional(rollbackFor = Exception.class)
public class ZuXuanDataMakerServiceImpl implements IDataMakerService {

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		String number = list.get(0);
		if (number.contains(",")) {
			String[] arr = number.split(",");
			for (String n : arr) {
				List<Integer> s = new ArrayList<Integer>();
				s.add(Integer.valueOf(n));
				single.add(s);
			}
		} else {
			List<Integer> s = new ArrayList<Integer>();
			s.add(Integer.valueOf(number));
			single.add(s);
		}
		return single;
	}
}
