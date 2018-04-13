<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	

<body>
{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
				{include file='/default/ucenter/left.tpl'}
			<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<form action="/withdraw/postdraw/" method="post" id="J-form">
			<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要提现</div>
				<div class="content bank-cash">
					<div class="notice"><i class="ico-warning"></i>提现所产生的银行手续费由平台为您免除。</div>
					<div class="bank-title">提现确认</div>
					<div class="bank-cash-info">
							<div class="bank-more-list" style="width:auto">
								<div class="bank-more-content">
									<table>
										<tbody>
											<tr>
												<td>提现银行卡：</td>
												<td><span class="ico-bank {$aBankCardInfo.bankIco}"></span></td>
												<td>开户人姓名：{$aBankCardInfo.bankAccount}</td>
												<td>银行卡号：{$aBankCardInfo.bankNumber}</td>
											</tr>
											<tr>
												<td>提现金额：</td>
												<td class="highbig color-red">{$changeCount}元</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
					</div>
					
					
					<input type="hidden" id="J-input-id" value="{$aBankCardInfo.id}"/>
					<input type="hidden" id="J-input-changeCount" value="{$changeCount}"/>
					<div class="ui-tab tab-change-pswd">
						<div class="ui-tab-title tab-title-from clearfix">
							<li class="current">通过“安全问题“</li>
							<!-- {if $isBindSecCard eq '1'} -->
							<li>通过“宝开安全中心“</li>
							<!-- {else} -->
							<!-- {/if} -->
						</div>
						
						
						<ul class="ui-form ui-tab-content ui-tab-content-current">
							<li>
								<label class="ui-label" for="name">安全问题：</label>
								<input id="J-input-question-id" type="hidden" name="" value="{$quStruc['id']}" />
								<span class="ui-info">{$quStruc['qus']}</span>
							</li>
							<li>
								<label class="ui-label" for="name">答案：</label>
								<input id="J-input-answer" type="text" class="input w-3" value="">
							<span class="ui-text-prompt" style="display:none;">请输入当前安全安全问题答案</span>
							<span class="ui-check" style="display:none;"><i></i>答案不能为空</span>
							</li>
							<li>
								<label class="ui-label" for="name">安全密码：</label>
								<input id="J-input-safePassword" type="password" class="input w-3" value="">
								<span class="ui-check" style="display:none;"><i></i>安全密码不能为空</span>
							</li>
						<li>
							<label for="name" class="ui-label"></label>
							<span class="ui-info"><a href="/safepersonal/sefecoderetrieve?stp=2">忘记安全密码</a></span>
						</li>
							<li class="ui-btn">
							<input id="J-button-submit" class="btn btn-important" type="button" value="申请提现"/>
							</li>
						</ul>
						<!-- {if $isBindSecCard eq '1'} -->
						<ul class="ui-form ui-tab-content">
							<li>
								<label class="ui-label" for="name">宝开安全中心验证码：</label>
								<input id="J-input-code" type="text" class="input w-2" value="">
								<span class="ui-text-prompt">为了您的账户安全，连续错误5次后当日将被锁定</span>
								<span class="ui-check" style="display:none;"><i></i>安全密码不能为空</span>
								<br/>
								<span class="ui-info" style="margin-left:120px;"><a href="#">手机遗失或忘记启动密码</a>
								{foreach from=$aToken item=data}
								{$data} - 
								{/foreach}
								</span>
							</li>
							<li>
								<label class="ui-label" for="name">安全密码：</label>
								<input id="J-safePassword-mobile" type="password" class="input w-3" value="">
								<span class="ui-text-prompt">请输入您的安全密码(6-20)位</span>
								<span class="ui-check" style="display:none;"><i></i>安全密码不能为空</span>
								<br/>
								<span class="ui-info" style="margin-left:120px;"><a href="/safepersonal/sefecoderetrieve?stp=2">忘记安全密码</a></span>
							</li>
							<li class="ui-btn">
							<input id="J-button-submit-mobile" class="btn btn-important" type="button" value="申请提现"/>
							</li>
						</ul>	
						<!-- {/if} -->
					</div>
				</div>
			</div>
		  </div>
		</form>
	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
<script>
var isBindCard = {$isBindSecCard|default:0};
</script>
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
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	if(isBindCard == 1){
		var tab = new phoenix.Tab({triggers:'.ui-tab-title li',panels:'.ui-tab-content',eventType:'click',currPanelClass:'ui-tab-content-current'});
	}
		var minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
		mask = phoenix.Mask.getInstance(),
		answer = $('#J-input-answer'),
		safePassword = $('#J-input-safePassword'),
		code = $('#J-input-code'),
		safepasswordMb = $('#J-safePassword-mobile');

	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
     var checkFn = function(){
			var me = this;
			//先把非数字的都替换掉，除了数字和.
		  	me.value = me.value.replace(/[^\d]/g,"");
	};
	code.keyup(checkFn);
	code.blur(checkFn);

	
	//安全问题答案
	var checkAnswer = function(){
		var el = answer,v = $.trim(answer.val()),isPass = true;
		if(v == ''){
			isPass = false;
			el.parent().find('.ui-check').html('<i></i>安全问题不能为空').show();
		}
		if(isPass){
			el.parent().find('.ui-check').hide();
		}
		return isPass;
	};
	answer.blur(function(){
		checkAnswer();
	});
	
	
	//安全密码
	var checkSafePassword = function(){
		var el = safePassword,v = $.trim(el.val()),isPass = true,par = el.parent(),error = par.find('.ui-check');
		if(v == ''){
			error.html('<i></i>安全密码不能为空').css('display', 'inline-block');
			isPass = false;
		}
		if(isPass){
			error.hide();
		}
		return isPass;
	};
	safePassword.focus(function(){
		var el = safePassword,par = el.parent(),tip = par.find('.ui-text-prompt'),error = par.find('.ui-check');
		error.hide();
		tip.show();
	});
	safePassword.blur(function(){
		var el = safePassword,par = el.parent(),tip = par.find('.ui-text-prompt'),error = par.find('.ui-check');
		checkSafePassword();
		tip.hide();
	});
	
	
	//通过安全问题提交
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		if(checkAnswer() && checkSafePassword()){
			var answerv = $.trim(answer.val()),
				passowrdv = $.trim(safePassword.val()),
				questionid = $.trim($('#J-input-question-id').val()),
				id = $.trim($('#J-input-id').val()),
				changeCount = $.trim($('#J-input-changeCount').val());
				
			$.ajax({
				url:'/withdraw/postdraw/',
				dataType:'json',
				cache:false,
				method:'POST',
				data:{type:1,answer:answerv, passowrd:Base64.encode(passowrdv), questionid:questionid,changeCount:changeCount,id:id},
				beforeSend:function(jqXHR, settings){
					//RSA加密
                    settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					//禁用发送								
					var button = $('#J-button-submit'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
					interval=setInterval(function(){
						button.val(list[i]);
						i += 1;
						if(i >= list.length){
							i = 0;
						}
					}, 300);
					button.attr("disabled","disabled");	
				},
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						location.href = '/withdraw/displayresult/?data='+data['msg'];
					}else if(Number(data['isSuccess']) == -1) {
						location.href = '/withdraw/';
					}else{
						minWindow.setTitle('温馨提示');
						minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">'+ data['msg'] +'</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
						minWindow.show();
					}
				},
				complete: function(){
					clearInterval(interval);
					var button = $('#J-button-submit');
					button.val("申请提现");	 	
					button.removeAttr("disabled","disabled");
				}
			});
		}
	});
	
	
	
	//安全中心的验证码
	var checkCode = function(){
		var el = code,v = $.trim(el.val()),isPass = true,par = el.parent(),error = par.find('.ui-check');
		if(v == ''){
			error.html('<i></i>验证码不能为空').css('display', 'inline-block');
			isPass = false;
		}
		if(isPass){
			error.hide();
		}
		return isPass;
	};
	code.focus(function(){
		var el = code,par = el.parent(),tip = par.find('.ui-text-prompt'),error = par.find('.ui-check');
		error.hide();
		tip.show();
	});
	code.blur(function(){
		var el = code,par = el.parent(),error = par.find('.ui-check');
		checkCode();
	});
	//安全中心的安全密码
	var checkSafepasswordMb = function(){
		var el = safepasswordMb,v = $.trim(el.val()),isPass = true,par = el.parent(),error = par.find('.ui-check');
		if(v == ''){
			error.html('<i></i>安全密码不能为空').css('display', 'inline-block');
			isPass = false;
		}
		if(isPass){
			error.hide();
		}
		return isPass;
	};
	safepasswordMb.focus(function(){
		var el = safepasswordMb,par = el.parent(),tip = par.find('.ui-text-prompt'),error = par.find('.ui-check');
		error.hide();
		tip.show();
	});
	safepasswordMb.blur(function(){
		var el = safepasswordMb,par = el.parent(),tip = par.find('.ui-text-prompt'),error = par.find('.ui-check');
		checkSafepasswordMb();
		tip.hide();
	});
	
	//通过宝开安全中心提交
	$('#J-button-submit-mobile').click(function(e){
		e.preventDefault();
		if(checkCode() && checkSafepasswordMb()){
			var codev = $.trim(code.val()),
				passowrdv = $.trim(safepasswordMb.val()),
				id = $.trim($('#J-input-id').val()),
				changeCount = $.trim($('#J-input-changeCount').val());
			$.ajax({
				url:'/withdraw/postdraw/',
				dataType:'json',
				cache:false,
				method:'POST',
				data:{tpye:2,code:codev, passowrd:Base64.encode(passowrdv),changeCount:changeCount,id:id},
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					//禁用发送								
					var button = $('#J-button-submit-mobile'),list = ['提交中   ', '提交中.  ', '提交中.. ', '提交中...'],i = 0;
					interval=setInterval(function(){
						button.val(list[i]);
						i += 1;
						if(i >= list.length){
							i = 0;
						}
					}, 300);
					button.attr("disabled","disabled");	
				},
				success:function(data){
					if(Number(data['isSuccess']) == 1){
						location.href = '/withdraw/displayresult/?data='+data['msg'];
					}else if(Number(data['isSuccess']) == -1) {
						location.href = '/withdraw/';
					}else{
						minWindow.setTitle('温馨提示');
						minWindow.setContent('<div class="pop-title"><i class="ico-success"></i><h4 class="pop-text" style="color:#F00;">'+ data['msg'] +'</h4></div><div class="pop-btn"><a class="btn closeBtn" href="#">确定<b class="btn-inner"></b></a></div>');
						minWindow.show();
					}
				},
				complete: function(){
					clearInterval(interval);
					var button = $('#J-button-submit-mobile');
					button.val("申请提现");	 	
					button.removeAttr("disabled","disabled");
				}
			});
		}
	});
	
	
		
		
})(jQuery);
</script>
{/literal}
	
</body>
</html>
