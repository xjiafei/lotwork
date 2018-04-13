<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>正常登录</title>
	<link rel="stylesheet" href="{$path_img}/images/login/login-optimize.min.css" />
</head>
<body class="bg-content new-login">
	<div class="wrap-login">
		<div class="logo">
			<a herf="javascript:void(0);"></a>
		</div>
		<div class="nav-wrap clearfix">
			<div class="guest-point guest-area">
				<span class="help-icon">有问题咨询在线客服？</span><a id="custom-service" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">点击此处</a>
			</div>
			<div class="nav">新平台登陆</div>
		</div>
		<div class="info-panel clearfix">
			<div class="login-info-area">
				<form action="#" method="post">
					<div class="user-info clearfix">
						<input type="text" id="J-user-name" data-name="name" name="user-name" class="user-name" placeholder="用户名">
						<input type="password" id="J-user-password" data-name="password" name="user-password" placeholder="密码" class="user-password">
						<a href="/login/findpwd" class="forget-password" tabindex="-1">忘记密码？</a>
						<a href="javascript:;" title="清除用户名" data-name="name" class="clear-name" tabindex="-1">清除用户名</a>
						<a href="javascript:;" title="清除密码" data-name="password" class="clear-password" tabindex="-1">清除密码</a>
						<input type="hidden" id="J-loginParam" data-name="loginParam" name="loginParam" value="{$loginRand}" />
					</div>
						<div id="J-panel-vcode" class="user-info ver-area clearfix"  {if $errortimes > 2}{else} style="display:none;" {/if}>
						<img class="var-img-area" id="J-vcode-img" src="{$imageurl}" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" alt="点击图片刷新验证码" title="点击图片刷新验证码" />
						<input type="text" id="J-verification" data-name="verification" name="user-name" class="user-ver" placeholder="验证码" >
					</div>
					<div id="J-msg-show" data-display="hide" class="msg-show"></div>
						<input id="J-form-submit" class="submit-btn" type="submit" value="登 录">
					<div class="login-tips">验证平台真伪，请使用<a id="aSafeLogin"  class="link" href="/login/showsectlogin/">安全登录</a></div>
				</form>
			</div>
			<div class="banner-area">
				<div id="focus" class="cycle-slideshow"
				data-cycle-slides="> .item"
				data-cycle-pager="> .cycle-pager-wrap .cycle-pager"
				data-cycle-fx="scrollHorz"
				data-cycle-timeout="4000"
				data-cycle-loader="wait"
				data-cycle-speed="800"
				data-pause-on-hover="true"
				>
					<div class="cycle-pager-wrap">
						<div class="cycle-pager"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="suggest-browser">
			<a href="http://www.google.cn/intl/zh-CN/chrome/browser/desktop/index.html" class="chrome" target="_blank"></a>
			<a href="https://www.mozilla.org/zh-CN/firefox/new" class="firefox" target="_blank"></a>
			<a href="http://windows.microsoft.com/zh-cn/internet-explorer/download-ie" class="ie" target="_blank"></a>
		</div>	
		<p class="browser-desc">为了更好的操作体验首选Google Chrome、Firefox 或 IE9 浏览器，点击可立即下载。</p>   
	</div>

<script type="text/javascript">
global_path_url="{$path_img}";
if(typeof customNum == "undefined") {
	var dt = new Date();
	customNum = "GUEST@" + dt.getHours() + dt.getMinutes() + dt.getSeconds() + "|||||||||||";
}
hjUserData=customNum;
</script>
<script src="{$path_js}/js/login/login-all.min.js"></script>
<script src="{$path_js}/js/login/login-logic.min.js"></script>
<script type="text/javascript">
(function() {       
	function async_load(){           
		var s = document.createElement('script');          
		s.type = 'text/javascript';          
		s.async = true;           
		s.src = "http://www.26hn.com/web/code/code.jsp?c=1&s=17";           
		var x = document.getElementsByTagName('script')[0];          
		x.parentNode.insertBefore(s, x);      
	}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  

	 $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
	 });
})();
</script>

</body>
</html>