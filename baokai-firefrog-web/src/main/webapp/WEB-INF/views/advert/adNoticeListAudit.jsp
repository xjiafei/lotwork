<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>公告管理(审核)</title>
	<style>
		.table-group .table-toggle a {color:#009B7D; text-decoration:underline;}
		.j-ui-tip {border:1px solid #CCC;padding:10px;}
		.j-ui-tip .sj {display:none;}
		.row-show {position:relative;}
		.row-show-pass {position:relative;}
		.row-show-panel {display:none;position:absolute;background:#FFFFE1;border:1px solid #CCC;padding:10px;}
		.row-show-panel-current {display:block;}
		.table a {text-decoration:underline;}
	</style>
	
</head>

<body>
			<div class="col-crumbs">
                <div class="crumbs">
                    <strong>当前位置：</strong>
                <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 公告管理（审核）
                </div>
            </div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
					<form action="${currentContextPath}/adAdmin/searchNoticeList" id="J-form">
						<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
						<input type="hidden" name="status" value="${search.status}" id="status">
						<input type="hidden" name="orderBy" value="${search.orderBy}" id="orderBy">
						<input type="hidden" name="isAudit" value="1">
						<ul class="ui-search">
							<li>
								<label class="ui-label" for="">公告标题：</label>
								<input type="text" class="input w-3"  value="${search.title}" name="title">
							</li>
							<li>
								<label class="ui-label">公告类型：</label>
								<select class="ui-select w-2" name="type">
									<option value="-1" <c:if test="${empty search.type}">selected</c:if>  >全部</option>
									<option value="1"  <c:if test="${search.type==1}">selected</c:if>  >普通</option>
									<option value="2"  <c:if test="${search.type==2}">selected</c:if>>紧急</option>
								</select>
							</li>
							<li>
								<label class="ui-label">有效期：</label>
								<select class="ui-select w-2" name="periodStatus">
									<option value="-1" <c:if test="${empty search.periodStatus}">selected</c:if>>全部</option>
									<option value="1"  <c:if test="${search.periodStatus==1}">selected</c:if>>进行中</option>
									<option value="2"  <c:if test="${search.periodStatus==2}">selected</c:if>>未开始</option>
									<option value="3"  <c:if test="${search.periodStatus==3}">selected</c:if>>已过期</option>
								</select>
							</li>
							<li class="checkbox-list">
								<label class="ui-label">展示用户组：</label>
								<label class="label" for="J-checkbox-1"><input id="J-checkbox-1" type="checkbox" class="checkbox" name="rcTopAgent" value="1" <c:if test="${search.rcTopAgent==1}">checked</c:if>  >总代理</label>
								<label class="label" for="J-checkbox-2"><input id="J-checkbox-2" type="checkbox" class="checkbox" name="rcOtAgent" value="1" <c:if test="${search.rcOtAgent==1}">checked</c:if>>其他代理</label>
								<label class="label" for="J-checkbox-3"><input id="J-checkbox-3" type="checkbox" class="checkbox" name="rcCustomer" value="1"  <c:if test="${search.rcCustomer==1}">checked</c:if>>玩家</label>
								<label class="label" for="J-checkbox-4"><input id="J-checkbox-4" type="checkbox" class="checkbox" name="rcVip" value="1"  <c:if test="${search.rcVip==1}">checked</c:if>>VIP</label>
								<label class="label" for="J-checkbox--1"><input id="J-checkbox-4" type="checkbox" class="checkbox" name="rcNonVip" value="1"  <c:if test="${search.rcNonVip==1}">checked</c:if>>非VIP</label>
							</li>
							<li><a id="J-button-search" href="javascript:void(0);" class="btn btn-important">搜 索<b class="btn-inner"></b></a></li>
						</ul>
					</form>
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
									<li <c:if test="${empty search.status}"> class="current"</c:if>><a href="javascript:changeStatus(-1,'GMT_CREATED DESC')">全部</a></li>
									<li <c:if test="${search.status==2}"> class="current"</c:if>><a href="javascript:changeStatus(2,'GMT_MODIFIED DESC')">审核中（${sumAudit }）</a></li>
									<li <c:if test="${search.status==3}"> class="current"</c:if>><a href="javascript:changeStatus(3,'GMT_MODIFIED DESC')">已通过</a></li>
									<li <c:if test="${search.status==4}"> class="current"</c:if>><a href="javascript:changeStatus(4,'GMT_MODIFIED DESC')">未通过（${sumRefuse }）</a></li>
								</ul>
							</div>
							<div class="ui-tab-content ui-tab-content-current">
							<c:if test="${empty list}">
								<div class="alert alert-waring">
									<i></i>
									<div class="txt">
										<h4>没有符合条件的记录，请更改查询条件！</h4>
									</div>
								</div>
							</c:if>
							<c:if test="${!empty list}">							
								<table class="table table-info table-group" id="J-table">
									<thead>
										<tr>
											<th class="table-toggle">编号</th>
											<th>分类</th>
											<th>公告标题</th>
											<th>公告类型</th>
											<th>展示页面</th>
											<th>展示用户组</th>
											<th>有效期</th>
											<th>申请人</th>
											<th>审核人</th>
											<th>审核状态</th>
											<th>操作</th>
										</tr>
									</thead>
									<tbody>
									
									<c:forEach items="${list}" var="list" varStatus="status">
										<tr>
											<td><label class="label">
												<c:if test="${list.status!=3 && list.status!=4}">
												<input type="checkbox" class="checkbox" value="${list.id}" />
												</c:if>
												${list.id}</label>
											</td>
											<td>
												<c:if test="${list.noticelevel==0}">重要通知</c:if>	
												<c:if test="${list.noticelevel==1}">派奖信息</c:if>	
												<c:if test="${list.noticelevel==2}">平台维护</c:if>	
												<c:if test="${list.noticelevel==3}">平台活动</c:if>	
												<c:if test="${list.noticelevel==4}">VIP资讯</c:if>	
											</td>
											<td><div style="width:150px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap;"><a style="cursor:hand;cursor:pointer;color:#555;text-decoration:none;" title="${list.title}">${list.title}</a></div></td>
											<td>${list.typeStr}</td>
											<td>
											<c:if test="${list.showPages=='ad_index'}">平台首页</c:if>	
											<c:if test="${list.showPages=='ad_top'}">平台顶部</c:if>	
											<c:if test="${list.showPages=='ad_foot'}">平台底部</c:if>	
											<c:if test="${list.showPages=='ad_reg'}">注册页</c:if>	
											<c:if test="${list.showPages=='ad_login'}">登陆页</c:if>	
											<c:if test="${list.showPages=='ad_cqssc'}">重庆时时彩</c:if>	
											<c:if test="${list.showPages=='ad_jxssc'}">江西时时彩</c:if>	
											<c:if test="${list.showPages=='ad_xjssc'}">新疆时时彩</c:if>	
											<c:if test="${list.showPages=='ad_tjssc'}">天津时时彩</c:if>	
											<c:if test="${list.showPages=='ad_hljssc'}">黑龙江时时彩</c:if>	
											<c:if test="${list.showPages=='ad_shssc'}">上海时时彩</c:if>	
											<c:if test="${list.showPages=='ad_llssc'}">乐利时时彩</c:if>	
											<c:if test="${list.showPages=='ad_sd115'}">山东11选5</c:if>	
											<c:if test="${list.showPages=='ad_jx115'}">江西11选5</c:if>	
											<c:if test="${list.showPages=='ad_gd115'}">广东11选5</c:if>	
											<c:if test="${list.showPages=='ad_cq115'}">重庆11选5</c:if>	
											<c:if test="${list.showPages=='ad_ll115'}">乐利11选5</c:if>	
											<c:if test="${list.showPages=='ad_sl115'}">顺利11选5</c:if>		
											<c:if test="${list.showPages=='ad_bjkl8'}">北京快乐8</c:if>	
											<c:if test="${list.showPages=='ad_fc3d'}">3D</c:if>	
											<c:if test="${list.showPages=='ad_p5'}">排列5</c:if>	
											<c:if test="${list.showPages=='ad_ssq'}">双球</c:if>	
											<c:if test="${list.showPages=='ad_mmc'}">顺利秒秒彩</c:if>	
											<c:if test="${list.showPages=='ad_ahk3'}">安徽快三</c:if>
											<c:if test="${list.showPages=='ad_lhc'}">六合彩</c:if>
											<c:if test="${list.showPages=='ad_vip'}">VIP页面</c:if>
											<c:if test="${list.showPages=='agent_first_page'}">全部页面，代理首页</c:if>	
											<c:if test="${list.type==1&&list.showPages!='agent_first_page'}">全部页面</c:if>
											
											</td>
											<td>${list.groups}</td>
											<td>${list.gmtEffectBeginStr}<br />${list.gmtEffectEndStr}</td>
											<td>${list.operator}</td>
											<td>${list.approver}</td>
											<c:if test="${list.status==1}"><td>待审核</td></c:if>
											<c:if test="${list.status==2}"><td>审核中</td></c:if>
											<c:if test="${list.status==3}"><td>已通过</td></c:if>
											<c:if test="${list.status==4}">
												<td class="row-show-pass" data-id="${list.id }" data-memo="${list.memo }" style="z-index:99">
												<a href="javascript:void(0);" class="row-show-tigger">未通过</a>
												<div class="row-show-panel"></div>
											    </td>
											</c:if>
											<c:if test="${list.status==5}"><td>已删除</td></c:if>
											<td class="table-tool">
												<a data-content="<spring:message htmlEscape="true" javaScriptEscape="true" text="${list.content}"></spring:message>" class="ico-preview" title="预览" href="javascript:void(0);"></a>
												<c:if test="${list.status==2}">
												<a data-id="${list.id}" class="ico-right row-pass" title="通过" href="javascript:void(0);"></a>
												<a data-id="${list.id}" class="ico-wrong row-notpass" title="不通过" href="javascript:void(0);"></a>
												</c:if>
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<div class="page-wrapper clearfix" style="float:left;">
									<span class="page-text">
										<label class="label"><input id="J-select-all" type="checkbox" class="checkbox" />全选</label>
										<c:if test="${search.status==2 || empty search.status}">
										<a id="J-button-passall" class="btn btn-small" href="javascript:void(0);">通 过<b class="btn-inner"></b></a>
										<a id="J-button-cancelall" class="btn btn-small" href="javascript:void(0);">不通过<b class="btn-inner"></b></a>
										</c:if>
									</span>
								</div>
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>	
								<br style="clear:both;">	
							</c:if>
							</div>
						</div>
					</div>
				</div>
			</div>
			
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/adNoticeListAudit.js"></script>				

</body>