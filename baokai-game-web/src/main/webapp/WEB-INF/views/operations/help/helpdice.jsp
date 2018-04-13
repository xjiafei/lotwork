<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
	<title><spring:message code="gameCenter_wanfamiaoshu" /></title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">helpMenu</div>
	<div class="col-crumbs">
	<div class="crumbs">
		<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>
		<c:choose>
			<c:when test="${pageType=='modify' }">修改玩法描述</c:when>
			<c:when test="${pageType=='audit' }">审核玩法描述</c:when>
			<c:when test="${pageType=='auditDetail' }">审核玩法描述</c:when>
			<c:otherwise><spring:message code="gameCenter_wanfamiaoshu" /></c:otherwise>
		</c:choose>
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
							</div>
							<div class="ui-tab-content clearfix">
								<h3 class="ui-title">
									<c:choose>
										<c:when test="${pageType=='modify' }">
									            <a href="toHelp?lotteryid=${lotteryId }">&lt;&lt;返回玩法描述</a>
									        </c:when>
									        <%--<c:when test="${checkStatus == 3 }">
									            <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_HELP_AUDIT">
									                <a href="${currentContextPath}/gameoa/toAuditHelpDetail?lotteryid=${lotteryId }" class="btn btn-small">审
									                    核<b class="btn-inner"></b></a>
									            </permission:hasRight>
									        </c:when>--%>
									        <c:when test="${checkStatus == 3 }">
									            <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_HELP_AUDIT">
									                <%--<a href="${currentContextPath}/gameoa/toAuditHelpDetail?lotteryid=${lotteryId }" class="btn btn-small"><spring:message code="gameCenter_shenhe" /><b class="btn-inner"></b></a>--%>
									                <a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt1">审核通过<b
									                        class="btn-inner"></b></a>
									                <a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt2"><spring:message code="gameCenter_shenhebutongguo" /><b
									                        class="btn-inner"></b></a>
									            </permission:hasRight>
									        </c:when>
									        <c:when test="${checkStatus == 4 }">
												<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_HELP_PUBLISH">
									                <%--<a href="${currentContextPath}/gameoa/toAuditHelpDetail?lotteryid=${lotteryId }" class="btn btn-small"><spring:message code="gameCenter_shenhe" /><b class="btn-inner"></b></a>--%>
									                <a class="btn btn-important " href="javascript:void(0);" id="J-Publish-Butt1">发布通过<b
									                        class="btn-inner"></b></a>
									                <a class="btn btn-important " href="javascript:void(0);" id="J-Publish-Butt2">发布不通过<b
									                        class="btn-inner"></b></a>
									            </permission:hasRight>
									        </c:when>
									        <c:otherwise>
									           <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_HELP_EDIT">
									                <a class="btn btn-small" href="${currentContextPath}/gameoa/toModifyHelp?lotteryid=${lotteryId }"><spring:message code="gameCenter_xiugai" /><b
									                        class="btn-inner"></b></a>
									            </permission:hasRight>
									        </c:otherwise>
									</c:choose>
									<input type="hidden" id="lotteryId" value="${lotteryId}"/>
									<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toHelp?lotteryid=">
									<spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include>
									<input type="hidden" id="lotteryid" value="${lotteryId }"/>
									<c:choose>
								        <c:when test="${checkStatus==3}">
								            <span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">待审核</span></span>
								             <c:if test="${!empty modifier}">修改人：${modifier}</c:if>
								        </c:when>
								        <c:when test="${checkStatus==4 }">
								            <span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">待发布</span></span>
								            <c:if test="${!empty modifier}">修改人：${modifier}</c:if>
								   			<c:if test="${!empty checker}">审核人：${checker}</c:if>
								        </c:when>
								        <c:when test="${checkStatus==5 }">
								            <span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">审核未通过</span></span>
								        </c:when>
								        <c:when test="${checkStatus==6 }">
								            <span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">发布未通过</span></span>
								        </c:when>
								        <c:otherwise>
								            <span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-green">进行中</span></span>
								        </c:otherwise>
								    </c:choose>
								    
								</h3>
								
								<div class="ui-form lottery-info">
									<label for="text" class="ui-label">开奖周期说明：</label>
									
									<div class="textarea w-12">
										<c:choose>
											<c:when test="${pageType=='modify' }">
												<textarea id="lotteryHelpDes" name="lotteryHelpDes">${lotteryHelpDes }</textarea>
											</c:when>
											<c:when test="${pageType=='audit' || checkStatus==4}">
												<c:choose>
													<c:when test="${lotteryHelpDes_bak != null }">
														<td>
															<div class="textarea-mark-cont w-6">
																<div class="text input-mark" data-showtip="${lotteryHelpDes_bak }">${lotteryHelpDes}</div>
																<div class="textarea-mark-old" style="display:none;">${lotteryHelpDes_bak }</div>
															</div>
														</td>
													</c:when>
													<c:otherwise>
														<td><div class="textarea w-6"><textarea readonly>${lotteryHelpDes}</textarea></div></td>
													</c:otherwise>
												</c:choose>
											</c:when>
											<c:otherwise><textarea readonly>${lotteryHelpDes }</textarea></c:otherwise>
										</c:choose>
									</div>
								</div>
								
								<c:choose>
										<c:when test="${pageType=='modify' || pageType=='audit' || pageType=='auditDetail' }">
											<ul id="menu" class="play-select-title clearfix">
												<li class="current" onclick="settab(0)">和值</li>
												<li onclick="settab(1)">三同号通选</li>
												<li onclick="settab(2)">三同号单选</li>
												<li onclick="settab(3)">二同号复选</li>
												<li onclick="settab(4)">二不同号</li>
												<li onclick="settab(5)">猜一个号(特殊)</li>
												<li onclick="settab(6)">特殊</li>
											</ul>
										</c:when>
										<c:otherwise>
											<ul id="menu" class="ui-tab-title  clearfix">
												<li class="current" onclick="settab(0)">和值</li>
												<li onclick="settab(1)">三同号通选</li>
												<li onclick="settab(2)">三同号单选</li>
												<li onclick="settab(3)">二同号复选</li>
												<li onclick="settab(4)">二不同号</li>
												<li onclick="settab(5)">猜一个号(特殊)</li>
												<li onclick="settab(6)">特殊</li>
											</ul>
										</c:otherwise>
									</c:choose>
								
								<div id="mainContent">
									<!-- 选一 -->
									<ul id="DivRules" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==34 }">
															<% int groupMethodCount1 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount1++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount1%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!-- 选二 -->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==35 }">
															<% int groupMethodCount2 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount2++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount2%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!-- 选三-->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==36 }">
															<% int groupMethodCount3 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount3++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount3%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!-- 选四 -->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==39 }">
															<% int groupMethodCount4 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount4++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount4%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${seriesConfig.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!-- 选五-->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==41}">
															<% int groupMethodCount5 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount5++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount5%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!--选六 -->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==42}">
															<% int groupMethodCount6 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount6++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount6%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									
									<!-- 选七-->
									<ul id="DivRules" style="display: none;" class="ui-form ui-tab-content ui-tab-content-current">
										<li>
											<table class="table table-info table-border">
												<thead>
													<tr>
														<th>玩法群</th>
														<th>玩法组</th>
														<th>玩法/投注方式</th>
														<th>玩法提示</th>
														<th>示例</th>
													</tr>
												</thead>
												<tbody>
												
													<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
														<c:if test="${groupVar.gameGroupCode==43 }">
															<% int groupMethodCount7 = 0; %>
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																<%groupMethodCount7++; %>
																</c:forEach>
															</c:forEach>
														
															<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
																<c:forEach var="helpVar" items="${setVar.helpCodes }" varStatus="helpIndex">
																	<tr>													
																		<c:if test="${setIndex.index==0 && helpIndex.index == 0}"><td rowspan="<%=groupMethodCount7%>">${groupVar.gameGroupCodeName }</td></c:if>
																		<c:if test="${helpIndex.index == 0}"><td rowspan="${fn:length(setVar.helpCodes)}">${setVar.gameSetCodeName }</td></c:if>
																		<td>${helpVar.betMethodName }</td>
																		
																		<c:choose>
																			<c:when test="${pageType=='modify' }">
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text">${helpVar.gamePromptDes }</textarea></div></td>
																				<td><div class="textarea w-6"><textarea name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${helpVar.betMethodCode }_text2">${helpVar.gameExamplesDes }</textarea></div></td>
																			</c:when>
																			<c:when test="${pageType=='audit' || checkStatus==4 }">
																				<c:choose>
																					<c:when test="${helpVar.gamePromptDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gamePromptDes_bak }">${helpVar.gamePromptDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gamePromptDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gamePromptDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																				<c:choose>
																					<c:when test="${helpVar.gameExamplesDes_bak != null }">
																						<td>
																							<div class="textarea textarea-mark-cont w-6">
																								<div class="text input-mark" data-showtip="${helpVar.gameExamplesDes_bak }">${helpVar.gameExamplesDes}</div>
																								<div class="textarea-mark-old" style="display:none;">${helpVar.gameExamplesDes_bak }</div>
																							</div>
																						</td>
																					</c:when>
																					<c:otherwise>
																						<td><div class="textarea w-6"><textarea readonly>${helpVar.gameExamplesDes}</textarea></div></td>
																					</c:otherwise>
																				</c:choose>
																			</c:when>
																			<c:otherwise>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gamePromptDes }</textarea></div></div></td>
																				<td><div class="textarea w-6"><div class="text"><textarea readonly>${helpVar.gameExamplesDes }</textarea></div></div></td>
																			</c:otherwise>
																		</c:choose>
																	</tr>
																</c:forEach>
															</c:forEach>
														</c:if>
														
													</c:forEach>
													
												</tbody>
											</table>
										</li>
									</ul>
									<table class="table table-info table-border">
										<tbody>
											<c:choose>
												<c:when test="${pageType=='modify' }">
													<tr>
														<td class="text-center"><a class="btn btn-important " href="javascript:void(0);" id="J-Modify-Butt1">保存修改<b class="btn-inner"></b></a></td>
													</tr>
												</c:when>
												<c:when test="${pageType=='auditDetail' }">
													<tr>
														<td class="text-center">
															<a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt1">审核通过<b class="btn-inner"></b></a>
															<a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt2"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
														</td>
													</tr>
												</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</tbody>
									</table>
									
								</div>
								
								
							</div>
						</div>
						
					</div>
				</div>
			</div>
     <div  id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn" id="CloseDs">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    <div  id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>
    
    <style>
	.textarea-mark-cont,.textarea-mark-cont:hover {border:1px dotted #F00;}
	.j-ui-tip-info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	.textarea{
	}
	.textarea .text{
		border: 0 none;
	    color: #555;
	    height: 80px;
	    line-height: 18px;
	    resize: none;
	    width: 100%;
	    white-space: normal;
	}
	</style>
	
	<script type="text/javascript">
		var currentContextPath = '${currentContextPath}';
		
		function settab(index){
			var tli =  document.getElementById("menu").getElementsByTagName("li"); 
			var mli = document.getElementById("mainContent").getElementsByTagName("ul");
			
			for(var i = 0;i < tli.length; i++){ 
	           tli[i].className = i == index ? "current" : ""; 
	           mli[i].style.display = i == index ? "block" : "none"; 
	        } 
		}
	</script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/configJS/help.js"></script>
</body>
