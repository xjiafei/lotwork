package com.winterframework.firefrog.game.web.validate.impl.ssc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.winterframework.firefrog.game.entity.BetDetails.fileMode;
import com.winterframework.firefrog.game.exception.GameBetAmountErrorException;
import com.winterframework.firefrog.game.exception.GameBetContentPatternErrorException;
import com.winterframework.firefrog.game.exception.GameTotbetsErrorException;
import com.winterframework.firefrog.game.web.validate.utils.SplitUtil;

public class ValidateUtils {

	private static Logger log = LoggerFactory.getLogger(ValidateUtils.class);

	/**
	 * 
	* @Title: valideateBetformat 
	* @Description: 验证投注内容格式的长度
	* @param betContent
	* @param length
	* @throws GameBetContentPatternErrorException
	 */
	public static void valideateBetformat(int betformat, int length) throws GameBetContentPatternErrorException {

		Assert.isTrue(betformat > 0, "长度必须大于0");
		Assert.isTrue(length > 0, "长度必须大于0");

		if (betformat != length) {

			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	public static int maxValue(String[] str) {

		int[] intArray = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			intArray[i] = Integer.parseInt(str[i]);
		}
		Arrays.sort(intArray);

		return intArray[str.length - 1];
	}

	public static int minValue(String[] str) {

		int[] intArray = new int[str.length];
		for (int i = 0; i < str.length; i++) {
			intArray[i] = Integer.parseInt(str[i]);
		}
		Arrays.sort(intArray);
		return intArray[0];
	}

	public static Boolean checkFormatNumber (String[] str,String regex) {

		for(String ss : str){
			if(!ss.matches(regex)){
				return false;
			}
		}
		return true;
	}
	
	/** 
	* @Title: valideateRangeMax 
	* @Description: 验证投注内容有没有超出大小限制  
	* @param rangeMax 范围上限
	* @param rangeMin 范围下限
	* @param betMax 最大投注值
	* @param betMax 最小投注值
	* @throws GameBetContentPatternErrorException
	*/
	public static void valideateBetNumberRange(int rangeMax, int rangeMin, int betMax, int betMin)
			throws GameBetContentPatternErrorException {

		if (rangeMax < betMax || betMin < rangeMin) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	/** 
	* @Title: valideateBetformatAtleast 
	* @Description: 处理那些至少需要多少位的验证 
	* @param betformat 
	* @param length 最小值
	* @throws GameBetContentPatternErrorException
	*/
	public static void valideateBetformatAtleast(int betformat, int length) throws GameBetContentPatternErrorException {

		Assert.isTrue(betformat > 0, "长度必须大于0");
		Assert.isTrue(length > 0, "长度必须大于0");

		if (betformat < length) {

			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	/** 
	* @Title: valideateBetformatAtleast 
	* @Description: 处理那些至多需要多少位的验证 
	* @param betformat 
	* @param length 最小值
	* @throws GameBetContentPatternErrorException
	*/
	public static void valideateBetformatAtmost(int betformat, int length) throws GameBetContentPatternErrorException {

		Assert.isTrue(betformat > 0, "长度必须大于0");
		Assert.isTrue(length > 0, "长度必须大于0");

		if (betformat > length) {

			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	/**
	 * 
	* @Title: validateBetContentLength 
	* @Description: 验证投注内容格式内容长度
	* @param betContent
	* @param length
	* @throws GameBetContentPatternErrorException
	 */
	public static void validateBetContentLength(String betContent, int length)
			throws GameBetContentPatternErrorException {

		Assert.isTrue(length > 0, "长度必须大于0");
		Assert.notNull(betContent, "内容不能为空");

		if (betContent.length() != length) {

			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}

	}

	/**
	 * 
	* @Title: validateBetCount 
	* @Description: 验证投注注数
	* @param length
	* @param count
	* @throws GameTotbetsErrorException
	 */
	public static void validateBetsCount(int length, Long count) throws GameTotbetsErrorException {

		Assert.isTrue(length > 0, "长度必须大于0");
		Assert.isTrue(count > 0, "必须大于0");

		if (length != count.intValue()) {

			log.error("投注注数有误");
			throw new GameTotbetsErrorException();
		}
	}
	
	
	/**
	 * 
	* @Title: validateTotalMoney 
	* @Description: 验证投注金额是否正确
	* @param totalAmount 投注总金额
	* @param betsCount 投注注数
	* @param multiple 倍数
	* @param moneyMode 元角模式
	* @throws GameBetAmountErrorException
	 */
	public static void validateTotalMoney(long totalAmount, int betsCount, int multiple, int moneyMode)
			throws GameBetAmountErrorException {

		Assert.isTrue(totalAmount > 0, "总金额必须大于0");
		Assert.isTrue(betsCount > 0, "注数必须大于0");
		Assert.isTrue(multiple > 0, "倍速必须大于0");
		long temp = 20000;
		if (moneyMode == 2) {
			temp = 2000;
		}else if (moneyMode == 3) {
			temp = 200;
		}

		if (totalAmount != (betsCount * multiple * temp)) {

			log.error("投注金额不对");
			throw new GameBetAmountErrorException();
		}
	}
	
	
	/**
	 * 
	* @Title: validateLHCTotalMoney 
	* @Description: 六合彩验证投注金额是否正确
	* @param totalAmount 投注总金额
	* @param betsCount 投注注数
	* @param multiple 倍数
	* @param moneyMode 元角模式
	* @throws GameBetAmountErrorException
	 */
	public static void validateLHCTotalMoney(Long totalAmount, int betsCount, int multiple, int moneyMode,int methodCode,Long singleWin)
			throws GameBetAmountErrorException {

		Assert.isTrue(totalAmount > 0, "总金额必须大于0");
		Assert.isTrue(betsCount > 0 && betsCount ==1, "注数必须大于0且等于1");
		Assert.isTrue(multiple == 1, "倍数必须等于1");
		
		Assert.isTrue(moneyMode == 1, "必须为元模式");

		if (totalAmount > 9999990000l) {

			log.error("投注金额不对");
			throw new GameBetAmountErrorException();
		}
	}

	/** 
	* @Title: checkZ3content 
	* @Description: 验证组三号码的格式正确性 
	* @param str
	*/
	public static void checkZ3content(String str) {
		char[] repeatChars = str.toCharArray();
		Set<String> repeatSet = new HashSet<String>();
		for (char s : repeatChars) {
			repeatSet.add(s + "");
		}
		if (repeatSet.size() != 2) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	/** 
	* @Title: checkZ6content 
	* @Description: 验证组六号码的正确性 
	* @param str
	*/
	public static void checkZ6content(String str) {
		char[] repeatChars = str.toCharArray();
		Set<String> repeatSet = new HashSet<String>();
		for (char s : repeatChars) {
			repeatSet.add(s + "");
		}
		if (repeatSet.size() != 3) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	/** 
	* @Title: checkBaoziContent 
	* @Description:  混合组选中不能有豹子 
	* @param str
	*/
	public static void checkBaoziContent(String str) {
		char[] repeatChars = str.toCharArray();
		Set<String> repeatSet = new HashSet<String>();
		for (char s : repeatChars) {
			repeatSet.add(s + "");
		}
		if (repeatSet.size() == 1) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
	}

	public static String[] convertBet2String(String betContent, int _fileMode, String regex) throws Exception {

		if (_fileMode == fileMode.file.getValue()) {
			return SplitUtil.fileContentSplit(betContent);
		} else {
			return SplitUtil.splitByRegex(betContent, regex);
		}
	}

	public static String[] convertBet2String(String betContent, int _fileMode, String regex, boolean need)
			throws Exception {

		if (_fileMode == fileMode.file.getValue()) {
			return betContent.replaceAll("\\s{1,}", " ").replaceAll("\\s{2,}", " ").trim().split(" ");
		} else {
			return SplitUtil.splitByRegex(betContent, regex);
		}
	}

	/** 
	* @Title: checkRepeat 
	* @Description: 检查投注内容是否重复 
	* @param repeatChar
	* @return
	*/
	public static Set<String> checkRepeat(String repeatChar) {
		char[] repeatChars = repeatChar.toCharArray();
		Set<String> repeatSet = new HashSet<String>();
		for (char s : repeatChars) {
			repeatSet.add(s + "");
		}
		if (repeatSet.size() != repeatChars.length) {
			log.error("投注内容格式有误，有重复");
			throw new GameBetContentPatternErrorException();
		}
		return repeatSet;
	}

	/** 
	* @Title: checkRepeat 
	* @Description: 检查投注内容是否重复 
	* @param repeatChar
	* @return
	*/
	public static Set<String> checkRepeat(String[] repeatChar) {
		Set<String> repeatSet = new HashSet<String>();
		for (String s : repeatChar) {
			repeatSet.add(s);
		}
		if (repeatSet.size() != repeatChar.length) {
			log.error("投注内容格式有误,有重复");
			throw new GameBetContentPatternErrorException();
		}
		return repeatSet;
	}
	
	/**
	 * 
	* @Title: validateBetsSame 
	* @Description: 验证投注內容(過濾ex:22或是65和56)
	* @param repeatChar
	* @throws GameBetContentPatternErrorException
	 */
	public static void validateBetsSame(String[] repeatChar) {
		
		for(int i=0;i<repeatChar.length;i++){
			int tens1=Integer.valueOf(repeatChar[i])/10;
			int ones1=Integer.valueOf(repeatChar[i])%10;
			
			if(tens1==ones1){
				log.error("投注内容格式有误,有重复");
				throw new GameBetContentPatternErrorException();
			}
			for(int j=(i+1);j<repeatChar.length;j++){
				int tens2=Integer.valueOf(repeatChar[j])/10;
				int ones2=Integer.valueOf(repeatChar[j])%10;
				
				if(tens1==ones2 && ones1==tens2){
					log.error("投注内容格式有误,有重复");
					throw new GameBetContentPatternErrorException();
				}
			}
		}
	}
	
	/**
	 * 
	* @Title: validateBetsSameZ3 
	* @Description: 验证投注內容(過濾ex:221和122)
	* @param repeatChar
	* @throws GameBetContentPatternErrorException
	 */
	public static void validateBetsSameZ3(String[] repeatChar) {
		String st ;
		String stin ;
		String stFianl = null ;
		String stFianlIn = null ;
		char[] stChar = null;
		char[] stCharin = null;
		Set<String> repeatSet = new HashSet<String>();
		
		for(int i=0;i<repeatChar.length;i++){
			st = repeatChar[i]; 
			stChar =st.toCharArray(); 
			Arrays.sort(stChar); 
			stFianl = stFianl.valueOf(stChar);
			for(int j=i+1;j<repeatChar.length;j++){
				stin = repeatChar[j]; 
				stCharin =stin.toCharArray(); 
				Arrays.sort(stCharin); 
				stFianlIn = stFianl.valueOf(stCharin);
				if(stFianl.equals(stFianlIn) ){
					log.error("投注内容格式有误,有重复");
 					throw new GameBetContentPatternErrorException();
				}
			}
		}
		
		
	}
	
	
	
	
	/** 
	* @Title: checkIsNumber 
	* @Description: 检验投注项为数字，不然抛出格式错误异常 
	* @param bet
	*/
	public static void checkIsNumber(String bet) {
		try {
			Integer.parseInt(bet);
		} catch (NumberFormatException e) {
			log.error("投注内容格式有误,有重复");
			throw new GameBetContentPatternErrorException();
		}
	}

	public static void checkIsNumber(String[] bets) {
		for (String bet : bets) {
			try {

				Integer.parseInt(bet);
			} catch (NumberFormatException e) {
				log.error("投注内容格式有误,有重复");
				throw new GameBetContentPatternErrorException();
			}
		}
	}

	public static void validateBetContentRegex(String betdetail, String regex) {
		if (!betdetail.matches(regex)) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}

	}

	public static void main(String[] args) {
//		Assert.isTrue(-1 > 0, "总金额必须大于0");
//		Assert.isTrue(-2 > 0, "注数必须大于0");
//		Assert.isTrue(-3 > 0, "倍速必须大于0");
		Long result = countCNM(14L, 14L);
		System.out.println(result);
	}

	/** 
	* @Title: validateArrayItemNotInStr 
	* @Description: 验证arr里面的每一项都不出现在str中 
	* @param arr 
	* @param str 
	*/
	public static void validateArrayItemNotInStr(String[] arr, String str) {
		for (String s : arr) {
			if (str.contains(s)) {
				log.error("投注内容格式有误");
				throw new GameBetContentPatternErrorException();
			}
		}

	}
	
	/**
	 * 計算C的N取M  有幾種可能 公式如下
	 * n! / (n-m)! * (m)!
	 * @param n
	 * @param m
	 * @return
	 */
	public static Long countCNM(Long n, Long m){
		if (n < m) {
			log.error("投注内容格式有误");
			throw new GameBetContentPatternErrorException();
		}
		Long nResult = multiplicationHierarchyResult(n);
		Long mResult = multiplicationHierarchyResult(m);
		Long nmResult = multiplicationHierarchyResult(n-m);
		return nResult / (mResult * nmResult);
	}
	
	/**
	 * 算出階層結果 n!
	 * @param nowNum
	 * @return
	 */
	public static Long multiplicationHierarchyResult(Long nowNum){
		return multiplicationHierarchyResult(1L, nowNum);
	}
	
	/**
	 * 算出階層結果 n!
	 * ex : 5! = 5*4*3*2*1
	 * @param result
	 * @param nowNum
	 * @return
	 */
	public static Long multiplicationHierarchyResult(Long result, Long nowNum){
		if(nowNum > 1){
			result *= nowNum;
			return multiplicationHierarchyResult(result, --nowNum);
		} else {
			return result;
		}
	}


}
