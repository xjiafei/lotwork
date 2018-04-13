/**   
* @Title: CountResultPager.java 
* @Package com.winterframework.firefrog.advert.web.dto 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-11-6 下午5:15:09 
* @version V1.0   
*/
package com.winterframework.firefrog.advert.web.dto;

import java.util.List;

/** 
* @ClassName: CountResult
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2013-11-6 下午5:15:09 
*  
*/
public class CountResult{
	private Long sumWait;
	private Long sumReviewing;
	private Long sumNotPass;
	private List<AdStruc> adStrucs;
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
	public List<AdStruc> getAdStrucs() {
		return adStrucs;
	}
	public void setAdStrucs(List<AdStruc> adStrucs) {
		this.adStrucs = adStrucs;
	}
}
