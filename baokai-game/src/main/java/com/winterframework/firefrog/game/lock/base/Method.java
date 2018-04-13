package com.winterframework.firefrog.game.lock.base;

import java.util.Map;

import com.winterframework.firefrog.game.util.LhcRedisUtil;

public abstract class Method {
	/**投注方式編碼*/
	protected String methodId;
	/**注單明細投注內容*/
	protected String betContent;
	/**六合彩獲取Redis內容工具*/
	protected LhcRedisUtil lhcRedisUtil;
	
	/**
	 * 取得注單明細投注內容。
	 * @return
	 */
   	public String getBetContent() {
		return betContent;
	}
   	/**
   	 * 設定注單明細投注內容。
   	 * @param betContent
   	 */
	public void setBetContent(String betContent) {
		this.betContent = betContent;
	}
	/**
	 * 取得投注方式編碼。
	 * @return
	 */
	public String getMethodId() {
		return methodId;
	}
	/**
	 * 設定投注方式編碼。
	 * @param methodId
	 */
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}

	/**
	 * 設定六合彩獲取Redis內容工具。
	 * @param lhcRedisUtil 
	 */
	public void setLhcRedisUtil(LhcRedisUtil lhcRedisUtil) {
		this.lhcRedisUtil = lhcRedisUtil;
	}
	
	public abstract Map<String, Integer> influncePoint() ;
}
