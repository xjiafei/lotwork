<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{if $isSetted eq '修改'}修改预留验证信息设置{else}设置预留验证信息{/if}</title>
	<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{literal}
	<style>
	.ui-check-right {padding-left:24px;color:green;display:none;}
	</style>
	{/literal}
	
	{include file='/default/script-base.tpl'}
	
</head>
<body>
	{include file='/default/ucenter/header.tpl'}
	<div class="g_33 common-main">
		<div class="g_5">
			<!-- //////////////左侧公共页面////////////// -->
    			{include file='/default/ucenter/left.tpl'}
    		<!-- /////////////左侧公共页面/////////////// -->	
		</div>
		<div class="g_28 g_last">
			<div class="common-content page-slogan">
				<div class="title">{if $isSetted eq '修改'}修改预留验证信息设置{else}设置预留验证信息{/if}</div>
				<div class="content">
					<div class="prompt">温馨提示：在登录时选择安全登录，您会看到此处设置的预留验证信息，可帮助你识别平台的真实性</div>
					<hr class="line-dotted" />
					
					<form id="J-form" name="fm_stp1" method="post">
					<ul class="ui-form">
						<li>
							<label for="info" class="ui-label">设置{if $isSetted eq '修改'}新的{/if}预留验证信息：</label>
							<input name="slogan" type="text" value="" id="J-slogan" class="input" maxlength="8">   
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">请输入2-8位中文或4-8位英文</div>                      
							<div class="ui-check"><i class="error"></i>请输入2-8位中文或4-8位英文</div>
						</li>
						<li>
							<label for="checkInfo" class="ui-label">确认{if $isSetted eq '修改'}新的{/if}预留验证信息：</label>
							<input type="text" value="" id="J-slogan2" class="input" maxlength="8">
							<span class="ui-check-right"></span>
                             <div class="ui-prompt" style="display:none">确认新的预留验证信息</div>   
							<div class="ui-check"><i class="error"></i>请确预留验证信息无误</div>
						</li>
						<li class="ui-btn">
							<a class="btn" id="J-button-submit" href="#">{$isSetted}<b class="btn-inner"></b></a>
						</li>
					</ul>
					</form>
					
					<!-- RSA Form-->
					<form method="post" id="rsa-form">
						<input type="hidden" name="rsa_data" value="">
					</form>
					
				</div>
			</div>
			
			
		</div>
	</div>



{literal}
<script>
(function(){
	//RSA 加密
	var rsa = new RSAKey();
	{/literal}
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
	{literal}
	var form = $('#J-form'),
		button = $('#J-button-submit'),
		slogan = $('#J-slogan'),
		slogan2 = $('#J-slogan2'),
		v_slogan,
		v_slogan2,
		showSuccessMessage,
		showErrorMessage,
		vGroup = [];
	showSuccessMessage = function(msg){
		var me = this;
		me.dom.parent().find('.ui-check').hide();
		me.dom.parent().find('.ui-check-right').show();
		
		if(me.dom.get(0) == slogan2.get(0)){
			if(v_slogan2.getValue() != v_slogan.getValue()){
				v_slogan2.validated = false;
				me.dom.parent().find('.ui-check').html('<i class="error"></i>两次输入信息不一致,请修改').css('display', 'inline');
				me.dom.parent().find('.ui-check-right').hide();
			}else{
				v_slogan2.validated = true;
				me.dom.parent().find('.ui-check').hide();
				v_slogan2.dom.parent().find('.ui-check').hide();
				v_slogan2.dom.parent().find('.ui-check-right').show();
			}
		}
		if(me.dom.get(0) == slogan.get(0)){
			if(v_slogan2.getValue() != v_slogan.getValue() && v_slogan2.getValue() != ''){
				v_slogan2.validated = false;
				v_slogan2.dom.parent().find('.ui-check').html('<i class="error"></i>两次输入信息不一致,请修改').css('display', 'inline');
			}else{
				v_slogan2.validated = true;
				me.dom.parent().find('.ui-check').hide();
				v_slogan2.dom.parent().find('.ui-check').hide();
			}
		}
	};
	showErrorMessage = function(msg){
		var me = this;
		me.dom.parent().find('.ui-check').html('<i class="error"></i>' + msg).css('display', 'inline');
		me.dom.parent().find('.ui-prompt').css('display', 'none');
		me.dom.parent().find('.ui-check-right').hide();
	}
	v_slogan = new phoenix.Validator({el:slogan,type:'slogan',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_slogan2 = new phoenix.Validator({el:slogan2,type:'slogan',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	vGroup.push(v_slogan);
	vGroup.push(v_slogan2);
	
	button.click(function(e){
		var passNum = 0;
		e.preventDefault();
		$.each(vGroup, function(){
			var me = this;
			me.validate();
			if(me.validated){
				passNum++;
			}
		});
		if(passNum >= vGroup.length){
			if(v_slogan.getValue() != v_slogan2.getValue()){
				v_slogan2.dom.parent().find('.ui-check').html('<i class="error"></i>两次输入信息不一致,请修改').css('display', 'inline');
			}else{
				//form.submit();
				var rsa_data = rsa.encrypt(form.serialize());
				$('#rsa-form input[name=rsa_data]').val(rsa_data);
				$('#rsa-form').submit();
			}
		}else{
			return false;
		}
		
	});
	//友情提示(待validator封装)
	slogan.focus(function(){	
		slogan.parent().find('.ui-check').css('display', 'none');
		slogan.parent().find('.ui-prompt').css('display', 'inline');
		slogan.parent().find('.ui-check-right').hide();
	}).blur(function(){
		slogan.parent().find('.ui-prompt').css('display', 'none');
	});
	slogan2.focus(function(){	
		slogan2.parent().find('.ui-check').css('display', 'none');
		slogan2.parent().find('.ui-prompt').css('display', 'inline');
		slogan2.parent().find('.ui-check-right').hide();
	}).blur(function(){
		slogan2.parent().find('.ui-prompt').css('display', 'none');
	});
	
})(jQuery);
</script>
{/literal}







</body>
</html>