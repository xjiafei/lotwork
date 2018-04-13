<?php
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////	                                        	3.0平台			                               /////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//登陆
define('FRONT_LOGIN',  HOST .  '/?controller=default&action=login' );
//登出
define('FRONT_LOGOUT',  HOST .  '/?controller=default&action=logout' );

//初始化数据（重庆时时彩+山东11选5）
define('GAME_INITDATA',  HOST .  '/highgame/?controller=game&action=play&flag=initdata&come_from=3&curmid=' );
//Init data
define('GAME_INITDATA_CQSSC',  HOST .  '/highgame/?controller=game&action=play&curmid=50&pid=50&flag=initdata' );
define('GAME_INITDATA_SD11X5',  HOST .  '/highgame/?controller=game&action=play&curmid=174&pid=174&flag=initdata' );
define('GAME_INITDATA_LLSSC',  HOST .  '/highgame/?controller=game&action=play&curmid=531&pid=531&flag=initdata' );
define('GAME_INITDATA_LL11X5',  HOST .  '/highgame/?controller=game&action=play&curmid=753&pid=753&flag=initdata' );
define('GAME_INITDATA_JLFFC',  HOST .  '/highgame/?controller=game&action=play&curmid=799&pid=799&flag=initdata' );
define('GAME_INITDATA_3D',  HOST .  '/lowgame/?controller=game&action=play&curmid=67&pid=67&flag=initdata' );
define('GAME_INITDATA_SSQ',  HOST .  '/lowgame/?controller=game&action=play&curmid=155&pid=155&flag=initdata&cb=1' );
//Simple Init
define('GAME_SIMPLEINITDATA_HIGH',  HOST .  '/highgame/?controller=game&action=clientplay&flag=simpleInit' );
define('GAME_SIMPLEINITDATA_LOW',  HOST .  '/lowgame/?controller=game&action=play&flag=simpleInit&curmid=73&pid=73' );

//投注
define('GAME_BUY_HIGH',  HOST .  '/highgame/?controller=game&action=clientplay' );
define('GAME_BUY_LOW',  HOST .  '/lowgame/?controller=game&action=play&curmid=65&pid=65' );

//撤单
//define('GAMEINFO_CANCELGAME',  HOST .  '/highgame/?controller=gameinfo&action=cancelgame&come_from=3&id=' );
//define('GAMEINFO_CANCELTASK',  HOST .  '/highgame/?controller=gameinfo&action=canceltask&come_from=3' );
define('GAME_CANCELGAME_HIGH',  HOST .  '/highgame/?controller=gameinfo&action=cancelgame&id=%s' );
define('GAME_CANCELGAME_LOW',  HOST .  '/lowgame/?controller=gameinfo&action=cancelgame&id=%s' );
define('GAME_CANCELTASK_HIGH',  HOST .  '/highgame/?controller=gameinfo&action=canceltask' );
define('GAME_CANCELTASK_LOW',  HOST .  '/lowgame/?controller=gameinfo&action=canceltask' );

//开奖号码，游戏列表，追号列表，游戏详情，追号详情，公告列表，公告详情
define('INFORMATION_HISTORY_INFO_HIGH',  HOST .  '/highgame/?controller=game&action=play&curmid=50&pid=50&flag=allhistoryinfo' );
define('INFORMATION_HISTORY_INFO_LOW',  HOST .  '/lowgame/?controller=game&action=bonuscode' );
define('INFORMATION_GAME_LIST_HIGH',  HOST .  '/highgame/index.php?controller=gameinfo&action=gamelist&flag=playinfo' );
define('INFORMATION_GAME_LIST_LOW',  HOST .  '/lowgame/index.php?controller=gameinfo&action=gamelist&flag=playinfo' );
define('INFORMATION_GAME_DETAIL_HIGH',  HOST .  '/highgame/index.php?controller=gameinfo&action=gamedetail&flag=gameinfo&id=%s' );
define('INFORMATION_GAME_DETAIL_LOW',  HOST .  '/lowgame/index.php?controller=gameinfo&action=gamedetail&flag=gameinfo&id=%s' );
define('INFORMATION_TASK_LIST_HIGH',  HOST .  '/highgame/?controller=gameinfo&action=task' );
define('INFORMATION_TASK_LIST_LOW',  HOST .  '/lowgame/?controller=gameinfo&action=task' );
define('INFORMATION_TASK_DETAIL_HIGH',  HOST .  '/highgame/?controller=gameinfo&action=taskdetail&id=%s' );
define('INFORMATION_TASK_DETAIL_LOW',  HOST .  '/lowgame/?controller=gameinfo&action=taskdetail&id=%s' );
define('INFORMATION_NOTICE_LIST_BANK',  HOST .  '/?controller=help&action=noticelist&flag=reportrecord' );
define('INFORMATION_NOTICE_LIST_HIGH',  HOST .  '/highgame/?controller=help&action=noticelist&flag=reportrecord' );
define('INFORMATION_NOTICE_LIST_LOW',  HOST .  '/lowgame/?controller=help&action=noticelist&flag=reportrecord' );
define('INFORMATION_NOTICE_DETAIL_BANK',  HOST .  '/?controller=help&action=notice&flag=ios&nid=%s' );
define('INFORMATION_NOTICE_DETAIL_HIGH',  HOST .  '/highgame/?controller=help&action=notice&flag=ios&nid=%s' );
define('INFORMATION_NOTICE_DETAIL_LOW',  HOST .  '/lowgame/?controller=help&action=notice&flag=ios&nid=%s' );

