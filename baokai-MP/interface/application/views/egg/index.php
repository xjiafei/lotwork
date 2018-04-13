<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>乐利陪您过双节 大屏Note3天天送</title>
<meta id="viewport" name="viewport" content="target-densitydpi=medium-dpi, width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta content="telephone=no" name="format-detection" />
<link rel="stylesheet" href="<?php echo $base_url;?>css/egg_style.css" />
<style type="text/css">

</style>
</head>
<body>
	<div id="eggPop" class="egg-pop">
		<img width="100%" src="<?php echo $base_url;?>images/egg_bg-egg.jpg" alt="" />
		<div class="hammer" id="hammer">锤子</div>
		<!--
		<div class="egg-tip"><strong>恭喜你!!</strong><span id="text">砸中了</span><span id="result" class="price"></span>元</div>
		-->
		<div class="egg-tip"><strong id="title">恭喜你!!</strong><p id="info"><span id="text">砸中了</span><span id="result" class="price"></span>元</p></div>
		
		
		<div class="egg-light"></div>
		<a href="javascript:void(0);" class="egg-btn"></a>
		<ul class="egg-list clearfix">
			<li><div class="egg-tips egg-tip1 animation wobble"></div></li>
			<li><div class="egg-tips egg-tip2 animation wobble"></div></li>
			<li><div class="egg-tips egg-tip3 animation wobble"></div></li>
		</ul>
		
	</div>
	<div class="content">
		<h3>活动内容</h3>
		<p>用户登入手机端，点击领奖页面即可领取登入奖励。<strong>※每日领取一次。</strong></p>
		<div class="step"><i></i><strong>1</strong><span class="text">用户登入手机端<br />点击砸蛋活动页面</span></div>
		<div class="step"><i></i><strong>2</strong><span class="text">点击砸蛋领取奖励</span></div>
		<div class="step"><i></i><strong>3</strong><span class="text">奖励0.1 ~ 1元</span></div>
		<h3>活动说明</h3>
		<ul>
			<li>1.用户于活动期间登入宝开手机端即可领取登入奖金。</li>
			<li>2.活动期间禁止任何刷量作弊行为，一经发现平台将取消参与活动资格，严重者将被冻结账号处理。</li>
			<li>3.平台保留活动最终解释权。</li>
		</ul>
	</div>
<script type="text/javascript" src="<?php echo $base_url;?>js/jquery-1.9.1.min.js"></script>

<script>
// egg - start
$(function(){
	var eggPop = $('#eggPop');
	var oEgg = $('.egg-list li');
	$('body').append('<div id="mask"></div>');//加蒙版
	$.ajax({type: 'POST',
		url:'<?php echo $base_url;?>event/doEggAct',
		dataType:'json',
		data: { "come_from": <?php echo $come_from;?>, "device": <?php echo $device;?>, "sid": "<?php echo $sid;?>", "uuid": "<?php echo $uuid;?>", "userId": <?php echo $userId;?>},
		success:function(res){
			//alert(res);
			//判断请求是否成功
			if(Number(res['isSuccess']) == 1){
				//判断是否参与过活动,如果已经参加，显示上次中奖结果
				if (res['msg'] == 0){
					$('#mask').fadeIn(200);
					$("#result").html(res.hisPrize);
					$("#text").html("今日已砸中了");
					$('.egg-tip').delay(400).fadeIn(200).animate({
						top: '200px'
					},300);
					oEgg.unbind('click');
				}if (res['msg'] == -1){
					$('#mask').fadeIn(200);
					$("#result").hide();
					$("#title").html("感谢您!!");
					$("#info").html("您今天已经参加过游戏，请明天再来");
					$('.egg-tip').delay(400).fadeIn(200).animate({
						top: '200px'
					},300);
					oEgg.unbind('click');
				}else{
					oEgg.click(function(){
						var $this = $(this);
						var x = $(this).offset().left;
						var y = $(this).offset().top;
						var w = $("#hammer").outerWidth();
						var h = $("#hammer").outerHeight();
						
						$("#hammer").css({
							left: x + 30,
							top: y - 30,
						}).show();
						
						setTimeout(function(){
							$("#hammer").addClass('hammer-current');
						},100);
					
						setTimeout(function(){
							$this.addClass('current'); //蛋碎效果
							$('.egg-tips').removeClass('animation');
							$('#mask').delay(400).fadeIn(200);
							//$(".hammer").hide();
							$("#result").html(res.prize);
							$('.egg-tip').delay(600).fadeIn(200).animate({
								top: '200px'
							},300);
							$('.egg-light').delay(1000).fadeIn(); //金花四溅
						},300);
						oEgg.unbind('click');
					});
					
				}
			}else{
				//alert('请求失败，请重新刷新页面');
			}
		}
	});
	$('#mask').click(function(){
		$('.egg-tip').fadeOut();
		$(this).fadeOut();
	})
	$('.egg-btn').click(function(){
		var top = $('.content').offset().top;
		$('body,html').animate({scrollTop:top-10},1000);
	});
	
});
// egg -- end
</script>
</body>
</html>
