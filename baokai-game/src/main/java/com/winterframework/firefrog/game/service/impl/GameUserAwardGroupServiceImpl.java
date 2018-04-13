package com.winterframework.firefrog.game.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.game.dao.IGameAwardDao;
import com.winterframework.firefrog.game.dao.IGameAwardGroupDao;
import com.winterframework.firefrog.game.dao.IGameSeriesDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardDao;
import com.winterframework.firefrog.game.dao.IGameUserAwardGroupDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GameAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameBetAwardGroup;
import com.winterframework.firefrog.game.dao.vo.GameSeries;
import com.winterframework.firefrog.game.dao.vo.GameUserAward;
import com.winterframework.firefrog.game.dao.vo.GameUserAwardGroup;
import com.winterframework.firefrog.game.entity.GameAwardGroupEntity;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.UserAwardGroupEntity;
import com.winterframework.firefrog.game.entity.UserBetAwardGroupEntity;
import com.winterframework.firefrog.game.entity.VOConvert;
import com.winterframework.firefrog.game.enums.GameAwardBetType;
import com.winterframework.firefrog.game.enums.YesNo;
import com.winterframework.firefrog.game.exception.UserGameAwardConfigErrorException;
import com.winterframework.firefrog.game.exception.UserLevelIsExistErrorException;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameSeriesService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.IGameUserAwardService;
import com.winterframework.firefrog.game.service.assist.award.SpecialLotteryGameAwardFactory;
import com.winterframework.firefrog.game.service.assist.bet.ILotteryKeyFactor;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.util.LHCUtil;
import com.winterframework.firefrog.game.util.SuperPairUtil;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;

/** 
 * 用户奖金组实现类 
 * @author Denny 
 * @date 2013-9-17 下午5:05:20 
 */
