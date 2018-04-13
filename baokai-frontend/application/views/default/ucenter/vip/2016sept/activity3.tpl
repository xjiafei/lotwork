<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>金秋九月</title>
<meta name="description" content="金秋九月">
<meta name="keywords" content="金秋九月">
<link href="{$path_img}/images/activity/sept/week3/css/style{if $viplvl gt 0}1{else}0{/if}.css" rel="stylesheet">

</head>
<body>
<div class="allBg">
	<div class="banner">
		<div class="result">
			<div class="rabbitBox">
				<span class="rabbit"></span>
				<div class="words"></div>
			</div>
		</div>
	</div>
	<div class="main">
		<img src="{$path_img}/images/activity/sept/week3/images/main.jpg">
	</div>
</div>
<div class="footer">
	<p>©2003-2016 宝开彩票 All Rigths Reserved</p>
</div>

<div class="pop">
	<a href="javascript:;" class="close"></a>
	<div class="popContent"></div>
	<!-- <p><a class="more" href="#">更多活动</a></p> -->
</div>
<script type="text/javascript" src="{$path_img}/js/jquery-1.9.1.min.js"></script>

</body>
<script>
	$(function(){
	
		$.ajax({
			dataType: 'json',
			cache: false,
			url: '/vip/week3getdata',
			//data: data,
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