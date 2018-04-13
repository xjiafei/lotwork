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
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Countdown.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Common.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.EditConfirm.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/sortabletable.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js" ></script>


	
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
		<decorator:body />
	</div>
</div>
</body>
</html>