<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"修改邮箱"}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
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
			<div class="common-content">
				<div class="title">修改邮箱</div>
				<div class="content">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="{if $step eq 1 or $step eq 2 or ($step eq 3 and $res eq 'fail')}current{else}tri{/if}"><div class="con">验证旧邮箱</div></td>
									<td><div class="{if $step eq 3 and $res eq 'succ'}current{else}tri{/if}"><div class="con">输入新邮箱</div></div></td>
									<td><div class="{if $step eq 4}current{else}tri{/if}"><div class="con">验证新邮箱</div></div></td>
									<td><div class="{if $step eq 5}current{else}tri{/if}"><div class="con">更新成功</div></div></td>
								</tr>
							</tbody>
						</table>

					</div>
					
					
					
					
					
						{if $step eq 1}
    					<ul class="ui-form">
    						<li class="ui-text"><strong class="highbig">在更改之前，请允许宝开彩票验证您是否是当前账户的拥有者。</strong><br />您绑定的邮箱：<strong class="biglight">{$oriEmail}</strong></li>
    						<li class="ui-btn"><a class="btn"  href="/safepersonal/rebindmail?step=2">点击发送验证邮件<b class="btn-inner"></b></a></li>
    						
    					</ul>
    					{else if $step eq 2}
    					<ul class="ui-form">
    						<li class="ui-text"><strong class="highbig">在更改之前，请允许宝开彩票验证您是否是当前账户的拥有者。</strong><br />验证邮件已发送至您的邮箱：<strong class="biglight">{$oriEmail}</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</li>
    						<li class="ui-text">没有收到邮件？</li>
    						
    						<li class="ui-btn"><a class="btn" id="J-button-resubmit"  style="color:#CCC;">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);"><span id="J-time">60</span>s<b class="btn-inner"></b></a></li>
    					</ul>
    					<dl class="help-text">
    						<dt>邮件可能会有延时，请耐心等候，如果长时间没有收到邮件，请参考以下步骤。</dt>
    						<dd>· 请确认您的电子邮件地址是否准确。</dd>
                            <dd>· 请检查是否您的邮箱已满不能接收新邮件。</dd>
                            <dd>· 请查看您的邮箱有拒收或过滤邮件的设置，有可能验证邮件被拒收或过滤到垃圾邮件中。</dd>
                            <dd>· 如果经过以上检查后仍不能收到验证电子邮件，您可以联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮您解决。</dd>
    					</dl>
    					{else if $step eq 3}
    						{if $res eq "fail"}
            					<div class="alert alert-error">
            						<i></i>
            						<div class="txt">
            							<h4>{$desc}</h4>
            							<p><a class="btn btn-small" href="/safepersonal/safecenter">返回安全中心<b class="btn-inner"></b></a></p>
            						</div>
            					</div>
            				{else if $res eq "succ"}
            				    <form id ="fm_stp3" name="fm_stp3" method="get">
            					<ul class="ui-form">
            						<li class="ui-text"><p class="highbiglight">旧邮箱验证成功，现在你可以重新设置新的邮箱了</p></li>
            						<li><label class="ui-label">旧邮箱：</label><strong class="ui-singleline">{$oriEmail}</strong><input type="hidden" id="J-email-old-text" value="{$oriEmailFull}" /></li>
            						<li><label for="newMail" class="ui-label">输入新邮箱：</label><input type="text" value="" name="newEmail" id="newEmail" class="input" maxlength="50">
                                     <div class="ui-prompt" style="display:none">请输入正确邮箱地址，如ab12@163.com</div>      
            						<div class="ui-check"><i class="error"></i>请输入正确邮箱地址，如ab12@163.com</div><span class="ui-check-right" style="display: none;"></span>
            						</li>
            						<li class="ui-btn"><a class="btn" href="javascript:void(0);" id="J-button-submit">更改邮箱<b class="btn-inner"></b></a><a class="btn btn-link" href="/safepersonal/rebindmail">上一步</a></li>
        						</ul>
            					<dl class="help-text">
            						<dt>如果半小时内没有收到邮件</dt>
            						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
            						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
            					</dl>
            					<input type="hidden" name ="step" value="4" />
            					</form>
            				{/if}
        				{else if $step eq 4}
        				<form id ="fm_stp4" name="fm_stp4" method="POST">
            				<ul class="ui-form">
        						<li class="ui-text">验证邮件已发送至您的邮箱：<strong class="biglight"> {$oriEmail}</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</li>
        						<li class="ui-btn"><a class="btn" href="/safepersonal/rebindmail">上一步<b class="btn-inner"></b></a></li>
        						<li class="ui-text">没有收到邮件？</li>
        						<li class="ui-btn"><a class="btn" href="javascript:void(0);" onClick="$('#fm_stp4').submit();">重新发送确认邮件<b class="btn-inner"></b></a></li>
        					</ul>
        					<dl class="help-text">
        						<dt>如果半小时内没有收到邮件</dt>
        						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
        						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
        					</dl>
        					<input type="hidden" name ="step" value="4" />
        					<input type="hidden" name ="newEmail" value="{$newEmail}" />
        				</form>
        				{else if $step eq 5}
        					{if $res eq "succ"}
        						<div class="alert alert-success">
            						<i></i>
            						<div class="txt">
            							<h4>修改邮箱成功</h4>
            							<p>您绑定的邮箱是：<strong>{$newEmail}</strong><br /><a href="/safepersonal/safecenter" class="btn btn-small">返回安全中心<b class="btn-inner"></b></a></p>
            						</div>
            					</div>
        					{else if $res eq "fail"}
        						<div class="alert alert-error">
            						<i></i>
            						<div class="txt">
            							<h4>{$desc}</h4>
            							<p><a class="btn btn-small" href="/safepersonal/safecenter">返回安全中心<b class="btn-inner"></b></a></p>
            						</div>
            					</div>
            				{/if}
    					{/if}
					
					
					
					
					
					
					
					
					
				</div>
			</div>
		</div>
	</div>
{literal}
<script>
//isexistemail
(function($){
	var email = $('#newEmail'),
		form = $('#fm_stp3'),
		error = '1',
		error1='',
		v_email = new phoenix.Validator({el:email,type:'email',expands:{showSuccessMessage:function(msg){
			var me = this;
			$('.ui-check', me.parentNode).hide();
			error1 = '';
		},showErrorMessage:function(msg){
			var me = this;
			$('.ui-check', me.parentNode).css('display', 'inline');
			error1 = '1';
		}}});
	//new phoenix.Input({el:'#newEmail',defText:'请输入正确邮箱地址，如ab12@163.com',focusClass:'input-focus'});
	//邮箱已经存在,请重新输入!
	$('#newEmail').focus(function(){	
	    $(this).parent().find('.ui-check-right').hide();
		$(this).parent().find('.ui-check').css('display', 'none');
		$(this).parent().find('.ui-prompt').css('display', 'inline');
	}).blur(function(){
		$(this).parent().find('.ui-prompt').css('display', 'none');	
		$(this).parent().find('.ui-check-right').hide();
		if($.trim(email.val()) == ''){
			$('.ui-check').html('<i class="error"></i>请输入邮箱');
			$('.ui-check').css('display', 'inline');
			return;
		}
		if(error1 == '1'){
			$('.ui-check').html('<i class="error"></i>请输入正确邮箱地址，如ab12@163.com');
			$('.ui-check').css('display', 'inline');
			return false;
		}
		if($.trim(email.val()).toLowerCase() == $.trim($('#J-email-old-text').val()).toLowerCase()){
			$('.ui-check').html('<i class="error"></i>新邮箱不能和老邮箱一致');
			$('.ui-check').css('display', 'inline');
			return false;
		}
		if(v_email.validated)
		{  
		   postAjax();
		}
	});
	postAjax = function(){
		$.ajax({
			url:'/safepersonal/isexistemail/',
			dataType:'json',
			method:'POST',
			async:false,
			data:{mail:$('#newEmail').val()},
			success:function(data){
				if(data['status'] == '1'){
					$('.ui-check').html('<i class="error"></i>邮箱地址已被占用，换一个吧!');
					$('.ui-check').css('display', 'inline');
					error = 1;
				} else if(data['status'] == '0'){
					$('.ui-check-right').show();
					error ='';
				}
			}
		});
	};
	$('#J-button-submit').click(function(e){
		e.preventDefault();
		v_email.validate();
		
		if($.trim(email.val()).toLowerCase() == $.trim($('#J-email-old-text').val()).toLowerCase()){
			$('.ui-check').html('<i class="error"></i>新邮箱不能和老邮箱一致');
			$('.ui-check').css('display', 'inline');
			return false;
		}
		
		if($.trim(email.val()) == ''){
			$('.ui-check').html('<i class="error"></i>请输入邮箱');
			$('.ui-check').css('display', 'inline');
			return;
		}
		
		if(error =='1'){
			$('.ui-check').html('<i class="error"></i>请输入正确邮箱地址，如ab12@163.com');
			$('.ui-check').css('display', 'inline');
		}
		if(v_email.validated)
		{  
		   postAjax();
		}
		if(v_email.validated && error ==''){
			form.submit();
		}
	});
	
	
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
		if(sc > 0){
			return false;
		}		
		$('#J-button-resubmit').attr("href","/safepersonal/rebindmail?step=2");
	});
})(jQuery);
</script>
{/literal}
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->	
</body>
</html>
