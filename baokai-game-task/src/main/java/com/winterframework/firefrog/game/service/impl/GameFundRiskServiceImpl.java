package com.winterframework.firefrog.game.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DeadlockLoserDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.winterframework.firefrog.common.httpjsonclient.IHttpJsonClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.game.dao.vo.GameControlEvent;
import com.winterframework.firefrog.game.dao.vo.GameFundQueue;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.fund.service.IGameRiskService;
import com.winterframework.firefrog.game.service.IGameControlEventService;
import com.winterframework.firefrog.game.service.IGameFundQueueService;
import com.winterframework.firefrog.game.service.IGameFundRiskService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.web.dto.AddCoinRequest;
import com.winterframework.firefrog.game.web.dto.FreezeFundRequest;
import com.winterframework.firefrog.game.web.dto.ReduceCoinRequest;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;
import com.winterframework.firefrog.game.web.dto.TORiskRequest;
import com.winterframework.firefrog.game.web.dto.TORiskResponse;
import com.winterframework.firefrog.game.web.dto.UnFreezeFundRequest;
import com.winterframework.firefrog.game.web.util.GameFundTypesUtils;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Response;

@Service("gameFundRiskServiceImpl") 
@Transactional(rollbackFor=Exception.class) 
public class GameFundRiskServiceImpl implements IGameFundRiskService {

	private static final Logger log = LoggerFactory.getLogger(GameFundRiskServiceImpl.class);

	@Resource(name = "httpJsonClientImpl")
	private IHttpJsonClient httpClient;

	@PropertyConfig(value = "url.gameRisk.reduceCoin")
	private String reduceCoinUrl;

	@PropertyConfig("url.gameRisk.addCoin")
	private String addCoinUrl;

	@PropertyConfig("url.gameRisk.unFreezeFund")
	private String unFreezeUrl;

	@PropertyConfig("url.gameRisk.freezeFund")
	private String freezeUrl;

	@PropertyConfig("url.gameRisk.integration")
	private String integrationUrl;

	@PropertyConfig(value = "url.connect")
	private String serverPath; 
	@Resource(name = "gameFundQueueServiceImpl")
	private IGameFundQueueService gameFundQueueService;
	@Resource(name = "gameOrderServiceImpl")
	protected IGameOrderService gameOrderService;
	@Resource(name = "gameControlEventServiceImpl")
	private IGameControlEventService gameControlEventService; 
	@Resource(name = "gameFundRiskServiceImpl")
	private IGameFundRiskService gameFundRiskService; 
	@Resource(name = "gameRiskServiceImpl")
	private IGameRiskService gameRiskService; 
	private final String SEPERATOR="|";
	
	private final int awardBatchSize=5;
	private final int commonBatchSize=1;
	
	@Override
	public void fundRequest(List<TORiskDTO> toRiskDTOList) throws Exception { 
		if (null!= toRiskDTOList) { 
			try {				 
				gameRiskService.integration(toRiskDTOList);   
			}catch (SocketTimeoutException ste) { //应亨总要求，timeout默认资金调用成功,task不抛出异常
				log.error("distributeAward error--timeout:", ste);    
			}catch (Exception e) {
				log.error("distributeAward error:", e); 
				throw new Exception("ask fund request exception"+e.getMessage());
			}	
		}
	}
	
