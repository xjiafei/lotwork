/**   
* @Title: CountResultPager.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 下午5:15:09 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import com.winterframework.modules.web.jsonresult.ResultPager;

/** 
* @ClassName: CountResultPager 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午5:15:09 
*  
*/
public class CountResultPager extends ResultPager{
	public Long sumWait;
	public Long sumReviewing;
	public Long sumNotPass;
	public Long getSumWait() {
		return sumWait;
	}
	public void setSumWait(Long sumWait) {
		this.sumWait = sumWait;
	}
	public Long getSumReviewing() {
		return sumReviewing;
	}
	public void setSumReviewing(Long sumReviewing) {
		this.sumReviewing = sumReviewing;
	}
	public Long getSumNotPass() {
		return sumNotPass;
	}
	public void setSumNotPass(Long sumNotPass) {
		this.sumNotPass = sumNotPass;
	}
}
