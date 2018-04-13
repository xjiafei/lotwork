<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<head>
	<title><spring:message code="gameCenter_canshushezhi" /></title>
<script>
		 function SBlimitSet(){
			var sbvalue = $('#smlimitrate').val();
        	var sbcount = $('#slipcount').val();
            jQuery.ajax({
				type: "POST",
				url: '${currentContextPath}/gameoa/modifySBlimitConfig',
				data : {sbvalue:sbvalue,sbcount:sbcount},
				dataType :"json",
				async: false,
				success: function(data){
					location.reload();
				},
				complete:function(){
					
				},
				error: function(xhr,status,errMsg){
					alert("错误！请洽系统管理者");
					return false;
				}
			});
         }
	</script>
</head>
<body>
    <div id="tab_menu_id" style="display:none">configMenu</div>
	<div class="col-crumbs">
	<div class="crumbs">
		<strong>当前位置：</strong><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status=">游戏中心</a>><a href="${currentContextPath}/gameoa/lotteryList?lotteryId=&status="><spring:message code="gameCenter_caizhongxinxiliebiao" /></a>>
		<c:choose>
			<c:when test="${pageType=='modify' }">修改参数设置</c:when>
			<c:when test="${pageType=='audit' }">审核参数设置</c:when>
			<c:when test="${pageType=='auditDetail' }">审核参数设置</c:when>
            <c:otherwise><spring:message code="gameCenter_canshushezhi" /></c:otherwise>
		</c:choose>
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
							
							<div class="ui-tab-content clearfix">
								<h3 class="ui-title">
                                    <c:if test="${status != 3 && status != 4 && pageType != 'modify'}">
                                   	    <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_CONFIG_EDIT"> 
                                            <a class="btn btn-small" href="${currentContextPath}/gameoa/toModifySeriesConfig?lotteryid=${lotteryId }"><spring:message code="gameCenter_xiugai" /><b class="btn-inner"></b></a>
                                        </permission:hasRight>
                                    </c:if>
                                    <input type="hidden" id="lotteryId" value="${lotteryId}"/>
									<input type="hidden" id="url" value="<%=request.getContextPath() %>/gameoa/toSeriesConfig?lotteryid=">
									<spring:message code="gameCenter_caizhongmingcheng" />：<jsp:include page="../lotteryListSelect.jsp"></jsp:include>
									<span class="info"><spring:message code="gameCenter_zhuangtai" />：
										<c:choose>
											<c:when test="${status==1}"><span class="color-green">进行中</span></c:when>
											<c:when test="${status==2}"><span class="color-red">已删除</span></c:when>
											<c:when test="${status==3 }"><span class="color-red">待审核</span>&nbsp;<c:if test="${!empty modifier}">修改人：${modifier}</c:if></c:when>
											<c:when test="${status==4 }"><span class="color-red">待发布</span>&nbsp;<c:if test="${!empty modifier}">修改人：${modifier}</c:if>
                                    <c:if test="${!empty checker}">审核人：${checker}</c:if></c:when>
											<c:when test="${status==5 }"><span class="color-red">审核未通过</span></c:when>
											<c:when test="${status==6}"><span class="color-red">发布未通过</span></c:when>
											<c:otherwise><span class="color-green">进行中</span></c:otherwise>
										</c:choose>
									</span>
                                   
								</h3>
								<permission:hasRight moduleName="GAME_SB_LIMIT"> 
									
										<c:if test="${!empty sblimit}">
											<h3 class="ui-title">骰宝相关限制</h3>
											<ul class="ui-form">
												<li>
													<label for="" class="ui-label w-4 ">数量限制：</label> 
													<input id="slipcount" type="number" maxlength='4' value="${slipcount}" class="input input-info w-2">
													<label for="" class="ui-label w-4 ">门槛限制：</label> 
													<input id="smlimitrate" type="number" maxlength='4' value="${sblimit}" class="input input-info w-2">
													<span class="ui-info">%</span>
													<a href="#" onclick="SBlimitSet();" class="btn btn-important">保存修改<b class="btn-inner"></b></a>
												</li>
											</ul>
										</c:if>
									 </permission:hasRight> 
								<form action="${currentContextPath}/gameoa/modifySeriesConfig" id="modifyForm" name="modifyForm" method="post">
									<input type="hidden" id="lotteryid" name="lotteryid" value="${lotteryId }"/>
									<input id="modifyType" type="hidden" value="${modify}">
									
									<%-- <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_CONFIG_BET"> --%>
									<h3 class="ui-title">投注相关参数</h3>
									<ul class="ui-form">
										<li>
											<label for="" class="ui-label w-4 ">撤单起始金额：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" maxlength='6'  name="backoutStartFee" value="<fmt:formatNumber value='${seriesConfig.backoutStartFee/10000 }' pattern='#' type='number'/>" class="input w-2 input-money"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.backoutStartFee_bak != null }">
															<div class="input w-2 input-mark" data-showtip="${seriesConfig.backoutStartFee_bak/10000.00 }">${seriesConfig.backoutStartFee/10000.00 }</div>
														</c:when>
														<c:otherwise><input type="text" disabled="disabled" maxlength='6' value="${seriesConfig.backoutStartFee/10000.00 }" class="input input-info w-2"></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" disabled="disabled" maxlength='6' value="<fmt:formatNumber value='${seriesConfig.backoutStartFee/10000.00 }' pattern='###,###,###.00' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">元</span>
										</li>
										<li>
											<label for="" class="ui-label w-4">撤单手续费比例：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" name="backoutRatio" maxlength='6' value="<fmt:formatNumber value='${seriesConfig.backoutRatio/100.00 }' pattern='#' type='number'/>" class="input w-2 input-number"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.backoutRatio_bak != null }">
															<div class="input w-2 input-mark" data-showtip="<fmt:formatNumber value='${seriesConfig.backoutRatio_bak/100.00 }' pattern='#' type='number'/>"><fmt:formatNumber value='${seriesConfig.backoutRatio/100.00 }' pattern='#' type='number'/></div>
														</c:when>
														<c:otherwise><input type="text" disabled="disabled" maxlength='6' value="<fmt:formatNumber value='${seriesConfig.backoutRatio/100.00 }' pattern='#' type='number'/>" class="input input-info w-2"></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" disabled="disabled" maxlength='6' value="<fmt:formatNumber value='${seriesConfig.backoutRatio/100.00 }' pattern='#' type='number'/>" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">%</span>
										</li>
									</ul>
									<%-- </permission:hasRight> --%>
									
									<%-- <permission:hasRight moduleName="GAME_LOTMGR_LOTLIST_CONFIG_ISSUE"> --%>
									<h3 class="ui-title">用户/方案处理相关参数</h3>
									<ul class="ui-form">
										<li>
											<label for="" class="ui-label w-4">管理员在开奖后：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" maxlength='4' name="issuewarnBackoutTime" value="${seriesConfig.issuewarnBackoutTime }" class="input w-2 numa-in input-number"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.issuewarnBackoutTime_bak != null }">
															<div class="input w-2 input-mark" data-showtip="${seriesConfig.issuewarnBackoutTime_bak }">${seriesConfig.issuewarnBackoutTime }</div>
														</c:when>
														<c:otherwise><input  maxlength='4' type="text" disabled="disabled" value="${seriesConfig.issuewarnBackoutTime }" class="input input-info w-2"></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" maxlength='4'  disabled="disabled" value="${seriesConfig.issuewarnBackoutTime }" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">分钟内（含<em style="font-style:none" class="numa-out">${seriesConfig.issuewarnBackoutTime }</em>分钟）可进行单笔撤单</span>
										</li>
										
										<li>
											<label for="" class="ui-label w-4">用户在截止销售前：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" maxlength='4' name="issuewarnUserBackoutTime" value="${seriesConfig.issuewarnUserBackoutTime }" class="input w-2 numc-in input-number"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.issuewarnUserBackoutTime_bak != null }">
															<div class="input w-2 input-mark" data-showtip="${seriesConfig.issuewarnUserBackoutTime_bak }">${seriesConfig.issuewarnUserBackoutTime }</div>
														</c:when>
														<c:otherwise><input  maxlength='4' type="text" disabled="disabled" value="${seriesConfig.issuewarnUserBackoutTime }" class="input input-info w-2"></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" maxlength='4'  disabled="disabled" value="${seriesConfig.issuewarnUserBackoutTime }" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">秒内（含<em style="font-style:none" class="numc-out">${seriesConfig.issuewarnUserBackoutTime }</em>秒）禁止进行撤销方案操作</span>
										</li>
										
										<li>
											<label for="" class="ui-label w-4">管理员在开奖后：</label>
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" maxlength='4'  name="issuewarnExceptionTime" value="${seriesConfig.issuewarnExceptionTime }" class="input w-2 numb-in input-number"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.issuewarnExceptionTime_bak != null }">
															<div class="input w-2 input-mark" data-showtip="${seriesConfig.issuewarnExceptionTime_bak }">${seriesConfig.issuewarnExceptionTime }</div>
														</c:when>
														<c:otherwise><input type="text" maxlength='4'  disabled="disabled" value="${seriesConfig.issuewarnExceptionTime }" class="input input-info w-2"></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" disabled="disabled" maxlength='4'  value="${seriesConfig.issuewarnExceptionTime }" class="input input-info w-2"></c:otherwise>
											</c:choose>
											<span class="ui-info">分钟内（含<em style="font-style:none" class="numb-out">${seriesConfig.issuewarnExceptionTime }</em>分钟）可进行流程异常处理</span>
											<span class="ui-prompt">（超过该时间范围后，该奖期不可再做任何后台操作）</span>
										</li>
										</ul>
										
										
										<c:if test="${lotteryId==99305||lotteryId==99106}">
										<h3 class="ui-title">视频管理</h3>
									
										    <c:choose>
										        <c:when test="${empty pageType}">
										       <ul class="ui-form">
											<li>
												
												<label for="" class="ui-label w-4">视频链接：	</label>
												<span class="ui-multiline">
													<table id="J-video-control" class="table table-border table-function">
														<thead>
															<tr>
																<th>视频位置</th>
																<th>链接</th>
																<th>状态</th>
																<th>操作</th>
															</tr>
														</thead>
														<tbody>
													    <c:if test="${empty videoStrucs}">
													    <tr>
													    <td></td><td></td><td></td>
													    <td><a data-id="3" data-type="add" class="add" href="#">新增</a></td>
													    </tr>
													    </c:if>
														<c:forEach var="videoStruc" items="${videoStrucs}" varStatus="status">
															<tr>
																<td class="name">${videoStruc.name}</td>
																<td class="url"><div style="white-space: normal; word-wrap: break-word; width: 340px;">${videoStruc.url}</div></td>
																<td>
																<c:choose>
																 <c:when test="${videoStruc.status==1}">开启</c:when>
																 <c:otherwise>关闭</c:otherwise>
																</c:choose>
																</td>
																<td>
																	<a data-id="${videoStruc.id}" data-type="edit" class="edit" href="#">修改</a>
																	<a data-id="${videoStruc.id}" data-type="remove" class="remove" href="#">删除</a>
																	<c:if test="${status.last}"><a data-id="3" data-type="add" class="add" href="#">新增</a></c:if>
																</td>
															</tr>
															
															</c:forEach>
														</tbody>
													</table>
												</span>
											</li>
										</ul>
										        </c:when>
										        <c:otherwise>
										        
										
										 <ul class="ui-form">
											<li>
												<label for="" class="ui-label w-4">视频链接：</label>
												<span class="ui-multiline">
													<table id="J-video-control" class="table table-border table-function">
														<thead>
															<tr>
																<th>视频位置</th>
																<th>链接</th>
																<th>状态</th>
															</tr>
														</thead>
														<tbody>
																<c:forEach var="videoStruc" items="${videoStrucs}" varStatus="status">
															<tr>
																<td class="name">${videoStruc.name}</td>
																<td class="url"><div style="white-space: normal; word-wrap: break-word; width: 340px;">${videoStruc.url}</div></td>
																<td>
																<c:choose>
																 <c:when test="${videoStruc.status==1}">开启</c:when>
																 <c:otherwise>关闭</c:otherwise>
																</c:choose>
																</td>
															</tr>
															
															</c:forEach>
														</tbody>
													</table>
												</span>
											</li>
										</ul>
										        </c:otherwise>
										    </c:choose>
										</c:if>
										
										<c:if test="${lotteryId==99401}">
										<h3 class="ui-title">方案邮件发送相关配置参数</h3>
										<ul class="ui-form">
										<li>
										 <label style="margin-left: 100px">当前有效邮箱地址（<span class="color-red">添加时，请用逗号进行分隔，支持中英文逗号格式</span>）</label>
										</li>
										<li>
										<label style="margin-left: 100px">
											<c:choose>
												<c:when test="${pageType=='modify' }"><input type="text" id="email" name="email" class="input" style="width:900px" value="${seriesConfig.email }"></c:when>
												<c:when test="${pageType=='auditDetail' ||status==4}">
													<c:choose>
														<c:when test="${seriesConfig.email_bak != null }">
															<div class="input input-mark" data-showtip="${seriesConfig.email_bak }">${seriesConfig.email }</div>
														</c:when>
														<c:otherwise><input type="text" disabled="disabled" class="input" style="width:900px" value="${seriesConfig.email }" ></c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise><input type="text" disabled="disabled" class="input"  style="width:900px" value="${seriesConfig.email }"></c:otherwise>
											</c:choose>
										</label>
										</li>
										  </ul>
										</c:if>
										<c:choose>
											<c:when test="${pageType=='modify' }">
												<li>
													<label for="" class="ui-label w-4"></label>
													<a href="javascript:void(0);" id="J-button-modify" class="btn btn-important">保存修改<b class="btn-inner"></b></a>
												</li>
											</c:when>
											<c:when test="${pageType=='auditDetail' }">
												<li>
													<label for="" class="ui-label w-4"></label>
													<a href="javascript:void(0);" id="J-button-audit" class="btn btn-important">审核通过<b class="btn-inner"></b></a>
													<a href="javascript:void(0);" id="J-button-audit2" class="btn btn-important"><spring:message code="gameCenter_shenhebutongguo" /><b class="btn-inner"></b></a>
												</li>
											</c:when>
                                            <c:when test="${pageType=='released' }">
                                                <li>
                                                    <label for="" class="ui-label w-4"></label>
                                                    <a href="javascript:void(0);" id="J-button-publish" class="btn btn-important">发布<b class="btn-inner"></b></a>
                                                    <a href="javascript:void(0);" id="J-button-publish2" class="btn btn-important">发布不通过<b class="btn-inner"></b></a>
                                                </li>
                                            </c:when>
											<c:otherwise></c:otherwise>
										</c:choose>
										
					
							<%-- </permission:hasRight> --%>
								</form>
							</div>
							
						</div>
						
					</div>
				</div>
			</div>
			
	<div  id="DivSuccessful"   class="pop pop-success w-4" style="position:absolute;z-index:2; display:none" >
    <i class="ico-success"></i>
    <h4 class="pop-text">操作成功</h4>
    </div>
	<script type="text/javascript">
		var currentContextPath = '${currentContextPath}';
	</script>
	<c:choose>
	  <c:when test="${lotteryId==99305||lotteryId==99106}">
	   <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/configJS/llvideo.js"></script>
	  </c:when>
	  <c:otherwise>
	   <script type="text/javascript" src="${staticFileContextPath}/static/js/operations/configJS/seriesConfig.js"></script>
	  </c:otherwise>
	</c:choose>
   
</body>
