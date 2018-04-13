<%--
功能名稱 : 使用者介面 Banner
功能說明 : 前端 tool bar 整合 tool bar 共用頁面，利用參數化的方式整合不同介面之 tool bar

RIVISION HISTORY
--------------------------------------------------------
2016.03.14 David Create

 --%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
    //banner script
    var userName = '${userName}';								<%-- user name --%>
   	var indexServer ='${currentContextPath}';					<%-- php server url 首頁--%>
    var userServer = indexServer;								<%-- user server url --%>
    var gameServer = '${frontGame}';							<%-- game server url --%>
    var ptgameServer = "${ptContextPath}";						<%-- PTgame server url --%>
    var wagameServer = "${wgPath}"+"/game/wg/openGame";								<%-- wagame server url --%>
    var fhxgameServer = indexServer + "/fhx/index";				<%-- fhxgame server url --%>
    var userbal = "/index/getuserbal"; 							<%-- get userbal url 餘額查詢--%>
    var permisssion = "";			   							<%-- get available user menu 使用者選單 --%>
    var support_service_link_a = "${sessionScope.info.account}";<%-- get user accunt for support_service --%>
    var support_service_link_aid = "${sessionScope.info.id}";   <%-- get user id for support_service --%>
    var support_service_link_l = "${sessionScope.info.vipLvl}"; <%-- get user vipLvl for support_service --%>
    var displayAvailBal = "0.00"; 								<%-- displayAvailBal 餘額--%>
    var bannerHeadClass = "g_33";								<%-- <div class="'+bannerHeadClass+'"> banner head class  --%>
    var bannerlogotitle='首页'; 									<%-- banner logo attribute : title Banner logo的文字--%>
    var bannerlogohref=userServer + "/index";					<%-- banner logo attribute : href  Banner logo的超連結--%>
    var userClassId = "J-user-center";							<%-- user panel class id 使用者選單--%>
    var msgClassId = "J-top-user-message";						<%-- message panel class id 站內信--%>
    var betHistory = gameServer + "/gameUserCenter/queryOrdersEnter?time=7";<%-- bet history 投注記錄查詢 --%> 
    var quickloginTarget = ''; 									<%-- quick login attribute : target 快速登入超連結屬性 target   --%>
    var lhcStatus = "${sessionScope.info.lhcStatus}"; <%-- get user lhcStatus for support_service --%>
</script>

<t:if test="${displayAvailBal!=null}"><%-- get available user menu 取得使用者選單--%>
	<script type="text/javascript">		
		displayAvailBal = '${displayAvailBal}';
	</script>
</t:if>
<t:if test="${userlvl!=-1&&isAllZero!=0}">
	<script type="text/javascript">		
		permisssion = '<a href="'+indexServer+'/proxy/index" >代理中心</a>';
		permisssion +='<a href="'+gameServer+'/gameUserCenter/queryCurrentUserReport" >报表查询</a>';
	</script>
</t:if>		
<%@ include file="base-script.jsp" %><%-- loading common script--%>
<%-- 聚合頁入口 --%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/activity/2016_DecRedBow/group/js/Tdrag.min.js"></script>
<%--loading JS Banner --%>
<script type="text/javascript" src="${staticFileContextPath}/static/html/toolBar/frontend-banner.js"></script>
<!-- toolbar Start --> 
<div id = "topbar" class="topbar"></div>
<!-- toolbar end --> 
<!-- header start -->   
<div id = "header" class="header"></div>
<!-- header end --> 
<!-- 2016.03.22 Tool Bar Integrate : online support & game remove-->
<%-- 2016.03.14 Tool Bar 整合 : 移除下架遊戲選單--%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/removeIssue/removeIssueforGame.js" ></script>
<%-- 2016.03.14 Tool Bar 整合 : 在線客服打不開--%>
<script defer="true" type="text/javascript" src="${userContextPath}/support/support_service?p=FF4"></script>



