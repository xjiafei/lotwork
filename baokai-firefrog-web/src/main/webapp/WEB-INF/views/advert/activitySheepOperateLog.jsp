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
	<title>操作日志</title>
	

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
					<a href="#">2015春节活动</a>&gt;操作日志
				</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">

						<div class="ui-tab">
							
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li ><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
							</div>
							
							<div class="ui-tab-content ui-tab-content-current">
								<form action="queryActivityOperateLog" id="J-form" method="post">
									<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
									<ul class="ui-search">
										<li>
											<label for="" class="ui-label">用户名：</label>
											<input type="text" value="${query.userName}" class="input w-3" name="userName">
											
											
										</li>
										<li>
											<label class="ui-label">活动：</label>
											<select class="ui-select" name="activityId" id="activityId">
												<option value="-1" selected="">全部</option>
												<option value="3">羊年抽红包</option>
												<option value="4">羊年大小通吃</option>
												<option value="5">羊年转运盘</option>
											</select>
											<input type="hidden" value="${query.activityId}" class="input w-3" id="activityIdv">
										</li>
										<li class="time">
											<label for="" class="ui-label">时间：</label>
											<input id="J-time-start" type="text" value="${timestart}" name="timeStartStr" class="input"> - <input id="J-time-end" type="text" value="${timeEndStr}" name="timeEndStr" class="input">
											<input id="startTimel" type="hidden" value="" name="startTimel">
											<input id="endTimel" type="hidden" value="" name="endTimel">
										</li>
										<li>
											<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
										</li>
									</ul>
								</form>
								
								<table class="table table-info">
									<thead class="text-center">
										<tr>
											<th>操作时间</th>
											<th>用户名</th>
											<th>操作人</th>
											<th>活动</th>
											<th>操作内容</th>
											<th>操作内容</th>
										</tr>
									</thead>
									<tbody class="text-center">
									<c:forEach items="${struc}" var="struc" varStatus="configIndex">
										<tr data-id="${struc.id}">
											<td><fmt:formatDate value="${struc.gmtCreated}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
											<td>${struc.userName }</td>
											<td>${struc.operateName}</td>
											<td>${struc.activityName}</td>
											<td><c:if test="${struc.operateType==1}">更新</c:if>
											<c:if test="${struc.operateType==2}">发布</c:if>
											<c:if test="${struc.operateType==3}">拒绝发布</c:if>
											</td>
											<td>${struc.operateContent}</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>
								<script>
								$('.menu-list li').removeAttr("class");
								$('.menu-list li:eq(6)').attr("class","current"); 
								$('.col-side .nav dd:eq(18)').attr("class","current");
							    var start = $('#J-time-start'),
						        end = $('#J-time-end');
							    
							    $("#activityId").val($("#activityIdv").val());

						    start.focus(function() {
						        (new phoenix.DatePicker({
						            input: start,
						            isShowTime: true
						        })).show();
						    });
						    end.focus(function() {
						        (new phoenix.DatePicker({
						            input: end,
						            isShowTime: true
						        })).show();
						    });
						    
						    $('#J-button-submit').click(function() {
						    	if($('#J-time-start').val()!=null&&$('#J-time-start').val()!=''){
						    		$("#startTimel").val(convertDate2UnixTimestamp($('#J-time-start').val()));
						    	}
						    	if($('#J-time-end').val()!=null&&$('#J-time-end').val()!=''){
						    		$("#endTimel").val(convertDate2UnixTimestamp($('#J-time-end').val()));
						    	}
						        $('#J-form').submit();
						    });

								</script>
								<!-- PAGER 分页 -->
								<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>

							</div>
								
						</div>



					</div>
				</div>
			</div>
		</div>
<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/dateUtil.js" ></script>
</body>
