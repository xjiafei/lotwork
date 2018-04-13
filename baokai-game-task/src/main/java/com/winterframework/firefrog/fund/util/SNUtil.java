/**   
* @Title: SNUtil.java 
* @Package com.winterframework.firefrog.utils 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-7-3 下午4:35:16 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.fund.enums.EnumItem;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.utils.DataUtils;
import com.winterframework.modules.utils.DateConvertUtils;

/** 
* @ClassName: SNUtil 
* @Description: 创建资金业务的流水号和资金变动的流水号. 
* @author Page
* @date 2013-7-3 下午4:35:16 
*  
*/
@Service("SNUtilFund")
public class SNUtil implements ISNGenerator {

	private final static String FLAG = "";

	@Resource(name = "RedisClient")
	private RedisClient redis;

	@PropertyConfig(value = "module_fund")
	private String module;

	@PropertyConfig(value = "function_business_sn")
	private String businessSn;

	@PropertyConfig(value = "function_fund_sn")
	private String fundSn;

	private String getRedisKey(String functionKey) {
		return this.module + "_" + functionKey;
	}

	private String createSn(String module, String identifier, String redisKey, long userId) throws Exception {
		StringBuilder sb = new StringBuilder(1024);
		sb.append(module);
		sb.append(FLAG);
		sb.append(identifier);
		sb.append(FLAG);
		sb.append(MultMd5.to62Digit(userId));
		String now = DateConvertUtils.format(new Date(), "yyyyMMddhhmm");
		sb.append(FLAG);
		sb.append(MultMd5.to62Digit(Long.parseLong(now)));
		sb.append(FLAG);
		long tcode = 1;
		if (!redis.exists(redisKey)) {
			redis.set(redisKey, String.valueOf(System.currentTimeMillis()) + "+1");
		} else {
			String[] value = StringUtils.split(redis.get(redisKey),"+");
			long redisDate = Long.valueOf(value[0]);
			long code = Long.valueOf(value[1]);
			Calendar redisCal = Calendar.getInstance();
			redisCal.setTimeInMillis(redisDate);
			if (DateUtils.truncatedEquals(Calendar.getInstance(), redisCal,Calendar.HOUR_OF_DAY)) {
				tcode = code + 1;
				redis.set(redisKey, redisDate + "+" + String.valueOf(tcode));
			} else {
				redis.set(redisKey, String.valueOf(System.currentTimeMillis()) + "+1");
			}
		}
		//sb.append(MultMd5.to62Digit(tcode));
		return sb.toString().toUpperCase()+MultMd5.to62Digit(tcode);
	}

	/**
	* Title: createSN
	* Description:
	* @param item
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#createBusinessSn(com.winterframework.firefrog.fund.enums.EnumItem, long) 
	*/
	
	public String createBusinessSn(EnumItem item, long userId) throws Exception {
		return this.createSn(item.getModel().getCode(), item.getSummaryCode(), getRedisKey(this.businessSn), userId);
	}
	
	public String createBusinessSnForNull(EnumItem item, long userId) throws Exception{
		return this.createSn(item.getModel().getCode(), item.getSummaryCode()+item.getTradeStatus(), getRedisKey(this.businessSn), userId);
	}

	/**
	* Title: parseSNDesc
	* Description:
	* @param sn
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#parseBusinessSnDesc(java.lang.String) 
	*/
	@Override
	public String parseBusinessSnDesc(String sn) {
		//String typeStr = sn.substring(0, 2);
		//String descStr = sn.substring(2, 6);
		if (sn.startsWith("TCL"))
			return "TCL";
		return sn.substring(0, 6);

	}

