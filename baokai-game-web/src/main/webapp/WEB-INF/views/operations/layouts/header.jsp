<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/css/pagination.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin-help.css" />	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Countdown.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.pagination.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Common.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.cookie.js" ></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/util/phoenix.EditConfirm.js" ></script>

	<style>
	.j-ui-tip-[]info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	.clearfix {display:block;}
	</style>
	
	<decorator:head />
</head>

<!--JS后期独立出去，前期方便调试-->

<body>
	<div class="menu">
		<div class="menu-logo"></div>
		<ul class="menu-list">
			
			<li><a href="${adminContextPath}/admin">首页</a></li>
			
				<li>
				<permission:hasRight moduleName="GLOBAL">
				<a href="${adminContextPath}/globeAdmin/goRegLoginConfig">全局管理</a>
				</permission:hasRight>
				</li>

				<li>
				<permission:hasRight moduleName="USER">
				<a href="${adminContextPath}/admin/user/list">用户中心</a>
				</permission:hasRight>
				</li>
		
				<li>
				<permission:hasRight moduleName="GAME">
				<a href="${currentContextPath }/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>
				</permission:hasRight>
				</li>
		
				<li>
				<permission:hasRight moduleName="RISK">
				<a href="${currentContextPath}/gameRisk/queryGameExceptionAuditList">审核中心</a>
				</permission:hasRight>
				</li>
		
				<li>
				<permission:hasRight moduleName="FUND">
				<a href="${adminContextPath}/admin/Rechargemange/index">资金中心</a>
				</permission:hasRight>
				</li>

				<li>
				<permission:hasRight moduleName="MARKET">
				<a href="${adminContextPath}/adAdmin/queryPublishAdPage?isAll=true">营销中心</a>
				</permission:hasRight>
				</li>
	
				<li style="display:none">
				<permission:hasRight moduleName="">
				<a href="#">数据中心</a>
				</permission:hasRight>
				</li>

				<li>
				<permission:hasRight moduleName="HELP">
				<a href="${adminContextPath}/helpAdmin/goHelpManager">内容中心</a>
				</permission:hasRight>
				</li>

				<li>
				<permission:hasRight moduleName="ACL">
				<a href="${adminContextPath}/aclAdmin/goUserManager">权限中心</a>
				</permission:hasRight>
				</li>
		
				<li>
				<permission:hasRight moduleName="NOTICE_CENTER">
				<a href="${adminContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a>
				</permission:hasRight>
				</li>			
			
				<li><permission:hasRight moduleName="PT">
					<a href="${ptadminServer}/ptadmin/">PT游戏</a>
					</permission:hasRight>
				</li>		
				
				
		</ul>
		<div class="menu-quit"><a href="${adminContextPath}/admin/login">退出</a><i class="ico-user"></i>你好， ${userName}<span id="_userName"></span></div>
	</div>


