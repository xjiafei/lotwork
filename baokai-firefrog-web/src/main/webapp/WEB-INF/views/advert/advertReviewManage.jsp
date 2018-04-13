<%@ page language="java" contentType="text/html; charset=UTF-8" import="com.winterframework.modules.page.Page"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en-US" class="html-content">
<head>
	<meta charset="UTF-8">
	<title>广告管理(审核)</title>
	
	<style>
	.table-group .table-toggle a {color:#009B7D; text-decoration:underline;}
	.j-ui-tip {border:1px solid #CCC;padding:10px;}
	.j-ui-tip .sj {display:none;}
	.row-show {position:relative;}
	.row-show-pass {position:relative;}
	.row-show-panel {display:none;position:absolute;background:#FFFFE1;border:1px solid #CCC;padding:10px;}
	.row-show-panel-current {display:block;white-space:normal;}
	.table a {text-decoration:underline;}
	</style>	
</head>
<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 广告管理（审核）
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form action="${currentContextPath }/adAdmin/queryReviewAdPage" id="J-form" method="post">
						<ul class="ui-search">
							<li>
								<label class="ui-label" for="">广告名称：</label>
								<input type="text" class="input w-3"  value="${adQueryRequest.name }" name="name">
							</li>
							<li>
								<label class="ui-label">投放位置：</label>
								<select class="ui-select w-5" name="spaceId" id="spaceId">
									<option value="-1" selected="selected">全部</option>
									<c:forEach items="${spaces }" var="space" varStatus="status">
									<option value="${space.id }">${space.pageIdStr }(${space.name }-${space.width }*${space.height })</option>
									</c:forEach>
								</select>
							</li>
							<li>
								<label class="ui-label">有效期：</label>
								<select class="ui-select w-2" name="type" id="type">
									<c:if test="${adQueryRequest.type == null }">
									<option value="-1" selected="selected">全部</option>
									</c:if>
									<c:if test="${adQueryRequest.type != null }">
									<option value="-1">全部</option>
									</c:if>
									<c:if test="${adQueryRequest.type == 1 }">
									<option value="1" selected="selected">进行中</option>
									</c:if>
									<c:if test="${adQueryRequest.type != 1 }">
									<option value="1">进行中</option>
									</c:if>
									<c:if test="${adQueryRequest.type == 2 }">
									<option value="2" selected="selected">未开始</option>
									</c:if>
									<c:if test="${adQueryRequest.type != 2 }">
									<option value="2">未开始</option>
									</c:if>
									<c:if test="${adQueryRequest.type == 3 }">
									<option value="3" selected="selected">已过期</option>
									</c:if>
									<c:if test="${adQueryRequest.type != 3 }">
									<option value="3">已过期</option>
									</c:if>
								</select>
							</li>
							<li class="checkbox-list">
								<label class="ui-label">展示用户组：</label>
								<label class="label" for="J-checkbox-1">
								<c:if test="${adQueryRequest.rcTopAgent==1 }">
								<input id="J-checkbox-1" type="checkbox" class="checkbox" value="1" name="rcTopAgent" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcTopAgent!=1 }">
								<input id="J-checkbox-1" type="checkbox" class="checkbox" value="1" name="rcTopAgent">
								</c:if>
								总代理</label>
								<label class="label" for="J-checkbox-2">
								<c:if test="${adQueryRequest.rcOtAgent==1 }">
								<input id="J-checkbox-2" type="checkbox" class="checkbox" value="1" name="rcOtAgent" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcOtAgent!=1 }">
								<input id="J-checkbox-2" type="checkbox" class="checkbox" value="1" name="rcOtAgent">
								</c:if>
								其他代理</label>
								<label class="label" for="J-checkbox-3">
								<c:if test="${adQueryRequest.rcCustomer==1 }">
								<input id="J-checkbox-3" type="checkbox" class="checkbox" value="1" name="rcCustomer" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcCustomer!=1 }">
								<input id="J-checkbox-3" type="checkbox" class="checkbox" value="1" name="rcCustomer">
								</c:if>
								玩家</label>
								<label class="label" for="J-checkbox-4">
								<c:if test="${adQueryRequest.rcVip==1 }">
								<input id="J-checkbox-4" type="checkbox" class="checkbox" value="1" name="rcVip" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcVip!=1 }">
								<input id="J-checkbox-4" type="checkbox" class="checkbox" value="1" name="rcVip">
								</c:if>
								VIP</label>
								<label class="label" for="J-checkbox--1">
								<c:if test="${adQueryRequest.rcNonVip==1 }">
								<input id="J-checkbox-4" type="checkbox" class="checkbox" value="1" name="rcNonVip" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcNonVip!=1 }">
								<input id="J-checkbox-4" type="checkbox" class="checkbox" value="1" name="rcNonVip">
								</c:if>
								非VIP</label>
								<label class="label" for="J-checkbox-5">
								<c:if test="${adQueryRequest.rcGuest==1 }">
								<input id="J-checkbox-5" type="checkbox" class="checkbox" value="1" name="rcGuest" checked="checked">
								</c:if>
								<c:if test="${adQueryRequest.rcGuest!=1 }">
								<input id="J-checkbox-5" type="checkbox" class="checkbox" value="1" name="rcGuest">
								</c:if>
								游客</label>
							</li>
							<li><input type="hidden" name="pageNo" id="pageForm" value="${page.pageNo}"> 
								<input type="hidden" name="status" id="status" value="${adQueryRequest.status }">
								<input type="hidden" name="orderBySpaces" id="orderBySpaces" value="${adQueryRequest.orderBySpaces }">
								<input type="hidden" name="orderById" id="orderById" value="${adQueryRequest.orderById }">
							<a id="J-button-search" href="javascript:void(0);" class="btn btn-important" onclick="query(0)">搜 索<b class="btn-inner"></b></a></li>
						</ul>
						</form>
						<input type="hidden" id="space_id" value="${adQueryRequest.spaceId }">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<c:if test="${adQueryRequest.status == null }">
									<li class="current"><a href="javascript:void(0);" id="-1">全部</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status != null }">
									<li><a href="javascript:void(0);" id="-1">全部</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status == 1 }">
									<li class="current"><a href="javascript:void(0);" id="1">审核中(${sumReviewing })</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status != 1 }">
									<li><a href="javascript:void(0);" id="1">审核中(${sumReviewing })</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status == 2 }">
									<li class="current"><a href="javascript:void(0);" id="2">已通过</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status != 2 }">
									<li><a href="javascript:void(0);" id="2">已通过</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status == 3 }">
									<li class="current"><a href="javascript:void(0);" id="3">未通过(${sumNotPass })</a></li>
									</c:if>
									<c:if test="${adQueryRequest.status != 3 }">
									<li><a href="javascript:void(0);" id="3">未通过(${sumNotPass })</a></li>
									</c:if>
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
								<table class="table table-info table-group" id="J-table">
									<thead>
										<tr>
											<th class="table-toggle">编号</th>
											<th>缩略图</th>
											<th>广告名称</th>
											<th>展示用户组</th>
											<th class="table-toggle">投放位置</th>
											<th>链接地址</th>
											<th class="table-toggle">有效期</th>
											<th>申请人</th>
											<th>审核人</th>
											<th>审核状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${adStrucs }" var="adStruc" varStatus="status">
											<tr>
											<td><label class="label">
											<c:if test="${adStruc.status == 1}">				
											<input type="checkbox" class="checkbox" value="${adStruc.id }">
											</c:if>
											<c:if test="${adStruc.status != 1}	">
											&nbsp;&nbsp;&nbsp;
											</c:if>
											${status.index+1 }</label></td>
											<td>
												<a href="javascript:void(0)" class="thumbnail"><img class="row-img-small" src="${imageServerVisit}${adStruc.imgUrl }" alt="" data-src-big="${imageServerVisit}${adStruc.imgUrl }" data-width="${adStruc.width }" data-height="${adStruc.height }" /></a>
											</td>
											<td>${adStruc.name }</td>
											<td>
												<c:if test="${adStruc.rcTopAgent ==1 }">&nbsp;总代</c:if>
												<c:if test="${adStruc.rcOtAgent ==1 }">&nbsp;其他代理</c:if>
												<c:if test="${adStruc.rcCustomer ==1 }">&nbsp;玩家</c:if>
												<c:if test="${adStruc.rcGuest ==1 }">&nbsp;游客</c:if>
												<c:if test="${adStruc.rcVip ==1 }">&nbsp;Vip</c:if>
												<c:if test="${adStruc.rcNonVip ==1 }">&nbsp;非Vip</c:if>
											</td>
											<td class="row-show" data-id="${adStruc.id }">
												<a href="javascript:void(0);" class="row-show-tigger">${adStruc.spaces }</a>
												<div class="row-show-panel"  style="z-index:10000"></div>
											</td>
											<td><a href="${adStruc.targetUrl }">${adStruc.targetUrl }</a></td>
											<td>${adStruc.effectBeginStr }<br />${adStruc.effectEndStr }</td>
											<td>${adStruc.operator }</td>
											<td><c:if test="${adStruc.status != 4 && adStruc.status != 1}">${adStruc.approver }</c:if></td>
											<c:if test="${adStruc.status == 3}">
											<td class="row-show-pass" data-id="${adStruc.id }" data-value="${adStruc.memo }">
												<a href="javascript:void(0);" class="row-show-tigger">未通过</a>
												<div class="row-show-panel" style="z-index:10000"></div>
											</td>
											</c:if>
											<c:if test="${adStruc.status == 1}"><td>审核中</td> </c:if>
											<c:if test="${adStruc.status == 2}"><td>已通过</td> </c:if>
											<c:if test="${adStruc.status == 4}"><td>待审核</td> </c:if>
											<c:if test="${adStruc.status == 5}"><td>已删除</td> </c:if>
											<td class="table-tool">
											<c:if test="${adStruc.status == 1}">
												<a data-id="${adStruc.id }" class="ico-right row-pass" title="通过" href="javascript:void(0);"></a>
												<a data-id="${adStruc.id }" class="ico-wrong row-notpass" title="不通过" href="javascript:void(0);"></a>
											</c:if>
											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
								
								<c:if test="${!empty adStrucs}">
								<div class="page-wrapper" style="float:left">
									<span class="page-text">
									<c:if test="${adQueryRequest.status==null||adQueryRequest.status == 1 }">
										<label class="label"><input id="J-select-all" type="checkbox" class="checkbox">全选</label>
										<a id="J-button-passall" class="btn btn-small" href="javascript:void(0);">通 过<b class="btn-inner"></b></a>
										<a id="J-button-cancelall" class="btn btn-small" href="javascript:void(0);">不通过<b class="btn-inner"></b></a>
									</c:if>
									</span>
								</div>
								<div style="height:200px"></div>
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
								</c:if>
								<c:if test="${empty adStrucs}">
								<div class="alert alert-waring">
									<i></i>
									<div class="txt">
										<h4>抱歉，没有找到符合的条件，请重新选择搜索！</h4>
									</div>
								</div>
								</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>	
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/advertReviewManage.js"></script>
</body>
</html>