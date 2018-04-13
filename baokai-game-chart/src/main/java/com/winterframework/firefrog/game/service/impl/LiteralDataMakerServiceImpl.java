package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: LiteralDataMakerServiceImpl 
* @Description:  字面值：大小、单双、质合、零一二等字面量
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("literalDataMaker")
@Transactional(rollbackFor = Exception.class)
public class LiteralDataMakerServiceImpl implements IDataMakerService {

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		if (list.get(0).contains(":")) {
			String[] arr = list.get(0).split(":");
			List<Integer> l = new ArrayList<Integer>();
			for (String s : arr) {
				l.add(Integer.parseInt(s));
			}
			single.add(l);
		} else if (list.get(0).contains(","))  {
			String[] arr = list.get(0).split(",");
			List<Integer> l = new ArrayList<Integer>();
			for (String s : arr) {
				l.add(Integer.parseInt(s));
			}
			single.add(l);
		} else {
			single.add(list.get(0));
		}
		
		return single;
	}
}
