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
				<dl class="prompt">
					<dt>温馨提示：定期跟换安全密码可以让您的账户更加安全。</dt>
					<dd>请确保安全密码与登录密码不同！</dd>
					<dd>建议密码使用字母和数字的组合、混合大小写、在组合中加入下划线等符号；</dd>
					<dd>安全信息是找回登录密码和资金流通安全凭证请妥善保存，切勿告诉他人。</dd>
				</dl>
			<h3 class="ui-title">填写申诉材料<span class="color-red">请填写您的真实资料，这将有助于帐号申诉。娱乐平台不会泄漏您的个人信息。</span></h3>
			<ul class="ui-form">
				<li>
					<label for="name" class="ui-label">用户名：</label>
					<input type="text" value="请输入你的用户名" id="name" class="input">
					<div class="ui-check"><i class="error"></i>用户名不能为空</div>
				</li>
				<li>
					<label class="ui-label">申诉类型：</label>
					<label class="label"><input type="radio" class="radio">安全信息（登录密码、安全密码、安全问题）</label>
					<label class="label"><input type="radio" class="radio">安全邮箱</label>
				</li>
				<li>
					<label class="ui-label">上传身份证扫描件：</label>
					<span class="ui-text-info">文件大小请控制在2MB内，支持JPG/PNG/GIF</span>
				</li>
				<li>
					<label class="ui-label"></label>
					<input type="file" value="请输入你的用户名" class="file">
				</li>
				<li>
					<label class="ui-label"></label>
					<a class="impress" href="javascript:void(0);">已上传文件名字.jpg<i class="close"></i></a>
				</li>
				<li>
					<label for="" class="ui-label">已绑定银行卡信息：</label>
					<input type="text" value="银行卡开户名" id="" class="input account-name">
					<input type="text" value="银行卡号" id="" class="input card-number">
					<div class="ui-check"><i class="error"></i>银行卡信息不为空</div>
				</li>
				<li>
					<label class="ui-label">已绑定银行卡扫描件：</label>
					<span class="ui-text-info">文件大小请控制在2MB内，支持JPG/PNG/GIF</span>
				</li>
				<li>
					<label for="" class="ui-label"></label>
					<input type="file" value="" class="file">
					<span class=".ui-text-info">（正面照片）</span>
				</li>
				<li>
					<label for="" class="ui-label"></label>
					<input type="file" value="" class="file">
					<span class="ui-text-info">（背面照片）</span>
				</li>
				<!-- 省市联动 -->
				<li>
					<label class="ui-label">账号注册地点：</label>
					<select class="ui-select" id="" name="">
						<option value="上海">上海</option>
						<option value="广州">广州</option>
						<option value="北京">北京</option>
					</select>
					<select class="ui-select" id="" name="">
						<option value="上海">上海</option>
					</select>
					<div class="ui-check"><i class="error"></i>请选择注册地点</div>
				</li>
				<li>
					<label class="ui-label">经常登录地区：</label>
					<select class="ui-select" id="" name="">
						<option value="上海">上海</option>
						<option value="广州">广州</option>
						<option value="北京">北京</option>
					</select>
					<select class="ui-select" id="" name="">
						<option value="上海">上海</option>
					</select>
					<div class="ui-check"><i class="error"></i>请选择登录地区</div>
				</li>
			</ul>
			<h3 class="ui-title">申诉结果接收方式<span>申诉进展和结果将通过您填写的联系方式通知到您</span></h3>
			<ul class="ui-form">
				<li>
					<label class="ui-label">接受结果邮箱：</label>
					<input type="text" value="请填写接受申诉结果的邮箱" class="input">
					<div class="ui-check"><i class="error"></i>请填写接受结果邮箱</div>
				</li>
				<li>
					<label for="name" class="ui-label">再次输入邮箱：</label>
					<input type="text" value="请输入你的用户名" class="input">
				</li>
				<li class="ui-btn"><a href="javascript:void(0);" class="btn">提交申诉<b class="btn-inner"></b></a></li>
			</ul>
		</div>
	</div>

	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
<script type="text/javascript" src="{$path_js}/js/jquery-1.9.1.min.js"></script>

</body>
</html>