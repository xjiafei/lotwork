<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>奖金组-新建奖金组</title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">awardGroupMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${gameAwardTheoryBonus.lotteryId }&status=&awardId="><spring:message code="gameCenter_jiangjinzu"/></a>>新增奖金组
				
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
							<div class="">
								<h3 class="ui-title"><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${gameAwardTheoryBonus.lotteryId }&status=&awardId=">&lt;&lt; 返回奖金组列表</a></h3>
								<ul class="ui-search">
									<li>
										<label class="ui-label" for=""><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<span class="ui-text-info">${gameAwardName }</span>
										<input type="hidden" id="lotteryId" name="gameAwardGroup.lotteryId" value="${gameAwardTheoryBonus.lotteryId }"/>
										<input type="hidden" id="miniLotteryProfit" value="${miniLotteryProfit }"/>
									</li>
									<li>
										<label class="ui-label" for="">奖金组命名：</label>
										<input type="text" class="input w-2" value="" id="awardName" name="${gameAwardGroup.awardName}">
										
										<!-- <input type="text" class="input w-2" value="1800奖金组" name="names"> -->
									</li>
									<li>
										<label class="ui-label" for="">返点设置：</label>
										<span class="ui-text-info">直选及其他返点</span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.directRet" value="0.05" id="J-point-general" />
										<span class="ui-text-info color-gray">（设值范围：0.05-0.06，即5%-6%）</span>
									</li>
									<li>
										<span class="ui-text-">三星一码不定位返点</span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.threeoneRet"  value="0.05" id="J-point-budingwei" />
										<span class="ui-text-info color-gray">（设值范围：0.05-0.06，即5%-6%）</span>
									</li>
									<li><a href="javascript:void(0);" class="btn btn-important" id="J-button-setting">一键设置<b class="btn-inner"></b></a></li>
								</ul>
								<table class="table table-info table-border" id="J-data-table">
									<thead>
										<tr>
											<th>玩法群</th>
											<th>玩法组</th>
											<th>玩法/投注方式</th>
											<th>奖金（元）</th>
											<th>总利润</th>
											<th>总代返点</th>
											<th>公司留水</th>
										</tr>
									</thead>
									<tbody>
										
										<c:forEach items="${gameAwardTheoryBonus.awardList}" var="awards" varStatus="awardIndex">
											
											<c:forEach items="${awards.setCodeList}" var="setCodes" varStatus="setCodeIndex">
												
												<c:forEach	items="${setCodes.methodCodeList}" var="methodCodes" varStatus="methodIndex">
													
													<tr>
														<c:if test="${setCodeIndex.index==0 && methodIndex.index==0 }">
															<td rowspan="${awards.rowsCount }">${awards.groupName }</td>
														</c:if>
														<c:if test="${methodIndex.index==0 }">
															<td rowspan="${setCodes.setCount}">${setCodes.setCodeName}</td>
														</c:if>
															<td>${methodCodes.methodCodeName }</td>
															<c:if test="${methodCodes.methodCount ==0 }">
															<td>
															<input type="hidden" name="eachMethod" value="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_0"/>
															<input type="text" class="input w-2 input-money" name="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${methodCodes.theoryBonus *100}_text" value="${methodCodes.actualBonus/100 }" />
															</td>
															</c:if>
															<c:if test="${methodCodes.methodCount !=0 }">
															<td>
															<input type="hidden" name="eachMethod" value="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_1"/>
															<div>
															<c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex">
															<div>
																${assistBonus.methodTypeName }&nbsp;&nbsp;<input type="text" class="input w-2 input-money" name="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${methodCodes.theoryBonus *100}_${assistBonus.methodType }_text" value="${assistBonus.actualBonus/100 }" />
															</div>
															</c:forEach>
															</div>
															</td>
															</c:if>			
															<td><span class="point-lirun">${methodCodes.totalProfit }</span></td>
															<td><span class="point-proxy">${methodCodes.topAgentPoint }</span></td>
															<td><span class="point-liushui">${methodCodes.companyProfit }</span></td>
													</tr>	
												
												</c:forEach>
												
											</c:forEach>
										</c:forEach>
									
										<tr>
											<td colspan="7" class="text-center"><a class="btn btn-important"  id="J-SaveChanges">保存<b class="btn-inner"></b></a></td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
	<textarea id="DivUnfillContent" style="display:none;">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您还有未填内容，请完整填写</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="CloseDf">关 闭<b class="btn-inner"></b></a>                
            </div>
        </div>
	</textarea>
    
    <textarea id="DivReset" style="display:none;">
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您设置的返点内容不符合规范或不在返点范围内，请重新设置</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important" id="CloseDd">确 定<b class="btn-inner"></b></a>
              
            </div>
        </div>
    </textarea>
    
    <div  id="DivFailed" style="position:absolute;z-index:2; display:none"  class="pop w-8">
        <div class="hd"><i class="close"></i>温馨提示</div>
        <div class="bd">
            <div class="pop-title">
                <i class="ico-error"></i>
                <h4 class="pop-text">操作失败，请检查网络，并重试</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn" id="CloseDs">关 闭<b class="btn-inner"></b></a>
            </div>
        </div>
    </div>
    <div  id="DivSuccessful" class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
        <i class="ico-success"></i>
        <h4 class="pop-text">操作成功</h4>
    </div>
    
    <script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameAward/createGameAwardGroup.js"></script>
</body>
