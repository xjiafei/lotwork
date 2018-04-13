<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>方案追号管理-  恶意记录</title>
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">审核中心</a>><a href="#">统计报表</a>>恶意记录
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li><a href="queryWarnOrderList">风险记录</a></li>
									<li class="current">恶意记录</li>
								</ul>
							</div>
							<div class="ui-tab-content clearfix">

						
						<form action="querySpiteOrderList" method="post" id="J-search-form">
							<input type="hidden" id="lotteryid" name="lotteryid" value="${req.lotteryid}"/>
							<input type="hidden" id="paramCode" name="paramCode" value="${req.paramCode }"/>
							<input type="hidden" id="containSub" name="containSub" value="${req.containSub }"/>
							<input type="hidden" id="selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
							<input type="hidden" id="startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
							<input type="hidden" id="endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
							<input type="hidden" id="status" name="status" value="${req.status }"/>
							
							<input type="hidden" id="pageCount" name="pageCount" value="${page.pageSize }"/>
							<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
						
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value=""><spring:message code="gameCenter_quanbucaizhong" /></option>
									<option value="99101">重庆时时彩</option>
									<option value="99106">宝开时时彩</option>
									<option value="99102">江西时时彩</option>
									<option value="99103">新疆时时彩</option>
									<option value="99104">天津时时彩</option>
									<option value="99107">上海时时乐</option>
									<option value="99301">山东11选5</option>
									<option value="99302">江西11选5</option>
									<option value="99307">江苏11选5</option>
									<option value="99303">广东11选5</option>
									<option value="99304">重庆11选5</option>
									<option value="99306">秒开11选5</option>
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>
									<option value="99111">宝开一分彩</option>
									<option value="99114">腾讯分分彩</option>
									<option value="99112">秒开时时彩</option>
									<option value="99501">江苏快三</option>
									<option value="99502">安徽快三</option>
									<option value="99601">江苏骰宝</option>
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>
								</select>
							</li>
							<li>
								<label for="" class="ui-label">搜索用户/方案：</label>
								<input type="text" id="J-input-paramCode" name="J-input-paramCode" class="input w-3">
								<label class="label" for="ck1"><input type="checkbox" checked="true" class="checkbox" name="type" id="J-select-containSub">包含下级</label>
							</li>
							<li>
								<label for="" class="ui-label">异常原因：</label>
								<select id="J-select-status" class="ui-select">
									<option value="-1" selected=""><spring:message code="gameCenter_quanbu" /></option>
									<option value="2" selected="">审核未通过</option>
									<option value="1" selected="">方案比较错误</option>
								</select>
							</li>
							<li class="time">
								<label for="" class="ui-label">投注时间：</label>
								<select class="ui-select" id="J-select-time">
									<option value="1" selected="selected"><spring:message code="gameCenter_jintian" /></option>
									<option value="2">2天</option>
									<option value="3"><spring:message code="gameCenter_santian" /></option>
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
								<label class="ui-label">每页记录数：</label>
								<input id="J-input-pageCount" type="text" value="" class="input input-num w-1" />
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
					
					<!-- 
					<c:choose>
						<c:when test="${totalDataCount == 0 }">
							<div class="alert alert-waring">
								<i></i>
								<div class="txt">
									<h4>没有符合条件的记录，请更改查询条件！</h4>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							
						</c:otherwise>
					</c:choose>
					 -->
					
						<table class="table table-info">
							<thead>
								<tr>
									<th><spring:message code="gameCenter_fanganbianhao" /></th>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_qihao" /></th>
									<th><spring:message code="gameCenter_yonghuming" /></th>
									<th>投注时间</th>
									<th><spring:message code="gameCenter_touzhujine" /></th>
									<th>异常原因</th>
									<th>用户状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${spites}" var="spite">
									<tr>
										<td>
										<permission:hasRight moduleName="GAME_OPMGR_WARNLIST_SPITE_DETAIL">
										<a href="queryOrderDetail?orderid=${spite.orderId}&orderCode=${spite.orderCode}" >
										</permission:hasRight>
										${spite.orderCode}
										<permission:hasRight moduleName="GAME_OPMGR_WARNLIST_SPITE_DETAIL">
										</a>
										</permission:hasRight>
										</td>
										<td>${spite.lotteryName}</td>
										<td>${spite.webIssueCode}</td>
										<td><a href="${adminContextPath}/admin/user/userdetail?id=${spite.userId}" >${spite.account}</a></td>
										<td>${spite.saleTime}</td>
										<td>${spite.totamount/10000}</td>										
										<td>
										${spite.status }
										
										</td>
										<td>
											<span class="row-freeze color-red">
												<c:if test="${spite.userStatus!=1 }">
												未冻结
												</c:if>
												<c:if test="${spite.userStatus==1 }">
												已冻结
												<span class="row-freeze-tip j-ui-tip">
												冻结说明：<br />
												冻结此用户和所有下级
												不可投注，可充提<br />
												<a href="${adminContextPath}/admin/user/freezeuserlist?search=0">去冻结名单查看&gt;&gt;</a>
												</span>
												</c:if>
												
											</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
					</div>
				</div>
			</div>
	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/warn/spiteList.js"></script>
</body>