package com.winterframework.firefrog.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.user.dao.IJudgementDao;
import com.winterframework.firefrog.user.dao.impl.JudgementDaoImpl;
import com.winterframework.firefrog.user.dao.vo.Judgement;
import com.winterframework.firefrog.user.service.IJudgementService;
import com.winterframework.orm.dal.ibatis3.BaseManager;

/**
 * 
 *    
 * 类功能说明: 冻结解冻用户操作服务类  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @Version 1.0  
 * @author Richard & David
 *
 */
@Service("judgementServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class JudgementServiceImpl extends BaseManager<JudgementDaoImpl, Judgement> implements IJudgementService {

	@Override
	public void setEntityDao(JudgementDaoImpl entityDao) {
		this.entityDao = entityDao;
	}

	@Resource(name = "judgementDaoImpl")
	private IJudgementDao judgementDao;

	/**
	* Title: initAction
	* Description:当数据库中不存在这个judgement，则插入；当新的时间比数据库中工具类的生效时间要早，就更新这个时间
	* @param judgement 
	* @see com.winterframework.firefrog.user.service.IJudgementService#initAction(com.winterframework.firefrog.user.dao.vo.Judgement) 
	*/
	@Override
	public void initAction(Judgement judgement) {
		Judgement jd = judgementDao.getAction(judgement);
		if (null == jd) {
			judgementDao.initAction(judgement);
		} else if (jd.getEffectTime().after(judgement.getEffectTime())) {
			judgementDao.updateAction(judgement);
		}

	}

	@Override
	public void updateAction(Judgement judgement) {
		judgementDao.updateAction(judgement);

	}

	@Override
	public Judgement getAction(Judgement judgement) {
		return judgementDao.getAction(judgement);

	}

}
