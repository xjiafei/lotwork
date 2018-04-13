<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%> 
<%@ taglib prefix="pg" uri="/tag-page"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>平台升级活动</title>
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	</style>
	<script type="text/javascript">
		var baseUrl = '${currentContextPath}';
	
	</script>
	
</head>
<body>
	
		<div class="col-main">
			<!-- 从被装饰页面获取body标签内容 -->
			
			<div class="col-crumbs">
			<div class="crumbs">
				<strong>当前位置：</strong><a href="#">活动</a>>抽奖用户查询
			</div>
			</div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<div class="ui-tab">
							<div class="ui-tab-title clearfix">
								<ul>
										<li ><a  href="${currentContextPath}/gameoa/queryActivityHongBaoLog">红包明细查询</a></li>
									<li ><a href="${currentContextPath}/gameoa/queryActivityChouJianUserLog">抽奖用户查询</a></li>
                                    <li class="current"><a href="${currentContextPath}/gameoa/queryActivityChouJianLog">中奖结果查询</a></li>
									<li ><a href="${currentContextPath}/gameoa/queryActivityConfig">中奖概率配置</a></li>
								</ul>
							</div>
							<div class="clearfix">
						<form action="queryActivityChouJianLog" method="post" id="J-search-form">
						<ul class="ui-search" id="J-search-panel">
							<li>
								<label for="" class="ui-label">用户名：</label>
								<input type="text" id="userName" name="userName" class="input w-2">
							</li>
							
							<li class="time">
								<label for="" class="ui-label">中奖时间：</label>
								<input id="timestart" name="timestart" type="text" value="" class="input"> - <input id="timeend" type="text" name="timeend" value="" class="input">
							</li>
							<li>
								<label for="" class="ui-label">中奖奖品：</label>
								<select id="config" style="width:90px;" name="config" class="ui-select">
									<option value="-1">全部</option>
								
									<option value="1">1元</option>
									<option value="2">1元</option>
									<option value="3">2元</option>
									<option value="4">10元</option>
									<option value="5">50元</option>
									<option value="6">50元</option>
									<option value="7">300元</option>
									<option value="8">999元</option>
								</select>
							</li>
                            <li>
								<label for="" class="ui-label">中奖渠道：</label>
								<select id="status" style="width:90px;" name="status" class="ui-select">
									<option value="-1">全部</option>
									<option value="0">pc</option>
									<option value="1">ios</option>
									<option value="2">android</option>
								</select>
							</li>
							<li>
								<input id="submit" class="btn btn-primary" type="submit" value="搜 索">
							
								
							</li>
						</ul>
						
						<table class="table table-info" id="J-table">
							<thead>
								<tr>
									<th>中奖时间</th>
									<th>用户名</th>
									<th>获得奖品</th>
									<th>中奖渠道</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<th>2014-02-12 22:30:30</th>
									<th>kukucai</th>
									<th>100元（充值50元后送）</th>
									<th>pc</th>
								</tr>
							</tbody>
						</table>
                        
					<c:if test="${page!=null}">
				        	<pg:page totalCount="${page.totalCount}" pageNo="${page.pageNo}" pageSize="${page.pageSize}"/>
						
							</c:if>
					</form>		
					</div>
				</div>
			</div>
		</div>
	</div>
    <script type="text/javascript">
	(function($){
	var inputStart = $('#timestart'),
		inputEnd = $('#timeend');
	inputStart.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
	inputEnd.focus(function(){
		var d = new phoenix.DatePicker({input:this,isShowTime:true});
		d.show();
		d.addEvent('afterSetValue', function(){
			setSelect(0);
			selectTime.val(0);
		});
	});
   })(jQuery);
    </script><script type="text/javascript">
		
	$('.menu-list li').removeAttr("class");
	$('.menu-list li:eq(6)').attr("class","current"); 
	$('.col-side .nav dd:eq(9)').attr("class","current");
	</script>
    
	<script type="text/javascript" src="${staticFileContextPath}/static/js/paging/paging.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/operations/order/manualRecord.js"></script>
</body>