package com.winterframework.firefrog.game.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 彩种类型
 * @author Richard
 * @date 2013-7-22 下午4:02:25 
 */
public class Lottery implements Serializable {

	private static final long serialVersionUID = 5507393999754245415L;

	private Long id;
	/**彩票類型；1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型*/
	private Long lotteryTypeCode;
	/**彩票類型名稱*/
	private String lotteryTypeName;
	/**彩系編碼；1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、、6: 快乐彩系7: 快乐彩系、8: 电子游艺、9: 六合系*/
	private Long lotterySeriesCode;
	/**彩系名稱*/
	private String lotterySeriesName;
	/**彩種ID*/
	private Long lotteryId;
	/**彩種名稱*/
	private String lotteryName;

	/**
	 * 彩種銷售狀態。<br>
	 * 0:停售、1:可銷售
	 * @author Pogi.Lin
	 */
	public enum LotteryStatus {
		/**0:停售*/
		stopSelling(0), 
		/**1:可銷售*/
		sale(1);

		public int _value = 0;

		LotteryStatus(int value) {
			this._value = value;
		}

		public int getValue() {
			return _value;
		}
	}

	/**創建時間*/
	private Date createTime;
	/**更新時間*/
	private Date updateTime;

	public Lottery() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	 * 取得彩票類型。
	 * @return 1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型
	 */
	public Long getLotteryTypeCode() {
		return lotteryTypeCode;
	}

	/**
	 * 設定彩票類型。
	 * @param lotteryTypeCode 1:數字型、樂透同區型、3:基諾型、樂透同區型、4:樂透分區型、5:遊藝型
	 */
	public void setLotteryTypeCode(Long lotteryTypeCode) {
		this.lotteryTypeCode = lotteryTypeCode;
	}

	/**
	 * 取得彩票類型名稱。
	 * @return
	 */
	public String getLotteryTypeName() {
		return lotteryTypeName;
	}

	/**
	 * 設定彩票類型名稱。
	 * @param lotteryTypeName
	 */
	public void setLotteryTypeName(String lotteryTypeName) {
		this.lotteryTypeName = lotteryTypeName;
	}

	/**
	 * 取得彩系編碼。
	 * @return 1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、、6: 快乐彩系7: 快乐彩系、8: 电子游艺、9: 六合系
	 */
	public Long getLotterySeriesCode() {
		return lotterySeriesCode;
	}

	/**
	 * 設定彩系編碼。
	 * @param lotterySeriesCode 1: 时时彩系、2: 3D系、3: 11选5系、4: 基诺系、5: 双色球系、、6: 快乐彩系7: 快乐彩系、8: 电子游艺、9: 六合系
	 */
	public void setLotterySeriesCode(Long lotterySeriesCode) {
		this.lotterySeriesCode = lotterySeriesCode;
	}

	/**
	 * 取得彩系名稱。
	 * @return
	 */
	public String getLotterySeriesName() {
		return lotterySeriesName;
	}

	/**
	 * 設定彩系名稱。
	 * @param lotterySeriesName
	 */
	public void setLotterySeriesName(String lotterySeriesName) {
		this.lotterySeriesName = lotterySeriesName;
	}

	/**
	 * 取得彩種ID。
	 * @return
	 */
	public Long getLotteryId() {
		return lotteryId;
	}

	/**
	 * 設定彩種ID。
	 * @param lotteryId
	 */
	public void setLotteryId(Long lotteryId) {
		this.lotteryId = lotteryId;
	}

	/**
	 * 取得彩種名稱。
	 * @return
	 */
	public String getLotteryName() {
		return lotteryName;
	}

	/**
	 * 設定彩種名稱。
	 * @param lotteryName
	 */
	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}

}