	/**
	* Title: createFundSn
	* Description:
	* @param item
	* @param userId
	* @return 
	* @see com.winterframework.firefrog.fund.util.ISNGenerator#createFundSn(com.winterframework.firefrog.fund.enums.EnumItem, long) 
	*/
	@Override
	public String createFundSn(EnumItem item, long userId) throws Exception{
		if (item.getTradeCode() != null) {
			return this.createSn(item.getModel().getCode(), item.getTradeCode(), getRedisKey(this.fundSn), userId);
		} else
			return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
private static class MultMd5 {
		private static Map<Character, Integer> keys = new HashMap<Character, Integer>();
		static {
			keys.put('0', 0);
			keys.put('1', 1);
			keys.put('2', 2);
			keys.put('3', 3);
			keys.put('4', 4);
			keys.put('5', 5);
			keys.put('6', 6);
			keys.put('7', 7);
			keys.put('8', 8);
			keys.put('9', 9);
			keys.put('a', 10);
			keys.put('b', 11);
			keys.put('c', 12);
			keys.put('e', 13);
			keys.put('d', 14);
			keys.put('f', 15);
			keys.put('g', 16);
			keys.put('h', 17);
			keys.put('i', 18);
			keys.put('j', 19);
			keys.put('k', 20);
			keys.put('l', 21);
			keys.put('m', 22);
			keys.put('n', 23);
			keys.put('o', 24);
			keys.put('p', 25);
			keys.put('q', 26);
			keys.put('r', 27);
			keys.put('s', 28);
			keys.put('t', 29);
			keys.put('u', 30);
			keys.put('v', 31);
			keys.put('w', 32);
			keys.put('x', 33);
			keys.put('y', 34);
			keys.put('z', 35);
			keys.put('A', 36);
			keys.put('B', 37);
			keys.put('C', 38);
			keys.put('D', 39);
			keys.put('E', 40);
			keys.put('F', 41);
			keys.put('G', 42);
			keys.put('H', 43);
			keys.put('I', 44);
			keys.put('J', 45);
			keys.put('K', 46);
			keys.put('L', 47);
			keys.put('M', 48);
			keys.put('N', 49);
			keys.put('O', 50);
			keys.put('P', 51);
			keys.put('Q', 52);
			keys.put('R', 53);
			keys.put('S', 54);
			keys.put('T', 55);
			keys.put('U', 56);
			keys.put('V', 57);
			keys.put('W', 58);
			keys.put('X', 59);
			keys.put('Y', 60);
			keys.put('Z', 61);
			//keys.put('^', 62);
			//keys.put('~', 63);

		}
		private static Map<Integer, Character> values = new HashMap<Integer, Character>();
		static {
			values.put(0, '0');
			values.put(1, '1');
			values.put(2, '2');
			values.put(3, '3');
			values.put(4, '4');
			values.put(5, '5');
			values.put(6, '6');
			values.put(7, '7');
			values.put(8, '8');
			values.put(9, '9');
			values.put(10, 'a');
			values.put(11, 'b');
			values.put(12, 'c');
			values.put(13, 'e');
			values.put(14, 'd');
			values.put(15, 'f');
			values.put(16, 'g');
			values.put(17, 'h');
			values.put(18, 'i');
			values.put(19, 'j');
			values.put(20, 'k');
			values.put(21, 'l');
			values.put(22, 'm');
			values.put(23, 'n');
			values.put(24, 'o');
			values.put(25, 'p');
			values.put(26, 'q');
			values.put(27, 'r');
			values.put(28, 's');
			values.put(29, 't');
			values.put(30, 'u');
			values.put(31, 'v');
			values.put(32, 'w');
			values.put(33, 'x');
			values.put(34, 'y');
			values.put(35, 'z');
			values.put(36, 'A');
			values.put(37, 'B');
			values.put(38, 'C');
			values.put(39, 'D');
			values.put(40, 'E');
			values.put(41, 'F');
			values.put(42, 'G');
			values.put(43, 'H');
			values.put(44, 'I');
			values.put(45, 'J');
			values.put(46, 'K');
			values.put(47, 'L');
			values.put(48, 'M');
			values.put(49, 'N');
			values.put(50, 'O');
			values.put(51, 'P');
			values.put(52, 'Q');
			values.put(53, 'R');
			values.put(54, 'S');
			values.put(55, 'T');
			values.put(56, 'U');
			values.put(57, 'V');
			values.put(58, 'W');
			values.put(59, 'X');
			values.put(60, 'Y');
			values.put(61, 'Z');
			//values.put(62, '^');
			//values.put(63, '~');
		}

		/**
		 * 通用加密
		 * @param original
		 * @param factors
		 * @return
		 */
		public static String dmd(final String original, String... factors) {
			String ret = "";
			String qs = "";
			if (factors.length == 0) {
				factors = new String[] { "" };
			}
			for (String str : factors) {
				try {
					ret = DigestUtils.md5Hex((original + str.length() + str).getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				qs += (str + str.length());
			}
			ret = ret.subSequence(10, 15) + createRadom(qs);
			return ret;
		}

		/**
		 * 通用加密
		 * @param original
		 * @param factors
		 * @return
		 */
		public static String dmd5(final String original, String... factors) {
			String ret = "";
			if (factors.length == 0) {
				factors = new String[] { "" };
			}
			for (String str : factors) {
				try {
					ret = DigestUtils.md5Hex((original + str.length() + str).getBytes("UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			return ret;
		}

		private static String createRadom(String org) {
			long abc = DataUtils.randomBetween(100000000, 999999999);
			String dest = abc + "0" + org;
			return dest.substring(dest.length() - 10);

		}

		final static String str = "0123456789abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final static char[] bs = str.toCharArray();

		/**
		 * long转换成64进制
		 * @param orginal
		 * @return
		 */
		public static String to62Digit(long orginal) {
			long sult = orginal;
			StringBuffer bufer = new StringBuffer();
			while ((sult = getNexValue(sult, bufer)) > 0) {
			}
			return bufer.reverse().toString();
		}

		public static long toLong(String dig) {
			char[] b = dig.toCharArray();
			int length = dig.length();
			Inner inner = new Inner(0, 1);
			for (int i = length - 1; i >= 0; i--) {
				getCurrent(inner, keys.get(b[i]));
			}
			return inner.rtn;
		}

		private static void getCurrent(Inner ref, long val) {
			ref.rtn += (val * ref.re);
			ref.re = ref.re * 62;
		}

		private static class Inner {
			public long rtn;
			public long re;

			public Inner(long rt, long re) {
				this.rtn = rt;
				this.re = re;
			}

		}

		private static long getNexValue(long orginal, StringBuffer lists) {
			long sult = orginal / 62;
			long remainder = orginal % 62;
			lists.append(bs[(int) remainder]);
			return sult;
		}

		public static void main(String[] args) {
			//System.out.println(to64Digit(63));
			//System.out.println(to64Digit(64));
			//System.out.println(to64Digit(65));
			long m = 1358666634;
			System.out.println(to62Digit(2701));
			System.out.println(to62Digit(2702));
			System.out.println(to62Digit(2703));
			System.out.println(toLong("HZ"));

		}
	}

}
