package com.winterframework.firefrog.subsys.dao;

import com.winterframework.firefrog.subsys.vo.SubSystemActivityLog;

/**
 * 
 *    
 * 类功能说明:工具类Dao层   
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author David  
 *
 */
public interface ISubSystemActivityLogDao {



	public Long querySubSystemActivityLogCount(SubSystemActivityLog systemActivityLog);
	
	public SubSystemActivityLog querySubSystemActivityLog(SubSystemActivityLog systemActivityLog);

	public void saveSubSystemActivityLog(SubSystemActivityLog systemActivityLog);

}
