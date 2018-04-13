<!DOCTYPE HTML>
<html lang="UTF-8" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>{$title|default:"资金中心"}</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css?20151204" />
	<link rel="stylesheet" href="{$path_img}/images/common/js-ui.css" />
	<link rel="stylesheet" href="{$path_img}/images/admin/admin.css" />
	<link rel="stylesheet" href="{$path_img}/css/pagination.css" />
	
</head>
<body>
	<div class="menu">
		<div class="menu-logo"></div>
		<ul class="menu-list">
			<li><a href="/admin/index/" >首页</a></li>
			<!-- {if "GLOBAL"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/globeAdmin/goRegLoginConfig/">全局管理</a></li>
			<!-- {/if} -->
			<!-- {if "USER"|in_array:$smarty.session.datas.info.acls} -->
			<li><a class='MenuUser' href="/admin/user/">用户中心</a></li>
			<!-- {/if} -->
			<!-- {if "GAME"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/gameoa/lotteryList?lotteryId=&status=">游戏中心</a></li>
			<!-- {/if} -->
			<!-- {if "RISK"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/gameRisk/toSeriesConfigRisk/">审核中心</a></li>
			<!-- {/if} -->
			<!-- {if "FUND"|in_array:$smarty.session.datas.info.acls} -->
			<li><a class='Menufunds' href="/admin/Rechargemange/">资金中心</a></li>
			<!-- {/if} -->
			<!-- {if "MARKET"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/adAdmin/">营销中心</a></li>
			<!-- {/if} -->
			<!-- <li><a href="">数据中心</a></li> -->
			<!-- {if "HELP"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/helpAdmin/goHelpManager/">内容中心</a></li>
			<!-- {/if} -->
			<!-- {if "ACL"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/aclAdmin/goUserManager/">权限中心</a></li>
			<!-- {/if} -->
			<!-- {if "NOTICE_CENTER"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/noticeAdmin/goSysMsgManager/">通知中心</a></li>
			<!-- {/if} -->
		<div class="menu-quit"><a href="/admin/login/logout">退出</a><i class="ico-user"></i>你好，
		{$smarty.session.datas.info.account}
		</div>
	</div>
	{include file='token.tpl'}
