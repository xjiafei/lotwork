<%--
功能名稱 : 遊戲頁面 tool bar
功能說明 : 前端 tool bar 整合 tool bar 共用頁面，利用參數化的方式整合不同介面之 tool bar

RIVISION HISTORY
--------------------------------------------------------
2016.03.14 David Create

 --%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    //toolbar script
    var userName = '${userName}'; 								<%-- user name --%>
    var indexServer ='${userContextPath}';						<%-- php server url 首頁--%>
    var userServer = indexServer;								<%-- user server url --%>
    var gameServer = '${currentContextPath}';					<%-- game server url --%>
    var ptgameServer = "${ptContextPath}";						<%-- PTgame server url --%>
    var wagameServer = "${wgPath}";								<%-- wagame server url --%>
    var fhxgameServer = indexServer + "/fhx/index";				<%-- fhxgame server url --%>
    var userbal = "/gameBet/queryUserBal";						<%-- get userbal url 餘額查詢--%>
    var permisssion = "";										<%-- get available user menu 使用者選單 --%>
    var support_service_link_a = "${sessionScope.info.account}";<%-- get user accunt for support_service --%>
    var support_service_link_aid = "${sessionScope.info.id}";	<%-- get user id for support_service --%>
    var support_service_link_l = "${sessionScope.info.vipLvl}"; <%-- get user vipLvl for support_service --%>
    var displayAvailBal = ""; 									<%-- displayAvailBal --%>
    var projectName = "${projectName}";							<%-- game menu display name --%>
    var userClassId = "J-user-panel";//使用者選單				<%-- user panel class id 使用者選單--%>
    var msgClassId = "J-msg-panel";//站內信						<%-- message panel class id 站內信--%>
    var frontpageLink="";										<%-- home page link 首頁連結--%>
    var betHistory = gameServer + "/gameUserCenter/queryOrdersEnter?time=7";<%-- bet history 投注記錄查詢 --%>  
    var lhcStatus = "${sessionScope.info.lhcStatus}"; <%-- get user lhcStatus for support_service --%>
</script>
<c:if test="${userlvl!=-1&&isAllZero!=0}"><%-- get available user menu 取得使用者選單--%> 
	<script type="text/javascript">		
		permisssion = '<a href="'+indexServer+'/proxy/index" >代理中心</a>';
		permisssion +='<a href="'+gameServer+'/gameUserCenter/queryCurrentUserReport" >报表查询</a>';
	</script>
</c:if>		
<jsp:include page="base-script.jsp" flush="true" /> <%-- loading common script--%>


<%--loading JS Tool Bar --%>

	<script type="text/javascript" src="${staticFileContextPath}/static/html/toolBar/frontend-toolbar.js"></script>
<!-- toolbar Start --> 
<div id = "jsheader" ></div>
<!-- toolbar end --> 

	<!-- 2016.03.22 Tool Bar Integrate : online support & game remove-->
	<%-- 2016.03.14 Tool Bar 整合 : 移除下架遊戲選單--%>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/removeIssue/removeIssueforGame.js" ></script>
	<%-- 2016.03.14 Tool Bar 整合 : 在線客服打不開--%>
	<script defer="true"  type="text/javascript" src="${userContextPath}/support/support_service?p=FF4"></script>



