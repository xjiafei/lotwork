<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>宝开四月返利</title>
	<meta name="description" content="四月投嗨森，秒秒变土豪！">
	<meta name="keywords" content="宝开 宝开彩票 宝开彩票 返利">
	<link rel="stylesheet" href="{$path_img}/css/vipactivity/css/style.css"/>

</head>
<body>
	<header>
		<div class="banner">
			<div class="logo"></div>
		</div>
	</header>
	<article>
		<div class="content1">
			<div class="layout">
				<p class="time">2016.4.4 00:00:00 - 2016.4.9 23:59:59</p>
				<p class="text">VIP2以上用户于活动期间内达到指定销量即可享受红利彩种奖金加送，最高加送88%！<br />加码奖金无上限！</p>
				<a class="btn" href="/ht/active1.html">查看详情</a>
			</div>
		</div>
		<div class="content2">
			<div class="layout">
				<p class="time">2016.4.11 00:00:00 - 2016.4.16 23:59:59</p>
				<p class="text">活动期间，宝开时时彩由原5分钟开奖时间调整为每3分钟开奖<br />用户投注宝开时时彩，还可享受销量返利，最高28,888！</p>
				<a class="btn"  href="/ht/active2.html">查看详情</a>
			</div>
		</div>
		<div class="content3">
			<div class="layout">
				<p class="time">2016.4.18 00:00:00 -2016.4.23 23:59:59</p>
				<p class="text">活动期间，用户充值并投注平台所有彩票类游戏，可获活动充值返利！</p>
				<a class="btn btn-reg"  id="register1"></a>
			</div>
		</div>
		<div class="content4">
			<div class="layout">
				<p class="time">2016.4.25 -2016.4.30 每天19:00:00 -22:59:59</p>
				<p class="text">VIP用户，尊享福利，奖金加送16%！<br />用户投注平台所有彩种，即可获得限时返利。</p>
				<a class="btn btn-reg"   id="register2"></a>
			</div>
		</div>
	</article>
	<footer class="footer">
		<div class="copy-right">&copy;2003-2016 宝开彩票 All Rights Reserved</div>
	</footer>
	<script type="text/javascript" src="{$path_js}/js/base-all.js"></script>
	<script>
	$('#register1').click(function(){
		var sdata = "month=3&source=web";
		$.ajax({
			url:'/vip/application',
			dataType:'json',
			method:'post',
			data:sdata,
			success:function(res){
				if (Number(res['isSuccess']) == 1){
				
					alert(res['message']);
					window.location = "/ht/active3.html";
				}
			}
		});
	});
	$('#register2').click(function(){
		var sdata = "month=4&source=web";
		$.ajax({
			url:'/vip/application',
			dataType:'json',
			method:'post',
			data:sdata,
			success:function(res){
				if (Number(res['isSuccess']) == 1){
					alert(res['message']);
					window.location = "/ht/active4.html";
				}
			}
		});
	});
	</script>
</body>
</html>