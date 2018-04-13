<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
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
		
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">我要提现</div>
				<div class="content">
					<div class="notice"><i class="ico-warning"></i>您还没有绑定银行卡，请绑定后再进行提现。</div>
					<ul class="ui-form">
						<li>为了保障您的账户资金安全，请先绑定银行卡后再进行提现。</li>
						<li><a href="/bindcard" class="btn">银行卡绑定<b class="btn-inner"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
		
	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->


<script  src="{$path_js}/js/phoenix.Common.js"></script>
{literal}

{/literal}
	
</body>
</html>