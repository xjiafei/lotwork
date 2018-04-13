<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>宝开娱乐-宝开老虎机开户礼</title>
<meta name="description" content="宝开娱乐-宝开老虎机开户礼">
<meta name="keywords" content="宝开 宝开娱乐 宝开彩票 老虎机">
<link href="${staticFileContextPath}/static/css/activity/labar/style.css" rel="stylesheet">
</head>
<body>
<div class="window sub">
	<div class="main">
		<div class="header">
			<a class="logo" href="" title="宝开娱乐">宝开娱乐</a>
			<h3>宝开老虎机开户礼</h3>
		</div>
		<div class="step">
			<h3 class="step_money">恭喜您获得<span id="exchangeAmount"></span>元开户大礼包！</h3>
			<div class="step_tab" id="tab">
				<div class="tab_title">
					<div></div>
				</div>
				<ol class="list_title">
					<li id="accountLi" class="active">开户礼金</li>
					<li id="rechargeLi">充值礼金</li>
					<li id="bettingLi">投注礼金</li>
				</ol>
				<div class="tab_content">
					<div class="content_box" style="display: block;">
						<p class="color1">
							开户礼金：20元
						</p>
						<p class="color2">
							开户礼金有效期：2015年11月16日-2015年11月20日<br>
							填写手机收到的兑换码，立即领取20元开户礼金！
							礼金将在兑换成功后十五分钟内发放至PT老虎机账户。
						</p>
						<p class="color3">
							领奖说明：<br>
							用户需绑定平台唯一有效银行卡，且提款卡与绑定银行卡一致，方可进行提款。
						</p>
					</div>
					<div class="content_box">
						<p class="color1">
							充值礼金：100元
						</p>
						<p class="color2">
							充值礼金有效期：2015年11月16日~2015年11月20日<br>
							领取条件：注册后首次充值不低于100元，当天投注达到3500元，即可获得100充值礼金。
						</p>
						<p class="color3">
							领奖说明：<br>
						</p>
						<ul>
							<li>用户需首次充值不低于100元，且仅限当天投注达到3500元。礼金无须申请，将在达到要求次日派发到用户PT账户。</li>
							<li>用户需绑定平台唯一有效银行卡，每一账户、每一银行卡、每一持卡人仅可领取一次。</li>
							<li>流水仅限老虎机游戏，彩票、PT客户端非老虎机游戏，不参与此次活动，地妖之穴、海洋公主、热带滚筒、三倍利润游戏不参与。</li>
						</ul>
					</div>
					<div class="content_box">
						<p class="color1" id="exchangeBalance"></p>
						
						<p class="color2">
							礼金有效期：<br>
							方式A:2015年11月21日-2015年11月25日<br>
							方式B:2015年11月21日-2015年11月23日<br>
							您可以选择A或B方式领取，一旦选择无法更改哦~
						</p>
						<div class="choice">
							<div id="bettingStyleADev" class="choice_box dischoice choice_boder">
								<h3>方式A</h3>
								<p id="bettingStyleMultipleA" class="color2">兑换充值礼金2天内，投注满180元即可获得18元礼金，之后每3日投注满1500元即可获得50元，直至奖金送完为止，如果连续两个3
									日投注达不到要求则剩余奖金作废</p>
								<p class="color3">领取说明:</p>
								<ul>
									<li>每日礼金均为1/5投注礼金，流水仅统计当日，不累计到次日。</li>
									<li>每日投注礼金将在次日派发到用户PT账户。</li>
									<li>用户需绑定平台唯一有效银行卡，每一账户、每一银行卡、每一持卡人仅可领取一次。</li>
									<li>活动仅限平台及客户端中老虎机游戏参与，非老虎机游戏投注不计入，地狱之穴、海洋公主、热带滚筒、三倍利润游戏不参与。</li>
								</ul>
								<p style="text-align: center; padding: 10px 0px;">
									<a id="bettingStyleABtn" class="select_btn" onclick="modifyBettingStyle(1)" href="javascript:;">选定</a>
								</p>
							</div>

							<div id="bettingStyleBDev" class="choice_box dischoice choice_boder">
								<h3>方式B</h3>
								<p id="bettingStyleMultipleB" class="color2"></p>
								<p class="color3">领取说明:</p>
								<ul>
									<li>活动时间内，用户在3日内达到投注要求，即可获得奖金，3日内未达到投注要求视为自动放弃。</li>
									<li>投注礼金将在达到投注要求次个工作日派发。</li>
									<li>活动仅限平台及客户端中老虎机游戏参与，非老虎机游戏投注不计入，地狱之穴、海洋公主、三倍利润、热带滚筒游戏不参与。</li>
								</ul>
								<p style="text-align: center; padding: 10px 0px;">
									<a id="bettingStyleBBtn" class="select_btn" onclick="modifyBettingStyle(2)" href="javascript:;">选定</a>
								</p>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="tips tips2">
			<h3>玩法说明</h3>
			<ul>
				<li>活动仅限新用户参与，老用户通过作弊手段获得开户礼金将被扣除。</li>
				<li>同一玩家、同一账户、同一IP地址、同一个持卡人、相同支付方式（相同银行卡/第三方支付账号等）、同一电话号码只可参与一次。</li>
				<li>与宝开平台其他拉新活动不重叠，奖金不可重复领取。禁止作弊行为，一旦发现取消参与资格，扣除已发礼金，严重者封号处理，平台保留最终解释权。</li>
			</ul>
		</div>

	</div>
