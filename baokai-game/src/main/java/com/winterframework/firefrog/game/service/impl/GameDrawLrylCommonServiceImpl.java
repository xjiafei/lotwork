package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.service.IGameDrawLrylService;

/** 
* @ClassName: GameDrawLrylCommonServiceImpl 
* @Description: 选号球(0~9)的冷热遗漏查询 
* @author Denny 
* @date 2013-12-20 上午10:47:06 
*  
*/
@Service("gameDrawLrylCommonServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawLrylCommonServiceImpl implements IGameDrawLrylService {

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;
	
	@Resource(name ="gameTrendJbylDaoImpl")
	private IGameTrendJbylDao chartDao;
	
	@Override
	public List<GameLryl> queryColdAndHotNumbers(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int countIssue) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, countIssue);
		Map<Integer, List<Integer>> numberRecordEveryDigit = getNumberRecordEveryDigit(gdrs);

		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		
		GameLryl gl;
		for (int i = startBit; i >= endBit; i--) {
			List<Integer> l = numberRecordEveryDigit.get(i);
			for (int j = 0; j < 10; j++) {
				int v = getColdAndHotNumbers(l, j);
				gl = new GameLryl();
				gl.setBitNumber(i);
				gl.setLottNumber(j);
				gl.setRetValue(v);
				gls.add(gl);
			}
		}

		return gls;
	}

	/** 
	* @Title: getStartAndEndBitForEveryGameGroup 
	* @Description: 获取玩法群对应的冷热遗漏号码的开始位和结束位
	*/
	private static List<Integer> getStartAndEndBitForEveryGameGroup(int gameGroupCode) {
		List<Integer> list = new ArrayList<Integer>();

		switch (gameGroupCode) {
			case 10:
				list.add(5);
				list.add(1);
				break;
			case 11:
				list.add(4);
				list.add(1);
				break;
			case 12:
				list.add(5);
				list.add(3);
				break;
			case 13:
				list.add(3);
				list.add(1);
				break;
			case 14:
				list.add(2);
				list.add(1);
				break;
			case 15:
				list.add(5);
				list.add(4);
				break;
			case 16:
				list.add(5);
				list.add(1);
				break;
			default:
				list.add(5);
				list.add(1);
				break;
		}
		return list;
	}

	/** 
	* @Title: getColdAndHotNumbers 
	* @Description: 获取某一位数上某一选号出现的次数
	*/
	private int getColdAndHotNumbers(List<Integer> l, int j) {
		int count = 0;
		for (int number : l) {
			if (number == j) {
				count++;
			}
		}
		return count;
	}

	@Override
	public List<GameLryl> queryMissingValue(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int queryType) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();

		if (queryType == 1) {
			// 当前遗漏
			gls = queryCurrentMissingValue(lotteryId, gameGroupCode, gameSetCode);
		} else if (queryType == 2) {
			// 最大遗漏
			gls = queryMaxMissingValue(lotteryId, gameGroupCode, gameSetCode);
		}
		return gls;
	}

	private List<GameLryl> queryCurrentMissingValue(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		//20131228 add 获取数据库的值。
		//获取最后的一条数据
//		GameTrendChart chart = chartDao.getLastGameTrendChartByLotteryId(lotteryId);
//		String omission  = "";
//		if (chart != null ){
//			omission  = chart.getOmission();
//		}
//		if(StringUtils.isNotBlank(omission)){
//			
//			String[] _strs = omission.split(",");
//			
//			Map<Integer, List<Integer>> omissionListEveryDigit = getOmissionListEveryDigit(_strs);
//			for (int i = startBit; i >= endBit; i--) {
//				List<Integer> l = omissionListEveryDigit.get(i);
//				for (int j = 0; j < l.size(); j++) {
//					gl = new GameLryl();
//					gl.setBitNumber(i);
//					gl.setLottNumber(j);
//					gl.setRetValue(l.get(j));
//					gls.add(gl);
//				}
//			}
//		}
		
		return gls;
	}
	
	private Map<Integer, List<Integer>> getOmissionListEveryDigit(String[] arr) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		List<Integer> l = new ArrayList<Integer>();
		int digit = 5;
		for (int i = 0; i < arr.length; i++) {
			if (i % 10 == 0) {
				l = new ArrayList<Integer>();
				map.put(digit, l);
				digit--;
			}
			l.add(Integer.parseInt(arr[i]));
		}
		return map;
	}

	private List<GameLryl> queryMaxMissingValue(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		Map<Integer, List<Integer>> numberRecordEveryDigit = getNumberRecordEveryDigit(gdrs);

		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		
		for (int i = startBit; i >= endBit; i--) {
			List<Integer> l = numberRecordEveryDigit.get(i);
			for (int j = 0; j < 10; j++) {
				int v = getMaxMissingValue(l, j);
				gl = new GameLryl();
				gl.setBitNumber(i);
				gl.setLottNumber(j);
				gl.setRetValue(v);
				gls.add(gl);
			}
		}

		return gls;
	}

	/** 
	* @Title: getCurrentMissingValue 
	* @Description: 获取某一位数上某一选号的当前遗漏值
	*/
	private int getCurrentMissingValue(List<Integer> l, int n) {
		for (int i = 0; i < l.size(); i++) {
			if (n == l.get(i)) {
				return i;
			}
		}
		return l.size();
	}

	/** 
	* @Title: getMaxMissingValue 
	* @Description: 获取某一位数上某一选号的最大遗漏值
	*/
	private int getMaxMissingValue(List<Integer> l, int n) {
		int maxMissingValue = getCurrentMissingValue(l, n);
		int tempMissingValue = 0;
		int startIndex = -1;
		for (int i = 0; i < l.size(); i++) {
			if (n == l.get(i)) {
				tempMissingValue = i - startIndex - 1;
				if (tempMissingValue > maxMissingValue) {
					maxMissingValue = tempMissingValue;
				}
				startIndex = i;
			}
		}
		tempMissingValue = l.size() - startIndex - 1;
		if (tempMissingValue > maxMissingValue) {
			maxMissingValue = tempMissingValue;
		}
		return maxMissingValue;
	}

	/** 
	* @Title: getNumberRecordEveryDigit 
	* @Description: 获取每个位数上对应的历史中奖号码列表
	*/
	private Map<Integer, List<Integer>> getNumberRecordEveryDigit(List<GameDrawResult> gdrs) {
		Map<Integer, List<Integer>> numberRecordEveryDigit = new HashMap<Integer, List<Integer>>();
		// 万位的所有中奖号码
		List<Integer> myriabit = new ArrayList<Integer>();
		// 千位
		List<Integer> kilobit = new ArrayList<Integer>();
		// 百位
		List<Integer> hundred = new ArrayList<Integer>();
		// 十位
		List<Integer> decade = new ArrayList<Integer>();
		// 个位
		List<Integer> units = new ArrayList<Integer>();

		for (GameDrawResult gdr : gdrs) {
			Integer numberRecord = Integer.parseInt(gdr.getNumberRecord());
			int mbit = numberRecord / 10000;
			int kbit = (numberRecord % 10000) / 1000;
			int hbit = (numberRecord % 1000) / 100;
			int dbit = (numberRecord % 100) / 10;
			int ubit = numberRecord % 10;

			myriabit.add(mbit);
			kilobit.add(kbit);
			hundred.add(hbit);
			decade.add(dbit);
			units.add(ubit);
		}

		numberRecordEveryDigit.put(1, units);
		numberRecordEveryDigit.put(2, decade);
		numberRecordEveryDigit.put(3, hundred);
		numberRecordEveryDigit.put(4, kilobit);
		numberRecordEveryDigit.put(5, myriabit);

		return numberRecordEveryDigit;
	}

	
	
}