//获取未来奖期
define('INFORMATION_ALLISSUE_CQSSC',  HOST .  '/highgame/?controller=game&action=play&curmid=50&pid=50&flag=allIssues&lottery_id=1' );
define('INFORMATION_ALLISSUE_SD11X5',  HOST .  '/highgame/?controller=game&action=play&curmid=174&pid=174&flag=allIssues&lottery_id=5' );
define('INFORMATION_ALLISSUE_LLSSC',  HOST .  '/highgame/?controller=game&action=play&curmid=531&pid=531&flag=allIssues&lottery_id=11' );
define('INFORMATION_ALLISSUE_LL11X5',  HOST .  '/highgame/?controller=game&action=play&curmid=753&pid=753&flag=allIssues&lottery_id=13' );
define('INFORMATION_ALLISSUE_JLFFC',  HOST .  '/highgame/?controller=game&action=play&curmid=799&pid=799&flag=allIssues&lottery_id=14' );
define('INFORMATION_ALLISSUE_3D',  HOST .  '/lowgame/?controller=game&action=play&curmid=65&pid=65&flag=allIssues&lottery_id=1' );
define('INFORMATION_ALLISSUE_SSQ',  HOST .  '/lowgame/?controller=game&action=play&curmid=155&pid=155&flag=allIssues&lottery_id=3' );

//广告信息、版本信息、频道馀额、服务器时间
define('INFORMATION_GET_ADINFO',  HOST .  '/?controller=default&action=login&flag=getAdInfo' );
define('INFORMATION_GET_VERSION',  HOST .  '/?controller=default&action=login&flag=getVersion' );
define('INFORMATION_GET_BALANCE',  HOST .  '/highgame/?controller=security&action=tran&flag=balance' );
define('INFORMATION_GET_TIME',  HOST .  '/?controller=default&action=login&flag=getTime' );

//站内信
define('INFORMATION_USER_MSG_LIST',  HOST .  '/?controller=default&action=usermsg' );
define('INFORMATION_USER_MSG_DETAIL',  HOST .  '/?controller=default&action=usermsg&come_from=4&mid=%s' );
define('INFORMATION_USER_MSG_DEL',  HOST .  '/?controller=default&action=usermsg&flag=del&come_from=4&mid=%s' );

//用户点数
define('INFORMATION_USER_POINT',  HOST .  '/index.php?controller=report&action=query&flag=userpoint' );

//链结开户
define('INFORMATION_OPEN_LINK_LIST',  HOST .  '/?controller=link&action=linksmanage' );
define('INFORMATION_OPEN_LINK_DETAIL',  HOST .  '/?controller=link&action=linkdetail&id=%s' );

//充值
define('RECHARGE_INIT',  HOST .  '/?controller=emaildeposit&action=emailload&flag=initload' );
define('RECHARGE_CONFIRM',  HOST .  '/?controller=emaildeposit&action=emailload&flag=appload' );

//提现
define('WITHDRAW_INIT',  HOST .  '/?controller=security&action=platwithdraw' );
define('WITHDRAW_VERIFY',  HOST .  '/?controller=security&action=platwithdraw&flag=withdraw' );
define('WITHDRAW_COMMIT',  HOST .  '/index.php?controller=security&action=platwithdraw&flag=confirm' );

//频道转帐
define('SECURITY_BANK_TRANSFER_HIGH',  HOST .  '/highgame/?controller=security&action=pickup' );
define('SECURITY_HIGH_TRANSFER_BANK',  HOST .  '/highgame/?controller=security&action=tran' );
define('SECURITY_BANK_TRANSFER_LOW',  HOST .  '/lowgame/?controller=security&action=pickup&flag=doTranfer' );
define('SECURITY_LOW_TRANSFER_BANK',  HOST .  '/lowgame/?controller=security&action=tran&flag=doTranfer' );

//资金密码验证
define('SECURITY_CHECK_SECPASS',  HOST .  '/?controller=security&action=userbankinfo&flag=checksecpass' );
//资金密码添加
define('SECURITY_ADD_SECPASS',  HOST .  '/?controller=security&action=changeloginpass&flag=changepass' );
//资金密码修改
define('SECURITY_MODIFY_SECPASS',  HOST .  '/?controller=security&action=changeloginpass&flag=changepass' );
//登入密码修改
define('SECURITY_MODIFY_LOGINPASS',  HOST .  '/?controller=security&action=changeloginpass&flag=changepass' );

