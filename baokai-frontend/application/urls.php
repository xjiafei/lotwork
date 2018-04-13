<?php
require_once "include/Common.php";
require_once "include/geoip.inc";
return objectToArray ( new Urls () );
class Urls {
    
    // 44个接口 已实现17个 其中 1 个接口有问题
    /**
     * **********************default模块*********************************
     */
    // public $service = array (
    // 'default' => 'http://192.168.100.200:801/user/login'
    // );
	public $service = array(
			/*查询用户通知任务*/
			'queryNoticeTask'=>'/noticeAdmin/queryNoticeTask',
			'queryNoticeTaskForUser'=>'/noticeAdmin/queryNoticeTaskForUser',
			/*设置用户通知任务*/
			'bindUserNoticeTask'=>'/noticeAdmin/bindUserNoticeTask',
	);
    public $index = array(
    		'queryIndexInfo' => '/index/queryIndexInfo'
    );
    public $proxy = array(
    	//代理首页报表数据
    	'queryUserAgentCount' => '/user/agent/queryUserAgentCount',
    	//团队汇总数据
    	'queryUserCount' => '/user/agent/queryUserCount',
        /* 4.5.2.1    查询客户下级代理请求 */
        'searchLowLevelProxy' => '/user/profile/querySubAgentList',
        /* 4.5.2.3    查询客户下级客户请求 */
        'querySubCustomerList' => '/user/profile/querySubCustomerList',
        /*     4.5.2.5    查询客户下级请求 */
        'querySubUser' => '/user/profile/querySubUser',
    	//查询所有下级
        'querySubUsers' => '/user/profile/querySubUsers',
        /*查询用户根据用户名*/
        'getUserByAccount'=>'/user/getUserByAccount',
        'getUserByName'=>'/user/profile/queryUserDetailInfoByAccount',
    	/*修改奖金组返点*/
        'changeBonus'=>'/user/profile/changeBonus',
    	/*修改玩法返点*/
        'modifyGameUserAward'=>'/game/modifyGameUserAward',
    	
    );
    public $activity = array(
    	'migrateById'=>'/user/profile/migrateById',
        // 砸蛋 2015.06
    	'eggclick'=>'/fund/api/game/action',
		//寫活動 log
    	'log' => '/fund/api/game/saveActivityLog',
        //super2000活動
        'super2000' => '/index/super2000Activity',
        //vip acitivity
        'application' => '/activity/vip',
		//2016.05拉霸活動
		'maylabar' => '/activity/doublebox/initData',
		'labaraward' => '/activity/doublebox/award',
    	'activityIndex'=> '/activity/activityIndex',
		//2016.08奧運活動
		'Olympic' => '/activity/auguest/initOlympic',
		'Olympicsign' => '/activity/auguest/signUp',
        //2016.08搶紅包活動 基本資料
        'Qianghongbao' => '/activity/auguest/initRedEnvelope',
        //2016.08搶紅包活動 中獎金額
        'Qianghongbaoprize' => '/activity/auguest/getRedEnvelope',
    	//2016.09燈塔活動
    	'lightdata' =>'/activity/20160902/getActivityStep2Info',
    	'openDraw'=>'/activity/20160902/openDraw',
    	//2016.09 金秋宝开千元大放送
    	'Septweek3' => '/activity/20160903/getUserData',
    	'getVipLvl' => '/activity/20160903/getVipLvl',
        //2016.10 十月第一週
        'octAcivity1info' =>'/activity/octActivity/info',
        'octAcivity1ShotKill' => '/activity/octActivity/shotKill',
        //2016.10 十月第二週
        'Oct2Info' =>'/activity/octActivity/oct2Info',
        'Oct2SignUp' => '/activity/octActivity/oct2SignUp',
        //2016.10 十月第三週
        'Oct3Info' =>'/activity/octActivity/oct3Info',
        'Oct3SignUp' => '/activity/octActivity/oct3SignUp',
        //2016.10 十月第四週
        'Oct4Info' =>'/activity/octActivity/oct4Info',
        'Oct4SignUp' => '/activity/octActivity/oct4SignUp',
        'Oct4Addr' => '/activity/octActivity/oct4Addr',
		//11月紅包活動
    	'redBowInit' => '/index/redBowConfigActivity',
		'novRedBow' => '/index/redBowActivity',
		'novSignUp' => '/activeapi/signUp',
		'novQuerySignUp' => '/activeapi/20161201/querySignUp20161201',
		'redbow3chekbets' => '/activeapi/queryTodayTotalBets',
    	'checkActivityId' => '/index/checkActivityId',
    	'nov4Query' => '/activeapi/queryActivity201611WeekFour',
		//12月紅包活動第一周
		'decSignUp' => '/activeapi/20161201/decSignUp',
		//12月紅包活動第二周
		'decSignUp02' => '/activeapi/20161202/decSignUp', //參加報名
		'decQuerySignUp' => '/activeapi/20161202/querySignUp20161201',//報名查詢
		//12月紅包活動第三周
		'decSignUp03' => '/activeapi/20161203/decSignUp', //參加報名
		'decQuerySignUp03' => '/activeapi/20161203/querySignUp20161201', //報名查詢
		//12月紅包活動第四周
		'querygrouptime04' => '/activeapi/group/20161204/queryGroupTimeSetting', //時間查詢
		'decSignUp04' => '/activeapi/20161204/decSignUp', //參加報名
		'decQuerySignUp04' => '/activeapi/20161204/querySignUp20161201', //報名查詢
		'decQuerySignUp_get200' => '/activeapi/20161204_get200/getUser200', //取200名
		'decQuerySignUp_rank' => '/activeapi/20161204_getrank/getUserRank', //取得用戶排名
		'decGroupQuery' => '/activeapi/group/20161204/decQuery',
		'groupDrawListQuery' => '/activeapi/group/20161204/drawListQuery',
		'queryJanSignUp' => '/activeapi/group/queryJanSignUp',
		'janSignUp' => '/activeapi/group/janSignUp',
		//2017 1月紅包活動
        'janRedBowCheck' => '/activeapi/janredbowcheck/',
        'janRedBowSave' => '/activeapi/janredbowsave/',
		//2017 春節充值送兩倍
		'newYearChargeDoubleInit' => '/activeapi/getUserFundCharge/',
		'newYearChargeDoubleGetAward' => '/activeapi/getCNYFirstMoney/',
		//2017 春節活動整點來抽獎
		'newYearLotteryInit' => '/activeapi/2017_CNY_2/initialData/',
		'newYearLotteryLottery' => '/activeapi/2017_CNY_2/lottery/',
		//2017 春節你充我就送
		'newYearChargeGiveInit' => '/activeapi/2017_CNY_3/initFundAndRedbowRecord/',
		'newYearChargeGiveRedBowAward' => '/activeapi/2017_CNY_3/redBowAward/',
		//2017 春節vip領紅包
		'newYearVipInit' => '/activeapi/cny2017VipRedBowCheck/',
		'newYearVipRedBowSave' => '/activeapi/cny2017VipRedBowSave/',
		//2017 春節活動時間
		'newYearDateCheck' => '/activeapi/cny2017DateCheck/',
		//2017 2月開春活動 开春签到送礼
		'openSpringInit' => '/activeapi/170201/springSignIn',
		//2017 2月开春活动 查询红包状态
		'openSpringRedbowState' => '/activeapi/170201/isReached',
		//2017 2月开春活动 红包开奖
		'openSpringAward' => '/activeapi/170201/award',
		//2017 2月活动2 報名查詢
		'querySignUp20170202' => '/activeapi/170202/querySignUp201702',
		//2017 2月活动3 報名查詢
		'querySignUp20170203' => '/activeapi/17020301/querySignUp201702',
		//2017 2月活动2 參加報名
		'signUp20170202' => '/activeapi/170202/signUp201702',
		//2017 2月活动3 參加報名
		'signUp20170203' => '/activeapi/17020301/signUp201702',
		'activity20170301init' => '/activeapi/2017_03_01/activity20170301init',
		'activity20170301award' => '/activeapi/2017_03_01/activity20170301award',
		//2017 3月活动4 
        'activity20170304init' => '/activeapi/2017_03_04/activity20170304init',
		//2017 3月活动4 參加報名
        'activity20170304signup' => '/activeapi/2017_03_04/activity20170304signUp',
		//2017 4月活動 問卷調查，浮層初始化
		'activity201704surveyEnter' => '/activeapi/2017_05_SUV/activity201704surveyEnter',
		//2017 4月活動 問卷調查
		'activity201704surveyInit' => '/activeapi/2017_05_SUV/activity201704surveyInit',
        'activity20170304signup' => '/activeapi/2017_03_04/activity20170304signUp',
        //2017 4月活动2 
        'activity20170402init' => '/activeapi/2017_04_02/activity20170402init',
        //2017 4月活动2 領獎
        'activity20170402award' => '/activeapi/2017_04_02/activity20170402award',
		//2017 4月活動 問卷調查 檢查用戶投注總額
		'checkUserBetTotal' => '/activeapi/2017_05_SUV/checkUserBetTotal',
		//2017 4月活動 問卷調查 檢查用戶投注總額
		'startaward' => '/activeapi/2017_05_SUV/startAward',
        //2017 4月活动4 
		'activity20170404init' => '/activeapi/2017_04_04/activity20170404init',
		//2017 4月活动4 參加報名
		'activity20170404signup' => '/activeapi/2017_04_04/activity20170404signUp',
		//2017 5月活动1 
		 'activity20170501init' => '/activeapi/2017_05_01/activity20170501init',
		//2017 5月活动1 參加報名
		'activity20170501signup' => '/activeapi/2017_05_01/activity20170501signUp',
		 //2017 5月活动1 領獎
        'activity20170501award' => '/activeapi/2017_05_01/activity20170501award',
		//2017 5月活动1 取倍數
        'activity20170501rate' => '/activeapi/2017_05_01/activity20170501rate',
		//2017 5月活动3 
		'activity20170503init' => '/activeapi/2017_05_03/activity20170503init',
		//2017 5月活动3 參加報名
		'activity20170503signup' => '/activeapi/2017_05_03/activity20170503signUp',
		//2017 6月活动1 
		'activity20170601init' => '/activeapi/2017_06_01/activity20170601init',
		//2017 6月活动1 參加報名
		'activity20170601signup' => '/activeapi/2017_06_01/activity20170601signUp',
		//2017 6月活动3 
		'activity20170603init' => '/activeapi/2017_06_03/activity20170603init',
		//2017 6月活动3 參加報名
		'activity20170603signup' => '/activeapi/2017_06_03/activity20170603signUp',
		//2017 6月活动3 派獎
		'activity20170603award' => '/activeapi/2017_06_03/activity20170603award',
		//2017 7月活动1 
		'activity20170701init' => '/activeapi/2017_07_01/activity20170701init',
		//2017 7月活动1 參加報名
		'activity20170701signup' => '/activeapi/2017_07_01/activity20170701signUp',
		//2017 7月活动1 浮層初始化
		'activity20170701surveyEnter' => '/activeapi/2017_07_01/activity20170701surveyEnter',
    );
	
