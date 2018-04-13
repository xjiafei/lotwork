<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>宝开--钻石加奖新玩法</title>
	<meta name="description" content="钻石加奖新玩法 投注多1远 奖金大翻12倍">
	<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 钻石加奖新玩法">
	<link rel="stylesheet" type="text/css" href="${staticFileContextPath}/static/js/game/mmc/css/style.css">
</head>
<body>
<div class="outer o1">
	<div class="inner">
		<div id="window" class="window"><span class="down"></span></div>
		<a class="btnJoin" href="/gameBet/slmmc">敢来挑战吗？</a>
		<div class="text t1">
			<p>钻石转盘为宝开独创的全新模式<br>
			中奖后以<span>开奖号码"万位"</span>为钻石号，判断其余后四位出现的钻石号个数，出现钻石号则可参与转盘将奖金大翻倍<br>
			钻石号出现越多，能翻的倍数越高！</p>
			<p>与一般模式无异，仅需在想参与加奖模式的玩法内容后勾选加注<br>
				<span>多加一块钱</span>，即可享受高倍数奖金！</p>
		</div>
		<a class="btnVideo" href="javascript:;"></a>
	</div>
</div>
<div class="outer o2">
	<div class="inner">
		<div class="text t2">
			<p>目前钻石加奖模式支持“秒开时时彩”中<br>
				“四星”、“后三”、“中三”、“后二”及“一星”的玩法</p>
		</div>
		<div class="text t3">
			<p>参加加注模式，任何正常玩法中奖后：<br>
				后四中若存在0位钻石号，奖金不变；后四中若存在1位钻石号，奖金则依照钻1转盘翻倍<br>
				依此类推最多可达钻4转盘</p>
			<p><i>各等级转盘详见下面“钻石转盘列表”</i></p>
			<p><span>加注模式支持奖金返点切换系统，因此会根据奖金返点切换而调整</span></p>
		</div>
	</div>
</div>
<div class="outer o3">
	<div class="inner">
		<div class="footer">
			<p>&copy;2003-2016 宝开娱乐 All Rights Reserved</p>
		</div>
	</div>
</div>

<div class="video_box">
	<object width="100%" height="100%">
		<param name="movie" value="transparent">
		<param name="flashvars" value="images/video.swf">
		<param name="allowFullScreen" value="true">
		<param name="allowscriptaccess" value="always">
		<param name="wmode" value="transparent">
		<embed width="100%" height="100%" wmode="transparent" src="${staticFileContextPath}/static/js/game/mmc/images/video.swf" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" >
	</object>
	<a href="javascript:void(0);" class="close"></a>
</div>


<script src="${staticFileContextPath}/static/js/game/mmc/js/jquery-1.9.1.min.js"></script>
<script>
	$(function(){
		var $window = $('#window'),
			$down = $window.find('.down');
		$window.height($(window).height());
		$(window).resize(function(){
			$window.height($(window).height());
		});
		$(window).scroll(function(){
			var t = $(this).scrollTop();
			if(t>0){
				$down.fadeOut();
			}else {
				$down.fadeIn();
			}
		});
		var $vbox = $('.video_box'),
			$vbtn = $('.btnVideo'),
			$close = $('.video_box .close');
		$vbtn.click(function(){
			$vbox.show();
		});
		$close.click(function(){
			$vbox.hide();
		})
	})
</script>
</body>

</html>