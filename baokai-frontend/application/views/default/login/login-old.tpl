<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>正常登录</title>
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
	
<body class="bg-content">
	<div class="login-header"></div>
	<div class="login-body">
		<div class="login-content">
			<div class="login-logo"></div>
			<form action="/login/login" method="POST" id="J-form-login">
			<ul class="login-list">
				<li class="login-tip-error">
					<span id="J-tip-error">
						
					</span>
				</li>
				<li class="login-list-input">
					<div>
					<i class="ico-name"></i>
					<input id="J_username" type="text" class="login-input" value="" tabindex="1" maxlength="16" />
					</div>
				</li>
				<li class="login-list-input" style="position:relative;">
					
					<i class="ico-pwd"></i>
					<input id="J_password" type="password" class="login-input" value="" tabindex="2" maxlength="20" />
					<span class="password-deftext" id="J-password-deftext">请输入密码</span>
					<a class="find-pwd" href="/login/findpwd">忘记密码？</a>
				</li>
			
				
				<li id="J-panel-vcode" class="login-list-input verify-code" {if $errortimes > 2}{else} style="display:none;" {/if}>
					<img id="J-vcode-img" src="{$imageurl}" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" alt="点击图片刷新验证码" title="点击图片刷新验证码" />
					<input id="J_vcode" type="text" class="login-input" value="" tabindex="3"  maxlength="4"/>
				</li>


				<li class="login-list-btn"><input id="J_submit" type="button" class="login-btn" value="登 录" tabindex="4" /></li>
			</ul>
			</form>
			<p class="change_mode">验证平台真伪，请使用 <a id="aSafeLogin" href="/login/showsectlogin/">安全登录</a></p>
			
			
			
			{if $adSpaces.isDftUsed eq '1'}
				<div class="ad2">
				{if $adSpaces.dftImg eq ''}
					<img src="{$path_img}/images/ucenter/index/ad2.jpg" />
				{else}
					<img src="{$dynamicroot}/{$adSpaces.dftImg}" />
				{/if}
				</div>
            {else}
	            	<input type="hidden" name="urlTarget" value="{$adSpaces.urlTarget}" />
	            	<input type="hidden" name="name" value="{$adSpaces.name}" />
	                <div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_login">

					</div>
            {/if}
			
			
			
		</div>
	</div>
	<div class="login-footer"></div>
	
	
{literal}
<script>
(function($){
	var isGlobalLoading = false;
	var showError = function(html){
		$('#J-tip-error').html(html).show();
	};
	var hideError = function(){
		$('#J-tip-error').hide();
	};
	var username = $('#J_username'),password = $('#J_password'),vcode = $('#J_vcode'),button = $('#J_submit');
	var IptUseranme = new phoenix.Input({el:username,defText:'请输入用户名',focusClass:'login-input-focus'});
	var IptPassword = new phoenix.Input({el:password,defText:'',focusClass:'login-input-focus'});
	var IptVcode = new phoenix.Input({el:vcode,defText:'请输入验证码',focusClass:'login-input-focus'});
	var mask = phoenix.Mask.getInstance();
		mask.css({'opacity':0});
	var WidthCheck=function(str){  
		var w = 0;  
		var tempCount = 0; 
		for (var i=0; i<str.length; i++) {  
		   var c = str.charCodeAt(i);  
		   //单字节加1  
		   if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
			w++;  
		  
		   }else {     
			w+=2;
		   }  
		 }
		return w;
	}  

	username.bind('input', function(){
		
		if($.trim(this.value) != '') {
			$('#J-password-deftext').hide();
		}
	});
	
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
	username.blur(function(){
		var dom = username,v = $.trim(dom.val()),isPass = false;
		if(v == '' || v == IptUseranme.defConfig.defText){
			isPass = false;
			showError('用户名不能为空');
		}else if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
			isPass = false;
			showError('用户名长度有误，请输入4-16位字符');
		}else if((/^\d+$/g).test(v)){
			isPass = false;
			showError('用户名不能是纯数字');
		}
		else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(v)){
			isPass = false;
			showError('用户名只能由中文、字母和数字组成');
		}
		else{
			isPass = true;
		}
		if(isPass){
			hideError();
		}else{
			dom.focus();
		}
		return isPass;
		
	});
	var checkUsername = function(){
		var dom = username,v = $.trim(dom.val()),isPass = false;
		if(v == '' || v == IptUseranme.defConfig.defText){
			isPass = false;
			showError('用户名不能为空');
		}else if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
			isPass = false;
			showError('用户名长度有误，请输入4-16位字符');
		}else if((/^\d+$/g).test(v)){
			isPass = false;
			showError('用户名不能是纯数字');
		}
		else if((/[^A-Za-z0-9\u4E00-\u9FA5]/g).test(v)){
			isPass = false;
			showError('用户名只能由中文、字母和数字组成');
		}
		else{
			isPass = true;
		}
		if(isPass){
			hideError();
		}else{
			dom.focus();
		}
		return isPass;
	};
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
	var checkVcode = function(){
		var dom = vcode,v = $.trim(dom.val()),isPass = false;
		if(!isOpenVcode()){
			return true;
		}
		if(v == '' || v == IptVcode.defConfig.defText){
			isPass = false;
			showError('验证码不能为空');
		}else if(!(/[A-Za-z0-9]{4}/).test(v)){
			isPass = false;
			showError('验证码格式不正确');
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
	var isOpenVcode = function(){
		return $('#J-panel-vcode').css('display') != 'none' ? true : false;
	};
	var refreshCode = function(){
		var img = $('#J-vcode-img'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};
	var verifyCode = function(){
		var v = $.trim(vcode.val()),timer;
		$.ajax({
			url:'/login/checkvcode',
			method:'POST',
			dataType:'json',
			data:'vcode=' + v,
			beforeSend:function(){
				timer = setTimeout(formLoading, 500);
			},
			success:function(data){
				if(data['status'] == '1'){
					submitForm();
				}else{
					showError(data['data']);
					refreshCode();
					vcode.focus();
				}
			},
			complete:function(){
				clearTimeout(timer);
				formLoaded();
			}
		});
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
		isGlobalLoading = false;
		button.val(text);
		mask.hide();
	};

	button.click(function(){
		if(checkUsername() && checkPassword() && checkVcode()){
			if(isOpenVcode()){
				verifyCode();
			}else{
				submitForm();
			}
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
		var vUsername = $.trim(username.val()),vPassword = $.trim(password.val()),vVcode = $.trim(vcode.val()).toUpperCase(),
			timer,params;
			if(isOpenVcode()){
				if(!checkVcode()){
					return;
				}
				params = {'username':vUsername,'password':vPassword,'vcode':vVcode};
			}else{
				params = {'username':vUsername,'password':vPassword};
			}
		$.ajax({
			url:'/login/login',
			dataType:'json',
			method:'post',
			cahce:false,
			data:params,
			beforeSend:function(){
				hideError();
				timer = setTimeout(formLoading, 500);
				isGlobalLoading = true;
			},
			success:function(data){
				var url = '/index';
				if(data['errors'].length > 0){
					showError(data['errors'][0][1]);
					if(isOpenVcode() || (data['data'] && data['data'] > 2)){
						$('#J-panel-vcode').show();
						vcode.val('');
						refreshCode();
					}
					if(data['errors'][0][0] == 'password'){
						password.focus();
					}
					if(data['errors'][0][0] == 'username'){
						username.focus();
					}
				}else{
					url = getRedirectParams() == '' ? url : getRedirectParams();
					location.href = url;
				}
			},
			complete:function(){
				clearTimeout(timer);
				isGlobalLoading = false;
				formLoaded();
				//var url = '/index';
				//location.href = url;
			}
		});
	};
	
	$(document).keydown(function(e){
		if(e.keyCode == 13 && !isGlobalLoading && checkUsername() && checkPassword() && checkVcode()){
			button.focus();
			submitForm();
		}
	});
	
/*	setTimeout(function(){
		username.focus();
	}, 500);*/
	
	
	//  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });

	
	//页面广告
	new phoenix.GlobalAd({
		width:320,
		height:120,
		url:'/api/jsonp/getAdverts?u=-1&k=index_pos_login&r='+Math.random(),
		callback:function(){
			var slider = new phoenix.Slider({
				par:$('#J-globalad-index_pos_login'),
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