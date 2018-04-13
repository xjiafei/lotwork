<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title><spring:message code="gameCenter_jiangqiliuchengguanli" />-<spring:message code="gameCenter_yichangliuchengjilu" /></title>
	
</head>
<body>
	<div class="col-main">
			<div class="col-crumbs">
			<div class="crumbs">
			<strong>当前位置：</strong><a href="#">游戏中心</a>><a href="#"><spring:message code="gameCenter_jiangqiliuchengguanli" /></a>&gt;<spring:message code="gameCenter_yichangliuchengjilu" />
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					
					<form action="queryLotteryIssueWarnLog" id="J-form" method="post">
						<ul class="ui-search">
							<li>
								<label class="ui-label"><spring:message code="gameCenter_caizhongmingcheng" />：</label>
								<select class="ui-select" id="selectLottery">
									<option value="0"><spring:message code="gameCenter_quanbucaizhong" /></option>
									<option value="99101">重庆时时彩</option>
									<option value="99106">宝开时时彩</option>
									<option value="99102">江西时时彩</option>
									<option value="99103">新疆时时彩</option>
									<option value="99104">天津时时彩</option>
									<option value="99105">黑龙江时时彩</option>
									<option value="99107">上海时时乐</option>
									<option value="99301">山东11选5</option>
									<option value="99302">江西11选5</option>
									<option value="99307">江苏11选5</option>
									<option value="99303">广东11选5</option>
									<option value="99304">重庆11选5</option>
									<option value="99999">宝开11选5</option>
									<option value="99306">秒开11选5</option>
									<option value="99201">北京快乐8</option>
									<option value="99108">3D</option>
									<option value="99109">排列5</option>
									<option value="99401">双色球</option>
									<option value="99701">六合彩</option>									
									<option value="99111">宝开1分彩</option>
									<option value="99114">腾讯分分彩</option>
									<option value="99112">秒开时时彩</option>
								<!-- 	<option value="99113">超级2000秒秒彩（APP版）</option> -->
									<option value="99501">江苏快三</option>
									<option value="99502">安徽快三</option>
									<option value="99601">江苏骰宝</option>
									<option value="99602">高频骰宝(娱乐厅)</option>
									<option value="99603">高频骰宝(至尊厅)</option>
								</select>
							</li>
							<input type="hidden" id="lotteryid" name="lotteryid" value="${lotteryId }"/>
							<input type="hidden" id="warnType" name="warnType" value="${warnType }"/>
							<input type="hidden" id="dateType" name="dateType" value="${dateType }"/>
							<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
							<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
							
							
							<li class="search-time time">
								<label for="" class="ui-label"><spring:message code="gameCenter_tijiaoshijian" />：</label>
								<a href="#" name="1" id="dates"><spring:message code="gameCenter_jintian" /></a>
								<a href="#" name="3" id="dates"><spring:message code="gameCenter_santian" /></a>
								<a href="#" name="7" id="dates"><spring:message code="gameCenter_yizhou" /></a>
								<a href="#" name="0" id="dates"><spring:message code="gameCenter_quanbu" /></a>
							</li>
							
							
							<li>
								<label class="ui-label"><spring:message code="gameCenter_yichangcaozuoxiangmingcheng" />：</label>
								<select class="ui-select" id="selectWarnType" style="width: auto;">
									<option value="0" selected=""><spring:message code="gameCenter_quanbu" /></option>
									<option value="1">官方提前开奖x秒（开奖已暂缓）</option>
									<option value="2">官方提前开奖y秒（已处理）</option>
									<option value="3">官方重新开奖</option>
									<option value="4">官方未开奖</option>
									<option value="5">暂缓派奖</option>
									<option value="6">继续派奖</option>
									<option value="7"><spring:message code="gameCenter_shuruguanfangshijikaijianghaoma" /></option>
									<option value="8">批量<spring:message code="gameCenter_chexiaobenqifangan" />操作</option>
									<option value="9">批量撤销追号方案操作</option>
								</select>
							</li>
							<li><a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a></li>
						</ul>
						
					</form>
						
					<div style="overflow-x: auto;">
						<table class="table table-info" id="J-data-table">
							<thead>
								<tr>
									<th><spring:message code="gameCenter_caizhongmingcheng" /></th>
									<th><spring:message code="gameCenter_qihao" /></th>
									<th><spring:message code="gameCenter_tijiaoshijian" /></th>
									<th><spring:message code="gameCenter_yichangqingkuangmiaoshu" /></th>
									<th><spring:message code="gameCenter_yichangcaozuoxiangmingcheng" /></th>
									<th><spring:message code="gameCenter_caozuoneirong" /></th>
									<th>操作人</th>
									<th><spring:message code="gameCenter_zhuangtai" /></th>
									<th>备注</th>
									<th>处理内容说明</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${logList}" var="logs" varStatus="logStatus">
									<tr>
									<td>${logs.lotteryName }</td>
									<td>${logs.webIssueCode}</td>
									<td>${logs.createTime }</td>
									<td>${logs.warnDes }</td>
									<td>${logs.warnTypeName }</td>
									<td>${logs.disposeInfo }</td>
									<td>${logs.disposeUser }</td>
									<td>${logs.status }</td>
									<td>${logs.disposeMemo }</td>
									<td>${logs.doingMemo }</td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
                    </div>
						<div class="page-wrapper clearfix">
							<!--<jsp:include page="../../userCenter/page.jsp"></jsp:include>-->
							<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						</div>
						
					</div>
				</div>
			</div>
		</div>
		<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	
	.pop-window-datepicker {z-index:710;}
	
	.search-time a {display:inline-block;border:1px solid #CCC;padding:5px 10px;color:#999;}
	.search-time a:hover { text-decoration:none;}
	.search-time a.current {border-color:#08AE8E;font-weight:bold;color:#08AE8E}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
		<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/issueMonitor/issueMonitorLog.js"></script>
</body>