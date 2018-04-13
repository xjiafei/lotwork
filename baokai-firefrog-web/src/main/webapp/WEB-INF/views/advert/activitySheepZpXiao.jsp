<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="pg" uri="/tag-page"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<meta charset="UTF-8">
	<title>转盘活动数据</title>
	

	<style>
	.j-ui-tip-info {background:#FFFFE1;border:1px solid #CCC;font-size:12px;color:#F60;}
	.j-ui-tip-info .sj-l {display:none;}
	.clearfix {display:block;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	
	</script>

</head>
<body>
	


		<div class="col-main">
			<div class="col-crumbs">
				<div class="crumbs">
					<strong>当前位置：</strong>
					<a href="#">活动</a>
					&gt;
					<a href="#">2015春节活动</a>&gt;转盘活动数据
				</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">

						<div class="ui-tab">
							
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
							</div>
							
							<div class="ui-tab-content ui-tab-content-current">
								
								<ul class="ui-form">
									<li>
										<label class="ui-label w-1">&nbsp;</label>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivityZpLog">全部</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityZpLog?pageStatus=3">待审核（${unCheckNum}）</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityZpLog?pageStatus=4">已通过</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityZpLog?pageStatus=5">未通过</a></span>
									</li>
								</ul>
								
								<form action="queryActivityZpLog" id="J-form" method="post">
									<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
									<input type="hidden" name="pageStatus" id="pageStatus" value="${pageStatus}">
									<ul class="ui-search">
										<li>
											<label for="" class="ui-label">用户名：</label>
											<input type="text" value="${query.userName}" class="input w-3" name="userName">
										</li>
										<li>
											<label class="ui-label">来源：</label>
											<select class="ui-select" name="channel" id="channel">
												<option value="-1">全部</option>
												<option value="1">ios2.1</option>
												<option value="2">android2.1</option>
												<option value="3">web3.0</option>
												<option value="4">web4.0</option>
											</select>
											<input type="hidden" value="${query.channel}" class="input w-3" id="channelv">
										</li>
										<li>
											<label class="ui-label">操作状态：</label>
											<select class="ui-select" name="updateStatus" id="updateStatus">
												<option value="-1" selected="">全部</option>
												<option value="1">待发布</option>
											</select>
											<input type="hidden" value="${query.updateStatus}" class="input w-3" id="updateStatusv">
										</li>
										<li>
											<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
											<a id="J-download-submit" class="btn btn-important" href="${fromGame}/gameoa/exportActivityZpLog">导出报表<b class="btn-inner"></b></a>
										</li>
									</ul>
								</form>
								
								<table class="table table-info">
									<thead class="text-center">
										<tr>
											<th>用户名</th>
											<th>剩余次数</th>
											<th>累计充值金额</th>
											<th>累计中奖金额</th>
											<th>来源</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody class="text-center">
									<c:forEach items="${struc}" var="struc" varStatus="configIndex">
										<tr data-id="${struc.id}">
											<td><span data-username="${struc.userName}">${struc.userName}</span><c:if test="${struc.vip==1}"><img src="http://static.rfonlineedu.com:888/static/images/ucenter/safe/vip.jpg"></c:if></td>
											<td <c:if test="${struc.updateStatus==1}">class="color-red"</c:if>>
											<span data-type="increase" data-updatetimes="${struc.updateLastNum }" data-last="${struc.lastNum+struc.updateLastNum}" data-showtip="${struc.lastNum}" data-times="${struc.lastNum }">${struc.lastNum+struc.updateLastNum}</span></td>
											<td>${struc.allRecharge/10000}</td>
											<td>${struc.allAward/10000}</td>
											<td><c:if test="${struc.channel==1 }">ios2.1</c:if>
												<c:if test="${struc.channel==2 }">android2.1</c:if>
												<c:if test="${struc.channel==3 }">web3.0</c:if>
												<c:if test="${struc.channel==4 }">web4.0</c:if>
											</td>
											<td>
												<a data-action="detail" href="javascript:void(0);">详细</a>
												<permission:hasRight moduleName="MARKET_ACTIVITY_UPDATEACTIVITYZP">
												<c:if test="${struc.updateStatus!=1 || struc.updateStatus==null}"><a data-action="update" href="javascript:void(0);">更新</a></c:if>
												</permission:hasRight>
												
												<permission:hasRight moduleName="MARKET_ACTIVITY_PUBLISHACTIVITYZP">
												<c:if test="${struc.updateStatus==1 }">
												<a class="color-red" data-actionuser="${struc.updateName}" data-action="publish" href="javascript:void(0);" data-reason="${struc.updateReason }">发布</a>
												
												</c:if>
												</permission:hasRight>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/activitySheepZpXiao.js"></script>
								<!-- PAGER 分页 -->
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>

							</div>
								
						</div>



					</div>
				</div>
			</div>
		</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
</body>
