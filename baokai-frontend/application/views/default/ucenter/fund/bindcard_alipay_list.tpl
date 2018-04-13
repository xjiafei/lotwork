<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>银行卡管理</title>
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
		<form action="/bindcard/bindcardalipay" method="post" id="J-form">
		
		<div class="g_28 g_last">
			<div class="common-content bank-card-bind">
				<div class="title">银行卡管理</div>
				<div class="ui-tab-title tab-title-bg clearfix appeal-link-tab">
					<ul>
						<li>银行卡管理</li>
						<li class="current">支付宝管理</li>
					</ul>
                </div>
				<div class="content">
					<div class="notice"><i class="ico-warning"></i>为了您的充值可以顺利到账，请务必绑定正确的支付宝信息，充值时请勾选当前使用的支付宝账户进行充值。</div>
					<ul class="ui-form">
						<li>
							<label for="name" class="ui-label">已绑定的支付宝信息：</label>
							<table class="table table-bank-limit">
								<tbody>
									<tr>
										<th>勾选当前使用支付宝</th>
										<th>支付宝姓名</th>
										<th>昵称</th>
										<th>手机/邮箱</th>
										<th>最后操作时间</th>
										<th>操作</th>
										<th></th>
									</tr>
									{if $bindCnt >0}
										{foreach from=$userbankstruc item=data}
										<tr>
											<td><span class="ico-bank {$data.logo}"></span></td>
											<td>{$data.bankAccount}</td>
											<td>{$data.nickName}</td>
											<td>{$data.bankNumber}</td>
											<td>{$data.bindDate}</td>
											
											<!-- {if $lock eq '0'} -->
											<td><a href="javascript:void(0);"  name="UpdateBankCard" 
											pro_id='{$data.id}' pro_bankId='{$data.bankId}' pro_mcBankId='{$data.mcBankId}'
											pro_bankAccount ='{$data.deBankAccount}' pro_nickName = '{$data.nickName}' pro_bankNumber ='{$data.deBankNumber}'
											>修改</a></td>
											<td><a href="javascript:void(0);"  name="DeleteBankCard" pro_id='{$data.id}' pro_bankId='{$data.bankId}' pro_mcBankId='{$data.mcBankId}' pro_nickName = '{$data.nickName}'>删除</a></td>
											<!-- {else} -->
											<td><a href="javascript:void(0);" class="disable" >修改</a></td>
											<td><a href="javascript:void(0);" class="disable" >删除</a></td>
											<!-- {/if} -->
											
										</tr>
										{/foreach}
									{else}
									<tr>
										<td colspan="5"><br />您尚未绑定任何支付宝信息，<a href="/bindcard/bindcardalipay?bindcardType=1">立即绑定</a><br /><br /></td>
									</tr>
									{/if}
								</tbody>                        
							</table>
						</li>
						<li>
							<label for="name" class='big'>已绑定{$bindCnt}张支付宝，一共可以绑定{if $icardcount neq '-1'}{$icardcount}{else}无限{/if}张支付宝账户。</label>
						</li>
						<!-- {if $addlock eq 0} -->
						<!-- {if $bindCnt >0} -->
						<li>
							<a href="/bindcard/bindcardalipay?bindcardType=1" class="btn btn-important">增加绑定<b class="btn-inner"></b></a>
							<span class="ui-text-info">增加绑定、删除功能尚未锁定；</span>
<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank" class="ui-text-info">联系客服</a>
						</li>
						<!-- {/if} -->
						<!-- {else} -->
						<li>
							<a href="javascript:void(0);" class="btn btn-disable">增加绑定<b class="btn-inner"></b></a>
							<span class="ui-prompt"><!-- {if $lock neq '0'} -->您的银行卡已经锁定，不能进行银行卡信息的增加和删除。<!-- {else} -->您已经绑定了{$bindCnt}张银行卡,不能再增加新的银行卡.<!-- {/if} --></span>
