<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>安全登录</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/login/login.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{literal}
	<style>
	.password-deftext {color:#BDBDBD;font-size:14px;position:absolute;left:52px;top:15px;}
	</style>
	{/literal}
	<script type="text/javascript">global_path_url="{$path_img}";</script>
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
	<script type="text/javascript" src="{$path_js}/js/phoenix.util.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Timer.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Slider.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.GlobalAd.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.ga.js"></script>
<body class="bg-content safe-login">
	<div class="login-header"></div>
	<div class="login-body">
		<div class="login-content">
			<div class="login-mark"></div>
			<div class="login-logo"></div>
			{if not $selfMessage}
			<p class="msg-setup">您尚未设置预留验证信息，请登录成功后前往安全中心设置!</p>
			{else}
			<p class="msg-info">您设置的预留信息为：<strong>{$selfMessage}</strong>若“预留验证信息”与您设置不一致！请勿输入密码！  </p>
			{/if}
			<!-- 用户名、密码、验证码请自行选择是否使用 -->
			<ul class="login-list">
					<li class="login-tip-error">
						<span id="J-tip-error">
							
						</span>
					</li>
					<li class="login-list-input" style="position:relative;">
						<i class="ico-pwd"></i>
						<a class="find-pwd" href="/login/findpwd">忘记密码？</a>
						<input id="J_password" type="password" class="login-input" value="" tabindex="1" maxlength="20"/>
						<span class="password-deftext" id="J-password-deftext">请输入密码</span>
					</li>
					<li class="login-list-btn"><input id="J_submit" type="button" class="login-btn" value="登 录" tabindex="2"/></li>
			</ul>
			<p class="change_mode">返回使用 <a href="/index/index">普通登录</a></p>
			
			<div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_loginTop">

			</div>
			
		</div>
	</div>
	<div class="login-footer"></div>
{literal}
<script>
(function($){
	var isGlobalLoading = false;
	var Wd = phoenix.Message.getInstance();
	var showError = function(html){
		$('#J-tip-error').html(html).show();
	};
	var hideError = function(){
		$('#J-tip-error').hide();
	};
	var password = $('#J_password'),button = $('#J_submit');
	var IptPassword = new phoenix.Input({el:password,defText:'',focusClass:'login-input-focus'});
	var mask = phoenix.Mask.getInstance();
		mask.css({'opacity':0});
		
		
	$('#J-password-deftext').mousedown(function(){
		$(this).hide();
		password.focus();
	});
	$('#J-password-deftext').click(function(){
		$(this).hide();
		password.focus();
	});
	password.focus(function(){
		$('#J-password-deftext').hide();
	});
	password.blur(function(){
		var v = $.trim(this.value);
		if(v == '请输入密码' || v == ''){
			$('#J-password-deftext').show();
		}
	});
	
	
	var checkPassword = function(){
		var dom = password,v = $.trim(dom.val()),isPass = false;
		if(v == '' || v == IptPassword.defConfig.defText){
			isPass = false;
			showError('密码不能为空');
		}else{
			isPass = true;
		}
		if(isPass){
			hideError();
		}else{
			dom.focus();
		}
		return isPass;
	};
	
	var formLoading = function(){
		var textArr = ['加载中   ', '加载中.  ', '加载中.. ', '加载中...'],i = 0;
		isGlobalLoading = true;
		mask.show(button);
		clearInterval(formLoading.timer);
		formLoading.timer = setInterval(function(){
			button.val(textArr[i]);
			i = i >= textArr.length - 1 ? 0 : i + 1;
		}, 200);
	};
	var formLoaded = function(){
		var text = '登 录';
		clearInterval(formLoading.timer);
		button.val(text);
		mask.hide();
	};

	button.click(function(){
		if(checkPassword()){
			submitForm();
		}
	});
	
	var getRedirectParams = function(){
	    var url = location.search,pattern = "redirect=",str = "";
	    if (url.indexOf(pattern) != -1) {
			var str = url.substr(url.indexOf(pattern)+pattern.length);
		}
		return str;
	};
	
	var submitForm = function(){
		var vPassword = $.trim(password.val()),
			timer,params;
			params = {'password':vPassword};
		$.ajax({
			url:'/login/sectlogin',
			dataType:'json',
			method:'post',
			cahce:false,
			data:params,
			beforeSend:function(){
				hideError();
				timer = setTimeout(formLoading, 500);
				isGlobalLoading = true;
				button.blur();
			},
			success:function(data){
				var url = '/index';
				if(data['errors'].length > 0){
					isGlobalLoading = true;
					Wd.hideClose();
					Wd.show({
						mask:true,
						title:'提示',
						content:'<div class="bd text-center"><div class="pop-title"><i class="ico-waring"></i><h4 class="pop-text">密码输入错误，请重试</h4></div></div>',
						confirmIsShow:true,
						confirmFun:function(){
							location.href = '/login/showsectlogin';
						}
					});
					
				}else{
					url = getRedirectParams() == '' ? url : getRedirectParams();
					location.href = url;
				}
			},
			complete:function(){
				clearTimeout(timer);
				formLoaded();
			}
		});
	};
	
	$(document).keydown(function(e){
		if(e.keyCode == 13 && !isGlobalLoading){
			button.focus();
			submitForm();
		}
	});
	
	setTimeout(function(){
		password.focus();
	}, 500);
	
	
	//  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });


    //页面广告
	new phoenix.GlobalAd({
		width:320,
		height:120,
		url:'/api/jsonp/getAdverts?k=index_pos_loginTop&r='+Math.random(),
		callback:function(){
			var slider = new phoenix.Slider({
				par:$('#J-globalad-ad_login'),
				panels:'.slider-pic li',
				triggers:'.slider-num li',
				sliderDirection:'left',
				sliderIsCarousel:true
			});
		}
	});
	

})(jQuery);
</script>

{/literal}
	
	
	
</body>
</html>