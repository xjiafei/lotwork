<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri ="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="permission" uri="/tag-permission"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"
	prefix="decorator"%>
<html>

	<head>
		<title>资金中心-充值相关配置</title>
	</head>

<body>
	<div class="col-main">
		<div class="col-crumbs">
			<div class="crumbs"><strong>当前位置：</strong>
				<a href="${currentContextPath}/fundAdmin/Rechargemange/">资金中心</a> &gt; <span id="titleName">充值相关配置</span></div>
		</div>
		<div class="col-content">
			<div class="col-main">
				<div class="main">
					<div class="ui-tab">
						<ul class="ui-tab-title ui-tab-title2 clearfix">
							<span><li><a href="/fundAdmin/Rechargemange/index?parma=bypass">充值分流设置</a></li></span>
							<span><li style="display:none;"></li></span>
							<span><li class="current">充值上下限配置</li></span>
							<span><li style="display:none;"></li></span>
							<span><li><a href="/fundAdmin/Rechargemange/index?parma=sv2&index=1">充值返送手续费配置</a></li></span>
							<span><li style="display:none;"></li></span>
						</ul>

						<div class="ui-tab tab-left">
							<div class="ui-tab-content ui-tab-content-current" id="DivRules">
								<div id="J-side-menu" class="ui-tab-title clearfix" style="display: inline;">
									<ul>
									
										<c:forEach var="name" items="${chargeNameList }" varStatus="i">
											<c:choose>
												<c:when test="${i.index == index}">
													<li class="current">
													<input type="hidden" id="indexs" name="indexs" value="${index }">
														<a href="/fundAdmin/Rechargemange/index?parma=sv2&index=0&type=${i.index}">
															${name }
														</a>
													</li>
												</c:when>
												<c:otherwise>
													<li>
														<a href="/fundAdmin/Rechargemange/index?parma=sv2&index=0&type=${i.index}">
															${name }
														</a>
													</li>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</ul>
								</div>
							</div>
						</div>

						<ul style="overflow-y: hidden;">

							<form  method="post" id="J-form1" name="J-form1">
								<table class="table table-info table-border">
									<thead>
										<tr>
										<th colspan="2" class="text-left">${chargeName}配置</th>
										</tr>
									</thead>
									<tbody>
										<tr>
											<td class="text-left w-4">充值限额设置</td>
											<td class="text-left">
												<dl class="set-list">
													<dt>普通用户</dt>
													<input type="hidden" id="chargeName" name="chargeName" value="${chargeName }">
													<c:forEach var="bank" items="${bankList}" varStatus="i">
													<input type="hidden"  name="code" value="${bank.code }">
													<input type="hidden"  name="version" value="${bank.version }">
													<c:choose>
														<c:when test="${(chargeName eq '快捷充值') or (chargeName eq '支付宝企业版') }">
															<dd>
																<span class="ui-info" style="width: 80px;">${bank.name }：</span>
																<span class="ui-text-info">最低：</span> 
																<input type="text" name="lowLimit" value="<fmt:parseNumber value="${bank.otherdownlimit div 10000}" integerOnly="true"/>" class="input w-1"> 
																<span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
																<span class="ui-text-info">最高：</span>
																<input type="text" name="upLimit" value="<fmt:parseNumber value="${bank.otheruplimit div 10000}" integerOnly="true"/>" class="input w-1"> 
																<span class="ui-info">元</span>
															</dd>
														
														</c:when>
														
														<c:otherwise>
														<dd>
															<span class="ui-info" style="width: 80px;">${bank.name }：</span>
															<span class="ui-text-info">最低：</span> 
															<input type="text" name="lowLimit" value="<fmt:parseNumber value="${bank.lowlimit div 10000}" integerOnly="true"/>" class="input w-1"> 
															<span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
															<span class="ui-text-info">最高：</span>
															<input type="text" name="upLimit" value="<fmt:parseNumber value="${bank.uplimit div 10000}" integerOnly="true"/>" class="input w-1"> 
															<span class="ui-info">元</span>
														</dd>
														</c:otherwise>
													</c:choose>
													</c:forEach>
													
													<dt>VIP客户</dt>
													
													<c:forEach var="bank" items="${bankList}" varStatus="i">
														<c:choose>
															<c:when test="${(chargeName eq '快捷充值') or (chargeName eq '支付宝企业版')  }">
																<dd>
																	<span class="ui-info" style="width: 80px;">${bank.name }：</span>
																	<span class="ui-text-info">最低：</span>
																	 <input type="text" name="vipLowLimit" value="<fmt:parseNumber value="${bank.othervipdownlimit div 10000}" integerOnly="true"/>  " class="input w-1"> 
																	 <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
																	<span class="ui-text-info">最高：</span> 
																	<input type="text" name="vipUpLimit" value="<fmt:parseNumber value="${bank.othervipuplimit div 10000}" integerOnly="true"/>  " class="input w-1">
																	 <span class="ui-info">元</span>
																</dd>
															</c:when>
															<c:otherwise>
																<dd>
																	<span class="ui-info" style="width: 80px;">${bank.name }：</span>
																	<span class="ui-text-info">最低：</span> <input type="text" name="vipLowLimit" value="<fmt:parseNumber value="${bank.viplowlimit div 10000}" integerOnly="true"/>  " class="input w-1"> <span class="ui-info">元</span>&nbsp;&nbsp;&nbsp;&nbsp;
																	<span class="ui-text-info">最高：</span> <input type="text" name="vipUpLimit" value="<fmt:parseNumber value="${bank.vipuplimit div 10000}" integerOnly="true"/>  " class="input w-1"> <span class="ui-info">元</span>
																</dd>
															</c:otherwise>
														</c:choose>
														
													</c:forEach>
												</dl>
											</td>

										</tr>
										<tr>
										
											<td class="text-left">充值倒计时设置</td>
											<td class="text-left">
											<span class="ui-info">显示倒计时分钟数：</span> 
											<c:forEach  items="${countDownMinute}"  var="countDown"> 
	    										 <c:if  test="${countDown.key eq chargeName}"> 
	         										<input type="text" name="charge_ratio" value="${countDown.value}" class="input w-1">
	      										 </c:if> 
											</c:forEach>
											
												<span class="ui-info">分钟</span>
											</td>
										</tr>
										<!-- {if "FUND_RECHARGE_CONFIG_UPTO_SUBMIT"|in_array:$smarty.session.datas.info.acls} -->
										<tr>
											<td></td>
											<td class="text-left ui-btn">
												<input id="saveBtn" type="button" class="btn btn-important" value="保 存">
												<input type="reset" class="btn  btn-important" value="撤销编辑">
												</a>
											</td>
										</tr>
										<!-- {/if} -->
									</tbody>
								</table>
							</form>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			var chargeName=$("#chargeName").val();
			var indexs=$("#indexs").val();
			$("#saveBtn").bind("click", function () {
		        $.ajax({
		        	url: "/fundAdmin/fundbankManage/savebankcfg?index="+indexs+"&type="+chargeName,
		            data: $('#J-form1').serialize(),
		            method: 'post',
		            success: function () {
		              window.location = "/fundAdmin/Rechargemange/index?parma=sv2&index=0&type="+indexs;
		            }
		        });
		    });
		})
	</script>
</body>

</html>