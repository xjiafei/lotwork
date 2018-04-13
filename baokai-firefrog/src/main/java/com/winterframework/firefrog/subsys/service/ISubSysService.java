package com.winterframework.firefrog.subsys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.subsys.web.dto.SubSysUserStrucResponse;
import com.winterframework.firefrog.user.entity.GameAwardUserGroupVo;

@Transactional(rollbackFor = Exception.class)
public interface ISubSysService {

	public Boolean validateToken(String token) throws Exception;

	public List<GameAwardUserGroupVo> queryUserAward(Long userId ,Long lotteryId)throws Exception;

	public SubSysUserStrucResponse getNewUserBankList(Integer isNewUser, SubSysUserStrucResponse result)throws Exception;
	
}