    public $login = array(
        /* 登陆页面 */
        'default' => '/user/login',
        'safelogin'=>'/user/profile/queryUserByName',
    	//获取登陆页广告位展位图
        'getAdSpaceById'=>'/adAdmin/getAdSpaceById',
		'getAllAdSpace'=>'/adAdmin/getAllAdSpace',
		
    );
    public $member = array(
        'savePass'=>'/user/profile/savePassword',
    );
    public $mail = array(
        'sendmail'=>'/user/email/sendEmail', //直接发送邮件接口暂停使用
    	'sendNotice' => '/noticeAdmin/sendNotice'//调用发送信息的接口
    );
    public $accountsSecurity = array(
            
            /* 个人资料加载页面 */
            'default' => '/user/profile/queryUserProfile',
            /* 个人资料保存 */
            'personalinfosmt' => '/user/profile/saveUserProfile',
			'checknickname' => '/user/profile/checkNickname',
            /* 绑定邮箱 */
            'bindmail' => '/user/profile/saveEmail',
            /* 设置验证预留信息 */
            'saveCipher' => '/user/profile/saveCipher',
    		//修改验证预留信息
            'modifyCipher' => '/user/profile/modifyCipher',
            /*修改登录密码 */
            'changepwd' => '/user/profile/savePassword',
            /*更新回收密码记录 */
            'updateUserRecyclePwdFlag' => '/user/updateUserRecyclePwdFlag',
            /*修改安全密码 */
            'modifyWithdrawPassword'=>'/user/profile/modifyWithdrawPassword',
            //设置安全密码
    		'saveWithdrawPassword'=>'/user/profile/saveWithdrawPassword',
            /*设置安全问题 */
            'saveSecurityQA' => '/user/profile/saveSecurityQA',
    		/*修改安全问题*/
            'safequestedit' => '/user/profile/modifySecurityQA',
            /* 按用户名查询用户信息 */
            'queryUserByName' => '/user/profile/queryUserByName' ,
            /* 4.4.3    安全中心首页个人信息查询 */
            'queryUserSecurityInfo' => '/user/profile/queryUserSecurityInfo',
            /* 4.10.5    冻结用户(1.2) */
            'freezeUser' => '/user/freezeUser',
            /*4.5.4	更新安全问题以及安全密码*/
            'saveSafePwdQuest' => '/user/profile/saveWithdrawPasswordAndSecurityQA' ,
    		/* 4.5.4	更新安全问题以及安全密码以及登陆密码 */
    		'savePasswdAndWithdrawPasswordAndSecurityQA' => '/user/profile/savePasswdAndWithdrawPasswordAndSecurityQA',
    		/* 4.5.14.1 绑定安全中心*/
    		'bindSecurityCard'=>'/user/profile/bindSecurityCard',
    		/* 4.5.14.2 修改安全中心绑定*/
    		'updateSecurityCard'=>'/user/profile/updateSecurityCard',
    		/* 4.5.14.3 解除安全中心绑定*/
    		'unbindSecurityCard'=>'/user/profile/unbindSecurityCard',
    		/* 4.5.14.4 获取宝开安全中心验证码*/
    		'getSecurityCardNumber'=>'/user/profile/getSecurityCardNumber',
    		/* 4.5.14.5检测宝开安全中心序列号是否已经绑定*/
    		'checkSecurityCardNumber'=>'/user/profile/checkSecurityCardNumber',
    );
    public $appeal = array(
            /* 4.7.1 帐号申诉请求 (暂未通过)*/
            'userappeal' => '/user/userAppeal' 
    );
    public $message = array(
            /* 4.8.1.1    获取站内信请求*/
            'querymessagelist' => '/user/message/queryMessageList',
    		/* 4.9.1.3	标记为已读请求 */
            'markRead' => '/user/message/markRead',
            /* 4.8.1.5    删除站内信请求 */
            'deleteMessages' => '/user/message/deleteMessages',
            /* 4.8.1.7    给上下级发信请求 */
            'sendMessage' => '/user/message/sendMessage',
            /* 4.8.1.11    回复站内信聚合请求 */
            'replyMessage' => '/user/message/replyMessage' ,
             /* 1.1.1.1    查询站内信聚合请求 */
            'queryMessageDetail' => '/user/message/queryMessageDetail' ,
    		/* 查询未读站内信列表 */
    		'queryUnReadMessageList'=>'/user/message/queryUnReadMessageList'
    );
    public $message2 = array(
            /* 4.8.1.1    获取站内信请求*/
            'querymessagelist' => '/user/message2/queryMessageList',
    		/* 4.9.1.3	标记为已读请求 */
            'markRead' => '/user/message2/markRead',
            /* 4.8.1.5    删除站内信请求 */
            'deleteMessages' => '/user/message2/deleteMessages',
            /* 4.8.1.7    给上下级发信请求 */
            'sendMessage' => '/user/message2/sendMessage',
            /* 4.8.1.11    回复站内信聚合请求 */
            'replyMessage' => '/user/message2/replyMessage' ,
             /* 1.1.1.1    查询站内信聚合请求 */
            'queryMessageDetail' => '/user/message2/queryMessageDetail' ,
            /* 查询未读站内信列表 */
            'queryUnReadMessageList'=>'/user/message2/queryUnReadMessageList',
            /* 查询未读站内信推送列表*/
            'queryUnreadNoticePushMsgs'=>'/user/message2/queryUnreadNoticePushMsgs',
            /* 站内信推送标记为已读*/
            'markNoticeRead' => '/user/message2/markNoticeRead',
    );
    
