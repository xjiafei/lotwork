package com.winterframework.firefrog.game.dao;

import java.util.List;

import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.entity.GamePackageItem;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.orm.dal.ibatis3.BaseDao;

public interface IGameReturnPointDao extends BaseDao<GameRetPoint> {
	
	/**
	 * 保存订单返点信息
	 * @param slipList
	 * @param orderId
	 * @return 返回用户链和资金链的合并值  aa,bb,cc/11,22,33
	 * @throws Exception
	 */
	String saveGameOrderUserReturnPoint(List<GameSlip> slipList, Long orderId) throws Exception;

	/**
	 * 撤销订单时 撤销订单返点
	 * @param orderId
	 */
	void updateGameRetunPointByOrderId(Long orderId);

	/**
	 * 根据OrderID和RetPoint信息
	 * @param id
	 * @return
	 */
	GameRetPoint getRetPointByOrderId(Long id);
	GameRetPoint getRetPointByOrderIdWithOutStatus(Long id);

	/**
	 * 获取订单详情的用户链和返点链
	 * @param gameOrderDetails 注单列表
	 * @param userChain 用户链
	 * @return userChain+"/"+retPointChain  example:"aa,bb,cc/33,22,11"
	 * @throws Exception
	 */
	String getUserChainAndRetunPointChain(List<GameSlip> gameOrderDetails, String userChain) throws Exception;
	
	/**
	 * 返回用户链和对应的返点链   返点计算说明： 总代的返点在gameAwardGroup表中   根据当前用户的奖金组信息 拿到奖金组的groupChain<br>
	 * 例子：根据groupChain(奖金组id链) 获取返点链为20,10,5,2 则分别获取的返点为10 ,5,3,2 即 自己的所得返点=自己的总返点-自己直接下级的返点
	 * @param gameOrderDetails
	 * @param userChain
	 * @return
	 * @throws Exception
	 */
	Long getRetunPoint(GameSlip gameOrderDetails, String userChain) throws Exception;


	/**
	 * 设置packageItem的返点信息，并返回用户链信息
	 * @param gameSlipList
	 * @param userChain
	 * @return
	 * @throws Exception
	 */
	String getRetunPointChain(List<GamePackageItem> gameSlipList, String userChain) throws Exception;

	/**
	 * 初始化用户链信息
	 * @param accounts
	 * @return
	 * @throws Exception
	 */
	String getUserChain(String accounts) throws Exception;
	
	public GameRetPoint getGameRetPointByGameOrderId(Long gameOrderId) ;
}
