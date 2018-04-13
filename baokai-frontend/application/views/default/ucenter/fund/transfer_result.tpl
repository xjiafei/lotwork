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
		<form action="/transfer/transferconform" method="post" id="J-form">
		<input type="hidden" name="accountName" value="{$accountName}"/>
		<input type="hidden" name="changeCount" value="{$changeCount}"/>
		<input type="hidden" name="tranto" value="{$tranto}"/>
		<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">平台转账</div>
					<div class="alert alert-mini alert-success">
						<i class="ico-success"></i>
						<div class="txt">
							<h4>转账发起成功！</h4>
						</div>
					</div>
					<div class="content level-transfer">
                        <ul class="ui-form ui-from-border">
                            <li>
                                <label class="ui-label">收款用户名：</label>
                                <span class="ui-singleline">
                                    <strong class="bighigh">{$accountName}</strong>
                                </span>
                            </li>
                            <li>
                                <label class="ui-label">转账金额：</label>
                                <span class="ui-singleline">
                                    <strong class="bighigh">{$changeCount}元</strong>
                                </span>
                            </li>
                        </ul>
                        <ul class="ui-form">
                            <li class="ui-btn">
                                <a href="/transfer/" class="btn btn-important">继续转账<b class="btn-inner"></b></a>
                                <a class="btn btn-link" href="/transfer/history/">查看转账记录</a>
                            </li>
                        </ul>
					</div>
			</div>
		</div>
		
		
	</form>
	</div> 

	</div>
		<!-- //////////////底侧公共页面////////////// -->
		{include file='/default/ucenter/footer.tpl'}
	<!-- /////////////底侧公共页面/////////////// -->
</body>
</html>