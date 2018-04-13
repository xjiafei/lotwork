<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<head>
	<title>奖金组（列表页）-查看奖金组</title>
</head>
<body>
<div id="tab_menu_id" style="display:none">awardGroupMenu</div>
			<div class="col-crumbs">
            <div class="crumbs">
            	<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${gameAward.lotteryId }&status=&awardId="><spring:message code="gameCenter_jiangjinzu"/></a>>查看奖金组
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
							<div class="">
								<h3 class="ui-title"><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${gameAward.lotteryId }&status=&awardId=">&lt;&lt; 返回奖金组列表</a></h3>
								<ul class="ui-search">
									<li>
										<label class="ui-label" for=""><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<span class="ui-text-info">${gameAward.lotteryName }</span>
										<input type="hidden" name="gameAward.lotteryId" value="${gameAward.lotteryId }"/>
										<input type="hidden" id="miniLotteryProfit" value="${miniLotteryProfit}"/>
									</li>
									
									
									<c:if test="${gameAward.lotteryId != 99701}">
									<li>
										<label class="ui-label" for="">奖金组命名：</label>
										<span class="ui-text-info">${gameAward.awardGroupName}</span>
									</li>
									<li>
										<label class="ui-label" for="">返点设置：</label>
										<span class="ui-text-info"><c:if test="${gameAward.lotteryId != 99201}">直选及其他返点</c:if> <c:if test="${gameAward.lotteryId == 99201}">任选型</c:if></span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.directRet" value="${gameAward.directRet/10000}" id="J-point-general" disabled="disabled"/>
										<span class="ui-text-info color-gray">(设置范围 ：<label id="label1">0.05</label>- <label id="label2">0.06</label>即<label id="label3">5%</label>- <label id="label4">6%</label>)</span>
									</li>
									</c:if>
									<li>
									 <c:if test="${gameAward.lotteryId != 99301 &&  gameAward.lotteryId != 99302 &&  gameAward.lotteryId != 99303 &&  gameAward.lotteryId != 99304 &&  gameAward.lotteryId != 99305 &&  gameAward.lotteryId != 99306 &&  gameAward.lotteryId != 99701}">
										<span class="ui-text-"><c:if test="${gameAward.lotteryId != 99201}">三星一码不定位返点</c:if><c:if test="${gameAward.lotteryId == 99201}">趣味型</c:if></span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.threeoneRet " value="${gameAward.threeoneRet/10000}" id="J-point-budingwei" disabled="disabled"/>
										<span class="ui-text-info color-gray">(设置范围 ：<label id="label11">0.05</label>- <label id="label12">0.06</label>即<label id="label13">5%</label>- <label id="label14">6%</label>)</span>
									 </c:if> 
									</li>
									<c:if test="${(gameAward.lotteryId>=99101 && gameAward.lotteryId<= 99105) or gameAward.lotteryId== 99113}">
									<li>
										<span class="ui-text-info"><c:if test="${gameAward.lotteryId != 99201}">超级2000返点</c:if> <c:if test="${gameAward.lotteryId == 99201}">任选型</c:if></span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.directRet" value="${gameAward.superRet/10000}" id="J-point-general" disabled="disabled"/>
										<span class="ui-text-info color-gray">(设置范围 ：<label id="label1">0.00</label>- <label id="label2">0.01</label>即<label id="label3">0%</label>- <label id="label4">1%</label>)</span>
									</li>
									</c:if>
								</ul>
								<table class="table table-info table-border" id="J-data-table">
									<thead>
										<tr>
											<th>玩法群</th>
											<th>玩法组</th>
											<th>玩法/投注方式</th>
											
											
											<c:choose>
											<c:when test="${gameAward.lotteryId == 99701}"><!-- 六合彩 -->
											<th>投注內容</th>
											<th>賠率</th>
											</c:when>
											<c:otherwise>
											<th>奖金（元）</th>
											</c:otherwise>
											</c:choose>
																				
											<th>总利润</th>
											<th>总代返点</th>
											<th>公司留水</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${awardList}" var="awards" varStatus="awardIndex">
											
											<c:forEach items="${awards.setCodeList}" var="setCodes" varStatus="setCodeIndex">
												
												<c:forEach	items="${setCodes.methodCodeList}" var="methodCodes" varStatus="methodIndex">
													
													<tr>
														<c:if test="${setCodeIndex.index==0 && methodIndex.index==0 }">
															<td rowspan="${awards.rowsCount }">${awards.groupName }</td>
														</c:if>
														
														<c:if test="${methodIndex.index==0 }">
															<td rowspan="${setCodes.setCount}">${setCodes.setCodeName}</td>
														</c:if>
														
															<td>${methodCodes.methodCodeName }</td>
															
															<c:if test="${methodCodes.methodCount ==0 && methodCodes.methodCodeCount ==0}">
															<td><span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode}_${methodCodes.theoryBonus *100}" 
																<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13|| awards.groupCode == 33) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															>
															<c:if test="${awards.groupCode>=44&& awards.groupCode<=51}">
																非对子:${methodCodes.actualBonus/100 }<br>对子&nbsp;&nbsp;&nbsp;:${methodCodes.actualBonusDown/100 }
															</c:if>
															<c:if test="${awards.groupCode<44||awards.groupCode>51 }">
																${methodCodes.actualBonus/100 }
															</c:if>
															</span>
															</td>
															<td><span class="point-lirun"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13 || awards.groupCode == 33) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															><fmt:formatNumber value="${methodCodes.totalProfit/100 }" pattern="#.##" minFractionDigits="2" />%</span></td>
															<td><span class="point-proxy"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13|| awards.groupCode == 33) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															><fmt:formatNumber value="${methodCodes.topAgentPoint/100 }" pattern="#.##" minFractionDigits="2" />%</span></td>
															<td><span class="point-liushui"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13|| awards.groupCode == 33) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															><fmt:formatNumber value="${methodCodes.totalProfit/100 - methodCodes.topAgentPoint/100}" pattern="#.##" minFractionDigits="2" />%</span></td>															
															</c:if>
															
															<c:if test="${methodCodes.methodCount !=0 }">
															
															<c:if test="${gameAward.lotteryId == 99701}"><!-- 六合彩 -->
															<td>
															<c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex">
															<div>${assistBonus.lhcCodeName }</div>
															</c:forEach>
															</td>
															</c:if>
															
															
															<td>
															<c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex">					
															<div name="${assistBonus.methodTypeName }">
																${assistBonus.methodTypeName }&nbsp;&nbsp;<span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${assistBonus.theoryBonus *100}_${assistBonus.methodType }_text"  >
																<c:if test="${awards.groupCode>=44&& awards.groupCode<=51}">
																非对子:${assistBonus.actualBonus/100 }&nbsp;&nbsp;对子:${assistBonus.actualBonusDown/100 }
																</c:if>
																<c:if test="${awards.groupCode<44||awards.groupCode>51 }">
																${assistBonus.actualBonus/100 }
																</c:if>
																</span>
															</div>
															</c:forEach>
															</td>	
															<!-- 总利润 -->
															<td><c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex"><div>
															<c:if test="${awards.groupCode>=44&& awards.groupCode<=51}"><fmt:formatNumber value="${(assistBonus.actualBonus-assistBonus.actualBonusDown)*0.1/assistBonus.actualBonus*100 }" pattern="#.##" minFractionDigits="2" />%</c:if>
															<c:if test="${(awards.groupCode<44|| awards.groupCode>51) && gameAward.lotteryId != 99701}"><fmt:formatNumber value="${(1-(assistBonus.actualBonus/assistBonus.theoryBonus))*100 }" pattern="#.##" minFractionDigits="2" />%</c:if>
															<c:if test="${gameAward.lotteryId == 99701}"><fmt:formatNumber value="${(1-(assistBonus.actualBonus/assistBonus.lhcTheoryBonus))*100 }" pattern="#.##" minFractionDigits="2" />%</c:if>
															</div></c:forEach></td>
															
															<!-- 总代返点 --><!-- 六合彩總代返點抓game_award 其他抓game_award_group -->
															<td><c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex">
															<div>
															<c:if test="${awards.groupCode>=44&& awards.groupCode<=51}">1.00%</c:if>
															<c:if test="${gameAward.lotteryId == 99701}">
																<fmt:formatNumber value="${assistBonus.retVal/100 }" pattern="#.##" minFractionDigits="2" />%
															</c:if>
															<c:if test="${(gameAward.lotteryId == 99601 || gameAward.lotteryId == 99602 ||gameAward.lotteryId == 99603) && (awards.groupName=='猜一个号')}">
																<fmt:formatNumber value="${assistBonus.retVal/100 }" pattern="#.##" minFractionDigits="2" />%
															</c:if>
															<c:if test="${(awards.groupCode<44|| awards.groupCode>51) && gameAward.lotteryId != 99701 && !((gameAward.lotteryId == 99601 || gameAward.lotteryId == 99602 ||gameAward.lotteryId == 99603) && (awards.groupName=='猜一个号'))}">
																<fmt:formatNumber value="${methodCodes.topAgentPoint/100 }" pattern="#.##" minFractionDigits="2" />%
															</c:if>
															</div></c:forEach></td>
															
															
															<!-- 公司留水 --><!-- 六合彩99701 總代返點抓game_award 其他抓game_award_group -->
															<td><c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex"><div><c:if test="${awards.groupCode>=44&& awards.groupCode<=51}"><fmt:formatNumber value="${((assistBonus.actualBonus-assistBonus.actualBonusDown)*0.1/assistBonus.actualBonus*100)-1.0}" pattern="#.##" minFractionDigits="2" />%</c:if>
															<c:if test="${awards.groupCode<44|| awards.groupCode>51 && gameAward.lotteryId != 99701}"><fmt:formatNumber value="${(1-(assistBonus.actualBonus/assistBonus.theoryBonus))*100- methodCodes.topAgentPoint/100 }" pattern="#.##" minFractionDigits="2" />%</c:if>
															<c:if test="${gameAward.lotteryId == 99701}"><fmt:formatNumber value="${(1-(assistBonus.actualBonus/assistBonus.lhcTheoryBonus))*100-  assistBonus.retVal/100 }" pattern="#.##" minFractionDigits="2" />%</c:if>
															
															</div></c:forEach></td>												
															</c:if>
															
															<c:if test="${methodCodes.methodCodeCount !=0 }">
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div name="${methodCodeChild.methodCodeName }">
																${methodCodeChild.methodCodeName }&nbsp;&nbsp;<span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${methodCodeChild.theoryBonus *100}_${methodCodeChild.methodCode }_text" >
																<c:if test="${awards.groupCode>=44&& awards.groupCode<=51}">
																非对子:${methodCodeChild.actualBonus/100 }&nbsp;&nbsp;对子:${methodCodeChild.actualBonusDown/100 }
															</c:if>
															<c:if test="${awards.groupCode<44||awards.groupCode>51 }">
																${methodCodeChild.actualBonus/100 }
															</c:if>
																</span>
															</div>
															</c:forEach>
															</td>																														
															<%-- <td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-lirun">${methodCodeChild.totalProfit }</span>
															</div>
															</c:forEach>											
															</td>
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-proxy">${methodCodeChild.topAgentPoint }</span>
															</div>
															</c:forEach>
															</td>
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-liushui">${methodCodeChild.companyProfit }</span>
															</div>
															</c:forEach>
															</td> --%>
															<td><span class="point-lirun"><fmt:formatNumber value="${methodCodes.totalProfit/100 }" pattern="#.##" minFractionDigits="2" />%</span></td>
															<td><span class="point-proxy"><fmt:formatNumber value="${methodCodes.topAgentPoint/100 }" pattern="#.##" minFractionDigits="2" />%</span></td>
															<td><span class="point-liushui"><fmt:formatNumber value="${methodCodes.companyProfit/100 }" pattern="#.##" minFractionDigits="2" />%</span></td>
															</c:if>
															
															
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
		
		<%-- <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameAward/queryGameAwardGroup.js"></script> --%>
		<script type="text/javascript">
			//去除上面js，导致一二三级导航无法定位。在此增加
			//菜单样式加载
			$('.menu-list li').removeAttr("class");
			$('.menu-list li:eq(3)').attr("class","current"); 
			$('.col-side .nav dd:eq(0)').attr("class","current");
			$('#awardGroupMenu').attr("class","current");
		</script>
</body>