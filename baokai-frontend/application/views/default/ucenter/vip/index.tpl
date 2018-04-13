<!DOCTYPE html>
<html>
<head>
<title>宝开VIP俱乐部</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="{$path_img}/css/vip/style.css"/>
<script type="text/javascript">
	hjUserData= "{$hjUserData}";
</script>
<script type="text/javascript">
	(function() {       
		function async_load(){           
			var s = document.createElement('script');          
			s.type = 'text/javascript';          
			s.async = true;           
			s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s={if $smarty.session.datas.info.vipLvl >= '1'}20{else}17{/if}";           
			var x = document.getElementsByTagName('script')[0];          
			x.parentNode.insertBefore(s, x);      
		}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  
	})();
	</script>
</head>
<body>
	<div class="service"><a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1"><img src="{$path_img}/images/vip/services.png" alt="联系客服" /></a></div>
	<div class="back"><img src="{$path_img}/images/vip/back.png" alt="返回顶部" /></div>
	{if $smarty.session.datas.info.vipLvl gt '0'}
		<div class="banner banner-vip">
			<div class="name"><strong>{$smarty.session.datas.info.account}</strong></div>
			<a href="/fund/" target="_blank" class="btn1"></a>
			<a href="{$game_server}/gameBet/cqssc/" target="_blank" class="btn2"></a>
		</div>
	{else}
		<div class="banner banner-normal">
			<div class="name">用户：<strong>{$smarty.session.datas.info.account}</strong>我们期待您的加入</div>
			<a href="{$game_server}/gameBet/cqssc/" target="_blank" class="btn1"></a>
			<a href="/fund/" class="btn2" target="_blank"></a>
		</div>
	{/if}
	<div class="content">
		
		<div class="content-list">
			<div class="model"></div>
			<ul class="content-list-left">
				<li class="list1">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·专属在线客服</p>
						<p>·专属QQ</p>
						<p>·专属电话</p>
						<p>·移动客服渠道<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list2">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·电话修改密码</p>
						<p>·电话解锁</p>
						<p>·电话确认大额提现并短信发送<span class="new">NEW</span></p>
						<p>·电话确定资金到账并短信发送<span class="new">NEW</span></p>
						<p>·当天提款次数无限次业务<span class="new">NEW</span></p>
						<p>·充值5分钟内到账</p>
						<p>·提款15分钟内到账</p>
						<p>·转账次数无限制业务<span class="new">NEW</span></p>
						<p>·新增绑卡后立即生效<span class="new">NEW</span></p>
						<p>·电话充/提业务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list3">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·专属游戏通道</p>
						<p>·追号单查询业务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list4">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·每月享有平台惊喜福利</p>
					</div>
				</li>
				<li class="list5">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·享有全年多个节日贺礼 </p>
					</div>
				</li>
				<li class="list6">
					<a href=""></a>
					<div class="base-jmp base-jmp-left animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·旅游礼包</p>
						<p>·专属活动</p>
						<p>·活动小天使</p>
					</div>
				</li>
			</ul>
			<ul class="content-list-right">
				<li class="list1">
					<a href=""></a>
					<div class="base-jmp base-jmp-right animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·话费充值服务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list2">
					<a href=""></a>
					<div class="base-jmp base-jmp-right animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·重要事假提醒服务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list3">
					<a href=""></a>
					<div class="base-jmp base-jmp-right animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·各种礼品代购服务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list4">
					<a href=""></a>
					<div class="base-jmp base-jmp-right animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·提供健康等小贴士服务<span class="new">NEW</span></p>
					</div>
				</li>
				<li class="list5">
					<a href=""></a>
					<div class="base-jmp base-jmp-right animation">
						<b class="tri-out"></b>
						<b class="tri-in"></b>
						<p>·缴纳电费账单服务<span class="new">NEW</span></p>
					</div>
				</li>
			</ul>
		</div>
		<ul class="content-text">
			<li>·宝开VIP身份的有效期为3个月。</li>
			<li>·宝开VIP身份有效期内，单月投注金额及充值金额再次达标的客户，继续延长3个月会员。</li>
			<li>·宝开VIP身份有效期到期后，若单月投注金额及充值金额未能达标的客户，平台会延长半个月观察期。</li>
			<li>·处于观察期内的宝开VIP账户仍可享受宝开VIP专属服务。</li>
			<li>·观察期内，若单月投注金额及充值金额达标，仍算保级成功，将继续延长会员服务期。</li>
			<li>·观察期后，仍未达标的账户，身份将会转变为荣誉会员。</li>
		</ul>
		<ul class="content-text2">
			<li>·  平台对第三方服务或商品，在一定范围内提供必要的支持、协商和帮助，不承担质量问题或产品所带来的后果。</li>
			<li>·  宝开彩票平台的客户如对本细则有歧义，可通过宝开彩票平台提供的正常沟通渠道进行反馈。</li>
		</ul>
	</div>
	
	<div class="footer">
		<p class="footer-copyright">&copy;2003-2016 宝开彩票 All Rights Reserved</p>
	</div>

<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
{literal}
<script type="text/javascript">
	/* jmpinfo */
	var jmpTimer = null;
	var menu = $(".content-list");
	var menuLeftLi = $(".content-list-left li");
	var menuRightLi = $(".content-list-right li");
	var baseJmp = $(".base-jmp");
	menu.hover(function(){
		$('.model').animate({opacity:.2},500);
	},function(){
		$('.model').animate({opacity:1},500);
	})
	menuLeftLi.hover(function(){
		var curTop = - $(this).find('.base-jmp').outerHeight()/2;
		$(this).addClass("hover").find('.base-jmp').show().css("margin-top",curTop).addClass("fadeInRight");
	},function(){
		var domCurrent = $(this);
		jmpTimer = setTimeout(function(){
			domCurrent.removeClass("hover").find('.base-jmp').hide().removeClass("fadeInRight");
		} ,500);
		domCurrent.find('.base-jmp').hover(function(){
			clearTimeout(jmpTimer);
		},function(){
			var domCurrent = $(this);
			jmpTimer = setTimeout(function(){
				domCurrent.hide();
			} ,500);
		});
	});
	menuRightLi.hover(function(){
		var curTop = - $(this).find('.base-jmp').outerHeight()/2;
		$(this).addClass("hover").find('.base-jmp').show().css("margin-top",curTop).addClass("fadeInLeft");
	},function(){
		var domCurrent = $(this);
		jmpTimer = setTimeout(function(){
			domCurrent.removeClass("hover").find('.base-jmp').hide().removeClass("fadeInLeft");
		} ,300);
		domCurrent.find('.base-jmp').hover(function(){
			clearTimeout(jmpTimer);
		},function(){
			var domCurrent = $(this);
			jmpTimer = setTimeout(function(){
				domCurrent.hide();
			} ,300);
		});
	});
	$(window).scroll(function(){
		if($(window).scrollTop() > 200){
			$('.back').fadeIn();
		}else{
			$('.back').fadeOut();
		}
	})
	$('.back').click(function(){
		$('body,html').animate({scrollTop:0},500);
	});
</script>
{/literal}
</body>
</html>
