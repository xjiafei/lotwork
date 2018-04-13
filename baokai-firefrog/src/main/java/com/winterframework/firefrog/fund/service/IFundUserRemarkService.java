/**   
* @Title: IFundUserRemarkService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-3-11 上午10:16:04 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import com.winterframework.firefrog.fund.dao.vo.FundUserRemarkRecyleExtVO;
import com.winterframework.firefrog.fund.entity.FundUserRemark;
import com.winterframework.firefrog.fund.entity.FundUserRemarkRecyle;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IFundUserRemarkService 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 你的名字 
* @date 2014-3-11 上午10:16:04 
*  
*/
public interface IFundUserRemarkService {

	public Boolean checkRemarkExist(String remark) throws Exception;

	public void importUser(List<Long> userId) throws Exception;

	public Page<FundUserRemarkRecyleExtVO> getAllRecyleRemarks(PageRequest<FundUserRemarkRecyle> search) throws Exception;

	public Page<FundUserRemark> getAllRemarks(PageRequest<FundUserRemark> search) throws Exception;

	public String getModifiedDays() throws Exception;

	public String getNextSystemRemark() throws Exception;
	public FundUserRemark getRemarkByUserId(Long userId) throws Exception;

	public Boolean saveRemark(String remark,Long userId) throws Exception;

	public void setCanModifiedDays(Long days) throws Exception;

	public void cancelLock(Long userId) throws Exception;
	public void recyleRemark(Long id,String remark) throws Exception;

	public void cancelRemark(Long userId) throws Exception;
	
	public Boolean updateRemark(String remark,Long userId) throws Exception;

}
