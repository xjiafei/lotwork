<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>正常登录</title>
	<link rel="stylesheet" href="{$path_img}/images/login/login.css" />
<style>
.quick-login {
display: none;}


.choose-game {
display: none; }


.login-tips {

display: none;
}

.bg-content {
background: url({$path_img}/images/login/bg-header.png) no-repeat;
}

.submit-btn {
background-color: #168DD3;
transition: all .3s;
}


.submit-btn:hover {
opacity: 0.8;
background-color:#168DD3}
.info-panel .login-info-area {

padding-top: 35px;}


.guest-area a:hover {
color: orange;
}

input:-webkit-autofill {
 -webkit-box-shadow: 0 0 0px 1000px white inset;

}


</style>
</head>
<body class="bg-content new-login">
	<div class="wrap-login">
		<div class="nav-wrap clearfix">
			<div class="logo"></div>
			<div class="guest-point guest-area">
				<span class="help-icon">有问题咨询在线客服？</span> 
				<a id="custom-service-allen" href="http://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145" target="_blank">点击此处</a>
			</div>
			<div class="service-box">
                            <i class="tri"></i>在线客服打不开？
                            <a class="support_service_link">请点击</a>
                        </div>
		</div>
		<div class="info-panel clearfix">
			<div class="login-info-area">
			<a href="http://www.ph158.info:8080/quicklogin.html" class="quick-login" target="_blank">如何快速登录</a>
				<form action="#" method="post">
					<div class="user-info clearfix">
						<input type="text" id="J-user-name" data-name="name" name="user-name" class="user-name" placeholder="用户名">
						<input type="password" id="J-user-password" data-name="password" name="user-password" placeholder="密码" class="user-password">
						<a href="/login/findpwd" class="forget-password" tabindex="-1" id="forgetPW">忘记密码？</a>
						<a href="javascript:;" title="清除用户名" data-name="name" class="clear-name" tabindex="-1">清除用户名</a>
						<a href="javascript:;" title="清除密码" data-name="password" class="clear-password" tabindex="-1">清除密码</a>
						<input type="hidden" id="J-loginParam" data-name="loginParam" name="loginParam" value="{$loginRand}" />
					</div>
						<div id="J-panel-vcode" class="user-info ver-area clearfix"  {if $errortimes > 2}{else} style="display:none;" {/if}>
						<img class="var-img-area" id="J-vcode-img" src="{$imageurl}" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" alt="点击图片刷新验证码" title="点击图片刷新验证码" />
						<input type="text" id="J-verification" data-name="verification" name="user-name" class="user-ver" placeholder="验证码" >
					</div>
					<div id="J-msg-show" data-display="hide" class="msg-show"></div>
					<div class="choose-game" >
		 				<label for="choose-lottery" class="label"><input type="radio" id="choose-lottery" class="radio" name="choose-game" value="1" checked="checked"/><span class="lottery-span"></span></label>
		 				<label for="choose-pt" class="label"><input type="radio" id="choose-pt" class="radio" name="choose-game" value="2"/><span class="game-span"></span></label>
		 			</div>
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
var ptgame_server = "{$ptgame_server}";
if(typeof customNum == "undefined") {
	var dt = new Date();
	customNum = "GUEST@" + dt.getHours() + dt.getMinutes() + dt.getSeconds() + "|||||||||||";
}
hjUserData=customNum;
</script>

<script src="{$path_js}/js/base-all.js"></script>
<script src="{$path_js}/js/login/login-all.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
<script src="{$path_js}/js/login/login-logic.js"></script>
<script src="/support/support_service?p=FF4"></script>
<script src="{$path_js}/js/phoenix.ga.js" defer></script>
{literal}
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

	 $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
		   $('.service-box').fadeIn().delay(3000).fadeOut();
	 });

$('#focus').html('<div class="item"><a target="_blank"><img src="'+global_path_url+'/images/login/new-login/login_banner.jpg" alt=""></a></div>');

})();
</script>
{/literal}
</body>
</html>
