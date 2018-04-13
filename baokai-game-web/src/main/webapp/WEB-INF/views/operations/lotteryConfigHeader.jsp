<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
      <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE">
      <c:if test="${lotteryId!=99112&&lotteryId!=99306&&lotteryId!=99113}">
            <li id="issueRuleMenu"><a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=${lotteryId }&ruleId="><spring:message code="gameCenter_jiangqiguize" /></a></li>
       </c:if>
        </permission:hasRight>
        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP">
            <li id="awardGroupMenu"><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${lotteryId }&status=&awardId="><spring:message code="gameCenter_jiangjinzu" /></a>
            </li>
        </permission:hasRight>
        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_BETLIMIT">
            <c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99701&&lotteryId!=99401}">
                <li id="betLimitMenu"><a href="${currentContextPath}/gameoa/toBetLimit?lotteryid=${lotteryId }"><spring:message code="gameCenter_touzhuxianzhi" /></a></li>
            </c:if>
        </permission:hasRight>
        <c:if test="${lotteryId==99108||lotteryId==99109||lotteryId==99701||lotteryId==99401 }">
            <li id="lockMenu"  ><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁</a></li>
        </c:if>
          <c:if test="${lotteryId==99108||lotteryId==99109}">
           <li id="changeMenu"><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">变价</a></li>
        </c:if>
        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_HELP">
            <li id="helpMenu"><a href="${currentContextPath}/gameoa/toHelp?lotteryid=${lotteryId }"><spring:message code="gameCenter_wanfamiaoshu" /></a></li>
        </permission:hasRight>
        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SALESTATUS">
            <li id="soldMenu"><a href="${currentContextPath}/gameoa/toSellingStatus?lotteryid=${lotteryId }"><spring:message code="gameCenter_xiaoshouzhuangtai" /></a></li>
        </permission:hasRight>
        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_CONFIG">
        <c:if test="${lotteryId!=99112&&lotteryId!=99306&&lotteryId!=99113}">
            <li id="configMenu"><a href="${currentContextPath}/gameoa/toSeriesConfig?lotteryid=${lotteryId }"><spring:message code="gameCenter_canshushezhi" /></a></li>
        </c:if>
        </permission:hasRight>