<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

		<div class="col-side">
			<dl class="nav">
				<permission:hasRight moduleName="GLOBAL_GLOBALMANAGER"><dt>全局管理</dt></permission:hasRight>
				<permission:hasRight moduleName="GLOBAL_REGEISTLOGINSETTING"><dd <c:if test="${global_no == 1}">class="current" </c:if>><a href="${currentContextPath}/globeAdmin/goRegLoginConfig">注册登录设置</a></dd></permission:hasRight>
				<permission:hasRight moduleName="GLOBAL_SENSITIVEWORDSETTING"><dd <c:if test="${global_no == 2}">class="current" </c:if>><a href="${currentContextPath}/globeAdmin/goSensitiveWord">敏感词管理</a></dd></permission:hasRight>				
				<permission:hasRight moduleName="GLOBAL_IPSWITCH"><dd <c:if test="${global_no == 3}">class="current" </c:if>>
				<c:choose>
				<c:when test="${fn:contains(aclList,'GLOBAL_IPILLEGALITY')}">
				<a href="${currentContextPath}/globeAdmin/goIpSwitch">
				</c:when>
				<c:otherwise>
				<a href="${currentContextPath}/globeAdmin/goIpSwitch?type=1">
				</c:otherwise>
				</c:choose>	
				IP黑白名单</a></dd></permission:hasRight>
				<permission:hasRight moduleName="GLOBAL_WHITELISTIP"><dd <c:if test="${global_no == 4}">class="current" </c:if>><a href="${currentContextPath}/globeAdmin/goWhiteList">指定IP白名单</a></dd></permission:hasRight>						
			</dl>
		</div>
