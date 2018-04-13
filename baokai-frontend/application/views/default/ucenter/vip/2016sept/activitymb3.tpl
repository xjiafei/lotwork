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
	<link rel="stylesheet" type="text/css" href="{$path_img}/images/activity/sept/week3/mb/css/style.css">

</head>
<body>
<div class="all">
<div class="banner">
	<img src="{$path_img}/images/activity/sept/week3/mb/images/banner{if $viplvl gt 0}_mb1{else}_mb0{/if}.jpg">
	<div class="result">
		<div class="rabbitBox">
			<span class="rabbit"></span>
			<div class="words"></div>
		</div>
	</div>
</div>
<div class="main">
	<img src="{$path_img}/images/activity/sept/week3/mb/images/main.jpg">
</div>
</div>

<div class="pop">
	<a href="javascript:;" class="close"></a>
	<div class="popContent"></div>
	<!-- <p><a class="more" href="#">更多活动</a></p> -->
</div>
<script type="text/javascript" src="{$path_img}/js/jquery-1.9.1.min.js"></script>
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
</body>
<script>
	$(function(){
		var sdata = "token=" + encodeURIComponent ('{$token}');
		$.ajax({
			dataType: 'json',
			cache: false,
			url: '/vipmb/week3getmbdata',
			data: sdata,
			success:function(data){
				if(data){
					afterAjax(data);
				}
			}
		});
		function afterAjax(data){
			//先判斷活動是否開始
			var activityStart = new Date(2016, 8, 19, 0, 0, 0, 0);
			var now = new Date();
			if( (activityStart.getTime() - now.getTime()) > 0){
				showMessage("4");
				$('.rabbitBox').hide();
				return;
			} else {
				if(!data.isJoin){
					showMessage("0");
					$('.rabbitBox').hide();
					return;
				}
				
				if(data.isFinished){
					if(data.prize == '0'){
						showMessage("2",300);
						$('.rabbitBox').hide();
						return;
					}else{
						showMessage("1",data.prize);
						$('.rabbitBox').hide();
						return;
					}
				} else if(!data.betToday) {
					showMessage("3");
					$('.rabbitBox').hide();
					return;
				}
				setRabbit(data);
			}
		};
		function setRabbit(data){
			var moneyMap = { "1":"1千5百","2":"5千","3":"1万","4":"5万"};
			$('.rabbitBox').addClass('pos'+data.position.level+data.position.day).find('.rabbit').show();
			$('.rabbitBox').find('.words').html('您已经连续投注<span>'+data.position.day+'</span>天，还需要连续投注<span>'+
			(6-Number(data.position.day))+''+'</span>天，每天投注<em>'+moneyMap[data.position.level+''] +'</em>元以上，可获得'+
			data.prize+'元。');
			if(Number(data.position.day) == 6){
				$('.rabbitBox').find('.words').hide();
			}
		}

		function showMessage(type,prize){
			var tpl = '';
			switch(type){
				case "0":
					tpl = "<p>您还没有参加资格下次记得先报名哦</p>";
					break;
				case "1":
					tpl = "<p>恭喜您获得<span>"+ prize +"</span>元大礼</p>";
					break;
				case "2":
					tpl = '<p class="small">很遗憾，您没有完成每日投注标准已经失去参与资格~</p>';
					break;
				case "3":	
					tpl = '<p class="small">您今天投注还未达标~</p>';
					break;
				case "4":	
					tpl = '<p class="small">活动还未开始，请耐心等待~</p>';
					break;		
			};

			$('.pop').find('.popContent').html(tpl);
			$('.pop').show();

			$('.close').click(function(){
				$(this).parent().hide();
			})
		}
	});
</script>
</html>