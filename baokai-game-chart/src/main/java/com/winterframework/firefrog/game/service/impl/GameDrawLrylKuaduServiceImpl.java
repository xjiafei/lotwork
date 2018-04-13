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
* @ClassName: GameDrawLrylKuaduServiceImpl 
* @Description: 跨度的冷热遗漏查询Service实现类
* @author Denny 
* @date 2013-12-20 上午10:46:09 
*  
*/
@Service("gameDrawLrylKuaduServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawLrylKuaduServiceImpl implements IGameDrawLrylService {

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao;

	@Override
	public List<GameLryl> queryColdAndHotNumbers(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,
			int countIssue) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, countIssue);

		// 历史期数的跨度list
		List<Integer> kuaduList = calculateKuadu(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getKuaduMap(kuaduList);

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
	* @Title: getKuaduMap 
	* @Description:  整理跨度值列表为按选号对应的map
	*/
	private Map<Integer, Integer> getKuaduMap(List<Integer> kuaduList) {

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			map.put(i, 0);
		}

		for (Integer j : kuaduList) {
			map.put(j, map.get(j) + 1);
		}
		return map;
	}

	/** 
	* @Title: calculateKuadu 
	* @Description: 计算跨度
	* @param gdrs
	* @param gameGroupCode
	* @return List<Integer>    返回类型 
	* @throws 
	*/
	private List<Integer> calculateKuadu(List<GameDrawResult> gdrs, int gameGroupCode) {
		List<String> calculateNumberList = new ArrayList<String>();
		for (GameDrawResult gdr : gdrs) {
			String numberRecord = gdr.getNumberRecord();
			String calculateNumber = "";
			if (gameGroupCode == 12) {
				// 前三
				calculateNumber = numberRecord.substring(0, 3);
			} else if (gameGroupCode == 13) {
				if (numberRecord.length() == 5) {
					// 后三
					calculateNumber = numberRecord.substring(numberRecord.length() - 3, numberRecord.length());
				} else {
					calculateNumber = numberRecord;
				}

			} else if (gameGroupCode == 333) {
				calculateNumber = numberRecord.substring(1, 4);
			} else if (gameGroupCode == 14) {
				// 后二
				calculateNumber = numberRecord.substring(numberRecord.length() - 2, numberRecord.length());
			} else if (gameGroupCode == 15) {
				// 前二
				calculateNumber = numberRecord.substring(0, 2);
			}
			calculateNumberList.add(calculateNumber);
		}

		List<Integer> kuaduList = new ArrayList<Integer>();
		for (String n : calculateNumberList) {
			Integer kuadu = parse(n);
			kuaduList.add(kuadu);
		}

		return kuaduList;
	}

	/** 
	* @Title: parse 
	* @Description: 算中奖号码的跨度
	*/
	private Integer parse(String n) {
		int max = 0;
		int min = 28;
		for (int i = 0; i < n.length(); i++) {
			int current = Integer.parseInt(String.valueOf(n.charAt(i)));
			if (current > max) {
				max = current;
			}

			if (current < min) {
				min = current;
			}
		}
		return max - min;
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
	* @Description: 查询最大遗漏值
	*/
	private List<GameLryl> queryMaxMissingValue(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		// 历史期数的跨度list
		List<Integer> kuaduList = calculateKuadu(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getMaxMissingMap(kuaduList);

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
	* @throws Exception 
	 * @Title: queryCurrentMissingValue 
	* @Description: 查询当前遗漏值
	*/
	private List<GameLryl> queryCurrentMissingValue(long lotteryId, int gameGroupCode, int gameSetCode)
			throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		// 历史期数的跨度list
		List<Integer> kuaduList = calculateKuadu(gdrs, gameGroupCode);

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		map = getCurrentMissingMap(kuaduList);

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
	* @Description: 整理跨度值列表为按选号对应的map
	* @param kuaduList
	* @return Map<Integer,Integer>    返回类型 
	* @throws 
	*/
	private Map<Integer, Integer> getCurrentMissingMap(List<Integer> kuaduList) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			map.put(i, 0);
		}

		for (Integer i : map.keySet()) {
			int value = 0;
			for (int j = kuaduList.size() - 1; j >= 0; j--) {
				if (i == kuaduList.get(j)) {
					break;
				} else {
					value++;
				}
			}

			map.put(i, value);
		}

		return map;
	}

	/** 
	* @Title: getMaxMissingMap 
	* @Description: 整理跨度值列表为按选号对应的map
	*/
	private Map<Integer, Integer> getMaxMissingMap(List<Integer> kuaduList) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < 10; i++) {
			map.put(i, 0);
		}

		for (Integer i : map.keySet()) {
			int sum = 0;
			int temp = 0;
			for (int j = 0; j < kuaduList.size(); j++) {
				if (i.equals(kuaduList.get(j))) {
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

}
