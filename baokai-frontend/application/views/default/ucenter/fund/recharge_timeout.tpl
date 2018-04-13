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
				<div class="title">充值超时</div>
				
				<div class="content">
					<p style="color:red">您的此次充值已超时！为了保证充值成功，请发起新的充值申请。</p>
					<br />
					<br />
					<p>
						<a href="/fund" class="btn btn-important">重新发起充值<b class="btn-inner"></b></a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<a target="_blank" href="/fund/history/">充值记录</a>
					</p>
					<br />
					<br />
					<p>如您已经成功充值，请耐心等待。</p>
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