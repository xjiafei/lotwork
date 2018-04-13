<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"绑定邮箱"}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	{literal}
	<style>
	.ui-check-tip {color:#999;}
#J-button-resubmit{
border: 2px solid #008ad8;
background-color: #FAFAFA;
color: #008ad8;
}
.btn {
margin-left: 196px;}
	</style>
	{/literal}
	
	
	{include file='/default/script-base.tpl'}
	

<body>

{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			{include file='/default/ucenter/left.tpl'}
		</div>
		
		<div class="g_28 g_last">
			<div class="common-content page-email-add">
			{if $step ne '2'}
			<form id="J-form-step1" name="fm_step1" method="post" action="?step=2">
				<div class="title">设置邮箱</div>
				<div class="content">
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
					<ul class="ui-form">
						<li>
							<label for="name" class="ui-label">邮箱地址：</label>
							<input type="hidden" id="vemail" value="{$email}" />
							<input type="text" name="email" maxlength="50" value="{$email}" id="J-email" class="input">
                           <div class="ui-prompt" style="display:none">绑定后可用于找回密码</div> 
							<div class="ui-check"><i class="error"></i>请输入正确邮箱地址，如ab12@163.com</div>
						</li>
						<li class="ui-btn">
							<a href="javascript:void(0);" class="btn" id="J-button-submit">下一步<b class="btn-inner"></b></a>
						</li>
					</ul>
					<dl class="help-text">
						<dt>没有邮箱？推荐您申请以下常用邮箱:</dt>
						<dd><a href="https://mail.qq.com/" target="_blank">&gt;申请QQ邮箱</a></dd>
						<dd><a href="http://mail.163.com/" target="_blank">&gt;申请网易邮箱</a></dd>
						<dd><a href="http://mail.sohu.com/"  target="_blank">&gt;申请搜狐邮箱</a></dd>
						<dd><a href="http://mail.sina.com.cn"  target="_blank">&gt;申请新浪邮箱</a></dd>
					</dl>
				</div>
				<input type="hidden" value="2" name="step"/>
			</form>
            <script>
			var v_emailbind='{$email}';
            </script>

{literal}
<script>
(function($){
	var email = $('#J-email'),
	    phoenixEmail=null,s
		form = $('#J-form-step1'),
		errortype = false,	//基本验证，通过true
		errortype2 = false,	//唯一性验证，通过true	
		v_email = new phoenix.Validator({el:email,type:'email',expands:{showSuccessMessage:function(msg){
			var me = this;
			$('.ui-check', me.parentNode).hide();
			errortype = true;
		},showErrorMessage:function(msg){
			if(v_emailbind =='')
			{
				var me = this;
				$('.ui-check', me.parentNode).removeClass("ui-check-right").css('display', 'inline');
				errortype = false;
			}
		}}});
	if($('#vemail').val() =='') {
	     phoenixEmail=new phoenix.Input({el:'#J-email',defText:'请输入正确邮箱地址，如ab12@163.com',focusClass:'input-focus'});
	}else
	{
		 phoenixEmail=new phoenix.Input({el:'#J-email',defText:v_emailbind,focusClass:'input-focus'});
	}
	
	email.focus(function(){	
		email.parent().find('.ui-check').css('display', 'none');
		email.parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		v_email.validate;
		email.parent().find('.ui-prompt').css('display', 'none');
		if($.trim($(this).val()) == phoenixEmail.defConfig.defText && v_emailbind =='')
		{
			$('.ui-check').html('<i class="error"></i>请输入邮箱');
			$('.ui-check').css('display', 'inline');
			return false;
		}
		if(v_emailbind =='' && errortype == false){
			$('.ui-check').html('<i class="error"></i>请输入正确邮箱地址，如ab12@163.com');
			$('.ui-check').css('display', 'inline');
		}
		else{
			$.ajax({
				url:'/safepersonal/isexistemail/',
				dataType:'json',
				method:'POST',
				data:{mail:$('#J-email').val()},				
				success:function(data){
					if(data['status'] == '1'){	
					    $('.ui-check').removeClass().addClass('ui-check');
						$('.ui-check').html('<i class="error"></i>邮箱地址已被占用，换一个吧!');
						$('.ui-check').css('display', 'inline');
						errortype2 = false;
					} else{			
						errortype2 = true;
						showRight(email);
					}
				}
			});		
		}
		
	});
	
	var showRight = function(el, msg){
		var msg = typeof msg == 'undefined' ? '' : msg;
		$(el).parent().find('.ui-check').html(msg).removeClass().addClass('ui-check ui-check-right').css('display', 'inline');
	};
	
	$('#J-button-submit').click(function(e){
		//debugger
		$('.ui-check').hide();	
		e.preventDefault();
		v_email.validate();		
		if(phoenixEmail !=null && v_emailbind =='' && ($.trim($("#J-email").val())==phoenixEmail.defConfig.defText))
		{
			$('.ui-check').html('<i class="error"></i>请输入邮箱');
			$('.ui-check').css('display', 'inline');
			return false;
		}
		if(v_emailbind =='' && errortype ==false){
			$('.ui-check').html('<i class="error"></i>请输入正确邮箱地址，如ab12@163.com');
			$('.ui-check').css('display', 'inline');
			return false;
		}

		$.ajax({
			url:'/safepersonal/isexistemail/',
			dataType:'json',
			method:'POST',
			data:'mail='+$('#J-email').val(),				
			success:function(data){					
				if(data['status'] == '1'){	
					$('.ui-check').html('<i class="error"></i>邮箱地址已被占用，换一个吧!');
					$('.ui-check').css('display', 'inline');
					errortype2 = false;
				}else{
					errortype2 = true;
					if(v_email.validated && errortype ==true){
						form.submit();
					}			
				}
			}
		});		
	});	
	
})(jQuery);
</script>
{/literal}
				
				
				
				
				
				{else if $step eq '2'}
				<form id="J-form-step2" name="fm_step2" method="post" action="?">
					<div class="title">设置邮箱</div>
        				<div class="content">
        					<div class="step">
        						<table class="step-table">
        							<tbody>
        								<tr>
        									<td class="clicked"><div class="con">输入邮箱</div></td>
        									<td class="current"><div class="tri"><div class="con">查收验证邮件</div></div></td>
        									<td><div class="tri"><div class="con">绑定成功</div></div></td>
        								</tr>
        							</tbody>
        						</table>
        					</div>
        					<ul class="ui-form">
        						<li><p class="ui-text">验证邮件已发送至您的邮箱：<strong class="biglight">{substr_replace($email, '****', 2, 4)}</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</p></li>
        						<li class="ui-btn"><a class="btn"  href="/safepersonal/bindmail?step=1">返回修改邮箱<b class="btn-inner"></b></a></li>
        						<li><p class="ui-text">没有收到邮件？</p></li>
        						<li class="ui-btn"><a class="btn" id="J-button-resubmit" href="#" style="color:#CCC;">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);"><span id="J-time">60</span>s<b class="btn-inner"></b></a></li>
        					</ul>
        					<dl class="help-text">
        						<dt>如果半小时内没有收到邮件</dt>
        						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
        						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
        					</dl>
        			</div>
        			<input type="hidden" value="{$email}" name="email"/>
        			<input type="hidden" value="2" name="step"/>
    			</form>
				{/if}
			</div>
			

			
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	
	
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
		$('#J-form-step2').submit();
	});
	
})(jQuery);
</script>
{/literal}
	
	
	
	
</body>
</html>
