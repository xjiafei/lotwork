<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>账户安全</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
</head>
<body>
	
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
		</div>
	</div>
	<!-- header end -->
	
	<!-- step-num star -->
	<div class="step-num">
		<ul>
			<li><i class="step-num-1">1</i>输入用户名</li>
			<li class="current"><i class="step-num-2">2</i>选择找回密码方式</li>
			<li><i class="step-num-3">3</i>重置密码</li>
			<li><i class="step-num-4">4</i>完成</li>
		</ul>
	</div>
	<!-- step-num end -->
	
	<div class="g_33">
		<div class="find-select-content">
			<ul class="ui-form">
				<li>登录密码找回邮件已发送至您的邮箱：<strong class="light">vava****@qq.com</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</li>
				<li><a href="javascript:void(0);" class="btn">上一步<b class="btn-inner"></b></a></li>
				<li>没有收到邮件？</li>
				<li><a class="btn" href="javascript:void(0);">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);">60S<b class="btn-inner"></b></a></li>
			</ul>
			<dl class="help-text">
				<dt>如果半小时内没有收到邮件</dt>
				<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
				<dd>联系<a title="客服" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" />在线客服</a>，由客服帮你解决</dd>
			</dl>
		</div>
	</div>
	
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script>
(function($){
	var footer = $('#footer');
	footer.css('position','fixed');
	if($(document).height()>$(window).height()){
		footer.css('position','static');
	}
})(jQuery);
</script>

</body>
</html>
