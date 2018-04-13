package com.winterframework.firefrog.game.lock.config.mongo.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * 封鎖Service選擇器。
 * @author Pogi.Lin
 */
@Service
public class LockSelector {
	@Resource(name = "lockService")
	private LockService lockService;
	@Resource(name="ssqLockService")
	private SSQService ssqLockService;
	@Resource(name="lhcLockService")
	private LHCLockService lhcLockService;

	/**
	 * 依據 lotteryId 返回對應的 Service。<br>
	 * 只有 99401(雙色球)、99108(3D)、99109(排列5)、99701(六合彩)有封鎖Service。
	 * @param lotteryId 彩種ID
	 * @return 非可封鎖彩種返回null。
	 */
	public LotteryLockService getRealService(Long lotteryId){
		if(lotteryId==99401l){
			return ssqLockService;
		}else if(lotteryId==99108L || lotteryId==99109l){
			return lockService;
		}else if(lotteryId == 99701L){
			return lhcLockService;
		}else{
			return null;
		}
	}

}
