package com.winterframework.firefrog.subsys.dao;

import com.winterframework.firefrog.subsys.vo.SubSystem;
import com.winterframework.firefrog.user.dao.vo.Judgement;

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
public interface ISubSystemDao {



	public void updateSubSystem(SubSystem system);

	public SubSystem getName(SubSystem system);

}
