<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!-- 后台菜单 -->
<div class="menu">
	<div class="menu-logo"></div>
	<!-- 首页  全局管理  用户中心  游戏中心  资金中心  内容中心  营销中心  通知中心  数据中心   权限中心 -->
	<ul class="menu-list">
		<li><a href="/admin/">首页</a></li>

		<li><permission:hasRight moduleName="GLOBAL">
				<a href="${currentContextPath}/globeAdmin/goRegLoginConfig">全局管理</a>
			</permission:hasRight>
					<li><permission:hasRight moduleName="USER">
				<a href="/admin/user/list"> 用户中心</a>
			</permission:hasRight></li>
			
			
		<li><permission:hasRight moduleName="GAME">
				<a href="${gamecenterPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>
			</permission:hasRight></li>
			
		<li><permission:hasRight moduleName="RISK"><a href="${gamecenterPath}/gameRisk/toSeriesConfigRisk/">审核中心</a></permission:hasRight></li>
		
<li><permission:hasRight moduleName="FUND">
				<a href="/admin/Rechargemange/index">资金中心</a>
			</permission:hasRight></li>
		<li><permission:hasRight moduleName="MARKET">
			<a href="${currentContextPath}/adAdmin/queryPublishAdPage ">营销中心</a>
			</permission:hasRight></li>

		<li><permission:hasRight moduleName="HELP">
				<a href="${currentContextPath}/helpAdmin/goHelpManager">内容中心</a>
			</permission:hasRight></li>

		<li><permission:hasRight moduleName="ACL">
				<a href="${currentContextPath}/aclAdmin/goUserManager">权限中心</a>
			</permission:hasRight></li>

		<li><permission:hasRight moduleName="NOTICE_CENTER">
				<a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a>
			</permission:hasRight></li>
			

		

	</ul>
	<!--<div class="menu-quit"><a href="">退出</a><i class="ico-user"></i>你好，vava</div>-->
	<div class="menu-quit">
		<a href="${currentContextPath}/admin/login">退出</a><i class="ico-user"></i>你好，${userName}<span
			id="_userName"></span>
	</div>
	<script type="text/javascript"
		src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>	
</div>