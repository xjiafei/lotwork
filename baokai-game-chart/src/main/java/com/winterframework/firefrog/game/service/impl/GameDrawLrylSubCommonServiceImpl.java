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
@Service("gameDrawLrylSubCommonServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameDrawLrylSubCommonServiceImpl extends GameDrawLrylCommonServiceImpl implements IGameDrawLrylService {
	protected List<GameLryl> makeLrylList(long lotteryId, int gameGroupCode, int gameSetCode, int betMethodCode,String omitValue) {
		List<GameLryl> gls=new ArrayList<GameLryl>();
		GameLryl gl;
		if(omitValue != null){
			String[] _strs = omitValue.split(",");
			int startBit = _strs.length-1;
			int endBit = 0;
			Map<Integer, List<Integer>> omissionListEveryDigit = getOmissionListEveryDigit(_strs, startBit, endBit, _strs.length);
			for (int i = startBit; i >= endBit; i--) {
				List<Integer> l = omissionListEveryDigit.get(i);
				if (l == null || l.size() == 0) {
					break;
				}
				int index = 1;
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
}
