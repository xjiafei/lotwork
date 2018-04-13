<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
</head>
<body>
<!-- //////////////头部公共页面////////////// -->
{include file='/default/ucenter/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="g_33 common-main">
		<div class="g_5">
		<!-- //////////////左侧公共页面////////////// -->
			{include file='/default/ucenter/left.tpl'}
		<!-- /////////////左侧公共页面/////////////// -->				
		</div>
		<div class="g_28 g_last">
			<div class="common-article">
				<div class="title">
					<div class="more">
						{if $smarty.session.datas.info.userLvl neq 0}
						<a href="/Service/servicesup?unread={$unread}">给上级发信</a>
						{/if}
						{if $smarty.session.datas.info.userLvl >= 0}
						<a href="/Service/servicesub?unread={$unread}">给下级发信</a>
						{/if}
					</div>	站内信(<span id="msgcount2">0</span>)
				</div>
				<div class="content">
					<div class="ui-tab">
						<div class="ui-tab-content">
							<ul class="ui-form form-mail-info">
								<li>
									<label for="" class="ui-label">主题：</label>
									<span class="ui-text-info">{$title}</span>
								</li>
								<li>
									<label for="" class="ui-label">发件人：</label>
									<span class="ui-text-info">{$sender}</span>
								</li>
								<li>
									<label for="" class="ui-label">时间：</label>
									<span class="ui-text-info">{date("Y-m-d G:i:s ",$receiveTime)}</span>
								</li>
								<li>
									<label for="" class="ui-label">收件人：</label>
									<span class="ui-text-info">{$receiver}</span>
								</li>
							</ul>
							<p class="form-mail-text">{$content}</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
	{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
	
	<div style="position:absolute;left:100px;display:none" class="pop w-4">
		<i class="ico-success"></i>
		<h4 class="pop-text">删除成功</h4>
	</div>
	
	<div style="position:absolute;left:400px;display:none" class="pop w-4">
		<i class="ico-error"></i>
		<h4 class="pop-text">删除失败，请重试</h4>
	</div>
	
	<div style="position:absolute;left:700px;display:none" class="pop w-8">
		<div class="hd"><i class="close"></i>收件人列表</div>
		<div class="bd">
			<div class="textarea">
				<textarea>wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,vava11111,fku3333,ww2233.wakaka,</textarea>
			</div>
		</div>
	</div>

</body>
</html>