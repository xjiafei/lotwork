<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=no"/>
    <title>普通登录</title>
	<style type="text/css">
		*{
			margin:0; 
			padding:0;
		}
		.box{
			position: absolute;
			left:0;
			right: 0;
			top:0;
			bottom: 0;
			overflow: hidden;
		}
	</style>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/common.css"/>
    <link type="text/css" rel="stylesheet" href="{$path_img}/images/wap/login/loginNormal.css"/>
<body>
<!--wapLogin begin-->
<div class="container">
    <div class="page login_page">

        <div class="title">登录</div>         <!-- title -->

        <h1 class="logo">                     <!-- logo -->
            <!--<p class="v"></p>-->
        </h1>

        <p class="input_box">                  <!-- btn-->
        <!--    <span class="btn_loginType btn_loginType_active">普通登录</span>-->
       <!--     <a href="/login/loginSecurity/" class="btn_loginType">安全登录</a>-->
        </p>
        <div id="login_normal_form" class="login_form login_unique">    <!-- form -->
			<form autocomplete="off">
				<ol class="inputOL">
					<li>
						<span>账户</span>
						<input type="text" name="username" style="display:none;">
						<input type="text" name="username" placeholder="请输入您的用户名" id="username" maxLength="16"/>
						<b></b>
					</li>
					<li>
						<span>密码<span>
						<input type="password" name="password" placeholder="请输入您的密码" id="password"/>
					</li>
					<li {if $errortimes > 2}style="display:block;"{else} style="display:none;" {/if}>
						<input type="text" name="vcode" placeholder="请输入验证码" maxLength="4" class="vcode" id="vcode"/>
						<i clickID="Login_refresh"></i>
						<img src="/login/changevcode" width="160" height="60" alt="验证码图片"/>
					</li>
				</ol>
			</form>
        </div>

        <!--忘记密码和输入错误提示-->
        <div class="login_tips">
            <span></span><a id = 'forgetPwd' href="/login/forgetpwd">忘记密码?</a>
        </div>

        <!--登录按钮-->
        <button class="btn_login" id="login">登录</button>

        <!--下载链接-->
        <div class="link_downLoadApp"><a id = 'downloadApp' href="{$path_img}/dbyl/" title="下载app">下载APP</a></div>

        <!--加密时间校验-->
        <input type="hidden" id="param" name="loginParam" value="{$loginRand}" />
    </div>
</div>

<div id="main" class="box" style="display:none;">
	<img id="img" src="{$path_img}/images/wap/login/android.png" height="100%" width="100%" />
</div>

<script type="text/javascript">
	window.onload = function(){
		var u = window.navigator.userAgent;
		var isAndroid = u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端 
		var isiOS = !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); 		   //ios终端 
		if(u.match(/MicroMessenger/i) == 'MicroMessenger'){			//是微信环境
			$("#main").css('display','');
			if(isiOS){
				var img = document.getElementById('img');
				img.src = '{$path_img}/images/wap/login/ios.png';
			}	
		}else{							//不是微信环境
			$("#main").css('display','none');
		}										
	}
</script>
<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.min.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/zepto.md5.js"></script>
<script type="text/javascript" src="{$path_js}/js/wap/login/loginNormal.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
<!--wapLogin  end-->
</body>
</html>
