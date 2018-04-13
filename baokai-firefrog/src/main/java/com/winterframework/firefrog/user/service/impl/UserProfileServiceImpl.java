package com.winterframework.firefrog.user.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winterframework.firefrog.common.config.service.IConfigService;
import com.winterframework.firefrog.common.event.FireFrogEventPublisher;
import com.winterframework.firefrog.common.noticepublisher.INoticeMsgPublisher;
import com.winterframework.firefrog.common.redis.RedisClient;
import com.winterframework.firefrog.common.util.DateUtils;
import com.winterframework.firefrog.common.util.HttpJsonClientExt;
import com.winterframework.firefrog.config.web.controller.dto.RegisterLoginConfigDto;
import com.winterframework.firefrog.global.entity.SensitiveWord;
import com.winterframework.firefrog.global.service.GlobalSensitiveWordService;
import com.winterframework.firefrog.notice.entity.NoticeTaskEnum;
import com.winterframework.firefrog.user.dao.IUserLoginLogDao;
import com.winterframework.firefrog.user.dao.impl.UserCustomerDaoImpl;
import com.winterframework.firefrog.user.dao.impl.UserPtRegisterDaoImpl;
import com.winterframework.firefrog.user.dao.vo.UserCustomer;
import com.winterframework.firefrog.user.dao.vo.UserPtRegister;
import com.winterframework.firefrog.user.dao.vo.VOConverter;
import com.winterframework.firefrog.user.entity.CustomerQueryDTO;
import com.winterframework.firefrog.user.entity.LoginLog;
import com.winterframework.firefrog.user.entity.QAInfo;
import com.winterframework.firefrog.user.entity.QueryGeneralAgentDTO;
import com.winterframework.firefrog.user.entity.User;
import com.winterframework.firefrog.user.entity.UserAgent;
import com.winterframework.firefrog.user.entity.UserProfile;
import com.winterframework.firefrog.user.entity.UserUrl;
import com.winterframework.firefrog.user.event.UserCreateEvent;
import com.winterframework.firefrog.user.exception.RegisterServiceException;
import com.winterframework.firefrog.user.exception.UserExistServiceException;
import com.winterframework.firefrog.user.exception.UserSecurityException;
import com.winterframework.firefrog.user.service.IUserProfileService;
import com.winterframework.firefrog.user.service.UserUrlManager;
import com.winterframework.firefrog.user.web.controller.OverException;
import com.winterframework.firefrog.user.web.controller.game.GameGroup;
import com.winterframework.firefrog.user.web.controller.game.GameGroupReq;
import com.winterframework.firefrog.user.web.controller.game.GameGroups;
import com.winterframework.firefrog.user.web.controller.game.UserGameGroup;
import com.winterframework.firefrog.user.web.dto.UserActivityRegisterResponse;
import com.winterframework.firefrog.user.web.dto.UserAwardStruc;
import com.winterframework.firefrog.user.web.dto.UserStrucResponse;
import com.winterframework.modules.page.Page;
import com.winterframework.modules.page.PageRequest;
import com.winterframework.modules.spring.exetend.PropertyConfig;
import com.winterframework.modules.web.jsonresult.Request;
import com.winterframework.modules.web.jsonresult.RequestBody;
import com.winterframework.modules.web.jsonresult.RequestHeader;
import com.winterframework.modules.web.util.JsonMapper;


@Transactional(rollbackFor = Exception.class)
@Service
public class UserProfileServiceImpl implements IUserProfileService {

