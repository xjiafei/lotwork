package com.winterframework.firefrog.user.service;

import com.winterframework.firefrog.user.entity.FreezeDTO;
import com.winterframework.firefrog.user.entity.FreezeLog;
import com.winterframework.firefrog.user.entity.QueryFreezeUserDTO;
import com.winterframework.firefrog.user.entity.QueryUnFreezeUserLogDTO;
import com.winterframework.firefrog.user.entity.UnFreezeDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/**
 * 
 * 类功能说明: 冻结解冻操作
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David
 */
public interface IFreezeService {

	/**
	 * 
	 * 方法描述：冻结单个用户
	 * @throws Exception
	 */
	public void freezeUser(FreezeDTO freeze) throws Exception;

	/**
	 * 
	 * 方法描述：冻结用户本身及冻结下级用户
	 * @throws Exception
	 */
	public void freezeUserAndSubUser(FreezeDTO freeze) throws Exception;

	/**
	 * 
	 * 方法描述：解冻单个用户
	 * @throws Exception
	 */
	public void unFreezeUser(UnFreezeDTO unFreeze) throws Exception;
	
	/**
	 * 
	 * 方法描述：解冻单个申诉用户
	 * @throws Exception
	 */
	public void unFreezeApproveUser(UnFreezeDTO unFreeze) throws Exception;

	/**
	 * 
	 * 方法描述：解冻用户及解冻下级用户
	 * @param parentId
	 * @param memo 解冻原因
	 * @throws Exception
	 */
	public void unFreezeUserAndSubUser(UnFreezeDTO unFreeze) throws Exception;

	/** 
	* @Title: searchFreezeUser 
	* @Description: 冻结账户查询 
	* @param pageReqeust
	* @return
	 * @throws Exception 
	*/
	public Page<User> searchFreezeUser(PageRequest<QueryFreezeUserDTO> pageReqeust) throws Exception;

	/** 
	* @Title: searchUnFreezeUserLog 
	* @Description: 历史冻结记录查询
	* @param pageReqeust
	* @return
	 * @throws Exception 
	*/
	public Page<FreezeLog> searchUnFreezeUserLog(PageRequest<QueryUnFreezeUserLogDTO> pageReqeust) throws Exception;

}