    public $fund =array(
    		/* 获取用户银行卡已经绑卡信息 */
    		'queryallbank' => '/fund/queryAllBank',
                                        /* 获取可充值申訴的银行卡 */
    		'getCanAppealBanks' => '/fund/getCanAppealBanks',
    		/* 查询用户绑卡请求 */
    		'queryboundbankcard'=>'/fund/queryBoundbankCard',
    		/* 提交充值请求 */
    		'chargeapply' =>'/fund/chargeApply',
    		/* 提现初始化请求 */
    		'initfundwithdraw'=>'/fund/initFundWithdraw',
    		/* 发起提现请求 */
    		'applyfundwithdraw' => '/fund/applyFundWithdraw',
    		/*转账初始化请求*/
    		'fundtransferinit' => '/fund/fundTransferInit',
    		/* 转账请求 */
    		'fundtransfer'=> '/fund/fundTransfer',
    		/*获取通用配置接口*/
    		'getconfigvaluebykey' => '/common/getConfigValueByKey',
    		/*获取提现配置*/
    		'getconfigvaluebywithdraw' => '/common/getConfigValueByKey/withdraw',
    		/*审核配置*/
    		'getconfigvaluebywithdrawCheck'=> '/common/getConfigValueByKey/withdrawCheck',    		
    		/*申请绑卡*/
    		'applybankcardbind' => '/fund/applyBankCardBind',
    		/*删除绑卡*/
    		'unbindbankcard' => '/fund/unbindBankCard',
                                             /*立即锁定*/
                                             'bankCardNowLock' => '/fund/bankCardNowLock',
    		/*获取充值记录*/
    		'chargequery' => '/fund/chargeQuery',
    		/*获取提现记录*/
    		'queryfundwithdraworderbyuserId' =>'/fund/queryFundWithdrawOrderByUserId',
                                             /*获取提现申訴詳情*/
		'queryWithdrawAppealByRootSn'=>'/fund/queryWithdrawAppealByRootSn',
		/*获取提现记录BySn*/
    		'queryfundwithdraworderbySn' =>'/fund/queryFundWithdrawOrderBySn',
		/*更新提現申請單對應的提現申訴單狀態*/
    		'updateWithdrawAppealbySn' =>'/fund/updateWithdrawAppealbySn',
    		'searchLog' =>'/fund/searchLog',  
		/*更新提現申請單對應的提現申訴單狀態*/
    		'updateWithdrawAppealbySn' =>'/fund/updateWithdrawAppealbySn',
    		/*获取转账记录*/
    		'queryfundtranferrecordbycriteria' =>'/fund/queryFundTranferRecordByCriteria',
    		/*获取账户明细*/
    		'queryFundChangeLog' => '/fund/changeLog/queryFundChangeLog',
    		/*获取前台账户明细*/
    		'queryFrontFundChangeLog' => '/fund/changeLog/queryFrontFundChangeLog',
    		/*查询账户余额*/
    		'getuserfundinfo' => '/fund/getUserFundInfo',
    		//实时查询用户可用余额的接口
    		'getUserBal' => '/fund/getUserBal',
    		/*查询银行卡是否已经绑定*/
    		'checkbankcardbind' => '/fund/checkBankCardBind',
			/*提款申訴*/
			'queryAppealWithdrawList'=>'/fund/queryAppealWithdrawList',    		
    		//取消订单
    		'chargeCancel'=>'/fund/chargeCancel',
    		//查询有效期内未成功的订单
    		'haveChargeItem'=>'/fund/haveChargeItem',
    		//提现白名单
    		'whiteList'=>'/fund/whiteList',
              //充提申诉进度查询
              'queryFundAppealStatus'=>'/fund/queryFundAppealStatus',
              //充提申诉列表查询
              'queryRechargeList'=>'/fund/appeal/queryRechargeList',
              //充提申诉存檔
              'appealRecharge'=>'/fund/appeal/appealRecharge',
              //充值申訴
    		   'checkChargeOpen'=>'/fund/checkChargeOpen',
    		 //Vip和普通用戶是否可充值判斷
    		 'checkdaylimit'=>'/fund/checkdaylimit',
			 //微信支付限制
			 
			 
			 
			 
			 //第三方充值用户限额
			 'chargeThirdPartyLimit' =>'/fund/chargeThirdPartyLimit',
			  //銀行(支付寶)單日金額限额
			 'checkBankDayLimit' =>'/fund/checkBankDayLimit',
			 
			 
    );
    public $prize = array(
    	//初始化开户
    	'initCreateUrl' => '/user/url/initCreateUrl',
    	//开户分配奖金组
    	'openAccountDetailedConfigAwardGroup' => '/winter-game/game/openAccountDetailedConfigAwardGroup',
    	//创建链接
    	'createURL'   => '/user/url/create',
    	//链接管理接口
    	'urlList'    => '/user/url/list',
    	//创建用户
    	'createUser' => '/user/profile/createUser',
		//创建用户 PT自動註冊
		'createPtUser' => '/user/profile/createPtUser',
    	//检测注册用户名是否合法
    	'islegalAccount' => '/user/profile/islegalAccount',
    	//修改备注
    	'modifyMemo' => '/user/url/modifyMemo',
    	//根据注册链接 获取开户数据
    	'queryUrl' => '/user/url/queryUrl',
    	//根据ID获取链接详情
    	'geturlById' => '/user/url/getById',
    	//根据ID删除链接
    	'deleteurlbyid' => '/user/url/delete',
    	//检测链接是否已经被注册
    	'checkRegisted'=>'/user/url/checkRegisted',
    	//获取链接注册用户
    	'getRegisters' => '/user/url/getRegisters',
    	/*获取玩法*/
    	'queryUserGameAward'=>'/game/queryUserGameAward',
    );
    public $remark=array(
    	//取消锁定
    	'cancelLock' => '/fund/cancelLock',
    	//取消唯一附言
    	'cancelRemark' => '/fund/cancelRemark',
    	//检查附言是否存在
    	'checkRemarkExist' => '/fund/checkRemarkExist',
    	//导入用户
    	'exportUsers' => '/fund/exportUsers',
    	//获取所有的回收列表
    	'getAllRecyleRemarks' => '/fund/getAllRecyleRemarks',
    	//获取所有的附言
    	'getAllRemarks' => '/fund/getAllRemarks',
    	//获取用户唯一附言
    	'getRemark' => '/fund/getRemark',
    	//获取修改时间
    	'getModifiedDays' => '/fund/getModifiedDays',
    	//设置最大可修改日期
    	'setCanModifiedDays' => '/fund/setCanModifiedDays',
    	//获取下一个系统附言
    	'getNextSystemRemark' => '/fund/getNextSystemRemark',
    	//保存附言
    	'saveRemark' => '/fund/saveRemark',
    	//修改附言
    	'updateRemark' => '/fund/updateRemark',
    	//回收附言
    	'recyleRemark' => '/fund/recyleRemark',
    );
	public $lottery=array(
    	//取得下架彩種
    	'stopLottery' => '/game/queryGameSeries',
    );
    
    
    
