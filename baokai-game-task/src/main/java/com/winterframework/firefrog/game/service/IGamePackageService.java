package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GamePackage;

/**
 * 方案服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月29日
 */
public interface IGamePackageService {

	GamePackage getById(Long id) throws Exception;
	
	List<GamePackage> getByLotteryIdAndIssueCode(Long lotteryId,Long issueCode) throws Exception;
}