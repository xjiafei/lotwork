<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{literal}
	<style>
	.ui-check-tip {color:#999;}
	.ui-check-right {padding-left:24px;color:green;}
#J-button-submit-password{margin-left: 193px;}
#J-button-submit-safePassword{margin-left: 193px;}
	</style>
	{/literal}
	
	{include file='/default/script-base.tpl'}	
</head>
<body>
<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	
	<div class="g_33 common-main">
	
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
			{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->			
		</div>
		
		
		<div class="g_28 g_last">
			<div class="common-content content-password-edit">
				<div class="title">密码管理</div>
				<div class="content">
					<!-- ucenter-safe-password-edit start -->
					<div class="ui-tab tab-change-pswd">
						<ul class="ui-tab-title tab-title-from clearfix">
							<li>修改登录密码</li>
							<li>修改安全密码</li>
						</ul>
						
						
						<form action="?act=1" method="post" id="J-form-password">
						<input id="J-user-value" type="hidden" value="{$smarty.session.datas.info.account}" />
						<ul class="ui-form ui-tab-content">
							<li>
								<label class="ui-label" for="info">当前登录密码：</label>
								<input type="password" name="password"  class="input" id="J-password" value="" >
								<span class="ui-check"></span>
							</li>
							<li>
								<label class="ui-label" for="checkInfo">新登录密码：</label>
								<input type="password" name="password_new" class="input" id="J-password-new" value="">
                                <span class="ui-check"></span>
                                <div class="pwd-new-strength" id="J-div-pwdstrength" style="display: none;">密码强度：弱
                                	<span class="pwd-new-bar"><b id="password_label" class="pwd-new-weak"></b></span>强<span id="newcheckString"></span></div>
							</li>
							<li>
								<label class="ui-label" for="checkInfo">确认新密码：</label>
								<input type="password" class="input" name="password_new2" id="J-password-new2" value="">
                                 <span class="ui-check"></span>
							</li>
							<!-- {if $isBindSecCard eq '1'} -->
							<li>
								<label class="ui-label" for="name">安全中心验证码：</label>
								<input id="J-code-safecenter" type="text" class="input w-2" value="">
								<span class="ui-prompt">为了您的账户安全，连续错误5次后当日将被锁定，会影响您的提现、绑定等操作 </span>
								<span class="ui-check"></span>
							</li>
							<li>
								<label class="ui-label"></label>
								<a href="#">手机遗失或忘记启动密码</a>
								{foreach from=$aToken item=data}
								{$data} - 
								{/foreach}
							</li>
							<!-- {else} -->
							<!-- {/if} -->
							<li class="ui-btn">
								<a href="javascript:void(0);" class="btn" id="J-button-submit-password"><span id="J-button-submit-text">修 改</span><b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
						
						
						<form action="?act=2" method="post" id="J-form-safePassword">
						<ul class="ui-form ui-tab-content">
							<li>
								<label class="ui-label" for="info">当前安全密码：</label>
								<input type="password" name="safePassword" class="input" id="J-safePassword" value="">
                                <span class="ui-check"></span>
							</li>
							<li>
								<label class="ui-label" for="checkInfo">新安全密码：</label>
								<input type="password" name="safePassword_new" class="input" id="J-safePassword-new" value="">
                                <span class="ui-check"></span>
                                <div class="pwd-new-strength" id="J-div-pwdstrength1" style="display: none;">密码强度：弱
                                	<span class="pwd-new-bar"><b id="password_label1" class="pwd-new-weak"></b></span>强<span id="safecheckString"></span></div>
							</li>
							<li>
								<label class="ui-label" for="checkInfo">确认新密码：</label>
								<input type="password" name="safePassword_new2" class="input" id="J-safePassword-new2" value="">
                               <span class="ui-check"></span>
                                
							</li>
							<!-- {if $isBindSecCard eq '1'} -->
							<li>
								<label class="ui-label" for="name">安全中心验证码：</label>
								<input id="J-code-safecenter2" type="text" class="input w-2" value="">
								<span class="ui-prompt">为了您的账户安全，连续错误5次后当日将被锁定，会影响您的提现、绑定等操作</span>
								<span class="ui-check"></span>
							</li>
							<li>
								<label class="ui-label"></label>
								<a href="#">手机遗失或忘记启动密码</a>
								{foreach from=$aToken item=data}
								{$data} - 
								{/foreach}
								
							</li>
							<!-- {else} -->
							<li>
							</li>
							<!-- {/if} -->
							<li class="ui-btn">
								<a href="javascript:void(0);" class="btn" id="J-button-submit-safePassword"><span id="J-button-submit-text-2">修 改</span><b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>					
						
						
						
					</div>
					
					<!-- ucenter-safe-password-edit end -->
				</div>
			</div>
		</div>
		
		
		
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
	
<div id="J-ui-mask-msg" style="position:absolute;display:none;z-index:601;width:360px;" class="pop pop-success w-6">
	<i class="ico-success"></i>
	<h4 class="pop-text">操作成功</h4>
</div>

<div class="pop pop-search" id="Idivs" style="position:absolute;z-index:2;display:none ">  
	<div class="hd"><i  id="CloseDiv"></i>温馨提示</div>							
	
	<div class="bd">
		<div class="bd text-center">
			<i class="ico-success"></i>
			<h4 class="pop-text">恭喜您密码修改成功，请重新登录。</h4>
		</div><br>
		
		
	</div>
	<a class="btn closeTip" id="closeTip1" style="" href="javascript:void(0);">确定</a>
</div>
	
<script>
var isSetWithdrawpwd = {if $smarty.session.datas.info.withdrawPasswd eq ''}0{else}1{/if};
</script>
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script type="text/javascript" src="{$path_js}/js/phoenix.Verification.js"></script>
<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>

{literal}
<script>
(function($){
	//base64 加密
	var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};
	//RSA 加密
	var rsa = new RSAKey();
{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
{literal}
	//tab切换
	var tabIndex = phoenix.util.getParam('issafecode') == '1' ? 1 : 0,
	//输入框
	password = $('#J-password'),
	password_new = $('#J-password-new'),
	password_new2 = $('#J-password-new2'),
	codeSafeCenter = $('#J-code-safecenter'),
	safePassword = $('#J-safePassword'),
	safePassword_new = $('#J-safePassword-new'),
	safePassword_new2 = $('#J-safePassword-new2'),
	codeSafeCenter2 = $('#J-code-safecenter2'),
	usernamev = $.trim($('#J-user-value').val()),
	//ajax正确结果数
	loadingNum = 0,
	loadingNum2 = 0,
	box = new LightBox("Idivs"),
	mask = phoenix.Mask.getInstance();
	
    var checkFn = function(){
			var me = this;
			//先把非数字的都替换掉，除了数字和.
		  	me.value = me.value.replace(/[^\d]/g,"");
	};
	codeSafeCenter.keyup(checkFn);
	codeSafeCenter.blur(checkFn);
	codeSafeCenter2.keyup(checkFn);
	codeSafeCenter2.blur(checkFn);
	
	//提示消息
	var maskMsg = {
		el:$('#J-ui-mask-msg'),
		show:function(isSuccess, msg){
			var me = this;
			if(isSuccess){
				me.el.find('i').removeClass().addClass('ico-success');
				me.el.removeClass().addClass('pop pop-success w-4');
			}else{
				me.el.find('i').removeClass().addClass('ico-error');
				me.el.removeClass().addClass('pop pop-error w-6');
			}
			me.el.find('.pop-text').html(msg);
			phoenix.util.toViewCenter(me.el);
			me.el.show();
			mask.show();
		},
		hide:function(){
			var me = this;
			me.el.hide();
			mask.hide();
		}
	};
	
	var loadingFn = function(el){
		var el = $(el);
	};
	
	var tab = new phoenix.Tab({triggers:'.ui-tab-title li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current',index:tabIndex});
	tab.addEvent('beforeSwitch', function(e, index){
		if(index == 1){
			if(isSetWithdrawpwd == '0'){
				location.href = '/safepersonal/safecodeset';
			}/*  else {
				location.href = '?issafecode=1';// 
			} */
		}/* else{
			location.href = '?';
		} */
	});
	
	$('#closeTip1').click(function(){
		box.Close();
		location.href = '/login/logout';
		
	});
	
	var eventFocus = function(el, msg){
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-tip').show();
	};
	var showError = function(el, msg){
		$(el).parent().find('.ui-check').html('<i></i>'+msg).removeClass().addClass('ui-check').show();
	};
	var showLoading = function(el){
		$(el).parent().find('.ui-check').html('&nbsp;&nbsp;&nbsp;&nbsp;').removeClass().addClass('ui-check ui-check-loading').show();
	};
	var hideLoading = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	var showRight = function(el, msg){
		var msg = typeof msg == 'undefined' ? '' : msg;
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-right').show();
	};
	var hideTip = function(el){
		$(el).parent().find('.ui-check').removeClass().addClass('ui-check').hide();
	};
	//获取密码复杂等级
	var getPassowrdLevel = function(v){
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
	};
	var ajaxCallBackList = [];
	var ajaxCallBack = function(obj){
		var i = 0,len = ajaxCallBackList.length;
		if(loadingNum > 0){
			return;
		}
		for(;i < len;i++){
			if(ajaxCallBackList[i].call(obj || null) === false){
				break;
			}
		}
		ajaxCallBackList = [];
	};
	//isAjax 是否进行远程校验
	//isSubmit 验证成功是否调用回调列队
	var checkPassword = function(isAjax, isSubmit){
		var el = password,v = $.trim(el.val()),isPass = false;
		if(v == ''){
			showError(el, '登陆密码不能为空');
			isPass = false;
		}else if(!(/^.{6,20}$/).test(v))
		{
			showError(el, '长度有误，请输入6-20位字符');
			isPass = false;
		}else{
			hideTip(el);
			isPass = true;
		}
		if(isAjax && isPass){
			$.ajax({
				url:'/safepersonal/safecodeedit',
				dataType:'json',
				method:'post',
				data:{'password':$.trim(password.val()), 'password_new':$.trim(password_new.val()), 'password_new2':$.trim(password_new.val()),'code':$.trim(codeSafeCenter.val()), 'act':1},
				beforeSend:function(){
					showLoading(el);
					loadingNum += 1;
				},
				success:function(data){
					hideLoading(el);
					loadingNum -= 1;
					if(Number(data['isSuccess']) == 1){
						showRight(el);
						if(isSubmit){
							ajaxCallBack();
						}
					}else{
						showError(el, data['msg']);
					}
					
				}
			});
		}
		return isPass;
	};
	var checkPasswordNew = function(){
		var el = password_new,v = $.trim(el.val()),isPass = false,level,levelStr = '';
		if(v == ''){
			showError(el, '新登陆密码不能为空');
			isPass = false;
		}else if((/\s/).test(v)){
			showError(el, '密码不含空格');
			isPass = false;
		}else if((/^\d{1,8}$/).test(v)){
			showError(el, '密码不能是9位以下纯数字');
			isPass = false;
		}else if(!(/^.{6,20}$/).test(v)){
			showError(el, '长度有误，请输入6-20位字符');
			isPass = false;
		}else if(v == usernamev){
			showError(el, '密码不能和用户名一致');
			isPass = false;
		}else{
			isPass = true;
			level = getPassowrdLevel(v);
			var password_label = $("#password_label");
			password_label.removeAttr("class");

			if(level == 1){
				password_label.attr("class","pwd-new-weak");
				levelStr = '试试字母、数字、标点组合吧';
			}
			if(level == 2){
				password_label.attr("class","pwd-new-weak");
				levelStr = '连续字符密码易被破解，请用多组合的密码';
			}
			if(level == 3){
				password_label.attr("class","pwd-new-weak");
				levelStr = '相同字符密码易被破解，请用多组合的密码';
			}
			if(level == 4){
				password_label.attr("class","pwd-new-middle");
				levelStr = '密码复杂度中等';
			}
			if(level == 5){
				password_label.attr("class","pwd-new-strong");
				levelStr = '密码强度好，请牢记！';
			}
			$("#J-div-pwdstrength").css("display","inline");
			$(el).parent().find('.ui-check').html("").removeClass().addClass('ui-check');
			$('#newcheckString').html(levelStr);
			//showRight(el,levelStr);
		}
		return isPass;
	};
	var checkPasswordNew2 = function(){
		var el = password_new2,v = $.trim(el.val()),isPass = false,level,levelStr = '';
		if(v == ''){
			showError(el, '确认新密码不能为空');
			isPass = false;
		}else if(v != $.trim(password_new.val())){
			showError(el, '两次密码输入不一致');
			isPass = false;
		}else{
			isPass = true;
			showRight(el);
		}
		return isPass;
	};
	
	
	//宝开安全中心验证码
	var checkCodeSafecenter = function(){
		var el = codeSafeCenter,v = $.trim(el.val()),isPass = true,level,levelStr = '';
		if(codeSafeCenter.size() < 1){
			return isPass;
		}
		if(v == ''){
			showError(el, '当前宝开安全中心验证码不能为空');
			isPass = false;
		}
		return isPass;
	};
	codeSafeCenter.focus(function(){
		eventFocus(this, '请输入当前宝开安全中心验证码');
	});
	codeSafeCenter.blur(function(){
		checkCodeSafecenter();
	});
	
	
	
	//登陆密码
	password.focus(function(){
		eventFocus(this, '请输入当前登录密码');
	});
	password.blur(function(){
		checkPassword();
	});
	//新登陆密码
	password_new.focus(function(){
		$("#J-div-pwdstrength").css("display","none");
		eventFocus(this, '6-20位字符组成，区分大小写');
	});
	password_new.blur(function(){
		checkPasswordNew();
	});
	//确认新登陆密码
	password_new2.focus(function(){
		eventFocus(this, '请再次输入登录密码');
	});
	password_new2.blur(function(){
		checkPasswordNew2();
	});
	
	//提交
	$('#J-button-submit-password').click(function(){
		if(checkPassword() && checkPasswordNew() && checkPasswordNew2() && checkCodeSafecenter()){
			/**
			ajaxCallBackList.push(function(){
				loadingNum += 1;
				$('#J-form-password').submit();
				return false;
			});
			checkPassword(true, true);
			**/
			var interval;
			// RSA 加密
			//var password_val = rsa.encrypt($.trim(password.val()));
			//var password_new_val = rsa.encrypt($.trim(password_new.val()));
			//var password_new2_val = rsa.encrypt($.trim(password_new2.val()));
			
			var password_val = $.trim(password.val());
			var password_new_val = $.trim(password_new.val());
			var password_new2_val = $.trim(password_new2.val());
			
			try
			{
				$.ajax({
					url:'/safepersonal/safecodeedit',
					method:'post',
					dataType:'json',
					data:{'password':Base64.encode(password_val), 'password_new':Base64.encode(password_new_val), 'password_new2':Base64.encode(password_new2_val), 'code':$.trim(codeSafeCenter.val()), 'act':1},
					beforeSend:function(jqXHR, settings){
						//RSA加密
						settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
						var button = $('#J-button-submit-text'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
						interval=setInterval(function(){
							button.text(list[i]);
							i += 1;
							if(i >= list.length){
								i = 0;
							}
						}, 300);
					},
					success:function(data){
						//alert("ok");
						clearInterval(interval);
						if(Number(data['isSuccess']) == 1){							
							if(Sys.ie!=null){	fn("Idivs");}
							else{ 
								box.OverLay.Color = "rgb(51, 51, 51)" ; 
								box.Over = true;   
								box.OverLay.Opacity = 50;  
								box.Fixed = true;	 
								box.Center = true; box.Show();	
							}	
						}else{
							maskMsg.show(false, data['msg']);
							setTimeout(function(){
								maskMsg.hide();
								location.href = location.href;
							}, 2000);
						}
					}
				});
			}catch(err)
			{
				clearInterval(interval);
			}
		}
	});
	
	var ajaxCallBackList2 = [];
	var ajaxCallBack2 = function(obj){
		var i = 0,len = ajaxCallBackList2.length;
		if(loadingNum2 > 0){
			return;
		}
		for(;i < len;i++){
			if(ajaxCallBackList2[i].call(obj || null) === false){
				break;
			}
		}
		ajaxCallBackList2 = [];
	};
	//isAjax 是否进行远程校验
	//isSubmit 验证成功是否调用回调列队
	var checkSafePassword = function(isAjax, isSubmit){
		var el = safePassword,v = $.trim(el.val()),isPass = false;
		if(v == ''){
			showError(el, '安全密码不能为空');
			isPass = false;
		}else if(!(/^.{6,20}$/).test(v)){
			showError(el, '长度有误，请输入6-20位字符');
			isPass = false;
		}else{
			hideTip(el);
			isPass = true;
		}
		if(isAjax && isPass){
			$.ajax({
				url:'/safepersonal/safecodeedit',
				dataType:'json',
				method:'post',
				data:{'password':v,'type':'safe'},
				beforeSend:function(){
					showLoading(el);
					loadingNum2 += 1;
				},
				success:function(data){
					hideLoading(el);
					loadingNum2 -= 1;
					if(Number(data['isSuccess']) == 1){
						showRight(el);
						if(isSubmit){
							ajaxCallBack2();
						}
					}else{
						showError(el, data['msg']);
					}
					
				}
			});
		}
		return isPass;
	};
	var checkSafePasswordNew = function(){
		var el = safePassword_new,v = $.trim(el.val()),isPass = false,level,levelStr = '';
		if(v == ''){
			showError(el, '新安全密码不能为空');
			isPass = false;
		}else if((/\s/).test(v)){
			showError(el, '密码不含空格');
			isPass = false;
		}else if((/^\d{1,8}$/).test(v)){
			showError(el, '密码不能是9位以下纯数字');
			isPass = false;
		}else if(!(/^.{6,20}$/).test(v)){
			showError(el, '长度有误，请输入6-20位字符');
			isPass = false;
		}else if(v == usernamev){
			showError(el, '安全密码不能和用户名一致');
			isPass = false;
		}else{
			isPass = true;
			level = getPassowrdLevel(v);
			var password_label1 = $("#password_label1");
			password_label1.removeAttr("class");

			if(level == 1){
				password_label1.attr("class","pwd-new-weak");
				levelStr = '试试字母、数字、标点组合吧';
			}
			if(level == 2){
				password_label1.attr("class","pwd-new-weak");
				levelStr = '连续字符密码易被破解，请用多组合的密码';
			}
			if(level == 3){
				password_label1.attr("class","pwd-new-weak");
				levelStr = '相同字符密码易被破解，请用多组合的密码';
			}
			if(level == 4){
				password_label1.attr("class","pwd-new-middle");
				levelStr = '密码复杂度中等';
			}
			if(level == 5){
				password_label1.attr("class","pwd-new-strong");
				levelStr = '密码强度好，请牢记！';
			}
			$("#J-div-pwdstrength1").css("display","inline");
			$(el).parent().find('.ui-check').html("").removeClass().addClass('ui-check');
			$('#safecheckString').html(levelStr);
			//showRight(el, levelStr);
		}
		return isPass;
	};
	var checkSafePasswordNew2 = function(){
		var el = safePassword_new2,v = $.trim(el.val()),isPass = false,level,levelStr = '';
		if(v == ''){
			showError(el, '确认新安全密码不能为空');
			isPass = false;
		}else if(v != $.trim(safePassword_new.val())){
			showError(el, '两次密码输入不一致');
			isPass = false;
		}else{
			isPass = true;
			showRight(el);
		}
		return isPass;
	};
	//安全密码
	safePassword.focus(function(){
		eventFocus(this, '请输入当前安全密码');
	});
	safePassword.blur(function(){
		checkSafePassword();
	});
	//新安全密码
	safePassword_new.focus(function(){
		$("#J-div-pwdstrength1").css("display","none");
		eventFocus(this, '6-20位字符组成，区分大小写');
	});
	safePassword_new.blur(function(){
		checkSafePasswordNew();
	});
	//确认新登陆密码
	safePassword_new2.focus(function(){
		eventFocus(this, '请再次输入新安全密码');
	});
	safePassword_new2.blur(function(){
		checkSafePasswordNew2();
	});
	//宝开安全中心验证码
	var checkCodeSafecenter2 = function(){
		var el = codeSafeCenter2,v = $.trim(el.val()),isPass = true,level,levelStr = '';
		if(codeSafeCenter2.size() < 1){
			return isPass;
		}
		if(v == ''){
			showError(el, '当前宝开安全中心验证码不能为空');
			isPass = false;
		}
		return isPass;
	};
	codeSafeCenter2.focus(function(){
		eventFocus(this, '请输入当前宝开安全中心验证码');
	});
	codeSafeCenter2.blur(function(){
		checkCodeSafecenter2();
	});
	
	
	//提交
	$('#J-button-submit-safePassword').click(function(){
		if(checkSafePassword() && checkSafePasswordNew() && checkSafePasswordNew2() && checkCodeSafecenter2()){
		    var interval;
			
			//RSA加密
			//var safePassword_val = rsa.encrypt($.trim(safePassword.val()));
			//var safePassword_new_val = rsa.encrypt($.trim(safePassword_new.val()));
			//var safePassword_new2_val = rsa.encrypt($.trim(safePassword_new2.val()));
			
			var safePassword_val = $.trim(safePassword.val());
			var safePassword_new_val = $.trim(safePassword_new.val());
			var safePassword_new2_val = $.trim(safePassword_new2.val());
			
			try
			{
			$.ajax({
				url:'/safepersonal/safecodeedit',
				method:'post',
				dataType:'json',
				data:{'safePassword':Base64.encode(safePassword_val), 'safePassword_new':Base64.encode(safePassword_new_val), 'safePassword_new2':Base64.encode(safePassword_new2_val),'code':$.trim(codeSafeCenter2.val()), 'act':2},
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					var button = $('#J-button-submit-text-2'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
					interval=setInterval(function(){
						button.text(list[i]);
						i += 1;
						if(i >= list.length){
							i = 0;
						}
					}, 300);
				},
				success:function(data){
					//alert('OK2');
					clearInterval(interval);
					if(Number(data['isSuccess']) == 1){
						maskMsg.show(true, data['msg']);
						setTimeout(function(){
							maskMsg.hide();
							//location.href = location.href;
							location.href = '?issafecode=1';
						}, 2000);
					}else{
						maskMsg.show(false, data['msg']);
						setTimeout(function(){
							maskMsg.hide();
							//location.href = location.href;
							location.href = '?issafecode=1';
						}, 2000);
					}
				}
			});
			}catch(err)
			{
				clearInterval(interval);
			}
		}
	});
	
	
	//  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });

	//操作后提示	 
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";						
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";
	} 	
	
	
})(jQuery);
</script>
{/literal}
	
</body>
</html>
