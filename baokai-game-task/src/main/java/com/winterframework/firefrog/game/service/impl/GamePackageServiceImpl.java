package com.winterframework.firefrog.game.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.service.IGamePackageService;
 

 
/**
 * 方案服务接口实现类
 * @ClassName
 * @Description
 * @author ibm
 * 2014年10月29日
 */
@Service("gamePackageServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GamePackageServiceImpl implements IGamePackageService {

	private static final Logger log = LoggerFactory.getLogger(GamePackageServiceImpl.class);
	
	@Resource(name = "gamePackDaoImpl")
	private IGamePackageDao gamePackageDao; 

	@Override
	public GamePackage getById(Long id) throws Exception { 
		return this.gamePackageDao.getById(id);
	} 
	@Override
	public List<GamePackage> getByLotteryIdAndIssueCode(Long lotteryId,
			Long issueCode) throws Exception {
		return this.gamePackageDao.getByLotteryIdAndIssueCode(lotteryId, issueCode);
	}
}
