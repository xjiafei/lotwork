<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, minimum-scale=1, minimal-ui">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<meta content="telephone=no" name="format-detection" />
	<title>注册</title>
    <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
    <link rel="stylesheet" href="{$path_img}/images/register_mobile/mobile.css" />
    
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
</head>
<body>
	<h3>新用户注册</h3>
	<form id="J-form" method="post">
		<ul class="register">
			<li>
				<label for="name" class="ui-label">用户名：</label>
				<input type="text" value="支持字母，数字" name="username" id="J-input-username" class="input">
			</li>
			<li>
				<label for="pwd" class="ui-label">密码：</label>
				<input type="text" value="最少6位" name="password1" id="J-input-password" class="input" onpaste="return false">
			</li>
			<li>
				<label for="check-pwd" class="ui-label">确认密码：</label>
				<input type="text" value="重新输入" name="password2" id="J-input-password2" class="input" onpaste="return false">
			</li>
			<li style="display:none" id="J-panel-vcode">
				<label for="check-pwd" class="ui-label">验证码：</label>
				<input type="text" maxlength="4" tabindex="3" name="vcode" value="" class="input" onpaste="return false" id="J-input-vcode">
				<img data-src-path="/register/changevcode" onclick="this.src='/register/changevcode?r='+Math.random();" alt="验证码" src="/register/changevcode"  id="img-code">
			</li>
			<li class="submit">
				<input type="button" id="J-button-submit" value="提交注册"  class="btn">
				
			</li>
		</ul>
	</form>
	
    <script type="text/javascript" src="{$path_js}/js/phoenix.Common.js"></script>
    <script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	{literal}
	<script type="text/javascript">
		
	(function($){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	var uniq_id = '{$uniq_id}';
	var jumpApp = '{$jumpApp}';
	{literal}
	var username = $('#J-input-username'),password = $('#J-input-password'),password2 = $('#J-input-password2'),vcode = $('#J-input-vcode');
	var disabledCn = function(){
		this.value = this.value.replace(/[\u4E00-\u9FA5]/g, '');
	};
	var isOpenVcode = function(){
		return $('#J-panel-vcode').css('display') != 'none' ? true : false;
	};
	var changeType = function(e){
		if(e.type == 'focus'){
			this.type = 'password';
		}else{
			this.type = 'text';
		}
	};
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
		//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	}
	
	var usernameObj = new phoenix.Input({el:username,defText:'支持字母，数字'});
	var passwordObj = new phoenix.Input({el:password,defText:'最少6位'});
	var password2Obj = new phoenix.Input({el:password2,defText:'重新输入'});
	var vcodeObj = new phoenix.Input({el:vcode,defText:'输入验证码'});
	
	//username.keyup(disabledCn);
	password.keyup(disabledCn);
	password2.keyup(disabledCn);
	vcode.keyup(disabledCn);
	
	
	var testpass=function(password){
		var score = 0;
		if (password.match(/^\d+$/)){ score =1;}
		if (password.match(/^[a-zA-Z]+$/)){ score =1;}
		if (password.match(/^[!,@,#,$,%,^,&,*,?,_,~]+$/)){ score =1;}
		if ((password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([0-9])/)) && password.match(/[a-zA-Z]/)==null
		||(password.match(/([!,@,#,$,%,^,&,*,?,_,~])/) && password.match(/([a-zA-Z])/) && password.match(/([0-9])/)==null) 
		|| (password.match(/([0-9])/) && password.match(/([a-zA-Z])/)) && password.match(/([!,@,#,$,%,^,&,*,?,_,~])/)==null)
		{ 
			score =2
		}
		if (password.match(/([!,@,#,$,%,^,&,*,?,_,~])/)&& password.match(/([0-9])/) && password.match(/([a-zA-Z])/) ){score =3;}
		return score;
	}
	
	
	//密码强度验证
	function checkpass(pass){
		var score = testpass(pass.val());
		var password_label = $("#password_label");
		password_label.removeAttr("class");
		if(score == 1){
			password_label.attr("class","pwd-weak");
		}else if(score == 2){
			password_label.attr("class","pwd-middle");
		}else if(score==3){
			password_label.attr("class","pwd-strong");
		}
		$("#J-div-pwdstrength").css("display","inline");
	}
	
	password.focus(function(){
		$(this).attr("type",'password');
		//this.type = 'password';
		password.parent().find('.check-right').css('display', 'none');
		$("#J-div-pwdstrength").css("display","none");
	}).blur(function(){
		if(this.value == '' || this.value == passwordObj.defConfig.defText){
			$(this).attr("type",'text');
			//this.type = 'text';
		}
	});
	password2.focus(function(){
		//this.type = 'password';
		$(this).attr("type",'password');
		password2.parent().find('.check-right').css('display', 'none');
	}).blur(function(){
		if(this.value == '' || this.value == password2Obj.defConfig.defText){
			$(this).attr("type",'text');
			//this.type = 'text';
		}
	});
	
	var checkUsername = function(isForm){
		var v = username.val(),isPass = false;
		if(isForm && (v == '' || v == usernameObj.defConfig.defText)){
			isPass = false;
			//username.parent().find('.msg-error').html('<i class="error"></i>用户名不能为空').show();
			alert("用户名不能为空");
			return isPass;
		}
		if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
			isPass = false;
			//username.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入4-16位字符').show();
			alert("长度有误，请输入4-16位字符");
		}else if(!(/^(?![0-9])/g).test(v)){
			isPass = false;
			//username.parent().find('.msg-error').html('<i class="error"></i>用户名不能数字开头').show();
			alert("用户名不能数字开头");
		}else if((/^\d+$/g).test(v)){
			isPass = false;
			//username.parent().find('.msg-error').html('<i class="error"></i>用户名不能是纯数字').show();
			alert("用户名不能是纯数字");
		}
		else if((/[^A-Za-z0-9]/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名只能由字母和数字组成').show();
			alert("用户名只能由字母和数字组成");
		}
		else{
			isPass = true;
			username.parent().find('.msg-error').hide();
		}
		if(!isPass){
			username.parent().find('.check-right').css('display', 'none');
		}
		return isPass;
	};
	username.focus(function(){
		username.parent().find('.check-right').css('display', 'none');
	});
	
	
	
	var checkPassword = function(isForm){
		var v = password.val(),isPass = false;
		if(isForm && (v == '' || v == passwordObj.defConfig.defText)){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			//password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			alert("密码不能为空");
			return isPass;
		}
		if(v.length < 6 || v.length > 20){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			//password.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入6-20位字符').show();
			alert("长度有误，请输入6-20位字符");
		}else if(v != '' && username.val() == v){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能和用户名一致').show();
			alert("密码不能和用户名一致");
		}else{
			isPass = true;
			password.parent().find('.msg-error').hide();
			password.parent().find('.check-right').css('display', 'inline-block');
			checkpass(password);
		}
		if(!isPass){
			password.parent().find('.check-right').css('display', 'none');
		}else{
			password.parent().find('.check-right').css('display', 'inline-block');
		}
		return isPass;
	};
	
	
	var checkPassword2 = function(isForm){
		var v = password2.val(),isPass = false;
		if(isForm && (v == '' || v == password2Obj.defConfig.defText)){
			isPass = false;
			//password2.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			alert("确认密码不能为空");
			return isPass;
		}
		if(v != password.val()){
			isPass = false;
			//password2.parent().find('.msg-error').html('<i class="error"></i>两次输入的密码不一致，请重新输入').show();
			alert("两次输入的密码不一致，请重新输入");
		}else{
			isPass = true;
			password2.parent().find('.msg-error').hide();
		}
		if(!isPass){
			password2.parent().find('.check-right').css('display', 'none');
		}else{
			password2.parent().find('.check-right').css('display', 'inline-block');
		}
		return isPass;
	};
	
	
	var checkVcode = function(isForm){
		var v = vcode.val(),isPass = false;
		if(isForm && (v == '' || v == vcodeObj.defConfig.defText)){
			isPass = false;
			//vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			alert("验证码不能为空");
			return isPass;
		}
		else{
			isPass = true;
			vcode.parent().find('.msg-error').hide();
		}
		return isPass;
	};
	var refreshCode = function(){
		var img = $('#img-code'),src = img.attr('data-src-path');
		img.attr('src', src + '?' + Math.random());
	};
	var verifyCode = function()
	{
		var v = vcode.val();
		if(v == '' || v == vcodeObj.defConfig.defText){
			//vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			alert("验证码不能为空");
			return;
		}
		if(checkVcode()){
			$.ajax({
				url:"/register/checkvcode",
				type:'POST',
				dataType:'json',
				data:{vcode:v},
				beforeSend:function(){
					vcode.parent().find('.check-loading').css('display', 'inline-block');
				},
				success:function(data){
					if(data['status'] == "ok"){
						vcode.parent().find('.check-right').css('display', 'inline-block');
						submitForm();
					}else{
						vcode.parent().find('.msg-error').html('<i class="error"></i>'+data["data"]).show();
						refreshCode();
					    vcode.focus();
					}
				},
				complete:function(){
					vcode.parent().find('.check-loading').css('display', 'none');
				}
			});
		}
	};
	
	vcode.focus(function(){
		vcode.parent().find('.check-right').css('display', 'none');
	}).blur(function(){
		var v = vcode.val();
		if(v == '' || v == vcodeObj.defConfig.defText){
			//vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			alert("验证码不能为空");
			return;
		}
	});

	//设备类型对象
	var browser={
            versions:function() {
                var u=navigator.userAgent;
                return {//移动终端浏览器版本信息
                    trident: u.indexOf('Trident') > -1, //IE内核
                    presto: u.indexOf('Presto') > -1, //opera内核
                    webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                    gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                    mobile: !!u.match(/AppleWebKit.*Mobile.*/) || !!u.match(/AppleWebKit/), //是否为移动终端
                    ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                    android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                    iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                    iPad: u.indexOf('iPad') > -1, //是否iPad
                    webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
                };
            }(),
            language:(navigator.browserLanguage || navigator.language).toLowerCase()
        }


	var submitForm=function()
	{
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};
		var params;
		var s_device; //设备类型
		if(browser.versions.ios || browser.versions.iPhone || browser.versions.iPad){
			s_device = 'ios';
		} else if (browser.versions.android){
			s_device = 'android';
		} else {
			s_device = 'web';
		}
		 if(isOpenVcode()){
			if(!checkVcode()){
					return;
				}
				params = {'username':username.val(),'password1':Base64.encode(password.val()),'password2':Base64.encode(password2.val()),
				'vcode':vcode.val(),'device':s_device};
			}else{
				params = {'username':username.val(),'password1':Base64.encode(password.val()),'password2':Base64.encode(password2.val()),'device':s_device};
		}
        
		/*if(checkUsername(true) && checkPassword(true) && checkPassword2(true) && checkVcode(true)){*/
			/*var sdata="username="+username.val()+"&password1="+password.val()+"&password2="+password2.val()+"&vcode"+vcode.val();*/
			$.ajax({
				url:"/register/conform/",
				dataType:'json',
				method:'post',
				data:params,
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data) + '&uniq_id=' + uniq_id;
					
					$("#J-button-submit").attr("disabled","true").val('提交中，请稍等！').css('background','#ff9600');
				},
				success:function(data){
					if(data["status"]=="ok")
					{
						
						TempCache.setItem("userName_Mobile",username.val()); 
						if(jumpApp){
							setTimeout(function(){ window.location="lottery://login";},3000);
						} else {
							setTimeout(function(){ window.location="/register?typeMobile=true";},3000);
						}
					}else
					{
						if(isOpenVcode() || (data['dataCnt'] && data['dataCnt'] > 2)){
							$('#J-panel-vcode').show();
							vcode.val('');
							refreshCode();
					    }
						if(data['type']=='1')
						{
							alert(data['data']);
						}else if(data['type']=='2')
						{
							alert(data['data']);
							
						}else if(data['type']=='3')
						{
							alert(data['data']);
						}else if(data['type']=='4')
						{
							alert(data['data']);
						} else if(data['type'] == '6'){
							alert(data['data']);
						} else {
							alert(data['data']);
						}
					}
				}, 
				complete:function(){
					setTimeout(function(){
						$("#J-button-submit").removeAttr("disabled").val('提交注册').css('background','');						
					}, 1000);
					
				}
			});
	};
	
	
	$('#J-button-submit').click(function(){
		if(!checkUsername(true)){
		return;
		}
		if(!checkPassword(true)){
		return;
		}
		if(!checkPassword2(true)){
		return;
		}
		if(checkUsername(true) && checkPassword(true) && checkPassword2(true)){
			if(isOpenVcode()){
				verifyCode();
			}else{
				submitForm();
			}
		}
	});
	$(document).keyup(function(e){
		if(e.keyCode == 13){
			$('#J-button-submit').click();
		}
	});
	
	//临时存储
	var TempCache = {
		cache:function(value){
			localStorage.setItem("EasyWayTempCache",value);
		},
		getCache:function(){
			return  localStorage.getItem("EasyWayTempCache");
		},
		setItem:function(key,value){
			localStorage.setItem(key,value);
		},
		getItem:function(key){
			return localStorage.getItem(key);
		},
		removeItem:function(key){
			return localStorage.removeItem(key);
		}
	};

	})(jQuery);
	
</script>
	{/literal}
	
</body>
</html>