<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>银行卡绑定</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/funds/funds.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/tooltip.css" />
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
		<form  method="post" id="J-form" action="/bindcard/bindcardadd">		
		<div class="g_28 g_last">
			<div class="common-content bank-card-management ">
				<div class="title">
				{if {$opeType} != 'update'}
				支付宝绑定
				{else}
				修改支付宝绑定
				{/if}
				</div>
				<div class="content">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="current"><div class="con">1.输入支付宝信息</div></td>
									<td><div class="tri"><div class="con">2.绑定成功</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<ul class="ui-form">
						
						<li>
							<label for="name" class="ui-label">支付宝账户名：</label>
							<input type="text" value='{$bankNumber}' class="input w-4" maxlength="35" name="bankNumber" id="bankNumber" autocomplete="off" placeholder="手机/邮箱">
                           	<span class="ui-check"></span>
						</li>
						<li>
							<label for="name" class="ui-label">绑定支付宝姓名：</label>
							<input type="text" value='{$bankAccount}' class="input w-4" maxlength="30" name="bankAccount" id="bankAccount" autocomplete="off">
							<span class="ui-check"></span>
						</li>
						<li>
							<label for="name" class="ui-label">支付宝昵称：</label>
							<input type="text" value='{$nickName}' class="input w-4" maxlength="30" name="nickName" id="nickName" autocomplete="off">
							<span class="ui-check"></span>
						</li>
						{if {$opeType} != 'update'}
						<li>
							<label for="name" class="ui-label">安全密码：</label>
							<input type="password" value="" class="input w-4" name="securityPassword" id="securityPassword" autocomplete="off">
                            <span class="ui-check"></span>
						</li>
						{/if}
						<li class="ui-btn"><a href="javascript:void(0);" class="btn btn-important" id="J-Submit">提 交<b class="btn-inner"></b></a>
						&nbsp&nbsp <a href="/help/goIndex">如何绑定支付宝？</a>
						</li>
						 <input type="hidden" id="opeType" name="opeType" value='{$opeType}'/>
						 <input type="hidden" id="id" name="id" value='{$id}'/>
						 <input type="hidden" id="path_img" name="path_img" value='{$path_img}'/>
			
					</ul>
					<dl class="prompt">
						<dt>提示：</dt>
						<dd>1、请绑定正确的支付宝账户名，若您的绑定信息有误，将会影响您的充值无法到账</dd>
						<dd>2、若您的支付宝有设置过昵称，请在平台绑定支付宝信息时一定要保存昵称，若您未设置，可以不用填写。请切记将您的支付宝昵称信息与平台保存信息一致，否者将会影响充值到账。</dd>
						<dd>3、若您有修改过支付宝昵称，请您在进行充值之前先修改平台绑定昵称信息资料。</dd>
					</dl>
				</div>
			</div>
		</div>
	</form>
	</div>  
      <div class="pop pop-search" id="div_no" style="position:absolute;z-index:2; display:none">  
		<div class="hd"><i class="close" id="CloseDiv"></i>银行卡绑定提醒</div>
		<div class="bd">
			<div class="pop-error">
				<i class="ico-error"></i>
				<h4 class="pop-text"><span id="errortype"></span></h4>
			</div>
			<div class="pop-btn">
				<a class="btn btn-important " id="reType">重新輸入<b class="btn-inner"></b></a>
