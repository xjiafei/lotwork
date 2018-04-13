<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="异常充值处理" /></title>	
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
<link rel="stylesheet" href="${staticFileContextPath}/static/css/pagination.css" />
	
<script src="${staticFileContextPath}/static/js/base-all.js" type="text/javascript" ></script>
<script src="${staticFileContextPath}/static/js/jquery-ui-1.10.2.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/jquery.tmpl.min.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/phoenix.DatePicker.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/phoenix.pagination.js" type="text/javascript"></script>
<script src="${staticFileContextPath}/static/js/phoenix.Message.js" type="text/javascript" ></script>
<script src="${staticFileContextPath}/static/js/phoenix.Common.js" type="text/javascript" ></script>
	
<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	var global_path_url='${staticFileContextPath}/static';
</script>
	<decorator:head />
</head>
<body>
<%-- 2016.03.14 Tool Bar 整合 Start--%>
	<jsp:include page="../../layouts/header-admin-toolbar.jsp" flush="true" />
<%-- 2016.03.14 Tool Bar 整合 End--%>

<%-- 2016.03.14 Tool Bar 整合 Start
	<jsp:include page="../../layouts/menu.jsp" flush="true" />
2016.03.14 Tool Bar 整合 End--%>
	<div class="col-content">
	<jsp:include page="sider.jsp" flush="true" />
	<div class="col-main">
		<!-- 从被装饰页面获取body标签内容 -->
		<decorator:body />
	</div>
</div>
</body>
</html>