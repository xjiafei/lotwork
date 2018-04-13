/**   
* @Title: Kl8DataMakerServiceImpl.java 
* @Package com.winterframework.firefrog.game.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-6-12 下午6:22:40 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.constance.ServiceConstance;
import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: Kl8DataMakerServiceImpl 
* @Description:  kl8 大小单双上下奇偶和值
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("kl8DataMaker")
@Transactional(rollbackFor = Exception.class)
public class Kl8DataMakerServiceImpl implements IDataMakerService {

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		String[] valueArray = list.get(0).split(",");
		single.add(valueArray[2].equals(String.valueOf(ServiceConstance.DA)) ? 1 : 0);
		single.add(valueArray[3].equals(String.valueOf(ServiceConstance.DAN)) ? 1 : 0);
		single.add(valueArray[0].equals(String.valueOf(ServiceConstance.SHANG)) ? 2 : valueArray[0].equals(String
				.valueOf(ServiceConstance.ZHONG)) ? 1 : 0);
		single.add(valueArray[1].equals(String.valueOf(ServiceConstance.JI)) ? 0 : valueArray[1].equals(String
				.valueOf(ServiceConstance.HE)) ? 1 : 2);
		single.add(Integer.parseInt(valueArray[4]));
		return single;
	}

}
