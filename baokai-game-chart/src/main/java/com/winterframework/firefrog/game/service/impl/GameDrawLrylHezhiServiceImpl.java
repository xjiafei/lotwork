package com.winterframework.firefrog.game.service.impl;

import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.service.IGameDrawLrylService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @ClassName: GameDrawLrylHezhiServiceImpl 
* @Description: 和值的冷热遗漏查询Service实现类 
* @author Denny 
* @date 2013-12-20 上午10:41:11 
*  
*/
@Service("gameDrawLrylHezhiServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawLrylHezhiServiceImpl implements IGameDrawLrylService {

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	@Override
	public List<GameLryl> queryColdAndHotNumbers(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,
			int countIssue) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, countIssue);

		// 历史期数的和值list
		List<Integer> hezhiList = calculateHezhi(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getHezhiMap(hezhiList, gameGroupCode);

		GameLryl gl = null;
		for (Integer i : map.keySet()) {
			gl = new GameLryl();
			gl.setBitNumber(1);
			gl.setLottNumber(i);
			gl.setRetValue(map.get(i));
			gls.add(gl);
		}
		return gls;
	}

	/** 
	* @Title: getHezhiMap 
	* @Description: 根据玩法群将和值列表整理成与选号对应的冷热值map
	* @param hezhiList
	* @param gameGroupCode
	* @return Map<Integer,Integer>    返回类型 
	* @throws 
	*/
	private Map<Integer, Integer> getHezhiMap(List<Integer> hezhiList, int gameGroupCode) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (gameGroupCode == 12 || gameGroupCode == 13 || gameGroupCode == 333) {
			for (int i = 0; i < 28; i++) {
				map.put(i, 0);
			}
		} else if (gameGroupCode == 14 || gameGroupCode == 15) {
			for (int i = 0; i < 19; i++) {
				map.put(i, 0);
			}
		}else if(gameGroupCode == 34){
			for (int i = 3; i < 19; i++) {
				map.put(i, 0);
			}
		}

		for (Integer i : hezhiList) {
			map.put(i, map.get(i) + 1);
		}
		return map;
	}

	/** 
	* @Title: calculateHezhi 
	* @Description: 计算所有期数的和值列表 
	* @param gdrs
	* @param gameGroupCode
	* @return    设定文件 
	* @return List<Integer>    返回类型 
	* @throws 
	*/
	private List<Integer> calculateHezhi(List<GameDrawResult> gdrs, int gameGroupCode) {

		List<String> calculateNumberList = new ArrayList<String>();
		for (GameDrawResult gdr : gdrs) {
			String numberRecord = gdr.getNumberRecord();
			String calculateNumber = "";
			if (gameGroupCode == 12) {
				// 前三
				calculateNumber = numberRecord.substring(0, 3);
			} else if (gameGroupCode == 13) {
				// 后三
				calculateNumber = numberRecord.substring(numberRecord.length() - 3, numberRecord.length());

			} else if (gameGroupCode == 333) {
				// 后三
				calculateNumber = numberRecord.substring(1, 4);
			} else if (gameGroupCode == 14) {
				// 后二
				calculateNumber = numberRecord.substring(numberRecord.length() - 2, numberRecord.length());
			} else if (gameGroupCode == 15) {
				// 前二
				calculateNumber = numberRecord.substring(0, 2);
			}else{
				calculateNumber = numberRecord;
			}
			calculateNumberList.add(calculateNumber);
		}

		List<Integer> hezhiList = new ArrayList<Integer>();
		for (String n : calculateNumberList) {
			Integer hezhi = parse(n);
			hezhiList.add(hezhi);
		}

		return hezhiList;
	}

	/** 
	* @Title: parse 
	* @Description: 按中奖号码算和值
	* @param n
	* @return    设定文件 
	* @return Integer    返回类型 
	* @throws 
	*/
	private Integer parse(String n) {
		int sum = 0;
		for (int i = 0; i < n.length(); i++) {
			sum += Integer.parseInt(String.valueOf(n.charAt(i)));
		}
		return sum;
	}

	@Override
	public List<GameLryl> queryMissingValue(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,
			int queryType) throws Exception {
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

	/**
	 * @throws Exception  
	* @Title: queryMaxMissingValue 
	* @Description: 查询历史遗漏值
	* @param lotteryId
	* @param gameGroupCode
	* @param gameSetCode
	* @param @return    设定文件 
	* @return List<GameLryl>    返回类型 
	* @throws 
	*/
	private List<GameLryl> queryMaxMissingValue(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		// 历史期数的和值list
		List<Integer> hezhiList = calculateHezhi(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getMaxMissingMap(hezhiList, gameGroupCode);

		for (Integer i : map.keySet()) {
			gl = new GameLryl();
			gl.setBitNumber(1);
			gl.setLottNumber(i);
			gl.setRetValue(map.get(i));
			gls.add(gl);
		}
		return gls;
	}

	/** 
	* @Title: getMaxMissingMap 
	* @Description: 根据玩法群将和值列表整理成与选号对应的最大遗漏值map
	* @param hezhiList
	* @param gameGroupCode
	* @return Map<Integer,Integer>    返回类型 
	* @throws 
	*/
	private Map<Integer, Integer> getMaxMissingMap(List<Integer> hezhiList, int gameGroupCode) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (gameGroupCode == 12 || gameGroupCode == 13 || gameGroupCode == 333) {
			for (int i = 0; i < 28; i++) {
				map.put(i, 0);
			}
		} else if (gameGroupCode == 14 || gameGroupCode == 15) {
			for (int i = 0; i < 19; i++) {
				map.put(i, 0);
			}
		}

		for (Integer i : map.keySet()) {
			int temp = 0;
			int sum = 0;
			for (int j = 0; j < hezhiList.size(); j++) {

				if (i.equals(hezhiList.get(j))) {
					if (sum > temp) {
						temp = sum;
					}
					sum = 0;

				} else {
					sum++;
				}
			}
			if (temp > sum) {
				map.put(i, temp);
			} else {
				map.put(i, sum);
			}

		}
		return map;
	}

	/**
	 * @throws Exception  
	* @Title: queryCurrentMissingValue 
	* @Description: 查询当前遗漏值 
	* @param lotteryId
	* @param gameGroupCode
	* @param gameSetCode
	* @return List<GameLryl>    返回类型 
	* @throws 
	*/
	private List<GameLryl> queryCurrentMissingValue(long lotteryId, int gameGroupCode, int gameSetCode)
			throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		// 历史期数的和值list
		List<Integer> hezhiList = calculateHezhi(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getCurrentMissingMap(hezhiList, gameGroupCode);

		for (Integer i : map.keySet()) {
			gl = new GameLryl();
			gl.setBitNumber(1);
			gl.setLottNumber(i);
			gl.setRetValue(map.get(i));
			gls.add(gl);
		}
		return gls;

	}

	/** 
	* @Title: getCurrentMissingMap 
	* @Description:  根据玩法群将和值列表整理成与选号对应的当前遗漏值map
	* @param hezhiList
	* @param gameGroupCode
	* @return Map<Integer,Integer>    返回类型 
	* @throws 
	*/
	private Map<Integer, Integer> getCurrentMissingMap(List<Integer> hezhiList, int gameGroupCode) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		if (gameGroupCode == 12 || gameGroupCode == 13 || gameGroupCode == 333) {
			for (int i = 0; i < 28; i++) {
				map.put(i, 0);
			}
		} else if (gameGroupCode == 14 || gameGroupCode == 15) {
			for (int i = 0; i < 19; i++) {
				map.put(i, 0);
			}
		}

		for (Integer i : map.keySet()) {
			int value = 0;
			for (int j = hezhiList.size() - 1; j >= 0; j--) {
				if (i.equals(hezhiList.get(j))) {
					break;
				} else {
					value++;
				}
			}

			map.put(i, value);
		}

		return map;
	}

}
