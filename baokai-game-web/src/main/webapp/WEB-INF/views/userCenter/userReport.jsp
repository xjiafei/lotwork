<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.winterframework.modules.page.Page" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>报表查询</title>
<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/userReport.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/lotteryBettpyeConfig.js"></script>
<script type="text/javascript">
var baseUrl = '${currentContextPath}';
var staticFileContextPath='${staticFileContextPath}';
	</script>
</head>
<body>
	<div class="g_28 g_last">
		<div class="common-article">
			<div class="title">报表查询</div>
			<div class="content">
			<c:if test="${!empty ptStatus}">
				<div class="ui-tab-title tab-title-bg clearfix">
					<ul>
							<li class="current">彩票</li>
							<li><a href="${ptContextPath}/pt/bet/queryordersmanagement">老虎机</a></li>
					</ul>
				</div>
			</c:if>
				<form id="J-form" action="queryUserReportByComplexCondition"
					method="post">
					<input
						id="pageNo" type="hidden" name="pageNo" value="${page.pageNo}">
					<input id="pageSize" type="hidden" name="pageSize"
						value="${page.pageSize}">
					<ul class="ui-search search-porxy clearfix" style="position:relative;">
						<li class="name"><label for="name" class="ui-label">用户名：</label>
							<input type="text" class="input" id="accountInput" value="${request.account}"/>
							<input type="hidden" value="${request.account}" id="account" name="account"/></li>
						<li class="type"><label class="ui-label">彩种：</label> <input
							type="hidden" id="lotteryIdValue" value="${request.lotteryId}"></input>
							<em id="sel"></em></li>
						<li class="type"><label class="ui-label">玩法：</label>
							<input type="hidden" name="betTypeCode" id="betTypeCode" value="${request.betTypeCode}"></input>
							<input type="hidden" name="crowdId" id="crowdId" value="${request.crowdId}"></input>
							<input type="hidden" name="groupId" id="groupId" value="${request.groupId}"></input>
							<input type="hidden" name="setId" id="setId" value="${request.setId}"></input>
							<input type="hidden" name="methodId" id="methodId" value="${request.methodId}"></input>
							<input name="select" name="crowdname" id="crowdname" value="所有玩法" type="text" size="20" readonly="" />
							<div id="method_message" style="top: 21px;  border-top-width: 1px; border-top-style: solid; border-top-color: rgb(127, 157, 185); display: none;">
								<div class="allcrowd">
									<div class="crowdid" id="1">所有玩法</div>
								</div>
								<div class="allcrowd">
									<div class="crowdid" id="2">普通玩法</div>
								</div>
								<div class="allcrowd">
									<div class="crowdid" id="3">超级2000</div>
								</div>
							</div>							
						</li>
						<li class="date"><label for="dateStar" class="ui-label">日期：</label>
							<input type="text" class="input" id="J-date-start" value=""
							name="queryTimeView"> <input type="hidden" id="jdate"
							value="${request.queryTime}" name="queryTime" /></li>
						<li><a id="search"  class="btn btn-important"
							href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
					</ul>
				</form>
				<c:if test="${!empty cucrr.userCenterReportStrucs}">
					<div class="ui-tab">
						<table class="table table-info">
							<thead>
								<tr>
									<th>用户名</th>
									<th>用户组</th>
									<th>总代购费</th>
									<th>总返点</th>
									<th>实际总代购费</th>
									<th>中奖金额</th>
									<th>活动礼金</th>
									<th>总结算</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id = "tbd_dataEach">
								<c:forEach items="${cucrr.userCenterReportStrucs}" var="ucrs"
									begin="0">
									<tr>
										<td>${ucrs.account}</td>
										<td><c:if test="${ucrs.userLvl==0}">总代</c:if> <c:if
												test="${ucrs.userLvl==-1}">用户</c:if> <c:if
												test="${ucrs.userLvl>0}">代理</c:if></td>
										<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${ucrs.totalSubuserSaleroom/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${ucrs.totalSubuserPoint/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${ucrs.actualTotalSubuserSaleroom/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${ucrs.totalSubuserWins/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${ucrs.activityGifts/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td><span class="price"><dfn>&yen;</dfn></span>
										<c:if test="${ucrs.totalSubuserWins/10000.00+ucrs.activityGifts/10000.00-ucrs.actualTotalSubuserSaleroom/10000.00 >0}">+</c:if><fmt:formatNumber value="${ucrs.totalSubuserWins/10000.00+ucrs.activityGifts/10000.00-ucrs.actualTotalSubuserSaleroom/10000.00}" pattern="#,###.##"  minFractionDigits="2"/></td>
										<td>
											<c:if test="${ucrs.userLvl!=-1 && ucrs.hasNextUser==1}">
												<a href="${currentContextPath}/gameUserCenter/querySubUserReport?userId=${ucrs.userId}&account=${request.account}&queryTime=${request.queryTime}&lotteryId=${request.lotteryId}&betTypeCode=${request.betTypeCode}">查看下级</a>
											</c:if> <c:if test="${ucrs.userLvl==-1 || ucrs.hasNextUser==0}">-</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					<script>
					function addCommas(nStr) {
					    nStr += '';
					    var x = nStr.split('.');
					    var x1 = x[0];
					    var x2 = x.length > 1 ? '.' + x[1] : '.00';
					    var rgx = /(d+)(d{3})/;
					    while (rgx.test(x1)) {
					        x1 = x1.replace(rgx, '$1' + ',' + '$2');
					    }
					    return x1 + x2;
					}
					
						function ajaxGetMore() {
							$("#pageNo").val(parseInt($("#pageNo").val()) + 1);
							if($("#pageNo").val() == "${page.totalPages}"){
								$("#btn_getMore").prop("disabled",true);
							}
							$.ajax({
								url:"${currentContextPath}/gameUserCenter/querySubUserReportAjax?userId=${query.userId}&queryTime=${query.queryTime}&lotteryId=${query.lotteryId}&pageNo="+$("#pageNo").val()+"&pageSize="+$("#pageSize").val()+"",
								dataType:'json',
								method:'post',
								success:(function(data){
									var content = "";
									$.each(data,function(key,val){
										content = "";

										content += "<tr><td>"+val.account+"</td>"
										if(val.userLvl==0){
											content += "<td>总代</td>"
										}else if(val.userLvl==-1){
											content += "<td>用户</td>"
										}else if(val.userLvl>0){
											content += "<td>代理</td>"
										}
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>"+addCommas(val.totalSubuserSaleroom/10000.00)+"</td>";
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>"+addCommas(val.totalSubuserPoint/10000.00)+"</td>";
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>"+addCommas(val.actualTotalSubuserSaleroom/10000.00)+"</td>";
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>"+addCommas(val.totalSubuserWins/10000.00)+"</td>";
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>"+addCommas(val.activityGifts/10000.00)+"</td>";
										content += "<td><span class=\"price\"><dfn>&yen;</dfn></span>";
										if(((val.totalSubuserWins/10000.00) + (val.activityGifts/10000.00) - (val.actualTotalSubuserSaleroom/10000.00)) > 0){
											content += "+";
										}
										content += addCommas((val.totalSubuserWins/10000.00) + (val.activityGifts/10000.00) - (val.actualTotalSubuserSaleroom/10000.00))+"</td>";
										var userId = ${query.userId};
										if(val.userLvl != 1 && val.hasNextUser == 1 && val.userId != userId){
											content += "<td><a id=\"subUserId_"+val.userId+"\"href=\"#\">查看下级</a></td>";
										}else if(val.userLvl == -1 || val.hasNextUser == 0){
											content += "<td>-</td>";
										}
										content += "<tr>";
										$("#tbd_dataEach").append(content);
									});
								})
							});
						 }
						</script>
						<div class="page-wrapper">
							<span class="page-text"> <span class="lower">共${page.totalCount}条记录</span>
							</span>
							

							<div class="page page-right">
								<c:if test = "${page.totalPages != page.pageNo}">
									<input type="button" id="btn_getMore" value="显示更多" onclick="ajaxGetMore()">
								</c:if>
								
								<!-- 2015/06/05 Mark by Hassan Start-->
								<!-- requirement #415 代理的【报表查询】页码优化 -->
								<!-- 頁碼修改為顯示更多按鈕，按一下AJAX出10條記錄 -->
<%-- 								<c:if test="${page.pageNo!=1}"> --%>
<!-- 									<a href="#" onclick="doPre()" class="prev">上一步</a> -->
<%-- 								</c:if> --%>
						
<%-- 								<c:if test="${page.totalPages<=10}"> --%>
<%-- 									<c:forEach begin="1" end="${page.totalPages}" var="i"> --%>
<%-- 										<a href="#" onclick="doCurrent(${i})" --%>
<%-- 											<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a> --%>
<%-- 									</c:forEach> --%>
<%-- 								</c:if> --%>
						
<%-- 								<c:if test="${page.totalPages>10 && page.pageNo>=5}"> --%>
<%-- 									<c:forEach begin="${page.pageNo-4}" end="${page.pageNo+4}" var="i"> --%>
<%-- 									<c:if test="${i<= page.totalPages}"> --%>
<%-- 										<a href="#" onclick="doCurrent(${i})" --%>
<%-- 											<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a> --%>
<%-- 									</c:if> --%>
<%-- 									</c:forEach> --%>
<%-- 									<a href="javascript:void(0)" onclick="doForward(${page.pageNo+5})">...</a> --%>
<%-- 								</c:if> --%>
						
<%-- 								<c:if test="${page.totalPages>10 && page.pageNo<5}"> --%>
<%-- 									<c:forEach begin="1" end="10" var="i"> --%>
<%-- 										<a href="#" onclick="doCurrent(${i})" --%>
<%-- 											<c:if test="${page.pageNo==i}">class="current"</c:if>>${i}</a> --%>
<%-- 									</c:forEach> --%>
<!-- 									<a href="javascript:void(0)" onclick="doForward(11)">.1.</a> -->
<%-- 								</c:if> --%>
<%-- 								<c:if test="${page.pageNo!=page.totalPages}"> --%>
<!-- 									<a href="#" onclick="doNext()" class="next">下一步</a> -->
<%-- 								</c:if> --%>
<!-- 								<span class="page-few">到第 <input id="forwardPage" type="text" -->
<%-- 									value="" class="input"> /${page.totalPages}页 --%>
<!-- 								</span> <input type="button" value="确 认" class="page-btn" -->
<!-- 									onclick="doForward(-1)"> -->
<!-- 2015/06/05 Mark by Hassan End-->
							</div>

						</div>

					</div>
				</c:if>
				<c:if test="${empty cucrr.userCenterReportStrucs}">
				<div class="alert alert-waring">
					<i></i>
					<div class="txt">
						<h4>没有符合条件的记录，请更改查询条件</h4>
					</div>
				</div>
				</c:if>
				
			</div>
		</div>
         <dl class="fund-info-supplement">
				<dt>说明：您可以查询报表管理7天内的记录。</dt>
	    </dl>
	</div>
</body>

</html>