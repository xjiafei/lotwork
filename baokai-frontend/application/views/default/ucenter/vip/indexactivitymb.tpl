<!DOCTYPE html>
<html id="html">
<head>
	<meta charset="utf-8" />
	<title>宝开四月返利</title>
	<meta id="viewport" name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" />
	<link rel="stylesheet" href="{$path_img}/css/vipmobile/style/style.css" />
</head>
<body>
	<div class="main">
		<div class="ico-next"></div>
		<div class="ico-next2"></div>
		<div class="banner">
			<img src="{$path_img}/css/vipmobile/images/banner.jpg" alt="">
		</div>
		<article class="content clearfix">
			<div class="content1">
				<p class="time">2016.4.4 00:00:00 - 2016.4.9 23:59:59</p>
				<p class="text">VIP2以上用户于活动期间内达到指定销量即可享受红利彩种奖金加送，最高加送88%！<br />加码奖金无上限！</p>
				<a class="btn" href="/ht/active1.html" >查看详情</a>
	        </div>
	        <div class="content2">
	            <p class="time">2016.4.11 00:00:00 - 2016.4.16 23:59:59</p>
				<p class="text">活动期间，宝开时时彩由原5分钟开奖时间调整为每3分钟开奖<br />用户投注宝开时时彩，还可享受销量返利，最高28,888！</p>
				<a class="btn" href="/ht/active2.html" >查看详情</a>
	        </div>
	        <div class="content3">
	            <p class="time">2016.4.18 00:00:00 -2016.4.23 23:59:59</p>
				<p class="text">活动期间，用户充值并投注平台所有彩票类游戏，可获活动充值返利！</p>
			
				<a class="btn"   id="register1">立即报名</a>
	        </div>
	        <div class="content4">
	            <p class="time">2016.4.25 -2016.4.30 每天19:00:00 -22:59:59</p>
				<p class="text">VIP用户，尊享福利，奖金加送16%！<br />用户投注平台所有彩种，即可获得限时返利。</p>
			
				<a class="btn"   id="register2">立即报名</a>
	        </div>
		</article>
		<footer class="footer">
	        <div class="copy-right">&copy;2003-2016 宝开彩票 All Rights Reserved</div>
	    </footer>
	</div>
	<script>
		var fitPage = function(){
			var w = document.body.clientWidth;
			w = w > 720 ? 720: w;
			w = w / 720;
			w = w * 100;
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

	<script type="text/javascript" src="{$path_img}/js/base-all.js"></script>
	<script type="text/javascript" >
		$('.ico-next').click(function(){

			var h = $('.banner').height();
			var scrollOffset = $(window).scrollTop();
			{literal}
			$('body,html').animate({scrollTop:h + scrollOffset},1000);
			{/literal}
			
		});
		$('.ico-next2').click(function(){

			var h = $('.banner').height();
			var scrollOffset = $(window).scrollTop();
			{literal}
			$('body,html').animate({scrollTop:h + scrollOffset},1000);
			{/literal}
			
		});
		$('#register1').click(function(){
		var sdata = "month=3&source=mobile&token=" +encodeURIComponent ('{$token}');
		$.ajax({
			url:'/vipmb/application',
			dataType:'json',
			method:'post',
			data:sdata,
			success:function(res){
				if (Number(res['isSuccess']) == 1){
					alert(res['message'])
						window.location = "/ht/active3.html";
				}
			}
		});
	});
	$('#register2').click(function(){
		var sdata = "month=4&source=mobile&token=" +encodeURIComponent ('{$token}');
		$.ajax({
			url:'/vipmb/application',
			dataType:'json',
			method:'post',
			data:sdata,
			success:function(res){
				if (Number(res['isSuccess']) == 1){
					alert(res['message'])
					window.location = "/ht/active4.html";
				}
			}
		});
	});
		
		
	</script>

</body>
</html>
