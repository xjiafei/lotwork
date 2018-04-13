<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<c:if test="${empty checkMobile}">
<title><decorator:title default="用户中心" /></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<%-- 2016.03.14 Tool Bar 整合 Start--%>
<script type="text/javascript">
	var baseUrl = '${currentContextPath}',
		staticFileContextPath='${staticFileContextPath}',
		ctx = "${currentContextPath}",
		global_userName='${userId}',
		_logOut = '${userContextPath}',
		global_userID = '${userId}',
		 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/base-all.min.js"></script>
	<%-- 聚合頁入口 --%>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/2016_DecRedBow/group/js/Tdrag.min.js"></script>
<%-- 2016.03.14 Tool Bar 整合 End--%>	
</c:if>
<decorator:head />
</head>

<body <c:if test="${empty shierShengXiao}"> ng-app="starter" ng-controller="allCtrl" ng-click="allClick()" </c:if>>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<c:if test="${empty checkMobile}">
		<jsp:include page="../../../../layouts/header-front-toolbar.jsp" flush="true" />
	</c:if>
<%-- 2016.03.14 Tool Bar 整合 End--%>
	<decorator:body />
</body>
	<c:if test="${empty checkMobile}">
		<script type="text/javascript" src="${staticFileContextPath}/static/js/game/phoenix.Notice.js" ></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.ga.js" async></script>
	</c:if>
<!-- 公告通知 -->
</html>