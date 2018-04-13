<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="/tag-page" prefix="pg"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>站内信-发送站内信</title>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/notice/msgCreate.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/phoenix.Input.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/jquery/jquery-1.4.4.min.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor-1.2.1.min.js"></script>	
	<script type="text/javascript" src="${staticFileContextPath}/static/js/xheditor121/xheditor_lang/zh-cn.js"></script>
	<script type="text/javascript" src="${staticFileContextPath}/static/js/ajaxfileupload.js"></script>	
	<style>
	.j-ui-tip {background:#FFFFE1;border:1px solid #CCC;color:#333;font-size:12px;}
	.j-ui-tip .sj {display:none;}
	.ui-form-window li {margin-top:5px;margin-bottom:5px;}
	.pop-window-datepicker {z-index:710;}
	.upload-file-button {outline:none;-ms-filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=0);filter:alpha(opacity=0);opacity: 0;position:absolute;top:0px; left:0px; width:107px; height:30px;z-index:1;background:#000}
	.ui-check {display:none;}
	.btn-disabled,.btn-disabled:hover {color:#CCC; cursor:default;border-color:#CACACA;}
	.ui-form .J-panel-gourp {margin:0;}
	.panel-group-line {border-bottom:1px dotted #CCC;}
	.checkbox-list {border-bottom:1px dotted #CCC;display:inline-block;padding-bottom:10px;}
	.checkbox-list-last {border:0;}
	</style>
</head>
<body>
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="${currentContextPath}/noticeAdmin/goSysMsgManager/">通知中心</a> &gt; 发送站内信</div></div>
			<div class="col-content">
				<div class="col-main">
					<div class="main">
						<form id="J-form-upload" name="form1" enctype="multipart/form-data" method="post" action="${currentContextPath}/noticeAdmin/uploadUserFile" style="position:relative;">
							<input name="file" type="file" id="J-upload-file" class="upload-file-button" size="40" hidefocus="true" value="导入" />
						</form>
						
						<form  action="${currentContextPath}/noticeAdmin/createMsg" method="post" id="J-form">
						<ul class="ui-form">
							<li>
								<label for="" class="ui-label">接受用户：</label>
								<span class="radio-list" id="J-radio-list">
									<label for="J-switch-a" class="label"><input type="radio" name="sendType" value="1" class="radio" id="J-switch-a" checked="checked">按用户组</label>
									<label for="J-switch-b" class="label"><input type="radio" name="sendType" value="2" class="radio" id="J-switch-b">按用户名</label>
								</span>
							</li>
							<li class="J-panel-gourp">
								<label for="" class="ui-label">用户组：</label>
								<span class="checkbox-list">
									<label for="J-checkbox-all" class="label"><input id="J-checkbox-all" value="ALL" type="checkbox" value="1" class="checkbox" name="recGroups">全部客户</label>
								</span>
							</li>
							<li class="J-panel-gourp">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list">
									<label for="J-usergroup-2" class="label"><input type="checkbox" value="TOP_AGENT" class="checkbox" id="J-usergroup-2" name="recGroups">总代</label>
									<label for="J-usergroup-3" class="label"><input type="checkbox" value="AGENT_1" class="checkbox" id="J-usergroup-3" name="recGroups">一级代理</label>
									<label for="J-usergroup-4" class="label"><input type="checkbox" value="AGENT_2" class="checkbox" id="J-usergroup-4" name="recGroups">二级代理</label>
									<label for="J-usergroup-5" class="label"><input type="checkbox" value="AGENT_3" class="checkbox" id="J-usergroup-5" name="recGroups">三级代理</label>
									<label for="J-usergroup-6" class="label"><input type="checkbox" value="AGENT_4" class="checkbox" id="J-usergroup-6" name="recGroups">四级代理</label>
									<label for="J-usergroup-7" class="label"><input type="checkbox" value="AGENT_5" class="checkbox" id="J-usergroup-7" name="recGroups">五级代理</label>
									<label for="J-usergroup-8" class="label"><input type="checkbox" value="AGENT_xx" class="checkbox" id="J-usergroup-8" name="recGroups">xx级代理</label>
								
									<label for="J-usergroup-10" class="label"><input type="checkbox" value="PASSANGE" class="checkbox" id="J-usergroup-13" name="recGroups">玩家</label>
								</span>
							</li>
							<li class="J-panel-gourp">
								<label for="" class="ui-label"></label>
								<span class="checkbox-list checkbox-list-last">
									<label for="J-usergroup-11" class="label"><input type="checkbox" value="VIP" class="checkbox" id="J-usergroup-11" name="recGroups">VIP</label>
									<label for="J-usergroup-12" class="label"><input type="checkbox" value="NONVIP" class="checkbox" id="J-usergroup-12" name="recGroups">非VIP</label>
								</span>
								<span class="ui-check"><i></i>请选择一个用户组</span>
							</li>
							
							
							
							<li id="J-panel-username" style="display:none;">
								<label for="" class="ui-label">用户名：</label>
								<div class="textarea w-10" style="border:none;position:relative;">

									<iframe src="upload.php" name="check_file_frame" style="display:none;"></iframe>
									<div style="padding:0 0 10px 0;">
									<a class="btn" href="javascript:void(0);" onClick="document.getElementById('J-form-upload').file.click();">+ 导入用户名<b class="btn-inner"></b></a>&nbsp;&nbsp;（支持txt/csv格式文件）
									</div>
									<div>
									<textarea id="J-textarea-username" name="receives" style="border:1px solid #CCC;"></textarea>
									<span class="ui-check"><i></i>请导入或者填写接收人用户名</span>
									</div>
								</div>
							</li>
							
							
							<li>
								<label for="" class="ui-label">主题：</label>
								<input id="J-input-title" type="text" class="input w-8" name="title" value="主题长度不得超过40字符" />
								<span class="ui-check"><i></i>主题不能为空</span>
							</li>
							<li>
								<label for="" class="ui-label">内容：</label>
									<div class="textarea">
                                    <textarea id="J-content" name="content" rows="12" cols="80" style="width:100%;height:300px;">
                                    </textarea>
                                    <span class="ui-check"><i></i>内容不能超过2000字符</span>
								</div>
							</li>
							<li>
								<label for="" class="ui-label">发送时间：</label>
								<select class="ui-select w-2" id="J-select-date" name="status">
									<option value="2" selected="selected">当前时间</option>
									<option value="1">指定时间</option>
								</select>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input id="J-daatepicker" type="text" class="input w-3" name="gmtSendedStr" style="display:none;" />
								
								<span class="ui-check"><i></i>请选择一个时间</span>
							</li>
							<li>
								<label for="" class="ui-label">消息推送:</label>
								
								<span class="checkbox-list">
									<label class="label"><input type="radio" id="specialTip" name="messagePush" class="checkbox" value="0" checked>无</label>
									<label class="label"><input type="radio" id="specialTip" name="messagePush" class="checkbox" value="1">特殊提示</label>
									<label class="label"><input type="radio" id="powerTip" name="messagePush" class="checkbox" value="2">强制提示</label>
									<label class="label"><input type="radio" id="powerSecTip" name="messagePush" class="checkbox" value="3">强制5秒</label>
								</span>
							</li>
							<li>
								<label for="" class="ui-label">消息有效时间：</label>
								<input type="text" class="input w-1" value="48" id="J-input-time" name="effectHours" style="text-align:center;" />
								<span class="ui-text-info">小时</span>
								<span class="ui-check"><i></i>消息有效时间不能为空</span>								
							</li>
							<li class="ui-btn">
								<a id="J-button-view" class="btn btn-important" href="javascript:void(0);">预 览<b class="btn-inner"></b></a>
								<a id="J-button-submit" class="btn btn-disabled" href="javascript:void(0);">发 送<b class="btn-inner"></b></a>
								<a class="btn" href="${currentContextPath }/noticeAdmin/goSysMsgManager/">取消<b class="btn-inner"></b></a>
							</li>
						</ul>
						</form>
						
						
						<div style="height:600px;"></div>
						
						
						<div id="DivSuccessful" class="pop w-4" style="position:absolute;left:100px;display: none">
							<i class="ico-success"></i>
							<h4 class="pop-text">发送成功</h4>
						</div>
						
						<div id="DivFailed" class="pop w-4" style="position:absolute;left:400px;display: none">
							<i class="ico-error"></i>
							<h4 class="pop-text">发送失败，请重试</h4>
						</div>

						
					</div>
				</div>
			</div>
			
<script>
function ajaxFileUpload() {  
    $.ajaxFileUpload  
	    (  
	        {  
	            url: baseUrl+'/noticeAdmin/uploadUserFile',  
	            secureuri: false,  
	            fileElementId: 'J-upload-file',  
	            dataType: false,  
	            beforeSend: function() {  
	            },  
	            complete: function(data) { 
	            },  
	            success: function(data, status) {
	            	$("#J-textarea-username").val(data);
	            	$("#J-textarea-username").focus();
	            	$('#J-button-submit').removeClass('btn-disabled').addClass('btn-important');
	            },  
	            error: function(data, status, e) {  
	            }  
	        }  
	    )  
    return false;  
}  	
</script>
</body>