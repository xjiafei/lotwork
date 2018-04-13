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
	<title>活动总览</title>
	

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
					<a href="#">2015春节活动</a>&gt;活动总览
				</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">

						<div class="ui-tab">
							
							<div class="ui-tab-title clearfix">
								<ul>
									<li class="current"><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li ><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li ><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
							</div>
							
							<div class="ui-tab-content ui-tab-content-current">
								
								<ul class="ui-form">
								
									<li>
										<label class="ui-label w-1">&nbsp;</label>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">红包活动</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityYaDaoXiaoLogzl?activityId=4">押大小活动</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityYaDaoXiaoLogzl?activityId=5">转盘抽奖活动</a></span>
									</li>
								</ul>
								<style>
								.detail-tbody td{
									background-color: #f9f9f9!important;
									padding-top: 2px!important;
									padding-bottom: 2px!important;
								}
								</style>
								<table class="table table-info">
									<thead class="text-center">
										<tr>
											<th>日期</th>
											<th>报名红包人数</th>
											<th>完成红包人数</th>
											<th>领取红包人数</th>
											<th>操作</th>
										</tr>
									</thead>
									
									<c:forEach items="${struc}" var="struc" varStatus="configIndex">
									<tbody class="text-center">
									<tr data-str="${struc.countDateStr}">
										<td>${struc.countDateStr}</td>
										<td>${struc.sighNum}</td>
										<td>${struc.reachNum}</td>
										<td>${struc.getNum}</td>
										<td><a data-detail href="javascript:void(0);">查看明细</a></td>
									</tr>
									</tbody>
									</c:forEach>
								</table>
								<!-- 以下JS仅用于“查看明细” -->
								<script>
								$(function(){
									$('.menu-list li').removeAttr("class");
									$('.menu-list li:eq(6)').attr("class","current"); 
									$('.col-side .nav dd:eq(13)').attr("class","current");
									//var $allDetailTbodys = $('.detail-tbody').slideUp(0);
									
									var MSG = new phoenix.Message();
									$('a[data-detail]').on('click', function(e){
										e.preventDefault();
										var URL = baseUrl+"/adAdmin/querySheepHongBaoDetailCount";
										var	$this = $(this);
										var date = $this.parent().parent().attr("data-str");
										
										$.get(URL, {'date':date}, function(res) {
											if(res && res['status'] == 1){
												render(res['data']);
											}
										}, 'json');
										
										
                                    
										/*var $thisTbody = $(this).parents('tbody:eq(0)'),
											$nextTbody = $thisTbody.next();
										$nextTbody.siblings('.detail-tbody').slideUp(0);
										$nextTbody.slideToggle();*/
									});
									
									render = function(data){
										 var template='<table class="table table-info">'
											 template += '<thead class="text-center">';
											 template += '<tr><th>渠道</th>';
											 template += '<th>报名红包人数</th>';
											 template += '<th>完成红包人数</th>';
											 template += '<th>领取红包人数</th>';
											 template += '</thead>';
											 template += '<tbody class="detail-tbody text-center">';
											 for (var i = 0; i < data.length; i++) {
												 template += '<tr><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>';
											 }
											 template += '</tbody></table>';
												MSG.show({
												'content':template,
												'confirmIsShow': false,
											  });
									}
								});
								</script>
							</div>
								
						</div>
					</div>
				</div>
			</div>
		</div>
</body>
