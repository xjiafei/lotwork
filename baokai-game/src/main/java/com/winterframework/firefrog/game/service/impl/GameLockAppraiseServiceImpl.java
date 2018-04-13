/**   
* @Title: GameLockAppraiseServiceImpl.java 
* @Package IGameLockAppraiseService 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 你的名字   
* @date 2014-5-5 下午5:30:18 
* @version V1.0   
*/
package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DataConverterUtil;
import com.winterframework.firefrog.game.dao.IGameLockAppraiseDao;
import com.winterframework.firefrog.game.dao.vo.GameLockAppraise;
import com.winterframework.firefrog.game.service.IGameLockAppraiseService;
import com.winterframework.firefrog.game.web.dto.GameLockAppraiseChangeStruc;

/** 
* @ClassName: GameLockAppraiseServiceImpl 
* @Description: 变价设置
* @author 你的名字 
* @date 2014-5-5 下午5:30:18 
*  
*/
@Service("gameLockAppraiseServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameLockAppraiseServiceImpl implements IGameLockAppraiseService {

	@Resource(name = "gameLockAppraiseDaoImpl")
	private IGameLockAppraiseDao gameLockAppraiseDaoImpl;

	/**
	* Title: queryAllGameLockAppraise
	* Description:
	* @param lotteryId
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#queryAllGameLockAppraise(java.lang.Long) 
	*/
	@Override
	public List<GameLockAppraise> queryAllGameLockAppraise(Long lotteryId) throws Exception {
		return gameLockAppraiseDaoImpl.queryAllGameLockAppraise(lotteryId);
	}

	/**
	* Title: updateGameLockAppraise
	* Description:
	* @param gameLockAppraise
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#updateGameLockAppraise(com.winterframework.firefrog.game.dao.vo.GameLockAppraise) 
	*/
	@Override
	public int updateGameLockAppraise(GameLockAppraise gameLockAppraise) throws Exception {
		String changeStruc = gameLockAppraise.getChangeStrucProcess();
		if (changeStruc != null) {
			String[] array = changeStruc.split("\\|");
			List<GameLockAppraiseChangeStruc> list = new ArrayList<GameLockAppraiseChangeStruc>();
			for (String changeStrucString : array) {
				GameLockAppraiseChangeStruc gameLockAppraiseChangeStruc = new GameLockAppraiseChangeStruc();
				Double from = Double.valueOf(changeStrucString.split(",")[0]) * 10000;
				Double to = Double.valueOf(changeStrucString.split(",")[1]) * 10000;
				gameLockAppraiseChangeStruc.setFrom(from.longValue());
				gameLockAppraiseChangeStruc.setTo(to.longValue());
				gameLockAppraiseChangeStruc.setRate(Double.valueOf(changeStrucString.split(",")[2]));
				list.add(gameLockAppraiseChangeStruc);
			}
			gameLockAppraise.setChangeStrucProcess(DataConverterUtil.convertObject2Json(list));
		}
		return gameLockAppraiseDaoImpl.update(gameLockAppraise);
	}

	/**
	* Title: deleteGameLockAppraise
	* Description:
	* @param ids
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#deleteGameLockAppraise(java.lang.String) 
	*/
	@Override
	public int deleteGameLockAppraise(String ids) throws Exception {
		ids = ids.substring(1);
		String[] idsArray = ids.split(",");
		List<Long> idsList = new ArrayList<Long>();
		for (String idsString : idsArray) {
			idsList.add(Long.valueOf(idsString));
		}
		return gameLockAppraiseDaoImpl.deleteGameLockAppraise(idsList);
	}

	/**
	* Title: addGameLockAppraise
	* Description:
	* @param gameLockAppraise
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#addGameLockAppraise(com.winterframework.firefrog.game.dao.vo.GameLockAppraise) 
	*/
	@Override
	public int addGameLockAppraise(GameLockAppraise gameLockAppraise) throws Exception {
		String changeStruc = gameLockAppraise.getChangeStruc();
		String[] array = changeStruc.split("\\|");
		List<GameLockAppraiseChangeStruc> list = new ArrayList<GameLockAppraiseChangeStruc>();
		for (String changeStrucString : array) {
			GameLockAppraiseChangeStruc gameLockAppraiseChangeStruc = new GameLockAppraiseChangeStruc();
			Double from = Double.valueOf(changeStrucString.split(",")[0]) * 10000;
			Double to = Double.valueOf(changeStrucString.split(",")[1]) * 10000;
			gameLockAppraiseChangeStruc.setFrom(from.longValue());
			gameLockAppraiseChangeStruc.setTo(to.longValue());
			gameLockAppraiseChangeStruc.setRate(Double.valueOf(changeStrucString.split(",")[2]));
			list.add(gameLockAppraiseChangeStruc);
		}
		gameLockAppraise.setChangeStruc(DataConverterUtil.convertObject2Json(list));
		gameLockAppraise.setChangeStrucProcess(gameLockAppraise.getChangeStruc());
		gameLockAppraise.setGmtCreated(new Date());
		gameLockAppraise.setStatus(1l);
		gameLockAppraise.setCurrUse(0l);
		return gameLockAppraiseDaoImpl.insert(gameLockAppraise);
	}

	/**
	* Title: queryGameLockAppraise
	* Description:
	* @param id
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#queryGameLockAppraise(java.lang.Long) 
	*/
	@Override
	public GameLockAppraise queryGameLockAppraise(Long id) throws Exception {
		return gameLockAppraiseDaoImpl.getById(id);
	}

	/**
	* Title: updateStatus
	* Description:
	* @param gameLockAppraise
	* @return
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#updateStatus(com.winterframework.firefrog.game.dao.vo.GameLockAppraise) 
	*/
	@Override
	public int updateStatus(GameLockAppraise gameLockAppraise) throws Exception {
		return gameLockAppraiseDaoImpl.updateStatus(gameLockAppraise);
	}

	/**
	* Title: updateCurUser
	* Description:
	* @param lotterId
	* @throws Exception 
	* @see com.winterframework.firefrog.game.service.IGameLockAppraiseService#updateCurUser(java.lang.Long) 
	*/
	@Override
	public void updateCurUser(Long lotterId) throws Exception {
		gameLockAppraiseDaoImpl.updateCurUser(lotterId);
	}
}