	private static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);
	
	@PropertyConfig(value = "game_distribute_group")
	private String game_distribute_group;
	
	@Resource(name = "userPtRegisterDaoImpl")
	private UserPtRegisterDaoImpl userPtRegisterDao;

	@Resource(name = "userCustomerDaoImpl")
	private UserCustomerDaoImpl userCustomerDao;

	@Resource(name = "userLoginLogDaoImpl")
	private IUserLoginLogDao userLoginLogDao;
	
	@Autowired
	private UserUrlManager userUrlManager;

	@PropertyConfig(value = "platform.name")
	private String platformName;

	@Resource(name = "fireFrogEventPublisher")
	FireFrogEventPublisher fireFrogEventPublisher;

	@Resource(name = "noticeMsgPublisher")
	private INoticeMsgPublisher msgToMQ;

	@Resource(name = "globalSensitiveWordServiceImpl")
	private GlobalSensitiveWordService globalSensitiveWordServiceImpl;
    @Resource(name = "RedisClient")
    private RedisClient redis;

    @Resource(name = "configServiceImpl")
    private IConfigService configService;

	@Override
	public UserProfile queryUserProfile(long id) throws Exception {
		User user = userCustomerDao.queryUserById(id);
		if (user == null) {
			logger.warn("User is not exsits.ID:" + id);
			return null;
		}
		UserProfile profile = user.getUserProfile();
		return profile;
	}

	public void saveUser(UserCustomer uc) throws Exception {
		userCustomerDao.insert(uc);
	}
	public List<UserCustomer> querySubusers(Long parentId) throws Exception{
		return userCustomerDao.querySubUsers(parentId);
	}

	@Override
	public void saveUserProfile(long id, UserProfile profile) throws Exception {
		checkNickname(id,profile.getNickname(),true);
		if(StringUtils.isNotEmpty(profile.getNickname())){
			UserCustomer uc=userCustomerDao.getById(id);
			if(null!=uc){
				if(!profile.getNickname().equals(uc.getNickname())){					
					profile.setNickUpdateTime(DateUtils.currentDate());
				}
			}
		}
		User user = new User();
		user.setId(id);
		user.setUserProfile(profile);
		if (profile != null && profile.getEmail() != null && !profile.getEmail().equals("")) {
			profile.setEmail(profile.getEmail().toLowerCase());
		}
		userCustomerDao.updateUser(user);
	}

	public void setVip(Long id, Long vipLvl) {
		User user = new User();
		user.setId(id);
		user.setVipLvl(vipLvl.intValue());
		userCustomerDao.updateUser(user);
	}
	@Override
	public void bizSwitchSetting(Long userId, Integer type, Integer settMode,
			Integer status) throws Exception {
		//1奖金返点模式 2超级2000
		if(1==type){
			awardModeSetting(userId,settMode,status);
		}else if(2==type){
			superPairSetting(userId,settMode,status);
		}else if(3==type){
			lhcSetting(userId,settMode,status);
		}
	}
	private void awardModeSetting(Long userId,Integer settMode,Integer status) throws Exception{
		//1本人 2本人及其下级
		if(1==settMode){
			awardModeSettingSelf(userId,status);
		}else if(2==settMode){
			awardModeSettingUserAndSub(userId,status);
		}
	}
	private void superPairSetting(Long userId,Integer settMode,Integer status) throws Exception{
		//1本人 2本人及其下级
		if(1==settMode){
			superPairSettingSelf(userId,status);
		}else if(2==settMode){
			superPairSettingUserAndSub(userId,status);
		}
	}
	private void lhcSetting(Long userId,Integer settMode,Integer status) throws Exception{
		//1本人 2本人及其下级
		if(1==settMode){
			lhcSettingSelf(userId,status);
		}else if(2==settMode){
			lhcSettingUserAndSub(userId,status);
		}
	}
	private void awardModeSettingSelf(Long userId,Integer status) throws Exception{
		awardModeSettingOneUser(userId, status);
	}
	private void awardModeSettingUserAndSub(Long userId, Integer status) throws Exception{
		User user=queryUserById(userId);
		userCustomerDao.updateUserAwardRetStatusUserAndSub(user.getUserProfile().getUserChain(),status);
	}
	private void superPairSettingSelf(Long userId,Integer status) throws Exception{
		superPairSettingOneUser(userId, status);
	}
	private void superPairSettingUserAndSub(Long userId, Integer status) throws Exception{
		User user=queryUserById(userId);
		userCustomerDao.updateUserSuperPairStatusUserAndSub(user.getUserProfile().getUserChain(),status);
	}
	private void lhcSettingSelf(Long userId,Integer status) throws Exception{
		lhcSettingOneUser(userId, status);
	}
	private void lhcSettingUserAndSub(Long userId, Integer status) throws Exception{
		User user=queryUserById(userId);
		userCustomerDao.updateUserLhcStatusUserAndSub(user.getUserProfile().getUserChain(),status);
	}
	private void awardModeSettingOneUser(Long userId, Integer status) throws Exception{
		User user = new User();
		user.setId(userId);
		user.setAwardRetStatus(status);
		userCustomerDao.updateUser(user);
	} 
	private void superPairSettingOneUser(Long userId, Integer status) throws Exception{
		User user = new User();
		user.setId(userId);
		user.setSuperPairStatus(status);
		userCustomerDao.updateUser(user);
	} 
	private void lhcSettingOneUser(Long userId, Integer status) throws Exception{
		User user = new User();
		user.setId(userId);
		user.setLhcStatus(status);
		userCustomerDao.updateUser(user);
	}
	@Deprecated
	private void awardModeSettingSubUser(Long userId,Integer status) throws Exception{
		List<UserCustomer> subUserList=querySubusers(userId);
		if(null!=subUserList && subUserList.size()>0){
			List<Long> userIdList=new ArrayList<Long>();
			for(UserCustomer user:subUserList){
				userIdList.add(user.getId());
			}
			//awardModeSettingBatchUser(userIdList.toArray(new Long[userIdList.size()]), status);
		}
	}
	@Override
	public Integer queryBizSwitch(Long userId, Integer type) throws Exception {
		User user=userCustomerDao.queryUserById(userId);
		if(1==type){
			return user.getAwardRetStatus();
		}else if(2==type){
			return user.getSuperPairStatus();
		}else if(3==type){
			return user.getLhcStatus();
		}
		return null;
	}
	@Override
	public List<LoginLog> queryUserLoginLog(long id) {
		return userLoginLogDao.queryUserLoginLog(id);
	}

	@Override
	public void saveWithdrawPassword(long id, String withdrawPwd) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setWithdrawPwd(withdrawPwd);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
		// TODO notice_安全密码设置
	}

	@Override
	public void saveEmail(long id, String email) throws Exception {
		User emailUser = userCustomerDao.queryUserByEmail(email);
		if (emailUser != null && emailUser.getId().longValue() != id) {
			throw new UserSecurityException(UserCustomer.USER_EMAIL_EXSITS, "邮箱已存在");
		}
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setEmail(email.toLowerCase());
		profile.setEmailActived(1);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void saveCipher(long id, String cipher) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setCipher(cipher);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void savePassword(long id, String password, int passwordLevel) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setPassword(password);
		profile.setPasswordLevel(passwordLevel);
		profile.setModifyPasswdDate(new Date());
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
		// TODO notice_登录密码修改
	}

	@Override
	public void saveSecurityQA(long id, List<QAInfo> qaList) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
		// TODO notice_安全问题设置
	}

	@Override
	public List<QAInfo> querySecurityQA(long id) throws Exception {
		UserProfile profile = queryUserProfile(id);
		return profile.getQa();
	}

	@Override
	public User queryUserByName(String username) throws Exception {
		return userCustomerDao.queryUserByName(username);
	}

	@Override
	public Page<User> searchCustomer(PageRequest<CustomerQueryDTO> queryDTO) throws Exception {

		log("开始进入查询客户列表功能...");

		return userCustomerDao.queryCustomer(queryDTO);
	}
	
	public Page<User> searchCustomerDownCList(PageRequest<CustomerQueryDTO> queryDTO) throws Exception {

		log("开始进入查询客户列表功能...");

		return userCustomerDao.queryCustomerDownCList(queryDTO);
	}


	/**
	 * 
	 * 方法描述：设置日志信息
	 * 
	 * @param message
	 */
	private void log(String message) {
		if (logger.isInfoEnabled()) {
			logger.info(message);
		}
	}

	@Override
	public Page<User> queryGeneralAgentByCriteria(PageRequest<QueryGeneralAgentDTO> pageRequest) throws Exception {
		pageRequest.getSearchDo().setAccountMatch(true);
		return userCustomerDao.queryGeneralAgent(pageRequest);
	}

	@Override
	public void createGeneralAgentUser(String account, String password, Long agentLimit, Long registerIp)
			throws Exception {

		UserAgent agent = new UserAgent();
		UserProfile profile = new UserProfile();
		User user = new User();
		user.setId(-1l); //set parentId 

		profile.setAccount(account);
		profile.setPassword(password);
		profile.setPasswordLevel(1);
		profile.setRegisterDate(new Date());
		profile.setRegisterIP(registerIp);
		agent.setAgentLimit(agentLimit);
		agent.setUserProfile(profile);
		agent.setTeamAgentCount(0);
		agent.setTeamUserCount(0);
		agent.setParent(user);
		agent.setUserLevel(0);
		agent.setAwardRetStatus(1);
		
		profile.setUserChain("/" + profile.getAccount() + "/");

		userCustomerDao.createUser(agent);
		
		//创建客户对应的资金实现		
		User userEntity = userCustomerDao.getUserByUserName(account);
		UserCreateEvent loginEvent = new UserCreateEvent(userEntity);
		fireFrogEventPublisher.publish(loginEvent);
	}

	@Override
	public void updateGeneralAgentUser(Long userId, Long availableQuota) throws Exception {
		UserAgent agent = new UserAgent();
		agent.setId(userId);
		agent.setAgentLimit(availableQuota);
		userCustomerDao.updateUser(agent);
	}

	@Override
	public User queryUserById(long id) throws Exception {
		return userCustomerDao.queryUserById(id);
	}

	@Override
	public void saveWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		profile.setWithdrawPwd(withdrawPwd);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	@Override
	public void savePasswdAndWithdrawPasswordAndSecurityQA(long id, List<QAInfo> qaList, String withdrawPwd,
			String pwd, int pwdLevel) throws Exception {
		User user = new User();
		user.setId(id);
		UserProfile profile = new UserProfile();
		profile.setQa(qaList);
		profile.setWithdrawPwd(withdrawPwd);
		profile.setPassword(pwd);
		profile.setPasswordLevel(pwdLevel);
		user.setUserProfile(profile);
		userCustomerDao.updateUser(user);
	}

	private boolean isOverAgentUserLimit(UserCustomer puser) throws Exception {
		//如果puser是总代，而且userCount+userTCount>agentLimt 则不可再注册
		if (0 == puser.getUserLvl()) {
			Long agentCount = (puser.getTermAcount() == null ? 0 : puser.getTermAcount()) ;
			Long userCount = (puser.getTermUaccount() == null ? 0 : puser.getTermUaccount()) ;
			if (puser.getAgentLimit() != null) {
				if(puser.getAgentLimit() <= (agentCount+userCount)){
					return true;
				}
			}
		}
		return false;
	}
	private String filterSpecial(String str)   throws   PatternSyntaxException   {     
        //清除掉所有特殊字符   只允许字母、数字、汉字       
		String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
		Pattern p=Pattern.compile(regEx);     
		Matcher m=p.matcher(str);     
		return  m.replaceAll("").trim();     
	} 
	@Override
	public void createUser(UserCustomer user) throws Exception {
		checkNickname(user.getId(),user.getNickname(),false);
		if(StringUtils.isNotEmpty(user.getNickname())){
			user.setNickUpdateTime(DateUtils.currentDate());
		}
        //查询redis里面有没有这个IP存在
        //如果有则正在IP时间限制内
        String str = configService.getConfigValueByKey("global", "login");
        RegisterLoginConfigDto ddto = JsonMapper.nonEmptyMapper().fromJson(str, RegisterLoginConfigDto.class);
        if(ddto.getReregister() != null) {
        	if( ddto.getReregister().intValue() > 0){
            	if (user.getRegisterIp() != null && redis.get("reregister:" + String.valueOf(user.getRegisterIp())) != null) {
            		throw new RegisterServiceException(UserCustomer.IP_LIMIT, "同一IP不能重复注册");
            	}
        	}
        }
		UserCustomer puser = userCustomerDao.getById(user.getParentId());
		UserUrl url = userUrlManager.getById(user.getUrlId());
		//System.out.println("puser.getAwardRetStatus()============"+puser.getAwardRetStatus());
		//设置奖金返点机制是注册时候上级的同样的值
		user.setAwardRetStatus(puser.getAwardRetStatus());
		user.setUserChain(puser.getUserChain() + user.getAccount() + "/");
		if (1 == url.getType()) {
			if (isOverAgentUserLimit(puser)) {
				//超出总代开户的限制不可注册
				throw new OverException();
			}
			//如果是代理的话，为父级代理+1
			if (puser.getUserLvl() == null) {
				puser.setUserLvl(0);
			}
			user.setUserLvl(puser.getUserLvl() + 1);
			puser.setTermAcount((puser.getTermAcount() == null ? 0 : puser.getTermAcount()) + 1);
		} else {
			//如果是普通用户，则返回-1
			if (isOverAgentUserLimit(puser)) {
				//超出总代开户的限制不可注册
				throw new OverException();
			}
			user.setUserLvl(-1);
			puser.setTermUaccount((puser.getTermUaccount() == null ? 0 : puser.getTermUaccount()) + 1);
		}
		//user.setAwardRetStatus(1);//新注册用户默认开启奖金模式
		//上级用户数+1
		userCustomerDao.update(puser);
		userCustomerDao.insert(user);

		User userEntity = VOConverter.customer2User(user);// userCustomerDao.getUserByUserName(user.getAccount());

		//创建客户对应的资金实现		
		UserCreateEvent loginEvent = new UserCreateEvent(userEntity);
		fireFrogEventPublisher.publish(loginEvent);

	//	SendMsg (userEntity);

		if (url.getRegisters() == null) {
			url.setRegisters(1L);
		} else {
			/*if (url.getType() == 0) {
				throw new Exception();
			}*/
			url.setRegisters(url.getRegisters() + 1);
		}
		userUrlManager.update(url);
		UserGameGroup ugg = new UserGameGroup();
		ugg.setUserLvl(user.getUserLvl().longValue());
		ugg.setUserid(user.getId());
		ugg.setUserAwardListStruc(url.getUserawardListStruc());
		Request<UserGameGroup> rq = new Request<UserGameGroup>();
		rq.setHead(new RequestHeader());
		rq.getHead().setUserId(user.getParentId());
		rq.setBody(new RequestBody<UserGameGroup>());
		rq.getBody().setParam(ugg);
		if(HttpJsonClientExt.postJsonObject(game_distribute_group, rq)==null){
			throw new Exception("can't openAccount");
		};
		
		//默认开户链接
		if(user.getUserLvl() != 0 || user.getUserLvl() != -1){
			//createUserUrl(user); 去掉默认开户链接
		}
        //如果ip==null或者ip==0不做限制
        if (user.getRegisterIp() != null && user.getRegisterIp() != 0) {
            try {
                //放到数据库业务执行的最后，防止异常导致插入Redis但是没有注册成功
                //把插入redis有可能产生的异常catch掉防止因为，插入redis的异常导致的注册失败
                //如果没有则放进去一个存货时间等于设置一个防重复登录的时间
                if (ddto.getReregister() != null ) {
                	if(ddto.getReregister().intValue() > 0 ){
                    redis.set("reregister:" + String.valueOf(user.getRegisterIp()),
                            user.getRegisterIp().toString(), Integer.parseInt(ddto.getReregister() == null ? "1" : ddto.getReregister().toString()) * 60 );
                	}
                }
            } catch (Exception e) {
                logger.error("注册IP插入Redis错误：", e);
            }
        }
	}

	private void createUserUrl(UserCustomer user) throws Exception{
		//取得代理最高獎金組
		UserAwardStruc[] userAwardStruc = getUserAwardGroup(user.getId());
		//默认开户链接
		createUserUrl(userAwardStruc,user.getId());
	}
	
	private UserAwardStruc[] getUserAwardGroup(Long userid) throws Exception{
		Request<GameGroupReq> request = new Request<GameGroupReq>();
		request.setHead(new RequestHeader());
		request.getHead().setUserId(userid);
		request.setBody(new RequestBody<GameGroupReq>());
		GameGroupReq gameGroupReq = new GameGroupReq();
		gameGroupReq.setUserId(userid);
		gameGroupReq.setType(1l);
		gameGroupReq.setAwardType(0l);
		request.getBody().setParam(gameGroupReq);
		List<GameGroup> groupList = getGameGroupMax(request);
		UserAwardStruc[] userAwardStrucList = new UserAwardStruc[groupList.size()];
		for(int i = 0 ; i < groupList.size() ; i++){
			userAwardStrucList[i] = VOConverter.gameGroupTouserAwardStruc(groupList.get(i));
		}
		return userAwardStrucList;
	}
	
	//取得代理最高獎金組
	private List<GameGroup> getGameGroupMax(Request<GameGroupReq> request) throws Exception{
		boolean start = true;
		List<GameGroup> groupList = new ArrayList<GameGroup>();
		GameGroups gameGroup = userUrlManager.getGameGroup(request).getBody().getResult();
		for(GameGroup  group : gameGroup.getUserAwardListStruc()){
			if(!start){
				boolean wr = false;
				for(int i = 0 ; i < groupList.size() ; i++){
					if(group.getLotterySeriesCode() == 1 || group.getLotterySeriesCode() == 2){
						if(group.getLotteryId().longValue() == groupList.get(i).getLotteryId().longValue()){
							wr = true;
							if(groupList.get(i).getAwardName().equals("奖金组1500")){
								groupList.remove(i);
								groupList.add(group);
								break;
							}else if(groupList.get(i).getAwardName().equals("奖金组1700") && group.getAwardName().equals("奖金组1800")){
								groupList.remove(i);
								groupList.add(group);
								break;
							}else{
								break;
							}
						}
					}else if(group.getLotterySeriesCode() == 3){
						if(group.getLotteryId().longValue() == groupList.get(i).getLotteryId().longValue()){
							wr = true;
							if(groupList.get(i).getAwardName().equals("奖金组1620")){
								groupList.remove(i);
								groupList.add(group);
								break;
							}else{
								break;
							}
						}
					}
				}
				if(!wr){
					groupList.add(group);
				}
			}else{
				groupList.add(group);
				start = false;
			}
		}
		return groupList;
	}
	
	//創建代理開戶鏈接
	private void createUserUrl(UserAwardStruc[] userAwardStruc,Long userid){
		UserUrl uu = new UserUrl();
		uu.setCreator(userid);
		uu.setGmtCreated(new Date());
		uu.setMemo("默认链接");
		uu.setType(1l);
		uu.setUuid(UUID.randomUUID().toString());
		uu.setUserawardListStruc(userAwardStruc);
		uu.setDays(-1l);
		userUrlManager.save(uu);
		UserUrl nuu = new UserUrl();
		nuu.setUuid(uu.getUuid());
		List<UserUrl> uuids = userUrlManager.getByEntity(nuu);
		for (UserUrl uuid : uuids) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			int days = 365 * 10;
			c.add(Calendar.DATE, days);
			Long pid = Long.valueOf(userid + ""
					+ uuid.getType());
			String key = createKey(uuid.getId(), pid);
			uuid.setUrl("id=" + uuid.getId() + "&exp=" + c.getTimeInMillis()
					+ "&pid=" + pid + "&token=" + key);
			uuid.setCreator(userid);
			userUrlManager.update(uuid);
		}
	}
	/**
	 * 生成url后缀
	 * 
	 * @param id
	 * @param pid
	 * @return
	 */
	private static String createKey(Long id, Long pid) {
		return StringUtils.substring(
				org.apache.commons.codec.digest.DigestUtils.md5Hex(id + "|"
						+ pid), 28);
	}
	
	
	@Override
	public void checkNickname(Long userId,String nickname,boolean isModify) throws Exception {
		if(StringUtils.isNotEmpty(nickname)){
			String nicknameNew=filterSpecial(nickname);
			if(nicknameNew.length()!=nickname.length()){
				throw new Exception("nickname contains special character.---"+nickname);
			}
			
			User oldUser=userCustomerDao.getUserByNickname(nickname);
			if(null!=oldUser){
				if(!oldUser.getId().equals(userId)){
					throw new Exception("nickname has duplicated.---"+nickname);
				}
			}else{
				if(isModify){
					UserCustomer uc=userCustomerDao.getById(userId);
					if(null!=uc && null!=uc.getNickUpdateTime()){
						//修改次数限制
						if(DateUtils.getMonth(uc.getNickUpdateTime())==DateUtils.getMonth(DateUtils.currentDate())){
							throw new Exception("nickname cannot be changed twice in one month.---"+uc.getNickUpdateTime());
						}
					}
				}
			}
		}
	}
	
	@Override
	public boolean islegalAccount(UserCustomer customer) throws Exception {
		
		List<SensitiveWord> sensitiveWordList = globalSensitiveWordServiceImpl.quaryListByType(0L);
		if (customer.getAccount() != null && !customer.getAccount().equals("")) {
			for (SensitiveWord sensitiveWord : sensitiveWordList) {
				if (customer.getAccount().contains(sensitiveWord.getWord())) {
					return false;
				}
			}
		}
		
		User user= userCustomerDao.getUserByUserName(customer.getAccount());
		if(user!=null){
			throw new UserExistServiceException("The user account "+customer.getAccount()+"is exists ,please change the account");
		}
		
		
		return true;
	}
	
	public void SendMsg (User userEntity) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("platform", platformName);
		msgToMQ.addMessageToMq(userEntity, NoticeTaskEnum.Register, map);
	}

