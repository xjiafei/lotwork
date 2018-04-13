<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<head>
	<title><spring:message code="gameCenter_xiaoshouzhuangtai" /></title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">soldMenu</div>
	<div class="col-crumbs">
	<div class="crumbs">
		<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>
		<c:choose>
			<c:when test="${pageType=='modify' }">修改销售状态</c:when>
			<c:when test="${pageType=='audit' }">审核销售状态</c:when>
			<c:when test="${pageType=='auditDetail' }">审核销售状态</c:when>
			<c:otherwise>
				<c:if test="${checkStatus == 4 }">发布</c:if>销售状态
			</c:otherwise>
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
						
						<input type="hidden" id="lotteryid" value="${lotteryId }"/>
						<input type="hidden" id="status" value="${status }"/>
						<input type="hidden" id="checkStatus" value="${checkStatus }"/>
						<input type="hidden" id="lotteryId" value="${lotteryId}"/>
						<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toSellingStatus?lotteryid=">
						<div class="ui-tab-content clearfix">
							<h3 class="ui-title">
								<c:choose>
									<%--<c:when test="${checkStatus == 2 }">
										&lt;%&ndash;<a href="toSellingStatus?lotteryid=${lotteryId }">&lt;&lt;返回销售状态</a>&ndash;%&gt;
									</c:when>--%>
									<c:when test="${checkStatus==3 }">
										<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SALESTATUS_AUDIT">
                                            <a class="btn btn-small " href="javascript:void(0);" id="J-Audit-Butt1">审核通过<b class="btn-inner"></b></a>
                                            <a class="btn btn-small " href="javascript:void(0);" id="J-Audit-Butt2"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
                                        </permission:hasRight>
									</c:when>

									<c:when test="${checkStatus==4}">
                                        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SALESTATUS_PUBLISH">
                                            <a class="btn btn-small"  id="J-button-publish" href="javascript:void(0);">发布<b class="btn-inner"></b></a>
                                            <a class="btn btn-small"  id="J-button-publish2" href="javascript:void(0);">发布不通过<b class="btn-inner"></b></a>
                                        </permission:hasRight>
									</c:when>
                                    <c:otherwise>
                                        <c:if test="${pageType!='modify'}">
                                        <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_SALESTATUS_EDIT">
                                            <a class="btn btn-small" href="${currentContextPath}/gameoa/toModifySellingStatus?lotteryid=${lotteryId }"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
                                        </permission:hasRight>
                                        </c:if>
                                    </c:otherwise>
								</c:choose>

                                <spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include>
								<span class="info"><spring:message code="gameCenter_xiaoshouzhuangtai" />：
								<c:choose>
									<c:when test="${pageType == 'modify' }">
										<c:if test="${status==1 }">
											<span id="J-game-closebutton" class="color-green" style="cursor:pointer">在售</span>
										</c:if>
										<c:if test="${status==0 }">
											<span id="J-game-closebutton" class="color-red" style="cursor:pointer">停售中</span>
										</c:if>
										<span class="color-gray">（ 说明：绿色文字表示销售中，红色文字表示停售中）</span>
										<input id="J-time-start" value="" class="input" type="text">
									</c:when>
									<c:otherwise>
										<c:if test="${status==1 }"><span class="color-green">在售</span></c:if>
										<c:if test="${status==0 }"><span class="color-red">停售中</span></c:if>
											<span class="color-gray">（ 说明：绿色文字表示销售中，红色文字表示停售中）</span><spring:message code="gameCenter_zhuangtai" />：
										<c:if test="${checkStatus == 2 }"><span class="color-green">进行中</span></c:if>
										<c:if test="${checkStatus == 3 }"><span class="color-red">待审核 </span>&nbsp;<c:if test="${!empty modifier}">修改人：${modifier}</c:if></c:if>
										<c:if test="${checkStatus == 4 }"><span class="color-red">待发布</span>&nbsp;<c:if test="${!empty modifier}">修改人：${modifier}</c:if>
                                		<c:if test="${!empty checker}">审核人：${checker}</c:if></c:if>
										<c:if test="${checkStatus == 5 }"><span class="color-red"><spring:message code="gameCenter_shenhebutongguo" /></span></c:if>
										<c:if test="${checkStatus == 6 }"><span class="color-red">发布不通过</span></c:if>
										<c:if test="${checkStatus == 3 || checkStatus == 4 }">
											<input id="J-time-start" value="${takeOffTime}" class="input" type="text" disabled>
										</c:if>
									</c:otherwise>
								</c:choose>
								</span>
                                
							</h3>
							
								<table class="table table-info table-border">
									<thead>
										<tr>
											<th>玩法群</th>
											<th>玩法组</th>
											<th>玩法/投注方式</th>
										</tr>
									</thead>
									<tbody>
									
										<c:forEach var="groupVar" items="${strucs }" varStatus="groupIndex">
											<% int groupMethodCount = 0; %>
											<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
												<c:forEach var="sellingVar" items="${setVar.sellingCodes }" varStatus="sellingIndex">
													<%groupMethodCount++; %>
												</c:forEach>
											</c:forEach>
											
											
											<c:forEach var="setVar" items="${groupVar.setCodes }" varStatus="setIndex">
												<c:forEach var="sellingVar" items="${setVar.sellingCodes }" varStatus="sellingIndex">
													<tr>		
																
														<!-- 区分在售和停售(只颜色区分，code一样) -->
														<c:choose>
															<c:when test="${setIndex.index==0 && sellingIndex.index == 0 && groupVar.gameGroupColorStatus == 1}">
																<td rowspan="<%=groupMethodCount%>" class="color-green">
																	<c:choose>
																		<c:when test="${pageType=='modify' }">
																			<span data-param="qun" code="${groupVar.gameGroupCode }_0_0">${groupVar.gameGroupCodeName }</span>
																		</c:when>
																		<c:otherwise>${groupVar.gameGroupCodeName }</c:otherwise>
																	</c:choose>
																</td>
															</c:when>
															<c:when test="${setIndex.index==0 && sellingIndex.index == 0 && groupVar.gameGroupColorStatus == 0}">
																<td rowspan="<%=groupMethodCount%>" class="color-red">
																	<c:choose>
																		<c:when test="${pageType=='modify' }">
																			<span data-param="qun" code="${groupVar.gameGroupCode }_0_0">${groupVar.gameGroupCodeName }</span>
																		</c:when>
																		<c:otherwise>${groupVar.gameGroupCodeName }</c:otherwise>
																	</c:choose>
																</td>
															</c:when>
														</c:choose>							
														
														
														<!-- 区分在售和停售(只颜色区分，code一样) -->
														<c:choose>
															<c:when test="${sellingIndex.index == 0 && setVar.gameSetColorStatus == 1}">
																<td rowspan="${fn:length(setVar.sellingCodes)}" class="color-green">
																	<c:choose>
																		<c:when test="${pageType=='modify' }">
																			<span data-param="zu" code="${groupVar.gameGroupCode }_${setVar.gameSetCode }_0">${setVar.gameSetCodeName }</span>
																		</c:when>
																		<c:otherwise>${setVar.gameSetCodeName }</c:otherwise>
																	</c:choose>
																</td>
															</c:when>
															<c:when test="${sellingIndex.index == 0 && setVar.gameSetColorStatus == 0 }">
																<td rowspan="${fn:length(setVar.sellingCodes)}"  class="color-red">
																	<c:choose>
																		<c:when test="${pageType=='modify' }">
																			<span data-param="zu" code="${groupVar.gameGroupCode }_${setVar.gameSetCode }_0">${setVar.gameSetCodeName }</span>
																		</c:when>
																		<c:otherwise>${setVar.gameSetCodeName }</c:otherwise>
																	</c:choose>
																</td>
															</c:when>
														</c:choose>
														
														
														<!-- 区分在售和停售(只颜色区分，code一样) -->
														<c:choose>
															<c:when test="${sellingVar.status==1 }">
															<td class="color-green">
															</c:when>
															<c:otherwise><td class="color-red"></c:otherwise>
														</c:choose>
																	<c:choose>
																		<c:when test="${sellingVar.status_changed == true }">
																			
																				<c:choose>
																					<c:when test="${pageType=='modify' }">
																						<span data-param="dan" code="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${sellingVar.betMethodCode }">${sellingVar.betMethodName }</span>
																					</c:when>
																					<c:when test="${pageType=='audit' ||  checkStatus == 4}">
																						<c:choose>
																							<c:when test="${sellingVar.status==0 }">
																								<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="已改为:可销售">${sellingVar.betMethodName }</span>
																							</c:when>
																							<c:otherwise>
																								<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="已改为:停售">${sellingVar.betMethodName }</span>
																							</c:otherwise>
																						</c:choose>
																					</c:when>
																					<c:otherwise>${sellingVar.betMethodName }</c:otherwise>
																				</c:choose>
																				<input type="hidden" name="modifyParamsHidden" value="${groupVar.gameGroupCode}_${setVar.gameSetCode}_${sellingVar.betMethodCode}_${sellingVar.status}_${setVar.gameSetColorStatus}_${groupVar.gameGroupColorStatus}"/>
																			
																		</c:when>
																		<c:otherwise>
																			<c:choose>
																				<c:when test="${pageType=='modify' }">
																					<span data-param="dan" code="${groupVar.gameGroupCode }_${setVar.gameSetCode }_${sellingVar.betMethodCode }">${sellingVar.betMethodName }</span>
																				</c:when>
																				<c:otherwise>${sellingVar.betMethodName }</c:otherwise>
																			</c:choose>
																			<input type="hidden" name="modifyParamsHidden" value="${groupVar.gameGroupCode}_${setVar.gameSetCode}_${sellingVar.betMethodCode}_${sellingVar.status}_${setVar.gameSetColorStatus}_${groupVar.gameGroupColorStatus}"/>
																		</c:otherwise>
																	</c:choose>
															</td>

													</tr>
												</c:forEach>
											</c:forEach>
										</c:forEach>
										</tbody>
									</table>
							
						</div>
					</div>
					
				</div>
			</div>
		</div>
	<script type="text/javascript">
		var currentContextPath = '${currentContextPath}';
	</script>
	<style>
		.table-info td span{
			text-decoration: underline;
			cursor:pointer;
		}
	</style>
    <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/configJS/sellingStatus.js"></script>
</body>
