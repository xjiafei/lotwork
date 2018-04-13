package com.winterframework.firefrog.game.service;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameLock;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.web.dto.GameLockDataQueryRequest;
import com.winterframework.firefrog.game.web.dto.GameNumberShares;

/**
 * 封鎖變價設定Service
 * @author Pogi.Lin
 */
public interface IGameLockService {
	/**
	 * 查询封锁数据
	 * @param gameId 彩種ID
	 * @return
	 * @throws Exception 
	 */
	public GameLock queryGameLock(Long gameId) throws Exception;

	/**
	 * <pre>
	 * 修改封锁数据，並依據 status 異動 game_series.change_status 對應封鎖的位元狀態。<br>
	 * GameLock 	狀態	1：待審核、2：審核通過、3：審核不通過、4：已發佈
	 * 對應
	 * GameSeries	狀態	3:待审核、4:待发布、5:审核不通过、1:发布通过、6:发布不通过(其他情況)
	 * 例：
	 * gameLock.status = 2，則 gameSeries.change_status = 11411111；因封鎖對應第三個位元。
	 * </pre>
	 * @param gameLock
	 * @return
	 * @throws Exception 
	 */
	public int updateGameLock(GameLock gameLock) throws Exception;

	/**
	 * 查询封锁变价实体；由 gameLock, gameLockAppraise, gameLockParam 各別取出資料存入 gameLockEntity。
	 * @param gameId 彩種ID
	 * @return
	 * @throws Exception
	 */
	public GameLockEntity queryCurrUseGameLockEntity(Long gameId) throws Exception;

	/**
	 * 获取封锁号码数据
	 * @param gameLock
	 * @return
	 * @throws Exception 
	 */
	public List<GameNumberShares> getGameNumberShares3(Long betTal,List<GameNumberShares> lockList) throws Exception;
	
	/**
	 * 获取封锁号码数据
	 * @param gameLock
	 * @return
	 * @throws Exception 
	 */
	public List<GameNumberShares> getGameNumberShares2(Long betTal,List<GameNumberShares> lockList) throws Exception;
	
	/**
	 * 获取六合彩封锁頁面号码数据。<br>
	 * 因 lockList 不一定會有全部號碼或生肖故要補齊缺少的資料。
	 * @param profitLoss 未投注號碼盈虧值
	 * @param lockList
	 * @param isZodiac 玩法是否選擇正特碼_一肖；true:是、false:否
	 * @return 
	 * @throws Exception
	 */
	public List<GameNumberShares> getGameNumberShareslLHC(Long profitLoss, List<GameNumberShares> lockList, boolean isZodiac) throws Exception;
	
	public Long queryAllSlipSale(Long lotteryId, Long issueCode) throws Exception;
	
	/**
	 * 依據 lotteryid, issueCode 取得狀態為 1(等待開獎), 2(中獎), 3(未中獎) 的訂單總金額。
	 * @param lockdatae
	 * @return
	 * @throws Exception
	 */
	public Long queryAlltotamount(GameLockDataQueryRequest lockdatae) throws Exception;

	/**
	 * 取得屬於 lotteryid, issueCode, 狀態為1(等待開獎)、2(中獎)、3(未中獎) 訂單對應 GAME_RET_POINT(遊戲返點信息) 的返點金額鏈加總值。
	 * @param lockdata
	 * @return sum(ret_point_chain)
	 * @throws Exception
	 */
	public Long queryTotalRetPoint(GameLockDataQueryRequest lockdata) throws Exception;
}
