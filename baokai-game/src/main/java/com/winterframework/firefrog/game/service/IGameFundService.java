package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;



/**
 * 资金服务接口
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月14日
 */
public interface IGameFundService {
	void fundRequest(List<TORiskDTO> toRiskDTOList);
	
}