<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1" target="_blank" class="ui-text-info">联系客服</a>
						</li>
						<!-- {/if} -->
					</ul>
					<dl class="prompt">
						<dt>提示：</dt>
						<dd>请绑定正确的支付宝使用者姓名、昵称、手机或者邮箱账号，绑定支付宝账户有助于您更加快捷安全进行充值。</dd>
						<dd>为了您的账户和资金安全，在您绑定或者删除已绑定支付宝账户时，您需要输入正确的安全密码即可完成修改或者删除。</dd>
						<dd>平台郑重承若，平台在任何时候都不会泄露客户绑定资料，还请客户放心使用。</dd>
					</dl>
				</div>
			</div>
		</div>
		
	</form>
	</div> 
    
    <textarea id="Divpwd" style="display:none;">  
        <div class="bd">
            <ul class="ui-form">
                <li>               
                    <label class="ui-label" for="name">安全密码：</label>
                    <input type="password" class="input w-4" id="securityPassword">
                  	<sapn  class="ui-check" id="passWordError"><i class="error"></i></span>
                </li>
               
				<li class="ui-text" id="shownotie1">请输入您的安全密码 <a href="/safepersonal/sefecoderetrieve?stp=2">忘记安全密码</a></li>
                <li class="ui-check" id="error1" style="padding-left:150px"><i class="error"></i><span id="errorText1">请输入您的安全密码(6-20位) </span><a target="_blank" id="errorText2" href="/safepersonal/sefecoderetrieve?stp=2">忘记安全密码</a></li>
                <li class="ui-check" id="error2" style="padding-left:150px"><span id="errorText3" style="color:red"> </span></li>
            </ul>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Sumbit-Button1">提 交<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>	
    
    <div  id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text"><span id="errorTip">操作失败，请检查网络，并重试</span></h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn" id="CloseDs">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    
    <div  id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>   
  
    <textarea id="DivBankUp" style="display:none;">  
        <div class="bd">
            <div class="pop-title">
            <i class="ico-waring"></i>
            <h4 class="pop-text">删除银行卡后，无法使用该银行卡进行提款，请问您确定需要删除吗？</h4>
            </div>
            <div class="pop-btn">               
                 <a href="javascript:void(0);" class="btn btn-important " id="J-Sumbit-Button2">确 定<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs2">取 消<b class="btn-inner"></b></a>
                
            </div>
        </div>
	</textarea>
    
    <textarea id="DivDeleOk" style="display:none;">  
		<div class="bd">
			<div class="pop-title">
				<i class="ico-success"></i>
				<h4 class="pop-text">银行卡删除成功！</h4>
			</div>
		</div>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important " id="J-Sumbit-Button3">确 定<b class="btn-inner"></b></a>
		</div>
	</textarea>
	
	<form style="display: hidden" action="/bindcard/bindcardalipay?bindcardType=1" method="POST" id="aliform">
	  <input type="hidden" id="id" name="id" value=''/>
	  <input type="hidden" id="bankid" name="bankid" value=''/>
	  <input type="hidden" id="mcbankid" name="mcbankid" value=''/>
	  <input type="hidden" id="bankAccount" name="bankAccount" value=''/>
	  <input type="hidden" id="nickName" name="nickName" value=''/>
	  <input type="hidden" id="bankNumber" name="bankNumber" value=''/>
	  <input type="hidden" id="opeType" name="opeType" value=''/>
	  
	
	</form>

