package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: NumberDataMakerServiceImpl 
* @Description:  数值：和值值、跨度值等数值
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("numberDataMaker")
@Transactional(rollbackFor = Exception.class)
public class NumberDataMakerServiceImpl implements IDataMakerService {

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		single.add(list.get(0));
		return single;
	}
}
