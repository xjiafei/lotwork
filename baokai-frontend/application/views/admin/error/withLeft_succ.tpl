<!DOCTYPE HTML>
<html lang="en-US">
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"成功页面"}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/ucenter/safe/safe.css" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	
</head>
<body>
	{if $errView neq ''}
        {include file="/admin/$errView/header.tpl"}
    {else}
        {include file="/admin/header.tpl"}
    {/if}
	
	<div class="col-content">
			<!-- //////////////左侧公共页面////////////// -->
                {if $errView neq ''}
                    {include file="/admin/$errView/left.tpl"}
                {else}
                    {include file='/admin/left.tpl'}
                {/if}
			<div class="col-main">
				<div class="content">
					{$tab}
					<div class="alert alert-success">
						<i></i>
						<div class="txt">
							<h4>{$msgTitle}</h4>
							<p>{$msgContent}<br />
							{if $redURL|@count neq 0}现在您可以&nbsp;{foreach $redURL as $item}
                              	<a class="{if $item.isBtn eq '1'}btn btn-small{else}{/if}" href="{$item.url}">{$item.title}<b class="btn-inner"></b></a> {if $item@last}{else}|{/if}
                            {/foreach}
                            {/if}
							</p>
						</div>
					</div>
				</div>
			</div>
	</div>
	<!-- //////////////底侧公共页面////////////// -->
		 {if $errView neq ''}
            {include file="/admin/$errView/footer.tpl"}
        {else}
            {include file="/admin/footer.tpl"}
        {/if}
	<!-- /////////////底侧公共页面/////////////// -->	
</body>
</html>