@Override
	public Long userByName(String account) throws Exception {
		return userCustomerDao.userByName(account);
	}
	
	@Override
	public void createPtUser(Long id,Long parentId) throws Exception {
		UserPtRegister userPt = new UserPtRegister();
		userPt.setUserId(id);
		userPt.setParentId(parentId);
		userPt.setType(1L);
		userPtRegisterDao.insertPtRegister(userPt);
}
	
	@Override
	public UserActivityRegisterResponse getPtUser(UserPtRegister userPtRegister) throws Exception {
		UserActivityRegisterResponse response = new UserActivityRegisterResponse();
		UserPtRegister model = userPtRegisterDao.getPtRegister(userPtRegister);
		
		if(model != null){
			response.setUserId(model.getUserId());
			response.setParentId(model.getParentId());
			response.setType(model.getType());
		}
		
		return response;
	}
	
	@Override
	public List<User> queryUserParent(String username) throws Exception {
		return userCustomerDao.queryUserParent(username);
	}

	@Override
	public boolean checkIsCurrentUsersChild(Long currentUserId, Long childUserId)
			throws Exception {
		User currentUser = userCustomerDao.queryUserById(currentUserId);
		User childUser = userCustomerDao.queryUserById(childUserId);
		String currentUserChain = currentUser.getUserProfile().getUserChain();
		String childUserChain = childUser.getUserProfile().getUserChain();
		return childUserChain.indexOf(currentUserChain)>-1;
	}
}
