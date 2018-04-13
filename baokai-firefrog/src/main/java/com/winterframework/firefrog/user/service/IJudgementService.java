package com.winterframework.firefrog.user.service;

import com.winterframework.firefrog.user.dao.vo.Judgement;

/**
 * 
 * 类功能说明: 工具类操作
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author David
 */
public interface IJudgementService {

	/** 
	* @Title: initAction 
	* @Description: 生成工具类
	* @param judgement
	*/
	public void initAction(Judgement judgement);

	/** 
	* @Title: updateAction 
	* @Description:更新工具类
	* @param judgement
	*/
	public void updateAction(Judgement judgement);

	/** 
	* @Title: getAction 
	* @Description: 获取工具类 
	* @param judgement
	* @return
	*/
	public Judgement getAction(Judgement judgement);

}
