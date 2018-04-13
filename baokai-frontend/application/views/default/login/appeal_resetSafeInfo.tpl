<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>重置安全信息</title>
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
				<dd>请重新设置你的登录密码、安全密码、安全问题</dd>
				<dd>为保证账号安全，此链接仅能使用一次</dd>
				<dd>以下所有信息为必填项</dd>
			</dl>
			<h3 class="ui-title">重置登录密码</h3>
			<ul class="ui-form">
				<li>
					<label for="" class="ui-label">请输入新的登录密码：</label>
					<input type="text" value="" id="" class="input">
				</li>
				<li>
					<label for="" class="ui-label">再次输入新登录密码：</label>
					<input type="text" value="" id="" class="input">
				</li>
			</ul>
			<h3 class="ui-title">重置安全密码</h3>
			<ul class="ui-form">
				<li>
					<label for="" class="ui-label">请输入新的安全密码：</label>
					<input type="text" value="" id="" class="input">
				</li>
				<li>
					<label for="" class="ui-label">再次输入新安全密码：</label>
					<input type="text" value="" id="" class="input">
				</li>
			</ul>
			<h3 class="ui-title">重置安全问题</h3>
			<ul class="ui-form set-safeissue">
				<li>
					<label class="ui-label" for="question1">问题一：</label>
					<select class="ui-select" id="question1" name="">
						<option value="您叫什么名字">您叫什么名字1</option>
						<option value="您叫什么名字">您叫什么名字2</option>
						<option value="您叫什么名字">您叫什么名字3</option>
					</select>
				</li>
				<li>
					<label class="ui-label" for="answer1">答案：</label>
					<input type="text" class="input" id="answer1" value="">
					<div class="ui-check"><i class="error"></i>请输入答案</div>
				</li>
				<li>
					<label class="ui-label" for="question2">问题二：</label>
					<select class="ui-select" id="question2" name="">
						<option value="您叫什么名字">您叫什么名字1</option>
						<option value="您叫什么名字">您叫什么名字2</option>
						<option value="您叫什么名字">您叫什么名字3</option>
					</select>
				</li>
				<li>
					<label class="ui-label" for="answer2">答案：</label>
					<input type="text" class="input" id="answer2" value="">
					<div class="ui-check"><i class="error"></i>请输入答案</div>
				</li>
				<li>
					<label class="ui-label" for="question3">问题三：</label>
					<select class="ui-select" id="question3" name="">
						<option value="您叫什么名字">您叫什么名字1</option>
						<option value="您叫什么名字">您叫什么名字2</option>
						<option value="您叫什么名字">您叫什么名字3</option>
					</select>
				</li>
				<li class="ui-btn"><a href="javascript:void(0);" class="btn">保 存<b class="btn-inner"></b></a></li>
			</ul>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>

</body>
</html>