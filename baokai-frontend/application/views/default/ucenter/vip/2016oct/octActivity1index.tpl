<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>宝开彩票-国庆一起打鬼子</title>
<meta name="description" content="国庆一起打鬼子">
<meta name="keywords" content="国庆一起打鬼子">
<link href="{$path_img}/images/activity/octActivity/week1/web/style.css" rel="stylesheet">

</head>
<body id="game">
<div class="main">
	<div class="banner">
		<div class="game">
			<div class="cloud2"></div>
			<div class="cloud"></div>
			<div class="cover"></div>
			<div class="tower">
				<div class="flag"></div>
				<div class="japanese j1"><img id = 'japan1' src="{$path_img}/images/activity/octActivity/week1/web/anim_1.gif"></div>
				<div class="japanese j2"><img id = 'japan2' src="{$path_img}/images/activity/octActivity/week1//web/anim_2.gif"></div>
				<div class="japanese j3"><img id = 'japan3' src="{$path_img}/images/activity/octActivity/week1/web/anim_3.gif"></div>
			</div>
			<div class="bags"></div>
			<div class="result">
				<img class="running" src="{$path_img}/images/activity/octActivity/week1/web/anim_4.gif">
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
		<img src="{$path_img}/images/activity/octActivity/week1/web/content.jpg">
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
<script type="text/javascript" src="{$path_js}/js/activity/octActivity/week1/web/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/activity/octActivity/week1/web/game.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.GameGa.js"></script>

<script>
	$(function(){
		$(function(){
			$('#game').fight().init();
		});
	});
</script>
</body>

</html>