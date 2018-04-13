<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<script type="text/javascript">global_path_url="{$path_img}";</script>
    <script type="text/javascript">hjUserData= "{$hjUserData}";</script>
    <script type="text/javascript">global_params_notice = "all,ad_top,agent_first_page";</script>
	<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.base.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
    <script type="text/javascript" src="{$path_js}/js/phoenix.Timer.js"></script>
    <script type="text/javascript">
	(function() {       
		function async_load(){           
			var s = document.createElement('script');          
			s.type = 'text/javascript';          
			s.async = true;           
			s.src = "";           
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
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
		</div>
	</div>
	<!-- header end -->
	<div class="g_33">
		<div class="appeal-content">
			<!-- star -->
			<dl class="prompt">
				<dt>温馨提示：</dt>
				<dd>请重新设置你的安全邮箱</dd>
				<dd>为保证账号安全，此链接仅能使用一次</dd>
			</dl>
			<!-- end -->
			<!-- star -->
			<div class="step">
				<table class="step-table">
					<tbody>
						<tr>
							<td class="clicked"><div class="con">输入邮箱</div></td>
							<td class="current"><div class="tri"><div class="con">查收验证邮件</div></div></td>
							<td><div class="tri"><div class="con">绑定成功</div></div></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- end -->
			<!-- star -->
			<form id="J-form-step2" name="fm_step2" method="post" action="sendmail">
				<input type="hidden" name="userId" value="{$userId}" >
				<input type="hidden" name="name" value="{$name}" >
				<input type="hidden" name="time" value="{$time}" >
				<input type="hidden" name="chkAct" value="{$chkAct}" >
				<input type="hidden" name="active" value="{$active}" >
				<input type="hidden" name="BCODE" value="{$BCODE}" >
				<input type="hidden" name="email" value="{$email}" >
				<ul class="ui-form">
					<li><p class="ui-text">验证邮件已发送至您的邮箱：<strong class="biglight">{substr_replace($email, '****', 2, 4)}</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</p></li>
					<li><p class="ui-text">没有收到邮件？</p></li>
					<li class="ui-btn"><a class="btn" id="J-button-resubmit" href="#" style="color:#CCC;">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);"><span id="J-time">60</span>s<b class="btn-inner"></b></a></li>
				</ul>
			</form>
			<!-- end -->
			<!-- star -->
			<dl class="help-text">
				<dt>如果半小时内没有收到邮件</dt>
				<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
				<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" />在线客服</a>，由客服帮你解决</dd>
			</dl>
			<!-- end -->
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	
	
{literal}
<script>
(function($){
		var footer = $('#footer');
		footer.css('position','fixed');
		if($(document).height()>$(window).height()){
			footer.css('position','static');
		}
		
		
		var button = $('#J-button-resubmit'),dom = $('#J-time'),timeCase,sc = 60,fn;
		fn = function(){
			if(sc <= 0){
				dom.html(sc--).parent().hide();
				button.css('color', '#333');
				timeCase.remove(fn);
			}else{
				dom.html(sc--);
			}
		};
		timeCase = phoenix.Timer({time:1000,fn:fn});
		button.click(function(e){
			e.preventDefault();
			if(sc > 0){
				return false;
			}
			$('#J-form-step2').submit();
		});
})(jQuery);
</script>
{/literal}

</body>
</html>
