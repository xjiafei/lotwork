package com.winterframework.firefrog.game.web.controller.utlis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.winterframework.firefrog.game.web.controller.utlis.GameDrawNumberChecker;
import com.winterframework.firefrog.game.web.controller.utlis.K3DrawNumberChecker;

public class GameAbnormalOperationUtils {

	/** 
	* 不同玩法的开奖号码规则
	*/
	private static Map<Long, GameDrawNumberChecker> resultCodeRegMap = new HashMap<Long, GameDrawNumberChecker>();
	static {
		//时时彩系列      12345
		resultCodeRegMap.put(99101L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99102L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99103L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99104L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99105L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99106L, new SSCDrawNumberChecker());
		resultCodeRegMap.put(99107L, new ThreeeDDrawNumberChecker());
		//3D系                 234(0-9)
		resultCodeRegMap.put(99108L, new ThreeeDDrawNumberChecker());
		//11选5系          01,02,03,04,05(01----11)
		resultCodeRegMap.put(99304L, new ElevenChsFiveeDrawNumberChecker());
		resultCodeRegMap.put(99302L, new ElevenChsFiveeDrawNumberChecker());
		resultCodeRegMap.put(99301L, new ElevenChsFiveeDrawNumberChecker());
		resultCodeRegMap.put(99303L, new ElevenChsFiveeDrawNumberChecker());
		resultCodeRegMap.put(99305L, new ElevenChsFiveeDrawNumberChecker());
		resultCodeRegMap.put(99307L, new ElevenChsFiveeDrawNumberChecker());
		//基诺系             01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20(0---80)
		resultCodeRegMap.put(99201L, new JiNuoDrawNumberChecker());
		//双色球系    01，02,03,10,13,33+13	（1---33选6在1-16选1）
		resultCodeRegMap.put(99401L, new DoubleColorDrawNumberChecker());
		resultCodeRegMap.put(99501l, new K3DrawNumberChecker());
		resultCodeRegMap.put(99502l, new K3DrawNumberChecker());
		resultCodeRegMap.put(99601l, new K3DrawNumberChecker());
		resultCodeRegMap.put(99701l, new LhcDrawNumberChecker());
	}

	/** 
	* @Title: isRightResultNumber 
	* @Description: 校验开奖号码格式是否正确
	* @param lotteryId
	* @param resultNumber
	* @return
	*/
	public static boolean isRightResultNumber(Long lotteryId, String resultNumber) {
		if (resultCodeRegMap.get(lotteryId) != null) {
			return resultCodeRegMap.get(lotteryId).checkDrawNumber(resultNumber);
		} else {
			return true;
		}
	}
}

abstract class GameDrawNumberChecker {
	abstract boolean checkDrawNumber(String resultNumber);
};

/** 
* @ClassName: SSCDrawNumberChecker 
* @Description: 时时彩系开奖号码
* @author 你的名字 
* @date 2014-6-6 上午9:46:53 
*  
*/
class SSCDrawNumberChecker extends GameDrawNumberChecker {

	@Override
	boolean checkDrawNumber(String resultNumber) {
		Pattern pattern = Pattern.compile("^[0-9]{5}");
		Matcher matcher = pattern.matcher(resultNumber);
		return matcher.matches();
	}
}

/** 
* @ClassName: ThreeeDDrawNumberChecker 
* @Description: 3D系开奖号码
* @author 你的名字 
* @date 2014-6-6 上午9:47:11 
*  
*/
class ThreeeDDrawNumberChecker extends GameDrawNumberChecker {
	@Override
	boolean checkDrawNumber(String resultNumber) {
		Pattern pattern = Pattern.compile("^[0-9]{3}");
		Matcher matcher = pattern.matcher(resultNumber);
		return matcher.matches();
	}
}

/** 
* @ClassName: K3DrawNumberChecker 
* @Description: k3系开奖号码
* @author 你的名字 
* @date 2014-6-6 上午9:47:11 
*  
*/
class K3DrawNumberChecker extends GameDrawNumberChecker {
	@Override
	boolean checkDrawNumber(String resultNumber) {
		Pattern pattern = Pattern.compile("^[1-6]{3}");
		Matcher matcher = pattern.matcher(resultNumber);
		return matcher.matches();
	}
}

