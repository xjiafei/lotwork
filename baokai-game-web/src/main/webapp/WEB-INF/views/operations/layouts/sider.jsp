<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<dl class="nav">
	<permission:hasRight moduleName="GAME_LOTMGR">
	<dt><spring:message code="gameCenter_caizhongguanli" /></dt>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST">
	<dd><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_ISSUEMGR">
	<dt><spring:message code="gameCenter_jiangqiliuchengguanli" /></dt>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_ISSUEMGR_MONITOR">
	<dd><a href="${currentContextPath}/gameoa/queryLotteryIssueWarn"><spring:message code="gameCenter_liuchengjiankong" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_ISSUEMGR_WARNLOG">
	<dd><a href="${currentContextPath}/gameoa/queryLotteryIssueWarnLog"><spring:message code="gameCenter_yichangliuchengjilu" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_ISSUEMGR_MANUAL">
	<dd><a href="${currentContextPath}/gameoa/queryGameManualRecord"><spring:message code="gameCenter_shougongluhao" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_OPMGR">
	<dt><spring:message code="gameCenter_fanganzhuihaoguanli" /></dt>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST">
	<dd><a href="${currentContextPath}/gameoa/queryOrderList"><spring:message code="gameCenter_fanganjilu" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_OPMGR_PLANLIST">
	<dd><a href="${currentContextPath}/gameoa/queryPlanList"><spring:message code="gameCenter_zhuihaojilu" /></a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST">
	<dd><a href="${currentContextPath}/gameoa/querySlipListByIssueCode"><spring:message code="gameCenter_sidanjilu" /></a></dd>
	</permission:hasRight>

	<permission:hasRight moduleName="GAME_REPORT">
	<dt><spring:message code="gameCenter_baobiaoguanli" /></dt>
	</permission:hasRight>
	<permission:hasRight moduleName="GAME_REPORT_WINS">
	<dd><a href="${currentContextPath}/gameoa/queryWinsReport"><spring:message code="gameCenter_danqiyingkuibiao" /></a></dd>
	</permission:hasRight>
	
	
</dl>
