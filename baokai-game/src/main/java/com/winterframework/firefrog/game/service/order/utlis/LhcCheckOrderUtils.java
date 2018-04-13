package com.winterframework.firefrog.game.service.order.utlis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameNumberConfigDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameNumberConfig;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.util.LHCUtil.BetTypeCodeMapping;

/**
 * 判斷六合彩賠率問題
 * @author Ami.Tsai
 */
@Service("LhcCheckOrderUtils")
public class LhcCheckOrderUtils {
	
	public static final Map<String,String> funMapLeys = new HashMap<String, String>();
	
	static{
		//色波
		funMapLeys.put("红", "RED");
		funMapLeys.put("蓝", "BLUE");
		funMapLeys.put("绿", "GREEN");
		//半波
		funMapLeys.put("红大", "REDBIG");
		funMapLeys.put("红小", "REDSMALL");
		funMapLeys.put("红单", "REDODD");
		funMapLeys.put("红双", "REDEVEN");
		funMapLeys.put("蓝大", "BLUEBIG");
		funMapLeys.put("蓝小", "BLUESMALL");
		funMapLeys.put("蓝单", "BLUEODD");
		funMapLeys.put("蓝双", "BLUEEVEN");
		funMapLeys.put("绿大", "GREENBIG");
		funMapLeys.put("绿小", "GREENSMALL");
		funMapLeys.put("绿单", "GREENODD");
		funMapLeys.put("绿双", "GREENEVEN");
		//特肖
		funMapLeys.put("Y", "ONYEAR");
		funMapLeys.put("N", "UNYEAR");
	}
	
	@Resource(name="gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	
	@Resource(name="gameNumberConfigDaoImpl")
	private IGameNumberConfigDao gameNumberConfigDao;
	
	
	public Boolean checkFunBetTypeSingleWin(GameSlip slip){
		Boolean isOk = true;
		String betType = slip.getGameBetType().getBetTypeCode();
		
		GameAward awardQuery = new GameAward();
		awardQuery.setLotteryid(99701L);
		awardQuery.setBetTypeCode(betType);
		List<GameAward> awards = gameAwardDao.getAllByEntity(awardQuery);
		
		//LHCTODO 新增玩法尚未補齊
		if(BetTypeCodeMapping.lhc_54_19_84.name().contains(betType)){ //色波
			String betCode= funMapLeys.get(slip.getBetDetail());
			//色波比較是否與設定不同
			isOk = checkSingleWin(slip, awards, betCode);
		}else if(BetTypeCodeMapping.lhc_54_19_85.name().contains(betType)){ //半波
			String betCode= funMapLeys.get(slip.getBetDetail());
			//半波比較是否與設定不同
			isOk = checkSingleWin(slip, awards, betCode);
		}else if(BetTypeCodeMapping.lhc_54_37_83.name().contains(betType)){ //兩面
			isOk = checkSingleWin(slip, awards, null);
		}else if(BetTypeCodeMapping.lhc_54_18_82.name().contains(betType)){ //特肖
			List<GameNumberConfig> configs = gameNumberConfigDao.getByEffDate();
			for(GameNumberConfig numberConfig :configs){
				if(numberConfig.getNumType().equals(slip.getBetDetail())){
					String betCode = funMapLeys.get(numberConfig.getSpecialFlag());
					isOk = checkSingleWin(slip, awards, betCode);
					break;
				}
			}
		}
		
		return isOk;
	}
	
	/**
	 * 檢核注單明細的單注獎金是否與獎金組明細的實際獎金相同。
	 * @param slip 注單明細
	 * @param awards 獎金組明細組
	 * @param betCode 獎金識別碼
	 * @return true:相同、false:不同
	 */
	private Boolean checkSingleWin(GameSlip slip , List<GameAward> awards, String betCode){
		for(GameAward award:awards){
			if(StringUtils.isNotEmpty(betCode)){
				if(betCode.equals(award.getLhcCode())){
					if(!award.getActualBonus().equals(slip.getSingleWin())){
						return false;
					}
				}
			}else{
				if(!award.getActualBonus().equals(slip.getSingleWin())){
					return false;
				}
			}
		}
		return true;
	}
	
}
