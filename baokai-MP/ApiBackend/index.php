<?php
/*
$lotteryid = 9;
$issue = 679799;
$k='123qwe';
$n='0';

$sFileName = md5($lotteryid.$issue.md5($k).$n).'.txt';
echo $sFileName;
*/


//echo $date=date('Y-m-d H:i:s');
function getMicroSec()
{
	$time = explode (" ", microtime () );
        $time = $time [1] . "000";
        //$time2 = explode ( ".", $time );   
        //$time = $time2 [0];  
        return $time;  
}
/*
	$time = explode (" ", microtime () );
	echo "time1:" . $time . "<br/>";
        $time = $time [1] . ($time [0] * 1000);
        echo "time2:" . $time . "<br/>";
        $time2 = explode ( ".", $time );   
        $time = $time2 [0];  
        echo "time3:" . $time . "<br/>";
        return $time;  
        
        
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
	<link href="BetMgr.css" rel="stylesheet" type="text/css" />
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="./jUtil.js"></script>
	<script src="./BetMgr.js"></script>
</head>
<style>
.tbltd
{
	width: 20%;
	border: solid 1px #A9A9A9;
	color: #ff0000;
}
#div_api div
{
	padding: 0 4px;
	clear: both;
}
#div_api div ul
{
	list-style-type: none;
	margin: 0 0 0 0;
}
#div_api div ul li button
{
	float:left; 
}
#div_api div ul li button
{
	margin: 2px 2px 2px 2px;
}

body.normal
{
	
}
body.black
{
	background-color: #000000;
	color: #00FF00;
}
body.black input
{
	background-color: transparent;
	color: #00ff00;
}
body.black textarea
{
	background-color: transparent;
	color: #00ff00;
}
body.pink
{
	background-color: #FEF6FA;
	color: #BB2642;
}
body.pink input
{
	background-color: transparent;
	color: #BB2642;
}
body.pink textarea
{
	background-color: transparent;
	color: #BB2642;
}
body.normal .btn
{
	border: solid 1px #000000;
}
body.black .btn
{
	border: solid 1px #000000;
}
body.pink .btn
{
	border: solid 1px #FE8BCC;
	background-color: #FFE8E5;
	color: #FF006B;
}
body.normal .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #CCCCCC;
	border-right: solid 1px #CCCCCC;
	border-left: solid 1px #CCCCCC;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #CCCCCC;
	color: #000000;
}
body.normal .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #CCCCCC;
	border-right: solid 1px #CCCCCC;
	border-left: solid 1px #CCCCCC;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
body.black .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFFFFF;
	border-right: solid 1px #FFFFFF;
	border-left: solid 1px #FFFFFF;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #CCCCCC;
	color: #000000;
}
body.black .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFFFFF;
	border-right: solid 1px #FFFFFF;
	border-left: solid 1px #FFFFFF;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
body.pink .tab_on
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFC0CB;
	border-right: solid 1px #FFC0CB;
	border-left: solid 1px #FFC0CB;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	background-color: #FFC0CB;
	color: #FF006B;
}
body.pink .tab_off
{
	border-top-left-radius: 5px;
	border-top-right-radius: 5px;
	border-bottom-right-radius: 0px;
	border-bottom-left-radius: 0px;
	border-top: solid 1px #FFC0CB;
	border-right: solid 1px #FFC0CB;
	border-left: solid 1px #FFC0CB;
	float: left;
	margin: 0 2px 0 2px;
	padding: 2px 4px 2px 4px;
	cursor: pointer;
}
</style>

<script>
var BetMgr = null;
//var BASE_URL = "http://10.3.7.168:8080/mobileapi/index.php";
var mIsProduct = 0;
var BASE_URL = "http://ios1.phl5b.org";

var PLATFORM_3 = 1;
var PLATFORM_ADMIN = 2;
var PLATFORM_4 = 3;
var PLATFORM_BM = 4;
var PLATFORM_BM_AGENT = 5;



var API_URL = {"FRONT":{
			"LOGIN_AAKENT": {"name": "登入(aakent)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"aakent"}'},
			"LOGIN_APP002": {"name": "登入(app002)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app002"}'},
			"LOGIN_APP022": {"name": "登入(app022)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app022"}'},
			"LOGIN_APP023": {"name": "登入(app023)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"app023"}'},
			"LOGIN_TESTY": {"name": "登入(testy)", "url" : "#DOMAIN#/front/login/#APPID#", "data": '{"loginpass":"4d3d4e9b019ffb43e0f514f25c5a8159","loginpass_source":"46f94c8de14fb36680850768ff1b7f2a","username":"testy"}'},
			
			"LOGIN_ADMIN": {"name": "登入(admin)(总代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"admin"}'},
			"LOGIN_JEFF999": {"name": "登入(jeff999)(总代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"jeff999"}'},
			"LOGIN_JEFF7777": {"name": "登入(jeff7777)(一级)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"jeff7777"}'},
			"LOGIN_JEFF6666": {"name": "登入(jeff6666)(一级)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"jeff6666"}'},
			"LOGIN_JEFF888": {"name": "登入(jeff888)(会员)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"jeff888"}'},
			"LOGIN_fiona18": {"name": "登入(fiona18)(1代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"fiona18"}'},
			
			"LOGIN_FACAI778899": {"name": "登入(facai778899)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"facai778899"}'},
			"LOGIN_LISI987": {"name": "登入(lisi987)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"lisi987"}'},
			"LOGIN_TESTPDD": {"name": "登入(测试PDD)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"aa77d26f6738f7d9451b43eadb18d4c4","username":"测试PDD"}'},
			"LOGIN_WANGBA987": {"name": "登入(wangba987)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"wangba987"}'},
			"LOGIN_TESTMOW222": {"name": "登入(mow222)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mow222"}'},
			"LOGIN_ZHANGFEI987": {"name": "登入(zhangfei987)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"zhangfei987"}'},
			"LOGIN_MDDPERIC": {"name": "登入(mddPeric)(1代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddperic"}'},
			"LOGIN_MDDJEFF": {"name": "登入(mddJeff)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddjeff"}'},
			"LOGIN_MDDBETTER": {"name": "登入(mddBetter)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddbetter"}'},
			"LOGIN_YANNI": {"name": "登入(mddYanni)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddyanni"}'},
			"LOGIN_Laurence": {"name": "登入(mddLaurence)", "url" : "#DOMAIN#/front/login/#APPID#", "data_4": '{"loginpassSource":"46f94c8de14fb36680850768ff1b7f2a","username":"mddlaurence"}'},
			
			"LOGIN_topjohnny": {"name": "登入(topjohnny)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm_agent": '{"password":"57a9b1622e34240422aa02f81e08b3ec","username":"topjohnny"}'},
			
			
			//"LOGIN_topjohnny": {"name": "登入(topjohnny)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"57a9b1622e34240422aa02f81e08b3ec","username":"topjohnny"}'},
			
			"LOGIN_testcmd": {"name": "登入(testcmd)(总代)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"55e3bf4f08df91952fc33f55818e3448","username":"testcmd"}'},
			"LOGIN_testbetter001": {"name": "登入(testbetter001)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"f6a1e8f7deace64901f2a357c3f9605f","username":"testbetter001"}'},
			
			
			
			"LOGIN_testmdd1": {"name": "登入(testmdd1)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"ccaafdc9d57d3063f20487ec2efdb3aa","username":"testmdd1"}'},
			"LOGIN_testmdd2": {"name": "登入(testmdd2)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"4176cab5d3e985e31ccee6a6bc7aaa8b","username":"testmdd2"}'},
			"LOGIN_testmdd3": {"name": "登入(testmdd3)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"ab88bc8fc4d1b7c714df617ddbb865fe","username":"testmdd3"}'},
			
			
			
			"LOGIN_topabelplayer4": {"name": "登入(topabelplayer4)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"ad248e5a449c3988ba89084c289c974b","username":"topabelplayer4"}'},
			"LOGIN_topabelplayer6": {"name": "登入(topabelplayer6)", "url" : "#DOMAIN#/front/login/#APPID#", "data_bm": '{"password":"f921ca60a252e7cad2d7678ec3cd2290","username":"topabelplayer6"}'},
			
			},
		"GAME":{
			//"BUY_HIGH": {"name": "投注(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","lt_issue_start":"140429058","lt_project":[{\"codes\":\"0|0|6|6|9\",\"desc\":\"[五星直选]0,0,6,6,9\",\"methodid\":429,\"mode\":1,\"money\":2,\"name\":\"五星直选\",\"nums\":1,\"times\":1,\"type\":\"digital\"},{\"codes\":\"0&1&5&6&7\",\"desc\":\"[五星组选120]0&1&5&6&7\",\"methodid\":435,\"mode\":1,\"money\":2,\"name\":\"五星组选120\",\"nums\":1,\"times\":1,\"type\":\"digital\"}],"lt_total_money":4}'},
			//"BUY_HIGH_TRACE": {"name": "追号(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","lt_issue_start":"140430024","lt_project":[{\"codes\":\"5&7|5|9|9|5\",\"desc\":\"[五星直选]5&7,5,9,9,5\",\"methodid\":429,\"mode\":1,\"money\":16,\"name\":\"五星直选\",\"nums\":2,\"times\":4,\"type\":\"digital\"},{\"codes\":\"5|6&7|8|1\",\"desc\":\"[四星直选]5,6&7,8,1\",\"methodid\":442,\"mode\":1,\"money\":16,\"name\":\"四星直选\",\"nums\":2,\"times\":4,\"type\":\"digital\"},{\"codes\":\"单|小&单|小\",\"desc\":\"[后三大小单双]单,小&单,小\",\"methodid\":490,\"mode\":1,\"money\":16,\"name\":\"后三大小单双\",\"nums\":2,\"times\":4,\"type\":\"dxds\"},{\"codes\":\"豹子&顺子&对子\",\"desc\":\"[后三特殊号]豹子&顺子&对子\",\"methodid\":471,\"mode\":1,\"money\":24,\"name\":\"后三特殊号\",\"nums\":3,\"times\":4,\"type\":\"dxds\"}],"lt_total_money":216,"lt_trace_if":"yes","lt_trace_issues":[\"140430024\",\"140430025\",\"140430026\"],"lt_trace_stop":"yes"}'},
			//"BUY_LOW_3D": {"name": "投注3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","methodid":9,"lottery_currentissue":"2014112","lottery_confirmnums":"424|494|724|794","lottery_times":2,"lottery_totalamount":16,"lottery_totalnum":4,"lottery_istrace":0}'},
			//"BUY_LOW_3D_TRACE": {"name": "追号3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","methodid":9,"lottery_currentissue":"2014112","lottery_confirmnums":"334|338|364|368|634|638|664|668","lottery_times":2,"lottery_totalamount":32,"lottery_totalnum":8,"lottery_istrace":1,"trace_issue":["2014112","2014113","2014114"],"trace_stop":1,"trace_totalamount":96}'},
			//"BUY_LOW_CB": {"name": "投注双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","methodid":61,"lottery_currentissue":"2014048","lottery_confirmnums":["04 07 15 20 23 30 33+05 09:61:2:9193286975342268"],"lottery_times":2,"lottery_totalnum":14,"lottery_totalamount":56,"lottery_istrace":0}'},
			//"BUY_LOW_CB_TRACE": {"name": "追号双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryid":"#LOT_ID#","methodid":61,"lottery_currentissue":"2014048","lottery_confirmnums":["12 13 17 19 24 26 29+08 11:61:2:8269637271542689"],"lottery_times":2,"lottery_totalnum":14,"trace_totalamount":168,"lottery_istrace":1,"trace_issue":["2014048","2014049","2014050"],"trace_stop":1}'},
			
			"BUY_BM": {"name": "投注", "url" : "#DOMAIN#/game/buy/#APPID#", "data_bm": '{"jsessionid":"#TOKEN#","lottery_id":1,"balls":[{\"wayId\": 69,\"ball\":\"6|7|8\",\"viewBalls\":\"6|7|8\",\"moneyunit\":1,\"type\":\"housan.zhixuan.fushi\",\"num\":1,\"multiple\":1},{\"wayId\":69,\"ball\":\"4|4|5\",\"viewBalls\":\"4|4|5\",\"moneyunit\":1,\"type\":\"housan.zhixuan.fushi\",\"num\":1,\"multiple\":1}],"isTrace":0,"traceWinStop":0,\"orders\":{\"150204070\":1},"amount":4}'},
			"BUY_TRACE_BM": {"name": "追號", "url" : "#DOMAIN#/game/buy/#APPID#", "data_bm": '{"jsessionid":"#TOKEN#","lottery_id":1,"balls":[{\"wayId\": 69,\"ball\":\"6|7|8\",\"viewBalls\":\"6|7|8\",\"moneyunit\":1,\"type\":\"housan.zhixuan.fushi\",\"num\":1,\"multiple\":1},{\"wayId\":69,\"ball\":\"4|4|5\",\"viewBalls\":\"4|4|5\",\"moneyunit\":1,\"type\":\"housan.zhixuan.fushi\",\"num\":1,\"multiple\":1}],"isTrace":1,"traceWinStop":0,\"orders\":{\"150204070\":1,\"150204071\":1},"amount":8}'},
			
			
			"BUY_HIGH": {"name": "投注(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":1,"issue":"140429058","projects":[{\"codes\":\"0|0|6|6|9\",\"methodid\":429,\"mode\":1,\"money\":2,\"name\":\"五星直选\",\"nums\":1,\"times\":1},{\"codes\":\"0&1&5&6&7\",\"methodid\":435,\"mode\":1,\"money\":2,\"name\":\"五星组选120\",\"nums\":1,\"times\":1}],"money":4}'},
			"BUY_HIGH_TRACE": {"name": "追号(H)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":1,"issue":"140430024","projects":[{\"codes\":\"5&7|5|9|9|5\",\"methodid\":429,\"mode\":1,\"money\":16,\"name\":\"五星直选\",\"nums\":2,\"times\":4},{\"codes\":\"5|6&7|8|1\",\"methodid\":442,\"mode\":1,\"money\":16,\"name\":\"四星直选\",\"nums\":2,\"times\":4},{\"codes\":\"单|小&单|小\",\"methodid\":490,\"mode\":1,\"money\":16,\"name\":\"后三大小单双\",\"nums\":2,\"times\":4},{\"codes\":\"豹子&顺子&对子\",\"methodid\":471,\"mode\":1,\"money\":24,\"name\":\"后三特殊号\",\"nums\":3,\"times\":4}],"trace_istrace":"1","trace_stop":"1","trace_issues":[\"140430024\",\"140430025\",\"140430026\"],"money":216}'},
			"BUY_LOW_3D": {"name": "投注3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":1,"issue":"2014112","projects":[{\"codes\":\"424|494|724|794\",\"methodid\":9,\"nums\":4,\"times\":2}],"trace_istrace":"0","money":16}'},
			"BUY_LOW_3D_TRACE": {"name": "追号3D(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":1,"issue":"2014112","projects":[{\"codes\":\"334|338|364|368|634|638|664|668\",\"methodid\":9,\"nums\":8,\"times\":2}],"trace_istrace":1,"trace_stop":1,"trace_issues":["2014112","2014113","2014114"],"money":96}'},
			"BUY_LOW_CB": {"name": "投注双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":3,"issue":"2014048","projects":[{\"codes\":\"04 07 15 20 23 30 33+05 09\",\"methodid\":61,\"nums\":14,\"times\":2}],"trace_istrace":0,"money":56}'},
			"BUY_LOW_CB_TRACE": {"name": "追号双色球(L)", "url" : "#DOMAIN#/game/buy/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":3,"issue":"2014048","projects":[{\"codes\":\"12 13 17 19 24 26 29+08 11\",\"methodid\":61,\"nums\":14,\"times\":2}],"trace_istrace":1,"trace_stop":1,"trace_issues":["2014048","2014049","2014050"],"money":168}'},
			
			"BUY_4": {"name": "投注(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20140930101032","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,2,3,4,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":4.0}'},
			
			"BUY_CQSSC_4": {"name": "投注(CQSSC)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20140930101032","list":[{\"codes\":\"-,-,3,4,5\",\"methodid\":"5",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"-,-,1,2,3\",\"methodid\":"5",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":4.0}'},
			"BUY_llX5_4": {"name": "投注(乐利11选5)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20141107305162","list":[{\"codes\":\"01 02,03 04,05 06 07,-,-\",\"methodid\":"793",\"mode\":1,\"money\":24.0,\"nums\":12,\"times\":1}],"money":24.0}'},
			
			
			"BUY_LOCK_4": {"name": "投注(超过投注倍数)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20140930101032","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"5,5,5,5,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":4.0}'},
			"BUY_3D_SLIP_4": {"name": "投注(3D)(封锁+变价)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20140930101032","list":[{\"codes\":\"01,01,01\",\"methodid\":"9",\"mode\":1,\"money\":1599984.0,\"nums\":8,\"times\":99999},{\"codes\":\"23,23,23\",\"methodid\":"9",\"mode\":1,\"money\":1599984.0,\"nums\":8,\"times\":99999},{\"codes\":\"45,45,45\",\"methodid\":"9",\"mode\":1,\"money\":16.0,\"nums\":8,\"times\":1}],"money":3199984.0}'},
			
			"BUY_CB_4": {"name": "投注(双色球)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20141030401001","list":[{\"codes\":\"01,02,03,04,05,06+01\",\"methodid\":"61",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":2.0}'},
			"BUY_CB_SLIP_4": {"name": "投注(双色球)(封锁)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20141030401001","list":[{\"codes\":\"01,02,03,04,05,06+01\",\"methodid\":"61",\"mode\":1,\"money\":199998.0,\"nums\":1,\"times\":99999},{\"codes\":\"12,13,14,15,16,17+07\",\"methodid\":"61",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"23,24,25,26,27,28+13\",\"methodid\":"61",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":200002.0}'},
			
			"BUY_P35": {"name": "投注(P35)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20150203109001","list":[{"codes":"-,-,-,2,3","methodid":"1","mode":1,"money":2.0,"nums":1,"times":1}],"money":2.0}'},
			
			"PLAN_4": {"name": "追号(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20140930101032","traceIstrace":1,"traceStop":0,"traceIssues":"20140930101040,20140930101041","traceTimes":"1,1","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,2,3,4,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":8.0}'},
			
			"PLAN_3D_SLIP_4": {"name": "投注(3D)(追号)(封锁+变价)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20141105108001","traceIstrace":1,"traceStop":0,"traceIssues":"20141105108001,20141106108001,20141107108001,20141108108001,20141109108001","traceTimes":"99999,99999,99999,99999,99999","list":[{\"codes\":\"0,0,0\",\"methodid\":"9",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,1,1\",\"methodid\":"9",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"2,2,2\",\"methodid\":"9",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":2999970.0}'},
			"PLAN_CB_LOCK_4": {"name": "投注(双色球)(追号)(封锁)(4.0)", "url" : "#DOMAIN#/game/buy/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","issue":"20141106401001","traceIstrace":1,"traceStop":0,"traceIssues":"20141106401001,20141109401001,20141111401001,20141113401001,20141116401001","traceTimes":"1,1,1,1,1","list":[{\"codes\":\"01,02,03,04,05,06+01\",\"methodid\":"61",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"12,13,14,15,16,17+07\",\"methodid\":"61",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":20.0}'},
			
			"CANCELGAME": {"name": "撤游戏单", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","id":"D140505024VDIFEECFHG"}'
													, "data_bm": '{"jsessionid":"#TOKEN#","id":"2329"}'},
			"CANCELTASK": {"name": "撤追号单", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","planId":"3844","issueCode":"242873537,242873539"}'
													, "data_bm": '{"jsessionid":"#TOKEN#","id":"2329", "aDetailIds":["123","456"]}'},
			
			"CANCELGAME_HIGH": {"name": "撤游戏单(H)", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","id":"D140505024VDIFEECFHG"}'},
			"CANCELGAME_LOW": {"name": "撤游戏单(L)", "url" : "#DOMAIN#/game/cancelGame/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","id":"D2014117VCJFIIJGHK"}'},
			"CANCELTASK_HIGH": {"name": "撤追号单(H)", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","id":"T140505025VBGFIAFFEK","taskid":"242873537,242873539"}'},
			"CANCELTASK_LOW": {"name": "撤追号单(L)", "url" : "#DOMAIN#/game/cancelTask/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","id":"T2014117VICFBDKLMN","taskid":"369182,369183"}'},
			"INITDATA": {"name": "Init Data", "url" : "#DOMAIN#/game/initData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'
												, "data_bm": '{"jsessionid":"#TOKEN#","lotteryId":"#LOT_ID#"}'},
			"SIMPLEINITDATA": {"name": "Simple Init", "url" : "#DOMAIN#/game/simpleInitData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","lotteryId":"#LOT_ID#"}'},
			"ADDBONUSDATA": {"name": "Add bonus data", "url" : "#DOMAIN#/game/addBonusData/#APPID#", "data_4": '==={"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","awardGroupId":"1"}'},
			"GET_METHOD_LIST": {"name": "Get Method List", "url" : "#DOMAIN#/game/getMethodList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			
			"LOTTERY_DATA": {"name": "Get LOTTERY DATA", "url" : "#DOMAIN#/game/lotteryData/#APPID#", "data_bm": '{"jsessionid":"#TOKEN#","lotteryId":"#LOT_ID#"}'},
			
			"PRIZE_SET": {"name": "用户奖金组", "url" : "#DOMAIN#/game/prizeSet/#APPID#", "data_bm": '{"jsessionid":"#TOKEN#","lottery_id":"#LOT_ID#"}'},
			
			},
		"INFORMATION":{
			"HISTORYINFO": {"name": "开奖号码", "url" : "#DOMAIN#/information/historyInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","lotteryId":"#LOT_ID#"}'},
			"GAMELIST": {"name": "游戏列表", "url" : "#DOMAIN#/information/gameList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"GAMEDETAIL": {"name": "游戏详情", "url" : "#DOMAIN#/information/gameDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#", "id":"D2014093VCJFIIJBFK"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"68419"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","id":"68419"}'},
			"TASKLIST": {"name": "追号列表", "url" : "#DOMAIN#/information/taskList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"TASKDETAIL": {"name": "追号详情", "url" : "#DOMAIN#/information/taskDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","id":"T2014117VICFBDKLMN"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"ZCQC140923024005"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","id":"68419"}'},
			"NOTICELIST": {"name": "公告列表", "url" : "#DOMAIN#/information/noticeList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"NOTICEDETAIL": {"name": "公告详情", "url" : "#DOMAIN#/information/noticeDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","nid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","nid":"382"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","id":"68419"}'},		
			"ALLISSUE": {"name": "奖期列表", "url" : "#DOMAIN#/information/allIssue/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lottery_id":"#LOT_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","lottery_id":"#LOT_ID#"}'},
			//"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/information/getAdInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			//"GETVERSION": {"name": "版本号码", "url" : "#DOMAIN#/information/getVersion/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'},
			"GETBALANCE": {"name": "频道馀额", "url" : "#DOMAIN#/information/getBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"GETTIME": {"name": "服务器时间", "url" : "#DOMAIN#/information/getTime/#APPID#", "data": '{}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{}'},
			"USER_MSG_LIST": {"name": "站内信列表", "url" : "#DOMAIN#/information/userMsgList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'
														, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"USER_MSG_DETAIL": {"name": "站内信详情", "url" : "#DOMAIN#/information/userMsgDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","mid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","mid":"1"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","id":"1"}'},
			"USER_MSG_DEL": {"name": "删除站内信", "url" : "#DOMAIN#/information/userMsgDel/#APPID#", "data": '{"CGISESSID":"#TOKEN#","mid":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","mid":"1"}'},
			"OPEN_LINK_LIST": {"name": "链结开户列表", "url" : "#DOMAIN#/information/openLinkList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"OPEN_LINK_DETAIL": {"name": "链结开户详情", "url" : "#DOMAIN#/information/openLinkDetail/#APPID#", "data": '{"CGISESSID":"#TOKEN#","id":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","id":"1"}'},
			"OPEN_LINK_USERS": {"name": "链结开户用户", "url" : "#DOMAIN#/information/openLinkUsers/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","id":"1"}'},
			"DEL_OPEN_LINK": {"name": "删除链结开户", "url" : "#DOMAIN#/information/delOpenLink/#APPID#", "data_4": '===={"CGISESSID":"#TOKEN#","id":"1"}'},
			"MODIFY_REMARK": {"name": "修改备注", "url" : "#DOMAIN#/information/modifyRemark/#APPID#", "data_4": '===={"CGISESSID":"#TOKEN#","id":"1","remark":"1234"}'},
			"PRIZE_DETAIL": {"name": "奖金详情", "url" : "#DOMAIN#/information/prizeDetail/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"INIT_CREATE_URL": {"name": "新增链接initial", "url" : "#DOMAIN#/information/initCreateUrl/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"DO_RET_SETTING": {"name": "新增链接設置", "url" : "#DOMAIN#/information/doRetSetting/#APPID#"
														, "data_4": '===={"CGISESSID":"#TOKEN#","type":"1","setUp":"1","days":"-1","memo":"雞排要辣不要切","setValue":"0","infos":[{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":10,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"1"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":12,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"1"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":14,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"3"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":17,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"3"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":19,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"6"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":21,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"6"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":36,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"12"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":38,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"12"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":13,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"2"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":16,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"2"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":33,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"11"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":35,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"11"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":15,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"4"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":18,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"4"},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":101,"awardName":"奖金组1700","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"1","lotteryId":"1"},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":103,"awardName":"奖金组1500","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"1","lotteryId":"1"},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":102,"awardName":"奖金组1700","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"1","lotteryId":"2"},{"lotterySeriesCode":2,"lotterySeriesName":"3D系","awardGroupId":106,"awardName":"奖金组1500","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"1","lotteryId":"2"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":41,"awardName":"奖金组1800","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"14"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":43,"awardName":"奖金组1500","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"14"},{"lotterySeriesCode":1,"lotterySeriesName":"时时彩系","awardGroupId":57,"awardName":"奖金组1700","directRet":1.5,"threeoneRet":1.5,"directLimitRet":200,"threeLimitRet":200,"channelId":"4","lotteryId":"15"},{"lotterySeriesCode":4,"lotterySeriesName":"基诺系","awardGroupId":32,"awardName":"混合奖金组","directRet":4.5,"threeoneRet":4.5,"directLimitRet":500,"threeLimitRet":500,"channelId":"4","lotteryId":"9"},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":24,"awardName":"奖金组1782","directRet":3.5,"threeoneRet":3.5,"directLimitRet":400,"threeLimitRet":400,"channelId":"4","lotteryId":"5"},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":26,"awardName":"奖金组1620","directRet":2.5,"threeoneRet":2.5,"directLimitRet":300,"threeLimitRet":300,"channelId":"4","lotteryId":"7"},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":29,"awardName":"奖金组1782","directRet":1.5,"threeoneRet":1.5,"directLimitRet":200,"threeLimitRet":200,"channelId":"4","lotteryId":"8"},{"lotterySeriesCode":3,"lotterySeriesName":"11选5系","awardGroupId":40,"awardName":"奖金组1620","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"4","lotteryId":"13"},{"lotterySeriesCode":5,"lotterySeriesName":"双色球系","awardGroupId":107,"awardName":"双色球奖金组","directRet":0.5,"threeoneRet":0.5,"directLimitRet":100,"threeLimitRet":100,"channelId":"1","lotteryId":"3"},{"lotterySeriesCode":6,"lotterySeriesName":"快乐彩系","awardGroupId":188,"awardName":"混合奖金组","directRet":3.5,"threeoneRet":3.5,"directLimitRet":400,"threeLimitRet":400,"channelId":"4","lotteryId":"16"},{"lotterySeriesCode":6,"lotterySeriesName":"快乐彩系","awardGroupId":190,"awardName":"混合奖金组","directRet":3.5,"threeoneRet":3.5,"directLimitRet":400,"threeLimitRet":400,"channelId":"4","lotteryId":"18"},{"lotterySeriesCode":7,"lotterySeriesName":"快乐彩系","awardGroupId":189,"awardName":"混合奖金组","directRet":3.5,"threeoneRet":0,"directLimitRet":400,"threeLimitRet":0,"channelId":"4","lotteryId":"17"}]}'},																																														
			
			"USER_POINT": {"name": "用户点数", "url" : "#DOMAIN#/information/userPoint/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"AGENT_INFO": {"name": "代理资料", "url" : "#DOMAIN#/information/agentInfo/#APPID#", "data_bm_agent": '{}'},
			},
		"PUSH":{
			"REGISTER_ID": {"name": "注册设备", "url" : "#DOMAIN#/push/registerId/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114","deviceid":"222222222222222222"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7","deviceid":"11111111111111111111"}'
													, "data_bm": '{"userid":"7","deviceid":"11111111111111111111"}'},
			"SWITCH_STATUS": {"name": "推送信息状态", "url" : "#DOMAIN#/push/switchStatus/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7"}'
													, "data_bm": '{"userid":"7"}'},
			"SWITCH_SET": {"name": "推送信息设置", "url" : "#DOMAIN#/push/switchSet/#APPID#", "data": '{"CGISESSID":"#TOKEN#","userid":"167114","openstatus":"0"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","userid":"7","openstatus":"0"}'
													, "data_bm": '{"userid":"7","openstatus":"0"}'}
			},
		"RECHARGE":{
			"INIT": {"name": "充值初始化", "url" : "#DOMAIN#/recharge/init/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'
												, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"CONFIRM": {"name": "充值确认", "url" : "#DOMAIN#/recharge/confirm/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bid":"9","bankradio":"ccb","bank":"31092","amount":"300","alertmin":"100","secpwd":"123qweasd"}'
												, "data_4": '{"CGISESSID":"#TOKEN#","bank":"10","amount":"300","alertmin":"100"}'
												, "data_bm": '{"jsessionid":"#TOKEN#","bank":"1","amount":100}'},
			"QUICKINIT": {"name": "快捷充值初始化", "url" : "#DOMAIN#/recharge/quickInit/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"QUICKCOMMIT": {"name": "快捷充值提交", "url" : "#DOMAIN#/recharge/quickCommit/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","bankId":"2", "money": 100}'},
			"CHARGE_RECORD": {"name": "充值记录查询", "url" : "#DOMAIN#/recharge/chargeRecord/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","chargeType":"0"}'},
			},
		"WITHDRAW":{
			"INIT": {"name": "提现初始化", "url" : "#DOMAIN#/withdraw/init/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'
												, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"VERIFY": {"name": "提现验证", "url" : "#DOMAIN#/withdraw/verify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bankinfo":"29865#6","money":"580"}'
																						, "data_4": '{"CGISESSID":"#TOKEN#","bankinfo":"2818#10","money":"580"}'},
			"COMMIT": {"name": "提现提交", "url" : "#DOMAIN#/withdraw/commit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bank_id":6,"cardid":"29865","money":500,"secpwd":"123qweasd"}'
												, "data_4": '{"CGISESSID":"#TOKEN#","bindId":"2818","money":50,"secpwd":"123qweasd","questionId":1,"questionpwd":"qweasd"}'
												, "data_bm": '{"jsessionid":"#TOKEN#","id":148,"amount":100,"account":"1031111111111111","fund_password":"123qweasd"}'}
			},
		"SECURITY":{
			"BANK_TO_HIGH": {"name": "转帐(Bank to High)", "url" : "#DOMAIN#/security/bankToHigh/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"HIGH_TO_BANK": {"name": "转帐(High to Bank)", "url" : "#DOMAIN#/security/highToBank/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"BANK_TO_LOW": {"name": "转帐(Bank to Low)", "url" : "#DOMAIN#/security/bankToLow/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			"LOW_TO_BANK": {"name": "转帐(Low to Bank)", "url" : "#DOMAIN#/security/lowToBank/#APPID#", "data": '{"CGISESSID":"#TOKEN#","fmoney":"100.0"}'},
			
			"CHECK_SECPASS": {"name": "资金密码验证", "url" : "#DOMAIN#/security/checkSecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","secpass":"123qweasd"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","secpass":"123qweasd"}'},
			"ADD_SECPASS": {"name": "资金密码添加", "url" : "#DOMAIN#/security/addSecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","newpass2":"57ba172a6be125cca2f449826f9980ca","confirm_newpass2":"57ba172a6be125cca2f449826f9980ca"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","newpass2":"57ba172a6be125cca2f449826f9980ca","confirmNewpass2":"57ba172a6be125cca2f449826f9980ca"}'
														, "data_bm": '{"jsessionid":"#TOKEN#","fund_password":"123qweasd","fund_password_confirmation":"123qweasd"}'},
			"MODIFY_SECPASS": {"name": "资金密码修改", "url" : "#DOMAIN#/security/modifySecpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","oldpass2":"123qweasd","newpass2":"57ba172a6be125cca2f449826f9980ca","confirm_newpass2":"57ba172a6be125cca2f449826f9980ca"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","oldpass2":"123qweasd","newpass2":"57ba172a6be125cca2f449826f9980ca","confirmNewpass2":"57ba172a6be125cca2f449826f9980ca"}'
															, "data_bm": '{"jsessionid":"#TOKEN#","old_fund_password":"123qweasd","fund_password":"123qweasd","fund_password_confirmation":"123qweasd"}'},
			"MODIFY_LOGINPASS": {"name": "登入密码修改", "url" : "#DOMAIN#/security/modifyLoginpass/#APPID#", "data": '{"CGISESSID":"#TOKEN#","oldpass":"123qwe","newpass":"46f94c8de14fb36680850768ff1b7f2a","confirm_newpass":"46f94c8de14fb36680850768ff1b7f2a"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","oldpass":"123qwe","newpass":"46f94c8de14fb36680850768ff1b7f2a","confirmNewpass":"46f94c8de14fb36680850768ff1b7f2a"}'
															, "data_bm": '{"jsessionid":"#TOKEN#","old_password":"123qwe","password":"123qwe","password_confirmation":"123qwe"}'},
			
			"CARD_SAFE_QUEST_INIT": {"name": "安全问题初始化", "url" : "#DOMAIN#/security/safeQuestInit/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"CARD_SAFE_QUEST_SET": {"name": "安全问题设定", "url" : "#DOMAIN#/security/safeQuestSet/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#", "quests":[{"qid":"1","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"},{"qid":"2","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"},{"qid":"3","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"}]}'},
			"CARD_SAFE_QUEST_VERIFY": {"name": "安全问题验证", "url" : "#DOMAIN#/security/safeQuestVerify/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#", "quests":[{"qid":"1","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"},{"qid":"2","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"}]}'},
			"CARD_SAFE_QUEST_EDIT": {"name": "安全问题修改", "url" : "#DOMAIN#/security/safeQuestEdit/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#", "quests":[{"qid":"1","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"},{"qid":"2","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"},{"qid":"3","qpwd":"36f17c3939ac3e7b2fc9396fa8e953ea"}]}'},
			
			"CARD_BINDING_INIT": {"name": "绑卡初始化", "url" : "#DOMAIN#/security/cardBindingInit/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
															, "data_4": '{"CGISESSID":"#TOKEN#"}'
															, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"CARD_LIST": {"name": "绑卡列表", "url" : "#DOMAIN#/security/cardList/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
															, "data_4": '{"CGISESSID":"#TOKEN#"}'
															, "data_bm": '{"jsessionid":"#TOKEN#"}'},
			"CARD_BINDING_CONFIRM": {"name": "绑卡验证", "url" : "#DOMAIN#/security/cardBindingConfirm/#APPID#", "data": '{"CGISESSID":"#TOKEN#","account":"6226901707847717","account_name":"史的一","id":"472972"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","account":"2222222222222222226","accountName":"jeff","id":"1"}'
															, "data_bm": '{"jsessionid":"#TOKEN#","id":"1","account_name":"jeff","account":"2222222222222222226","fund_password":"123qweasd"}'},
			"CARD_BINDING_COMMIT": {"name": "绑卡提交", "url" : "#DOMAIN#/security/cardBindingCommit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","bank_id":5,"bank": "中国民生银行","province_id":2,"province":"上海","city_id":140,"city":"上海","branch":"史的一","account_name":"史的一","account":"6226901707847718","secpass":"123qweasd"}'
															, "data_4": '===={"CGISESSID":"#TOKEN#","bankId":1,"bank": "中国工商银行","province":"北京","city":"东城","branch":"北京东城支行","accountName":"jeff","account":"2222222222222222226","secpass":"123qweasd"}'
															, "data_bm": '===={"jsessionid":"#TOKEN#","bank_id":25,"bank": "中国工商银行","province":"北京","province_id":"1","city":"东城区","branch":"北京东城支行","account_name":"jeff","account":"2222222222222222226","account_confirmation":"2222222222222222226"}'},
			"CARD_BINDING_DELETE": {"name": "绑卡删除", "url" : "#DOMAIN#/security/cardBindingDelete/#APPID#", "data": '{"CGISESSID":"#TOKEN#","account":"6226901707847718","account_name":"史的一","id":"472973"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","id":"1","bindId":"2348"}'
															, "data_bm": '{"jsessionid":"#TOKEN#","id":"1","account_name":"jeff","account": "1206222222222227","fund_password": "123qweasd"}'},
			"CITY_LIST": {"name": "城市列表", "url" : "#DOMAIN#/security/cityList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","province":"6"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","province":"6"}'
													, "data_bm": '{"jsessionid":"#TOKEN#"}'}
			},
		"USER":{
			"REGISTER": {"name": "注册新用户", "url" : "#DOMAIN#/user/register/#APPID#", "data_bm": '===={"jsessionid":"#TOKEN#","username":"jeff01","password":"123qwe","password_confirmation":"123qwe","email":"abc@c.d"}'},
			
			"ADD_USER": {"name": "注册新用户", "url" : "#DOMAIN#/user/addUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","nickname":"jeff01","usertype":"0"}'},
			"ADD_CUSTOMIZED_USER": {"name": "注册客制化用户", "url" : "#DOMAIN#/user/addCustomizedUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","nickname":"jeff01","usertype":"0"}'
															, "data_4": '{"CGISESSID":"#TOKEN#","username":"jeff01","userpass":"123qwe","usertype":"0"}'},
			"TEAM_BALANCE": {"name": "团队频道馀额", "url" : "#DOMAIN#/user/teamBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#"}'
														, "data_4": '{"CGISESSID":"#TOKEN#"}'},
			"TEAM_USER_BALANCE": {"name": "下级团队频道馀额", "url" : "#DOMAIN#/user/teamUserBalance/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","uid":"215239"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1383"}'},
			"PROXY_LIST": {"name": "下级代理和会员的列表", "url" : "#DOMAIN#/user/proxyList/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","type":"0","uid":"215239","p":"1"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","type":"0","uid":"1383","p":"0"}'},
			"PROXY_NUMBER": {"name": "下级代理和会员的个数", "url" : "#DOMAIN#/user/proxyNumber/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","uid":"215239"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1383"}'},
			"USER_BANK_REPORT": {"name": "个人银行账变", "url" : "#DOMAIN#/user/userBankReport/#APPID#", "data": '{"CGISESSID":"#TOKEN#","username":"aakent"}'
														, "data_4": '{"CGISESSID":"#TOKEN#","username":"mddperic"}'},
			"SAVEUP_DATA": {"name": "下级充值资料", "url" : "#DOMAIN#/user/saveupData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","uid":"1448"}'},
			"SAVEUP_CHECK": {"name": "检查下级充值金额", "url" : "#DOMAIN#/user/saveupCheck/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239","money":"100.00"}'},
			"SAVEUP_COMMIT": {"name": "下级充值提交", "url" : "#DOMAIN#/user/saveupCommit/#APPID#", "data": '{"CGISESSID":"#TOKEN#","uid":"215239","money":"100.00","secpwd":"123qweasd"}'},
			"SEARCH_USER": {"name": "搜寻用户", "url" : "#DOMAIN#/user/searchUser/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","username":"1800kent"}'
													, "data_4": '{"CGISESSID":"#TOKEN#","username":"mdd001"}'},
			"UPEDIT": {"name": "返点资料", "url" : "#DOMAIN#/user/upeditData/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","lotteryId":"#LOT_ID#","uid":"1015598"}'},
			"UPEDIT_MODIFY_HIGH": {"name": "设定返点资料(H)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","uid":"1015598","accord_lottery_point":"0","accord_indefinite_point":"0","lottery":[\"1\",\"2\",\"3\",\"4\",\"6\",\"11\",\"12\",\"5\",\"7\",\"8\",\"10\",\"9\",\"13\",\"14\"],"pg_1":"8","point_1":"0.0","min_point_1":"0.0","max_point_1":"4.5","min_indefinite_point_1":"0.0","max_indefinite_point_1":"4.5","indefinite_point_1":"0.0","pg_2":"43","point_2":"0.0","min_point_2":"0.0","max_point_2":"4.5","min_indefinite_point_2":"0.0","max_indefinite_point_2":"4.5","indefinite_point_2":"0.0","pg_3":"78","point_3":"0.0","min_point_3":"0.0","max_point_3":"4.5","indefinite_point_3":"0.0","min_indefinite_point_3":"0.0","max_indefinite_point_3":"4.5","pg_4":"131","point_4":"0.0","min_point_4":"0.0","max_point_4":"4.5","min_indefinite_point_4":"0.0","max_indefinite_point_4":"4.5","indefinite_point_4":"0.0","pg_5":"387","min_point_5":"0.0","max_point_5":"4.0","point_5":"0.0","pg_6":"192","point_6":"0.0","min_point_6":"0.0","max_point_6":"4.5","min_indefinite_point_6":"0.0","max_indefinite_point_6":"4.5","indefinite_point_6":"0.0","pg_7":"366","point_7":"0.0","min_point_7":"0.0","max_point_7":"4.0","pg_8":"486","point_8":"0.0","min_point_8":"0.0","max_point_8":"4.0","pg_9":"772","point_9":"0.0","min_point_9":"0.0","max_point_9":"14.0","min_indefinite_point_9":"0.0","max_indefinite_point_9":"9.0","indefinite_point_9":"0.0","pg_10":"706","point_10":"0.0","min_point_10":"0.0","max_point_10":"4.0","pg_11":"1399","point_11":"0.0","min_point_11":"0.0","max_point_11":"4.5","min_indefinite_point_11":"0.0","max_indefinite_point_11":"4.5","indefinite_point_11":"0.0","pg_12":"1530","point_12":"0.0","min_point_12":"0.0","max_point_12":"4.5","min_indefinite_point_12":"0.0","max_indefinite_point_12":"4.5","indefinite_point_12":"0.0","pg_13":"2163","min_point_13":"0.0","max_point_13":"4.0","point_13":"0.0","pg_14":"2316","point_14":"0.0","min_point_14":"0.0","max_point_14":"4.5","min_indefinite_point_14":"0.0","max_indefinite_point_14":"4.5","indefinite_point_14":"0.0"}'},
			"UPEDIT_MODIFY_3D": {"name": "设定返点资料(3D)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","uid":"1015598","accord_point":"0.0","lotteryid":"#LOT_ID#","prizegroup":"11","prizegroupselect":"11","method":[\"1\",\"3\",\"4\",\"5\",\"6\",\"7\",\"8\",\"25\"],"point_1":"0.0","minpoint_1":"0.0","maxpoint_1":"9.5","point_3":"0.0","minpoint_3":"0.0","maxpoint_3":"9.5","point_4":"0.0","minpoint_4":"0.0","maxpoint_4":"4.5","point_5":"0.0","minpoint_5":"0.0","maxpoint_5":"9.5","point_6":"0.0","minpoint_6":"0.0","maxpoint_6":"9.5","point_7":"0.0","minpoint_7":"0.0","maxpoint_7":"9.5","point_8":"0.0","minpoint_8":"0.0","maxpoint_8":"9.5","point_25":"0.0","minpoint_25":"0.0","maxpoint_25":"9.5"}'},
			"UPEDIT_MODIFY_P5": {"name": "设定返点资料(P5)", "url" : "#DOMAIN#/user/upeditModify/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","uid":"1015634","accord_point":"0.0","lotteryid":"#LOT_ID#","prizegroup":"30","prizegroupselect":"30","point_28":"0.0","minpoint_28":"0.0","maxpoint_28":9.5,"point_30":"0.0","minpoint_30":"0.0","maxpoint_30":9.5,"point_31":"0.0","minpoint_31":"0.0","maxpoint_31":4.5,"point_32":"0.0","minpoint_32":"0.0","maxpoint_32":9.5,"point_33":"0.0","minpoint_33":"0.0","maxpoint_33":9.5,"point_34":"0.0","minpoint_34":"0.0","maxpoint_34":9.5,"point_35":"0.0","minpoint_35":"0.0","maxpoint_35":9.5,"point_36":"0.0","minpoint_36":"0.0","maxpoint_36":9.5,"method":[28,30,31,32,33,34,35,36]}'},
			"MIGRATE_BALANCE": {"name": "查询3.0平台馀额", "url" : "#DOMAIN#/user/migrateBalance/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","userid":"255798","fundpassword":"123qweasd"}'},
			"MIGRATE": {"name": "资金转移3.0=>4.0", "url" : "#DOMAIN#/user/migrate/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","userid":"255798","fundpassword":"123qweasd","amount":"10"}'},
			
			},
		"BANK":{
			"BANKREPORT": {"name": "账变列表", "url" : "#DOMAIN#/bank/bankreport/#APPID#", "data": '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID#","ordertype":"1"}'
												, "data_4": '{"CGISESSID":"#TOKEN#","ordertype":"1"}'
												, "data_bm": '{"jsessionid":"#TOKEN#","type_id":"7"}'},
			},
		"SHARED":{
			"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/shared/getAdInfo/#APPID#", "data": '{"CGISESSID":"#TOKEN#"}'
												, "data_4": '{"CGISESSID":"#TOKEN#"}'
												, "data_bm": '{"CGISESSID":"#TOKEN#"}'},
			"GETVERSION": {"name": "版本号码", "url" : "#DOMAIN#/shared/getVersion/#APPID#", "data": '{}'
												, "data_4": '{}'
												, "data_bm": '{}'},
			"ADDDOWNLOADCOUNT": {"name": "下载计数", "url" : "#DOMAIN#/shared/addDownloadCount/#APPID#", "data": '{"initial":"1","version":"1.0"}'
														, "data_4": '{"initial":"1","version":"2.1"}'
														, "data_bm": '{"initial":"1","version":"2.1"}'},
			

			

			"PROXY_MULTIPLY": {"name": "app代理开通资料", "url" : "#DOMAIN#/shared/proxyMultiply/#APPID#", "data_4": '{"appCode":"1"}'},
			
			"ADD_APP_MULTIPLY_LOG": {"name": "代理开通记录", "url" : "#DOMAIN#/shared/addAppMultiplyLog/#APPID#", "data_4": '{"appCode":"1","uuid":"xxxx-xxxx-xxxx"}'},		
														
			},
		"ADMIN":{
			"LOGIN": {"name": "登陆", "url" : "#DOMAIN#/admin/login/#APPID#", "data_admin": '{"account":"peric","password":"62c8ad0a15d9d1ca38d5dee762a16e01"}'},
			"GETMYAPPINFO": {"name": "应用资料", "url" : "#DOMAIN#/admin/getMyAppInfo/#APPID#", "data_admin": '{}'},
			"UPDATEAPPVERSION": {"name": "更新应用版本资料", "url" : "#DOMAIN#/admin/updateAppVersion/#APPID#", "data_admin": '======{"app_id":"7","version":"2","download_url":"1"}'},
			"GETADINFO": {"name": "广告资料", "url" : "#DOMAIN#/admin/getAdInfo/#APPID#", "data_admin": '{}'},
			"UPDATEAD": {"name": "更新广告资料", "url" : "#DOMAIN#/admin/updateAd/#APPID#", "data_admin": '======{"act_id":"1","act_name":"act_name","subject":"测试广告","act_url":"http://apple.com/caipiao.php","img_url":"http://ios1.phl5b.org/images/lottery_gallery_img_loading2.png","start_time":"2013-05-27 10:55:35","end_time":"2016-05-27 10:56:03","enabled":"1"}'},
			"ADDAD": {"name": "新增广告资料", "url" : "#DOMAIN#/admin/addAd/#APPID#", "data_admin": '======{"app_id":"1","act_name":"act_name","subject":"测试广告","act_url":"http://apple.com/caipiao.php","img_url":"http://ios1.phl5b.org/images/lottery_gallery_img_loading2.png","start_time":"2013-05-27 10:55:35","end_time":"2016-05-27 10:56:03","enabled":"1"}'},
			"GET_DOWNLOAD_COUNT_LIST": {"name": "App下载次数", "url" : "#DOMAIN#/admin/getDownloadCountList/#APPID#", "data_admin": '{}'},
			"GET_LOGIN_COUNT_LIST": {"name": "取得使用者登入次数", "url" : "#DOMAIN#/admin/getLoginCountList/#APPID#", "data_admin": '{}'},
			"GET_OPEN_CODE_LIST": {"name": "取得开奖号码列表", "url" : "#DOMAIN#/admin/getOpenCodeList/#APPID#", "data_admin": '{}'},
			"GET_PUSH_MSG_LIST": {"name": "取得推送信息列表", "url" : "#DOMAIN#/admin/getPushMsgList/#APPID#", "data_admin": '{"app_id":"1","start_time":"2014-01-01","end_time":"2014-01-01"}'},
			
			"UPDATE_PUSH_MSG": {"name": "更新推送信息", "url" : "#DOMAIN#/admin/updatePushMsg/#APPID#", "data_admin": '====={"id":"1","pushmsg":"3D 第2014210期开奖号码为 585","pushstatus":"0","triggertime":"2014-08-07 10:10:03"}'},
			
			"GET_USER_PUSH_INFO_LIST": {"name": "取得使用者手机资料", "url" : "#DOMAIN#/admin/getUserPushInfoList/#APPID#", "data_admin": '{}'},
			"SEND_PUSH_BY_DEVICEID": {"name": "发送push给使用者", "url" : "#DOMAIN#/pushcli/testPush2/#APPID#", "data_admin": '====={}'},
			"GET_OPEN_CODE": {"name": "取得开奖号码", "url" : "#DOMAIN#/opencode/getOpenCode/#APPID#", "data_admin": '{}'},
			"GET_PROJECT_LIST": {"name": "取得投注资料", "url" : "#DOMAIN#/admin/getProjectList/#APPID#", "data_admin": '{}'},
			"GET_MIGRATE_LIST": {"name": "取得资金转移资料", "url" : "#DOMAIN#/admin/getMigrateList/#APPID#", "data_admin": '{"start":"0","end":"10"}'},
			
			"GET_DAILY_REPORT": {"name": "取得每日总销量", "url" : "#DOMAIN#/admin/getDailyReport/#APPID#", "data_admin": '{"day":"2015-05-13"}'},
			"GET_MONTHLY_DL_REPORT": {"name": "取得每月下载量", "url" : "#DOMAIN#/admin/getMonthlyDownloadReport/#APPID#", "data_admin": '{"initial":"1","start":"2015-05-18","end":"2015-06-18"}'},
			"GET_MONTHLY_BUY_REPORT": {"name": "取得每月销售量", "url" : "#DOMAIN#/admin/getMonthlyBuyReport/#APPID#", "data_admin": '{"app_id":"9","start":"2015-05-18","end":"2015-06-18"}'},
			"GET_MONTHLY_RECHARGE_REPORT": {"name": "取得每月充值金额", "url" : "#DOMAIN#/admin/getMonthlyRechargeReport/#APPID#", "data_admin": '{"start":"2015-05-18","end":"2015-06-18"}'},
			"GET_MONTHLY_COUNT_REPORT": {"name": "取得每月投注人数", "url" : "#DOMAIN#/admin/getMonthlyCountReport/#APPID#", "data_admin": '{"start":"2015-05-18","end":"2015-06-18"}'},
			
			"GET_APP_DOMAIN_ACTIVITY": {"name": "取得所有開戶域名", "url" : "#DOMAIN#/admin/getAppDomainActivity/#APPID#", "data_admin": '{}'},
			"UPDATE_APP_DOMAIN": {"name": "更新開戶域名", "url" : "#DOMAIN#/admin/updateAppDomain/#APPID#", "data_admin": '======{"app_id":"9","domain":"http://www.baidu.com"}'},
			"GET_APP_DOMAIN": {"name": "依ID取得開戶域名", "url" : "#DOMAIN#/admin/getAppDomain/#APPID#", "data_admin": '{"app_id":"9"}'},
			
			"GET_APP_MULTIPLY": {"name": "取得推广资料", "url" : "#DOMAIN#/admin/getAppMultiply/#APPID#", "data_admin": '{}'},
			"ADD_APP_MULTIPLY": {"name": "新增推广资料", "url" : "#DOMAIN#/admin/addAppMultiply/#APPID#", "data_admin": '===={"link":"http://www.yannibaoGoodGoodEat.com","image":"http://www.yannibaoGoodGoodEat.com/1.jpg","status":"1"}'},
			"UPDATE_APP_MULTIPLY": {"name": "更新推广资料", "url" : "#DOMAIN#/admin/updateAppMultiply/#APPID#", "data_admin": '===={"appCode":"1","link":"","image":"","status":"1"}'},
			"GET_APP_MULTIPLY_LOG": {"name": "取得推广资料记录", "url" : "#DOMAIN#/admin/getAppMultiplyLog/#APPID#", "data_admin": '{}'},
			"GET_APP_MULTIPLY_LOGIN": {"name": "取得推广注册", "url" : "#DOMAIN#/admin/getAppMultiplyLogin/#APPID#", "data_admin": '{}'},
			},
		"EVENT":{
			"GET_EGG_PRIZE": {"name": "取得砸蛋奖金", "url" : "#DOMAIN#/event/getEggPrize/#APPID#", "data_4": '{"CGISESSID":"#TOKEN#","system":"1"}'},
			"CONFIRM_EGG_PRIZE": {"name": "取得砸蛋奖金", "url" : "#DOMAIN#/event/confirmEggPrize/#APPID#", "data_4": '===={"CGISESSID":"#TOKEN#","uuid":"abcdefghijklmnopqrstuvwxyz"}'},
			},
		};
		
$(document).ready(function(){
	BetMgr = new $.BetMgr("selLotteryId", "selMethodId", "selMode", "divPlates", "txtNum", "txtTimes", "txtMoney", "divList", "selIssues", "selTrace", "txtTraceTimes", "divBetData", "txtToken");
	BetMgr.SetPlatform(GetPlatForm());

	$("#txtTimes").change(function(){
		BetMgr.RefreshData();
	});
	$("#btnAdd").click(function(){
			BetMgr.AddBet();
		});
	$("#btnClear").click(function(){
			BetMgr.ClearBets();
		});
	$("#btnCopy").click(function(){
			$("#txtRequest").val($("#divBetData").html());
		});
		
	$("#divBetBlock").toggle();
	$("#btnShowBet").click(function(){
				$("#divBetBlock").toggle();
			});
	$("#btnShowBet2").click(function(){
				$("#divBetBlock").toggle();
			});
	if($.cookie("MEMO") != "")
	{
		$("#txtMemo").val($.cookie("MEMO"));
	}
	$("#txtMemo").blur(function(){
		$.cookie("MEMO", $("#txtMemo").val());
		});
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
	if($.cookie("ENV") != "")
	{
		$("#selEnv").val($.cookie("ENV"));
	}
	if($.cookie("SKIN") != "")
	{
		$("#selSkin").val($.cookie("SKIN"));
	}
	$("#selAppId").blur(function(){
		$.cookie("APP_ID", $("#selAppId").val());
		});
	$("#selEnv").blur(function(){
		$.cookie("ENV", $("#selEnv").val());
		});
	$("#selAppId").change(function(){
		EnableFuncBtns();
		});
	$("#selEnv").change(function(){
		SelectEnv();
		});
	$("#selSkin").change(function(){
		SelectSkin();
		});
	$("#chk_debug").click(function(){
		SwitchDebug();
		});
		
	mIsProduct = $.cookie("IS_PRODUCT");
	
	var d = new $.DivMgr('#div_api');
	
	var iCount;
	for(var key in API_URL)
	{
		d.AddDiv().InnerHtml(key).ToParent();
		
		iCount = 0;
		d.AddDiv().AddUl();
		for(var key2 in API_URL[key])
		{
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
							    		else if(GetPlatForm() == PLATFORM_BM)
									{
							    			return HanldePostData(API_URL[key][func]["data_bm"]);
							    		}
							    		else if(GetPlatForm() == PLATFORM_BM_AGENT)
									{
							    			return HanldePostData(API_URL[key][func]["data_bm_agent"]);
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
										//result = "d7215fd2d952ce8a3305ac4ca38fe487ec68aec0b26afccae30cb20b5c015284fd18a1ff22decea80b9a5ec5f46d0f0d136e35654146e807972cba40a55f20d6df45a3f33b349d0b26cb5150fba28f27e19d0d57811bbaaab9af6352fa234111";
										$("#txtAES128EncodeRequest").val(result);
										SendRequest(url, result);
									});
							}
				        	 }
				    });
			//tbl.AddTd().InnerHtml(btn);
			d.AddLi().InnerHtml(btn).ToParent();
			iCount++;
		}
		d.ToParent().ToParent();
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
	
	EnableFuncBtns();
	SelectSkin();
	
	$("div[id^=div_req_tab").click(function(){
				var tab_id = $(this).attr("id").split("_")[3];
				//alert(tab_id);
				$("div[id^=div_req_tab").each(function(){
						$(this).attr("class", "tab_off");
					});
				$("#div_req_tab_" + tab_id).attr("class", "tab_on");
				
				$("#txtRequest").css("display", "none");
				$("#txtVarDump").css("display", "none");
				$("#txtAES128EncodeRequest").css("display", "none");
				if(tab_id == 1)
				{
					$("#txtRequest").css("display", "block");
				}else if(tab_id == 2){
					$("#txtVarDump").css("display", "block");
				}else if(tab_id == 3){
					$("#txtAES128EncodeRequest").css("display", "block");
				}
			});
			
	$("div[id^=div_resp_tab").click(function(){
				var tab_id = $(this).attr("id").split("_")[3];
				//alert(tab_id);
				$("div[id^=div_resp_tab").each(function(){
						$(this).attr("class", "tab_off");
					});
				$("#div_resp_tab_" + tab_id).attr("class", "tab_on");
				
				$("#txtAES128DecodeResponse").css("display", "none");
				$("#txtResponse").css("display", "none");
				if(tab_id == 1)
				{
					$("#txtAES128DecodeResponse").css("display", "block");
				}else if(tab_id == 2){
					$("#txtResponse").css("display", "block");
				}
			});
	
});

function SwitchDebug()
{
	var rtn = $("#txtRequest").val();
	if(rtn != "")
	{
		var json = $.parseJSON(rtn);
		if($("#chk_debug").is(':checked'))
		{
			json.debug = 1;
		}
		else
		{
			delete json.debug;
		}
		$("#txtRequest").val(JSON.stringify(json));
	}
}

function SelectSkin()
{
	var i = $("#selSkin").val();
	if(i == 1)
	{
		$("body").attr("class", "normal");
	}
	else if(i == 2)
	{
		$("body").attr("class", "black");
	}
	else if(i == 3)
	{
		$("body").attr("class", "pink");
	}
	$.cookie("SKIN", i);
}

function SelectEnv()
{
	var i = $("#selEnv").val();
	if(i == 1)
	{
		mIsProduct = 0;
		$("#txtDomain").val("http://10.3.7.133/mobileapi/index.php");
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	else if(i == 2)
	{
		mIsProduct = 0;
		$("#txtDomain").val("http://ios1.phl5b.org");
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	else if(i == 3)
	{
		mIsProduct = 1;
		
		var appId = $("#selAppId").val();
		if(appId == "13" || appId == "14")
		{
			$("#txtDomain").val("http://www.bomao21.com:6060");
		}else{
			$("#txtDomain").val("http://mobile.ios188.com:6060");
		}
		$.cookie("DOMAIN", $("#txtDomain").val());
		$.cookie("IS_PRODUCT", mIsProduct);
	}
	$.cookie("ENV", i);
}

function EnableFuncBtns()
{
	var platform = GetPlatForm();
	BetMgr.SetPlatform(platform);
	
	$("button[id$=__API]").each(function(){
			var btnId = $(this).attr("id");
			var keys = btnId.split("__");
		
			if(platform == PLATFORM_3 && API_URL[keys[0]][keys[1]]["data"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_ADMIN && API_URL[keys[0]][keys[1]]["data_admin"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_4 && API_URL[keys[0]][keys[1]]["data_4"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_BM && API_URL[keys[0]][keys[1]]["data_bm"])
			//else if(platform == PLATFORM_BM && API_URL[keys[0]][keys[1]]["data"])
			{
				$(this).attr("disabled", false).show();
			}
			else if(platform == PLATFORM_BM_AGENT && API_URL[keys[0]][keys[1]]["data_bm_agent"])
			{
				$(this).attr("disabled", false).show();
			}
			else
			{
				$(this).attr("disabled", true).hide();
			}
		});
}
	
function SendRequest(url, data)
{
	$.post("api.php"
		, { "action": "req", "url": url, "data": data}
		,function(result){
			var rtn = jQuery.parseJSON(result);
			//rtn["data"] = "673388f474d43b1e763c156e828b6cc9bfe2e19ac44da3bad2249f3d7948130ec2def2bc4478cb6d58af5e9a4e3c8ce6bf15555a88f3af2e19b0823cf8fcc1bc4e77225def60bf2192275dcfbb6f0f086c5dfed58c5ace66a48bbe5a6c2c2db5a113c9d8c143fd065807964bbb5e6c70";
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
					if(json.data != null && json.data.jsessionid != null)
					{
						$("#txtUserid").val(json.data.user_id);
						$("#txtUsername").val(json.data.username);	
						$("#txtToken").val(json.data.jsessionid);	
					}
					if(Array.isArray(json))
					{
						if(json.length != 0 && json[0]["issue"] != null && json[0]["issueCode"] != null)
						{
							$('#selIssues')
								.find('option')
								.remove()
								.end();
							$('#selTrace')
								.find('option')
								.remove()
								.end();
							for(var i = 1;i <= json.length - 1;i++)
							{
								$("#selIssues").append("<option value='" + json[i - 1]["issueCode"] + "'>" + json[i - 1]["issue"] + "</option>");
								$("#selTrace").append("<option value='" + i + "'>" + i + "</option>");
							}
							BetMgr.RefreshData();
						}
					}
					if(json["data"] != null && json["data"]["trace_issues"] != null)
					{
						var issues = json["data"]["trace_issues"];
						$('#selIssues')
							.find('option')
							.remove()
							.end();
						$('#selTrace')
							.find('option')
							.remove()
							.end();
						for(var i = 1;i <= issues.length - 1;i++)
						{
							$("#selIssues").append("<option value='" + issues[i - 1]["number"] + "'>" + issues[i - 1]["number"] + "</option>");
							$("#selTrace").append("<option value='" + i + "'>" + i + "</option>");
						}
						BetMgr.RefreshData();
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
	
	rtn = data.replace("#TOKEN#", $("#txtToken").val())
		.replace("#APP_ID#", $("#selAppId").val());
	if($("#selLotteryId").val() != null)
	{
		rtn = rtn.replace("#CHAN_ID#", BetMgr.LOTTERYS[$("#selLotteryId").val()]["chan_id"])
		.replace("#LOT_ID#", BetMgr.LOTTERYS[$("#selLotteryId").val()]["lottery_id"]);
	}
	
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
	var ary_platform_3 = ["1", "2", "3", "4", "5", "6"];
	var ary_platform_admin = ["7"];
	var ary_platform_4 = ["9", "10"];
	var ary_platform_bm = ["13", "14"];
	var ary_platform_bm_agent = ["8", "11", "12"];
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
	else if($.inArray($("#selAppId").val(), ary_platform_bm) != -1)
	{
		return PLATFORM_BM;	
	}
	else if($.inArray($("#selAppId").val(), ary_platform_bm_agent) != -1)
	{
		return PLATFORM_BM_AGENT;	
	}
}
	
</script>

<body class="black">

<div style="margin: 4px 0 4px 0;">
	<select id="selSkin">
		<option value ="1">White</option>
		<option value ="2">Black</option>
		<option value ="3">Pink</option>
	</select>
	<select id="selAppId">
		<option value ="1">1.凤凰Caipiao(ios)(3.0)</option>
		<option value ="2">2.凤凰Caipiao(android)(3.0)</option>
		<option value="3">3.凤凰token(ios)(3.0)</option>
		<option value="4">4.凤凰token(android)(3.0)</option>
		<option value="5">5.凤凰cs(ios)(3.0)</option>
		<option value="6">6.凤凰cs(android)(3.0)</option>
		<option value="7">7.admin(web)</option>
		<option value="8">8.博猫agent(pc)</option>
		<option value="9">9.凤凰Caipiao(ios)(4.0)</option>
		<option value="10">10.凤凰Caipiao(android)(4.0)</option>
		<option value="11">11.博猫agent(iphone)</option>
		<option value="12">12.博猫agent(android)</option>
		<option value="13">13.博猫Caipiao(ios)</option>
		<option value="14">14.博猫Caipiao(android)</option>
	</select>
	<select id="selEnv">
		<option value ="1">内部环境</option>
		<option value ="2">测试环境</option>
		<option value="3">生产环境</option>
	</select>
	<input id="txtDomain" style="width: 300px;" value="http://10.3.7.133/mobileapi/index.php"/>
	<select id="selLotteryId"></select>
	<select id="selMethodId"></select>
	<button id="btnShowBet">选号</button>
</div>

<div style="margin: 4px 0 4px 0;">
	Userid:<input id="txtUserid" style="width: 100px;"/>&nbsp;&nbsp;&nbsp;Username:<input id="txtUsername" style="width: 100px;"/>
	&nbsp;&nbsp;&nbsp;Current Token:<input id="txtToken" style="width: 300px;"/><button id="btnResend" class="btn">Resend</button>
	<input type="checkbox" id="chk_debug" />debug
</div>	

<div style="width: 70%;height: 40%;border: solid 1px #A9A9A9;overflow-y: scroll;float: left;">
	<div id="div_api"></div>
</div>
<div style="width: 25%;height: 40%;float: left;margin: 0 5px 0 5px;">
	Memo:
	<textarea id="txtMemo" style="width: 100%;height: 90%;"></textarea>
</div>

<div style="clear: both;height: 10px;"></div>

URL:<input id="txtUrl"  style="width: 90%; margin-bottom: 5px;border: solid 0px #000000;font-weight: bold;"/>


<div>
	<div style="float: left; width: 48%; height: 40%;">
		<div id="div_req_tab_1" class="tab_on">Request</div>
		<div id="div_req_tab_2" class="tab_off">var_dump</div>
		<div id="div_req_tab_3" class="tab_off">AES128EncodeRequest</div>
		<div style="clear: both;"></div>
		<textarea id="txtRequest" style="width: 100%;height: 90%;"></textarea>
		<textarea id="txtVarDump" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
		<textarea id="txtAES128EncodeRequest" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
	</div>
	<div style="float: left; width: 2%; height: 40%;"></div>
	<div style="float: left; width: 48%; height: 40%;">
		<div id="div_resp_tab_1" class="tab_on">AES128DecodeResponse</div>
		<div id="div_resp_tab_2" class="tab_off">Response</div>
		<div style="clear: both;"></div>
		<textarea id="txtAES128DecodeResponse" style="width: 100%;height: 90%;" readonly="readonly"></textarea>
		<textarea id="txtResponse" style="width: 100%;height: 90%;display: none;" readonly="readonly"></textarea>
	</div>
</div>

<!--
<table id="tbl" border="0" cellpadding="10" cellspacing="0" style="width: 100%;height: 40%;">
	<tr>
		<td class="tbltd">
			Request<br/>
			<textarea id="txtRequest" style="width: 100%;height: 90%;"></textarea>
		</td>
		<td class="tbltd">
			var_dump<br/>
			<textarea id="txtVarDump" style="width: 100%;height: 90%;" readonly="readonly"></textarea>
		</td>
		<td class="tbltd">
			AES128EncodeRequest<br/>
			<textarea id="txtAES128EncodeRequest" readonly="readonly" style="width: 100%;height: 90%;"></textarea>
		</td>
		<td class="tbltd">
			Response<br/>
			<textarea id="txtResponse" readonly="readonly" style="width: 100%;height: 90%;"></textarea>
		</td>
		<td class="tbltd">
			AES128DecodeResponse<br/>
			<textarea id="txtAES128DecodeResponse" readonly="readonly" style="width: 100%;height: 90%;"></textarea>
		</td>
	</tr>
</table>
-->
Request Time:<div id="divRequestTime" style="display: inline;"></div><br/>
Response Time:<div id="divResponseTime" style="display: inline;"></div><br/>

<div id="divBetArea" style="position: absolute;right: 10px; top: 8px;">
	<div style="text-align: center;">
	</div>
	<div id="divBetBlock" class="bet_block">
		<div class="top_block">
			Issues:
			<select id="selIssues"></select>
			Trace:
			<select id="selTrace"></select>
			TraceTimes:
			<input type="text" id="txtTraceTimes" value="1" style="width: 40px;"/>
		</div>
		<div id="divPlates" class="plates" onselectstart="return false"></div>
		你选择了<span id="txtNum">0</span>注，<input type="text" class="times" id="txtTimes" value="1" />倍
		<select id="selMode">
		</select>
		，共<span id="txtMoney">0</span>元
		<button id="btnAdd" class="btn">添加</button>
		<br/>
		
		注单列表：<button id="btnClear" class="btn">清空</button><br/>
		
		<div id="divList" class="data_list">
		
		</div>
		投注资料：<button id="btnCopy" class="btn">複製</button><br/>
		<div id="divBetData" class="bet_data">
		
		</div>
		<div style="text-align: center;">
			<button id="btnShowBet2">關閉</button>
		</div>
	</div>
</div>

</body>