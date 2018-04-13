package com.winterframework.firefrog.game.web.util;

import java.util.Date;

import com.winterframework.firefrog.common.util.DateUtils;

public class IssueCodeUtil {
	
	/**99101:重慶時時彩*/
	public static long LOTTERYID_CQSSC = 99101;
	/**99102:江西時時彩*/
	public static long LOTTERYID_JXSSC = 99102;
	/**99103:新疆時時彩*/
	public static long LOTTERYID_XJSSC = 99103;
	/**99104:天津時時彩*/
	public static long LOTTERYID_TJSSC = 99104;
	/**99105:黑龍江時時彩*/
	public static long LOTTERYID_HLJSSC = 99105;
	/**99106:樂利時時彩*/
	public static long LOTTERYID_LLSSC = 99106;
	/**99107:上海時時彩*/
	public static long LOTTERYID_SSL = 99107;
	/**99108:3D*/
	public static long LOTTERYID_F3D = 99108;
	/**99109:排列5*/
	public static long LOTTERYID_PL5 = 99109;
	/**99110:排列3*/
	public static long LOTTERYID_PL3 = 99110;
	/**99301:山東11選5*/
	public static long LOTTERYID_SD115 = 99301;
	/**99302:江西11選5*/
	public static long LOTTERYID_JX115 = 99302;
	/**99303:廣東11選5*/
	public static long LOTTERYID_GD115 = 99303;
	/**99304:重慶11選5*/
	public static long LOTTERYID_CQ115 = 99304;
	/**99305:樂利11選5*/
	public static long LOTTERYID_LL115 = 99305;
	/**99306:順利11選5*/
	public static long LOTTERYID_SL115 = 99306;
	/**99307:江苏11選5*/
	public static long LOTTERYID_JS115 = 99307;
	/**99201:北京快樂8*/
	public static long LOTTERYID_BJKL8 = 99201;
	/**99701:六合彩*/
	public static long LOTTERYID_LHC = 99701;	
	/**99401:雙色球*/
	public static long LOTTERYID_SSQ = 99401;
	/**99111:宝开一分彩*/
	public static long LOTTERYID_JLFFC = 99111;
	/**99501:江蘇快三*/
	public static long LOTTERYID_JSK = 99501;
	/**99502:安徽快三*/
	public static long LOTTERYID_AHK = 99502;
	/**99503:江蘇骰寶*/
	public static long LOTTERYID_JSS = 99601;
	/**99602:吉利骰寶(娛樂廳)*/
	public static long LOTTERYID_JLSB_COM = 99602;
	/**99603:吉利骰寶(至尊廳)*/
	public static long LOTTERYID_JLSB_VIP = 99603;
	/**99112:順利秒秒彩*/
	public static long LOTTERYID_SLMMC = 99112;
	/**99113:超級2000秒秒彩(APP版)*/
	public static long LOTTERYID_SLMMC2000 = 99113;
	/**99114:腾讯分分彩*/
	public static long LOTTERYID_TXFFC = 99114;
	/** 
	 * 通用奖期编码生成规则  年+月+日+间隔符+3位数字 
	 * @param issueDate 奖期日期
	 * @param separator 分隔符
	 * @param sequence 奖期当天期数序列
	 * @param digit  规则中的最后是几位数字，需要补0
	 * @return
	 */
	public static String createWebIssueCode(Long lotteryId,Date issueDate, int sequence) {
		StringBuilder issueCode=new StringBuilder();
		switch (lotteryId.intValue()) {
		case 99103://新疆时时彩 年+月+日+间隔符+2位数字
			issueCode.append(DateUtils.format(issueDate,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR));
			issueCode.append("-");
			issueCode.append(paddingChar(sequence+"", 2, '0', false));
			break;
		case 99105://黑龙江时时彩  7位数字，不足7位时用0补足  按全部已开奖期顺序编号，历史第一期为0000001  奖期规则比较特殊  在生成的地方单独处理 此处不处理
			break;
		case 99107://上海时时乐 年+月+日+间隔符+2位数字
			issueCode.append(DateUtils.format(issueDate,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR));
			issueCode.append("-");
			issueCode.append(paddingChar(sequence+"", 2, '0', false));
			break;
		case 99111:
		case 99602:
		case 99603:
		case 99114:
			issueCode.append(DateUtils.format(issueDate,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR));
			issueCode.append("-");
			issueCode.append(paddingChar(sequence+"", 4, '0', false));
			break;
		default:
			buildCommenIssueCode(issueDate, sequence, issueCode);
			break;
		}
		return issueCode.toString();
	}

	private static void buildCommenIssueCode(Date issueDate, int sequence, StringBuilder issueCode) {
		issueCode.append(DateUtils.format(issueDate,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR));
		issueCode.append("-");
		issueCode.append(paddingChar(sequence+"", 3, '0', false));
	}
	
	/** 
	 * 生成奖期  奖期标准是统一的  格式为 8位时间 20140114 +彩种lotteryid后三位+三位序列号 少的补零 总共14位
	 * @param issueDate 时间
	 * @param separator 分隔符
	 * @param sequence 序列号
	 * @param digit 位数
	 * @param lotteryId 彩种id
	 * @return
	 */
	public static String createCommenIssueCode(Date issueDate, String separator, Long sequence, int digit,Long lotteryId) {
		StringBuilder issueCode=new StringBuilder();
		issueCode.append(DateUtils.format(issueDate,DateUtils.DATE_FORMAT_PATTERN_NO_SEPARATOR));
		issueCode.append(separator);
		issueCode.append((lotteryId+"").substring(2));
		issueCode.append(paddingChar(sequence+"", digit, '0', false));
		return issueCode.toString();
	}
	
	/** 
	 * 字符串的补位操作 如果需要长度为3位不足的添加某个字符
	 * @param origin 源字符串
	 * @param size  长度需要补足
	 * @param appendChar 填充的字符串
	 * @param append  是否是在字符串尾部填充
	 * @return
	 */
	public static String paddingChar(String origin, long size, char appendChar,
			boolean append) {
		if (origin.length() >= size) {
			return origin;
		}
		StringBuilder builder = new StringBuilder(origin);
		while (builder.length() < size) {
			if (append) {
				builder.append(appendChar);
			} else {
				builder.insert(0, appendChar);
			}
		}
		return builder.toString();
	}
}
