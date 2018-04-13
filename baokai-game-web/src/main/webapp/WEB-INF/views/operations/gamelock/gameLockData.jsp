<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>1.17  封锁数据 </title>
</head>
<body>
<div id="tab_menu_id" style="display:none">lockMenu</div>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>封锁数据
			</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<jsp:include page="../lotteryConfigHeader.jsp"></jsp:include>
								</ul>
							</div>
							<div id="J-list-container">
							
								<ul class="ui-tab-title clearfix">
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">封锁数据</a></li>
									<li><a href="${currentContextPath}/gameoa/toGameLockConfig?lotteryid=${lotteryId}">封锁参数设定</a></li>
								</ul>
								
								
								<ul class="ui-tab-title clearfix">
									<li class="current"><a href="${currentContextPath}/gameoa/toGameLockDate?lotteryid=${lotteryId}">汇总数据</a></li>
								</ul>
								
								
								<form action="${currentContextPath}/gameoa/getGameLockData" method="post" id="J-search-form">
								<ul class="ui-search">
									<li>
										<!-- 彩种名称 -->
										<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
										<select id="J-select" class="ui-select w-2" disabled="disabled">
										<c:if test="${request.lotteryId==99108 }"><option value="99108" selected="selected">3D</option></c:if>
										<c:if test="${request.lotteryId!=99108 }"><option value="99108">3D</option></c:if>	
										<c:if test="${request.lotteryId==99109 }"><option value="99109" selected="selected">p3/p5</option></c:if>
										<c:if test="${request.lotteryId!=99109 }"><option value="99109">p3/p5</option></c:if>
										<c:if test="${request.lotteryId==99401 }"><option value="99401" selected="selected">双色球</option></c:if>
										<c:if test="${request.lotteryId!=99401 }"><option value="99401">双色球</option></c:if>	
										<c:if test="${request.lotteryId==99701 }"><option value="99701" selected="selected">六合彩</option></c:if>
										<c:if test="${request.lotteryId!=99701 }"><option value="99701">六合彩</option></c:if>										
										</select>
										<input type="hidden" name="lotteryId" value="${request.lotteryId}">
									</li>
									<c:if test="${request.lotteryId==99701 }">
										<li>
											<!-- 玩法 -->
											<label class="ui-label"><spring:message code="gameCenter_wanfa" />：</label>
											<select class="ui-select" name="playType" onchange="changePlayType(this)">
												<c:if test="${request.playType==0}"><option value="0" selected="selected">特码</option></c:if>
												<c:if test="${request.playType!=0}"><option value="0">特码</option></c:if>
												<c:if test="${request.playType==1}"><option value="1" selected="selected">正特码_一肖</option></c:if>
												<c:if test="${request.playType!=1}"><option value="1">正特码_一肖</option></c:if>
												<c:if test="${request.playType==2}"><option value="2" selected="selected">其他玩法</option></c:if>
												<c:if test="${request.playType!=2}"><option value="2">其他玩法</option></c:if>
											</select>
										</li>				
									</c:if>
									<li>
										<label class="ui-label">奖期：</label>
										<select class="ui-select" name="issueCode">
											<c:forEach items="${issueList}" var="issueCode">
												<c:if test="${issueCode.issueCode== request.issueCode}">
													<option value="${issueCode.issueCode }" selected="selected">${issueCode.webIssueCode}</option>
												</c:if>
												<c:if test="${issueCode.issueCode!= request.issueCode}">
													<option value="${issueCode.issueCode }">${issueCode.webIssueCode}</option>
												</c:if>
											</c:forEach>
										</select>
									</li>
									<li>
										<!-- 排序 -->
										<label class="ui-label"><spring:message code="gameCenter_paixu"/>：</label>
										<select class="ui-select" name="sortType">
										<c:if test="${request.sortType==0}"><option id="sortType0" value="0" selected="selected" style="${request.playType ==1 ? 'display:none' : ''}">号码</option></c:if>
										<c:if test="${request.sortType!=0}"><option id="sortType0" value="0" style="${request.playType ==1 ? 'display:none' : ''}">号码</option></c:if>	
										<c:if test="${request.sortType==1 }"><option id="sortType1" value="1" selected="selected">盈亏值</option></c:if>
										<c:if test="${request.sortType!=1 }"><option id="sortType1" value="1">盈亏值</option></c:if>
										<c:if test="${request.sortType==2 && request.lotteryId==99701}"><option id="sortType2" value="2" selected="selected" style="${request.playType !=1 ? 'display:none' : ''}">生肖</option></c:if>
										<c:if test="${request.sortType!=2 && request.lotteryId==99701}"><option id="sortType2" value="2" style="${request.playType !=1 ? 'display:none' : ''}">生肖</option></c:if>
										</select>
									</li>
									<li>
										<label class="ui-label">呈现方式：</label>
										<select id="J-select" class="ui-select w-2">
											<option value="1">数据汇总</option>
										</select>
									</li>
									<li>
										<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">查询<b class="btn-inner"></b></a>
									</li>
								</ul>
								<c:if test="${request.lotteryId==99109}">
								<ul class="ui-tab-title clearfix">
									<li class="current" id="p3Li"><a href="javascript:void(0)" onclick="show('p3')">p3汇总数据</a></li>
									<li id="p5Li"><a href="javascript:void(0)" onclick="show('p5')">p5后二汇总数据</a></li>
								</ul>
								</c:if>
								</form>
								
								<!-- 六合彩預設展示層 -->
								<div id="p3">
								<ul class="ui-form ">
									
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>1. 全体号码假设盈亏值均数 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberValueAvg}" pattern="#,###.##"  minFractionDigits="2"/></span> 元</span>
									</li>
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>2. 截止目前总销售额(S) : <span class="color-red"><fmt:formatNumber value="${struc.totalSaleValue}" pattern="#,###.##"  minFractionDigits="2"/></span></span>
									</li>
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>3. 理论利润率 : <span class="color-red"><fmt:formatNumber value="${struc.theoryProfitRate}" pattern="#,###.##"  minFractionDigits="2"/>%</span></span>
									</li>
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>4. 全体号码假设盈亏值标准差 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberGrantProfitBZC}" pattern="#,###.##"  minFractionDigits="2"/></span></span>
									</li>
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>5. 全体号码假设盈亏值变异系数 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberGrantProfitBYXS}" pattern="#,###.##"  minFractionDigits="2"/></span> 倍</span>
									</li>
									<c:if test="${struc.currentGameResult !=  null}">
									<li>
										<label class="ui-label w-1" for=""></label>
										<span>6. 本期开奖号码 : <span class="color-green">${struc.currentGameResult}, </span>盈亏 : 
											<c:if test="${struc.theoryProfitValue>=0 }"><span class="color-green"></c:if>
											<c:if test="${struc.theoryProfitValue<0 }"><span class="color-red"></c:if>
											<fmt:formatNumber value="${struc.theoryProfitValue/10000.00}" pattern="#,###.##"  minFractionDigits="2"/>
											</span>
										</span>
									</li>
									</c:if>
								</ul>
						
								
								<table class="table table-info">
									<thead>
										<tr>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
											<c:choose>
												<c:when test="${request.sortType==2 && request.lotteryId==99701}"><th>生肖</th></c:when>
												<c:otherwise><th>号码</th></c:otherwise>
											</c:choose>
											<th>盈亏值</th>
										</tr>
									</thead>
									<tbody>
									<c:forEach items="${struc.gameSharesStruc }" var="varlist">
									<tr>
										<c:forEach items="${varlist}" var="gameShares">
											<td>${gameShares.number}</td>
											
											<td>
												<c:if test="${gameShares.profitLoss>=0 }"><span class="color-green"></c:if>
												<c:if test="${gameShares.profitLoss<0 }"><span class="color-red"></c:if>
												<fmt:formatNumber value="${gameShares.profitLoss/10000.00}" pattern="#,###.##"  minFractionDigits="2"/>
												</span>
											</td>
										</c:forEach>
									</tr>
									</c:forEach>
									</tbody>
								</table>
								</div>
						
						
								<div id="p5" style="display:none">
									<ul class="ui-form ">
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>1. 全体号码假设盈亏值均数 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberValueAvg2}" pattern="#,###.##"  minFractionDigits="2"/></span> 元</span>
										</li>
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>2. 截止目前总销售额(S) : <span class="color-red"><fmt:formatNumber value="${struc.totalSaleValue2}" pattern="#,###.##"  minFractionDigits="2"/></span></span>
										</li>
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>3. 理论利润率 : <span class="color-red"><fmt:formatNumber value="${struc.theoryProfitRate2}" pattern="#,###.##"  minFractionDigits="2"/>%</span></span>
										</li>
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>4. 全体号码假设盈亏值标准差 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberGrantProfitBZC2}" pattern="#,###.##"  minFractionDigits="2"/></span></span>
										</li>
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>5. 全体号码假设盈亏值变异系数 : <span class="color-red"><fmt:formatNumber value="${struc.totalNumberGrantProfitBYXS2}" pattern="#,###.##"  minFractionDigits="2"/></span> 倍</span>
										</li>
										<c:if test="${struc.currentGameResult2 !=  null}">
										<li>
											<label class="ui-label w-1" for=""></label>
											<span>6. 本期开奖号码 : <span class="color-green">${struc.currentGameResult2}, </span>盈亏 : 
											<c:if test="${struc.theoryProfitValue2>=0 }"><span class="color-green"></c:if>
												<c:if test="${struc.theoryProfitValue2<0 }"><span class="color-red"></c:if>
												<fmt:formatNumber value="${struc.theoryProfitValue2/10000.00}" pattern="#,###.##"  minFractionDigits="2"/>
												</span>
											</span>
										</li>
										</c:if>
									</ul>
									<table class="table table-info">
										<thead>
											<tr>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
												<th>号码</th>
												<th>盈亏值</th>
											</tr>
										</thead>
										<tbody>
										<c:forEach items="${struc.gameSharesStruc2 }" var="varlist">
										<tr>
											<c:forEach items="${varlist}" var="gameShares">
												<td>${gameShares.number}</td>
												
												<td><c:if test="${gameShares.profitLoss>=0 }"><span class="color-green"></c:if>
												<c:if test="${gameShares.profitLoss<0 }"><span class="color-red"></c:if>
												<fmt:formatNumber value="${gameShares.profitLoss/10000.00}" pattern="#,###.##"  minFractionDigits="2"/>
												</span></td>
											</c:forEach>
										</tr>
										</c:forEach>
										</tbody>
									</table>
								</div>

								
							</div>
						</div>
					</div>
				</div>
			</div>
