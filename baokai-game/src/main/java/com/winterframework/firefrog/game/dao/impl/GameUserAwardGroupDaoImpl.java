package com.winterframework.firefrog.game.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBetAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.exception.NoChooseBetAwardException;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.orm.dal.ibatis3.BaseIbatis3Dao;

@Repository("gameUserAwardGroupDaoImpl")
public class GameUserAwardGroupDaoImpl extends BaseIbatis3Dao<GameUserAwardGroup> implements IGameUserAwardGroupDao {

	@Override
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserId(Long userid) {
		return this.sqlSessionTemplate.selectList("getGameUserAwardGroupByUserId", userid);
	}

	@Override
	public List<GameBetAwardGroup> getGameBetAwardGroupByUserId(Long userid) {
		return this.sqlSessionTemplate.selectList("getGameBetAwardGroupByUserId", userid);
	}

	@Override
	public List<GameBetAwardGroup> getGameBetAwardGroupByUserId(Long userid, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryid);
		map.put("userid", userid);
		return this.sqlSessionTemplate.selectList("getGameBetAwardGroupByUserIdAndLotteryid", map);
	}

	@Override
	public void updateRet(GameUserAwardGroup guag) {
		this.sqlSessionTemplate.update("updateRet", guag);
	}

	@Override
	public UserAwardGroupEntity selectUserAwardGroup(Long userId, Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryId", lotteryId);
		map.put("userId", userId);
		List<GameBetAwardGroup> groups=getGameBetAwardGroupByUserId(userId,lotteryId);
		GameUserAwardGroup guag = this.sqlSessionTemplate.selectOne("getGameUserAwardGroupByUserIdAndLotteryId", map);
		if(groups!=null&&guag==null){
			throw new NoChooseBetAwardException();
		}else if(guag==null){
			throw new UserGameAwardConfigErrorException();
		}
		return VOConvert.gameUserAwardGroup2UserAwardGroupEntity(guag);
	}

	@Override
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserIdAndBetType(long userid, int betType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		if (betType == 1) {
			map.put("betType", betType);
		}

		return this.sqlSessionTemplate.selectList("getGameUserAwardGroupByUserIdAndBetType", map);
	}
	
	@Override
	public List<GameUserAwardGroup> getAllByLotterySeriesCodeAndUserIdAndAwardName(Integer lotterySeriesCode,
			Long userid, String awardName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotterySeriesCode", lotterySeriesCode);
		map.put("userid", userid);
		map.put("awardName", awardName);
		return this.sqlSessionTemplate.selectList("getAllByLotterySeriesCodeAndUserIdAndAwardName", map);
	}

	@Override
	public void updateUserAwardGroup(GameUserAwardGroup userAwardGroup) {
		this.sqlSessionTemplate.update("updateUserAwardGroup", userAwardGroup);
	}

	@Override
	public List<GameUserAwardGroup> getAwardByUserIdAndLryIdAndSysAwardId(List<Long> userIds, Long lotteryId,
			Long sysAwardGroupId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lotteryid", lotteryId);
		map.put("sysAwardGroupId", sysAwardGroupId);
		map.put("userIds", userIds);
		List<GameUserAwardGroup> guag = this.sqlSessionTemplate
				.selectList("getAwardByUserIdAndLryIdAndSysAwardId", map);
		return guag;
	}

	@Override
	public List<GameUserAwardGroup> getGameUserAwardGroupByUserIdAndLotteryId(long userid, String lotteryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("lotteryid", lotteryid);
		return this.sqlSessionTemplate.selectList("getGameUserAwardGroupByUserIdAndLotteryIds", map);
	}
	
	
	@Override
	public List<GameUserAwardGroup> getLotteryGameUserAwardGroupByUserIdAndLotteryId(Long userid, Long lotteryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("lotteryid", lotteryid);
		return this.sqlSessionTemplate.selectList("getLotteryGameUserAwardGroupByUserIdAndLotteryId", map);
	}
	
	@Override
	public List<GameAwardGroup> queryUserAwardRet(Long userid, Long lotteryid) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("lotteryid", lotteryid);
		return this.sqlSessionTemplate.selectList("queryUserAwardRet", map);
	}

	@Override
	public Map<String, BigDecimal> getUserAwardGroupDirPoint(Long sysAwardGroupId, Long userAwardGroupIds,
			Long lotteryId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryId);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupDirPoint", map);
	}

	@Override
	public Map<String, BigDecimal> getUserAwardGroupThreeOnePoint(Long sysAwardGroupId,
			Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupId);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupThreeOnePoint", map);
	}
	@Override
	public Map<String, BigDecimal> getUserAwardGroupSuperPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupSuperPoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupYearPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupYearPoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupColorPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupColorPoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupSbThreeOnePoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupSbThreeOnePoint", map);
	}

	@Override
	public Map<String, BigDecimal> getUserAwardGroupFlatcodePoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupFlatcodePoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupHalfwavePoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupHalfwavePoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupOneyearPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupOneyearPoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupNotinPoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupNotinPoint", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuein23Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuein23Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuein4Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuein4Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuein5Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuein5Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin23Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuenotin23Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin4Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuenotin4Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuenotin5Point(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuenotin5Point", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardGroupContinuecodePoint(
			Long sysAwardGroupIds, Long userAwardGroupIds, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userAwardGroupIds", userAwardGroupIds);
		map.put("lotteryId", lotteryid);
		map.put("sysAwardGroupId", sysAwardGroupIds);
		return this.sqlSessionTemplate.selectOne("getUserAwardGroupContinuecodePoint", map);
	}

	@Override
	public GameUserAwardGroup getGameUserAwardGroupByGroupId(
			Long id) {
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup.getGameUserAwardGroupByGroupId", id);
	}

	@Override
	public GameUserAwardGroup getUserAwardGroupByUserAndSysAwardId(Long sysGroupAwardId,
			Long userid) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysGroupAwardId", sysGroupAwardId);
		map.put("userid", userid);
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup.getUserAwardGroupByUserAndSysAwardId", map);
	}

	@Override
	public GameUserAwardGroup getDirectRetValueRange(Long awardGroupId, Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardGroupId", awardGroupId);
		map.put("userid", userId);
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup.getDirectRetValueRange", map);
	}

	@Override
	public GameUserAwardGroup getThreeoneRetValueRange(Long awardGroupId, Long userId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("awardGroupId", awardGroupId);
		map.put("userid", userId);
		return this.sqlSessionTemplate.selectOne("com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup.getThreeoneRetValueRange", map);
	}
	
	@Override
	public Map<String, BigDecimal> getUserAwardPoint(String gameBetType,
			Long userId, Long lotteryid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gameBetType", gameBetType);
		map.put("lotteryId", lotteryid);
		map.put("userId", userId);
		return this.sqlSessionTemplate.selectOne("getUserAwardPoint", map);
	}

}
