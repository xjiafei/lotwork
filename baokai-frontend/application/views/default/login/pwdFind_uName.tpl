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
			<li class="current"><i class="step-num-1">1</i>输入用户名</li>
			<li><i class="step-num-2">2</i>选择找回密码方式</li>
			<li><i class="step-num-3">3</i>重置密码</li>
			<li><i class="step-num-4">4</i>完成</li>
		</ul>
	</div>
	<!-- step-num end -->
	
	<div class="g_33">
		<div class="find-login-content">
			<ul class="ui-form">
				<li>
					<label for="name" class="ui-label">用户名：</label>
					<input type="text" value="请输入你的用户名" id="name" class="input">
					<div class="ui-check"><i class="error"></i>请输入你的用户名</div>
				</li>
				<li>
					<label for="pwd" class="ui-label">验证码：</label>
					<input type="text" value="请输入右侧验证码" id="pwd" class="input w-3">
					<img alt="验证码" title="看不清，请点击更换图片" src="{$path_img}/images/common/img-verify-code.png" class="verify-code">
					<div class="ui-check"><i class="error"></i>请输入验证码</div>
				</li>
				<li class="ui-btn"><a href="javascript:void(0);" class="btn">下一步<b class="btn-inner"></b></a></li>
			</ul>
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