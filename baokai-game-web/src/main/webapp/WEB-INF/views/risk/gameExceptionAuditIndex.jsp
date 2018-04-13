<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>审核系统-审核奖期信息</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	
</head>
<body>
	
	<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">审核中心</a><a href="#">>游戏审核管理</a>>游戏异常审核</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form" action="queryGameExceptionAuditList" method="post">
						<div class="ui-tab">
						<ul class="ui-form ui-form-long" id="J-lotterys-panel">
							<li>
								<input type="hidden" id="result" value='${result}'/>
								<input type="hidden" id="dateType" name="dateType" value="${dateType }"/>
								<input type="hidden" id="lotteryId" name="lotteryId" value="${lotteryId }"/>
								<input type="hidden" id="statusValue" name="statusValue"  value="${status}"/>
								<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
								<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
								
							</li>
							<li>
							    <label class="ui-label w-2 big">彩种名称：</label>
								<span class="ico-tab ico-tab-item"  id="99101"><a href="#"  class="_gameType">重庆时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99102"><a href="#" class="_gameType">江西时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99104"><a href="#"  class="_gameType">天津时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99103"><a href="#" class="_gameType">新疆时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99105"><a href="#" class="_gameType">黑龙江时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99107"><a href="#" class="_gameType">上海时时乐</a></span>
								<span class="ico-tab ico-tab-item"  id="99301"><a href="#" class="_gameType">山东11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99302"><a href="#" class="_gameType">江西11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99307"><a href="#" class="_gameType">江苏11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99303"><a href="#" class="_gameType">广东11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99304"><a href="#" class="_gameType">重庆11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99201"><a href="#" class="_gameType">北京快乐8</a></span>
								<span class="ico-tab ico-tab-item"  id="99108"><a href="#" class="_gameType">3D</a></span>
								<span class="ico-tab ico-tab-item"  id="99109"><a href="#" class="_gameType">排列3/5</a></span>
								<span class="ico-tab ico-tab-item"  id="99401"><a href="#" class="_gameType">双色球</a></span>
								<span class="ico-tab ico-tab-item"  id="99701"><a href="#" class="_gameType">六合彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99106"><a href="#" class="_gameType">宝开时时彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99305"><a href="#" class="_gameType">宝开11选5</a></span>
								<span class="ico-tab ico-tab-item"  id="99111"><a href="#" class="_gameType">宝开一分彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99114"><a href="#" class="_gameType">腾讯分分彩</a></span>
								<span class="ico-tab ico-tab-item"  id="99501"><a href="#" class="_gameType">江苏快三</a></span>
								<span class="ico-tab ico-tab-item"  id="99502"><a href="#" class="_gameType">安徽快三</a></span>
								<span class="ico-tab ico-tab-item"  id="99601"><a href="#" class="_gameType">江苏骰宝</a></span>
								<span class="ico-tab ico-tab-item"  id="99602"><a href="#" class="_gameType">高频骰宝(娱乐厅)</a></span>
								<span class="ico-tab ico-tab-item"  id="99603"><a href="#" class="_gameType">高频骰宝(至尊厅)</a></span>
								<span class="ico-tab ico-tab-item"  id="-1"><a href="#" class="_gameType">全部</a></span>
							</li>
							<li>
								<label class="ui-label w-2 big">时间：</label>
								<span class="ico-tab" id="dateSpan" ><a href="#" name="1" id="dates" >今天</a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="3" id="dates">三天</a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="7" id="dates">一周</a></span>
								<span class="ico-tab" id="dateSpan"><a href="#" name="0" id="dates">全部</a></span>
							</li>
							<li>
								<label class="ui-label w-2 big">状态：</label>
								 <select name="status" id="status">
								    <option value="2">待处理</option>
									<option value="0">全部</option>
									<option value="1">已完成</option>
									
								</select>
							</li>
						</ul>
						
						<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th>彩种</th>
									<th>期号</th>
									<th>销售日期</th>
									<th>理论开奖时间</th>
									<th>开奖号码确认时间</th>
									<th>状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${issueList}" var="issues" varStatus="issueStatus">
									<tr id="${issues.lotteryid }_${issues.issueCode}">
									
									<td>${issues.lotteryName}</td>
										<td>${issues.webIssueCode }</td>
										<td>${issues.saleDate }</td>
										<td>${issues.confirmDrawTime }</td>
										<td>${issues.confirmDrawTime }</td>
										<td><c:if test="${issues.status==0}">无需审核</c:if><c:if test="${issues.status==1}">已完成</c:if><c:if test="${issues.status==2}">待处理</c:if></td>
										<td><c:if test="${issues.status==0}">-</c:if><c:if test="${issues.status==2}"><c:if test="${issues.operator == null }"> <input id="${issues.lotteryid }_${issues.issueCode}" class="deal" type="button" value="立即处理"></input></c:if>
										<c:if test="${issues.operator != null }"> <span class="input-mark w-2" style="padding:5px 20px;" data-showtip="${issues.operator}正在处理中">${issues.operator}正在处理中</span></c:if>
										
										</c:if><c:if test="${issues.status==1}"><a id="${issues.lotteryid }_${issues.issueCode}" class="detail">详情</a></c:if></td>
									</tr>
								</c:forEach>
						
							</tbody>
						</table>
						
						<div class="page-wrapper clearfix">
							<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						</div>
						
					</div>
				</div>
				</form>
			</div>
			
		</div>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/risk/gameExceptionAuditIndex.js"></script>
</body>