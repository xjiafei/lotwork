<?php
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////	                                        	4.0平台			                               /////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////
////    front    ////
/////////////////////

define('FRONT_LOGIN_4',  HOST_4 .  '/front/login' );

///////////////////////////
////    information    ////
///////////////////////////

//开奖号码，游戏列表，追号列表，游戏详情，追号详情，公告列表，公告详情
define('INFORMATION_HISTORY_INFO_4',  HOST_4 .  '/information/historyInfo' );
define('INFORMATION_GAME_LIST_4',  HOST_4 .  '/information/gameList' );
define('INFORMATION_GAME_DETAIL_4',  HOST_4 .  '/information/gameDetail' );
define('INFORMATION_WIN_LIST_4',  HOST_4 .  '/information/winList' );
define('INFORMATION_TASK_LIST_4',  HOST_4 .  '/information/taskList' );
define('INFORMATION_TASK_DETAIL_4',  HOST_4 .  '/information/taskDetail' );
define('INFORMATION_WIN_AMT_4',  HOST_4 .  '/information/winAmt' );
define('INFORMATION_LAST_NUMBER_4',  HOST_4 .  '/information/lastNumber' ); 
define('INFORMATION_USER_PROFIT_4',  HOST_4 .  '/information/userProfit' );
define('INFORMATION_NOTICE_LIST_4',  HOST_4 .  '/information/noticeList' );
define('INFORMATION_NOTICE_DETAIL_4',  HOST_4 .  '/information/noticeDetail' );
define('INFORMATION_METHOD_INIT_4',  HOST_4 .  '/information/methodInit' ); 
define('INFORMATION_CHART_4',  HOST_4 .  '/chart/chartData' );	//走势图数据

define('INFORMATION_ALLISSUE_4',  HOST_4 .  '/information/allIssue' );

//频道馀额、服务器时间
define('INFORMATION_GET_BALANCE_4',  HOST_4 .  '/information/getBalance' );
define('INFORMATION_GET_TIME_4',  HOST_4 .  '/information/getTime' );

//站内信
define('INFORMATION_USER_MSG_LIST_4',  HOST_4 .  '/information/userMsgList' );
define('INFORMATION_USER_MSG_DETAIL_4',  HOST_4 .  '/information/userMsgDetail' );
define('INFORMATION_USER_MSG_DEL_4',  HOST_4 .  '/information/userMsgDel' );

//用户点数
define('INFORMATION_USER_POINT_4',  HOST_4 .  '/information/userPoint' );

//链结开户
define('INFORMATION_OPEN_LINK_LIST_4',  HOST_4 .  '/information/openLinkList' );
define('INFORMATION_OPEN_LINK_DETAIL_4',  HOST_4 .  '/information/openLinkDetail' );

define('INFORMATION_OPEN_LINK_USERS_4',  HOST_4 .  '/information/openLinkUsers' );

define('INFORMATION_DEL_OPEN_LINK_4',  HOST_4 .  '/information/delOpenLink' );
define('INFORMATION_MODIFY_REMARK_4',  HOST_4 .  '/information/modifyRemark' );

define('INFORMATION_PRIZE_DETAIL_4',  HOST_4 .  '/information/prizeDetail' );

define('INFORMATION_INIT_CREATE_URL_4',  HOST_4 .  '/information/initCreateUrl' );
define('INFORMATION_DO_RET_SETTING_4',  HOST_4 .  '/information/doRetSetting' );

//開戶連結Domain
//define("HOST_OPENLINK_PRODUCT", "http://www.fh500.com");
//define("HOST_OPENLINK_DEVELOP", "http://www.baidu.com");

////////////////////
////    User    ////
////////////////////

//注册客制化新用户
define('USER_ADD_CUSTOMIZED_USER_4',  HOST_4 .  '/user/addCustomizedUser' );

//团队频道馀额
define('USER_TEAM_BALANCE_4',  HOST_4 .  '/user/teamBalance' );

//下级团队频道馀额
define('USER_TEAM_USER_BALANCE_4',  HOST_4 .  '/user/teamUserBalance' );

//搜寻用户
define('USER_SEARCH_USER_4',  HOST_4 .  '/user/searchUser' );

//(下级)代理和会员的列表
define('USER_PROXY_LIST_4',  HOST_4 .  '/user/proxyList' );

//(下级)代理和会员的个数
define('USER_PROXY_NUMBER_4',  HOST_4 .  '/user/proxyNumber' );

//用户银行账变
define('USER_USER_BANK_REPORT_4',  HOST_4 .  '/user/userBankReport' );

//下级充值资料
define('USER_SAVEUP_DATA_4',  HOST_4 .  '/user/saveupData' );
//检查下级充值金额
define('USER_SAVEUP_CHECK_4',  HOST_4 .  '/user/saveupCheck' );
//下级充值提交
define('USER_SAVEUP_COMMIT_4',  HOST_4 .  '/user/saveupCommit' );

