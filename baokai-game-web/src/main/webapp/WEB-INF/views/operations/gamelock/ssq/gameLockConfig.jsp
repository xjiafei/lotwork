<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>封锁参数设定  封锁数据</title>
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
							<div id="J-list-container">
							
								<ul class="ui-tab-title clearfix">
									<li><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁数据</a></li>
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">封锁参数设定</a></li>
								</ul>
								<input type="hidden" id="lotteryId" value="${lotteryId}"/>
								<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toGameLockConfig?lotteryid=">
								
								
								<h3 class="ui-title">
									<spring:message code="gameCenter_caizhongmingcheng" />：<select id="J-select-lotteryid" name="J-select-lotteryid" class="ui-select">
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>										
									<option value="99701">六合彩</option>																			
								</select>	
									&nbsp;&nbsp;
									当前状态：<c:if test="${gameLock.status==1 }">
									<span class="color-red">待审核</span>
									</c:if>
									<c:if test="${gameLock.status==2 }">
									<span class="color-red">待发布</span>
									</c:if>
									<c:if test="${gameLock.status==3 }">
									<span class="color-red"><spring:message code="gameCenter_shenhebutongguo" /></span>
									</c:if>
									<c:if test="${gameLock.status==4 }">
									<span class="color-red">进行中</span>
									</c:if>
									
									&nbsp;&nbsp;



										<c:if test="${gameLock.status!=2 }">
										<a href="${currentContextPath}/gameoa/toEditGameLockConfig?lotteryid=${lotteryId}" class="btn btn-important"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
										</c:if>
										
										<c:if test="${gameLock.status==1 || gameLock.status==3 }">
										<a href="${currentContextPath}/gameoa/toAuditGameLockConfig?id=${gameLock.id}&upVal=${gameLock.upVal}&upValProcess=${gameLock.upValProcess}&lotteryid=${lotteryId}" class="btn btn-important" >审核<b class="btn-inner"></b></a>
										</c:if>
										<c:if test="${gameLock.status==2 }">										
										<a id="J-button-submit" href="#" class="btn btn-important" >发布<b class="btn-inner"></b></a>
										<a id="J-button-submit_" href="#" class="btn btn-important" >不发布<b class="btn-inner"></b></a>
										</c:if>

								</h3>
								
								
								<div id="J-content-info" class="ssq-content-area">
								<form id="J-form" action="${currentContextPath}/gameoa/publishConfig" method="post">
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
												<td><c:choose>
													<c:when test="${gameLock.status==1 ||gameLock.status==2}">
													<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.redSlipVal/10000 }">${gameLock.redSlipValProcess/10000 }</span>
													</c:when>
													<c:otherwise> ${gameLock.redSlipValProcess/10000 }</c:otherwise>
													</c:choose></td>
												<td><c:choose>
													<c:when test="${gameLock.status==1 ||gameLock.status==2}">
													<span class="input-mark w-2" style="padding:5px 20px;" data-showtip="原值：${gameLock.blueSlipVal/10000 }">${gameLock.blueSlipValProcess/10000 }</span>
													</c:when>
													<c:otherwise> ${gameLock.blueSlipValProcess/10000 }</c:otherwise>
													</c:choose></td>
												<td><span id="maxRNum">${gameLock.upValProcess/10000 }</span></td>
												<input type="hidden" id="J-blue-ball" value="${gameLock.blueSlipValProcess/10000 }"></input>
												<input id="J-red-ball" type="hidden" value="${gameLock.redSlipValProcess/10000}">
											</tr>
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
									<input id="id" type="hidden" name="id" value="${gameLock.id }">
									<input id="redSlipVal" type="hidden" name="redSlipVal" value="${gameLock.redSlipValProcess }">
									<input id="blueSlipVal" type="hidden" name="blueSlipVal" value="${gameLock.blueSlipValProcess }">
										<input id="upVal" type="hidden" name="upVal" value="${gameLock.upValProcess }">
										<input id="gameId" type="hidden" name="gameId" value="${lotteryId} ">
										<input id="status" type="hidden" name="status" value="4">
										
									</form>	
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/ssqGameLockDetail.js"></script>
<script>
var inputTip = new phoenix.Tip.getInstance();
$('body').on('mouseover', '.input-mark', function(){
	var dom = $(this),text = dom.attr('data-showtip');
	if(text){
		inputTip.setText(dom.attr('data-showtip'));
		inputTip.show(dom.outerWidth() + 4, dom.outerHeight()*-1, this);
	}
}).on('mouseout', '.input-mark', function(){
	var text = this.getAttribute('data-showtip');
	if(text){
		inputTip.hide();
	}
});
var lotteryId = $('#lotteryId').val();
var url = $('#url').val();
$("#J-select-lotteryid").find("option[value='"+lotteryId+"']").attr("selected",true);
$("#J-select-lotteryid").change(function(){
	location.href = url + $("#J-select-lotteryid").val();
});
$('#J-button-submit').click(function(e){
	e.preventDefault();
	$('#J-form').submit();
});
$('#J-button-submit_').click(function(e){
	e.preventDefault();
	$("#status").val(6);
	$('#J-form').submit();
});
</script>
</body>