    /**
     * **********************************admin模块********************************
     */
    public $login_adm = array(
            /*4.9    管理员登陆*/
            'adminLogin' => '/user/adminLogin'
    );
    public $queryUserByCriteria_proxy = array(
            
            /* 4.10.1    客户条件查询(1.1)*/
            'queryUserByCriteria' => '/user/profile/querySubUserByCriteria',
            /* 4.10.2    管理用户详情(1.11) */
            'queryUserDetailInfo' => '/user/profile/queryUserDetailInfo',
            /* 4.10.7    按复杂条件查询下级用户(1.5) */
            'querySubUserByCriteria' => '/user/profile/querySubUserByCriteria',
			/* 4.10.11   下載下級報表 */
			'querySubUserForDownClist' => '/user/profile/querySubUserForDownClist',
            /* 4.11.3    账号申诉查询(2.2|2.22) */
            'queryUserAppealByCriteria' => '/user/queryUserAppealByCriteria' ,
            /* 4.10.8    查询总代用户(1.71) */
            'queryGeneralAgent' => '/user/profile/queryGeneralAgent' ,
            /* 4.10.9    更新开户配额 */
            'updateGeneralAgentBalance' => '/user/profile/updateGeneralAgentBalance',
             /* 4.10.10    总代开户(1.72) */
            'createGeneralAgent' => '/user/profile/createGeneralAgent' ,
    		//'querySubUserByCriteria'=>'/user/profile/querySubUserByCriteria'
    		//设置用为VIP
    		'setVip' => '/user/profile/setVip',
    		/*根据多个用户名来查询用户名的有效性*/
    		'queryUserDetailInfoByAccounts' => '/user/profile/queryUserDetailInfoByAccounts',
    		
    		//奖金模式查看接口
    		'queryBizSwitch' => '/user/profile/queryBizSwitch',
    		//奖金模式修改接口
    		'bizSwitch' => '/user/profile/bizSwitch',
    )
    ;
    public $freezeUser = array(
            /* 4.10.5    冻结用户(1.2) */
            'freezeUser' => '/user/freezeUser' ,
            /*解冻用户*/
            'unFreezeUser' => '/user/unFreezeUser', 
            'freezeUserlist'=>'/user/queryUserFreeze',
            'queryUserFreezeLog'=>'/user/queryUserFreezeLog'
    )
    ;
    public $queryUserAppealDetail = array(
            /* 4.11.4    用户申诉详情查询(2.21) */
            'queryUserAppealDetail' => '/user/queryUserAppealDetail',
            /* 4.11.5    客服填写审核资料(2.21) */
            'userAppealAudit' => '/user/userAppealAudit' 
    );
    
