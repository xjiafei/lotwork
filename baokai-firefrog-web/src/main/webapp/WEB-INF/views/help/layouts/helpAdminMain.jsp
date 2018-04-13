<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="帮助后台" /></title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin-help.css" />
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/game/base-all.min.js"></script>

	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	var global_path_url='${staticFileContextPath}/static';
	</script>
	<decorator:head />
</head>
<body>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<%@ include file="../../layouts/header-admin-toolbar.jsp" %>
<%-- 2016.03.14 Tool Bar 整合 End--%>

<%-- 2016.03.14 Tool Bar 整合 Start
	<jsp:include page="../../layouts/menu.jsp" flush="true" />
2016.03.14 Tool Bar 整合 End--%>

	<div class="col-content">
		<div class="col-side">
			<jsp:include page="sider.jsp" flush="true" />
		</div>  
	<div class="col-main">
		<!-- 从被装饰页面获取body标签内容 -->
		<decorator:body />
	</div>
</div>
</body>
</html>