<!DOCTYPE html>
<html id="html">
<head>
<meta charset="utf-8">
<title>宝开彩票-国庆一起打鬼子</title>
<meta name="description" content="国庆一起打鬼子">
<meta name="keywords" content="国庆一起打鬼子">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta content="telephone=no" name="format-detection"/>
<link href="{$path_img}/images/activity/octActivity/week1/mb/style.css" rel="stylesheet">

</head>
<body id="game">
<div class="main">
	<div class="banner">
		<div class="game">
			<div class="tower">
				<div class="flag"></div>
				<div class="japanese j1"><img id = 'japan1' src="{$path_img}/images/activity/octActivity/week1/mb/anim_1.gif"></div>
				<div class="japanese j2"><img id = 'japan2' src="{$path_img}/images/activity/octActivity/week1/mb/anim_2.gif"></div>
				<div class="japanese j3"><img id = 'japan3' src="{$path_img}/images/activity/octActivity/week1/mb/anim_3.gif"></div>
			</div>
			<div class="bags"></div>
			<div class="result">
				<img class="running" src="{$path_img}/images/activity/octActivity/week1/mb/anim_4.gif">
				<div class="line">
					<span class="tips"></span>
				</div>
				<span class="amount"></span>
				<span class="scale" ></span>
				<span class="multiple"></span>
				<span class="bonus"></span>
			</div>

		</div>
	</div>
	<div class="content">
		<img src="{$path_img}/images/activity/octActivity/week1/mb/content.jpg">
	</div>
</div>
<div class="mask"></div>
<div class="pop">
	<div class="popConfirm">
		<h3>注意</h3>
		<p>
			确定现在就向小鬼子开枪么？<br>
			（每天只能参与一次哦！）
		</p>
		<p>
			<a class="cancel" href="javascript:;">取消</a>
			<a class="confirm" href="javascript:;">确定</a>
		</p>
	</div>
	<div class="popSuccess">
		<h3>恭喜</h3>
		<p>
			获得奖励倍数<span class="img_multiple"></span><br>
			最终获得奖金<span class="result_bonus"></span>
		</p>
		<p class="count"></p>
	</div>
	<div class="popFail">
		<h3>抱歉</h3>
		<p>
			您还未达到最低投注要求
		</p>
		<p class="count"></p>
	</div>

</div>
<script type="text/javascript" src="{$path_img}/js/activity/octActivity/week1/mb/jquery-1.9.1.min.js"></script>
<script>
	var tokenInfo = encodeURIComponent ('{$token}');
	var fitPage = function(){
		var w = document.body.clientWidth;
		w = w > 720 ? 720: w;
		w = (w / 720) * 100 ;
		document.getElementById('html').style.fontSize = w + 'px';
	}
	fitPage();
	var t;
	var func = function(){
		clearTimeout(t);
		t = setTimeout(fitPage, 25);
	}
	window.onresize = func();
</script>

<script>
	$(function(){
		$(function(){
			$('#game').fight().init();
		});
	});
</script>
<script type="text/javascript" src="{$path_img}/js/activity/octActivity/week1/mb/game.js"></script>
<script type="text/javascript" src="{$path_img}/js/phoenix.GameGa.js"></script>
</body>

</html>