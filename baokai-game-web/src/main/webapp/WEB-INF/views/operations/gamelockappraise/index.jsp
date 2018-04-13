<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>变价  封锁数据</title>

</head>
<body>
<div id="tab_menu_id" style="display:none">changeMenu</div>
			<div class="col-crumbs"><div class="crumbs">
			<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>变价方案
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
									<c:if test="${lotteryId==99109}">
										<li class="current"><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${p3}">变价</a></li>
										<li><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${p3}">变价参数设定</a></li>
									</c:if>
									<c:if test="${lotteryId!=99109}">
										<li class="current"><a href="${currentContextPath}/gameoa/queryAllGameLockAppraise?lotteryid=${lotteryId}">变价</a></li>
										<li><a href="${currentContextPath}/gameoa/toGameLockParam?lotteryid=${lotteryId}">变价参数设定</a></li>
									</c:if>
								</ul>
								
								
								<h3 class="ui-title">
									<spring:message code="gameCenter_caizhongmingcheng" />：
									<select id="J-select" class="ui-select w-2">
									<c:if test="${lotteryId==99108 }"><option value="99108" selected="selected">3D</option></c:if>
									<c:if test="${lotteryId!=99108 }"><option value="99108">3D</option></c:if>
									<c:if test="${p3==99109 }"><option value="99109" selected="selected">排列3</option></c:if>	
									<c:if test="${p3!=99109 }"><option value="99109">排列3</option></c:if>
									<c:if test="${p3==99110 }"><option value="99110" selected="selected">排列5</option></c:if>	
									<c:if test="${p3!=99110 }"><option value="99110">排列5</option></c:if>
									</select>
									<c:if test="${lotteryId==99109}">
									<a href="${currentContextPath}/gameoa/toAddGameLockAppraise?lotteryid=${p3}" class="btn btn-small">+ 新增变价方案<b class="btn-inner"></b></a>
									</c:if>
									<c:if test="${lotteryId!=99109}">
									<a href="${currentContextPath}/gameoa/toAddGameLockAppraise?lotteryid=${lotteryId}" class="btn btn-small">+ 新增变价方案<b class="btn-inner"></b></a>
									</c:if>
								</h3>
								
								
								
								<div style="padding:10px;">
								
								<table id="J-table" class="table table-border">
									<tr>
										<th>序号</th>
										<th class="text-center">变价方案标题</th>
										<th>最后修改日期</th>
										<th><spring:message code="gameCenter_zhuangtai" /></th>
										<th>操作</th>
									</tr>
									
									<c:forEach items="${resultList}" var="gameLockAppraise" >
										<tr>
											<td><input type="checkbox" value="${gameLockAppraise.id}" /></td>
											<td>${gameLockAppraise.title}</td>
											<td>${gameLockAppraise.gmtModify}</td>
											<td><span class="color-red">
											<c:if test="${gameLockAppraise.status==1}">未审核</c:if>
											<c:if test="${gameLockAppraise.status==2}">已审核</c:if>
											<c:if test="${gameLockAppraise.status==4&&gameLockAppraise.curUser!=1}">已发布</c:if>
											<c:if test="${gameLockAppraise.status==4&&gameLockAppraise.curUser==1}">进行中</c:if>
											</span></td>
											<td>
												<c:if test="${gameLockAppraise.status==1}">
													<a data-id="${gameLockAppraise.id}" data-action="2" href="#">审核通过</a>
													<c:if test="${lotteryId==99109}">
													<a href="${currentContextPath}/gameoa/toEditGameLockAppraise?lotteryid=${p3}&id=${gameLockAppraise.id}"><spring:message code="gameCenter_xiugai" /></a>
													<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${p3}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
													<c:if test="${lotteryId!=99109}">
														<a href="${currentContextPath}/gameoa/toEditGameLockAppraise?lotteryid=${lotteryId}&id=${gameLockAppraise.id}"><spring:message code="gameCenter_xiugai" /></a>
														<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${lotteryId}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
												</c:if>
												<c:if test="${gameLockAppraise.status==2}">
												<c:if test="${notUser}">
													<a data-id="${gameLockAppraise.id}" data-action="4" href="#">发布</a>
													</c:if>
													<c:if test="${lotteryId==99109}">
													<a href="${currentContextPath}/gameoa/toEditGameLockAppraise?lotteryid=${p3}&id=${gameLockAppraise.id}"><spring:message code="gameCenter_xiugai" /></a>
													<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${p3}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
													<c:if test="${lotteryId!=99109}">
													<a href="${currentContextPath}/gameoa/toEditGameLockAppraise?lotteryid=${lotteryId}&id=${gameLockAppraise.id}"><spring:message code="gameCenter_xiugai" /></a>
													<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${lotteryId}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
												</c:if>
												<c:if test="${gameLockAppraise.status==4}">
													<a data-id="${gameLockAppraise.id}" data-action="2" href="#">取消发布</a>
													<c:if test="${lotteryId==99109}">
													<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${p3}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
													<c:if test="${lotteryId!=99109}">
													<a href="${currentContextPath}/gameoa/viewGameLockAppraise?lotteryid=${lotteryId}&id=${gameLockAppraise.id}">查看</a>
													</c:if>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</table>
								<form action="<%=request.getContextPath() %>/gameoa/deleteGameLockAppraise" id="J-form" method="post">
								<input type="hidden" name="ids" id="ids" value="">
								<c:if test="${lotteryId==99109}">
								<input type="hidden" name="lotteryid" id="lotteryid" value="${p3}">
								</c:if>
								<c:if test="${lotteryId!=99109}">
								<input type="hidden" name="lotteryid" id="lotteryid" value="${lotteryId}">
								</c:if>
								</form>
								<div class="page-wrapper clearfix" style="padding:10px">
									<span class="page-text">
										<label class="label" for="J-select-all"><input id="J-select-all" type="checkbox" class="checkbox">全选</label>
										<a class="btn btn-small" href="#" id="J-delete-all">删除<b class="btn-inner"></b></a>
									</span>
								</div>
								
								
								</div>
								
																
								
						
								
								
							</div>
						</div>
					</div>
				</div>
			</div>

	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	</script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gamelockappraise/index.js"></script>
</body>