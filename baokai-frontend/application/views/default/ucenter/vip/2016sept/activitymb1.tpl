<!DOCTYPE html>
<html id="html">
<head>
	<meta charset="UTF-8">
	<title>9月丰收季</title>
	<meta name="description" content="9月丰收季">
	<meta name="keywords" content="9月丰收季">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta content="telephone=no" name="format-detection"/>
	<link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/sept/week1/mb/css/style.css">

</head>
<body>
<div class="all">
	<div class="banner">
		<img src="{$path_img}/images/activity/sept/week1/mb/images/banner.jpg">
		<a href="javascript:;" class="joinBtn" id="register">立即参加</a>
	</div>
	<div class="main">
		<h3>活动说明:</h3>
		<p><img src="{$path_img}/images/activity/sept/week1/mb/images/table.jpg"></p>
		<p class="tips">*  流水要求=当日总销量/当日累计充值金额<br>
			*  赠送奖金=当日累计充值金额*赠送比例<br>
			*  若用户存款后，未达到相应流水要求，可按实际流水获得0.3%的活动奖金。</p>
		<p>例：用户A当日累计存款50,000元，要求打50,000*23=1,150,000流水。用户A实际只打了800,000流水，则获得800,000*0.3%=2400元奖金。</p>
		<h3>活动规则:</h3>
		<p>1.  活动返奖，参与游戏只限于宝开彩票旗舰版及宝开彩票专业版彩票类游戏。投注必须为已开奖注单，视为有效投注。<br>
			2.  骰宝、大小单双销量、电子游艺类游戏销量不计算在活动销量内。<br>
			3.  超级2000玩法销量按实际销量的80%计算，例：用户在超级2000投注1,000，活动按照1,000*80%=800进行计算。<br>
			4.  本次活动采取报名制，用户需要点击“立即报名”后才能获取活动奖金。注：VIP3（包含VIP3）以上用户无需报名即可参加。<br>
			5.  参与活动要求用户每日累计充值最低1000元，包含上下级充值，平台根据当日总充值金额计算活动销量。<br>
			6.  平台新注册用户，首存优惠不得与本次活动同时享受。当首存优惠领取后，方可参与本次活动。<br>
			7.  奖励计算依据用户在活动当日内的有效投注量审核计算，两个平台销量分别独立计算。<br>
			8.  奖金发放时间：平台每天18点前发放前一天符合条件的用户。<br>
			9.  活动期间禁止任何刷量作弊行为，一经发现宝开彩票平台将取消参与活动资格，严重者将被冻结账号处理。<br>
			10.  宝开彩票平台保留活动最终解释权。</p>
		<div class="footer">
			<p>©2003-2016 宝开彩票 All Rigths Resrved</p>
		</div>
	</div>

</div>
<div class="alert">
	<p>您已完成报名<br>祝您顺利完成活动</p>
	<p><a class="confirm" href="#">确定</a></p>
</div>
<div class="alert2" >
	<p>您已报名参加</p>
	<p><a class="confirm" href="#">确定</a></p>
</div>
<script src="{$path_img}/js/jquery-1.9.1.min.js"></script>
<script>
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
		var sdata = "month=9&source=mobile&startTime=20160905&endTime=20160910&token=" +encodeURIComponent ('{$token}');
		 $.ajax({
				url:'/vipmb/registerinfo',
				dataType:'json',
				method:'post',
				data:sdata,
				success:function(res){
					if (Number(res['isSuccess']) == 1){
						//判断是否已经报名
						if(res['isRegistered']){
	                    	//$('#register').hide();
	                    	$('#register').addClass('finish');
						}else{
							//$('#register').click(function(){
							//	$('#register').hide();								
							//	$('.alert').show();
							//});
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
							$('.alert2').show();
							$('#register').hide();
							$('#register').addClass('finish');
						}else if( res['registerOk']){
	                        //$('#register').hide();
							$('.alert').show();
						    $('#register').addClass('finish');
						
						}
					}
				}
			});		
		})
		
		$('.pop .close').click(function(){
			$('.mask').hide();
			$('.pop').fadeOut();
		})

		$('.confirm').click(function(){
			$(this).parent().parent().hide();
		})
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
      ga('send', 'event', '活动1手機報名', 'click(点击)');
    });
  })

</script>
	{/literal}
</body>

</html>