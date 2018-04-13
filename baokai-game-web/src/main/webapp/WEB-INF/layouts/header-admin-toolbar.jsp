<%--
功能名稱 : 後台 共用 tool bar
RIVISION HISTORY
--------------------------------------------------------
2016.03.14 David Create

 --%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>	

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
				<a href="${adminContextPath}/adAdmin/queryPublishAdPage ">营销中心</a>
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
		<div class="menu-quit"><a href="${adminContextPath}/admin/login">退出</a><i class="ico-user"></i>你好，${userName} <span id="_userName"></span></div>
	</div>


