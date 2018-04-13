<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>用户手册</title>
<meta name="viewport" content="width=device-width,initial-scale=1, minimum-scale=0.1, maximum-scale=1.0, user-scalable=yes"/>
<meta name="description" content="宝开彩票平台新用户注册页">
<meta name="keywords" content="宝开 宝开彩票 宝开彩票 用户注册 用户开户">
<link href="{$path_js}/css/activity/video_help/style.css" rel="stylesheet">
<!--[if lt IE 9]>
<script src="{$path_js}/js/activity/video_help/html5.js"></script>
<![endif]-->
</head>
<body>
	<header>
		<a href="#" class="logo"></a>
	</header>
	<section class="content">
		<div class="background"></div>
		<div class="video-area">
			<div id="J-video-play" class="video-play"></div>
			<div class="girl"></div>
			<nav>
				<a href="javascript:;" data-video="0" class="video guest bingo"><i></i></a>
				<a href="javascript:;" data-video="1" class="video safe"><i></i></a>
				<a href="javascript:;" data-video="2" class="video bet"><i></i></a>
				<a href="javascript:;" data-video="3" class="video dream"><i></i></a>
				<a href="../index/manual" class="guide"><i></i></a>
			</nav>
		</div>
	</section>
	<footer>
		©2014-2017 宝开彩票 All Rights Reserved
	</footer>
</body>
<script src="{$path_js}/js/activity/video_help/jquery-1.9.1.min.js" type="text/javascript"></script>
<script>
	$(function(){
		var videoDom = $('#J-video-play'),
			videoList = [
				'<embed src="http://player.youku.com/player.php/sid/XODMwODcyNjY0/v.swf" allowFullScreen="true" quality="high" width="593" height="372" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>',
				'<embed src="http://player.youku.com/player.php/sid/XODMwOTY4ODQ0/v.swf" allowFullScreen="true" quality="high" width="593" height="372" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>',
				'<embed src="http://player.youku.com/player.php/sid/XODMxMzMyNjI0/v.swf" allowFullScreen="true" quality="high" width="593" height="372" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>',
				'<embed src="http://player.youku.com/player.php/sid/XODMwODQ2NjI0/v.swf" allowFullScreen="true" quality="high" width="593" height="372" align="middle" allowScriptAccess="always" type="application/x-shockwave-flash"></embed>'
			];

		var loadVideo = function(num){

			//初始化加载默认视频
			videoDom.html(videoList[num]);
		}

		$('.video').on('click', function(){
			var num = $(this).attr('data-video');

			$('.video').removeClass('bingo');
			$(this).addClass('bingo')
			loadVideo(num);
		});

		loadVideo(0);
	});
</script>
</html>