</div>
<div class="footer">
	<div class="footer_in">
		<p>© 2001-2015 宝开娱乐 All Rights Reserved</p>
	</div>
</div>

<div id="maskDev" class="mask" style="display: block"></div>
<div id="exchangeDev" class="pop2" style="display: block">
	<div class="line1">
		<input type="text" class="input" id="code" value="请填写兑换码"/>
	</div>
	<div id="errorMsg" class="line2">
		兑换验证码成功将立即获得开户礼金
	</div>
	<div id="exchangeSubDev" class="line3">
		<a id="exchangeBtn" href="javascript:;" class="exchange">立即领取20元开户礼金！</a>
	</div>
</div>

<input id="exchangeNumber" type="hidden"/>

<a href="javascript:;" id="service" class="service"></a>

</body>
<script src="${staticFileContextPath}/static/js/activity/labar/jquery-1.9.1.min.js" type="text/javascript"></script>
<script type="text/javascript">

$(function(){
	
	var isStatus = slotInit();
	
	$('#exchangeBtn').click(function(){
		$('#exchangeSubDev').hide();
		var number = $('#code').val();
		var str = 'exchangeNumber='+number;
		
		jQuery.ajax({
			type: "POST",
			url: '/user/slot/exchange',
			data : str,
			dataType :"json",
			async: false,
			success: function(data){
				var status = data.body.result.status;

				if(status == 0){
					$('#errorMsg').html("对不起，您输入的兑换码有误，请核实正确后填写。");
				}else if(status == 1){
					//$('#errorMsg').html("恭喜您获得了XX金额的PT游戏币，礼金将在兑换成功后十五分钟内发放至PT老虎机账户。");
					slotInit();
				}else if(status == 2){
					$('#errorMsg').html("对不起，您输入的兑换码已被使用过。");
				}else if (status == 3){
					$('#errorMsg').html("与注册手机不一致");
				}else if (status == 4){
					$('#errorMsg').html("活动结束");
				}else if(status == 5){
					$('#errorMsg').html("您今天的操作已经超过IP限制，请明天再来。");
				}
				else{
					alert("错误！请洽系统管理者");
				}
			},
			complete:function(){
				
			},
			error: function(xhr,status,errMsg){
				alert("错误！请洽系统管理者");
				return false;
			}
		});
		
		$('#exchangeSubDev').show();
	})
	hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
	function async_load(){
		var s = document.createElement('script');
		s.type = 'text/javascript';
		s.async = true;
		var vipLvl=${sessionScope.info.vipLvl};
		var temp=1;
		if(vipLvl==1){
			temp=20;
		}else{
			temp=17;
		}
		s.src = "";
		var x = document.getElementsByTagName('script')[0];
		x.parentNode.insertBefore(s, x);
	}
	if (window.attachEvent){
		window.attachEvent('onload', async_load);
	}else{
		window.addEventListener('load', async_load, false);
	}
	
	function service(){
		if(typeof hj5107 != "undefined")
			hj5107.openChat();
	};
	$("#service").click(function(){
		service();
	});
	//tab切换
	var tabs = $("#tab .list_title li");
	var divs = $("#tab .content_box");
	tabs.on('click',function(){
		var n = $(this).index();
		divs.hide();
		tabs.removeClass('active');
		$(this).addClass('active');
		divs.eq(n).show();
	});

	//placeholder
	$('#code').focus(function(){
		if($(this).val() != '请填写兑换码'){
		}else{
			$(this).val('');
		}
	}).blur(function(){
		if(!$(this).val()){
			$(this).val('请填写兑换码');
		}
	})
})

