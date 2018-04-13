<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>正常登录</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/admin/admin.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<script type="text/javascript">global_path_url="{$path_img}";</script>
	<script type="text/javascript" src="{$path_js}/js/base-all.js"></script>

	<script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>

	<script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.util.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.GlobalAd.js"></script>
<body class="bg-content">
	<div class="login-content">
		<div class="login-logo"></div>
      
            <ul class="login-list">
			<li class="login-list-input">
			<div>
				<i class="ico-name"></i>
				<input id="J_username" type="text" class="login-input" value="" tabindex="1" maxlength="16" />
			</div>
			<li class="login-list-input">					
				<i class="ico-pwd"></i>
				<input id="J_password" type="password" class="login-input" value="" tabindex="2" maxlength="20" />			
			</li>
			<li class="login-list-input">
				<i class="ico-pwd"></i>
				<input id="J_bindpwd" type="text" class="login-input" value="" tabindex="3"  maxlength="20" />
			</li>
	<!--		<div id="divcode">
				<li class="login-list-input verify-code">
					<img id="J-vcode-img" src="{$imageurl}" alt="验证码" onClick="this.src='{$imageurl}?r='+Math.random();" data-src-path="{$imageurl}" />
					<input id="J_vcode" type="text" class="login-input" value="" tabindex="3" />
				</li>
			</div>-->
			<li class="login-list-btn"><input id="J_submit" type="button" class="login-btn" value="登 录" tabindex="4" /></li>
			</ul>
	
	</div>
	<script  src="{$path_js}/js/phoenix.Verification.js"></script>
{literal}

<script>
(function($){
	var msg = new phoenix.Message();
	var username = $('#J_username'),password = $('#J_password'),bindpwd=$('#J_bindpwd');
	var p_username = new phoenix.Input({el:username,defText:'请输入用户名',focusClass:'login-input-focus'});
	var p_password = new phoenix.Input({el:password,defText:'********',focusClass:'login-input-focus'});
	var p_bindpwd = new phoenix.Input({el:bindpwd,defText:'请输入动态码',focusClass:'login-input-focus'});
	//校验字段
	var showSuccessMessage = function(msg){
		var me = this;
		if(me.customTip){
			me.customTip.remove();
			me.customTip = null;
		}
	},
	showErrorMessage = function(msg){
		var me = this;
		me.customTip = me.customTip || (new phoenix.Tip({text:'<i class="error"></i>'+ msg +''}));
		me.customTip.setText('<i class="error"></i>'+ msg +'');
		me.customTip.show(300, 5, me.dom);
	};
	var v_username = new phoenix.Validator({el:username,type:'usernameAdmin',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}}),	
	v_password = new phoenix.Validator({el:password,type:'password',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}}),
	v_bindpwd = new phoenix.Validator({el:bindpwd,type:'bindpwd',expands:{showSuccessMessage:showSuccessMessage,showErrorMessage:showErrorMessage}});
	var vGroup = [],isFormLoading = false,hasGroup = {'username':v_username,'password':v_password,'bindpwd':v_bindpwd};	
		vGroup.push(v_username);
		vGroup.push(v_password);	
		vGroup.push(v_bindpwd);	
	
	$('#J_submit').click(function(e){
		try{
			fnCheckLogin();
		}catch(ex)
		{
			
		}
	});
	
	//记录错误到服务器（按等级区分）
	function logError(sev,msg){
		var img=new Image();
		img.src="log.php?sev="+encodeURIComponent(sev)+"&msg="+encodeURIComponent(msg);
	}
	
	var getRedirectParams = function(){
	    var url = location.search,pattern = "redirect=",str = "";
	    if (url.indexOf(pattern) != -1) {
			var str = url.substr(url.indexOf(pattern)+pattern.length);
		}
		return str;
	};
	
	function fnCheckLogin(){

		var passNum = 0,i = 0,len = vGroup.length;
		if(isFormLoading){
			return;
		}
		for(;i < len;i++){
			vGroup[i].validate();
			if(vGroup[i].validated){
				passNum++;
			}else{
				break;	
			}
		}
		if(passNum == len){
	         var datas="username="+$.trim(v_username.getValue())+"&password="+$.trim(v_password.getValue())+"&bindpwd="+$.trim(v_bindpwd.getValue());
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'login/login',
				data:datas,
				beforeSend:function(){
					//isLoading = true;
					ShowTipDiv();
				},
				success:function(data){
					if(data['isSuccess']){		
						var url = '/admin/';
						url = getRedirectParams() == '' ? url : getRedirectParams();
						location.replace(url); //跳转后禁止后退
					}else{
						var errors = data['errors'];
						$.each(errors, function(){
							var vObj = hasGroup[this[0]];
							if(vObj){
								vObj.showErrorMessage(this[1]);
							}
						});
						password.val('');
					}
				},
				error:function(xhr, type){
					msg.show({
						mask: true,
						hideClose: true,
						title: '温馨提示',
						content: '<h3 style="height:30px;line-height:30px;text-align:center; ">登录失败，请重试</h3><div style="height:30px;line-height:30px;"></div>',
						confirmIsShow: 'true',
						confirmText: '确定',
						confirmFun: function(){
							msg.hide();
						}
					});
				},
				complete:function(){
					HideTipDiv();
				}
			});
		}
	}
})(jQuery);
</script>

{/literal}


	
</body>
</html>