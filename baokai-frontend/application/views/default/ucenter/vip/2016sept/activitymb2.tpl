<!DOCTYPE html>
<html id="html">
<head>
	<meta charset="UTF-8">
	<title>金秋九月</title>
	<meta name="description" content="金秋九月">
	<meta name="keywords" content="宝开 宝开彩票 宝开彩票">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta content="telephone=no" name="format-detection"/>
	<link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/sept/week2/mb/css/style.css">
</head>
<body>
<div class="all">
	<div class="banner">
		<a class="sbtn showRead"  href="javascript:;">游戏说明</a>
		<a class="sbtn showRule" href="javascript:;" >游戏规则</a>
		<div class="join">
			<a class="joinGame" href="#" id="register"></a>
			<div class="joinStatus">已报名<span id="registerCount" >1230</span></div>
		</div>
		<div class="tower" id="tower">
			<div class="floorTop floor floor0">
				<div class="fbg"></div>
				<div class="topTip"></div>
			</div>
			<div class="floor floor1">
				<div class="fbg"></div>
			</div>
			<div class="floor floor2">
				<div class="fbg"></div>
			</div>
			<div class="floor floor3">
				<div class="fbg"></div>
			</div>
			<div class="floor floor4">
				<div class="fbg"></div>
			</div>
			<div class="floor floor5">
				<div class="fbg"></div>
			</div>
			<div class="floor floor6">
				<div class="fbg"></div>
			</div>
			<div class="floor floor7">
				<div class="fbg"></div>
			</div>
			<div class="tip"><span></span></div>
		</div>
	</div>
	<div class="main">
		<div class="rule">
			<a class="rclose sbtn" href="javascript:;">关闭</a>
			1. 活动返奖，参与游戏只限于宝开彩票旗舰版及宝开彩票专业版彩票类游戏。投注必须为已开奖注单，视为有效投注，当您完成某一层点灯投注要求后，即可
			在活动页面看到已完成的灯会闪烁，点击“立即点亮”领取该盏灯奖金，同时自动开始下一层销量累计。<br>
			2. 本次活动采取报名制，用户需要点击“立即报名”方可参与活动。注：VIP3（包含VIP3）以上用户无需报名即可参加。<br>
			3. 奖金领取温馨提示：为了避免错过点灯奖金，请您务必在当日23:59：59至少领取一次点灯奖金哦~如果当日您一盏灯的奖金都未领取视为自动放弃。<br>
			4. 当天奖金是可以累积领取的。<br>
			5. 骰宝大小单双销量、电子游艺类游戏销量不计算在活动销量内。<br>
			6. 超级2000玩法销量按实际销量的80%计算，例：用户在超级2000投注1,000，活动按照1,000*80%=800进行计算。<br>
			7. 平台新注册用户，首存优惠不得与本次活动同时享受。当首存优惠领取后，方可参与本次活动。<br>
			8. 奖励计算依据用户在活动当日内的有效投注量审核计算，两个平台销量分别独立计算。<br>
			9. 活动期间禁止任何刷量作弊行为，一经发现宝开彩票平台将取消参与活动资格，严重者将被冻结账号处理。<br>
			10. 宝开彩票平台保留活动最终解释权。
		</div>
		<div class="content">
			<a class="rclose sbtn" href="javascript:;">关闭</a>
			<p>用户活动期间内，每日最低销量达到“钱程万里灯”的要求后，该层灯塔会闪烁，即可点击“立即点亮”领取该盏灯奖金，同时进行高一层点灯销量累计，层层累计领先点齐7盏灯的前三名玩家系统会自动为您点亮塔顶“金玉满堂灯”，您将会额外获得奖金999元。</p>
			<img src="{$path_img}/images/activity/sept/week2/mb/images/table.jpg" />
			<p>例：用户A当日累计有效销量达 585000 元，则<br>普通用户 累计奖金为 19+169+519+1099+1699=3505 元<br>VIP用户 累计奖金为 29+199+599+1199+1999=4025 元</p>
		</div>
	</div>
</div>


<div class="pop">
	<a href="javascript:;" class="close"></a>
	<div class="popContent">
		<span class="money">1200</span>
		<a class="confirm" href="javascript:;">更多活动</a>
	</div>
</div>
<script type="text/javascript" src="{$path_img}/images/activity/sept/week2/mb/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="{$path_img}/images/activity/sept/week2/mb/js/game.js"></script>
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
		
		$('.joinStatus').hide();
		var sdata = "month=9&source=mobile&startTime=20160912&endTime=20160918&token=" +encodeURIComponent ('{$token}');
		//var sdata = "month=9&source=mobile&startTime=20160909&endTime=20160911&token=" +encodeURIComponent ('{$token}'); //ffor test
		$.ajax({
				url:'/vipmb/registerinfo',
				dataType:'json',
				method:'post',
				data:sdata,
				success:function(res){
					if (Number(res['isSuccess']) == 1){
						$('#registerCount').html(res['registerCount']);
						//判断是否已经报名
						if(res['isRegistered']){
							$('#register').hide();
							$('.joinStatus').show();
							$('#registerCount').html(+$('#registerCount').text());
							$('#tower').tower();
						}else{
								
						}
					}
				}
		});
		//點擊註冊
		$('#register').click(function(){
			$.ajax({
				url:'/vipmb/application',
				dataType:'json',
				method:'post',
				data:sdata,
				success:function(res){
					if (Number(res['isSuccess']) == 1){
						//判断是否已经报名
						if(res['isRegistered']){
							$('#register').hide();
							$('.joinStatus').show();
							$('#registerCount').html(+$('#registerCount').text());
							$('#tower').tower();
						}else{
							$('#register').hide();
							$('.joinStatus').show();
							$('#registerCount').html(+$('#registerCount').text() + +1);
							$('#tower').tower();
						}
					}
				}
			});		
		})


		$('.showRule').click(function(){
			$('.banner').hide();
			$('.rule').show();
			$('.content').hide();
		});
		$('.showRead').click(function(){
			$('.banner').hide();
			$('.rule').hide();
			$('.content').show();
		});
		$('.rclose').click(function(){
			$(this).parent().hide();
			$('.banner').show();
		});
	})
</script>

        {literal}
 <script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
  ga('create', 'UA-63536434-1', 'auto');
  ga('send', 'pageview');
  
      $(function(){
    //google analytics 点击统计
    $("#register").on('click',function(){
      ga('send', 'event', '活动2手機報名', 'click(点击)');
    });
  })

</script>
	{/literal}


</body>

</html>