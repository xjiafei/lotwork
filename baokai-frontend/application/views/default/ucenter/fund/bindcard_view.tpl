<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>银行卡绑定</title>
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
		<form action="/bindcard/bindcardsecurityinfo" method="post" id="J-form">
		
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">银行卡绑定</div>
				<div class="content">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="current"><div class="con">1.验证安全信息</div></td>
									<td><div class="tri"><div class="con">2.输入银行卡信息</div></div></td>
									<td><div class="tri"><div class="con">3.绑定成功</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="notice"><i class="ico-warning"></i>为了保障您的账户安全，需要先验证您的安全密码；</div>
					<ul class="ui-form">
					{if $ishavebind eq '1'}
						<!-- <li>
						<label for="name" class="ui-label">银行名称：</label>
						<span class="ico-bank {$data.logo}" name="{$data['logo']}" max="{$data.upLimit}" min="{$data.lowLimit}" ></span>
						</li> -->
						<li>
							<label for="name" class="ui-label">已绑卡号：</label>
							<span class="ui-singleline">
							<select name="cardid" id="cardid" class="ui-select">
							{foreach from=$userBankStruc item=data key=key}
								<option value="{$key}" >{$data.bankNumber}</option>
							{/foreach}
							<!-- logo="{$data.logo}" -->
							</select> </span>
						</li>
						<li>
							<label for="name" class="ui-label">验证卡号：</label>
							<input type="text" value="" class="input w-4" name="bankNumber" id="bankNumber">
                            <div class="ui-prompt"><span class="ui-prompt">请根据已绑卡号的提示，输入完整银卡号位</span></div>
                            <div class="ui-check"><i class="error"></i><span >卡号不符合规则（16-19位）</span></div>
                            
							<span class="ui-prompt"></span>
						</li>
						<li>
							<label for="name" class="ui-label">开户人：</label>
							<input type="text" value="" class="input w-4" name="bankAccount" id="bankAccount">
                            <div class="ui-prompt"><span class="ui-prompt">请根据已绑卡号的提示，输入开户人姓名</span></div>
                            <div class="ui-check"><i class="error"></i><span >请输入开户人姓名</span></div>
							
						</li>
					{/if}
						<li>
							<label for="name" class="ui-label">安全密码：</label>
							<input type="hidden" name="fromid" value="{$fromid}"/>
							<input type="hidden" name="bankid" value="{$bankid}"/>
							<input type="password" value="" class="input w-4" name="securityPassword" id="securityPassword">
							
                             <div class="ui-prompt">请输入您的安全密码，如需要帮助请选择 <a target="_blank" href="/safepersonal/sefecoderetrieve?stp=2">忘记密码</a></div>
                            <div class="ui-check"><i class="error"></i><span >请输入您的安全密码(6-20位)，如需要帮助请选择</span><a target="_blank" href="/safepersonal/sefecoderetrieve?stp=2">忘记密码</a></div>
						</li>
						<li class="ui-btn"><a href="javascript:void(0);" class="btn btn-important" id="J-Submit">提 交<b class="btn-inner"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
		
	</form>
	</div> 
<!-- //////////////底侧公共页面////////////// -->
{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal}
<script>
(function(){	
	var form = $('#J-form'),bankNumber,bankNumberPar,bankAccount,bankAccountPar,securityPassword,securityPasswordPar,isture="0",	
	//表单检测错误数量
	errorTypes = ['bankNumber','bankNumber','securityPassword'],
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
	
	//验证卡号,Luhm校验
	bankNumber = $('#bankNumber');
	bankNumberPar = bankNumber.parent();	
	bankNumber.blur(function(){
		var v = $.trim(this.value),isture=false;
		if(v != ''){
			var isture=luhmCheck(v);
		} 		
		if(isture == false || v==''){			
			bankNumberPar.find('.ui-check').css('display', 'inline');	
			bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);
		}else{				
			bankNumberPar.find('.ui-check').css('display', 'none');
			setErrorNum('bankNumber', -1);
		}			
	}).focus(function(){
		bankNumberPar.find('.ui-check').css('display', 'none');
		bankNumberPar.find('.ui-prompt').css('display', 'inline');
	});
	//开户人验证
	bankAccount = $('#bankAccount');
	bankAccountPar = bankAccount.parent();	
	bankAccount.blur(function(){
		var v = $.trim(this.value);	
		if(v == ''  ){			
			bankAccountPar.find('.ui-check').css('display', 'inline');	
			bankAccountPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankAccount', 1);
		}else{				
			bankAccountPar.find('.ui-check').css('display', 'none');
			setErrorNum('bankAccount', -1);
		}			
	}).focus(function(){
		bankAccountPar.find('.ui-check').css('display', 'none');
		bankAccountPar.find('.ui-prompt').css('display', 'inline');
	});
	//安全密码验证
	securityPassword = $('#securityPassword');
	securityPasswordPar = securityPassword.parent();	
	securityPassword.blur(function(){
		var v = $.trim(this.value);	
		if(v == '' || v.length < 6 || v.length > 20){			
			securityPasswordPar.find('.ui-check').css('display', 'inline');	
			securityPasswordPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('securityPassword', 1);
		}else{				
			securityPasswordPar.find('.ui-check').css('display', 'none');
			setErrorNum('securityPassword', -1);
		}			
	}).focus(function(){
		securityPasswordPar.find('.ui-check').css('display', 'none');
		securityPasswordPar.find('.ui-prompt').css('display', 'inline');
	});
	//表单检测
	checkform = function(){		
		if($('#bankNumber')[0].value=='' || $.trim($('#bankNumber')[0].value).length < 16 || $.trim($('#bankNumber')[0].value).length > 19){
			bankNumberPar.find('.ui-check').css('display', 'inline');	
			bankNumberPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankNumber', 1);
		}
		if($('#bankAccount')[0].value==''){
			bankAccountPar.find('.ui-check').css('display', 'inline');	
			bankAccountPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('bankAccount', 1);
		}
		if($('#securityPassword')[0].value=='' || $.trim($('#securityPassword')[0].value).length < 6 || $.trim($('#securityPassword')[0].value).length > 20){
			securityPasswordPar.find('.ui-check').css('display', 'inline');	
			securityPasswordPar.find('.ui-prompt').css('display', 'none');
			setErrorNum('securityPassword', 1);
		}
	}		 
	
	//表单提交校验
	$('#J-Submit').click(function(){		
		form.submit();
	});	
	
	form.submit(function(e){			
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
		return true;
	});
})();
</script>	
{/literal}	
</body>
</html>