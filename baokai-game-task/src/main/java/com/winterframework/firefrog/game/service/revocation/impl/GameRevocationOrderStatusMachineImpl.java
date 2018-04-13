package com.winterframework.firefrog.game.service.revocation.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.GameContext;
import com.winterframework.firefrog.common.util.ProcessResult;
import com.winterframework.firefrog.game.dao.vo.GameOrder;
import com.winterframework.firefrog.game.exception.GameRevocationException;
import com.winterframework.firefrog.game.service.revocation.IGameRevocationOrderService;
import com.winterframework.firefrog.game.web.dto.TORiskDTO;

@Service("gameRevocationOrderStatusMachineImpl")
@Transactional
public class GameRevocationOrderStatusMachineImpl implements IGameRevocationOrderService, ApplicationContextAware {

	@Resource(name = "revocationOrderMap")
	private HashMap<String, String> revocationOrderMap;

	@Override
	public List<TORiskDTO> doRevocation(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			return service.doRevocation(result,order);
		} else {
			return null;
		}

	}

	@Override
	public void doRevocationAndAskRisk(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			service.doRevocationAndAskRisk(result,order);
		}
	}

	@Override
	public List<TORiskDTO> doRevocationToBeforeDraw(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			return service.doRevocationToBeforeDraw(result,order);
		} else {
			return null;
		}
	}
	@Override
	public List<TORiskDTO> doRevocationToRedraw(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			return service.doRevocationToRedraw(result,order);
		} else {
			return null;
		}
	}
	public IGameRevocationOrderService getRevocationOrderService(Integer orderStatus) {
		if (revocationOrderMap.get(orderStatus + "") != null) {
			return (IGameRevocationOrderService) context.getBean(revocationOrderMap.get(orderStatus + ""));
		} else if(orderStatus == 4 || orderStatus == 6 ){
			//数据已撤销 或 归档 不执行
			return null;
		} else {
			throw new GameRevocationException("订单状态"+orderStatus+"没有撤销处理执行类");
		}

	}

	@Override
	public List<TORiskDTO> doRevocationNotAskPlan(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			return service.doRevocationNotAskPlan(result,order);
		} else {
			return null;
		}
	}

	@Override
	public void doRevocationNotAskPlanAskRisk(ProcessResult result,GameOrder order) throws Exception{
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			service.doRevocationNotAskPlanAskRisk(result,order);
		}
	}
	@Override
	public void doRevocationNotAskPlanAskRisk(GameContext ctx,
			ProcessResult result, GameOrder order) throws Exception {
		IGameRevocationOrderService service = getRevocationOrderService(order.getStatus());
		if (service != null) {
			service.doRevocationNotAskPlanAskRisk(ctx,result,order);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		context = contex;
	}

	private ApplicationContext context;

}
