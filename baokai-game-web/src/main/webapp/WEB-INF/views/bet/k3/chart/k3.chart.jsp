<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<table class="chart-table">
					<thead class="thead">
					<tr class="title-text">
						<th class="ball-none border-bottom" rowspan="2"></th>
						<th class="border-bottom" rowspan="2">期号</th>
						<th class="ball-none border-bottom border-right" rowspan="2"></th>
						<th class="ball-none border-bottom" rowspan="2"></th>
						<th class="border-bottom" rowspan="2">开奖号码</th>
						<th class="ball-none border-bottom border-right" rowspan="2"></th>
						<th class="border-right" colspan="8">号码走势</th>
						<th class="border-right" colspan="18">和值</th>
						<th colspan="4" class="border-bottom border-right">和值组合形态</th>
						<th colspan="6" class="border-bottom border-right">号码形态</th>
					</tr>
					<tr class="title-number">
						<th class="ball-none border-bottom"></th>
						<th class="border-bottom"><i class="ball-noraml">1</i></th>
						<th class="border-bottom"><i class="ball-noraml">2</i></th>
						<th class="border-bottom"><i class="ball-noraml">3</i></th>
						<th class="border-bottom"><i class="ball-noraml">4</i></th>
						<th class="border-bottom"><i class="ball-noraml">5</i></th>
						<th class="border-bottom"><i class="ball-noraml">6</i></th>
						<th class="ball-none border-bottom border-right"></th>
						<th class="ball-none border-bottom"></th>
						<th class="border-bottom"><i class="ball-noraml">3</i></th>
						<th class="border-bottom"><i class="ball-noraml">4</i></th>
						<th class="border-bottom"><i class="ball-noraml">5</i></th>
						<th class="border-bottom"><i class="ball-noraml">6</i></th>
						<th class="border-bottom"><i class="ball-noraml">7</i></th>
						<th class="border-bottom"><i class="ball-noraml">8</i></th>
						<th class="border-bottom"><i class="ball-noraml">9</i></th>
						<th class="border-bottom"><i class="ball-noraml">10</i></th>
						<th class="border-bottom"><i class="ball-noraml">11</i></th>
						<th class="border-bottom"><i class="ball-noraml">12</i></th>
						<th class="border-bottom"><i class="ball-noraml">13</i></th>
						<th class="border-bottom"><i class="ball-noraml">14</i></th>
						<th class="border-bottom"><i class="ball-noraml">15</i></th>
						<th class="border-bottom"><i class="ball-noraml">16</i></th>
						<th class="border-bottom"><i class="ball-noraml">17</i></th>
						<th class="border-bottom"><i class="ball-noraml">18</i></th>
						<th class="ball-none border-bottom border-right"></th>
						<th class="border-bottom border-right">小奇</th>
						<th class="border-bottom border-right">小偶</th>
						<th class="border-bottom border-right">大奇</th>
						<th class="border-bottom border-right">大偶</th>
						<th class="border-bottom border-right">三同号</th>
						<th class="border-bottom border-right">三不同号</th>
						<th class="border-bottom border-right">三连号</th>
						<th class="border-bottom border-right">二同号(复)</th>
						<th class="border-bottom border-right">二同号(单)</th>
						<th class="border-bottom">二不同号</th>
					</tr>
				</thead>
					<tbody class="tbody">
						<c:forEach items="${betCharts}" var="betChart" varStatus="status">
							<tr>
								<td class="ball-none"></td>
								<td class="issue-numbers">${betChart[0]}</td>
								<td class="ball-none border-right"></td>
								<td class="ball-none"></td>
								
								<td><span class="lottery-numbers">${betChart[1]}</span></td>
								<td class="ball-none border-right"></td>
								
								<td class="ball-none"></td>
								<c:forEach items="${betChart[2]}" var="ballTrendChart" varStatus="bIndex" >
									<td>
										<c:if test="${ballTrendChart[0] !=0 }"><i class="ball-noraml">${ballTrendChart[0]}</i></c:if>
										<c:if test="${ballTrendChart[0] ==0 && ballTrendChart[1]>1}"><i class="ball-noraml ball-orange"><i class="ball-mark">${ballTrendChart[1]}</i>${bIndex.index+1}</i></c:if>
										<c:if test="${ballTrendChart[0] ==0 && ballTrendChart[1]<=1}"><i class="ball-noraml ball-orange">${bIndex.index+1}</i></c:if>
									</td>
								</c:forEach>
								<td class="ball-none border-right"></td>
								
								
								<td class="ball-none>"></td>
								<c:forEach items="${betChart[3]}" var="hezhiTrendChart" varStatus="hIndex" >
									<c:if test="${hezhiTrendChart[0] ==0}"><td class="bg-red"><i class="ball-noraml">${hIndex.index+3 }</i></td></c:if>
									<c:if test="${hezhiTrendChart[0] !=0}"><td><i class="ball-noraml">${hezhiTrendChart[0]}</i></td></c:if>
								</c:forEach>
								<td class="ball-none border-right"></td>
								
								
								<c:forEach items="${betChart[4]}" var="zuheTrendChart" varStatus="zIndex" >
									<c:if test="${zuheTrendChart ==0}"><td class="border-right bg-blue">
										<c:if test="${zIndex.index+1==1 }">小奇</c:if>
										<c:if test="${zIndex.index+1==2 }">小偶</c:if>
										<c:if test="${zIndex.index+1==3 }">大奇</c:if>
										<c:if test="${zIndex.index+1==4 }">大偶</c:if>
										</td>
									</c:if>
									<c:if test="${zuheTrendChart !=0}"><td class="border-right">${zuheTrendChart }</td></c:if>
								</c:forEach>
								
								
								<c:forEach items="${betChart[5]}" var="xingtaiTrendChart" varStatus="xIndex" >
									<c:if test="${xingtaiTrendChart ==0}">
										<c:if test="${xIndex.index+1==1 }"><td class="border-right bg-red">三同号</td></c:if>
										<c:if test="${xIndex.index+1==2 }"><td class="border-right bg-blue">三不同号</td></c:if>
										<c:if test="${xIndex.index+1==3 }"><td class="border-right bg-green">三连号</td></c:if>
										<c:if test="${xIndex.index+1==4 }"><td class="border-right bg-red">二同号(复)</td></c:if>
										<c:if test="${xIndex.index+1==5 }"><td class="border-right bg-blue">二同号(单)</td></c:if>
										<c:if test="${xIndex.index+1==6 }"><td class="border-right bg-green">二不同号</td></c:if>
									</c:if>
									<c:if test="${xingtaiTrendChart !=0}"><td class="border-right">${xingtaiTrendChart }</td></c:if>
								</c:forEach>
								
								
							</tr>
						
						
						
						</c:forEach>
					</tbody>
				</table>