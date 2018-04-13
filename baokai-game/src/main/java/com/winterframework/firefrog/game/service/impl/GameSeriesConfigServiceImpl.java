package com.winterframework.firefrog.game.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winterframework.firefrog.game.dao.IGameIssueRuleCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigCheckDao;
import com.winterframework.firefrog.game.dao.IGameSeriesConfigDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameSeriesConfig;
import com.winterframework.firefrog.game.dao.vo.GameSeriesConfigCheck;
import com.winterframework.firefrog.game.entity.GameIssueRuleEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity;
import com.winterframework.firefrog.game.entity.GameSeriesConfigEntity.StatusType;
import com.winterframework.firefrog.game.service.IGameSeriesConfigService;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigDTO;
import com.winterframework.firefrog.game.web.dto.GameSeriesConfigVedioSourceRequest;
import com.winterframework.firefrog.game.web.dto.VedioStruc;
import com.winterframework.modules.page.PageRequest;

/**
 * 
* @ClassName: GameSeriesConfigServiceImpl 
* @Description: 运营参数服务
* @author Richard & Alan
* @date 2013-9-17 下午5:42:12 
*
 */
@Service("gameSeriesConfigServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameSeriesConfigServiceImpl implements IGameSeriesConfigService {

	@Resource(name = "gameSeriesConfigCheckDaoImpl")
	private IGameSeriesConfigCheckDao gameSeriesConfigCheckDao;

	@Resource(name = "gameSeriesConfigDaoImpl")
	private IGameSeriesConfigDao gameSeriesConfigDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Resource(name = "gameIssueRuleCheckDaoImpl")
	private IGameIssueRuleCheckDao gameIssueRuleCheckDao;

	@Override
	public GameSeriesConfigEntity queryGameSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		return getGameSeriesConfigByLotteryId(lotteryId);
	}

	@Override
	public void editGameSeriesConfig(GameSeriesConfigDTO dto) throws Exception {

		GameSeriesConfigEntity configEntity = gameSeriesConfigCheckDao.getGameSeriesConfigByLotteryId(dto
				.getLotteryid());
		boolean isUpdata = true;
		if (null == configEntity) {
			isUpdata = false;
			configEntity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(dto.getLotteryid());
		}

		GameSeriesConfigCheck check = com.winterframework.firefrog.game.dao.vo.VOConverter
				.gameSeriesConfigEntity2GameSeriesConfigCheck(configEntity);

		check.setBackoutRatio(dto.getBackoutRatio());
		check.setBackoutStartFee(dto.getBackoutStartFee());
		check.setEmail(dto.getEmail());
		check.setIssuewarnUserBackoutTime(dto.getIssuewarnUserBackoutTime());
		check.setIssuewarnAheadOpenaward(dto.getIssuewarnAheadOpenaward());
		check.setIssuewarnBackoutTime(dto.getIssuewarnBackoutTime());
		check.setIssuewarnDelayOpenaward(dto.getIssuewarnDelayOpenaward());
		check.setIssuewarnExceptionTime(dto.getIssuewarnExceptionTime());
		check.setIssuewarnNotOpenaward(dto.getIssuewarnNotOpenaward());
		check.setStatus(new Long(StatusType.Pending.getValue()));
		check.setUpdateTime(new Date());
		if (isUpdata) {

			check.setId(configEntity.getId());
			gameSeriesConfigCheckDao.update(check);
		} else {

			gameSeriesConfigCheckDao.insert(check);
		}
		gameSeriesDao.updateGameSeriesChangeStatus(dto.getLotteryid(), 7, 3);
		gameSeriesDao.updateLastModifyDate(dto.getLotteryid());
	}

	@Override
	public void auditGameSeriesConfig(Long lotteryId, Long auditType) throws Exception {
		//查询config表中是否存在该参数配置信息
		//		GameSeriesConfigEntity entity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
		//查询lotteryId对应的参数配置信息
		GameSeriesConfigEntity checkEntity = gameSeriesConfigCheckDao.getGameSeriesConfigByLotteryId(lotteryId);
		if (auditType == 1) {

			/*if (null == entity) {
				//不存在则config表中新增一条参数配置记录
				GameSeriesConfig config = convert2GameSeriesConfig(checkEntity, false);
				gameSeriesConfigDao.insert(config);
			} else {
				//存在则覆盖config中参数信息
				GameSeriesConfig config = convert2GameSeriesConfig(checkEntity, true);
				config.setId(entity.getId());
				gameSeriesConfigDao.update(config);
			}

			//发布成功之后删除check中信息
			gameSeriesConfigCheckDao.deleteGameSeriesConfigCheck(lotteryId);*/
			checkEntity.setStatus(StatusType.Released);
			gameSeriesConfigCheckDao.updateGameSeriesConfigCheck(checkEntity);
			gameSeriesDao.updateGameSeriesChangeStatus(lotteryId, 7, auditType == 1 ? 4 : 5);
		} else {
			checkEntity.setStatus(StatusType.Unapprove);
			gameSeriesConfigCheckDao.updateGameSeriesConfigCheck(checkEntity);
			this.cancelModify(lotteryId);
		}
		
	}

	private void cancelModify(Long lotteryId) throws Exception {
		gameSeriesConfigCheckDao.deleteGameSeriesConfigCheck(lotteryId);
		gameSeriesDao.updateGameSeriesChangeStatus(lotteryId, 7, 1);
	}

	@Override
	public void releaseGameSeriesConfig(Long lotteryId, Long publishType) throws Exception {
		//查询config表中是否存在该参数配置信息
		GameSeriesConfigEntity entity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
		//查询lotteryId对应的参数配置信息
		GameSeriesConfigEntity checkEntity = gameSeriesConfigCheckDao.getGameSeriesConfigByLotteryId(lotteryId);

		if (publishType == 1) {

			if (null == entity) {
				//不存在则config表中新增一条参数配置记录
				GameSeriesConfig config = convert2GameSeriesConfig(checkEntity, false);
				gameSeriesConfigDao.insert(config);
			} else {
				//存在则覆盖config中参数信息
				GameSeriesConfig config = convert2GameSeriesConfig(checkEntity, true);
				config.setId(entity.getId());
				gameSeriesConfigDao.update(config);
			}

			//发布成功之后删除check中信息
			gameSeriesConfigCheckDao.deleteGameSeriesConfigCheck(lotteryId);
			gameSeriesDao.updateGameSeriesChangeStatus(lotteryId, 7, publishType == 1 ? 1 : 6);
		} else {
			checkEntity.setStatus(StatusType.PublishFailed);
			gameSeriesConfigCheckDao.updateGameSeriesConfigCheck(checkEntity);
			cancelModify(lotteryId);
		}

	}

	private GameSeriesConfigEntity getGameSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		GameSeriesConfigEntity entity = gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);

		GameSeriesConfigEntity check = gameSeriesConfigCheckDao.getGameSeriesConfigByLotteryId(lotteryId);

		if (check == null) {

			return entity;

		} else {

			if (!entity.getBackoutStartFee().equals(check.getBackoutStartFee())) {
				check.setBackoutStartFee_bak(entity.getBackoutStartFee());
			}

			if (entity.getEmail() != null) {
				if (!entity.getEmail().equals(check.getEmail())) {
					check.setEmail_bak(entity.getEmail());
				}
			}

			if (!entity.getBackoutRatio().equals(check.getBackoutRatio())) {
				check.setBackoutRatio_bak(entity.getBackoutRatio());
			}

			/*if(!entity.getIssuewarnNotOpenaward().equals(check.getIssuewarnNotOpenaward())){
				check.setIssuewarnNotOpenaward_bak(entity.getIssuewarnNotOpenaward());
			}
			
			if(!entity.getIssuewarnAheadOpenaward().equals(check.getIssuewarnAheadOpenaward())){
				check.setIssuewarnAheadOpenaward_bak(entity.getIssuewarnAheadOpenaward());
			}
			
			if(!entity.getIssuewarnDelayOpenaward().equals(check.getIssuewarnDelayOpenaward())){
				check.setIssuewarnDelayOpenaward_bak(entity.getIssuewarnDelayOpenaward());
			}*/

			if (!entity.getIssuewarnBackoutTime().equals(check.getIssuewarnBackoutTime())) {
				check.setIssuewarnBackoutTime_bak(entity.getIssuewarnBackoutTime());
			}

			if (entity.getIssuewarnUserBackoutTime() != null) {
				if (!entity.getIssuewarnUserBackoutTime().equals(check.getIssuewarnUserBackoutTime())) {
					check.setIssuewarnUserBackoutTime_bak(entity.getIssuewarnUserBackoutTime());
				}
			}

			if (!entity.getIssuewarnExceptionTime().equals(check.getIssuewarnExceptionTime())) {
				check.setIssuewarnExceptionTime_bak(entity.getIssuewarnExceptionTime());
			}
			check.setVedioStrucs(entity.getVedioStrucs());

			return check;
		}

	}

	private GameSeriesConfig convert2GameSeriesConfig(GameSeriesConfigEntity checkEntity, boolean isUpdate) {
		GameSeriesConfig config = new GameSeriesConfig();
		config.setLotteryid(checkEntity.getLottery().getLotteryId());
		config.setBackoutRatio(checkEntity.getBackoutRatio());
		config.setBackoutStartFee(checkEntity.getBackoutStartFee());
		config.setIssuewarnAheadOpenaward(checkEntity.getIssuewarnAheadOpenaward());
		config.setIssuewarnBackoutTime(checkEntity.getIssuewarnBackoutTime());
		config.setIssuewarnDelayOpenaward(checkEntity.getIssuewarnDelayOpenaward());
		config.setIssuewarnExceptionTime(checkEntity.getIssuewarnExceptionTime());
		config.setIssuewarnNotOpenaward(checkEntity.getIssuewarnNotOpenaward());
		config.setCreateTime(isUpdate ? checkEntity.getCreateTime() : new Date());
		config.setUpdateTime(isUpdate ? new Date() : checkEntity.getUpdateTime());
		config.setEmail(checkEntity.getEmail());
		config.setIssuewarnUserBackoutTime(checkEntity.getIssuewarnUserBackoutTime());
		return config;
	}

	@Override
	public List<GameSeries> getAllGameSeries(Long lotteryId, Integer status) throws Exception {

		PageRequest<GameSeries> page = new PageRequest<GameSeries>();

		Map<String, Object> map = new HashMap<String, Object>();
		page.setPageSize(1000);
		map.put("lotteryid", lotteryId);
		map.put("status", status);
		map.put("sortColumns", "LOTTERYID");
		page.setFilters(map);

		page.setSortColumns("VIEW_ORDER");
		List<GameSeries> list = gameSeriesDao.getAllByPage(page).getResult();
		for (GameSeries gameSeries : list) {
			List<GameIssueRuleEntity> rules = gameIssueRuleCheckDao.queryGameIssueRuleCheckByLotteryIdAndRuleId(
					gameSeries.getLotteryid(), null);

			if (rules == null && rules.isEmpty()) {
				continue;
			} else {
				for (GameIssueRuleEntity rule : rules) {
					if (rule.getStatus() == 4) {
						StringBuffer changeStatus = gameSeries.getChangeStatus() == null ? new StringBuffer("11111111")
								: new StringBuffer(String.valueOf(gameSeries.getChangeStatus()));
						changeStatus = changeStatus.replace(0, 1, "3");
						gameSeries.setChangeStatus(Long.valueOf(changeStatus.toString()));
						break;
					}
				}
			}
		}

		return list;
	}

	@Override
	public GameSeriesConfigEntity getSeriesConfigByLotteryId(Long lotteryId) throws Exception {
		return gameSeriesConfigDao.getGameSeriesConfigByLotteryId(lotteryId);
	}

	@Override
	public void vedioSourceConfig(GameSeriesConfigVedioSourceRequest request)
			throws Exception {
		String type=request.getType();
		Long lotteryId=request.getLotteryId();
		String name=request.getName();
		String url=request.getUrl();
		Long id=request.getId();
		Integer status=request.getStatus();
		Map<String, Object> map = new HashMap<String, Object>();
		GameSeriesConfigEntity gce=getSeriesConfigByLotteryId(lotteryId);
		List<VedioStruc> vedioStrucDatabase=gce.getVedioStrucs();
		if(type.equals("add")){
			List<VedioStruc> newvedioStruc=new ArrayList<VedioStruc>();
			VedioStruc vs=new VedioStruc();
			if(vedioStrucDatabase!=null&&!vedioStrucDatabase.isEmpty()){
				newvedioStruc=new ArrayList(vedioStrucDatabase);
			    for(int i=0;i<vedioStrucDatabase.size();i++){
			    	if(i==vedioStrucDatabase.size()-1){
			    		vs.setId(vedioStrucDatabase.get(i).getId()+1);
						vs.setName(name);
						vs.setStatus(status);
						vs.setUrl(url);
			    	}
			    }
				
			}else{
				vs.setId(1L);
				vs.setName(name);
				vs.setStatus(status);
				vs.setUrl(url);
			}
			newvedioStruc.add(vs);
			ObjectMapper mapper=new ObjectMapper();
			map.put("lotteryId", lotteryId);
			map.put("videoStruc", mapper.writeValueAsString(newvedioStruc));
			gameSeriesConfigDao.updateVideoStruc(map);
			
		}else if(type.equals("remove")){
			List<VedioStruc> newvedioStruc=new ArrayList<VedioStruc>();
			if(vedioStrucDatabase!=null&&!vedioStrucDatabase.isEmpty()){
			    for(int i=0;i<vedioStrucDatabase.size();i++){
			    	if(vedioStrucDatabase.get(i).getId()!=id.longValue()){
			    		newvedioStruc.add(vedioStrucDatabase.get(i));
			    	}
			    }
			}
			ObjectMapper mapper=new ObjectMapper();
			map.put("lotteryId", lotteryId);
			map.put("videoStruc", mapper.writeValueAsString(newvedioStruc));
			gameSeriesConfigDao.updateVideoStruc(map);
			
		}else if(type.equals("edit")){
			List<VedioStruc> newvedioStruc=new ArrayList<VedioStruc>();
			VedioStruc vs=new VedioStruc();
			if(vedioStrucDatabase!=null&&!vedioStrucDatabase.isEmpty()){
			    for(int i=0;i<vedioStrucDatabase.size();i++){
			    	if(vedioStrucDatabase.get(i).getId()==id.longValue()){
			    		vs.setId(id);
						vs.setName(name);
						vs.setStatus(status);
						vs.setUrl(url);
						newvedioStruc.add(vs);
			    	}else{
			    		newvedioStruc.add(vedioStrucDatabase.get(i));
					}
			    	
			    }
			}
		
			ObjectMapper mapper=new ObjectMapper();
			map.put("lotteryId", lotteryId);
			map.put("videoStruc", mapper.writeValueAsString(newvedioStruc));
			gameSeriesConfigDao.updateVideoStruc(map);
			
			
		}
		
		
		
	}

}
