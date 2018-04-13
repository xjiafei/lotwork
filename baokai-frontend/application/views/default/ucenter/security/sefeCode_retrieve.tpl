<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"找回安全密码"}</title>
	<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
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
			<div class="common-content">
				
				{if $stp eq 1}
    				<div class="title">找回安全密码</div>
    				<div class="content">
    					<!-- start -->
    					<div class="alert alert-error">
    						<i></i>
    						<div class="txt">
    							<h4>抱歉，您不能自助找回安全密码。</h4>
    							<p>找回安全密码需要同时绑定安全问题和邮箱<br /><a href="/safepersonal/safecenter/" class="btn btn-small">返回安全中心<b class="btn-inner"></b></a></p>
    						</div>
    					</div>
    					<!-- end -->
						
						<div style="text-align:center;">
						上面的方式都不可用？您还可以通过<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>进行人工申诉找回安全密码。
						</div>
						
    				</div>
				
				{else if $stp eq 2}
    				<div class="title">找回安全密码</div>
    				<div class="content">
    					<div class="step">
    						<table class="step-table">
    							<tbody>
    								<tr>
    									<td class="current"><div class="con">验证安全问题</div></td>
    									<td><div class="tri"><div class="con">验证邮箱</div></div></td>
    									<td><div class="tri"><div class="con">重置密码</div></div></td>
    									<td><div class="tri"><div class="con">完成</div></div></td>
    								</tr>
    							</tbody>
    						</table>
    					</div>
						<ul class="ui-form set-safeissue" id="J-panel-stp2">
							<li>
								<label class="ui-label">问题一：</label>
								<select id="J-question1" name="questId" class="ui-select" data-sort="一">
									<option value="">请选择安全问题一</option>
									{foreach from=$questList item=item}
										<option value="{$item.Id}">{$item.quest}</option>
									{/foreach}
								</select>
								<div class="ui-check"><i class="error"></i>请选择安全问题一</div>
							</li>
							<li>
								<label for="answer1" class="ui-label">答案：</label>
								<input type="text" name="questAns" value="" id="J-answer1" class="input">
								<div class="ui-prompt" style="display:none">请输入答案</div>
								<div class="ui-check"><i class="error"></i>请输入答案</div>
							</li>
							<li>
								<label class="ui-label">问题二：</label>
								<select id="J-question1" name="questId2" class="ui-select" data-sort="二">
									<option value="">请选择安全问题二</option>
									{foreach from=$questList item=item}
										<option value="{$item.Id}">{$item.quest}</option>
									{/foreach}
								</select>
								<div class="ui-check"><i class="error"></i>请选择安全问题二</div>
							</li>
							<li>
								<label for="answer2" class="ui-label">答案：</label>
								<input type="text" value="" name="questAns2" id="J-answer2" class="input">
								<div class="ui-prompt" style="display:none">请输入答案</div>
								<div class="ui-check"><i class="error"></i>请输入答案</div>
							</li>
							<li class="ui-btn"><a class="btn" href="#" id="J-button-step1">下一步<b class="btn-inner"></b></a></li>
						</ul>
    					<!-- ucenter-safe-setting-firsttime end -->
    				</div>
                    <div class="content">忘记安全问题？您可以通过<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>进行人工申诉找回安全问题。</div>
					
					
{literal}
<script>
(function(){
{/literal}
	//RSA 加密
	var rsa = new RSAKey();
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
{literal}
	var minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
	mask = phoenix.Mask.getInstance();
	var panel = $('#J-panel-stp2'),selects = panel.find('.ui-select'),allOpts = selects.eq(0).find('option'),selValues = [],
		answer1 = $('#J-answer1'),
		answer2 = $('#J-answer2'),
		v_answer1,
		v_answer2,
		answerSuccess,
		answerError,
		vGroup,
		reBuildOptions = function(sel, v){
			var sels = selects.not(sel),_sel,_v,oldSelValue,arrStr;

			sels.each(function(){
				_sel = $(this);
				oldSelValue = _sel.val();
				arrStr = ['<option value="">请选择安全问题'+ _sel.attr('data-sort') +'</option>'];
				allOpts.each(function(i){
					if(i > 0){
						_v = this.getAttribute('value');
						if($.inArray(_v, selValues) < 0 || _sel.val() == _v){
							arrStr.push('<option value="'+ _v +'">'+ $.trim(this.innerHTML) +'</option>');
						}
					}
				});
				_sel.html(arrStr.join(''));
				_sel.val(oldSelValue);
			});
		};
	
	//安全问题校验
	answerSuccess = function(msg){
		$('.ui-check', this.dom.parent()).css('display', 'none');
	};
	answerError = function(msg){
		$('.ui-check', this.dom.parent()).html('<i class="error"></i>' + msg).css('display', 'inline');
	};
	v_answer1 = new phoenix.Validator({el:answer1,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
	v_answer2 = new phoenix.Validator({el:answer2,type:'safeAnswer',expands:{showSuccessMessage:answerSuccess,showErrorMessage:answerError}});
	
	selects.change(function(){
		var me = this,v = $.trim(me.value);
		if(v == ''){
			$('.ui-check', me.parentNode).css('display', 'inline');
		}else{
			$('.ui-check', me.parentNode).hide();
		}
		selValues = [];
		selects.each(function(){
			selValues.push(this.value);
		});
		reBuildOptions(me, v);
	});

	vGroup = [];
	vGroup.push(v_answer1);
	vGroup.push(v_answer2);
	$('#J-button-step1').click(function(e){
		var passNum = 0;
		e.preventDefault();
		$.each(vGroup, function(){
			this.validate();
			if(this.validated){
				passNum++;
			}
		});
		selects.each(function(){
			var me = $(this);
			if(me.val() != ''){
				passNum++;
				$('.ui-check', me.parent()).hide();
			}else{
				$('.ui-check', me.parent()).css('display', 'inline');
			}
		});
		if(passNum >= (vGroup.length + selects.size())){
			$.ajax({
				url:'?stp=2&act=1',
				dataType:'json',
				method:'POST',
				data:{questId:selects.get(0).value,questId2:selects.get(1).value,questAns:v_answer1.getValue(),questAns2:v_answer2.getValue()},
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
				},
				success:function(data){
					if(data['isSuccess']){
						location.href = '?stp=3';
					}else{
						$.each(data['errors'], function(){
							var err = this;
							if(err[0] == 'ansError'){
								v_answer1.showErrorMessage(err[1]);
							}else if(err[0] == 'ansError1'){
								v_answer2.showErrorMessage(err[1]);
							} else {
								minWindow.setTitle('温馨提示');
								minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">'+ err[1] +'</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
								minWindow.show();
							}
						});
					}
				}
			});
		}else{
			return false;
		}
	});

	answer1.focus(function(){	
		answer1.parent().find('.ui-check').css('display', 'none');
		answer1.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		answer1.parent().find('.ui-prompt').css('display', 'none');
	});
	answer2.focus(function(){	
		answer2.parent().find('.ui-check').css('display', 'none');
		answer2.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		answer2.parent().find('.ui-prompt').css('display', 'none');
	});

	
	
})();
</script>
{/literal}	

					
					
				{else if $stp eq 3}
				
					<div class="title">找回安全密码</div>
    				<div class="content">
    					<div class="step">
    						<table class="step-table">
    							<tbody>
    								<tr>
    									<td class="clicked"><div class="con">验证安全问题</div></td>
    									<td class="current"><div class="tri"><div class="con">验证邮箱</div></div></td>
    									<td><div class="tri"><div class="con">重置密码</div></div></td>
    									<td><div class="tri"><div class="con">完成</div></div></td>
    								</tr>
    							</tbody>
    						</table>
    					</div>
    					<ul class="ui-form">
    						<li class="ui-text"><strong class="highbig">安全密码找回邮件已发送到您的邮箱：</strong><strong class="biglight">{$mail}</strong><br>请找到来自宝开彩票的验证邮件，打开邮件后点击链接找回安全密码。<br>您的激活链接在24小时内有效。</li>
    						<li class="ui-text">没有收到邮件？ <a href="/safepersonal/sefecoderetrieve?stp=2">返回</a></li>
    						<li class="ui-btn"><a class="btn" id="J-button-resubmit" href="#" style="color:#CCC;">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);"><span id="J-time">60</span>s<b class="btn-inner"></b></a></li>
    					</ul>
    					<dl class="help-text">
    						<dt>如果半小时内没有收到邮件</dt>
    						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
    						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
    					</dl>
    				</div>
    				{literal}
				<script>
				(function($){
					var button = $('#J-button-resubmit'),dom = $('#J-time'),timeCase,sc = 60,fn;
					fn = function(){
						if(sc <= 0){
							dom.html(sc--).parent().hide();
							button.css('color', '#333');
							timeCase.remove(fn);
						}else{
							dom.html(sc--);
						}
					};
					timeCase = phoenix.Timer({time:1000,fn:fn});
					button.click(function(e){
						e.preventDefault();
						if(sc > 0){
							return false;
						}
						location.href="/safepersonal/sefecoderetrieve?stp=3";
					});
					
				})(jQuery);
				</script>
				{/literal}
				{else if $stp eq 4}
					<div class="title">找回安全密码</div>
    				<div class="content">
    					<!-- start -->
    					<div class="alert alert-error">
    						<i></i>
    						<div class="txt">
    							<h4>找回密码失败，邮箱激活链接已失效</h4>
    							<p><a href="/safepersonal/safecenter/" class="btn btn-small">返回安全中心<b class="btn-inner"></b></a></p>
    						</div>
    					</div>
    					<!-- end -->
    				</div>
				{else if $stp eq 5}
					<div class="title">找回安全密码</div>
    				<div class="content">
    					<div class="step">
    						<table class="step-table">
    							<tbody>
    								<tr>
    									<td class="clicked"><div class="con">验证安全问题</div></td>
    									<td class="clicked"><div class="tri"><div class="con">验证邮箱</div></div></td>
    									<td class="current"><div class="tri"><div class="con">重置密码</div></div></td>
    									<td><div class="tri"><div class="con">完成</div></div></td>
    								</tr>
    							</tbody>
    						</table>
    					</div>
						
						<form id="J-form-password" name="fm_stp" method="post" action="?stp=5">
							<ul class="ui-form">
								<li class="ui-text"><strong class="highbig">设置新的安全密码</strong><br />您申请了找回安全密码，为保护您的账号安全，您的当前登录安全密码系统已随机重置，请立即修改为您常用的新的密码。</li>
								<li>
									<label for="name" class="ui-label">请输入新的密码：</label>
									<input type="password" onpaste="return false" value="" id="J-password-new" class="input" maxlength="20">
                                    <div class="ui-prompt" style="display:none">6-20位字符组成，区分大小写</div>
                                    <div class="ui-check"><i class="error"></i>长度有误，请输入6-20位字符</div>
                                    <div class="pwd-new-strength" id="J-div-pwdstrength" style="display: none; margin-left: 20px;">密码强度：弱
                                	<span class="pwd-new-bar"><b id="password_label" class="pwd-new-weak"></b></span>强<span id="newcheckString"></span></div>
								</li>
								<li>
									<label for="pwd" class="ui-label">再次输入新密码：</label>
									<input type="password" onpaste="return false" value="" id="J-password-new2" class="input"  maxlength="20">
                                    <span class="ui-check-right"></span>
                                    <div class="ui-prompt" style="display:none"></i>请再次输入安全密码</div>
									<div class="ui-check"><i class="error"></i>两次输入的密码不一致</div>
								</li>
								<li class="ui-btn"><a id="J-button-submit-password" href="#" class="btn">确 定<b class="btn-inner"></b></a></li>
							</ul>
						</form>
{literal}
<script>
(function($){
	//base64 加密
	var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};

{/literal}
	//RSA 加密
	var rsa = new RSAKey();
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
{literal}
	//输入框
	var password_new = $('#J-password-new'),
	password_new2 = $('#J-password-new2'),
	v_password_new,
	v_password_new2,
	showSuccessMessage,
	showErrorMessage,
	check_passswd = false,
	//按钮
	button_password = $('#J-button-submit-password'),
	//表单
	form_password = $('#J-form-password'),
	vGroup_password = [],


	//登陆密码校验
	showSuccessMessage = function(msg){
		var me = this;
		$('.ui-check', me.dom.parent()).hide();
		$('.ui-check-right', me.dom.parent()).show();
		//长度限制
		if(me.dom.get(0).length <6 || me.dom.get(0).length >20 ){			
			v_password_new.validated = false;
			$('.ui-check', me.dom.parent()).css('display', 'inline');		
		}
		if(password_new2.get(0).length <6 || password_new2.get(0).length >20 ){			
			v_password_new2.validated = false;
			$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');		
		}
		if((/\s/).test(me.dom.get(0).value) && me != v_password_new2){
			check_passswd = false;
			$('.ui-check', me.dom.parent()).html('<i class="error"></i>密码不含空格');
			$('.ui-check', me.dom.parent()).css('display', 'inline');
			return;
		}
		if((/^\d{1,8}$/).test(me.dom.get(0).value) && me != v_password_new2){
			check_passswd = false;
			$('.ui-check', me.dom.parent()).html('<i class="error"></i>密码不能是9位以下纯数字');
			$('.ui-check', me.dom.parent()).css('display', 'inline');
			return;
		}
		if(me != v_password_new2){
			check_passswd = true;
		}
		//登录密码两次新密码不一致
		if(me.dom.get(0) == password_new2.get(0)){
			if(v_password_new2.getValue() != v_password_new.getValue()){
				v_password_new2.validated = false;
				$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
				$('.ui-check-right', v_password_new2.dom.parent()).hide();
			}else{
				v_password_new2.validated = true;
				//$('.ui-check', v_password_new.dom.parent()).hide();
				$('.ui-check', v_password_new2.dom.parent()).hide();
			}
		}
		if(me.dom.get(0) == password_new.get(0)){
			if(v_password_new2.getValue() != v_password_new.getValue() && v_password_new2.getValue() != ''){
				v_password_new2.validated = false;
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
				$('.ui-check-right', v_password_new2.dom.parent()).hide();
			}else{
				v_password_new2.validated = true;
				$('.ui-check', v_password_new.dom.parent()).hide();
				$('.ui-check', v_password_new2.dom.parent()).hide();
			}
		}
	};
	showErrorMessage = function(msg){
		var me = this;

		if(me.dom.get(0) == password_new.get(0) && v_password_new.getValue() == '')
		{
		    $('.ui-check', me.dom.parent()).html('<i class="error"></i>请输入新的密码');
		}else if(me.dom.get(0) == password_new.get(0))
		{
			 $('.ui-check', me.dom.parent()).html('<i class="error"></i>'+msg);
		}

		if(me.dom.get(0) == password_new2.get(0)){
			if(v_password_new2.getValue() != v_password_new.getValue()){
				v_password_new2.validated = false;
				$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
				$('.ui-check-right', v_password_new2.dom.parent()).hide();
			}
		}
		$('.ui-check', me.dom.parent()).css('display', 'inline');
		$('.ui-check-right', me.dom.parent()).hide();
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
	
	v_password_new = new phoenix.Validator({el:password_new,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_password_new2 = new phoenix.Validator({el:password_new2,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	password_new.blur(function(){
		$('.ui-check-right', v_password_new.dom.parent()).hide();
		$('.ui-check-right', v_password_new2.dom.parent()).hide();
		var level = 1,levelStr = '',v = $.trim(password_new.val());
		if(v_password_new.validated)
		{
		$.ajax({
					url:'/safepersonal/checkpwd/',
					data:{pwd:Base64.encode(v_password_new.getValue())},
					dataType:'json',
					method:'POST',
					beforeSend:function(jqXHR, settings){
						//RSA加密
						settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					},
					success:function(data){
						if(data['isSuccess']){
							v_password_new.validate();
							if(v_password_new.validated){
								if(check_passswd){
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
								$('#newcheckString').html(levelStr);
								}
							}
						} else {
							$('.ui-check-right', v_password_new.dom.parent()).hide();
							$('.ui-check-right', v_password_new2.dom.parent()).hide();
							$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>安全密码不能和登陆密码一致');
							$('.ui-check', v_password_new.dom.parent()).css('display', 'inline');
						}
					}
				});
		}
	});
	
	//表单提交
	vGroup_password.push(v_password_new);
	vGroup_password.push(v_password_new2);
	//登录密码
	button_password.click(function(e){
		var passNum = 0;
		e.preventDefault();
		$.each(vGroup_password, function(){
			var me = this;
			me.validate();
			if(me.validated){
				passNum++;
			}else
			{
				return false;
			}
		});
		if(passNum >= vGroup_password.length){
			if(v_password_new2.getValue() != v_password_new.getValue()){
				$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
				$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
			}else{
				if(check_passswd){
					$.ajax({
					url:'?stp=5&act=reset',
					data:{pwd:Base64.encode(v_password_new.getValue()),pwd1:Base64.encode(v_password_new2.getValue())},
					dataType:'json',
					method:'POST',
					beforeSend:function(jqXHR, settings){
						//RSA加密
						settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					},
					success:function(data){
						if(data['isSuccess']){
							location.href = '?stp=6';
						} else if(data['error'] == '0'){
							$('.ui-check', v_password_new2.dom.parent()).html('<i class="error"></i>两次输入的密码不一致');
							$('.ui-check', v_password_new2.dom.parent()).css('display', 'inline');
						} else {
							$('.ui-check-right', v_password_new.dom.parent()).hide();
							$('.ui-check-right', v_password_new2.dom.parent()).hide();
							$('.ui-check', v_password_new.dom.parent()).html('<i class="error"></i>安全密码不能和登陆密码一致');
							$('.ui-check', v_password_new.dom.parent()).css('display', 'inline');
						}
					}
					});
				}else{
					return false;
				}
			}
		}else{
			return false;
		}
	});

	password_new.focus(function(){	
		$("#J-div-pwdstrength").css("display","none");
		password_new.parent().find('.ui-check').css('display', 'none');
		password_new.parent().find('.ui-check-right').css('display', 'none');
		password_new.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		password_new.parent().find('.ui-prompt').css('display', 'none');
	});
	password_new2.focus(function(){	
		password_new2.parent().find('.ui-check').css('display', 'none');
		password_new2.parent().find('.ui-check-right').css('display', 'none');
		password_new2.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		password_new2.parent().find('.ui-prompt').css('display', 'none');
	});
	
})(jQuery);
</script>
{/literal}
						
						
						
						
    					<dl class="help-text">
    						<dt>如果半小时内没有收到邮件</dt>
    						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
    						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
    					</dl>
    				</div>
				{else if $stp eq 6}
					<div class="title">找回安全密码</div>
    				<div class="content">
    					<!-- start -->
    					<div class="alert alert-success">
    						<i></i>
    						<div class="txt">
    							<h4>恭喜您安全密码重置成功！</h4>
    						</div>
    					</div>
    					<!-- end -->
    				</div>
				{else if $stp eq 6}
				{/if}
			</div>
		</div>
	</div>
	
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
	
	
</body>
</html>
