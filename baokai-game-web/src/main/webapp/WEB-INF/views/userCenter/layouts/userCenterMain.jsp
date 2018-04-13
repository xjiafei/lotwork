<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<%@ taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title><decorator:title default="用户中心" /></title>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta http-equiv="Cache-Control" content="no-store" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />	
	
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
	<link rel="stylesheet" href="${staticFileContextPath}/static/images/bet/bet.css" />
	


	<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
    <script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.easing.1.3.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-ui-1.10.2.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.flot.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.flot.crosshair.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.base.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Class.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Event.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.util.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Timer.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Input.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tab.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Slider.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Hover.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Tip.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Mask.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.MiniWindow.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Validator.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.DatePicker.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.BASE64.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/datePicker.js"></script>	
    <script type="text/javascript" src="${staticFileContextPath}/static/js/lotteryConfig.js"></script>	
    <script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.ga.js"></script>

	<script type="text/javascript">
		 global_params_notice = "all,ad_top",global_userName='${userId}';
		 global_userID = '${userId}';
		 global_path_url="${staticFileContextPath}/static/";
		 _logOut = '${userContextPath}';
		 hjUserData=encodeURI('${userName}')+'|${sex}|${cellphone}|${cellphone}|${email}|${registerAddress}||||${userId}|${viplvl}|';
	</script>
	<decorator:head />
</head>
<body>
	<%-- 聚合頁入口 --%>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/2016_DecRedBow/group/js/Tdrag.min.js"></script>
	
	<%--Tool Bar 整合 --%>
		<jsp:include page="../../../../layouts/header-front-banner.jsp" flush="true" />
	<!-- toolbar Start --> 

	<div class="g_33 common-main">
		<jsp:include page="sider.jsp" flush="true" />
		<decorator:body />
	</div>
	<jsp:include page="footer.jsp" flush="true" />
	
	<!-- 公告通知 -->
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Notice.js" ></script>
</body>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/removeIssue/removeIssueforOrder.js" ></script>
	
</html>