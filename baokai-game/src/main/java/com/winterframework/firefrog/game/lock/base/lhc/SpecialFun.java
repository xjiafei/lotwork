package com.winterframework.firefrog.game.lock.base.lhc;

import java.util.HashMap;
import java.util.Map;

import com.winterframework.firefrog.game.lock.base.SingleMethod;


/**
 * 特碼.趣味
 * @author Ami.Tsai
 */
public abstract class SpecialFun extends SingleMethod{

	/**
	 * key:投注號碼；value:同投注號碼出現幾次
	 */
	public Map<String, Integer> influncePoint() {
		Map<String, Integer> lockMap = new HashMap<String, Integer>();
		String[] betNumbers = null;
		betNumbers = getBetNumber(betContent);
		
		if(betNumbers!=null){
			for(String betNumber:betNumbers){
				if(lockMap.get(betNumber)==null){
					lockMap.put(betNumber, 1);
				}else{
					lockMap.put(betNumber, lockMap.get(betNumber)+1);
				}
			}
		}
		return lockMap;
	}
		
	/**
	 * 取得投注號碼。
	 * @param betContent
	 * @return
	 */
	protected abstract String[] getBetNumber(String betContent);
}
