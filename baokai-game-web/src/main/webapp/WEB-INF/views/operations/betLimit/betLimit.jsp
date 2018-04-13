<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
	<title><spring:message code="gameCenter_touzhuxianzhi" /></title>
	
</head>
<body>
    <div id="tab_menu_id" style="display:none">betLimitMenu</div>
	<div class="col-crumbs">
	<div class="crumbs">
		<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>
		<c:choose>
			<c:when test="${pageType=='modify' }">修改投注限制</c:when>
			<c:when test="${pageType=='audit' }">审核投注限制</c:when>
			<c:when test="${pageType=='auditDetail' }">审核投注限制</c:when>
			<c:when test="${pageType=='publish' }">发布投注限制</c:when>
			<c:otherwise><spring:message code="gameCenter_touzhuxianzhi" /></c:otherwise>
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
								<input type="hidden" id="lotteryId" value="${lotteryId}"/>
								<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toBetLimit?lotteryid=">
							</div>
							<div class="ui-tab-content clearfix">
								<h3 class="ui-title">
									<c:choose>
										<c:when test="${status==3 }">
											<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_BETLIMIT_AUDIT">
                                                <a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt1">审核通过<b class="btn-inner"></b></a>
                                                <a class="btn btn-important " href="javascript:void(0);" id="J-Audit-Butt2"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
                                            </permission:hasRight>
										</c:when>

                                        <c:when test="${status==4}">
                                            <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_BETLIMIT_PUBLISH">
                                                <a href="#" class="btn btn-small" id="J-button-publish">发 布<b class="btn-inner"></b></a>
                                                <a href="#" class="btn btn-small" id="J-button-publish2">发布不通过<b class="btn-inner"></b></a>
                                            </permission:hasRight>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${pageType!='modify'}">
                                            <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_BETLIMIT_EDIT">
                                                <a class="btn btn-small" href="${currentContextPath}/gameoa/toModifyBetLimit?lotteryid=${lotteryId }"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
                                            </permission:hasRight>
                                            </c:if>
                                        </c:otherwise>
									</c:choose>
									<spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include>
									<input type="hidden" id="lotteryid" value="${lotteryId }"/>
									<c:choose>
										<c:when test="${status==3 }">
											<span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">待审核</span></span>
											<c:if test="${!empty modifier}">修改人：${modifier}</c:if>
										</c:when>
										<c:when test="${status==4 }">
											<span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">待发布</span></span>
											 <c:if test="${!empty modifier}">修改人：${modifier}</c:if>
                                   			 <c:if test="${!empty checker}">审核人：${checker}</c:if>
										</c:when>
										<c:when test="${status==5 }">
											<span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">审核未通过</span></span>
										</c:when>
										<c:when test="${status==6}">
											<span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-red">发布未通过</span></span>
										</c:when>										
										<c:otherwise>
											<span class="info"><spring:message code="gameCenter_zhuangtai" />：<span class="color-green">进行中</span></span>										
										</c:otherwise>
									</c:choose>
                                   
                                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <c:if test="${pageType=='modify' }">请输入期望奖金限额：<input  id="limitMoney" type="text" class="input w-2"/> <input id="limitButton" type="button" value="一键设置" /></c:if>

								</h3>
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th>玩法群</th>
											<th>玩法组</th>
											<th>玩法/投注方式</th>
											<th>倍数限制（倍）</th>
											<th>可能中奖最大金额（以${awardGroupName}为例）</th>
										</tr>
									</thead>
									<tbody>
									
										<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
											<% int groupMethodCount = 0; %>
											<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
												<c:forEach var="methodVar" items="${setVar.methodCodes }" varStatus="methodIndex">
												<%groupMethodCount++; %>
												</c:forEach>
											</c:forEach>
											
											<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
												<c:forEach var="methodVar" items="${setVar.methodCodes }" varStatus="methodIndex">

