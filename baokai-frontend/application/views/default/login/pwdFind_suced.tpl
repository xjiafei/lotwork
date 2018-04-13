<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>重置安全信息成功</title>
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
			<div class="common-content">
				<div class="content">
					<div class="alert alert-success">
						<i></i>
						<div class="txt">
							<h4>恭喜您，密码重置成功！</h4>
							<p><a class="btn" href="javascript:void(0);">立即登录<b class="btn-inner"></b></a></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script>
(function($){
	var footer = $('#footer');
	footer.css('position','fixed');
	if($(document).height()>$(window).height()){
		footer.css('position','static');
	}
})(jQuery);
</script>

</body>
</html>