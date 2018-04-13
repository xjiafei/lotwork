<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
<title>奖金组（列表页）</title>
	
</head>
<body>
<div id="tab_menu_id" style="display:none">awardGroupMenu</div>
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>><spring:message code="gameCenter_jiangjinzu"/>
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
								<input type="hidden" id="lotteryId" value="${lotteryId}"/>
								<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/queryGameAwardGroupList?status=&awardId=&lotteryid=">
							</div>
							<div class=" ">
								<h3 class="ui-title">

								<spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include></h3>
								<table class="table table-info table-group">
									<thead>
										<tr>
											<th>奖金组名称</th>
											<th><spring:message code="gameCenter_zhuangtai" /></th>
											<th>创建时间</th>
											<th><spring:message code="gameCenter_zuijinxiugaishijian" /></th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${gameAwardGroupList}" var="gameAwardGroup" >
											<tr>
												<td>${gameAwardGroup.awardName }</td>
												<c:if test="${gameAwardGroup.status==1}"><td>进行中</td></c:if>
												<c:if test="${gameAwardGroup.status==2}"><td class="color-red">已删除</td></c:if>
												<c:if test="${gameAwardGroup.status==3}"><td>待审核</td></c:if>
												<c:if test="${gameAwardGroup.status==4}"><td>待发布</td></c:if>
												<c:if test="${gameAwardGroup.status==5}"><td><spring:message code="gameCenter_shenhebutongguo" /></td></c:if>
												<c:if test="${gameAwardGroup.status==6}"><td>发布不通过</td></c:if>
												<td>${gameAwardGroup.createTime }</td>
												<td>${gameAwardGroup.updateTime }</td>
												<td>
													<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP_VIEW">
													<a href="${currentContextPath}/gameoa/queryGameAward?lotteryId=${gameAwardGroup.lotteryId}&awardId=${gameAwardGroup.awardGroupId}&status=${gameAwardGroup.status}">查看</a>
													</permission:hasRight>
													<!--  
													<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP_EDIT">
													<c:if test="${gameAwardGroup.status!=3 && gameAwardGroup.status!=4}">
	                                                <a href="${currentContextPath}/gameoa/preEditGameAwardGroup?lotteryId=${gameAwardGroup.lotteryId}&awardId=${gameAwardGroup.awardGroupId}&status=${gameAwardGroup.status}"><spring:message code="gameCenter_xiugai" /></a>
	                                                </c:if>
	                                                </permission:hasRight>
	                                                <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP_PUBLISH">
	                                                <c:if test="${gameAwardGroup.status==4}">
													<a href="javascript:void(0);" id="J-Publish" name="${gameAwardGroup.lotteryId}_${gameAwardGroup.awardGroupId}" >发布</a>
													<a href="javascript:void(0);" id="J-Publish2" name="${gameAwardGroup.lotteryId}_${gameAwardGroup.awardGroupId}" >发布不通过</a>
													</c:if>
													</permission:hasRight>
													<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP_AUDIT">
													<c:if test="${gameAwardGroup.status==3}">
													<a href="${currentContextPath}/gameoa/preAuditGameAwardGroup?lotteryId=${gameAwardGroup.lotteryId}&awardId=${gameAwardGroup.awardGroupId}&status=${gameAwardGroup.status}">审核</a>
													</c:if>
													</permission:hasRight>
													<permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_AWARDGROUP_DEL">
													<%--<c:if test="${gameAwardGroup.status>=3}">
													<a href="javascript:void(0);" id="J-Delete1" name="${gameAwardGroup.lotteryId}_${gameAwardGroup.awardGroupId}">删除</a>
													</c:if>--%>
													</permission:hasRight>
													-->
												</td>
											</tr>
										</c:forEach>
										
									</tbody>
								</table>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		 <textarea id="DeleteGroup" style="display:none;">        
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">你确定要删除当前奖金组吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Delete-Butt1"><spring:message code="gameCenter_shanchu" /><b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDd">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>  
    
    <textarea id="DivPublish" style="display:none;">     
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">新建奖金组发布后，奖金不可再修改。该奖金组将适用于所有总代。<br />您确定发布该奖金组吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Submit-But1">确 定<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs">取 消<b class="btn-inner"></b></a>
            </div>
        </div>
    </textarea>
    
    <textarea id="DivPublish2" style="display:none;">     
        <div class="bd">
            <div class="pop-title">
                <i class="ico-waring"></i>
                <h4 class="pop-text">您确定发布不通过该奖金组吗？</h4>
            </div>
            <div class="pop-btn">
                <a href="javascript:void(0);" class="btn btn-important " id="J-Submit-But2">确 定<b class="btn-inner"></b></a>
                <a href="javascript:void(0);" class="btn" id="closeDs2">取 消<b class="btn-inner"></b></a>
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
                <a href="javascript:void(0);" class="btn">关 闭<b class="btn-inner"></b></a>
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
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/gameAward/gameAwardIndex.js"></script>
</body>