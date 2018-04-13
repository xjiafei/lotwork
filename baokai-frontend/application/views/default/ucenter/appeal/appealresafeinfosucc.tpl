<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>账号申诉</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
</head>
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
			<div class="alert alert-success">
				<i></i>
				<div class="txt"> 
				
					<h4>恭喜您，安全信息重置成功！</h4>
					<p>新的安全信息将{if $hours eq 0}立即生效，{else}在{$hours}小时后生效，{/if}{if $hours neq 0}在生效之前，您的账号将不能登录，生效后，将自动解除限制，{/if}如有问题，请联系客服，谢谢。</p>
				</div>
			</div>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>


</body>
</html>