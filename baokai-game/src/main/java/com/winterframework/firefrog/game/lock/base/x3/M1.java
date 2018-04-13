package com.winterframework.firefrog.game.lock.base.x3;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;
import com.winterframework.firefrog.game.lock.base.LockTools;
import com.winterframework.firefrog.game.lock.base.SingleMethod;
//一码不定位
public class M1 extends SingleMethod {

	@Override
	public Map<String, Integer> influncePoint() {
		String[] m1s = betContent.split(",");
		Map<String, Integer> lists = new HashMap<String, Integer>();
		
		ICombinatoricsVector<String> initialVector = Factory.createVector(new String[] { "0", "1", "2", "3", "4", "5",
				"6", "7", "8", "9" });
		Generator<String> gen = Factory.createSimpleCombinationGenerator(initialVector, 2);
		for (ICombinatoricsVector<String> combination : gen) {
			for (String m1 : m1s) {
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(new String[] { m1,
						combination.getValue(0), combination.getValue(1) });
				Generator<String> gen2 = Factory.createPermutationGenerator(initialVector2);
				for (ICombinatoricsVector<String> combination2 : gen2) {
					LockTools.addPkg(lists,combination2.stringVal());
				}
			}
		}
		Generator<String> gen2 = Factory.createSimpleCombinationGenerator(initialVector, 1);
		for (ICombinatoricsVector<String> combination : gen2) {
			for (String m1 : m1s) {
				ICombinatoricsVector<String> initialVector2 = Factory.createVector(new String[] { m1,
						combination.getValue(0), combination.getValue(0) });
				Generator<String> gen3 = Factory.createPermutationGenerator(initialVector2);
				for (ICombinatoricsVector<String> combination3 : gen3) {
					LockTools.addPkg(lists,combination3.stringVal());
				}
			}
		}
		return lists;
	}

	public static void main(String[] s) {
		M1 sm = new M1();
		sm.setBetContent("1");
		LockTools.printList(sm.influncePoint());
	}
}
