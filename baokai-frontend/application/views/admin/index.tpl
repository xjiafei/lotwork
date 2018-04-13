<!DOCTYPE HTML>
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>首页</title>
	<link rel="stylesheet" href="{$path_img}/images/common/base.css" />
	<link rel="stylesheet" href="{$path_img}/images/admin/admin.css"/>
</head>
<body class="index">
	<div class="menu">
		<div class="menu-logo"></div>
		<ul class="menu-list">
			<li><a href="/admin/index/" >首页</a></li>
			<!-- {if "GLOBAL"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/globeAdmin/goRegLoginConfig/">全局管理</a></li>
			<!-- {/if} -->
			<!-- {if "USER"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/admin/user/">用户中心</a></li>
			<!-- {/if} -->
			<!-- {if "GAME"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/gameoa/lotteryList">游戏中心</a></li>
			<!-- {/if} -->
			<!-- {if "RISK"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/gameRisk/toSeriesConfigRisk/">审核中心</a></li>
			<!-- {/if} -->
			<!-- {if "FUND"|in_array:$smarty.session.datas.info.acls} -->
			<li><a href="/admin/Rechargemange/">资金中心</a></li>
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
		</ul>
	</div>
<div class="col-content">
		<div class="col-side">
			<div id="calendar" class="calendar"></div>
			<div class="btn-calendar">
			<!-- {if "ACL_MODFIYPASSWORD"|in_array:$smarty.session.datas.info.acls} -->
				<a href="/aclAdmin/goModifyPwd" class="btn btn-important">修改密码<b class="btn-inner"></b></a>
			<!-- {/if} -->
				<a href="/admin/login/logout" class="btn">退出系统<b class="btn-inner"></b></a>
			</div>
		</div>
		<div class="col-main">
			<div class="index-content">
				<h1>欢迎登陆<strong>宝开彩票</strong>管理后台</h1>
				<div class="login-info">
					登陆者：<strong class="color-green">{$smarty.session.datas.info.account}</strong>，所属用户组：<strong class="color-green">{$smarty.session.datas.info.groupName}</strong><br />
					这是你今天第<strong class="color-green">{$smarty.session.datas.cLoginTimes|default:'1'}</strong>次登录系统，你上次的登录时间：<strong class="color-green">{$lastLoginDate}</strong>，登录IP：{if $issameIp eq '1'}<strong class="color-red">{$lastLoginIp}（与上次登录不一致）</strong>{else}<strong class="color-green">{$lastLoginIp}</strong>{/if}
				</div>
				<ul class="link-info">
				<!-- {if "GLOBAL"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-1">
						<dl>
							<dt>全局管理：</dt>
							<!-- {if "GLOBAL_REGEISTLOGINSETTING"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/globeAdmin/goRegLoginConfig">注册登录设置</a></dd>
							<!-- {/if} -->
							<!-- {if "GLOBAL_SENSITIVEWORDSETTING"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/globeAdmin/goSensitiveWord">敏感词管理</a></dd>
							<!-- {/if} -->
							<!-- {if "GLOBAL_IPSWITCH"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/globeAdmin/goIpSwitch">IP黑白名单</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "USER"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-2">
						<dl>
							<dt>用户中心：</dt>
							<!-- {if "USER_MANAGE_LIST"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/user/list">客户列表</a></dd>
							<!-- {/if} -->
							<!-- {if "USER_MANAGE_TOPAGENT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/proxy/index">总代管理</a></dd>
							<!-- {/if} -->
							<!-- {if "USER_EXCEPTION_FREEZEUSER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/user/freezeuserlist">冻结用户管理</a></dd>
							<!-- {/if} -->
							<!-- {if "USER_EXCEPTION_APPEAL"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/user/accomplaints">账号申诉管理</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "GAME"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-3">
						<dl>
							<dt>游戏中心：</dt>
							<!-- {if "GAME_LOTMGR_LOTLIST"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/lotteryList">彩种信息列表</a></dd>
							<!-- {/if} -->
							<!-- {if "GAME_ISSUEMGR_MONITOR"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/queryLotteryIssueWarn">流程监控</a></dd>
							<!-- {/if} -->
							<!-- {if "GAME_ISSUEMGR_WARNLOG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/queryLotteryIssueWarnLog">异常流程记录</a></dd>
							<!-- {/if} -->
							<!-- {if "GAME_OPMGR_ORDERLIST"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/queryOrderList">方案记录</a></dd>
							<!-- {/if} -->
							<!-- {if "GAME_OPMGR_PLANLIST"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/queryPlanList">追号记录</a></dd>
							<!-- {/if} -->
							<!-- {if "GAME_OPMGR_WARNLIST"|in_array:$smarty.session.datas.info.acls} -->
							<!-- <dd><a href="/gameoa/queryWarnOrderList">异常记录</a></dd> -->
							<!-- {/if} -->
							<!-- {if "GAME_REPORT_WINS"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameoa/queryWinsReport">单期盈亏表</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "RISK"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-4">
						<dl>
							<dt>审核中心：</dt>
							<!-- {if "RISK_GAME_WARN"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameRisk/queryGameExceptionAuditList">游戏异常审核</a></dd>
							<!-- {/if} -->
							<!-- {if "RISK_GAME_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameRisk/toSeriesConfigRisk">游戏审核设置</a></dd>
							<!-- {/if} -->
							<!-- {if "RISK_GAME_REPORT_DETAIL"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameRisk/gotoGameRiskTransactionReport">游戏明细表</a></dd>
							<!-- {/if} -->
							<!-- {if "RISK_GAME_REPORT_EXCEPTION"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/gameRisk/queryWarnOrderList">异常记录</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "FUND"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-5">
						<dl>
							<dt>资金中心：</dt>
							<!-- {if "FUND_RECHARGE_EXCEPTION"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Rechargemange/index">异常充值处理</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_RECHARGE_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Rechargemange/index?parma=sv2">充值相关配置</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_RECHARGE_REMARK"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="admin/Remark/index?parma=sv1">唯一附言管理</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_WITHDRAW_RISK"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Rechargemange/index?parma=sv3">风险提现处理</a></dd>
							<!-- {/if} -->
							<!-- {if "GLOBAL"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Fundconfig/index?parma=sv1">提现相关配置</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_MANUAL_PROCEDURE"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Opterators/index?parma=sv1">人工资金操作审核流程</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_BANKCARD_BANKMANAGE"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Bankcardsmanage/index?parma=sv41">银行管理</a></dd>
							<!-- {/if} -->
							<!-- {if "FUND_BANKCARD_BINDMANAGE"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/admin/Bankcardsmanage/index?parma=sv42">用户银行卡绑定管理</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "MARKET"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-6">
						<dl>
							<dt>营销中心：</dt>
							<!-- {if "MARKET_ADVERTMANAGERPUB"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/queryPublishAdPage">广告管理(发布)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_ADVERTMANAGERAUDIT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/queryReviewAdPage">广告管理(审核)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_ADSPACEMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/queryAllAdSpace">广告位管理</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_NOTICEANAGERPUB"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goNoticeListPublish">公告管理(发布)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_NOTICEANAGERAUDIT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goNoticeListAudit">公告管理(审核)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_ADDNOTICE"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goCreateNotice">新建公告</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_SUBJECTMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goTopicManager">专题管理(发布)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_MODIFYSUBJECT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goTopicView">专题管理(查看)</a></dd>
							<!-- {/if} -->
							<!-- {if "MARKET_ADDSUBJECT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/adAdmin/goCreateTopic">新建专题</a></dd>
							<!-- {/if} -->
							<!-- {if "CHANNEL_CONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/channel/toSaveChannelTemplate">设定参数</a></dd>
							<!-- {/if} -->
							<!-- {if "CHANNEL_SELECT"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/channel/channelView">查询参数</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "HELP"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-7">
						<dl>
							<dt>内容中心：</dt>
							<!-- {if "HELP_MANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/helpAdmin/goHelpManager/">帮助管理</a></dd>
							<!-- {/if} -->
							<!-- {if "HELP_NEWHELP"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/helpAdmin/goCreateHelp">新建帮助</a></dd>
							<!-- {/if} -->
							<!-- {if "HELP_CATEGORY"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/helpAdmin/queryCategory?cate2Name=类目列表">类目列表</a></dd>
							<!-- {/if} -->
							<!-- {if "HELP_HELPCONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/helpAdmin/queryHelpConfig?cate2Name=帮助配置">帮助配置</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "ACL"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-8">
						<dl>
							<dt>权限中心：</dt>
							<!-- {if "ACL_ACLGROUPMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/queryAclGroup?userId={$smarty.session.datas.info.id}">权限组管理</a></dd>
							<!-- {/if} -->
							<!-- {if "ACL_CREATEACLGROUP"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/toAddGroup?userId={$smarty.session.datas.info.id}">创建权限组</a></dd>
							<!-- {/if} -->
							<!-- {if "ACL_USERMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/goUserManager">用户管理</a></dd>
							<!-- {/if} -->
							<!-- {if "ACL_ADDUSER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/goCreateUser">添加用户</a></dd>
							<!-- {/if} -->
							<!-- {if "ACL_OPERATELOG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/goLog">操作日志</a></dd>
							<!-- {/if} -->
							<!-- {if "ACL_MODFIYPASSWORD"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/aclAdmin/goModifyPwd">修改密码</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				<!-- {if "NOTICE_CENTER"|in_array:$smarty.session.datas.info.acls} -->
					<li class="link-info-9">
						<dl>
							<dt>通知中心：</dt>
							<!-- {if "NOTICE_SENDSITEMSG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/goCreateMsg/">发送站内信</a></dd>
							<!-- {/if} -->
							<!-- {if "NOTICE_SYSMSGMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/goSysMsgManager/">系统消息管理</a></dd>
							<!-- {/if} -->
							<!-- {if "NOTICE_USERMSGMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/goUserMsg">用户消息管理</a></dd>
							<!-- {/if} -->
							<!-- {if "NOTICE_EMAILCONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/toSaveEmailTemplate">邮件配置</a></dd>
							<!-- {/if} -->
							<!-- {if "NOTICE_EMAILCONFIG"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/toTestEmailTemplate">邮件测试</a></dd>
							<!-- {/if} -->
							<!-- {if "NOTICE_TASKMANAGER"|in_array:$smarty.session.datas.info.acls} -->
							<dd><a href="/noticeAdmin/queryNoticeTask">通知任务管理</a></dd>
							<!-- {/if} -->
						</dl>
					</li>
				<!-- {/if} -->
				

				</ul>
			</div>
		</div>
	</div>
</body>
<script src="{$path_js}/js/jquery-1.9.1.min.js"></script>
<script src="{$path_js}/js/glDatePicker.min.js"></script>
<script>
$(function() {
	$('#calendar').glDatePicker({
		showAlways : true,
		format : 'yyyy-mm-dd'
	});
	$('.menu-list li').removeAttr("class");			
	$('.menu-list li:eq(0)').attr("class","current");
});
</script>
</html>
