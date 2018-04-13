<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<script type="text/javascript">global_path_url="{$path_img}";</script>
    <script type="text/javascript">hjUserData= "{$hjUserData}";</script>
    <script type="text/javascript">global_params_notice = "all,ad_top,agent_first_page";</script>
	<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="{$path_js}/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.base.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="{$path_js}/js/phoenix.Validator.js"></script>
    <script type="text/javascript">
	(function() {       
		function async_load(){           
			var s = document.createElement('script');          
			s.type = 'text/javascript';          
			s.async = true;           
			s.src = "";           
			var x = document.getElementsByTagName('script')[0];          
			x.parentNode.insertBefore(s, x);      
		}       
	if (window.attachEvent)           
	window.attachEvent('onload', async_load);
	else 
	window.addEventListener('load', async_load, false);  
	})();
	</script>

<body>
	
	<!-- header start -->
	<div class="header">
		<div class="g_33">
			<h1 class="logo"><a title="首页" href="/index">宝开</a></h1>
		</div>
	</div>
	<!-- header end -->
	
	<div class="g_33">
		<div class="appeal-content">
			<dl class="prompt">
				<dt>温馨提示：</dt>
				<dd>请重新设置你的安全邮箱</dd>
				<dd>为保证账号安全，此链接仅能使用一次</dd>
			</dl>
			<div class="step">
				<table class="step-table">
					<tbody>
						<tr>
							<td class="current"><div class="con">输入邮箱</div></td>
							<td><div class="tri"><div class="con">查收验证邮件</div></div></td>
							<td><div class="tri"><div class="con">绑定成功</div></div></td>
						</tr>
					</tbody>
				</table>
			</div>
			<form id="J-form-step1" name="fm_step1" method="post" action="/appeal/sendmail">
			<input type="hidden" name="userId" value="{$userId}" >
			<input type="hidden" name="name" value="{$name}" >
			<input type="hidden" name="time" value="{$time}" >
			<input type="hidden" name="chkAct" value="{$chkAct}" >
			<input type="hidden" name="active" value="{$active}" >
			<input type="hidden" name="BCODE" value="{$BCODE}" >
				<ul class="ui-form">
					<li>
						<label  class="ui-label">修改的邮箱：</label>
						<input type="text" name="email" id="J-email" class="input">
						<div class="ui-check"><i class="error"></i>邮箱格式应为6~50位字母或数字</div>
					</li>
					<li class="ui-btn"><a id="J-button-submit" class="btn"  href="#">下一步<b class="btn-inner"></b></a></li>
				</ul>
			</form>
		</div>
	</div>
	
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
{literal}
<script>
(function($){
	var email = $('#J-email'),
		form = $('#J-form-step1'),
		v_email = new phoenix.Validator({el:email,type:'email',expands:{showSuccessMessage:function(msg){
			var me = this;
			$('.ui-check', me.parentNode).hide();
		},showErrorMessage:function(msg){
			var me = this;
			$('.ui-check', me.parentNode).html('<div class="ui-check"><i class="error"></i>邮箱格式应为6~50位字母或数字</div>');
			$('.ui-check', me.parentNode).css('display', 'inline');
		}}});
	new phoenix.Input({el:'#J-email',defText:'6~50位字母或数字',focusClass:'input-focus'});
	email.blur(function(){
		var me=$(this);
		$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/appeal/checkemailexist',			
				data:'email='+$(this).val(),					
				success:function(data){							
					if(data['status']=="1"){
						v_email.validated=false;
						$('.ui-check', me.parentNode).html('<div class="ui-check"><i class="error"></i>该邮箱已经注册，请重新输入</div>');
						$('.ui-check', me.parentNode).css('display', 'inline');
					}
				}
			});
	});
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		v_email.validate();
		if(v_email.validated){
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/appeal/checkemailexist',			
				data:'email='+email.val(),					
				success:function(data){							
					if(data['status']=="1"){
						v_email.validated=false;
						$('.ui-check', email.parentNode).html('<div class="ui-check"><i class="error"></i>该邮箱已经注册，请重新输入</div>');
						$('.ui-check', email.parentNode).css('display', 'inline');
					}else
					{
						form.submit();
					}
				}
			});
			
		}
	});
})(jQuery);
</script>
{/literal}

</body>
</html>
