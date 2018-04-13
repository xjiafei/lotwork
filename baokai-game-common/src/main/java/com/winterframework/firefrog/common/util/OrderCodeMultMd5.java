package com.winterframework.firefrog.common.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.winterframework.modules.utils.DataUtils;

/** 
* @ClassName: OrderCodeMultMd5 
* @Description: 生成订单编码时三位随机码生成辅助类 ，随机整数的取值范围是0-238327 即 随机码000-ZZZ
* @author david 
* @date 2013-11-7 下午3:50:27 
*  
*/
public class OrderCodeMultMd5 {
	private static Map<Character, Integer> keys = new HashMap<Character, Integer>();
	static {
		keys.put('0',24);
		keys.put('1',47);
		keys.put('2',52);
		keys.put('3',10);
		keys.put('4',33);
		keys.put('5',2);
		keys.put('6',53);
		keys.put('7',15);
		keys.put('8',56);
		keys.put('9',34);
		keys.put('A',59);
		keys.put('B',28);
		keys.put('C',3);
		keys.put('D',9);
		keys.put('E',61);
		keys.put('F',55);
		keys.put('G',18);
		keys.put('H',38);
		keys.put('I',8);
		keys.put('J',29);
		keys.put('K',13);
		keys.put('L',20);
		keys.put('M',46);
		keys.put('N',49);
		keys.put('O',42);
		keys.put('P',26);
		keys.put('Q',39);
		keys.put('R',12);
		keys.put('S',58);
		keys.put('T',22);
		keys.put('U',27);
		keys.put('V',30);
		keys.put('W',57);
		keys.put('X',60);
		keys.put('Y',41);
		keys.put('Z',5);
		keys.put('a',40);
		keys.put('b',36);
		keys.put('c',25);
		keys.put('d',19);
		keys.put('e',17);
		keys.put('f',50);
		keys.put('g',4);
		keys.put('h',35);
		keys.put('i',45);
		keys.put('j',32);
		keys.put('k',54);
		keys.put('l',14);
		keys.put('m',6);
		keys.put('n',44);
		keys.put('o',23);
		keys.put('p',51);
		keys.put('q',37);
		keys.put('r',0);
		keys.put('s',48);
		keys.put('t',16);
		keys.put('u',7);
		keys.put('v',43);
		keys.put('w',1);
		keys.put('x',11);
		keys.put('y',21);
		keys.put('z',31);
	}
	private static Map<Integer, Character> values = new HashMap<Integer, Character>();
	static {
		values.put(0,'r');
		values.put(1,'w');
		values.put(10,'3');
		values.put(11,'x');
		values.put(12,'R');
		values.put(13,'K');
		values.put(14,'l');
		values.put(15,'7');
		values.put(16,'t');
		values.put(17,'e');
		values.put(18,'G');
		values.put(19,'d');
		values.put(2,'5');
		values.put(20,'L');
		values.put(21,'y');
		values.put(22,'T');
		values.put(23,'o');
		values.put(24,'0');
		values.put(25,'c');
		values.put(26,'P');
		values.put(27,'U');
		values.put(28,'B');
		values.put(29,'J');
		values.put(3,'C');
		values.put(30,'V');
		values.put(31,'z');
		values.put(32,'j');
		values.put(33,'4');
		values.put(34,'9');
		values.put(35,'h');
		values.put(36,'b');
		values.put(37,'q');
		values.put(38,'H');
		values.put(39,'Q');
		values.put(4,'g');
		values.put(40,'a');
		values.put(41,'Y');
		values.put(42,'O');
		values.put(43,'v');
		values.put(44,'n');
		values.put(45,'i');
		values.put(46,'M');
		values.put(47,'1');
		values.put(48,'s');
		values.put(49,'N');
		values.put(5,'Z');
		values.put(50,'f');
		values.put(51,'p');
		values.put(52,'2');
		values.put(53,'6');
		values.put(54,'k');
		values.put(55,'F');
		values.put(56,'8');
		values.put(57,'W');
		values.put(58,'S');
		values.put(59,'A');
		values.put(6,'m');
		values.put(60,'X');
		values.put(61,'E');
		values.put(7,'u');
		values.put(8,'I');
		values.put(9,'D');

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

	private static String createRadom(String org) {
		long abc = DataUtils.randomBetween(100000000, 999999999);
		String dest = abc + "0" + org;
		return dest.substring(dest.length() - 10);

	}

	final static String str = "rw5CgZmuID3xRKl7teGdLyTo0cPUBJVzj49hbqHQaYOvniM1sNfp26kF8WSAXE";

	final static char[] bs = str.toCharArray();

	/**
	 * long转换成62进制
	 * @param orginal
	 * @return
	 */
	public static String to62Digit(long orginal) {
		long sult = orginal;
		StringBuffer bufer = new StringBuffer();
		while ((sult = getNexValue(sult, bufer)) > 0) {
		}
		String str = paddingChar(bufer.reverse().toString(), 3L, '0', false);
		return str;
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

	/** 
	* @Title: paddingChar 
	* @Description: 字符串的补位操作 如果需要长度为3位不足的添加某个字符
	* @param origin 源字符串
	* @param size  长度需要补足
	* @param appendChar 填充的字符串
	* @param append  是否是在字符串尾部填充
	* @return
	*/
	public static String paddingChar(String origin, long size, char appendChar, boolean append) {
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

	public static void main(String[] args) {
		//		System.out.println(to62Digit(63));
		//		System.out.println(to62Digit(62));
		//		System.out.println(to62Digit(65));
		long m = 238328;
		System.out.println(to62Digit(1));
		System.out.println(to62Digit(2));
		System.out.println(toLong(to62Digit(m)));
		System.out.println("201311101024".substring(2));

	}
}