function slotInit(){
	jQuery.ajax({
		type: "POST",
		url: '/user/slot/exchangeCheck',
		data : '',
		dataType :"json",
		async: false,
		success: function(data){
			var status = data.body.result.status;
			var type = data.body.result.type;
			var exchangeNumber = data.body.result.exchangeNumber;
			var exchangeAmount = data.body.result.exchangeAmount;

			if(status == 0){
				//未兌換
			}else if(status == 1){
				//已兌換
				$("#exchangeNumber").val(exchangeNumber);
				
				closeExchangeDev();
				cutoverBettingLi();
				
				var amount = parseInt(exchangeAmount);
				
				$("#exchangeAmount").html(amount);
				$("#exchangeBalance").html("投注礼金："+(amount - 120)+"元");
				
				var multipleA = "";
				var multipleB = 0;
				
				if(amount == 288){
					multipleA = "5天有效期内，每天投注达到1176即可获得1/5投注礼金即33.6元，当日投注未达到要求，当日礼金作废。";
					multipleB = 5040;
				}else if(amount == 388){
					multipleA = "活动时间内，每天投注达到1876即可获得1/5投注礼金即53.6元，当日投注未达到要求，当日礼金作废。";
					multipleB = 8040;
				}else if(amount == 588){
					multipleA = "活动时间内，每天投注达到3276即可获得1/5投注礼金即93.6当日投注未达到要求，当日礼金作废。";
					multipleB = 14040;
				}else if(amount == 688){
					multipleA = "活动时间内，每天投注达到3976即可获得1/5投注礼金即113.6元，当日投注未达到要求，当日礼金作废。";
					multipleB = 17040;
				}else if(amount == 888){
					multipleA = "活动时间内，每天投注达到5376即可获得1/5投注礼金即153.6元，当日投注未达到要求，当日礼金作废。";
					multipleB = 23040;
				}else if(amount == 1000){
					multipleA = "活动时间内，每天投注达到6160即可获得1/5投注礼金即176元，当日投注未达到要求，当日礼金作废。";
					multipleB = 26400;
				}
				
				$("#bettingStyleMultipleA").html(multipleA);
				$("#bettingStyleMultipleB").html("3日内投注达到投注礼金的30倍即"+multipleB+"元");
				
				if(type == 1){
					changeBettingStyle("bettingStyleA");
				}else if(type == 2){
					changeBettingStyle("bettingStyleB");
				}
				
			}else if(status == 2){
				$('#errorMsg').html("活動結束");
			}else{
				alert("错误！请洽系统管理者");
			}
		},
		complete:function(){
			
		},
		error: function(xhr,status,errMsg){
			alert("错误！请洽系统管理者");
			return false;
		}
	});
}

function modifyBettingStyle(type){
	var number = $("#exchangeNumber").val();
	var str = 'exchangeNumber='+number+'&type='+type;
		
	jQuery.ajax({
		type: "POST",
		url: '/user/slot/modifyExchangeStyle',
		data : str,
		dataType :"json",
		async: false,
		success: function(data){
			var status = data.body.result.status;
			var type = data.body.result.type;

			if(status == 0){
				//修改失敗
			}else if(status == 1){
				//修改成功
				if(type == 1){
					changeBettingStyle("bettingStyleA");
				}else if(type == 2){
					changeBettingStyle("bettingStyleB");
				}
				window.location.reload();
			}else{
				alert("错误！请洽系统管理者");
			}
		},
		complete:function(){
			
		},
		error: function(xhr,status,errMsg){
			alert("错误！请洽系统管理者");
			return false;
		}
	});
}

function closeExchangeDev(){
	$('#exchangeDev').removeClass('pop2');
	$('#maskDev').removeClass('mask');
}

function cutoverBettingLi(){
	var tabs = $("#tab .list_title li");
	var divs = $("#tab .content_box");
	divs.hide();
	tabs.removeClass('active');
	$("#bettingLi").addClass('active');
	divs.eq(2).show();
}

function changeBettingStyle(name){
	$("#"+name+"Dev").removeClass('dischoice');
	$("#"+name+"Dev").removeClass('choice_boder');
	$("#"+name+"Dev").addClass('choiced');
	$("#bettingStyleABtn").hide();
	$("#bettingStyleBBtn").hide();
}
</script>
</html>