//绑卡初始化
define('SECURITY_CARD_BINDING_INIT',  HOST .  '/?controller=security&action=userbankinfo&flag=cardbinding' );
//绑卡列表
define('SECURITY_CARD_LIST',  HOST .  '/?controller=security&action=userbankinfo&flag=getcardlist' );
//绑卡验证
define('SECURITY_CARD_BINDING_CONFIRM',  HOST .  '/?controller=security&action=userbankinfo&flag=newbinding' );
//绑卡提交
define('SECURITY_CARD_BINDING_COMMIT',  HOST .  '/?controller=security&action=userbankinfo&flag=confirm' );
//绑卡删除
define('SECURITY_CARD_BINDING_DELETE',  HOST .  '/?controller=security&action=deluserbank&flag=delcard' );

//城市列表
define('SECURITY_CITY_LIST',  HOST .  '/?controller=security&action=userbankinfo&flag=getcitylist' );

//银行帐变
define('BANK_BANKREPORT_HIGH',  HOST .  '/highgame/?controller=report&action=orders&flag=reportrecord&ordertype=%s' );
define('BANK_BANKREPORT_LOW',  HOST .  '/lowgame/?controller=report&action=list&flag=reportrecord&ordertype=%s' );

//推送
define('PUSH_USER_APP_FLAG_REGISTER',  HOST .  '/?controller=default&action=usermsg&flag=userappflagregister' );
define('PUSH_USER_APP_FLAG_SWITCH',  HOST .  '/index.php?controller=default&action=usermsg&flag=userappflagswitch' );
define('PUSH_USER_APP_FLAG_SWITCH_SET',  HOST .  '/index.php?controller=default&action=usermsg&flag=userappflagswitchset' );

//注册新用户
define('USER_ADD_USER',  HOST .  '/index.php?controller=user&action=adduser' );

//团队频道馀额
define('USER_TEAM_BALANCE_BANK',  HOST .  '/index.php?controller=user&action=userteam&flag=getteambalance' );
define('USER_TEAM_BALANCE_HIGH',  HOST .  '/highgame/?controller=user&action=team&flag=getteambalance' );
define('USER_TEAM_BALANCE_LOW',  HOST .  '/lowproxy/?controller=user&action=team&flag=getteambalance' );

//下级团队频道馀额
define('USER_TEAM_USER_BALANCE_BANK',  HOST .  '/?controller=user&action=userlist&frame=show&flag=getuserbalance&uid=%s' );
define('USER_TEAM_USER_BALANCE_HIGH',  HOST .  '/highgame/?controller=user&action=list&frame=show&flag=getuserbalance&uid=%s' );
define('USER_TEAM_USER_BALANCE_LOW',  HOST .  '/lowproxy/?controller=user&action=list&frame=show&flag=getuserbalance&uid=%s' );

//(下级)代理和会员的列表
define('USER_PROXY_LIST_HIGH',  HOST .  '/highgame/?controller=user&action=list&frame=show&flag=getuserlist&type=%s&p=%s&uid=%s' );
define('USER_PROXY_LIST_LOW',  HOST .  '/lowproxy/?controller=user&action=list&frame=show&flag=getuserlist&type=%s&p=%s&uid=%s' );

//(下级)代理和会员的个数
define('USER_PROXY_NUMBER_HIGH',  HOST .  '/highgame/?controller=user&action=list&frame=show&flag=getuserlistnum&uid=%s' );
define('USER_PROXY_NUMBER_LOW',  HOST .  '/lowproxy/?controller=user&action=list&frame=show&flag=getuserlistnum&uid=%s' );

//用户银行账变
define('USER_USER_BANK_REPORT',  HOST .  '/index.php?controller=report&action=bankreport&flag=bankreport&username=%s' );

//下级充值资料
define('USER_SAVEUP_DATA',  HOST .  '/index.php?controller=user&action=saveup&flag=saveup&uid=%s' );
//检查下级充值金额
define('USER_SAVEUP_CHECK',  HOST .  '/index.php?controller=user&action=saveup&uid=%s&flag=saveup&money=%s' );
//下级充值提交
define('USER_SAVEUP_COMMIT',  HOST .  '/index.php?controller=user&action=saveup&flag=saveup&uid=%s&secpwd=%s&money=%s' );
//搜寻用户
define('USER_SEARCH_USER_HIGH',  HOST .  '/highgame/?controller=user&action=list&frame=show&flag=searchuser&username=%s' );
define('USER_SEARCH_USER_LOW',  HOST .  '/lowproxy/?controller=user&action=list&frame=show&flag=search&tag=searchuser&username=%s' );

//返点资料
define('USER_UPEDIT_HIGH',  HOST .  '/highgame/?controller=user&action=upedituser&uid=%s' );
define('USER_UPEDIT_LOW',  HOST .  '/lowproxy/?controller=user&action=upedituser' );

//设置返点资料
define('USER_UPEDIT_MODIFY_HIGH',  HOST .  '/highgame/?controller=user&action=upedituser&uid=%s' );
define('USER_UPEDIT_MODIFY_LOW',  HOST .  '/lowproxy/?controller=user&action=upedituser' );


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////	                                        3.0 transfer to 4.0		                               /////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

define('USER_MIGRATE_BALANCE',  HOST . '/index.php?controller=migrate&action=gettotalbalance' );
define('USER_MIGRATE',  HOST . '/index.php?controller=migrate&action=transfermoney' );