    public $tool = array(
        //4.8.1	生成工具类
        'init'=>'/tools/initAction',
        //4.8.1	更新工具类
        'update'=>'/tools/updateAction',
        //4.8.1	访问工具类
        'get'=>'/tools/getAction',
    );
    public $fundadmin = array(
    	//4.25.1	条件搜索异常数据
    	'exceptionQuery'=>'/fund/exceptionQuery',	
    	//锁定异常充值业务单
    	'yuchuli'=> '/fund/yuchuli',
    	//释放异常充值业务单
    	'yuchuliEnd'=> '/fund/yuchuliEnd',
    	//锁定风险提现业务单
    	'yuchulidraw'=> '/fund/yuchuliWithDraw',
    	//释放风险提现业务单
    	'yuchulidrawEnd'=>'/fund/yuchuliWithDrawEnd',
		//风险提现业务单處理中退款
    	'yuchulidrawEndRefund'=>'/fund/yuchuliWithDrawEndRefund',
		/*人工处理提现*/
		'manualWithdraw' =>'/fund/manualWithdraw',
			/*人工完成提现*/
		'manualFinish' =>'/fund/manualFinish',
    	//4.25.5	处理方式之加游戏币
    	'exceptionAddGameCoin'=>'/fund/exceptionAddGameCoin',	
    	//4.25.7	处理方式之退款和补充信息待复审
    	'exceptionRefund'=>'/fund/exceptionRefund',
    	//审核不通过
    	'exceptionRefundReject'=>'/fund/exceptionRefundReject',
    	//4.25.3 处理方式之没收
    	'exceptionConfiscate'=>'/fund/exceptionConfiscate',
        //获取mow退款状态，更新异常充值状态
        'checkAndUpdateReFundStatus'=>'/fund/checkAndUpdateReFundStatus',
    	//4.8.1 获取充值记录
    	'chargeQuery'=>'/fund/chargeQuery',
    		//4.9.1 获取提现记录
    	'queryFundWithdrawOrder'=>'/fund/queryFundWithdrawOrder',
    		//4.9.2 获取提现记录
    	'queryFundWithdrawOrderBySn'=>'/fund/queryFundWithdrawOrderBySn',
		//4.9.2 查詢MOW AND更新提現訂單狀態
    	'checkAndUpdateWithDrawMowStatus'=>'/fund/checkAndUpdateWithDrawMowStatus',
		//4.9.3 获取提现訂單詳情 
    	'queryWithDrawNowDetail'=>'/fund/queryWithDrawNowDetail',
		//4.9.4 获取退款记录
    	'queryReFundWithdrawOrder'=>'/fund/queryReFundWithdrawOrder',
    		//4.23.1 首页打款列表
    	'depositApply'=>'/fund/depositApply',
    	//批量人工操作新加接口
    	'depositApplys'=>'/fund/depositApplys',
    	//撤销人工充值订单接口
    	'adChargeCancel'=>'/fund/adChargeCancel',
    	//4.24.1 账户余额调整	
    	'queryFundAdjustRecordByStatus'=>'/fund/queryFundAdjustRecordByStatus',
    	//4.24.3加减请求
    	'fundAdjustApply'=>'/fund/fundAdjustApply',
    	//4.24.5 审核备注
    	'remarkFundAdjust'=>'/fund/remarkFundAdjust',
    	//4.24.7 审核
    	'auditFundAdjust'=>'/fund/auditFundAdjust',
    	//4.21.1 用户绑卡记录
    	'queryBankCardRecords'=>'/fund/queryBankCardRecords',
    	//4.21.5 后台管理员加解锁
    	'lockBankCard'=>'/fund/lockBankCard',
    	//单用户绑卡记录
    	'queryBankCardHistory'=>'/fund/queryBankCardHistory',
    	//获取通用配置接口
    	'getconfigvaluebykey' => '/common/getConfigValueByKey',
    	//获取提现配置
    	'getconfigvaluebywithdraw' => '/common/getConfigValueByKey/withdraw',
	//審核提示配置99
    	'getconfigvaluebyexaminetips' => '/common/getConfigValueByKey/examinetips',
	//申訴提示配置
    	'getconfigvaluebyarguetips' => '/common/getConfigValueByKey/arguetips',
	//後台提款申訴處理
	'queryAppealList'=>'/fund/queryAppealList',
	//後台提款申訴審核通過和審核不通過
	'updateAppealbySn'=>'/fund/updateAppealbySn',
                 //後台待複審先查詢有無此筆訂單
	'queryAppealbySn'=>'/fund/queryAppealbySn',
    	//审核配置
    	'getconfigvaluebywithdrawCheck'=> '/common/getConfigValueByKey/withdrawCheck',
    	//返回绑定银行卡数目
    	'getconfigvaluebychargeCoute'=> '/common/getConfigValueByKey/chargeCoute',
    	//返回充值倒计时
    	'getconfigvaluebychargeCountDown'=> '/common/getConfigValueByKey/chargeCountDown',
    	//返回不可提现额度配置 
    	'getconfigvaluebycharge'=> '/common/getConfigValueByKey/charge',
    	//返回转账配置接口
    	'getconfigvaluebytransfer'=> '/common/getConfigValueByKey/transfer',
    	//获取可提现配置
    	'getconfigvaluebybet'=> '/common/getConfigValueByKey/bet',
    	//通用保存配置文件接口
    	'saveConfig'=>'/common/saveConfig',
    	//保存提现配置接口
    	'saveConfigWithdraw' => '/common/saveConfig/withdraw',
		//保存審核提示配置接口
    	'saveConfigExaminetips' => '/common/saveConfig/examinetips',
		//保存申訴提示配置接口
    	'saveConfigArguetips' => '/common/saveConfig/arguetips',
    	//获取审核配置接口
    	'saveConfigWithdrawCheck' => '/common/saveConfig/withdrawCheck',
    	//保存银行卡绑定数目
    	'saveConfigchargeCoute' => '/common/saveConfig/chargeCoute',
    	//保存倒计时
    	'saveConfigchargeCountDown' => '/common/saveConfig/chargeCountDown',
    	//不可提现额度配置
    	'saveConfigcharge' => '/common/saveConfig/charge',
    	//转账配置
    	'saveConfigtransfer' => '/common/saveConfig/transfer',
    	//可提现额度配置
    	'saveConfigbet' => '/common/saveConfig/bet',
    	//可疑银行卡
    	'querySuspiciousCards'=>'/fund/querySuspiciousCards',
    	//查询可疑银行卡是否已添加
    	'checkSuspiciousCard' => '/fund/checkSuspiciousCard',
    	//删除请求
    	'deleteSuspiciousCards'=>'/fund/deleteSuspiciousCards',
    	//添加请求
    	'addSuspiciousCards'=>'/fund/addSuspiciousCards',
    	//审核备注
    	"depositRemark"=>"/fund/depositRemark",
    	//人工单审核
    	"depositAudit"=>"/fund/depositAudit",
    	//批量人工单审核
    	"depositAudits"=>"/fund/depositAudits",
    	//客户提取审核
    	"auditFundWithdraw"=>"/fund/auditFundWithdraw",
    	//客户提现复审
    	"auditFundWithdraw2"=>"/fund/auditFundWithdraw2",
		//客户提现退款
    	"auditRefund"=>"/fund/auditRefund",
    	//客户提取备注
    	"withdrawRemark"=>"/fund/withdrawRemark",
		//银行卡列表
    	"bankParamsSet"=>"/fund/bankParamsSet",
    	//异常审核备注
    	"exceptionAuditRemark"=>"/fund/exceptionAuditRemark",
		//银行卡绑定配置
    	"chargeCoute"=>"/fund/chargeCoute",
    	//审核配置
    	"withdrawCheck"=>"/fund/withdrawCheck",
    	//转账配置
    	"transfer"=>"/fund/transfer",
    	//充值配置
    	"charge"=>"/fund/transfer/charge",
    	//查询所有银行卡
    	"queryAllBank"=>"/fund/queryAllBank",
    	//充值倒计时
    	"configvaluebykey"=>"/common/getConfigValueByKey",
    	//新增银行
    	"addBank"=>"/fund/addBank",
    	//编辑银行
    	"updateBank"=>"/fund/updateBank",
    		
    	// 4.20.3 处理请求
    	"depositApply"=>"/fund/depositApply",
    	//
    	"depositQuery"=>"/fund/depositQuery",
    	//转账明细
        "deposit"=>"/fund/report/deposit",
    	//新转账明细报表
        "userFundTransfer"=>"/fund/report/userFundTransfer",
    	//获取转账记录
    	"queryFundTranferRecordByCriteria"=>"/fund/queryFundTranferRecordByCriteria",
    	//查询绑卡
    	"queryBoundbankCard"=>"/fund/queryBoundbankCard",
    	//充提报表
    	"withdrawAndCharge"=>"/fund/report/withdrawAndCharge",
    	//获取账户明细
    	'queryFundChangeLog' => '/fund/changeLog/queryFundChangeLog',
    	//总代充提表
    	'queryTopChargeWithdrawRpt' => '/user/agent/queryTopChargeWithdrawRpt',
    	//总代游戏币明细表
    	'getGameGoldDetails' => '/fund/getGameGoldDetails',
    	//总代盈亏报表玩法列表查询
    	'getBetTypes' => '/fund/getBetTypes',
    	//总代盈亏报表查询
    	'getUserAgentIncomes' => '/fund/getUserAgentIncomes',
    	//总代盈亏下级报表查询
    	'getUserAndAgentIncomes' => '/fund/getUserAndAgentIncomes',
    	//玩法明细 lotteryId 
    	'getUserBetIncomes' => '/fund/getUserBetIncomes',
	//查詢審核提示配置
    	'searchDrawTips' => '/fund/searchDrawTips',
    	//增加審核提示配置    		
    	'addTips' =>	'/fund/addTips',
    	//查詢申訴提示配置    		
    	'searchAppealTips' =>'/fund/searchAppealTips',
    	//查詢緊急發布	
    	'searchUrgency' => '/fund/searchUrgency',	
    	//新增緊急發布    		
        'addUrgency' => '/fund/addUrgency',  	
    	//更新緊急發布
    	'updateUrgency' => '/fund/updateUrgency',
        //查詢充值申訴提示
        'searchTips' => '/fund/searchTips',
        'insertTip' => '/fund/insertTips',
	//查詢充值申訴紀錄
	'getRechargeAppreals' => '/fund/getRechargeAppreals',
    	//查詢充值申訴審核
	'getRechargeAppealReview' => '/fund/getRechargeAppealReview',
	//查詢充值申訴提示
	'searchTips' => '/fund/searchTips',
	'addTips' => '/fund/addTips',
	'insertTip' => '/fund/insertTips',
	'appealReview' =>'/fund/appeal/review',
	'queryAppealCountsByStatus' => '/fund/appeal/queryAppealCountsByStatus',
     'queryUncheckAppeal' => '/fund/queryUncheckAppeal',
	'queryUnhandleWithdraw' => '/fund/queryUnhandleWithdraw',
	'queryUnhandleCharge' => '/fund/queryUnhandleCharge',
    //根據id查詢提現單 
    'queryWithdrawById' => '/fund/queryWithdrawById' ,
    'chargeRecordQuery' => '/fund/chargeRecordQuery',
    //查詢分流設置白名單
    'queryBypassWhiteList' => '/fund/bypass/queryBypassWhiteList',
    'deleteBypassWhiteListData' => '/fund/bypass/deleteBypassWhiteListData',
    'saveItem' => '/fund/bypass/saveWhiteList',
    'isAccountExist' => '/fund/bypass/isAccountExist',
    'queryBypassConfigByType' => '/fund/bypass/queryBypassConfigByType',
    'saveBypassCfg' => '/fund/bypass/saveBypassCfg',
	//查詢人工活動派獎活動名稱時間
	'queryactivitydetail' =>'/fund/queryActivityDetail',
	//加載人工活動派獎資料(未派獎))
	'querywinningloguntreat' =>'/fund/queryWinningLogUntreat',
	//加載人工活動派獎資料(已派獎)
	'querywinninglogtreat' =>'/fund/queryWinningLogTreat',
	//提交派獎
	'awardsapply' =>'/fund/awardsApply'
     );
    
