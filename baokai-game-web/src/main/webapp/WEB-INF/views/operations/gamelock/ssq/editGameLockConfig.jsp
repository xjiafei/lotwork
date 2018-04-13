<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>封锁参数修改  封锁数据</title>
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
								
								
								<h3 class="ui-title"><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">&lt;&lt; 返回</a></h3>
								
								<form id="J-form" action="<%=request.getContextPath() %>/gameoa/editConfig" method="post">
								<div id="J-content-info" class="ssq-content-area">
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
											<input id="id" name="id" type="hidden" value="${id}">
											<input id="gameId" name="gameId" type="hidden" value="${lotteryId}">
											<input id="upValProcess" name="upValProcess" type="hidden" value="">
											<input id="redSlipValProcess" name="redSlipValProcess" type="hidden" value="">
											<input id="blueSlipValProcess" name="blueSlipValProcess" type="hidden" value="">
												<td><input id="J-red-ball" type="text" class="input w-2" value="${gameLock.redSlipVal/10000 }"></td>
												<td><input id="J-blue-ball" type="text" class="input w-2" value="${gameLock.blueSlipVal/10000 }"></td>
												<td><span class="tips-area"><span id="maxRNum">${gameLock.upVal/10000 }</span><span class="tips-bonus">小提示：该值值会根据5红球封锁注数和蓝球封锁注数自动得出</span></span></td>
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
									<div style="margin:0 auto;width:200px;">
									<a id="J-button-submit" href="#" class="btn btn-important" style="margin: 10px auto">保存修改<b class="btn-inner"></b></a>
									</div>
								</div>
								</form>
								
								
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>
	
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelock/ssqGameLockDetail.js"></script>
<script>
	(function(){
		$('#J-button-submit').on('click',function(){
			$('#upValProcess').val(Number($('#maxRNum').text().replace(/,/gm,''))*10000);
			$('#redSlipValProcess').val(Number($('#J-red-ball').val().replace(/,/gm,''))*10000);
			$('#blueSlipValProcess').val(Number($('#J-blue-ball').val().replace(/,/gm,''))*10000);
			 if(Number($('#redSlipValProcess').val().replace(",",'')) <0 || Number($('#blueSlipValProcess').val().replace(",",''))<0){
				alert("封锁值必须大于0");
				return;
			}
			$('#J-form').submit();
			
		});
	})();
</script>
</body>
