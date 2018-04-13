<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<head>
	<title>返点管理</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/userCenter/commonUserModifyRet.js"></script>
	<script>var baseUrl = '${currentContextPath}';</script>
</head>
<body>
<div class="g_28 g_last">
			<div class="common-content">
				<div class="title">返点修改</div>
				<div class="content">
					
					<!-- start -->
					<div class="rebate-list">
						<input type="hidden" id="userid" name="userid" value="${req.userid}"/>
						<ul class="rebate-list-setup">
							<li>
								<span class="text">开户类型：</span>
								<span id="proxy" class="ico-tab">代理</span>
								<span id="player" class="ico-tab ico-tab-current">玩家</span>
							</li>
						</ul>
						
						<!-- iteration start -->
						<c:forEach var="awardGoupMap" items="${awardGoupMap}"
						varStatus="status">
						<c:forEach var="awardGoupMapPerSeries"
							items="${awardGoupMap.value}">
							<c:choose>
								<c:when test="${status.index == 0}">
									<dl class="item">
								</c:when>
								<c:otherwise>
									<dl class="item item-ssc">
								</c:otherwise>
							</c:choose>
							<dt>
								<c:out value="${awardGoupMapPerSeries.key}" />
							</dt>
							
							<c:forEach var="awardGoupStruc"
								items="${awardGoupMapPerSeries.value}"  varStatus="awardGoupStrucStatus">
								<dd>
									<table class="table">
										<input type="hidden" id="awardGroupId" name="awardGroupId" value="${awardGoupStruc.awardGroupId}"/>
										<input type="hidden" id="lotterySeriesName" name="lotterySeriesName" value="${awardGoupStruc.lotterySeriesName}"/>
										<input type="hidden" id="lotterySeriesCode" name="lotterySeriesCode" value="${awardGoupStruc.lotterySeriesCode}"/>
										<input type="hidden" id="awardName" name="awardName" value="${awardGoupStruc.awardName}"/>
										<input type="hidden" id="status" name="status" value="${awardGoupStruc.status}"/>
										<input type="hidden" id="directLimitRet" name="directLimitRet" value="${awardGoupStruc.directLimitRet}"/>
										<input type="hidden" id="threeLimitRet" name="threeLimitRet" value="${awardGoupStruc.threeLimitRet}"/>
										<tr>
											<td><c:out value="${awardGoupStruc.awardName}" />保留返点：
											</td>
											<td>直选返点<input type="text" parentId="awardGoupStruc-${status.index}-${awardGoupStrucStatus.index}-1" id="directRet" name="directRet"  max="${awardGoupStruc.directLimitRet/100}"
												<c:if test="${awardGoupStruc.status == 1}">
															value="${awardGoupStruc.directRet/100}"
															min="${awardGoupStruc.directRet/100}"
												</c:if>
												<c:if test="${awardGoupStruc.status == 0}">
															value="" min="0" disabled 
												</c:if>
												 class="input w-2" />(可分配返点范围为
													<c:choose>
														<c:when test="${awardGoupStruc.directLimitRet == 0}">
															0
														</c:when>
														<c:otherwise>
															0-${awardGoupStruc.directLimitRet/100}
														</c:otherwise>
													</c:choose>
												
												)</td>
												<td rowspan="2"><input type="radio" id="awardGoupStruc-${status.index}-${awardGoupStrucStatus.index}" name="${awardGoupMapPerSeries.key}" class="radio" 
												<c:if test="${awardGoupStruc.status == 1}">
															checked
												</c:if>
												/>
												</td>
										</tr>
										<tr>
											<td></td>
											<td>不定位返点<input type="text" parentId="awardGoupStruc-${status.index}-${awardGoupStrucStatus.index}-2" id="threeoneRet" name="threeoneRet" max="${awardGoupStruc.threeLimitRet/100}"
												<c:if test="${awardGoupStruc.status == 1}">
															value="${awardGoupStruc.threeoneRet/100}"
															min="${awardGoupStruc.threeoneRet/100}"
												</c:if>
												<c:if test="${awardGoupStruc.status == 0}">
															value="" min="0" disabled 
												</c:if>
												 class="input w-2" />(可分配返点范围为
													<c:choose>
														<c:when test="${awardGoupStruc.threeLimitRet == 0}">
															0
														</c:when>
														<c:otherwise>
															0-${awardGoupStruc.threeLimitRet/100}
														</c:otherwise>
													</c:choose>
													
												
												)</td>
										</tr>
									</table>
								</dd>
							</c:forEach>
							</dl>
						</c:forEach>
					</c:forEach>
						
						<!-- iteration end -->
						
						<div class="rebate-btn">
							<a href="javascript:void(0);" id="J-Submit-Butt1" class="btn btn-important">修改返点<b class="btn-inner"></b></a>
						</div>
					</div>
					
					
					<!-- end -->
				</div>
			</div>
		</div>

<div id="divPrompt" class="pop w-7" style="position:absolute;left:500px;display:none">
	<div class="hd"><span class="close" id="divclose"></span>温馨提示</div>
		<div class="bd">
			<h4 class="pop-title">您确定要把当前会员升级为代理吗？</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divCanceled">取 消<b class="btn-inner"></b></a>
				<a href="javascript:void(0);" class="btn btn-important " id="J-Submit">确 定<b class="btn-inner"></b></a>
			</div>
		</div>
</div>

	<div id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;left:900px;display:none">
		<i class="ico-success"></i>
		<h4 class="pop-text">操作成功</h4>
	</div>
<!-- 	<div id="" class="pop pop-error w-4" style="position:absolute;left:1200px;display:none">
		<i class="ico-error"></i>
		<h4 class="pop-text">操作失败</h4>
	</div> -->

	<div class="pop w-7" style="position:absolute;left:500px;display:none" id="">
		<div class="hd"><i class="close" id="divPromptFailuren1"></i>温馨提示</div>
		<div class="bd">
			<h4 class="pop-title color-red">操作失败</h4>
			<div class="pop-btn">
				<a href="javascript:void(0);" class="btn" id="divPromptFailuren2">关 闭<b class="btn-inner"></b></a>
			</div>
		</div>
	</div>
	
<!-- 	<div id="divPromptOk" class="pop pop-success w-4" style="position:absolute;left:900px;display:none" >
			<i class="ico-success"></i>
			<h4 class="pop-text">操作成功</h4>
	</div>	 -->
    <textarea id="DivUnfillContent" style="display:none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">返点设置不能为空，请重新设置</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="CloseDf">关 闭<b class="btn-inner"></b></a>                
            </div>
        </div>
    </textarea>

<!-- <div id="DivUnfillContent" class="pop w-7" style="position:absolute;left:100px;display:none">
	<div class="hd"><i class="close"></i>温馨提示</div>
	<div class="bd">
		<h4 class="pop-title">返点设置不能为空，请重新设置</h4>
		<div class="pop-btn">
			<a href="javascript:void(0);" class="btn btn-important ">确 定<b class="btn-inner"></b></a>
		</div>
	</div>
</div> -->
</body>
