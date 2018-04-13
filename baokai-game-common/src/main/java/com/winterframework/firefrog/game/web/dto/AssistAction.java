package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 
* @ClassName: Bets 
* @Description:存放辅助表信息
* @author Richard
* @date 2013-8-23 下午2:35:42 
*
 */
public class AssistAction implements Serializable {

	private static final long serialVersionUID = 6210895184293763906L;

	protected static final int COUNTS = 10;
	//参数间以竖杠“|”分隔
	public final static String paraSeparate = "|";
	//每组取值以逗号”,”分隔
	public final static String groupSeparate = ",";

	//开奖号码
	private Integer numberRecord;

	/**
	 * 
	* Title:
	* Description:
	* @param numberRecode 开奖号码
	 */
	public AssistAction(Integer numberRecord) {
		this.numberRecord = numberRecord;
	}

	/**
	 * 
	* @Title: mbit 
	* @Description: 万位数号码
	* @return
	 */
	public int mbit() {
		return numberRecord / 10000;
	}

	/**
	 * 
	* @Title: kbit 
	* @Description: 千位数号码
	* @return
	 */
	public int kbit() {
		return (numberRecord % 10000) / 1000;
	}

	/**
	 * 
	* @Title: hbit 
	* @Description: 百位数号码 
	* @return
	 */
	public int hbit() {
		return (numberRecord % 1000) / 100;
	}

	/**
	 * 
	* @Title: dbit 
	* @Description: 十位数号码 
	* @return
	 */
	public int dbit() {
		return (numberRecord % 100) / 10;
	}

	/**
	 * 
	* @Title: ubit 
	* @Description: 个位数号码
	* @return
	 */
	public int ubit() {
		return numberRecord % 10;
	}

	protected Integer[] initArray(Integer[] arrays, int count) {

		arrays = new Integer[count];

		for (int i = 0; i < arrays.length; i++) {

			arrays[i] = 1;
		}

		return arrays;
	}

	/**
	 * 
	* @Title: setArray 
	* @Description: 直选 号码辅助
	* @param arrays
	* @param num
	* @return
	 */
	protected Integer[] setArray(Integer[] arrays, int num) {

		for (int i = 0; i < arrays.length; i++) {

			if (i == num) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}

		return arrays;
	}
	
	/**
	 * 
	* @Title: setArray 
	* @Description: 组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == mbit() || i == hbit() || i == kbit() || i == dbit() || i == ubit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}

	/**
	 * 
	* @Title: setWuXingArray 
	* @Description: 五星组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setWuXingArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == mbit() || i == hbit() || i == kbit() || i == dbit() || i == ubit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}
	
	/**
	 * 
	* @Title: setSiXingArray 
	* @Description: 四星组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setSiXingArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == hbit() || i == kbit() || i == dbit() || i == ubit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}
	
	/**
	 * 
	* @Title: setQianSanArray
	* @Description: 前三组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setQianSanArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == hbit() || i == kbit() || i == mbit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}
	
	/**
	 * 
	* @Title: setHouSanArray 
	* @Description: 后三组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setHouSanArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == hbit() || i == dbit() || i == ubit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}

	/**
	 * 
	* @Title: setQianErArray
	* @Description: 前二组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setQianErArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == kbit() || i == mbit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}
	
	/**
	 * 
	* @Title: setHouErArray 
	* @Description: 后二组选号码辅助
	* @param arrays
	* @return
	 */
	protected Integer[] setHouErArray(Integer[] arrays) {
		for (int i = 0; i < arrays.length; i++) {
			if (i == dbit() || i == ubit()) {
				arrays[i] = 0;
				continue;
			}
			arrays[i] = arrays[i] + 1;
		}
		return arrays;
	}

}