	//@Override
	public void fundRequest_bk(List<TORiskDTO> toRiskDTOList) throws Exception {
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) { 
				TORiskRequest request = new TORiskRequest(); 
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});
			} 
		}catch (SocketTimeoutException ste) { //应亨总要求，timeout默认资金调用成功,task不抛出异常
			log.error("distributeAward error--timeout:", ste);  
			//此时responce==null
		}catch (Exception e) {
			log.error("distributeAward error:", e); 
			throw new Exception("ask fund request exception"+e.getMessage());
		}		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new Exception("ask fund request exception"+responce.getBody().getResult().getExceptionMessage());
		} 
	}
	
	@Override
	//@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class) 
	public boolean fundRequestAward(GameContext ctx,Map<String, List<TORiskDTO>> dtoMap)  throws Exception{
		if(dtoMap==null) return true;  
		try{
			return fundRequestEverBatch(ctx,getAwardDtoMap(dtoMap),awardBatchSize,true);
		}catch(Exception e){
			log.error("award fund request failed when ever batch.dtoMap："+dtoMap,e);
			return false;
		}
	}
	@Override
	//@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class) 
	public boolean fundRequest(GameContext ctx,Map<String, List<TORiskDTO>> dtoMap) throws Exception{  
		if(dtoMap==null) return true;   
		//if(dtoMap.size()<100){ 
		try{
			return fundRequestEverBatch(ctx,dtoMap,commonBatchSize,false);
		}catch(Exception e){
			log.error("common fund request failed when ever batch.dtoMap："+dtoMap,e);
			return false;
		}
		/*}else{
			this.fundRequestSubmap(dtoMap,false); 
		}	*/
	}
	
	private  Map<String, List<TORiskDTO>> getAwardDtoMap(Map<String, List<TORiskDTO>> dtoMap) {
		Map<String,List<TORiskDTO>> awardDtoMap=new HashMap<String,List<TORiskDTO>>(); 
		for(Map.Entry<String, List<TORiskDTO>> entry:dtoMap.entrySet()){
			List<TORiskDTO> dtoList=entry.getValue();
			if(dtoList!=null){ 
				List<TORiskDTO> awardDtoList=new ArrayList<TORiskDTO>();
				List<TORiskDTO> tempDtoList=new ArrayList<TORiskDTO>();
				for(TORiskDTO dto:dtoList){
					if(dto!=null){
						if(dto.getType().intValue()==GameFundTypesUtils.GAME_DISTRIBUTE_DETEAIL_DETEAIL_TYPE){
							awardDtoList.add(dto);
							tempDtoList.add(dto);
						}
					}
				}
				if(awardDtoList.size()>0){
					awardDtoMap.put(entry.getKey(), awardDtoList); 
					dtoList.removeAll(tempDtoList);	//原始map中去除派奖账变
				}
			}			
		}		
		return awardDtoMap;
	}

	private boolean fundRequestEverBatch(GameContext ctx,Map<String, List<TORiskDTO>> dtoMap,int batchSize,boolean isAward) throws Exception{
		boolean isSuccess=true;
		if(dtoMap==null || dtoMap.size()==0) return isSuccess;
		int mapSize=dtoMap.size();
		int count=0;  
		Map<String, List<TORiskDTO>> subMap=new HashMap<String, List<TORiskDTO>>();
		for(Map.Entry<String, List<TORiskDTO>> entry:dtoMap.entrySet()){  
			count++;  
			subMap.put(entry.getKey(), entry.getValue());
			if(count%batchSize==0 || count==mapSize){
				try{
					if(!gameFundRiskService.fundRequestSubmap(ctx,subMap,isAward)){
						isSuccess=false;
					}
				}catch(Exception e){
					log.error("fund request failed.subMap:"+subMap+";isAward:"+isAward,e);
					isSuccess=false;
				}
				subMap.clear();
			}				
		}
		subMap=null;
		return isSuccess;
	} 
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public boolean fundRequestSubmap(GameContext ctx,Map<String, List<TORiskDTO>> subDtoMap,boolean isAward) throws Exception{  
		List<TORiskDTO> dtoList=new ArrayList<TORiskDTO>(); 
		List<String> orderCodeList=new ArrayList<String>();
		try{  
			String orderCodes="";
			for(Map.Entry<String, List<TORiskDTO>> entry:subDtoMap.entrySet()){
				dtoList.addAll(entry.getValue());	
				orderCodes+=entry.getKey()+SEPERATOR;
				orderCodeList.add(entry.getKey());
			} 		
			log.info("fund request(orderCodeList:"+orderCodes+")");
			if(isAward){  
				this.fundRequest(dtoList); 		
				//更新fund_status=2
				gameOrderService.updateFundStatus(new GameContext(), GameOrder.FundStatus.WIN,orderCodeList);
			}else{ 
				//if(subDtoMap.size()<100){
					this.fundRequest(dtoList); 	
					//更新fund_status=3
					List<String> failedOrderCodeList=(List<String>)ctx.get("AWARD_FAILED_ORDERCODELIST");
					if(null!=orderCodeList && orderCodeList.size()>0){
						List<String> finishedOrderCodeList=new ArrayList<String>();
						if(null==failedOrderCodeList){	
							finishedOrderCodeList=orderCodeList; 
						}else{
							for(String orderCode:orderCodeList){
								if(!failedOrderCodeList.contains(orderCode)){
									finishedOrderCodeList.add(orderCode);
								}
							}
						}
						gameOrderService.updateFundStatus(new GameContext(), GameOrder.FundStatus.UNWIN,finishedOrderCodeList);
					}
				/*}else{
					saveAllRiskDto(subDtoMap, orderCodes);
				}*/
			}
			dtoList=null;
		}catch(Exception e){ 
			/*try{  
				Thread.sleep(100);
				this.fundRequest(dtoList); 
				if(isAward){ 
					gameOrderService.updateFundStatus(new GameContext(), GameOrder.FundStatus.WIN,orderCodeList);
				}else{
					gameOrderService.updateFundStatus(new GameContext(), GameOrder.FundStatus.UNWIN,orderCodeList);
				}
			}catch(Exception ee){*/
			log.error("award fund request failed when try.dtoList:"+dtoList,e);
			gameFundRiskService.doFundFailed(ctx,subDtoMap,orderCodeList, getStackTrace(e));
			if(isAward){ 	//派送奖金异常的订单，即使后面返点成功也保留fund_status=1
				List<String> failedOrderCodeList=(List<String>)ctx.get("AWARD_FAILED_ORDERCODELIST");
				if(null==failedOrderCodeList){
					failedOrderCodeList=new ArrayList<String>();
				}
				failedOrderCodeList.addAll(orderCodeList);
				ctx.set("AWARD_FAILED_ORDERCODELIST", failedOrderCodeList); 
			}
			return false;
			//}
		}
		return true;
	}
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class)
	public void doFundFailed(GameContext ctx,Map<String, List<TORiskDTO>> subDtoMap,List<String> orderCodeList, String errMsg){
		try{
			try{
				gameOrderService.updateFundStatus(new GameContext(), GameOrder.FundStatus.NEED,orderCodeList);
			}catch(Exception e){
				log.error("update order fund status failed.(fund_status="+GameOrder.FundStatus.NEED+",orderCodeList="+orderCodeList+")",e);
			}			
			List<TORiskDTO> dtoExcepList=null;
			List<TORiskDTO> dtoExcepAllList=null;
			String orderCode=null;
			String orderCodes="";
			for (Map.Entry<String, List<TORiskDTO>> entry:subDtoMap.entrySet()){
				orderCode=entry.getKey();
				dtoExcepList=entry.getValue();
				for(TORiskDTO dtoExcep:dtoExcepList){
					dtoExcep.setOrderCodeList(orderCode);
				}
				if(dtoExcepAllList==null){
					dtoExcepAllList=new ArrayList<TORiskDTO>();
				}
				dtoExcepAllList.addAll(dtoExcepList);
				orderCodes+=orderCode+SEPERATOR;
			}
			log.error("fund request failed,now put the abnormal fund into game fund queue(orderCodes:"+orderCodes+")"); 
			try{
				log.error("保存game fund queue,saveRiskDtoList size:"+(dtoExcepAllList!=null?dtoExcepAllList.size():0)); 
				//addGameOrderFundEvent(dtoExcepAllList.get(0).getLotteryid(),dtoExcepAllList.get(0).getIssueCode());
				saveGameFundQueue(new GameContext(),dtoExcepAllList,errMsg);
			}catch(Exception eee){
				log.error("保存game fund queue失败.资金内容进入日志",eee); 
				this.logRiskDtoList(dtoExcepAllList); 
			} 
			dtoExcepAllList=null;
			dtoExcepList=null;
		}catch(Exception e){
			log.error("exception occurs when do fund failed.(orderCodeList="+orderCodeList+")",e);
		}
	}
	private void addGameOrderFundEvent(Long lotteryId,Long issueCode) throws Exception{
		Date date=DateUtils.currentDate();
		GameControlEvent event = new GameControlEvent();	 
		event.setLotteryid(10003L); 
		event.setStartIssueCode(issueCode);
		event.setEnentType(new Long(GameControlEvent.EventType.ORDER_FUND.getValue()));
		event.setSaleEndTime(date);
		event.setSaleStartTime(date);
		event.setEndIssueCode(lotteryId);
		event.setParams("lotteryId:"+lotteryId+";issueCode:"+issueCode);
		event.setStatus(0L);
		event.setMessage("资金调用");   
		this.gameControlEventService.save(event);
	}
	private void saveAllRiskDto(Map<String, List<TORiskDTO>> subDtoMap,
			String orderCodeList) {
		List<TORiskDTO> dtoExcepList=null;
		List<TORiskDTO> dtoExcepAllList=null;
		String orderCode=null; 
		for (Map.Entry<String, List<TORiskDTO>> entry:subDtoMap.entrySet()){
			orderCode=entry.getKey();
			dtoExcepList=entry.getValue();
			for(TORiskDTO dtoExcep:dtoExcepList){
				dtoExcep.setOrderCodeList(orderCode);
			}
			if(dtoExcepAllList==null){
				dtoExcepAllList=new ArrayList<TORiskDTO>();
			}
			dtoExcepAllList.addAll(dtoExcepList);
			//orderCodeList+=orderCode+SEPERATOR;
		}
		log.error("fund request failed(orderCodeList:"+orderCodeList+")");
		log.error("now put the abnormal fund into game fund queue(orderCodeList:"+orderCodeList+")"); 
		try{
			log.error("保存game fund queue,saveRiskDtoList size:"+(dtoExcepAllList!=null?dtoExcepAllList.size():0));
			saveGameFundQueue(new GameContext(),dtoExcepAllList,null);
		}catch(Exception ee){
			log.error("保存game fund queue失败.资金内容进入日志",ee); 
			this.logRiskDtoList(dtoExcepAllList); 
		}
		dtoExcepAllList=null;
		dtoExcepList=null;
	}
	@Override 
	@Transactional(propagation=Propagation.REQUIRES_NEW,rollbackFor=Exception.class,readOnly=false)
	public void fundRequestBatches(GameContext ctx,
			List<TORiskDTO> toRiskDTOList,int requestType) throws Exception {
		log.info("任务中心开始通过审核中心请求资金系统"); 
		if(toRiskDTOList==null) return;
		int dtoSize=toRiskDTOList.size();
		if(dtoSize>0){   
			int batchSize=30;
			int interSize=(dtoSize%batchSize)==0?(dtoSize/batchSize):(dtoSize/batchSize+1);
			int beginIndex=0,endIndex=0;
			List<TORiskDTO> tempRiskDtoList=null;
			boolean haveFlag=false; 
			for(int i=0;i<interSize;i++){ 
				log.info("任务中心进行第（"+(i+1)+"批资金处理"); 
				beginIndex=i*batchSize;
				endIndex=((i+1)*batchSize)>dtoSize?dtoSize:((i+1)*batchSize);
				log.info("资金列表：beginIndex="+beginIndex+",endIndex:"+endIndex+",toRiskDTOList size="+dtoSize);
				tempRiskDtoList=toRiskDTOList.subList(beginIndex, endIndex);
				log.info("资金列表：tempRiskDtoList size:"+(tempRiskDtoList!=null?tempRiskDtoList.size():0));
				if(tempRiskDtoList!=null && tempRiskDtoList.size()>0){
					try{
						log.info("进行正式资金请求");
						this.fundRequest(tempRiskDtoList);   
						//haveFlag=true; 
					}catch(Exception e){
						log.error("调用risk资金请求失败",e);
						/*if(!haveFlag){	//如果资金之前的循环调用已经成功，则不回滚开奖
							throw e;
						}*/ 
						saveRiskDtoList(ctx, toRiskDTOList, dtoSize,
								beginIndex, e);
						break;
					} 
				}					
			}
			tempRiskDtoList=null;
		}
	}
	@Override 
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class,readOnly=false)
	public void fundRequestBatches2(GameContext ctx,
			List<TORiskDTO> toRiskDTOList,int requestType) throws Exception {
		log.info("任务中心开始通过审核中心请求资金系统"); 
		if(toRiskDTOList==null) return;
		int dtoSize=toRiskDTOList.size();
		if(dtoSize>0){   
			int batchSize=30;
			int interSize=(dtoSize%batchSize)==0?(dtoSize/batchSize):(dtoSize/batchSize+1);
			int beginIndex=0,endIndex=0;
			List<TORiskDTO> tempRiskDtoList=null;
			boolean haveFlag=false; 
			for(int i=0;i<interSize;i++){ 
				log.info("任务中心进行第（"+(i+1)+"批资金处理"); 
				beginIndex=i*batchSize;
				endIndex=((i+1)*batchSize)>dtoSize?dtoSize:((i+1)*batchSize);
				log.info("资金列表：beginIndex="+beginIndex+",endIndex:"+endIndex+",toRiskDTOList size="+dtoSize);
				tempRiskDtoList=toRiskDTOList.subList(beginIndex, endIndex);
				log.info("资金列表：tempRiskDtoList size:"+(tempRiskDtoList!=null?tempRiskDtoList.size():0));
				if(tempRiskDtoList!=null && tempRiskDtoList.size()>0){
					try{
						log.info("进行正式资金请求");
						this.fundRequest(tempRiskDtoList);   
						//haveFlag=true; 
					}catch(Exception e){
						log.error("调用risk资金请求失败",e);
						/*if(!haveFlag){	//如果资金之前的循环调用已经成功，则不回滚开奖
							throw e;
						}*/ 
						saveRiskDtoList2(ctx, toRiskDTOList, dtoSize,
								beginIndex, e,requestType);
						break;
					} 
				}					
			}
			tempRiskDtoList=null;
		}
	}
	public void saveRiskDtoList(GameContext ctx,
			List<TORiskDTO> toRiskDTOList, int dtoSize, int beginIndex,
			Exception e) {
		log.error("未完成资金列表：beginIndex="+beginIndex+",endIndex:"+dtoSize+",toRiskDTOList="+dtoSize);
		List<TORiskDTO> saveRiskDtoList=toRiskDTOList.subList(beginIndex, dtoSize);
		try{
			log.error("保存game fund queue,saveRiskDtoList size:"+(saveRiskDtoList!=null?saveRiskDtoList.size():0));
			saveGameFundQueue(ctx, saveRiskDtoList,getStackTrace(e)); 
		}catch(Exception ee){
			log.error("保存game fund queue失败.资金内容进入日志",ee); 
			this.logRiskDtoList(saveRiskDtoList); 
		}
		saveRiskDtoList=null;
	}
	public void saveRiskDtoList2(GameContext ctx,
			List<TORiskDTO> toRiskDTOList, int dtoSize, int beginIndex,
			Exception e,int requestType) {
		log.error("未完成资金列表：beginIndex="+beginIndex+",endIndex:"+dtoSize+",toRiskDTOList="+dtoSize);
		List<TORiskDTO> saveRiskDtoList=toRiskDTOList.subList(beginIndex, dtoSize);
		try{
			log.error("保存game fund queue,saveRiskDtoList size:"+(saveRiskDtoList!=null?saveRiskDtoList.size():0));
			saveGameFundQueue2(ctx, saveRiskDtoList,getStackTrace(e),requestType); 
		}catch(Exception ee){
			log.error("保存game fund queue失败.资金内容进入日志",ee); 
			this.logRiskDtoList(saveRiskDtoList); 
		}
		saveRiskDtoList=null;
	}
	private String getStackTrace(Throwable t)
    {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        pw.flush();
        sw.flush(); 
        String stackTrace=sw.toString();
        return stackTrace.length()>1000?stackTrace.substring(0, 1000):stackTrace;
    }
	private void logRiskDtoList(List<TORiskDTO> riskDtoList){
		if(riskDtoList!=null && riskDtoList.size()>0){ 
			StringBuffer sb=new StringBuffer();
			sb.append("\n\r");
			for(TORiskDTO riskDto:riskDtoList){
				sb.append(riskDto.toString()).append("\n\r");
			}
			log.error(sb.toString());
		}
	}

	private void saveGameFundQueue(GameContext ctx,List<TORiskDTO> tempRiskDtoList,String excepStacks) throws Exception {
		if(tempRiskDtoList!=null && tempRiskDtoList.size()>0){			
			List<GameFundQueue> fundQueueList=new ArrayList<GameFundQueue>(); 
			for(TORiskDTO riskDto:tempRiskDtoList){
				GameFundQueue fundQueue=new GameFundQueue();  
				fundQueue.setLotteryId(riskDto.getLotteryid());
				fundQueue.setIssueCode(riskDto.getIssueCode());
				fundQueue.setFundType(riskDto.getType());
				fundQueue.setStatus(GameFundQueue.Status.UN_EXEC.getValue());
				fundQueue.setUserIdList(riskDto.getUserid());
				fundQueue.setAmountList(riskDto.getAmount());
				fundQueue.setOrderCodeList(riskDto.getOrderCodeList());
				fundQueue.setPlanCodeList(riskDto.getPlanCodeList());
				fundQueue.setNote(riskDto.getNote());
				fundQueue.setRemark(excepStacks);
				fundQueueList.add(fundQueue);
			}
			this.gameFundQueueService.saveBatch(ctx, fundQueueList);
		} 
	}
	private void saveGameFundQueue2(GameContext ctx,
			List<TORiskDTO> tempRiskDtoList,String excepStacks,int requestType) throws Exception {
		if(tempRiskDtoList!=null && tempRiskDtoList.size()>0){			
			List<GameFundQueue> fundQueueList=new ArrayList<GameFundQueue>(); 
			for(TORiskDTO riskDto:tempRiskDtoList){
				GameFundQueue fundQueue=new GameFundQueue();  
				fundQueue.setLotteryId(riskDto.getLotteryid());
				fundQueue.setIssueCode(riskDto.getIssueCode());
				fundQueue.setFundType(riskDto.getType());
				fundQueue.setStatus(GameFundQueue.Status.UN_EXEC.getValue());
				fundQueue.setUserIdList(riskDto.getUserid());
				fundQueue.setAmountList(riskDto.getAmount());
				fundQueue.setOrderCodeList(riskDto.getOrderCodeList());
				fundQueue.setPlanCodeList(riskDto.getPlanCodeList());
				fundQueue.setNote(riskDto.getNote());
				fundQueue.setRemark(excepStacks);
				fundQueueList.add(fundQueue);
			}
			this.gameFundQueueService.saveBatch2(ctx, fundQueueList);
		}
	}
	
	@Override
	@Deprecated
	public void reduceCoin(Long amount, Long lotteryId, Long issueCode, String orderCode, Integer type, Long userId)
			throws Exception {
		ReduceCoinRequest reduceCoinRequest = new ReduceCoinRequest();
		reduceCoinRequest.setAmount(amount);
		reduceCoinRequest.setLotteryid(lotteryId);
		reduceCoinRequest.setIssueCode(issueCode);
		reduceCoinRequest.setOrderCodeList(orderCode);
		reduceCoinRequest.setType(type);
		reduceCoinRequest.setUserid(userId);

		try {

			httpClient.invokeHttpWithoutResultType(serverPath + reduceCoinUrl, reduceCoinRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
		}

	}

	@Override
	@Deprecated
	public void freezeFundGameOrder(Long amount, Long issueCode, Long lotteryId, String orderCode, Integer type,
			Long userId) throws Exception {

		FreezeFundRequest freezeFundRequest = new FreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setOrderCodeList(orderCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);

		try {
			httpClient.invokeHttpWithoutResultType(serverPath + freezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("freezeFundGameOrder error:", e);
		}
	}

	@Override
	@Deprecated
	public void addCoin(Long amount, Long lotteryId, String orderCode, Integer type, Long userId, Long issueCode)
			throws Exception {

		//3001 本金返还加款（审核系统内部接口5001-5006接口生成摘要）3002 派发奖金

		AddCoinRequest addCoinRequest = new AddCoinRequest();
		addCoinRequest.setAmount(amount);
		addCoinRequest.setLotteryid(lotteryId);
		addCoinRequest.setOrderCodeList(orderCode);
		addCoinRequest.setType(type);
		addCoinRequest.setUserid(userId);
		addCoinRequest.setIssueCode(issueCode);

		try {

			httpClient.invokeHttpWithoutResultType(serverPath + addCoinUrl, addCoinRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
		}
	}

	@Override
	@Deprecated
	public void unFreezeFundGamePlan(Long amount, Long issueCode, Long lotteryId, String planCode, Integer type,
			Long userId) throws Exception {

		UnFreezeFundRequest freezeFundRequest = new UnFreezeFundRequest();
		freezeFundRequest.setAmount(amount);
		freezeFundRequest.setIssueCode(issueCode);
		freezeFundRequest.setLotteryid(lotteryId);
		freezeFundRequest.setPlanCodeList(planCode);
		freezeFundRequest.setType(type);
		freezeFundRequest.setUserid(userId);
		try {

			httpClient.invokeHttpWithoutResultType(serverPath + unFreezeUrl, freezeFundRequest);
		} catch (Exception e) {
			log.error("callFundService error:", e);
		}
	}

	/**
	 * 派奖
	 * 
	 * 1 解冻计划中该订单的金额(当是计划时)
	 * 2 当前订单进行投注扣款(普通方案产生的订单扣款5004与计划的订单扣款类型5009)
	 * 3 解冻该订单的返点
	 * 4 如果中奖派发奖金
	 *
	 */
	@Override
	public void distributeAward(List<TORiskDTO> toRiskDTOList) throws Exception {

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				//				Request<TORiskRequest> request = new Request<TORiskRequest>();
				//				TORiskRequest riskRequest = new TORiskRequest();
				//				riskRequest.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
				//				riskRequest.setToRiskDTOList(toRiskDTOList);
				//				RequestBody<TORiskRequest> body = new RequestBody<TORiskRequest>();
				//				body.setParam(riskRequest);
				//				
				//				request.setBody(body);
				/*request.setGameFundTypes(GameFundTypesUtils.GAME_DISTRIBUTE_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});*/
				gameRiskService.integration(toRiskDTOList);  
			} else {
				
				throw new RuntimeException("distributeAward is null");
			}

		} catch (Exception e) {
			
			log.error("distributeAward error:", e);
			throw new RuntimeException("ask distributeAward exception"+e.getMessage());
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask distributeAward exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}

	/**
	 * 投注扣款冻结
	 * 
	 * 1 冻结计划整个总金额(当是计划时,而且是计划的第一期时)产生一个TORiskDTO
	 * 2 计划执行产生的订单无需在进行冻结(当是计划时)
	 * 3 冻结订单金额(当是普通方案产生的订单)产生一个TORiskDTO
	 * 4 无论计划还是方案产生的订单,都需要冻结返点产生两个TORiskDTO
	 *
	 */
	@Override
	public void betAmountFreezer(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				/*request.setGameFundTypes(GameFundTypesUtils.GAME_BET_FREEZEN_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});*/
				gameRiskService.integration(toRiskDTOList);  
			} else {
				throw new RuntimeException("betAmountFreezer is null");
			}

		} catch (Exception e) {
			
			log.error("betAmountFreezer error:", e);
			throw new RuntimeException("ask betAmountFreezer exception"+e.getMessage());
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask betAmountFreezer exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}

	@Override
	public void cancelFee(List<TORiskDTO> toRiskDTOList) throws Exception {
		// TODO Auto-generated method stub

		TORiskRequest request = new TORiskRequest();
		Response<TORiskResponse> responce = null;
		try {
			if (null != toRiskDTOList) {
				/*request.setGameFundTypes(GameFundTypesUtils.GAME_CANCEL_BET_TYPE);
				request.setToRiskDTOList(toRiskDTOList);
				responce = httpClient.invokeHttp(serverPath + integrationUrl, request,
						new TypeReference<Response<TORiskResponse>>() {
						});*/
				gameRiskService.integration(toRiskDTOList);  
			} else {
				throw new RuntimeException("cancelFee is null");
			}

			
			
		} catch (Exception e) {			
			log.error("cancelFee error:", e);
			throw new RuntimeException("ask cancelFee exception"+e.getMessage());
		}
		
		if(responce !=null && responce.getBody().getResult().getResultStatus() !=0L){
			throw new RuntimeException("ask cancelFee exception"+responce.getBody().getResult().getExceptionMessage());
		}
	}
}
