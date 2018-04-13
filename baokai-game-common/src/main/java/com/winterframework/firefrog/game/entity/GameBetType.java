package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

import com.winterframework.orm.dal.ibatis3.BaseEntity;

/**
 * 玩法类型实例
 * @author Richard
 * @date 2014-4-1 下午5:36:55 
 */
public class GameBetType  extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 4715607246283234881L;
	
	/**投注方法編碼*/
	private Integer betMethodCode;
	/**創建時間*/
	private Date createTime;
	/**示例*/
	private String gameExampleDes;
	/**玩法群編碼*/
	private Integer gameGroupCode;
	/**玩法描述*/
	private String gamePromptDes;
	/**玩法組編碼*/
	private Integer gameSetCode;
	/**最大限制倍数*/
	private Long maxMultiple;
	/**更新時間*/
	private Date updateTime;
	/**辅助玩法类型*/
	private Integer methodType;
	/**投注方式分隔符號 _*/
	private final String SEPERATOR="_";

	public GameBetType() {}

	/**
	 * @param gameGroupCode 玩法群編碼
	 * @param gameSetCode 玩法組編碼
	 * @param betMethodCode 投注方式編碼
	 */
	public GameBetType(Integer gameGroupCode, Integer gameSetCode, Integer betMethodCode) {
		this.gameGroupCode = gameGroupCode;
		this.gameSetCode = gameSetCode;
		this.betMethodCode = betMethodCode;
	}
	
	/**
	 * 將 betTypeCode 拆開為 gameGroupCode, gameSetCode, betMethodCode。
	 * @param betTypeCode 投注方式編碼
	 */
	public GameBetType(String betTypeCode) {
		this.gameGroupCode = -1;
		this.gameSetCode = -1;
		this.betMethodCode = -1;
		if(betTypeCode!=null){
			String[] arr=betTypeCode.split(SEPERATOR);
			if(arr!=null && arr.length==3){
				this.gameGroupCode = Integer.valueOf(arr[0]);
				this.gameSetCode = Integer.valueOf(arr[1]);
				this.betMethodCode = Integer.valueOf(arr[2]);
			}			
		}		
	}

	/**
	 * 取得gameGroupCode, gameSetCode, betMethodCode 組合後的投注方式編碼。<br>
	 * 例：01_01_01
	 * @return
	 */
	public String getBetTypeCode() {
		return gameGroupCode + SEPERATOR + gameSetCode + SEPERATOR + betMethodCode;
	}

	/**
	 * 取得投注方法編碼。
	 * @return
	 */
	public Integer getBetMethodCode() {
		return betMethodCode;
	}

	/**
	 * 設定投注方法編碼。
	 * @param betMethodCode
	 */
	public void setBetMethodCode(Integer betMethodCode) {
		this.betMethodCode = betMethodCode;
	}

	/**
	 * 取得創建時間。
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 設定創建時間。
	 * @param createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得示例。
	 * @return
	 */
	public String getGameExampleDes() {
		return gameExampleDes;
	}

	/**
	 * 設定示例。
	 * @param gameExampleDes
	 */
	public void setGameExampleDes(String gameExampleDes) {
		this.gameExampleDes = gameExampleDes;
	}

	/*
	 * 取得玩法群編碼。
	 */
	public Integer getGameGroupCode() {
		return gameGroupCode;
	}

	/**
	 * 設定玩法群編碼。
	 * @param gameGroupCode
	 */
	public void setGameGroupCode(Integer gameGroupCode) {
		this.gameGroupCode = gameGroupCode;
	}

	/**
	 * 取得玩法描述。
	 * @return
	 */
	public String getGamePromptDes() {
		return gamePromptDes;
	}

	/**
	 * 設定玩法描述。
	 * @param gamePromptDes
	 */
	public void setGamePromptDes(String gamePromptDes) {
		this.gamePromptDes = gamePromptDes;
	}

	/**
	 * 取得玩法組編碼。
	 * @return
	 */
	public Integer getGameSetCode() {
		return gameSetCode;
	}

	/**
	 * 設定玩法組編碼。
	 * @param gameSetCode
	 */
	public void setGameSetCode(Integer gameSetCode) {
		this.gameSetCode = gameSetCode;
	}

	/**
	 * 取得最大限制倍数。
	 * @return
	 */
	public Long getMaxMultiple() {
		return maxMultiple;
	}

	/**
	 * 設定最大限制倍数。
	 * @param maxMultiple
	 */
	public void setMaxMultiple(Long maxMultiple) {
		this.maxMultiple = maxMultiple;
	}

	/**
	 * 取得更新時間。
	 * @return
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 設定更新時間。
	 * @param updateTime
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 取得辅助玩法类型。
	 * @return
	 */
	public Integer getMethodType() {
		return methodType;
	}

	/**
	 * 設定辅助玩法类型。
	 * @param methodType
	 */
	public void setMethodType(Integer methodType) {
		this.methodType = methodType;
	}
}