<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
{literal}
<script>
(function(){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	var htmls="",minWindow,mask,initFn,pro_bankId,pro_mcBankId,pro_id,pro_nickName;	
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});		
	//--------------------------------------------表单验证----------------------------------------------------
	var form1 = $('#J-form'),securityPassword,securityPasswordPar,isture="0",
	//表单检测错误数量
	errorTypes = ['securityPassword'],
	errorHas = {},
	setErrorNum = function(name, num){
		if(typeof errorHas[name] != 'undefined'){
			errorHas[name] += num;
			errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
		}
	};
	$.each(errorTypes, function(){
		errorHas[this] = 0;
	});	
	//表单检测
	checkform = function(){			
		if($('#securityPassword')[0].value=='' || $.trim($('#securityPassword')[0].value).length < 6 || $.trim($('#securityPassword')[0].value).length > 20){
			$('#error1').css('display', 'inline');
			$('#shownotie1').css('display', 'none');
			setErrorNum('securityPassword', 1);
		}
	}	
	
	//---------------------弹出验证安全密码层开始1：当前层做安全验证，2：通过确定层，3：成功确定层--------------------------------------------
	$('[name="DeleteBankCard"]').click(function(e){		
		$('#opeType').val('delete');
		
		pro_bankId=$.trim($(this).attr("pro_bankId")),
		pro_mcBankId=$.trim($(this).attr("pro_mcBankId")),
		pro_id=$.trim($(this).attr("pro_id"));
		pro_nickName=$.trim($(this).attr("pro_nickName"));		
		e.preventDefault();		
		minWindow.setTitle('为了保障您的账户安全，需要先验证您的安全密码');
		minWindow.setContent($('#Divpwd').val());
		minWindow.show();
		initFn();	
	});	
	$('[name="UpdateBankCard"]').click(function(e){		

		$('#opeType').val('update');
		$('#id').val($.trim($(this).attr("pro_id")));
		$('#bankid').val($.trim($(this).attr("pro_bankid")));
		$('#mcBankId').val($.trim($(this).attr("pro_mcBankId")));
		$('#bankAccount').val($.trim($(this).attr("pro_bankAccount")));		
		
		$('#nickName').val($.trim($(this).attr("pro_nickName")));	
		$('#bankNumber').val($.trim($(this).attr("pro_bankNumber")));	
		
		
		e.preventDefault();		
		minWindow.setTitle('为了保障您的账户安全，需要先验证您的安全密码');
		minWindow.setContent($('#Divpwd').val());
		minWindow.show();
		initFn();	
		
		
		
	});	
	initFn = function(){
		//base64 加密
		var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9\+\/\=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);return t},_utf8_encode:function(e){e=e.replace(/\r\n/g,"\n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}};

		var securityPassword;//安全密码
		//安全密码验证
		securityPassword = $('#securityPassword');
		securityPasswordPar = securityPassword.parent();	
		securityPassword.blur(function(){
			var v = $.trim(this.value);	
			if(v == '' || v.length < 6 || v.length > 20){			
				$('#error1').css('display', 'inline');
				$('#shownotie1').css('display', 'none');
				setErrorNum('securityPassword', 1);
			}else if($("#error2").css('display') == 'inline'){
				$('#shownotie1').css('display', 'none');
				$('#error1').css('display', 'none');
			}else{				
				$('#shownotie1').css('display', 'inline');
				$('#error1').css('display', 'none');
				setErrorNum('securityPassword', -1);
			}			
		}).focus(function(){
			if($("#error2").css('display') == 'inline'){
				$('#shownotie1').css('display', 'none');
				$('#error1').css('display', 'none');
			}else{
				$('#shownotie1').css('display', 'inline');
				$('#error1').css('display', 'none');
			}
			
		});		
			
		$('#closeDs').click(function(e){
			minWindow.hide();
		});		
		$('#J-Sumbit-Button1').click(function(e){			
			var err = 0;
			checkform();
			$.each(errorTypes, function(){
				if(typeof errorHas[this] != 'undefined'){
					err += errorHas[this];
				}
			});	
			if(err > 0 ){
				e.preventDefault();
				return false;
			}			
			if(pro_bankId =="" || pro_mcBankId == "" || pro_id==''){
				alert("数据异常");
				return false;						
			}				
			securityPassword=$.trim($('#securityPassword').val())
			$.ajax({			
				type:'post',
				dataType:'json',
				cache:false,				
				url:'/bindcard/checksecuritypwd/',
				data:'securitypwd='+Base64.encode(securityPassword),
				beforeSend:function(jqXHR, settings){
					//RSA加密
					settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
					$('#J-Sumbit-Button1').attr('disabled',"true");
				},			
				success:function(data){
					if(data['status']=="ok"){	
						
						if($('#opeType').val() != 'update'){
							minWindow.hide();
							minWindow.setTitle('银行卡修改提醒');
							minWindow.setContent($('#DivBankUp').val());
							minWindow.show();
							initFn2();	
						}else{
							$("#aliform").submit();
						}	
						
						
						
					}else{	
						$('#passWordError,#error2').css("display","inline");	
						$('#shownotie1,#error1').css('display', 'none');						
						$('#errorText3').html(data['data']);
					}
				},
				complete:function()	{					
					 $('#J-Sumbit-Button1').removeAttr("disabled");
				}				
			});	
			initFn2 = function(){
				$('#closeDs2').click(function(e){
					minWindow.hide();
				});	
		
				$('#J-Sumbit-Button2').click(function(e){	
					var datas="id="+pro_id+"&bankid="+pro_bankId+"&mcbankid="+pro_mcBankId+"&securityPassword="+Base64.encode(securityPassword)+"&bindcardType=1&nickName="+pro_nickName;
					$.ajax({			
						type:'post',
						dataType:'json',
						cache:false,
						url:'/bindcard/baindcarddelete/',					
						data:datas,			
						beforeSend:function(jqXHR, settings){
							//RSA加密
							settings.data = 'rsa_data=' + rsa.encrypt(settings.data);
						},	
						success:function(data){			
							minWindow.hide();
							if(data['status']=="ok"){	
								minWindow.hide();
								minWindow.setTitle('提示');
								minWindow.setContent($('#DivDeleOk').val());
								minWindow.show();
							}
						}				
					});	
					
				});		
							
				$(document).on('click', '#J-Sumbit-Button3', function(){		
					location=location;
				});	
			}
			
			
			
		});			
	};	
	
	initFn3 = function(){
			
		$("#aliform").submit();
			
			
				
	}	
	
	$('.ui-tab-title ul li').click(function(){
		var indexs = $(this).index();
		if($(this).attr('class') !='current'){
			window.location.href="/bindcard/index?type="+indexs;
		}
	});
	
})();
</script>	
{/literal}	
</body>
</html>
