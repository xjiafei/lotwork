<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	<script type="text/javascript" src="{$path_js}/js/rsa.min.js"></script>
	
	{literal}
	<style>
	.ui-check-right {padding-left:24px;color:green;display:none;}
	</style>
	{/literal}
	
	{include file='/default/script-base.tpl'}
	

<body>

{include file='/default/ucenter/header.tpl'}
	<div class="g_33 common-main">
    	<div class="g_5">
    		<!-- //////////////左侧公共页面////////////// -->
    			{include file='/default/ucenter/left.tpl'}
    		<!-- /////////////左侧公共页面/////////////// -->	
    	</div>
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">{$title}</div>
				
				
				<form action="?act=smt" method="post" id="J-form">
				<div class="content page-safecode-set">
					<dl class="prompt">
						<dt>温馨提示：定期更换安全密码可以让您的账户更加安全。</dt>
						<dd>请确保安全密码与登录密码不同！</dd>
						<dd>建议密码使用字母和数字的组合、混合大小写、在组合中加入下划线等符号；</dd>
					</dl>
					<h3 class="ui-title">设置安全密码</h3>
					<ul class="ui-form">
						<li>
							<label for="safeCode" class="ui-label">设置安全密码：</label>
							<input type="password" value="" id="J-safePassword" class="input" maxlength="20">
							<input type="hidden" id="safePwd" name="safePwd" value="">
                            <div class="ui-prompt" style="display:none">6-20位字符</div> 
							<div class="ui-check"><i class="error"></i><span id="error1">长度有误，请输入6-20位字</span></div>
							<div class="pwd-new-strength" id="J-div-pwdstrength" style="margin-left: 20px; width: 270px; display: none;">密码强度：弱
                                	<span class="pwd-new-bar"><b id="password_label" class="pwd-new-weak"></b></span>强<span id="newcheckString"></span></div>
						</li>
						<li>
							<label for="checkSafeCode" class="ui-label">确认安全密码：</label>
							<input type="password" value="" id="J-safePassword2" class="input" maxlength="20">
							<span class="ui-check-right"></span>
                            <div class="ui-prompt" style="display:none">请再次输入安全密码</div> 
							<div class="ui-check"><i class="error"></i><span id="errorsafe2">两次输入不一致，请修改</span></div>
						</li>
						<li class="ui-btn"><a id="J-button-submit" class="btn"  href="#">设 置<b class="btn-inner"></b></a></li>
					</ul>
					<!-- ucenter-safe-setting-firsttime end -->
				</div>
				</form>
			</div>
			
		</div>
	</div>
{literal}
<script>
(function($){
{/literal}
	//RSA 加密
	var rsa = new RSAKey();
	rsa.setPublic('{$rsa_n}', '{$rsa_e}');
{literal}

	var safePassword = $('#J-safePassword'),safePassword2 = $('#J-safePassword2'),v_pwd,v_pwd2,
		showSuccessMessage,showErrorMessage,
		inputs=$('input'),
		vGroup,
		minWindow,mask,
		checkPasswdSubmit = false;
	
	//密码校验
	showSuccessMessage = function(msg){
		var me = this,par = me.dom.parent();
		$('.ui-check', par).css('display', 'none');
		$('.ui-check-right', par).show();
		
		
		//两次密码不一样
		if(me.dom.get(0) == safePassword2.get(0) && v_pwd.getValue() !== v_pwd2.getValue()){
			me.validated = false;
			$('.ui-check', par).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
			$('.ui-check-right', par).hide();
			return;
		}
		if((/\s/).test(safePassword.val()) && me != v_pwd2 ){
			checkPasswdSubmit = false;
			$('.ui-check', par).html('<i class="error"></i>密码不含空格').css('display', 'inline');
			$('.ui-check-right', par).hide();
			return;
		}

		if((/^\d{1,8}$/).test(safePassword.val()) && me != v_pwd2){
			checkPasswdSubmit = false;
			$('.ui-check', par).html('<i class="error"></i>密码不能是9位以下纯数字').css('display', 'inline');
			$('.ui-check-right', par).hide();
			return;
		}
		if(me != v_pwd2){
			checkPasswdSubmit = true;
		}
		if(me.dom.get(0) == safePassword.get(0)){
			if(v_pwd.getValue() !== v_pwd2.getValue() && v_pwd2.getValue() != ''){
				me.validated = false;
				showPassowrdLevel();
				$('.ui-check', v_pwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
				$('.ui-check-right', v_pwd2.dom.parent()).hide();
			}else{
				me.validated = true;
				showPassowrdLevel();
				$('.ui-check', v_pwd2.dom.parent()).css('display', 'none');
				$('.ui-check-right', par).show();

				}
		}
		};
	showErrorMessage = function(msg){
		var me = this,par = me.dom.parent();
		$('.ui-check', par).html('<i class="error"></i>' + msg).css('display', 'inline');
		$('.ui-check-right', par).hide();
		if(me.dom.get(0) == safePassword.get(0) && v_pwd.getValue() !== v_pwd2.getValue() && v_pwd2.getValue() != ''){
			me.validated = false;
			$('.ui-check', v_pwd2.dom.parent()).html('<i class="error"></i>两次输入不一致，请修改').css('display', 'inline');
		}
	};
	v_pwd = new phoenix.Validator({el:safePassword,type:'safepassword',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	v_pwd2 = new phoenix.Validator({el:safePassword2,type:'safepassword2',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	
	
	inputs.each(function() {
        $(this).val("");
    });
	//  禁止复制和黏贴文本框中的内容 
	$("input:password").bind("copy cut paste",function(e){ 
      return false; 
    });
	
	//表单验证
	minWindow = new phoenix.MiniWindow();
	mask = phoenix.Mask.getInstance();
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	vGroup = [];
	vGroup.push(v_pwd);
	vGroup.push(v_pwd2);
	$('#J-button-submit').click(function(e){		
		v_pwd.validate();
		v_pwd2.validate();
		if(!v_pwd.validated || !v_pwd2.validated)
		{
			return false;
		}
		var passNum = 0,tplData = [];
		e.preventDefault();
		$.each(vGroup, function(){
			this.validate();
			if(this.validated){
				passNum++;
			}
		});
		//此处判断放在提示后执行.
		if( safePassword.val()!=safePassword2.val() ){	e.preventDefault(); minWindow.hide();return false;}
		e.preventDefault();
		
		//RSA加密
		var encode_pwd = rsa.encrypt($('#J-safePassword').val());
		$('#safePwd').val(encode_pwd)
		if(checkPasswdSubmit){
			$('#J-form').submit();
		}
	});
	
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

	var showPassowrdLevel = function(v){
		level = getPassowrdLevel(safePassword.val());
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
	//友情提示(待validator封装)
	safePassword.focus(function(){
		$("#J-div-pwdstrength").css("display","none");	
		safePassword.parent().find('.ui-check').css('display', 'none');
		safePassword.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		safePassword.parent().find('.ui-prompt').css('display', 'none');
	});
	safePassword2.focus(function(){	
		safePassword2.parent().find('.ui-check').css('display', 'none');
		safePassword2.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		safePassword2.parent().find('.ui-prompt').css('display', 'none');
	});
})(jQuery);
</script>
{/literal}
	
	
<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
</body>
</html>