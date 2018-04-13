package com.winterframework.firefrog.fund.dao;

import java.util.List;

import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundUserRemarkDao 
* @Description: 用户和附言dao
* @author 你的名字 
* @date 2014-3-11 下午1:21:54 
*  
*/
public interface IFundUserRemarkDao {

	/** 
	 * 获取所有的手工附言
	*/
	Page<FundUserRemark> getPageRemarks(PageRequest<FundUserRemark> pageRequest) throws Exception;

	/** 
	* @Title: batchSaveRemark 
	* @Description: 批量保存用户附言表 
	* @param userIds
	*/
	void batchSaveRemark(List<Long> userIds);

	/** 
	* @Title: updateRemarkManual 
	* @Description: 更新附言
	* @param manual
	*/
	public void updateUserRemark(FundUserRemark manual);
	/** 
	* @Title: getRemarkManualById 
	* @Description: 根据id获取用户附言信息
	* @param manual
	*/
	public FundUserRemark getUserRemarkByUserId(Long userId);
}
