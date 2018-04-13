<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<head>
	<title>游戏审核设置</title>
	<script type="text/javascript">
		var currentContextPath = '${currentContextPath}';
		var lotteryId = '${lotteryid}';
	</script>
</head>
<body>
<permission:hasRight moduleName="RISK">
	<div class="col-crumbs">
	<div class="crumbs">
		<strong>当前位置：</strong><a href="">审核中心</a>><a href="">游戏审核管理</a>>游戏审核设置
	</div>
	</div>
	
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
						
							<div class="ui-tab-content clearfix">
								
								<form action="${currentContextPath}/gameRisk/modifySeriesConfigRisk" id="modifyForm" name="modifyForm" method="post">
									<input type="hidden" id="lotteryid" name="lotteryid" value="${lotteryId }"/>
									<h3 class="ui-title">游戏审核设置（以下条件满足任意一个即进入审核列表）</h3>
									<ul class="ui-form">
										<li>
											<label class="ui-label">彩种名称：</label>
											<select id="lotteryId" name="lotteryId" class="ui-select">
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
														<option value="99201">北京快乐8</option>
														<option value="99108">3D</option>
														<option value="99109">排列5</option>
														<option value="99401">双色球</option>
														<option value="99701">六合彩</option>
														<option value="99111">宝开一分彩</option>
														<option value="99114">腾讯分分彩</option>
														<option value="99501">江苏快三</option>
														<option value="99502">安徽快三</option>
														<option value="99601">江苏骰宝</option>
														<option value="99602">高频骰宝(娱乐厅)</option>
														<option value="99603">高频骰宝(至尊厅)</option>
											</select>
										</li>
										
										<li>
											<label for="" class="ui-label w-4">单人单期最大获奖金额：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="orderwarnMaxwins" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnMaxwins/10000.00 }' pattern='#' type='number'/>" class="input w-2 input-money"></c:when>
												<c:otherwise><input type="text" disabled="disabled" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnMaxwins/10000.00 }' pattern='###,###,###' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">元</span>
										</li>
										<li>
											<label for="" class="ui-label w-4">单人单期最大中/投比：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="orderwarnWinsRatio" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnWinsRatio/10000.00 }' pattern='#' type='number'/>" class="input w-2 input-number"></c:when>
												<c:otherwise><input type="text" disabled="disabled" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnWinsRatio/10000.00 }' pattern='#' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
										</li>
										<!--
										<li>
											<label for="" class="ui-label w-4">单人最大连续中奖次数：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="orderwarnContinuousWins" value="${seriesConfigRisk.orderwarnContinuousWins }" class="input w-2 input-number"></c:when>
												<c:otherwise><input type="text" disabled="disabled" value="${seriesConfigRisk.orderwarnContinuousWins }" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">次</span>
											<span class="ui-prompt">（一个订单中的一个注单中奖计为一次中奖，数量为一期或连续期数中所有中奖注单数量加和）</span>
										</li>  -->
										<li>
											<label for="" class="ui-label w-4">单注最大获奖金额：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="orderwarnMaxslipWins" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnMaxslipWins/10000.00 }' pattern='#' type='number'/>" class="input w-2 input-money"></c:when>
												<c:otherwise><input type="text" disabled="disabled" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnMaxslipWins/10000.00 }' pattern='###,###,###' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">元</span>
										</li>
										<li>
											<label for="" class="ui-label w-4">单注最大中/投比：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="orderwarnSlipWinsRatio" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnSlipWinsRatio/10000.00 }' pattern='#' type='number'/>" class="input w-2 input-number"></c:when>
												<c:otherwise><input type="text" disabled="disabled" value="<fmt:formatNumber value='${seriesConfigRisk.orderwarnSlipWinsRatio/10000.00 }' pattern='#' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
										</li>
									
									<li>
									<label for="" class="ui-label w-4"></label>
									<c:choose>
										<c:when test="${pageType=='modify' }">
											<a class="btn btn-small"  id="J-button-back">返回<b class="btn-inner"></b></a>
											<a class="btn btn-small" href="javascript:void(0);" id="J-button-modify">保存<b class="btn-inner"></b></a>
										</c:when>
										<c:otherwise>
										<permission:hasRight moduleName="RISK_GAME_CONFIG_MODIFY">
											<a class="btn btn-small"  id="J-button-Update">修改<b class="btn-inner"></b></a>
										</permission:hasRight>
										</c:otherwise>
									</c:choose>
									</li>
									<li>
                                       <label for="" class="ui-label w-4">说明：</label>
                                       <span class="ui-info">值为0或为空的参数，将不做审核判断。</span>
                                    </li>
									</ul>
								</form>
							</div>
							
						</div>
						
					</div>
				</div>
			</div>
			
    <script type="text/javascript" src="${staticFileContextPath}/static/js/risk/seriesConfigRiskSetting.js"></script>
    </permission:hasRight>
</body>
