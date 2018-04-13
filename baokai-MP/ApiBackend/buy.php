<?php



?>
<head>
	<meta charset="utf-8">
	<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
	<script src="./jUtil.js"></script>
	<script src="./BetMgr.js"></script>
</head>
<style>
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
.plates
{
	height: 360px;	
}
.plate
{
	
}
.position
{
	margin: 5px 15px 5px 5px;
	padding: 0 10px 0 10px;
	width: 50px;
	height: 30px;
	border-radius: 5px 5px 5px 5px;
	background-color: #FFFFFF;
	text-align: center;
	line-height: 30px;
	color: #000000;
	float: left;
}
.balls
{
	float: left;
	width: 450px;
}
.ball
{
	margin: 5px 5px 5px 5px;
	width: 30px;
	height: 30px;
	border-radius: 15px 15px 15px 15px;
	background-color: #FFFFFF;
	text-align: center;
	line-height: 30px;
	color: #000000;
	float: left;
	cursor: pointer;
}

.ball_enable
{
	margin: 5px 5px 5px 5px;
	width: 30px;
	height: 30px;
	border-radius: 15px 15px 15px 15px;
	background-color: #FF0000;
	text-align: center;
	line-height: 30px;
	color: #FFFFFF;
	float: left;
	cursor: pointer;
}
.func
{
	margin: 5px 5px 5px 15px;
	width: 30px;
	height: 30px;
	border-radius: 5px 5px 5px 5px;
	background-color: #FFFFFF;
	text-align: center;
	line-height: 30px;
	color: #000000;
	float: left;
	cursor: pointer;
}

.times
{
	border:solid 1px #FFFFFF;
	width: 40px;
	text-align: center;
}

.data_list
{
	width: 840px;
	height: 200px;
	border: solid 1px #FFFFFF;
	overflow-x: hidden;
	overflow-y: auto;
}
.data_row
{
	height: 24px;
	line-height: 24px;
	border: solid 1px #000000;
}
.data_row:hover
{
	height: 24px;
	line-height: 24px;
	border: solid 1px #FFFFFF;
}
.data_num
{
	width: 80px;
	float: left;
}
.data_mode
{
	width: 50px;
	float: left;
}
.data_times
{
	width: 80px;
	float: left;
}
.data_money
{
	width: 100px;
	float: left;
}
.data_name
{
	width: 150px;
	float: left;
}
.data_codes
{
	width: 100px;
	float: left;
	white-space: nowrap;
}
</style>

<script>
var BetMgr = null;
	
var BUY_DATA = '{"CGISESSID":"#TOKEN#","chan_id":"#CHAN_ID","lotteryId":"#LOTTERY_ID","issue":"#ISSUE","list":[{\"codes\":\"3,3,3,3,3\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1},{\"codes\":\"1,2,3,4,5\",\"methodid\":"429",\"mode\":1,\"money\":2.0,\"nums\":1,\"times\":1}],"money":4.0}';
	
		
$(document).ready(function(){
	BetMgr = new $.BetMgr("selLotteryId", "selMethodId", "selMode", "divPlates", "txtNum", "txtTimes", "txtMoney", "divList");
	
	if($.cookie("SKIN") != "")
	{
		$("#selSkin").val($.cookie("SKIN"));
	}
	$("#selSkin").change(function(){
		SelectSkin();
	});
	$("#txtTimes").change(function(){
		BetMgr.RefreshData();
	});
	$("#btnAdd").click(function(){
			BetMgr.AddBet();
		});
	$("#btnClear").click(function(){
			BetMgr.ClearBets();
		});
	SelectSkin();
	
	$("#txtRequestData").val(BUY_DATA);
});

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

</script>

<body class="black">

<div style="margin: 4px 0 4px 0;">
	<select id="selSkin">
		<option value ="1">White</option>
		<option value ="2">Black</option>
		<option value ="3">Pink</option>
	</select>
	<select id="selLotteryId"></select>
	<select id="selMethodId"></select>
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

Request Data:<br/>
<textarea id="txtRequestData" style="width: 840px;height: 200px;"></textarea>



</body>
