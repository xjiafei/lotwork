package com.winterframework.firefrog.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;

/** 
* @ClassName: SNUtil 
* @Description: 定单和追号规则定义 
* @author 你的名字 
* @date 2013-7-3 下午4:35:16 
*  
*/
@Service("SNUtil")
public class SNUtil {

	private static final String ORDER_CODE_PREFIX = "D";//追号编码前缀，以D开头
	private static final String PLAN_CODE_PREFIX = "Z";//追号编码前缀，以Z开头
	private static final String PACKAGE_CODE_PREFIX = "P";//方案编码前缀，以P开头
	private final ReentrantLock lock = new ReentrantLock();

	//编码类型 1 订单 2 追号 3 方案
	public static final Integer TYPE_ORDER = 1;
	public static final Integer TYPE_PLAN = 2;
	public static final Integer TYPE_PACKAGE = 3;
	private static final Map<String, Object> LOTTERY_SHORT_NAME_MAP = new HashMap<String, Object>();

	static {
		LOTTERY_SHORT_NAME_MAP.put("99101", "CQC");
		LOTTERY_SHORT_NAME_MAP.put("99102", "JXC");
		LOTTERY_SHORT_NAME_MAP.put("99103", "XJC");
		LOTTERY_SHORT_NAME_MAP.put("99104", "TJC");
		LOTTERY_SHORT_NAME_MAP.put("99105", "HLJ");
		LOTTERY_SHORT_NAME_MAP.put("99106", "LLC");
		LOTTERY_SHORT_NAME_MAP.put("99107", "SSL");
		LOTTERY_SHORT_NAME_MAP.put("99108", "F3D");
		LOTTERY_SHORT_NAME_MAP.put("99109", "PL5");
		LOTTERY_SHORT_NAME_MAP.put("99110", "PL3");
		LOTTERY_SHORT_NAME_MAP.put("99111", "FFC");
		LOTTERY_SHORT_NAME_MAP.put("99112", "MMC");
		LOTTERY_SHORT_NAME_MAP.put("99301", "SD5");

		LOTTERY_SHORT_NAME_MAP.put("99302", "JX5");
		LOTTERY_SHORT_NAME_MAP.put("99303", "GD5");
		LOTTERY_SHORT_NAME_MAP.put("99304", "CQ5");
		LOTTERY_SHORT_NAME_MAP.put("99305", "LL5");
		LOTTERY_SHORT_NAME_MAP.put("99306", "SL5");
		LOTTERY_SHORT_NAME_MAP.put("99307", "JS5");
		LOTTERY_SHORT_NAME_MAP.put("99201", "BJ8");
		LOTTERY_SHORT_NAME_MAP.put("99401", "SSQ");
		LOTTERY_SHORT_NAME_MAP.put("99501", "JSK");
		LOTTERY_SHORT_NAME_MAP.put("99502", "AHK");
		LOTTERY_SHORT_NAME_MAP.put("99601", "JSS");
		LOTTERY_SHORT_NAME_MAP.put("99701", "LHC");	
		LOTTERY_SHORT_NAME_MAP.put("99602", "JLS");
		LOTTERY_SHORT_NAME_MAP.put("99603", "JLSV");
		LOTTERY_SHORT_NAME_MAP.put("99114", "TFC");	
	}

	@Resource(name = "RedisClient")
	private RedisClient redis;

	/** 
	* @Title: createSN 
	* @Description:  创建订单编码或者追号编码,注意当一个奖期完成之后才能清空redies的数据，便于处理  方案编码规则？
	* @param type 1 订单编码  2 追号编码 ，注意当追号生成订单编码时使用订单编码, 14年9月19日 定义规则为  传递web期号过来，键入期号带中划线“-”，将前面的年份去掉20，“-”线去掉，其他格式直接拼接
	* eg: webCode:20140101-001 --> DCQC+140101001+00Z(三位随机码) ；  webCode:2014110 -->DCSC+2014110+zzz(三位随机码)
	* @param lotteryId 彩种id
	* @param issueCode 奖期
	* @param webIssueCode web奖期
	* @return
	*/
	public String createSN(Integer type, Long lotteryId, String issueCode) throws Exception {
		try {
			lock.lock();
			StringBuilder sb = new StringBuilder(1024);
			String key = getKey(type, lotteryId, issueCode, sb);
			//充分利用redis的强悍性，每次获取的时候，可以有效的避免冰法
			long code = redis.getIncre(key)+10000;
			//OrderCodeMultMd5.to62Digit code區間1489~1549會與0~60發生重複 在來就到92318之後才會再次發生重複問題，請避過此區間
			sb.append(OrderCodeMultMd5.to62Digit(code));
			return sb.toString();
		} finally {
			lock.unlock();
		}

	}

	@Deprecated
	public String getKey(Integer type, Long lotteryId, Long issueCode, StringBuilder sb) {
		String key = "";
		if (type == 2) {
			key = SNUtil.PLAN_CODE_PREFIX + issueCode;
			sb.append(SNUtil.PLAN_CODE_PREFIX);
		} else if (type == 3) {
			key = SNUtil.PACKAGE_CODE_PREFIX + issueCode;
			sb.append(SNUtil.PACKAGE_CODE_PREFIX);
		} else {
			key = SNUtil.ORDER_CODE_PREFIX + issueCode;
			sb.append(SNUtil.ORDER_CODE_PREFIX);
		}
		sb.append(LOTTERY_SHORT_NAME_MAP.get(lotteryId + ""));
		String issue=(issueCode + "").substring(2);
		issue=issue.replace(""+(lotteryId-99000), "");
		sb.append(issue);//获取奖期号的后
		return key;
	}
	
	public String getKey(Integer type, Long lotteryId, String issueCode, StringBuilder sb) {
		if (type == 2) {
			sb.append(SNUtil.PLAN_CODE_PREFIX);
		} else if (type == 3) {
			sb.append(SNUtil.PACKAGE_CODE_PREFIX);
		} else {
			sb.append(SNUtil.ORDER_CODE_PREFIX);
		}
		sb.append(LOTTERY_SHORT_NAME_MAP.get(lotteryId + ""));
		String issue=issueCode;
		if(issueCode.contains("-")){
			issue=issue.substring(2).replace("-", "");
		}
		sb.append(issue);//获取奖期号的后
		return sb.toString();
	}
	
	public static void main(String[] args) throws Exception {
		List<String> list = new ArrayList<String>();
		for(int i=1;i<100000;i++){
			String code = OrderCodeMultMd5.to62Digit(i);
			if(list.contains(code)){
				System.out.println(i+","+list.indexOf(code)+":"+code);
			}
			list.add(code);
		}
	}
}
