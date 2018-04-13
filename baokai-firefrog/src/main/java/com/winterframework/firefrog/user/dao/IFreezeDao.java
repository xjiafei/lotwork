package com.winterframework.firefrog.user.dao;

import java.util.List;

import com.winterframework.firefrog.user.entity.FreezeLog;

/**
 * 
 *    
 * 类功能说明:冻结解冻Dao层   
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David  
 *
 */
public interface IFreezeDao {

	/**
	 * 
	 * 方法描述：冻结单个用户日志信息
	 * @param freeze
	 * @throws Exception
	 */
	public void saveFreezeLog(FreezeLog freezeLog) throws Exception;

	void deleteByFreezeAccount(String freezeAccount) ;
	/**
	 * 
	 * 方法描述：冻结单个用户日志信息
	 * @param freeze
	 * @throws Exception
	 */
	public void saveUnFreezeLog(FreezeLog freezeLog) throws Exception;

	/**
	 * 
	* @Title: saveFreezeLogBatch 
	* @Description: 批量插入用户冻结日志信息 
	* @param @param freezeLog
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveFreezeLogBatch(final List<FreezeLog> freezeLog) throws Exception;
}
