package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLock;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.orm.dal.ibatis3.BaseDao;

/**
 * 封鎖變價設定DAO
 * @author Pogi.Lin
 */
public interface IGameLockDao extends BaseDao<GameLock> {
	/**
	 * 依據 gameId 取得所有封鎖變價設定。
	 * @param gameId 彩種ID
	 * @return
	 * @throws Exception
	 */
	public GameLock queryGameLock(Long gameId) throws Exception;
	/**
	 * 取得屬於 lotteryid, issueCode, 狀態為1(等待開獎)、2(中獎)、3(未中獎) 訂單對應 GAME_RET_POINT(遊戲返點信息) 的返點金額鏈。
	 * @param lockdata
	 * @return 返點金額鏈(ret_point_chain)
	 * @throws Exception
	 */
	public List<String> queryGameLockTotalRetPoint(GameLockDataQueryRequest lockdata) throws Exception;
}