<script type="text/javascript">
	//菜单样式加载
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(3)').attr("class","current"); 
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('#lockMenu').attr("class","current");
	
	$('#J-button-submit').click(function(){
		$('#J-search-form').submit();
	});
	function show(tag){
		if(tag=='p3'){
			$("#p3").show();
			$("#p5").hide();
			$("#p3Li").addClass("current");
			$("#p5Li").removeClass("current");
		}else{
			$("#p3").hide();
			$("#p5").show();
			$("#p5Li").addClass("current");
			$("#p3Li").removeClass("current");
		}
		
	}
	
	function changePlayType(e) {
		var playType = parseInt(e.value);
		// 玩法為"正特碼_一肖"顯示"生肖"的排序選項、隱藏"號碼"的排序選項
		if(playType == 1) {
			$('#sortType0').removeAttr('selected');
			$("#sortType0").hide();
			$('#sortType1').removeAttr('selected');
			$("#sortType2").show();
			$('#sortType2').attr('selected','selected');
		} else {
			$("#sortType0").show();
			$('#sortType0').attr('selected','selected');
			$('#sortType1').removeAttr('selected');
			$('#sortType2').removeAttr('selected');
			$("#sortType2").hide();
		}
	}
</script>	
	
<style>
	.ui-label {height:24px;}
	.ui-form li {margin:5px 0;}
</style>
</body>
