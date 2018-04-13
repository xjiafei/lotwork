<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>渠道配置-查询参数</title>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/channel/channeltempalt.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/datePickerForChannel.js"></script>
</head>

<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a
				href="${currentContextPath}/channel/channelView">渠道配置</a>
			&gt; 查询参数
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						<form action="${currentContextPath}/channel/channelView"
							id="form" method="post" >
							<input type="hidden" name="pageNo" value="${page.pageNo}"
								id="pageNo">
							<ul class="ui-search">
								<li><select class="ui-select" name="searchType">
										<option value="1"
										<c:if test="${search.searchType==1 }">selected</c:if>>编号</option>
										<option value="2"
										<c:if test="${search.searchType==2 }">selected</c:if>>渠道参数</option>	
										<option value="3"
										<c:if test="${search.searchType==3 }">selected</c:if>>使用链接</option>																		
								</select>
								</li>
								<li>
								<input id ="value-1" type="text" class="input w-4"  value="${search.searchValue}" autocomplete="off"/>
								<input id ="value-2" type="hidden" name="searchValue">
								</li>
								<li><a id="J-button-select-submit" class="btn btn-important" href="javascript:void(0);">查 询<b class="btn-inner"></b></a></li>
							</ul>					
						</form>
						
						<c:if test="${empty list}">
								<div class="alert alert-waring"><i></i>
									<div class="txt"><h4>没有符合条件的记录，请更改查询条件！</h4></div>
								</div>
						</c:if>
						
						<c:if test="${!empty list}">
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>编号</th>
									<th>渠道参数</th>
									<th>使用链接</th>
									<th>日验证次数</th>
									<th>累计验证总数</th>
									<th>每日申请次数</th>
									<th>累计申请次数</th>
									<th>生效次数</th>
									<th>到期时间</th>
									<th>预警频率</br>(次数)</th>
									<th>预警频率</br>(分钟)</th>
									<th>冻结时长</th>
									<th>永久冻结</th>
									<th>是否每日</br>重置</th>
									<th>清除历史数据</th>
								</tr>
							</thead>
							
							<tbody>
							<c:forEach items="${list}" var="group">
								<tr>
									<td>${group.id}</td>
									<td>${group.name}</td>
									<td class ="left">${group.url}</td>
									<td>${group.day}</td>
									<td>${group.count}</td>
									<td>${group.day_appl}</td>
									<td>${group.count_appl}</td>
									<td>${group.vaild}</td>
									<td><fmt:formatDate pattern="yyyy-MM-dd" value="${group.end_time}" /></td>
									<td>${group.frequency}</td>
									<td>${group.frequency_time}</td>
									<td>${group.freeze_time}</td>
									<td>${group.freeze}</td>
									<td>
									<c:if test="${group.reset == 0 }">否</c:if>
									<c:if test="${group.reset == 1 }">是</c:if>
									</td>
									<td><a class="delete" dir ="${group.id}" href="javascript:void(0);">删除</a></td>
								</tr>
								</c:forEach>	
							</tbody>
						</table>
						</c:if>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}"
								pageSize="${page.pageSize}" />	
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		function doPre() {
			var currentPageNo = $("#pageNo").val();
			$("#pageNo").val(parseInt(currentPageNo) - 1);
			$("#form").submit();
		}

		function doNext() {
			var currentPageNo = $("#pageNo").val();
			$("#pageNo").val(parseInt(currentPageNo) + 1);
			$("#form").submit();
		}

		function doForward(index) {
			if (index == -1) {
				var reg = /^[0-9]+$/;
				if (reg.test($("#forwardPage").val())) {
					$("#pageNo").val(parseInt($("#forwardPage").val()));
				} else {
					return;
				}
			} else {
				$("#pageNo").val(index);
			}
			$("#form").submit();
		}

		function doCurrent(pageNo) {
			$("#pageNo").val(pageNo);
			$("#form").submit();
		}
	</script>
</body>