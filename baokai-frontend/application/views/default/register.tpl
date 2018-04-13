<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>注册</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/login/register.css" />
    <link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
    <link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
    <style>
.register-area .submit-btn {
transition: all .3s;
background-color: #25A2FF;
}


.register-area .submit-btn:hover{
    opacity: 0.8;
}

.cycle-slideshow .cycle-pager-wrap span.cycle-pager-active {
background: #25A2FF;
}


    </style>
</head>
<body class="new-register">
	<!-- star -->
	<!-- <div class="header">
		<div class="g_33">
			<a class="logo" title="首页" href="/index"></a>
		</div>
	</div> -->
	<!-- end -->
	<div class="register-wrap">
		<div class="register-content">
			<div class="top-wrap clearfix">
				<div class="logo"></div>

			</div>
			<div class="register-panel clearfix">
				<!-- <div class="banner-area">
					{if $adSpaces.isDftUsed eq '1'}
						<div class="slider j-ui-globalad-pos">
						{if $adSpaces.dftImg eq ''}
							<img src="{$path_img}/images/ucenter/index/ad2.jpg" />
						{else}
							<img src="{$dynamicroot}/{$adSpaces.dftImg}" />
						{/if}
						</div>
		            {else}
			            	<input type="hidden" name="urlTarget" value="{$adSpaces.urlTarget}" />
			            	<input type="hidden" name="name" value="{$adSpaces.name}" />
			                <div class="slider j-ui-globalad-pos" id="J-globalad-index_pos_register"  style="width:400px; height:300px;"></div>
		            {/if}
		        </div> -->
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
			
				<div class="register-area">
				


					<form method="post" id="J-form">

						<ul class="register">
							<li>
								<label class="ui-label" for="name" style="color: rgb(51, 51, 51);">用户名：</label>
								<input type="text" class="input" id="J-input-username" name="username" value="4~16位字母或数字，首位为字母" />
			                    <i class="check-right"></i>
								<i class="check-loading"></i>
								<div class="msg-error" style="display:none" id="nameerror">
			                    <i class="error"></i>
			                    </div>
								<input type="hidden" id="J-loginParam" data-name="loginParam" name="loginParam" value="{$loginRand}" />
							</li>
							<li>
								<label class="ui-label" for="pwd" style="color: rgb(51, 51, 51);">密码：</label>
								<input type="text" onpaste="return false" class="input"  id="J-input-password" name="password1" value="请输入密码，英文字符加数字" />
								<div class="pwd-strength" id="J-div-pwdstrength" style="width: 270px; display: none;">密码强度：弱
			                                	<span class="pwd-bar"><b id="password_label" class="pwd-weak"></b></span>强<span id="newcheckString"></span></div>
								<i class="check-loading"></i>
			                    <div class="msg-error" style="display:none" id="pwderror">
			                    <i class="error"></i>
			                    </div>
			                     <div id="capsLock" class="caps-lock eyeon" state="show">
			                    	<!--<div class="caps-lock-tip"><i></i>请注意大写锁定已经打开</div>   2016-7-8 09:40:46 by feiker --> 
			                    </div>
							</li>
							<li>
								<label class="ui-label" for="check-pwd" style="color: rgb(51, 51, 51);">确认密码：</label>
								<input type="text" onpaste="return false" class="input"  id="J-input-password2" name="password2" value="请输入密码，英文字符加数字" />
			                    <i class="check-right"></i>
								<i class="check-loading"></i>
			                    <div class="msg-error" style="display:none" id="pwderror2">
			                    <i class="error"></i>
			                    </div>
			                    <!--<div id="capsLock2" class="caps-lock eyeon" state="show">
			                    	<div class="caps-lock-tip"><i></i>请注意大写锁定已经打开</div>  2016-7-8 09:41:20 by feiker  
			                    </div>-->
							</li >
							<li id="J-panel-vcode" {if $errorCnt lt 3}style="display:none"{/if}>
			                    <label class="ui-label" for="check-pwd" style="color: rgb(51, 51, 51);">验证码：</label>
								<input id="J-input-vcode" onpaste="return false" type="text"  class="input" value="" name="vcode" tabindex="3"  maxlength="4"/ style="width:220px">
			                    <img id="img-code" style="width:70px;height:34px;cursor:pointer;" src="{$imageurl}" alt="点击更换验证码" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" />
			                    <div class="msg-error" style="display:none" id="pwderror"><i class="error"></i></div>
			                    <a href="#" onClick="javascript:$('#img-code').click();" style="display:none;">换一张</a>
			                    <i class="check-right"></i>
								<i class="check-loading"></i>
							</li>
							<li style="height:0;">
								<input type="hidden" class="input" style="width:240px;"  id ="cellphone" name="cellphone" value="{$cellphone}"/>
								<input type="hidden" class="input" style="width:240px;"  id ="activityType" name="activityType" value="{$activityType}"/>
							</li>
						</ul>
						<div id="J-button-submitDev">
							<input id="J-button-submit" class="submit-btn" type="submit" value="提交注册">
							<a href="../login" class="btn-login-link">已有账户请点击</a>
						</div>	
					</form>
				</div>
			</div>
		</div>

		<!-- {if $qq != ''}  -->
		<a class="custom-service" target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin={$qq}&site=qq&menu=yes"></a>
		<!-- {/if} -->
		
	</div>

    <!-- footer 不使用的情况下请自行删除 -->