<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank" class="ui-text-info">联系客服</a>			
</div>
		</div>
	</div>
	
	
    <div class="pop w-10" id="div_ok" style="position:absolute;left:600px;display:none">  
		<div class="hd"><i class="close" id="CloseDiv2"></i>银行卡绑定提醒</div>
		<div class="bd">
			<div class="pop-success">
				<i class="ico-success"></i>
				<h4 class="pop-text">恭喜您支付宝绑定成功！</h4>
			</div>
            <div class="bank-cash">
					<div class="bank-more-content">
						<table>
							<tbody>
								<tr>
									<td>为了您的充值可以顺利到账，请您务必将个人支付宝信息保存资料与平台绑定信息一致，谢谢！您现在可以操作</td>
								</tr>
								
							</tbody>
						</table>
					</div>
			</div>   

			<div class="pop-btn">
				<a class="btn btn-important " href="/fund/index?type=4">我要充值<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
	

   
    
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script src="{$path_js}/js/phoenix.area.js"></script> 
<script  src="{$path_js}/js/phoenix.Common.js"></script>
<script  src="{$path_js}/js/phoenix.Verification.js"></script>
<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
<script src="{$path_js}/js/userCenter/fundCharge.js" type="text/javascript" ></script>
{literal}
<script>
(function(){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	
	function fn(obj){
		var Idivdok = document.getElementById(obj);	
		Idivdok.style.display="block";						
		Idivdok.style.left=(document.documentElement.clientWidth-Idivdok.clientWidth)/2+document.documentElement.scrollLeft+"px";			
		Idivdok.style.top=(document.documentElement.clientHeight-Idivdok.clientHeight)/2+document.documentElement.scrollTop-40+"px";		
	} 	
	
	
	var form = $('#J-form'),bankAccount,bankAccountPar,bankNumber,bankNumberPar,
		securityPassword,securityPasswordPar,isture="0",box = new LightBox("div_no"),box1 = new LightBox("div_ok");
	
	//公用方法提示文字控制
	var staticPrompt=function(objPar,obj){	
		objPar.find('.ui-check').css('display', 'inline');	
		objPar.find('.ui-prompt').css('display', 'none');
		setErrorNum(obj, 1);		
	}		
	var staticPromptDe=function(objPar,obj){			
		objPar.find('.ui-check').css('display', 'none');
		setErrorNum(obj, -1);	
	}	
	//集焦样式提示
	var foucePrompte=function(obj){
		obj.find('.ui-check').css('display', 'none');
		obj.find('.ui-prompt').css('display', 'inline');
	}

	
	//支付宝账户名
	bankNumber = $('#bankNumber');
	bankNumberPar = bankNumber.parent();	
	//绑定支付宝姓名
	bankAccount = $('#bankAccount');
	bankAccountPar = bankAccount.parent();	
	//支付宝昵称
	nickName = $('#nickName');
	nickNamePar = nickName.parent();	
 	//安全密码验证
	securityPassword = $('#securityPassword');
	securityPasswordPar = securityPassword.parent();	
	securityPassword.blur(function(){
		var v = $.trim(this.value);			
		if( v=='' || v.length < 6 || v.length > 20 ){	
		//staticPrompt(securityPasswordPar,'securityPassword');
		securityPasswordPar.find('.ui-check').html(
		"<i class='error'></i>请输入您的安全密码 <a href='/safepersonal/sefecoderetrieve?stp=2' target='_blank'>忘记安全密码</a>")
		.removeClass().addClass('ui-check').show();
		
		setErrorNum('securityPassword', 1);
								
		}
		else{	staticPromptDe(securityPasswordPar,'securityPassword');	}			
	}).focus(function(){
		foucePrompte(securityPasswordPar);
		
	});	
	
	
	$("#securityPassword").keypress(function (e) {
		var currKey = 0, e = e || event;
		currKey = e.keyCode || e.which || e.charCode;
		if (currKey == 13) {		
			Putsubmit();
		}
	});
	
	//表单检测
	checkform = function(){
		var bankNum = $.trim($('#bankNumber').val());
	
		
		if(bankNum==''){
		    bankNumberPar.find('.ui-check').html("<i class='error'></i>支付宝账户名不可为空");
		    bankNumberPar.find('.ui-check').css('display', 'inline');	
		    bankNumberPar.find('.ui-prompt').css('display', 'none');
		  	staticPrompt(bankNumberPar,'bankNum');
		}
		
		
		if($.trim($('#bankAccount').val())=='' )
		{	staticPrompt(bankAccountPar,'bankAccount');
		}			
		
		
			
		
		if($.trim($('#securityPassword').val())=='' || $.trim($('#securityPassword').val()).length < 6 || $.trim($('#securityPassword').val()).length > 20)
		{	
			securityPasswordPar.find('.ui-check').html(
			"<i class='error'></i>请输入您的安全密码 <a href='/safepersonal/sefecoderetrieve?stp=2' target='_blank'>忘记安全密码</a>")
			.removeClass().addClass('ui-check').show();
		    staticPrompt(securityPasswordPar,'securityPassword');	
		}				
	}	
	
	//表单提交校验
	$('#J-Submit').click(function(){	
		if($('#opeType').val() == 'update'){
			updateSubmit();
		}else{
			Putsubmit();
		}
		
		//$("#J-form").submit();
	});	
	
	function Putsubmit(){
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};

		var err = 0;
		checkform();
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});	
		if(err > 0){		
			return false;
		}
		
		var datas="bankAccount="+$.trim($('#bankAccount').val())+"&securityPassword="+Base64.encode($.trim($('#securityPassword').val()))+"&bankNumber="+$.trim($('#bankNumber').val())+"&nickName="+$.trim($('#nickName').val())+"&bindcardType=1&bankid=30";

		$.ajax({			
				type:'post',
				dataType:'json',
				cache:false,
				url:'/bindcard/bindcardalipayadd',
				data:datas,
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					ShowTipDiv();
				},
				
				success:function(data){
					if(data['status']=="ok"){	
							$("#new_bankAccount").html(data['data']['bankAccount']);
							$("#new_bankCardId").html(data['data']['bankNumber']);
							box1.OverLay.Color = "rgb(51, 51, 51)" ; 
							box1.Over = true;   
							box1.OverLay.Opacity = 50;  
							box1.Fixed = true;	 
							box1.Center = true; 
							box1.Show();				
					}else{						
						box.OverLay.Color = "rgb(51, 51, 51)" ; 
						box.Over = true;   
						box.OverLay.Opacity = 50;  
						box.Fixed = true;	 
						box.Center = true; 
						box.Show();	
						$('#errortype').html(data['errorkey']);
					}
				},
				complete:function()	{					
					HideTipDiv();
				}
				
			});
	}	
	
	
	function updateSubmit(){
		
		var err = 0;
	
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});	

		if(err > 0){		
			return false;
		}
		
		var datas="bankAccount="+$.trim($('#bankAccount').val())+"&bankNumber="+$.trim($('#bankNumber').val())+"&nickName="+$.trim($('#nickName').val())+"&bindcardType=1&bankid=30&opeType="+$('#opeType').val()+"&id="+$('#id').val();

		$.ajax({			
				type:'post',
				dataType:'json',
				cache:false,
				url:'/bindcard/bindcardalipayadd',
				data:datas,
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					ShowTipDiv();
				},
				
				success:function(data){
					if(data['status']=="ok"){	
							$("#new_bankAccount").html(data['data']['bankAccount']);
							$("#new_bankCardId").html(data['data']['bankNumber']);
							box1.OverLay.Color = "rgb(51, 51, 51)" ; 
							box1.Over = true;   
							box1.OverLay.Opacity = 50;  
							box1.Fixed = true;	 
							box1.Center = true; 
							box1.Show();				
					}else{						
						box.OverLay.Color = "rgb(51, 51, 51)" ; 
						box.Over = true;   
						box.OverLay.Opacity = 50;  
						box.Fixed = true;	 
						box.Center = true; 
						box.Show();	
						$('#errortype').html(data['errorkey']);
					}
				},
				complete:function()	{					
					HideTipDiv();
				}
				
			});
	}	
	
	$(document).on('click', '#CloseDiv', function(){		
		box.Close();
		location='/bindcard/index?type=1';
	});	
	$(document).on('click', '#CloseDiv2', function(){		
		box1.Close();
		location='/bindcard/index?type=1';
	});	
	
	$(document).on('click', '#reType', function(){	
		box.Close();
		$('#securityPassword').val("");
		$('#securityPassword').focus();
	});	

	
	
	
})();
</script>	
<style>
	.ui-check-tip {color:#999;}

	</style>
{/literal}	
</body>
</html>