@Service("gameUserAwardGroupServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class GameUserAwardGroupServiceImpl implements IGameUserAwardGroupService {

	private Logger log = LoggerFactory.getLogger(GameUserAwardGroupServiceImpl.class);

	@Resource(name = "gameUserAwardGroupDaoImpl")
	private IGameUserAwardGroupDao gameUserAwardGroupDao;

	@Resource(name = "gameUserAwardDaoImpl")
	private IGameUserAwardDao gameUserAwardDao;

	@Resource(name = "gameSeriesDaoImpl")
	private IGameSeriesDao gameSeriesDao;

	@Resource(name = "gameAwardGroupDaoImpl")
	private IGameAwardGroupDao gameAwardGroupDao;

	@Resource(name = "gameAwardDaoImpl")
	private IGameAwardDao gameAwardDao;
	@Resource(name = "userCustomerDaoImpl")
	private IUserCustomerDao customerDao;

	@Resource(name = "lotteryMap")
	private Map<String, String> awardMap;

	@Resource(name = "specialLotteryGameAwardFactory")
	private SpecialLotteryGameAwardFactory specialLotteryGameAwardFactory;
	@Resource(name = "gameUserAwardServiceImpl")
	private IGameUserAwardService gameUserAwardService;
	@Resource(name = "gameSeriesServiceImpl")
	private IGameSeriesService gameSeriesService;
	@Resource(name = "gameAwardGroupServiceImpl")
	private IGameAwardGroupService gameAwardGroupService;

	private final String MIN_DIRECT = "MIN_DIRECT";
	private final String MAX_DIRECT = "MAX_DIRECT";
	private final String MIN_THREEONE = "MIN_THREEONE";
	private final String MAX_THREEONE = "MAX_THREEONE";
	private final String MIN_SUPER = "MIN_SUPER";
	private final String MAX_SUPER = "MAX_SUPER";
	private final String MIN_LHC_YEAR = "MIN_LHC_YEAR";
	private final String MAX_LHC_YEAR = "MAX_LHC_YEAR";
	private final String MIN_LHC_COLOR = "MIN_LHC_COLOR";
	private final String MAX_LHC_COLOR = "MAX_LHC_COLOR";
	private final String MIN_SB_THREEONE_RET = "MIN_SB_THREEONE_RET";
	private final String MAX_SB_THREEONE_RET = "MAX_SB_THREEONE_RET";
	private final String MIN_LHC_FLATCODE = "MIN_LHC_FLATCODE";
	private final String MAX_LHC_FLATCODE = "MAX_LHC_FLATCODE";
	private final String MIN_LHC_HALFWAVE = "MIN_LHC_HALFWAVE";
	private final String MAX_LHC_HALFWAVE = "MAX_LHC_HALFWAVE";
	private final String MIN_LHC_ONEYEAR = "MIN_LHC_ONEYEAR";
	private final String MAX_LHC_ONEYEAR = "MAX_LHC_ONEYEAR";
	private final String MIN_LHC_NOTIN = "MIN_LHC_NOTIN";
	private final String MAX_LHC_NOTIN = "MAX_LHC_NOTIN";
	private final String MIN_LHC_CONTINUEIN23 = "MIN_LHC_CONTINUEIN23";
	private final String MAX_LHC_CONTINUEIN23 = "MAX_LHC_CONTINUEIN23";
	private final String MIN_LHC_CONTINUEIN4 = "MIN_LHC_CONTINUEIN4";
	private final String MAX_LHC_CONTINUEIN4 = "MAX_LHC_CONTINUEIN4";
	private final String MIN_LHC_CONTINUEIN5 = "MIN_LHC_CONTINUEIN5";
	private final String MAX_LHC_CONTINUEIN5 = "MAX_LHC_CONTINUEIN5";
	private final String MIN_LHC_CONTINUENOTIN23 = "MIN_LHC_CONTINUENOTIN23";
	private final String MAX_LHC_CONTINUENOTIN23 = "MAX_LHC_CONTINUENOTIN23";
	private final String MIN_LHC_CONTINUENOTIN4 = "MIN_LHC_CONTINUENOTIN4";
	private final String MAX_LHC_CONTINUENOTIN4 = "MAX_LHC_CONTINUENOTIN4";
	private final String MIN_LHC_CONTINUENOTIN5 = "MIN_LHC_CONTINUENOTIN5";
	private final String MAX_LHC_CONTINUENOTIN5 = "MAX_LHC_CONTINUENOTIN5";
	private final String MIN_LHC_CONTINUECODE = "MIN_LHC_CONTINUECODE";
	private final String MAX_LHC_CONTINUECODE = "MAX_LHC_CONTINUECODE";
	private final String SEPERATOR = "_";
	@Override
	public List<UserAwardGroupEntity> queryGameUserAwardGroup(long userid) throws Exception {
		List<UserAwardGroupEntity> userAwardGroupEntityList = new ArrayList<UserAwardGroupEntity>();
		List<GameUserAwardGroup> gameUserAwardGroupList = gameUserAwardGroupDao
				.getGameUserAwardGroupByUserIdAndBetType(userid, -1);
		//		Integer lotterySeriesCode;
		if (gameUserAwardGroupList != null && gameUserAwardGroupList.size() > 0) {
			UserAwardGroupEntity entity = null; 
			for (GameUserAwardGroup guag : gameUserAwardGroupList) {
				setUserAwardGroupRetValueRange(guag);
				entity = new UserAwardGroupEntity();
				entity = VOConvert.gameUserAwardGroup2UserAwardGroupEntity(guag);
				GameSeries gameSeries = gameSeriesDao.getByLotteyId(guag.getLotteryid());
				entity.setLotterySeriesCode(gameSeries.getLotterySeriesCode().longValue());
				entity.setLotterySeriesName(gameSeries.getLotterySeriesName());
				entity.setIsUsed(true);
				userAwardGroupEntityList.add(entity);
			}
		}
		return userAwardGroupEntityList;
	}

	/**
	 * 用户登录时奖金组查询 betType=1
	 */
	@Override
	public List<UserBetAwardGroupEntity> queryGameBetAwardGroup(long userid) throws Exception {
		List<UserBetAwardGroupEntity> userAwardGroupEntityList = new ArrayList<UserBetAwardGroupEntity>();
		List<GameBetAwardGroup> gameBetAwardGroupList = gameUserAwardGroupDao.getGameBetAwardGroupByUserId(userid);

		UserBetAwardGroupEntity entity = new UserBetAwardGroupEntity();

		for (GameBetAwardGroup guag : gameBetAwardGroupList) {

			entity = VOConvert.GameBetAwardGroup2UserBetAwardGroupEntity(guag);

			userAwardGroupEntityList.add(entity);

		}

		return userAwardGroupEntityList;
	}

	/**
	 * 当用户无奖金组时,返回可以设置的投注奖金组列表
	 * @param userid
	 * @param lotteryid
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<UserBetAwardGroupEntity> queryGameBetAwardGroup(long userid, long lotteryid) throws Exception {
		List<UserBetAwardGroupEntity> userAwardGroupEntityList = new ArrayList<UserBetAwardGroupEntity>();
		List<GameBetAwardGroup> gameBetAwardGroupList = gameUserAwardGroupDao.getGameBetAwardGroupByUserId(userid,
				lotteryid);
		
		UserBetAwardGroupEntity entity = new UserBetAwardGroupEntity();

		for (GameBetAwardGroup guag : gameBetAwardGroupList) {

			entity = VOConvert.GameBetAwardGroup2UserBetAwardGroupEntity(guag);

			userAwardGroupEntityList.add(entity);

		}

		return userAwardGroupEntityList;
	}

	@Override
	@Deprecated
	public void modifyGameUserAwardGroup(UserAwardGroupEntity e) {
		GameUserAwardGroup guag = VOConvert.userAwardGroupEntity2GameUserAwardGroup(e);
		guag.setUpdateTime(DateUtils.currentDate());
		gameUserAwardGroupDao.update(guag);

	}

	@Override
	public void batchModifyGameUserAwardGroup(List<UserAwardGroupEntity> newData) throws Exception {
		/**理论上不会有需要新增和删除的数据
		 * 新增：新数据有现有数据没有
		 * 修改：新数据有现有数据有
		 * 删除：新数据没有现有数据有
		 * 
		 * 判断用户：老用户，判断返点min=？=max
		 * 		min！=max	跳过不处理
		 *		min==max	保存award_user_group，删除award_user
		 *
		 */
		if (newData == null || newData.size() <= 0)
			return;

		Long userid = newData.get(0).getUserid();

		boolean isOldUser = false; //是否为老用户
		User user = customerDao.queryUserById(userid);
		if (user == null) {
			throw new Exception("user not exists.");
		}
		if (user != null && user.getUserProfile() != null && user.getUserProfile().getSource() != null) {
			if (user.getUserProfile().getSource().equals("3.0")) {
				isOldUser = true;
			}
		}
		List<UserAwardGroupEntity> existedData = queryGameUserAwardGroup(userid);

		List<UserAwardGroupEntity> toUpdateList = new ArrayList<UserAwardGroupEntity>();
		List<UserAwardGroupEntity> toAddList = new ArrayList<UserAwardGroupEntity>();
		List<UserAwardGroupEntity> toRemoveList = new ArrayList<UserAwardGroupEntity>();
		if (existedData != null && existedData.size() > 0) {
			for (UserAwardGroupEntity uage : existedData) {
				boolean hasUpdateData = false;
				for (UserAwardGroupEntity u : newData) {
					if (u.getLotteryId().equals(uage.getLotteryId())
							&& u.getSysGroupAward().getId().equals(uage.getSysGroupAward().getId())) {
						if (isOldUser) {
							//如果都没有修改（返点范围不允许修改），则无需处理
							if (u.getMaxDirectRet() != null && u.getDirectRet() != null
									&& u.getMaxDirectRet().longValue() != u.getDirectRet().longValue()
									&& u.getMaxThreeOneRet() != null && u.getThreeoneRet() != null
									&& u.getMaxThreeOneRet().longValue() != u.getThreeoneRet().longValue()
									&& u.getMaxSuperRet() != null && u.getSuperRet() != null
									&& u.getMaxSuperRet().longValue() != u.getSuperRet().longValue()
									&& u.getMaxLhcYear() != null && u.getLhcYear() != null
									&& u.getMaxLhcYear().longValue() != u.getLhcYear().longValue()			
									&& u.getMaxLhcColor() != null && u.getLhcColor() != null
									&& u.getMaxLhcColor().longValue() != u.getLhcColor().longValue()
									&& u.getMaxSbThreeoneRet() != null && u.getSbThreeoneRet() != null
									&& u.getMaxSbThreeoneRet().intValue() != u.getSbThreeoneRet().intValue()
									&& u.getMaxLhcFlatcode() != null && u.getLhcFlatcode() != null
									&& u.getMaxLhcFlatcode().longValue() != u.getLhcFlatcode().longValue()
									&& u.getMaxLhcHalfwave() != null && u.getLhcHalfwave() != null
									&& u.getMaxLhcHalfwave().longValue() != u.getLhcHalfwave().longValue()
									&& u.getMaxLhcOneyear() != null && u.getLhcOneyear() != null
									&& u.getMaxLhcOneyear().longValue() != u.getLhcOneyear().longValue()
									&& u.getMaxLhcNotin() != null && u.getLhcNotin() != null
									&& u.getMaxLhcNotin().longValue() != u.getLhcNotin().longValue()
									&& u.getMaxLhcContinuein23() != null && u.getLhcContinuein23() != null
									&& u.getMaxLhcContinuein23().longValue() != u.getLhcContinuein23().longValue()
									&& u.getMaxLhcContinuein4() != null && u.getLhcContinuein4() != null
									&& u.getMaxLhcContinuein4().longValue() != u.getLhcContinuein4().longValue()
									&& u.getMaxLhcContinuein5() != null && u.getLhcContinuein5() != null
									&& u.getMaxLhcContinuein5().longValue() != u.getLhcContinuein5().longValue()
									&& u.getMaxLhcContinuenotin23() != null && u.getLhcContinuenotin23() != null
									&& u.getMaxLhcContinuenotin23().longValue() != u.getLhcContinuenotin23().longValue()
									&& u.getMaxLhcContinuenotin4() != null && u.getLhcContinuenotin4() != null
									&& u.getMaxLhcContinuenotin4().longValue() != u.getLhcContinuenotin4().longValue()
									&& u.getMaxLhcContinuenotin5() != null && u.getLhcContinuenotin5() != null
									&& u.getMaxLhcContinuenotin5().longValue() != u.getLhcContinuenotin5().longValue()
									&& u.getMaxLhcContinuecode() != null && u.getLhcContinuecode() != null
									&& u.getMaxLhcContinuecode().longValue() != u.getLhcContinuecode().longValue()
									) {
								continue;
							}
							Long maxDirectRet = u.getMaxDirectRet() == null ? 0 : u.getMaxDirectRet();
							Long directRet = u.getDirectRet() == null ? 0 : u.getDirectRet();
							Long maxThreeOneRet = u.getMaxThreeOneRet() == null ? 0 : u.getMaxThreeOneRet();
							Long threeoneRet = u.getThreeoneRet() == null ? 0 : u.getThreeoneRet();
							Long maxSuperRet = u.getMaxSuperRet() == null ? 0 : u.getMaxSuperRet();
							Long superRet = u.getSuperRet() == null ? 0 : u.getSuperRet();
							Long maxSbThreeoneRet = u.getMaxSbThreeoneRet() == null ? 0 : u.getMaxSbThreeoneRet();
							Long sbThreeoneRet = u.getSbThreeoneRet() == null ? 0 : u.getSbThreeoneRet();
							//直选范围值相同，则更新直选值，同时更新award_user状态：已删除 
							if (maxDirectRet.intValue() != directRet.intValue()) {
								u.setDirectRet(uage.getDirectRet());
							} else {
								//已经是新值，不用set								
								//更新直选award_user状态：已删除								
								this.gameUserAwardService.deleteByAwardBetType(u.getLotteryId(), userid, GameAwardBetType.DIRECT.getValue());
							}
							//不定位范围值相同，则更新直选值，同时更新award_user状态：已删除 
							if (maxThreeOneRet.intValue() != threeoneRet.intValue()) {
								u.setThreeoneRet(uage.getThreeoneRet());
							} else {
								//更新不定位award_user状态：已删除
								this.gameUserAwardService.deleteByAwardBetType(u.getLotteryId(), userid, GameAwardBetType.THREE_ONE.getValue());
							}
							//超级对子范围值相同，则更新直选值，同时更新award_user状态：已删除 
							if (maxSuperRet.intValue() != superRet.intValue()) {
								u.setSuperRet(uage.getSuperRet());
							} else {
								//更新不定位award_user状态：已删除
								this.gameUserAwardService.deleteByAwardBetType(u.getLotteryId(), userid, GameAwardBetType.SUPER.getValue());
							}
							//骰宝不定位范围值相同，则更新直选值，同时更新award_user状态：已删除 
							if (maxSbThreeoneRet.intValue() != sbThreeoneRet.intValue()) {
								u.setSbThreeoneRet(uage.getSbThreeoneRet());
							} else {
								//更新不定位award_user状态：已删除
								this.gameUserAwardService.deleteByAwardBetType(u.getLotteryId(), userid, GameAwardBetType.SB_THREE_ONE.getValue());
							}
							//六合彩是新彩種不會出現在 GAME_AWARD_USER 內
						}
						if(u!=null){
							Long directRet=u.getDirectRet()==null?0:u.getDirectRet();
							Long maxDirectRet=u.getMaxDirectRet()==null?0:u.getMaxDirectRet();
							Long threeOneRet=u.getThreeoneRet()==null?0:u.getThreeoneRet();
							Long maxThreeOneRet=u.getMaxThreeOneRet()==null?0:u.getMaxThreeOneRet();
							Long superRet=u.getSuperRet()==null?0:u.getSuperRet();
							Long maxSuperRet=u.getMaxSuperRet()==null?0:u.getMaxSuperRet();
							
							Long maxLhcYear = u.getMaxLhcYear() == null ? 0 : u.getMaxLhcYear();
							Long lhcYear = u.getLhcYear() == null ? 0 : u.getLhcYear();
							Long maxLhcColor = u.getMaxLhcColor() == null ? 0 : u.getMaxLhcColor();
							Long lhcColor = u.getLhcColor() == null ? 0 : u.getLhcColor();
							Long maxSbThreeoneRet = u.getMaxSbThreeoneRet() == null ? 0 : u.getMaxSbThreeoneRet();
							Long sbThreeoneRet = u.getSbThreeoneRet() == null ? 0 : u.getSbThreeoneRet();
							Long maxLhcFlatcode = u.getMaxLhcFlatcode() == null ? 0 : u.getMaxLhcFlatcode();
							Long lhcFlatcode = u.getLhcFlatcode() == null ? 0 : u.getLhcFlatcode();
							Long maxLhcHalfwave = u.getMaxLhcHalfwave() == null ? 0 : u.getMaxLhcHalfwave();
							Long lhcHalfwave = u.getLhcHalfwave() == null ? 0 : u.getLhcHalfwave();
							Long maxLhcOneyear = u.getMaxLhcOneyear() == null ? 0 : u.getMaxLhcOneyear();
							Long lhcOneyear = u.getLhcOneyear() == null ? 0 : u.getLhcOneyear();
							Long maxLhcNotin = u.getMaxLhcNotin() == null ? 0 : u.getMaxLhcNotin();
							Long lhcNotin = u.getLhcNotin() == null ? 0 : u.getLhcNotin();
							Long maxLhcContinuein23 = u.getMaxLhcContinuein23() == null ? 0 : u.getMaxLhcContinuein23();
							Long lhcContinuein23 = u.getLhcContinuein23() == null ? 0 : u.getLhcContinuein23();
							Long maxLhcContinuein4 = u.getMaxLhcContinuein4() == null ? 0 : u.getMaxLhcContinuein4();
							Long lhcContinuein4 = u.getLhcContinuein4() == null ? 0 : u.getLhcContinuein4();
							Long maxLhcContinuein5 = u.getMaxLhcContinuein5() == null ? 0 : u.getMaxLhcContinuein5();
							Long lhcContinuein5 = u.getLhcContinuein5() == null ? 0 : u.getLhcContinuein5();
							Long maxLhcContinuenotin23 = u.getMaxLhcContinuenotin23() == null ? 0 : u.getMaxLhcContinuenotin23();
							Long lhcContinuenotin23 = u.getLhcContinuenotin23() == null ? 0 : u.getLhcContinuenotin23();
							Long maxLhcContinuenotin4 = u.getMaxLhcContinuenotin4() == null ? 0 : u.getMaxLhcContinuenotin4();
							Long lhcContinuenotin4 = u.getLhcContinuenotin4() == null ? 0 : u.getLhcContinuenotin4();
							Long maxLhcContinuenotin5 = u.getMaxLhcContinuenotin5() == null ? 0 : u.getMaxLhcContinuenotin5();
							Long lhcContinuenotin5 = u.getLhcContinuenotin5() == null ? 0 : u.getLhcContinuenotin5();
							Long maxLhcContinuecode = u.getMaxLhcContinuecode() == null ? 0 : u.getMaxLhcContinuecode();
							Long lhcContinuecode = u.getLhcContinuecode() == null ? 0 : u.getLhcContinuecode();
							//出现负数则抛异常
							if(directRet < 0 || maxDirectRet < 0 || threeOneRet < 0 || maxThreeOneRet < 0 
									|| superRet < 0 || maxSuperRet < 0
									|| maxLhcYear < 0 || lhcYear < 0 || maxLhcColor < 0 || lhcColor < 0
									|| maxSbThreeoneRet<0 || sbThreeoneRet<0
									|| maxLhcFlatcode < 0 || lhcFlatcode < 0 || maxLhcHalfwave < 0 || lhcHalfwave < 0
									|| maxLhcOneyear < 0 || lhcOneyear < 0 || maxLhcNotin < 0 || lhcNotin < 0
									|| maxLhcContinuein23 < 0 || lhcContinuein23 < 0 || maxLhcContinuein4 < 0 || lhcContinuein4 < 0
									|| maxLhcContinuein5 < 0 || lhcContinuein5 < 0 || maxLhcContinuenotin23 < 0 || lhcContinuenotin23 < 0
									|| maxLhcContinuenotin4 < 0 || lhcContinuenotin4 < 0 || maxLhcContinuenotin5 < 0 || lhcContinuenotin5 < 0
									|| maxLhcContinuecode < 0 || lhcContinuecode < 0
							){
								throw new Exception("返点设置不合理，请重新设置");
							}					
						}
						u.setBetType(uage.getBetType()); //设置为原先的投注属性 
						toUpdateList.add(u);
						hasUpdateData = true;
						break;
					}
				}
				//理论上前台不存在去掉勾选操作（用户有可能在正常投注中）
				if (!hasUpdateData) {
					toRemoveList.add(uage);
				}
			}
		}

		for (UserAwardGroupEntity n : newData) {
			boolean flag = false;
			for (UserAwardGroupEntity e : existedData) {
				if (n.getLotteryId().equals(e.getLotteryId())
						&& n.getSysGroupAward().getId().equals(e.getSysGroupAward().getId())) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				if(n!=null){
					Long directRet=n.getDirectRet()==null?0:n.getDirectRet();
					Long maxDirectRet=n.getMaxDirectRet()==null?0:n.getMaxDirectRet();
					Long threeOneRet=n.getThreeoneRet()==null?0:n.getThreeoneRet();
					Long maxThreeOneRet=n.getMaxThreeOneRet()==null?0:n.getMaxThreeOneRet();
					Long superRet=n.getSuperRet()==null?0:n.getSuperRet();
					Long maxSuperRet=n.getMaxSuperRet()==null?0:n.getMaxSuperRet();
					
					Long lhcYear=n.getLhcYear()==null?0:n.getLhcYear();
					Long maxLhcYear=n.getMaxLhcYear()==null?0:n.getMaxLhcYear();
					Long lhcColor=n.getLhcColor()==null?0:n.getLhcColor();
					Long maxLhcColor=n.getMaxLhcColor()==null?0:n.getMaxLhcColor();
					Long lhcFlatcode=n.getLhcFlatcode()==null?0:n.getLhcFlatcode();
					Long maxLhcFlatcode=n.getMaxLhcFlatcode()==null?0:n.getMaxLhcFlatcode();
					Long lhcHalfwave=n.getLhcHalfwave()==null?0:n.getLhcHalfwave();
					Long maxLhcHalfwave=n.getMaxLhcHalfwave()==null?0:n.getMaxLhcHalfwave();
					Long lhcOneyear=n.getLhcOneyear()==null?0:n.getLhcOneyear();
					Long maxLhcOneyear=n.getMaxLhcOneyear()==null?0:n.getMaxLhcOneyear();
					Long lhcNotin=n.getLhcNotin()==null?0:n.getLhcNotin();
					Long maxLhcNotin=n.getMaxLhcNotin()==null?0:n.getMaxLhcNotin();
					Long lhcContinuein23=n.getLhcContinuein23()==null?0:n.getLhcContinuein23();
					Long maxLhcContinuein23=n.getMaxLhcContinuein23()==null?0:n.getMaxLhcContinuein23();
					Long lhcContinuein4=n.getLhcContinuein4()==null?0:n.getLhcContinuein4();
					Long maxLhcContinuein4=n.getMaxLhcContinuein4()==null?0:n.getMaxLhcContinuein4();
					Long lhcContinuein5=n.getLhcContinuein5()==null?0:n.getLhcContinuein5();
					Long maxLhcContinuein5=n.getMaxLhcContinuein5()==null?0:n.getMaxLhcContinuein5();
					Long lhcContinuenotin23=n.getLhcContinuenotin23()==null?0:n.getLhcContinuenotin23();
					Long maxLhcContinuenotin23=n.getMaxLhcContinuenotin23()==null?0:n.getMaxLhcContinuenotin23();
					Long lhcContinuenotin4=n.getLhcContinuenotin4()==null?0:n.getLhcContinuenotin4();
					Long maxLhcContinuenotin4=n.getMaxLhcContinuenotin4()==null?0:n.getMaxLhcContinuenotin4();
					Long lhcContinuenotin5=n.getLhcContinuenotin5()==null?0:n.getLhcContinuenotin5();
					Long maxLhcContinuenotin5=n.getMaxLhcContinuenotin5()==null?0:n.getMaxLhcContinuenotin5();
					Long lhcContinuecode=n.getLhcContinuecode()==null?0:n.getLhcContinuecode();
					Long maxLhcContinuecode=n.getMaxLhcContinuecode()==null?0:n.getMaxLhcContinuecode();
					//出现负数则抛异常
					if(directRet<0 || maxDirectRet<0 || threeOneRet<0 || maxThreeOneRet<0 ||
					   superRet<0 || maxSuperRet<0 || lhcYear<0 || maxLhcYear<0 ||
					   lhcColor<0 || maxLhcColor<0 || lhcFlatcode<0 || maxLhcFlatcode<0 ||
					   lhcHalfwave<0 || maxLhcHalfwave<0 || lhcOneyear<0 || maxLhcOneyear<0 ||
					   lhcNotin<0 || maxLhcNotin<0 || lhcContinuein23<0 || maxLhcContinuein23<0 ||
					   lhcContinuein4<0 || maxLhcContinuein4<0 || lhcContinuein5<0 || maxLhcContinuein5<0 ||
					   lhcContinuenotin23<0 || maxLhcContinuenotin23<0 || lhcContinuenotin4<0 || maxLhcContinuenotin4<0 ||
					   lhcContinuenotin5<0 || maxLhcContinuenotin5<0 || lhcContinuecode<0 || maxLhcContinuecode<0){
						throw new Exception("返点设置不合理，请重新设置");
					}					
				}
				toAddList.add(n);
			}
		}
		Set<String> existsSet = new HashSet<String>();
		if (toUpdateList != null && toUpdateList.size() > 0) {
			for (UserAwardGroupEntity up : toUpdateList) {
				if (!existsSet.contains(String.valueOf(up.getLotteryId()))) {//修改数据判断错误
					existsSet.add(String.valueOf(up.getLotteryId()));
				}
				GameUserAwardGroup guag = new GameUserAwardGroup();
				guag = VOConvert.userAwardGroupEntity2GameUserAwardGroup(up);
				//前面已经设置原先的投注属性 		guag.setBetType(1);  
				guag.setStatus(1);
				guag.setUpdateTime(DateUtils.currentDate());
				gameUserAwardGroupDao.updateRet(guag);
			}
		}

		if (toRemoveList != null && toRemoveList.size() > 0) {
			for (UserAwardGroupEntity tr : toRemoveList) {
				GameUserAwardGroup guag = new GameUserAwardGroup();
				guag = VOConvert.userAwardGroupEntity2GameUserAwardGroup(tr);
				guag.setStatus(0);
				guag.setUpdateTime(DateUtils.currentDate());
				gameUserAwardGroupDao.updateRet(guag);
			}
		}
		//新增在后
		if (toAddList != null && toAddList.size() > 0) {
			Map<String, Integer> countMap = new HashMap<String, Integer>();
			GameUserAwardGroup guag = new GameUserAwardGroup();
			for (UserAwardGroupEntity e : toAddList) {
				if (!existsSet.contains(String.valueOf(e.getLotteryId()))) {//修改数据判断错误
					Integer count = countMap.get(String.valueOf(e.getLotteryId()));
					count = count == null ? 1 : (count + 1);
					countMap.put(String.valueOf(e.getLotteryId()), count);
				}
			}
			for (UserAwardGroupEntity e : toAddList) {
				Integer count = countMap.get(String.valueOf(e.getLotteryId()));
				count = count == null ? 0 : count;
				guag = VOConvert.userAwardGroupEntity2GameUserAwardGroup(e);
				guag.setBetType(count == 1 ? 1 : 0); //不存在修改且计数等于1
				guag.setCreateTime(DateUtils.currentDate());
				guag.setSetType(1);
				guag.setStatus(1);
				gameUserAwardGroupDao.insert(guag);
			}

		}
	}
	
	@Override
	public Long selectActualBoundsByCodes(Integer lotteryid, Long userId, Long gameGroupCode, Long gameSetCode,
			Long betMethodCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("lotteryid", lotteryid);
		map.put("gameGroupCode", gameGroupCode);
		map.put("gameSetCode", gameSetCode);
		map.put("betMethodCode", betMethodCode);
		map.put("userId", userId);

		Long actualBounds = gameUserAwardDao.selectActualBoundsByCondition(map);
		return actualBounds;
	}

	@Override
	public void modifyGameUserAwardGroupById(Long userAwardGroupId, Long userId, Long lotteryId) throws Exception {
		//更改策略：如果投注奖金组已经设置则不覆盖更新
		List<GameUserAwardGroup> userAwardGroupList = gameUserAwardGroupDao
				.getLotteryGameUserAwardGroupByUserIdAndLotteryId(userId, lotteryId);
		if (userAwardGroupList != null && userAwardGroupList.size() > 0) {
			boolean flag = false;
			GameUserAwardGroup userAwardGroupTmp = null;
			for (GameUserAwardGroup userAwardGroup : userAwardGroupList) {
				if (userAwardGroup.getBetType().intValue() == 1) {
					flag = true;
					throw new UserGameAwardConfigErrorException();//说明已配置奖金组
				}
				if (userAwardGroup.getId().equals(userAwardGroupId)) {
					userAwardGroupTmp = userAwardGroup;
				}
			}
			if (!flag && userAwardGroupTmp != null) {
				userAwardGroupTmp.setBetType(1);
				userAwardGroupTmp.setUpdateTime(DateUtils.currentDate());
				gameUserAwardGroupDao.update(userAwardGroupTmp);
			} else {
				//投注奖金组已经设置，抛异常给前台
				throw new Exception("User award group has been setted.");
			}
		}
	}
	
	
	@Override
	public void updateGameUserAwardGroupById(Long userAwardGroupId, Long userId, Long lotteryId) throws Exception {
		//更改策略：如果投注奖金组已经设置则不覆盖更新
		List<GameUserAwardGroup> userAwardGroupList = gameUserAwardGroupDao
				.getLotteryGameUserAwardGroupByUserIdAndLotteryId(userId, lotteryId);
		log.info("----------userAwardGroupList size : " + userAwardGroupList.size());
		if (userAwardGroupList != null && userAwardGroupList.size() > 0) {
			boolean flag = false;
			GameUserAwardGroup userAwardGroupTmp = null;
			for (GameUserAwardGroup userAwardGroup : userAwardGroupList) {
				if (userAwardGroup.getBetType().intValue() == 1) {
					log.info("----------userAwardGroup.getBetType().intValue() : " + userAwardGroup.getBetType().intValue());
					flag = true;
					throw new UserGameAwardConfigErrorException();//说明已配置奖金组
				}
				log.info("---------- getSysGroupAwardId: " + userAwardGroup.getSysGroupAwardId().intValue());
				log.info("---------- userAwardGroupId: " + userAwardGroupId);
				
				if (userAwardGroup.getSysGroupAwardId().equals(userAwardGroupId)) {
					userAwardGroupTmp = userAwardGroup;
				}
			}
			log.info("----------flag : " + flag);
			
			if (!flag && userAwardGroupTmp != null) {
				log.info("----------userAwardGroupTmp != null  ");
				userAwardGroupTmp.setBetType(1);
				userAwardGroupTmp.setUpdateTime(DateUtils.currentDate());
				gameUserAwardGroupDao.update(userAwardGroupTmp);
			} else {
				//投注奖金组已经设置，抛异常给前台
				throw new Exception("User award group has been setted.");
			}
		}
	}

	@Override
	public List<UserAwardGroupEntity> queryGameUserAwardGroupByUserIdAndBetType(long userid, int awardType)
			throws Exception {
		List<UserAwardGroupEntity> userAwardGroupEntityList = new ArrayList<UserAwardGroupEntity>();
		List<GameUserAwardGroup> gameUserAwardGroupList = gameUserAwardGroupDao
				.getGameUserAwardGroupByUserIdAndBetType(userid, awardType);
		UserAwardGroupEntity entity = null;
		if (gameUserAwardGroupList != null && gameUserAwardGroupList.size() > 0) { 
			for (GameUserAwardGroup guag : gameUserAwardGroupList) {
				setUserAwardGroupRetValueRange(guag);
				entity = new UserAwardGroupEntity();
				entity = VOConvert.gameUserAwardGroup2UserAwardGroupEntity(guag);
				GameSeries gameSeries = gameSeriesDao.getByLotteyId(guag.getLotteryid());
				entity.setLotterySeriesCode(gameSeries.getLotterySeriesCode().longValue());
				entity.setLotterySeriesName(gameSeries.getLotterySeriesName());
				entity.setLotteryName(gameSeries.getLotteryName());
				entity.setIsUsed(true);
				userAwardGroupEntityList.add(entity);
			}
		}

		return userAwardGroupEntityList;
	}

	private void setUserAwardGroupRetValueRange(GameUserAwardGroup userAwardGroup) throws Exception {
		Long lotteryId=userAwardGroup.getLotteryid();
		Long userId=userAwardGroup.getUserid();
		Long userAwardGroupId=userAwardGroup.getId();
		String suffixKey=SEPERATOR+lotteryId+SEPERATOR+userAwardGroupId;
		Map<String, Integer> retValueMap = this.getUserAwardRange(lotteryId, userId,userAwardGroupId); 
		if (retValueMap != null) {
			userAwardGroup.setDirectRet(retValueMap.get(MIN_DIRECT+suffixKey) == null ? userAwardGroup.getDirectRet() : retValueMap
					.get(MIN_DIRECT+suffixKey));
			userAwardGroup.setMaxDirectRet(retValueMap.get(MAX_DIRECT+suffixKey) == null ? userAwardGroup.getDirectRet() : retValueMap
					.get(MAX_DIRECT+suffixKey));
			userAwardGroup.setThreeoneRet(retValueMap.get(MIN_THREEONE+suffixKey) == null ? userAwardGroup.getThreeoneRet() : retValueMap
					.get(MIN_THREEONE+suffixKey));
			userAwardGroup.setMaxThreeOneRet(retValueMap.get(MAX_THREEONE+suffixKey) == null ? userAwardGroup.getThreeoneRet() : retValueMap
					.get(MAX_THREEONE+suffixKey));
			userAwardGroup.setSuperRet(retValueMap.get(MIN_SUPER+suffixKey) == null ? userAwardGroup.getSuperRet() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxSuperRet(retValueMap.get(MAX_SUPER+suffixKey) == null ? userAwardGroup.getSuperRet() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcYear(retValueMap.get(MIN_LHC_YEAR+suffixKey) == null ? userAwardGroup.getLhcYear(): retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcYear(retValueMap.get(MAX_LHC_YEAR+suffixKey) == null ? userAwardGroup.getLhcYear() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcColor(retValueMap.get(MIN_LHC_COLOR+suffixKey) == null ? userAwardGroup.getLhcColor() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcColor(retValueMap.get(MAX_LHC_COLOR+suffixKey) == null ? userAwardGroup.getLhcColor() : retValueMap
					.get(MAX_SUPER+suffixKey));
			userAwardGroup.setSbThreeoneRet(retValueMap.get(MIN_SB_THREEONE_RET+suffixKey) == null ? userAwardGroup.getSbThreeoneRet() : retValueMap
					.get(MIN_SB_THREEONE_RET+suffixKey));
			userAwardGroup.setMaxSbThreeoneRet(retValueMap.get(MAX_SB_THREEONE_RET+suffixKey) == null ? userAwardGroup.getLhcColor() : retValueMap
					.get(MAX_SB_THREEONE_RET+suffixKey));
			
			
			userAwardGroup.setLhcFlatcode(retValueMap.get(MIN_LHC_FLATCODE+suffixKey) == null ? userAwardGroup.getLhcFlatcode() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcFlatcode(retValueMap.get(MAX_LHC_FLATCODE+suffixKey) == null ? userAwardGroup.getLhcFlatcode() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcHalfwave(retValueMap.get(MIN_LHC_HALFWAVE+suffixKey) == null ? userAwardGroup.getLhcHalfwave() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcHalfwave(retValueMap.get(MAX_LHC_HALFWAVE+suffixKey) == null ? userAwardGroup.getLhcHalfwave() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcOneyear(retValueMap.get(MIN_LHC_ONEYEAR+suffixKey) == null ? userAwardGroup.getLhcOneyear() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcOneyear(retValueMap.get(MAX_LHC_ONEYEAR+suffixKey) == null ? userAwardGroup.getLhcOneyear() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcNotin(retValueMap.get(MIN_LHC_NOTIN+suffixKey) == null ? userAwardGroup.getLhcNotin() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcNotin(retValueMap.get(MAX_LHC_NOTIN+suffixKey) == null ? userAwardGroup.getLhcNotin() : retValueMap
					.get(MAX_SUPER+suffixKey));

			userAwardGroup.setLhcContinuein23(retValueMap.get(MIN_LHC_CONTINUEIN23+suffixKey) == null ? userAwardGroup.getLhcContinuein23() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuein23(retValueMap.get(MAX_LHC_CONTINUEIN23+suffixKey) == null ? userAwardGroup.getLhcContinuein23() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuein4(retValueMap.get(MIN_LHC_CONTINUEIN4+suffixKey) == null ? userAwardGroup.getLhcContinuein4() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuein4(retValueMap.get(MAX_LHC_CONTINUEIN4+suffixKey) == null ? userAwardGroup.getLhcContinuein4() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuein5(retValueMap.get(MIN_LHC_CONTINUEIN5+suffixKey) == null ? userAwardGroup.getLhcContinuein5() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuein5(retValueMap.get(MAX_LHC_CONTINUEIN5+suffixKey) == null ? userAwardGroup.getLhcContinuein5() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuenotin23(retValueMap.get(MIN_LHC_CONTINUENOTIN23+suffixKey) == null ? userAwardGroup.getLhcContinuenotin23() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuenotin23(retValueMap.get(MAX_LHC_CONTINUENOTIN23+suffixKey) == null ? userAwardGroup.getLhcContinuenotin23() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuenotin4(retValueMap.get(MIN_LHC_CONTINUENOTIN4+suffixKey) == null ? userAwardGroup.getLhcContinuenotin4() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuenotin4(retValueMap.get(MAX_LHC_CONTINUENOTIN4+suffixKey) == null ? userAwardGroup.getLhcContinuenotin4() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuenotin5(retValueMap.get(MIN_LHC_CONTINUENOTIN5+suffixKey) == null ? userAwardGroup.getLhcContinuenotin5() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuenotin5(retValueMap.get(MAX_LHC_CONTINUENOTIN5+suffixKey) == null ? userAwardGroup.getLhcContinuenotin5() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
			userAwardGroup.setLhcContinuecode(retValueMap.get(MIN_LHC_CONTINUECODE+suffixKey) == null ? userAwardGroup.getLhcContinuecode() : retValueMap
					.get(MIN_SUPER+suffixKey));
			userAwardGroup.setMaxLhcContinuecode(retValueMap.get(MAX_LHC_CONTINUECODE+suffixKey) == null ? userAwardGroup.getLhcContinuecode() : retValueMap
					.get(MAX_SUPER+suffixKey));
			
		} else {
			userAwardGroup.setDirectRet(userAwardGroup.getDirectRet());
			userAwardGroup.setMaxDirectRet(userAwardGroup.getDirectRet());
			userAwardGroup.setThreeoneRet(userAwardGroup.getThreeoneRet());
			userAwardGroup.setMaxThreeOneRet(userAwardGroup.getThreeoneRet());
			userAwardGroup.setSuperRet(userAwardGroup.getSuperRet());
			userAwardGroup.setMaxSuperRet(userAwardGroup.getSuperRet());
			userAwardGroup.setLhcYear(userAwardGroup.getLhcYear());
			userAwardGroup.setLhcColor(userAwardGroup.getLhcColor());
			userAwardGroup.setSbThreeoneRet(userAwardGroup.getSbThreeoneRet());
			userAwardGroup.setLhcFlatcode(userAwardGroup.getLhcFlatcode());
			userAwardGroup.setLhcHalfwave(userAwardGroup.getLhcHalfwave());
			userAwardGroup.setLhcOneyear(userAwardGroup.getLhcOneyear());
			userAwardGroup.setLhcNotin(userAwardGroup.getLhcNotin());
			userAwardGroup.setLhcContinuein23(userAwardGroup.getLhcContinuein23());
			userAwardGroup.setLhcContinuein4(userAwardGroup.getLhcContinuein4());
			userAwardGroup.setLhcContinuein5(userAwardGroup.getLhcContinuein5());
			userAwardGroup.setLhcContinuenotin23(userAwardGroup.getLhcContinuenotin23());
			userAwardGroup.setLhcContinuenotin4(userAwardGroup.getLhcContinuenotin4());
			userAwardGroup.setLhcContinuenotin5(userAwardGroup.getLhcContinuenotin5());
			userAwardGroup.setLhcContinuecode(userAwardGroup.getLhcContinuecode());
			
			userAwardGroup.setMaxLhcYear(userAwardGroup.getLhcYear());
			userAwardGroup.setMaxLhcColor(userAwardGroup.getLhcColor());
			userAwardGroup.setMaxSbThreeoneRet(userAwardGroup.getMaxSbThreeoneRet());
			userAwardGroup.setMaxLhcFlatcode(userAwardGroup.getLhcFlatcode());
			userAwardGroup.setMaxLhcHalfwave(userAwardGroup.getLhcHalfwave());
			userAwardGroup.setMaxLhcOneyear(userAwardGroup.getLhcOneyear());
			userAwardGroup.setMaxLhcNotin(userAwardGroup.getLhcNotin());
			userAwardGroup.setMaxLhcContinuein23(userAwardGroup.getLhcContinuein23());
			userAwardGroup.setMaxLhcContinuein4(userAwardGroup.getLhcContinuein4());
			userAwardGroup.setMaxLhcContinuein5(userAwardGroup.getLhcContinuein5());
			userAwardGroup.setMaxLhcContinuenotin23(userAwardGroup.getLhcContinuenotin23());
			userAwardGroup.setMaxLhcContinuenotin4(userAwardGroup.getLhcContinuenotin4());
			userAwardGroup.setMaxLhcContinuenotin5(userAwardGroup.getLhcContinuenotin5());
			userAwardGroup.setMaxLhcContinuecode(userAwardGroup.getLhcContinuecode());
		}
	}

	/**
	 * 新建用户的时候，我们需要比对一下，前台传过来的返点列表，是否是当前系统中所有的游戏，如果是所有的游戏，那么就ok
	 * 如果有个游戏没覆盖。比如我们新上的江苏快三，那么快三，就按照默认总代的返点-代理层级*某个变量   来实现
	 */
	@Override
	public void openAccountAssignAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long currentUserId,
			Long userLvl) throws Exception {

		log.info("开始开户保存奖金组...用户的级别是:" + userLvl);

		User user = customerDao.queryUserById(currentUserId);
		if (user == null) {
			throw new UserLevelIsExistErrorException();
		}

		log.info("新增用户奖金组数据...");

		if (userAwardGroupList != null && userAwardGroupList.size() > 0) {
			Map<String, Integer> countMap = new HashMap<String, Integer>();
			//开户时被分配用户的用户奖金组同用户同彩种下的系统奖金组计数
			for (UserAwardGroupEntity userAwardGroupEntity : userAwardGroupList) {
				Integer count = countMap.get(String.valueOf(userAwardGroupEntity.getLotteryId()));
				count = count == null ? 1 : (count + 1);
				countMap.put(String.valueOf(userAwardGroupEntity.getLotteryId()), count);
			}
			for (UserAwardGroupEntity userAwardGroupEntity : userAwardGroupList) {
				if(userAwardGroupEntity!=null){
					Long directRet=userAwardGroupEntity.getDirectRet()==null?0:userAwardGroupEntity.getDirectRet();
					Long maxDirectRet=userAwardGroupEntity.getMaxDirectRet()==null?0:userAwardGroupEntity.getMaxDirectRet();
					Long threeOneRet=userAwardGroupEntity.getThreeoneRet()==null?0:userAwardGroupEntity.getThreeoneRet();
					Long maxThreeOneRet=userAwardGroupEntity.getMaxThreeOneRet()==null?0:userAwardGroupEntity.getMaxThreeOneRet();
					Long superRet=userAwardGroupEntity.getSuperRet()==null?0:userAwardGroupEntity.getSuperRet();
					Long maxSuperRet=userAwardGroupEntity.getMaxSuperRet()==null?0:userAwardGroupEntity.getMaxSuperRet();
					
					Long lhcYear=userAwardGroupEntity.getLhcYear()==null?0:userAwardGroupEntity.getLhcYear();
					userAwardGroupEntity.setLhcYear(lhcYear);
					Long lhcColor=userAwardGroupEntity.getLhcColor()==null?0:userAwardGroupEntity.getLhcColor();
					userAwardGroupEntity.setLhcColor(lhcColor);
					Long sbThreeoneRet = userAwardGroupEntity.getSbThreeoneRet()==null?0:userAwardGroupEntity.getSbThreeoneRet();
					userAwardGroupEntity.setSbThreeoneRet(sbThreeoneRet);
					Long lhcFlatcode=userAwardGroupEntity.getLhcFlatcode()==null?0:userAwardGroupEntity.getLhcFlatcode();
					userAwardGroupEntity.setLhcFlatcode(lhcFlatcode);
					Long lhcHalfwave=userAwardGroupEntity.getLhcHalfwave()==null?0:userAwardGroupEntity.getLhcHalfwave();
					userAwardGroupEntity.setLhcHalfwave(lhcHalfwave);
					Long lhcOneyear=userAwardGroupEntity.getLhcOneyear()==null?0:userAwardGroupEntity.getLhcOneyear();
					userAwardGroupEntity.setLhcOneyear(lhcOneyear);
					Long lhcNotin=userAwardGroupEntity.getLhcNotin()==null?0:userAwardGroupEntity.getLhcNotin();
					userAwardGroupEntity.setLhcNotin(lhcNotin);
					Long lhcContinuein23=userAwardGroupEntity.getLhcContinuein23()==null?0:userAwardGroupEntity.getLhcContinuein23();
					userAwardGroupEntity.setLhcContinuein23(lhcContinuein23);
					Long lhcContinuein4=userAwardGroupEntity.getLhcContinuein4()==null?0:userAwardGroupEntity.getLhcContinuein4();
					userAwardGroupEntity.setLhcContinuein4(lhcContinuein4);
					Long lhcContinuein5=userAwardGroupEntity.getLhcContinuein5()==null?0:userAwardGroupEntity.getLhcContinuein5();
					userAwardGroupEntity.setLhcContinuein5(lhcContinuein5);
					Long lhcContinuenotin23=userAwardGroupEntity.getLhcContinuenotin23()==null?0:userAwardGroupEntity.getLhcContinuenotin23();
					userAwardGroupEntity.setLhcContinuenotin23(lhcContinuenotin23);
					Long lhcContinuenotin4=userAwardGroupEntity.getLhcContinuenotin4()==null?0:userAwardGroupEntity.getLhcContinuenotin4();
					userAwardGroupEntity.setLhcContinuenotin4(lhcContinuenotin4);
					Long lhcContinuenotin5=userAwardGroupEntity.getLhcContinuenotin5()==null?0:userAwardGroupEntity.getLhcContinuenotin5();
					userAwardGroupEntity.setLhcContinuenotin5(lhcContinuenotin5);
					Long lhcContinuecode=userAwardGroupEntity.getLhcContinuecode()==null?0:userAwardGroupEntity.getLhcContinuecode();
					userAwardGroupEntity.setLhcContinuecode(lhcContinuecode);
					//这幾个值出现负数则抛异常

					if(directRet<0 || maxDirectRet<0 ||threeOneRet<0 || maxThreeOneRet<0 ||superRet<0 || maxSuperRet<0 ||lhcYear<0||lhcColor<0
						||lhcFlatcode<0	|| lhcHalfwave<0 || lhcOneyear<0 || lhcNotin<0 || lhcContinuein23<0 || lhcContinuein4<0 || lhcContinuein5<0
						 || lhcContinuenotin23<0 || lhcContinuenotin4<0 || lhcContinuenotin5<0 || lhcContinuecode<0){
						throw new Exception("返点设置不合理，请重新设置");
					}					
				}
				GameUserAwardGroup userAwardGroup = VOConvert
						.userAwardGroupEntity2GameUserAwardGroup(userAwardGroupEntity);
				Integer count = countMap.get(String.valueOf(userAwardGroupEntity.getLotteryId()));
				count = count == null ? 0 : count;
				//非总代且系统奖金组计数=1的自动设置为投注用户奖金组
				if (userLvl != 0 && count == 1) { //总代不设置投注属性
					userAwardGroup.setBetType(1);
				} else {
					userAwardGroup.setBetType(0);
				}
				if(null==userAwardGroupEntity.getSuperRet()){	//老链接开户 传超级对子返点=null
					User openUser=new User();
					openUser.setId(userAwardGroupList.get(0).getUserid());
					openUser.setUserLevel(userLvl.intValue());
					openUser.setParent(user);
					replenishUserAwardGroupSuper(openUser,userAwardGroup);
				}
				userAwardGroup.setCreateTime(DateUtils.currentDate());
				gameUserAwardGroupDao.insert(userAwardGroup);
			} 
			User openUser=new User();
			openUser.setId(userAwardGroupList.get(0).getUserid());
			openUser.setUserLevel(userLvl.intValue());
			openUser.setParent(user);
			if(openUser.getUserLevel()!=0){	//非总代用户开户  & 老用户链接开户
				replenishUserAwardGroup(openUser,userAwardGroupList);
			}
		}
		log.info("新增用户奖金组数据完成。");

	}
	private void replenishUserAwardGroupSuper(User user,GameUserAwardGroup userAwardGroup){
		try{
			User puser=user.getParent();
			int userLvl=puser.getUserLevel();
			if(userLvl==0){
				GameAwardGroup awardGroup=gameAwardGroupService.queryById(userAwardGroup.getSysGroupAwardId());
				if(null!=awardGroup){
					userAwardGroup.setSuperRet(0l);
				}
			}else{
				List<GameUserAwardGroup> userAwardGroupList=queryLotteryGameUserAwardGroupByLotteryIdAndUserId(puser.getId(), userAwardGroup.getLotteryid());
				if(null!=userAwardGroupList && userAwardGroupList.size()>0){
					for(GameUserAwardGroup userAwardGroup2:userAwardGroupList){
						if(userAwardGroup.getSysGroupAwardId().longValue()==userAwardGroup2.getSysGroupAwardId().longValue()){
							userAwardGroup.setSuperRet(0l);
							break;
						}
					}
				}
			}
		}catch(Exception e){
			log.error("replenish user award group super failed.",e);
		}
	}
	/**
	 * 补充未覆盖的彩种（采用默认返点规则:总代的返点-代理层级*某个变量  ）
	 * 暂时只支持快三系列
	 */
	private void replenishUserAwardGroup(User user,List<UserAwardGroupEntity> userAwardGroupList){
		/**
		 * 1.列出未覆盖的彩种
		 * 2.计算该用户的返点
		 * 3.初始化这些彩种的返点
		 */
		List<GameSeries> gameSeriesList=gameSeriesService.getAllForSale();
		if(gameSeriesList!=null && gameSeriesList.size()>0){
			
			for(Iterator<GameSeries> iter=gameSeriesList.iterator();iter.hasNext();){
				boolean isExists=false;
				GameSeries gameSeries=iter.next();
				if(userAwardGroupList!=null){					
					for(UserAwardGroupEntity userAwardGroup:userAwardGroupList){
						if(userAwardGroup.getLotteryId().longValue()==gameSeries.getLotteryid().longValue()){
							isExists=true;
							break;
						}
					}
				}
				if(isExists){
					iter.remove();
				}
			} 
			if(gameSeriesList.size()>0){
				for(GameSeries gameSeries:gameSeriesList){
					initUserAwardGroupByGeneralAgent(user, gameSeries);
					
				}
			}
		}		
	}
	
	private void initUserAwardGroupByGeneralAgent(User user,GameSeries gameSeries) {
		try{
			Date curDate=DateUtils.currentDate();
			
			//获取某彩种的总代返点
			List<GameAwardGroupEntity> awardGroupList=gameAwardGroupService.queryGameAwardGroupByLotteryId(gameSeries.getLotteryid());
			if(awardGroupList!=null){
				
				for(GameAwardGroupEntity awardGroup:awardGroupList){
					
					GameUserAwardGroup userAwardGroup = new GameUserAwardGroup();
					userAwardGroup.setLotterySeriesCode(new Long(gameSeries.getLotterySeriesCode()));
					userAwardGroup.setLotterySeriesName(gameSeries.getLotterySeriesName());
					userAwardGroup.setLotteryid(gameSeries.getLotteryid());
					userAwardGroup.setDirectRet(0L);
					userAwardGroup.setThreeoneRet(0L); 
					userAwardGroup.setUserid(user.getId());
					userAwardGroup.setStatus(YesNo.YES.getValue());
					userAwardGroup.setSetType(YesNo.YES.getValue());
					userAwardGroup.setBetType(awardGroupList.size()==1?YesNo.YES.getValue():YesNo.NO.getValue());
					userAwardGroup.setSysGroupAwardId(awardGroup.getId()); 
					userAwardGroup.setCreateTime(curDate);
					userAwardGroup.setUpdateTime(curDate);
					//TODO 秒秒彩超級2000，待需求確認是否為0
					userAwardGroup.setSuperRet(0L);

					userAwardGroup.setSbThreeoneRet(0L);
					userAwardGroup.setLhcYear(0L);
					userAwardGroup.setLhcColor(0L);
					userAwardGroup.setLhcFlatcode(0L);
					userAwardGroup.setLhcHalfwave(0L);
					userAwardGroup.setLhcOneyear(0L);
					userAwardGroup.setLhcNotin(0L);
					userAwardGroup.setLhcContinuein23(0L);
					userAwardGroup.setLhcContinuein4(0L);
					userAwardGroup.setLhcContinuein5(0L);
					userAwardGroup.setLhcContinuenotin23(0L);
					userAwardGroup.setLhcContinuenotin4(0L);
					userAwardGroup.setLhcContinuenotin5(0L);
					userAwardGroup.setLhcContinuecode(0L);
					gameUserAwardGroupDao.insert(userAwardGroup);
				}
			}
		}catch(Exception e){
			log.error("补充未覆盖的彩种返点异常",e);
		}
	}
	
	@Override
	@Deprecated
	public void openAccountAssignAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long currentUserId)
			throws Exception {
		log.info("开始开户保存奖金组...");

		Long userid = userAwardGroupList.get(0).getUserid();

		User user = customerDao.queryUserById(userid);
		if (user == null) {
			throw new UserLevelIsExistErrorException();
		}

		Integer userLevel = user.getUserLevel();

		openAccountAssignAwardGroup(userAwardGroupList, currentUserId, new Long(userLevel));

	}

	@Override
	public List<GameAward> queryLotteryGameAwardsByUser(Long lotteryId, Long userId) throws Exception {
		UserAwardGroupEntity entity = gameUserAwardGroupDao.selectUserAwardGroup(userId, lotteryId);
		return gameAwardDao.queryGameAwardByGroupId(entity.getSysGroupAward().getId(), lotteryId, null);
	}

	@Override
	public List<GameUserAwardGroup> queryLotteryGameUserAwardGroupByLotteryIdAndUserId(Long userId, Long lotteryId) {
		return gameUserAwardGroupDao.getLotteryGameUserAwardGroupByUserIdAndLotteryId(userId, lotteryId);
	}
	
	@Override
	public List<GameAwardGroup> queryUserAwardRet(Long userId, Long lotteryId) {
		return gameUserAwardGroupDao.queryUserAwardRet(userId, lotteryId);
	}

	@Deprecated
	@Override
	public void chageGameUserAwardGroup(List<UserAwardGroupEntity> userAwardGroupList, Long userId) throws Exception {
		//此方法当未使用
		log.debug("修改用户奖金组信息，userid=" + userId);

		//获取用户的所有奖金组信息。
		GameUserAwardGroup entity = new GameUserAwardGroup();
		entity.setUserid(userId);
		List<GameUserAwardGroup> awardGroupList = gameUserAwardGroupDao.getAllByEntity(entity);

		for (UserAwardGroupEntity userAwardGroup : userAwardGroupList) {

			long lsc = userAwardGroup.getLotterySeriesCode();

			//系统奖金组信息。
			List<GameAwardGroupEntity> gameAwardGroupList = gameAwardGroupDao.queryGameAwardGroupByGroupId(
					userAwardGroup.getSysGroupAward().getId(), lsc);

			boolean isAdd = true;
			//系统默认奖金组
			for (GameAwardGroupEntity sysAwardGroup : gameAwardGroupList) {

				//用户已持有的奖金组
				for (GameUserAwardGroup guag : awardGroupList) { //用户现有的奖金组
					// 对比判断是否为投注奖金组
					log.debug("系统的彩种为lotteryid = " + sysAwardGroup.getLottery().getId() + ", 用户已经拥有的彩种lotteryid="
							+ guag.getLotteryid());
					if (guag.getLotteryid().equals(sysAwardGroup.getLottery().getId())
							&& guag.getSysGroupAwardId().equals(sysAwardGroup.getId())) {

						//如果存在相同的奖金组信息，不需要新增
						isAdd = false;
						//投注奖金组设置用会原来的，无需修改。
						guag.setDirectRet(userAwardGroup.getDirectRet());
						guag.setThreeoneRet(userAwardGroup.getThreeoneRet());
						guag.setUpdateTime(DateUtils.currentDate());
						gameUserAwardGroupDao.update(guag);
					}

				}

				if (isAdd) {

					//可能在原来用户的奖金组中找不到，从而新增，比如，管理员 给用户新增1500奖金组，
					log.debug("用户 " + userId + ",无对应的彩种" + sysAwardGroup.getLottery().getId() + ",系统奖金组Id为"
							+ sysAwardGroup.getId());
					//生成新的用户奖金组数据；
					GameUserAwardGroup gas = new GameUserAwardGroup();
					//投注奖金组设置用会原来的，无需修改。
					gas.setBetType(0);
					gas.setLotteryid(sysAwardGroup.getLottery().getId());
					gas.setSetType(1);
					gas.setStatus(sysAwardGroup.getStatus().getValue());
					gas.setSysGroupAwardId(sysAwardGroup.getId());
					gas.setUserid(userId);
					gas.setCreateTime(DateUtils.currentDate());
					gas.setUpdateTime(DateUtils.currentDate());
					gas.setDirectRet(userAwardGroup.getDirectRet());
					gas.setThreeoneRet(userAwardGroup.getThreeoneRet());
					gameUserAwardGroupDao.insert(gas);
				}
			}

		}

	}

	@Override
	public GameAwardBetType getGameAwardBetType(GameBetType betType) throws Exception {
		LotteryPlayMethodKeyGenerator keyGenerator = new LotteryPlayMethodKeyGenerator(null,
				betType.getGameGroupCode(), betType.getGameSetCode(), betType.getBetMethodCode(), "_$_");
		List<ILotteryKeyFactor> specialList = specialLotteryGameAwardFactory.getObject(keyGenerator);
		if (specialList != null && specialList.size() > 0) {
			return GameAwardBetType.THREE_ONE;
		}else if(SuperPairUtil.isSuperPair(betType.getGameGroupCode())){
			return GameAwardBetType.SUPER;
		} else if(LHCUtil.isLhcBetTypeCode(betType.getBetTypeCode())){
			return LHCUtil.findLhcBetTypeCode(betType.getBetTypeCode()).getGameAwardBetType();
		} else {
			return GameAwardBetType.DIRECT;
		}
	}

	/**
	 * 获取用户奖金返点范围(根据彩种和用户ID)
	 * @param lotteryId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	private Map<String, Integer> getUserAwardRange(Long lotteryId, Long userId,Long userAwardGroupId) throws Exception {
		String suffixKey=SEPERATOR+lotteryId+SEPERATOR+userAwardGroupId;
		List<GameUserAward> userAwardList = this.gameUserAwardService.getNormalByLotteryIdAndUserId(lotteryId, userId,userAwardGroupId);
		if (userAwardList != null && userAwardList.size() > 0) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			Long minDirectRetValue = null, maxDirectRetValue = null, 
					minThreeOneRetValue = null, maxThreeOneRetValue = null,
					minSuperRetValue = null, maxSuperRetValue = null,
					minLhcYearValue = null, maxLhcYearValue = null,
					minLhcColorValue = null, maxLhcColorValue = null,
					minLhcFlatcodeValue = null, maxLhcFlatcodeValue = null,
					minLhcHalfwaveValue = null, maxLhcHalfwaveValue = null,
					minLhcOneyearValue = null, maxLhcOneyearValue = null,
					minLhcNotinValue = null, maxLhcNotinValue = null,
					minLhcContinuein23Value = null, maxLhcContinuein23Value = null,
					minLhcContinuein4Value = null, maxLhcContinuein4Value = null,
					minLhcContinuein5Value = null, maxLhcContinuein5Value = null,
					minLhcContinuenotin23Value = null, maxLhcContinuenotin23Value = null,
					minLhcContinuenotin4Value = null, maxLhcContinuenotin4Value = null,
					minLhcContinuenotin5Value = null, maxLhcContinuenotin5Value = null,
					minLhcContinuecodeValue = null, maxLhcContinuecodeValue = null;
			for (GameUserAward userAward : userAwardList) {
				if (userAward.getBetTypeCode() != null) {
					Long retValue = userAward.getRetValue();
					GameBetType betType = new GameBetType(userAward.getBetTypeCode());
					GameAwardBetType awardBetType=this.getGameAwardBetType(betType);
					if (awardBetType.getValue() == GameAwardBetType.THREE_ONE.getValue()) {
						if (minThreeOneRetValue == null) {
							minThreeOneRetValue = maxThreeOneRetValue = retValue;
						} else {
							if (retValue > maxThreeOneRetValue) {
								maxThreeOneRetValue = retValue;
							}
							if (retValue < minThreeOneRetValue) {
								minThreeOneRetValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.SUPER.getValue()) {
						if (minSuperRetValue == null) {
							minSuperRetValue = maxSuperRetValue = retValue;
						} else {
							if (retValue > maxSuperRetValue) {
								maxSuperRetValue = retValue;
							}
							if (retValue < minSuperRetValue) {
								minSuperRetValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_YEAR.getValue()) {
						if (minLhcYearValue == null) {
							minLhcYearValue = maxLhcYearValue = retValue;
						} else {
							if (retValue > maxLhcYearValue) {
								maxLhcYearValue = retValue;
							}
							if (retValue < minLhcYearValue) {
								minLhcYearValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_COLOR.getValue()) {
						if (minLhcColorValue == null) {
							minLhcColorValue = maxLhcColorValue = retValue;
						} else {
							if (retValue > maxLhcColorValue) {
								maxLhcColorValue = retValue;
							}
							if (retValue < minLhcColorValue) {
								minLhcColorValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_FLATCODE.getValue()) {
						if (minLhcFlatcodeValue == null) {
							minLhcFlatcodeValue = maxLhcFlatcodeValue = retValue;
						} else {
							if (retValue > maxLhcFlatcodeValue) {
								maxLhcFlatcodeValue = retValue;
							}
							if (retValue < minLhcFlatcodeValue) {
								minLhcFlatcodeValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_HALFWAVE.getValue()) {
						if (minLhcHalfwaveValue == null) {
							minLhcHalfwaveValue = maxLhcHalfwaveValue = retValue;
						} else {
							if (retValue > maxLhcHalfwaveValue) {
								maxLhcHalfwaveValue = retValue;
							}
							if (retValue < minLhcHalfwaveValue) {
								minLhcHalfwaveValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_ONEYEAR.getValue()) {
						if (minLhcOneyearValue == null) {
							minLhcOneyearValue = maxLhcOneyearValue = retValue;
						} else {
							if (retValue > maxLhcOneyearValue) {
								maxLhcOneyearValue = retValue;
							}
							if (retValue < minLhcOneyearValue) {
								minLhcOneyearValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_NOTIN.getValue()) {
						if (minLhcNotinValue == null) {
							minLhcNotinValue = maxLhcNotinValue = retValue;
						} else {
							if (retValue > maxLhcNotinValue) {
								maxLhcNotinValue = retValue;
							}
							if (retValue < minLhcNotinValue) {
								minLhcNotinValue = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN23.getValue()) {
						if (minLhcContinuein23Value == null) {
							minLhcContinuein23Value = maxLhcContinuein23Value = retValue;
						} else {
							if (retValue > maxLhcContinuein23Value) {
								maxLhcContinuein23Value = retValue;
							}
							if (retValue < minLhcContinuein23Value) {
								minLhcContinuein23Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN4.getValue()) {
						if (minLhcContinuein4Value == null) {
							minLhcContinuein4Value = maxLhcContinuein4Value = retValue;
						} else {
							if (retValue > maxLhcContinuein4Value) {
								maxLhcContinuein4Value = retValue;
							}
							if (retValue < minLhcContinuein4Value) {
								minLhcContinuein4Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUEIN5.getValue()) {
						if (minLhcContinuein5Value == null) {
							minLhcContinuein5Value = maxLhcContinuein5Value = retValue;
						} else {
							if (retValue > maxLhcContinuein5Value) {
								maxLhcContinuein5Value = retValue;
							}
							if (retValue < minLhcContinuein5Value) {
								minLhcContinuein5Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN23.getValue()) {
						if (minLhcContinuenotin23Value == null) {
							minLhcContinuenotin23Value = maxLhcContinuenotin23Value = retValue;
						} else {
							if (retValue > maxLhcContinuenotin23Value) {
								maxLhcContinuenotin23Value = retValue;
							}
							if (retValue < minLhcContinuenotin23Value) {
								minLhcContinuenotin23Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN4.getValue()) {
						if (minLhcContinuenotin4Value == null) {
							minLhcContinuenotin4Value = maxLhcContinuenotin4Value = retValue;
						} else {
							if (retValue > maxLhcContinuenotin4Value) {
								maxLhcContinuenotin4Value = retValue;
							}
							if (retValue < minLhcContinuenotin4Value) {
								minLhcContinuenotin4Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUENOTIN5.getValue()) {
						if (minLhcContinuenotin5Value == null) {
							minLhcContinuenotin5Value = maxLhcContinuenotin5Value = retValue;
						} else {
							if (retValue > maxLhcContinuenotin5Value) {
								maxLhcContinuenotin5Value = retValue;
							}
							if (retValue < minLhcContinuenotin5Value) {
								minLhcContinuenotin5Value = retValue;
							}
						}
					}else if (awardBetType.getValue() == GameAwardBetType.LHC_CONTINUECODE.getValue()) {
						if (minLhcContinuecodeValue == null) {
							minLhcContinuecodeValue = maxLhcContinuecodeValue = retValue;
						} else {
							if (retValue > maxLhcContinuecodeValue) {
								maxLhcContinuecodeValue = retValue;
							}
							if (retValue < minLhcContinuecodeValue) {
								minLhcContinuecodeValue = retValue;
							}
						}
					}else {
						if (minDirectRetValue == null) {
							minDirectRetValue = maxDirectRetValue = retValue;
						} else {
							if (retValue > maxDirectRetValue) {
								maxDirectRetValue = retValue;
							}
							if (retValue < minDirectRetValue) {
								minDirectRetValue = retValue;
							}
						}
					}
				}
			}
			map.put(MIN_DIRECT+suffixKey, minDirectRetValue == null ? null : minDirectRetValue.intValue());
			map.put(MAX_DIRECT+suffixKey, maxDirectRetValue == null ? null : maxDirectRetValue.intValue());
			map.put(MIN_THREEONE+suffixKey, minThreeOneRetValue == null ? null : minThreeOneRetValue.intValue());
			map.put(MAX_THREEONE+suffixKey, maxThreeOneRetValue == null ? null : maxThreeOneRetValue.intValue());
			map.put(MIN_SUPER+suffixKey, minSuperRetValue == null ? null : minSuperRetValue.intValue());
			map.put(MAX_SUPER+suffixKey, maxSuperRetValue == null ? null : maxSuperRetValue.intValue());
			map.put(MIN_LHC_YEAR+suffixKey, minLhcYearValue == null ? null : minLhcYearValue.intValue());
			map.put(MAX_LHC_YEAR+suffixKey, maxLhcYearValue == null ? null : maxLhcYearValue.intValue());
			map.put(MIN_LHC_COLOR+suffixKey, minLhcColorValue == null ? null : minLhcColorValue.intValue());
			map.put(MAX_LHC_COLOR+suffixKey, maxLhcColorValue == null ? null : maxLhcColorValue.intValue());
			map.put(MIN_LHC_FLATCODE+suffixKey, minLhcFlatcodeValue == null ? null : minLhcFlatcodeValue.intValue());
			map.put(MAX_LHC_FLATCODE+suffixKey, maxLhcFlatcodeValue == null ? null : maxLhcFlatcodeValue.intValue());
			map.put(MIN_LHC_HALFWAVE+suffixKey, minLhcHalfwaveValue == null ? null : minLhcHalfwaveValue.intValue());
			map.put(MAX_LHC_HALFWAVE+suffixKey, maxLhcHalfwaveValue == null ? null : maxLhcHalfwaveValue.intValue());
			map.put(MIN_LHC_ONEYEAR+suffixKey, minLhcOneyearValue == null ? null : minLhcOneyearValue.intValue());
			map.put(MAX_LHC_ONEYEAR+suffixKey, maxLhcOneyearValue == null ? null : maxLhcOneyearValue.intValue());
			map.put(MIN_LHC_NOTIN+suffixKey, minLhcNotinValue == null ? null : minLhcNotinValue.intValue());
			map.put(MAX_LHC_NOTIN+suffixKey, maxLhcNotinValue == null ? null : maxLhcNotinValue.intValue());
			map.put(MIN_LHC_CONTINUEIN23+suffixKey, minLhcContinuein23Value == null ? null : minLhcContinuein23Value.intValue());
			map.put(MAX_LHC_CONTINUEIN23+suffixKey, maxLhcContinuein23Value == null ? null : maxLhcContinuein23Value.intValue());
			map.put(MIN_LHC_CONTINUEIN4+suffixKey, minLhcContinuein4Value == null ? null : minLhcContinuein4Value.intValue());
			map.put(MAX_LHC_CONTINUEIN4+suffixKey, maxLhcContinuein4Value == null ? null : maxLhcContinuein4Value.intValue());
			map.put(MIN_LHC_CONTINUEIN5+suffixKey, minLhcContinuein5Value == null ? null : minLhcContinuein5Value.intValue());
			map.put(MAX_LHC_CONTINUEIN5+suffixKey, maxLhcContinuein5Value == null ? null : maxLhcContinuein5Value.intValue());
			map.put(MIN_LHC_CONTINUENOTIN23+suffixKey, minLhcContinuenotin23Value == null ? null : minLhcContinuenotin23Value.intValue());
			map.put(MAX_LHC_CONTINUENOTIN23+suffixKey, maxLhcContinuenotin23Value == null ? null : maxLhcContinuenotin23Value.intValue());
			map.put(MIN_LHC_CONTINUENOTIN4+suffixKey, minLhcContinuenotin4Value == null ? null : minLhcContinuenotin4Value.intValue());
			map.put(MAX_LHC_CONTINUENOTIN4+suffixKey, maxLhcContinuenotin4Value == null ? null : maxLhcContinuenotin4Value.intValue());
			map.put(MIN_LHC_CONTINUENOTIN5+suffixKey, minLhcContinuenotin5Value == null ? null : minLhcContinuenotin5Value.intValue());
			map.put(MAX_LHC_CONTINUENOTIN5+suffixKey, maxLhcContinuenotin5Value == null ? null : maxLhcContinuenotin5Value.intValue());
			map.put(MIN_LHC_CONTINUECODE+suffixKey, minLhcContinuecodeValue == null ? null : minLhcContinuecodeValue.intValue());
			map.put(MAX_LHC_CONTINUECODE+suffixKey, maxLhcContinuecodeValue == null ? null : maxLhcContinuecodeValue.intValue());
			return map;
		}
		return null;
	}
	@Override
	public Long getRetPointByUserIdAndLotteryIdAndBetTypeCode(Long userId,
			Long lotteryId, String betTypeCode) throws Exception {
		Map<String, BigDecimal> mapPoint=gameUserAwardGroupDao.getUserAwardPoint(betTypeCode, userId, lotteryId);
		if(null!=mapPoint && mapPoint.size()>0){
			return mapPoint.get("RETVALUE").longValue();
		}
		GameUserAwardGroup userAwardGroup=getBetedByUserIdAndLotteryId(userId, lotteryId);
		GameBetType betType = new GameBetType(betTypeCode);
		GameAwardBetType awardBetType=getGameAwardBetType(betType);
		//區分99601,99602,99603的猜一個號返點抓取
		if((lotteryId == 99601 || lotteryId == 99602 || lotteryId == 99603) && betTypeCode.equals("42_36_78")){
			return userAwardGroup.getSbThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.THREE_ONE)){
			return userAwardGroup.getThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.SUPER)){
			return userAwardGroup.getSuperRet();
		}else if(awardBetType.equals(GameAwardBetType.LHC_YEAR)){
			return userAwardGroup.getLhcYear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_COLOR)){
			return userAwardGroup.getLhcColor();
		}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
			return userAwardGroup.getLhcFlatcode();
		}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
			return userAwardGroup.getLhcHalfwave();
		}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
			return userAwardGroup.getLhcOneyear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
			return userAwardGroup.getLhcNotin();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
			return userAwardGroup.getLhcContinuein23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
			return userAwardGroup.getLhcContinuein4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
			return userAwardGroup.getLhcContinuein5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
			return userAwardGroup.getLhcContinuenotin23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
			return userAwardGroup.getLhcContinuenotin4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
			return userAwardGroup.getLhcContinuenotin5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
			return userAwardGroup.getLhcContinuecode();
		}else{
			return userAwardGroup.getDirectRet();
		}
	}
	@Override
	public Long getRetPointByUserIdAndLotteryIdAndBetTypeCodeAndMultiple(Long userId,
			Long lotteryId, String betTypeCode,Long odds) throws Exception {
		GameUserAwardGroup userAwardGroup=getBetedByUserIdAndLotteryId(userId, lotteryId);
		GameBetType betType = new GameBetType(betTypeCode);
		GameAwardBetType awardBetType=getGameAwardBetType(betType);
		if(awardBetType.equals(GameAwardBetType.THREE_ONE)){
			return userAwardGroup.getThreeoneRet();
		}else if(awardBetType.equals(GameAwardBetType.SUPER)){
			return userAwardGroup.getSuperRet();
		}else if(awardBetType.equals(GameAwardBetType.LHC_YEAR)){
			return userAwardGroup.getLhcYear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_COLOR)){
			return userAwardGroup.getLhcColor();
		}else if(awardBetType.equals(GameAwardBetType.LHC_FLATCODE)){
			return userAwardGroup.getLhcFlatcode();
		}else if(awardBetType.equals(GameAwardBetType.LHC_HALFWAVE)){
			return userAwardGroup.getLhcHalfwave();
		}else if(awardBetType.equals(GameAwardBetType.LHC_ONEYEAR)){
			return userAwardGroup.getLhcOneyear();
		}else if(awardBetType.equals(GameAwardBetType.LHC_NOTIN)){
			return userAwardGroup.getLhcNotin();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN23)){
			return userAwardGroup.getLhcContinuein23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN4)){
			return userAwardGroup.getLhcContinuein4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUEIN5)){
			return userAwardGroup.getLhcContinuein5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN23)){
			return userAwardGroup.getLhcContinuenotin23();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN4)){
			return userAwardGroup.getLhcContinuenotin4();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUENOTIN5)){
			return userAwardGroup.getLhcContinuenotin5();
		}else if(awardBetType.equals(GameAwardBetType.LHC_CONTINUECODE)){
			return userAwardGroup.getLhcContinuecode();
		}
		int defOdd = 40;
		odds = odds / 10000;
		for (Long i = userAwardGroup.getDirectRet() ; i >= 0; i -= 200) {
			if(defOdd == odds){
				return i;
			}
			defOdd++;
		}
		throw new Exception("无法取得用户(" + userId + ")彩种(" + lotteryId + ")玩法(" + betTypeCode + ")返点。来源直选返点(" + odds + ")");
	}
	
	@Override
	public GameUserAwardGroup getBetedByUserIdAndLotteryId(Long userId, Long lotteryId) throws Exception {
		List<GameUserAwardGroup> userAwardGroupList=queryLotteryGameUserAwardGroupByLotteryIdAndUserId(userId, lotteryId);
		if(null!=userAwardGroupList){
			for(GameUserAwardGroup userAwardGroup:userAwardGroupList){
				if(YesNo.YES.getValue()==userAwardGroup.getBetType().intValue()){
					return userAwardGroup;
				}
			}
		}
		return null;
	}

	@Override
	public List<UserBetAwardGroupEntity> checkMaxAward(List<UserBetAwardGroupEntity> currentUserAwardGroupList) {
		
		Long lotteryId = currentUserAwardGroupList.get(0).getLotteryid();
		//先撈取獎金組的其中一項玩法
		String betTypeCode = gameAwardDao.getAwardBetTypeForOne(lotteryId);
		//判斷於該玩法哪一獎金組獎金多
		
		Integer awardGroupId = 0;
		Long awardbouns = 0l;
		for(UserBetAwardGroupEntity entity:currentUserAwardGroupList){
			Long bouns = gameAwardDao.getActualBonusByAwardGroupId(lotteryId, betTypeCode, entity.getSysAwardGroupId());	
			
			if(bouns>awardbouns){
				awardGroupId = entity.getSysAwardGroupId();
				awardbouns = bouns;
			}
		}
		
		for(UserBetAwardGroupEntity entity:currentUserAwardGroupList){
			if(awardGroupId==entity.getSysAwardGroupId()){
				entity.setIsMaxAward(true);
			}
		}
		
		return currentUserAwardGroupList;
	}
}
