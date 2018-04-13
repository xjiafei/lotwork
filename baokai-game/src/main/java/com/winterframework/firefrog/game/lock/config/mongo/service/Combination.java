package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Combination {
	
	private ArrayList<String> combList= new ArrayList<String>();

	public void mn(String[] array, int n) {
		int m = array.length;
		if (m < n)
			throw new IllegalArgumentException("Error   m   <   n");
		BitSet bs = new BitSet(m);
		for (int i = 0; i < n; i++) {
			bs.set(i, true);
		}
		do {
			printAll(array, bs);
		} while (moveNext(bs, m));

	}
	
	public List<String> mn(String[] array, int n, String[] blueAarry) {
		
		mn(array, n);
		
		List<String> ssqList = new ArrayList<String>();
		for(String blue : blueAarry){
			
			for(String s : getCombList()){
				
				ssqList.add(s + "|"+ blue);
			}
		}
		
		return ssqList;
	}
	
	private boolean moveNext(BitSet bs, int m) {
		int start = -1;
		while (start < m)
			if (bs.get(++start))
				break;
		if (start >= m)
			return false;

		int end = start;
		while (end < m)
			if (!bs.get(++end))
				break;
		if (end >= m)
			return false;
		for (int i = start; i < end; i++)
			bs.set(i, false);
		for (int i = 0; i < end - start - 1; i++)
			bs.set(i);
		bs.set(end);
		return true;
	}
	
	private void printAll(String[] array, BitSet bs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++)
			if (bs.get(i)) {
				sb.append(array[i]).append(',');
			}
		sb.setLength(sb.length() - 1);
		combList.add(sb.toString());
	}
	
	public ArrayList<String> getCombList() {
		return combList;
	}

	public static void main(String[] args) throws Exception {
		Combination comb = new Combination();
		List<String> list = comb.mn(new String[]{"01","02","03","04","05","06"}, 5, new String[]{"01","03"});
		for(String str: list){
			System.out.println(str);
		}
		String str = "D:01,02,04T:02,03,04";
		String[] strs = str.split("T:");
		
		System.out.println(strs[0].split("D:")[1]);
		
		
	}
	
}