<?php



/*
	$pushIDs = array();
                    
	$pushIDs[] = "11111";
	$pushIDs[] = "22222";
	$aa =  serialize($pushIDs);
	
	echo $aa . PHP_EOL;
	
	$bb = unserialize($aa);
	var_dump($bb);
*/


//echo "[" . date("y/m/d h:i:s") . "]PUSH STEP 6\r\n";

//echo false;


?>
<head>
	<meta charset="utf-8">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="./jUtil.js"></script>
</head>
<style>
.tbltd
{
	width: 20%;
	height: 10%;
	border: solid 1px #000000;
	color: #ff0000;
}
.btn
{
	border: solid 1px #000000;
}
</style>

<script>
//var BASE_URL = "http://192.168.99.234:8080/mobileapi/index.php";
var mIsProduct = 0;
var BASE_URL = "http://ios1.phl5b.org";

var PLATFORM_3 = 1;
var PLATFORM_ADMIN = 2;
var PLATFORM_4 = 3;



var API_URL = {"FRONT":{
			"LOGIN_AAKENT": {"name": "登入(aakent)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"aakent"}'},
			"LOGIN_APP002": {"name": "登入(app002)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app002"}'},
			"LOGIN_APP022": {"name": "登入(app022)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app022"}'},
			"LOGIN_APP023": {"name": "登入(app023)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app023"}'},
			"LOGIN_TESTY": {"name": "登入(testy)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"testy"}'},
			"LOGIN_JAYITACHI": {"name": "登入(jayitachi)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"jayitachi"}'},
			"LOGIN_MDD001": {"name": "登入(mdd001)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mdd001"}'},
			
			"LOGIN_ADMIN": {"name": "登入(admin)(总代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"admin"}'},
			"LOGIN_fiona18": {"name": "登入(fiona18)(1代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"fiona18"}'},
			
			
			"LOGIN_MDD000": {"name": "登入(mdd000)(总代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mdd000"}'},
			"LOGIN_MDDPERIC": {"name": "登入(mddPeric)(1代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddperic"}'},
			"LOGIN_MDDJEFF": {"name": "登入(mddJeff)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddjeff"}'},
			"LOGIN_MDDBETTER": {"name": "登入(mddBetter)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddbetter"}'},
			"LOGIN_YANNI": {"name": "登入(mddYanni)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddyanni"}'},
			"LOGIN_Laurence": {"name": "登入(mddLaurence)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddlaurence"}'},
			
			
			
			},
		"GAME":{
			//"BUY_HIGH": {"name": "投注(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","lotteryid":1,"lt_issue_start":"140429058","lt_project":[{\"codes\":\"0|0|6|6|9\",\"desc\":\"[五星直选]0,0,6,6,9\",\"methodid\":429,\"mode\":1,\"money\":2,\"name\":\"五星直选\",\"nums\":1,\"times\":1,\"type\":\"digital\"},{\"codes\":\"0&1&5&6&7\",\"desc\":\"[五星组选120]0&1&5&6&7\",\"methodid\":435,\"mode\":1,\"money\":2,\"name\":\"五星组选120\",\"nums\":1,\"times\":1,\"type\":\"digital\"}],"lt_total_money":4}'},
			//"BUY_HIGH_TRACE": {"name": "追号(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","lotteryid":1,"lt_issue_start":"140430024","lt_project":[{\"codes\":\"5&7|5|9|9|5\",\"desc\":\"[五星直选]5&7,5,9,9,5\",\"methodid\":429,\"mode\":1,\"money\":16,\"name\":\"五星直选\",\"nums\":2,\"times\":4,\"type\":\"digital\"},{\"codes\":\"5|6&7|8|1\",\"desc\":\"[四星直选]5,6&7,8,1\",\"methodid\":442,\"mode\":1,\"money\":16,\"name\":\"四星直选\",\"nums\":2,\"times\":4,\"type\":\"digital\"},{\"codes\":\"单|小&单|小\",\"desc\":\"[后三大小单双]单,小&单,小\",\"methodid\":490,\"mode\":1,\"money\":16,\"name\":\"后三大小单双\",\"nums\":2,\"times\":4,\"type\":\"dxds\"},{\"codes\":\"豹子&顺子&对子\",\"desc\":\"[后三特殊号]豹子&顺子&对子\",\"methodid\":471,\"mode\":1,\"money\":24,\"name\":\"后三特殊号\",\"nums\":3,\"times\":4,\"type\":\"dxds\"}],"lt_total_money":216,"lt_trace_if":"yes","lt_trace_issues":[\"140430024\",\"140430025\",\"140430026\"],"lt_trace_stop":"yes"}'},
			//"BUY_LOW_3D": {"name": "投注3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryid":1,"methodid":9,"lottery_currentissue":"2014112","lottery_confirmnums":"424|494|724|794","lottery_times":2,"lottery_totalamount":16,"lottery_totalnum":4,"lottery_istrace":0}'},
			//"BUY_LOW_3D_TRACE": {"name": "追号3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryid":1,"methodid":9,"lottery_currentissue":"2014112","lottery_confirmnums":"334|338|364|368|634|638|664|668","lottery_times":2,"lottery_totalamount":32,"lottery_totalnum":8,"lottery_istrace":1,"trace_issue":["2014112","2014113","2014114"],"trace_stop":1,"trace_totalamount":96}'},
			//"BUY_LOW_CB": {"name": "投注双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryid":3,"methodid":61,"lottery_currentissue":"2014048","lottery_confirmnums":["04 07 15 20 23 30 33+05 09:61:2:9193286975342268"],"lottery_times":2,"lottery_totalnum":14,"lottery_totalamount":56,"lottery_istrace":0}'},
			//"BUY_LOW_CB_TRACE": {"name": "追号双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryid":3,"methodid":61,"lottery_currentissue":"2014048","lottery_confirmnums":["12 13 17 19 24 26 29+08 11:61:2:8269637271542689"],"lottery_times":2,"lottery_totalnum":14,"trace_totalamount":168,"lottery_istrace":1,"trace_issue":["2014048","2014049","2014050"],"trace_stop":1}'},
			
			"BUY_HIGH": {"name": "投注(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","lottery_id":1,"issue":"140429058","projects":[{\"codes\":\"0|0|6|6|9\",\"methodid\":429,\"mode\":1,\"money\":2,\"name\":\"五星直选\",\"nums\":1,\"times\":1},{\"codes\":\"0&1&5&6&7\",\"methodid\":435,\"mode\":1,\"money\":2,\"name\":\"五星组选120\",\"nums\":1,\"times\":1}],"money":4}'},
			"BUY_HIGH_TRACE": {"name": "追号(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","lottery_id":1,"issue":"140430024","projects":[{\"codes\":\"5&7|5|9|9|5\",\"methodid\":429,\"mode\":1,\"money\":16,\"name\":\"五星直选\",\"nums\":2,\"times\":4},{\"codes\":\"5|6&7|8|1\",\"methodid\":442,\"mode\":1,\"money\":16,\"name\":\"四星直选\",\"nums\":2,\"times\":4},{\"codes\":\"单|小&单|小\",\"methodid\":490,\"mode\":1,\"money\":16,\"name\":\"后三大小单双\",\"nums\":2,\"times\":4},{\"codes\":\"豹子&顺子&对子\",\"methodid\":471,\"mode\":1,\"money\":24,\"name\":\"后三特殊号\",\"nums\":3,\"times\":4}],"trace_istrace":"1","trace_stop":"1","trace_issues":[\"140430024\",\"140430025\",\"140430026\"],"money":216}'},
			"BUY_LOW_3D": {"name": "投注3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":1,"issue":"2014112","projects":[{\"codes\":\"424|494|724|794\",\"methodid\":9,\"nums\":4,\"times\":2}],"trace_istrace":"0","money":16}'},
			"BUY_LOW_3D_TRACE": {"name": "追号3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":1,"issue":"2014112","projects":[{\"codes\":\"334|338|364|368|634|638|664|668\",\"methodid\":9,\"nums\":8,\"times\":2}],"trace_istrace":1,"trace_stop":1,"trace_issues":["2014112","2014113","2014114"],"money":96}'},
			"BUY_LOW_CB": {"name": "投注双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":3,"issue":"2014048","projects":[{\"codes\":\"04 07 15 20 23 30 33+05 09\",\"methodid\":61,\"nums\":14,\"times\":2}],"trace_istrace":0,"money":56}'},
			"BUY_LOW_CB_TRACE": {"name": "追号双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":3,"issue":"2014048","projects":[{\"codes\":\"12 13 17 19 24 26 29+08 11\",\"methodid\":61,\"nums\":14,\"times\":2}],"trace_istrace":1,"trace_stop":1,"trace_issues":["2014048","2014049","2014050"],"money":168}'},
			
			
			
			"BUY_4": {"name": "投注(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"4","lotteryId":1,"issue":"20140930101032","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,2,3,4,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":4.0}'},
			
			"PLAN_4": {"name": "追号(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"4","lotteryId":1,"issue":"20140930101032","traceIstrace":1,"traceStop":0,"traceIssues":"20140930101040,20140930101041","traceTimes":"1,1","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,2,3,4,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":8.0}'},
			
			
			"CANCELGAME_4": {"name": "撤游戏单(4.0)", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","id":"D140505024VDIFEECFHG"}'},
			"CANCELTASK_4": {"name": "撤追号单(4.0)", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryId":4,"planId":"3844","issueCode":"242873537,242873539"}'},
			
			
			
			
			"CANCELGAME_HIGH": {"name": "撤游戏单(H)", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","id":"D140505024VDIFEECFHG"}'},
			"CANCELGAME_LOW": {"name": "撤游戏单(L)", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","id":"D2014117VCJFIIJGHK"}'},
			"CANCELTASK_HIGH": {"name": "撤追号单(H)", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","id":"T140505025VBGFIAFFEK","taskid":"242873537,242873539"}'},
			"CANCELTASK_LOW": {"name": "撤追号单(L)", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","id":"T2014117VICFBDKLMN","taskid":"369182,369183"}'},
			"INITDATA": {"name": "Init Data", "url" : "#DOMAIN#/game/initData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":"1"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"SIMPLEINITDATA": {"name": "Simple Init", "url" : "#DOMAIN#/game/simpleInitData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryId":"1"}'},
			"GET_METHOD_LIST": {"name": "Get Method List", "url" : "#DOMAIN#/game/getMethodList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'}
			},
		"INFORMATION":{
			"HISTORYINFO": {"name": "开奖号码", "url" : "#DOMAIN#/information/historyInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryId":"1"}'},
			"GAMELIST": {"name": "游戏列表", "url" : "#DOMAIN#/information/gameList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"GAMEDETAIL": {"name": "游戏详情", "url" : "#DOMAIN#/information/gameDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1", "id":"D2014093VCJFIIJBFK"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"68419"}'},
			"TASKLIST": {"name": "追号列表", "url" : "#DOMAIN#/information/taskList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"TASKDETAIL": {"name": "追号详情", "url" : "#DOMAIN#/information/taskDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","id":"T2014117VICFBDKLMN"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"ZCQC140923024005"}'},
			"NOTICELIST": {"name": "公告列表", "url" : "#DOMAIN#/information/noticeList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"NOTICEDETAIL": {"name": "公告详情", "url" : "#DOMAIN#/information/noticeDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","nid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","nid":"382"}'},		
			"ALLISSUE": {"name": "奖期列表", "url" : "#DOMAIN#/information/allIssue/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"1","lotteryId":"1"}'},
			//"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/information/getAdInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			//"GETVERSION": {"name": "版本号码", "url" : "#DOMAIN#/information/getVersion/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			"GETBALANCE": {"name": "频道馀额", "url" : "#DOMAIN#/information/getBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"GETTIME": {"name": "服务器时间", "url" : "#DOMAIN#/information/getTime/#APPID#", "data": '{}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"USER_MSG_LIST": {"name": "站内信列表", "url" : "#DOMAIN#/information/userMsgList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"USER_MSG_DETAIL": {"name": "站内信详情", "url" : "#DOMAIN#/information/userMsgDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","mid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","mid":"1"}'},
			"USER_MSG_DEL": {"name": "删除站内信", "url" : "#DOMAIN#/information/userMsgDel/#APPID#", "data": '{"CGISESSID":"#TOKEN#","mid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","mid":"1"}'},
			"OPEN_LINK_LIST": {"name": "链结开户列表", "url" : "#DOMAIN#/information/openLinkList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"OPEN_LINK_DETAIL": {"name": "链结开户详情", "url" : "#DOMAIN#/information/openLinkDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"1"}'},
			"USER_POINT": {"name": "用户点数", "url" : "#DOMAIN#/information/userPoint/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'}
			},
		"PUSH":{
			"REGISTER_ID": {"name": "注册设备", "url" : "#DOMAIN#/push/registerId/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114","deviceid":"222222222222222222"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7","deviceid":"11111111111111111111"}'},
			"SWITCH_STATUS": {"name": "推送信息状态", "url" : "#DOMAIN#/push/switchStatus/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7"}'},
			"SWITCH_SET": {"name": "推送信息设置", "url" : "#DOMAIN#/push/switchSet/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114","openstatus":"0"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7","openstatus":"0"}'}
			},
		"RECHARGE":{
			"INIT": {"name": "充值初始化", "url" : "#DOMAIN#/recharge/init/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			"CONFIRM": {"name": "充值确认", "url" : "#DOMAIN#/recharge/confirm/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bid":"9","bankradio":"ccb","bank":"31092","amount":"300","alertmin":"100","secpwd":"123qweasd"}'}
			},
		"WITHDRAW":{
			"INIT": {"name": "提现初始化", "url" : "#DOMAIN#/withdraw/init/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			"VERIFY": {"name": "提现验证", "url" : "#DOMAIN#/withdraw/verify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bankinfo":"29865#6","money":"580"}'},
			"COMMIT": {"name": "提现提交", "url" : "#DOMAIN#/withdraw/commit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bank_id":6,"cardid":"29865","money":500,"secpwd":"123qweasd"}'}
			},
		"SECURITY":{
			"BANK_TO_HIGH": {"name": "转帐(Bank to High)", "url" : "#DOMAIN#/security/bankToHigh/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"HIGH_TO_BANK": {"name": "转帐(High to Bank)", "url" : "#DOMAIN#/security/highToBank/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"BANK_TO_LOW": {"name": "转帐(Bank to Low)", "url" : "#DOMAIN#/security/bankToLow/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"LOW_TO_BANK": {"name": "转帐(Low to Bank)", "url" : "#DOMAIN#/security/lowToBank/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			
			"CHECK_SECPASS": {"name": "资金密码验证", "url" : "#DOMAIN#/security/checkSecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","secpass":"123qweasd"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","secpass":"123qweasd"}'},
			"ADD_SECPASS": {"name": "资金密码添加", "url" : "#DOMAIN#/security/addSecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","newpass2":"57ba172a6be125cca2f449826f9980ca","confirm_newpass2":"57ba172a6be125cca2f449826f9980ca"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","newpass2":"57ba172a6be125cca2f449826f9980ca","confirmNewpass2":"57ba172a6be125cca2f449826f9980ca"}'},
			"MODIFY_SECPASS": {"name": "资金密码修改", "url" : "#DOMAIN#/security/modifySecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","oldpass2":"123qweasd","newpass2":"57ba172a6be125cca2f449826f9980ca","confirm_newpass2":"57ba172a6be125cca2f449826f9980ca"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","oldpass2":"123qweasd","newpass2":"57ba172a6be125cca2f449826f9980ca","confirmNewpass2":"57ba172a6be125cca2f449826f9980ca"}'},
			"MODIFY_LOGINPASS": {"name": "登入密码修改", "url" : "#DOMAIN#/security/modifyLoginpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","oldpass":"123qwe","newpass":"46f94c8de14fb36680850768ff1b7f2a","confirm_newpass":"46f94c8de14fb36680850768ff1b7f2a"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","oldpass":"123qwe","newpass":"46f94c8de14fb36680850768ff1b7f2a","confirmNewpass":"46f94c8de14fb36680850768ff1b7f2a"}'},
			"CARD_BINDING_INIT": {"name": "绑卡初始化", "url" : "#DOMAIN#/security/cardBindingInit/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
															, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"CARD_LIST": {"name": "绑卡列表", "url" : "#DOMAIN#/security/cardList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
															, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"CARD_BINDING_CONFIRM": {"name": "绑卡验证", "url" : "#DOMAIN#/security/cardBindingConfirm/#APPID#", "data": '{"CGISESSID":"#TOKEN#","account":"6226901707847717","account_name":"史的一","id":"472972"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","account":"2222222222222222226","accountName":"jeff","id":"1"}'},
			"CARD_BINDING_COMMIT": {"name": "绑卡提交", "url" : "#DOMAIN#/security/cardBindingCommit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bank_id":5,"bank": "中国民生银行","province_id":2,"province":"上海","city_id":140,"city":"上海","branch":"史的一","account_name":"史的一","account":"6226901707847718","secpass":"123qweasd"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","bankId":1,"bank": "中国工商银行","province":"北京","city":"东城","branch":"北京东城支行","accountName":"jeff","account":"2222222222222222226","secpass":"123qweasd"}'},
			"CARD_BINDING_DELETE": {"name": "绑卡删除", "url" : "#DOMAIN#/security/cardBindingDelete/#APPID#", "data": '{"CGISESSID":"#TOKEN#","account":"6226901707847718","account_name":"史的一","id":"472973"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","account":"1111111111111111113","accountName":"jeff","id":"472973","bindId":"472973"}'},
			"CITY_LIST": {"name": "城市列表", "url" : "#DOMAIN#/security/cityList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","province":"6"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","province":"6"}'}
			},
		"USER":{
			"ADD_USER": {"name": "注册新用户", "url" : "#DOMAIN#/user/addUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","nickname":"jeff01","usertype":"0"}'},
			"ADD_CUSTOMIZED_USER": {"name": "注册客制化用户", "url" : "#DOMAIN#/user/addCustomizedUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","nickname":"jeff01","usertype":"0"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","usertype":"0"}'},
			"TEAM_BALANCE": {"name": "团队频道馀额", "url" : "#DOMAIN#/user/teamBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"TEAM_USER_BALANCE": {"name": "下级团队频道馀额", "url" : "#DOMAIN#/user/teamUserBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","uid":"215239"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1383"}'},
			"PROXY_LIST": {"name": "下级代理和会员的列表", "url" : "#DOMAIN#/user/proxyList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","type":"0","uid":"215239","p":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","type":"0","uid":"1383","p":"0"}'},
			"PROXY_NUMBER": {"name": "下级代理和会员的个数", "url" : "#DOMAIN#/user/proxyNumber/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","uid":"215239"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1383"}'},
			"USER_BANK_REPORT": {"name": "个人银行账变", "url" : "#DOMAIN#/user/userBankReport/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"aakent"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","username":"mddperic"}'},
			"SAVEUP_DATA": {"name": "下级充值资料", "url" : "#DOMAIN#/user/saveupData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1448"}'},
			"SAVEUP_CHECK": {"name": "检查下级充值金额", "url" : "#DOMAIN#/user/saveupCheck/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239","money":"100.00"}'},
			"SAVEUP_COMMIT": {"name": "下级充值提交", "url" : "#DOMAIN#/user/saveupCommit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239","money":"100.00","secpwd":"123qweasd"}'},
			"SEARCH_USER": {"name": "搜寻用户", "url" : "#DOMAIN#/user/searchUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","username":"1800kent"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","username":"mdd001"}'},
			"UPEDIT": {"name": "返点资料", "url" : "#DOMAIN#/user/upeditData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","lottery_id":"1","uid":"1015598"}'},
			"UPEDIT_MODIFY_HIGH": {"name": "设定返点资料(H)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"4","uid":"1015598","accord_lottery_point":"0","accord_indefinite_point":"0","lottery":[\"1\",\"2\",\"3\",\"4\",\"6\",\"11\",\"12\",\"5\",\"7\",\"8\",\"10\",\"9\",\"13\",\"14\"],"pg_1":"8","point_1":"0.0","min_point_1":"0.0","max_point_1":"4.5","min_indefinite_point_1":"0.0","max_indefinite_point_1":"4.5","indefinite_point_1":"0.0","pg_2":"43","point_2":"0.0","min_point_2":"0.0","max_point_2":"4.5","min_indefinite_point_2":"0.0","max_indefinite_point_2":"4.5","indefinite_point_2":"0.0","pg_3":"78","point_3":"0.0","min_point_3":"0.0","max_point_3":"4.5","indefinite_point_3":"0.0","min_indefinite_point_3":"0.0","max_indefinite_point_3":"4.5","pg_4":"131","point_4":"0.0","min_point_4":"0.0","max_point_4":"4.5","min_indefinite_point_4":"0.0","max_indefinite_point_4":"4.5","indefinite_point_4":"0.0","pg_5":"387","min_point_5":"0.0","max_point_5":"4.0","point_5":"0.0","pg_6":"192","point_6":"0.0","min_point_6":"0.0","max_point_6":"4.5","min_indefinite_point_6":"0.0","max_indefinite_point_6":"4.5","indefinite_point_6":"0.0","pg_7":"366","point_7":"0.0","min_point_7":"0.0","max_point_7":"4.0","pg_8":"486","point_8":"0.0","min_point_8":"0.0","max_point_8":"4.0","pg_9":"772","point_9":"0.0","min_point_9":"0.0","max_point_9":"14.0","min_indefinite_point_9":"0.0","max_indefinite_point_9":"9.0","indefinite_point_9":"0.0","pg_10":"706","point_10":"0.0","min_point_10":"0.0","max_point_10":"4.0","pg_11":"1399","point_11":"0.0","min_point_11":"0.0","max_point_11":"4.5","min_indefinite_point_11":"0.0","max_indefinite_point_11":"4.5","indefinite_point_11":"0.0","pg_12":"1530","point_12":"0.0","min_point_12":"0.0","max_point_12":"4.5","min_indefinite_point_12":"0.0","max_indefinite_point_12":"4.5","indefinite_point_12":"0.0","pg_13":"2163","min_point_13":"0.0","max_point_13":"4.0","point_13":"0.0","pg_14":"2316","point_14":"0.0","min_point_14":"0.0","max_point_14":"4.5","min_indefinite_point_14":"0.0","max_indefinite_point_14":"4.5","indefinite_point_14":"0.0"}'},
			"UPEDIT_MODIFY_3D": {"name": "设定返点资料(3D)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","uid":"1015598","accord_point":"0.0","lotteryid":"1","prizegroup":"11","prizegroupselect":"11","method":[\"1\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"25\"],"point_1":"0.0","minpoint_1":"0.0","maxpoint_1":"9.5","point_3":"0.0","minpoint_3":"0.0","maxpoint_3":"9.5","point_4":"0.0","minpoint_4":"0.0","maxpoint_4":"4.5","point_5":"0.0","minpoint_5":"0.0","maxpoint_5":"9.5","point_6":"0.0","minpoint_6":"0.0","maxpoint_6":"9.5","point_7":"0.0","minpoint_7":"0.0","maxpoint_7":"9.5","point_8":"0.0","minpoint_8":"0.0","maxpoint_8":"9.5","point_25":"0.0","minpoint_25":"0.0","maxpoint_25":"9.5"}'},
			"UPEDIT_MODIFY_P5": {"name": "设定返点资料(P5)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","uid":"1015634","accord_point":"0.0","lotteryid":"2","prizegroup":"30","prizegroupselect":"30","point_28":"0.0","minpoint_28":"0.0","maxpoint_28":9.5,"point_30":"0.0","minpoint_30":"0.0","maxpoint_30":9.5,"point_31":"0.0","minpoint_31":"0.0","maxpoint_31":4.5,"point_32":"0.0","minpoint_32":"0.0","maxpoint_32":9.5,"point_33":"0.0","minpoint_33":"0.0","maxpoint_33":9.5,"point_34":"0.0","minpoint_34":"0.0","maxpoint_34":9.5,"point_35":"0.0","minpoint_35":"0.0","maxpoint_35":9.5,"point_36":"0.0","minpoint_36":"0.0","maxpoint_36":9.5,"method":[28,30,31,32,33,34,35,36]}'}
			
			},
		"BANK":{
			"BANKREPORT": {"name": "账变列表", "url" : "#DOMAIN#/bank/bankreport/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"1","ordertype":"1"}'
												, "data_4": '{"CGISESSID":"#TOKEN#","ordertype":"1"}'},
			},
		"SHARED":{
			"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/shared/getAdInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"GETVERSION": {"name": "版本号码", "url" : "#DOMAIN#/shared/getVersion/#APPID#", "data": '{}'
												, "data_4": '{}'},
			"ADDDOWNLOADCOUNT": {"name": "下载计数", "url" : "#DOMAIN#/shared/addDownloadCount/#APPID#", "data": '{"initial":"1","version":"1.0"}'
														, "data_4": '{"initial":"1","version":"2.1"}'},
			},
		"ADMIN":{
			"LOGIN": {"name": "登陆", "url" : "#DOMAIN#/admin/login/#APPID#", "data_admin": '{"account":"peric","password":"62c8ad0a15d9d1ca38d5dee762a16e01"}'},
			"GETMYAPPINFO": {"name": "应用资料", "url" : "#DOMAIN#/admin/getMyAppInfo/#APPID#", "data_admin": '{}'},
			"UPDATEAPPVERSION": {"name": "更新应用版本资料", "url" : "#DOMAIN#/admin/updateAppVersion/#APPID#", "data_admin": '======{"app_id":"7","version":"2","download_url":"1"}'},
			"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/admin/getAdInfo/#APPID#", "data_admin": '{}'},
			"UPDATEAD": {"name": "更新广告资料", "url" : "#DOMAIN#/admin/updateAd/#APPID#", "data_admin": '======{"act_id":"1","act_url":"http://apple.com/caipiao.php","img_url":"http://ios1.phl5b.org/images/lottery_gallery_img_loading2.png","start_time":"2013-05-27 10:55:35","end_time":"2016-05-27 10:56:03","enabled":"1"}'},
			"ADDAD": {"name": "新增广告资料", "url" : "#DOMAIN#/admin/addAd/#APPID#", "data_admin": '======{"app_id":"1","act_url":"http://apple.com/caipiao.php","img_url":"http://ios1.phl5b.org/images/lottery_gallery_img_loading2.png","start_time":"2013-05-27 10:55:35","end_time":"2016-05-27 10:56:03","enabled":"1"}'},
			"GET_DOWNLOAD_COUNT_LIST": {"name": "App下载次数", "url" : "#DOMAIN#/admin/getDownloadCountList/#APPID#", "data_admin": '{}'},
			"GET_LOGIN_COUNT_LIST": {"name": "取得使用者登入次数", "url" : "#DOMAIN#/admin/getLoginCountList/#APPID#", "data_admin": '{}'},
			"GET_OPEN_CODE_LIST": {"name": "取得开奖号码列表", "url" : "#DOMAIN#/admin/getOpenCodeList/#APPID#", "data_admin": '{}'},
			"GET_PUSH_MSG_LIST": {"name": "取得推送信息列表", "url" : "#DOMAIN#/admin/getPushMsgList/#APPID#", "data_admin": '{}'},
			"GET_USER_PUSH_INFO_LIST": {"name": "取得使用者手机资料", "url" : "#DOMAIN#/admin/getUserPushInfoList/#APPID#", "data_admin": '{}'},
			"SEND_PUSH_BY_DEVICEID": {"name": "发送push给使用者", "url" : "#DOMAIN#/pushcli/testPush2/#APPID#", "data_admin": '====={}'},
			"GET_OPEN_CODE": {"name": "取得开奖号码", "url" : "#DOMAIN#/opencode/getOpenCode/#APPID#", "data_admin": '{}'},
			"GET_PROJECT_LIST": {"name": "取得投注资料", "url" : "#DOMAIN#/admin/getProjectList/#APPID#", "data_admin": '{}'},
			
			},
		};
		
		
$(document).ready(function(){
	if($.cookie("DOMAIN") != "")
	{
		$("#txtDomain").val($.cookie("DOMAIN"));
	}
	$("#txtDomain").blur(function(){
		$.cookie("DOMAIN", $("#txtDomain").val());
		});
	if($.cookie("TOKEN") != "")
	{
		$("#txtToken").val($.cookie("TOKEN"));
	}
	$("#txtToken").blur(function(){
		$.cookie("TOKEN", $("#txtToken").val());
		});
	
	if($.cookie("APP_ID") != "")
	{
		$("#selAppId").val($.cookie("APP_ID"));
	}
	$("#selAppId").blur(function(){
		$.cookie("APP_ID", $("#selAppId").val());
		});
	$("#selAppId").change(function(){
		EnableFuncBtns();
		});
		
	mIsProduct = $.cookie("IS_PRODUCT");
	
	var tbl = new $.HtmlTableMgr('#tbl_api', 0, 1, 2);
	var iCount;
	for(var key in API_URL)
	{
		tbl.AddTr();
		tbl.AddTd().InnerHtml(key);
		
		iCount = 0;
		for(var key2 in API_URL[key])
		{
			if(iCount % 8 == 0 || iCount == 0)	
			{
				tbl.AddTr();
			}
			var btn = $('<button/>',
				    {
				    	id: key + "__" + key2 + "__API",
				    	class: "btn",
				        text: API_URL[key][key2]["name"],
				        click: function(){
				        		ClearAll();
				        		var obj = this;
				        		var key = obj.id.split("__")[0];
				        		var func = obj.id.split("__")[1];
				        		
				        		var url = HanldePostUrl(API_URL[key][func]["url"]);
				        		
				        		$("#txtUrl").val(url);
							$("#txtRequest").val(function(){
									if(GetPlatForm() == PLATFORM_4)
									{
							    			return HanldePostData(API_URL[key][func]["data_4"]);
							    		}
							    		else if(GetPlatForm() == PLATFORM_ADMIN)
									{
							    			return HanldePostData(API_URL[key][func]["data_admin"]);
							    		}
							    		else
							    		{
							    			return HanldePostData(API_URL[key][func]["data"]);
							    		}
								});
							if($("#txtRequest").val())
							{
								$.post("api.php"
									, { "action": "var_dump", "data": $("#txtRequest").val() }
									,function(result){
										$("#txtVarDump").val(result);
									});
								
								$.post("api.php"
									, { "action": "Aes128Encode", "is_product": mIsProduct,"data": $("#txtRequest").val(), "app_id": $("#selAppId").val()}
									,function(result){
										$("#txtAES128EncodeRequest").val(result);
										SendRequest(url, result);
									});
							}
				        	 }
				    });
			tbl.AddTd().InnerHtml(btn);
			iCount++;
		}
	}
	
	$("#btnResend").click(function(){		
		$("#txtAES128EncodeRequest").val("");
		$("#txtVarDump").val("");
		$("#txtResponse").val("");
		$("#txtAES128DecodeResponse").val("");
		
		$.post("api.php"
			, { "action": "var_dump", "data": $("#txtRequest").val() }
			,function(result){
				$("#txtVarDump").val(result);
			});
			
		$.post("api.php"
			, { "action": "Aes128Encode", "is_product": mIsProduct, "data": $("#txtRequest").val(), "app_id": $("#selAppId").val()}
			,function(result){
				$("#txtAES128EncodeRequest").val(result);
				SendRequest($("#txtUrl").val(), result);
			});
	});
	
	$("#btnInternal").click(function(){
			mIsProduct = 0;
			$("#txtDomain").val("http://192.168.99.234:8080/mobileapi/index.php");
			$.cookie("DOMAIN", $("#txtDomain").val());
			$.cookie("IS_PRODUCT", mIsProduct);
		});
	$("#btnTest").click(function(){	
			mIsProduct = 0;
			$("#txtDomain").val("http://ios1.phl5b.org");
			$.cookie("DOMAIN", $("#txtDomain").val());
			$.cookie("IS_PRODUCT", mIsProduct);
		});
	
	$("#btnProduct").click(function(){
			mIsProduct = 1;
			$("#txtDomain").val("http://mobile.ios188.com:6060");
			$.cookie("DOMAIN", $("#txtDomain").val());
			$.cookie("IS_PRODUCT", mIsProduct);
		});
	
	EnableFuncBtns();
});

function EnableFuncBtns()
{
	var platform = GetPlatForm();
	
	$("button[id$=__API]").each(function(){
			var btnId = $(this).attr("id");
			var keys = btnId.split("__");
		
			if(platform == PLATFORM_3 && API_URL[keys[0]][keys[1]]["data"])
			{
				$(this).attr("disabled", false);
			}
			else if(platform == PLATFORM_ADMIN && API_URL[keys[0]][keys[1]]["data_admin"])
			{
				$(this).attr("disabled", false);
			}
			else if(platform == PLATFORM_4 && API_URL[keys[0]][keys[1]]["data_4"])
			{
				$(this).attr("disabled", false);
			}
			else
			{
				$(this).attr("disabled", true);	
			}
		});
}
	
function SendRequest(url, data)
{
	$.post("api.php"
		, { "action": "req", "url": url, "data": data}
		,function(result){
			var rtn = jQuery.parseJSON(result);
			$("#divRequestTime").text(rtn["start"]);
			$("#divResponseTime").text(rtn["end"]);
			$("#txtResponse").val(rtn["data"]);
			$.post("api.php"
				, { "action": "Aes128Decode", "is_product": mIsProduct, "data": rtn["data"], "app_id": $("#selAppId").val()}
				,function(result){
					var rs = UnicodeDecode(result);
					$("#txtAES128DecodeResponse").val(rs);

					var json = JSON.parse(rs.replaceAll("\0",""));
					if(json.token != null)
					{
						$("#txtUserid").val(json.userid);
						$("#txtUsername").val(json.username);	
						$("#txtToken").val(json.token);	
					}
					
				});
		});
}

function ClearAll()
{
	$("#txtRequest").val("");
	$("#txtAES128EncodeRequest").val("");
	$("#txtResponse").val("");
	$("#txtAES128DecodeResponse").val("");
	$("#divRequestTime").text("");
	$("#divResponseTime").text("");
}
function UnicodeEncode(data) 
{ 
	return escape(data).replace(/%/g,"\\").toLowerCase(); 
}
function UnicodeDecode(data)
{
	return unescape(data.replace(/\\/g, "%")); 	
}

function GetToken()
{
	return $("#txtToken").val();
}

function HanldePostUrl(url)
{
	return url.replace("#DOMAIN#", $("#txtDomain").val()).replace("#APPID#", $("#selAppId").val());
}

function HanldePostData(data)
{
	var rtn;
	if(data === undefined)
	{
		return "";	
	}
	
	rtn = data.replace("#TOKEN#", $("#txtToken").val()).replace("#APP_ID#", $("#selAppId").val());
	
	if($("#chk_debug").is(':checked'))
	{
		var json = $.parseJSON(rtn);
		json.debug = 1;
		//alert(json.username);
		rtn = JSON.stringify(json);
		//alert(rtn);
	}
	
	return rtn;
}

function GetPlatForm()
{
	var ary_platform_3 = ["1", "2", "3", "4", "5", "6", "8", "11", "12"];
	var ary_platform_admin = ["7"];
	var ary_platform_4 = ["9", "10"];
	if($.inArray($("#selAppId").val(), ary_platform_3) != -1)
	{
		return PLATFORM_3;
	}
	else if($.inArray($("#selAppId").val(), ary_platform_admin) != -1)
	{
		return PLATFORM_ADMIN;
	}
	else if($.inArray($("#selAppId").val(), ary_platform_4) != -1)
	{
		return PLATFORM_4;	
	}
}
	
</script>
<div id="div_result">
</div>


Current Domain:<input id="txtDomain" style="width: 300px;" value="http://192.168.99.234:8080/mobileapi/index.php"/>
<input type="button" id="btnInternal" value="內部" />:http://192.168.99.234:8080/mobileapi/index.php，
<input type="button" id="btnTest" value="測試" />:http://ios1.phl5b.org
<input type="button" id="btnProduct" value="生产" />:http://mobile.ios188.com:6060<br/>
App Id:
<select id="selAppId">
	<option value ="1">1.Caipiao(ios)(3.0)</option>
	<option value ="2">2.Caipiao(android)(3.0)</option>
	<option value="3">3.token(ios)(3.0)</option>
	<option value="4">4.token(android)(3.0)</option>
	<option value="5">5.cs(ios)(3.0)</option>
	<option value="6">6.cs(android)(3.0)</option>
	<option value="7">7.admin(web)</option>
	<option value="8">8.博猫agent(pc)</option>
	<option value="9">9.Caipiao(ios)(4.0)</option>
	<option value="10">10.Caipiao(android)(4.0)</option>
	<option value="11">11.博猫agent(iphone)</option>
	<option value="12">12.博猫agent(android)</option>
</select>
Userid:<input id="txtUserid" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;Username:<input id="txtUsername" style="width: 100px;"/>
&nbsp;&nbsp;&nbsp;Current Token:<input id="txtToken" style="width: 300px;"/><input type="button" id="btnResend" value="Resend" />
<input type="checkbox" id="chk_debug" />debug<br/>

<div style="width: 100%;height: 40%;border: solid 1px #000000;overflow-y: scroll;">
	<table id="tbl_api"></table>
</div>

URL:<input id="txtUrl"  style="width: 90%;"/>
<table id="tbl" border="1" cellpadding="10" cellspacing="0" style="width: 100%;height: 40%;">
	<tr>
		<td class="tbltd">Request</td>
		<td class="tbltd">var_dump</td>
		<td class="tbltd">AES128EncodeRequest</td>
		<td class="tbltd">Response</td>
		<td class="tbltd">AES128DecodeResponse</td>
	</tr>
	<tr>
		<td><textarea id="txtRequest" style="width: 100%;height: 90%;"></textarea></td>
		<td><textarea id="txtVarDump" style="width: 100%;height: 90%;" readonly="readonly"></textarea></td>
		<td><textarea id="txtAES128EncodeRequest" readonly="readonly" style="width: 100%;height: 90%;"></textarea></td>
		<td><textarea id="txtResponse" readonly="readonly" style="width: 100%;height: 90%;"></textarea></td>
		<td><textarea id="txtAES128DecodeResponse" readonly="readonly" style="width: 100%;height: 90%;"></textarea></td>
	</tr>
</table>
Request Time:<div id="divRequestTime" style="display: inline;"></div><br/>
Response Time:<div id="divResponseTime" style="display: inline;"></div><br/>











