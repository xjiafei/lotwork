package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameControlEvent;

/**
 * 
 * @ClassName: IGameDrawResultService
 * @Description: 开奖号码服务类
 * @author Richard
 * @date 2013-11-18 下午2:33:06
 *
 */
public interface IGameDrawResultService {

	/**
	 * 
	 * @Title: getnumberRecordByLotteryIdAndIssueCode
	 * @Description:获取开奖号码
	 * @param lotteryId
	 * @param issueCode
	 * @return
	 * @throws Exception
	 */
	public String getnumberRecordByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception;

	/**
	 * 新增或更新開獎結果
	 * 
	 * @param event
	 * @throws Exception
	 */
	public void addOrUpdateDrawResult(GameControlEvent event) throws Exception;

}
