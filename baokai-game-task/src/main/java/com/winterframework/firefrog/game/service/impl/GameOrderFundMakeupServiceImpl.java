
package com.winterframework.firefrog.game.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameFundQueueService;
import com.winterframework.firefrog.game.service.IGameOrderFundMakeupService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.utils.ParamsParserUtil;

 
@Service("gameOrderFundMakeupServiceImpl") 
@Transactional(rollbackFor = Exception.class)
public class GameOrderFundMakeupServiceImpl implements IGameOrderFundMakeupService {
	private static final Logger log = LoggerFactory.getLogger(GameOrderFundMakeupServiceImpl.class);
	
	@Resource(name = "gameFundQueueServiceImpl")
	private IGameFundQueueService gameFundQueueService;
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService; 
	@Override
	public void doBusiness(GameControlEvent event) throws Exception {
		/** 
		 * 1.导出当期注单信息
		 * 2.导出活动
		 */
		// 解析event.params 得到需要的业务信息 
		Map map = ParamsParserUtil.parse(event.getParams());
		if (map == null || map.size() == 0) {
			throw new Exception("调度事件的参数不正确");
		}		
		Long lotteryId = map.get("lotteryId") == null ? null : Long.valueOf(((String) map.get("lotteryId")));
		Long issueCode = map.get("issueCode") == null ? null : Long.valueOf(((String) map.get("issueCode"))); 
		if(lotteryId==null || issueCode==null){
			throw new Exception("调度事件的参数不正确");
		} 
		GameContext ctx=new GameContext(); 
		int batchSize=10000;
		List<GameOrder> orderList=this.gameOrderService.getUnfundedByLotteryIdAndIssueCodeAndBatchSize(ctx, lotteryId, issueCode, batchSize);
		if(orderList!=null && orderList.size()>0){
			boolean hasExcep=false;
			String excepMsg="";
			int count=0;
			int batchSubSize=3; 
			List<String> doOrderCodeList=new ArrayList<String>();
			for(GameOrder order:orderList){       
				count++; 
				doOrderCodeList.add(order.getOrderCode()); 
				if(count%batchSubSize==0 || count==batchSize || count==orderList.size()){
					try{ 
						gameFundQueueService.fundRequestByOrderList(ctx, doOrderCodeList);
					}catch(Exception e){
						if(!hasExcep){
							hasExcep=true;
							excepMsg=e.getMessage();
						}
						//每一订单作为请求独立体，不影响其它订单处理
						log.error("-----Exception occurs when request the fund queue.(doOrderCodeList:"+doOrderCodeList,e);   
					}
					doOrderCodeList.clear();
				}  
			} 
			doOrderCodeList=null;
			if(hasExcep){
				throw new Exception(excepMsg);
			}
		} 
	}  
////////////////////////业务服务----end////////////////////////// 
}
