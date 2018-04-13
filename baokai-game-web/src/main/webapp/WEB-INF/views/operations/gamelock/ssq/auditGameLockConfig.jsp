<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>封锁参数审核  封锁数据</title>

</head>
<body>
<div id="tab_menu_id" style="display:none">lockMenu</div>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>封锁参数设定
			</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
							<div id="J-list-container" class="">
							
								<ul class="ui-tab-title clearfix">
									<li><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁数据</a></li>
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">封锁参数设定</a></li>
								</ul>
								
								<h3 class="ui-title"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">&lt;&lt; 返回</a></h3>
								
								
								
								<form id="J-form" action="${currentContextPath}/gameoa/auditConfig" method="post">
									<div id="J-content-info" class="ssq-content-area ssq-audit">
									<table class="table table-info text-center">
										<thead>
											<tr>
												<th colspan="3">封锁参数设置</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>5红球封锁注数</td>
												<td>蓝球封锁注数</td>
												<td>最大风险值</td>
											</tr>
											<tr>
												<td>
													<span class="tips-area">
														<span>${gameLock.redSlipValProcess/10000 }</span>
														<span class="tips-bonus">原值：${gameLock.redSlipVal/10000 }</span>
													</span>
												</td>
												<td>
													<span class="tips-area">
														<span>${gameLock.blueSlipValProcess/10000 }</span>
														<span class="tips-bonus">原值：${gameLock.blueSlipVal/10000 }</span>
													</span>
												</td>
												<td>${gameLock.upValProcess/10000}</td>
											</tr>
											<input type="hidden" id="J-red-ball" value="${gameLock.redSlipValProcess/10000 }">
											<input type="hidden" id="J-blue-ball" value="${gameLock.blueSlipValProcess/10000 }">
										<input type="hidden" name="id" value="${id }">
										<input type="hidden" name="gameId" value="${lotteryId }">
										<input type="hidden" name="upVal" value="${gameLock.upVal }">
										<input type="hidden" name="upValProcess" value="${gameLock.upValProcess }">
										<input type="hidden" name="redSlipVal" value="${gameLock.redSlipVal }">
										<input type="hidden" name="redSlipValProcess" value="${gameLock.redSlipValProcess }">
										<input type="hidden" name="blueSlipVal" value="${gameLock.blueSlipVal }">
										<input type="hidden" name="blueSlipValProcess" value="${gameLock.blueSlipValProcess }">
										<input id="status" type="hidden" name="status" value="2">
										</tbody>
									</table>	
									<div class="tab-title">封锁<spring:message code="gameCenter_xiangqing" /></div>
									<table class="table table-info text-center">
										<thead>
											<tr>
												<th rowspan="2">奖级</th>
												<th colspan="2">
													中奖条件
												</th>
												<th rowspan="2">封锁注数</th>
												<th rowspan="2">单注奖金</th>
												<th rowspan="2">奖项风险值</th>
											</tr>
											<tr>
												<th>
													红球
												</th>
												<th>
													蓝球
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>三等奖</td>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv3bets">300</td>
												<td id="lv3Bonus">${bonus[0]/10000 }</td>
												<td id="lv3">1,050,000.00</td>
											</tr>
											<tr>
												<td rowspan="2">四等奖</td>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td></td>
												<td id="lv41bets">300</td>
												<td id="lv41Bonus">${bonus[1]/10000 }</td>
												<td id="lv41">90,000.00</td>
											</tr>
											<tr>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv42bets">8,400</td>
												<td id="lv42Bonus">${bonus[1]/10000 }</td>
												<td id="lv42">2,520,000.00</td>
											</tr>
											<tr>
												<td rowspan="2">五等奖</td>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td></td>
												<td id="lv51bets">8,400</td>
												<td id="lv51Bonus">${bonus[2]/10000 }</td>
												<td id="lv51">151,200.00</td>
											</tr>
											<tr>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv52bets">10,000</td>
												<td id="lv52Bonus">${bonus[2]/10000 }</td>
												<td id="lv52">180,000.00</td>
											</tr>
											<tr>
												<td rowspan="3">六等奖</td>
												<td class="text-left">
													<span class="ball red"></span>
													<span class="ball red"></span>
												</td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv61bets">8,400</td>
												<td id="lv61Bonus">${bonus[3]/10000 }</td>
												<td id="lv61">100,000.00</td>
											</tr>
											<tr>
												<td class="text-left">
													<span class="ball red"></span>
												</td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv62bets">10,000</td>
												<td id="lv62Bonus">${bonus[3]/10000 }</td>
												<td id="lv62">100,000.00</td>
											</tr>
											<tr>
												<td class="text-left"></td>
												<td>
													<span class="ball blue"></span>
												</td>
												<td id="lv63bets">10,000</td>
												<td id="lv63Bonus">${bonus[3]/10000 }</td>
												<td id="lv63">100,000.00</td>
											</tr>
											<tr>
												<td colspan="5">最大风险值</td>
												<td><strong id="maxnum">4,050,000</strong></td>
											</tr>
										</tbody>
									</table>
									<div style="margin:0 auto;width:400px;">
									<a id="J-button-submit" href="#" class="btn btn-important">审核通过<b class="btn-inner"></b></a>
									<a id="J-button-submit_" href="#" class="btn btn-important"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
									</div>
								</div>	
								</form>
								
								
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/ssqGameLockDetail.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/auditGameLockConfig.js"></script>
</body>