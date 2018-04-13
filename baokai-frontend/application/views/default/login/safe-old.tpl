<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>安全登录</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/login/login.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />

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

<body class="bg-content safe-login">
	<div class="login-header"></div>
	<div class="login-body">
		<div class="login-content">
			<div class="login-mark"></div>
			<div class="login-logo"></div>

			<ul class="login-list">
					<li class="login-tip-error">
						<span id="J-tip-error">
							
						</span>
					</li>
					<li class="login-list-input">
						<i class="ico-name"></i>
						<input id="J_username" type="text" class="login-input" value="" tabindex="1"  maxlength="16"/>
					</li>
					<li id="J-panel-vcode" class="login-list-input verify-code">
						<img id="J-vcode-img" src="{$imageurl}" alt="验证码" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" />
						<input id="J_vcode" type="text" class="login-input" value="" tabindex="2"  maxlength="4"/>
					</li>
					<li class="login-list-btn">
					<input id="J_submit" type="button" class="login-btn" value="下一步" tabindex="3" />
					</li>
				</ul>

			<p class="change_mode">返回使用 <a href="/index/index">普通登录</a></p>
			<div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_loginTop">

			</div>
		</div>
	</div>
	<div class="login-footer"></div>
	<div class="j-ui-tip j-ui-tip-l" id="userNameExist" style="position: absolute; left: 865.5px; top: 275px; z-index: 500; display:none">
        <i class="sj sj-t"></i>
        <i class="sj sj-r"></i>
        <i class="sj sj-b"></i>
        <i class="sj sj-l"></i>
        <span class="ui-tip-text">
        <i class="error"></i>
        <span id="spnerror">用户名不存在，请重试！</span>
         
        </span>
    </div>
	
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
	var IptVcode = new phoenix.Input({el:vcode,defText:'请输入验证码',focusClass:'login-input-focus'});
	var mask = phoenix.Mask.getInstance();
		mask.css({'opacity':0});
	
	var checkUsername = function(){
		var dom = username,v = $.trim(dom.val()),isPass = false;
		if(v == '' || v == IptUseranme.defConfig.defText){
			isPass = false;
			showError('用户名不能为空');
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
		var text = '下一步';
		isGlobalLoading = false;
		clearInterval(formLoading.timer);
		button.val(text);
		mask.hide();
	};

	button.click(function(){
		if(checkUsername() && checkVcode()){
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
				params = {'username':vUsername,'vcode':vVcode,'safemod':1};
			}else{
				params = {'username':vUsername,'safemod':1};
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
				var url = '/login/sectlogin';
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
					//console.log(data);
					location.href = url;
				}
			},
			complete:function(){
				clearTimeout(timer);
				isGlobalLoading = false;
				formLoaded();
			}
		});
	};
	
	$(document).keydown(function(e){
		if(e.keyCode == 13 && !isGlobalLoading && checkUsername() && checkVcode()){
			button.focus();
			submitForm();
		}
	});
	
	setTimeout(function(){
		username.focus();
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