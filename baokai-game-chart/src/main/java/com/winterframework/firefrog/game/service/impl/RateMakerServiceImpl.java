package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.service.IDataMakerService;

/** 
* @ClassName: RateMakerServiceImpl 
* @Description:  大小比、单双、质合
* @author Denny 
* @date 2014-4-2 下午5:47:11 
*  
*/

@Service("rateDataMaker")
@Transactional(rollbackFor = Exception.class)
public class RateMakerServiceImpl implements IDataMakerService {
	
	private static List<String> rateValueList = new ArrayList<String>();
	static {
		rateValueList.add("5,0");
		rateValueList.add("4,1");
		rateValueList.add("3,2");
		rateValueList.add("2,3");
		rateValueList.add("1,4");
		rateValueList.add("0,5");
	}

	private List<String> numbers;
	
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

	@Override
	public List<Object> makeData(List<Object> single, List<String> list, List<List<Integer>> numberRecords, int currentIndex, long lotteryId, Integer isGeneral) {
		String[] omissions = list.get(0).split(",");
		String[] rateValue = new String[2];
		List<Integer> omissionList = new ArrayList<Integer>();
		for (int i = 0; i < omissions.length; i++) {
			omissionList.add(Integer.parseInt(omissions[i]));
			if (Integer.parseInt(omissions[i]) == 0) {
				rateValue = rateValueList.get(i).split(",");
			}
		}
		
		List<Object> result = new ArrayList<Object>();
		for(int i = 0; i < omissionList.size(); i++){
			List<Object> cell = new ArrayList<Object>();
			cell.add(omissionList.get(i));
			cell.add(Integer.parseInt(rateValue[0]));
			cell.add(Integer.parseInt(rateValue[1]));
			cell.add(isOmitBarExsit(i, numberRecords, currentIndex));
			result.add(cell);
		}
		single.add(result);
		return single;
	}

	private Integer isOmitBarExsit(int index, List<List<Integer>> numberRecords, int currentIndex){
		
		List<Integer> numberList = new ArrayList<Integer>();
		for (String s : numbers) {
			numberList.add(Integer.parseInt(s));
		}
		
		boolean flag = true;
		for(int i = numberRecords.size()-1; i > currentIndex; i-- ) {
			int numberCount = 0;
			for (Integer nr : numberRecords.get(i)) {
				if (numberList.contains(nr)) {
					numberCount++;
				}
			}
			
			if (numberCount == index) {
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
