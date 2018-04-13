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
			<div class="service">
				<a title="客服" class="link-service" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" />在线客服</a>
				<a title="帮助" target="_blank" class="link-help" href="/help/goIndex">帮助</a>
			</div>
		</div>
	</div>
	<!-- header end -->
	<!-- step-num star -->
	<div class="step-num">
		<ul>
			<li><i class="step-num-1">1</i>输入用户名</li>
			<li><i class="step-num-2">2</i>选择找回密码方式</li>
			<li class="current"><i class="step-num-3">3</i>重置密码</li>
			<li><i class="step-num-4">4</i>完成</li>
		</ul>
	</div>
	<!-- step-num end -->
	<div class="g_33">
		
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

</script>
</body>
</html>
