<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>渠道配置-设定参数</title>
<style>
.j-ui-tip {
	background: #FFFFE1;
	border: 1px solid #CCC;
	color: #333;
	font-size: 12px;
}

.j-ui-tip .sj {
	display: none;
}

.ui-form-window li {
	margin-top: 5px;
	margin-bottom: 5px;
}

.pop-window-datepicker {
	z-index: 710;
}
</style>
<script type="text/javascript"
	src="${staticFileContextPath}/static/js/channel/channeltempalt.js"></script>
<script type="text/javascript" src="${staticFileContextPath}/static/js/datePickerForChannel.js"></script>
</head>

<body>
	<div class="col-crumbs">
		<div class="crumbs">
			<strong>当前位置：</strong><a
				href="${currentContextPath}/channel/toSaveChannelTemplate">渠道配置</a>
			&gt; 设定参数
		</div>
	</div>
	<div class="col-content">
		<div class="col-main">
			<div class="main">
				<div class="ui-tab">
					<div class="ui-tab-content ui-tab-content-current">
						<form action="${currentContextPath}/channel/queryByID"
							id="J-form-1" method="post" >
							<ul class="ui-search">
							
								<li><label for="" class="ui-label">编号：</label> 
								<input id ="input-id" type="text" class="input w-1" name="id" value="${channel.id}" autocomplete="off"/>
								</li>
								<li id = "error" style="display: none;"><label for="" class="ui-label error_color">数字介于1 〜 999</label></li>
								<li><a id="J-button-submit" class="btn btn-important" href="javascript:void(0);">定 位<b class="btn-inner"></b></a></li>
								
								<li><a id="J-button-Pre" class="btn btn-important" href="javascript:void(0);">上一参数<b class="btn-inner"></b></a></li>
								
								<li><a id="J-button-Next" class="btn btn-important" href="javascript:void(0);">下一参数<b class="btn-inner"></b></a></li>
							</ul>					
						</form>


						<form  action="${currentContextPath}/channel/updateChannel" method="post" id="J-form" >
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">编号：</label>
								<input id ="id" type="text" class="input w-1" value="${channel.id}" disabled/>
								<input type="hidden" name="id" value="${channel.id}"/>

							</li>
							<li>
								<label for="" class="ui-label">渠道参数：</label>
								<input id="name" type="text" class="input w-3" name="name" value="${channel.name}" autocomplete="off"/>
								<label id ="error-3" for="" class="ui-label error_color" style="display: none;">长度不得超过10字符</label>
								<label for="" class="ui-label">使用链接：</label>
								<input type="text" class="input w-7" name="url" value="${channel.url}" autocomplete="off"/>
							</li>

							<li>
								<label for="" class="ui-label">生效次数：</label>
								<input id="vaild" type="text" class="input w-1" name="vaild" value="${channel.vaild}" autocomplete="off"/>&nbsp次 &nbsp&nbsp
								
								<label for="" class="ui-label">到期时间：</label>
								<input id ="J-date-end" type="text" class="input w-2" name="end_timeStr" value="${channel.end_time}" autocomplete="off"/>
																
								<label for="" class="ui-label">IP时间限制：</label>
								<input id="ip" type="text" class="input w-1" name="ip" value="${channel.ip}" autocomplete="off"/> 次/
								<input id="ip_time" type="text" class="input w-1" name="ip_time" value="${channel.ip_time}" autocomplete="off"/> 分钟
								
							</li>
							
							<div id="DivSuccessful" class="pop w-4" style="position:absolute;left:100px;display: none;">
							<i class="ico-success"></i>
							<h4 class="pop-text">确认成功</h4>
							</div>
							
							<div id="DivFailed" class="pop w-4" style="position:absolute;left:400px;display: none;">
							<i class="ico-error"></i>
							<h4 class="pop-text">确认失败，请重试</h4>
							</div>
							
							<input type="hidden" id ="status" value="${status}" />
							<li>
								<label for="" class="ui-label">暂停冻结时长：</label>
								<input id="freeze_time" type="text" class="input w-1" name="freeze_time" value="${channel.freeze_time}" autocomplete="off"/> 分钟
								
								<label for="" class="ui-label">触发永久冻结：</label>
								<input id="freeze" type="text" class="input w-1" name="freeze" value="${channel.freeze}" autocomplete="off"/> 次
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
								<label for="" class="ui-label">预警频率：</label>
								<input id="frequency" type="text" class="input w-1" name="frequency" value="${channel.frequency}" autocomplete="off"/> 次/
								<input id="frequency_time" type="text" class="input w-1" name="frequency_time" value="${channel.frequency_time}" autocomplete="off"/> 分钟
								
							</li>
							
							<li>
								<label for="" class="ui-label">每日重置：</label>
								<input id="reset" type="checkbox" class="checkbox " name="reset" value="${channel.reset}">
							</li>
							<li>
							<label for="" class="ui-label w-4">
							<a id="J-button-cancel" class="btn btn-primary"
									href="javascript:void(0);">取消操作<b class="btn-inner"></b></a>
							</label>
							
							<label for="" class="ui-label">
							<a id="J-button-submit-1" class="btn btn-important"
									href="javascript:void(0);">确认操作<b class="btn-inner"></b></a>
							</label>
							</li>
						</ul>
						</form><hr>
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">生成参数链接：</label>
							</li>
							<li>
								 &nbsp&nbsp &nbsp&nbsp &nbsp&nbsp &nbsp&nbsp &nbsp&nbsp
								 <input  type="text" class="input w-7" value="${url}">
							</li>
						</ul>
						
					</div>
				</div>
				
			</div>
		</div>
	</div>
</body>