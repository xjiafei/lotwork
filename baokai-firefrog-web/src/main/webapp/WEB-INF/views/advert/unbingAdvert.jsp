<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/unbingAdvertSpace.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<div class="col-content">
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">广告位管理</a> &gt; 解绑和排序广告</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main sequence">
						<form action="?" id="J-form">
						<input type="hidden" name="adSpaceId" id="adSpaceId" value=${adSpaceId }>
						<table class="table table-info tabel-group" id="J-table">
							<thead>
								<tr>
									<th>缩略图</th>
									<th>广告名称</th>
									<th>展示用户组</th>
									<th class="table-toggle">有效期</th>
									<th>状态</th>
									<th>审核状态</th>
									<th class="table-toggle">排序</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="voItem" items="${adspaceRelationStruc }" >
								<tr>
									<td>
										<input type="hidden" name="adId" id="adId_${voItem.advertId }" value="${voItem.advertId }">
										<input name="check" tr_index='${voItem.advertId }' type="checkbox" id="isShown_${voItem.advertId }"  <c:if test="${voItem.isShown ==1}">checked="checked"</c:if> value="1" />
										<a href="" class="thumbnail"><img class="row-img-small" src="${urlViewPic }${voItem.adImgUrl }" alt="" data-src-big="${urlViewPic }${voItem.adImgUrl }" data-width="10" data-height="20" /></a>
									</td>
									<td>${voItem.adName }</td>
									<td>${voItem.showGroup}</td>
									<td>${voItem.adGmtEffectBegin }<br />${voItem.adGmtEffectEnd }</td>
									<td>${voItem.showStatus }</td>
									<td>${voItem.adStatus }</td>
									<td><input type="text" id="orders_${voItem.advertId }" value="${voItem.orders }" class="input w-1 input-num">
									<span class="ui-check"><i></i>顺序号应在0-10000之间</span></td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
						</form>
						<p class="text-right"><a id="J-button-submit" href="javascript:void(0);" class="btn btn-important">确 定<b class="btn-inner"></b></a></p>
					</div>
				</div>
			</div>
		</div>
	</div>
