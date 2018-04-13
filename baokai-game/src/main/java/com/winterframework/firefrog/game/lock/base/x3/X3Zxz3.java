package com.winterframework.firefrog.game.lock.base.x3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

public class X3Zxz3 extends SingleMethod{

	@Override
	public Map<String,Integer> influncePoint() {
		Map<String,Integer> list = new HashMap<String,Integer>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(betContent.split(",") );
			   Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 2);
			   for (ICombinatoricsVector<String> combination : gen) {
				   for(String number:toZ3(combination.stringVal())){					   
					   list.putAll(LockTools.toZ3(number));
				   }
			   } 
				return list;

	}
	public List<String> toZ3(String c){
		String[] numberArr=LockTools.getBaseNum(c);
		List<String> numberList=new ArrayList<String>();
		
		numberList.add(numberArr[0]+numberArr[0]+numberArr[1]); 
		numberList.add(numberArr[1]+numberArr[0]+numberArr[0]);
		numberList.add(numberArr[0]+numberArr[1]+numberArr[0]);
		numberList.add(numberArr[1]+numberArr[1]+numberArr[0]);
		numberList.add(numberArr[0]+numberArr[1]+numberArr[1]);
		numberList.add(numberArr[1]+numberArr[0]+numberArr[1]);
		return numberList;
	}
	/*public static void main(String[] args){
		LockTools.printList(new SanxingZxz3().influncePoint("3,4,5,6"));
	}*/

}
