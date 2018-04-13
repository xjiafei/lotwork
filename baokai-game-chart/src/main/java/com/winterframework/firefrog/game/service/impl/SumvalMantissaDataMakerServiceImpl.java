package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: SumvalMantissaDataMakerServiceImpl 
* @Description:  和值尾数
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("sumvalMantissaDataMaker")
@Transactional(rollbackFor = Exception.class)
public class SumvalMantissaDataMakerServiceImpl implements IDataMakerService {

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		single.add(Integer.parseInt(list.get(0)) % 10);
		return single;
	}
}
