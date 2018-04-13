/**   
* @Title: IFundChangeService.java 
* @Package com.winterframework.firefrog.fund.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2013-12-16 下午1:21:28 
* @version V1.0   
*/
package com.winterframework.firefrog.fund.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.fund.entity.UserFund;
import com.winterframework.firefrog.fund.exception.FundChangedException;
import com.winterframework.firefrog.fund.web.controller.vo.FundChangeDetail;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVo;
import com.winterframework.firefrog.game.fund.ff.bean.FundGameVos;

/** 
* @ClassName: IFundAtomicOperationService 
* @Description: 操作资金的原子接口定义，由5个核心的操作方法组成。
* @author Page
* @date 2013-12-16 下午1:21:28 
*  
*/
@Transactional(rollbackFor = Exception.class)
public interface IFundAtomicOperationService {

	/**
	 * 
	* @Title: frozenAmt 
	* @Description: 冻结余额
	* @param userId
	* @param user
	* @param amount
	* @param sn
	* @param item
	* @throws FundChangedException
	 */

	public void action(List<FundGameVo>  vos,List<FundChangeDetail> map)	throws FundChangedException,Exception;
	public void action(FundGameVo...vos)	throws FundChangedException,Exception;
	public void actions(List<FundGameVo>  vos)	throws FundChangedException,Exception;
	
	/**
	 * 
	* @Title: unFrozenAmt 
	* @Description: 解冻余额
	* @param userId
	* @param user
	* @param amount
	* @param sn
	* @param item
	* @throws FundChangedException
	 */
	/*public FundChangeDetail unFrozenAmt(Long userId, BaseUser user, Long amount, String sn, EnumItem item)
			throws FundChangedException;

	*//**
	 * 
	* @Title: cleanFrozenAmt 
	* @Description: 清除冻结余额
	* @param userId
	* @param user
	* @param amount
	* @param sn
	* @param item
	* @throws FundChangedException
	 *//*
	public FundChangeDetail cleanFrozenAmt(Long userId, BaseUser user, Long amount, String sn, EnumItem item)
			throws FundChangedException;

	*//**
	 * 
	* @Title: addAmt 
	* @Description: 增加余额
	* @param userId
	* @param user
	* @param amount
	* @param sn
	* @param item
	* @throws FundChangedException
	 *//*
	public FundChangeDetail addAmt(Long userId, BaseUser user, Long amount, String sn, EnumItem item) throws FundChangedException;

	*//**
	 * 
	* @Title: reduceAmt 
	* @Description: 减余额
	* @param userId
	* @param user
	* @param amount
	* @param sn
	* @param item
	* @throws FundChangedException
	 *//*
	public FundChangeDetail reduceAmt(Long userId, BaseUser user, Long amount, String sn, EnumItem item)
			throws FundChangedException;*/
	
	/** 
	* @Title: getUserFund 
	* @Description: 获取用户资金信息
	* @param userId 用户id
	* @return
	*/
	public UserFund getUserFund(Long userId);
}
