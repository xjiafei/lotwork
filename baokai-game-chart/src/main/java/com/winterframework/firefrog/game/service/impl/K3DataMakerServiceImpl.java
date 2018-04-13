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

@Service("k3DataMaker")
@Transactional(rollbackFor = Exception.class)
public class K3DataMakerServiceImpl implements IDataMakerService {
	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		
		String[] trends = list.get(0).split(",");
		Long[] trendsl = new Long[trends.length];
		for(int i=0;i<trendsl.length;i++){
			trendsl[i]=Long.valueOf(trends[i]);
		}
		single.add(trendsl);
		return single;
	}
}
