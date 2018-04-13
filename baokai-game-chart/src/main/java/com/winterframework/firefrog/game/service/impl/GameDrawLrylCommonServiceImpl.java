package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameDrawResultDao;
import com.winterframework.firefrog.game.dao.IGameTrendJbylDao;
import com.winterframework.firefrog.game.dao.vo.GameDrawResult;
import com.winterframework.firefrog.game.dao.vo.GameTrendBet;
import com.winterframework.firefrog.game.dao.vo.GameTrendJbyl;
import com.winterframework.firefrog.game.entity.GameLryl;
import com.winterframework.firefrog.game.service.IGameDrawLrylService;
import com.winterframework.firefrog.game.service.IGameTrendBetService;

/** 
* @ClassName: GameDrawLrylCommonServiceImpl 
* @Description: 冷热遗漏查询 
* @author Denny 
* @date 2013-12-20 上午10:47:06 
*  
*/
@Service("gameDrawLrylCommonServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawLrylCommonServiceImpl implements IGameDrawLrylService {

	@Resource(name = "gameDrawResultDaoImpl")
	private IGameDrawResultDao gameDrawResultDao; 
	@Resource(name = "ballRengeMap")
	private Map<String, String> ballRengeMap;
    @Resource(name = "gameTrendJbylDaoImpl")
	private IGameTrendJbylDao gameTrendJbylDao;
    @Resource(name ="gameTrendBetServiceImpl")
	private IGameTrendBetService gameTrendBetService; 
    @Resource(name = "hezhiRengeMap")
	private Map<String, String> hezhiRengeMap;
    
	
	@Override
	public List<GameLryl> queryColdAndHotNumbers(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int countIssue) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		int trendBetType=GameTrendBet.Type.HOTCOLD_30.getValue();
		if(countIssue==60){
			trendBetType=GameTrendBet.Type.HOTCOLD_60.getValue();
		}else if(countIssue==100){
			trendBetType=GameTrendBet.Type.HOTCOLD_100.getValue();
		}
		GameTrendBet trendBet = this.getGameTrendBet(lotteryId, gameGroupCode,gameSetCode,betMethodCode,trendBetType);
		if(trendBet!=null){  
			gls=makeLrylList(lotteryId, gameGroupCode,gameSetCode,betMethodCode, trendBet.getValue());
		} 
		return gls;
	}
	
	public List<GameLryl> queryColdAndHotNumbers_bk(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode, int countIssue) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryIdAndCountIssue(lotteryId, countIssue);
        if (lotteryId == 99401l) {
            return getNumberRecordEveryDigitSSQ(gdrs);
        }
		Map<Integer, List<Integer>> numberRecordEveryDigit = getNumberRecordEveryDigit(gdrs);
		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		
		String[] ballRenge = ballRengeMap.get(String.valueOf(lotteryId)).split(",");

		int ballStartIndex = Integer.parseInt(ballRenge[0]);
		int ballEndIndex = Integer.parseInt(ballRenge[1]);
		
		GameLryl gl;
		for (int i = startBit; i >= endBit; i--) {
			List<Integer> l = numberRecordEveryDigit.get(i);
			for (int j = ballStartIndex; j <= ballEndIndex; j++) {
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
			case 333:
				list.add(4);
				list.add(2);
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
			case 35:
			case 38:
				list.add(0);
				list.add(0);
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
			gls = queryCurrentMissingValue(lotteryId, gameGroupCode, gameSetCode, betMethodCode);
		} else if (queryType == 2) {
			// 最大遗漏
			gls = queryMaxMissingValue(lotteryId, gameGroupCode, gameSetCode, betMethodCode);
		}
		return gls;
	}

	private List<GameLryl> queryCurrentMissingValue(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>(); 
        if (lotteryId == 99401l) {
            return queryCurrentMissingValueSSQ(lotteryId, gameGroupCode,gameSetCode,betMethodCode);
        }
        GameTrendBet trendBet = this.getGameTrendBet(lotteryId, gameGroupCode,gameSetCode,betMethodCode,GameTrendBet.Type.CURRENT.getValue());
		if(trendBet!=null){  
			gls=makeLrylList(lotteryId, gameGroupCode, gameSetCode,betMethodCode, trendBet.getValue()); 
		}
        
		return gls;
	}

	protected List<GameLryl> makeLrylList(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,String omitValue) {
		List<GameLryl> gls=new ArrayList<GameLryl>();
		GameLryl gl;
		if(omitValue != null){
			int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
			int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
			
			String[] ballRenge =null;

			if (betMethodCode == 33 || gameGroupCode==344) {	//和值
				ballRenge=hezhiRengeMap.get(String.valueOf(gameGroupCode)).split(","); 
			} else if (betMethodCode == 34) {	//跨度
				ballRenge="0,9".split(","); 
			}else{
				ballRenge = ballRengeMap.get(String.valueOf(lotteryId)).split(",");
			}			
			int ballStartIndex = Integer.parseInt(ballRenge[0]);
			int ballEndIndex = Integer.parseInt(ballRenge[1]);
			int ballCount = ballEndIndex - ballStartIndex + 1;
			
			if (betMethodCode == 12) {	//胆拖--胆码+拖码
				omitValue+=","+omitValue;
			}
			String[] _strs = omitValue.split(",");
			
			Map<Integer, List<Integer>> omissionListEveryDigit = getOmissionListEveryDigit(_strs, startBit, endBit, ballCount);
			for (int i = startBit; i >= endBit; i--) {
				List<Integer> l = omissionListEveryDigit.get(i);
				if (l == null || l.size() == 0) {
					break;
				}
				int index = ballStartIndex;
				for (int j = 0; j < l.size(); j++) {
					
					gl = new GameLryl();
					gl.setBitNumber(i);
					gl.setLottNumber(index);
					gl.setRetValue(l.get(j));
					index++;
					gls.add(gl);
				}
			}
		}
		return gls;
	}
	@Deprecated
	private List<GameLryl> queryCurrentMissingValue_bk(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;
        if (lotteryId == 99401l) {
            return queryCurrentMissingValueSSQ(lotteryId, gameGroupCode,0,0);
        }
		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		 
		String[] ballRenge = ballRengeMap.get(String.valueOf(lotteryId)).split(",");

		int ballStartIndex = Integer.parseInt(ballRenge[0]);
		int ballEndIndex = Integer.parseInt(ballRenge[1]);
		int ballCount = ballEndIndex - ballStartIndex + 1;
		
		
		//获取最后的一条数据
		GameTrendJbyl chart = gameTrendJbylDao.getLastWeiShuTrend(lotteryId, gameGroupCode);
		String omission  = "";
		if (chart != null ){
			omission  = chart.getValue();
		}
		if(omission != null){
		
			String[] _strs = omission.split(",");
			
			Map<Integer, List<Integer>> omissionListEveryDigit = getOmissionListEveryDigit(_strs, startBit, endBit, ballCount);
			for (int i = startBit; i >= endBit; i--) {
				List<Integer> l = omissionListEveryDigit.get(i);
				if (l == null || l.size() == 0) {
					break;
				}
				int index = ballStartIndex;
				for (int j = 0; j < l.size(); j++) {
					
					gl = new GameLryl();
					gl.setBitNumber(i);
					gl.setLottNumber(index);
					gl.setRetValue(l.get(j));
					index++;
					gls.add(gl);
				}
			}
		}
		
		return gls;
	}

	private GameTrendBet getGameTrendBet(long lotteryId, int groupCode,
			int setCode,int methodCode,int trendBetType) throws Exception {
		GameContext ctx=new GameContext();
		return this.gameTrendBetService.getByLotteryIdAndBetTypeAndType(ctx, lotteryId, groupCode, setCode, methodCode, trendBetType);
	}
	private List<GameLryl> queryCurrentMissingValueSSQ(long lotteryId, int gameGroupCode,int gameSetCode, int betMethodCode) throws Exception {
    	List<GameLryl> gameLrylList = new ArrayList<GameLryl>();
		GameTrendBet  trendBet=this.getGameTrendBet(lotteryId, gameGroupCode, gameSetCode, betMethodCode, GameTrendBet.Type.CURRENT.getValue());
		if(trendBet!=null){ 
			//GameTrendJbyl gameTrendJbyl = gameTrendJbylDao.getLastWeiShuTrend(lotteryId, gameGroupCode);
			String[] balls = trendBet.getValue().split(",");
			for (int i = 0; i < balls.length; i++) {
				GameLryl gl = new GameLryl();
				gl.setRetValue(Integer.valueOf(balls[i]));
				if (i > 32) {
					gl.setBitNumber(i - 32);
					gl.setLottNumber(i - 32);
				} else {
					gl.setBitNumber(i + 1);
					gl.setLottNumber(i + 1);
				} 
				gameLrylList.add(gl);
			}
		}
        return gameLrylList;
    }
    private List<GameLryl> queryCurrentMissingValueSSQ_bk(long lotteryId, int gameGroupCode) throws Exception {
    	List<GameLryl> gameLrylList = new ArrayList<GameLryl>();
		GameTrendBet  trendBet=this.getGameTrendBet(lotteryId, gameGroupCode, 0, 0, GameTrendBet.Type.CURRENT.getValue());
		if(trendBet!=null){ 
			//GameTrendJbyl gameTrendJbyl = gameTrendJbylDao.getLastWeiShuTrend(lotteryId, gameGroupCode);
			String[] balls = trendBet.getValue().split(",");
			for (int i = 0; i < balls.length; i++) {
				GameLryl gl = new GameLryl();
				gl.setRetValue(Integer.valueOf(balls[i]));
				if (i > 32) {
					gl.setBitNumber(i - 32);
					gl.setLottNumber(i - 32);
				} else {
					gl.setBitNumber(i + 1);
					gl.setLottNumber(i + 1);
				} 
				gameLrylList.add(gl);
			}
		}
        return gameLrylList;
    }

	/** 
	 * @param arr
	 * @param startBit 5
	 * @param endBit	1
	 * @param ballCount	10,11
	 * @return
	 * 5,[1,2,3,4,5,6,7,8,9,10]
	 * 4,[1,2,3,4,5,6,7,8,9,10]
	 * ...
	 * 1,[1,2,3,4,5,6,7,8,9,10]
	 */
	protected Map<Integer, List<Integer>> getOmissionListEveryDigit(String[] arr, int startBit, int endBit, int ballCount) {
		Map<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
		List<Integer> l = new ArrayList<Integer>();
		
		for (int i = 0; i < arr.length; i++) {
			if (i % ballCount == 0) {
				l = new ArrayList<Integer>();
				map.put(startBit, l);
				startBit--;
			}
			l.add(Integer.parseInt(arr[i]));
		}
		return map;
	}
	private List<GameLryl> queryMaxMissingValue(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameTrendBet trendBet = this.getGameTrendBet(lotteryId, gameGroupCode,gameSetCode,betMethodCode,GameTrendBet.Type.MAX.getValue());
		if(trendBet!=null){  
			gls=makeLrylList(lotteryId, gameGroupCode,gameSetCode,betMethodCode,trendBet.getValue());
		}
		return gls;
	}
	private List<GameLryl> queryMaxMissingValue_bk(long lotteryId, int gameGroupCode, int gameSetCode) throws Exception {
		List<GameLryl> gls = new ArrayList<GameLryl>();
		GameLryl gl;

		List<GameDrawResult> gdrs = gameDrawResultDao.getAllByLotteryId(lotteryId);
		Map<Integer, List<Integer>> numberRecordEveryDigit = getNumberRecordEveryDigit(gdrs);
        if (lotteryId == 99401l) {
            return getNumberRecordEveryDigitSSQ(gdrs);
        }
		int startBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(0);
		int endBit = getStartAndEndBitForEveryGameGroup(gameGroupCode).get(1);
		
		String[] ballRenge = ballRengeMap.get(String.valueOf(lotteryId)).split(",");

		int ballStartIndex = Integer.parseInt(ballRenge[0]);
		int ballEndIndex = Integer.parseInt(ballRenge[1]);

		for (int i = startBit; i >= endBit; i--) {
			List<Integer> l = numberRecordEveryDigit.get(i);
			for (int j = ballStartIndex; j <= ballEndIndex; j++) {
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

    private List<GameLryl> getNumberRecordEveryDigitSSQ(List<GameDrawResult> gdrs) {
        List<GameLryl> gameLryls = new ArrayList<GameLryl>();
        //构建双色球的列表
        //红球
        for (int i = 1; i <= 33; i++) {
            GameLryl gameLryl = new GameLryl();
            gameLryl.setBitNumber(i);
            gameLryl.setLottNumber(i);
            gameLryl.setRetValue(0);
            gameLryl.setValueTemp(0);
            gameLryls.add(gameLryl);
        }
        for (GameDrawResult gr : gdrs) {
            gr.setNumberRecord(gr.getNumberRecord().replace("+", ","));
            String[] balls = gr.getNumberRecord().split(",");
            for (GameLryl gl : gameLryls) {
                gl.setValueTemp(gl.getValueTemp() + 1);
                if (gl.getValueTemp() > gl.getRetValue()) {
                    gl.setRetValue(gl.getValueTemp());
                }
                for (int i = 0; i < 7; i++) {
                    if (gl.getLottNumber() == Integer.valueOf(balls[i])) {
                        gl.setRetValue(0);
                        break;
                    }
                }

            }
        }
        //篮球
        List<GameLryl> gameLryls1 = new ArrayList<GameLryl>();
        for (int i = 1; i <= 16; i++) {
            GameLryl gameLryl = new GameLryl();
            gameLryl.setBitNumber(i);
            gameLryl.setLottNumber(i);
            gameLryl.setRetValue(0);
            gameLryl.setValueTemp(0);
            gameLryls1.add(gameLryl);
        }
        for (GameDrawResult gr : gdrs) {
            String[] balls = gr.getNumberRecord().split(",");
            for (GameLryl gl : gameLryls1) {
                gl.setValueTemp(gl.getValueTemp() + 1);
                if (gl.getValueTemp() > gl.getRetValue()) {
                    gl.setRetValue(gl.getValueTemp());
                }
                if (gl.getLottNumber() == Integer.valueOf(balls[6])) {
                    gl.setRetValue(0);
                    break;
                }

            }
        }
        gameLryls.addAll(gameLryls1);
        return gameLryls;
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
		
		
		for (GameDrawResult result : gdrs) {
			String [] arr = null;
			if (result.getNumberRecord().contains(",")) {
                if(result.getLotteryid().longValue() == 99401l) {
                    result.setNumberRecord(result.getNumberRecord().replace("+", ","));
                }
				arr = result.getNumberRecord().split(",");
			} else {
				arr = new String [result.getNumberRecord().length()];
				for (int i = 0; i < result.getNumberRecord().length(); i++) {
					arr[i] = result.getNumberRecord().substring(i, i+1);
				}
			}
			
			int j = arr.length;
			for (Integer i = 0; i < arr.length; i++) {
				
				if (numberRecordEveryDigit.get(j) == null || numberRecordEveryDigit.get(j).isEmpty()) {
					List<Integer> numbers = new ArrayList<Integer>();
					numberRecordEveryDigit.put(j, numbers);
				}
				
				numberRecordEveryDigit.get(j).add(Integer.parseInt(arr[i]));
				j--;
			}
		}
		
		return numberRecordEveryDigit;
		
		
		
		
		
		
		/*// 万位的所有中奖号码
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
			if (gdr.getNumberRecord().contains(",")) {
				String [] arr = gdr.getNumberRecord().split(",");
			} else {
				Integer numberRecord = Integer.parseInt(gdr.getNumberRecord());
				int mbit = numberRecord / 10000;
				int kbit = (numberRecord % 10000) / 1000;
				int hbit = (numberRecord % 1000) / 100;
				int dbit = (numberRecord % 100) / 10;
				int ubit = numberRecord % 10;
			}
			
			
			
			

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

		return numberRecordEveryDigit;*/
	}

	
	
}
