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
					<h4>恭喜您，邮箱绑定成功！</h4> 
					<p>您绑定的邮箱是：<strong>{substr_replace($email, '****', 2, 4)}</strong></p>
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