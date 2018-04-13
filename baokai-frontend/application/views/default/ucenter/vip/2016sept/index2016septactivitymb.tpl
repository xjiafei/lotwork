<!DOCTYPE html>
<html id="html">
<head>
	<meta charset="UTF-8">
	<title>金秋九月</title>
	<meta name="description" content="金秋九月">
	<meta name="keywords" content="金秋九月">
	<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1,user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes"/>
	<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
	<meta content="telephone=no" name="format-detection"/>
	<link href="{$path_img}/images/activity/sept/index/mb/css/style.css" rel="stylesheet">	
</head>
<body>
<div class="all">
	<div class="banner">
		<img src="{$path_img}/images/activity/sept/index/mb/images/banner.jpg">
	</div>
	<div class="main">
		<div class="act">
			<a href="javascript:;"  id="register1"></a>
			<a href="javascript:;"  id="more1"></a>
		</div>
		<div class="act">
			<a href="javascript:;"  id="register2"></a>
			<a href="javascript:;"  id="more2"></a>
		</div>
		<div class="act">
			<a href="javascript:;" class="joinBtn" id="register3">立即报名</a>
			<a href="/vipmb/activitymbweek3?sid={$token}" class="moreInfo" id="more3">活动详情</a>
		</div>
		<div class="footer">
			<p>©2003-2016 宝开彩票 All Rigths Resrved</p>
		</div>
	</div>
</div>

<script src="{$path_img}/js/jquery-1.9.1.min.js"></script>
<script>
	var sdata3 = "month=9&source=mobile&startTime=20160919&endTime=20160925&token=" +encodeURIComponent ('{$token}');
	var isRegister3= false;
	
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
	
	checkWeek3Register();
	//點擊註冊活動3
	$('#register3').click(function(){
		if(isRegister3){
			return;
		}else{
			$.ajax({
				url:'/vipmb/application',
				dataType:'json',
				method:'post',
				data:sdata3,
				success:function(res){
					console.log(res);
					if (Number(res['isSuccess']) == 1){
						//判断是否已经报名
						if(res['isRegistered']){
						  $('.alert2').show();
						  $('#register3').addClass('finish');
					      isRegister3=true;
						}else if( res['registerOk']){
							$('.alert').show();
							$('#register3').addClass('finish');
							isRegister3=true;
						}
					}
				}
			});
		}		
	})
	
	function checkWeek3Register(){
		$.ajax({
			url:'/vipmb/registerinfo',
			dataType:'json',
			method:'post',
			data:sdata3,
			success:function(res){
				if (Number(res['isSuccess']) == 1){
					//判断是否已经报名
					if(res['isRegistered']){
						$('#register3').addClass('finish');
					}else{
							
					}
				}
			}
		});
	}
	
</script>
</body>
</html>