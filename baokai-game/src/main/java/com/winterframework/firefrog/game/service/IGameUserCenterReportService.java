package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.UserCenterReportVo;
import com.winterframework.firefrog.game.entity.UserCenterReportInfo;
import com.winterframework.firefrog.game.web.dto.SubUserReportRequest;
import com.winterframework.firefrog.game.web.dto.UserCenterReportComplexRequest;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;

/** 
* @ClassName: IGameUserCenterReportService 
* @Description: 游戏用户中心报表service 
* @author david 
* @date 2013-9-17 下午5:21:54 
*  
*/
public interface IGameUserCenterReportService {

	/** 
	* @Title: queryUserCenterReportByUserId 
	* @Description: 根据用户id获取用户以及下级的报表信息
	* @param pr
	* @return
	*/
	public Page<UserCenterReportInfo> queryUserCenterReportByUserId(PageRequest<SubUserReportRequest> pr);

	/** 
	* @Title: queryUserCenterReportByComplexCondition 
	* @Description: 根据 复杂条件获取用户以及下级的报表信息 
	* @param pr
	 * @param userId 
	* @return
	*/
	public Page<UserCenterReportInfo> getUserReportByBetTypeCodeList(PageRequest<UserCenterReportComplexRequest> pr,Long userId);
	
	/** 
	* @Title: queryUserCenterReportByComplexCondition 
	* @Description: 根据 复杂条件获取用户以及下级的报表信息 
	* @param pr
	 * @param userId 
	* @return
	*/
	public Page<UserCenterReportInfo> queryUserCenterReportByComplexCondition(
			PageRequest<UserCenterReportComplexRequest> pr, Long userId);

	/** 
	* @Title: getCurrentUserReportByUserId 
	* @Description: 获取当前用户报表信息
	* @param userId
	* @return
	*/
	public UserCenterReportInfo getCurrentUserReportByUserId(Long userId);

}
