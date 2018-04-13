/**   
* @Title: GameLockStruc.java 
* @Package com.winterframework.firefrog.game.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-6 上午10:54:15 
* @version V1.0   
*/
package com.winterframework.firefrog.game.web.dto;

import java.io.Serializable;

/**
 * 封鎖變價設定 Struc
 * @author Pogi.Lin
 */
public class GameLockStruc implements Serializable{

	private static final long serialVersionUID = 23423512343l;
	
	private Long id;
	/**彩種ID*/
	private Long gameId;
	/**封鎖值；對應3D、P3、六合彩(特碼)*/
	private Long upVal;
	/**老的封鎖值*/
	private Long upValProcess;
	/**封鎖值2；對應P5、六合彩(正特碼一肖)*/
	private Long upVal2;
	/**老的封鎖值2*/
	private Long upValProcess2;
	/**狀態；1：待審核、2：審核通過、3：審核不通過、4：已發佈*/
	private Long status;
	/**紅球封鎖值；對應雙色球*/
	private Long redSlipVal;
	/**老的紅球封鎖值*/
	private Long redSlipValProcess;
	/**藍球封鎖值；對應雙色球*/
	private Long blueSlipVal;
	/**老的藍球封鎖值*/
	private Long blueSlipValProcess;
	/**封鎖值3；對應六合彩(其他玩法)*/
	private Long upVal3;
	/**老的封鎖值3*/
	private Long upValProcess3;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getGameId() {
		return gameId;
	}
	/**
	 * 設定彩種ID。
	 * @param gameId
	 */
	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}
	/**
	 * 取得封鎖值；對應3D、P3、六合彩(特碼)。
	 * @return
	 */
	public Long getUpVal() {
		return upVal;
	}
	/**
	 * 設定封鎖值；對應3D、P3、六合彩(特碼)。
	 * @param upVal
	 */
	public void setUpVal(Long upVal) {
		this.upVal = upVal;
	}
	/**
	 * 取得老的封鎖值。
	 * @return
	 */
	public Long getUpValProcess() {
		return upValProcess;
	}
	/**
	 * 設定老的封鎖值。
	 * @param upValProcess
	 */
	public void setUpValProcess(Long upValProcess) {
		this.upValProcess = upValProcess;
	}
	/**
	 * 取得狀態。
	 * @return 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * 設定狀態。
	 * @param status 1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
	 * 取得封鎖值2；對應P5、六合彩(正特碼一肖)。
	 * @return
	 */
	public Long getUpVal2() {
		return upVal2;
	}
	/**
	 * 設定封鎖值2；對應P5、六合彩(正特碼一肖)。
	 * @param upVal2
	 */
	public void setUpVal2(Long upVal2) {
		this.upVal2 = upVal2;
	}
	/**
	 * 取得老的封鎖值2。
	 * @return
	 */
	public Long getUpValProcess2() {
		return upValProcess2;
	}
	/**
	 * 設定老的封鎖值2。
	 * @param upValProcess2
	 */
	public void setUpValProcess2(Long upValProcess2) {
		this.upValProcess2 = upValProcess2;
	}
	/**
	 * 取得紅球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getRedSlipVal() {
		return redSlipVal;
	}
	/**
	 * 設定紅球封鎖值；對應雙色球。
	 * @param redSlipVal
	 */
	public void setRedSlipVal(Long redSlipVal) {
		this.redSlipVal = redSlipVal;
	}
	/**
	 * 取得老的紅球封鎖值。
	 * @return
	 */
	public Long getRedSlipValProcess() {
		return redSlipValProcess;
	}
	/**
	 * 設定老的紅球封鎖值。
	 * @param redSlipValProcess
	 */
	public void setRedSlipValProcess(Long redSlipValProcess) {
		this.redSlipValProcess = redSlipValProcess;
	}
	/**
	 * 取得藍球封鎖值；對應雙色球。
	 * @return
	 */
	public Long getBlueSlipVal() {
		return blueSlipVal;
	}
	/**
	 * 設定藍球封鎖值；對應雙色球。
	 * @param blueSlipVal
	 */
	public void setBlueSlipVal(Long blueSlipVal) {
		this.blueSlipVal = blueSlipVal;
	}
	/**
	 * 取得老的藍球封鎖值。
	 * @return
	 */
	public Long getBlueSlipValProcess() {
		return blueSlipValProcess;
	}
	/**
	 * 設定老的藍球封鎖值。
	 * @param blueSlipValProcess
	 */
	public void setBlueSlipValProcess(Long blueSlipValProcess) {
		this.blueSlipValProcess = blueSlipValProcess;
	}

	/**
	 * 取得封鎖值3；對應六合彩(其他玩法)。
	 * @return 
	 */
	public Long getUpVal3() {
		return upVal3;
	}

	/**
	 * 設定封鎖值3；對應六合彩(其他玩法)。
	 * @param upVal3 
	 */
	public void setUpVal3(Long upVal3) {
		this.upVal3 = upVal3;
	}

	/**
	 * 取得老的封鎖值3。
	 * @return 
	 */
	public Long getUpValProcess3() {
		return upValProcess3;
	}

	/**
	 * 設定老的封鎖值3。
	 * @param upValProcess3 
	 */
	public void setUpValProcess3(Long upValProcess3) {
		this.upValProcess3 = upValProcess3;
	}
}
