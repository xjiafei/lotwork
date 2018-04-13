<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>奖期规则查看页-<spring:message code="gameCenter_chakanjiangqi" /></title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">issueRuleMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=99101&ruleId="><spring:message code="gameCenter_jiangqiguize"/></a>><spring:message code="gameCenter_chakanjiangqi" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main" style="margin-bottom: 220px;">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
							<h3 class="ui-title">
								<a href="${currentContextPath}/gameoa/gameIssueIndex?lotteryId=${lotteryId }&ruleId="><< 返回奖期规则列表</a>
							</h3>
							
							
							
							<form action="<%=request.getContextPath() %>/gameoa/queryGameIssues?lotteryId=${lotteryId}" method="post" id="J-form">
								
								<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
								<input type="hidden" id="lotteryId" name="lotteryId" value="${lotteryId }"/>
								<input type="hidden" id="queryType" name="queryType" value="${queryType }"/>
								<input type="hidden" id="showStartTime" name="showStartTime" value="${showStartTime}"/>
								<input type="hidden" id="showEndTime" name="showEndTime" value="${showEndTime}"/>
								<input type="hidden" id="message" value="${message }"/>
								<input type="hidden" id="checkAudit" name="checkAudit" value="${reqData.checkAudit}"/>
								<div class="">
									<h3 class="ui-title"><spring:message code="gameCenter_caizhongmingcheng" />：${gameAwardName }
									<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_EXPORT">									
									<a id="J-button-export" class="btn" href="javascript:$('#J-downLoad-form').submit();"><spring:message code="gameCenter_daochuliebiao" /><<b class="btn-inner"></b></a>	
									</permission:hasRight>					
								    <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_RULE_MANUAL">
									<a id="j-button-manual" class="btn" href="<%=request.getContextPath() %>/gameoa/preManualGameIssues?lotteryId=${lotteryId}"><spring:message code="gameCenter_shoudongshengchengjiangqi" /><b class="btn-inner"></b></a>
									</permission:hasRight>
									<%-- <a id="j-button-delete" class="btn" href="<%=request.getContextPath() %>/gameoa/preDeleteGameIssues?lotteryId=${lotteryId}">删除奖期<b class="btn-inner"></b></a> --%>
									</h3>
									<ul class="ui-search">
										
										<li class="date">
											<label for="" class="ui-label">显示时间段：</label>
											<input type="text" value="" class="input w-3" id="timesStart"> 至 <input type="text" value=""  class="input w-3" id="timesEnd">
										
										</li>
										<li>
										<label class="ui-label">
										<input type="checkbox" id="queryTypeId" autocomplete="off"   name="checkbox" value="1"  />仅显示实际已生成的奖期
										</label>
										</li>
										
										<li><label class="ui-label"><input type="checkbox" id="queryTypeId_1" autocomplete="off"  name="checkbox" value="2" />包含待审核内容</label></li>
										<li><a class="btn btn-important" href="javascript:void(0);" id="J-Submit">查 询<b class="btn-inner"></b></a></li>
									</ul>
									
									<table class="table table-info">
										<thead>
											<tr>
												<th><spring:message code="gameCenter_qihao" /></th>
												<th>开奖时间</th>
												<th>周期开始时间</th>
												<th>周期结束时间</th>
												<th style="width:35%"></th>
											</tr>
										</thead>
										<tbody>
											<% int count =0 ;%>
											<c:forEach items="${issueList }" var="issue" varStatus="issueIndex">
													<tr>
														<td id="webIssueCodeTd<%=count%>">${issue.webIssueCode }</td>
														<td id="openAwardTimeTd<%=count%>">${issue.openAwardTime }</td>
														<td id="saleStartTimeTd<%=count%>">${issue.saleStartTime }</td>
														<td id="saleEndTimeTd<%=count%>">${issue.saleEndTime }</td>
														<c:if test="${lotteryId==99701 && queryType ==1 }">
														<td>
														<!--  <td><input type ="button"   style="width:100px;height:25px;border-radius:5px;border:1px gray solid;color:#383838" value="调整开奖时间"/></td> -->
															
															<c:choose>
															<c:when test="${issue.display }">
															<div id="AwardTimeBtn<%=count%>" style="display:inline">
															<input type ="button"  class="btn" value="调整开奖时间" onclick="showChangeAwardTime(<%=count%>)"/>
															</div>
															</c:when>
															<c:otherwise>	
															<input type ="button"  class='btn btn-disable' disabled value="调整开奖时间" />
															</c:otherwise>
															</c:choose>
															
															
															<div id="AwardTimediv<%=count%>" style="display:none;">
															<input type="text" value="" class="input w-3" id="openAwardTime<%=count%>" />
															<input type ="button"  class="btn" value="完成" onclick="changeAwardTime(<%=count%>,${issue.webIssueCode },${issue.issueCode },${issue.id })" />
															</div>
															<c:if test="${issue.periodStatus == 1 && issue.display}"><!-- 銷售中 -->
															<div  style="display:inline">
															<input type ="button"  class="btn" value="顺延一期" onclick="extend(${issue.issueCode })"/>
															</div>
															</c:if>
															</td>												
														</c:if>
														
													
													</tr>
													<%  count ++;%>
											</c:forEach>
										</tbody>
									</table>
									<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
									<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
									 <pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
								</div>
							</form>
							
							<form action="<%=request.getContextPath() %>/gameoa/downLoadGameIssues?lotteryId=${lotteryId}" method="post" id="J-downLoad-form">
								<input type="hidden" id="queryTypeDown" name="queryTypeDown" value="${queryType }"/>
								<input type="hidden" id="showStartTimeDown" name="showStartTimeDown" value="${showStartTime}"/>
								<input type="hidden" id="showEndTimeDown" name="showEndTimeDown" value="${showEndTime}"/>
							</form>
						</div>
						<div style="position:absolute;left:100px; display:none" class="pop w-8">
							<div class="hd"><i class="close"></i></div>
							<div class="bd">
								<div class="pop-title">
									<i class="ico-waring"></i>
									<h4 class="pop-text">请至少选择一注投注号码！</h4>
								</div>
								<div class="pop-btn">
									<a href="javascript:void(0);" class="btn btn-important "><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
									<a href="javascript:void(0);" class="btn">取 消<b class="btn-inner"></b></a>
								</div>
							</div>
						</div>
						<div class="pop pop-success w-4" style="position:absolute;left:600px; display:none">
							<i class="ico-success"></i>
							<h4 class="pop-text">操作成功</h4>
						</div>
					</div>
				</div>
			</div>
			
			<div id="DivMessage" style="position:absolute;z-index:2; display:none"  class="pop w-8">
	        <div class="hd"><i class="close"></i>温馨提示</div>
	        <div class="bd">
	            <div class="pop-title">
	                <h4 class="pop-text">操作失败</h4>
	            </div>
	            <div class="pop-btn">
	                <a href="javascript:void(0);" id ="close" class="btn">关 闭<b class="btn-inner"></b></a>
	            </div>
	        </div>
	   		</div>
	   		
	   		<div id="extendCheckMsg" style="position:absolute;z-index:2; display:none"  class="pop w-8">
	        <div class="hd"><i class="close"></i>温馨提示</div>
	        <div class="bd">
	            <div class="pop-title">
	                <h4 class="pop-text">操作失败</h4>
	            </div>
	            <div class="pop-btn">
	             	<a href="javascript:void(0);" id ="doExtend" class="btn">是<b class="btn-inner"></b></a>
	                <a href="javascript:void(0);" id ="close2" class="btn">否<b class="btn-inner"></b></a>
	            </div>
	        </div>
	   		</div>
	   		<input type="hidden" id="id"  value=""/>
	   		<input type="hidden" id="nextId" value=""/>
	   		<input type="hidden" id="lotteryId" value="${lotteryId}"/>
	   		
		
		<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>	
		<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameIssue/queryIssues.js"></script>
</body>