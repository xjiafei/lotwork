<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>重置邮箱</title>
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
			<ul class="ui-form">
				<li>
					<label for="name" class="ui-label">邮箱地址：</label>
					<input type="text" value="" id="name" class="input">
				</li>
				<li class="ui-btn"><a href="javascript:void(0);" class="btn">下一步<b class="btn-inner"></b></a></li>
			</ul>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
		
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>

</body>
</html>