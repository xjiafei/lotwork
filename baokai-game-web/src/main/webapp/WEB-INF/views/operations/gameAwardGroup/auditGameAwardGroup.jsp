<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<meta http-equiv="Content-Type" content="text/html;">
<title>奖金组-审核奖金组</title>

	
</head>
<body>
<div id="tab_menu_id"  style="display:none">awardGroupMenu</div>
<div class="col-main">
			<div class="col-crumbs">
            <div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${lotteryId}&status=&awardId="><spring:message code="gameCenter_jiangjinzu"/></a>>审核奖金组
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
								<h3 class="ui-title"><a href="${currentContextPath}/gameoa/queryGameAwardGroupList?lotteryid=${lotteryId }&status=&awardId=">&lt;&lt; 返回奖金组列表</a></h3>
								<ul class="ui-search">
									<li>
										<label class="ui-label" for=""><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<span class="ui-text-info">${gameAward.lotteryName }</span>
										<input type="hidden" name="gameAward.lotteryId" value="${gameAward.lotteryId }"/>
									</li>
									<li>
										<label class="ui-label" for="">奖金组命名：</label>
										<span class="ui-text-info">${gameAward.awardGroupName}</span>
									</li>
									<li>
										
										<!-- <span class="ui-text-info">${gameAward.directRet }</span> -->
										<label class="ui-label" for="">返点设置：</label>
										<span class="ui-text-info">直选及其他返点</span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.directRet" value="${gameAward.directRet}" id="J-point-general" />
										<span class="ui-text-info color-gray">(设置范围 ：<label id="label1">0.05</label>- <label id="label2">0.06</label>即<label id="label3">5%</label>- <label id="label4">6%</label>)</span>
										<input type="hidden" id="directRet" value="${gameAward.directRet }"/>
										<input type="hidden" id="miniLotteryProfit" value="${miniLotteryProfit}"/>
									</li>
									
									<li>
									<c:if test="${gameAward.lotteryId != 99301 &&  gameAward.lotteryId != 99302 &&  gameAward.lotteryId != 99303 &&  gameAward.lotteryId != 99304 &&  gameAward.lotteryId != 99305 &&  gameAward.lotteryId != 99306}">
										<span class="ui-text-">三星一码不定位返点</span>
										<input type="text" class="input input-decimal w-1" name="gameAwardGroup.threeoneRet " value="${gameAward.threeoneRet}" id="J-point-budingwei" />
										<span class="ui-text-info color-gray">(设置范围 ：<label id="label11">0.05</label>- <label id="label12">0.06</label>即<label id="label13">5%</label>- <label id="label14">6%</label>)</span>
									</c:if>
										
										<input type="hidden" value="${gameAward.threeoneRet }" id="threeoneRet" />
										<input type="hidden" value="${lotteryId }" id="lotteryId"/>
										<input type="hidden" value="${awardId}" id="awardId"/>
										<input type="hidden" value="2" id="checkType"/>
									</li>
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
										<c:forEach items="${awardList}" var="awards" varStatus="awardIndex">
											
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
															
															<c:if test="${methodCodes.methodCount ==0 && methodCodes.methodCodeCount ==0}">
															<td><span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode}_${methodCodes.theoryBonus *100}" 
																<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															>
															${methodCodes.actualBonus/100 }</span>
															</td>
															<td><span class="point-lirun"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															>${methodCodes.totalProfit }</span></td>
															<td><span class="point-proxy"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															>${methodCodes.topAgentPoint }</span></td>
															<td><span class="point-liushui"
															<c:if test="${(awards.groupCode ==12 || awards.groupCode == 13) && setCodes.setCode ==12 && methodCodes.methodCode ==40}">
																name="samson"
																</c:if>
															>${methodCodes.companyProfit }</span></td>															
															</c:if>
															
															<c:if test="${methodCodes.methodCount !=0 }">
															<td>
															<c:forEach items="${methodCodes.assistBonusList }" var="assistBonus" varStatus="assistBonusIndex">
															<div name="${assistBonus.methodTypeName }">
																${assistBonus.methodTypeName }&nbsp;&nbsp;<span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${assistBonus.theoryBonus *100}_${assistBonus.methodType }_text"  >
																${assistBonus.actualBonus/100 }</span>
															</div>
															</c:forEach>
															</td>	
															<td><span class="point-lirun">${methodCodes.totalProfit }</span></td>
															<td><span class="point-proxy">${methodCodes.topAgentPoint }</span></td>
															<td><span class="point-liushui">${methodCodes.companyProfit }</span></td>														
															</c:if>
															
															<c:if test="${methodCodes.methodCodeCount !=0 }">
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div name="${methodCodeChild.methodCodeName }">
																${methodCodeChild.methodCodeName }&nbsp;&nbsp;<span class="point-bonus" id="${awards.groupCode }_${setCodes.setCode }_${methodCodes.methodCode }_${methodCodeChild.theoryBonus *100}_${methodCodeChild.methodCode }_text" >
																${methodCodeChild.actualBonus/100 }</span>
															</div>
															</c:forEach>
															</td>																														
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-lirun">${methodCodeChild.totalProfit }</span>
															</div>
															</c:forEach>											
															</td>
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-proxy">${methodCodeChild.topAgentPoint }</span>
															</div>
															</c:forEach>
															</td>
															<td>
															<c:forEach items="${methodCodes.methodCodeList }" var="methodCodeChild" varStatus="q">
															<div>
															<span class="point-liushui">${methodCodeChild.companyProfit }</span>
															</div>
															</c:forEach>
															</td>
															</c:if>
													</tr>
												
												</c:forEach>
												
											</c:forEach>
										</c:forEach>
										<tr>
											<td colspan="7" class="text-center">
												<a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button1">审核通过<b class="btn-inner"></b></a>
												<a class="btn btn-important" href="javascript:void(0);" id="J-Submit-Button2"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
											</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	<textarea  id="DivTip" style="position:absolute;z-index:2; display:none"  class="pop w-8">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定审核通过当前内容吗？</h4>
            </div>
            <div class="pop-btn">
            	<a href="javascript:void(0);" class="btn btn-important " id="J-Button1">确 认<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
               
            </div>
        </div>
    </textarea>
    <textarea  id="DivTip2" style="position:absolute;z-index:2; display:none"  class="pop w-8">       
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定<spring:message code="gameCenter_shenhebutongguo" />当前内容吗？</h4>
            </div>
            <div class="pop-btn">
            	<a href="javascript:void(0);" class="btn btn-important " id="J-Button2">确 认<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs2">取 消<b class="btn-inner"></b></a>
               
            </div>
        </div>
    </textarea>
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
	<style>
	.j-ui-tip-info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	.input-mark, .input-mark:focus, .input-mark:hover{
		cursor: default;
	}
	</style>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameAward/auditGameAwardGroup.js"></script>
</body>