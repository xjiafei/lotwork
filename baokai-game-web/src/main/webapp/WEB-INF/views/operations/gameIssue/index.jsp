<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta charset="UTF-8">
	<title>奖期规则查看页</title>

</head>
<body>
<div id="tab_menu_id" style="display:none">issueRuleMenu</div>
					<div class="col-crumbs">
						<div class="crumbs">
							<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><spring:message code="gameCenter_jiangqiguize"/>
						</div>
					</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
								<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
								<input type="hidden" id="lotteryId" value="${lotteryId}"/>
								<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/gameIssueIndex?ruleId=&lotteryId=">
							</div>
							<div class="ui-tab-content clearfix">
								<h3 class="ui-title"><a href="<%=request.getContextPath() %>/gameoa/queryGameIssues?lotteryId=${lotteryId}" class="more"><spring:message code="gameCenter_chakanjiangqi" />&raquo;</a><spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include>&nbsp;&nbsp;&nbsp;&nbsp;<spring:message code="gameCenter_yishengchengjiangqizhi"/> ${maxIssuesDay } (第${maxIssueCode }期)</h3>
								<h4 class="ui-title"><spring:message code="gameCenter_changguijiangqiguize"/>
								<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_ADD">
								<a href="<%=request.getContextPath() %>/gameoa/preAddOrUpdateGameIssueRule?lotteryId=${lotteryId}" class="info"><spring:message code="gameCenter_jiangqiguizetiaozheng" /></a>
								</permission:hasRight>
								</h4>
								<c:if test="${not empty currentIssueRule }">
								<div class="table-bg table-bg1">
									<table class="table">
										<thead>
											<tr>
												<th colspan="5"><strong>${currentIssueRule.issueRulesName}</strong>&nbsp;&nbsp;${currentIssueRule.ruleStartTime}
												
												</th>
												<th>
												 <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_EDIT">
															<c:if test="${currentIssueRule.ruleStatus <= 3 && isHaveCurrent4==0}">
																<a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${currentIssueRule.ruleType}&ruleId=${currentIssueRule.ruleId }&ruleStatus=${currentIssueRule.status}" id="editCommonSpecialGame2">預計<spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
															</c:if>
															 </permission:hasRight>
												</th>
											
											</tr>
											<tr>
												<th colspan="6"><strong>开奖周期</strong>&nbsp;&nbsp;${currentIssueRule.openAwardPeriod }</th>
											</tr>
											<tr>
												<th></th>
												<!-- <th>销售开始时间</th> -->
												<th><spring:message code="gameCenter_guanfangdiyiqikaijiangshijian" /></th>
												<th><spring:message code="gameCenter_guanfangzuihouyiqikaijiangshijian" /></th>
												<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
												<th><spring:message code="gameCenter_xiaoshouzhouqi" /></th>
												</c:if>
												<th><spring:message code="gameCenter_dengdaikaijiangshijian" /></th>
											</tr>
										</thead>
										<tbody>
											
											<c:forEach items="${currentIssueRule.salesIssueStrucs}" var="salesStrucs" varStatus="gameIndex">
												<tr>
													<c:if test="${gameIndex.index == 0 }"><td><strong>排序开始时间点</strong></td></c:if>
													<c:if test="${gameIndex.index > 0 }"><td></td></c:if>
													<%-- <td>${salesStrucs.saleStartTime }</td> --%>
													<td>${salesStrucs.firstAwardTime }</td>
													<td>${salesStrucs.lastAwardTime}</td>
													<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
													<td>${salesStrucs.salePeriodTime }</td>
													</c:if>
													<td>${salesStrucs.scheduleStopTime }</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								</c:if>
								<h4 class="ui-title"><spring:message code="gameCenter_jiangqiguizetiaozheng" />记录<label class="label info"><input type="checkbox" class="checkbox" id="checkBoxId" /><spring:message code="gameCenter_yincangyiguoqishijianduan" /></label></h4>
								
								<!-- 循环开始 -->
								<c:forEach items="${gameIssueRules}" var="gameRule">
									<!-- 1为未开始，2，未开始，待审核， 3，进行中， 4， 删除/无效, 5为已停止 -->
									<c:if test="${gameRule.ruleStatus <=3 && gameRule.ruleType < 3 }">	<!-- 除休市规则外 -->
										<div class="table-bg table-bg2">
											<table class="table">
												<thead>
													<tr>
														<!-- 1.未开始、2、未开始，待审核、3。进行中、4。删除、5停止 -->
														<th><strong>
														<c:if test="${gameRule.ruleStatus==1 }">
															<spring:message code="gameCenter_zhuangtai" />：未开始
														</c:if>
														<c:if test="${gameRule.ruleStatus==2}">
															<spring:message code="gameCenter_zhuangtai" />：未开始，<span class="color-red">待审核</span>
														</c:if>
														<c:if test="${gameRule.ruleStatus==3 }">
															状态：<span class="color-red">进行中</span>
														</c:if>
														</strong>
														</th>
														<!-- <th><strong>状态：未开始，<span class="color-red">待审核</span></strong></th> -->
														<c:if test="${gameRule.ruleType==2 }">
														<th colspan="4"><strong>${gameRule.issueRulesName }</strong>&nbsp;&nbsp;${gameRule.ruleStartTime }&nbsp;~~&nbsp;${gameRule.ruleEndTime }</th>
														</c:if>
														<c:if test="${gameRule.ruleType!=2 }">
														<th colspan="4"><strong>${gameRule.issueRulesName }</strong>&nbsp;&nbsp;${gameRule.ruleStartTime }&nbsp;&nbsp;开始执行</th>
														</c:if>
														<th class="ui-btn">
															<c:if test="${gameRule.ruleType!=2}">
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_COM_RULE_AUDIT">
															<c:if test="${gameRule.ruleStatus==2 }">
																<a class="btn btn-important btn-mini" href="javascript:void(0);" id="AuditSubmit1" name="${lotteryId}_${gameRule.ruleType}_${gameRule.ruleId}"><spring:message code="gameCenter_shenhe" /><b class="btn-inner"></b></a>
															</c:if>
															</permission:hasRight>
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_EDIT">
															<c:if test="${gameRule.ruleStatus == 3 || gameRule.ruleStatus == 1}">
																<c:if test="${gameRule.ruleType==2}">
																	<a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${gameRule.ruleType}&ruleId=${gameRule.ruleId }&ruleStatus=${gameRule.status}" id="editCommonSpecialGame2"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
																</c:if>
															</c:if>
															</permission:hasRight>
															</c:if>
															
															<c:if test="${gameRule.ruleType==2}">
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SPE_RULE_AUDIT">
															<c:if test="${gameRule.ruleStatus==2 }">
																<a class="btn btn-important btn-mini" href="javascript:void(0);" id="AuditSubmit1" name="${lotteryId}_${gameRule.ruleType}_${gameRule.ruleId}"><spring:message code="gameCenter_shenhe" /><b class="btn-inner"></b></a>
															</c:if>
															</permission:hasRight>
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SPE_RULE_EDIT">
															<c:if test="${gameRule.ruleStatus == 3 || gameRule.ruleStatus == 1}">
																<c:if test="${gameRule.ruleType==2}">
																	<a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${gameRule.ruleType}&ruleId=${gameRule.ruleId }&ruleStatus=${gameRule.status}" id="editCommonSpecialGame2"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
																</c:if>
															</c:if>
															</permission:hasRight>
															</c:if>
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_DEL">
															<c:if test="${gameRule.ruleStatus == 1|| gameRule.ruleStatus == 2 }">
																<a class="btn" href="javascript:void(0);" id="DeleteSubmit1" name="${lotteryId}_${gameRule.ruleId}"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a>
															</c:if>
															</permission:hasRight>	
															<c:if test="${gameRule.ruleStatus == 3 }">
																<c:if test="${gameRule.ruleType==2}">
																<a class="btn" href="javascript:void(0);" id="cancalButton" name="${lotteryId}_${gameRule.ruleId}">停止</a>
																</c:if>
															</c:if>
														</th>
													</tr>
													<tr>
														<th colspan="6"><strong>开奖周期</strong>&nbsp;&nbsp; ${gameRule.openAwardPeriod }</th>
													</tr>
													<tr>
														<th></th>
														<!-- <th>销售开始时间</th> -->
														<th><spring:message code="gameCenter_guanfangdiyiqikaijiangshijian" /></th>
														<th><spring:message code="gameCenter_guanfangzuihouyiqikaijiangshijian" /></th>
														<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
														<th><spring:message code="gameCenter_xiaoshouzhouqi" /></th>
														</c:if>
														<th><spring:message code="gameCenter_dengdaikaijiangshijian" /></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${gameRule.salesIssueStrucs}" var="salesStrucs" varStatus="gameIndex">
														<tr>
															<c:if test="${gameIndex.index == 0 }"><td><strong>排序开始时间点</strong></td></c:if>
															<c:if test="${gameIndex.index > 0 }"><td></td></c:if>
															<%-- <td>${salesStrucs.saleStartTime }</td> --%>
															<td>${salesStrucs.firstAwardTime }</td>
															<td>${salesStrucs.lastAwardTime}</td>
															<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
															<td>${salesStrucs.salePeriodTime }</td>
															</c:if>
															<td>${salesStrucs.scheduleStopTime }</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:if>
									<!-- 休市规则 -->
									<c:if test="${gameRule.ruleStatus <=3 && gameRule.ruleType ==3}">
										<div class="table-bg table-bg3">
											<table class="table">
												<thead>
													<tr>
														<!-- 1.未开始、2、未开始，待审核、3。进行中、4。删除、5停止 -->
														<th><strong>
														<c:if test="${gameRule.ruleStatus==1 }">
															<spring:message code="gameCenter_zhuangtai" />：未开始
														</c:if>
														<c:if test="${gameRule.ruleStatus==2}">
															<spring:message code="gameCenter_zhuangtai" />：未开始，<span class="color-red">待审核</span>
														</c:if>
														<c:if test="${gameRule.ruleStatus==3 }">
															状态：<span class="color-red">进行中</span>
														</c:if>
														</strong>
														</th>
														<th colspan="4"><strong>${gameRule.issueRulesName }</strong>&nbsp;&nbsp;${gameRule.ruleStartTime }&nbsp;~~&nbsp;${gameRule.ruleEndTime }</th>
														<th class="ui-btn">
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_STOP_RULE_AUDIT">															                                 
															<c:if test="${gameRule.ruleStatus==2 }">
																<a class="btn btn-important btn-mini" href="javascript:void(0);" id="AuditSubmit1" name="${lotteryId}_${gameRule.ruleType}_${gameRule.ruleId}"><spring:message code="gameCenter_shenhe" /><b class="btn-inner"></b></a>
																<a class="btn btn-important btn-mini" href="javascript:void(0);" id="AuditSubmit2" name="${lotteryId}_${gameRule.ruleType}_${gameRule.ruleId}"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
															</c:if>
															</permission:hasRight>
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_STOP_RULE_EDIT">
															<c:if test="${gameRule.ruleStatus == 1 }">
																<a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${gameRule.ruleType}&ruleId=${gameRule.ruleId }&ruleStatus=${gameRule.status}" id="editCommonSpecialGame2"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
															</c:if>
															</permission:hasRight>
															<!-- 常规奖期不能删除 -->
															<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_DEL">
															<c:if test="${gameRule.ruleStatus == 1 || gameRule.ruleStatus == 2}">
																<a class="btn" href="javascript:void(0);" id="DeleteSubmit1" name="${lotteryId}_${gameRule.ruleId}"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a>
															</c:if>	
															</permission:hasRight>
															<!-- <a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${gameRule.ruleType}&ruleId=${gameRule.ruleId }&ruleStatus=${gameRule.status}" id="editCommonSpecialGame2"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a> -->
															<!-- <a class="btn" href="javascript:void(0);" id="DeleteSubmit1" name="${lotteryId}_${gameRule.ruleId}"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a> -->
														</th>
													</tr>
													<tr>
														<th colspan="6"><strong>休市维护周期</strong>&nbsp;&nbsp; ${gameRule.openAwardPeriod }</th>
													</tr>
													<tr>
														<th colspan="6"><strong>休市维护时间</strong>&nbsp;&nbsp; ${gameRule.stopStartTime } - ${gameRule.stopEndTime }</th>
													</tr>
												</thead>
											</table>
										</div>
									</c:if>
									
									<!-- 无效、删除、停止状态 -->
								
									<c:if test="${gameRule.ruleStatus > 3 && gameRule.ruleType < 3 }">
										<div class="table-bg table-bg5" id="invalidDiv">
											<table class="table">
												<thead>
													<tr>
														<!-- <th><strong>状态：无效</th> -->
														 <th><strong>
														<c:if test="${gameRule.ruleStatus==4 }">
															<spring:message code="gameCenter_zhuangtai" />：已删除
														</c:if>
														<c:if test="${gameRule.ruleStatus==5}">
															<spring:message code="gameCenter_zhuangtai" />：已停止
														</c:if>
														</strong>
														</th>
														<th colspan="5"><strong>${gameRule.issueRulesName}</strong>&nbsp;&nbsp;${gameRule.ruleStartTime }&nbsp;~~&nbsp;${gameRule.ruleEndTime }</th>
													</tr>
													<tr>
														<th colspan="6"><strong>开奖周期</strong>&nbsp;&nbsp;${gameRule.openAwardPeriod }</th>
													</tr>
													<tr>
														<th></th>
														<!-- <th>销售开始时间</th> -->
														<th><spring:message code="gameCenter_guanfangdiyiqikaijiangshijian" /></th>
														<th><spring:message code="gameCenter_guanfangzuihouyiqikaijiangshijian" /></th>
														<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
														<th><spring:message code="gameCenter_xiaoshouzhouqi" /></th>
														</c:if>
														<th><spring:message code="gameCenter_dengdaikaijiangshijian" /></th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${gameRule.salesIssueStrucs}" var="salesStrucs" varStatus="gameIndex">
														<tr>
															<c:if test="${gameIndex.index == 0 }"><td><strong>排序开始时间点</strong></td></c:if>
															<c:if test="${gameIndex.index > 0 }"><td></td></c:if>
															<%-- <td>${salesStrucs.saleStartTime }</td> --%>
															<td>${salesStrucs.firstAwardTime }</td>
															<td>${salesStrucs.lastAwardTime}</td>
															<c:if test="${lotteryId!=99108&&lotteryId!=99109&&lotteryId!=99401&&lotteryId!=99701}">
															<td>${salesStrucs.salePeriodTime }</td>
															</c:if>
															<td>${salesStrucs.scheduleStopTime }</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</c:if>
									<c:if test="${gameRule.ruleStatus >3 && gameRule.ruleType ==3}">
										<div class="table-bg table-bg5" id="stopDiv">
											<table class="table">
												<thead>
													<tr>
														<!-- 1.未开始、2、未开始，待审核、3。进行中、4。删除、5停止 -->
														 <th><strong>
														<c:if test="${gameRule.ruleStatus==4 }">
															<spring:message code="gameCenter_zhuangtai" />：已删除
														</c:if>
														<c:if test="${gameRule.ruleStatus==5}">
															<spring:message code="gameCenter_zhuangtai" />：已停止
														</c:if>
														</strong>
														</th>
														<th colspan="4"><strong>${gameRule.issueRulesName }</strong>&nbsp;&nbsp;${gameRule.ruleStartTime }&nbsp;~~&nbsp;${gameRule.ruleEndTime }</th>
														<th class="ui-btn">
															<!-- <a class="btn btn-important btn-mini" href="${currentContextPath}/gameoa/preEditCommonGameIssue?lotteryId=${lotteryId}&ruleType=${gameRule.ruleType}&ruleId=${gameRule.ruleId }&ruleStatus=${gameRule.status}" id="editCommonSpecialGame2"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a> -->
															<!--<a class="btn" href="javascript:void(0);" id="DeleteSubmit1" name="${lotteryId}_${gameRule.ruleId}"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a> -->
														</th>
													</tr>
													<tr>
														<th colspan="6"><strong>休市维护周期</strong>&nbsp;&nbsp; ${gameRule.openAwardPeriod }</th>
													</tr>
													<tr>
														<th colspan="6"><strong>休市维护时间</strong>&nbsp;&nbsp; ${gameRule.stopStartTime } - ${gameRule.stopEndTime }</th>
													</tr>
												</thead>
											</table>
										</div>
									</c:if>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
		
	<!-- add -->
	<div id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
     <div  id="DivSuccessful"   class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>
     <textarea id="DeleteTip" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定要删除该奖期规则吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-safePassword"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
   
 	<textarea id="AuditTip" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text"> 您确定审核通过该条奖期规则吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-Audit1">确 定<b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDd">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    <textarea id="AuditTip2" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text"> 您确定<spring:message code="gameCenter_shenhebutongguo" />该条奖期规则吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-Audit1">确 定<b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDd">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    <textarea id="calcanTip" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text"> 您确定停止该条奖期规则吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="#" class="btn btn-important " id="J-submit-calcan1">确 定<b class="btn-inner"></b></a>
                <a href="#" class="btn" id="closeDd">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
    <script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameIssue/gameIssueIndex.js"></script>
</body>