    public $Person = "/user/message/queryMessageList";
    
    public $levelRecycleUser = array(
            //一代回收用户列表
            'levelRecycleUserlist' => '/user/queryLevelRecycleList',
            //一代回收纪录
            'levelRecycleHistorylist' => '/user/queryLevelRecycleHistory',
            //一代回收申请
            'sendApplylevelRecycle' => '/user/applyLevelRecycle',
            //检查一代回收密码修改
            'isLevelRecycleFirstLogin' => '/user/isLevelRecycleFirstLogin',
            //清除奖金组
            'cleanAwardGroup' => '/user/cleanAwardGroup',
            //清理安全中心
            'cleanSafeCenter' => '/user/cleanSafeCenter',
            //清理个人资料
            'cleanPersonalinfo' => '/user/cleanPersonalinfo',
            //清理银行卡信息
            'cleanBindCard' => '/user/cleanBindCard',
            //清理投注记录
            'cleanOrderHistory' => '/user/cleanOrderHistory',
            //清理站內信
            'cleanUserMessage' => '/user/cleanUserMessage',
            //重置PT密码
            'resetPtPassword' => '/user/resetPtPassword',
            //重置平台登录密码
            'resetLoginPassword' => '/user/resetLoginPassword',
            //查询追号记录
            'checkPlanList' => '/game/getUndoPlansCountByUserId'
    );

     public $beginMession = array(
            
            
            'logincheck' => '/begin/mission/loginCheck',
            'NewMission' => '/begin/mission/gotoNewMission',
            'answerward' => '/begin/mission/dailyAnswerAward',
            'gotoEggLottery' => '/begin/mission/gotoEggLottery',
            'eggLotteryAward' => '/begin/mission/eggLotteryAward',
            'DaillyMission' => '/begin/mission/gotoDaillyMission',
            'DaillyQuestion' => '/begin/mission/gotoDaillyQuestion',
            'NewCharge' => '/begin/mission/getUserBeginNewCharge'
         
    );
   
     
     public $vipData = array(
         'getconfigvaluebykey' => '/common/getConfigValueByKey',
         'queryAdNotice'=> '/adAdmin/queryAdNoticeList',
         'queryAdverts'=>'/adAdmin/getAdvertByNames'
     );
     


//      public $phone = array(
//      		'upload' => '/phone/support/uploadFiles'
//      );
}

?>
