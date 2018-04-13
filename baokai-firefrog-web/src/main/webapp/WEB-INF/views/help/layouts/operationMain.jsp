<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="帮助" /></title>
		<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/help/help.css" />
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Slider.js"></script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
  <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Notice.js"></script>
  <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.ga.js"></script>
  
	<script type="text/javascript">
	var global_path_url='${staticFileContextPath}/static';
	var global_params_notice = "all,ad_top,agent_first_page";
	var global_userName='${userName}';
	var global_userID ='${sessionScope.info.id}';
	var baseUrl = '${currentContextPath}',
		staticFileContextPath='${staticFileContextPath}',
	 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
</script>
		
	<decorator:head />
</head>
<body>

<%-- 2016.03.14 Tool Bar 整合 Start--%>
<%-- <%@ include file="../../layouts/header-front-banner.jsp" %>  --%> 
<%-- 2016.03.14 Tool Bar 整合 End--%>

<%-- 2016.03.14 Tool Bar 整合 Start
	<%@ include file="../../layouts/header.jsp" %>  
2016.03.14 Tool Bar 整合 End--%>

<div class="g_33">
	<div class="help-main clearfix">
		<div class="help-container">
			<!-- 从被装饰页面获取body标签内容 -->
			<decorator:body />
		</div>
	</div>
</div>
<!-- 公告通知 -->
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Notice.js" ></script>
<%@ include file="../../layouts/footer.jsp" %>  
</body>
</html>