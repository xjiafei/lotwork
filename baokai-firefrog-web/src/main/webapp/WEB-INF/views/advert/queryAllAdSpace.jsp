<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="/tag-permission" prefix="permission"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${staticFileContextPath}/static/js/advert/queryAllSpace.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>广告位管理</title>
</head>
		<div class="col-main">
		
			<div class="col-crumbs">
                <div class="crumbs">
         			<permission:hasRight moduleName="MARKET_ADDADSPACE">       
                   		 <a href="${currentContextPath}/adAdmin/editAdSpace" class="more">新建广告位</a>
         			</permission:hasRight>           
                    <strong>当前位置：</strong>
                    <a href="${currentContextPath}/adAdmin/queryPublishAdPage">营销中心</a> &gt; 广告位管理
                </div>
            </div>
		
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>广告位置</th>
									<th>广告位尺寸</th>
									<th>链接打开方式</th>
									<th>占位图</th>
									<th>位置状态</th>
									<th>已绑定</th>
									<th>进行中</th>
									<th>未开始</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="adSpaceItem" items="${adSpaceList }">
								<tr>
									<td class="table-tool text-left" data-img="${urlViewPic}${adSpaceItem.dftImg}">${adSpaceItem.pageIdStr}(${adSpaceItem.name })</td>
									<td>${adSpaceItem.width }*${adSpaceItem.height }</td>
									<td>
										<c:if test="${adSpaceItem.urlTarget ==1 }">
											新窗口
										</c:if>
										<c:if test="${adSpaceItem.urlTarget ==2 }">
											原窗口
										</c:if>
									</td>
									<td class="table-tool">
										<c:if test="${adSpaceItem.isDftUsed == 1 }">
										<a data-id="1" class="ico-normal" title="开启" href="javascript:void(0);"></a>
										</c:if>
										<c:if test="${adSpaceItem.isDftUsed == 0 }">
										<a data-id="1" class="ico-disable" title="禁用" href="javascript:void(0);"></a>
										</c:if>
									<span class="ico-disable"></span></td>
									<td class="table-tool">
										<c:if test="${adSpaceItem.isUsed == 1 }">
										<a data-id="1" class="ico-unlock" title="开启" href="javascript:void(0);"></a>
										</c:if>
										<c:if test="${adSpaceItem.isUsed == 0 }">
										<a data-id="1" class="ico-lock" title="禁用" href="javascript:void(0);"></a>
										</c:if>
									</td>
									<td>${adSpaceItem.allProcess }</td>
									<td>${adSpaceItem.inProcess }</td>
									<td>${adSpaceItem.noProcess }</td>
									<td class="table-tool">
									<permission:hasRight moduleName="MARKET_MODIFYADSPACE">
										<a class="ico-edit" title="修改" href="${currentContextPath}/adAdmin/editAdSpace?id=${adSpaceItem.id}"></a>
										</permission:hasRight>
									<permission:hasRight moduleName="MARKET_UNBINGADSPACE">
										<a class="ico-link" title="解绑和排序广告" href="${currentContextPath}/adAdmin/getAdvertBySpaceId?id=${adSpaceItem.id}"></a>
									</permission:hasRight>
									</td>
								</tr>
							</c:forEach>
							
							</tbody>
						</table>
					</div>
				</div>
			</div>
					<div style="height:200px"></div>
		</div>
	</div>