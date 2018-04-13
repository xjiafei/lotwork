package com.winterframework.firefrog.game.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameSeriesCheckDao;
import com.winterframework.firefrog.game.dao.vo.GameSeriesCheck;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GameSeriesCheckDaoImpl 
* @Description: 彩系彩种状态审核实现类 
* @author Denny 
* @date 2013-8-27 下午3:31:24 
*  
*/
@Repository("gameSeriesCheckDaoImpl")
public class GameSeriesCheckDaoImpl extends BaseIbatis3Dao<GameSeriesCheck> implements IGameSeriesCheckDao {

	@Override
	public GameSeriesCheck getGameSeriesCheckByLotteryId(long lotteryid, int checkType) {
        Map param = new HashMap();
        param.put("lotteryid", lotteryid);
        param.put("checkType", checkType);
		return this.sqlSessionTemplate.selectOne("getGameSeriesCheckByLotteryId", param);
	}

	@Override
	public void addGameSeriesCheck(long lotteryid) {

	}

	@Override
	public void removeGameSeriesCheck(long id) {

	}

	@Override
	public void updateGameSeriesCheck(GameSeriesCheck GameSeriesCheck) {
		this.update(GameSeriesCheck);

	}

	@Override
	public void insert(List<GameSeriesCheck> entitys) {

	}

	@Override
	public GameSeriesCheck getLotteryHelpDes(long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.getLotteryHelpDesByLotteryId", lotteryid);
	}

	@Override
	public GameSeriesCheck getLotterySellingStatus(long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.getLotterySellingStatus", lotteryid);
	}

	@Override
	public void updateToPublish(Long lotteryid, Long auditType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("status", auditType==1 ? 4 : 5);
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.updateToPublish", map);
	}

	@Override
	public Integer getLotteryCheckStatus(long lotteryid) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.getLotteryCheckStatus", lotteryid);
	}

	@Override
	public void insertOne(GameSeriesCheck gsc) {
		this.insert(gsc);
	}

	@Override
	public void removeByLotteryId(Long lotteryid, Integer checkType) {
        Map param = new HashMap();
        param.put("lotteryid", lotteryid);
        param.put("checkType", checkType);
        this.sqlSessionTemplate.delete("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.delete", param);
	}

    @Override
	public void updateToCheck(GameSeriesCheck gsc) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.updateToCheck", gsc);
	}

	@Override
	public void updateSeriesCheckToNotPublished(Long lotteryid) {
		this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.updateSeriesCheckToNotPublished", lotteryid);
	}

    @Override
    public Integer getStatus(Long lotteryId, Integer type) throws Exception {
        if (lotteryId == null || type == null) throw new Exception();
        Map param = new HashMap();
        param.put("lotteryid", lotteryId);
        param.put("checkType", type);
        return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.getStatusS", param);
    }

    @Override
    public void updateStatus(Long lotteryId, Integer checkStatus, Integer checkType) throws Exception {
        GameSeriesCheck gameSeriesCheck = new GameSeriesCheck();
        gameSeriesCheck.setLotteryid(lotteryId);
        gameSeriesCheck.setCheckStatus(checkStatus);
        gameSeriesCheck.setCheckType(checkType);
        this.sqlSessionTemplate.update("com.winterframework.firefrog.game.dao.vo.GameSeriesCheck.updateStatus", gameSeriesCheck);
    }
}
