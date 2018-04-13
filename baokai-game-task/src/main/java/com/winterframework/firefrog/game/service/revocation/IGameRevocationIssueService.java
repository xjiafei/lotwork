package com.winterframework.firefrog.game.service.revocation;

/** 
* @ClassName IGameRevocationIssueService 
* @Description 撤销一期 
* 实现类包括：</br>
* GameRevocationIssueServiceImpl撤销一期方案（包括订单以及这一期没生成订单的追号）</br>
* GameRevocationIssueSimplePlanServiceImpl 撤销一期没有生成订单的追号（奖期还未开奖）</br>
* GameRevocationIssueToBeforeDrawServiceImpl 撤销一期到开奖前 （重新输入开奖号码时调用）</br>
* @author  hugh
* @date 2014年5月6日 上午11:20:52 
*  
*/
public interface IGameRevocationIssueService {

	/** 
	* @Title: cancel 
	* @Description: 撤销一期 
	* @param lotteryId
	* @param issueCode
	*/
	void doRevocation(Long lotteryId, Long issueCode) throws Exception;;

}
