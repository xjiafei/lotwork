<!DOCTYPE HTML>
<html lang="en-US">
<head>
    <meta charset="UTF-8">
    <title>{$title|default:"错误页面"}</title>
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
                {if $errView neq ''}
                    {include file="/admin/$errView/left.tpl"}
                {else}
                    {include file='/admin/left.tpl'}
                {/if}
            <div class="col-main">
                <div class="content">
                    {$tab}
                    <div class="alert alert-error">
                        <i></i>
                        <div class="txt">
                            <h4 >{$msgTitle}</h4><br/>
                             <p>{$msgContent}</p>
                            {foreach $errList as $err}
                              <font color="red"><b>{$err}</b></font><br/>
                            {/foreach}<br/>
                            
                            {if $redURL neq ''}{foreach $redURL as $item}
                              	<a class="{if $item.isBtn eq '1'}btn btn-small{else}{/if}" href="{$item.url}">{$item.title}<b class="btn-inner"></b></a> {if $item@last}{else}|{/if}
                            {/foreach}
                            {/if}
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