<%-- 											<c:if test="${groupVar.gameGroupCode == 18 && setVar.gameSetCode == 13 && methodVar.betMethodCode == 65 && (lotteryId == 99301 || lotteryId == 99302 || lotteryId == 99303 || lotteryId == 99304 || lotteryId == 99305 || lotteryId == 99306 )}"> --%>
<!-- 												<tr> -->
<!-- 												<td> -->
<%-- 												${lotteryId}eee --%>
<!-- 												</td> -->
<!-- 												</tr> -->
<%-- 											</c:if>													 --%>
<!-- 												<tr> -->
<!-- 												<td> -->
<%-- 												${methodVar.specialMultiple[0]} --%>
<!-- 												</td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 												<td> -->
<%-- 												${setVar.gameSetCode} --%>
<!-- 												</td> -->
<!-- 												</tr> -->
<!-- 												<tr> -->
<!-- 												<td> -->
<%-- 												${methodVar.betMethodCode} --%>
<!-- 												</td> -->
<!-- 												</tr> -->
												<c:if test="${groupVar.gameGroupCode == 18 && setVar.gameSetCode == 13 && methodVar.betMethodCode == 65 && (lotteryId == 99301 || lotteryId == 99302 || lotteryId == 99303 || lotteryId == 99304 || lotteryId == 99305 || lotteryId == 99306 )}">
													<tr>
														<c:if test="${setIndex.index==0 && methodIndex.index == 0}"><td rowspan="7">${groupVar.gameGroupCodeName }</td></c:if>
														<c:if test="${methodIndex.index == 0}"><td rowspan="6">定单双</td></c:if>
														
														<td>5单0双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[1]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[1]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_1" value="${methodVar.specialMultiple[0] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[0] == -1 && methodVar.specialMultiple_bak[0] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[0] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[0] == -1 && methodVar.specialMultiple_bak[0] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[0] != null && methodVar.specialMultiple_bak[0] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[0]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[0] != null && methodVar.specialMultiple_bak[0] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[0] }">${methodVar.specialMultiple[0]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[0]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[0] == -1 || methodVar.specialMultiple[0] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[0]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[0] == -1 || methodVar.specialMultiple[0] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[1]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[0]*methodVar.specialMaxBonus[1]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
													<tr>
														<td>4单1双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[3]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[3]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_2" value="${methodVar.specialMultiple[1] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[1] == -1 && methodVar.specialMultiple_bak[1] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[1] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[1] == -1 && methodVar.specialMultiple_bak[1] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[1] != null && methodVar.specialMultiple_bak[1] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[1]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[1] != null && methodVar.specialMultiple_bak[1] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[1] }">${methodVar.specialMultiple[1]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[1]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[1] == -1 || methodVar.specialMultiple[1] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[1]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[1] == -1 || methodVar.specialMultiple[1] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[3]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[1]*methodVar.specialMaxBonus[3]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
													<tr>
														<td>3单2双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[5]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[5]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_3" value="${methodVar.specialMultiple[2] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[2] == -1 && methodVar.specialMultiple_bak[2] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[2] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[2] == -1 && methodVar.specialMultiple_bak[2] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[2] != null && methodVar.specialMultiple_bak[2] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[2]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[2] != null && methodVar.specialMultiple_bak[2] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[2] }">${methodVar.specialMultiple[2]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[2]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[2] == -1 || methodVar.specialMultiple[2] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[2]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[2] == -1 || methodVar.specialMultiple[2] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[5]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[2]*methodVar.specialMaxBonus[5]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
													<tr>
														<td>2单3双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[4]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[4]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_4" value="${methodVar.specialMultiple[3] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[3] == -1 && methodVar.specialMultiple_bak[3] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[3] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[3] == -1 && methodVar.specialMultiple_bak[3] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[3] != null && methodVar.specialMultiple_bak[3] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[3]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[3] != null && methodVar.specialMultiple_bak[3] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[3] }">${methodVar.specialMultiple[3]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[3]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[3] == -1 || methodVar.specialMultiple[3] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[3]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[3] == -1 || methodVar.specialMultiple[3] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[4]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[3]*methodVar.specialMaxBonus[4]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
													<tr>
														<td>1单4双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[2]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[2]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_5" value="${methodVar.specialMultiple[4] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[4] == -1 && methodVar.specialMultiple_bak[4] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[4] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[4] == -1 && methodVar.specialMultiple_bak[4] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[4] != null && methodVar.specialMultiple_bak[4] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[4]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[4] != null && methodVar.specialMultiple_bak[4] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[4] }">${methodVar.specialMultiple[4]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[4]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[4] == -1 || methodVar.specialMultiple[4] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[4]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[4] == -1 || methodVar.specialMultiple[4] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[2]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[4]*methodVar.specialMaxBonus[2]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
													<tr>
														<td>0单5双</td>
														
														<c:choose>
															<c:when test="${pageType=='modify' }">
																<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																		<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																			<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[0]/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																		</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																		<input type="text" pro_maxBonus="${methodVar.specialMaxBonus[0]/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_6" value="${methodVar.specialMultiple[5] }" class="input w-2"/>
																	</c:if>
																</td>
															</c:when>
															<c:when test="${pageType=='audit' || pageType=='publish'}">
															<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																			<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																		</c:if>
																		<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																			<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																		</c:if>
																	</c:forEach>
																</td>
															</c:if>
															<c:if test="${fn:length(methodVar.assistList)==0 }">
																<c:choose>
																	<c:when test="${methodVar.specialMultiple[5] == -1 && methodVar.specialMultiple_bak[5] != null }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[5] }">无限制</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple[5] == -1 && methodVar.specialMultiple_bak[5] == null }">
																		<td>无限制</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[5] != null && methodVar.specialMultiple_bak[5] == -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.specialMultiple[5]}</span>
																		</td>
																	</c:when>
																	<c:when test="${methodVar.specialMultiple_bak[5] != null && methodVar.specialMultiple_bak[5] != -1 }">
																		<td>
																			<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.specialMultiple_bak[5] }">${methodVar.specialMultiple[5]}</span>
																		</td>
																	</c:when>
																	<c:otherwise><td>${methodVar.specialMultiple[5]}</td></c:otherwise>
																</c:choose>
															</c:if>
															</c:when>
															<c:otherwise>
																<td>
																	<c:choose>
																		<c:when test="${methodVar.specialMultiple[5] == -1 || methodVar.specialMultiple[5] == null}">
																			<c:if test="${fn:length(methodVar.assistList)>0 }">
																			
																				<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																				</c:forEach>
																			
																			</c:if>
																			<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																		</c:when>
																		<c:otherwise>${methodVar.specialMultiple[5]}</c:otherwise>
																	</c:choose>
																</td>
															</c:otherwise>
														</c:choose>

														<td>
														<c:choose>
															<c:when test="${methodVar.specialMultiple[5] == -1 || methodVar.specialMultiple[5] == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMaxBonus[0]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
															<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.specialMultiple[5]*methodVar.specialMaxBonus[0]/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
														</c:choose>
														</td>
													</tr>
													
												</c:if>
												<c:if test="${groupVar.gameGroupCode == 18 && setVar.gameSetCode == 13 && methodVar.betMethodCode == 66 && (lotteryId == 99301 || lotteryId == 99302 || lotteryId == 99303 || lotteryId == 99304 || lotteryId == 99305 || lotteryId == 99306 )}">
													<tr>
														<td rowspan="1">${methodVar.betMethodName }</td>
														
														<td>${methodVar.betMethodName }</td>
															
															<c:choose>
																<c:when test="${pageType=='modify' }">
																	<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.maxBonus/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																	</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																	<input type="text" pro_maxBonus="${methodVar.maxBonus/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_0" value="${methodVar.multiple }" class="input w-2"/>
																	</c:if>
																	</td>
																</c:when>
																<c:when test="${pageType=='audit' || pageType=='publish'}">
																<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																					<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																						<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																						</c:if>
																						<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																						<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																						</c:if>
																					</c:forEach>
																				</td>
																		</c:if>
																		<c:if test="${fn:length(methodVar.assistList)==0 }">
																	<c:choose>
																		
																		
																		<c:when test="${methodVar.multiple == -1 && methodVar.multiple_bak != null }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.multiple_bak }">无限制</span>
																			</td>
																		</c:when>
																		<c:when test="${methodVar.multiple == -1 && methodVar.multiple_bak == null }">
																			<td>无限制</td>
																		</c:when>
																		<c:when test="${methodVar.multiple_bak != null && methodVar.multiple_bak == -1 }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.multiple}</span>
																			</td>
																		</c:when>
																		<c:when test="${methodVar.multiple_bak != null && methodVar.multiple_bak != -1 }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.multiple_bak }">${methodVar.multiple}</span>
																			</td>
																		</c:when>
																		<c:otherwise><td>${methodVar.multiple}</td></c:otherwise>
																		
																	</c:choose>
																	</c:if>
																</c:when>
																<c:otherwise>
																	<td>
																		<c:choose>
																			<c:when test="${methodVar.multiple == -1 || methodVar.multiple == null}">
																				<c:if test="${fn:length(methodVar.assistList)>0 }">
																				
																					<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																						<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																					</c:forEach>
																				
																				</c:if>
																				<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																			</c:when>
																			<c:otherwise>${methodVar.multiple}</c:otherwise>
																		</c:choose>
																	</td>
																</c:otherwise>
															</c:choose>
	
															<td>
															<c:choose>
																			<c:when test="${methodVar.multiple == -1 || methodVar.multiple == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.maxBonus/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
																			<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.multiple*methodVar.maxBonus/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
																		</c:choose>
															</td>
														
													</tr>
												</c:if>
												<c:if test="${groupVar.gameGroupCode != 18 && setVar.gameSetCode != 13 && methodVar.betMethodCode != 65}">
														<tr>													
															<c:if test="${setIndex.index==0 && methodIndex.index == 0}"><td rowspan="<%=groupMethodCount%>">${groupVar.gameGroupCodeName }</td></c:if>
															<c:if test="${methodIndex.index == 0}"><td rowspan="${fn:length(setVar.methodCodes)}">${setVar.gameSetCodeName }</td></c:if>
															<td>${methodVar.betMethodName }</td>
															
															<c:choose>
																<c:when test="${pageType=='modify' }">
																	<td>
																	<c:if test="${fn:length(methodVar.assistList)>0 }">
																	<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																		<div>${assist.remark }  :<input type="text" pro_maxBonus="${methodVar.maxBonus/10000.00 }" name="assist"  title="${assist.id }" value="${assist.maxMultiple }" class="input w-2"/></div>
																	</c:forEach>
																	</c:if>
																	<c:if test="${fn:length(methodVar.assistList)==0 }">
																	<input type="text" pro_maxBonus="${methodVar.maxBonus/10000.00 }"  name="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${methodVar.betMethodCode }_text_0" value="${methodVar.multiple }" class="input w-2"/>
																	</c:if>
																	</td>
																</c:when>
																<c:when test="${pageType=='audit' || pageType=='publish'}">
																<c:if test="${fn:length(methodVar.assistList)>0 }">
																<td>				
																					<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																					<c:if test="${assist.maxMultiple_bak!=null &&  assist.maxMultiple_bak != assist.maxMultiple}">
																						<div><span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${assist.maxMultiple_bak }">${assist.remark }  :  ${assist.maxMultiple }</span></div>
																						</c:if>
																						<c:if test="${assist.maxMultiple_bak==null || assist.maxMultiple_bak == assist.maxMultiple}">
																						<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																						</c:if>
																					</c:forEach>
																				</td>
																		</c:if>
																		<c:if test="${fn:length(methodVar.assistList)==0 }">
																	<c:choose>
																		
																		
																		<c:when test="${methodVar.multiple == -1 && methodVar.multiple_bak != null }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.multiple_bak }">无限制</span>
																			</td>
																		</c:when>
																		<c:when test="${methodVar.multiple == -1 && methodVar.multiple_bak == null }">
																			<td>无限制</td>
																		</c:when>
																		<c:when test="${methodVar.multiple_bak != null && methodVar.multiple_bak == -1 }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="无限制">${methodVar.multiple}</span>
																			</td>
																		</c:when>
																		<c:when test="${methodVar.multiple_bak != null && methodVar.multiple_bak != -1 }">
																			<td>
																				<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${methodVar.multiple_bak }">${methodVar.multiple}</span>
																			</td>
																		</c:when>
																		<c:otherwise><td>${methodVar.multiple}</td></c:otherwise>
																		
																	</c:choose>
																	</c:if>
																</c:when>
																<c:otherwise>
																	<td>
																		<c:choose>
																			<c:when test="${methodVar.multiple == -1 || methodVar.multiple == null}">
																				<c:if test="${fn:length(methodVar.assistList)>0 }">
																				
																					<c:forEach items="${ methodVar.assistList}" var="assist" varStatus="assistIndex">
																						<div>${assist.remark }  :  ${assist.maxMultiple }</div>
																					</c:forEach>
																				
																				</c:if>
																				<c:if test="${fn:length(methodVar.assistList)==0 }">无限制</c:if>
																			</c:when>
																			<c:otherwise>${methodVar.multiple}</c:otherwise>
																		</c:choose>
																	</td>
																</c:otherwise>
															</c:choose>
	
															<td>
															<c:choose>
																			<c:when test="${methodVar.multiple == -1 || methodVar.multiple == null}"><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.maxBonus/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:when>
																			<c:otherwise><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${methodVar.multiple*methodVar.maxBonus/10000.00 }" pattern="#,###.##"  minFractionDigits="2"/></c:otherwise>
																		</c:choose>
															</td>
														</tr>
													</c:if>
												</c:forEach>
											</c:forEach>
										</c:forEach>
										
										<!-- 判断页面来源 -->
										<c:choose>
											<c:when test="${pageType=='modify' }">
												<tr>
													<td colspan="5" class="text-center"><a class="btn btn-important " href="javascript:void(0);" id="J-Modify-Butt1">保存修改<b class="btn-inner"></b></a></td>
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
	<textarea id="DivReset" style="display:none;">
        <div class="bd">
             <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">
                    <c:if test="${pageType=='modify'}">您确认修改当前投注限制吗？</c:if>
                    <c:if test="${status==3}">
                        您确认审批通过当前投注限制吗？
                    </c:if>
				</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Submit-Butt2">确 认<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="DivClose2">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
    <textarea id="DivReset2" style="display:none;">
        <div class="bd">
             <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确认审批不通过当前投注限制吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Submit-Butt22">确 认<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="DivClose22">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
    <textarea id="DivUnfillContent" style="display:none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您还有未填内容，请完整填写</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="CloseDf">关 闭<b class="btn-inner"></b></a>                
            </div>
        </div>
    </textarea>
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
    <script type="text/javascript">
		var currentContextPath = '${currentContextPath}';
	</script>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/configJS/betLimit.js"></script>
</body>
