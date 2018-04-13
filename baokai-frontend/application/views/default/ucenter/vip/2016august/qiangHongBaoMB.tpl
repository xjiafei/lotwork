<!DOCTYPE html>
<html id="html">
<head>
	<meta charset="UTF-8">
	<title>宝开--就四喜，'翻'宝开</title>
	<meta name="description" content="就四喜，'翻'宝开">
	<meta name="keywords" content="宝开 宝开彩票 宝开彩票">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta content="telephone=no" name="format-detection"/>
        <link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/august/qiangHongBao/mobile/style.css">
        <link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/august/qiangHongBao/mobile/animate.css">

</head>
<body>
<div class="main">
	<div class="top" id="game">
		<img class="imgbg" src="{$path_img}/images/activity/august/qiangHongBao/mobile//top.jpg">
		<div class="gameStatus"></div>
		<div class="gameBox">
			
			<ul class="cardList ">
				<li>
					<a class="front flip out" href="javascript:;"></a>
					<a class="back flip" href="javascript:;"></a>
				</li>
				<li>
					<a class="front flip out" href="javascript:;"></a>
					<a class="back flip" href="javascript:;"></a>
				</li>
				<li>
					<a class="front flip out" href="javascript:;"></a>
					<a class="back flip" href="javascript:;"></a>
				</li>
				<li>
					<a class="front flip out" href="javascript:;"></a>
					<a class="back flip" href="javascript:;"></a>
				</li>
			</ul>
			<div class="cardFun">
				<div class="funLeft"></div>
				<div class="funRight"></div>
			</div>
		</div>
	</div>
	<div class="rule">
		<img class="imgbg" src="{$path_img}/images/activity/august/qiangHongBao/mobile/rule.jpg">
	</div>
	<div class="footer">
		<p>&copy;2003-2016 宝开彩票 All Rights Reserved</p>
	</div>
	<div class="pop popTip">
		<p>凡是在<span>8月1日-8月20日</span><br>
			在平台累积投注100以上用户，<br>
			均可<a href="###">参与</a>翻牌拿奖金。</p>
		<a class="confirm" href="javascript:;">确认</a>
	</div>
	<div class="pop noneRight">
		<p>对不起，您没有翻卡抽奖的资格。<br>
			如有疑问请<a href="#"　style="cursor:text;">联系客服</a></p>
		<a class="confirm" href="javascript:;">确认</a>
	</div>
	<div class="pop noneCard">
		<p>对不起，<span>卡牌</span>已经被抢光了~下次请早啊！</p>
		<a class="confirm" href="javascript:;">确认</a>
	</div>
	<div class="pop winning">
		<p>恭喜您抽中了<span class="money"></span>元，您需要在24:00前有效投注满<span class="amount"></span>元。</p>
		<a class="confirm" href="javascript:;">确认</a>
	</div>
</div>
 <script src="{$path_img}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
 <script src="{$path_img}/js/activity/august/qiangHongBao/mobile/game.js" type="text/javascript"></script>
<script>
	var tokenInfo = encodeURIComponent ('{$info}');
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
		$('#game').card();
	});
</script>
</body>

</html>