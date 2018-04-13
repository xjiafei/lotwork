package com.winterframework.firefrog.user.dao;

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
public interface IJudgementDao {

	public void initAction(Judgement judgement);

	public void updateAction(Judgement judgement);

	public Judgement getAction(Judgement judgement);

}
