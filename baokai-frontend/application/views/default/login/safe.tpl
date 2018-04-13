<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>安全登录</title>
    <link rel="stylesheet" href="{$path_img}/images/login/login.min.css" />
</head>
<body class="bg-content new-login">
    <div class="wrap-login">
         <div class="nav-wrap clearfix">
            <div class="logo"></div>
            <div class="guest-point guest-area">
                <span class="help-icon">有问题咨询在线客服？</span><a id="custom-service" href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">点击此处</a>
            </div>
        </div>
         <div class="info-panel clearfix">
            <div id="J-login-area" class="login-info-area">
                <form action="#" method="post">
                    <div id="J-user-info" class="user-info clearfix">
                        <div id="J-username-area">
                            <input type="text" id="J-user-name" data-name="name" name="user-name" class="user-name" placeholder="用户名">
                            <a href="javascript:;" title="清除用户名" data-name="name" class="clear-name" tabindex="-1">清除用户名</a>
                        </div>
                        <div id="J-password-area" class="hide">
                            <input type="password" id="J-user-password" data-name="password" name="user-password" placeholder="密码" class="user-password">
                            <input type="hidden" id="J-loginParam" data-name="loginParam" name="loginParam" value="{$loginRand}" >
                            <a href="/login/findpwd" class="forget-password" id="forgetPW">忘记密码？</a>
                            <a href="javascript:;" title="清除密码" data-name="password" class="clear-password" tabindex="-1">清除密码</a>
                        </div>
                    </div>
                    <div id="J-user-var" class="user-info ver-area clearfix">
                        <!--<div class="var-img-area"></div>-->
                        <img class="var-img-area" id="J-vcode-img" src="{$imageurl}" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" alt="点击图片刷新验证码" title="点击图片刷新验证码" />
                        <input type="text" id="J-verification" data-name="verification" name="user-name" class="user-ver" placeholder="验证码" >
                    </div>
                    <div id="J-msg-show" data-display="hide" class="msg-show"></div>
                    <div class="choose-game" >
                        <label for="choose-lottery" class="label"><input type="radio" id="choose-lottery" class="radio" name="choose-game" value="1" checked="checked"/>彩票</label>
                        <label for="choose-pt" class="label"><input type="radio" id="choose-pt" class="radio" name="choose-game" value="2"/>老虎机</label>
                    </div>
                    <input id="J-form-submit" class="submit-btn" type="submit" value="下一步">
                    <div class="login-tips">返回使用<a href="/login/index" class="link" id="normalLogin">普通登录</a></div>
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
</body>
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
<script src="{$path_js}/js/login/login-safe.js"></script>
<script src="{$path_js}/js/phoenix.ga.js" defer></script>
{literal}
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

	 $("#custom-service").click(function(){
		   if(typeof hj5107 != "undefined")
		   {
			 hj5107.openChat();
		   }
	 });
})();
</script>
{/literal}
</html>