////////////////////
////    Bank    ////
////////////////////

//银行帐变
define('BANK_BANKREPORT_4',  HOST_4 .  '/bank/bankReport' );

////////////////////////
////    Recharge    ////
////////////////////////

//充值
define('RECHARGE_INIT_4',  HOST_4 .  '/recharge/init' );
define('RECHARGE_CONFIRM_4',  HOST_4 .  '/recharge/confirm' );

//快捷充值
define('RECHARGE_QUICK_INIT_4',  HOST_4 .  '/recharge/quickInit' );
define('RECHARGE_QUICK_COMMIT_4',  HOST_4 .  '/recharge/quickCommit' );
define('RECHARGE_CHARGE_RECORD_4',  HOST_4 .  '/recharge/chargeRecord' );

////////////////////////
////    Withdraw    ////
////////////////////////

//提现
define('WITHDRAW_INIT_4',  HOST_4 .  '/withdraw/init' );
define('WITHDRAW_VERIFY_4',  HOST_4 .  '/withdraw/verify' );
define('WITHDRAW_COMMIT_4',  HOST_4 .  '/withdraw/commit' );

////////////////////////
////    Security    ////
////////////////////////

//资金密码验证
define('SECURITY_CHECK_SECPASS_4',  HOST_4 .  '/security/checkSecpass' );
//资金密码添加
define('SECURITY_ADD_SECPASS_4',  HOST_4 .  '/security/addSecpass' );
//资金密码修改
define('SECURITY_MODIFY_SECPASS_4',  HOST_4 .  '/security/modifySecpass' );
//登入密码修改
define('SECURITY_MODIFY_LOGINPASS_4',  HOST_4 .  '/security/modifyLoginpass' );

//安全问题初始化
define('SECURITY_SAFE_QUEST_INIT_4',  HOST_4 .  '/security/safeQuestInit' );
//安全问题设定
define('SECURITY_SAFE_QUEST_SET_4',  HOST_4 .  '/security/safeQuestSet' );
//安全问题验证
define('SECURITY_SAFE_QUEST_VERIFY_4',  HOST_4 .  '/security/safeQuestVerify' );
//安全问题修改
define('SECURITY_SAFE_QUEST_EDIT_4',  HOST_4 .  '/security/safeQuestEdit' );

//绑卡初始化
define('SECURITY_CARD_BINDING_INIT_4',  HOST_4 .  '/security/cardBindingInit' );
//绑卡列表
define('SECURITY_CARD_LIST_4',  HOST_4 .  '/security/cardList' );
//绑卡验证
define('SECURITY_CARD_BINDING_CONFIRM_4',  HOST_4 .  '/security/cardBindingConfirm' );
//绑卡提交
define('SECURITY_CARD_BINDING_COMMIT_4',  HOST_4 .  '/security/cardBindingCommit' );
//绑卡删除
define('SECURITY_CARD_BINDING_DELETE_4',  HOST_4 .  '/security/cardBindingDelete' );

////////////////////
////    Game    ////
////////////////////

//投注
define('GAME_BUY_4',  HOST_4 .  '/game/buy' );
//追号
define('GAME_PLAN_4',  HOST_4 .  '/game/plan' );


//撤游戏单
define('GAME_CANCELGAME_4',  HOST_4 .  '/game/cancelGame' );
//撤追号单
define('GAME_CANCELTASK_4',  HOST_4 .  '/game/cancelTask' );

//Simple Init
define('GAME_SIMPLEINITDATA_4',  HOST_4 .  '/game/simpleInitData' );

define('GAME_ADDBONUSDATA_4',  HOST_4 .  '/game/addBonusData' );


////////////////////
////    Event    ////
////////////////////

//确认砸蛋奖金
define('EVENT_DO_EGG_ACT_4',  HOST_4 .  '/event/doEggAct' );

//确认刮刮卡奖金
define('EVENT_DO_GUAGUACARD_ACT_4',  HOST_4 .  '/event/doGuaguacardAct' );

//客服工單 建立問題
define('SUPPORT_CREATE_TICKET_4',  HOST_4 .  '/phone/support/createTicket' );

//客服工單 儲存問題
define('SUPPORT_SAVE_TICKET_4',  HOST_4 .  '/phone/support/saveTicket' );

//客服工單 問題列表
define('SUPPORT_TICKET_LIST_4',  HOST_4 .  '/phone/support/ticketList' );

//客服工單 檔案上傳
define('SUPPORT_UPLOAD_FILES_4',  HOST_4 .  '/phone/support/uploadFiles' );

//客服工單 問題明細
define('SUPPORT_TICKET_DETAIL_4',  HOST_4 .  '/phone/support/ticketDetail' );

//客服工單 回覆內容
define('SUPPORT_SECOND_SAVE_4',  HOST_4 .  '/phone/support/secondSaveTicket' );

//客服工單 問題結案
define('SUPPORT_TICKET_CLOSE_4',  HOST_4 .  '/phone/support/closeTicket' );