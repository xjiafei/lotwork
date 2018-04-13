<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
	{include file='/default/script-base.tpl'}
	
</head>
<body>

{include file='/default/ucenter/header.tpl'}
	<!-- header end -->
	<div class="g_33 common-main">
		<div class="g_5">
			{include file='/default/ucenter/left.tpl'}
		</div>
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">设置邮箱</div>
				<div class="content">
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
							<label for="name" class="ui-label">修改的邮箱：</label>
							<input type="text" value="4~16位字母或数字，首位为字母" id="name" class="input">
						</li>
						<li class="ui-btn"><a class="btn"  href="javascript:void(0);">下一步<b class="btn-inner"></b></a></li>
					</ul>
					<dl class="help-text">
						<dt>没有邮箱？推荐您申请以下常用邮箱:</dt>
						<dd><a href="#">&gt;申请QQ邮箱</a></dd>
						<dd><a href="#">&gt;申请网易邮箱</a></dd>
						<dd><a href="#">&gt;申请搜狐邮箱</a></dd>
						<dd><a href="#">&gt;申请新浪邮箱</a></dd>
					</dl>
				</div>
			</div>
			<div class="common-content">
				<div class="title">设置邮箱</div>
				<div class="content">
					<div class="step">
						<table class="step-table">
							<tbody>
								<tr>
									<td class="clicked"><div class="con">输入邮箱</div></td>
									<td class="current"><div class="tri"><div class="con">查收验证邮件</div></div></td>
									<td><div class="tri"><div class="con">绑定成功</div></div></td>
								</tr>
							</tbody>
						</table>
					</div>
					<ul class="ui-form">
						<li><p class="ui-text">验证邮件已发送至您的邮箱：<strong class="biglight">vava****@qq.com</strong><br />请找到来自宝开彩票的验证邮件，打开邮件后点击链接完成邮箱绑定。<br />您的激活链接在24小时内有效。</p></li>
						<li class="ui-btn"><a class="btn"  href="javascript:void(0);">返回修改邮箱<b class="btn-inner"></b></a></li>
						<li><p class="ui-text">没有收到邮件？</p></li>
						<li class="ui-btn"><a class="btn" href="javascript:void(0);">重新发送确认邮件<b class="btn-inner"></b></a> <a class="btn btn-disable" href="javascript:void(0);">60s<b class="btn-inner"></b></a></li>
					</ul>
					<dl class="help-text">
						<dt>如果半小时内没有收到邮件</dt>
						<dd>到邮箱的广告邮件、垃圾邮件列表中找找</dd>
						<dd>联系<a href="https://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=740552&configID=2945&jid=4611765122&skillId=145&s=1">在线客服</a>，由客服帮你解决</dd>
					</dl>
				</div>
			</div>
			<div class="common-content">
				<div class="title">设置邮箱</div>
				<div class="content">
					<div class="alert alert-success">
						<i></i>
						<div class="txt">
							<h4>恭喜您，邮箱绑定成功</h4>
							<p>您绑定的邮箱是<strong>VAVA****@QQ.COM</strong></p>
						</div>
					</div>
				</div>
			</div>
			<div class="common-content">
				<div class="title">设置邮箱</div>
				<div class="content">
					<div class="alert alert-error">
						<i></i>
						<div class="txt">
							<h4>绑定失败，邮箱激活链接已失效</h4>
							<p><a class="btn btn-small" href="javascript:void(0);">重新绑定<b class="btn-inner"></b></a></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
</body>
</html>
