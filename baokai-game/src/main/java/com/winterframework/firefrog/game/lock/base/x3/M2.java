package com.winterframework.firefrog.game.lock.base.x3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;

//2码不定位
public class M2 extends SingleMethod {
	static final String[] all = "0,1,2,3,4,5,6,7,8,9".split(",");


	@Override
	public Map<String, Integer> influncePoint() {
		String[] m1s = betContent.split(",");
		Map<String, Integer> lists = new HashMap<String, Integer>();
		
		ICombinatoricsVector<String> initialVector = Factory.createVector(m1s);
		Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 2);

		for (ICombinatoricsVector<String> combination : gen) {
			for (String a : all) {
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(new String[] { a,
						combination.getValue(0), combination.getValue(1) });
				Generator<String> gen2 = Factory.createPermutationGenerator(initialVector2);
				for (ICombinatoricsVector<String> combination2 : gen2) {
					LockTools.addPkg(lists,combination2.stringVal());
				}
			}
		}

		return lists;
	}


}
