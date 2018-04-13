package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.game.dao.IGameBettypeStatusCheckDao;
import com.winterframework.firefrog.game.dao.IGameBettypeStatusDao;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatus;
import com.winterframework.firefrog.game.dao.vo.GameBettypeStatusCheck;
import com.winterframework.firefrog.game.entity.SellingStatus;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.service.IBetMethodSellingStatusService;
import com.winterframework.firefrog.game.web.dto.AwardBonusStruc;

/** 
* @ClassName: BetMethodSellingStatusServiceImpl 
* @Description: 投注方式销售状态Service实现类 
* @author Denny 
* @date 2013-8-29 上午12:56:27 
*  
*/
@Service("betMethodSellingStatusServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class BetMethodSellingStatusServiceImpl implements IBetMethodSellingStatusService {
	
	@Resource(name = "gameBettypeStatusDaoImpl")
	private IGameBettypeStatusDao gameBettypeStatusDaoImpl;
	
	@Resource(name = "gameBettypeStatusCheckDaoImpl")
	private IGameBettypeStatusCheckDao gameBettypeStatusCheckDaoImpl;

	@Override
	public List<SellingStatus> queryBetMethodSellingStatus(long lotteryid) throws Exception {
		List<SellingStatus> resultList = new ArrayList<SellingStatus>();
		
		//1、查询销售状态check信息
		List<GameBettypeStatusCheck> gameBettypeStatusCheckList = gameBettypeStatusCheckDaoImpl.getAllByLotteryId(lotteryid);
		//2、查询销售状态信息
		List<GameBettypeStatus> gameBettypeStatusList = gameBettypeStatusDaoImpl.getAllByLotteryId(lotteryid);
		
		Collections.sort(gameBettypeStatusCheckList, new Comparator<GameBettypeStatusCheck>() {
			@Override
			public int compare(GameBettypeStatusCheck o1, GameBettypeStatusCheck o2) {
				Double o1GroupCode = o1.getGameGroupCode().doubleValue();
				Double o2GroupCode = o2.getGameGroupCode().doubleValue();
				if (o1.getGameGroupCode() == 33) {
					o1GroupCode = 12.5d;
				}
				if (o2.getGameGroupCode() == 33) {
					o2GroupCode = 12.5d;
				}
				int result = o1GroupCode.compareTo(o2GroupCode);
				if (result == 0) {
					result = o1.getGameSetCode().compareTo(o2.getGameSetCode());
				}
				if (result == 0) {
					result = o1.getBetMethodCode().compareTo(o2.getBetMethodCode());
				}
				return result;
			}

		});
		
		Collections.sort(gameBettypeStatusList, new Comparator<GameBettypeStatus>() {
			@Override
			public int compare(GameBettypeStatus o1, GameBettypeStatus o2) {
				Double o1GroupCode = o1.getGameGroupCode().doubleValue();
				Double o2GroupCode = o2.getGameGroupCode().doubleValue();
				if (o1.getGameGroupCode() == 33) {
					o1GroupCode = 12.5d;
				}
				if (o2.getGameGroupCode() == 33) {
					o2GroupCode = 12.5d;
				}
				int result = o1GroupCode.compareTo(o2GroupCode);
				if (result == 0) {
					result = o1.getGameSetCode().compareTo(o2.getGameSetCode());
				}
				if (result == 0) {
					result = o1.getBetMethodCode().compareTo(o2.getBetMethodCode());
				}
				return result;
			}

		});
		
		
		//3、判断check表中是否有数据，若无，则显示主表中所有数据，若有，则整合两者数据，以check为先
		if(gameBettypeStatusCheckList.size() == 0){
			for (GameBettypeStatus gbs : gameBettypeStatusList) {
				SellingStatus ss = VOConvert.gameBettypeStatus2SellingStatus(gbs);
				resultList.add(ss);
			}
		}else{
			for(GameBettypeStatus gbs : gameBettypeStatusList){
				SellingStatus ss = null;
				//4、若有匹配数据则显示check数据，若无则显示主表数据
				for(GameBettypeStatusCheck gbsc : gameBettypeStatusCheckList){
					if(gbsc.getGameGroupCode()==gbs.getGameGroupCode()
							&& gbsc.getGameSetCode()==gbs.getGameSetCode()
							&& gbsc.getBetMethodCode()==gbs.getBetMethodCode()){
						ss = VOConvert.gameBettypeStatusCheck2SellingStatus(gbsc);
						//若发现check表数据有更改，则标识为有更改
						if(gbsc.getStatus() != gbs.getStatus()){
							ss.setStatus_changed(true);
							ss.setStatus(gbs.getStatus());
						}
						break;
					}
				}
				if(ss == null){
					ss = VOConvert.gameBettypeStatus2SellingStatus(gbs);
				}
				resultList.add(ss);
			}
		}
		
		return resultList;
	}

	@Override
	public void modifyBetMethodSellingStatus(List<SellingStatus> sellingStatusList, Long lotteryid) throws Exception {
		GameBettypeStatusCheck gbcs = new GameBettypeStatusCheck();
		
		for(SellingStatus ss : sellingStatusList) {
			gbcs = VOConvert.sellingStatus2GameBettypeStatusCheck(ss);
			gbcs.setCreateTime(new Date());
			gbcs.setCheckStatus(3);
			gbcs.setUpdateTime(new Date());
			
			//查询check表中是否有此记录，没有就insert，有则update
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("lotteryid", gbcs.getLotteryid());
			map.put("gameGroupCode", gbcs.getGameGroupCode());
			map.put("gameSetCode", gbcs.getGameSetCode());
			map.put("betMethodCode", gbcs.getBetMethodCode());
			GameBettypeStatusCheck check = gameBettypeStatusCheckDaoImpl.queryGameBettypeStatusCheckByMap(map);
			
			if(check == null){
				gameBettypeStatusCheckDaoImpl.insertToCheck(gbcs);
			}else{
				gameBettypeStatusCheckDaoImpl.updateToCheck(gbcs);
			}
			
		}
	}

	@Override
	public void checkBetMethodSellingStatus(Long lotteryid, Long auditType) {
		gameBettypeStatusCheckDaoImpl.updateToPublish(lotteryid, auditType);
		if(auditType!=1){
			this.cancelModifySellingStatus(lotteryid);
		}
	}
	
	private void cancelModifySellingStatus(Long lotteryid){
		gameBettypeStatusCheckDaoImpl.deleteAllByLotteryId(lotteryid);
	}

	@Override
	public void publishBetMethodSellingStatus(Long lotteryid, Long publishType) {
		//1发布通过，2为发布不通过
		if(publishType == 1) {
			List<GameBettypeStatusCheck> gameBettypeStatusCheckList = gameBettypeStatusCheckDaoImpl.getAllByLotteryId(lotteryid);
			GameBettypeStatus gbs = new GameBettypeStatus();
			for (GameBettypeStatusCheck gbsc : gameBettypeStatusCheckList) {
				 gbs = VOConvert.gameBettypeStatusCheck2GameBettypeStatus(gbsc);
				 gbs.setUpdateTime(new Date());
				 gameBettypeStatusDaoImpl.updateForPublish(gbs);
			}
			
			gameBettypeStatusCheckDaoImpl.deleteAllByLotteryId(lotteryid);
		}else{
			gameBettypeStatusCheckDaoImpl.updateBetttypeStatusCheckToNotPublished(lotteryid);
			cancelModifySellingStatus(lotteryid);
		}
		
	}

	@Override
	public List<GameBettypeStatus> queryValidBetMethods(long lotteryid) throws Exception {
		return gameBettypeStatusDaoImpl.getValidBetMethodByLotteryId(lotteryid);
	}
	
	
	

}
