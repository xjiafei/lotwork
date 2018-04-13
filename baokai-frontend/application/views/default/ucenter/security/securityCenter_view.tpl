<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>安全中心</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	
</head>
<body>




<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="g_33 common-main">
	
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
			{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->	
		</div>
		
		
		<div class="g_28 g_last">
			<div class="common-content clearfix">
				<div class="title">安全中心</div>
				<div class="content">
					
					
					<!-- ucenter-safe-safe start -->
					<div class="safe-safe">
					
						<div class="clearfix info">
							{if $safeLevel eq 500}
								<div class="star star1">高</div>
							{elseif $safeLevel eq 300 or $safeLevel eq 400}
								<div class="star star2">中</div>
							{else}
								<div class="star star3">低</div>
							{/if}
							<div class="text">
							{if $safeLevel eq 500}
								<p class="highbig color-green">您的账号安全级别为高，你好棒哦，请继续保持。</p>
							{elseif $safeLevel eq 300 or $safeLevel eq 400}
								<p class="highbig color-orange">您的账号安全级别为中，可以通过完善安全信息提高级别。</p>
							{else}
								<p class="highbig color-red">您的账号安全级别为低，请完善安全信息。</p>
							{/if}
								<p>上次登录：
								<!-- {if $lastLoginTime eq ''} -->
									首次登录平台
								<!-- {else} -->
									{$lastLoginTime}
								<!-- {/if} --> 
								<!-- {if $lastLoginLocat neq ''}   -->
									，{$lastLoginLocat}  
								<!-- {/if} -->
								|  不是我登录？ <a href="/safepersonal/safecodeedit" id="changePWLink">修改密码</a></p>
							</div>
						</div>
						
						<div class="item item-login">
							<i class="ico-pwd"></i>
							<div class="item-info">
								<p class="type">登录密码</p>
								<p class="tip">建议您使用字母和数字的组合、混合大小写、在组合中加入下划线等符号。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/safecodeedit" class="btn" id="changePWBtn">修改密码<b class="btn-inner"></b></a>
							</div>
						</div>
						
						
					
					{if $isSetSafeCode == '1'}
						<div class="item item-safe">
							<i class="ico-safecode"></i>
							<div class="item-info">
								<p class="type">安全密码</p>
								<p class="tip">在进行银行卡绑定，转账等资金操作时需要进行安全密码确认，以提高您的资金安全性。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/safecodeedit?issafecode=1" class="btn" id="changeSafePW">修改安全密码<b class="btn-inner"></b></a>
								<a href="/safepersonal/sefecoderetrieve?stp=2" class="btn " id="getSafePW">找回安全密码<b class="btn-inner"></b></a>
							</div>
						</div>
					{else}
						<div class="item item-none item-safe-none">
							<i class="ico-safecode"></i>
							<div class="item-info">
								<p class="type">未设置安全密码</p>
								<p class="tip">在进行银行卡绑定，转账等资金操作时需要进行安全密码确认，以提高您的资金安全性。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/safecodeset" class="btn " id="setSafePW">设置安全密码<b class="btn-inner"></b></a>
							</div>
						</div>
					{/if}
					
						
					
					{if $isSetSafeQuest == '1'}
						<div class="item item-question">
							<i class="ico-safequestion"></i>
							<div class="item-info">
								<p class="type">安全问题</p>
								<p class="tip">绑定安全问题后可以通过安全问题找回账号资料。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/safequestedit" class="btn " id="changeSafeQuestion">修改安全问题<b class="btn-inner"></b></a>
							</div>
						</div>
					{else}
						<div class="item item-none item-question-none">
							<i class="ico-safequestion"></i>
							<div class="item-info">
								<p class="type">未设置安全问题</p>
								<p class="tip">绑定安全问题后可以通过安全问题找回账号资料。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/safequestset" class="btn " id="setSafeQuestion">设置安全问题<b class="btn-inner"></b></a>
							</div>
						</div>
					{/if}
					
					
					{if $isBindMail == '1'}
						<div class="item item-email">
							<i class="ico-mail"></i>
							<div class="item-info">
								<p class="type">已绑定邮箱： {$email} </p>
								<p class="tip">绑定邮箱可增加账号安全级别，也可以确保在邮箱正常的情况下取回登录密码。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/rebindmail" class="btn " id="changeEmail">修改邮箱<b class="btn-inner"></b></a>
							</div>
						</div>
					{else}
						<div class="item item-none item-email-none">
							<i class="ico-mail"></i>
							<div class="item-info">
								<p class="type">未绑定邮箱</p>
								<p class="tip">绑定邮箱可增加账号安全级别，也可以确保在邮箱正常的情况下取回登录密码。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/bindmail" class="btn " id="setEmail">绑定邮箱<b class="btn-inner"></b></a>
							</div>
						</div>
					{/if}
					
					
				<!--	{if $isSetSlogan == '1'}
						<div class="item item-message">
							<i class="ico-info"></i>
							<div class="item-info">
								<p class="type">预留验证信息：{$slogan}</p>
								<p class="tip">在登录时选择安全登录，您会看到此处设置的预留验证信息，可帮助你识别平台的真实性。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/modifycipher" class="btn " style="font-weight:700;width:140px;padding: 0 3px;" id="changePreVerifyInfo">修改预留验证信息<b class="btn-inner"></b></a>
							</div>
						</div>
					{else}
						<div class="item item-none item-message-none">
							<i class="ico-info"></i>
							<div class="item-info">
								<p class="type">未填写预留验证信息</p>
								<p class="tip">在登录时选择安全登录，您会看到此处设置的预留验证信息，可帮助你识别平台的真实性。</p>
							</div>
							<div class="item-control">
								<a href="/safepersonal/savecipher" class="btn " style="font-weight:700;width:140px;padding: 0 3px;" id="setPreVerifyInfo">绑定预留验证信息<b class="btn-inner"></b></a>
							</div>
						</div>
					{/if} -->
					
					
					</div>
					
					
					
					<!-- ucenter-safe-safe end -->
				</div>
			</div>
		</div>
	</div>

<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
<!-- /////////////底侧公共页面/////////////// -->
	
</body>
</html>
