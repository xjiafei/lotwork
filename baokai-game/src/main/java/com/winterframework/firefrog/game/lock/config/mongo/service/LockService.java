
package com.winterframework.firefrog.game.lock.config.mongo.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.validate.business.IKeyGenerator;
import com.winterframework.firefrog.common.wincaculate.ICommonKeyFactory;
import com.winterframework.firefrog.game.dao.IGameOrderDao;
import com.winterframework.firefrog.game.dao.IGamePackageDao;
import com.winterframework.firefrog.game.dao.IGamePackageItemDao;
import com.winterframework.firefrog.game.dao.IGamePlanDao;
import com.winterframework.firefrog.game.dao.IGamePlanDetailDao;
import com.winterframework.firefrog.game.dao.IGamePointDao;
import com.winterframework.firefrog.game.dao.IGameReturnPointDao;
import com.winterframework.firefrog.game.dao.IGameSlipDao;
import com.winterframework.firefrog.game.dao.vo.GameAward;
import com.winterframework.firefrog.game.dao.vo.GamePackage;
import com.winterframework.firefrog.game.dao.vo.GamePackageItem;
import com.winterframework.firefrog.game.dao.vo.GamePoint;
import com.winterframework.firefrog.game.dao.vo.GameRetPoint;
import com.winterframework.firefrog.game.entity.FileMode;
import com.winterframework.firefrog.game.entity.GameBetType;
import com.winterframework.firefrog.game.entity.GameLockEntity;
import com.winterframework.firefrog.game.entity.GameOrder;
import com.winterframework.firefrog.game.entity.GamePlan;
import com.winterframework.firefrog.game.entity.GamePlanDetail;
import com.winterframework.firefrog.game.entity.GameSlip;
import com.winterframework.firefrog.game.entity.GameSlipStatus;
import com.winterframework.firefrog.game.entity.LockPoint;
import com.winterframework.firefrog.game.entity.Lottery;
import com.winterframework.firefrog.game.entity.MoneyMode;
import com.winterframework.firefrog.game.entity.Points;
import com.winterframework.firefrog.game.lock.base.Method;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PhaseStatus;
import com.winterframework.firefrog.game.lock.config.mongo.service.LockIssue.PontStatus;
import com.winterframework.firefrog.game.service.IGameAwardGroupService;
import com.winterframework.firefrog.game.service.IGameIssueService;
import com.winterframework.firefrog.game.service.IGameLockService;
import com.winterframework.firefrog.game.service.IGameOrderService;
import com.winterframework.firefrog.game.service.IGameUserAwardGroupService;
import com.winterframework.firefrog.game.service.assist.bet.LotteryPlayMethodKeyGenerator;
import com.winterframework.firefrog.game.service.order.utlis.GameSlipUtilsEnum;
import com.winterframework.firefrog.game.web.dto.GameOrderResponseDTO;
import com.winterframework.firefrog.game.web.dto.GamePlanBetOriginDataStruc;
import com.winterframework.firefrog.game.web.dto.GamePlanParm;
import com.winterframework.firefrog.user.dao.IUserCustomerDao;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.util.JsonMapper;

@SuppressWarnings("unused")
@Service("lockService")
public class LockService extends LotteryLockService{

	

}
