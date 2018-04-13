<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<dl class="nav">
	<permission:hasRight moduleName="RISK_GAME_MANAGE">
	<dt>游戏审核管理</dt>
	</permission:hasRight>
	<permission:hasRight moduleName="RISK_GAME_WARN">
	<dd ><a href="${currentContextPath}/gameRisk/queryGameExceptionAuditList">游戏异常审核</a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="RISK_GAME_CONFIG">
	<dd ><a href="${currentContextPath}/gameRisk/toSeriesConfigRisk?lotteryid=99101">游戏审核设置</a></dd>
	</permission:hasRight>
	<permission:hasRight moduleName="RISK_GAME_REPORT">
	<dt>统计报表</dt>
	</permission:hasRight>
	<permission:hasRight moduleName="RISK_GAME_REPORT_DETAIL">
	<dd><a href="${currentContextPath}/gameRisk/gotoGameRiskTransactionReport">游戏明细表</a></dd>
	</permission:hasRight>
	<!--  dd><a href="${currentContextPath}/gameRisk/queryGameRiskIncomeReport">单期收支报表</a></dd>-->
	<permission:hasRight moduleName="RISK_GAME_REPORT_EXCEPTION">
	<dd><a href="${currentContextPath}/gameRisk/queryWarnOrderList">异常记录</a></dd>
	</permission:hasRight>
</dl>
