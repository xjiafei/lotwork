package com.winterframework.firefrog.fund.dao;

import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkRecyleExtVO;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundUserRecyleDao 
* @Description: 附言回收
* @author 你的名字 
* @date 2014-3-11 下午1:21:49 
*  
*/
public interface IFundUserRecyleDao {

	/** 
	* 获取所有的系统附言
	*/
	Page<FundUserRemarkRecyleExtVO> getAllRecyleRemarks(PageRequest<FundUserRemarkRecyle> pageRequest) throws Exception;

	/** 
	* @Title: updateFundUserRecyle 
	* @Description: 跟新回收附言
	* @param recyle
	*/
	void saveFundUserRecyle(FundUserRemarkRecyle recyle);

	/** 
	* @Title: deleteFundUserRecyle 
	* @Description: 根据附言删除回收表中记录
	* @param recyle
	*/
	void deleteFundUserRecyle(String remark);
	void deleteFundUserRecyle(Long id);
}
