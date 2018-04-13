package com.winterframework.firefrog.active.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/** 
* @ClassName ActivityUserAwardRequest 
* @Description 用户获奖记录
* @author  hugh
* @date 2014年11月28日 上午11:52:37 
*  
*/
public class ActivityRatioEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1147166121373568793L;
	
	private Float ratio;
	
	private Float multiple;

	public Float getRatio() {
		return ratio;
	}

	public void setRatio(Float ratio) {
		this.ratio = ratio;
	}

	public Float getMultiple() {
		return multiple;
	}

	public void setMultiple(Float multiple) {
		this.multiple = multiple;
	}
}
