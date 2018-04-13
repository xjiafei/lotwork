package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameOrderDrawService;
import com.winterframework.firefrog.game.service.IGameWarnService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

/** 
* @ClassName AbstractGameRevocationOrderAfterDrawServiceImpl 
* @Description 开奖后订单撤销父类 
* @author  hugh
* @date 2014年5月12日 下午2:33:56 
*  
*/
@Service("gameRevocationOrderAfterDrawServiceImpl")
@Transactional
public class AbstractGameRevocationOrderAfterDrawServiceImpl extends AbstractGameRevocationOrderService {

	@Resource(name = "gameWarnServiceImpl")
	private IGameWarnService gameWarnServiceImpl; 
	@Resource(name = "gameOrderDrawServiceImpl")
	private IGameOrderDrawService gameOrderDrawService; 
	
	/** 
	* @Title: getCancelTORiskDTO 
	* @Description: 获取撤销订单 和 返点 资金交易数据
	* @param order
	* @return
	*/
	public List<TORiskDTO> getCancelTORiskDTO(ProcessResult result,GameOrder order) {
		List<TORiskDTO> dtos = new ArrayList<TORiskDTO>();
		dtos.add(gameOrderFundServcie.packageOrderCancelTORiskDTO(order));
		dtos.add(gameReturnPointFundServcie.packageCancelTORiskDTO(order));
		return dtos;
	}

	/**
	* @Title: doRevocation
	* @Description:执行撤销（包含订单更新 返点更新 获取撤销订单 和 返点 资金交易数据）
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderService#doRevocation(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception{
		gameOrderFundServcie.updateOrderCancelStatus(order);
		gameReturnPointFundServcie.updateRetPointCancelStatus(order.getId());
		undoLock(order);
		return getCancelTORiskDTO(result,order);
	} 
	/**
	* @Title: doRevocationToBeforeDraw
	* @Description:执行撤销到开奖前 （包含 获取撤销订单 和 撤销返点 ，冻结投注 资金交易数据，更新订单状态，返点状态）（重新输入开奖号码时调用）
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService#doReDraw(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception{
		List<TORiskDTO> dtos = getCancelTORiskDTO(result,order);
		dtos.add(gameOrderFundServcie.packageOrderFreezeTORiskDTO(order));
		gameOrderFundServcie.updateOrderFreezeStatus(order);
		gameReturnPointFundServcie.updateRetPointFreeznStatus(order.getId());
		return dtos;
	}  
	@Override
	public List<TORiskDTO> doRevocationToRedraw(ProcessResult result,
			GameOrder order) throws Exception {
		/**
		 * 已开奖的订单：
		 * 	中->中	处理
			中->不中	处理
			不中->中	处理
			不中->不中  不处理
			异常->中	不处理
			异常->不中	不处理 
		 */
		GameContext ctx=new GameContext();
		boolean isWinPre=order.getStatus().intValue()==GameOrder.Status.WIN.getValue();
		boolean isWarnPre=order.getStatus().intValue()==GameOrder.Status.EXCEP.getValue();
		boolean isWin=this.gameOrderDrawService.isWin(ctx, order);
		if(!isWinPre && !isWin) return null;
		if(isWarnPre) return null;	//异常订单不处理
		gameOrderFundServcie.updateOrderFreezeStatus(order);
		gameReturnPointFundServcie.updateRetPointFreeznStatus(order.getId());
		return null;
	} 
	/** 
	* @Title: cancelGameOrderWarn 
	* @Description: 撤销风险订单
	* @param order
	*/
	public void cancelGameOrderWarn(GameOrder order) {
		gameWarnServiceImpl.cancelWarnOrder(order.getId());
	}

	/**
	* @Title: doRevocationNotAskPlan
	* @Description:是否执行追号 由子类判断实现
	* @param order
	* @return 
	* @see com.winterframework.firefrog.game.service.revocation.impl.AbstractGameRevocationOrderService#doRevocationNotAskPlan(com.winterframework.firefrog.game.dao.vo.GameOrder) 
	*/
	@Override
	public List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception{
		return doRevocation(result,order);
	}

}
