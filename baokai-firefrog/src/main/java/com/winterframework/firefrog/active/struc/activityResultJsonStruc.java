package com.winterframework.firefrog.active.struc;

import com.winterframework.modules.web.util.JsonMapper;

public class activityResultJsonStruc{
	
	/**
	 *用戶 中獎金額
	 */
	private Long prize;
	
	/**
	 * 紅包設定數量
	 */
	private Long quantity;
	
	/**
	 * 活動今日開始時間
	 */
	private Long dayStart;
	/**
	 * 活動今日結束時間
	 */
	private Long dayEnd;
	
	
	/**
	 * 大獎機率最大值
	 */
	private Long rateRandMax;
	/**
	 * 大獎機率最小值
	 */
	private Long rateRandMin;

	
	@Override
	public String toString(){
		return JsonMapper.nonEmptyMapper().toString();
	}

	public Long getPrize() {
		return prize;
	}

	public void setPrize(Long prize) {
		this.prize = prize;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getDayStart() {
		return dayStart;
	}

	public void setDayStart(Long dayStart) {
		this.dayStart = dayStart;
	}

	public Long getDayEnd() {
		return dayEnd;
	}

	public void setDayEnd(Long dayEnd) {
		this.dayEnd = dayEnd;
	}

	public Long getRateRandMax() {
		return rateRandMax;
	}

	public void setRateRandMax(Long rateRandMax) {
		this.rateRandMax = rateRandMax;
	}

	public Long getRateRandMin() {
		return rateRandMin;
	}

	public void setRateRandMin(Long rateRandMin) {
		this.rateRandMin = rateRandMin;
	}

}
