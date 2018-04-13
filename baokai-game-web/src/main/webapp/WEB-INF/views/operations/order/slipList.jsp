<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring-form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
		<title><spring:message code="gameCenter_fanganzhuihaoguanli" />-<spring:message code="gameCenter_sidanjilu" /></title>
<script type="text/javascript">
	var baseUrl = '${currentContextPath}';
	var slip = true;
</script>

</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">游戏中心</a>><spring:message code="gameCenter_sidanjilu" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						
						<form action="querySlipListByIssueCode?lotteryId=${lotteryId }" method="post" id="J-search-form">
							<input type="hidden" id="lotteryid" name="lotteryid" value="${req.lotteryid}"/>
							<input type="hidden" id="paramCode" name="paramCode" value="${req.paramCode }"/>
							<input type="hidden" id="containSub" name="containSub" value="${req.containSub }"/>
							<input type="hidden" id="selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
							<input type="hidden" id="startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
							<input type="hidden" id="endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
							<input type="hidden" id="status" name="status" value="${req.status }"/>
							<input type="hidden" id="startWinsCount" name="startWinsCount" value="${req.startWinsCount}"/>
							<input type="hidden" id="endWinsCount" name="endWinsCount" value="${req.endWinsCount}"/>
							<input type="hidden" id="issueCode" name="issueCode" value="${req.issueCode}"/>
							<input type="hidden" id="device" name="device" value="${req.device}"/>
							
							<input type="hidden" id="sortType" name="sortType" value="${req.sortType}">
							<input type="hidden" id="pageCount" name="pageCount" value="${page.pageSize }"/>
							<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
						
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>		
						</select>
							</li>
							<li>
								<label for="" class="ui-label"><spring:message code="gameCenter_sousuoyonghufangan" />：</label>
								<input type="text" id="J-input-paramCode" name="J-input-paramCode" class="input w-3">
								<label class="label" for="ck1"><input type="checkbox" checked="true" class="checkbox" name="type" id="J-select-containSub"><spring:message code="gameCenter_baohanxiaji" /></label>
							</li>
							<li class="time">
								<label for="" class="ui-label"><spring:message code="gameCenter_touzhushijian" />：</label>
								<select class="ui-select" id="J-select-time">
									<option value="1"><spring:message code="gameCenter_jintian" /></option>
									<option value="2">2天</option>
									<option value="3" selected="selected">3天</option>
									<option value="4">4天</option>
									<option value="5">5天</option>
									<option value="6">6天</option>
									<option value="7">7天</option>
									<option value="-1"><spring:message code="gameCenter_quanbu" /></option>
									<option value="0">自定义</option>
								</select>
								<input id="J-time-start" type="text" value="" class="input"> - <input id="J-time-end" type="text" value="" class="input">
							</li>
							<li>
								<label for="" class="ui-label"><spring:message code="gameCenter_zhuangtai" />：</label>
								<select id="J-select-status" name="J-select-status" class="ui-select">
									<option value="0"><spring:message code="gameCenter_quanbu" /></option>
									<option value="1">等待开奖</option>
									<option value="2">中奖</option>
									<option value="3">未中奖</option>
									<option value="41">用户撤销</option>
									<option value="42">系统撤销</option>
									<option value="5">处理中</option>
									<option value="10">存在异常</option>
								</select>
							</li>
							<li class="issues"><label class="ui-label"><spring:message code="gameCenter_qihao" />：</label> <input
							type="hidden" id="webIssueCodeValue"
							value="${req.issueCode}"></input> <select
							class="ui-select" name="webIssueCode" id="webIssueCode">
								<!--  <option value="130718053">所有奖期</option>-->
						</select></li>
							
							<li>
								<label for="" class="ui-label"><spring:message code="gameCenter_jiangjin" />：</label>
								<input type="text" id="J-input-startWins" class="input input-money w-1"> - <input type="text" id="J-input-endWins" class="input input-money w-1">
							</li>
							<li>
								<!--  <label class="ui-label"><spring:message code="gameCenter_meiyejilushu" />：</label> -->
								<input id="J-input-pageCount" type="hidden" value="" class="input input-num w-1" />
							</li>
							<li>
								<label class="ui-label">来源：</label>
								<spring-form:select id="J-select-device" name="J-select-device" class="ui-select" path="device">
								    <spring-form:option value=""><spring:message code="gameCenter_quanbu" /></spring-form:option>
								    <c:forEach var="channelTypeEnum" items="${device}">
								        <spring-form:option value="${channelTypeEnum.value}"><spring:eval expression="channelTypeEnum"/></spring-form:option>
								    </c:forEach>
								</spring-form:select>
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
								<!--  <a id="J-button-export" class="btn" href="javascript:void(0);">导出报表<b class="btn-inner"></b></a>-->							
								<a id="J-button-betDetail" class="btn" href="javascript:void(0);">玩法分布<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
					
					<c:choose>
						<c:when test="${ConnectFail == 1}">
							<div class="alert alert-waring">
								<i></i>
								<div class="txt">
									<h4>！后台查询异常</h4>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							
						
					
					<div >
						<table class="table table-info">
							<thead>
								<tr>
									<th><spring:message code="gameCenter_fanganbianhao" /></th>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_qihao" /></th>
									<th><spring:message code="gameCenter_yonghuming" /></th>
									<th class="table-toggle"><a href="#" id="1"><spring:message code="gameCenter_touzhushijian" /><i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="3"><spring:message code="gameCenter_touzhujine" />（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="5"><spring:message code="gameCenter_jiangjin" />（<span class="price"><dfn>&yen;</dfn></span>）<i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th class="table-toggle"><a href="#" id="7"><spring:message code="gameCenter_fanganzhongtoubi" /><i class="ico-up-current"></i><i class="ico-down-current"></i></a></th>
									<th><spring:message code="gameCenter_betdetail" /></th>
									<th><spring:message code="gameCenter_zhuangtai" /></th>
									<th><spring:message code="gameCenter_kaijianghaoma" /></th>
									<th><spring:message code="gameCenter_shifouweizhihaodan" /></th>
									<th><spring:message code="gameCenter_banbenhao" /></th>
									<th><spring:message code="gameCenter_laiyuan" /></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${orders}" var="order">
								<tr>
									<td>
									<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST_DETAIL">
									<a href="queryOrderDetail?orderid=${order.orderId }&orderCode=${order.orderCode}">
									</permission:hasRight>
									${order.orderCode}
									<permission:hasRight moduleName="GAME_OPMGR_ORDERLIST_DETAIL">
									</a>
									</permission:hasRight>
									</td>
									<td>${order.lotteryName }</td>
									<td><a href="${currentContextPath}/gameoa/queryLotteryIssueWarnDetail?lotteryId=${order.lotteryid}&issueCode=${order.issueCode}">${order.webIssueCode }</a></td>
									<td><a href="${currentContextPath}/admin/user/userdetail?id=${order.userid}&typepage=${userInfoUrl}">${order.account }</a></td>
									<td>${order.saleTime }</td>
									<td><fmt:formatNumber value="${order.totalAmount/10000.00 }" pattern="###,###.#" minFractionDigits="2" type="number"/></td>
									<td><fmt:formatNumber value="${(order.totwin+order.totDiamondWin)/10000.00 }" pattern="###,###.####" minFractionDigits="4" type="number"/></td>
									<td><fmt:formatNumber value="${order.winsRatio }" pattern="###,###.#" minFractionDigits="2" type="number"/>
									</td>
									<td>
											<div class="panel-lotterys">
											<div class="panel-lotterys-inner">${order.betDetail }</div>
											</div>
											<c:if test="${(fn:length(order.betDetail)) > 1000}">
											<div class="ico-view-more" class="clearfix">
												<i class="ico-close"></i>
											</div>
											</c:if>
									</td>
									<td>${order.status }</td>
									<td>${order.numberRecord }</td>
									<td>${order.parentType }</td>
									<td>${order.channelVersion }</td>
									<td>${order.channelId }</td>
								</tr>
							</c:forEach>
								
								<tr>
									<td>小结</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${betTotal }" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td><span class="price"><dfn>&yen;</dfn></span><fmt:formatNumber value="${winTotal  }" pattern="#,###.##" minFractionDigits="4"/></td>
									<td><fmt:formatNumber value="${scaleTotal}" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>


							</tbody>
						</table>
                        </div>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
					</div>
					
					</c:otherwise>
					</c:choose>
				</div>
			</div>
			
			<script type="text/template" id="J-tpl-export">
				<form action="exportOrderOperations" target="_blank" id="J-download-form">
						
						<input type="hidden" id="J-hidden-lotteryId" name="lotteryid" value="${req.lotteryid}"/>
						<input type="hidden" id="J-hidden-paramCode" name="paramCode" value="${req.paramCode }"/>
						<input type="hidden" id="J-hidden-containSub" name="containSub" value="${req.containSub }"/>
						<input type="hidden" id="J-hidden-selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
						<input type="hidden" id="J-hidden-startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
						<input type="hidden" id="J-hidden-endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
						<input type="hidden" id="J-hidden-status" name="status" value="${req.status }"/>
						<input type="hidden" id="J-hidden-startWinsCount" name="startWinsCount" value="${req.startWinsCount }"/>
						<input type="hidden" id="J-hidden-endWinsCount" name="endWinsCount" value="${req.endWinsCount }"/>
						<input type="hidden" id="J-hidden-issueCode" name="issueCode" value="${req.issueCode}"/>

							<ul class="ui-form ui-form-small">
									<li><span class="ui-singleline">为您导出符合以下条件的数据</span></li>
									<li data-data="<#=lotteryName#>">
										<label class="ui-label w-2" for=""><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<span id="Span-lotteryName" class="ui-singleline"></span>
									</li>
									<li data-data="<#=userName#>">
										<label class="ui-label w-2" for="">用户/方案：</label>
										<span id="Span-account" class="ui-singleline"></span>
									</li>
									<li data-data="<#=startTime#><#=endTime#>">
										<label class="ui-label w-2" for="">投注时间：</label>
										<span id="Span-startTime" class="ui-singleline"></span> - <span id="Span-endTime" class="ui-singleline"></span>
									</li>
									<li data-data="<#=status#>">
										<label class="ui-label w-2" for=""><spring:message code="gameCenter_betdetail" />：</label>
										<span id="Span-status" class="ui-singleline"></span>
									</li>
									<li data-data="<#=status#>">
										<label class="ui-label w-2" for=""><spring:message code="gameCenter_zhuangtai" />：</label>
										<span id="Span-status" class="ui-singleline"></span>
									</li>
									<li data-data="<#=issueCode#>">
										<label class="ui-label w-2" for="">奖期：</label>
										<span id="Span-issueCode" class="ui-singleline"></span>
									</li>
									<li data-data="<#=startMoney#><#=endMoney#>">
										<label class="ui-label w-2" for=""><spring:message code="gameCenter_yihuojiangjin" />：</label>
										<span id="Span-startWins" class="ui-singleline"></span><span id="Span-endWins" class="ui-singleline"></span>
									</li>
							</ul>
							<ul class="ui-form ui-form-small">
									<li style="text-align:center; color:red;"><span class="ui-singleline">报表超过6万笔，档案会无法开启</span></li>
									<li style="text-align:center;"><span class="ui-singleline">请选择导出报表的格式</span></li>	
									<li class="radio-list" style="text-align:center;">
										<label class="label" for="J-radio-doc-type-1"><input checked="checked" id="J-radio-doc-type-1" name="radio-doc-type" type="radio" class="radio radio-doc-type" value="1">xls</label>
										<!--<label class="label" for="J-radio-doc-type-2"><input id="J-radio-doc-type-2" name="radio-doc-type" type="radio" class="radio radio-doc-type" value="2">txt</label>-->
									</li>
							</ul>
			</form>
	</script>
			
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/orderList.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/orderBetDetail.js"></script>
</body>