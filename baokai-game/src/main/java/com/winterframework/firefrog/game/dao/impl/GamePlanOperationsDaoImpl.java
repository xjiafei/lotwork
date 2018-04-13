package com.winterframework.firefrog.game.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGamePlanOperationsDao;
import com.winterframework.firefrog.game.dao.vo.GamePlan;
import com.winterframework.firefrog.game.dao.vo.VOConverter;
import com.winterframework.firefrog.game.entity.GamePlanOperationsEntity;
import com.winterframework.firefrog.game.web.dto.DTOConvert;
import com.winterframework.firefrog.game.web.dto.GamePlanOperationsQueryDTO;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

/** 
* @ClassName: GamePlanOperationsDaoImpl 
* @Description: 追号记录运营后台Dao
* @author Alan
* @date 2013-10-12 上午10:19:39 
*  
*/
@Repository("gamePlanOperationsDaoImpl")
public class GamePlanOperationsDaoImpl extends BaseIbatis3Dao<GamePlan> implements IGamePlanOperationsDao {

	@Override
	public Page<GamePlanOperationsEntity> getGamePlanByPage(PageRequest<GamePlanOperationsQueryDTO> pageRequest) {
		GamePlanOperationsQueryDTO queryDTO = pageRequest.getSearchDo();
		Map<String, Object> queryParamMap = makeQueryParamMap(queryDTO);

		Number totalCount = (Number) sqlSessionTemplate.selectOne("getGamePlanTotalCount", queryParamMap);
		if (totalCount == null || totalCount.intValue() <= 0) {
			return new Page<GamePlanOperationsEntity>(0);
		}

		Page<GamePlanOperationsEntity> page = new Page<GamePlanOperationsEntity>(
				pageRequest.getPageNumber(), pageRequest.getPageSize(), totalCount.intValue());
		
		Map<String, Object> filters = new HashMap<String, Object>();
		filters.put("offset", page.getFirstResult());
		filters.put("pageSize", page.getPageSize());
		filters.put("lastRows", page.getFirstResult() + page.getPageSize());
		filters.put("sortColumns", pageRequest.getSortColumns());
		filters.putAll(queryParamMap);
		
		RowBounds rowBounds = new RowBounds(page.getFirstResult(), page.getPageSize());
		List<GamePlanOperationsEntity> planEntityList = sqlSessionTemplate.selectList("getGamePlansByCondition", filters, rowBounds);
		page.setResult(planEntityList);
		return page;
	}
	
	@Override
	public List<GamePlanOperationsEntity> getGamePlanList(GamePlanOperationsQueryDTO queryDTO) {
		List<GamePlanOperationsEntity> list = sqlSessionTemplate.selectList("getGamePlansByCondition", makeQueryParamMap(queryDTO));
		return list;
	}

	private Map<String, Object> makeQueryParamMap(GamePlanOperationsQueryDTO queryDTO){
		Map<String, Object> map = new HashMap<String, Object>();

		Long userid = queryDTO.getUserid();
		Date startCreateTime = null, endCreateTime = null;
		if(null != queryDTO.getQueryRequest().getStartCreateTime()){
			startCreateTime = new Date(queryDTO.getQueryRequest().getStartCreateTime());
		}
		if(null != queryDTO.getQueryRequest().getEndCreateTime()){
			endCreateTime = new Date(queryDTO.getQueryRequest().getEndCreateTime());
		}
		
		if(null != userid){
			map.put("userid", userid);
		}
		map.put("issueCode", queryDTO.getQueryRequest().getIssueCode());
		map.put("device", queryDTO.getQueryRequest().getDevice());
		if(null != queryDTO.getQueryRequest().getLotteryid() && queryDTO.getQueryRequest().getLotteryid() != 0){
			map.put("lotteryid", queryDTO.getQueryRequest().getLotteryid());
		}
		//此处还得根据命名规则区分出account或orderCode,暂时只用account
		String paramCode = queryDTO.getQueryRequest().getParamCode();
		if(null != paramCode){
			paramCode=paramCode.trim();
		}
		if(null != paramCode/* && (("D").equals(paramCode.substring(0, 1))||("Z").equals(paramCode.substring(0, 1))) && VOConverter.isNumeric(paramCode.substring(4, 14))*/){
			map.put("orderCode", paramCode);
			map.put("account", paramCode);
		}
		if(null != queryDTO.getQueryRequest().getContainSub()){
			map.put("containSub", queryDTO.getQueryRequest().getContainSub());
		}else{
			map.put("containSub", 0);
		}
		if(null != startCreateTime){
			map.put("startCreateTime", startCreateTime);
		}
		if(null != endCreateTime){
			map.put("endCreateTime", endCreateTime);
		}
		if(null != queryDTO.getQueryRequest().getStatus()){
			map.put("status", queryDTO.getQueryRequest().getStatus());
		}
		if(null != queryDTO.getQueryRequest().getStartWinsCount()){
			map.put("startWinsCount", queryDTO.getQueryRequest().getStartWinsCount());
		}
		if(null != queryDTO.getQueryRequest().getEndWinsCount()){
			map.put("endWinsCount", queryDTO.getQueryRequest().getEndWinsCount());
		}
		if(null != queryDTO.getQueryRequest().getStopMode()){
			map.put("stopMode", queryDTO.getQueryRequest().getStopMode());
		}
		if(null != queryDTO.getQueryRequest().getStopParams()){
			map.put("stopParams", queryDTO.getQueryRequest().getStopParams());
		}
		if(null != DTOConvert.convert2PlanSortType(queryDTO.getQueryRequest().getSortType())){
			map.put("sortColumns", DTOConvert.convert2PlanSortType(queryDTO.getQueryRequest().getSortType()));
		}		
		
		return map;
	}

	@Override
	public GamePlanOperationsEntity queryGamePlanOperationsDetail(Long planid) {
		GamePlanOperationsEntity entity = this.sqlSessionTemplate.selectOne("getGamePlanByPlanID", planid);
		return entity;
	}

}
