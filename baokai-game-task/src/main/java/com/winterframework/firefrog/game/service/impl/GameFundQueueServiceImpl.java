
package com.winterframework.firefrog.game.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.IGameFundQueueDao;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameFundQueue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameFundQueueService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;

 
@Service("gameFundQueueServiceImpl") 
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameFundQueueServiceImpl implements IGameFundQueueService {
	private static final Logger log = LoggerFactory.getLogger(GameFundQueueServiceImpl.class);
	
	@Resource(name="gameFundQueueDaoImpl")
	private IGameFundQueueDao gameFundQueueDao; 
	@Resource(name="gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskService; 
	@Resource(name="gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService;  
	@Resource(name="gameFundQueueServiceImpl")
	private IGameFundQueueService gameFundQueueService;  
	@Resource(name = "gameOrderServiceImpl")
	private IGameOrderService gameOrderService; 
	  
	@Override
	public GameFundQueue getById(GameContext ctx, Long id) throws Exception {
		return this.gameFundQueueDao.getById(id);
	}
	@Override 
	public int save(GameContext ctx, GameFundQueue fundQueue) throws Exception {
		int retCount=0;
		if(fundQueue==null) return retCount;
		Date curDate=DateUtils.currentDate();
		if(fundQueue.getId()!=null){
			fundQueue.setUpdateTime(curDate);
			retCount=this.gameFundQueueDao.update(fundQueue);
		}else{
			fundQueue.setCreateTime(curDate);
			retCount=this.gameFundQueueDao.insert(fundQueue); 
		}
		return retCount;
	}
	@Override
	//@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class,readOnly = false)	//失败不影响主流程事务，但是主流程事务出错要回滚当前事务
	public int saveBatch(GameContext ctx, List<GameFundQueue> fundQueueList)
			throws Exception {
		int retCount=0; 
		if(fundQueueList!=null && fundQueueList.size()>0){
			boolean hasInsert=false; 
			for(GameFundQueue fundQueue:fundQueueList){
				if(fundQueue!=null && fundQueue.getId()==null){
					hasInsert=true;
				}
				retCount+=this.save(ctx, fundQueue); 
			}
			/*if(hasInsert){		//存在新增,则新增调度事件
				this.addGameControlEvent();
			}*/
		}    
		return retCount;
	}
	public int saveBatch2(GameContext ctx, List<GameFundQueue> fundQueueList)
			throws Exception {
		int retCount=0; 
		if(fundQueueList!=null && fundQueueList.size()>0){
			boolean hasInsert=false; 
			for(GameFundQueue fundQueue:fundQueueList){
				if(fundQueue!=null && fundQueue.getId()==null){
					hasInsert=true;
				}
				retCount+=this.save(ctx, fundQueue); 
			}
			/*if(hasInsert){		//存在新增,则新增调度事件
				this.addGameControlEvent();
			} */
		}  
		return retCount;
	}
	@Override
	public int remove(GameContext ctx, Long id) throws Exception {
		return this.gameFundQueueDao.delete(id);
	}
////////////////////////业务服务----begin//////////////////////////  
	@Override
	public List<GameFundQueue> getUnexecInLine(GameContext ctx) throws Exception {
		return this.gameFundQueueDao.getByStatus(GameFundQueue.Status.UN_EXEC.getValue());
	}
	@Override
	public List<String> getOrderCodeListUnexecInLine(GameContext ctx) throws Exception {
		return this.gameFundQueueDao.getOrderCodeListByStatus(ctx, GameFundQueue.Status.UN_EXEC.getValue());
	}
	@Override
	public List<GameFundQueue> getUnexecInLineByOrderCode(GameContext ctx,String orderCode) throws Exception {
		return this.gameFundQueueDao.getByOrderCodeAndStatus(ctx, orderCode, GameFundQueue.Status.UN_EXEC.getValue());
	}
	@Override
	public List<GameFundQueue> getUnexecInLineByOrderCodeList(GameContext ctx,List<String> orderCodeList) throws Exception {
		return this.gameFundQueueDao.getByOrderCodeListAndStatus(ctx, orderCodeList, GameFundQueue.Status.UN_EXEC.getValue());
	} 
	public java.util.List<GameFundQueue> getUnexecInLineByRowCount(GameContext ctx, Integer rowCount) throws Exception {
		return this.gameFundQueueDao.getByStatusAndRowCount(GameFundQueue.Status.UN_EXEC.getValue(),rowCount);
	};	
	
	//@Override
	public void doRequest_bk(GameControlEvent event) throws Exception {
		GameContext ctx=new GameContext();
		//获取订单列表
		List<String> orderCodeList=this.getOrderCodeListUnexecInLine(ctx);
		if(orderCodeList!=null && orderCodeList.size()>0){
			int batchSize=500; //一次调度执行订单数量
			int count=0;
			for(String orderCode:orderCodeList){  
				log.info("-----get fund queue list-----orderCode:"+orderCode);   
				try{
					this.gameFundQueueService.fundRequestByOrder(ctx, orderCode);
				}catch(Exception e){
					//每一订单作为请求独立体，不影响其它订单处理
					log.error("-----Exception occurs when request the fund queue.(orderCode:"+orderCode,e);   
				}
				count++;
				if(count>batchSize) break;
			}
		}
		
		/*
		List<GameFundQueue> fundQueueList=this.getUnexecInLine(ctx); 
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			int queueSize=fundQueueList.size();
			int batchSize=1000; 
			int beginIndex=0,endIndex=queueSize>batchSize?batchSize:queueSize;
			List<GameFundQueue> execQueueList=fundQueueList.subList(beginIndex, endIndex);
			if(queueSize>batchSize){ 	//超过batchSize，下次执行
				this.addGameControlEvent();
			}
			this.updateExec(ctx, execQueueList);
			this.gameFundRiskService.fundRequestBatches2(ctx, convert2RiskDtoList(execQueueList),1);
			
		}*/	
	}  
	@Override
	public void doRequest(GameControlEvent event) throws Exception {
		GameContext ctx=new GameContext();
		//获取订单列表
		List<String> orderCodeList=this.getOrderCodeListUnexecInLine(ctx);
		if(orderCodeList!=null && orderCodeList.size()>0){
			int batchSize=500; //一次调度执行订单数量
			int count=0;
			int batchSubSize=10; 
			List<String> doOrderCodeList=new ArrayList<String>();
			log.info("-----get fund queue list-----orderCode:"+orderCodeList); 
			for(String orderCode:orderCodeList){    
				count++; 
				doOrderCodeList.add(orderCode); 
				if(count%batchSubSize==0 || count==batchSize || count==orderCodeList.size()){
					try{ 
						this.gameFundQueueService.fundRequestByOrderList(ctx, doOrderCodeList);
					}catch(Exception e){
						//每一订单作为请求独立体，不影响其它订单处理
						log.error("-----Exception occurs when request the fund queue.(orderCode:"+orderCodeList,e);   
					}
					doOrderCodeList.clear();
				}  
				if(count>=batchSize) break;
			}
			doOrderCodeList=null;
		}
		
		/*
		List<GameFundQueue> fundQueueList=this.getUnexecInLine(ctx); 
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			int queueSize=fundQueueList.size();
			int batchSize=1000; 
			int beginIndex=0,endIndex=queueSize>batchSize?batchSize:queueSize;
			List<GameFundQueue> execQueueList=fundQueueList.subList(beginIndex, endIndex);
			if(queueSize>batchSize){ 	//超过batchSize，下次执行
				this.addGameControlEvent();
			}
			this.updateExec(ctx, execQueueList);
			this.gameFundRiskService.fundRequestBatches2(ctx, convert2RiskDtoList(execQueueList),1);
			
		}*/	
	}  
	public void doRequestNew(GameControlEvent event) throws Exception {
		GameContext ctx=new GameContext();
		int batchSize=500; //一次调度执行订单数量
		//获取订单列表
		List<GameFundQueue> fundQueueList=this.getUnexecInLineByRowCount(ctx,batchSize);
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			int count=0;
			int queueCount=0;
			int batchSubSize=10; 
			List<GameFundQueue> subFundQueueList=new ArrayList<GameFundQueue>();
			String preOrderCode="defaultCode";
			boolean isEnd=false;
			boolean isNew=false;
			for(GameFundQueue fundQueue:fundQueueList){
				queueCount++;	
				if(!preOrderCode.equals(fundQueue.getOrderCodeList())){					
					count++;
					isNew=true;
					preOrderCode=fundQueue.getOrderCodeList();
				}
				isEnd=queueCount==fundQueueList.size(); 
				if((isNew && (count-1)>0 && (count-1)%batchSubSize==0) || isEnd){ 
					if(isEnd){ 
						subFundQueueList.add(fundQueue);	//如果后续没有订单了则后补最后一条记录
					}
					try{  
						gameFundQueueService.fundRequestSubBatch(ctx,subFundQueueList);
					}catch(Exception e){
						//每一订单作为请求独立体，不影响其它订单处理
						log.error("-----Exception occurs when request the fund queue.(subFundQueueList:"+subFundQueueList,e);   
					}
					isNew=false;
					subFundQueueList.clear();
				}
				subFundQueueList.add(fundQueue);
			} 
			subFundQueueList=null;
		}
		
		/*
		List<GameFundQueue> fundQueueList=this.getUnexecInLine(ctx); 
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			int queueSize=fundQueueList.size();
			int batchSize=1000; 
			int beginIndex=0,endIndex=queueSize>batchSize?batchSize:queueSize;
			List<GameFundQueue> execQueueList=fundQueueList.subList(beginIndex, endIndex);
			if(queueSize>batchSize){ 	//超过batchSize，下次执行
				this.addGameControlEvent();
			}
			this.updateExec(ctx, execQueueList);
			this.gameFundRiskService.fundRequestBatches2(ctx, convert2RiskDtoList(execQueueList),1);
			
		}*/	
	} 
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void fundRequestSubBatch(GameContext ctx, List<GameFundQueue> fundQueueList)
			throws Exception {
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			log.info("-----request fund---------fundQueueList.size:"+fundQueueList.size());   
			log.info("-----update status firstly----");   
			this.updateExec(ctx, fundQueueList);
			if(!this.fundRequestBatches(ctx, convert2RiskDtoList(fundQueueList))){
				log.info("-----request fund failed,now throw exception to rollback----");  
				throw new Exception("request fund failed.fundQueueList:"+fundQueueList);
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void fundRequestByOrderList(GameContext ctx, List<String> orderCodeList)
			throws Exception {
		List<GameFundQueue> fundQueueList=this.getUnexecInLineByOrderCodeList(ctx, orderCodeList);
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			log.info("-----request fund---------fundQueueList.size:"+fundQueueList.size());   
			log.info("-----update status firstly----");   
			this.updateExec(ctx, fundQueueList);
			gameOrderService.updateFundStatus(ctx, GameOrder.FundStatus.UNWIN, orderCodeList);
			if(!this.fundRequestBatches(ctx, convert2RiskDtoList(fundQueueList))){
				log.info("-----request fund failed,now throw exception to rollback----");  
				throw new Exception("request fund failed.orderCode:"+orderCodeList);
			}
		}
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void fundRequestByOrder(GameContext ctx, String orderCode)
			throws Exception {
		List<GameFundQueue> fundQueueList=this.getUnexecInLineByOrderCode(ctx, orderCode);
		if(fundQueueList!=null && fundQueueList.size()>0){ 
			log.info("-----request fund---------fundQueueList.size:"+fundQueueList.size());   
			log.info("-----update status firstly----");   
			this.updateExec(ctx, fundQueueList);
			if(!this.fundRequestBatches(ctx, convert2RiskDtoList(fundQueueList))){
				log.info("-----request fund failed,now throw exception to rollback----");  
				throw new Exception("request fund failed.orderCode:"+orderCode);
			}
		}
	}
	/**
	 * 低粒度业务：封装资金请求接口
	 * @param ctx
	 * @param toRiskDTOList
	 * @return
	 * @throws Exception
	 */
	private boolean fundRequestBatches(GameContext ctx,List<TORiskDTO> toRiskDTOList) throws Exception {
		log.info("-----fundRequestBatches-----starting............");   
		if(toRiskDTOList==null) return false;
		try{
			log.info("进行正式资金请求");  
			this.gameFundRiskService.fundRequest(toRiskDTOList);  
		}catch(Exception e){ 			
			/*//log.error("调用risk资金接口失败，now try again(first time)");
			Thread.sleep(100);
			try{
				gameFundRiskService.fundRequest(toRiskDTOList); 
			}catch(Exception ee){
				Thread.sleep(100);
				try{
					gameFundRiskService.fundRequest(toRiskDTOList); 
				}catch(Exception eee){ 
					log.error("调用risk资金接口失败",e); 
					return false;
				}
			}*/
			log.error("调用risk资金接口失败",e); 
			return false;
		}
		return true;
	}
	
	
	private void addGameControlEvent() throws Exception{
		GameControlEvent event = new GameControlEvent();	 
		Date curDate=DateUtils.currentDate();
		event.setEnentType(new Long(GameControlEvent.EventType.FUND_QUEUE.getValue()));
		event.setLotteryid(10001L);
		event.setStartIssueCode(100000000L); 
		event.setSaleStartTime(curDate);
		event.setSaleEndTime(curDate);
		event.setStatus(0L); 
		event.setParams(null);
		event.setMessage("资金队列请求"); 
		this.gameControlEventService.save(event);
	}
	private int updateExec(GameContext ctx,List<GameFundQueue> fundQueueList) throws Exception {
		int retCount=0;
		if(fundQueueList!=null && fundQueueList.size()>0){
			for(GameFundQueue fundQueue:fundQueueList){
				fundQueue.setStatus(GameFundQueue.Status.EXEC.getValue()); 
			}
			retCount= this.saveBatch2(ctx, fundQueueList); 
		}
		return retCount;
	}
	private List<TORiskDTO> convert2RiskDtoList(List<GameFundQueue> fundQueueList){
		List<TORiskDTO> riskDtoList=new ArrayList<TORiskDTO>();
		if(fundQueueList!=null && fundQueueList.size()>0){
			for(GameFundQueue fundQueue:fundQueueList){
				if(fundQueue!=null){					
					riskDtoList.add(this.packRiskDto(fundQueue));
				}
			}
		}
		return riskDtoList;
	}
	private TORiskDTO packRiskDto(GameFundQueue fundQueue){
		if(fundQueue==null) return null;
		TORiskDTO riskDto=new TORiskDTO();
		riskDto.setLotteryid(fundQueue.getLotteryId());
		riskDto.setIssueCode(fundQueue.getIssueCode());
		riskDto.setType(fundQueue.getFundType());
		riskDto.setUserid(fundQueue.getUserIdList());
		riskDto.setAmount(fundQueue.getAmountList());
		riskDto.setOrderCodeList(GameFundTypesUtils.GAME_SYS_PLAN_RESERVE_UNFREEZEN_DETEAIL_TYPE==fundQueue.getFundType().intValue()?null: fundQueue.getOrderCodeList());
		riskDto.setPlanCodeList(fundQueue.getPlanCodeList());
		riskDto.setNote(fundQueue.getNote());
		return riskDto;
	}
////////////////////////业务服务----end////////////////////////// 
}
