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
	<title>押大小活动数据</title>
	

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
					<a href="#">2015春节活动</a>&gt;押大小活动数据
				</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">

						<div class="ui-tab">
							
							<div class="ui-tab-title clearfix">
								<ul>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityHongBaoLogzl">活动总览</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepHongBao">红包活动数据</a></li>	
                                    <li class="current"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">押大小活动数据</a></li>                            
									<li ><a href="${currentContextPath}/adAdmin/queryActivityZpLog">转盘抽奖活动数据</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivitySheepConfig?activityId=4">活动配置</a></li>
									<li ><a href="${currentContextPath}/adAdmin/queryActivityOperateLog">操作日志</a></li>
								</ul>
							</div>
							
							<div class="ui-tab-content ui-tab-content-current">
								<ul class="ui-form">
									<li>
										<label class="ui-label w-1">&nbsp;</label>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog">全部</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog?pageStatus=3">待审核（${unCheckNum}）</a></span>
										<span class="ico-tab ico-tab-item"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog?pageStatus=4">已通过</a></span>
										<span class="ico-tab ico-tab-item ico-tab-current"><a href="${currentContextPath}/adAdmin/queryActivityYaDaXiaoLog?pageStatus=5">未通过</a></span>
									</li>
								</ul>
								
								<form action="queryActivityYaDaXiaoLog?pageStatus=5" id="J-form" method="post">
									<input type="hidden" name="pageNo" value="${page.pageNo}" id="pageNo">
									<input type="hidden" name="pageStatus" id="pageStatus" value="${pageStatus}">
									<ul class="ui-search">
									
									<li>
											<label for="" class="ui-label">用户名：</label>
											<input type="text" value="${query.userName}" class="input w-3" name="userName">
										</li>
										<li class="time">
											<label for="" class="ui-label">押宝时间：</label>
											<input id="J-time-start" type="text" value="${timeStartStr}" name="timeStartStr" class="input"> - <input id="J-time-end" type="text" value="${timeEndStr}" name="timeEndStr" class="input">
											<input id="startTimel" type="hidden" value="${query.startTimel}" name="startTimel">
											<input id="endTimel" type="hidden" value="${query.endTimel }" name="endTimel">
										</li>
										<li>
											<label class="ui-label">来源：</label>
											<select class="ui-select" name="channel" id="channel">
												<option value="-1">全部</option>
												<option value="1">ios2.1</option>
												<option value="2">android2.1</option>
												<option value="3">web3.0</option>
												<option value="4">web4.0</option>
											</select>
											<input id="channelv" type="hidden" value="${query.channel}">
										</li>
										<li>
											<a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">搜 索<b class="btn-inner"></b></a>
											<a id="J-download-submit" class="btn btn-important" href="${fromGame}/gameoa/exportActivityYaDaXiaoLog?pageStatus=5">导出报表<b class="btn-inner"></b></a>
										</li>
									</ul>
								</form>
								
								<table class="table table-info">
									<thead class="text-center">
										<tr>
											<th><input type="checkbox" id="J-select-all-item"></th>
											<th>用户名</th>
											<th>押宝时间</th>
											<th>结果</th>
											<th>奖金</th>
											<th>来源</th>
											<th>原因</th>
											<th>审核人</th>
											<th>审核时间</th>
										</tr>
									</thead>
									<tbody class="text-center">
									<c:forEach items="${struc}" var="struc" varStatus="configIndex">
										<tr data-id="${struc.id }">
											<td><input type="checkbox"></td>
											<td><span data-username="${struc.userName }">${struc.userName }</span><c:if test="${struc.vip==1}"><img src="http://static.rfonlineedu.com:888/static/images/ucenter/safe/vip.jpg"></c:if></td>
											<td>${struc.activityTimeStr }</td>
											<td>${struc.result}</td>
											<td>￥${struc.award/10000}</td>
											<td><c:if test="${struc.channel==1 }">ios2.1</c:if>
												<c:if test="${struc.channel==2 }">android2.1</c:if>
												<c:if test="${struc.channel==3 }">web3.0</c:if>
												<c:if test="${struc.channel==4 }">web4.0</c:if>
											<td>${struc.verifyReason}</td>
											<td>
												${struc.verifyName}
											</td>
											<td>
												${struc.verifyTimeStr}
											</td>
										</tr>
									</c:forEach>
									</tbody>
								</table>

								<script>
								$(function(){
									$('.menu-list li').removeAttr("class");
									$('.menu-list li:eq(6)').attr("class","current"); 
									$('.col-side .nav dd:eq(15)').attr("class","current");
									$("#channel").val($("#channelv").val());
									// 搜索相关
									var start = $('#J-time-start'),
								        end = $('#J-time-end');

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

									// ajax 请求url
									var ajaxurl = 'blabla.php';
									
									// 通过/拒绝
									var minWindow = new phoenix.MiniWindow({ cls: 'pop' }), 
										mask = phoenix.Mask.getInstance();

									minWindow.addEvent('beforeShow', function() {
										mask.show();
									});
									minWindow.addEvent('afterHide', function() {
										mask.hide();
									});
									$('[data-action]').on('click', function(){
										var action = $(this).data('action');
										// console.log(action);
										if( !action ) return false;

										var $tr = $(this).parents('tr:eq(0)'),
											username = $tr.find('[data-username]').data('username'),
											id = $tr.data('id');

										if( action == 'pass' ){
											// 请开启以下注释，并去掉上一行代码
											/*$.post(ajaxurl, {ids: [id], action: 'pass'}).done(function(resp){
												var resp = $.parseJSON(resp);                
												if( resp.status == 'ok' ){
													minWindow.hide();
													alert('操作成功！');
													location.reload();
												}else{
													alert(resp.msg);
												}
											}).fail(function(xhr, textStatus, errorThrown){
												alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
											});*/
										}else if( action == 'refuse' ){
											minWindow.setTitle('拒绝');
											minWindow.setContent(
												'<div class="pop-title text-left"><strong>用户名：</strong>' + username + '</div>' +
												'<div class="pop-title text-left">' +
													'<p><strong>拒绝原因：</strong></p>' +
													'<textarea class="textarea w-8" name="" placeholder="请输入拒绝原因"></textarea>' + 
												'</div>' + 
												'<div class="pop-btn">' +
													'<a href="javascript:void(0);" class="btn btn-important" id="J-button-pop-confirm">确 认<b class="btn-inner"></b></a>' +
													'<a href="javascript:void(0);" class="btn" id="J-button-pop-cancel">取 消<b class="btn-inner"></b></a>' +
												'</div>'
											);
											minWindow.show();

											$('#J-button-pop-confirm').on('click', function(e) {
												e.preventDefault();
												minWindow.hide();
												// 请开启以下注释，并去掉上一行代码
												/*$.post(ajaxurl, {ids: [id], action: 'pass'}).done(function(resp){
													var resp = $.parseJSON(resp);                
													if( resp.status == 'ok' ){
														minWindow.hide();
														alert('操作成功！');
														location.reload();
													}else{
														alert(resp.msg);
													}
												}).fail(function(xhr, textStatus, errorThrown){
													alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
												});*/

											});
											$('#J-button-pop-cancel').on('click', function() {
												minWindow.hide();
											});
										}
									});

									// 全选按钮
									var $checkboxs = $('tbody input[type="checkbox"]'),
										$selectAllCheckbox = $('#J-select-all-item');

									$selectAllCheckbox.on('click', function(){
										var checked = this.checked;
										$checkboxs.prop('checked', checked);
									});

									$checkboxs.on('click', function(){
										var $allCheckeds = $checkboxs.filter(':checked');
										$selectAllCheckbox.prop('checked', $allCheckeds.length == $checkboxs.length);
									});

									$('[data-pass-or-refuse-buttons] a').on('click', function(){
										var type = $(this).data('button-type');
										// console.log(type);
										var $allCheckeds = $checkboxs.filter(':checked');
										if( !$allCheckeds.length || (type != 'pass' && type != 'refuse') ){
											return false;
										}
										var ids = [];
										$allCheckeds.each(function(){
											var $tr = $(this).parents('tr:eq(0)'),
												id = parseInt($tr.data('id'));
											if( id ){
												ids.push(id);
											}
										});
										// console.log(ids);
										$.post(ajaxurl, {ids: ids, action: type}).done(function(resp){
											var resp = $.parseJSON(resp);                
											if( resp.status == 'ok' ){
												minWindow.hide();
												alert('操作成功！');
												location.reload();
											}else{
												alert(resp.msg);
											}
										}).fail(function(xhr, textStatus, errorThrown){
											alert('服务器返回错误："' + xhr.responseText + '". 失败，请重试！');
										});
									});				
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
</body>
