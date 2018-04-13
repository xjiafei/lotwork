<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>设置安全信息</title>
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
		<form action="/fund/confirm" method="post" id="J-form">
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">{$descript.head}</div>
				<div class="content">
					<div class="notice"><i class="ico-warning"></i>{$descript.title}</div>
					<ul class="ui-form">
						<li>{$descript.content}</li>
						<li><a href="{$descript.url}" class="btn">立即设置<b class="btn-inner"></b></a></li>
					</ul>
				</div>
			</div>
		</div>
	</form>
	</div> 
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
</body>
</html>