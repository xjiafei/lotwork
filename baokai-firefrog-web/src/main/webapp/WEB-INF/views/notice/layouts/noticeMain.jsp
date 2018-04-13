<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="帮助" /></title>
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
    <link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.ga.js"></script>
	<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var staticFileContextPath='${staticFileContextPath}';
	var global_path_url='${staticFileContextPath}/static';
	 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
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
			<div id="DivSuccessful" class="pop pop-error w-4" style="position:absolute;z-index:2; display:none">
		    <i class="ico-success"><h4 class="pop-text">发送成功</h4>
		    </div>
		    <div id="DivFailed" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none">
		        <i class="ico-error"><h4 class="pop-text">发送失败，请重试</h4></i>
		    </div>
</body>
</html>