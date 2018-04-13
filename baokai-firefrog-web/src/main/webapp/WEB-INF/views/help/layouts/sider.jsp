<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
			<dl class="nav">
				<permission:hasRight moduleName="HELP_CENTER"><dt>帮助中心 </dt></permission:hasRight>
				<permission:hasRight moduleName="HELP_MANAGER">
					<dd <c:if test="${cate2Name == '帮助管理'}">class="current"</c:if>>
						<a href="${currentContextPath}/helpAdmin/goHelpManager">帮助管理</a>
					</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="HELP_NEWHELP">
					<dd <c:if test="${cate2Name == '新建帮助'}">class="current"</c:if>>
						<a href="${currentContextPath}/helpAdmin/goCreateHelp">新建帮助</a>
					</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="HELP_CATEGORY">
					<dd <c:if test="${cate2Name == '类目列表'}">class="current"</c:if>>
						<a href="${currentContextPath}/helpAdmin/queryCategory?cate2Name=类目列表">类目列表</a>
					</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="HELP_HELPCONFIG">
				<dd <c:if test="${cate2Name == '帮助配置'}"> class="current"</c:if>>
					<a href="${currentContextPath}/helpAdmin/queryHelpConfig?cate2Name=帮助配置">帮助配置</a>
				</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="SUPPORT_CENTER"><dt>客服工单 </dt></permission:hasRight>
				<permission:hasRight moduleName="SUPPORT_TICKET_LIST_FF3">
				<dd <c:if test="${cate2Name == '3.0问题列表'}">class="current"</c:if>>
					<a href="${currentContextPath}/support/supportAdmin/ticketList?platformCode=FF3">3.0问题列表</a>
				</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="SUPPORT_TICKET_LIST_FF4">
				<dd <c:if test="${cate2Name == '4.0问题列表'}">class="current"</c:if>>
					<a href="${currentContextPath}/support/supportAdmin/ticketList?platformCode=FF4">4.0问题列表</a>
				</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="SUPPORT_CENTER_CONFIG_FF3">
				<dd <c:if test="${cate2Name == '3.0系统设置'}">class="current"</c:if>>
					<a href="${currentContextPath}/support/supportAdmin/supportConfig?platformCode=FF3">3.0系统设置</a>
				</dd>
				</permission:hasRight>
				<permission:hasRight moduleName="SUPPORT_CENTER_CONFIG_FF4">
				<dd <c:if test="${cate2Name == '4.0系统设置'}">class="current"</c:if>>
					<a href="${currentContextPath}/support/supportAdmin/supportConfig?platformCode=FF4">4.0系统设置</a>
				</dd>
				</permission:hasRight>
			</dl>