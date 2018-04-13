package com.winterframework.firefrog.game.lock.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.winterframework.combinatorics.Factory;
import com.winterframework.combinatorics.Generator;
import com.winterframework.combinatorics.ICombinatoricsVector;

public class LockTools {

	public static String[] getBaseNum(String abc) {
		return abc.split("(?!^|$)");
	}

	public static void printList(Map<String, Integer> list) {
		for (Map.Entry<String, Integer> obj : list.entrySet()) {
			System.out.println(obj);
		}
	}

	public static Map<String, Integer> toZ3(String betContent) {
		Map<String, Integer> list = new HashMap<String, Integer>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(LockTools.getBaseNum(betContent));
		Generator<String> gen = Factory.createPermutationGenerator(initialVector);
		for (ICombinatoricsVector<String> combination : gen) { 
			LockTools.addPkg(list,combination.stringVal());
		}

		return list;
	}  
	public static Map<String, Integer> toZ6(String betContent) {
		Map<String, Integer> list = new HashMap<String, Integer>();
		ICombinatoricsVector<String> initialVector = Factory.createVector(LockTools.getBaseNum(betContent));
		Generator<String> gen = Factory.createPermutationGenerator(initialVector);
		for (ICombinatoricsVector<String> combination : gen) {
			LockTools.addPkg(list,combination.stringVal());
		}

		return list;
	}

//	private static String z6num(List<String> vector) {
//		String num="";
//		for(String s:vector){
//			num+=s;
//		}
//		return num;
//	}

	public static void addPkg(Map<String, Integer> points, String... pkgs) {
		for (String pkg : pkgs) {
			Integer ps = points.get(pkg);
			if (ps == null) {
				points.put(pkg, 1);
			} else {
				points.put(pkg, ps + 1);
			}
		}

	}

}
