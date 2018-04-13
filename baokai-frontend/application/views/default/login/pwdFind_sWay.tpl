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
			<strong class="highbig">您正在找回登录密码的账号是：<span class="highlight">VAVA1234</span>，请选择你准备找回密码的方式：</strong>
			<ul class="find-select-list">
				<li>
					<i class="ico-mail"></i>
					<p>通过绑定的邮箱找回登录密码<br />您绑定的 邮箱为VAVA**AA@2222.COM</p>
					<a href="javascript:void(0);" class="btn">立即找回<b class="btn-inner"></b></a>
				</li>
				<li>
					<i class="ico-safequestion"></i>
					<p>通过回答“安全问题”找回登录密码</p>
					<a href="javascript:void(0);" class="btn">立即找回<b class="btn-inner"></b></a>
				</li>
				<li>
					<i class="ico-safecode"></i>
					<p>通过安全密码找回登录密码</p>
					<a href="javascript:void(0);" class="btn">立即找回<b class="btn-inner"></b></a>
				</li>
				<li class="disable">
					<i class="ico-mail"></i>
					<p>通过绑定的邮箱找回登录密码<br />您绑定的 邮箱为VAVA**AA@2222.COM</p>
					<span>(未绑定，不可用)</span>
				</li>
				<li class="disable">
					<i class="ico-safequestion"></i>
					<p>通过回答“安全问题”找回登录密码</p>
					<span>(未绑定，不可用)</span>
				</li>
				<li class="disable">
					<i class="ico-safecode"></i>
					<p>通过安全密码找回登录密码</p>
					<span>(未绑定，不可用)</span>
				</li>
			</ul>
			<p>上面的方式都不可用？您还可以通过<a title="客服" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" />在线客服</a>进行人工申诉找回登录密码。</p>
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
