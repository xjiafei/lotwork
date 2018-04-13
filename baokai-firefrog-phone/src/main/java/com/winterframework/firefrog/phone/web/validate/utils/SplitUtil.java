package com.winterframework.firefrog.phone.web.validate.utils;

/** 
* @ClassName: SplitUtil 
* @Description: 分割工具类 
* @author david
* @date 2013-7-29 下午4:13:16 
*  
*/
public class SplitUtil {
	/** 
	* @Title: fileContentSplit 
	* @Description: 针对单式文件上传的分隔，先把其他符号替换成空格，然后将多个空格合成一个空格，最后用空格分隔
	* @param str
	* @return
	*/
	public static String[] fileContentSplit(String str) {
		return str.replaceAll(";|-|,|\\s{1,}|\\||\\.|:|。|-,", " ").replaceAll("\\s{2,}", " ").trim().split(" ");
	}

	/** 
	* @Title: commonContentSplit 
	* @Description: 非文件上传投注的一般切割 
	* @param str
	* @return
	*/
	public static String[] unfileContentSplit(String str) {
		return str.split(",");
	}

	/** 
	* @Title: splitByRegex 
	* @Description: 指定分隔规则分隔 
	* @param str
	* @param regex
	* @return
	*/
	public static String[] splitByRegex(String str, String regex) {
		//当字符串中间没有任何分隔符时要先拆分成字符去处理，避免出现一个空字符
		if (regex == "") {
			char[] sss = str.toCharArray();
			String[] newStr = new String[sss.length];
			for (int i = 0; i < sss.length; i++) {
				newStr[i] = sss[i] + "";
			}
			return newStr;
		}
		return str.split(regex);
	}

	public static void main(String[] args) {
		
		String regex="[0-9-]{1,},[0-9|-]{1,},[0-9|-]{1,},[0-9|-]{1,},[0-9|-]{1,}";
		String regex2="-{1,},-{1,},-{1,},-{1,},-{1,}";
		String str="--,-,1,-,-";
		
		System.out.println(str.matches(regex));

	}
}