/** 
* @ClassName: ThreeeDDrawNumberChecker 
* @Description: 11选5开奖号码
* @author 你的名字 
* @date 2014-6-6 上午9:47:11 
*  
*/
class ElevenChsFiveeDrawNumberChecker extends GameDrawNumberChecker {
	@Override
	boolean checkDrawNumber(String resultNumber) {
		String[] aimArr = new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11" };
		List<String> aimList = Arrays.asList(aimArr);
		//11选5系          01,02,03,04,05(01----11)
		String[] resultArr = resultNumber.split(",");
//		if (resultArr.length != 5) {
//			return false;
//		}
//		
		Set<String> re = new HashSet<String>();
		for (String string : resultArr) {
			re.add(string);
			if (!aimList.contains(string.trim())) {
				return false;
			}
		}
		
		if(re.size() != 5){
			return false;
		}
		
		return true;
	}
}

/** 
* @ClassName: JiNuoDrawNumberChecker 
* @Description: 基诺系
* @author 你的名字 
* @date 2014-6-6 上午9:49:53 
*  
*/
class JiNuoDrawNumberChecker extends GameDrawNumberChecker {
	private List<String> aimList = new ArrayList<String>();
	{
		for (int i = 1; i < 81; i++) {
			if (i < 10) {
				aimList.add("0" + String.valueOf(i));
			} else {
				aimList.add(String.valueOf(i));
			}
		}
	}

	@Override
	boolean checkDrawNumber(String resultNumber) {
		String[] resultArr = resultNumber.split(",");
		if (resultArr.length != 20) {
			return false;
		}
		for (String string : resultArr) {
			if (!aimList.contains(string)) {
				return false;
			}
		}
		return true;
	}
}

/** 
* @ClassName: DoubleColorDrawNumberChecker 
* @Description: 双色球
* @author 你的名字 
* @date 2014-6-6 上午9:49:53 
*  
*/
class DoubleColorDrawNumberChecker extends GameDrawNumberChecker {
	List<String> beforeList = new ArrayList<String>();
	List<String> afterList = new ArrayList<String>();
	{
		for (int i = 1; i < 34; i++) {
			if (i < 10) {
				beforeList.add("0" + String.valueOf(i));
			} else {
				beforeList.add(String.valueOf(i));
			}
		}

		for (int i = 1; i < 17; i++) {
			if (i < 10) {
				afterList.add("0" + String.valueOf(i));
			} else {
				afterList.add(String.valueOf(i));
			}
		}
	}

	@Override
	boolean checkDrawNumber(String resultNumber) {
		if (!resultNumber.contains("+")) {
			return false;
		}
		String[] split = resultNumber.split("\\+");
		String[] beforeNumberArr = split[0].split(",");
		String afterNumber = split[1];
		//双色球系    01，02,03,10,13,33+13	（1---33选6在1-16选1）
		if (beforeNumberArr.length != 6) {
			return false;
		}
		for (String string : beforeNumberArr) {
			if (!beforeList.contains(string)) {
				return false;
			}
		}
		if (!afterList.contains(afterNumber)) {
			return false;
		}
		return true;
	}
}

	/** 
	* @ClassName: LhcDrawNumberChecker 
	* @Description: 六合系开奖号码
	* @author 你的名字 
	* @date 2014-6-6 上午9:47:11 
	*  
	*/
	class LhcDrawNumberChecker extends GameDrawNumberChecker {
		private Logger log = LoggerFactory.getLogger(LhcDrawNumberChecker.class);

		@Override
		boolean checkDrawNumber(String resultNumber) {
			List<String> aimList = new ArrayList<String>();
			for (int i = 1; i < 50; i++) {
				if (i < 10) {
					aimList.add("0" + String.valueOf(i));
				} else {
					aimList.add(String.valueOf(i));
				}
			}
			
			String[] resultArr = resultNumber.split(",");
			if (resultArr.length != 7) {
				log.info("LHC not 7");
				return false;
			}
			for (String string : resultArr) {
				log.info("LHC"+string);
				if (!aimList.contains(string)) {
					log.info("LHC not contains 01~49");
					return false;
				}
			}
			
			//驗證開獎號碼是否重複
			for (String string : resultArr) {
				aimList.remove(string);
			}
			if(aimList.size()!=42){
				log.info("LHC number repeat");
				return false;
			}
			return true;
		}
	}
	
	
	