<div id="DivSuccessful" class="pop pop-error w-4" style="position:absolute;z-index:100; display:none">
<i class="ico-success"></i><h4 class="pop-text">注册成功</h4>
</div>
<div id="DivFailed" class="pop pop-success w-4" style="position:absolute;z-index:100; display:none">
<i class="ico-error"></i><h4 class="pop-text">注册失败，请重试</h4>
</div>
<div id="DivURLFailed" class="pop pop-success w-4" style="position:absolute;z-index:100; display:none">
<i class="ico-error"></i><h4 class="pop-text">注册失败，链接已失效，请联系代理获取新注册链接</h4>
</div>
<div id="DivCusFailed" class="pop pop-success w-4" style="position:absolute;z-index:100; display:none">
<i class="ico-error"></i><h4 class="pop-text"></h4>
</div>



    <script type="text/javascript">global_path_url="{$path_img}";</script>
	<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.cycle2.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.backgroundpos.min.js"></script>
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
    <script type="text/javascript" src="{$path_js}/js/phoenix.Common.js"></script>
    <script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.md5.js"></script>
	{literal}
	<script type="text/javascript">
	(function() {
		if(window.innerHeight < 700){
			$('.top-wrap').css("paddingTop",'50px');
		}
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

		/* $("#contact").click(function(){
			   if(typeof hj5107 != "undefined")
			   {
				 hj5107.openChat();
			   }
		 });*/    //  2016-7-11 13:06:18 by feiker
	})();
	</script>
	<script>
	(function($){
	//RSA 加密
	var rsa = new RSAKey();
	var ajaxLock = false;
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	var uniq_id = '{$uniq_id}';
	{literal}
	var username = $('#J-input-username'),password = $('#J-input-password'),password2 = $('#J-input-password2'),vcode = $('#J-input-vcode'),cellphone = $('#cellphone'),activityType = $('#activityType');
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
	/* 2016-7-8 09:44:15 by feiker
		var capLock = function(e){
	 	kc = e.keyCode?e.keyCode:e.which;
	 	sk = e.shiftKey?e.shiftKey:((kc == 16)?true:false);
	 	if(((kc >= 65 && kc <= 90) &&  !sk)||((kc >= 97 && kc <= 122) &&  sk)){
	 		$('#capsLock').fadeIn();
	 	}else{
	 		$('#capsLock').fadeOut();
	 	}
	}
	var capLock2 = function(e){
	 	kc = e.keyCode?e.keyCode:e.which;
	 	sk = e.shiftKey?e.shiftKey:((kc == 16)?true:false);
	 	if(((kc >= 65 && kc <= 90) &&  !sk)||((kc >= 97 && kc <= 122) &&  sk)){
	 		$('#capsLock2').fadeIn();
	 	}else{
	 		$('#capsLock2').fadeOut();
	 	}
	}**/
	//}
	//操作后提示	 
	function fnDiv(obj){		
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";		
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	}
	
	var usernameObj = new phoenix.Input({el:username,defText:'4-16位字符，可由字母、数字组成'});
	var passwordObj = new phoenix.Input({el:password,defText:'6-20位字符组成，区分大小写'});
	var password2Obj = new phoenix.Input({el:password2,defText:'请重新输入一遍密码'});
	var vcodeObj = new phoenix.Input({el:vcode,defText:'请输入图片中的字符'});
	
	//username.keyup(disabledCn);
	//password.keypress(capLock);		2016-7-8 09:44:52 by feiker
	password.keyup(disabledCn);
	//password2.keypress(capLock2);		2016-7-8 09:45:02 by feiker
	password2.keyup(disabledCn);
	vcode.keyup(disabledCn);
	
	
	//2016-7-8 17:06:03 by feiker
	
	var _capslock = document.getElementById("capsLock");
	var pwd1 = document.getElementById('J-input-password');
	_capslock.addEventListener('click',function(evt){
		if(pwd1.value != '' && pwd1.value != '6-20位字符组成，区分大小写'){
			if(_capslock.getAttribute('state') == 'show'){
				_capslock.setAttribute('state','hide');
				_capslock.setAttribute('class','caps-lock eyeoff');						
				pwd1.type = 'text';
			}else{
				_capslock.setAttribute('state','show');
				_capslock.setAttribute('class','caps-lock eyeon');							
				pwd1.type = 'password';
			}
		}
	},false);

	
	var testpass=function(v){
		var level = 0;
		if((/^\d+$/).test(v) || (/^[A-Za-z]+$/).test(v) || (/^[^a-zA-Z0-9]+$/).test(v)){
			level = 1;
		}
		if((/^\d+$/).test(v)){
			var first = v.charAt(0);
			for (var i = 0; i < v.length; i++) {
				if(first == v.charAt(i)){
					level = 3;
				}else if(Number(first)+Number(i) == v.charAt(i)){
					level = 2;
				}else{
					level = 1;
					break;
				}
			};
		}
		if(((/\d+/).test(v) && (/[A-Za-z]+/).test(v)) || ((/\d+/).test(v) && (/[^a-zA-Z0-9]+/).test(v)) || ((/[A-Za-z]+/).test(v) && (/[^a-zA-Z0-9]+/).test(v))){
			level = 4;
		}
		if((/\d+/).test(v) && (/[A-Za-z]+/).test(v) && (/[^a-zA-Z0-9]+/).test(v)){
			level = 5;
		}
		return level;
	}
	
	
	//密码强度验证
	function checkpass(pass){
		var score = testpass(pass.val());
		var password_label = $("#password_label");
		var levelStr = '';
		password_label.removeAttr("class");
		if(score == 1){
			password_label.attr("class","pwd-weak");
			levelStr = '试试字母、数字、标点组合吧';
		}else if(score == 2){
			password_label.attr("class","pwd-weak");
			levelStr = '连续字符密码易被破解，请用多组合的密码';
		}else if(score == 3){
			password_label.attr("class","pwd-weak");
			levelStr = '相同字符密码易被破解，请用多组合的密码';
		}else if(score==4){
			password_label.attr("class","pwd-middle");
			levelStr = '密码复杂度中等';
		}else if(score==5){
			password_label.attr("class","pwd-strong");
			levelStr = '密码强度好，请牢记！';
		}
		$("#J-div-pwdstrength").css("display","inline-block");
		$('#newcheckString').html(levelStr);
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
	
	var formLoading = function(){
		var textArr = ['加载中   ', '加载中.  ', '加载中.. ', '加载中...'],i = 0;
		isGlobalLoading = true;
		mask.show($submitDom);
		clearInterval(formLoading.timer);
		formLoading.timer = setInterval(function(){
			$submitDom.val(textArr[i]);
			i = i >= textArr.length - 1 ? 0 : i + 1;
		}, 200);
	};
	
	var getRedirectParams = function(){
	    var url = location.search,pattern = "redirect=",str = "";
	    if (url.indexOf(pattern) != -1) {
			var str = url.substr(url.indexOf(pattern)+pattern.length);
		}
		return str;
	};
	
	var checkUsername = function(isForm){
		var v = username.val(),isPass = false;
		if(isForm && (v == '' || v == usernameObj.defConfig.defText)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能为空').show();
			return isPass;
		}
		if(WidthCheck(v) < 4 || WidthCheck(v) > 16){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入4-16位字符').show();
		}else if(!(/^(?![0-9])/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能数字开头').show();
		}else if((/^\d+$/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能是纯数字').show();
		}
		else if((/[^A-Za-z0-9]/g).test(v)){
			isPass = false;
			username.parent().find('.msg-error').html('<i class="error"></i>用户名只能由字母和数字组成').show();
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
	loginParam = $('#J-loginParam').val();
	username.blur(function(){
		var v = this.value;
		if(v == '' || v == usernameObj.defConfig.defText){
			username.parent().find('.msg-error').html('<i class="error"></i>用户名不能为空').show();
			return;
		}
		if(checkUsername()){
			$.ajax({
				url:"/register/checkusername",
				dataType:'json',
				method:'post',
				data:{username:v},
				beforeSend:function(){
					username.parent().find('.check-loading').css('display', 'inline-block');
				},
				success:function(data){
					//username.parent().find('.check-right').css('display', 'inline-block');
					if(data['status'] == 0){
						username.parent().find('.msg-error').hide();
						username.parent().find('.check-right').css('display', 'inline-block');
					}else{
						username.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
					}
				},
				complete:function(){
					username.parent().find('.check-loading').css('display', 'none');
				}
			});
		}
	});
	
	var checkPassword = function(isForm){
		var v = password.val(),isPass = false;
		if(isForm && (v == '' || v == passwordObj.defConfig.defText)){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return isPass;
		}
		if(v.length < 6 || v.length > 20){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>长度有误，请输入6-20位字符').show();
		}else if(v != '' && username.val() == v){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能和用户名一致').show();
		}else if((/\s/).test(v)){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不含空格').show();
		}else if((/^\d{1,8}$/).test(v)){
			isPass = false;
			$("#J-div-pwdstrength").css("display","none");
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能是9位以下纯数字').show();
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
	password.blur(function(){
		var v = this.value;
		if(v == '' || v == passwordObj.defConfig.defText){
			password.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return;
		}
		checkPassword();
	});
	
	var checkPassword2 = function(isForm){
		var v = password2.val(),isPass = false;
		if(isForm && (v == '' || v == password2Obj.defConfig.defText)){
			isPass = false;
			password2.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return isPass;
		}
		if(v != password.val()){
			isPass = false;
			password2.parent().find('.msg-error').html('<i class="error"></i>两次输入的密码不一致，请重新输入').show();
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
	password2.blur(function(){
		var v = this.value;
		if(v == '' || v == password2Obj.defConfig.defText){
			password2.parent().find('.msg-error').html('<i class="error"></i>密码不能为空').show();
			return;
		}
		checkPassword2();
	});
	
	var checkVcode = function(isForm){
		var v = vcode.val(),isPass = false;
		if(isForm && (v == '' || v == vcodeObj.defConfig.defText)){
			isPass = false;
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
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
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
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
			vcode.parent().find('.msg-error').html('<i class="error"></i>验证码不能为空').show();
			return;
		}
	});
	
	if(ajaxLock){
			return;
		}
		ajaxLock = true;
	var formLoaded = function(){
		var text = '登 录';
		clearInterval(formLoading.timer);
		isGlobalLoading = false;
		//$submitDom.val(text);
		mask.hide();
	};
		//关闭消息提示
	
	var submitForm=function()
	{
		//base64 加密
	var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};

		var params;
		var vUsername = $.trim(username.val()),vPassword = $.trim(password.val()),vLoginParam = $.trim(loginParam),vVcode,timer,params;
        vPassword = $.md5($.md5($.md5($.md5($.md5(vPassword))))+vLoginParam);
		 if(isOpenVcode()){
			if(!checkVcode()){
					return;
				}
				params = {'username':username.val(),'password1':Base64.encode(password.val()),'password2':Base64.encode(password2.val()),'cellphone':cellphone.val(),
				'vcode':vcode.val(),'device':'web','activityType':activityType.val()};
				params_new = {'username':vUsername,'password':vPassword,'param':vLoginParam,'vcode':vVcode};
			}else{
				params = {'username':username.val(),'password1':Base64.encode(password.val()),'password2':Base64.encode(password2.val()),'cellphone':cellphone.val(),'device':'web','activityType':activityType.val()};
				params_new = {'username':vUsername,'password':vPassword,'param':vLoginParam};
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
					$("#J-button-submitDev").hide();
					//$("#J-button-submit").attr("disabled","true");
				},
				success:function(data){
					if(data["status"]=="ok")
					{
						//$.cookie('loginType', '1', {expires: 7, path: '/'});
						fnDiv('DivSuccessful');
			            /*setTimeout(function(){
			            	$('#DivSuccessful').css("display","none");
			            	window.location.href="/login/index";
			            },3000);*/
						$.ajax({
			url: '/login/login',
			type: 'POST',
			dataType: 'json',
			cache:false,
			data: params_new,
			beforeSend:function(){
				timer = setTimeout(formLoading, 500);
				isGlobalLoading = true;
			}
		})
		.done(function(data) {
			var url = '/index';
		
				var loginType = $('.choose-game input[name="choose-game"]:checked ').val();
				$.cookie('loginType', loginType, {expires: 7, path: '/'});
				if(loginType =='2'){
					url = "/index/electronic";
				}
				url = getRedirectParams() == '' ? url : getRedirectParams();
				location.href = url;
			
		})
		.fail(function() {
			
		})
		.always(function() {
			ajaxLock = false;
			clearTimeout(timer);
			isGlobalLoading = false;
			formLoaded();
		});
		
		e.preventDefault();
					}else
					{
						if(isOpenVcode() || (data['dataCnt'] && data['dataCnt'] > 2)){
							$('#J-panel-vcode').show();
							vcode.val('');
							refreshCode();
					    }
						if(data['type']=='1')
						{
							username.parent().find('.check-right').css('display', 'none');
						    username.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
							fnDiv('DivFailed');
			                setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
						}else if(data['type']=='2')
						{
							password.parent().find('.check-right').css('display', 'none');
						    password.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
							fnDiv('DivFailed');
			                setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
							
						}else if(data['type']=='3')
						{
							vcode.parent().find('.check-right').css('display', 'none');
						    vcode.parent().find('.msg-error').html('<i class="error"></i>'+data['data']).show();
							fnDiv('DivFailed');
			                setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
						}else if(data['type']=='4')
						{
							fnDiv('DivURLFailed');
			                setTimeout(function(){ $('#DivURLFailed').css("display","none");},3000);
						} else if(data['type'] == '6'){
							$('#DivCusFailed').html('<i class="ico-error"></i><h4 class="pop-text">'+data['data']+'</h4>'); 
							fnDiv('DivCusFailed');
			                setTimeout(function(){ $('#DivCusFailed').css("display","none");},3000);
						} else {
							fnDiv('DivFailed');
			           	    setTimeout(function(){ $('#DivFailed').css("display","none");},3000);
						}
					}

					$("#J-button-submitDev").show();
					//$("#J-button-submit").removeAttr("disabled");
				}, 
				complete:function(){
					//$("#J-button-submit").removeAttr("disabled");
				}
			});
	};
	
	
	$('#J-button-submit').click(function(){
		checkUsername(true);
		checkPassword(true);
		checkPassword2(true);
		if(checkUsername(true) && checkPassword(true) && checkPassword2(true)){
			if(isOpenVcode()){
				verifyCode();
			}else{
				submitForm();
			}
		}
		return false;
	});
		
	//页面广告
	// new phoenix.GlobalAd({
	// 	width:400,
	// 	height:300,
	// 	url:'/api/jsonp/getAdverts?u=-1&k=index_pos_register&r='+Math.random(),
	// 	callback:function(){
	// 		var slider = new phoenix.Slider({
	// 			par:$('#J-globalad-index_pos_register'),
	// 			panels:'.slider-pic li',
	// 			triggers:'.slider-num li',
	// 			sliderDirection:'left',
	// 			sliderIsCarousel:true
	// 		});
	// 	}
	// });
	var reBuildAd=function(tpl,ad,w,h){
       var me = tpl;
        ad['link'] = reBuildLink(ad['id'], ad['link']);
        ad['src'] = ad['src'];
        ad['width'] = w;
        ad['height'] = h;
      return ad;
    }
     var reBuildLink=function(id, link){
      return link;
    }
	$.ajax({
		url:'/api/jsonp/getAdverts?u=-1&k=index_pos_register&r='+Math.random(),
		cache:false,
		dataType:'jsonp',
		jsonp: "callBack",
		success:function(data){
			if(Number(data['isSuccess']) == 1){
				var me = data,list = me.data,len = list.length,listLen,dom,width,height,tplSingle,html = '';
				if(len > 0){
					for(var i=0;i<len;i++){
						if(list[i]['name']=='index_pos_register'){
							html = '';
							width=400;
							height=300;
							dom = $('#focus');
							tplSingle='<div class="item"><a target="_blank" href="<#=link#>"><img src="<#=src#>" title="<#=title#>" alt="<#=title#>"></a></div>'
							if(dom.size() > 0){
								listLen = list[i]['list'].length;
								if(listLen > 0){
									dom.css({width:width});
									for(var j=0;j < listLen;j++){
										ad = reBuildAd(tplSingle,list[i]['list'][j],width,height);
										ad['index'] = (j + 1);
										html = phoenix.util.template(tplSingle, ad);
										dom.cycle('add',html);
									}
								}else{
									dom.cycle('add','<div class="item"><a target="_blank" href="#"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
								}
							}
						}
					}
				}
			}else{
				$('#focus').cycle('add','<div class="item"><a target="_blank" href="/index"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
			}
		},
		error:function(xhr, type){
			$('#focus').cycle('add','<div class="item"><a target="_blank" href="/index"><img src="'+global_path_url+'/images/login/new-login/banner.jpg" alt=""></a></div>');
		}
	});

	//首屏高度
	var fitPage = function(){
		$('.register-wrap').height($(window).height());
	}
	fitPage();
	var t;
	var func = function(){
		clearTimeout(t);
		t = setTimeout(fitPage, 25);
	}
	window.onresize = function(){
		func();
	}
	$('#nextTitle').click(function(){
		var h = $('.register-wrap').height();
		$('body,html').animate({scrollTop:h},800);
	})
	$('#gameBtn').click(function(){
		$('body,html').animate({scrollTop:0},800);
	})
	var oJackpot = $('#jackpot');
	function rollNum(n,obj){
		var nL = String(n).length;
		var objNum = obj.find('em');
		var objHeight = obj.height();
		var oL = objNum.length/obj.length
		for(var i=0;i<nL;i++){
			if(oL<=i){
				obj.append("<em><span style='background-position:0 0'></span></em>");
			}
			var num = String(n).charAt(i);
			obj.each(function(){
				var objEm = $(this).find('em').eq(i);
				var objSpan = $(this).find('span').eq(i);
				if(isNaN(num)){
					objEm.addClass('ico-comma');
				}else{
					objEm.removeClass('ico-comma');
					var y = -parseInt(num)*objHeight;
					objSpan.animate({
						backgroundPosition:'0 '+String(y)+'px'
					}, 800);
				}
			});
		}
	}
	var n = $("#cur_num").val();
	function move(){
		rollNum(n,oJackpot);
		n = (Number(n) + parseInt(Math.random()*10) + Number(Math.random().toFixed(2))).toFixed(2);
	}
	setInterval(move,3000);



	})(jQuery);
	</script>
	{/literal}
	
</body>
</html>
