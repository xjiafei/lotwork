<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>安全密码验证</title>
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
		<form action="/bindcard/bankcardmanagerlist" method="post" id="J-form">
		
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">安全密码验证</div>
				<div class="content">
					<div class="notice"><i class="ico-warning"></i>为了保障您的账户安全，需要先验证您的安全密码；</div>
					<ul class="ui-form">
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
	var form = $('#J-form'),isture="0",	
	//表单检测错误数量
	//安全密码验证
	securityPassword = $('#securityPassword');
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
})();
</script>	
{/literal}	
</body>
</html>