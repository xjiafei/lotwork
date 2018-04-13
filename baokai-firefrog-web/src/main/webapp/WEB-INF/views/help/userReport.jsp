<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>

<html>
	<head>
		<title>统计报表-游戏明细报表</title>
		<%String path = request.getContextPath(); %>
		<script type="text/javascript">
			var baseUrl = '${currentContextPath}';
		</script>
		<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin.css" />
		<link rel="stylesheet" href="${staticFileContextPath}/static/images/admin/admin-help.css" />
		<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/base.css" />
		<link rel="stylesheet" href="${staticFileContextPath}/static/images/common/js-ui.css" />
	<style type="text/css">
	td,.sp-td-title {
	text-align:right !important;
	}
	</style>
	</head>

<body>
	<div class="col-content">
		<div class="col-main">
				<form id="queryForm" action="${currentContextPath}/operation/statistics" method="post">
					<ul class="ui-search">
						<li class="time">
							<label for="" class="ui-label">年月：</label>
							<input id="J-time-start" type="text" value="" name="excelTimeStr" class="input" placeholder="yyyy-mm">
							<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
						</li>
					</ul>

					</h3>
					<table id="J-table-data" class="table table-info table-function" style="text-align: left;">
						<thead>
							<tr>
								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日期</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>

								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日活数量</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>

								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日毛利</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>
								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日投注量</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>
								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日充值量</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>
								<th id="J-sp-tid" class="sp-td">
									<div class="sp-td-cont sp-td-cont-b">
										<div class="sp-td-title">日提现量</div>
										<ul class="sp-filter-cont sp-filter-cont-b">
											<li>
												<div class="input-append">
													<input type="text" class="input w-2" size="10" maxlength="25">
													<a href="javascript:void(0);" class="btn sp-filter-submit"></a>
												</div>
											</li>
										</ul>
										<span class="sp-filter-close"></span>
									</div>
								</th>
							</tr>
						</thead>
						<tbody id="showInfo">
							<c:forEach items="${opers}" var="oper" varStatus="status">
								<tr>
									<td>${oper.bizDate}</td>
									<td>
									<fmt:formatNumber value="${oper.activeUserCount}" pattern="#,###" />
									</td>
									<td><fmt:formatNumber value="${oper.profit/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${oper.betAmt/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${oper.chargeAmt/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${oper.withdrawAmt/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr>
							</c:forEach>
									<td>小结</td>	
									<td>
									<fmt:formatNumber value="${operCount.activeUserCountAVG }" pattern="#,###"  minFractionDigits="0"/>
									</td>
									<td><fmt:formatNumber value="${operCount.profitSum/10000}" pattern="#,###.##"  minFractionDigits="2"/> </td>
									<td><fmt:formatNumber value="${operCount.betAmtSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${operCount.chargeAmtSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><fmt:formatNumber value="${operCount.withdrawAmtSum/10000}" pattern="#,###.##"  minFractionDigits="2"/></td>
								</tr> 
						</tbody>
					</table>

					<c:if test="${page!=null}">
					<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}" />
					</c:if>
				</form>
		</div>
</div>
<script type="text/javascript">
	global_path_url = "..";
</script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery-ui-1.10.2.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Message.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.pagination.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearch.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.SuperSearchGroup.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/base-all.js"></script>
<script language=javascript>
	function doPre() {
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(currentPageNo - 1);
		$("#queryForm").submit();
	}

	function doNext() {
		var currentPageNo = $("#pageNo").val();
		$("#pageNo").val(Number(currentPageNo) + 1);
		$("#queryForm").submit();
	}

	function doForward() {
		$("#pageNo").val($("#forwardPage").val());
		$("#queryForm").submit();
	}

	function doCurrent(pageNo) {
		$("#pageNo").val(pageNo);
		$("#queryForm").submit();
	}
</script>

<script type="text/javascript">
	$('#J-button-submit').click(function() {
		$('#queryForm').submit();
	});
    document.onkeydown=keyDownSearch; 
    function keyDownSearch(e) {  
        var theEvent = e || window.event;  
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;  
        if (code == 13) {   
        	$('#queryForm').submit();
            return false;  
        }  
        return true;  
    } 
</script>
</body>