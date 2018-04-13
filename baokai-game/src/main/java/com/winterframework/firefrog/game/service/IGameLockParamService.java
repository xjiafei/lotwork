package com.winterframework.firefrog.game.service;

import com.winterframework.firefrog.game.dao.vo.GameLockParam;

public interface IGameLockParamService {
	public GameLockParam queryGameLockParam(Long gameId) throws Exception;

	/**
	 * <pre>
	 * 修改封锁数据，並依據 status 異動 game_series.change_status 對應變價的位元狀態。<br>
	 * GameLockParam 	狀態	1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 * 對應
	 * GameSeries		狀態	3:待审核、4:待发布、5:审核不通过、1:发布通过、6:发布不通过(其他情況)
	 * 例：
	 * gameLock.status = 3，則 gameSeries.change_status = 11151111；因變價對應第四個位元。
	 * </pre>
	 * @param gameLockParam
	 * @return
	 * @throws Exception
	 */
	public int updateGameLockParam(GameLockParam gameLockParam) throws Exception;
}
