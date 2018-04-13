<%@ page language="java" contentType="text/html; charset=UTF-8"
	 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<table class="chart-table">
					<thead class="thead">
						<tr class="title-text">
							<th class=" border-bottom border-right">期号</th>
							
							<th class="border-bottom border-right">&nbsp;</th>
							
							<th class="border-bottom border-right">上</th>
							<th class="border-bottom border-right">中</th>
							<th class="border-bottom border-right">下</th>
							
							<th class="border-bottom border-right">&nbsp;</th>
							
							<th class="border-bottom border-right">奇</th>
							<th class="border-bottom border-right">和</th>
							<th class="border-bottom border-right">偶</th>
							
							<th class="border-bottom border-right">&nbsp;</th>
							
							<th class="border-bottom border-right">大</th>
							<th class="border-bottom border-right">小</th>
							
							<th class="border-bottom border-right">&nbsp;</th>
							
							<th class="border-bottom border-right">单</th>
							<th class="border-bottom border-right">双</th>
							
							<th class="border-bottom border-right">&nbsp;</th>
							
							<th class="border-bottom border-right">和值</th>
							
						</tr>
					</thead>
					<tbody class="tbody">
						<c:forEach items="${betCharts}" var="betChart" varStatus="status">
							<tr>
								<td class="issue-numbers border-right">${betChart.webIssueCode }</td>
								<td class=" border-right"></td>
							    <c:if test="${betChart.shang == true}"><td class=" border-right color-pink">上</td></c:if>
								<c:if test="${betChart.shang != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.zhong == true}"><td class=" border-right color-cyan">中</td></c:if>
								<c:if test="${betChart.zhong != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.xia == true}"><td class=" border-right color-blue">下</td></c:if>
								<c:if test="${betChart.xia != true}"><td class=" border-right"></td></c:if>
								<td class=" border-right"></td>
								<c:if test="${betChart.ji == true}"><td class=" border-right color-pink">奇</td></c:if>
								<c:if test="${betChart.ji != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.he== true}"><td class=" border-right color-cyan">和</td></c:if>
								<c:if test="${betChart.he != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.ou== true}"><td class=" border-right color-blue">偶</td></c:if>
								<c:if test="${betChart.ou != true}"><td class=" border-right"></td></c:if>
								<td class=" border-right"></td>
								<c:if test="${betChart.da== true}"><td class=" border-right color-pink">大</td></c:if>
								<c:if test="${betChart.da != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.xiao== true}"><td class=" border-right color-blue">小</td></c:if>
								<c:if test="${betChart.xiao != true}"><td class=" border-right"></td></c:if>
								<td class=" border-right"></td>
								<c:if test="${betChart.dan== true}"><td class=" border-right color-pink">单</td></c:if>
								<c:if test="${betChart.dan != true}"><td class=" border-right"></td></c:if>
								<c:if test="${betChart.shuang== true}"><td class=" border-right color-blue">双</td></c:if>
								<c:if test="${betChart.shuang != true}"><td class=" border-right"></td></c:if>
								<td class=" border-right"></td>
								<td class=" border-right color-cyan">${betChart.heZhi}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>