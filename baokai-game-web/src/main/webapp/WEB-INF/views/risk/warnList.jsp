<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>方案追号管理-  风险记录</title>
	
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
</head>
<body>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">审核中心</a>><a href="#">统计报表</a>>风险记录
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<permission:hasRight moduleName="RISK_GAME_REPORT_WARN">
									<li class="current">风险记录</li>
									</permission:hasRight>
									<permission:hasRight moduleName="RISK_GAME_REPORT_ERROR">
									<li><a href="querySpiteOrderList">恶意记录</a></li>
									</permission:hasRight>
								</ul>
							</div>
							<div class="ui-tab-content clearfix">
							
					

						
						<form action="queryWarnOrderList" method="post" id="J-search-form">
							<input type="hidden" id="lotteryid" name="lotteryid" value="${req.lotteryid}"/>
							<input type="hidden" id="paramCode" name="paramCode" value="${req.paramCode }"/>
							<input type="hidden" id="containSub" name="containSub" value="${req.containSub }"/>
							<input type="hidden" id="selectTimeMode" name="selectTimeMode" value="${req.selectTimeMode}"/>
							<input type="hidden" id="startCreateTime" name="startCreateTime" value="${req.startCreateTime}"/>
							<input type="hidden" id="endCreateTime" name="endCreateTime" value="${req.endCreateTime}"/>
							<input type="hidden" id="issueAward" name="issueAward" value="${req.issueAward }"/>
							<input type="hidden" id="issueWinsRatio" name="issueWinsRatio" value="${req.issueWinsRatio }"/>
							<input type="hidden" id="betAward" name="betAward" value="${req.betAward }"/>
							<input type="hidden" id="betWinsRatio" name="betWinsRatio" value="${req.betWinsRatio }"/>
							<input type="hidden" id="winsTime" name="winsTime" value="${req.winsTime }"/>
							<input type="hidden" id="status" name="status" value="${req.status }"/>
							
							<input type="hidden" id="pageCount" name="pageCount" value="${page.pageSize }"/>
							<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
							<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
						
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label class="ui-label">彩种名称：</label>
								<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value="">全部彩种</option>
									<option value="99101">重庆时时彩</option>
									<option value="99106">宝开时时彩</option>
									<option value="99102">江西时时彩</option>
									<option value="99104">新疆时时彩</option>
									<option value="99103">天津时时彩</option>
									<option value="99107">上海时时乐</option>
									<option value="99105">黑龙江时时彩</option>
									<option value="99301">山东11选5</option>
									<option value="99302">江西11选5</option>
									<option value="99307">江苏11选5</option>
									<option value="99303">广东11选5</option>
									<option value="99304">重庆11选5</option>
									<option value="99305">宝开11选5</option>
									<option value="99306">秒开11选5</option>
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>
									<option value="99701">六合彩</option>
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
							
							<li class="time">
								<label for="" class="ui-label">投注时间：</label>
								<select class="ui-select" id="J-select-time">
									<option value="1" selected="selected">今天</option>
									<option value="2">2天</option>
									<option value="3">3天</option>
									<option value="4">4天</option>
									<option value="5">5天</option>
									<option value="6">6天</option>
									<option value="7">7天</option>
									<option value="-1">全部</option>
									<option value="0">自定义</option>
								</select>
								<input id="J-time-start" type="text" value="" class="input"> - <input id="J-time-end" type="text" value="" class="input">
							</li>
							<li>
								<label class="ui-label">单期奖金 &gt; </label>
								 <input type="text" id="J-input-issueAward" class="input input-money w-1">
							</li>
							<li>
								<label class="ui-label">单期中/投比 &gt; </label>
								 <input type="text" id="J-input-issueWinsRatio" class="input input-num w-1">
							</li>
							<li>
								<label class="ui-label">单注奖金 &gt; </label>
								 <input type="text" id="J-input-betAward" class="input input-money w-1">
							</li>
							<li>
								<label class="ui-label">单注中/投比 &gt; </label>
								<input type="text" id="J-input-betWinsRatio" class="input input-num w-1">
							</li>
							<li>
								<label class="ui-label">连续中奖次数 &gt; </label>
								<input type="text" id="J-input-winsTime" class="input input-num w-1">
							</li>
							<li>
								<label for="" class="ui-label">状态：</label>
								<select id="J-select-status" name="J-select-status" class="ui-select">
									<option value="-1">全部</option>
									<option value="0">待审核</option>
									<option value="1">审核通过</option>
									<option value="2">审核未通过</option>
								</select>
							</li>

							<li>
								<label class="ui-label">每页记录数：</label>
								<input id="J-input-pageCount" type="text" class="input input-num w-1" />
							</li>
							<li>
								<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
								<a id="J-download-submit" class="btn btn-important" href="javascript:void(0);">导出报表<b class="btn-inner"></b></a>
							</li>
						</ul>
					</form>
					 <form action="downLoadWarnOrderList" method="post" id="J-download-form">
							<input type="hidden" id="lotteryid_" name="lotteryid" value="${req.lotteryid}"/>
							<input type="hidden" id="paramCode_" name="paramCode" value="${req.paramCode }"/>
							<input type="hidden" id="containSub_" name="containSub" value="${req.containSub }"/>
							<input type="hidden" id="selectTimeMode_" name="selectTimeMode" value="${req.selectTimeMode}"/>
							<input type="hidden" id="startCreateTime_" name="startCreateTime" value="${req.startCreateTime}"/>
							<input type="hidden" id="endCreateTime_" name="endCreateTime" value="${req.endCreateTime}"/>
							<input type="hidden" id="issueAward_" name="issueAward" value="${req.issueAward }"/>
							<input type="hidden" id="issueWinsRatio_" name="issueWinsRatio" value="${req.issueWinsRatio }"/>
							<input type="hidden" id="betAward_" name="betAward" value="${req.betAward }"/>
							<input type="hidden" id="betWinsRatio_" name="betWinsRatio" value="${req.betWinsRatio }"/>
							<input type="hidden" id="winsTime_" name="winsTime" value="${req.winsTime }"/>
							<input type="hidden" id="status_" name="status" value="${req.status }"/>
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
					
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th class="w-1"><a id="J-button-all" class="btn btn-small" href="javascript:void(0);">展开<b class="btn-inner"></b></a></th>
									<th>用户名</th>
									<th>彩种名称</th>
									<th>期号</th>
									<th>单期奖金(&yen;)</th>
									<th>单期中/投比</th>
									<th>连续中奖期数</th>
									<th style="position: relative;">连续中奖次数 <i id="J-th-question" class="icon-question">?</i></th>
									<th>操作项</th>

								</tr>
							</thead>
							<tbody>
								<c:forEach items="${risks }" var="riskOrders">
								
								<tr>
									<td>&nbsp;</td>
									<td class="table-tool"><a class="ico-control ico-unfold" href="javascript:void(0);"></a><a href="${adminContextPath}/admin/user/userdetail?id=${riskOrders.userId}" class="link">${riskOrders.account }</a></td>
									<td>${riskOrders.lotteryName }</td>
									<td>${riskOrders.webIssueCode }</td>
									<td <c:if test="${not empty config.orderwarnMaxwins && config.orderwarnMaxwins!=0 && riskOrders.countWins > config.orderwarnMaxwins }"> class="color-red" </c:if> ><fmt:formatNumber value="${riskOrders.countWins/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td <c:if test="${not empty config.orderwarnWinsRatio && config.orderwarnWinsRatio!=0 && riskOrders.issueWinsRatio > config.orderwarnWinsRatio}">class="color-red"  </c:if>><fmt:formatNumber value="${riskOrders.issueWinsRatio/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
									<td>${riskOrders.orderwarnContinuousWins }</td>
									<td>${riskOrders.continuousWinsTimes }</td>
									<td>
										<a href="${currentContextPath}/gameoa/queryOrderListByIssueCode?lotteryId=${riskOrders.lotteryid}&issueCode=${riskOrders.issueCode}">方案记录</a>
										<a href="${currentContextPath}/admin/Reporting/index?parma=sv55&UserName=${riskOrders.account}">交易记录</a>
									</td>
								</tr>
								<tr style="display:none;">
									<td>&nbsp;</td>
									<td colspan="7">
										<table class="table table-border">
											<thead>
												<tr>
													<th>方案编号</th>
													<th>投注时间</th>
													<th>投注金额(&yen;)</th>
													<th>奖金(&yen;)</th>
													<th>中奖号码</th>
													<th>方案中/投比</th>
													<th>单注最大奖金(&yen;)</th>
													<th>单注最大中/投比</th>
													<th>是否为追号单</th>
													<th>版本号</th>
													<th>审核状态</th>
													<th>审核员</th>
													<th>审核时间</th>
													<th>审核备注</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${riskOrders.riskOrderDetailStrucs }" var="riskOrderDetail">
													<tr>
														<td><a href="${currentContextPath}/gameoa/queryOrderDetail?orderid=${riskOrderDetail.orderId }&orderCode=${riskOrderDetail.orderCode }">${riskOrderDetail.orderCode }</a></td>
														<td>${riskOrderDetail.isoSaleTime } </td>
														<td><fmt:formatNumber value="${riskOrderDetail.totamount/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
														<td><fmt:formatNumber value="${riskOrderDetail.countWin/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
														<td>${riskOrderDetail.numberRecord }</td>
														<td><fmt:formatNumber value="${riskOrderDetail.winsRatio/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
														<td><fmt:formatNumber value="${riskOrderDetail.maxslipWins/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
														<td><fmt:formatNumber value="${riskOrderDetail.slipWinsratio/10000 }" pattern="#,###.##"  minFractionDigits="2"/></td>
														<td><c:if test="${riskOrderDetail.parentType==1 }">否</c:if><c:if test="${riskOrderDetail.parentType==2 }">是</c:if> </td>
														<td>${riskOrderDetail.channelVersion }</td>
														<td>
															<permission:hasRight moduleName="RISK_GAME_REPORT_WARN_AUDIT">
															<c:if test="${riskOrderDetail.status==0 }">
																<a class="button-action button-noaudit" id="${riskOrderDetail.id}" name="${riskOrderDetail.id}" href="javascript:void(0);">未审核</a><input type="hidden" class="record-hidden-id" value="1" />
															</c:if>
															</permission:hasRight>
															<c:if test="${riskOrderDetail.status==1 }">
																<span style="color:#999;">已通过</span>
															</c:if>
															<c:if test="${riskOrderDetail.status==2 }">
																<span style="color:red;">未通过</span>
															</c:if>
														</td>
														<td>${riskOrderDetail.apprUser }</td>													
														<c:choose>

   																	<c:when test="${riskOrderDetail.apprTimeStr=='1970-01-01 08:00:00' }"> 
																			<td></td>
   																	</c:when>
   
   																	<c:otherwise>  <td>${riskOrderDetail.apprTimeStr }</td>
   																	</c:otherwise>
  
														</c:choose>
														
														
														<td>${riskOrderDetail.apprMemo }</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
						<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
					</div>
				</div>
			</div>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	
	.pop-window-datepicker {z-index:710;}
	</style>
	<script id="J-tpl-noaudit" type="text/template">
审核备注：
<div><textarea style="width:400px;height:80px;"></textarea></div>
</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/risk/warnList.js"></script>
</body>